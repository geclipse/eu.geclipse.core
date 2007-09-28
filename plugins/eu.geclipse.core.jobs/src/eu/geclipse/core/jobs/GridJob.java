/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *****************************************************************************/
package eu.geclipse.core.jobs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eu.geclipse.core.AbstractProblem;
import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.GridException;
import eu.geclipse.core.GridJobStatusServiceFactoryManager;
import eu.geclipse.core.ProblemRegistry;
import eu.geclipse.core.jobs.internal.Activator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.GridModelProblems;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.core.model.IGridJobStatusService;
import eu.geclipse.core.model.IGridJobStatusServiceFactory;
import eu.geclipse.core.model.impl.ResourceGridContainer;
import eu.geclipse.core.model.impl.ResourceGridElement;
import eu.geclipse.jsdl.JSDLJobDescription;

/**
 * Class representing submitted job.
 */
public class GridJob extends ResourceGridContainer implements IGridJob {

  final static private String JOBID_FILENAME = ".jobID"; //$NON-NLS-1$
  final static private String JOBINFO_FILENAME = ".jobInfo"; //$NON-NLS-1$
  final static private String JOBSTATUS_FILENAME = ".jobStatus"; //$NON-NLS-1$
  final static private String JOBINFO_XMLNODENAME = "JobInfo"; //$NON-NLS-1$
  final static private String JOBINFO_JOBDESCRIPTION_XMLNODENAME = "JobDescriptionFileName"; //$NON-NLS-1$
  final static private String JOBINFO_SUBMISSIONTIME_XMLNODENAME = "SubmissionTime"; //$NON-NLS-1$
  private GridJobID jobID = null;
  private IGridJobDescription jobDescription = null;
  private GridJobStatus jobStatus = null;
  private IFile jobDescriptionFile=null;
  private IFile jobIdFile=null;
  private IFile jobStatusFile=null;
  private IFile jobInfoFile=null;
  private IGridJobStatusService statusService;
  private Date submissionTime;

  /**
   * @param jobFolder
   */
  public GridJob( final IFolder jobFolder ) {
    super( jobFolder );
    this.jobStatusFile = jobFolder.getFile( JOBSTATUS_FILENAME );
    this.jobIdFile = jobFolder.getFile( JOBID_FILENAME );
    this.jobInfoFile = jobFolder.getFile( JOBINFO_FILENAME );
    readJobInfo( jobFolder );
    // gridJobFolder = new GridJobFolder( jobFolder, this );
    readJobID();
    if( this.jobDescriptionFile != null ) {
      readJobDescription();
      try {
        ResourceAttributes attributes = this.jobDescription.getResource().getResourceAttributes();
        attributes.setReadOnly( true );
        try {
          this.jobDescription.getResource().setResourceAttributes(attributes);
        } catch( CoreException e ) {
          Activator.logException( e );
        }
        addElement( this.jobDescription );
      } catch( GridModelException e ) {
        Activator.logException( e, "Cannot load Job Description of job"
                                   + jobFolder.getName() );
      }
    }
    IGridJobStatusServiceFactory factory = GridJobStatusServiceFactoryManager.getFactory( this.jobID.getClass() );
    if( factory != null ) {
      this.statusService = factory.getGridJobStatusService( this.jobID );
    }
  }

  public static void createJobStructure( final IFolder jobFolder,
                                         final GridJobID id,
                                         final IGridJobDescription description )
    throws GridModelException
  {
    GridJob job = new GridJob( jobFolder );
    job.create( jobFolder, id, description );
  }

  /**
   * @param jobFolder
   * @param id
   * @param description
   * @throws GridModelException
   */
  public void create( final IFolder jobFolder,
                      final GridJobID id,
                      final IGridJobDescription description )
    throws GridModelException
  {
    this.submissionTime = Calendar.getInstance().getTime();
    this.jobStatusFile = jobFolder.getFile( JOBSTATUS_FILENAME );
    this.jobIdFile = jobFolder.getFile( JOBID_FILENAME );
    this.jobInfoFile = jobFolder.getFile( JOBINFO_FILENAME );
    this.jobID = id;
    this.jobDescription = description;
    this.jobStatus = new GridJobStatus( Messages.getString( "GridJob.jobStatusSubmitted" ), //$NON-NLS-1$
                                        IGridJobStatus.SUBMITTED );
    writeJobDescription( description, jobFolder );
    writeJobID( id, jobFolder );
    writeJobStatus( this.jobStatusFile );
    writeJobInfo( description, jobFolder );
    // IPath path = jobFolder.getFullPath().append( jobDescriptionFile.getName()
    // );
    // try {
    // jobDescriptionFile.copy( path, true, null );
    // } catch( CoreException e ) {
    // throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
    // e,
    // "Problem while creating job description file" );
    // }
    // IGridElementCreator creator = findCreator( jobDescriptionFile );
    // if( creator != null ) {
    // create( creator );
    // }
    // IGridJobStatusServiceFactory factory =
    // GridJobStatusServiceFactoryManager.getFactory( id.getClass() );
    // if( factory != null ) {
    // statusService = factory.getGridJobStatusService( id );
    // }
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
            if( filename != null )
              filename = filename.trim();
            this.jobDescriptionFile = jobFolder.getFile( filename );
          }
          if( JOBINFO_SUBMISSIONTIME_XMLNODENAME.equals( node.getNodeName() ) )
          {
            DateFormat df = DateFormat.getDateTimeInstance();
            try {
              this.submissionTime = df.parse( node.getTextContent() );
            } catch( DOMException e ) {
              // empty implementation
            } catch( ParseException e ) {
              // empty implementation
            }
          }
        }
      }
    }
  }

  public void cancel() {
    // TODO pawel
  }

  public IGridJobID getID() {
    if( this.jobID == null ) {
      if( this.jobIdFile.exists() ) {
        readJobID();
      }
    }
    return this.jobID;
  }

  private void readJobID() {
    Document document = createXmlDocument( this.jobIdFile );
    if( document == null ) {
      this.jobID = new GridJobID();
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
            } catch( CoreException e ) {
              // TODO Auto-generated catch block
            }
          }
        }
      }
      if( this.jobID == null )
        this.jobID = new GridJobID( rootElement );
    }
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
        List<IConfigurationElement> list = manager.getConfigurationElements( "eu.geclipse.grid.jobStatu", //$NON-NLS-1$
                                                                             "JobStatus" ); //$NON-NLS-1$
        for( IConfigurationElement element : list ) {
          String attr = element.getAttribute( "class" ); //$NON-NLS-1$
          if( className.equals( attr ) ) {
            try {
              this.jobStatus = ( GridJobStatus )element.createExecutableExtension( "class" ); //$NON-NLS-1$
              this.jobStatus.setXMLNode( rootElement );
            } catch( CoreException e ) {
              // TODO Auto-generated catch block
            }
          }
        }
      }
      if( this.jobStatus == null )
        this.jobStatus = new GridJobStatus( rootElement );
    }
  }

  public IGridJobStatus updateJobStatus() {
    IGridJobStatus newJobStatus = null;
    try {
      if( this.statusService != null
          && this.jobID.getJobID() != GridJobID.UNKNOWN )
      {
        newJobStatus = this.statusService.getJobStatus( this.jobID );
      }
      if( newJobStatus != null && newJobStatus instanceof GridJobStatus ) {
        this.jobStatus = ( GridJobStatus )newJobStatus;
        writeJobStatus( this.jobStatusFile );
      }
    } catch( GridException e ) {
      Activator.logException( e );
    }
    // jobStatus = new GridJobStatus( jobID );
    return this.jobStatus;
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

  public IGridElementManager getManager() {
    return GridModel.getJobManager();
  }

  // public JSDLJobDescription getJsdlDescription() {
  // new JSDLJobDescription(jobDescriptionFile);
  // return jsdlDescription;
  // }
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

  private void readJobDescription() {
    this.jobDescription = new JSDLJobDescription( this.jobDescriptionFile );
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

  private void logXmlError( final int severity,
                            final String errorString,
                            final Throwable exception )
  {
    IResource resource = getResource();
    // Activator.logStatus( new Status( severity,
    // Activator.PLUGIN_ID,
    // IStatus.OK,
    // errorString + " " + resource.getFullPath(), //$NON-NLS-1$
    // exception ) );
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

  // Commented this out (temporarily?) since this does not work with
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
           || ( element instanceof ResourceGridContainer );
  }

  @Override
  public boolean isHidden() {
    return false;
  }

  /**
   * Writes job status XML to job folder
   * 
   * @param description - Job description
   * @param jobFolder - Folder to which file should be written
   * @throws GridModelException
   */
  private void writeJobDescription( final IGridJobDescription description,
                                    final IFolder jobFolder )
    throws GridModelException
  {
    // create job description file
    IFile sourceDescriptionFile = ( IFile )description.getResource();
    this.jobDescriptionFile = jobFolder.getFile( sourceDescriptionFile.getName() );
    try {
      InputStream is = sourceDescriptionFile.getContents( true );
      this.jobDescriptionFile.create( is, true, null );
      is.close();
    } catch( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    cExc,
                                    "Problem while creating job description file" ); //$NON-NLS-1$
    } catch( IOException ioExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    ioExc,
                                    "Problem while creating job description file" ); //$NON-NLS-1$
    }
  }

  /**
   * Writes job status XML to job folder
   * 
   * @param id - JobID
   * @param jobFolder - Folder to which file should be written
   * @throws GridModelException
   */
  private void writeJobID( final IGridJobID id, final IFolder jobFolder )
    throws GridModelException
  {
    IFile file;
    // create jobID file
    file = jobFolder.getFile( GridJob.JOBID_FILENAME );
    if( !( id instanceof GridJobID ) ) {
      throw new GridModelException( GridModelProblems.ELEMENT_SAVE_FAILED,
                                    "GridJobID was expected instead of " //$NON-NLS-1$
                                        + id.getClass() );
    }
    String xml = ( ( GridJobID )id ).getXML();
    Activator.consoleLog( xml );
    try {
      byte[] byteArray = xml.getBytes( "ISO-8859-1" ); // choose a charset
      // //$NON-NLS-1$
      ByteArrayInputStream baos = new ByteArrayInputStream( byteArray );
      file.create( baos, true, null );
    } catch( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    cExc,
                                    "Problem while creating job ID file " //$NON-NLS-1$
                                        + file.getName() );
    } catch( UnsupportedEncodingException e ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    e,
                                    "Problem while creating job ID file " ); //$NON-NLS-1$
    }
  }

  /**
   * Writes job status XML to job folder
   * 
   * @param jobFolder - Folder to which file should be written
   * @throws GridModelException
   */
  private void writeJobStatus( final IFile _jobStatusFile )
    throws GridModelException
  {
    String xml;
    byte[] byteArray;
    ByteArrayInputStream baos;
    // create jobStatus file
    xml = this.jobStatus.getXML();
    // TODO - pawelw - remove it
    Activator.consoleLog( xml );
    try {
      byteArray = xml.getBytes( "ISO-8859-1" ); // choose a charset
      // //$NON-NLS-1$
      baos = new ByteArrayInputStream( byteArray );
      if( _jobStatusFile.exists() ) {
        _jobStatusFile.setContents( baos, true, true, null );
      } else {
        _jobStatusFile.create( baos, true, null );
      }
    } catch( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    cExc,
                                    "Problem while writing job status file " //$NON-NLS-1$
                                        + _jobStatusFile.getName() );
    } catch( UnsupportedEncodingException e ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    e,
                                    "Problem while creating job status file " ); //$NON-NLS-1$
    }
  }

  /**
   * Writes XML with job information to .jobinfo file
   * 
   * @param description - JobDescription
   * @param jobFolder - Folder to which file should be written
   * @throws GridModelException
   */
  private void writeJobInfo( final IGridJobDescription description,
                             final IFolder jobFolder )
    throws GridModelException
  {
    IFile file;
    String xml;
    byte[] byteArray;
    ByteArrayInputStream baos;
    // create jobInfo file
    file = jobFolder.getFile( GridJob.JOBINFO_FILENAME );
    IFile descFile = ( IFile )description.getResource();
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
          + this.submissionTime
          + "</" //$NON-NLS-1$
          + JOBINFO_SUBMISSIONTIME_XMLNODENAME
          + ">" //$NON-NLS-1$
          + "</" //$NON-NLS-1$
          + JOBINFO_XMLNODENAME
          + ">"; //$NON-NLS-1$
    Activator.consoleLog( xml );
    try {
      byteArray = xml.getBytes( "ISO-8859-1" ); // choose a charset
      // //$NON-NLS-1$
      baos = new ByteArrayInputStream( byteArray );
      file.create( baos, true, null );
    } catch( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    cExc,
                                    "Problem while creating job information file " //$NON-NLS-1$
                                        + file.getName() );
    } catch( UnsupportedEncodingException e ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    e,
                                    "Problem while creating job information file " ); //$NON-NLS-1$
    }
  }

  public Date getSubmissionTime() {
    return this.submissionTime;
  }

  // public Object getAdapter(Class cl){
  // Object adapter=null;
  // // adapter=Platform.getAdapterManager().getAdapter(this, cl);
  // // if(adapter==null){
  // adapter=super.getAdapter( cl );
  // // }
  // return adapter;
  // }
  // public IResource getResource() {
  // return gridJobFolder;
  // }
  public IStatus downloadOutputs( final IProgressMonitor monitor )
    throws GridException
  {
    IStatus status = null;
    IResource resource = getResource();
    if( resource instanceof IFolder ) {
      IFolder jobFolder = ( IFolder )resource;
      status = this.getID().downloadOutputs( jobFolder, monitor );
    } else {
      throw new GridException( new AbstractProblem( ProblemRegistry.uniqueID(),
                                                    Messages.getString( "GridJob.errCannotFindJobFolder" ) ) { //$NON-NLS-1$

        @Override
        protected String getPluginID() {
          return Activator.PLUGIN_ID;
        }
      } );
    }
    return status;
  }

  /**
   * Check if a job can be created from this folder. Currently it checks only if
   * directory contains job info file.
   * 
   * @param folder
   * @return
   */
  public static boolean canCreate( final IFolder folder ) {
    IFile infoFile = folder.getFile( GridJob.JOBINFO_FILENAME );
    return "job".equalsIgnoreCase( folder.getFileExtension() )
                                                              ? infoFile.exists()
                                                              : false;
  }

  /*
   * GridJobs adds children in constructor. Children should not be handled by
   * model.
   * 
   * @see eu.geclipse.core.model.impl.ResourceGridContainer#fetchChildren(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected boolean fetchChildren( final IProgressMonitor monitor ) {
    return true;
  }
}
