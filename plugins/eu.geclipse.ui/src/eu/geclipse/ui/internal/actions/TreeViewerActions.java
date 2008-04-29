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

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionGroup;

/**
 * Action group that contains all actions that are dedicated to
 * {@link TreeViewer}s.
 */
public class TreeViewerActions extends ActionGroup {
  
  /**
   * The collapse all action.
   */
  private CollapseAllAction collapseAllAction;
  
  /**
   * Construct a <code>StandardTreeViewerActions</code> for the
   * specified {@link TreeViewer}.
   * 
   * @param viewer The tree viewer for which to create this action
   * group.
   */
  public TreeViewerActions( final TreeViewer viewer ) {
    this.collapseAllAction = new CollapseAllAction( viewer );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
   */
  @Override
  public void fillActionBars( final IActionBars actionBars ) {
    IToolBarManager manager = actionBars.getToolBarManager();
    manager.add( this.collapseAllAction );
  }
  
}
