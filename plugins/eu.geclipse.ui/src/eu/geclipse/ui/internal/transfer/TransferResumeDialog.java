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
 *    Szymon Mueller - initial API and implementation
 *****************************************************************************/
package eu.geclipse.ui.internal.transfer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import eu.geclipse.core.filesystem.GEclipseURI;
import eu.geclipse.core.filesystem.TransferOperation;

/**
 * Dialog for transfer resuming.
 *
 */
public class TransferResumeDialog extends Dialog {

  List<TransferOperation> operationsToResume = new ArrayList<TransferOperation>();
  
  private List<TransferOperation> operations;
  private CheckboxTableViewer tableViewer;

  protected TransferResumeDialog( final Shell parentShell,
                                  final List<TransferOperation> operations )
  {
    super( parentShell );
    setShellStyle( SWT.TITLE
//                   | SWT.CLOSE
                   | SWT.BORDER
                   | SWT.RESIZE
                   | SWT.MIN
                   | SWT.MAX
                   | SWT.YES );
    this.operations = operations;
  }

  @Override
  protected void configureShell( final Shell newShell ) {
    super.configureShell( newShell );
    newShell.setText( "Transfer resuming" );
  }

  @Override
  protected Control createDialogArea( final Composite parent ) {
    Composite composite = ( Composite )super.createDialogArea( parent );
    composite.setLayout( new GridLayout( 1, false ) );
    composite.setLayoutData( new GridData( GridData.FILL_BOTH ) );
    // Composite panel = new Composite( composite, SWT.NONE );
    // this.initializeDialogUnits( composite );
    Group group = new Group( composite, SWT.SHADOW_OUT );
    group.setLayout( new GridLayout( 1, false ) );
    group.setText( "Pending transfers" );
    GridData gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.horizontalSpan = 2;
    gData.grabExcessHorizontalSpace = true;
    gData.heightHint = 150;
    gData.verticalIndent = 20;
    group.setLayoutData( gData );
    int style = SWT.BORDER
                | SWT.H_SCROLL
                | SWT.V_SCROLL
                | SWT.SINGLE
                | SWT.CHECK;
    Table table = new Table( group, style );
    gData = new GridData( SWT.FILL, SWT.FILL, true, false );
    table.setLayoutData( gData );
    table.setHeaderVisible( true );
    TableColumn checkCol = new TableColumn( table, SWT.CENTER );
    checkCol.setWidth( 20 );
    TableColumn nameCol = new TableColumn( table, SWT.CENTER );
    nameCol.setText( "Name" );
    nameCol.setWidth( 80 );
    TableColumn sizeCol = new TableColumn( table, SWT.LEFT );
    sizeCol.setText( "Size" );
    sizeCol.setWidth( 50 );
    TableColumn fromCol = new TableColumn( table, SWT.LEFT );
    fromCol.setText( "From" );
    fromCol.setWidth( 350 );
    TableColumn toCol = new TableColumn( table, SWT.LEFT );
    toCol.setText( "To" );
    toCol.setWidth( 350 );
    this.tableViewer = new CheckboxTableViewer( table );
    this.tableViewer.setContentProvider( new TransferContentProvider() );
    this.tableViewer.setLabelProvider( new TransferLabelProvider() );
    if( this.operations != null && this.operations.size() > 0 ) {
      this.tableViewer.setInput( this.operations );
      for( TransferOperation op: this.operations ) {
        this.operationsToResume.add( op );
      }
    } else {
      System.out.println( "null operations" );
    }
    this.tableViewer.setAllChecked( true );
    this.tableViewer.addCheckStateListener( new ICheckStateListener() {

      public void checkStateChanged( final CheckStateChangedEvent event ) {
        if( event.getChecked() ) {
          Object obj = event.getElement();
          if( obj instanceof TransferOperation ) {
            TransferResumeDialog.this.operationsToResume.add( ( TransferOperation )obj );
          }
        } else {
          Object obj = event.getElement();
          if( obj instanceof TransferOperation ) {
            TransferResumeDialog.this.operationsToResume.remove( obj );
          }
        }
      }
    } );
    Button checkbox = new Button( composite, SWT.CHECK );
    checkbox.setText( "Disable transfer resuming (this can be changed in preferences)" );
    return composite;
  }

  public List<TransferOperation> getOperationsToResume() {
    return this.operationsToResume;
  }
  class TransferContentProvider implements IStructuredContentProvider {

    public Object[] getElements( final Object inputElement ) {
      List<TransferOperation> result = new ArrayList<TransferOperation>();
      if( inputElement instanceof List ) {
        for( Object obj : ( List )inputElement ) {
          if( obj instanceof TransferOperation ) {
            result.add( ( TransferOperation )obj );
          }
        }
      }
      return result.toArray();
    }

    public void dispose() {
      // TODO Auto-generated method stub
    }

    public void inputChanged( final Viewer viewer,
                              final Object oldInput,
                              final Object newInput )
    {
      // TODO Auto-generated method stub
    }
  }
  class TransferLabelProvider implements ITableLabelProvider {

    public Image getColumnImage( final Object element, final int columnIndex ) {
      // TODO Auto-generated method stub
      return null;
    }

    public String getColumnText( final Object element, final int columnIndex ) {
      String columnText = ""; //$NON-NLS-1$
      if( element instanceof TransferOperation ) {
        TransferOperation operation = ( TransferOperation )element;
        String source = "";
        String name = "";
        String destination = "";
        String length = "";
        IFileStore sourceStore = operation.getSource();
        GEclipseURI sourceURI = new GEclipseURI( sourceStore.toURI() );
        source = sourceURI.toSlaveURI().toString();
          
        Path path = new Path( sourceStore.toURI().getPath() );
        name = path.lastSegment();
        
        GEclipseURI destinationURI = new GEclipseURI( operation.getDestination()
          .toURI() );
        destination = destinationURI.toSlaveURI().toString();

        // TODO better size handling
        long size = operation.getSize();
        if( size / ( 1024 * 1024 * 1024 ) > 1 ) {
          length = String.valueOf( size / ( 1024 * 1024 * 1024 ) ) + " GB";
        } else if( size / ( 1024 * 1024 ) > 1 ) {
          length = String.valueOf( size / ( 1024 * 1024 ) ) + " MB";
        } else if( size / 1024 > 1 ) {
          length = String.valueOf( size / 1024 ) + " KB";
        }
        // length = String.valueOf( operation.getSize() );
        switch( columnIndex ) {
          case 1:
            columnText = name;
          break;
          case 2:
            columnText = length;
          break;
          case 3:
            columnText = source;
          break;
          case 4:
            columnText = destination;
          break;
          default:
          break;
        }
      }
      return columnText;
    }

    public void addListener( final ILabelProviderListener listener ) {
      // TODO Auto-generated method stub
    }

    public void dispose() {
      // TODO Auto-generated method stub
    }

    public boolean isLabelProperty( final Object element, final String property )
    {
      // TODO Auto-generated method stub
      return false;
    }

    public void removeListener( final ILabelProviderListener listener ) {
      // TODO Auto-generated method stub
    }
  }
}
