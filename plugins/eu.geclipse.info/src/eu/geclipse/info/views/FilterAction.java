/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Nikolaos Tsioutsias - initial API and implementation
 *****************************************************************************/

package eu.geclipse.info.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;

import eu.geclipse.info.model.InfoViewerFilter;


/**
 * @author tnikos
 *
 */
public class FilterAction extends Action {
  
  private InfoViewerFilter filter;
  private TreeViewer viewer;
  
  /**
   * @param filter an InfoViewerFilter
   * @param viewer the TreeViewer to filter
   */
  public FilterAction(final InfoViewerFilter filter,
                      final TreeViewer viewer)
  {
    this.filter = filter;
    this.viewer = viewer;
  }
  
  /**
   * Get the InfoViewerFilter
   * @return an InfoViewerFilter or null
   */
  public InfoViewerFilter getFilter()
  {
    return this.filter;
  }
  
  @Override
  public void run() {
    if (this.filter != null)
    {
      ViewerFilter[] myFilters = new ViewerFilter[1];
      myFilters[0] = this.filter;
      this.viewer.setFilters( myFilters );
      this.viewer.refresh();
    }
    else
    {
      ViewerFilter[] myFilters = new ViewerFilter[1];
      myFilters[0] = new InfoViewerFilter(); // Nothing is filtered
      this.viewer.setFilters( myFilters );
      this.viewer.refresh();
    }
  }
}
