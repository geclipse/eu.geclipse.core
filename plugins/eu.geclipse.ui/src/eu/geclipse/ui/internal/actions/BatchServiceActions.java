/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;


/**
 * @author nickl
 *
 */
public class BatchServiceActions extends ActionGroup {
  /**
   * The workbench site this action group belongs to.
   */
  private IWorkbenchSite site;
  
  /**
   * The submit job action itself.
   */
  private ApplyQueueConfigurationAction applyQueueConfigAction;
  
  
  /**
   * Create a new Batch Service action for the specified workbench site.
   * 
   * @param site The {@link IWorkbenchSite} to create this action for.
   */
  public BatchServiceActions ( final IWorkbenchSite site ) {
    this.site = site;
    ISelectionProvider provider = site.getSelectionProvider();
    this.applyQueueConfigAction = new ApplyQueueConfigurationAction( site );
    provider.addSelectionChangedListener( this.applyQueueConfigAction );
  }
  
  
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#dispose()
   */
  @Override
  public void dispose() {
    ISelectionProvider provider = this.site.getSelectionProvider();
    provider.removeSelectionChangedListener( this.applyQueueConfigAction );
  }

  
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    if ( this.applyQueueConfigAction.isEnabled() ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_BUILD, 
                          this.applyQueueConfigAction );
    }
    super.fillContextMenu(menu);
  }
  
  
  
} // End BatchServiceActions() class
