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

package eu.geclipse.ui.views.filters;

import org.eclipse.jface.viewers.ViewerFilter;

import eu.geclipse.ui.internal.actions.ConfigureFiltersAction;


/**
 * Listener which will notified about changes in {@link IGridFilterConfiguration}
 */
public interface IFilterConfigurationListener {
  
  /**
   * Called by {@link GridFilterConfigurationsManager}, when any property in configuration was changed
   * (e.g. new configuration added, configuration deleted, or changed).
   * Used mainly by {@link ConfigureFiltersAction} to update menu items with created configurations
   */
  public void configurationChanged();
  
  /**
   * Called when user changed current configuration
   * @param filters new filters selected by user, which should be applied to the view
   */
  public void filterConfigurationSelected( final ViewerFilter[] filters );
}
