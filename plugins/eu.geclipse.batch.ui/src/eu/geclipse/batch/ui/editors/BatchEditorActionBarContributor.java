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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.editors;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.actions.ActionFactory;

import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
//import org.eclipse.gef.ui.actions.ZoomInAction;
//import eu.geclipse.batch.ui.internal.Messages;
//import org.eclipse.gef.ui.actions.GEFActionConstants;
//import org.eclipse.jface.action.IMenuManager;
//import org.eclipse.jface.action.MenuManager;
//import org.eclipse.ui.IWorkbenchActionConstants;

/**
 * Contributes actions to a toolbar.
 * This class is tied to the editor in the definition of editor-extension (see plugin.xml).
 */
public class BatchEditorActionBarContributor extends ActionBarContributor {

  /**
   * Create actions managed by this contributor.
   * @see org.eclipse.gef.ui.actions.ActionBarContributor#buildActions()
   */
  @Override
  protected void buildActions() {
    addRetargetAction( new UndoRetargetAction() );
    addRetargetAction( new RedoRetargetAction() );
  }

  /**
   * Add actions to the given toolbar.
   * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToToolBar(org.eclipse.jface.action.IToolBarManager)
   */
  @Override
  public void contributeToToolBar( final IToolBarManager toolBarManager ) {
    String[] zoomStrings = new String[] {
      ZoomManager.FIT_ALL, ZoomManager.FIT_HEIGHT,
      ZoomManager.FIT_WIDTH
    };

    toolBarManager.add( getAction( ActionFactory.UNDO.getId() ) );
    toolBarManager.add( getAction( ActionFactory.REDO.getId() ) );
    toolBarManager.add( new ZoomComboContributionItem( getPage(), zoomStrings ) );
  }

//  @Override
/*  public void contributeToMenu( final IMenuManager menubar ) {
    super.contributeToMenu( menubar );

    MenuManager viewMenu = new MenuManager( Messages.getString( "BatchEditorActionBarContributor.Zoom" ) );//$NON-NLS-1$
    viewMenu.add( getAction( GEFActionConstants.ZOOM_IN ) );
    viewMenu.add( getAction( GEFActionConstants.ZOOM_OUT ) );
    menubar.insertAfter( IWorkbenchActionConstants.M_EDIT, viewMenu );  
  }
*/  
  /*
   * (non-Javadoc)
   * @see org.eclipse.gef.ui.actions.ActionBarContributor#declareGlobalActionKeys()
   */
  @Override
  protected void declareGlobalActionKeys() {
    // currently none
  }

}