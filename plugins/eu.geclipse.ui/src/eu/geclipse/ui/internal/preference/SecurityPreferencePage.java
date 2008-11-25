package eu.geclipse.ui.internal.preference;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import eu.geclipse.core.security.ICertificateHandle;
import eu.geclipse.core.security.ISecurityManager;
import eu.geclipse.core.security.ISecurityManagerListener;
import eu.geclipse.core.security.Security;
import eu.geclipse.core.security.X509Util;
import eu.geclipse.core.security.ICertificateManager.CertTrust;
import eu.geclipse.ui.comparators.TreeColumnComparator;
import eu.geclipse.ui.dialogs.CertificateInfoDialog;
import eu.geclipse.ui.dialogs.GridFileDialog;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.internal.preference.CertificateContentProvider.GroupMode;
import eu.geclipse.ui.internal.wizards.CertificateImportWizard;
import eu.geclipse.ui.listeners.TreeColumnListener;

public class SecurityPreferencePage
    extends PreferencePage
    implements IWorkbenchPreferencePage, ISecurityManagerListener {
  
  private static String EXPAND_ALL_IMG = "icons/elcl16/expandall.gif"; //$NON-NLS-1$
  
  private static String COLLAPSE_ALL_IMG = "icons/elcl16/collapseall.gif"; //$NON-NLS-1$
  
  private static String VIEW_MENU_IMG = "icons/elcl16/view_menu.gif"; //$NON-NLS-1$
  
  private static Hashtable< String, Image > images = new Hashtable< String, Image >();
  
  protected CertificateInfoDialog infoDialog;
  
  protected TreeViewer certViewer;
  
  protected CertificateContentProvider cProvider;
  
  private Button addButton;
  
  private Button importButton;
  
  private Button infoButton;
  
  private Button deleteButton;
  
  private MenuItem flatItem;
  
  private MenuItem groupedItem;
  
  private MenuItem trustItem;
  
  private MenuItem countryItem;
  
  private MenuItem stateItem;
  
  private MenuItem localityItem;
  
  private MenuItem organizationItem;
  
  private MenuItem unitItem;

  /**
   * Standard constructor for the security preference page.
   */
  public SecurityPreferencePage() {
    setDescription( "Manage your trusted and untrusted certificates. Import CA certificates from remote repositories or add them from a local or remote directory." );
    Security.getCertificateManager().addListener( this );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.security.ISecurityManagerListener#contentChanged(eu.geclipse.core.security.ISecurityManager)
   */
  public void contentChanged( final ISecurityManager manager ) {
    getControl().getDisplay().asyncExec( new Runnable() {
      public void run() {
        SecurityPreferencePage.this.certViewer.setInput( Security.getCertificateManager() );
      }
    } );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.DialogPage#dispose()
   */
  @Override
  public void dispose() {
    Security.getCertificateManager().removeListener( this );
    if ( this.infoDialog != null ) {
      this.infoDialog.close();
    }
    super.dispose();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createContents( final Composite parent ) {
    
    initializeDialogUnits( parent );
    noDefaultAndApplyButton();
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    
    GridLayout layout= new GridLayout( 2, false );
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    mainComp.setLayout( layout );
    
    GridData gData;
    
    final ToolBar toolBar = new ToolBar( mainComp, SWT.FLAT | SWT.RIGHT | SWT.HORIZONTAL );
    toolBar.setLayoutData( new GridData( GridData.END, GridData.END, false, false ) );
    
    ToolItem expandAllItem = new ToolItem( toolBar, SWT.PUSH );
    expandAllItem.setImage( getImage( EXPAND_ALL_IMG ) );
    
    ToolItem collapseAllItem = new ToolItem( toolBar, SWT.PUSH );
    collapseAllItem.setImage( getImage( COLLAPSE_ALL_IMG ) );
    
    final ToolItem menuItem = new ToolItem( toolBar, SWT.PUSH );
    menuItem.setImage( getImage( VIEW_MENU_IMG ) );
    
    final Menu settingsMenu = new Menu( getShell(), SWT.POP_UP );
      MenuItem modeItem = new MenuItem( settingsMenu, SWT.CASCADE );
      modeItem.setText( "Mode" );
      Menu modeMenu = new Menu( settingsMenu );
      modeItem.setMenu( modeMenu );
        this.flatItem = createModeItem( modeMenu, "Flat", false );
        this.groupedItem = createModeItem( modeMenu, "Grouped", true );
      MenuItem groupItem = new MenuItem( settingsMenu, SWT.CASCADE );
      groupItem.setText( "Group by" );
      Menu groupMenu = new Menu( settingsMenu );
      groupItem.setMenu( groupMenu );
        this.trustItem = createGroupItem( groupMenu, "Trust", GroupMode.Trust );
        new MenuItem( groupMenu, SWT.SEPARATOR );
        this.countryItem = createGroupItem( groupMenu, "Country (C)", GroupMode.Country );
        this.stateItem = createGroupItem( groupMenu, "State (ST)", GroupMode.State );
        this.localityItem = createGroupItem( groupMenu, "Locality (L)", GroupMode.Locality );
        this.organizationItem = createGroupItem( groupMenu, "Organization (O)", GroupMode.Organization );
        this.unitItem = createGroupItem( groupMenu, "Organizational Unit (OU)", GroupMode.OrganizationalUnit );
        
    /*Label dummy =*/new Label( mainComp, SWT.NONE );
    
    this.certViewer = new TreeViewer( mainComp, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.MULTI );
    Tree certTree = this.certViewer.getTree();
    gData = new GridData( GridData.FILL, GridData.FILL, true, true );
    gData.heightHint = 150;
    certTree.setLayoutData( gData );
    certTree.setHeaderVisible( true );
    
    TreeColumn nameColumn = new TreeColumn( certTree, SWT.LEFT );
    nameColumn.setText( "Certificate" );
    nameColumn.setWidth( 200 );
    nameColumn.setResizable( true );
    
    TreeColumn typeColumn = new TreeColumn( certTree, SWT.LEFT );
    typeColumn.setText( "Trust" );
    typeColumn.setWidth( 80 );
    typeColumn.setResizable( true );
    
    TreeColumn timeColumn = new TreeColumn( certTree, SWT.LEFT );
    timeColumn.setText( "Valid until" );
    timeColumn.setWidth( 200 );
    timeColumn.setResizable( true );
    
    this.cProvider = new CertificateContentProvider();
    this.certViewer.setContentProvider( this.cProvider );
    this.certViewer.setLabelProvider( new CertificateLabelProvider() );
    this.certViewer.setInput( Security.getCertificateManager() );
    
    TreeColumnListener columnListener = new TreeColumnListener( this.certViewer );
    for ( TreeColumn column : certTree.getColumns() ) {
      column.addSelectionListener( columnListener );
    }
    
    certTree.setSortColumn( nameColumn );
    certTree.setSortDirection( SWT.UP );
    this.certViewer.setComparator( new TreeColumnComparator( nameColumn ) );
    
    Composite buttons = new Composite( mainComp, SWT.NULL );
    gData = new GridData( GridData.VERTICAL_ALIGN_BEGINNING );
    gData.horizontalSpan = 1;
    buttons.setLayoutData( gData );
    layout = new GridLayout( 1, false );
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    buttons.setLayout( layout );
    
    this.addButton = new Button( buttons, SWT.PUSH );
    this.addButton.setText( "&Add..." );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.addButton.setLayoutData( gData );
    
    this.importButton = new Button( buttons, SWT.PUSH );
    this.importButton.setText( "&Import..." );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.importButton.setLayoutData( gData );
    
    this.infoButton = new Button( buttons, SWT.PUSH );
    this.infoButton.setText( "I&nfo..." );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.infoButton.setLayoutData( gData );
    
    this.deleteButton = new Button( buttons, SWT.PUSH );
    this.deleteButton.setText( "&Delete" );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    this.deleteButton.setLayoutData( gData );
    
    expandAllItem.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        SecurityPreferencePage.this.certViewer.expandAll();
      }
    } );
    collapseAllItem.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        SecurityPreferencePage.this.certViewer.collapseAll();
      }
    } );
    menuItem.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        Rectangle bounds = menuItem.getBounds();
        Point point = toolBar.toDisplay( bounds.x, bounds.y + bounds.height );
        settingsMenu.setLocation( point );
        settingsMenu.setVisible( true );
      }
    } );
    this.certViewer.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( final SelectionChangedEvent event ) {
        updateButtons();
        updateInfoDialog();
      }
    } );
    this.certViewer.addDoubleClickListener( new IDoubleClickListener() {
      public void doubleClick( final DoubleClickEvent event ) {
        processDoubleClickEvent( event );
      }
    } );
    this.addButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        openAddDialog();
      }
    } );
    this.importButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        openImportWizard();
      }
    } );
    this.infoButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        openInfoDialog();
      }
    } );
    this.deleteButton.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        deleteSelectedCertificates();
      }
    } );
    
    updateButtons();
    updateMenu();
    
    return mainComp;
    
  }

  public void init( final IWorkbench workbench ) {
    setPreferenceStore( Activator.getDefault().getPreferenceStore() );
  }
  
  protected void addCertificates( final IFileStore[] certStores,
                                  final IProgressMonitor monitor )
      throws CoreException {
    
    monitor.beginTask( "Adding certificates", certStores.length );
    
    X509Certificate[] certs = new X509Certificate[ certStores.length ];
    
    for ( int i = 0 ; i < certStores.length ; i++ ) {
      monitor.subTask( certStores[ i ].getName() );
      InputStream iStream = certStores[ i ].openInputStream( EFS.NONE, null );
      certs[ i ] = X509Util.loadCertificate( iStream );
      monitor.worked( 1 );
    }
    
    Security.getCertificateManager().addCertificates( certs, CertTrust.AlwaysTrusted );
    
  }
  
  protected void deleteSelectedCertificates() {
    boolean confirm = MessageDialog.openConfirm( getShell(),
                                                 "Delete certificates",
                                                 "Do you really want to delete all selected certificates?" );
    if ( confirm ) {
      ICertificateHandle[] certificates = getSelectedCertificates();
      Security.getCertificateManager().removeCertificates( certificates );
    }
  }
  
  protected void openAddDialog() {
    
    int style = GridFileDialog.STYLE_ALLOW_ONLY_FILES
                | GridFileDialog.STYLE_ALLOW_ONLY_EXISTING
                | GridFileDialog.STYLE_MULTI_SELECTION;
    GridFileDialog dialog = new GridFileDialog( getShell(), style );
    dialog.addFileTypeFilter( "0", "Base64 encoded certificate (*.0)" );
    dialog.addFileTypeFilter( "pem", "Base64 encoded certificate (*.pem)" );
    dialog.addFileTypeFilter( "der", "DER encoded certificate (*.der)" );
    dialog.addFileTypeFilter( "crt", "Base64 or DER encoded certificate (*.crt)" );
    
    if ( dialog.open() == Window.OK ) {
    
      final IFileStore[] fileStores = dialog.getSelectedFileStores();
      
      try {
        new ProgressMonitorDialog( getShell() ).run( true, true, new IRunnableWithProgress() {
          public void run( final IProgressMonitor monitor )
              throws InvocationTargetException, InterruptedException {
            try {
              addCertificates( fileStores, monitor );
            } catch( CoreException cExc ) {
              throw new InvocationTargetException( cExc );
            }
          }
        } );
      } catch( InvocationTargetException itExc ) {
        Throwable t = itExc.getCause() == null ? itExc : itExc.getCause();
        ProblemDialog.openProblem( getShell(),
                                   "Certificate problem",
                                   "Failed to add certificates", t );
      } catch( InterruptedException intExc ) {
        // Ignored
      }
      
    }
    
  }
  
  protected void openImportWizard() {
    CertificateImportWizard wizard
      = new CertificateImportWizard();
    WizardDialog dialog = new WizardDialog( getShell(), wizard );
    dialog.open(); 
  }
  
  protected void openInfoDialog() {
    ICertificateHandle[] certificates = getSelectedCertificates();
    if ( ( certificates != null ) && ( certificates.length > 0 ) ) {
      if ( this.infoDialog == null ) {
        this.infoDialog = new CertificateInfoDialog( getShell() );
      }
      this.infoDialog.setCertificate( certificates[ 0 ].getCertificate() );
      this.infoDialog.open();
    }
  }
  
  protected void processDoubleClickEvent( final DoubleClickEvent event ) {
    ISelection selection = event.getSelection();
    if ( selection instanceof IStructuredSelection ) {
      Object element = ( ( IStructuredSelection ) selection ).getFirstElement();
      if ( element instanceof ICertificateHandle ) {
        openInfoDialog();
      } else {
        boolean state = this.certViewer.getExpandedState( element );
        this.certViewer.setExpandedState( element, ! state );
      }
    }
  }
  
  protected void updateButtons() {
    
    int orgCount = 0;
    int certCount = 0;
    
    ISelection selection = this.certViewer.getSelection();
    if ( selection instanceof IStructuredSelection ) {
      Iterator< ? > iter = ( ( IStructuredSelection ) selection ).iterator();
      while ( iter.hasNext() ) {
        Object obj = iter.next();
        if ( obj instanceof ICertificateHandle ) {
          certCount++;
        } else if ( obj instanceof Entry< ?, ? > ) {
          orgCount++;
        }
      }
    }
    
    this.infoButton.setEnabled( ( certCount == 1 ) && ( orgCount == 0 ) );
    this.deleteButton.setEnabled( ( orgCount > 0 ) || ( certCount > 0 ) );
    
  }
  
  protected void updateInfoDialog() {
    if ( this.infoDialog != null ) {
      ICertificateHandle[] certs = getSelectedCertificates();
      if ( certs.length > 0 ) {
        this.infoDialog.setCertificate( certs[ 0 ].getCertificate() );
      }
    }
  }
  
  protected void updateMenu() {
    
    boolean grouped = this.cProvider.isGrouped();
    this.flatItem.setSelection( ! grouped );
    this.groupedItem.setSelection( grouped );
    
    GroupMode groupMode = cProvider.getGroupMode();
    this.countryItem.setSelection( groupMode == GroupMode.Country );
    this.stateItem.setSelection( groupMode == GroupMode.State );
    this.localityItem.setSelection( groupMode == GroupMode.Locality );
    this.organizationItem.setSelection( groupMode == GroupMode.Organization );
    this.unitItem.setSelection( groupMode == GroupMode.OrganizationalUnit );
    this.trustItem.setSelection( groupMode == GroupMode.Trust );
    
    this.countryItem.setEnabled( grouped );
    this.stateItem.setEnabled( grouped );
    this.localityItem.setEnabled( grouped );
    this.organizationItem.setEnabled( grouped );
    this.unitItem.setEnabled( grouped );
    this.trustItem.setEnabled( grouped );
    
  }
  
  private MenuItem createModeItem( final Menu parent, final String text, final boolean grouped ) {
    MenuItem item = new MenuItem( parent, SWT.RADIO );
    item.setText( text );
    item.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        SecurityPreferencePage.this.cProvider.setGrouped( grouped );
        SecurityPreferencePage.this.certViewer.refresh();
        updateMenu();
      }
    } );
    return item;
  }
  
  private MenuItem createGroupItem( final Menu parent, final String text, final GroupMode mode ) {
    MenuItem item = new MenuItem( parent, SWT.RADIO );
    item.setText( text );
    item.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        SecurityPreferencePage.this.cProvider.setGroupMode( mode );
        SecurityPreferencePage.this.certViewer.refresh();
        updateMenu();
      }
    } );
    return item;
  }
  
  private ICertificateHandle[] getSelectedCertificates() {
    
    List< ICertificateHandle > result = new ArrayList< ICertificateHandle >();
    
    ISelection selection = this.certViewer.getSelection();
    if ( selection instanceof IStructuredSelection ) {
      
      Iterator< ? > iterator = ( ( IStructuredSelection ) selection ).iterator();
      
      while ( iterator.hasNext() ) {
        
        Object next = iterator.next();
        
        if ( ( next instanceof ICertificateHandle )
            && ! result.contains( next ) ) {
          result.add( ( ICertificateHandle ) next );
        }
        
        else if ( next instanceof Entry< ?, ? > ) {
          Object value = ( ( Entry< ?, ? > ) next ).getValue();
          if ( value instanceof List< ? > ) {
            for ( Object o : ( List< ? > ) value ) {
              if ( ( o instanceof ICertificateHandle )
                  && ! result.contains( o ) ) {
                result.add( ( ICertificateHandle ) o );
              }
            }
          }
        }
        
      }
      
    }
    
    return result.toArray( new ICertificateHandle[ result.size() ] );
    
  }
  
  private static Image getImage( final String path ) {
    
    Image result = images.get( path );
    
    if ( result == null ) {
      result = loadImage( path );
      images.put( path, result );
    }
    
    return result;
    
  }
  
  private static Image loadImage( final String path ) {
    URL url = Activator.getDefault().getBundle().getEntry( path );
    ImageDescriptor descriptor = ImageDescriptor.createFromURL( url );
    return descriptor.createImage();
  }
  
}
