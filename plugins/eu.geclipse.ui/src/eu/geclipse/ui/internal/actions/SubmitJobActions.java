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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;


/**
 * Action group holding all action specific for job submission.
 */
public class SubmitJobActions extends ActionGroup {
  
  /**
   * The workbench site this action group belongs to.
   */
  private IWorkbenchSite site;
  
  /**
   * The submit job action itself.
   */
  private SubmitJobAction submitAction;
  
  /**
   * Create a new job submission action for the specified workbench site.
   * 
   * @param site The {@link IWorkbenchSite} to create this action for.
   */
  public SubmitJobActions( final IWorkbenchSite site ) {
    this.site = site;
    ISelectionProvider provider = site.getSelectionProvider();
    this.submitAction = new SubmitJobAction( site );
    provider.addSelectionChangedListener( this.submitAction );
    
    ISelection selection = provider.getSelection();
    if ( ( selection == null ) || ! ( selection instanceof IStructuredSelection ) ) {
      selection = StructuredSelection.EMPTY;
    }
    this.submitAction.selectionChanged( ( IStructuredSelection ) selection );
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#dispose()
   */
  @Override
  public void dispose() {
    ISelectionProvider provider = this.site.getSelectionProvider();
    provider.removeSelectionChangedListener( this.submitAction );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    if ( this.submitAction.isEnabled() ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_BUILD, 
                          this.submitAction );
    }
    super.fillContextMenu(menu);
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
    
    this.submitAction.selectionChanged( selection );
  }
  
}
