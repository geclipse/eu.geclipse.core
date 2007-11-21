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
package eu.geclipse.ui.wizards.jobsubmission;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobCreator;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobSubmissionService;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.ui.dialogs.NewProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.wizards.wizardselection.IInitalizableWizard;

/**
 * Base class for submission wizard
 */
public abstract class JobSubmissionWizardBase extends Wizard
  implements IInitalizableWizard, IExecutableExtension
{

  protected IGridJobCreator creator;
  protected List<IGridJobDescription> jobDescriptions;
  
  protected JobSubmissionWizardBase() {
    setNeedsProgressMonitor( true );
  }

  @Override
  public boolean performFinish()
  {
    boolean result = true;
    if( this.creator != null ) {
      final IGridJobSubmissionService service = getSubmissionService();
      final IWizardContainer container = getContainer();
      try {
        container.run( true, true, new IRunnableWithProgress() {
          
          protected void testCanceled( final IProgressMonitor monitor ) {
            if ( monitor.isCanceled() ) {
              throw new OperationCanceledException();
            }
          }

          public void run( final IProgressMonitor monitor )
            throws InvocationTargetException, InterruptedException
          {
            SubMonitor betterMonitor = SubMonitor.convert( monitor, JobSubmissionWizardBase.this.jobDescriptions.size() );
            
            for( IGridJobDescription description : JobSubmissionWizardBase.this.jobDescriptions )
            {              
              testCanceled( betterMonitor );              

              if( JobSubmissionWizardBase.this.creator.canCreate( description ) )
              {                
                betterMonitor.setTaskName( String.format( Messages.getString("JobSubmissionWizardBase.taskSubmittingJob"), description.getName() ) ); //$NON-NLS-1$
                
                try {
                  IGridContainer parent = buildPath( description );
                  testCanceled( betterMonitor );
                  
                  IGridJobID jobId = null;
                  if( service != null ) {                    
                    jobId = service.submitJob( description, betterMonitor.newChild( 1, SubMonitor.SUPPRESS_SETTASKNAME | SubMonitor.SUPPRESS_BEGINTASK ) );     
                    testCanceled( betterMonitor );                    
                  } else {
                    NewProblemDialog.openProblem( getShell(),
                                                  Messages.getString("JobSubmissionWizardBase.errSubmissionFailed"), //$NON-NLS-1$
                                                  Messages.getString("JobSubmissionWizardBase.errUnknownSubmissionService"), //$NON-NLS-1$
                                                  null );
                  }
                  // create job
                  JobSubmissionWizardBase.this.creator.create( parent, jobId );
                } catch( GridModelException gmExc ) {
                  NewProblemDialog.openProblem( getShell(),
                                                Messages.getString("JobSubmissionWizardBase.errSubmissionFailed"), //$NON-NLS-1$
                                                null,
                                                gmExc );
                } catch( CoreException cExc ) {
                    NewProblemDialog.openProblem( getShell(),
                                                  Messages.getString("JobSubmissionWizardBase.errSubmissionFailed"), //$NON-NLS-1$
                                                  null,
                                                  cExc );
                }
              }
            }
            betterMonitor.worked( 1 );
          }
        } );
      } catch( InvocationTargetException itExc ) {
        NewProblemDialog.openProblem( getShell(),
                                      Messages.getString("JobSubmissionWizardBase.errSubmissionFailed"), //$NON-NLS-1$
                                      Messages.getString("JobSubmissionWizardBase.errSubmissionFailed"), //$NON-NLS-1$
                                      itExc.getCause() );
        result = false;
      } catch( InterruptedException intExc ) {
        NewProblemDialog.openProblem( getShell(),
                                      Messages.getString("JobSubmissionWizardBase.errSubmissionInterupted"), //$NON-NLS-1$
                                      Messages.getString("JobSubmissionWizardBase.errSubmissionInterupted"), //$NON-NLS-1$
                                      intExc );
        result = false;
      }
    }
    return result;
  }

  /**
   * Method called when wizard is finished, and job should be submitted
   * Submission service is then asked to submit job using submitJob method
   * 
   * @return
   */
  protected abstract IGridJobSubmissionService getSubmissionService();

  IGridContainer buildPath( final IGridJobDescription description )
      throws CoreException {
    
    IGridContainer result = null;
    
    IGridProject project = description.getProject();
    IPath projectPath = project.getPath();
    
    IGridContainer jobFolder = project.getProjectFolder( IGridJob.class );
    IPath jobFolderPath = jobFolder.getPath();
    
    if ( jobFolderPath.equals( projectPath ) ) {
      result = project;
    } else {
    
      IPath descriptionPath = description.getPath().removeLastSegments( 1 );
      IGridContainer descriptionFolder = project.getProjectFolder( IGridJobDescription.class );
      IPath descriptionFolderPath = descriptionFolder.getPath();
        
      if ( descriptionFolderPath.isPrefixOf( descriptionPath ) ) {
        
        int matchingFirstSegments = descriptionPath.matchingFirstSegments( descriptionFolderPath );
        IPath appendedPath = descriptionPath.removeFirstSegments( matchingFirstSegments );
        jobFolderPath = jobFolderPath.append( appendedPath );
        
        IWorkspaceRoot workspaceRoot = ( IWorkspaceRoot )GridModel.getRoot().getResource();
        IFolder folder = workspaceRoot.getFolder( jobFolderPath );
        createFolder( folder );
        result = ( IGridContainer ) GridModel.getRoot().findElement( folder );
        
      } else {
        result = jobFolder;
      }
      
    }
    
    /*IPath descPath = description.getPath().removeLastSegments( 1 );
    IPath projPath = description.getProject().getPath();
    descPath = descPath.removeFirstSegments( projPath.segmentCount() );
    IPath jobPath = projPath.append( IGridProject.DIR_JOBS );
    if ( IGridProject.DIR_JOBDESCRIPTIONS.equals( descPath.segment( 0 ) ) ) {
      jobPath = jobPath.append( descPath.removeFirstSegments( 1 ) );
    }
    IWorkspaceRoot workspaceRoot = ( IWorkspaceRoot )GridModel.getRoot()
      .getResource();
    IFolder folder = workspaceRoot.getFolder( jobPath );
    createFolder( folder );
    result = ( IGridContainer )GridModel.getRoot().findElement( folder );*/
    return result;
    
  }

  private void createFolder( final IFolder folder ) throws CoreException {
    IContainer parent = folder.getParent();
    if( ( parent != null ) && ( parent instanceof IFolder ) ) {
      createFolder( ( IFolder )parent );
    }
    if( !folder.exists() ) {
      folder.create( true, true, null );
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.ui.wizards.wizardselection.IInitalizableWizard#init(java.lang.Object)
   */
  @SuppressWarnings("unchecked")
  public boolean init( final Object data ) {
    boolean result = false;
    if( data instanceof List ) {
      this.jobDescriptions = ( List<IGridJobDescription> )data;
      result = true;
    }
    return result;
  }

  public void setInitializationData( final IConfigurationElement config,
                                     final String propertyName,
                                     final Object data ) throws CoreException
  {
    IConfigurationElement[] elements = config.getDeclaringExtension()
      .getConfigurationElements();
    for( IConfigurationElement element : elements ) {
      if( "job_creator".equals( element.getName() ) ) { //$NON-NLS-1$
        Object obj = element.createExecutableExtension( "class" ); //$NON-NLS-1$
        if( !( obj instanceof IGridJobCreator ) ) {
          Status status = new Status( IStatus.ERROR,
                                      Activator.PLUGIN_ID,
                                      IStatus.OK,
                                      "Job Creator configured in class atribute for job_creator " //$NON-NLS-1$
                                        + "element in eu.geclipse.ou.jobSubmissionWizzard " //$NON-NLS-1$
                                        + "is not implementing IGridJobCreator interface", //$NON-NLS-1$
                                      null );
          throw new CoreException( status );
        }
        this.creator = ( IGridJobCreator )obj;
      }
    }
  }
  
}
