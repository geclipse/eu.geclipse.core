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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.jsdl.JSDLModelFacade;
import eu.geclipse.jsdl.model.DataStagingType;
import eu.geclipse.jsdl.model.SourceTargetType;
import eu.geclipse.ui.dialogs.GridFileDialog;
import eu.geclipse.ui.dialogs.NewProblemDialog;

/**
 * Set of controls used to handle presentation of Data Staging In (see
 * {@link DataStagingType} details to user. Information is presented in a table
 * with two columns (name of a file and a source URI). User can add new entry to
 * this table and edit or remove existing ones.
 */
public class DataStageInTable {

  /**
   * This constant is used to inform the class' constructor that buttons (Add,
   * Edit and Remove button) should be placed on the right side of the table.
   */
  public static final int BUTTONS_RIGHT = 0;
  /**
   * This constant is used to inform the class' constructor that buttons (Add,
   * Edit and Remove button) should be placed below the table.
   */
  public static final int BUTTONS_BOTTOM = 1;
  TableViewer tableViewer;
  List<DataStagingType> input = new ArrayList<DataStagingType>();
  Composite mainComp;
  private Table table;
  private Button addButton;
  private Button editButton;
  private Button removeButton;

  /**
   * Creates new instance of table, table viewer and 3 buttons (add, edit and
   * remove)
   * 
   * @param parent control's parent
   * @param buttonsPosition 
   */
  public DataStageInTable( final Composite parent, final int buttonsPosition ) {
    this( parent, new ArrayList<DataStagingType>(), buttonsPosition );
  }

  /**
   * @param parent
   * @param input
   * @param buttonsPosition
   */
  public DataStageInTable( final Composite parent,
                           final List<DataStagingType> input,
                           final int buttonsPosition )
  {
    this.input = input;
    this.mainComp = new Composite( parent, SWT.NONE );
    GridLayout gLayout;
    if( buttonsPosition == DataStageInTable.BUTTONS_RIGHT ) {
      gLayout = new GridLayout( 2, false );
    } else {
      gLayout = new GridLayout( 1, false );
    }
    this.mainComp.setLayout( gLayout );
    GridData gData = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL);
    this.mainComp.setLayoutData( gData );
    this.table = new Table( this.mainComp, SWT.BORDER
                                           | SWT.VIRTUAL
                                           | SWT.MULTI
                                           | SWT.FULL_SELECTION );
    this.table.setHeaderVisible( true );
    this.table.setLinesVisible( true );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessVerticalSpace = true;
    gData.widthHint = 400;
    gData.heightHint = 100;
    this.table.setLayoutData( gData );
    TableLayout tableLayout = new TableLayout();
    this.table.setLayout( tableLayout );
    TableColumn nameColumn = new TableColumn( this.table, SWT.CENTER );
    ColumnLayoutData data = new ColumnWeightData( 100 );
    tableLayout.addColumnData( data );
    data = new ColumnWeightData( 100 );
    tableLayout.addColumnData( data );
    data = new ColumnWeightData( 150 );
    tableLayout.addColumnData( data );
    nameColumn.setText( Messages.getString( "DataStageInTable.location_field_label" ) ); //$NON-NLS-1$
    TableColumn typeColumn = new TableColumn( this.table, SWT.LEFT );
    typeColumn.setText( Messages.getString( "DataStageInTable.name_field_label" ) ); //$NON-NLS-1$
    this.tableViewer = new TableViewer( this.table );
    IStructuredContentProvider contentProvider = new DataStageInContentProvider();
    this.tableViewer.setContentProvider( contentProvider );
    this.tableViewer.setColumnProperties( new String[]{
      Messages.getString( "DataStageInTable.location_field_label" ), //$NON-NLS-1$
      Messages.getString( "DataStageInTable.name_field_label" ) //$NON-NLS-1$
    } );
    this.tableViewer.setLabelProvider( new DataStageInContentLabelProvider() );
    if( this.input == null ) {
      this.input = new ArrayList<DataStagingType>();
    }
    this.tableViewer.setInput( this.input );
    this.tableViewer.addSelectionChangedListener( new ISelectionChangedListener()
    {

      public void selectionChanged( final SelectionChangedEvent event ) {
        updateButtons();
      }
    } );
    Composite buttonsComp = new Composite( this.mainComp, SWT.NONE );
    if( buttonsPosition == DataStageInTable.BUTTONS_BOTTOM ) {
      gLayout = new GridLayout( 3, true );
    } else {
      gLayout = new GridLayout( 1, false );
    }
    buttonsComp.setLayout( gLayout );
    this.addButton = new Button( buttonsComp, SWT.PUSH );
    gData = new GridData( GridData.VERTICAL_ALIGN_BEGINNING
                          | GridData.HORIZONTAL_ALIGN_END );
    buttonsComp.setLayoutData( gData );
    gData = new GridData( GridData.FILL_BOTH );
    this.addButton.setLayoutData( gData );
    this.addButton.setText( Messages.getString( "DataStageInTable.add_button_label" ) ); //$NON-NLS-1$
    this.addButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        editDataStagingEntry( null );
      }
    } );
    this.editButton = new Button( buttonsComp, SWT.PUSH );
    gData = new GridData( GridData.FILL_BOTH );
    this.editButton.setLayoutData( gData );
    this.editButton.setText( Messages.getString( "DataStageInTable.edit_button_label" ) ); //$NON-NLS-1$
    this.editButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        editDataStagingEntry( getSelectedObject() );
      }
    } );
    this.removeButton = new Button( buttonsComp, SWT.PUSH );
    gData = new GridData( GridData.FILL_BOTH );
    this.removeButton.setLayoutData( gData );
    this.removeButton.setText( Messages.getString( "DataStageInTable.remove_button_label" ) ); //$NON-NLS-1$
    this.removeButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        DataStageInTable.this.input.remove( getSelectedObject() );
        DataStageInTable.this.tableViewer.refresh();
      }
    } );
    DialogCellEditor editor = new DialogCellEditor() {

      @Override
      protected Object openDialogBox( final Control cellEditorWindow ) {
        String filename = ( String )doGetValue();
        IGridConnectionElement connection = GridFileDialog.openFileDialog( DataStageInTable.this.mainComp.getShell(),
                                                                           Messages.getString( "DataStageInTable.grid_file_dialog_title" ), //$NON-NLS-1$
                                                                           null,
                                                                           true );
        if( connection != null ) {
          try {
            filename = connection.getConnectionFileStore().toString();
          } catch( CoreException cExc ) {
            NewProblemDialog.openProblem( DataStageInTable.this.mainComp.getShell(),
                                          Messages.getString( "DataStageInTable.error" ), //$NON-NLS-1$
                                          Messages.getString( "DataStageInTable.error" ), //$NON-NLS-1$
                                          cExc );
          }
        }
        return filename;
      }
    };
    CellEditor[] table1 = new CellEditor[ 1 ];
    table1[ 0 ] = editor;
    editor.create( this.table );
    this.tableViewer.setCellModifier( new ICellModifier() {

      public boolean canModify( final Object element, final String property ) {
        return true;
      }

      public Object getValue( final Object element, final String property ) {
        int columnIndex = -1;
        if( property.equals( Messages.getString( "DataStageInTable.location_field_label" ) ) ) { //$NON-NLS-1$
          columnIndex = 0;
        }
        if( property.equals( Messages.getString( "DataStageInTable.name_field_label" ) ) ) { //$NON-NLS-1$
          columnIndex = 1;
        }
        Object result = null;
        DataStagingType data1 = ( DataStagingType )element;
        switch( columnIndex ) {
          case 0:
            result = data1.getSource().getURI();
          break;
          case 1:
            result = data1.getFileName();
          break;
          default:
            result = ""; //$NON-NLS-1$
        }
        return result;
      }

      public void modify( final Object element,
                          final String property,
                          final Object value )
      {
        int columnIndex = -1;
        if( property.equals( Messages.getString( "DataStageInTable.location_field_label" ) ) ) { //$NON-NLS-1$
          columnIndex = 0;
        }
        if( property.equals( Messages.getString( "DataStageInTable.name_field_label" ) ) ) { //$NON-NLS-1$
          columnIndex = 1;
        }
        TableItem item = ( TableItem )element;
        DataStagingType dataOld = ( DataStagingType )item.getData();
        DataStagingType dataNew = null;
        switch( columnIndex ) {
          case 0:
            dataNew = getNewDataStagingType( dataOld.getFileName(),
                                             ( String )value );
            if( !dataNew.getFileName().equals( dataOld.getFileName() )
                || !dataNew.getSource().getURI().equals( dataOld.getSource()
                  .getURI() ) )
            {
              if( !isDataInInput( dataNew ) ) {
                // DataStageInTable.this.input.add( dataNew );
                // DataStageInTable.this.input.remove( dataOld );
                updateDataStaging( dataOld,
                                   dataNew.getFileName(),
                                   dataNew.getSource() );
                DataStageInTable.this.tableViewer.refresh();
              } else {
                MessageDialog.openError( DataStageInTable.this.mainComp.getShell(),
                                         Messages.getString( "DataStageInTable.edit_dialog_title" ), //$NON-NLS-1$
                                         Messages.getString( "DataStageInTable.value_exists_dialog_message" ) ); //$NON-NLS-1$
              }
            }
          break;
          case 1:
            dataNew = getNewDataStagingType( ( String )value,
                                             dataOld.getSource().getURI() );
            if( !dataNew.getFileName().equals( dataOld.getFileName() )
                || !dataNew.getSource().getURI().equals( dataOld.getSource()
                  .getURI() ) )
            {
              if( !isDataInInput( dataNew ) ) {
                // DataStageInTable.this.input.add( dataNew );
                // DataStageInTable.this.input.remove( dataOld );
                updateDataStaging( dataOld,
                                   dataNew.getFileName(),
                                   dataNew.getSource() );
                DataStageInTable.this.tableViewer.refresh();
              } else {
                MessageDialog.openError( DataStageInTable.this.mainComp.getShell(),
                                         Messages.getString( "DataStageInTable.edit_dialog_title" ), //$NON-NLS-1$
                                         Messages.getString( "DataStageInTable.value_exists_dialog_message" ) ); //$NON-NLS-1$
              }
            }
          break;
        }
      }
    } );
    CellEditor[] edTable = new CellEditor[]{
      editor, new TextCellEditor( this.tableViewer.getTable() )
    };
    this.tableViewer.setCellEditors( edTable );
    TableViewerEditor.create( this.tableViewer,
                              new ColumnViewerEditorActivationStrategy( this.tableViewer ),
                              ColumnViewerEditor.TABBING_HORIZONTAL
                                  | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
                                  | ColumnViewerEditor.TABBING_VERTICAL );
    updateButtons();
  }

  DataStagingType getSelectedObject() {
    DataStagingType result = null;
    IStructuredSelection selection = ( IStructuredSelection )this.tableViewer.getSelection();
    Object obj = selection.getFirstElement();
    if( obj instanceof DataStagingType ) {
      result = ( DataStagingType )obj;
    }
    return result;
  }

  /**
   * Returns data kept in table
   * 
   * @return list of {@link DataStagingType} objects
   */
  public List<DataStagingType> getDataStagingType() {
    return this.input;
  }

  /**
   * @param input
   */
  public void setInput( final List<DataStagingType> input ) {
    this.input = input;
    if( this.input == null ) {
      this.input = new ArrayList<DataStagingType>();
    }
    this.tableViewer.setInput( this.input );
  }

  void editDataStagingEntry( final DataStagingType selectedObject ) {
    DataStagingInDialog dialog;
    if( selectedObject == null ) {
      dialog = new DataStagingInDialog( this.mainComp.getShell(),
                                            DataStagingInDialog.SIMPLE_DIALOG );
      if( dialog.open() == Window.OK ) {
        DataStagingType newData = getNewDataStagingType( dialog.getName(),
                                                         dialog.getPath() );
        if( !isDataInInput( newData ) ) {
          this.input.add( newData );
          this.tableViewer.refresh();
        } else {
          MessageDialog.openError( this.mainComp.getShell(),
                                   Messages.getString( "DataStageInTable.value_exists_dialog_title" ), //$NON-NLS-1$
                                   Messages.getString( "DataStageInTable.value_exists_dialog_message" ) ); //$NON-NLS-1$
        }
      }
    } else {
      dialog = new DataStagingInDialog( this.mainComp.getShell(),
                                        DataStagingInDialog.SIMPLE_DIALOG,
                                        selectedObject.getFileName(),
                               selectedObject.getSource().getURI() );
      if( dialog.open() == Window.OK ) {
        DataStagingType newData = getNewDataStagingType( dialog.getName(),
                                                         dialog.getPath() );
        // DataStagingType newData = selectedObject;
        // newData.setFileName( dialog.getName() );
        // SourceTargetType source = JSDLModelFacade.getSourceTargetType();
        // source.setURI( dialog.getPath() );
        // newData.setSource( source );
        if( !newData.getFileName().equals( selectedObject.getFileName() )
            || !newData.getSource().getURI().equals( selectedObject.getSource()
              .getURI() ) )
        {
          if( !isDataInInput( newData ) ) {
            // this.input.add( getNewDataStagingType( dialog.getName(),
            // dialog.getPath() ) );
            // this.input.remove( selectedObject );
            // this.input.add( getNewDataStagingType( dialog.getName(),
            // dialog.getPath() ) );
            // this.input.remove( selectedObject );
            updateDataStaging( selectedObject,
                               newData.getFileName(),
                               newData.getSource() );
            this.tableViewer.refresh();
          } else {
            MessageDialog.openError( this.mainComp.getShell(),
                                     Messages.getString( "DataStageInTable.edit_dialog_title" ), //$NON-NLS-1$
                                     Messages.getString( "DataStageInTable.value_exists_dialog_message" ) ); //$NON-NLS-1$
          }
        }
      }
    }
  }

  void updateDataStaging( final DataStagingType selectedObject,
                          final String name,
                          final SourceTargetType source )
  {
    selectedObject.setFileName( name );
    selectedObject.getSource().setURI( source.getURI() );
  }

  boolean isDataInInput( final DataStagingType newData ) {
    boolean result = false;
    for( DataStagingType data : this.input ) {
      if( data.getFileName().equals( newData.getFileName() )
          && data.getSource().getURI().equals( newData.getSource().getURI() ) )
      {
        result = true;
      }
    }
    return result;
  }

  DataStagingType getNewDataStagingType( final String name, final String path )
  {
    DataStagingType result = JSDLModelFacade.getDataStagingType();
    result.setFileName( name );
    SourceTargetType source = JSDLModelFacade.getSourceTargetType();
    source.setURI( path );
    result.setSource( source );
    return result;
  }

  protected void updateButtons() {
    ISelection selection = this.tableViewer.getSelection();
    boolean selectionAvailable = !selection.isEmpty();
    this.addButton.setEnabled( true );
    this.removeButton.setEnabled( selectionAvailable );
    this.editButton.setEnabled( selectionAvailable );
  }
  class DataStageInContentProvider implements IStructuredContentProvider {

    @SuppressWarnings("unchecked")
    public Object[] getElements( final Object inputElement ) {
      DataStagingType[] elements = new DataStagingType[ 0 ];
      elements = ( DataStagingType[] )( ( List )inputElement ).toArray( new DataStagingType[ 0 ] );
      return elements;
    }

    public void dispose() {
      // do nothing
    }

    public void inputChanged( final Viewer viewer,
                              final Object oldInput,
                              final Object newInput )
    {
      // do nothing
    }
  }

  /**
   * Updates the table input
   * 
   * @param newInput new object that will be used as an input
   */
  public void updateInput( final List<DataStagingType> newInput ) {
    this.input = newInput;
    this.tableViewer.setInput( this.input );
  }
  class DataStageInContentLabelProvider extends LabelProvider
    implements ITableLabelProvider
  {

    public Image getColumnImage( final Object element, final int columnIndex ) {
      return null;
    }

    public String getColumnText( final Object element, final int columnIndex ) {
      String result = null;
      if( element != null ) {
        DataStagingType var = ( DataStagingType )element;
        switch( columnIndex ) {
          case 0: // source location
            result = var.getSource().getURI();
          break;
          case 1: // name
            result = var.getFileName();
          break;
        }
      }
      return result;
    }
  }

}
