/******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse consortium 
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
package eu.geclipse.ui.widgets;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * @author katis
 * @param <T> Type of items stored in the table
 */
public abstract class TabComponent<T> extends AbstractLaunchConfigurationTab {

  protected Button editButton;
  protected Button removeButton;
  protected Button addButton;
  protected ColumnLayoutData[] tabColumnsLayouts;
  protected TableViewer table;
  private IStructuredContentProvider contentProvider;
  private ITableLabelProvider labelProvider;
  private String[] tabColumnsHeaders;
  private String[] tabColumnsProperties;
  private int tabHeight;
  private List<Integer> columnsWidth = new ArrayList<Integer>();
  private ArrayList<CellEditor> cellEditors = new ArrayList<CellEditor>();
  private ICellModifier cellModifier;
  private int buttonsPosition;
  private Object input;

  public TabComponent( final IStructuredContentProvider contentProvider,
                       final ITableLabelProvider labelProvider,
                       final List<String> propertiesVsHearders,
                       final Object input,
                       final int hight,
                       final int width )
  {
    this( contentProvider,
          labelProvider,
          propertiesVsHearders,
          hight,
          width,
          SWT.LEFT );
    this.input = input;
  }

  /**
   * @param contentProvider
   * @param labelProvider
   * @param propertiesVsHearders
   */
  public TabComponent( final IStructuredContentProvider contentProvider,
                       final ITableLabelProvider labelProvider,
                       final List<String> propertiesVsHearders,
                       final int hight,
                       final int width )
  {
    this( contentProvider,
          labelProvider,
          propertiesVsHearders,
          hight,
          width,
          SWT.LEFT );
  }

  public TabComponent( final IStructuredContentProvider contentProvider,
                       final ITableLabelProvider labelProvider,
                       final List<String> propertiesVsHearders,
                       final int hight,
                       final int width,
                       final int buttonsPosition )
  {
    this.contentProvider = contentProvider;
    this.tabHeight = hight;
    this.labelProvider = labelProvider;
    this.tabColumnsHeaders = new String[ propertiesVsHearders.size() ];
    this.tabColumnsProperties = new String[ propertiesVsHearders.size() ];
    this.tabColumnsHeaders = propertiesVsHearders.toArray( this.tabColumnsHeaders );
    this.tabColumnsProperties = propertiesVsHearders.toArray( this.tabColumnsProperties );
    this.tabColumnsLayouts = new ColumnLayoutData[ this.tabColumnsHeaders.length ];
    for( int i = 0; i < this.tabColumnsHeaders.length; i++ ) {
      this.columnsWidth.add( Integer.valueOf( width ) );
      this.tabColumnsLayouts[ i ] = new ColumnWeightData( this.columnsWidth.get( i )
                                                            .intValue(),
                                                          this.columnsWidth.get( i )
                                                            .intValue(),
                                                          false );
    }
    this.buttonsPosition = buttonsPosition;
  }

  public TabComponent( final IStructuredContentProvider contentProvider,
                       final ITableLabelProvider labelProvider,
                       final List<String> propertiesVsHearders,
                       final Object input,
                       final int hight,
                       final List<Integer> columnsWidth,
                       final int buttonsPosition )
  {
    this.contentProvider = contentProvider;
    this.tabHeight = hight;
    this.columnsWidth = columnsWidth;
    this.labelProvider = labelProvider;
    this.tabColumnsHeaders = new String[ propertiesVsHearders.size() ];
    this.tabColumnsProperties = new String[ propertiesVsHearders.size() ];
    this.tabColumnsHeaders = propertiesVsHearders.toArray( this.tabColumnsHeaders );
    this.tabColumnsProperties = propertiesVsHearders.toArray( this.tabColumnsProperties );
    this.tabColumnsLayouts = new ColumnLayoutData[ this.tabColumnsHeaders.length ];
    for( int i = 0; i < this.tabColumnsHeaders.length; i++ ) {
      this.tabColumnsLayouts[ i ] = new ColumnWeightData( this.columnsWidth.get( i )
                                                            .intValue(),
                                                          this.columnsWidth.get( i )
                                                            .intValue(),
                                                          false );
    }
    this.buttonsPosition = buttonsPosition;
    this.input = input;
  }

  public TabComponent( final IStructuredContentProvider contentProvider,
                       final ITableLabelProvider labelProvider,
                       final List<String> propertiesVsHearders,
                       final int hight,
                       final List<Integer> columnsWidth,
                       final int buttonsPosition )
  {
    this.contentProvider = contentProvider;
    this.tabHeight = hight;
    this.columnsWidth = columnsWidth;
    this.labelProvider = labelProvider;
    this.tabColumnsHeaders = new String[ propertiesVsHearders.size() ];
    this.tabColumnsProperties = new String[ propertiesVsHearders.size() ];
    this.tabColumnsHeaders = propertiesVsHearders.toArray( this.tabColumnsHeaders );
    this.tabColumnsProperties = propertiesVsHearders.toArray( this.tabColumnsProperties );
    this.tabColumnsLayouts = new ColumnLayoutData[ this.tabColumnsHeaders.length ];
    for( int i = 0; i < this.tabColumnsHeaders.length; i++ ) {
      this.tabColumnsLayouts[ i ] = new ColumnWeightData( this.columnsWidth.get( i )
                                                            .intValue(),
                                                          this.columnsWidth.get( i )
                                                            .intValue(),
                                                          false );
    }
    this.buttonsPosition = buttonsPosition;
  }

  public void createControl( final Composite parent ) {
    Composite mainComposite1 = new Composite( parent, SWT.NONE );
    setControl( mainComposite1 );
    GridLayout layout = new GridLayout();
    if( this.buttonsPosition == SWT.BOTTOM || this.buttonsPosition == SWT.TOP )
    {
      layout.numColumns = 1;
    } else {
      layout.numColumns = 2;
    }
    GridData gridData = new GridData( GridData.FILL_HORIZONTAL );
    mainComposite1.setLayout( layout );
    mainComposite1.setLayoutData( gridData );
    mainComposite1.setFont( parent.getFont() );
    createTable( mainComposite1 );
    createTableButtons( mainComposite1 );
    setLabels();
    Dialog.applyDialogFont( mainComposite1 );
    addEditors();
    if( !this.cellEditors.isEmpty() ) {
      CellEditor[] editorsTable = new CellEditor[ this.cellEditors.size() ];
      int i = 0;
      for( CellEditor editor : this.cellEditors ) {
        editorsTable[ i ] = editor;
        i++;
      }
      this.table.setCellEditors( editorsTable );
      this.table.setCellModifier( this.cellModifier );
    }
  }

  protected void addEditors() {
    // do nothing - subclasses defines its behavior
  }

  protected void setCellModifier( final ICellModifier cellModifier ) {
    this.cellModifier = cellModifier;
  }

  protected void addComboEditor( final ComboBoxCellEditor editor,
                                 final String[] types,
                                 final boolean readOnly )
  {
    editor.create( this.table.getTable() );
    editor.setItems( types );
    if( readOnly ) {
      editor.setStyle( SWT.READ_ONLY );
    }
    this.cellEditors.add( editor );
  }

  public void addEditor( final CellEditor editor ) {
    editor.create( this.table.getTable() );
    this.cellEditors.add( editor );
  }

  private void createTable( final Composite parent ) {
    Font font = parent.getFont();
    // Create table composite
    Composite tableComposite = new Composite( parent, SWT.NONE );
    GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    layout.numColumns = 1;
    GridData gridData = new GridData( GridData.FILL_BOTH );
    gridData.heightHint = this.tabHeight;
    int width = 0;
    for( Integer value : this.columnsWidth ) {
      width = width + value.intValue();
    }
    // gridData.widthHint = ( this.tabColumnsHeaders.length * width ) + 30;
    tableComposite.setLayout( layout );
    tableComposite.setLayoutData( gridData );
    tableComposite.setFont( font );
    // Create label
    // Label label = new Label( tableComposite, SWT.NONE );
    // label.setFont( font );
    // label.setText(Messages.getString("OutputFilesTab.output_files_settings_label"));
    // //$NON-NLS-1$
    // Create table
    this.table = new TableViewer( tableComposite, SWT.BORDER
                                                  | SWT.H_SCROLL
                                                  | SWT.V_SCROLL
                                                  | SWT.MULTI
                                                  | SWT.FULL_SELECTION );
    Table localTable = this.table.getTable();
    TableLayout tableLayout = new TableLayout();
    localTable.setLayout( tableLayout );
    localTable.setHeaderVisible( true );
    localTable.setFont( font );
    gridData = new GridData( GridData.FILL_BOTH );
    this.table.getControl().setLayoutData( gridData );
    this.table.setContentProvider( this.contentProvider );
    this.table.setInput( this.input );
    this.table.setLabelProvider( this.labelProvider );
    this.table.setColumnProperties( this.tabColumnsProperties );
    this.table.addSelectionChangedListener( new ISelectionChangedListener() {

      public void selectionChanged( final SelectionChangedEvent event ) {
        handleTableSelectionChanged( event );
      }
    } );
    this.table.addDoubleClickListener( new IDoubleClickListener() {

      public void doubleClick( final DoubleClickEvent event ) {
        if( !TabComponent.this.table.getSelection().isEmpty() ) {
          handleEditButtonSelected();
        }
      }
    } );
    // Create columns
    for( int i = 0; i < this.tabColumnsHeaders.length; i++ ) {
      tableLayout.addColumnData( this.tabColumnsLayouts[ i ] );
      TableColumn tc = new TableColumn( localTable, SWT.NONE, i );
      tc.setResizable( this.tabColumnsLayouts[ i ].resizable );
      tc.setText( this.tabColumnsHeaders[ i ] );
    }
    this.table.refresh();
  }

  public String getName() {
    // TODO Auto-generated method stub
    return null;
  }

  public void initializeFrom( final ILaunchConfiguration configuration ) {
    // TODO Auto-generated method stub
  }

  public void performApply( final ILaunchConfigurationWorkingCopy configuration )
  {
    // TODO Auto-generated method stub
  }

  public void setDefaults( final ILaunchConfigurationWorkingCopy configuration )
  {
    // TODO Auto-generated method stub
  }

  /**
   * Responds to a selection changed event in the environment table
   * 
   * @param event the selection change event
   */
  protected void handleTableSelectionChanged( final SelectionChangedEvent event )
  {
    int size = ( ( IStructuredSelection )event.getSelection() ).size();
    this.editButton.setEnabled( size == 1 );
    this.removeButton.setEnabled( size > 0 );
  }

  /**
   * Creates the add/edit/remove buttons for the table
   * 
   * @param parent the composite in which the buttons should be created
   */
  protected void createTableButtons( final Composite parent ) {
    // Create button composite
    Composite buttonComposite = new Composite( parent, SWT.NONE );
    GridLayout glayout = new GridLayout();
    glayout.marginHeight = 0;
    glayout.marginWidth = 0;
    if( this.buttonsPosition == SWT.BOTTOM || this.buttonsPosition == SWT.TOP )
    {
      glayout.numColumns = 3;
    } else {
      glayout.numColumns = 1;
    }
    GridData gdata = new GridData( GridData.VERTICAL_ALIGN_BEGINNING
                                   | GridData.HORIZONTAL_ALIGN_END );
    buttonComposite.setLayout( glayout );
    buttonComposite.setLayoutData( gdata );
    buttonComposite.setFont( parent.getFont() );
    if( this.buttonsPosition == SWT.LEFT ) {
      createVerticalSpacer( buttonComposite, 1 );
    }
    // Create buttons
    gdata = new GridData( GridData.FILL_BOTH );
    this.addButton = new Button( buttonComposite, SWT.PUSH );
    this.addButton.setLayoutData( gdata );
    this.addButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        handleAddButtonSelected();
      }
    } );
    gdata = new GridData( GridData.FILL_BOTH );
    this.editButton = new Button( buttonComposite, SWT.PUSH );
    this.editButton.setLayoutData( gdata );
    this.editButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        handleEditButtonSelected();
      }
    } );
    this.editButton.setEnabled( false );
    gdata = new GridData( GridData.FILL_BOTH );
    this.removeButton = new Button( buttonComposite, SWT.PUSH );
    this.removeButton.setLayoutData( gdata );
    this.removeButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        handleRemoveButtonSelected();
      }
    } );
    this.removeButton.setEnabled( false );
  }

  @SuppressWarnings("unchecked")
  protected boolean addVariable( final T variable ) {
    boolean result = true;
    TableItem[] items = this.table.getTable().getItems();
    for( int i = 0; i < items.length; i++ ) {
      T existingVariable = ( T )items[ i ].getData();
      if( existingVariable.equals( variable ) ) {
        boolean overWrite = MessageDialog.openQuestion( getShell(),
                                                        Messages.getString( "TabComponent.replace_table_item_question_title" ), //$NON-NLS-1$
                                                        Messages.getString( "TabComponent.replace_table_item_question" ) ); //$NON-NLS-1$
        if( !overWrite ) {
          result = false;
        } else {
          this.table.remove( existingVariable );
        }
        break;
      }
    }
    if( result ) {
      this.table.add( variable );
      // this.table.setInput( variable );
      // updateLaunchConfigurationDialog();
    }
    return result;
  }

  /**
   * Method to access data kept in table
   * 
   * @return ArrayList of items kept in table
   */
  @SuppressWarnings("unchecked")
  public ArrayList<T> getInput() {
    ArrayList<T> result = new ArrayList<T>();
    for( TableItem item : this.table.getTable().getItems() ) {
      result.add( ( T )item.getData() );
    }
    return result;
  }

  abstract protected void handleRemoveButtonSelected();

  abstract protected void handleAddButtonSelected();

  abstract protected void handleEditButtonSelected();

  abstract protected void setLabels();
}
