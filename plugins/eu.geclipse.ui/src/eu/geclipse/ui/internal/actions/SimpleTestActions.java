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
package eu.geclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import eu.geclipse.ui.Extensions;
import eu.geclipse.ui.ISimpleTestUIFactory;


/**
 * Action group holding all action specific for simple tests.
 * @author harald
 */
public class SimpleTestActions extends ActionGroup {
  /**
   * The workbench site this action group belongs to.
   */
  private IWorkbenchSite site;
  
  /**
   * The tests action itself.
   */
  private List < SimpleTestAction > testActions;

  private MenuManager dropDownMenuMgr;

  /**
   * Create a new simple test actions for the specified workbench site.
   * 
   * @param site The {@link IWorkbenchSite} to create this action for.
   */
  public SimpleTestActions( final IWorkbenchSite site ) {
    this.site = site;
    ISelectionProvider provider = site.getSelectionProvider();
    SimpleTestAction action;
    
    this.testActions = new ArrayList< SimpleTestAction >();
    List< ISimpleTestUIFactory > factories = Extensions.getRegisteredSimpleTestUIFactories();
    
    for ( ISimpleTestUIFactory factory : factories ) {
      action = new SimpleTestAction( this.site, factory );
      provider.addSelectionChangedListener( action );
      this.testActions.add( action );
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#dispose()
   */
  @Override
  public void dispose() {
    ISelectionProvider provider = this.site.getSelectionProvider();
    
    for ( SimpleTestAction action : this.testActions )
      provider.removeSelectionChangedListener( action );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    this.dropDownMenuMgr = new MenuManager( Messages.getString( "SimpleTestActions.title" ) ); //$NON-NLS-1$

    for ( SimpleTestAction action : this.testActions ) {
      if ( action.isEnabled() ) {
        this.dropDownMenuMgr.add( action );
      }
    }
    
    menu.appendToGroup( ICommonMenuConstants.GROUP_ADDITIONS, this.dropDownMenuMgr );

    super.fillContextMenu(menu);
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#updateActionBars()
   */
  @Override
  public void updateActionBars() {
    super.updateActionBars();
    
    IStructuredSelection selection = null;
    
    if( getContext() != null
        && getContext().getSelection() instanceof IStructuredSelection ) {
      selection = (IStructuredSelection)getContext().getSelection();
    }
    
    for( SimpleTestAction action : this.testActions ) {
      action.selectionChanged( selection );      
    }
  }
}
