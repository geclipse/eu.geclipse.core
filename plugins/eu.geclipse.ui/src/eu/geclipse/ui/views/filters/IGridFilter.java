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

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IMemento;
import eu.geclipse.ui.dialogs.ConfigureFiltersDialog;


/**
 * Filters used in g-eclipse views
 */
public interface IGridFilter {
  
  /**
   * Usefull when in given filter configuration user don't want to use all filters
   * (e.g. user want to filter jobs using status, but he haven't specified submission date)
   * @return true if this filter should be used to filter views
   */
  boolean isEnabled();
  
  
  /**
   * @param filterMemento memento, in which filter state should be stored
   */
  void saveState( final IMemento filterMemento );
  
  
  /**
   * @param filterMemento memento, from which filter state will be restored
   */
  void readState( final IMemento filterMemento );
  
  
  /**
   * @return {@link ViewerFilter} implementation used by eclipse {@link StructuredViewer}
   */
  ViewerFilter getFilter();
  
  
  /**
   * Cloning is used in {@link ConfigureFiltersDialog}, so it's important that every {@link IGridFilter} implement clone 
   * @return cloned filter
   * @throws CloneNotSupportedException
   */
  IGridFilter makeClone() throws CloneNotSupportedException;
  
  
  /**
   * @return filter id used to find filter in memento. Every {@link IGridFilter}
   *         class should has unique id. Objects of the same class should have
   *         the same id.
   */
  String getFilterId();
}
