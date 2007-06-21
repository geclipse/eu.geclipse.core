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

import java.util.List;
import org.eclipse.ui.IMemento;


/**
 * Interface, which gather many different filters, which can be applied on the
 * view. Implementations should collect all filter for one specific view. User
 * can has many different configurations for the same job. In this way, filters
 * are obtained from all enabled configurations.
 */
public interface IGridFilterConfiguration extends Cloneable {
  String getName();
  boolean isEnabled();
  void saveState( final IMemento memento );
  List<IGridFilter> getFilters();
  void read( final IMemento configurationMemento );
  IGridFilterConfiguration clone() throws CloneNotSupportedException;
  void setEnabled( boolean enabled );
}
