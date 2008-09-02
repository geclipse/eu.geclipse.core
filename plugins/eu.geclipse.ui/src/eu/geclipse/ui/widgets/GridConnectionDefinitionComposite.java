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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.widgets;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
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
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.core.model.IGridPreferences;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.Extensions;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.providers.ConnectionViewContentProvider;
import eu.geclipse.ui.providers.GridModelLabelProvider;
import eu.geclipse.ui.wizards.IConnectionTokenValidator;
import eu.geclipse.ui.wizards.IConnectionUriProcessor;


/**
 * A composite for specifying the settings of a new connection. In fact
 * it holds all necessary widgets to create an {@link URI} with any of
 * the available constructors. It may be contextualized with the
 * eu.geclipse.ui.efs extension point.
 */
public class GridConnectionDefinitionComposite extends Composite {
  
  
  private static class EditorFieldModifyListener implements ModifyListener {
    
    private GridConnectionDefinitionComposite parent;
    
    private boolean active;
    
    public EditorFieldModifyListener( final GridConnectionDefinitionComposite parent ) {
      this.parent = parent;
    }
    
    public boolean isActive() {
      return this.active;
    }
    
    public void setActive( final boolean b ) {
      this.active = b;
    }
    
    public void modifyText( final ModifyEvent e ) {
      if ( isActive() ) {
        this.parent.updateUI();
      }
    }
    
  }
  

  private static final String FS_GECL = "gecl"; //$NON-NLS-1$

  private static final String FS_NULL = EFS.getNullFileSystem().getScheme();
  
  private static final String KEY_FIELD_ACTIVE = "field_enablement"; //$NON-NLS-1$

  /**
   * Hard-coded separator used for preferences.
   */
  private static final String SEPARATOR = ":"; //$NON-NLS-1$

  /**
   * Tree viewer used for the temporary connection.
   */
  protected TreeViewer viewer;

  /**
   * Cached error message.
   */
  protected String errorMessage;

  /**
   * Link for creating a temporary connection.
   */
  protected Link pathLink;

  /**
   * Type of the currently edited URI.
   */
  private String currentURIType;

  /**
   * Label of the scheme combo.
   */
  private Label schemeLabel;

  /**
   * Label of the uri combo.
   */
  private Label uriLabel;

  /**
   * Label of the scheme spec part combo.
   */
  private Label schemeSpecificPartLabel;

  /**
   * Label of the authority combo.
   */
  private Label authorityLabel;

  /**
   * Label of the user info combo.
   */
  private Label userInfoLabel;

  /**
   * Label of the host combo.
   */
  private Label hostLabel;

  /**
   * Label of the port combo.
   */
  private Label portLabel;

  /**
   * Label of the path combo.
   */
  private Label pathLabel;

  /**
   * Label of the query combo.
   */
  private Label queryLabel;

  /**
   * Label of the fragment combo.
   */
  private Label fragmentLabel;

  /**
   * Combo for editing the URI's scheme.
   */
  private Combo schemeCombo;

  /**
   * Combo for editing the URI itself.
   */
  private StoredCombo uriCombo;

  /**
   * Combo for editing the URI's scheme spec part.
   */
  private StoredCombo schemeSpecificPartCombo;

  /**
   * Combo for editing the URI's authority.
   */
  private StoredCombo authorityCombo;

  /**
   * Combo for editing the URI's user info.
   */
  private StoredCombo userInfoCombo;

  /**
   * Combo for editing the URI's host.
   */
  private StoredCombo hostCombo;

  /**
   * Combo for editing the URI's port.
   */
  private StoredCombo portCombo;

  /**
   * Combo for editing the URI's path.
   */
  private StoredCombo pathCombo;

  /**
   * Combo for editing the URI's query.
   */
  private StoredCombo queryCombo;

  /**
   * Combo for editing the URI's fragment.
   */
  private StoredCombo fragmentCombo;

  /**
   * Validator used to validate the tokens or the URI.
   */
  private IConnectionTokenValidator validator;
  
  /**
   * Prozessor used to process the final URI.
   */
  private IConnectionUriProcessor processor;

  /**
   * List of registered ModifyListeners.
   */
  private List< ModifyListener > listeners;
  
  private EditorFieldModifyListener modifyListener;
  
  private IGridContainer mountPoint;
  
  /**
   * Create a new connection definition composite.
   *
   * @param parent The parent of the composite.
   * @param style The composite's style.
   */
  public GridConnectionDefinitionComposite( final IGridContainer mountPoint,
                                            final Composite parent,
                                            final int style ) {

    super(parent, style);
    
    this.mountPoint = mountPoint;
    
    GridData gData;
    this.modifyListener = new EditorFieldModifyListener( this );

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

    this.uriLabel = new Label( this, SWT.NONE );
    this.uriCombo = createEditorField(
        this,
        this.uriLabel,
        eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.uri_label"), //$NON-NLS-1$
        this.modifyListener
    );
    this.schemeSpecificPartLabel = new Label( this, SWT.NONE );
    this.schemeSpecificPartCombo = createEditorField(
        this,
        this.schemeSpecificPartLabel,
        eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.scheme_spec_part_label"), //$NON-NLS-1$
        this.modifyListener
    );
    this.authorityLabel = new Label( this, SWT.NONE );
    this.authorityCombo = createEditorField(
        this,
        this.authorityLabel,
        eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.authority_label"), //$NON-NLS-1$
        this.modifyListener
                                             );
    this.userInfoLabel = new Label( this, SWT.NONE );
    this.userInfoCombo = createEditorField(
        this,
        this.userInfoLabel,
        eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.user_info_label"), //$NON-NLS-1$
        this.modifyListener
    );
    this.hostLabel = new Label( this, SWT.NONE );
    this.hostCombo = createEditorField(
        this,
        this.hostLabel,
        eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.host_label"), //$NON-NLS-1$
        this.modifyListener
    );
    this.portLabel = new Label( this, SWT.NONE );
    this.portCombo = createEditorField(
        this,
        this.portLabel,
        eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.port_label"), //$NON-NLS-1$
        this.modifyListener
    );
    this.pathLabel = new Label( this, SWT.NONE );
    this.pathCombo = createEditorField(
        this,
        this.pathLabel,
        eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.path_label"), //$NON-NLS-1$
        this.modifyListener
    );
    this.queryLabel = new Label( this, SWT.NONE );
    this.queryCombo = createEditorField( this,
        this.queryLabel,
        eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.query_label"), //$NON-NLS-1$
        this.modifyListener
    );
    this.fragmentLabel = new Label( this, SWT.NONE );
    this.fragmentCombo = createEditorField(
        this,
        this.fragmentLabel,
        eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.fragment_label"), //$NON-NLS-1$
        this.modifyListener
    );

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
    ConnectionViewContentProvider cProvider = new ConnectionViewContentProvider();
    this.viewer.setContentProvider( cProvider );
    GridModelLabelProvider lProvider = new GridModelLabelProvider();
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
    this.modifyListener.setActive( true );

  }

  /**
   * Add a {@link ModifyListener} to the list of listeners. Modify
   * listeners are informed whenever a URI specific token was edited.
   *
   * @param l The listener to be added.
   */
  public void addModifyListener( final ModifyListener l ) {
    if ( this.listeners == null ) {
      this.listeners = new ArrayList< ModifyListener >();
    }
    if ( ! this.listeners.contains( l ) ) {
      this.listeners.add( l );
    }
  }

  /**
   * Get the cached error message if any or <code>null</code> otherwise.
   *
   * @return The error message of the last critical operation or
   * <code>null</code> if this operation did not cause an error.
   */
  public String getErrorMessage() {
    return this.errorMessage;
  }

  /**
   * Construct a URI from the currently specified parameters. If this
   * method fails to construct a URI it returns <code>null</code>. In
   * this case {@link #getErrorMessage()} will return an
   * appropriate error message.
   *
   * @return A URI from the currently specified parameters or
   * <code>null</code> in the case of an error.
   */
  public URI getURI() {

    String oldErrorMessage = getErrorMessage();
    String newErrorMessage = null;

    URI uri = null;

    String scheme = this.schemeCombo.getText();

    try {

      if ( isActive( this.uriCombo ) ) {
        uri = new URI( this.uriCombo.getText() );
      }

      else if ( isActive( this.schemeSpecificPartCombo ) ) {
        String schemeSpecificPart = this.schemeSpecificPartCombo.getText();
        String fragment = isActive( this.fragmentCombo ) ? this.fragmentCombo.getText() : null;
        uri = new URI( scheme, schemeSpecificPart, fragment );
      }

      else if ( ! isActive( this.hostCombo ) ) {
        String authority = this.authorityCombo.getText();
        String path = isActive( this.pathCombo ) ? this.pathCombo.getText() : null;
        String query = isActive( this.queryCombo ) ? this.queryCombo.getText() : null;
        String fragment = isActive( this.fragmentCombo ) ? this.fragmentCombo.getText() : null;
        uri = new URI( scheme, authority, path, query, fragment );
      }

      else {
        String userInfo = isActive( this.userInfoCombo ) ? this.userInfoCombo.getText() : null;
        String host = this.hostCombo.getText();
        int port = isActive( this.portCombo ) ? Integer.parseInt( this.portCombo.getText() ) : -1;
        String path = isActive( this.pathCombo ) ? this.pathCombo.getText() : null;
        String query = isActive( this.queryCombo ) ? this.queryCombo.getText() : null;
        String fragment = isActive( this.fragmentCombo ) ? this.fragmentCombo.getText() : null;
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
    
    if ( this.processor != null ) {
      uri = this.processor.processURI( this.mountPoint, uri );
    }

    return uri;

  }

  /**
   * Validates the current settings of the composite controls. If
   * any setting turns out to be invalid <code>false</code> will be
   * returned. In this case {@link #getErrorMessage()} will contain
   * an appropriate error message.
   *
   * @return <code>True</code> if all settings are valid,
   * <code>false</code> otherwise.
   */
  public boolean isValid() {

    String oldErrorMessage = getErrorMessage();
    String newErrorMessage = null;

    if ( this.validator != null ) {

      if ( isActive( this.uriCombo ) ) {
        newErrorMessage
          = this.validator.validateToken( IConnectionTokenValidator.URI_TOKEN,
                                          this.uriCombo.getText() );
      }

      if ( ( newErrorMessage == null ) && isActive( this.schemeSpecificPartCombo ) ) {
        newErrorMessage
          = this.validator.validateToken( IConnectionTokenValidator.SCHEME_SPEC_TOKEN,
                                          this.schemeSpecificPartCombo.getText() );
      }

      if ( ( newErrorMessage == null ) && isActive( this.authorityCombo ) ) {
        newErrorMessage
          = this.validator.validateToken( IConnectionTokenValidator.AUTHORITY_TOKEN,
                                          this.authorityCombo.getText() );
      }

      if ( ( newErrorMessage == null ) && isActive( this.userInfoCombo ) ) {
        newErrorMessage
          = this.validator.validateToken( IConnectionTokenValidator.USER_INFO_TOKEN,
                                          this.userInfoCombo.getText() );
      }

      if ( ( newErrorMessage == null ) && isActive( this.hostCombo ) ) {
        newErrorMessage
          = this.validator.validateToken( IConnectionTokenValidator.HOST_TOKEN,
                                          this.hostCombo.getText() );
      }

      if ( ( newErrorMessage == null ) && isActive( this.portCombo ) ) {
        newErrorMessage
          = this.validator.validateToken( IConnectionTokenValidator.PORT_TOKEN,
                                          this.portCombo.getText() );
      }

      if ( ( newErrorMessage == null ) && isActive( this.pathCombo ) ) {
        newErrorMessage
          = this.validator.validateToken( IConnectionTokenValidator.PATH_TOKEN,
                                          this.pathCombo.getText() );
      }

      if ( ( newErrorMessage == null ) && isActive( this.queryCombo ) ) {
        newErrorMessage
          = this.validator.validateToken( IConnectionTokenValidator.QUERY_TOKEN,
                                          this.queryCombo.getText() );
      }

      if ( ( newErrorMessage == null ) && isActive( this.fragmentCombo ) ) {
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

  /**
   * Removes the specified listener from the list of listeners.
   *
   * @param l The {@link ModifyListener} to be removed.
   */
  public void removeModifyListener( final ModifyListener l ) {
    if ( this.listeners != null ) {
      this.listeners.remove( l );
    }
  }

  /**
   * Set the {@link URI} represented by the controls of this
   * composite. No validation is done by this method.
   *
   * @param uri The {@link URI} to be set.
   */
  public void setURI( final URI uri ) {

    this.modifyListener.setActive( false );
    
    String scheme = uri.getScheme();
    this.schemeCombo.setText( scheme );

    String uris = uri.toString();
    String schemeSpecificPart = uri.getSchemeSpecificPart();
    String authority = uri.getAuthority();
    String userInfo = uri.getUserInfo();
    String host = uri.getHost();
    int port = uri.getPort();
    String path = uri.getPath();
    String query = uri.getQuery();
    String fragment = uri.getFragment();

    this.uriCombo.setDefaultItem( uris != null ? uris : "" ); //$NON-NLS-1$
    this.schemeSpecificPartCombo.setDefaultItem( schemeSpecificPart != null ? schemeSpecificPart : "" ); //$NON-NLS-1$
    this.authorityCombo.setDefaultItem( authority != null ? authority : "" ); //$NON-NLS-1$
    this.userInfoCombo.setDefaultItem( userInfo != null ? userInfo : "" ); //$NON-NLS-1$
    this.hostCombo.setDefaultItem( host != null ? host : "" ); //$NON-NLS-1$
    this.portCombo.setDefaultItem( port != -1 ? String.valueOf( uri.getPort() ) : "" ); //$NON-NLS-1$
    this.pathCombo.setDefaultItem( path != null ? path : "" ); //$NON-NLS-1$
    this.queryCombo.setDefaultItem( query != null ? query : "" ); //$NON-NLS-1$
    this.fragmentCombo.setDefaultItem( fragment != null ? fragment : "" ); //$NON-NLS-1$
    
    this.modifyListener.setActive( true );
    
    updateUI();

  }

  /**
   * Notify all {@link ModifyListener}s of a change.
   */
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

  /**
   * Handle a double click event in the tree viewer.
   */
  protected void handleDoubleClick() {
    IStructuredSelection selection
      = ( IStructuredSelection )this.viewer.getSelection();
    Object object = selection.getFirstElement();
    if( this.viewer.isExpandable( object ) ) {
      boolean state = this.viewer.getExpandedState( object );
      this.viewer.setExpandedState( object, !state );
    }
  }

  /**
   * Handle a grid model event.
   *
   * @param event The event to be handled.
   */
  protected void handleGridModelChanged( final IGridModelEvent event ) {
    if ( ( event.getType() == IGridModelEvent.ELEMENTS_ADDED )
        || ( event.getType() == IGridModelEvent.ELEMENTS_REMOVED ) ) {
      refreshViewer( event.getSource() );
    }
  }

  /**
   * Handle the change of a selection within the tree viewer.
   *
   * @param selection The new selection.
   */
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

  /**
   * Initialize the tree viewer in order to browse the specified {@link URI}.
   */
  protected void initializeBrowser() {

    getDisplay().asyncExec( new Runnable() {

      public void run() {

        GridConnectionDefinitionComposite.this.pathLink.setEnabled( false );

        URI slaveURI = getURI();

        if ( slaveURI != null ) {

          try {

            GEclipseURI geclURI = new GEclipseURI( slaveURI );
            URI masterURI = geclURI.toMasterURI();
            IGridPreferences preferences = GridModel.getPreferences();
            IGridConnection connection
              = preferences.createTemporaryConnection( masterURI );
            GridConnectionDefinitionComposite.this.viewer.setInput( connection );

          } catch ( ProblemException pExc ) {
            GridConnectionDefinitionComposite.this.errorMessage = eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.invalid_temp_conn_error"); //$NON-NLS-1$
            fireModifyEvent();
            ProblemDialog.openProblem( getShell(),
                                       eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.conn_error"), //$NON-NLS-1$
                                       eu.geclipse.ui.widgets.Messages.getString("GridConnectionDefinitionComposite.invalid_temp_conn_error"), //$NON-NLS-1$
                                       pExc );
          } finally {
            GridConnectionDefinitionComposite.this.pathLink.setEnabled( true );
          }

        }

      }

    } );

  }

  /**
   * Reset all controls.
   */
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

  /**
   * Setup all controls according to the scheme specified in the scheme combo.
   */
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
      
      try {
        this.processor
          = ( IConnectionUriProcessor ) extension.createExecutableExtension( Extensions.EFS_PROCESSOR_ATT );
      } catch ( CoreException cExc ) {
        // Since the processor is optional this may fail and
        // may throw an exception that we would not like
        // to handle here
      }

    }

    layout();
    updateUI();

  }

  /**
   * Update the UI and notify all {@link ModifyListener}s.
   */
  protected void updateUI() {
    URI uri = getURI();
    this.viewer.getTree().setEnabled( uri != null );
    this.pathLink.setEnabled( uri != null );
    fireModifyEvent();
  }

  /**
   * Helper method to create a field editor for a specific part of a {@link URI}.
   *
   * @param parent The parent of the created controls.
   * @param label The label of the created combo.
   * @param text The text of the label.
   * @return The combo used as editor.
   */
  private StoredCombo createEditorField( final Composite parent,
                                         final Label label,
                                         final String text,
                                         final ModifyListener listener ) {

    label.setText( text + SEPARATOR );
    GridData lData = new GridData();
    lData.minimumHeight = 0;
    label.setLayoutData( lData );

    StoredCombo editor = new StoredCombo( parent, SWT.NONE );
    GridData eData = new GridData( GridData.FILL_HORIZONTAL );
    eData.grabExcessHorizontalSpace = true;
    eData.minimumHeight = 0;
    editor.setLayoutData( eData );
    editor.addModifyListener( listener );

    return editor;

  }

  /**
   * Load all available schemes from the org.eclipse.core.filesystem.filesystems
   * extension point and initialize the scheme combo with these schemes.
   * @param combo
   */
  private void initializeSchemeCombo( final Combo combo ) {

    combo.removeAll();

    List< String > schemes
      = eu.geclipse.core.Extensions.getRegisteredFilesystemSchemes();
    Collections.sort( schemes, new Comparator< String >() {
      public int compare( final String s1, final String s2 ) {
        return s1.compareToIgnoreCase( s2 );
      }
    } );

    for ( String scheme : schemes ) {
      if ( ! scheme.equalsIgnoreCase( FS_GECL )
          && ! scheme.equalsIgnoreCase( FS_NULL ) ) {
        combo.add( scheme );
      }
    }

  }
  
  private boolean isActive( final StoredCombo editor ) {
    
    boolean result = false;
    
    Boolean active = ( Boolean ) editor.getData( KEY_FIELD_ACTIVE );
    if ( active != null ) {
      result = active.booleanValue();
    }
    
    return result;
    
  }

  /**
   * Helper method to test the speficied String if it is empty.
   *
   * @param s The String to be tested.
   * @return True if the String is <code>null</code> or its length is 0.
   */
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

  /**
   * Refreshes the {@link TreeViewer} starting with the specified element. If
   * the element is <code>null</code> the whole {@link TreeViewer} will be
   * refreshed.
   *
   * @param element The {@link IGridElement} that will be refreshed. This also
   *            includes the element's children.
   */
  private void refreshViewer( final IGridElement element ) {
    Control control = this.viewer.getControl();
    if ( ! control.isDisposed() ) {
      Display display = control.getDisplay();
      display.asyncExec( new Runnable() {
        public void run() {
          if ( ! GridConnectionDefinitionComposite.this.viewer.getControl().isDisposed() ) {
            if ( element == null ) {
              GridConnectionDefinitionComposite.this.viewer.refresh( false );
            } else {
              if ( element instanceof IGridContainer ) {
                IGridContainer container = ( IGridContainer ) element;
                if ( container.isLazy() && container.isDirty() ) {
                  GridConnectionDefinitionComposite.this.viewer.setChildCount( container, container.getChildCount() );
                }
              }
              GridConnectionDefinitionComposite.this.viewer.refresh( element, false );
            }
          }
        }
      } );
    }
  }

  private void setActive( final StoredCombo editor,
                          final Label label,
                          final String text ) {
    if ( ! isEmpty( text ) ) {
      label.setText( text + SEPARATOR );
    }
    label.setVisible( ! isEmpty( text ) );
    editor.setVisible( ! isEmpty( text ) );
    editor.setData( KEY_FIELD_ACTIVE, Boolean.valueOf( ! isEmpty( text ) ) );
    ( ( GridData ) label.getLayoutData() ).exclude = isEmpty( text );
    ( ( GridData ) editor.getLayoutData() ).exclude = isEmpty( text );
  }

}
