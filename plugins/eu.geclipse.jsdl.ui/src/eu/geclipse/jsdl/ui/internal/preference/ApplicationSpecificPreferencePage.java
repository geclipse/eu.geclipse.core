package eu.geclipse.jsdl.ui.internal.preference;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
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
import org.eclipse.swt.SWT;
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
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.wizards.appSpecificRegistry.ApplicationSpecificObject;
import eu.geclipse.jsdl.ui.internal.wizards.appSpecificRegistry.ApplicationSpecificRegistry;
import eu.geclipse.ui.dialogs.GridFileDialog;
import eu.geclipse.ui.dialogs.NewProblemDialog;

public class ApplicationSpecificPreferencePage extends PreferencePage
  implements IWorkbenchPreferencePage
{

  private Button addButton;
  private Button editButton;
  private Button removeButton;
  private TableViewer appsViewer;

  public ApplicationSpecificPreferencePage() {
    super();
    setDescription( "Application specific parameters" );
  }

  @Override
  protected Control createContents( Composite parent )
  {
    initializeDialogUnits( parent );
    noDefaultAndApplyButton();
    Composite mainComp = new Composite( parent, SWT.NONE );
    GridLayout gLayout = new GridLayout( 1, false );
    gLayout.marginHeight = 0;
    gLayout.marginWidth = 0;
    mainComp.setLayout( gLayout );
    initializeDialogUnits( mainComp );
    GridData gData;
    Table appsTable = new Table( mainComp, SWT.BORDER
                                           | SWT.MULTI
                                           | SWT.FULL_SELECTION );
    appsTable.setHeaderVisible( true );
    appsTable.setLinesVisible( true );
    gData = new GridData( GridData.FILL_BOTH );
    gData.horizontalSpan = 1;
    gData.grabExcessVerticalSpace = true;
    gData.widthHint = 400;
    gData.heightHint = 100;
    appsTable.setLayoutData( gData );
    TableLayout tableLayout = new TableLayout();
    appsTable.setLayout( tableLayout );
    TableColumn nameColumn = new TableColumn( appsTable, SWT.CENTER );
    ColumnLayoutData data = new ColumnWeightData( 100 );
    tableLayout.addColumnData( data );
    data = new ColumnWeightData( 100 );
    tableLayout.addColumnData( data );
    data = new ColumnWeightData( 200 );
    tableLayout.addColumnData( data );
    nameColumn.setText( "Name" ); //$NON-NLS-1$
    TableColumn typeColumn = new TableColumn( appsTable, SWT.LEFT );
    typeColumn.setText( "Path" ); //$NON-NLS-1$
    TableColumn xmlColumn = new TableColumn( appsTable, SWT.LEFT );
    xmlColumn.setText( "XML file" ); //$NON-NLS-1$
    this.appsViewer = new TableViewer( appsTable );
    IStructuredContentProvider contentProvider = new ApplicationSpecificPageContentProvider();
    this.appsViewer.setContentProvider( contentProvider );
    this.appsViewer.setLabelProvider( new ApplicationSpecificLabelProvider() );
    this.appsViewer.setInput( contentProvider.getElements( null ) );
    this.appsViewer.addSelectionChangedListener( new ISelectionChangedListener()
    {

      public void selectionChanged( final SelectionChangedEvent event ) {
        updateButtons();
      }
    } );
    Composite buttonsComp = new Composite( mainComp, SWT.NONE );
    gLayout = new GridLayout( 3, false );
    gLayout.marginHeight = 0;
    gLayout.marginWidth = 0;
    buttonsComp.setLayout( gLayout );
    gData = new GridData( GridData.VERTICAL_ALIGN_BEGINNING
                          | GridData.HORIZONTAL_ALIGN_END );
    gData.horizontalSpan = 1;
    gData.grabExcessVerticalSpace = true;
    buttonsComp.setLayoutData( gData );
    this.addButton = new Button( buttonsComp, SWT.PUSH );
    gData = new GridData( GridData.FILL_BOTH );
    this.addButton.setLayoutData( gData );
    this.addButton.setText( "Add..." );
    this.addButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e )
      {
        editAppliactionSpecificData( null );
      }
    } );
    this.editButton = new Button( buttonsComp, SWT.PUSH );
    gData = new GridData( GridData.FILL_BOTH );
    this.editButton.setLayoutData( gData );
    this.editButton.setText( "Edit..." );
    this.removeButton = new Button( buttonsComp, SWT.PUSH );
    gData = new GridData( GridData.FILL_BOTH );
    this.removeButton.setLayoutData( gData );
    this.removeButton.setText( "Delete" );
    updateButtons();
    return mainComp;
  }

  void editAppliactionSpecificData( ApplicationSpecificObject asO ) {
    EditDialog dialog = new EditDialog( getShell() );
    dialog.open();
  }

  public ApplicationSpecificObject getSelectedVo() {
    ApplicationSpecificObject selectedASO = null;
    IStructuredSelection selection = ( IStructuredSelection )this.appsViewer.getSelection();
    Object obj = selection.getFirstElement();
    if( obj instanceof ApplicationSpecificObject ) {
      selectedASO = ( ApplicationSpecificObject )obj;
    }
    return selectedASO;
  }

  protected void updateButtons() {
    /*
     * java.util.List< IVoDescription > registeredVODescriptions =
     * Extensions.getRegisteredVODescriptions(); boolean managersAvailable = (
     * registeredVODescriptions != null ) && ( registeredVODescriptions.size() >
     * 0 );
     */
    ISelection selection = this.appsViewer.getSelection();
    boolean selectionAvailable = !selection.isEmpty();
    this.addButton.setEnabled( true ); // TODO mathias
    this.removeButton.setEnabled( selectionAvailable );
    this.editButton.setEnabled( selectionAvailable );
  }

  public void init( IWorkbench workbench ) {
    setPreferenceStore( Activator.getDefault().getPreferenceStore() );
  }

  @Override
  public void performApply()
  {
    MessageDialog.openConfirm( getShell(), "title", "message" );
    // return super.performOk();
  }
  class ApplicationSpecificPageContentProvider
    implements IStructuredContentProvider
  {

    public Object[] getElements( Object inputElement ) {
      ApplicationSpecificObject[] result = new ApplicationSpecificObject[ 0 ];
      result = ApplicationSpecificRegistry.getInstance()
        .getApplicationDataList()
        .toArray( result );
      return result;
    }

    public void dispose() {
      // TODO Auto-generated method stub
    }

    public void inputChanged( Viewer viewer, Object oldInput, Object newInput )
    {
      // TODO Auto-generated method stub
    }
  }
  class ApplicationSpecificLabelProvider extends LabelProvider
    implements ITableLabelProvider
  {

    public Image getColumnImage( Object element, int columnIndex ) {
      return null;
    }

    public String getColumnText( Object element, int columnIndex ) {
      String result = null;
      ApplicationSpecificObject asO = ( ApplicationSpecificObject )element;
      switch( columnIndex ) {
        case 0: // name
          result = asO.getAppName();
        break;
        case 1: // appPath
          result = asO.getAppPath();
        break;
        case 2: // XML path
          result = asO.getXmlPath().toOSString();
        break;
        default:
          result = ""; //$NON-NLS-1$
        break;
      }
      return result;
    }
  }
  class EditDialog extends Dialog {
    
    private Text appName;
    private Text appPath;
    private Text xmlPath;

    protected EditDialog( Shell parentShell ) {
      super( parentShell );
    }

    @Override
    protected Control createDialogArea( Composite parent) 
    {
      
      
      
      Composite composite = (Composite) super.createDialogArea( parent );
      composite.setLayout( new GridLayout( 1, false ) );
      composite.setLayoutData( new GridData( GridData.FILL_BOTH ) );
      Composite panel = new Composite (composite, SWT.NONE);
      
      this.initializeDialogUnits( composite );
      
      GridData gd;
      
      GridLayout gLayout = new GridLayout(3, false);
      panel.setLayout( gLayout );
      gd = new GridData( GridData.FILL_HORIZONTAL );
      panel.setLayoutData( gd );
      
      
      Label appNameLabel = new Label(panel, SWT.LEAD);
      appNameLabel.setText( "Application name" );
      gd = new GridData( );
      appNameLabel.setLayoutData( gd );
      
      this.appName = new Text(panel, SWT.BORDER);
      gd = new GridData( GridData.FILL_BOTH );
      gd.horizontalSpan = 2;
      this.appName.setLayoutData( gd );
      
      
      Label pathNameLabel = new Label(panel, SWT.LEAD);
      pathNameLabel.setText( "Application path" );
      gd = new GridData();
      pathNameLabel.setLayoutData( new GridData() );
      
      this.appPath = new Text(panel, SWT.BORDER);
      gd = new GridData(GridData.FILL_BOTH );
      gd.widthHint = 250;
      gd.horizontalSpan = 2;
      this.appPath.setLayoutData( gd );
      
      Label xmlPathLabel = new Label(panel, SWT.LEAD);
      xmlPathLabel.setText( "XML path" );
      gd = new GridData();
      xmlPathLabel.setLayoutData( new GridData() );
      
      this.xmlPath = new Text(panel, SWT.BORDER);
      gd = new GridData( GridData.FILL_HORIZONTAL );
      gd.grabExcessHorizontalSpace = true;
//      gd.horizontalSpan = 2;
      this.xmlPath.setLayoutData( gd );
      
      Button browseButton = new Button(panel, SWT.PUSH);
//      browseButton.setText( "aaa" );
      gd = new GridData();
      browseButton.setLayoutData( gd );
      ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
      Image fileImage = sharedImages.getImage( ISharedImages.IMG_OBJ_FILE );
      browseButton.setImage( fileImage );
      browseButton.addSelectionListener( new SelectionAdapter(){

        @Override
        public void widgetSelected( SelectionEvent e )
        {
          IGridConnectionElement connection = GridFileDialog.openFileDialog( getShell(),
                                                                             "Choose a file",
                                                                             null, true );
          if( connection != null ) {
            try {
              String filename = connection.getConnectionFileStore().toString();
              if( filename != null ) {
                xmlPath.setText( filename );
              }
            } catch( CoreException cExc ) {
              NewProblemDialog.openProblem( getShell(), "error", "error", cExc );
            }
          }
        }
        
        
        
      });
      
      return composite;
      
    }
  }
}
