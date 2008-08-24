/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Ariel Garcia - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;


/**
 * Action group containing all the access control related actions.
 * 
 * @author agarcia
 */
public class AccessControlActions extends ActionGroup {
  
  private final IWorkbenchPartSite site;
  
  private ManagePermissionsAction permissionsAction;
  
  /**
   * Constructs a new access control action group.
   * 
   * @param site the {@link IWorkbenchPartSite} where this action group will be used.
   */
  public AccessControlActions( final IWorkbenchPartSite site ) {
    
    this.site = site;
    this.permissionsAction = new ManagePermissionsAction( site );
    
    ISelectionProvider selectionProvider = site.getSelectionProvider();
    selectionProvider.addSelectionChangedListener( this.permissionsAction );
  }
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu()
   */
  @Override
  public void fillContextMenu( final IMenuManager mgr ) {
    
    super.fillContextMenu( mgr );
    
    if ( this.permissionsAction.isEnabled() ) {
      mgr.appendToGroup( ICommonMenuConstants.GROUP_OPEN, this.permissionsAction );
    }
  }
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#dispose()
   */
  @Override
  public void dispose() {
    ISelectionProvider selectionProvider = this.site.getSelectionProvider();
    selectionProvider.removeSelectionChangedListener( this.permissionsAction );
  }

}
