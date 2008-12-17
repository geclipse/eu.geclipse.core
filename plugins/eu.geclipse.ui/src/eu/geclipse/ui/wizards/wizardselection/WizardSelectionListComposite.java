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

package eu.geclipse.ui.wizards.wizardselection;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

class WizardSelectionListComposite extends Composite {

  private Table table = null;
  private TableViewer tableViewer;
  private IWizardSelectionNode[] input;

  WizardSelectionListComposite( final Composite parent, final int style ) {
    super( parent, style );
    initialize();
    this.tableViewer = new TableViewer( this.table );
    this.tableViewer.setLabelProvider( new LabelProvider() {
      @Override
      public String getText( final Object element ) {
        String result;
        if ( element instanceof IWizardSelectionNode ) {
          result = ( ( IWizardSelectionNode ) element ).getName();
        } else {
          result = element.toString();
        }
        return result;
      }
      
      @Override
      public Image getImage( final Object element ) {
        Image result = null;
        if ( element instanceof IWizardSelectionNode ) {
          result = ( ( IWizardSelectionNode ) element ).getIcon();
        }
        return result;
      }
    } );
  }

  void addSelectionListener( final SelectionListener listener ) {
    this.table.addSelectionListener( listener );
  }

  void addSelectionChangedListener( final ISelectionChangedListener listener ) {
    this.tableViewer.addSelectionChangedListener( listener );
  }

  private void initialize() {
    GridData tableGridData = new GridData();
    tableGridData.horizontalAlignment = GridData.FILL;
    tableGridData.grabExcessHorizontalSpace = true;
    tableGridData.grabExcessVerticalSpace = true;
    tableGridData.verticalAlignment = GridData.FILL;
    this.table = new Table(this, SWT.BORDER);
    this.table.setLayoutData(tableGridData);
    setSize( new Point( 300, 200 ) );
    setLayout( new GridLayout() );
  }

  void fillWizardList( final IWizardSelectionNode[] wizardSelectionNodes ) {
    this.input = wizardSelectionNodes;
    this.tableViewer.add( wizardSelectionNodes );
  }
  
  
  
  void refreshList(final IWizardSelectionNode[] newContent){
    this.tableViewer.remove( this.input );
    fillWizardList( newContent );
  }
}
