/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.wizards.portforward;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import eu.geclipse.core.portforward.IForward;

class PortForwardingOptionsComposite extends Composite {
  Button removeButton = null;
  Table table = null;
  TableViewer tableViewer = null;
  private Button addButton = null;
  PortForwardingOptionsComposite( final Composite parent, final int style ) {
    super( parent, style );
    initialize();
    this.tableViewer.setLabelProvider( new PortForwardingTableLabelProvider() );
  }

  private void initialize() {
    GridData removeButtonGridData = new GridData();
    removeButtonGridData.horizontalAlignment = GridData.FILL;
    removeButtonGridData.verticalAlignment = GridData.CENTER;
    GridData addButtonGridData = new GridData();
    addButtonGridData.horizontalAlignment = GridData.FILL;
    addButtonGridData.verticalAlignment = GridData.CENTER;
    GridData tableGridData = new GridData();
    tableGridData.verticalSpan = 3;
    tableGridData.grabExcessVerticalSpace = true;
    tableGridData.horizontalAlignment = GridData.FILL;
    tableGridData.verticalAlignment = GridData.FILL;
    tableGridData.grabExcessHorizontalSpace = true;
    GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = 2;
    this.table = new Table(this, SWT.MULTI | SWT.BORDER);
    this.table.setHeaderVisible(true);
    this.table.setLayoutData(tableGridData);
    this.table.setLinesVisible(true);
    this.tableViewer = new TableViewer(this.table);
    this.table.addSelectionListener( new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent event ) {
        updateRemoveButtonStatus();
      }
    } );
    TableColumn typeColumn = new TableColumn(this.table, SWT.NONE);
    typeColumn.setWidth(100);
    typeColumn.setText(Messages.getString("PortForwardingOptionsComposite.type")); //$NON-NLS-1$
    TableColumn bindPortColumn = new TableColumn(this.table, SWT.NONE);
    bindPortColumn.setWidth(100);
    bindPortColumn.setText(Messages.getString("PortForwardingOptionsComposite.bindPort")); //$NON-NLS-1$
    TableColumn hostColumn = new TableColumn(this.table, SWT.NONE);
    hostColumn.setWidth(150);
    hostColumn.setText(Messages.getString("PortForwardingOptionsComposite.hostname")); //$NON-NLS-1$
    TableColumn portColumn = new TableColumn(this.table, SWT.NONE);
    portColumn.setWidth(100);
    portColumn.setText(Messages.getString("PortForwardingOptionsComposite.port")); //$NON-NLS-1$
    this.addButton = new Button(this, SWT.NONE);
    this.addButton.setText(Messages.getString("PortForwardingOptionsComposite.add")); //$NON-NLS-1$
    this.addButton.setLayoutData(addButtonGridData);
    this.addButton.addSelectionListener( new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent event ) {
        NewPortForwardDialog dialog = new NewPortForwardDialog( PortForwardingOptionsComposite.this.getShell() );
        if ( dialog.open() == Window.OK ) {
          IForwardTableEntry forward = dialog.getForward();
          PortForwardingOptionsComposite.this.tableViewer.insert( forward, -1 );
        }
      }
    } );
    this.removeButton = new Button(this, SWT.NONE);
    this.removeButton.setText(Messages.getString("PortForwardingOptionsComposite.remove")); //$NON-NLS-1$
    this.removeButton.setEnabled(false);
    this.removeButton.setLayoutData(removeButtonGridData);
    this.removeButton.addSelectionListener( new org.eclipse.swt.events.SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent event ) {
        PortForwardingOptionsComposite.this.tableViewer.remove(
            PortForwardingOptionsComposite.this.tableViewer.getElementAt( 
            PortForwardingOptionsComposite.this.table.getSelectionIndices()[0] ) );
        updateRemoveButtonStatus();
      }
    } );
    this.setLayout(gridLayout);
    setSize(new Point(552, 296));
  }

  List<IForward> getForwards() {
    List<IForward> list = new LinkedList<IForward>();
    for( int i = 0; i < this.table.getItemCount(); i++ ) {
      list.add( (IForward) this.table.getItem( i ).getData() );
    }
    return list;
  }

  void updateRemoveButtonStatus() {
    boolean enabled = this.table.getSelectionCount() > 0;
    if ( enabled ) {
      IForwardTableEntry entry;
      entry = (IForwardTableEntry) this.table.getItem( this.table.getSelectionIndex() ).getData();
      enabled = entry.isRemoveable();
    }
    this.removeButton.setEnabled( enabled );
  }
}  //  @jve:decl-index=0:visual-constraint="10,10"
