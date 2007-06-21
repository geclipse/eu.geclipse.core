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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import eu.geclipse.ui.views.filters.GridFilterConfigurationsManager;


/**
 *
 */
public class FilterActions extends ActionGroup {
  private IWorkbenchSite site;
  private GridFilterConfigurationsManager filterConfigurationsManager;
  
  public FilterActions( final IWorkbenchSite site, final GridFilterConfigurationsManager filterConfigurationsManager ) {
    super();
    this.site = site;
    this.filterConfigurationsManager = filterConfigurationsManager;
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
   */
  @Override
  public void fillActionBars( IActionBars actionBars )
  {
    actionBars.getToolBarManager().add( new ConfigureFiltersAction( this.site, this.filterConfigurationsManager ) );
//    actionBars.getToolBarManager().add( createChangeFilter() );
    super.fillActionBars( actionBars );
  }
  
  private IAction createChangeFilter() {
    return new Action( "Filter", Action.AS_DROP_DOWN_MENU ) {
      
    };
  }
}
