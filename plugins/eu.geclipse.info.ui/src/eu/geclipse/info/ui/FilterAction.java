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

package eu.geclipse.info.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;

import eu.geclipse.info.ui.InfoViewerFilter;


/**
 * @author tnikos
 *
 */
public class FilterAction extends Action {
  
  private InfoViewerFilter filter;
  private ViewerFilter[] viewerFilter;
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
    this.viewerFilter = new ViewerFilter[2];
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
    
    // The viewerFilter that is used consists of 2 filters. One to show all or only filled info
    // elements and another to show all/glite/gria elements.
    
    if (this.filter != null)
    {
      this.viewerFilter[1] = this.filter;
    }
    else
    { 
      this.viewerFilter[1] = new InfoViewerFilter(); // Nothing is filtered
    }
    
      this.viewerFilter[0] = this.viewer.getFilters()[0];
    
    this.viewer.setFilters( this.viewerFilter );
    this.viewer.refresh();
  }
}
