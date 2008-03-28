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
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionGroup;
import eu.geclipse.ui.views.GridModelViewPart;

/**
 * Action group that holds editor related actions.
 */
public class EditorActions extends ActionGroup {
  
  private GridModelViewPart view;
  
  /**
   * The link with editor action.
   */
  private LinkWithEditorAction linkWithEditorAction;
  
  /**
   * Construct a new editor actions group.
   * 
   * @param view The view this group belongs to.
   */
  public EditorActions( final GridModelViewPart view ) {
    this.view = view;
    this.linkWithEditorAction = new LinkWithEditorAction( view );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
   */
  @Override
  public void fillActionBars( final IActionBars actionBars ) {
    IToolBarManager manager = actionBars.getToolBarManager();
    manager.add( this.linkWithEditorAction );
  }
  
  /**
   * Get the associated view of this editor actions.
   * 
   * @return The associated view.
   */
  public GridModelViewPart getView() {
    return this.view;
  }
  
  /**
   * Delegate a link editor action to the associated action's
   * run method.
   */
  public void delegateLinkEditorAction() {
    this.linkWithEditorAction.run();
  }
  
}
