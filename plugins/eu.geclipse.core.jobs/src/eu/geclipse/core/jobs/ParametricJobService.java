/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.core.jobs;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.w3c.dom.Document;

import eu.geclipse.core.jobs.internal.Activator;
import eu.geclipse.core.jobs.internal.ParametricJobID;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobService;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.jsdl.parametric.IParametricJsdlGenerator;
import eu.geclipse.jsdl.parametric.IParametricJsdlHandler;
import eu.geclipse.jsdl.parametric.ParametricJsdlGeneratorFactory;
import eu.geclipse.jsdl.parametric.ParametricJsdlSaver;


public class ParametricJobService implements IGridJobService {
  private IGridJobService jobService;   // this service is valid only for job submission and may be null. Other methods delegates call to children of parametric job
  private GridJob job; // parametric job, to which children all calls will be delegated 
  
  /**
   * Constructor used only for job submission, when job is not known yet
   * @param jobService
   */
  public ParametricJobService( final IGridJobService jobService ) {
    this.jobService = jobService;
  }

  /**
   * Constructor used for submitted job, when parametric jobs is created and 
   * all actions are delegated to job children
   * @param jobID
   */
  public ParametricJobService( final ParametricJobID jobID ) {
    this.job = jobID.getJob();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridJobService#canSubmit(eu.geclipse.core.model.IGridJobDescription)
   */
  public boolean canSubmit( final IGridJobDescription desc ) {
    return this.jobService != null && this.jobService.canSubmit( desc );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridJobService#deleteJob(eu.geclipse.core.model.IGridJob, org.eclipse.core.runtime.IProgressMonitor)
   */
  public void deleteJob( final IGridJobID dummyJobId, final IVirtualOrganization vo, final IProgressMonitor monitor )
    throws ProblemException
  {
    SubMonitor subMonitor = SubMonitor.convert( monitor );
    List<GridJob> childrenJobs = getChildrenJobs();
    subMonitor.setWorkRemaining( childrenJobs.size() );
    
    for( GridJob gridJob : childrenJobs ) {
      gridJob.deleteJob( subMonitor.newChild( 1 ) );
    }
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridJobService#getJobStatus(eu.geclipse.core.model.IGridJobID, org.eclipse.core.runtime.IProgressMonitor)
   */
  public IGridJobStatus getJobStatus( final IGridJobID id, final IVirtualOrganization vo, final IProgressMonitor progressMonitor ) throws ProblemException {
    SubMonitor subMonitor = SubMonitor.convert( progressMonitor );
    Set<String> statusNames = new HashSet<String>();
    int statusType = IGridJobStatus.DONE;
    List<GridJob> childrenJobs = getChildrenJobs();
    subMonitor.setWorkRemaining( childrenJobs.size() );
    boolean unkOccured = false, abortedOccured = false, runningOccured = false;
    
    for( GridJob gridJob : childrenJobs ) {
      subMonitor.subTask( String.format( Messages.getString("ParametricJobService.taskNameUpdating"), gridJob.getID().getJobID() ) ); //$NON-NLS-1$
      IGridJobStatus jobStatus = gridJob.updateJobStatus( subMonitor.newChild( 1 ) );
      statusNames.add( jobStatus.getName() );
      
      GridModel.getJobManager().jobStatusChanged( gridJob );
      GridModel.getJobManager().jobStatusUpdated( gridJob );
      
      switch( jobStatus.getType() ) {
        case IGridJobStatus.DONE:
          break;
          
        case IGridJobStatus.ABORTED:        
          abortedOccured = true;
          break;
        
        case IGridJobStatus.WAITING:
        case IGridJobStatus.RUNNING:
          runningOccured = true;
          break;
          
        case IGridJobStatus.PURGED:
        case IGridJobStatus.UNKNOWN:
          unkOccured = true;
          break;
      }
    }
 
    if( unkOccured ) {
      statusType = IGridJobStatus.UNKNOWN;
    } else if( abortedOccured ) {
      statusType = IGridJobStatus.ABORTED;
    } else if( runningOccured ) {
      statusType = IGridJobStatus.RUNNING;
    } else {
      statusType = IGridJobStatus.DONE;
    }    
    
    return new GridJobStatus( statusNames.toString(), statusType );
  }

  private List<GridJob> getChildrenJobs() throws ProblemException {
    IGridElement[] children = this.job.getChildren( new NullProgressMonitor() );
    List<GridJob> childrenJobs = new ArrayList<GridJob>( children.length );
    for( IGridElement gridElement : children ) {
      if( gridElement instanceof GridJob ) {
        childrenJobs.add( ( GridJob )gridElement );
      }
    }
    return childrenJobs;
  }

  /**
   * @param jsdl
   * @param parent
   * @param jobName
   * @return created job
   * @throws ProblemException
   */
  public IGridJob createParamJobStructure( final JSDLJobDescription jsdl,
                                           final IGridContainer parent,
                                           final String jobName )
      throws ProblemException
  {
    ParametricJobID jobId = new ParametricJobID(); 
    GridJobCreator creator = new GridJobCreator();
    creator.canCreate( jsdl );
    return creator.create( parent, jobId, this, jobName );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridJobService#submitJob(eu.geclipse.core.model.IGridJobDescription, org.eclipse.core.runtime.IProgressMonitor)
   */
  /**
   * Specialized method to submit parametric job
   * @param description
   * @param monitor
   * @param parent
   * @param jobName
   * @return submitted job
   * @throws ProblemException
   */
  public IGridJobID submitJob( final IGridJobDescription description,
                               final SubMonitor monitor, final IGridContainer parent, final String jobName )
    throws ProblemException
  {
    IGridJobID jobId = null;
    SubMonitor subMonitor = SubMonitor.convert( monitor );
    
    Assert.isTrue( description instanceof JSDLJobDescription );
    
    JSDLJobDescription jsdl = ( JSDLJobDescription )description;
    
    if( isResumedSubmition( jsdl ) ) {
      jobId = resumeSubmission( jsdl, subMonitor );
    } else {
      Assert.isTrue( jsdl.isParametric() );      
      subMonitor.setWorkRemaining( 10 );
      subMonitor.subTask( Messages.getString("ParametricJobService.taskNameGeneratingJsdl") ); //$NON-NLS-1$
      IGridJob parametricGridJob = createParamJobStructure( jsdl, parent, jobName );
      IParametricJsdlGenerator generator = ParametricJsdlGeneratorFactory.getGenerator();
      IFolder generationTargetfolder = ((IFolder)parametricGridJob.getResource()).getFolder( Messages.getString("ParametricJobService.generatedJsdlFolder") ); //$NON-NLS-1$
      ParametricJsdlSaver saver = new ParametricJsdlSaver( jsdl, generationTargetfolder );
      generator.generate( jsdl, saver, subMonitor.newChild( 1 ) );
      List<JSDLJobDescription> generatedJsdls = saver.getGeneratedJsdl();    
      submitGeneratedJsdl( parametricGridJob, generatedJsdls, subMonitor.newChild( 9 ), jobName );
      cleanupSubmission( generationTargetfolder );
      jobId = new ParametricJobID();
    }

    return jobId;
  } 

  private IGridJobID resumeSubmission( final JSDLJobDescription jsdl, final SubMonitor monitor ) throws ProblemException {    
    GridJob parentJob = findParentParamJob( jsdl );
    Assert.isNotNull( parentJob );
    IFolder targetFolder = ( IFolder )parentJob.getResource();
    List<JSDLJobDescription> jsdlList = new ArrayList<JSDLJobDescription>( 1 );
    jsdlList.add( jsdl );
    submitGeneratedJsdl( parentJob, jsdlList, monitor, parentJob.getJobName() );
    cleanupSubmission( ( IFolder )jsdl.getResource().getParent() );
        
    return parentJob.getID();
  }

  private boolean isResumedSubmition( final JSDLJobDescription jsdl ) {
    return findParentParamJob( jsdl ) != null;
  }

  private GridJob findParentParamJob( final JSDLJobDescription jsdl ) {
    GridJob paramJob = null;    
    
    if( !jsdl.isParametric() ) {
      IGridContainer parent = jsdl.getParent();
      
      while( parent != null ) {
        if( parent instanceof GridJob ) {
          GridJob parentJob = ( GridJob )parent;
          IGridJobDescription parentDescription = parentJob.getJobDescription();
          if( parentDescription instanceof JSDLJobDescription
              && (( JSDLJobDescription )parentDescription).isParametric() ) {
            paramJob = parentJob;            
          }
          break;
        }
        parent = parent.getParent();
      }
    }

    
    return paramJob;
  }

  private void cleanupSubmission( final IFolder generationTargetfolder ) throws ProblemException {
    try {
      if( generationTargetfolder.members().length == 0 ) {
        generationTargetfolder.delete( true, null );
      }
    } catch ( CoreException exception ) {
      throw new ProblemException( "eu.geclipse.core.jobs.problem.cleanupSubmissionFailed", exception, Activator.PLUGIN_ID ); //$NON-NLS-1$
    }    
  }

  private List<IGridJobID> submitGeneratedJsdl( final IGridJob parametricJob, final List<JSDLJobDescription> generatedJsdls,
                                    final SubMonitor monitor, final String jobName ) throws ProblemException
  {
    List<IGridJobID> submittedJobs = new ArrayList<IGridJobID>( generatedJsdls.size() );
    GridJobCreator jobCreator = new GridJobCreator();
    monitor.setWorkRemaining( generatedJsdls.size() );
    
    for ( JSDLJobDescription jobDescription : generatedJsdls ) {
      String subjobName = getSubJobName( jobDescription, jobName );      
      
      testCancelled( monitor );
      
      monitor.setTaskName( String.format( Messages.getString("ParametricJobService.taskSubmitting"), jobDescription.getName() ) ); //$NON-NLS-1$
      
      IGridJobID jobID = this.jobService.submitJob( jobDescription, monitor.newChild( 1 ) );
      submittedJobs.add( jobID );
      
      testCancelled( monitor );
      
      jobCreator.canCreate( jobDescription );
      jobCreator.create( parametricJob, jobID, this.jobService, subjobName );
      
      try {
        jobDescription.getResource().delete( true, monitor.newChild( 0 ) );
      } catch( CoreException exception ) {
        // TODO mariusz Auto-generated catch block
        exception.printStackTrace();
      }      
    }
    
    return submittedJobs;
  }

  private String getSubJobName( final JSDLJobDescription jobDescription,
                                final String jobName )
  {
    String subJobName = jobName;
    String jsdlName = new Path( jobDescription.getName() ).removeFileExtension().toString();    
    int suffixIndex = jsdlName.indexOf( '[' );        
    
    if( suffixIndex > -1 ) {
      subJobName = jobName + jsdlName.substring( suffixIndex );
    }
    
    return subJobName;
  }

  private void testCancelled( final SubMonitor monitor ) {
    if( monitor.isCanceled() ) {
      throw new OperationCanceledException();
    }    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridResource#getHostName()
   */
  public String getHostName() {
    return this.jobService != null ? this.jobService.getHostName() : Messages.getString("ParametricJobService.unknownHostName"); //$NON-NLS-1$
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridResource#getURI()
   */
  public URI getURI() {
    return this.jobService != null ? this.jobService.getURI() : null;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#dispose()
   */
  public void dispose() {
    if( this.jobService != null ) {
      this.jobService.dispose();
    }
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getFileStore()
   */
  public IFileStore getFileStore() {
    return this.jobService.getFileStore();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getName()
   */
  public String getName() {
    return this.jobService.getName();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getParent()
   */
  public IGridContainer getParent() {
    return this.jobService.getParent();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getPath()
   */
  public IPath getPath() {
    return this.jobService.getPath();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getProject()
   */
  public IGridProject getProject() {    
    return this.jobService != null ? this.jobService.getProject() : null;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getResource()
   */
  public IResource getResource() {
    return this.jobService.getResource();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isHidden()
   */
  public boolean isHidden() {
    return this.jobService.isHidden();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isLocal()
   */
  public boolean isLocal() {
    return this.jobService.isLocal();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isVirtual()
   */
  public boolean isVirtual() {
    return this.jobService.isVirtual();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  public Object getAdapter( final Class adapter ) {
    return this.jobService != null ? this.jobService.getAdapter( adapter ) : null;
  }

  public IGridJobID submitJob( final IGridJobDescription description,
                               final IProgressMonitor monitor )
    throws ProblemException
  {
    // TODO mariusz operation not supported - add exception
    return null;
  }

  public IGridJobID submitJob( final IGridJobDescription description,
                               final IVirtualOrganization vo,
                               final IProgressMonitor monitor )
    throws ProblemException
  {
    // TODO mariusz operation not supported - add exception
    return null;
  }

  public Map<String, URI> getInputFiles( final IGridJobID jobId,
                                         final IGridJobDescription jobDescription,
                                         final IVirtualOrganization vo )
    throws ProblemException
  {
    // TODO mariusz Auto-generated method stub
    return null;
  }

  public Map<String, URI> getOutputFiles( final IGridJobID jobId,
                                          final IGridJobDescription jobDescription,
                                          final IVirtualOrganization vo )
    throws ProblemException
  {
    // TODO mariusz Auto-generated method stub
    return null;
  }
  
  // TODO mariusz remove it:
  private void previewParamJsdl( final JSDLJobDescription parametricJsdl ) {
    
    final TreeMap< String,String[] > paramValues = new TreeMap< String, String[] >();
    
    IParametricJsdlHandler handler = new IParametricJsdlHandler() {
      int maxIterations;
      int iteration = 0;

      public void generationFinished() throws ProblemException {
        // TODO mariusz Auto-generated method stub
      }

      public void newJsdlGenerated( final Document generatedJsdl,
                                    final List<Integer> iterationsStack,
                                    final IProgressMonitor monitor )
        throws ProblemException
      {
        iteration++;
      }

      public void paramSubstituted( final String paramName,
                                    final String newValue,
                                    final IProgressMonitor newParam )
        throws ProblemException
      {
        String[] values = paramValues.get( paramName );
        
        if ( values == null ) {
          values = new String[ maxIterations ];
          paramValues.put( paramName, values );
        }

        values[ iteration ] = newValue;
      }

      public void generationStarted( final int generatedJsdl )
        throws ProblemException
      {
        maxIterations = generatedJsdl;
      }
    };

    IParametricJsdlGenerator generator = ParametricJsdlGeneratorFactory.getGenerator();
    generator.generate( parametricJsdl, handler, null );      

    System.out.println("--------------------------------------------");//TODO mariusz

    if ( ! paramValues.isEmpty() ) {
      int iterations = paramValues.get( paramValues.firstKey() ).length;
      for ( int iteration = 0; iteration < iterations; iteration++ ) {
        for ( String paramName : paramValues.keySet() ) {
          String value = "";
          String[] values = paramValues.get( paramName );

          if ( iteration < values.length 
               && values[ iteration ] != null ) {
            value = values[ iteration ];
          }
          System.out.print( String.format( "%20s", value ) ); //TODO mariusz
        }
        System.out.println( "\n" ); //TODO mariusz
      }
    }
  }
}
