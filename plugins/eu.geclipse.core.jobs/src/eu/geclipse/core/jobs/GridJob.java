/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Pawel Wolniewicz 
 *    Mariusz Wojtysiak
 *    Ariel Garcia      - updated to new problem reporting
 *****************************************************************************/
package eu.geclipse.core.jobs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.filesystem.GEclipseURI;
import eu.geclipse.core.jobs.internal.Activator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobService;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.ResourceGridContainer;
import eu.geclipse.core.model.impl.ResourceGridElement;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.JSDLJobDescription;

/**
 * Class representing submitted job.
 */
public class GridJob extends ResourceGridContainer implements IGridJob {
  /**
   * Name for folder containing input files for job
   */
  public static final String FOLDERNAME_INPUT_FILES = Messages.getString( "GridJob.FolderInputFiles" ); //$NON-NLS-1$

  /**
   * Name for folder containing output files for job
   */
  public static final String FOLDERNAME_OUTPUT_FILES = Messages.getString( "GridJob.FolderOutputFiles" ); //$NON-NLS-1$
  
  static String JOBFILE_EXTENSION = ".job"; //$NON-NLS-1$

  final static private String FOLDER_PROPERTIES_QUALIFIER = "eu.geclipse.core.jobs.GridJob"; //$NON-NLS-1$
  final static private String FOLDER_PROPERTY_JOBID_CLASS = "JobID.class"; //$NON-NLS-1$
  final static private String JOBID_FILENAME = ".jobID"; //$NON-NLS-1$
  final static private String JOBINFO_FILENAME = ".jobInfo"; //$NON-NLS-1$
  final static private String JOBINFO_JOBDESCRIPTION_XMLNODENAME = "JobDescriptionFileName"; //$NON-NLS-1$
  final static private String JOBINFO_JOBNAME = "JobName"; //$NON-NLS-1$
  final static private String JOBINFO_SUBMISSIONTIME_XMLNODENAME = "SubmissionTime"; //$NON-NLS-1$
  final static private String JOBINFO_XMLNODENAME = "JobInfo"; //$NON-NLS-1$
  final static private String JOBSTATUS_FILENAME = ".jobStatus"; //$NON-NLS-1$
  final static private String XML_CHARSET = "ISO-8859-1"; //$NON-NLS-1$
  private IGridJobDescription jobDescription = null;
  private IFile jobDescriptionFile = null;
  private GridJobID jobID = null;
  private IFile jobIdFile = null;
  private IFile jobInfoFile = null;
  private String jobName;
  private IGridJobService jobService; // don't use it directly! Use getJobService()
  private GridJobStatus jobStatus = null;
  private IFile jobStatusFile = null;
  private Date submissionTime;
  
  /**
   * @param jobFolder
   */
  public GridJob( final IFolder jobFolder ) {
    super( jobFolder );
    this.jobStatusFile = jobFolder.getFile( JOBSTATUS_FILENAME );
    this.jobIdFile = jobFolder.getFile( JOBID_FILENAME );
    this.jobInfoFile = jobFolder.getFile( JOBINFO_FILENAME );
    this.jobName = getName();
    readJobID();
    setJobFolderProperties( jobFolder );
    readJobInfo( jobFolder );
    readChildren();    
    if( this.jobDescriptionFile != null ) {
      readJobDescription();
      try {
        addElement( this.jobDescription );
      } catch( ProblemException e ) {
        Activator.logException( e,
                                Messages.getString( "GridJob.errLoadJobDescription" ) //$NON-NLS-1$
                                    + jobFolder.getName() );
      }
    }
  }
  
  /**
   * Check if a job can be created from this folder. Currently it checks only if
   * directory contains job info file.
   * 
   * @param folder
   * @return true if job can be created
   */
  public static boolean canCreate( final IFolder folder ) {
    IFile infoFile = folder.getFile( GridJob.JOBINFO_FILENAME );
    return "job".equalsIgnoreCase( folder.getFileExtension() ) //$NON-NLS-1$
                                                              ? infoFile.exists()
                                                              : false;
  }
  /**
   * Creates files and folders for job
   * 
   * @param jobFolder folder, in which structure for job will be created
   * @param id job id
   * @param jobService
   * @param description job description
   * @param uniqueJobName
   * @return created job
   * @throws ProblemException
   */
  public static GridJob createJobStructure( final IFolder jobFolder,
                                            final GridJobID id,
                                            final IGridJobService jobService,
                                            final IGridJobDescription description,
                                            final String uniqueJobName )
    throws ProblemException
  {
    GridJob job = new GridJob( jobFolder );
    job.create( jobFolder, id, jobService, description, uniqueJobName );
    return job;
  }
  /**
   * Using job folder, get info about class implemented {@link IGridJobID} (it
   * helps to recognize GridJob middleware)
   * 
   * @param jobFolder
   * @return class implementing {@link IGridJobID}, or null if this information
   *         cannot be got
   */
  public static String getJobIdClass( final IFolder jobFolder ) {
    String jobIdClass = null;
    try {
      Object property = jobFolder.getSessionProperty( new QualifiedName( FOLDER_PROPERTIES_QUALIFIER,
                                                                         FOLDER_PROPERTY_JOBID_CLASS ) );
      if( property instanceof String ) {
        jobIdClass = ( String )property;
      }
    } catch( CoreException exception ) {
      Activator.logException( exception,
                              String.format( "Cannot get property for job folder " //$NON-NLS-1$
                                             + jobFolder.getName() ) );
    }
    return jobIdClass;
  }

  private void addStagingFolder( final IFolder jobFolder,
                                 final String folderName )
  {
    IFolder folder = jobFolder.getFolder( folderName );
    if( folder.exists() ) {
      try {
        createModelElement( folder );
      } catch( ProblemException exception ) {
        Activator.logException( exception,
                                String.format( Messages.getString( "GridJob.errAddFolder" ), folderName ) ); //$NON-NLS-1$
      }
    }
  }

  public void cancel() {
    // TODO pawel
  }

  // TODO Commented this out (temporarily?) since this does not work with
  // IGridContainer#findChild(String name) and IGridRoot#findElement(IPath path)
  /*
   * public String getName() { // return "Job ("+super.getName()+")"; String
   * name=super.getName(); IGridJobDescription desc = getJobDescription();
   * if(desc!=null){ name = name + "("+desc.getName()+")"; } return name; }
   */
  @Override
  public boolean canContain( final IGridElement element ) {
    return ( element instanceof IGridJobDescription )
           || ( element instanceof ResourceGridElement )
           || ( element instanceof ResourceGridContainer )
           || ( element instanceof IGridJob );
  }

  /**
   * @param jobFolder
   * @param id
   * @param jobSrvce
   * @param description
   * @param uniqueJobName
   * @throws ProblemException
   */
  public void create( final IFolder jobFolder,
                      final IGridJobID id,
                      final IGridJobService jobSrvce,
                      final IGridJobDescription description,
                      final String uniqueJobName ) throws ProblemException
  {
    this.submissionTime = Calendar.getInstance().getTime();
    this.jobStatusFile = jobFolder.getFile( JOBSTATUS_FILENAME );
    this.jobIdFile = jobFolder.getFile( JOBID_FILENAME );
    this.jobInfoFile = jobFolder.getFile( JOBINFO_FILENAME );
    this.jobID = ( GridJobID )id;
    this.jobService = jobSrvce;
    this.jobDescription = description;
    this.jobStatus = new GridJobStatus( Messages.getString( "GridJob.jobStatusSubmitted" ), //$NON-NLS-1$
                                        IGridJobStatus.SUBMITTED );
    this.jobName = uniqueJobName;
    writeJobDescription( description, jobFolder );
    writeJobID( id, jobFolder );
    writeJobStatus( this.jobStatusFile );
    writeJobInfo( description, jobFolder );
    createStagingFolders( jobFolder );
  }

  private void createFileLink( final IFolder localFolder,
                               final String localFilename,
                               final URI uri )
  {
    IFile file = localFolder.getFile( localFilename );
    if( !file.exists() ) {
      GEclipseURI geclURI = new GEclipseURI( uri );
      try {
        file.createLink( geclURI.toMasterURI(),
                         IResource.ALLOW_MISSING_LOCAL,
                         null );
      } catch( CoreException exception ) {
        Activator.logException( exception,
                                Messages.getString( "GridJob.errCreateLink" ) + uri.toString() ); //$NON-NLS-1$
      }
    }
  }

  private IGridJobService createJobService( final IGridJobID jobId ) {
    IGridJobService service = null;
    IGridElementCreator creator
      = GridModel.getCreatorRegistry().getCreator( jobId, IGridJobService.class );
    if( creator != null ) {
      try {
        IVirtualOrganization vo = getProject().getVO();
        service = ( IGridJobService )creator.create( vo );
      } catch( ProblemException exception ) {
        Activator.logException( exception,
                                Messages.getString( "GridJob.createJobServiceFailederr" ) ); //$NON-NLS-1$
      }
    }
    return service;
  }

  private IGridElement createModelElement( final IResource resource )
    throws ProblemException
  {
    IGridElement element = null;
    IGridElementCreator creator = findCreator( resource );
    if( creator != null ) {
      element = create( creator );
    }
    return element;
  }

  private IFolder createStagingFilesFolder( final IFolder jobFolder,
                                            final String folderName,
                                            final Map<String, URI> filesMap )
    throws CoreException
  {
    IFolder folder = jobFolder.getFolder( folderName );
    if( !folder.exists() ) {
      folder.create( true, true, null );
    }
    for( String filename : filesMap.keySet() ) {
      URI uri = filesMap.get( filename );
      String scheme = uri.getScheme();
      if( scheme != null && scheme.length() > 0 ) {
        createFileLink( folder, filename, uri );
      }
    }
    return folder;
  }

  private void createStagingFolders( final IFolder jobFolder )
  {
    if( this.jobDescription instanceof JSDLJobDescription ) {
      IGridJobService service = getJobService();

      try {
        Map<String, URI> inputFilesMap = service.getInputFiles( getID(),
                                                                getJobDescription(),
                                                                getProject().getVO() );
        if( inputFilesMap != null && inputFilesMap.size() > 0 ) {
          createStagingFilesFolder( jobFolder,
                                    FOLDERNAME_INPUT_FILES,
                                    inputFilesMap );
        }
      } catch( CoreException exception ) {
        Activator.logException( exception );
      }
      
      try {
        Map<String, URI> outputFilesMap = service.getOutputFiles( getID(),
                                                                getJobDescription(),
                                                                getProject().getVO() );
        if( outputFilesMap != null && outputFilesMap.size() > 0 ) {
          createStagingFilesFolder( jobFolder,
                                    FOLDERNAME_OUTPUT_FILES,
                                    outputFilesMap );
        }
      } catch( CoreException exception ) {
        Activator.logException( exception );
      }
    }
  }

  private Document createXmlDocument( final IFile xmlFile ) {
    Document xmlDocument = null;
    if( xmlFile.exists() ) {
      try {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance()
          .newDocumentBuilder();
        InputStream inputStream = xmlFile.getContents( true );
        xmlDocument = documentBuilder.parse( inputStream );
        inputStream.close();
      } catch( CoreException e ) {
        logXmlError( IStatus.WARNING,
                     Messages.getString( "GliteJob.jobfile_contents" ), //$NON-NLS-1$
                     e );
      } catch( SAXException e ) {
        logXmlError( IStatus.WARNING,
                     Messages.getString( "GliteJob.jobfile_parse_xml" ), //$NON-NLS-1$
                     e );
      } catch( IOException e ) {
        logXmlError( IStatus.WARNING,
                     Messages.getString( "GliteJob.jobfile_contents" ), //$NON-NLS-1$
                     e );
      } catch( ParserConfigurationException e ) {
        logXmlError( IStatus.WARNING,
                     Messages.getString( "GliteJob.jobfile_parse_xml" ), //$NON-NLS-1$
                     e );
      }
    }
    return xmlDocument;
  }

  public void deleteJob( final IProgressMonitor monitor )
    throws ProblemException
  {
    try {
      IGridJobService service = getJobService();
      if( service != null ) {
        service.deleteJob( this.getID(), getProject().getVO(), monitor );
      }
    } finally {
      monitor.done();
    }
  }

  /*
   * GridJobs adds children in constructor. Children should not be handled by
   * model.
   * 
   * @see eu.geclipse.core.model.impl.ResourceGridContainer#fetchChildren(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected IStatus fetchChildren( final IProgressMonitor monitor ) {
    return Status.OK_STATUS;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridResource#getHostName()
   */
  /**
   * @return hostname
   */
  public String getHostName() {
    // A job is not a physical resource so we will always return null
    return null;
  }

  public IGridJobID getID() {
    if( this.jobID == null ) {
      if( this.jobIdFile.exists() ) {
        readJobID();
      }
    }
    return this.jobID;
  }

  public List<URI> getInputStagers() throws ProblemException {
    return getStagers( FOLDERNAME_INPUT_FILES );    
  }

  public IGridJobDescription getJobDescription() {
    if( this.jobDescription == null ) {
      if( this.jobDescriptionFile != null ) {
        IGridElement element = this.findChild( this.jobDescriptionFile.getName() );
        if( element instanceof IGridJobDescription )
          this.jobDescription = ( IGridJobDescription )element;
        else if( this.jobDescriptionFile.exists() ) {
          readJobDescription();
        }
      }
    }
    return this.jobDescription;
  }

  public String getJobName() {
    return this.jobName;
  }

  /**
   * @return job service or null if service doesn't exists for that job.<br>
   *         Service may not exists if middleware doesn't support it, or if job
   *         was created in g-eclipse old version
   */
  public IGridJobService getJobService() {
    if( this.jobService == null ) {
      this.jobService = createJobService( this.jobID );
    }
    return this.jobService;
  }

  public IGridJobStatus getJobStatus() {
    if( this.jobStatus == null ) {
      if( this.jobStatusFile.exists() ) {
        readJobStatus();
      }
    }
    return this.jobStatus != null
                                 ? this.jobStatus
                                 : GridJobStatus.UNKNOWN_STATUS;
  }

  public IGridElementManager getManager() {
    return GridModel.getJobManager();
  }

  public List<URI> getOutputStagers() throws ProblemException {
    return getStagers( FOLDERNAME_OUTPUT_FILES );    
  }

  private List<URI> getStagers( final String stagersFolderName )
    throws ProblemException
  {
    List<URI> results = new ArrayList<URI>();
    IGridElement element = findChild( stagersFolderName );
    try {
      if( element instanceof IGridContainer ) {
        IGridContainer folder = ( IGridContainer )element;
        IGridElement[] childrenElements = folder.getChildren( null );
        for( IGridElement gridElement : childrenElements ) {
          if( gridElement instanceof IGridConnectionElement ) {
            IGridConnectionElement connectionElement = ( IGridConnectionElement )gridElement;
            results.add( connectionElement.getURI() );
          }
        }
      }
    } catch( CoreException exception ) {
      if( exception instanceof ProblemException ) {
        throw ( ProblemException )exception;
      } 
      throw new ProblemException( "eu.geclipse.core.jobs.problem.getStagersFailed", //$NON-NLS-1$
                                  exception,
                                  Activator.PLUGIN_ID );
    }
    return results;
  }

  public Date getSubmissionTime() {
    return this.submissionTime;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.getURI#getURI()
   */
  /**
   * @return uri
   */
  public URI getURI() {
    // TODO pawelw is there something like a contact string?
    return null;
  }

  private DateFormat getXmlDateFormatter() {
    DateFormat formatter = DateFormat.getDateTimeInstance( DateFormat.MEDIUM,
                                                           DateFormat.MEDIUM,
                                                           new Locale( "Locale.US" ) ); //$NON-NLS-1$
    if( formatter == null ) {
      formatter = DateFormat.getDateTimeInstance( DateFormat.MEDIUM,
                                                  DateFormat.MEDIUM );
    }
    formatter.setLenient( true );
    return formatter;
  }


  @Override
  public boolean isHidden() {
    return false;
  }

  @Override
  public boolean isLazy() {
    return false;
  }

  @Override
  public boolean isLocal() {
    return true;
  }

  @Override
  public boolean isVirtual() {
    return false;
  }

  private void logXmlError( final int severity,
                            final String errorString,
                            final Throwable exception )
  {
    IResource resource = getResource();
    StringBuilder messageStringBuilder = new StringBuilder( errorString == null
                                                                               ? "null" //$NON-NLS-1$
                                                                               : errorString );
    if( resource != null && resource.getFullPath() != null ) {
      messageStringBuilder.append( " File: " ); //$NON-NLS-1$
      messageStringBuilder.append( resource.getFullPath() );
    }
    Activator.logStatus( new Status( severity,
                                     Activator.PLUGIN_ID,
                                     IStatus.OK,
                                     messageStringBuilder.toString(),
                                     exception ) );
  }

  private void readChildren() {
    IFolder jobFolder = ( IFolder )this.getResource();
    try {
      for( IResource child : jobFolder.members( true ) ) {
        if( child instanceof IFolder )
        {
          createModelElement( child );
        }
      }
    } catch( CoreException exception ) {
      Activator.logException( exception, "Cannot read children jobs." ); //$NON-NLS-1$
    }
  }

  private void readJobDescription() {
    try {
      IGridElementCreator elementCreator
        = GridModel.getCreatorRegistry().getCreator( this.jobDescriptionFile, IGridJobDescription.class );
      if( elementCreator != null ) {
        IGridElement description = elementCreator.create( this );
        this.jobDescription = ( IGridJobDescription )description;
      }
    } catch( Exception e ) {
      Activator.logException( e );
    }
  }

  private void readJobID() {
    Document document = createXmlDocument( this.jobIdFile );
    if( document == null ) {
      this.jobID = new GridJobID();
      this.jobID.setJob( this );
    } else {
      Element rootElement = document.getDocumentElement();
      if( !GridJobID.XML_ROOT.equals( rootElement.getNodeName() ) ) {
        // TODO pawelw - file is not correct
      }
      String className = rootElement.getAttribute( GridJobID.XML_ATTRIBUTE_CLASS );
      if( className != null && !className.equals( "" ) ) { //$NON-NLS-1$
        ExtensionManager manager = new ExtensionManager();
        List<IConfigurationElement> list = manager.getConfigurationElements( "eu.geclipse.core.jobs.jobID", //$NON-NLS-1$
                                                                             "JobID" ); //$NON-NLS-1$
        for( IConfigurationElement element : list ) {
          String attr = element.getAttribute( "class" ); //$NON-NLS-1$
          if( className.equals( attr ) ) {
            try {
              this.jobID = ( GridJobID )element.createExecutableExtension( "class" ); //$NON-NLS-1$
              this.jobID.setXMLNode( rootElement );
              this.jobID.setJob( this );
            } catch( CoreException e ) {
              // TODO Auto-generated catch block
            }
          }
        }
      }
      if( this.jobID == null ) {
        this.jobID = new GridJobID( rootElement );
        this.jobID.setJob( this );
      }
    }
  }

  private void readJobInfo( final IFolder jobFolder ) {
    Document document = createXmlDocument( this.jobInfoFile );
    if( document != null ) {
      Element rootElement = document.getDocumentElement();
      if( !GridJob.JOBINFO_XMLNODENAME.equals( rootElement.getNodeName() ) ) {
        // Wrong format of jobInfo file
      } else {
        Node node;
        NodeList childNodes = rootElement.getChildNodes();
        for( int i = 0; i < childNodes.getLength(); i++ ) {
          node = childNodes.item( i );
          if( JOBINFO_JOBDESCRIPTION_XMLNODENAME.equals( node.getNodeName() ) )
          {
            String filename = node.getTextContent();
            if( filename != null ) {
              filename = filename.trim();
            }
            this.jobDescriptionFile = jobFolder.getFile( filename );
          } else if( JOBINFO_SUBMISSIONTIME_XMLNODENAME.equals( node.getNodeName() ) )
          {
            try {
              String textContent = node.getTextContent();
              if( textContent != null && textContent.length() > 0 ) {
                try {
                  this.submissionTime = getXmlDateFormatter().parse( textContent );
                } catch( ParseException exception ) {
                  this.submissionTime = DateFormat.getDateTimeInstance( DateFormat.SHORT,
                                                                        DateFormat.SHORT,
                                                                        new Locale( "Locale.US" ) ).parse( textContent ); //$NON-NLS-1$
                }
              }
            } catch( DOMException e ) {
              // empty implementation
            } catch( ParseException e ) {
              // empty implementation
            }
          } else if( JOBINFO_JOBNAME.equals( node.getNodeName() ) ) {
            this.jobName = node.getTextContent();
          }
        }
      }
    }
  }

  private void readJobStatus() {
    Document document = createXmlDocument( this.jobStatusFile );
    if( document == null ) {
      this.jobStatus = new GridJobStatus();
    } else {
      Element rootElement = document.getDocumentElement();
      if( !GridJobStatus.XML_ROOT.equals( rootElement.getNodeName() ) ) {
        // TODO pawelw - file is not correct
      }
      String className = rootElement.getAttribute( GridJobStatus.XML_ATTRIBUTE_CLASS );
      if( className != null && !className.equals( "" ) ) { //$NON-NLS-1$
        ExtensionManager manager = new ExtensionManager();
        List<IConfigurationElement> list = manager.getConfigurationElements( "eu.geclipse.core.jobs.jobStatus", //$NON-NLS-1$
                                                                             "JobStatus" ); //$NON-NLS-1$
        for( IConfigurationElement element : list ) {
          String attr = element.getAttribute( "class" ); //$NON-NLS-1$
          if( className.equals( attr ) ) {
            try {
              this.jobStatus = ( GridJobStatus )element.createExecutableExtension( "class" ); //$NON-NLS-1$
              this.jobStatus.setXMLNode( rootElement );
            } catch( CoreException e ) {
              Activator.logException( e );
              this.jobStatus = null;// instead not initialized middleware
                                    // status, rather use GridJobStatus
            }
          }
        }
      }
      if( this.jobStatus == null )
        try {
          this.jobStatus = new GridJobStatus( rootElement );
        } catch( ProblemException exception ) {
          // ignore errors
        }
      }
  }

  private void setJobFolderProperties( final IFolder jobFolder ) {
    try {
      jobFolder.setSessionProperty( new QualifiedName( FOLDER_PROPERTIES_QUALIFIER,
                                                       FOLDER_PROPERTY_JOBID_CLASS ),
                                    this.jobID.getClass().getName() );
    } catch( CoreException exception ) {
      Activator.logException( exception,
                              String.format( "Cannot set property for job folder " //$NON-NLS-1$
                                             + jobFolder.getName() ) );
    }
  }

  private void testCancelled( final IProgressMonitor progressMonitor ) {
    if( progressMonitor.isCanceled() ) {
      throw new OperationCanceledException();
    }
  }

  public IGridJobStatus updateJobStatus( final IProgressMonitor progressMonitor )
  {
    SubMonitor subMonitor = SubMonitor.convert( progressMonitor );
    IGridJobStatus newJobStatus = null;
    try {
      testCancelled( subMonitor );
      IGridJobService service = getJobService();
      if( service != null && this.jobID.getJobID() != GridJobID.UNKNOWN ) {
        IGridProject project = getProject();
        
        if( project != null ) {
          newJobStatus = service.getJobStatus( this.jobID,
                                               project.getVO(),
                                               subMonitor );
        }
        testCancelled( subMonitor );
      }
      if( newJobStatus != null && newJobStatus instanceof GridJobStatus && !subMonitor.isCanceled() ) {
        this.jobStatus = ( GridJobStatus )newJobStatus;
        writeJobStatus( this.jobStatusFile );
      }
    } catch( ProblemException e ) {
      Activator.logException( e );
    }
    // jobStatus = new GridJobStatus( jobID );
    return this.jobStatus;
  }

  /**
   * Writes job status XML to job folder
   * 
   * @param description - Job description
   * @param jobFolder - Folder to which file should be written
   * @throws ProblemException
   */
  private void writeJobDescription( final IGridJobDescription description,
                                    final IFolder jobFolder )
    throws ProblemException
  {
    IFile sourceDescriptionFile = null;
    if( description.getResource() instanceof IFolder ) {
      IFolder sourceDescriptionFolder = ( IFolder )description.getResource();
      sourceDescriptionFile = sourceDescriptionFolder.getFile( sourceDescriptionFolder.getName() );
    } else {
      sourceDescriptionFile = ( IFile )description.getResource();
    }
    // create job description file
    this.jobDescriptionFile = jobFolder.getFile( sourceDescriptionFile.getName() );
    try {
      InputStream is = sourceDescriptionFile.getContents( true );
      this.jobDescriptionFile.create( is, true, null );
      is.close();
    } catch( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                  "Problem while creating job description file", //$NON-NLS-1$
                                  cExc,
                                  Activator.PLUGIN_ID );
    } catch( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                  "Problem while creating job description file", //$NON-NLS-1$
                                  ioExc,
                                  Activator.PLUGIN_ID );
    }
  }
  
  /**
   * Writes job status XML to job folder
   * 
   * @param id - JobID
   * @param jobFolder - Folder to which file should be written
   * @throws ProblemException
   */
  private void writeJobID( final IGridJobID id, final IFolder jobFolder )
    throws ProblemException
  {
    IFile file;
    // create jobID file
    file = jobFolder.getFile( GridJob.JOBID_FILENAME );
    if( !( id instanceof GridJobID ) ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_SAVE_FAILED,
                                  "GridJobID was expected instead of " //$NON-NLS-1$
                                      + id.getClass(),
                                  Activator.PLUGIN_ID );
    }
    String xml = ( ( GridJobID )id ).getXML();
    try {
      byte[] byteArray = xml.getBytes( XML_CHARSET ); // choose a charset
      // //$NON-NLS-1$
      ByteArrayInputStream baos = new ByteArrayInputStream( byteArray );
      file.create( baos, true, null );
    } catch( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                  "Problem while creating job ID file " //$NON-NLS-1$
                                      + file.getName(),
                                  cExc,
                                  Activator.PLUGIN_ID );
    } catch( UnsupportedEncodingException e ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                  "Problem while creating job ID file ", //$NON-NLS-1$
                                  e,
                                  Activator.PLUGIN_ID );
    }
  }
  
  /**
   * Writes XML with job information to .jobinfo file
   * 
   * @param description - JobDescription
   * @param jobFolder - Folder to which file should be written
   * @throws ProblemException
   */
  private void writeJobInfo( final IGridJobDescription description,
                             final IFolder jobFolder ) throws ProblemException
  {
    IFile file;
    String xml;
    byte[] byteArray;
    ByteArrayInputStream baos;
    // create jobInfo file
    file = jobFolder.getFile( GridJob.JOBINFO_FILENAME );
    IFile descFile = null;
    if( description.getResource() instanceof IFolder ) {
      IFolder sourceDescriptionFolder = ( IFolder )description.getResource();
      descFile = sourceDescriptionFolder.getFile( sourceDescriptionFolder.getName() );
    } else {
      descFile = ( IFile )description.getResource();
    }
    // TODO pawelw - move it to GridJob
    xml = "<" //$NON-NLS-1$
          + JOBINFO_XMLNODENAME
          + ">" //$NON-NLS-1$
          + "<" //$NON-NLS-1$
          + JOBINFO_JOBDESCRIPTION_XMLNODENAME
          + " class=\"" //$NON-NLS-1$
          + description.getClass().getName()
          + "\">" //$NON-NLS-1$
          + descFile.getName()
          + "</" //$NON-NLS-1$
          + JOBINFO_JOBDESCRIPTION_XMLNODENAME
          + ">" //$NON-NLS-1$
          + "<" //$NON-NLS-1$
          + JOBINFO_SUBMISSIONTIME_XMLNODENAME
          + ">" //$NON-NLS-1$
          + ( this.submissionTime != null
                                         ? getXmlDateFormatter().format( this.submissionTime )
                                         : "" ) //$NON-NLS-1$
          + "</" //$NON-NLS-1$
          + JOBINFO_SUBMISSIONTIME_XMLNODENAME
          + ">" //$NON-NLS-1$
          + "<" + JOBINFO_JOBNAME + ">" + this.jobName + "</" + JOBINFO_JOBNAME + ">" //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
          + "</" //$NON-NLS-1$
          + JOBINFO_XMLNODENAME
          + ">"; //$NON-NLS-1$
    try {
      byteArray = xml.getBytes( XML_CHARSET ); // choose a charset
      baos = new ByteArrayInputStream( byteArray );
      file.create( baos, true, null );
    } catch( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                  "Problem while creating job information file " //$NON-NLS-1$
                                      + file.getName(),
                                  cExc,
                                  Activator.PLUGIN_ID );
    } catch( UnsupportedEncodingException e ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                  "Problem while creating job information file ", //$NON-NLS-1$
                                  e,
                                  Activator.PLUGIN_ID );
    }
  }
  
  /**
   * Writes job status XML to job folder
   * 
   * @param jobFolder - Folder to which file should be written
   * @throws ProblemException
   */
  private void writeJobStatus( final IFile _jobStatusFile )
    throws ProblemException
  {
    String xml;
    byte[] byteArray;
    ByteArrayInputStream baos;
    // create jobStatus file
    xml = this.jobStatus.getXML();
    try {
      byteArray = xml.getBytes( XML_CHARSET ); // choose a charset
      baos = new ByteArrayInputStream( byteArray );
      if( _jobStatusFile.exists() ) {
        _jobStatusFile.setContents( baos, true, true, null );
      } else {
        // if job is deleted, then status updater is stopped later and it's possible that we are trying to store status for deleted job.
        // In that case just ignore creation of status file
        if( _jobStatusFile.getParent().exists() ) {
          _jobStatusFile.create( baos, true, null );
        }
      }
    } catch( CoreException cExc ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                  "Problem while writing job status file " //$NON-NLS-1$
                                      + _jobStatusFile.getName(),
                                  cExc,
                                  Activator.PLUGIN_ID );
    } catch( UnsupportedEncodingException e ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_CREATE_FAILED,
                                  "Problem while creating job status file ", //$NON-NLS-1$
                                  e,
                                  Activator.PLUGIN_ID );
    }
  }
  
}
