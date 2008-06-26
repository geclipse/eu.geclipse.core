/*****************************************************************************
 * Copyright (c) 2006, 2007, 2008 g-Eclipse Consortium 
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
 *    Jie Tao - initial API and implementation
 *****************************************************************************/
 
package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;

 



/**an action for validating the deployed software
 * @author tao-j
 *
 */
public class ValidateActions extends ActionGroup {

  /**
   * The workbench this action group belongs to.
   */
  private IWorkbenchPartSite site;
  /**
   * The deploy action itself.
   */
  private ValidateAction validateAction;

  /**define the actions for uninstall a software
   * @param site
   */
  public ValidateActions( final IWorkbenchPartSite site ) {
    this.site = site;
    ISelectionProvider selectionProvider = site.getSelectionProvider();
    this.validateAction = new ValidateAction( site );
    selectionProvider.addSelectionChangedListener( this.validateAction );
  }

  @Override
  public void dispose() {
    ISelectionProvider selectionProvider = this.site.getSelectionProvider();
    selectionProvider.removeSelectionChangedListener( this.validateAction );
  }

  @Override
  public void fillContextMenu( final IMenuManager mgr ) {
    if ( this.validateAction.isEnabled() ) {
      mgr.appendToGroup( ICommonMenuConstants.GROUP_BUILD, this.validateAction );
    }
    super.fillContextMenu( mgr );
  }
  
}



