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
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.search.ui.IContextMenuConstants;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionGroup;

/**
 * Action group holding all mount actions.
 */
public class MountActions extends ActionGroup {
  
  /**
   * The workbench part site for which to create the actions.
   */
  private IWorkbenchPartSite site;
  
  /**
   * The mount action menu.
   */
  private MountMenu newMountMenu;
  
  /**
   * Construct a new mount action group for the specified workbench part site.
   * 
   * @param site The {@link IWorkbenchPartSite} for which to create this
   * action group.
   */
  public MountActions( final IWorkbenchPartSite site ) {
    
    this.site = site;
    Shell shell = site.getShell();
    ISelectionProvider selectionProvider
      = this.site.getSelectionProvider();
    
    this.newMountMenu = new MountMenu( shell );
    
    selectionProvider.addSelectionChangedListener( this.newMountMenu );
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#dispose()
   */
  @Override
  public void dispose() {
    ISelectionProvider selectionProvider
      = this.site.getSelectionProvider();
    selectionProvider.removeSelectionChangedListener( this.newMountMenu );
    this.newMountMenu.dispose();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    if ( this.newMountMenu.isVisible() ) {
      IMenuManager subMenu = new MenuManager( Messages.getString("MountActions.mount_actions_text") ); //$NON-NLS-1$
      menu.appendToGroup( IContextMenuConstants.GROUP_OPEN, subMenu );
      subMenu.add( this.newMountMenu );
    }
    super.fillContextMenu( menu );
  }
  
}
