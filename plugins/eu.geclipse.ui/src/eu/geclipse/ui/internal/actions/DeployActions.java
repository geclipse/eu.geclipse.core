/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Yifan Zhou - initial API and implementation
 ******************************************************************************/
package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;

  /**
   * Action group holding all actions specific for application deployment
   */
public class DeployActions extends ActionGroup {

  /**
   * The workbench this action group belongs to.
   */
  private IWorkbenchPartSite site;
  /**
   * The deploy action itself.
   */
  private DeployAction deployAction;

  /**
   * Create a new deploy action for the specified workbench site.
   * 
   * @param site The {@link IWorkbenchSite} to create this action for.
   */
  public DeployActions( final IWorkbenchPartSite site ) {
    this.site = site;
    ISelectionProvider selectionProvider = site.getSelectionProvider();
    this.deployAction = new DeployAction( site );
    selectionProvider.addSelectionChangedListener( this.deployAction );
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
    selectionProvider.removeSelectionChangedListener( this.deployAction );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void fillContextMenu( final IMenuManager mgr )
  {
    if ( this.deployAction.isEnabled() ) {
      mgr.appendToGroup( ICommonMenuConstants.GROUP_BUILD, this.deployAction );
    }
    super.fillContextMenu( mgr );
  }
}
