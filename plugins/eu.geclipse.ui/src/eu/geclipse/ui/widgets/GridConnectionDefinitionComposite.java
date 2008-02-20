package eu.geclipse.ui.widgets;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Tree;

import eu.geclipse.core.filesystem.GEclipseURI;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.core.model.IGridPreferences;
import eu.geclipse.ui.Extensions;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.providers.ConnectionViewContentProvider;
import eu.geclipse.ui.providers.ConnectionViewLabelProvider;
import eu.geclipse.ui.wizards.IConnectionTokenValidator;

public class GridConnectionDefinitionComposite extends Composite {
  
  private static final String SEPARATOR = ":"; //$NON-NLS-1$

  protected TreeViewer viewer;
  
  private String currentURIType;
  
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
  
  private List< ModifyListener > listeners;
  
  private String errorMessage;

  public GridConnectionDefinitionComposite( final Composite parent, final int style ) {
    
    super(parent, style);
    
    GridData gData;
    
    setLayout( new GridLayout( 2, false ) );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    setLayoutData( gData );
    
    this.schemeLabel = new Label( this, SWT.NONE );
    this.schemeLabel.setText( eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.scheme_label") ); //$NON-NLS-1$
    gData = new GridData();
    gData.minimumHeight = 0;
    this.schemeLabel.setLayoutData( gData );

    this.schemeCombo = new Combo( this, SWT.READ_ONLY );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    gData.minimumHeight = 0;
    this.schemeCombo.setLayoutData( gData );
    
    this.schemeCombo.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        updateUI();
      }
    } );
    
    this.uriLabel = new Label( this, SWT.NONE );
    this.uriCombo = createEditorField( this,
                                       this.uriLabel,
                                       eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.uri_label") ); //$NON-NLS-1$
    this.schemeSpecificPartLabel = new Label( this, SWT.NONE );
    this.schemeSpecificPartCombo = createEditorField( this,
                                                      this.schemeSpecificPartLabel,
                                                      eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.scheme_spec_part_label") ); //$NON-NLS-1$
    this.authorityLabel = new Label( this, SWT.NONE );
    this.authorityCombo = createEditorField( this,
                                             this.authorityLabel,
                                             eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.authority_label") ); //$NON-NLS-1$
    this.userInfoLabel = new Label( this, SWT.NONE );
    this.userInfoCombo = createEditorField( this,
                                            this.userInfoLabel,
                                            eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.user_info_label") ); //$NON-NLS-1$
    this.hostLabel = new Label( this, SWT.NONE );
    this.hostCombo = createEditorField( this,
                                        this.hostLabel,
                                        eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.host_label") ); //$NON-NLS-1$
    this.portLabel = new Label( this, SWT.NONE );
    this.portCombo = createEditorField( this,
                                        this.portLabel,
                                        eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.port_label") ); //$NON-NLS-1$
    this.pathLabel = new Label( this, SWT.NONE );
    this.pathCombo = createEditorField( this,
                                        this.pathLabel,
                                        eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.path_label") ); //$NON-NLS-1$
    this.queryLabel = new Label( this, SWT.NONE );
    this.queryCombo = createEditorField( this,
                                         this.queryLabel,
                                         eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.query_label") ); //$NON-NLS-1$
    this.fragmentLabel = new Label( this, SWT.NONE );
    this.fragmentCombo = createEditorField( this,
                                            this.fragmentLabel,
                                            eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.fragment_label") ); //$NON-NLS-1$
    
    Group browseGroup = new Group( this, SWT.NONE );
    browseGroup.setLayout( new GridLayout( 1, false ) );
    browseGroup.setText( eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.browser_group_title") ); //$NON-NLS-1$
    gData = new GridData( GridData.FILL_BOTH );
    gData.horizontalSpan = 4;
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    browseGroup.setLayoutData( gData );
    
    this.pathLink = new Link( browseGroup, SWT.NONE );
    this.pathLink.setText( eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.browser_link") ); //$NON-NLS-1$
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
    
    GridModel.getRoot().addGridModelListener( new IGridModelListener() {
      public void gridModelChanged( final IGridModelEvent event ) {
        handleGridModelChanged( event );
      }
    } );
    
    setupFields();
    
  }
  
  public void addModifyListener( final ModifyListener l ) {
    if ( this.listeners == null ) {
      this.listeners = new ArrayList< ModifyListener >();
    }
    if ( ! this.listeners.contains( l ) ) {
      this.listeners.add( l );
    }
  }
  
  public String getErrorMessage() {
    return this.errorMessage;
  }
  
  public URI getURI() {
    
    String oldErrorMessage = getErrorMessage();
    String newErrorMessage = null;
    
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
      newErrorMessage =  eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.invalid_uri_error") //$NON-NLS-1$
                         + uriExc.getMessage();
    } catch ( NumberFormatException nfExc ) {
      newErrorMessage = eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.invalid_uri_error") //$NON-NLS-1$
                        + nfExc.getMessage();
    }
    
    if ( ( ( newErrorMessage != null ) && ! newErrorMessage.equals( oldErrorMessage ) )
        || ( ( oldErrorMessage != null ) && ! oldErrorMessage.equals( newErrorMessage ) ) ) {
      this.errorMessage = newErrorMessage;
      fireModifyEvent();
    }
    
    return uri;
    
  }
  
  public boolean isValid() {
    
    String oldErrorMessage = getErrorMessage();
    String newErrorMessage = null;
    
    if ( this.validator != null ) {
      
      if ( this.uriCombo.isVisible() ) {
        newErrorMessage
          = this.validator.validateToken( IConnectionTokenValidator.URI_TOKEN,
                                          this.uriCombo.getText() );
      }
      
      if ( ( newErrorMessage == null ) && this.schemeSpecificPartCombo.isVisible() ) {
        newErrorMessage
          = this.validator.validateToken( IConnectionTokenValidator.SCHEME_SPEC_TOKEN,
                                          this.schemeSpecificPartCombo.getText() );
      }
      
      if ( ( newErrorMessage == null ) && this.authorityCombo.isVisible() ) {
        newErrorMessage
          = this.validator.validateToken( IConnectionTokenValidator.AUTHORITY_TOKEN,
                                          this.authorityCombo.getText() );
      }
      
      if ( ( newErrorMessage == null ) && this.userInfoCombo.isVisible() ) {
        newErrorMessage
          = this.validator.validateToken( IConnectionTokenValidator.USER_INFO_TOKEN,
                                          this.userInfoCombo.getText() );
      }
      
      if ( ( newErrorMessage == null ) && this.hostCombo.isVisible() ) {
        newErrorMessage
          = this.validator.validateToken( IConnectionTokenValidator.HOST_TOKEN,
                                          this.hostCombo.getText() );
      }
      
      if ( ( newErrorMessage == null ) && this.portCombo.isVisible() ) {
        newErrorMessage
          = this.validator.validateToken( IConnectionTokenValidator.PORT_TOKEN,
                                          this.portCombo.getText() );
      }
      
      if ( ( newErrorMessage == null ) && this.pathCombo.isVisible() ) {
        newErrorMessage
          = this.validator.validateToken( IConnectionTokenValidator.PATH_TOKEN,
                                          this.pathCombo.getText() );
      }
      
      if ( ( newErrorMessage == null ) && this.queryCombo.isVisible() ) {
        newErrorMessage
          = this.validator.validateToken( IConnectionTokenValidator.QUERY_TOKEN,
                                          this.queryCombo.getText() );
      }
      
      if ( ( newErrorMessage == null ) && this.fragmentCombo.isVisible() ) {
        newErrorMessage
          = this.validator.validateToken( IConnectionTokenValidator.FRAGMENT_TOKEN,
                                          this.fragmentCombo.getText() );
      }
      
    }
    
    if ( ( ( newErrorMessage != null ) && ! newErrorMessage.equals( oldErrorMessage ) )
        || ( ( oldErrorMessage != null ) && ! oldErrorMessage.equals( newErrorMessage ) ) ) {
      this.errorMessage = newErrorMessage;
    }
    
    return this.errorMessage == null;
    
  }
  
  public void removeModifyListener( final ModifyListener l ) {
    if ( this.listeners != null ) {
      this.listeners.remove( l );
    }
  }
  
  protected void fireModifyEvent() {
    if ( this.listeners != null ) {
      Event event = new Event();
      event.widget = this;
      ModifyEvent mEvent = new ModifyEvent( event );
      for ( ModifyListener l : this.listeners ) {
        l.modifyText( mEvent );
      }
    }    
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
  
  protected void handleGridModelChanged( final IGridModelEvent event ) {
    Control control = this.viewer.getControl();
    if ( ! control.isDisposed() ) {
      Display display = control.getDisplay();
      display.asyncExec( new Runnable() {
        public void run() {
          if ( ! GridConnectionDefinitionComposite.this.viewer.getControl().isDisposed() ) {
            IGridElement element = event.getSource();
            GridConnectionDefinitionComposite.this.viewer.refresh( element );
          }
        }
      } );
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
    
          IFileStore fileStore = element.getConnectionFileStore();
          GEclipseURI geclURI = new GEclipseURI( fileStore.toURI() );
          URI uri = geclURI.toSlaveURI();
          
          if ( this.currentURIType.equals( Extensions.EFS_URI_RAW ) ) {
            this.uriCombo.setText( uri.toString() );
          } else if ( this.currentURIType.equals( Extensions.EFS_URI_OPAQUE ) ) {
            this.schemeSpecificPartCombo.setText( uri.getSchemeSpecificPart() );
          } else if ( this.currentURIType.equals( Extensions.EFS_URI_HIERARCHICAL )
              || this.currentURIType.equals( Extensions.EFS_URI_SERVER ) ) {
            this.pathCombo.setText( uri.getPath() );
          }
          
        } catch ( CoreException cExc ) {
          this.errorMessage = eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.invalid_path_error"); //$NON-NLS-1$
          fireModifyEvent();
        }
        
      }
      
    }
    
  }
  
  protected void initializeBrowser() {
    
    this.viewer.setInput( null );
    
    URI slaveURI = getURI();
    
    if ( slaveURI != null ) {
      
      try {
      
        GEclipseURI geclURI = new GEclipseURI( slaveURI );
        URI masterURI = geclURI.toMasterURI();
        IGridPreferences preferences = GridModel.getPreferences();
        IGridConnection connection
          = preferences.createTemporaryConnection( masterURI );
        this.viewer.setInput( connection );
        
      } catch ( GridModelException gmExc ) {
        this.errorMessage = eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.invalid_temp_conn_error"); //$NON-NLS-1$
        fireModifyEvent();
        ProblemDialog.openProblem( getShell(),
                                   eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.conn_error"), //$NON-NLS-1$
                                   eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.invalid_temp_conn_error"), //$NON-NLS-1$
                                   gmExc );
      }
      
    }
    
  }
  
  protected void resetFields() {
    
    setActive( this.uriCombo, this.uriLabel, eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.uri_label") ); //$NON-NLS-1$
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
    
    layout();
    updateUI();
    
  }
  
  protected void updateUI() {
    URI uri = getURI();
    this.viewer.getTree().setEnabled( uri != null );
    this.pathLink.setEnabled( uri != null );
    fireModifyEvent();
  }
  
  private StoredCombo createEditorField( final Composite parent,
                                           final Label label,
                                           final String text ) {

    label.setText( text );
    GridData lData = new GridData();
    lData.minimumHeight = 0;
    label.setLayoutData( lData );

    StoredCombo editor = new StoredCombo( parent, SWT.NONE );
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
  
  private void initializeSchemeCombo( final Combo combo ) {
    List< String > schemes
      = eu.geclipse.core.Extensions.getRegisteredFilesystemSchemes();
    String[] schemeArray
      = schemes.toArray( new String[ schemes.size() ] );
    combo.setItems( schemeArray );
  }
  
  private boolean isEmpty( final String s ) {
    return ( s == null ) || ( s.length() == 0 );
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

}
