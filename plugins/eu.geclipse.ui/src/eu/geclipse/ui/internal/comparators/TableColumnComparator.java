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

package eu.geclipse.ui.internal.comparators;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;


/**
 * This comparator is used to sort the table entries according to the selected
 * sorting column, with a second ordering column as fallback if entries compare
 * equal.
 */
public class TableColumnComparator extends ViewerComparator {

  private TableViewer tableViewer;
  private Table table;
  private ITableLabelProvider labelProvider;
  private TableColumn defaultSortColumn;
  
  /**
   * Construct a <code>ViewerComparator</code> for the given <code>TableViewer</code>
   * and with the given column as fallback sorting column.
   * 
   * @param viewer The <code>TableViewer</code> whose elements need to be compared.
   * @param defaultSortColumn The sorting column used as fallback if elements compare equal.
   */
  public TableColumnComparator( final TableViewer viewer, final TableColumn defaultSortColumn ) {
    super();
    this.tableViewer = viewer;
    this.table = this.tableViewer.getTable();
    this.labelProvider = (ITableLabelProvider) this.tableViewer.getLabelProvider();
    this.defaultSortColumn = defaultSortColumn;
  }

  @Override
  public int compare( final Viewer viewer, final Object element1, final Object element2 ) {
    
    int col;
    // Sort the table by the first column if there is no sorting defined
    if ( this.table.getSortColumn() == null ) {
      col = 0;
    } else {
      col = this.table.indexOf( this.table.getSortColumn() );
    }
    
    String value1 = this.labelProvider.getColumnText( element1, col );
    String value2 = this.labelProvider.getColumnText( element2, col );
    
    int order = ( this.table.getSortDirection() == SWT.DOWN )
                  ? SWT.DOWN
                  : SWT.UP;
    
    int result = ( order == SWT.UP )
                   ? value1.compareTo( value2 )
                   : value2.compareTo( value1 );
    
    // If the elements compare equal, sort by ascending value of the preselected column
    if ( result == 0 ) {
      int sCol = this.table.indexOf( this.defaultSortColumn );
      value1 = this.labelProvider.getColumnText( element1, sCol );
      value2 = this.labelProvider.getColumnText( element2, sCol );
      result = value1.compareTo( value2 );
    }
    
    return result;
  }
}
