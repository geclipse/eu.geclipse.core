/*****************************************************************************
 * Copyright (c) 2007 g-Eclipse Consortium 
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
 *    Ariel Garcia - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.listeners;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;


/**
 * This listener is used for sorting a tree according to the clicked column.
 * Clicking the same column again reverses the sorting order.
 */
public class TreeColumnListener implements SelectionListener {

  private TreeViewer treeViewer;
  private Tree tree;
  
  /**
   * Construct a <code>SelectionListener</code> for the given <code>TreeViewer</code>.
   * 
   * @param viewer The <code>TreeViewer</code> whose entries need to be sorted.
   */
  public TreeColumnListener( final TreeViewer viewer ) {
    this.treeViewer = viewer;
    this.tree = this.treeViewer.getTree();
  }
  
  public void widgetSelected( final SelectionEvent e ) {
    // This listener is only for the columns of a tree
    assert e.getSource() instanceof TreeColumn
      : "This listener should only be used for Trees"; //$NON-NLS-1$
    
    TreeColumn clickedColumn = (TreeColumn) e.getSource();
    TreeColumn oldSortingColumn = this.tree.getSortColumn();
    
    if ( clickedColumn == oldSortingColumn ) {
      this.tree.setSortDirection( this.tree.getSortDirection() == SWT.UP
                                     ? SWT.DOWN
                                     : SWT.UP );
    } else {
      this.tree.setSortColumn( clickedColumn );
      this.tree.setSortDirection( SWT.UP );
    }
    this.treeViewer.refresh();
  }

  public void widgetDefaultSelected( final SelectionEvent e ) {
    // Empty implementation
  }
}