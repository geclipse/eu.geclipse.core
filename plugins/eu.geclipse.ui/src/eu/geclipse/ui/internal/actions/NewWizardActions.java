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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.search.ui.IContextMenuConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.actions.NewWizardMenu;

/**
 * Action group holding the new wizard actions.
 */
public class NewWizardActions extends ActionGroup {
  
  /**
   * The workbench window of this action group.
   */
  private IWorkbenchWindow window;
  
  /**
   * Create a new action for the specified workbench window.
   * 
   * @param window The {@link IWorkbenchWindow} for which to create
   * this action group.
   */
  public NewWizardActions( final IWorkbenchWindow window ) {
    super();
    this.window = window;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    super.fillContextMenu( menu );
    ISelection selection= getContext().getSelection();
    if (selection instanceof IStructuredSelection) {
      IStructuredSelection sselection= ( IStructuredSelection ) selection;
      if ( sselection.size() <= 1 ) {
        IMenuManager submenu = new MenuManager( Messages.getString( "NewWizardActions.new_action_group" ) ); //$NON-NLS-1$
        menu.appendToGroup( IContextMenuConstants.GROUP_NEW, submenu );
        submenu.add( new NewWizardMenu( this.window ) );
      }
    }
  }
  
}
