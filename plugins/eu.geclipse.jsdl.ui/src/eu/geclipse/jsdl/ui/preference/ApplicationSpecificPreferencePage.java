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
 *     PSNC - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.preference;

import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
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
import org.eclipse.swt.widgets.Display;
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

/**
 * Class providing contents of Application Parameters Preferences Page
 */
public class ApplicationSpecificPreferencePage extends PreferencePage
  implements IWorkbenchPreferencePage, IContentChangeListener
{

  private Button addButton;
  private Button editButton;
  private Button removeButton;
  TableViewer appsViewer;
  private Table appsTable;

  /**
   * Creates new instance of preferences page
   */
  public ApplicationSpecificPreferencePage() {
    super();
    setDescription( Messages.getString( "ApplicationSpecificPreferencePage.description" ) ); //$NON-NLS-1$
    ApplicationSpecificRegistry.getInstance().addContentChangeListener( this );
  }

  @Override
  public void dispose() {
    ApplicationSpecificRegistry.getInstance()
      .removeContentChangeListener( this );
    super.dispose();
  }

  @Override
  protected Control createContents( final Composite parent ) {
    initializeDialogUnits( parent );
    noDefaultAndApplyButton();
    Composite mainComp = new Composite( parent, SWT.NONE );
    GridLayout gLayout = new GridLayout( 1, false );
    gLayout.marginHeight = 0;
    gLayout.marginWidth = 0;
    GridData gData;
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    mainComp.setLayoutData( gData );
    mainComp.setLayout( gLayout );
    initializeDialogUnits( mainComp );
    this.appsTable = new Table( mainComp, SWT.BORDER
                                          | SWT.VIRTUAL
                                          | SWT.MULTI
                                          | SWT.FULL_SELECTION );
    this.appsTable.setHeaderVisible( true );
    this.appsTable.setLinesVisible( true );
    gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    this.appsTable.setLayoutData( gData );
    TableLayout tableLayout = new TableLayout();
    this.appsTable.setLayout( tableLayout );
    TableColumn nameColumn = new TableColumn( this.appsTable, SWT.CENTER );
    ColumnLayoutData data = new ColumnWeightData( 100 );
    tableLayout.addColumnData( data );
    data = new ColumnWeightData( 100 );
    tableLayout.addColumnData( data );
    data = new ColumnWeightData( 150 );
    tableLayout.addColumnData( data );
    data = new ColumnWeightData( 150 );
    tableLayout.addColumnData( data );
    nameColumn.setText( "Name" ); //$NON-NLS-1$
    TableColumn typeColumn = new TableColumn( this.appsTable, SWT.LEFT );
    typeColumn.setText( "Path" ); //$NON-NLS-1$
    TableColumn xmlColumn = new TableColumn( this.appsTable, SWT.LEFT );
    xmlColumn.setText( "XML file" ); //$NON-NLS-1$
    TableColumn jsdlColumn = new TableColumn( this.appsTable, SWT.LEFT );
    jsdlColumn.setText( "JSDL file" ); //$NON-NLS-1$
    this.appsViewer = new TableViewer( this.appsTable );
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
    buttonsComp.setLayoutData( gData );
    this.addButton = new Button( buttonsComp, SWT.PUSH );
    gData = new GridData( GridData.FILL_BOTH );
    this.addButton.setLayoutData( gData );
    this.addButton.setText( Messages.getString( "ApplicationSpecificPreferencePage.add_button" ) ); //$NON-NLS-1$
    this.addButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        editAppliactionSpecificData( null );
      }
    } );
    this.editButton = new Button( buttonsComp, SWT.PUSH );
    gData = new GridData( GridData.FILL_BOTH );
    this.editButton.setLayoutData( gData );
    this.editButton.setText( Messages.getString( "ApplicationSpecificPreferencePage.edit_button" ) ); //$NON-NLS-1$
    this.editButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        editAppliactionSpecificData( getSelectedAppSpecificObject() );
      }
    } );
    this.removeButton = new Button( buttonsComp, SWT.PUSH );
    gData = new GridData( GridData.FILL_BOTH );
    this.removeButton.setLayoutData( gData );
    this.removeButton.setText( Messages.getString( "ApplicationSpecificPreferencePage.delete_button" ) ); //$NON-NLS-1$
    this.removeButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent event ) {
        ApplicationSpecificRegistry.getInstance()
          .removeApplicationSpecificData( getSelectedAppSpecificObject() );
      }
    } );
    updateButtons();
    return mainComp;
  }

  void editAppliactionSpecificData( final ApplicationSpecificObject aSO ) {
    EditDialog dialog;
    if( aSO == null ) {
      dialog = new EditDialog( getShell() );
      if( dialog.open() == Window.OK ) {
        ApplicationSpecificRegistry.getInstance()
          .addApplicationSpecificData( dialog.getAppName(),
                                       dialog.getAppPath(),
                                       new Path( dialog.getXMLPath() ),
                                       new Path( dialog.getJSDLPath() ) );
      }
    } else {
      String jsdlPath = null;
      if( aSO.getJsdlPath() != null ) {
        jsdlPath = aSO.getJsdlPath().toOSString();
      }
      dialog = new EditDialog( getShell(),
                               aSO.getAppName(),
                               aSO.getAppPath(),
                               aSO.getXmlPath().toOSString(),
                               jsdlPath );
      if( dialog.open() == Window.OK ) {
        ApplicationSpecificRegistry.getInstance()
          .editApplicationSpecificData( aSO,
                                        dialog.getAppName(),
                                        dialog.getAppPath(),
                                        dialog.getXMLPath(),
                                        dialog.getJSDLPath() );
      }
    }
  }

  /**
   * Returns {@link ApplicationSpecificObject} corresponding to entry selected
   * in table.
   * 
   * @return ApplicationSpecificObject selected in table
   */
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
    ISelection selection = this.appsViewer.getSelection();
    boolean selectionAvailable = !selection.isEmpty();
    this.addButton.setEnabled( true ); // TODO mathias
    this.removeButton.setEnabled( selectionAvailable );
    this.editButton.setEnabled( selectionAvailable );
  }

  public void init( final IWorkbench workbench ) {
    setPreferenceStore( Activator.getDefault().getPreferenceStore() );
  }
  class ApplicationSpecificPageContentProvider
    implements IStructuredContentProvider
  {

    public Object[] getElements( final Object inputElement ) {
      ApplicationSpecificObject[] result = new ApplicationSpecificObject[ 0 ];
      result = ApplicationSpecificRegistry.getInstance()
        .getApplicationDataList()
        .toArray( result );
      return result;
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
  class ApplicationSpecificLabelProvider extends LabelProvider
    implements ITableLabelProvider
  {

    public Image getColumnImage( final Object element, final int columnIndex ) {
      return null;
    }

    public String getColumnText( final Object element, final int columnIndex ) {
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
        case 3: // JSDL path
          if( asO.getJsdlPath() != null ) {
            result = asO.getJsdlPath().toOSString();
          }
        break;
        default:
          result = ""; //$NON-NLS-1$
        break;
      }
      return result;
    }
  }
  class EditDialog extends Dialog {

    Text xmlPath;
    Text jsdlPath;
    private Text appName;
    private Text appPath;
    private String appNameInit;
    private String appPathInit;
    private String xmlPathInit;
    private String jsdlPathInit;
    private String returnAppName;
    private String returnXMLPath;
    private String returnAppPath;
    private String returnJSDLPath;

    protected EditDialog( final Shell parentShell ) {
      super( parentShell );
    }

    protected EditDialog( final Shell parentShell,
                          final String appName,
                          final String appPath,
                          final String xmlPath,
                          final String jsdlPath )
    {
      super( parentShell );
      this.appNameInit = appName;
      this.appPathInit = appPath;
      this.xmlPathInit = xmlPath;
      this.jsdlPathInit = jsdlPath;
    }

    @Override
    protected void createButtonsForButtonBar( final Composite parent ) {
      super.createButtonsForButtonBar( parent );
      updateButtons();
    }

    void updateButtons() {
      if( !this.appName.getText().equals( "" ) && /*!this.appPath.getText().equals( "" ) &&*/ !this.xmlPath.getText().equals( "" ) ) { //$NON-NLS-1$ //$NON-NLS-2$
        super.getButton( IDialogConstants.OK_ID ).setEnabled( true );
      } else {
        super.getButton( IDialogConstants.OK_ID ).setEnabled( false );
      }
    }

    @Override
    protected Control createDialogArea( final Composite parent ) {
      getShell().setText( Messages.getString("ApplicationSpecificPreferencePage.editDialogTitle") ); //$NON-NLS-1$
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
      Label appNameLabel = new Label( panel, SWT.LEAD );
      appNameLabel.setText( Messages.getString( "ApplicationSpecificPreferencePage.dialog_application_name_label" ) ); //$NON-NLS-1$
      gd = new GridData();
      appNameLabel.setLayoutData( gd );
      this.appName = new Text( panel, SWT.BORDER );
      gd = new GridData( GridData.FILL_BOTH );
      gd.horizontalSpan = 2;
      this.appName.setLayoutData( gd );
      if( this.appNameInit != null ) {
        this.appName.setText( this.appNameInit );
      }
      Label pathNameLabel = new Label( panel, SWT.LEAD );
      pathNameLabel.setText( Messages.getString( "ApplicationSpecificPreferencePage.dialog_application_path_label" ) ); //$NON-NLS-1$
      gd = new GridData();
      pathNameLabel.setLayoutData( new GridData() );
      this.appPath = new Text( panel, SWT.BORDER );
      gd = new GridData( GridData.FILL_BOTH );
      gd.widthHint = 250;
      gd.horizontalSpan = 2;
      this.appPath.setLayoutData( gd );
      if( this.appPathInit != null ) {
        this.appPath.setText( this.appPathInit );
      }
      Label xmlPathLabel = new Label( panel, SWT.LEAD );
      xmlPathLabel.setText( Messages.getString( "ApplicationSpecificPreferencePage.dialog_xml_label" ) ); //$NON-NLS-1$
      gd = new GridData();
      xmlPathLabel.setLayoutData( new GridData() );
      this.xmlPath = new Text( panel, SWT.BORDER );
      gd = new GridData( GridData.FILL_HORIZONTAL );
      gd.grabExcessHorizontalSpace = true;
      // gd.horizontalSpan = 2;
      this.xmlPath.setLayoutData( gd );
      this.xmlPath.setLayoutData( gd );
      if( this.xmlPathInit != null ) {
        this.xmlPath.setText( this.xmlPathInit );
      }
      Button browseButton = new Button( panel, SWT.PUSH );
      gd = new GridData();
      browseButton.setLayoutData( gd );
      ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
      Image fileImage = sharedImages.getImage( ISharedImages.IMG_OBJ_FILE );
      browseButton.setImage( fileImage );
      browseButton.addSelectionListener( new SelectionAdapter() {

        @Override
        public void widgetSelected( final SelectionEvent event ) {
          FileDialog file = new FileDialog( getShell() );
          file.setText( Messages.getString("ApplicationSpecificPreferencePage.selectXmlFile") ); //$NON-NLS-1$
          String connection = file.open();
          if( connection != null ) {
            EditDialog.this.xmlPath.setText( connection );
          }
          updateButtons();
        }
      } );
      Label jsdlPathLabel = new Label( panel, SWT.LEAD );
      jsdlPathLabel.setText( Messages.getString( "ApplicationSpecificPreferencePage.dialog_jsdl_label" ) ); //$NON-NLS-1$
      gd = new GridData();
      jsdlPathLabel.setLayoutData( new GridData() );
      this.jsdlPath = new Text( panel, SWT.BORDER );
      gd = new GridData( GridData.FILL_HORIZONTAL );
      gd.grabExcessHorizontalSpace = true;
      // gd.horizontalSpan = 2;
      this.jsdlPath.setLayoutData( gd );
      this.jsdlPath.setLayoutData( gd );
      if( this.jsdlPathInit != null ) {
        this.jsdlPath.setText( this.jsdlPathInit );
      }
      Button browseButton1 = new Button( panel, SWT.PUSH );
      gd = new GridData();
      browseButton1.setLayoutData( gd );
      browseButton1.setImage( fileImage );
      browseButton1.addSelectionListener( new SelectionAdapter() {

        @Override
        public void widgetSelected( final SelectionEvent event ) {
          FileDialog file = new FileDialog( getShell() );
          file.setText( Messages.getString("ApplicationSpecificPreferencePage.selectJsdlFile") ); //$NON-NLS-1$
          String connection = file.open();
          if( connection != null ) {
            EditDialog.this.jsdlPath.setText( connection );
          }
          updateButtons();
        }
      } );
      ModifyListener selectionAdapter = new updateAdapter();
      this.appName.addModifyListener( selectionAdapter );
      this.appPath.addModifyListener( selectionAdapter );
      this.xmlPath.addModifyListener( selectionAdapter );
      return composite;
    }

    @Override
    protected void okPressed() {
      this.returnAppName = this.appName.getText();
      this.returnAppPath = this.appPath.getText();
      this.returnXMLPath = this.xmlPath.getText();
      this.returnJSDLPath = this.jsdlPath.getText();
      super.okPressed();
    }

    /**
     * Method to access value of application name provided by user in dialog.
     * 
     * @return application name
     */
    public String getAppName() {
      return this.returnAppName;
    }

    /**
     * Method to access value of application path (path to executable file of
     * application) provided by user in dialog.
     * 
     * @return application path
     */
    public String getAppPath() {
      return this.returnAppPath;
    }

    /**
     * Method to access value of path to XML (this file defines how New Job
     * Wizard's extra pages should look like)provided by user in dialog.
     * 
     * @return path to XML file
     */
    public String getXMLPath() {
      return this.returnXMLPath;
    }

    /**
     * Method to access value of path to base JSDL file
     * 
     * @return path to JSDL file
     */
    public String getJSDLPath() {
      return this.returnJSDLPath;
    }
    class updateAdapter implements ModifyListener {

      public void modifyText( final ModifyEvent event ) {
        updateButtons();
      }
    }
  }

  public void contentChanged( final IContentChangeNotifier source ) {
    PlatformUI.getWorkbench().getDisplay().asyncExec( new Runnable(){

      public void run() {
        ApplicationSpecificPreferencePage.this.appsViewer.refresh();
        
      }
      
    });
//    this.appsViewer.refresh();
  }
}
