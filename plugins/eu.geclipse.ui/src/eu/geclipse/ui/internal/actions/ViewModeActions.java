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
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionGroup;

import eu.geclipse.ui.providers.ConfigurableContentProvider;
import eu.geclipse.ui.views.ElementManagerViewPart;

/**
 * Action group holding the view mode action for {@link ElementManagerViewPart}s.
 */
public class ViewModeActions extends ActionGroup {
  
  /**
   * The flat mode action.
   */
  private ViewModeAction flatAction;

  /**
   * The hierarchical mode Action.
   */
  private ViewModeAction hierarchicalAction;
  
  /**
   * Construct a new view mode action group for the specified view.
   * 
   * @param view The {@link ElementManagerViewPart} for which to create
   * this action group.
   */
  public ViewModeActions( final ElementManagerViewPart view ) {
    this.flatAction
      = new ViewModeAction( Messages.getString("ViewModeActions.flat_action_name"), //$NON-NLS-1$
                            ConfigurableContentProvider.MODE_FLAT,
                            view );
    this.hierarchicalAction
      = new ViewModeAction( Messages.getString("ViewModeActions.hierarchical_action_name"), //$NON-NLS-1$
                            ConfigurableContentProvider.MODE_HIERARCHICAL,
                            view );
    registerAction( this.flatAction );
    registerAction( this.hierarchicalAction );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#dispose()
   */
  @Override
  public void dispose() {
    unregisterAction( this.flatAction );
    unregisterAction( this.hierarchicalAction );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
   */
  @Override
  public void fillActionBars( final IActionBars actionBars ) {
    IMenuManager menuManager = actionBars.getMenuManager();
    menuManager.add( this.flatAction );
    menuManager.add( this.hierarchicalAction );
  }
  
  /**
   * @param action
   */
  protected void registerAction( final ViewModeAction action ) {
    ConfigurableContentProvider contentProvider
      = this.flatAction.getContentProvider();
    if ( contentProvider != null ) {
      contentProvider.addConfigurationListener( action );
    }
  }
  
  /**
   * @param action
   */
  protected void unregisterAction( final ViewModeAction action ) {
    ConfigurableContentProvider contentProvider
      = this.flatAction.getContentProvider();
    if ( contentProvider != null ) {
      contentProvider.removeConfigurationListener( action );
    }
  }
  
}
