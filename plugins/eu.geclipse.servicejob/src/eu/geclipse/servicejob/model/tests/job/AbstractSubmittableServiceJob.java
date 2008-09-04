/*****************************************************************************
 * Copyright (c) 2008, g-Eclipse Consortium All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Szymon Mueller - PSNC - Initial API and implementation
 *****************************************************************************/
package eu.geclipse.servicejob.model.tests.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobService;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.core.model.IServiceJob;
import eu.geclipse.core.model.IServiceJobResult;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.servicejob.Activator;
import eu.geclipse.servicejob.model.AbstractServiceJob;
import eu.geclipse.servicejob.parsers.GTDLJobParser;
import eu.geclipse.servicejob.parsers.GTDLJobWriter;

/**
 * Abstract class implementing basic {@link IServiceJob} methods for job based
 * tests. The implementation follows this specified workflow:
 * <ul>
 * <li>1. For each target resource single JSDL is created.
 * <li>2. Each JSDL is submitted using submission service.
 * <li>3. Jobs are monitored for their termination.
 * <li>4. When all of the submitted jobs are finished some additional actions
 * are performed by main test.
 * </ul>
 * If the test varies from the above workflow, some of the methods should be
 * overwritten.
 */
public abstract class AbstractSubmittableServiceJob extends AbstractServiceJob {

  /**
   * File name for storing info of the test
   */
  public static final String TEST_INFO_FILENAME = ".testinfo"; //$NON-NLS-1$
  /**
   * XML charset
   */
  public static final String XML_CHARSET = "ISO-8859-1"; //$NON-NLS-1$
  /**
   * Extension of the file storing info of the test results for the specified
   * nodes
   */
  public static final String TEST_STATUS_FILENAME = ".teststatus"; //$NON-NLS-1$
  protected Map<String, String> jobIDResourceNameMap = new HashMap<String, String>();
  protected ServiceJobUpdater updater;

  public boolean isLocal() {
    return true;
  }

  public void init() {
    this.name = getResource().getName().substring( 0,
                                                   getResource().getName()
                                                     .lastIndexOf( "." ) ); //$NON-NLS-1$
    IFile file = ( IFile )getResource();
    try {
      List<SubmittableServiceJobResult> tempRes = GTDLJobParser.getTestResults( file.getRawLocation()
        .toFile() );
      this.results = new ArrayList<IServiceJobResult>();
      for( SubmittableServiceJobResult result : tempRes ) {
        this.results.add( result );
        result.setJobID( createJobID( result ) );
      }
      List<IGridJobID> jobIDsToRun = new ArrayList<IGridJobID>();
      for( SubmittableServiceJobResult testResult : tempRes ) {
        if( testResult.getResultEnum()
          .equals( Messages.getString( "AbstractJobTest.running_status" ) ) //$NON-NLS-1$
            || testResult.getResultEnum()
              .equals( Messages.getString( "AbstractJobTest.pending_status" ) ) ) { //$NON-NLS-1$
          jobIDsToRun.add( testResult.getJobID() );
        }
      }
      if( jobIDsToRun.size() > 0 ) {
        ServiceJobUpdater testUpdater = getUpdater();
        testUpdater.addSubTests( jobIDsToRun );
        testUpdater.schedule( 30000 );
      }
    } catch( ParserConfigurationException e ) {
      // TODO szymon proper error handling
      Activator.logException( e );
    } catch( SAXException e ) {
      // TODO szymon proper error handling
      Activator.logException( e );
    } catch( IOException e ) {
      // TODO szymon proper error handling
      Activator.logException( e );
    } catch( DOMException e ) {
      // TODO szymon proper error handling
      Activator.logException( e );
    } catch( ParseException e ) {
      // TODO szymon proper error handling
      Activator.logException( e );
    }
    initData();
  }

  /**
   * Implementations of this method should create and return proper,
   * middleware-specific job id. This usually will require some additional
   * informations (e.g. job ID). Clients should use
   * <code>JobTestResult.getResultRawData()</code> and parse needed information.
   * 
   * @param jobTestResult job result for which job ID should be created
   * @return IGridJobID representing the test. If no middleware specific id can
   *         be returned this should return generic GridJobID class.
   */
  public abstract IGridJobID createJobID( final SubmittableServiceJobResult jobTestResult );

  /**
   * Perform additional, test specific tasks at the start of the test. If
   * needed, <code>inputString</code> representing input part of XML may be
   * parsed for additional informations about the test.
   */
  public abstract void initData();

  /**
   * Getter of this service job updater.
   * 
   * @return job test updater for this service job
   */
  public ServiceJobUpdater getUpdater() {
    if( this.updater == null ) {
      this.updater = new ServiceJobUpdater( getName(), this );
    }
    return this.updater;
  }

  public void run() {
    Job runJob = new Job( Messages.getString( "AbstractJobTest.submitting" ) //$NON-NLS-1$
                          + " " //$NON-NLS-1$
                          + getName()
                          + " " //$NON-NLS-1$
                          + Messages.getString( "AbstractJobTest.job" ) ) { //$NON-NLS-1$

      @Override
      protected IStatus run( final IProgressMonitor uMonitor ) {
        IStatus status = Status.OK_STATUS;
        IProgressMonitor monitor = uMonitor != null
                                                   ? uMonitor
                                                   : new NullProgressMonitor();
        List<IGridJobID> jobIDsToAdd = new ArrayList<IGridJobID>();
        for( String resourceName : getTestedResourcesNames() ) {
          IGridJobDescription description = getJSDLForSubmission( resourceName );
          IGridJobService jobService = getSubmissionService();
          if( description != null && jobService != null ) {
            try {
              IGridJobID jobID = jobService.submitJob( description, monitor );
              AbstractSubmittableServiceJob.this.jobIDResourceNameMap.put( jobID.getJobID(),
                                                                           resourceName );
              jobIDsToAdd.add( jobID );
              createNewResult( jobID, resourceName, Calendar.getInstance()
                .getTime() );
            } catch( ProblemException exc ) {
              status = new Status( IStatus.ERROR,
                                   Activator.PLUGIN_ID,
                                   Messages.getString( "AbstractJobTest.Error_submitting_job" ), //$NON-NLS-1$
                                   exc );
            }
          }
        }
        if( jobIDsToAdd.size() > 0 ) {
          ServiceJobUpdater testUpdater = getUpdater();
          testUpdater.addSubTests( jobIDsToAdd );
          testUpdater.schedule( 30000 );
        }
        return status;
      }
    };
    runJob.schedule();
  }

  // /**
  // * Method returns job submission service for the resource with the specified
  // * name. If no submission service is returned, job's can't be submitted.
  // *
  // * @param resourceName
  // * @return IGridJobService to which all JSDL testing specified resource name
  // * are submitted
  // */
  // public abstract IGridJobService getSubmissionService( final String
  // resourceName );
  /**
   * Gets the JSDL for submission to test specified resource name.
   * 
   * @param resourceName
   * @return JSDLJobDescription if the JSDL is ready for submission or null if
   *         it is not/
   */
  public abstract IGridJobDescription getJSDLForSubmission( final String resourceName );

  /**
   * Sets result of the running grid job with jobID, storing the result in the
   * status file of this job. If result for the job with jobID exists the result
   * is overwritten by the parser (the newly fetched status of the job is
   * written). If there is no job result for the given jobID yet stored, then
   * new XML node is added to the status XML for that jobID.
   * <p>
   * Developers can overwrite this method if the storage of the status results
   * is resolved differently.
   * </p>
   * 
   * @param jobID id of the running job
   * @param lastRefreshDate date of the last refresh of job status
   * @param status fetched job status
   * @param besStatus BES status of the job
   */
  public void setJobResult( final IGridJobID jobID,
                            final Date lastRefreshDate,
                            final String status,
                            final String besStatus )
  {
    SubmittableServiceJobResult jobTestResult = null;
    for( IServiceJobResult tempResult1 : this.results ) {
      SubmittableServiceJobResult tempResult = ( SubmittableServiceJobResult )tempResult1;
      if( tempResult.getJobIDString().equals( jobID.getJobID() ) ) {
        jobTestResult = tempResult;
      }
    }
    if( jobTestResult != null ) {
      jobTestResult.updateStatus( lastRefreshDate, status, besStatus );
      List<SubmittableServiceJobResult> ress = new ArrayList<SubmittableServiceJobResult>();
      ress.add( jobTestResult );
      try {
        GTDLJobWriter.addTestResults( getResource().getRawLocation().toFile(),
                                      ress );
        this.lastResult = jobTestResult;
        if( !getResource().isSynchronized( IResource.DEPTH_ZERO ) ) {
          try {
            getResource().refreshLocal( IResource.DEPTH_ZERO, null );
          } catch( CoreException e ) {
            // TODO
            Activator.logException( e );
          }
        }
      } catch( ParserConfigurationException e ) {
        Activator.logException( e );
      } catch( SAXException e ) {
        Activator.logException( e );
      } catch( IOException e ) {
        Activator.logException( e );
      } catch( TransformerFactoryConfigurationError e ) {
        Activator.logException( e );
      } catch( TransformerException e ) {
        Activator.logException( e );
      }
    }
  }

  /**
   * Create new result for this test.
   * 
   * @param jobID ID of the job for which test result should be created
   * @param resourceName name of the tested resource by the job
   * @param submissionDate date of the job submission
   */
  public void createNewResult( final IGridJobID jobID,
                               final String resourceName,
                               final Date submissionDate )
  {
    for( String singleTestName : getSingleTestsNames() ) {
      SubmittableServiceJobResult testResult = new SubmittableServiceJobResult( submissionDate,
                                                                                resourceName,
                                                                                singleTestName,
                                                                                getRawDataInput( jobID,
                                                                                                 resourceName ),
                                                                                Messages.getString( "AbstractJobTest.pending_status" ), //$NON-NLS-1$
                                                                                getResultType( singleTestName ),
                                                                                Messages.getString( "AbstractJobTest.pending_status" ) ); //$NON-NLS-1$
      testResult.setJobID( jobID );
      this.results.add( testResult );
      List<SubmittableServiceJobResult> ress = new ArrayList<SubmittableServiceJobResult>();
      ress.add( testResult );
      try {
        GTDLJobWriter.addTestResults( getResource().getRawLocation().toFile(),
                                      ress );
        if( !getResource().isSynchronized( IResource.DEPTH_ZERO ) ) {
          try {
            getResource().refreshLocal( IResource.DEPTH_ZERO, null );
          } catch( CoreException e ) {
            // TODO
            Activator.logException( e );
          }
        }
      } catch( ParserConfigurationException e ) {
        // TODO szymon proper error handling
        Activator.logException( e );
      } catch( SAXException e ) {
        // TODO szymon proper error handling
        Activator.logException( e );
      } catch( IOException e ) {
        // TODO szymon proper error handling
        Activator.logException( e );
      } catch( TransformerFactoryConfigurationError e ) {
        // TODO szymon proper error handling
        Activator.logException( e );
      } catch( TransformerException e ) {
        // TODO szymon proper error handling
        Activator.logException( e );
      }
    }
  }

  /**
   * This method should return type of the result data returned by single test.
   * 
   * @param singleTestName name of the single test
   * @return String representing type of the result data (extension).
   */
  public abstract String getResultType( final String singleTestName );

  /**
   * This method should return starting string XML structure of raw data node of
   * the given <code>jobID</code> associated with the <code>resourceName</code>.
   * 
   * @param jobID ID of the job for which the node should be created
   * @param resourceName Name of the tested resource
   * @return String representing starting XML of raw data for the test result.
   */
  public abstract String getRawDataInput( final IGridJobID jobID,
                                          final String resourceName );

  /**
   * This method is invoked by job test updater when one of the running jobs is
   * finished. Implementation should compute result of the test or sub-test(s)
   * accordingly to the outcome of job with <code>jobID</code>.
   * 
   * @param jobID ID of job which finished running
   * @param jobStatus status of the finished job
   */
  public abstract void computeJobResult( final IGridJobID jobID,
                                         final IGridJobStatus jobStatus );

  /**
   * This method is invoked by job test updater after last job of this test
   * finished and its result was computed by
   * {@link #computeJobResult(IGridJobID, IGridJobStatus)}.
   */
  public abstract void computeTestResult();

  protected InputStream getContentsStream( final String path,
                                           final String bundle )
  {
    InputStream resultStream = null;
    Path resultPath = new Path( path );
    URL fileURL = FileLocator.find( Platform.getBundle( bundle ),
                                    resultPath,
                                    null );
    try {
      fileURL = FileLocator.toFileURL( fileURL );
    } catch( IOException ioException ) {
      // TODO
      Activator.logException( ioException );
    } catch( NullPointerException nullExc ) {
      // TODO
      Activator.logException( nullExc );
    }
    String temp = fileURL.toString();
    temp = temp.substring( temp.indexOf( fileURL.getProtocol() )
                           + fileURL.getProtocol().length()
                           + 1, temp.length() );
    resultPath = new Path( temp );
    File file = resultPath.toFile();
    try {
      resultStream = new FileInputStream( file );
    } catch( FileNotFoundException e ) {
      // TODO
      Activator.logException( e );
    }
    return resultStream;
  }

  /**
   * Getter of the tested project's VO.
   * 
   * @return This test's VO or
   */
  public IVirtualOrganization getSelectedJobsVO() {
    return getProject() != null
                               ? getProject().getVO()
                               : null;
  }

  /**
   * Workaround for following problem in g-eclipse: store.openInputStream()
   * always return null if IFileStore was obtained directly using URI Instead of
   * directly obtaining IFileStore, we should list parent directory and find
   * there our file
   * 
   * @param fileSystem
   * @param fileUri
   * @return
   * @throws GridException
   */
  protected IFileStore getFileViaDirectory( final IFileSystem fileSystem,
                                            final URI fileUri )
    throws ProblemException
  {
    try {
      IPath filePath = new Path( fileUri.getPath() );
      IPath folderPath = filePath.removeLastSegments( 1 )
        .addTrailingSeparator();
      URI dirUri = new URI( fileUri.getScheme(),
                            fileUri.getUserInfo(),
                            fileUri.getHost(),
                            fileUri.getPort(),
                            folderPath.toString(),
                            fileUri.getQuery(),
                            fileUri.getFragment() );
      IFileStore dirStore = fileSystem.getStore( dirUri );
      dirStore.childNames( 0, null );
      return dirStore.getChild( filePath.lastSegment() );
    } catch( URISyntaxException exception ) {
      throw new ProblemException( ICoreProblems.NET_MALFORMED_URL,
                                  exception,
                                  Activator.PLUGIN_ID );
    } catch( CoreException exception ) {
      throw new ProblemException( ICoreProblems.IO_UNSPECIFIED_PROBLEM,
                                  exception,
                                  Activator.PLUGIN_ID );
    }
  }

  @Override
  public int getColumnWidth( final String singleTestName ) {
    return 200;
  }

  @Override
  public boolean needsSubmissionWizard() {
    return true;
  }
}