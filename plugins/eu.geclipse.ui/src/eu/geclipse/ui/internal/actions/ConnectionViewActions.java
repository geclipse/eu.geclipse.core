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
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;

/**
 * Action group that holds all actions specific to the
 * {@link eu.geclipse.ui.views.GridConnectionView}.
 */
public class ConnectionViewActions extends ActionGroup {
  
  /**
   * The new connection action.
   */
  private NewConnectionAction newConnectionAction;
  
  private DeleteConnectionAction deleteConnectionAction;
  
  private IWorkbenchSite site; 
  
  /**
   * Create a new connection view action for the specified workbench
   * window.
   * 
   * @param workbenchWindow The {@link IWorkbenchWindow} to generate
   * this action for.
   */
  public ConnectionViewActions( final IWorkbenchSite site ) {
    
    this.site = site;
    
    this.newConnectionAction = new NewConnectionAction( site.getWorkbenchWindow() );
    this.deleteConnectionAction = new DeleteConnectionAction();
    
    ISelectionProvider provider = this.site.getSelectionProvider();
    provider.addSelectionChangedListener( this.newConnectionAction );
    provider.addSelectionChangedListener( this.deleteConnectionAction );
    
  }
  
  @Override
  public void dispose() {
    ISelectionProvider provider = this.site.getSelectionProvider();
    provider.removeSelectionChangedListener( this.newConnectionAction );
    provider.removeSelectionChangedListener( this.deleteConnectionAction );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
   */
  @Override
  public void fillActionBars( final IActionBars actionBars ) {
    IToolBarManager manager = actionBars.getToolBarManager();
    manager.add( this.newConnectionAction );
    manager.add( this.deleteConnectionAction );
  }
  
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    
    if ( this.newConnectionAction.isEnabled() ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_NEW,
                          this.newConnectionAction );
    }
    
    if ( this.deleteConnectionAction.isEnabled() ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_NEW,
                          this.deleteConnectionAction );
    }
    
  }
  
}
