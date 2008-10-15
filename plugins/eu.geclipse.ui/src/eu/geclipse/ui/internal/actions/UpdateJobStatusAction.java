/*****************************************************************************
 * Copyright (c) 2007 g-Eclipse Consortium 
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
 *    Szymon Mueller - initial API and implementation
 *****************************************************************************/
package eu.geclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.SelectionListenerAction;

import eu.geclipse.core.jobs.GridJob;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.ui.internal.Activator;

/**
 * Action for toggling refresh for selected job
 */
public class UpdateJobStatusAction extends SelectionListenerAction {
  
  ArrayList<IGridJob> selectedJobs = new ArrayList<IGridJob>();
  private IWorkbenchWindow workbenchWindow;

  /**
   * Constructor.
   * @param workbenchWindow Workbench window
   */
  public UpdateJobStatusAction( final IWorkbenchWindow workbenchWindow ) {
    super( Messages.getString( "UpdateJobStatusAction.title" ) ); //$NON-NLS-1$
    this.workbenchWindow = workbenchWindow;
    setImageDescriptor( Activator.getDefault()
      .getImageRegistry()
      .getDescriptor( Activator.IMG_UPDATE_JOB_STATUS ) );
  }

  protected UpdateJobStatusAction() {
    super( Messages.getString( "UpdateJobStatusAction.title" ) ); //$NON-NLS-1$
    setImageDescriptor( Activator.getDefault()
      .getImageRegistry()
      .getDescriptor( Activator.IMG_UPDATE_JOB_STATUS ) );
  }

  @Override
  public void run() {
    if( this.selectedJobs.size() > 0 ) {
      final ArrayList<IGridJob> jobsToUpdate = ( ArrayList<IGridJob> )this.selectedJobs.clone();
        Job job = new Job( Messages.getString( "UpdateJobStatusAction.manual_update_job_name" ) ) { //$NON-NLS-1$

          @Override
          protected IStatus run( final IProgressMonitor monitor ) {
            SubMonitor subMonitor = SubMonitor.convert( monitor );
            subMonitor.beginTask( Messages.getString( "UpdateJobStatusAction.manual_update_task_name" ), //$NON-NLS-1$
                               jobsToUpdate.size() );
            for( IGridJob jobToUpdate : jobsToUpdate )
            {
              if( !subMonitor.isCanceled() ) {
                subMonitor.subTask( Messages.getString( "UpdateJobStatusAction.manual_update_subtask_name" ) //$NON-NLS-1$
                                 + " " + jobToUpdate.getID().getJobID() );                 //$NON-NLS-1$
                IGridJobStatus oldStatus = jobToUpdate.getJobStatus();
                jobToUpdate.updateJobStatus( subMonitor.newChild( 1 ), true );
                GridModel.getJobManager().jobStatusChanged( jobToUpdate, oldStatus );                
              }
            }
            subMonitor.done();
            return Status.OK_STATUS;
          }
        };
        job.setUser( true );
        job.schedule();
      }
  }

  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    this.selectedJobs.clear();
    for( Iterator<?> iterator = selection.iterator(); iterator.hasNext(); ) {
      Object element = iterator.next();
      if( element instanceof GridJob ) {
        this.selectedJobs.add( ( GridJob )element );
      }
    }
    if( this.selectedJobs.size() > 0 ) {
      if( !this.isEnabled() ) {
        this.setEnabled( true );
      }
    } else {
      this.setEnabled( false );
    }
    return super.updateSelection( selection )
           && ( this.selectedJobs.size() > 0 );
  }
}
