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

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;

/**
 * Class grouping all actions of GridJobView
 */
public class JobViewActions extends ActionGroup {

  private ToggleUpdateJobsAction toggleJobsUpdateAction;
  private UpdateJobStatusAction updateSelectedJobStatusAction;  
  private IWorkbenchSite site;

  /**
   * Constructor for GridJobView actions
   * @param site
   */
  public JobViewActions( final IWorkbenchSite site ) {
    this.site = site;
    this.toggleJobsUpdateAction = new ToggleUpdateJobsAction();
    this.updateSelectedJobStatusAction = new UpdateJobStatusAction( site.getWorkbenchWindow());    
    ISelectionProvider provider = this.site.getSelectionProvider();
    provider.addSelectionChangedListener( this.toggleJobsUpdateAction );
    provider.addSelectionChangedListener( this.updateSelectedJobStatusAction );    
  }
  
  /**
   * Sets if updates of job statuses should be running and updates tool tip for button 
   * @param status to be set
   */
  public void setJobsUpdateStatus( final boolean status ) {
    this.toggleJobsUpdateAction.setChecked( status );
    if( status ) {
      this.toggleJobsUpdateAction.setToolTipText( Messages
                                                  .getString( "ToggleJobsUpdatesAction.toggle_jobs_updates_action_active_text" ) );//$NON-NLS-1$
    } else {
      this.toggleJobsUpdateAction.setToolTipText( Messages
                                                  .getString( "ToggleJobsUpdatesAction.toggle_jobs_updates_action_inactive_text" ) );//$NON-NLS-1$
    }
  }  

  @Override
  public void dispose() {
    ISelectionProvider provider = this.site.getSelectionProvider();
    provider.removeSelectionChangedListener( this.toggleJobsUpdateAction );
    provider.removeSelectionChangedListener( this.updateSelectedJobStatusAction );    
  }

  @Override
  public void fillActionBars( final IActionBars actionBars ) {
    IToolBarManager manager = actionBars.getToolBarManager();
    manager.add( this.updateSelectedJobStatusAction );
    manager.add( this.toggleJobsUpdateAction );    
  }

  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    if( this.toggleJobsUpdateAction != null ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_NEW,
                          this.toggleJobsUpdateAction );
      if( this.updateSelectedJobStatusAction.isEnabled() ) {
        menu.appendToGroup( ICommonMenuConstants.GROUP_BUILD,
                            this.updateSelectedJobStatusAction );
      }
      super.fillContextMenu( menu );
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#updateActionBars()
   */
  @Override
  public void updateActionBars() {
    super.updateActionBars();
    
    IStructuredSelection selection = null;
    
    if( getContext() != null
        && getContext().getSelection() instanceof IStructuredSelection ) {
      selection = (IStructuredSelection)getContext().getSelection();
    }
    
    this.updateSelectedJobStatusAction.selectionChanged( selection );
  }  
  
}
