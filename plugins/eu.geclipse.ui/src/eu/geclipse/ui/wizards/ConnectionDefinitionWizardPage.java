package eu.geclipse.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Tree;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.ui.Extensions;
import eu.geclipse.ui.dialogs.NewProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.providers.ConnectionViewContentProvider;
import eu.geclipse.ui.providers.ConnectionViewLabelProvider;
import eu.geclipse.ui.widgets.StoredCombo;

public class ConnectionDefinitionWizardPage extends WizardPage {
  
  private static final String SEPARATOR = ":"; //$NON-NLS-1$

  protected TreeViewer viewer;
  
  private String currentURIType;
  
  private Composite mainComp;
  
  private Label schemeLabel;
  
  private Label uriLabel;
  
  private Label schemeSpecificPartLabel;
  
  private Label authorityLabel;
  
  private Label userInfoLabel;
  
  private Label hostLabel;
  
  private Label portLabel;
  
  private Label pathLabel;
  
  private Label queryLabel;
  
  private Label fragmentLabel;

  private Combo schemeCombo;
  
  private StoredCombo uriCombo;
  
  private StoredCombo schemeSpecificPartCombo;
  
  private StoredCombo authorityCombo;
  
  private StoredCombo userInfoCombo;
  
  private StoredCombo hostCombo;
  
  private StoredCombo portCombo;
  
  private StoredCombo pathCombo;
  
  private StoredCombo queryCombo;
  
  private StoredCombo fragmentCombo;
  
  private Link pathLink;
  
  private IConnectionTokenValidator validator;
    
  public ConnectionDefinitionWizardPage() {
    super( Messages.getString("ConnectionDefinitionWizardPage.name"), //$NON-NLS-1$
           Messages.getString("ConnectionDefinitionWizardPage.title"), //$NON-NLS-1$
           null );
    setDescription( Messages.getString("ConnectionDefinitionWizardPage.description") ); //$NON-NLS-1$
  }

  public void createControl( final Composite parent ) {
    
    GridData gData;
    
    this.mainComp = new Composite( parent, SWT.NONE );
    this.mainComp.setLayout( new GridLayout( 2, false ) );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.mainComp.setLayoutData( gData );
    
    this.schemeLabel = new Label( this.mainComp, SWT.NONE );
    this.schemeLabel.setText( Messages.getString("ConnectionDefinitionWizardPage.scheme_label") ); //$NON-NLS-1$
    gData = new GridData();
    gData.minimumHeight = 0;
    this.schemeLabel.setLayoutData( gData );

    this.schemeCombo = new Combo( this.mainComp, SWT.READ_ONLY );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    gData.minimumHeight = 0;
    this.schemeCombo.setLayoutData( gData );
    
    this.schemeCombo.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        updateUI();
      }
    } );
    
    this.uriLabel = new Label( this.mainComp, SWT.NONE );
    this.uriCombo = createEditorField( this.mainComp, this.uriLabel, Messages.getString("ConnectionDefinitionWizardPage.uri_label") ); //$NON-NLS-1$
    this.schemeSpecificPartLabel = new Label( this.mainComp, SWT.NONE );
    this.schemeSpecificPartCombo = createEditorField( this.mainComp, this.schemeSpecificPartLabel, Messages.getString("ConnectionDefinitionWizardPage.scheme_spec_label") ); //$NON-NLS-1$
    this.authorityLabel = new Label( this.mainComp, SWT.NONE );
    this.authorityCombo = createEditorField( this.mainComp, this.authorityLabel, Messages.getString("ConnectionDefinitionWizardPage.authority_label") ); //$NON-NLS-1$
    this.userInfoLabel = new Label( this.mainComp, SWT.NONE );
    this.userInfoCombo = createEditorField( this.mainComp, this.userInfoLabel, Messages.getString("ConnectionDefinitionWizardPage.user_info_label") ); //$NON-NLS-1$
    this.hostLabel = new Label( this.mainComp, SWT.NONE );
    this.hostCombo = createEditorField( this.mainComp, this.hostLabel, Messages.getString("ConnectionDefinitionWizardPage.host_label") ); //$NON-NLS-1$
    this.portLabel = new Label( this.mainComp, SWT.NONE );
    this.portCombo = createEditorField( this.mainComp, this.portLabel, Messages.getString("ConnectionDefinitionWizardPage.port_label") ); //$NON-NLS-1$
    this.pathLabel = new Label( this.mainComp, SWT.NONE );
    this.pathCombo = createEditorField( this.mainComp, this.pathLabel, Messages.getString("ConnectionDefinitionWizardPage.path_label") ); //$NON-NLS-1$
    this.queryLabel = new Label( this.mainComp, SWT.NONE );
    this.queryCombo = createEditorField( this.mainComp, this.queryLabel, Messages.getString("ConnectionDefinitionWizardPage.query_label") ); //$NON-NLS-1$
    this.fragmentLabel = new Label( this.mainComp, SWT.NONE );
    this.fragmentCombo = createEditorField( this.mainComp, this.fragmentLabel, Messages.getString("ConnectionDefinitionWizardPage.fragment_label") ); //$NON-NLS-1$
    
    Group browseGroup = new Group( this.mainComp, SWT.NONE );
    browseGroup.setLayout( new GridLayout( 1, false ) );
    browseGroup.setText( Messages.getString("ConnectionDefinitionWizardPage.browse_title") ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_BOTH );
    gData.horizontalSpan = 4;
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    browseGroup.setLayoutData( gData );
    
    this.pathLink = new Link( browseGroup, SWT.NONE );
    this.pathLink.setText( Messages.getString("ConnectionDefinitionWizardPage.browse_link") ); //$NON-NLS-1$
    gData = new GridData();
    this.pathLink.setLayoutData( gData );
    
    this.viewer = new TreeViewer( browseGroup, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE );
    ConnectionViewContentProvider cProvider
      = new ConnectionViewContentProvider();
    this.viewer.setContentProvider( cProvider );
    ConnectionViewLabelProvider lProvider
      = new ConnectionViewLabelProvider();
    this.viewer.setLabelProvider( lProvider );
    this.viewer.addFilter( new ViewerFilter() {
      @Override
      public boolean select( final Viewer view,
                             final Object parentElement,
                             final Object element ) {
        boolean result = true;
        if ( element instanceof IGridConnectionElement ) {
          result = ( ( IGridConnectionElement ) element ).isFolder();
        }
        return result;
      }
    } );
    
    Tree tree = this.viewer.getTree();
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    tree.setLayoutData( gData );
    
    initializeSchemeCombo( this.schemeCombo );
    
    this.schemeCombo.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        setupFields();
      }
    } );
    
    this.pathLink.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        initializeBrowser();
      }
    } );
    
    this.viewer.addDoubleClickListener( new IDoubleClickListener() {
      public void doubleClick( final DoubleClickEvent event ) {
        handleDoubleClick();
      }
    } );
    
    this.viewer.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( final SelectionChangedEvent event ) {
        handleSelectionChanged( event.getSelection() );
      }
    } );
    
    setupFields();
    
    setControl( this.mainComp );
    
  }
  
  protected StoredCombo createEditorField( final Composite parent,
                                           final Label label,
                                           final String text ) {

    label.setText( text );
    GridData lData = new GridData();
    lData.minimumHeight = 0;
    label.setLayoutData( lData );

    StoredCombo editor = new StoredCombo( parent, SWT.NONE );
    //this.pathCombo.setPreferences( preferenceStore, PATH_PREF_ID );
    GridData eData = new GridData( GridData.FILL_HORIZONTAL );
    eData.grabExcessHorizontalSpace = true;
    eData.minimumHeight = 0;
    editor.setLayoutData( eData );
    
    editor.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        updateUI();
      }
    } );
    
    return editor;

  }
  
  protected URI getURI() {
    
    setErrorMessage( null );
    
    URI uri = null;
    
    String scheme = this.schemeCombo.getText();
    
    try {
      
      if ( this.uriCombo.isVisible() ) {
        uri = new URI( this.uriCombo.getText() );
      }
    
      else if ( this.schemeSpecificPartCombo.isVisible() ) {
        String schemeSpecificPart = this.schemeSpecificPartCombo.getText();
        String fragment = this.fragmentCombo.isVisible() ? this.fragmentCombo.getText() : null;
        uri = new URI( scheme, schemeSpecificPart, fragment );
      }
      
      else if ( ! this.hostCombo.isVisible() ) {
        String authority = this.authorityCombo.getText();
        String path = this.pathCombo.isVisible() ? this.pathCombo.getText() : null;
        String query = this.queryCombo.isVisible() ? this.queryCombo.getText() : null;
        String fragment = this.fragmentCombo.isVisible() ? this.fragmentCombo.getText() : null;
        uri = new URI( scheme, authority, path, query, fragment );
      }
      
      else {
        String userInfo = this.userInfoCombo.isVisible() ? this.userInfoCombo.getText() : null;
        String host = this.hostCombo.getText();
        int port = this.portCombo.isVisible() ? Integer.parseInt( this.portCombo.getText() ) : -1;
        String path = this.pathCombo.isVisible() ? this.pathCombo.getText() : null;
        String query = this.queryCombo.isVisible() ? this.queryCombo.getText() : null;
        String fragment = this.fragmentCombo.isVisible() ? this.fragmentCombo.getText() : null;
        uri = new URI( scheme, userInfo, host, port, path, query, fragment );
      }
      
    } catch ( URISyntaxException uriExc ) {
      setErrorMessage( Messages.getString("ConnectionDefinitionWizardPage.invalid_uri_error") + uriExc.getMessage() ); //$NON-NLS-1$
    } catch ( NumberFormatException nfExc ) {
      setErrorMessage( Messages.getString("ConnectionDefinitionWizardPage.invalid_uri_error") + nfExc.getMessage() ); //$NON-NLS-1$
    }
    
    return uri;
    
  }
  
  protected void handleDoubleClick() {
    IStructuredSelection selection
      = ( IStructuredSelection )this.viewer.getSelection();
    Object object = selection.getFirstElement();
    if( this.viewer.isExpandable( object ) ) {
      boolean state = this.viewer.getExpandedState( object );
      this.viewer.setExpandedState( object, !state );
    }
  }
  
  protected void handleSelectionChanged( final ISelection selection ) {
    
    if ( selection instanceof IStructuredSelection ) {
      
      IStructuredSelection sSelection
        = ( IStructuredSelection ) selection;
      Object object = sSelection.getFirstElement();
      
      if ( ( object != null ) && ( object instanceof IGridConnectionElement ) ) {
        
        IGridConnectionElement element
          = ( ( IGridConnectionElement ) object );
        
        try {
    
          IFileStore fileStore
            = element.getConnectionFileStore();
          URI uri
            = fileStore.toURI();
          
          if ( this.currentURIType.equals( Extensions.EFS_URI_RAW ) ) {
            this.uriCombo.setText( uri.toString() );
          } else if ( this.currentURIType.equals( Extensions.EFS_URI_OPAQUE ) ) {
            this.schemeSpecificPartCombo.setText( uri.getSchemeSpecificPart() );
          } else if ( this.currentURIType.equals( Extensions.EFS_URI_HIERARCHICAL )
              || this.currentURIType.equals( Extensions.EFS_URI_SERVER ) ) {
            this.pathCombo.setText( uri.getPath() );
          }
          
        } catch ( CoreException cExc ) {
          setErrorMessage( Messages.getString("ConnectionDefinitionWizardPage.path_error") + cExc.getMessage() ); //$NON-NLS-1$
        }
        
      }
      
    }
    
  }
  
  protected void initializeBrowser() {
    
    this.viewer.setInput( null );
    
    URI uri = getURI();
    
    if ( uri != null ) {
      
      List< IGridElementCreator > standardCreators = GridModel.getStandardCreators();
      
      for ( final IGridElementCreator creator : standardCreators ) {
        if ( creator.canCreate( uri ) ) {
          try {
            getContainer().run( true, true, new IRunnableWithProgress() {
               public void run( final IProgressMonitor monitor )
                   throws InvocationTargetException, InterruptedException {
                 try {
                   final IGridConnection connection
                     = ( IGridConnection ) creator.create( null );
                   connection.getChildren( monitor );
                   getShell().getDisplay().asyncExec( new Runnable() {
                     public void run() {
                       ConnectionDefinitionWizardPage.this.viewer.setInput( connection );
                     }
                   } );
                 } catch( Exception gmExc ) {
                   throw new InvocationTargetException( gmExc );
                 }
               }
            } );
          } catch( InvocationTargetException itExc ) {
            Throwable cause = itExc.getCause();
            setErrorMessage( Messages.getString("ConnectionDefinitionWizardPage.temp_connection_error") + cause.getMessage() ); //$NON-NLS-1$
            NewProblemDialog.openProblem( getShell(),
                                          Messages.getString("ConnectionDefinitionWizardPage.connection_error_title"), //$NON-NLS-1$
                                          Messages.getString("ConnectionDefinitionWizardPage.connection_error_text"), //$NON-NLS-1$
                                          cause );
          } catch( InterruptedException e ) {
            // Don't handle this here
          }
          break;
        }
      }
            
    }
    
  }
  
  protected void initializeSchemeCombo( final Combo combo ) {
    List< String > schemes
      = eu.geclipse.core.Extensions.getRegisteredFilesystemSchemes();
    String[] schemeArray
      = schemes.toArray( new String[ schemes.size() ] );
    combo.setItems( schemeArray );
  }
  
  protected void resetFields() {
    
    setActive( this.uriCombo, this.uriLabel, Messages.getString("ConnectionDefinitionWizardPage.uri_raw_label") ); //$NON-NLS-1$
    setActive( this.schemeSpecificPartCombo, this.schemeSpecificPartLabel, null );
    setActive( this.authorityCombo, this.authorityLabel, null );
    setActive( this.userInfoCombo, this.userInfoLabel, null );
    setActive( this.hostCombo, this.hostLabel, null );
    setActive( this.portCombo, this.portLabel, null );
    setActive( this.pathCombo, this.pathLabel, null );
    setActive( this.queryCombo, this.queryLabel, null );
    setActive( this.fragmentCombo, this.fragmentLabel, null );
    
    String scheme = this.schemeCombo.getText();
    if ( ! isEmpty( scheme ) ) {
      this.uriCombo.setText( scheme + SEPARATOR );
    }
    
    this.currentURIType = Extensions.EFS_URI_RAW;
    this.validator = null;
    
  }
  
  protected void setupFields() {
    
    resetFields();
    
    String scheme = this.schemeCombo.getText();
    IConfigurationElement extension
      = Extensions.getRegisteredEFSExtension( scheme );
    
    if ( extension != null ) {
      
      IConfigurationElement[] children
        = extension.getChildren();
    
      if ( ( children != null ) && ( children.length == 1 ) ) {
        setActive( this.uriCombo, this.uriLabel, null );
        processURIScheme( children[ 0 ] );
      }
    
      try {
        this.validator
          = ( IConnectionTokenValidator ) extension.createExecutableExtension( Extensions.EFS_VALIDATOR_ATT );
      } catch ( CoreException cExc ) {
        // Since the validator is optional this may fail and
        // may throw an exception that we would not like
        // to handle here
      }
      
    }
    
    this.mainComp.layout();
    updateUI();
    
  }
  
  protected void updateUI() {
    URI uri = getURI();
    this.viewer.getTree().setEnabled( uri != null );
    this.pathLink.setEnabled( uri != null );
    setPageComplete( validatePage() && ( uri != null ) );
  }
  
  protected boolean validatePage() {
    
    String error = null;
    
    if ( this.validator != null ) {
      
      if ( this.uriCombo.isVisible() ) {
        error
          = this.validator.validateToken( IConnectionTokenValidator.URI_TOKEN,
                                          this.uriCombo.getText() );
      }
      
      if ( ( error == null ) && this.schemeSpecificPartCombo.isVisible() ) {
        error
          = this.validator.validateToken( IConnectionTokenValidator.SCHEME_SPEC_TOKEN,
                                          this.schemeSpecificPartCombo.getText() );
      }
      
      if ( ( error == null ) && this.authorityCombo.isVisible() ) {
        error
          = this.validator.validateToken( IConnectionTokenValidator.AUTHORITY_TOKEN,
                                          this.authorityCombo.getText() );
      }
      
      if ( ( error == null ) && this.userInfoCombo.isVisible() ) {
        error
          = this.validator.validateToken( IConnectionTokenValidator.USER_INFO_TOKEN,
                                          this.userInfoCombo.getText() );
      }
      
      if ( ( error == null ) && this.hostCombo.isVisible() ) {
        error
          = this.validator.validateToken( IConnectionTokenValidator.HOST_TOKEN,
                                          this.hostCombo.getText() );
      }
      
      if ( ( error == null ) && this.portCombo.isVisible() ) {
        error
          = this.validator.validateToken( IConnectionTokenValidator.PORT_TOKEN,
                                          this.portCombo.getText() );
      }
      
      if ( ( error == null ) && this.pathCombo.isVisible() ) {
        error
          = this.validator.validateToken( IConnectionTokenValidator.PATH_TOKEN,
                                          this.pathCombo.getText() );
      }
      
      if ( ( error == null ) && this.queryCombo.isVisible() ) {
        error
          = this.validator.validateToken( IConnectionTokenValidator.QUERY_TOKEN,
                                          this.queryCombo.getText() );
      }
      
      if ( ( error == null ) && this.fragmentCombo.isVisible() ) {
        error
          = this.validator.validateToken( IConnectionTokenValidator.FRAGMENT_TOKEN,
                                          this.fragmentCombo.getText() );
      }
      
      setErrorMessage( error );
      
    }
    
    return error == null;
    
  }
  
  private void processURIScheme( final IConfigurationElement element ) {
    this.currentURIType = element.getName();
    if ( this.currentURIType.equals( Extensions.EFS_URI_RAW ) ) {
      processRawURIScheme( element );
    } else if ( this.currentURIType.equals( Extensions.EFS_URI_OPAQUE ) ) {
      processOpaqueURIScheme( element );
    } else if ( this.currentURIType.equals( Extensions.EFS_URI_HIERARCHICAL ) ) {
      processHierarchicalURIScheme( element );
    } else if ( this.currentURIType.equals( Extensions.EFS_URI_SERVER ) ) {
      processServerURIScheme( element );
    }
  }

  private void processServerURIScheme( final IConfigurationElement element ) {
    
    String userInfo = element.getAttribute( Extensions.EFS_USER_INFO_ATT );
    String host = element.getAttribute( Extensions.EFS_HOST_ATT );
    String port = element.getAttribute( Extensions.EFS_PORT_ATT );
    String path = element.getAttribute( Extensions.EFS_PATH_ATT );
    String query = element.getAttribute( Extensions.EFS_QUERY_ATT );
    String fragment = element.getAttribute( Extensions.EFS_FRAGMENT_ATT );
    
    setActive( this.userInfoCombo, this.userInfoLabel, userInfo );
    setActive( this.hostCombo, this.hostLabel, host );
    setActive( this.portCombo, this.portLabel, port );
    setActive( this.pathCombo, this.pathLabel, path );
    setActive( this.queryCombo, this.queryLabel, query );
    setActive( this.fragmentCombo, this.fragmentLabel, fragment );
    
    String scheme = this.schemeCombo.getText();
    IPreferenceStore preferenceStore
      = Activator.getDefault().getPreferenceStore();
    this.userInfoCombo.setPreferences( preferenceStore,
        scheme + SEPARATOR + Extensions.EFS_USER_INFO_ATT );
    this.hostCombo.setPreferences( preferenceStore,
        scheme + SEPARATOR + Extensions.EFS_HOST_ATT );
    this.portCombo.setPreferences( preferenceStore,
        scheme + SEPARATOR + Extensions.EFS_PORT_ATT );
    this.pathCombo.setPreferences( preferenceStore,
        scheme + SEPARATOR + Extensions.EFS_PATH_ATT );
    this.queryCombo.setPreferences( preferenceStore,
        scheme + SEPARATOR + Extensions.EFS_QUERY_ATT );
    this.fragmentCombo.setPreferences( preferenceStore,
        scheme + SEPARATOR + Extensions.EFS_FRAGMENT_ATT );
    
  }

  private void processHierarchicalURIScheme( final IConfigurationElement element ) {
    
    String authority = element.getAttribute( Extensions.EFS_AUTHORITY_ATT );
    String path = element.getAttribute( Extensions.EFS_PATH_ATT );
    String query = element.getAttribute( Extensions.EFS_QUERY_ATT );
    String fragment = element.getAttribute( Extensions.EFS_FRAGMENT_ATT );
    
    setActive( this.authorityCombo, this.authorityLabel, authority );
    setActive( this.pathCombo, this.pathLabel, path );
    setActive( this.queryCombo, this.queryLabel, query );
    setActive( this.fragmentCombo, this.fragmentLabel, fragment );
    
    String scheme = this.schemeCombo.getText();
    IPreferenceStore preferenceStore
      = Activator.getDefault().getPreferenceStore();
    this.authorityCombo.setPreferences( preferenceStore,
        scheme + SEPARATOR + Extensions.EFS_AUTHORITY_ATT );
    this.pathCombo.setPreferences( preferenceStore,
        scheme + SEPARATOR + Extensions.EFS_PATH_ATT );
    this.queryCombo.setPreferences( preferenceStore,
        scheme + SEPARATOR + Extensions.EFS_QUERY_ATT );
    this.fragmentCombo.setPreferences( preferenceStore,
        scheme + SEPARATOR + Extensions.EFS_FRAGMENT_ATT );
    
  }

  private void processOpaqueURIScheme( final IConfigurationElement element ) {
    
    String schemeSpecificPart = element.getAttribute( Extensions.EFS_SCHEME_SPEC_PART_ATT );
    String fragment = element.getAttribute( Extensions.EFS_FRAGMENT_ATT );
    
    setActive( this.schemeSpecificPartCombo, this.schemeSpecificPartLabel, schemeSpecificPart );
    setActive( this.fragmentCombo, this.fragmentLabel, fragment );
    
    String scheme = this.schemeCombo.getText();
    IPreferenceStore preferenceStore
      = Activator.getDefault().getPreferenceStore();
    this.schemeSpecificPartCombo.setPreferences( preferenceStore,
        scheme + SEPARATOR + Extensions.EFS_SCHEME_SPEC_PART_ATT );
    this.fragmentCombo.setPreferences( preferenceStore,
        scheme + SEPARATOR + Extensions.EFS_FRAGMENT_ATT );
    
  }

  private void processRawURIScheme( final IConfigurationElement element ) {
    
    String uri = element.getAttribute( Extensions.EFS_URI_ATT );
    
    setActive( this.uriCombo, this.uriLabel, uri );
    
    String scheme = this.schemeCombo.getText();
    IPreferenceStore preferenceStore
      = Activator.getDefault().getPreferenceStore();
    this.uriCombo.setPreferences( preferenceStore,
        scheme + SEPARATOR + Extensions.EFS_URI_ATT );
    
  }

  private void setActive( final StoredCombo editor,
                          final Label label,
                          final String text ) {
    if ( ! isEmpty( text ) ) {
      label.setText( text + SEPARATOR );
    }
    label.setVisible( ! isEmpty( text ) );
    editor.setVisible( ! isEmpty( text ) );
    ( ( GridData ) label.getLayoutData() ).exclude = isEmpty( text );
    ( ( GridData ) editor.getLayoutData() ).exclude = isEmpty( text );
  }
  
  private boolean isEmpty( final String s ) {
    return ( s == null ) || ( s.length() == 0 );
  }
  
}
