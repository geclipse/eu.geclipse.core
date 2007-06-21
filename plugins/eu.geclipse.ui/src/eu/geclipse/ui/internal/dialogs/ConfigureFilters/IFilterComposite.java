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
package eu.geclipse.ui.internal.dialogs.ConfigureFilters;

import org.eclipse.swt.widgets.Composite;
import eu.geclipse.ui.views.filters.IGridFilter;
import eu.geclipse.ui.views.filters.IGridFilterConfiguration;

public interface IFilterComposite {
  void setFilter( final IGridFilterConfiguration filterConfiguration );
  void saveToFilter();
  void setReadOnly( boolean readOnly );
}
