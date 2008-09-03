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

package eu.geclipse.ui.internal.dialogs.configureFilters;

import eu.geclipse.ui.views.filters.IGridFilter;
import eu.geclipse.ui.views.filters.IGridFilterConfiguration;


/**
 * Composite implementing user interface to configure specific {@link IGridFilter}
 */
public interface IFilterComposite {
  
  
  /**
   * Find filter in {@link IGridFilterConfiguration} and show found filter values
   * @param filterConfiguration configuration, from which proper {@link IGridFilter} should be obtained
   */
  void setFilter( final IGridFilterConfiguration filterConfiguration );
  
  /**
   * Save values changed by user into stored {@link IGridFilter}
   * @return true if everything was successfully saved, false if saving should be interupted
   * 
   */
  boolean saveToFilter();

  /**
   * Changes controls state. Called when none filter is selected in dialog, and user shouldn't change data
   * @param enabled true if composite should be readolny, false if should be writeable
   */
  void setEnabled( boolean enabled );
}
