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
 *    Szymon Mueller - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.internal.actions;

import java.net.URL;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import eu.geclipse.ui.internal.Activator;

/**
 * Action responsible for starting/pausing background updates of jobs
 */
public class ToggleUpdateJobsAction extends Action
  implements ISelectionChangedListener
{

  protected ToggleUpdateJobsAction() {
    super( Messages.getString( "ToggleJobsUpdatesAction.toggle_jobs_updates_action_active_text" ), IAction.AS_CHECK_BOX ); //$NON-NLS-1$
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/job.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
    setChecked( eu.geclipse.core.Preferences.getUpdateJobsStatus() );
  }

  @Override
  public void run() {
    boolean updateJobsStatus = this.isChecked();
    eu.geclipse.core.Preferences.setUpdateJobsStatus( updateJobsStatus );
    eu.geclipse.core.Preferences.save();
    if( updateJobsStatus ) {
      this.setToolTipText( Messages
                           .getString( "ToggleJobsUpdatesAction.toggle_jobs_updates_action_active_text" ) );//$NON-NLS-1$
      eu.geclipse.core.model.GridModel.getJobManager().wakeUpAllUpdaters();
    } else {
      this.setToolTipText( Messages
                           .getString( "ToggleJobsUpdatesAction.toggle_jobs_updates_action_inactive_text" ) );//$NON-NLS-1$
      eu.geclipse.core.model.GridModel.getJobManager().pauseAllUpdaters();
    }
  }

  public void selectionChanged( final SelectionChangedEvent event ) {
    //empty declaration
  }
}
