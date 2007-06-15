/******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     Pawel Wolniewicz - PSNC
 *****************************************************************************/
package eu.geclipse.core.jobs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.GridException;
import eu.geclipse.core.GridJobStatusServiceFactoryManager;
import eu.geclipse.core.jobs.internal.Activator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.GridModelProblems;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.core.model.IGridJobStatusService;
import eu.geclipse.core.model.IGridJobStatusServiceFactory;
import eu.geclipse.core.model.impl.ResourceGridContainer;
import eu.geclipse.jsdl.JSDLJobDescription;

public class GridJob extends ResourceGridContainer implements IGridJob {

  // final static public String XMLNODE_JOBDESCRIPTION = "JobDescription";
  final static public String JOBID_FILENAME = ".jobID";
  final static public String JOBINFO_FILENAME = ".jobInfo";
  final static public String JOBSTATUS_FILENAME = ".jobStatus";
  final static public String JOBINFO_XMLNODENAME = "JobInfo";
  final static public String JOBINFO_JOBDESCRIPTION_XMLNODENAME = "JobDescriptionFileName";
  
  private GridJobID jobID = null;
  private IGridJobDescription jobDescription = null;
  private GridJobStatus jobStatus = null;
  private IFile jobDescriptionFile;
  private IFile jobIdFile;
  private IFile jobStatusFile;
  private IFile jobInfoFile;
  private IGridJobStatusService statusService;

  public GridJob( final IFolder jobFolder ) {
    super( jobFolder );
    jobStatusFile = jobFolder.getFile( JOBSTATUS_FILENAME );
    jobIdFile = jobFolder.getFile( JOBID_FILENAME );
    jobInfoFile = jobFolder.getFile( JOBINFO_FILENAME );
    readJobInfo( jobFolder );
//    gridJobFolder = new GridJobFolder( jobFolder, this );
    readJobID();
    IGridJobStatusServiceFactory factory = GridJobStatusServiceFactoryManager.getFactory( jobID.getClass() );
    if( factory != null ) {
      statusService = factory.getGridJobStatusService( jobID );
    }
  }

  public void create( IGridContainer parent, IFolder jobFolder, GridJobID id, IGridJobDescription description ) throws GridModelException {
//    super(jobFolder);
    jobStatusFile = jobFolder.getFile( JOBSTATUS_FILENAME );
    jobIdFile = jobFolder.getFile( JOBID_FILENAME );
    jobInfoFile = jobFolder.getFile( JOBINFO_FILENAME );
    
    jobID=id;
    jobDescription=description;
    jobStatus = new GridJobStatus("Submitted from g-Eclipse", IGridJobStatus.SUBMITTED);


    writeJobDescription(description, jobFolder);
    writeJobID( id, jobFolder );
    writeJobStatus( jobStatusFile );
    writeJobInfo( description, jobFolder);
    IGridElementCreator creator = findCreator( jobDescriptionFile );
    if ( creator != null ) {
      create( creator );
    }
    IGridJobStatusServiceFactory factory = GridJobStatusServiceFactoryManager.getFactory( id.getClass() );
    if( factory != null ) {
      statusService = factory.getGridJobStatusService( id );
    }

  }

  private void readJobInfo( IFolder jobFolder ) {
    Document document = createXmlDocument( jobInfoFile );
    if( document != null ) {
      Element rootElement = document.getDocumentElement();
      if( !GridJob.JOBINFO_XMLNODENAME.equals( rootElement.getNodeName() ) ) {
        // Wrong format of jobInfo file
      }
      else{
      Node node = rootElement.getFirstChild();
      if( !GridJob.JOBINFO_JOBDESCRIPTION_XMLNODENAME.equals( node.getNodeName() ) )
      {
        // TODO pawelw change it to something more reliable
      }
      String filename = node.getTextContent();
      jobDescriptionFile = jobFolder.getFile( filename );
      }
    }
  }

  public void cancel() {
    // TODO pawel
  }

  public IGridJobID getID() {
    if( jobID == null ) {
      if( jobIdFile.exists() ) {
        readJobID();
      }
    }
    return jobID;
  }

  private void readJobID() {
    Document document = createXmlDocument( jobIdFile );
    if( document == null ) {
      jobID= new GridJobID();
    } else {
      Element rootElement = document.getDocumentElement();
      if( !GridJobID.XML_ROOT.equals( rootElement.getNodeName() ) ) {
        // TODO pawelw  - file is not correct
      }
      String className = rootElement.getAttribute( GridJobID.XML_ATTRIBUTE_CLASS );
      if(className!=null && !className.equals("")){
        ExtensionManager manager = new ExtensionManager();
        List< IConfigurationElement > list = manager.getConfigurationElements( "eu.geclipse.grid.jobID", "JobID" );
        for( IConfigurationElement  element : list){
          String attr = element.getAttribute( "class" );
          if (className.equals( attr )){
            try {
              jobID = (GridJobID) element.createExecutableExtension( "class" );
              jobID.setXMLNode( rootElement );
            } catch( CoreException e ) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
        }
      }
      
      if(jobID==null)
        jobID = new GridJobID( rootElement );
    }
  }

  public IGridJobStatus getJobStatus() {
    if( jobStatus == null ) {
      if( jobStatusFile.exists() ) {
        readJobStatus();
      }
    }
    return this.jobStatus != null ? this.jobStatus : GridJobStatus.UNKNOWN_STATUS;
  }

  private void readJobStatus() {
    Document document = createXmlDocument( jobStatusFile );
    if( document == null ) {
      // TODO pawelw - log it?
    } else {
      Element rootElement = document.getDocumentElement();
      if( !GridJobStatus.XML_ROOT.equals( rootElement.getNodeName() ) ) {
        // TODO pawelw
      }
      String className = rootElement.getAttribute( GridJobStatus.XML_ATTRIBUTE_CLASS );
      // TODO pawelw - find apropriate class
      jobStatus = new GridJobStatus( rootElement );
    }
  }

  public IGridJobStatus updateJobStatus() {
    IGridJobStatus newJobStatus=null;
    try {
      if(statusService!=null && jobID.getJobID()!=GridJobID.UNKNOWN){
        newJobStatus = statusService.getJobStatus( jobID );
      }
      if(newJobStatus!=null && newJobStatus instanceof GridJobStatus){
        jobStatus=(GridJobStatus)newJobStatus;
        writeJobStatus( jobStatusFile );
      }
    } catch( GridException e ) {
      Activator.logException( e );
    }
    jobStatus = new GridJobStatus( jobID );
    return this.jobStatus;
  }

  public boolean isLazy() {
    return false;
  }

  public boolean isLocal() {
    return true;
  }

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
    if( jobDescription == null ) {
      if( jobDescriptionFile != null ) {
        IGridElement element = this.findChild( jobDescriptionFile.getName() );
        if( element instanceof IGridJobDescription )
          jobDescription = ( IGridJobDescription )element;
        else if( jobDescriptionFile.exists() ) {
          readJobDescription();
        }
      }
    }
    return jobDescription;
  }

  private void readJobDescription() {
    this.jobDescription = new JSDLJobDescription( jobDescriptionFile );
  }

  private Document createXmlDocument( final IFile xmlFile ) {
    Document xmlDocument = null;
    try {
      DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance()
        .newDocumentBuilder();
      InputStream inputStream = xmlFile.getContents();
      xmlDocument = documentBuilder.parse( inputStream );
      inputStream.close();
    } catch( CoreException e ) {
      logXmlError( Status.WARNING,
                   Messages.getString( "GliteJob.jobfile_contents" ),
                   e );
    } catch( SAXException e ) {
      logXmlError( Status.WARNING,
                   Messages.getString( "GliteJob.jobfile_parse_xml" ),
                   e );
    } catch( IOException e ) {
      logXmlError( Status.WARNING,
                   Messages.getString( "GliteJob.jobfile_contents" ),
                   e );
    } catch( ParserConfigurationException e ) {
      logXmlError( Status.WARNING,
                   Messages.getString( "GliteJob.jobfile_parse_xml" ),
                   e );
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
                                                                               ? "null"
                                                                               : errorString );
    if( resource != null && resource.getFullPath() != null ) {
      messageStringBuilder.append( " File: " );
      messageStringBuilder.append( resource.getFullPath() );
    }
    Activator.logStatus( new Status( severity,
                                     Activator.PLUGIN_ID,
                                     IStatus.OK,
                                     messageStringBuilder.toString(), //$NON-NLS-1$
                                     exception ) );
  }

  // Commented this out (temporarily?) since this does not work with
  // IGridContainer#findChild(String name) and IGridRoot#findElement(IPath path)
  /*
  public String getName() {
    // return "Job ("+super.getName()+")";
    String name=super.getName();
    IGridJobDescription desc = getJobDescription();
    if(desc!=null){
      name = name + "("+desc.getName()+")";
    }
    return name;
  }
*/
  public boolean canContain( final IGridElement element ) {
    return (element instanceof IGridJobDescription) ;
  }

  
  /**
   * Writes job status XML to job folder
   * @param description - Job description
   * @param jobFolder - Folder to which file should be written
   * @throws GridModelException
   */
  private void writeJobDescription(IGridJobDescription description, IFolder jobFolder) throws GridModelException{
    //create job description file
    IFile sourceDescriptionFile = ( IFile )description.getResource();
    jobDescriptionFile=jobFolder.getFile( sourceDescriptionFile.getName() );
    try{
    InputStream is=sourceDescriptionFile.getContents( true );
    jobDescriptionFile.create( is, true, null );
    is.close();
    }
    catch(CoreException cExc){
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    cExc,
                                    "Problem while creating job description file" );
    }
    catch(IOException ioExc){
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    ioExc,
                                    "Problem while creating job description file" );
    }
  }

    /**
     * Writes job status XML to job folder
     * @param id - JobID
     * @param jobFolder - Folder to which file should be written
     * @throws GridModelException
     */
    private void writeJobID( final IGridJobID id, IFolder jobFolder ) throws GridModelException {
      IFile file;
      //create jobID file
      file = jobFolder.getFile( GridJob.JOBID_FILENAME );
      if( !( id instanceof GridJobID ) ) {
        throw new GridModelException( GridModelProblems.ELEMENT_SAVE_FAILED,
                                      "GridJobID was expected instead of "
                                          + id.getClass() );
      }
      String xml = ( ( GridJobID )id ).getXML();
      Activator.consoleLog( xml );
      try{
      byte[] byteArray = xml.getBytes( "ISO-8859-1" ); // choose a charset
      ByteArrayInputStream baos = new ByteArrayInputStream( byteArray );
      file.create( baos, true, null );
    } catch( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                  cExc,
                                  "Problem while creating job ID file "+file.getName() );
    } catch( UnsupportedEncodingException e ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    e,
                                    "Problem while creating job ID file " );
    }
    }

    /**
     * Writes job status XML to job folder
     * @param jobFolder - Folder to which file should be written
     * @throws GridModelException 
     */
    private void writeJobStatus( IFile jobStatusFile ) throws GridModelException {
      String xml;
      byte[] byteArray;
      ByteArrayInputStream baos;
      // create jobStatus file
      xml = jobStatus.getXML();
      
      //TODO - pawelw - remove it 
      Activator.consoleLog( xml );
      try{
      byteArray = xml.getBytes( "ISO-8859-1" ); // choose a charset
      baos = new ByteArrayInputStream( byteArray );
      jobStatusFile.setContents( baos, true, true, null );
    } catch( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                  cExc,
                                  "Problem while writing job status file "+jobStatusFile.getName() );
    } catch( UnsupportedEncodingException e ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    e,
                                    "Problem while creating job status file " );
    }
    }

    /**
     * Writes XML with job information to .jobinfo file
     * @param description - JobDescription 
     * @param jobFolder - Folder to which file should be written
     * @throws GridModelException 
     */
    private void writeJobInfo( IGridJobDescription description, IFolder jobFolder) throws GridModelException {
      IFile file;
      String xml;
      byte[] byteArray;
      ByteArrayInputStream baos;
      //create jobInfo file
      file=jobFolder.getFile( GridJob.JOBINFO_FILENAME );
      IFile descFile = ( IFile )description.getResource();

      //TODO pawelw - move it to GridJob
      xml = "<JobInfo><JobDescriptionFileName class=\""
            + description.getClass().getName()
            + "\">"
            + descFile.getName()
            + "</JobDescriptionFileName></JobInfo>";
      Activator.consoleLog( xml );
      try{
      byteArray = xml.getBytes( "ISO-8859-1" ); // choose a charset
      baos = new ByteArrayInputStream( byteArray );
      file.create( baos, true, null );
    } catch( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                  cExc,
                                  "Problem while creating job information file "+file.getName() );
    } catch( UnsupportedEncodingException e ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED,
                                    e,
                                    "Problem while creating job information file " );
    }
    }
  
  
//  public Object getAdapter(Class cl){
//    Object adapter=null;
////    adapter=Platform.getAdapterManager().getAdapter(this, cl);
////    if(adapter==null){
//      adapter=super.getAdapter( cl );
////    }
//   return adapter;
//  }
  
//  public IResource getResource() {
//    return gridJobFolder;
//  }
}
