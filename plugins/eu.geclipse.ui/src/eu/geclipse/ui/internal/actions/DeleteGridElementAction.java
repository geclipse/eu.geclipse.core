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
package eu.geclipse.ui.internal.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.DeleteResourceAction;
import org.eclipse.ui.actions.SelectionListenerAction;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ISolution;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;


/**
 *
 */
public class DeleteGridElementAction extends SelectionListenerAction {
  Shell shell;
  private DeleteResourceAction eclipseAction;

  protected DeleteGridElementAction(final Shell shell) {
    super( Messages.getString("DeleteGridElementAction.actionNameDelete") ); //$NON-NLS-1$
    
    this.shell = shell;
    this.eclipseAction = new DeleteResourceAction( shell );
    
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    ImageDescriptor deleteImage 
        = sharedImages.getImageDescriptor( ISharedImages.IMG_TOOL_DELETE );
    setImageDescriptor( deleteImage );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    List<IGridJob> selectedJobs = new ArrayList<IGridJob>( getSelectedResources().size() );
    List<IResource> selectedResources = new ArrayList<IResource>( getSelectedResources().size() );
    dispatchSelectedElements( selectedJobs, selectedResources );
      if( !selectedJobs.isEmpty() ) {
        deleteJobs( selectedJobs );
      }
      if( !selectedResources.isEmpty() ) {
        deleteOtherResources( selectedResources );
      }    
  }
  
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    return ! getSelectedResources().isEmpty();
  }

  private void dispatchSelectedElements( final List<IGridJob> selectedJobs, final List<IResource> otherSelectedResources ) {   
    for( Object obj : getSelectedResources() ) {
      if( obj instanceof IResource ) {
        IResource resource = (IResource)obj;
        IGridElement element = GridModel.getRoot().findElement( resource );
        
        if( element instanceof IGridJob ) {
          selectedJobs.add( (IGridJob)element );
        } else {
          otherSelectedResources.add( resource );
        }
      }
    }
  }
  
  private void deleteOtherResources( final List<IResource> selectedResources ) {
    this.eclipseAction.selectionChanged( new StructuredSelection( selectedResources ) );
    this.eclipseAction.run();
    
  }
  
  private enum ConfirmChoice {
    /**
     * 
     */
    deleteFromGrid,
    /**
     * 
     */
    deleteOnlyFromWorkspace,
    /**
     * 
     */
    cancel
  }

  private void deleteJobs( final List<IGridJob> selectedJobs ) {
    ConfirmChoice choice = confirmDelete( selectedJobs );
    
    if( choice != ConfirmChoice.cancel ) {
      DeleteJobsJob job = new DeleteJobsJob( selectedJobs, choice );
      
      job.setUser( true );
      job.schedule();
    }
  }

  private ConfirmChoice confirmDelete( final List<IGridJob> selectedJobs ) {
    ConfirmChoice choice = ConfirmChoice.cancel;
    String question = null, warning = null;
    
    if( selectedJobs.size() == 1 ) {
      IGridJob job = selectedJobs.iterator().next();
      question = String.format( Messages.getString("DeleteGridElementAction.confirmationOne"), job.getJobName() ); //$NON-NLS-1$
      if( job.getJobStatus().canChange() ) {
        warning = String.format( Messages.getString("DeleteGridElementAction.warningOne"), job.getJobName() ); //$NON-NLS-1$
      }
    } else {
      question = String.format( Messages.getString("DeleteGridElementAction.confirmationMany"), Integer.valueOf( selectedJobs.size() ) ); //$NON-NLS-1$
      
      for( IGridJob job : selectedJobs ) {
        if( job.getJobStatus().canChange() ) {
          warning = Messages.getString("DeleteGridElementAction.warningMany"); //$NON-NLS-1$
          break;
        }
      }
    }
    
    String msg = question;
    
    if( warning != null ) {
      msg += "\n\n" + warning; //$NON-NLS-1$
    }
    
    ConfirmDialog dialog = new ConfirmDialog( msg, warning == null ? MessageDialog.QUESTION : MessageDialog.WARNING );
    
    if( dialog.open() == 0 ) {
      if( dialog.isDeleteFromGrid() ) {
        choice = ConfirmChoice.deleteFromGrid;
      } else {
        choice = ConfirmChoice.deleteOnlyFromWorkspace;
      }              
    }
 
    return choice;
  }
  
  private class ConfirmDialog extends MessageDialog {
    private Button deleteFromGridCheckbox;
    private boolean deleteFromGrid;

    ConfirmDialog( final String dialogMessage,
                          final int dialogImageType                          
                           )
    {
      super( DeleteGridElementAction.this.shell,
             Messages.getString("DeleteGridElementAction.confirmationTitle"), //$NON-NLS-1$
             null,
             dialogMessage,
             dialogImageType,
             new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL },
             0 );
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.MessageDialog#createCustomArea(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createCustomArea( final Composite parent ) {
      this.deleteFromGridCheckbox = new Button( parent, SWT.CHECK );
      this.deleteFromGridCheckbox.setText( Messages.getString("DeleteGridElementAction.alsoDeleteFromGrid") ); //$NON-NLS-1$
      this.deleteFromGridCheckbox.setSelection( true );      
      
      return super.createCustomArea( parent );
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.Dialog#close()
     */
    @Override
    public boolean close() {
      this.deleteFromGrid = this.deleteFromGridCheckbox.getSelection();
      return super.close();
    }

    boolean isDeleteFromGrid() {
      return this.deleteFromGrid;
    }
    
  }

  
 
  private class DeleteJobsJob extends Job {
    boolean forceDeleteLocal = false;
    private List<IGridJob> selectedJobs;
    private ConfirmChoice userChoice;    

    /**
     * @param selectedJobs
     * @param userChoice
     */
    public DeleteJobsJob( final List<IGridJob> selectedJobs,
                          final ConfirmChoice userChoice )
    {
      super( Messages.getString("DeleteGridElementAction.deleteJobName") ); //$NON-NLS-1$
      this.selectedJobs = selectedJobs;
      this.userChoice = userChoice;
    }


    @Override
    protected IStatus run( final IProgressMonitor monitor ) {
      IStatus status = Status.OK_STATUS;
      SubMonitor submonitor = SubMonitor.convert( monitor );
      submonitor.setWorkRemaining( this.selectedJobs.size() );
      
      try {
        Iterator<IGridJob> iterator = this.selectedJobs.iterator();
        IGridJob job = null;
        while( iterator.hasNext() ) {
          try {            
            testCancel( submonitor );
            job = iterator.next();
            
            deleteJob( submonitor.newChild( 1 ), job );
            iterator.remove();  // if succesful deleted, then don't delete it again during eventually next try
          } catch( ProblemException exception ) {
            addSolutionOnlyLocalDel( exception );
            ProblemDialog.openProblem( DeleteGridElementAction.this.shell,
                                       Messages.getString("DeleteGridElementAction.deleteProblemTitle"), //$NON-NLS-1$
                                       String.format( Messages.getString("DeleteGridElementAction.problemDescription"),  //$NON-NLS-1$
                                                      job != null ? job.getJobName() : "unknown" ), //$NON-NLS-1$
                                       exception );
            break;
          }      
        }
      } finally {
        submonitor.done();
      }
      
      return status;
    }

    private void deleteJob( final SubMonitor monitor, final IGridJob job )
      throws ProblemException
    {
      monitor.setTaskName( String.format( Messages.getString("DeleteGridElementAction.taskNameDeleting"), job.getJobName() ) ); //$NON-NLS-1$
      monitor.setWorkRemaining( this.userChoice == ConfirmChoice.deleteFromGrid ? 3 : 2 );
      
      stopJobStatusUpdater( job, monitor.newChild( 1 ) );
      
      if( this.userChoice == ConfirmChoice.deleteFromGrid ) {
        try {
          job.deleteJob( monitor.newChild( 1 ) );
        } catch( ProblemException exception ) {
          if( this.forceDeleteLocal ) {
            Activator.logException( exception );
          }
          else {
            throw exception;
          }
        }
      }

      try {
        testCancel( monitor );
        job.getResource().delete( true, monitor.newChild( 1 ) );
      } catch( CoreException exception ) {
        throw new ProblemException( "eu.geclipse.problem.deleteGridElementAction.cannotDeleteResource", //$NON-NLS-1$
                                    exception,
                                    Activator.PLUGIN_ID );
      }
    }
    
    private void stopJobStatusUpdater( final IGridJob job, final SubMonitor monitor ) {
      monitor.subTask( Messages.getString("DeleteGridElementAction.taskStoppingUpdater") ); //$NON-NLS-1$
      GridModel.getJobManager().removeJobStatusUpdater( job, true, monitor );      
    }


    private void testCancel( final IProgressMonitor monitor ) {
      if( monitor.isCanceled() ) {
        throw new OperationCanceledException();
      }      
    }

    void addSolutionOnlyLocalDel( final ProblemException exception ) {
      if( this.userChoice == ConfirmChoice.deleteFromGrid
          && !this.forceDeleteLocal ) {
        IProblem problem = exception.getProblem();
        problem.addSolution( new ISolution() {

          public String getDescription() {
            return Messages.getString("DeleteGridElementAction.forceDeleteLocal"); //$NON-NLS-1$
          }

          public String getID() {
            return null;
          }

          public boolean isActive() {
            return true;
          }

          public void solve() throws InvocationTargetException {
            DeleteJobsJob.this.forceDeleteLocal = true;
            DeleteJobsJob.this.schedule();            
          }} );
      }
      
    }
    
  }



  
}
