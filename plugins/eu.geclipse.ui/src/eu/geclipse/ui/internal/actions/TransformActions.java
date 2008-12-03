/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *
 *****************************************************************************/
package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.search.ui.IContextMenuConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionGroup;

/**
 * Transformation Actions
 *
 */
public class TransformActions extends ActionGroup {
  
  private IWorkbenchPartSite site;
  
  private TransformMenu transformMenu;
  
  /**
   * 
   * Constructor
   * @param site the IWorkbenchSite
   */
  public TransformActions( final IWorkbenchPartSite site ) {
    this.site = site;
    this.transformMenu = new TransformMenu( site );
    ISelectionProvider selectionProvider = site.getSelectionProvider();
    selectionProvider.addSelectionChangedListener( this.transformMenu );
  }
  
  @Override
  public void dispose() {
    ISelectionProvider selectionProvider = this.site.getSelectionProvider();
    selectionProvider.removeSelectionChangedListener( this.transformMenu );
    this.transformMenu.dispose();
  }
  
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    
    if ( this.transformMenu.isVisible() ) {
      IMenuManager subMenu = new MenuManager(Messages.getString( "TransformActions.TransformTo" )); //$NON-NLS-1$
      menu.appendToGroup( IContextMenuConstants.GROUP_GENERATE, subMenu );
      subMenu.add( this.transformMenu );
    }
    
    super.fillContextMenu( menu );
    
  }

}
