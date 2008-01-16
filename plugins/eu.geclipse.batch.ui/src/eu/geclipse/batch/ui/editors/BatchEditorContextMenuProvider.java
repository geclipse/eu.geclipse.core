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

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;

import eu.geclipse.batch.ui.internal.ComputingElementAction;
import eu.geclipse.batch.ui.internal.QueueEnDisAction;
import eu.geclipse.batch.ui.internal.QueueDeleteAction;
import eu.geclipse.batch.ui.internal.QueueStartStopAction;
import eu.geclipse.batch.ui.internal.WorkerNodeAction;

/**
 * Provides context menu actions for the BatchEditor.
 */
class BatchEditorContextMenuProvider extends ContextMenuProvider {

  /**
   * The editor's action registry.
   */
  private ActionRegistry actionRegistry;

  /**
   * Instantiate a new menu context provider for the specified EditPartViewer
   * and ActionRegistry.
   *
   * @param viewer the editor's graphical viewer
   * @param registry the editor's action registry
   * @throws IllegalArgumentException if registry is <code>null</code>.
   */
  public BatchEditorContextMenuProvider( final EditPartViewer viewer, final ActionRegistry registry )
  {
    super( viewer );

    if ( registry == null ) {
      throw new IllegalArgumentException();
    }
    
    setActionRegistry( registry );
  }

  /**
   * Called when the context menu is about to show. Actions, whose state is
   * enabled, will appear in the context menu.
   *
   * @see org.eclipse.gef.ContextMenuProvider#buildContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void buildContextMenu( final IMenuManager menu ) {
    // Add standard action groups to the menu
    GEFActionConstants.addStandardActionGroups( menu );

    IAction action;

    // Add actions to the menu
    action = getAction( ActionFactory.UNDO.getId() );
    if ( action.isEnabled() )
      menu.appendToGroup( GEFActionConstants.GROUP_UNDO, action ); 

    action = getAction( ActionFactory.REDO.getId() );
    if ( action.isEnabled() )
      menu.appendToGroup( GEFActionConstants.GROUP_UNDO, action );

    action = getAction( GEFActionConstants.ZOOM_IN );
    if ( action.isEnabled() )
      menu.appendToGroup( GEFActionConstants.GROUP_VIEW, action );

    action = getAction( GEFActionConstants.ZOOM_OUT );
    if ( action.isEnabled() )
      menu.appendToGroup( GEFActionConstants.GROUP_VIEW, action );

    action = getAction( WorkerNodeAction.PROPERTY_WN_ACTION_CHANGESTATUS );
    if ( action.isEnabled() )
      menu.appendToGroup( GEFActionConstants.GROUP_EDIT, action);

    action = getAction( QueueEnDisAction.PROPERTY_QUEUE_ACTION_ENABLE_DISABLE );
    if ( action.isEnabled() )
      menu.appendToGroup( GEFActionConstants.GROUP_EDIT, action);
    
    action = getAction( QueueStartStopAction.PROPERTY_QUEUE_ACTION_START_STOP );
    if ( action.isEnabled() )
      menu.appendToGroup( GEFActionConstants.GROUP_EDIT, action);

    action = getAction( QueueDeleteAction.PROPERTY_QUEUE_ACTION_DELETE );
    if ( action.isEnabled() )
      menu.appendToGroup( GEFActionConstants.GROUP_EDIT, action);
    
    action = getAction( ComputingElementAction.PROPERTY_COMPUTINGELEMENT_ACTION_NEWQUEUE );
    if ( action.isEnabled() )
      menu.appendToGroup( GEFActionConstants.GROUP_EDIT, action);
  }

  /**
   * Returns the action given its id
   * @param actionId the id of the action to be returned
   * @return Returns the action with the given id
   */
  private IAction getAction( final String actionId ) {
    return this.actionRegistry.getAction( actionId );
  }
  
  /**
   * Sets the action registry
   * @param registry The action registry
   */
  public void setActionRegistry( final ActionRegistry registry ) {
    this.actionRegistry = registry;
  }
  
}
