package eu.geclipse.jsdl.ui.widgets;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ColumnLayoutData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.jsdl.JSDLModelFacade;
import eu.geclipse.jsdl.model.DataStagingType;
import eu.geclipse.jsdl.model.SourceTargetType;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.wizards.ExecutableNewJobWizardPage;
import eu.geclipse.ui.dialogs.GridFileDialog;

public class DataStageInTable {

  private Table table;
  TableViewer tableViewer;
  private Button addButton;
  private Button editButton;
  private Button removeButton;
  List<DataStagingType> input;
  private Composite mainComp;

  public DataStageInTable( final Composite parent,
                           final List<DataStagingType> input )
  {
    this.input = input;
    this.mainComp = new Composite( parent, SWT.NONE );
    this.mainComp.setLayout( new GridLayout( 1, false ) );
    this.table = new Table( this.mainComp, SWT.BORDER
                                           | SWT.VIRTUAL
                                           | SWT.MULTI
                                           | SWT.FULL_SELECTION );
    this.table.setHeaderVisible( true );
    this.table.setLinesVisible( true );
    GridData gData = new GridData( GridData.FILL_BOTH );
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
    nameColumn.setText( "Location" ); //$NON-NLS-1$
    TableColumn typeColumn = new TableColumn( this.table, SWT.LEFT );
    typeColumn.setText( "Name" ); //$NON-NLS-1$
    this.tableViewer = new TableViewer( this.table );
    IStructuredContentProvider contentProvider = new DataStageInContentProvider();
    this.tableViewer.setContentProvider( contentProvider );
    this.tableViewer.setLabelProvider( new DataStageInContentLabelProvider() );
    this.tableViewer.setInput( this.input );
    this.tableViewer.addSelectionChangedListener( new ISelectionChangedListener()
    {

      public void selectionChanged( final SelectionChangedEvent event ) {
        updateButtons();
      }
    } );
    Composite buttonsComp = new Composite( this.mainComp, SWT.NONE );
    buttonsComp.setLayout( new GridLayout( 3, true ) );
    this.addButton = new Button( buttonsComp, SWT.PUSH );
    gData = new GridData( GridData.VERTICAL_ALIGN_BEGINNING
                          | GridData.HORIZONTAL_ALIGN_END );
    buttonsComp.setLayoutData( gData );
    gData = new GridData( GridData.FILL_BOTH );
    this.addButton.setLayoutData( gData );
    this.addButton.setText( "Add..." );
    this.addButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e ) {
        editDataStagingEntry( null );
      }
    } );
    this.editButton = new Button( buttonsComp, SWT.PUSH );
    gData = new GridData( GridData.FILL_BOTH );
    this.editButton.setLayoutData( gData );
    this.editButton.setText( "Edit..." );
    this.editButton.addSelectionListener( new SelectionAdapter(){

      @Override
      public void widgetSelected( SelectionEvent e ) {
        editDataStagingEntry( getSelectedObject() );
      }
      
    });
    this.removeButton = new Button( buttonsComp, SWT.PUSH );
    gData = new GridData( GridData.FILL_BOTH );
    this.removeButton.setLayoutData( gData );
    this.removeButton.setText( "Remove" );
    this.removeButton.addSelectionListener( new SelectionAdapter(){

      @Override
      public void widgetSelected( SelectionEvent e ) {
        DataStageInTable.this.input.remove( getSelectedObject() );
        DataStageInTable.this.tableViewer.refresh();
      }
      
    });
    updateButtons();
  }

  DataStagingType getSelectedObject() {
    DataStagingType result = null;
    IStructuredSelection selection = ( IStructuredSelection )this.tableViewer.getSelection();
    Object obj = selection.getFirstElement();
    if ( obj instanceof DataStagingType ){
      result = ( DataStagingType )obj;
    }
    return result;
  }
  
  public List<DataStagingType> getDataStagingInType(){
    return this.input;
  }

  private void editDataStagingEntry( DataStagingType selectedObject ) {
    EditDialog dialog;
    if( selectedObject == null ) {
      dialog = new EditDialog( this.mainComp.getShell() );
      if( dialog.open() == Window.OK ) {
        this.input.add( getNewDataStagingType( dialog.getName(), dialog.getPath() ) );
        this.tableViewer.refresh();
      }
    } else {
      dialog = new EditDialog(this.mainComp.getShell(), selectedObject.getName(), selectedObject.getSource().getURI());
      if (dialog.open() == Window.OK){
        this.input.add( getNewDataStagingType( dialog.getName(), dialog.getPath() ) );
        this.input.remove( selectedObject );
        this.tableViewer.refresh();
      }
    }
  }

  private DataStagingType getNewDataStagingType( final String name,
                                                 final String path )
  {
    DataStagingType result = JSDLModelFacade.getDataStagingType();
    result.setName( name );
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

    public Object[] getElements( Object inputElement ) {
      DataStagingType[] elements = new DataStagingType[ 0 ];
      elements = ( DataStagingType[] )( ( List )inputElement ).toArray( new DataStagingType[ 0 ] );
      return elements;
    }

    public void dispose() {
      // TODO Auto-generated method stub
    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput )
    {
      // TODO Auto-generated method stub
    }
  }

  public void updateInput( List<DataStagingType> newInput ) {
    this.input = newInput;
    this.tableViewer.setInput( this.input );
  }
  class DataStageInContentLabelProvider extends LabelProvider
    implements ITableLabelProvider
  {

    public Image getColumnImage( Object element, int columnIndex ) {
      return null;
    }

    public String getColumnText( Object element, int columnIndex ) {
      String result = null;
      if( element != null ) {
        DataStagingType var = ( DataStagingType )element;
        switch( columnIndex ) {
          case 0: // source location
            result = var.getSource().getURI();
          break;
          case 1: // name
            result = var.getName();
          break;
        }
      }
      return result;
    }
  }
  class EditDialog extends Dialog {

    private String initName;
    private String initPath;

    protected EditDialog( Shell parentShell ) {
      super( parentShell );
    }
    
    protected EditDialog( Shell parentShell, String name, String path  ) {
      super( parentShell );
      this.initName = name;
      this.initPath = path;
    }
    Text pathText;
    private Text nameText;
    private String returnName;
    private String returnPath;

    @Override
    protected Control createDialogArea( Composite parent ) {
      Composite composite = ( Composite )super.createDialogArea( parent );
      composite.setLayout( new GridLayout( 1, false ) );
      composite.setLayoutData( new GridData( GridData.FILL_BOTH ) );
      Composite panel = new Composite( composite, SWT.NONE );
      this.initializeDialogUnits( composite );
      GridData gd;
      GridLayout gLayout = new GridLayout( 3, false );
      panel.setLayout( gLayout );
      gd = new GridData( GridData.FILL_HORIZONTAL );
      panel.setLayoutData( gd );
      Label pathLabel = new Label( panel, SWT.LEAD );
      pathLabel.setText( "Location" );
      gd = new GridData();
      pathLabel.setLayoutData( gd );
      pathText = new Text( panel, SWT.BORDER );
      if (this.initPath != null){
        this.pathText.setText( this.initPath );
      }
      Button browseButton = new Button( panel, SWT.PUSH );
      IPreferenceStore prefs = Activator.getDefault().getPreferenceStore();
      ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
      Image fileImage = sharedImages.getImage( ISharedImages.IMG_OBJ_FILE );
      browseButton.setImage( fileImage );
      browseButton.addSelectionListener( new SelectionAdapter() {

        @Override
        public void widgetSelected( SelectionEvent e ) {
          String filename = null;
          IGridConnectionElement connection = GridFileDialog.openFileDialog( PlatformUI.getWorkbench()
                                                                               .getActiveWorkbenchWindow()
                                                                               .getShell(),
                                                                             "Choose a file",
                                                                             null,
                                                                             true );
          if( connection != null ) {
            try {
              filename = connection.getConnectionFileStore().toString();
            } catch( CoreException e1 ) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
            }
            if( filename != null ) {
              EditDialog.this.pathText.setText( filename );
            }
          }
          updateButtons();
        }
      } );
      Label nameLabel = new Label( panel, SWT.LEAD );
      nameLabel.setText( "Name" );
      nameLabel.setLayoutData( new GridData() );
      this.nameText = new Text( panel, SWT.BORDER );
      gd = new GridData();
      gd.horizontalSpan = 2;
      gd.horizontalAlignment = SWT.FILL;
      this.nameText.setLayoutData( gd );
      if (this.initName != null){
        this.nameText.setText( this.initName );
      }
      ModifyListener listener = new UpdateAdapter();
      this.nameText.addModifyListener( listener );
      this.pathText.addModifyListener( listener );
      return composite;
    }

    public String getPath() {
      return this.returnPath;
    }

    public String getName() {
      return this.returnName;
    }

    protected void okPressed() {
      this.returnName = this.nameText.getText();
      this.returnPath = this.pathText.getText();
      super.okPressed();
    }

    void updateButtons() {
      if( !this.nameText.getText().equals( "" )
          && !this.pathText.getText().equals( "" ) )
      {
        super.getButton( IDialogConstants.OK_ID ).setEnabled( true );
      } else {
        super.getButton( IDialogConstants.OK_ID ).setEnabled( false );
      }
    }

    @Override
    protected void createButtonsForButtonBar( Composite parent ) {
      super.createButtonsForButtonBar( parent );
      updateButtons();
    }
    class UpdateAdapter implements ModifyListener {

      public void modifyText( ModifyEvent e ) {
        updateButtons();
      }
    }
  }
}
