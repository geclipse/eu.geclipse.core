package eu.geclipse.jsdl.ui.preference;

import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.preference.ApplicationSpecificObject;
import eu.geclipse.jsdl.ui.internal.preference.ApplicationSpecificRegistry;

public class ApplicationSpecificPreferencePage extends PreferencePage
  implements IWorkbenchPreferencePage, IContentChangeListener
{

  private Button addButton;
  private Button editButton;
  private Button removeButton;
  private TableViewer appsViewer;
  private Table appsTable;
  
  

  public ApplicationSpecificPreferencePage() {
    super();
    setDescription( Messages.getString("ApplicationSpecificPreferencePage.description") ); //$NON-NLS-1$
    ApplicationSpecificRegistry.getInstance().addContentChangeListener( this );
  }
  

  @Override
  public void dispose()
  {
    ApplicationSpecificRegistry.getInstance().removeContentChangeListener( this );
    super.dispose();
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
    appsTable = new Table( mainComp, SWT.BORDER | SWT.VIRTUAL
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
    this.addButton.setText( Messages.getString("ApplicationSpecificPreferencePage.add_button") ); //$NON-NLS-1$
    this.addButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e )
      {
        editAppliactionSpecificData( null );
//        ApplicationSpecificRegistry.getInstance().addApplicationSpecificData( name, path, xmlPath )
      }
    } );
    this.editButton = new Button( buttonsComp, SWT.PUSH );
    gData = new GridData( GridData.FILL_BOTH );
    this.editButton.setLayoutData( gData );
    this.editButton.setText( Messages.getString("ApplicationSpecificPreferencePage.edit_button") ); //$NON-NLS-1$
    this.editButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( SelectionEvent e )
      {
        editAppliactionSpecificData( getSelectedAppSpecificObject() );
      }
    } );
    this.removeButton = new Button( buttonsComp, SWT.PUSH );
    gData = new GridData( GridData.FILL_BOTH );
    this.removeButton.setLayoutData( gData );
    this.removeButton.setText( Messages.getString("ApplicationSpecificPreferencePage.delete_button") ); //$NON-NLS-1$
    this.removeButton.addSelectionListener( new SelectionAdapter(){
      @Override
      public void widgetSelected( SelectionEvent e ){
        ApplicationSpecificRegistry.getInstance().removeApplicationSpecificData(getSelectedAppSpecificObject());
      }
    });
    updateButtons();
    return mainComp;
  }

  void editAppliactionSpecificData( ApplicationSpecificObject aSO ) {
    EditDialog dialog;
    if (aSO == null){
      dialog = new EditDialog( getShell() );
      if (dialog.open() == Window.OK){
        ApplicationSpecificRegistry.getInstance().addApplicationSpecificData( dialog.getAppName(), dialog.getAppPath(), new Path(dialog.getXMLPath()) );
      }
    } else {
      dialog = new EditDialog( getShell(), aSO.getAppName(), aSO.getAppPath(), aSO.getXmlPath().toOSString() );
      if (dialog.open() == Window.OK){
        ApplicationSpecificRegistry.getInstance().editApplicationSpecificData( aSO, dialog.getAppName(), dialog.getAppPath(), dialog.getXMLPath() );
//        aSO.setAppName( dialog.getAppName() );
//        aSO.setAppPath( dialog.getAppPath() );
//        aSO.setXmlPath( new Path(dialog.getXMLPath()) );
//        this.appsViewer.refresh();
      }
    }
    
  }

  public ApplicationSpecificObject getSelectedAppSpecificObject() {
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
    
    private String appNameInit;
    private String appPathInit;
    private String xmlPathInit;
    private String returnAppName;
    private String returnXMLPath;
    private String returnAppPath;

    protected EditDialog( Shell parentShell ) {
      super( parentShell );
    }
    
    protected EditDialog( Shell parentShell, String appName, String appPath, String xmlPath ) {
      super( parentShell );
      this.appNameInit = appName;
      this.appPathInit = appPath;
      this.xmlPathInit = xmlPath;
    }
    
    
    @Override
    protected void createButtonsForButtonBar( Composite parent )
    {
      super.createButtonsForButtonBar( parent );
      updateButtons();
    }



    void updateButtons(){
      if (!this.appName.getText().equals( "" ) && !this.appPath.getText().equals( "" ) && ! this.xmlPath.getText().equals( "" )){ //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        super.getButton( IDialogConstants.OK_ID ).setEnabled( true );
      } else {
        super.getButton( IDialogConstants.OK_ID ).setEnabled( false );
      }
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
      appNameLabel.setText( Messages.getString("ApplicationSpecificPreferencePage.dialog_application_name_label") ); //$NON-NLS-1$
      gd = new GridData( );
      appNameLabel.setLayoutData( gd );
      
      this.appName = new Text(panel, SWT.BORDER);
      gd = new GridData( GridData.FILL_BOTH );
      gd.horizontalSpan = 2;
      this.appName.setLayoutData( gd );
      if (this.appNameInit != null){
        this.appName.setText( this.appNameInit );
      }
      
      Label pathNameLabel = new Label(panel, SWT.LEAD);
      pathNameLabel.setText( Messages.getString("ApplicationSpecificPreferencePage.dialog_application_path_label") ); //$NON-NLS-1$
      gd = new GridData();
      pathNameLabel.setLayoutData( new GridData() );
      
      this.appPath = new Text(panel, SWT.BORDER);
      gd = new GridData(GridData.FILL_BOTH );
      gd.widthHint = 250;
      gd.horizontalSpan = 2;
      this.appPath.setLayoutData( gd );
      if (this.appPathInit != null){
        this.appPath.setText( this.appPathInit );
      }
      
      Label xmlPathLabel = new Label(panel, SWT.LEAD);
      xmlPathLabel.setText( Messages.getString("ApplicationSpecificPreferencePage.dialog_xml_label") ); //$NON-NLS-1$
      gd = new GridData();
      xmlPathLabel.setLayoutData( new GridData() );
      
      this.xmlPath = new Text(panel, SWT.BORDER);
      gd = new GridData( GridData.FILL_HORIZONTAL );
      gd.grabExcessHorizontalSpace = true;
//      gd.horizontalSpan = 2;
      this.xmlPath.setLayoutData( gd );
      this.xmlPath.setLayoutData( gd );
      if (this.xmlPathInit != null){
        this.xmlPath.setText( this.xmlPathInit );
      }
      
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
          FileDialog file = new FileDialog(getShell());
          
          String connection = file.open(); 
          if( connection != null ) {
                EditDialog.this.xmlPath.setText( connection );
          }
          updateButtons();
        }   
      });
      
      ModifyListener selectionAdapter = new updateAdapter();
      this.appName.addModifyListener( selectionAdapter );
      this.appPath.addModifyListener( selectionAdapter );
      this.xmlPath.addModifyListener( selectionAdapter );
      
      return composite;
      
    }
    
    
    
    @Override
    protected void okPressed()
    {
      this.returnAppName = this.appName.getText();
      this.returnAppPath = this.appPath.getText();
      this.returnXMLPath = this.xmlPath.getText();
      super.okPressed();
    }

    public String getAppName(){
      return this.returnAppName;
    }
    
    public String getAppPath(){
      return this.returnAppPath;
    }
    
    public String getXMLPath(){
      return this.returnXMLPath;
    }
    
    class updateAdapter implements ModifyListener{
      

      public void modifyText( ModifyEvent e ) {
        updateButtons(); 
      }
    }
  }
  public void contentChanged( IContentChangeNotifier source ) {
    this.appsViewer.refresh();
  }
  
  
}
