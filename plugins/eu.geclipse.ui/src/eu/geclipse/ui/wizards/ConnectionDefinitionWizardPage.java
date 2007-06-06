package eu.geclipse.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
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
import eu.geclipse.core.Extensions;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.ui.dialogs.NewProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.providers.ConnectionViewContentProvider;
import eu.geclipse.ui.providers.ConnectionViewLabelProvider;
import eu.geclipse.ui.util.FieldValidator;
import eu.geclipse.ui.widgets.StoredCombo;

public class ConnectionDefinitionWizardPage extends WizardPage {
  
  private static final String HOST_PREF_ID
    = "connectionWizardHost"; //$NON-NLS-1$
  
  private static final String PORT_PREF_ID
    = "connectionWizardPort"; //$NON-NLS-1$
  
  private static final String PATH_PREF_ID
    = "connectionWizardPath"; //$NON-NLS-1$

  private Combo schemeCombo;
  
  private StoredCombo hostCombo;
  
  private StoredCombo portCombo;
  
  private StoredCombo pathCombo;
  
  private Link pathLink;
  
  private TreeViewer viewer;
    
  public ConnectionDefinitionWizardPage() {
    super( "connectionDefinitionPage",
           "Connection Definition",
           null );
    setDescription( "Define your new connection to the Grid" );
  }

  public void createControl( final Composite parent ) {
    
    IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 4, false ) );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    mainComp.setLayoutData( gData );
    
    Label schemaLabel = new Label( mainComp, SWT.NONE );
    schemaLabel.setText( "Scheme:" );
    gData = new GridData();
    schemaLabel.setLayoutData( gData );
    
    this.schemeCombo = new Combo( mainComp, SWT.READ_ONLY );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    gData.horizontalSpan = 3;
    this.schemeCombo.setLayoutData( gData );
    
    Label hostPortLabel = new Label( mainComp, SWT.NONE );
    hostPortLabel.setText( "Host and Port:" );
    gData = new GridData();
    hostPortLabel.setLayoutData( gData );
    
    this.hostCombo = new StoredCombo( mainComp, SWT.NONE );
    this.hostCombo.setPreferences( preferenceStore, HOST_PREF_ID );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.hostCombo.setLayoutData( gData );
    
    Label sepLabel = new Label( mainComp, SWT.NONE );
    sepLabel.setText( ":" );
    gData = new GridData();
    sepLabel.setLayoutData( gData );
    
    this.portCombo = new StoredCombo( mainComp, SWT.NONE );
    this.portCombo.setPreferences( preferenceStore, PORT_PREF_ID );
    gData = new GridData();
    gData.widthHint = 50;
    this.portCombo.setLayoutData( gData );
    
    Label pathLabel = new Label( mainComp, SWT.NONE );
    pathLabel.setText( "Directory:" );
    gData = new GridData();
    pathLabel.setLayoutData( gData );
    
    this.pathCombo = new StoredCombo( mainComp, SWT.NONE );
    this.pathCombo.setPreferences( preferenceStore, PATH_PREF_ID );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    gData.horizontalSpan = 3;
    this.pathCombo.setLayoutData( gData );
    
    Group browseGroup = new Group( mainComp, SWT.NONE );
    browseGroup.setLayout( new GridLayout( 1, false ) );
    browseGroup.setText( "Remote Directory Browser" );
    gData = new GridData( GridData.FILL_BOTH );
    gData.horizontalSpan = 4;
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    browseGroup.setLayoutData( gData );
    
    this.pathLink = new Link( browseGroup, SWT.NONE );
    this.pathLink.setText( "You may want to <A>create a temporary connection</A> in order to browse the specified host." );
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
      public boolean select( final Viewer viewer,
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
        updateUI();
      }
    } );
    
    this.hostCombo.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        updateUI();
      }
    } );
    
    this.portCombo.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        updateUI();
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
    
    updateUI();
    
    setControl( mainComp );
    
  }
  
  protected URI getURI() {
    
    String scheme = this.schemeCombo.getText();
    if ( isEmpty(scheme) ) {
      scheme = null;
    }
    
    String host = this.hostCombo.getText();
    if ( isEmpty( host ) ) {
      host = null;
    }
    
    String portS = this.portCombo.getText(); 
    int port = isEmpty( portS ) ? -1 : Integer.parseInt( portS );
    
    String path = this.pathCombo.getText();
    if ( isEmpty( path ) ) {
      path = "/";
    }
    
    URI uri = null;
    try {
      uri = new URI( scheme, null, host, port, path, null, null );
    } catch( URISyntaxException uriExc ) {
      setErrorMessage( "Unable to create a valid URI from the specified information: " + uriExc.getMessage() );
    }
    
    System.out.println( "URI: " + uri );
    
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
          String path
            = uri.getPath();
          this.pathCombo.setText( path );
        } catch ( CoreException cExc ) {
          setErrorMessage( "Error while selecting path: " + cExc.getMessage() );
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
            setErrorMessage( "Unable to create a temporary connection: " + cause.getMessage() );
            NewProblemDialog.openProblem( getShell(),
                                          "Connection error",
                                          "Unable to create a temporary connection",
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
      = Extensions.getRegisteredFilesystemSchemes();
    String[] schemeArray
      = schemes.toArray( new String[ schemes.size() ] );
    combo.setItems( schemeArray );
  }
  
  protected void updateUI() {
    
    boolean contactValid = validateContact();
    
    this.pathLink.setEnabled( contactValid );
    this.viewer.getTree().setEnabled( contactValid );
    
  }
  
  protected boolean validateContact() {
    
    String errorMessage = null;
    
    String scheme = this.schemeCombo.getText();
    if ( ! FieldValidator.validateScheme( scheme ) ) {
      errorMessage = "You have to choose a valid scheme";
    }
    
    if ( errorMessage == null ) {
      String host = this.hostCombo.getText();
      if ( ! isEmpty( host ) && ! FieldValidator.validateHost( host ) ) {
        errorMessage = "The specified hostname is invalid";
      }
    }
    
    if ( errorMessage == null ) {
      String port = this.portCombo.getText();
      if ( ! isEmpty( port ) && ! FieldValidator.validatePort( port ) ) {
        errorMessage = "The specified port is invalid";
      }
    }
    
    setErrorMessage( errorMessage );
    
    return errorMessage == null;
    
  }
  
  private boolean isEmpty( final String s ) {
    return ( s == null ) || ( s.length() == 0 );
  }
  
}
