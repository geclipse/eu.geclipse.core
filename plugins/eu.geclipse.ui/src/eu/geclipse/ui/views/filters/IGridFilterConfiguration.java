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
 * Interface, which gather all possible filters, which can be applied on the
 * view. 
 * Implementations should collect all filters for one specific view. 
 */
public interface IGridFilterConfiguration extends Cloneable {
  /**
   * @return configuration name
   */
  String getName();
  
  /**
   * @return true if filters in this configuration should be applied into view
   */
  boolean isEnabled();
  
  /**
   * Saves configuration and all filters into memento
   * @param memento
   */
  void saveState( final IMemento memento );

  /**
   * Reads configuration and all filters from memento
   * @param configurationMemento
   */
  void read( final IMemento configurationMemento );
  
  /**
   * @return all enabled IGridFilter objects from this configuration, which should be applied to the view
   */
  List<IGridFilter> getFilters();
  
  IGridFilterConfiguration clone() throws CloneNotSupportedException;
  
  /**
   * @param enabled true if filters in this configuration should be applied to the view
   */
  void setEnabled( boolean enabled );
}
