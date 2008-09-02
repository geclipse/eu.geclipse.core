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
 *    Martin Polak GUP, JKU - initial implementation
 *****************************************************************************/

package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;


public class MonitorActions extends ActionGroup {
  
  /**
   * The workbench this action group belongs to.
   */
  private IWorkbenchPartSite site;
  /**
   * The deploy action itself.
   */
  private MonitorComputingAction monCEAction;
  private MonitorJobAction monJobAction;
  
  /**
   * Contributes Actions to monitor specific computing resources and jobs to the 
   * project views context menu
   * @param site
   */
  public MonitorActions(final IWorkbenchPartSite site){
    this.site = site;
    ISelectionProvider selectionProvider = site.getSelectionProvider();
    this.monCEAction = new MonitorComputingAction( site );
 //   this.monJobAction = new MonitorJobAction (site);
    selectionProvider.addSelectionChangedListener( this.monCEAction );
  //  selectionProvider.addSelectionChangedListener( this.monJobAction );
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.actions.ActionGroup#dispose()
   */
  @Override
  public void dispose()
  {
    ISelectionProvider selectionProvider = this.site.getSelectionProvider();
    selectionProvider.removeSelectionChangedListener( this.monCEAction );
 //   selectionProvider.removeSelectionChangedListener( this.monJobAction );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void fillContextMenu( final IMenuManager mgr )
  {
    super.fillContextMenu( mgr );
    if ( this.monCEAction.isEnabled() ) {
      mgr.appendToGroup( ICommonMenuConstants.GROUP_ADDITIONS, this.monCEAction );
    }
   // if ( this.monJobAction.isEnabled() ) {
   //   mgr.appendToGroup( ICommonMenuConstants.GROUP_BUILD, this.monJobAction );
   // }
    
  }
  
  
  
}
