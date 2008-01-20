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

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;


/**
 * This listener is used for sorting a table according to the clicked column.
 * Clicking the same column again reverses the sorting order.
 */
public class TableColumnListener implements SelectionListener {

  private TableViewer tableViewer;
  private Table table;
  
  /**
   * Construct a <code>SelectionListener</code> for the given <code>TableViewer</code>.
   * 
   * @param viewer The <code>TableViewer</code> whose entries need to be sorted.
   */
  public TableColumnListener( final TableViewer viewer ) {
    this.tableViewer = viewer;
    this.table = this.tableViewer.getTable();
  }
  
  public void widgetSelected( final SelectionEvent e ) {
    // This listener is only for the columns of a table
    assert e.getSource() instanceof TableColumn
      : "This listener should only be used for Tables"; //$NON-NLS-1$
    
    TableColumn clickedColumn = (TableColumn) e.getSource();
    TableColumn oldSortingColumn = this.table.getSortColumn();
    
    if ( clickedColumn == oldSortingColumn ) {
      this.table.setSortDirection( this.table.getSortDirection() == SWT.UP
                                     ? SWT.DOWN
                                     : SWT.UP );
    } else {
      this.table.setSortColumn( clickedColumn );
      this.table.setSortDirection( SWT.UP );
    }
    this.tableViewer.refresh();
  }

  public void widgetDefaultSelected( final SelectionEvent e ) {
    // Empty implementation
  }
}