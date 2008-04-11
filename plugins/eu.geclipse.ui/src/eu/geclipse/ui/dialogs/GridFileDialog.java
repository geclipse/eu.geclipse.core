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

package eu.geclipse.ui.dialogs;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import eu.geclipse.core.filesystem.GEclipseURI;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.ui.comparators.TreeColumnComparator;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.listeners.TreeColumnListener;
import eu.geclipse.ui.providers.FileStoreLabelProvider;
import eu.geclipse.ui.providers.GridFileDialogContentProvider;
import eu.geclipse.ui.providers.NewGridModelLabelProvider;
import eu.geclipse.ui.providers.NewProgressTreeNode;
import eu.geclipse.ui.widgets.StoredCombo;

/**
 * This is an implementation of a file dialog for both local
 * and remote files that are made available via EFS-implementations.
 * The remote parts make use of the Grid connections that are defined
 * within the user's. The dialog is highly configurable and allows to
 * only show the remote or local parts or both. Furthermore it can be
 * used to select only files or directories or both and only existing
 * or to specify even new files/directories. Last but not least multi-
 * selection may also be possible.
 */
public class GridFileDialog
    extends TitleAreaDialog {
  
  /**
   * Private interface used to listen to mode changes.
   */
  private interface IModeChangeListener {
    
    /**
     * Invoked whenever the dialog's mode has changed.
     * 
     * @param mode The new mode.
     */
    public void modeChanged( final int mode );
    
  }
  
  /**
   * The <code>ModeManager</code> does manage the dialogs modes.
   * On the one hand it manages the activation states of the
   * provided {@link ToolItem}s, on the other hand it informs
   * listeners about mode changes. 
   */
  private static class ModeManager extends SelectionAdapter {
    
    /**
     * Mode constant for the remote mode.
     */
    public static final int CONNECTION_MODE = 1;

    /**
     * Mode constant for the local mode with the user's home
     * as root directory.
     */
    public static final int HOME_MODE = 2;
    
    /**
     * Mode constant for the local mode with the workspace
     * as root directory.
     */
    public static final int WS_MODE = 3;
    
    /**
     * Mode constant for the local mode with the system's root
     * as root directory.
     */
    public static final int ROOT_MODE = 4;
    
    /**
     * Identifier used to tag the {@link ToolItem}s.
     */
    private static final String MODE_KEY = "button.mode"; //$NON-NLS-1$
    
    /**
     * List of all available {@link ToolItem}s.
     */
    private List< ToolItem > modeItems = new ArrayList< ToolItem >();
    
    /** 
     * List of all registered listeners.
     */
    private List< IModeChangeListener > listeners = new ArrayList< IModeChangeListener >();
    
    /**
     * Standard constructor.
     */
    public ModeManager() {
      // empty implementation
    }
    
    /**
     * Add a new {@link IModeChangeListener} to the list of listeners.
     * 
     * @param l The new listener.
     */
    public void addModeChangeListener( final IModeChangeListener l ) {
      if ( ! this.listeners.contains( l ) ) {
        this.listeners.add( l );
      }
    }
    
    /**
     * Add a new mode item to the list of items. Any formerly defined
     * item with the same mode will be overwritten.
     * 
     * @param item The new item used to select the specified mode.
     * @param mode The mode that refers to the specified item.
     */
    public void addModeItem( final ToolItem item, final int mode ) {
      item.setData( MODE_KEY, new Integer( mode ) );
      this.modeItems.add( item );
      item.addSelectionListener( this );
    }
    
    /**
     * Set the mode, i.e. update the selection states of the mode items.
     * 
     * @param mode The new mode.
     */
    public void setMode( final int mode ) {
      for ( ToolItem item : this.modeItems ) {
        Integer m = ( Integer ) item.getData( MODE_KEY );
        item.setSelection( ( m != null ) && ( m.intValue() == mode ) ); 
      }
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    @Override
    public void widgetSelected( final SelectionEvent e ) {
      for ( ToolItem item : this.modeItems ) {
        item.setSelection( item == e.widget );
      }
      Object mode = e.widget.getData( MODE_KEY );
      if ( mode instanceof Integer ) {
        fireModeChanged( ( ( Integer ) mode ).intValue() );
      }
    }
    
    /**
     * Inform all registered {@link IModeChangeListener}s about a mode
     * changed event.
     * 
     * @param mode The new mode to be reported to the listeners.
     */
    private void fireModeChanged( final int mode ) {
      for ( IModeChangeListener l : this.listeners ) {
        l.modeChanged( mode );
      }
    }
    
  }
  
  /**
   * {@link ViewerFilter} that filters out all non-folders.
   */
  private static class FolderFilter extends ViewerFilter {
    
    /**
     * Standard constructor.
     */
    public FolderFilter() {
      // empty implementation
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
     *        java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean select( final Viewer viewer,
                           final Object parentElement,
                           final Object element ) {
      boolean result = false;
      if ( element instanceof IGridElement ) {
        if ( element instanceof IGridConnectionElement ) {
          result = ( ( IGridConnectionElement ) element ).isFolder();
        } else if ( element instanceof IGridContainer ) {
          result = true;
        }
      } else if ( element instanceof IFileStore ) {
        IFileInfo info = ( ( IFileStore ) element ).fetchInfo();
        result = info.isDirectory();
      } else if ( element instanceof NewProgressTreeNode ) {
        result = true;
      }
      return result;
    }
    
  }
  
  /**
   * {@link ViewerFilter} that filters out all non-remote elements.
   */
  private static class RemoteConnectionFilter extends ViewerFilter {
    
    /**
     * Standard constructor.
     */
    public RemoteConnectionFilter() {
      // empty implementation
    }
    
    @Override
    public boolean select( final Viewer viewer,
                           final Object parentElement,
                           final Object element ) {
      boolean result = false;
      if ( element instanceof IGridConnectionElement ) {
        result = ! ( ( IGridConnectionElement ) element ).isLocal();
      } else if ( element instanceof NewProgressTreeNode ) {
        result = true;
      }
      return result;
    }
    
  }
  
  /**
   * {@link ViewerFilter} that filters out all files without a specific
   * file extension.
   */
  private static class FileTypeFilter extends ViewerFilter {
    
    /**
     * Constant for the wildcard filter.
     */
    private static final String WILDCARD = "*"; //$NON-NLS-1$
    
    /**
     * Separator used to separate filename and prefix.
     */
    private static final String PREFIX_SEPARATOR = "."; //$NON-NLS-1$
    
    /**
     * The file extension for allowed files.
     */
    private String prefix;
    
    /**
     * Construct a new standard filter, i.e. a filter that allows all
     * files (*.*).
     */
    public FileTypeFilter() {
      this( null );
    }
    
    /**
     * Construct a new filter that filters all files with other extensions
     * than the specified. Folders are not filtered.
     * 
     * @param prefix Prefix for all non-filtered files.
     */
    public FileTypeFilter( final String prefix ) {
      this.prefix = prefix;
    }
    
    /**
     * Get the prefix of the filtered files.
     * 
     * @return The file's prefix.
     */
    public String getPrefix() {
      return this.prefix == null ? WILDCARD : this.prefix;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
     *        java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean select( final Viewer viewer,
                           final Object parentElement,
                           final Object element ) {
      
      boolean result = false;
      
      if ( this.prefix != null ) {
        
        if ( element instanceof IFileStore ) {
          boolean isDir = ( ( IFileStore ) element ).fetchInfo().isDirectory();
          String name = ( ( IFileStore ) element ).getName();
          result = isDir || name.endsWith( PREFIX_SEPARATOR + this.prefix );
        }
        
        else if ( element instanceof IGridConnectionElement ) {
          boolean isDir = ( ( IGridConnectionElement ) element ).isFolder();
          String name = ( ( IGridConnectionElement ) element ).getName();
          result = isDir || name.endsWith( PREFIX_SEPARATOR + this.prefix );
        }
        
        else if ( element instanceof IGridContainer ) {
          IResource resource = ( ( IGridContainer ) element ).getResource();
          if ( ( resource != null ) && ( resource.getType() == IResource.FILE ) ) {
            String name = resource.getName();
            result = name.endsWith( PREFIX_SEPARATOR + this.prefix );
          } else {
            result = true;
          }
        }
        
        else if ( element instanceof IGridElement ) {
          String name = ( ( IGridElement ) element ).getName();
          result = name.endsWith( PREFIX_SEPARATOR + this.prefix );
        }
        
        else if ( element instanceof NewProgressTreeNode ){
          result = true;
        }
        
      }
      
      
      else {
        result = true;
      }
      
      return result;
      
    }
    
  }
  
  /**
   * Style constant for none styles.
   */
  public static final int STYLE_NONE = 0x00;
  
  /**
   * Style constant for a dialog that allows only local files,
   * i.e. no Grid connections.
   */
  public static final int STYLE_ALLOW_ONLY_LOCAL = 0x01;
  
  /**
   * Style constant for a dialog that allows Grid connections.
   */
  public static final int STYLE_ALLOW_ONLY_CONNECTIONS = 0x02;
  
  /**
   * Style constant for a dialog that allows only remote files,
   * i.e. only Grid connections that are not referring to local files.
   */
  public static final int STYLE_ALLOW_ONLY_REMOTE_CONNECTIONS = 0x04;
  
  /**
   * Style constant for a dialog that allows only the selection of
   * files.
   */
  public static final int STYLE_ALLOW_ONLY_FILES = 0x08;
  
  /**
   * Style constant for a dialog that allows only the selection of
   * directories.
   */
  public static final int STYLE_ALLOW_ONLY_FOLDERS = 0x10;
  
  /**
   * Style constant for a dialog that allows only the selection of
   * existing files/directories. The filename and url combos can not
   * be edited.
   */
  public static final int STYLE_ALLOW_ONLY_EXISTING = 0x20;
  
  /**
   * Style contant for a file dialog that allows multi selection.
   */
  public static final int STYLE_MULTI_SELECTION = 0x40;
  
  /**
   * Empty string constant.
   */
  private static final String EMPTY_STRING = ""; //$NON-NLS-1$
  
  /**
   * Path separator.
   */
  private static final String PATH_SEPARATOR = "/"; //$NON-NLS-1$
  
  /**
   * System property for the user's home directory.
   */
  private static final String HOME_PROPERTY = "user.home"; //$NON-NLS-1$
  
  /**
   * Root folder constant.
   */
  private static final String ROOT_FOLDER = "/"; //$NON-NLS-1$
  
  /**
   * The dialog's tree viewer.
   */
  protected TreeViewer treeViewer;
  
  /**
   * The combo for selecting the file type.
   */
  protected Combo filetypeCombo;
  
  /**
   * The combo for selecting and editing the URI directly.
   */
  protected StoredCombo uriCombo;
  
  /**
   * Map of all available {@link FileTypeFilter}s.
   */
  protected Hashtable< String, FileTypeFilter > filetypeFilters
    = new Hashtable< String, FileTypeFilter >();

  /**
   * The combo for selecting and editing the filename directly.
   */
  private StoredCombo filenameCombo;
  
  /**
   * {@link ModeManager} used to manage the tree viewer's mode.
   */
  private ModeManager modeManager;
  
  /**
   * Listener used to listen to changes in the Grid model.
   */
  private IGridModelListener modelListener;
  
  /**
   * Listener used to listen to modifications in the uri combo.
   */
  private ModifyListener uriListener;
  
  /**
   * Listener used to listen to modifications in the filename combo.
   */
  private ModifyListener filenameListener;
  
  /**
   * Listener used to listen to selections in the tree viewer.
   */
  private ISelectionChangedListener selectionListener;
  
  /**
   * The style of the dialog.
   */
  private int style;
  
  /**
   * The current selection of the tree viewer.
   */
  private IStructuredSelection currentSelection;
  
  /**
   * Create a new dialog with the specified style constant.
   * 
   * @param parent The dialog's parent {@link Shell}.
   * @param style The dialog's style, i.e. a bitwise or of style
   * constants.
   */
  public GridFileDialog( final Shell parent, final int style ) {
    super( parent );
    this.style = style;
    assertStyle();
    setShellStyle( SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE );
    URL imgURL = Activator.getDefault().getBundle()
                   .getResource( "icons/wizban/newconn_wiz.gif" ); //$NON-NLS-1$
    ImageDescriptor imgDesc = ImageDescriptor.createFromURL( imgURL );
    setTitleImage( imgDesc.createImage() );
  }
  
  /**
   * Convenience method to open a file dialog. If filters have to be
   * specified this method may not be used.
   * 
   * @param parent The dialog's parent {@link Shell}.
   * @param style The dialog's style, i.e. a bitwise or of style
   * constants.
   * @return Array containing the {@link URI}s of all selected elements
   * or <code>null</code> if no selection is available.
   */
  public static URI[] openFileDialog( final Shell parent, final int style ) {
    URI[] result = null;
    GridFileDialog dialog = new GridFileDialog( parent, style );
    if ( dialog.open() == Window.OK ) {
      result = dialog.getSelectedURIs();
    }
    return result;
  }
  
  /**
   * Add a file type filter to this dialog. The filter will appear in
   * the file type combo.
   * 
   * @param prefix The prefix of files that will be shown.
   * @param description A description of the filter. This description
   * will be shown in the file type combo.
   */
  public void addFileTypeFilter( final String prefix,
                                 final String description ) {
    FileTypeFilter filter = new FileTypeFilter( prefix );
    addFileTypeFilter( filter, description );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.TrayDialog#close()
   */
  @Override
  public boolean close() {
    if ( this.modelListener != null ) {
      GridModel.getRoot().removeGridModelListener( this.modelListener );
    }
    return super.close();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.Dialog#create()
   */
  @Override
  public void create() {
    super.create();
    validate();
  }
  
  /**
   * Get all currently selected {@link IFileStore}s that meet the style of
   * this dialog.
   * 
   * @return Array containing the {@link IFileStore}s of all selected elements
   * or <code>null</code> if no selection is available.
   */
  public IFileStore[] getSelectedFileStores() {
    
    IFileStore[] result = null;
    
    IFileStore[] stores = internalGetSelectedFileStores();
    
    if ( ( stores != null ) && ( stores.length > 0 ) ) {
      
      List< IFileStore > list = new ArrayList< IFileStore >();
      
      for ( IFileStore store : stores ) {
        if ( ( store.fetchInfo().exists() || ! hasStyle( STYLE_ALLOW_ONLY_EXISTING ) ) 
            && ( ( ! hasStyle( STYLE_ALLOW_ONLY_FILES ) && ! hasStyle( STYLE_ALLOW_ONLY_FOLDERS ) )
                || ( hasStyle( STYLE_ALLOW_ONLY_FILES ) && ! store.fetchInfo().isDirectory() )
                || ( hasStyle( STYLE_ALLOW_ONLY_FOLDERS ) && store.fetchInfo().isDirectory() ) ) ) {
          list.add( store );
        }
      }
      
      if ( ! list.isEmpty() ) {
        result = list.toArray( new IFileStore[ list.size() ] );
      }
      
    }
    
    return result;
    
  }
  
  /**
   * Get all currently selected {@link URI}s.
   * 
   * @return Array containing the {@link URI}s of all selected elements
   * or <code>null</code> if no selection is available.
   */
  public URI[] getSelectedURIs() {
    
    URI[] result = null;
    IFileStore[] stores = getSelectedFileStores();
    
    if ( ( stores != null ) && ( stores.length > 0 ) ) {
      result = new URI[ stores.length ];
      for ( int i = 0 ; i < stores.length ; i++ ) {
        URI uri = stores[ i ].toURI();
        GEclipseURI gUri = new GEclipseURI( uri );
        result[ i ] = gUri.toSlaveURI();
      }
    }
    
    return result;
    
  }
  
  /**
   * Configure the tree viewer's filters according to the dialog's style.
   */
  protected void configureViewerFilters() {
    
    if ( this.treeViewer != null ) {
      
      this.treeViewer.resetFilters();
    
      if ( hasStyle( STYLE_ALLOW_ONLY_FOLDERS ) ) {
        this.treeViewer.addFilter( new FolderFilter() );
      }
      
      if ( hasStyle( STYLE_ALLOW_ONLY_REMOTE_CONNECTIONS ) ) {
        this.treeViewer.addFilter( new RemoteConnectionFilter() );
      }
      
      if ( this.filetypeCombo != null ) {
        
        if ( this.filetypeCombo.getItemCount() != this.filetypeFilters.size() ) {
          initializeFileTypeCombo();
        }
        
        String key = this.filetypeCombo.getText();
        FileTypeFilter filter = this.filetypeFilters.get( key );
        if ( filter != null ) {
          this.treeViewer.addFilter( filter );
        }
        
      }
      
    }
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createDialogArea( final Composite parent ) {
    
    this.modeManager = new ModeManager();
    this.modeManager.addModeChangeListener( new IModeChangeListener() {
      public void modeChanged( final int mode ) {
        setMode( mode );
      }
    } );
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    gData.widthHint = 400;
    gData.heightHint = 400;
    mainComp.setLayoutData( gData );
    
    if ( ! hasStyle( STYLE_MULTI_SELECTION ) ) {
      
      Composite uriComp = new Composite( mainComp, SWT.NONE );
      uriComp.setLayout( new GridLayout( 2, false ) );
      gData = new GridData( GridData.FILL_HORIZONTAL );
      gData.grabExcessHorizontalSpace = true;
      gData.horizontalSpan = 2;
      uriComp.setLayoutData( gData );
      
      Label uriLabel = new Label( uriComp, SWT.NONE );
      uriLabel.setText( Messages.getString("GridFileDialog.label_URI") ); //$NON-NLS-1$
      gData = new GridData();
      uriLabel.setLayoutData( gData );
      
      this.uriCombo = new StoredCombo( uriComp, SWT.NONE );
      gData = new GridData( GridData.FILL_HORIZONTAL );
      gData.grabExcessHorizontalSpace = true;
      this.uriCombo.setLayoutData( gData );
      this.uriCombo.setEnabled( ! hasStyle( STYLE_ALLOW_ONLY_EXISTING ) );
      
    }
    
    if ( ! hasStyle( STYLE_ALLOW_ONLY_CONNECTIONS | STYLE_ALLOW_ONLY_REMOTE_CONNECTIONS ) ) {
    
      ToolBar modeBar = new ToolBar( mainComp, SWT.VERTICAL | SWT.BORDER );
      modeBar.setBackground( getShell().getDisplay().getSystemColor( SWT.COLOR_WHITE ) );
      gData = new GridData( GridData.FILL_VERTICAL );
      gData.grabExcessVerticalSpace = true;
      modeBar.setLayoutData( gData );
      
      if ( ! hasStyle( STYLE_ALLOW_ONLY_LOCAL ) ) {
        
        URL connURL = Activator.getDefault().getBundle()
                        .getResource( "icons/extras/grid_file_dialog_conn_mode.gif" ); //$NON-NLS-1$
        ImageDescriptor connDesc = ImageDescriptor.createFromURL( connURL );
        
        ToolItem connItem = new ToolItem( modeBar, SWT.CHECK );
        connItem.setImage( connDesc.createImage() );
        connItem.setToolTipText( Messages.getString("GridFileDialog.switch_to_connections") ); //$NON-NLS-1$
        this.modeManager.addModeItem( connItem, ModeManager.CONNECTION_MODE );
        
      }
      
      if ( ! hasStyle( STYLE_ALLOW_ONLY_CONNECTIONS | STYLE_ALLOW_ONLY_REMOTE_CONNECTIONS ) ) {

        URL homeURL = Activator.getDefault().getBundle()
                        .getResource( "icons/extras/grid_file_dialog_home_mode.gif" ); //$NON-NLS-1$
        ImageDescriptor homeDesc = ImageDescriptor.createFromURL( homeURL );
        
        ToolItem homeItem = new ToolItem( modeBar, SWT.CHECK );
        homeItem.setImage( homeDesc.createImage() );
        homeItem.setToolTipText( Messages.getString("GridFileDialog.switch_to_home") ); //$NON-NLS-1$
        this.modeManager.addModeItem( homeItem, ModeManager.HOME_MODE );
        
        URL wsURL = Activator.getDefault().getBundle()
                      .getResource( "icons/extras/grid_file_dialog_ws_mode.gif" ); //$NON-NLS-1$
        ImageDescriptor wsDesc = ImageDescriptor.createFromURL( wsURL );
        
        ToolItem wsItem = new ToolItem( modeBar, SWT.CHECK );
        wsItem.setImage( wsDesc.createImage() );
        wsItem.setToolTipText( Messages.getString("GridFileDialog.switch_to_workspace") ); //$NON-NLS-1$
        this.modeManager.addModeItem( wsItem, ModeManager.WS_MODE );
        
        URL rootURL = Activator.getDefault().getBundle()
                        .getResource( "icons/extras/grid_file_dialog_root_mode.gif" ); //$NON-NLS-1$
        ImageDescriptor rootDesc = ImageDescriptor.createFromURL( rootURL );
        
        ToolItem rootItem = new ToolItem( modeBar, SWT.CHECK );
        rootItem.setImage( rootDesc.createImage() );
        rootItem.setToolTipText( Messages.getString("GridFileDialog.switch_to_root") ); //$NON-NLS-1$
        this.modeManager.addModeItem( rootItem, ModeManager.ROOT_MODE );
      
      }
      
    }
    
    Composite browserComp = new Composite( mainComp, SWT.NONE );
    GridLayout browserLayout = new GridLayout( 1, false );
    browserLayout.marginWidth = 0;
    browserLayout.marginHeight = 0;
    browserComp.setLayout( browserLayout );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    browserComp.setLayoutData( gData );
    
    int treeStyle = SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER;
    if ( hasStyle( STYLE_MULTI_SELECTION ) ) {
      treeStyle |= SWT.MULTI;
    } else {
      treeStyle |= SWT.SINGLE;
    }
    
    this.treeViewer = new TreeViewer( browserComp, treeStyle );
    NewGridModelLabelProvider lProvider = new NewGridModelLabelProvider();
    lProvider.addColumn( 0, FileStoreLabelProvider.COLUMN_TYPE_NAME );
    lProvider.addColumn( 1, FileStoreLabelProvider.COLUMN_TYPE_SIZE );
    lProvider.addColumn( 2, FileStoreLabelProvider.COLUMN_TYPE_MOD_DATE );
    this.treeViewer.setContentProvider( new GridFileDialogContentProvider() );
    this.treeViewer.setLabelProvider( lProvider );
    
    Tree tree = this.treeViewer.getTree();
    tree.setHeaderVisible( true );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    tree.setLayoutData( gData );
    
    TreeColumn nameColumn = new TreeColumn( tree, SWT.NONE );
    nameColumn.setText( Messages.getString("GridFileDialog.column_title_name") ); //$NON-NLS-1$
    nameColumn.setAlignment( SWT.LEFT );
    nameColumn.setWidth( 300 );
    
    TreeColumn sizeColumn = new TreeColumn( tree, SWT.NONE );
    sizeColumn.setText( Messages.getString("GridFileDialog.column_title_size") ); //$NON-NLS-1$
    sizeColumn.setAlignment( SWT.RIGHT );
    sizeColumn.setWidth( 100 );
    
    TreeColumn modColumn = new TreeColumn( tree, SWT.NONE );
    modColumn.setText( Messages.getString("GridFileDialog.column_title_last_modification") ); //$NON-NLS-1$
    modColumn.setAlignment( SWT.CENTER );
    modColumn.setWidth( 200 );
    
    TreeColumnListener columnListener = new TreeColumnListener( this.treeViewer );
    for ( TreeColumn column : tree.getColumns() ) {
      column.addSelectionListener( columnListener );
    }
    
    tree.setSortColumn( nameColumn );
    tree.setSortDirection( SWT.UP );
    this.treeViewer.setComparator( new TreeColumnComparator( nameColumn ) );
    
    Composite fileComp = new Composite( browserComp, SWT.NONE );
    fileComp.setLayout( new GridLayout( 2, false ) );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    fileComp.setLayoutData( gData );
    
    if ( ! hasStyle( STYLE_MULTI_SELECTION ) ) {
      
      Label filenameLabel = new Label( fileComp, SWT.NONE );
      filenameLabel.setText(
          hasStyle( STYLE_ALLOW_ONLY_FOLDERS )
          ? Messages.getString("GridFileDialog.label_foldername") //$NON-NLS-1$
              : hasStyle( STYLE_ALLOW_ONLY_FILES )
              ? Messages.getString("GridFileDialog.label_filename") //$NON-NLS-1$
                  : Messages.getString("GridFileDialog.label_name") //$NON-NLS-1$
      );
      gData = new GridData();
      gData.horizontalAlignment = GridData.BEGINNING;
      filenameLabel.setLayoutData( gData );

      this.filenameCombo = new StoredCombo( fileComp, SWT.BORDER );
      gData = new GridData( GridData.FILL_HORIZONTAL );
      gData.grabExcessHorizontalSpace = true;
      this.filenameCombo.setLayoutData( gData );
      this.filenameCombo.setEnabled( ! hasStyle( STYLE_ALLOW_ONLY_EXISTING ) );
      
    }
    
    if ( ! hasStyle( STYLE_ALLOW_ONLY_FOLDERS ) ) {
    
      Label filetypeLabel = new Label( fileComp, SWT.NONE );
      filetypeLabel.setText( Messages.getString("GridFileDialog.label_filetype") ); //$NON-NLS-1$
      gData = new GridData();
      gData.horizontalAlignment = GridData.BEGINNING;
      filetypeLabel.setLayoutData( gData );
      
      this.filetypeCombo = new Combo( fileComp, SWT.BORDER | SWT.READ_ONLY );
      gData = new GridData( GridData.FILL_HORIZONTAL );
      gData.grabExcessHorizontalSpace = true;
      this.filetypeCombo.setLayoutData( gData );
      
    }
    
    int mode
      = ! hasStyle( STYLE_ALLOW_ONLY_LOCAL )
      ? ModeManager.CONNECTION_MODE
      : ModeManager.HOME_MODE; 
    this.modeManager.setMode( mode );
    setMode( mode );
    
    this.treeViewer.addDoubleClickListener( new IDoubleClickListener() {
      public void doubleClick( final DoubleClickEvent event ) {
        handleDoubleClick();
      }
    } );
    
    this.selectionListener = new ISelectionChangedListener() {
      public void selectionChanged( final SelectionChangedEvent event ) {
        setNotificationEnabled( false );
        handleSelectionChanged( ( IStructuredSelection ) event.getSelection() );
        setNotificationEnabled( true );
      }
    };
    this.treeViewer.addSelectionChangedListener( this.selectionListener );
    
    if ( this.uriCombo != null ) {
      this.uriListener = new ModifyListener() {
        public void modifyText( final ModifyEvent e ) {
          setNotificationEnabled( false );
          handleUriChanged();
          setNotificationEnabled( true );
        }
      };
      this.uriCombo.addModifyListener( this.uriListener );
    }
    
    if ( this.filenameCombo != null ) {
      this.filenameListener = new ModifyListener() {
        public void modifyText( final ModifyEvent e ) {
          setNotificationEnabled( false );
          handleFilenameChanged();
          setNotificationEnabled( true );
        }
      };
      this.filenameCombo.addModifyListener( this.filenameListener );
    }
    
    if ( this.filetypeCombo != null ) {
      this.filetypeCombo.addSelectionListener( new SelectionAdapter() {
        @Override
        public void widgetSelected( final SelectionEvent e ) {
          configureViewerFilters();
        }
      } );
    }
    
    this.modelListener = new IGridModelListener() {
      public void gridModelChanged( final IGridModelEvent event ) {
        if ( ( event.getType() == IGridModelEvent.ELEMENTS_ADDED )
            || ( event.getType() == IGridModelEvent.ELEMENTS_REMOVED ) ) {
          refreshViewer( event.getSource() );
        }
      }
    };
    
    GridModel.getRoot().addGridModelListener( this.modelListener );
    
    setTitle( Messages.getString("GridFileDialog.title") ); //$NON-NLS-1$
    setMessage( Messages.getString("GridFileDialog.message_select_your_files") ); //$NON-NLS-1$
    addFileTypeFilter( new FileTypeFilter(),
                       Messages.getString("GridFileDialog.label_all_files") ); //$NON-NLS-1$
    if ( this.filenameCombo != null ) {
      this.filetypeCombo.select( 0 );
    }
    
    return mainComp;
    
  }
  
  /**
   * Handler method for handling double clicks in the {@link TreeViewer}. This
   * handler expands or collapses tree nodes if the associated
   * element is a folder or selects an element and
   * closes the dialog if the associated element is a file.
   */
  protected void handleDoubleClick() {
    IStructuredSelection selection = ( IStructuredSelection ) this.treeViewer.getSelection();
    Object object = selection.getFirstElement();
    if ( this.treeViewer.isExpandable( object ) ) {
      boolean state = this.treeViewer.getExpandedState( object );
      this.treeViewer.setExpandedState( object, ! state );
    } else {
      if ( validate() ) {
        setReturnCode( IDialogConstants.OK_ID );
        close();
      }
    }
  }
  
  /**
   * Handler that handles changes in the uri combo.
   */
  protected void handleUriChanged() {
    
    setErrorMessage( null );
    
    try {
      URI uri = new URI( this.uriCombo.getText() );
      setSelectedURI( uri, false, true );
      validate();
    } catch ( URISyntaxException uriExc ) {
      this.filenameCombo.setText( EMPTY_STRING );
      setErrorMessage( Messages.getString("GridFileDialog.error_invalid_URI") //$NON-NLS-1$
                       + uriExc.getLocalizedMessage() );
    }
    
  }

  /**
   * Handler that handles changes in the filename combo.
   */
  protected void handleFilenameChanged() {

    setErrorMessage( null );
    
    try {
      URI uri = new URI( this.uriCombo.getText() );
      IPath path = new Path( uri.getPath() );
      path = path.removeLastSegments( 1 ).append( this.filenameCombo.getText() );
      String spath = path.toString();
      if ( ! spath.startsWith( PATH_SEPARATOR ) ) {
        spath = PATH_SEPARATOR + spath;
      }
      uri = new URI(
          uri.getScheme(),
          uri.getUserInfo(),
          uri.getHost(),
          uri.getPort(),
          spath,
          uri.getQuery(),
          uri.getFragment()
      );
      setSelectedURI( uri, true, false );
      validate();
    } catch ( URISyntaxException uriExc ) {
      setErrorMessage( "Invalid URI - " + uriExc.getLocalizedMessage() ); //$NON-NLS-1$
    }

  }
  
  protected void handleSelectionChanged( final IStructuredSelection selection ) {
    this.currentSelection = selection;
    if ( ! this.currentSelection.isEmpty() ) {
      IFileStore[] stores = getSelectedFileStores();
      setSelectedStore( ( ( stores != null ) && ( stores.length > 0 ) )
                          ? stores[ 0 ]
                          : null, true, true );
    }
    validate();
  }
  
  protected void setMode( final int mode ) {
    switch ( mode ) {
      case ModeManager.CONNECTION_MODE:
        this.treeViewer.setInput( GridModel.getConnectionManager() );
        break;
      case ModeManager.ROOT_MODE:
        this.treeViewer.setInput( EFS.getLocalFileSystem().getStore( new Path( ROOT_FOLDER ) ) );
        break;
      case ModeManager.HOME_MODE:
        String home = System.getProperty( HOME_PROPERTY );
        this.treeViewer.setInput( EFS.getLocalFileSystem().getStore( new Path( home ) ) );
        break;
      case ModeManager.WS_MODE:
        this.treeViewer.setInput( GridModel.getRoot() );
        break;
    }
    validate();
  }
  
  /**
   * Refreshes the {@link TreeViewer} starting with the specified element. If
   * the element is <code>null</code> the whole {@link TreeViewer} will be
   * refreshed.
   * 
   * @param element The {@link IGridElement} that will be refreshed. This also
   *            includes the element's children.
   */
  protected void refreshViewer( final IGridElement element ) {
    Control control = this.treeViewer.getControl();
    if ( ! control.isDisposed() ) {
      Display display = control.getDisplay();
      display.asyncExec( new Runnable() {
        public void run() {
          if ( ! GridFileDialog.this.treeViewer.getControl().isDisposed() ) {
            if ( element == null ) {
              GridFileDialog.this.treeViewer.refresh( false );
            } else {
              if ( element instanceof IGridContainer ) {
                IGridContainer container = ( IGridContainer ) element;
                if ( container.isLazy() && container.isDirty() ) {
                  GridFileDialog.this.treeViewer.setChildCount( container, container.getChildCount() );
                }
              }
              GridFileDialog.this.treeViewer.refresh( element, false );
            }
          }
        }
      } );
    }
  }
  
  /**
   * Add the specified {@link FileTypeFilter} to this dialog's filters.
   * 
   * @param filter The filter to be added.
   * @param description A description of the filter set as refering text
   * in the type filter combo.
   */
  private void addFileTypeFilter( final FileTypeFilter filter,
                                  final String description ) {
    this.filetypeFilters.put( description, filter );
    configureViewerFilters();
  }

  
  /**
   * Helper method to determine if this dialog was constructed with
   * the specified style.
   * 
   * @param bit One of the style constants.
   * @return True if the specified style constant was specified for
   * this dialog.
   */
  private boolean hasStyle( final int bit ) {
    return ( this.style & bit ) != 0; 
  }
  
  /**
   * Initialize the file type combo with the available file type filters.
   */
  private void initializeFileTypeCombo() {
    if ( this.filetypeCombo != null ) {
      this.filetypeCombo.removeAll();
      Set< String > keySet = this.filetypeFilters.keySet();
      String[] keyArray = keySet.toArray( new String[ keySet.size() ] );
      Arrays.sort( keyArray, new Comparator< String >() {
        public int compare( final String s1, final String s2 ) {
          String p1 = GridFileDialog.this.filetypeFilters.get( s1 ).getPrefix();
          String p2 = GridFileDialog.this.filetypeFilters.get( s2 ).getPrefix();
          return p1.compareToIgnoreCase( p2 );
        }
      } );
      this.filetypeCombo.setItems( keyArray );
      this.filetypeCombo.select( 0 );
    }
  }
  
  /**
   * Check if there are not ambiguities in the dialog's style.
   */
  private void assertStyle() {
    Assert.isTrue( ! (
        ( hasStyle( STYLE_ALLOW_ONLY_LOCAL ) && hasStyle( STYLE_ALLOW_ONLY_CONNECTIONS ) )
        ||
        ( hasStyle( STYLE_ALLOW_ONLY_LOCAL ) && hasStyle( STYLE_ALLOW_ONLY_REMOTE_CONNECTIONS ) )
        ||
        ( hasStyle( STYLE_ALLOW_ONLY_CONNECTIONS ) && hasStyle( STYLE_ALLOW_ONLY_REMOTE_CONNECTIONS ) )
    ), "Only one of STYLE_ALLOW_ONLY_LOCAL, STYLE_ALLOW_ONLY_CONNECTIONS" //$NON-NLS-1$
         + " and STYLE_ALLOW_ONLY_REMOTE_CONNECTIONS is allowed" //$NON-NLS-1$
    );
    Assert.isTrue( ! ( hasStyle( STYLE_ALLOW_ONLY_FILES ) && hasStyle( STYLE_ALLOW_ONLY_FOLDERS ) ),
        "Only one of STYLE_ALLOW_ONLY_FILES and STYLE_ALLOW_ONLY_FOLDERS is allowed" //$NON-NLS-1$
    );
  }
  
  /**
   * Get all currently selected {@link IFileStore}s.
   * 
   * @return Array containing the {@link IFileStore}s of all selected elements
   * or <code>null</code> if no selection is available.
   */
  private IFileStore[] internalGetSelectedFileStores() {
    
    IFileStore[] result = null;
    
    if ( ( this.currentSelection != null ) && ! this.currentSelection.isEmpty() ) {
      
      List< IFileStore > list = new ArrayList< IFileStore >();
      Iterator< ? > iterator = this.currentSelection.iterator();
      
      while ( iterator.hasNext() ) {
        
        Object o = iterator.next();
        
        if ( o instanceof IGridConnectionElement ) {
          try {
            o = ( ( IGridConnectionElement ) o ).getConnectionFileStore();
          } catch ( CoreException cExc ) {
            // Silently ignored
          }
        } else if ( o instanceof IGridElement ) {
          o = ( ( IGridElement ) o ).getFileStore();
        }
        
        if ( o instanceof IFileStore ) {
          list.add( ( IFileStore ) o );
        }
        
      }
      
      if ( ! list.isEmpty() ) {
        result = list.toArray( new IFileStore[ list.size() ] );
      }
      
    }
    
    return result;
    
  }
  
  /**
   * Set the specified {@link IFileStore} as selected. This method
   * updates the uri and the filename combo if the corresponding flags
   * are <code>true</code>.
   * 
   * @param store The store to the selected.
   * @param updateURI If <code>true</code> the uri combo is updated.
   * @param updateFilename If <code>true</code> the filename combo is updated.
   */
  private void setSelectedStore( final IFileStore store,
                                 final boolean updateURI,
                                 final boolean updateFilename ) {
    
    URI uri = null;
    
    if ( store != null ) {
      uri = store.toURI();
      GEclipseURI guri = new GEclipseURI( uri );
      uri = guri.toSlaveURI();
    }
    
    if ( updateURI && ( this.uriCombo != null ) ) {
      if ( uri == null ) {
        this.uriCombo.setText( EMPTY_STRING );
      } else {
        this.uriCombo.setText( uri.toString() );
      }
    }
    
    if ( updateFilename && ( this.filenameCombo != null ) ) {
      if ( ( uri == null )
          || ( ( store != null )
          && ( ( hasStyle( STYLE_ALLOW_ONLY_FILES ) && store.fetchInfo().isDirectory() )
              || ( hasStyle( STYLE_ALLOW_ONLY_FOLDERS ) && ! store.fetchInfo().isDirectory() ) ) ) ) {
        this.filenameCombo.setText( EMPTY_STRING );
      } else {
        IPath path = new Path( uri.getPath() );
        String fd = path.lastSegment();
        this.filenameCombo.setText( fd == null ? EMPTY_STRING : fd );
      }
    }
    
  }
  
  /**
   * Set the specified {@link URI} as selected. This method
   * updates the uri and the filename combo if the corresponding flags
   * are <code>true</code>.
   * 
   * @param uri The uri to the selected.
   * @param updateURI If <code>true</code> the uri combo is updated.
   * @param updateFilename If <code>true</code> the filename combo is updated.
   */
  private void setSelectedURI( final URI uri,
                               final boolean updateURI,
                               final boolean updateFilename ) {
    
    setErrorMessage( null );
    
    try {
      IFileStore store = EFS.getStore( uri );
      this.currentSelection = new StructuredSelection( store );
      setSelectedStore( store, updateURI, updateFilename );
    } catch ( CoreException cExc ) {
      setErrorMessage( "Invalid URI - " + cExc.getLocalizedMessage() ); //$NON-NLS-1$
    }
    
  }
  
  private boolean validate() {

    boolean valid = true;
    String errorMsg = null;
    setErrorMessage( null );
    IFileStore[] stores = internalGetSelectedFileStores();
    
    if ( ( stores == null ) || ( stores.length == 0 ) ) {
      valid = false;
    }
    
    else {
      
      for ( IFileStore store : stores ) {
        
        IFileInfo info = store.fetchInfo();
        
        boolean onlyExisting = hasStyle( STYLE_ALLOW_ONLY_EXISTING );
        boolean onlyDirs = hasStyle( STYLE_ALLOW_ONLY_FOLDERS );
        boolean onlyFiles = hasStyle( STYLE_ALLOW_ONLY_FILES );
        boolean exists = info.exists();
        boolean isDir = info.isDirectory();
        boolean isFile = ! info.isDirectory();
        
        if ( onlyExisting && ! exists ) {
          errorMsg = Messages.getString("GridFileDialog.error_only_existing"); //$NON-NLS-1$
        }
        
        else if ( onlyFiles && ! isFile ) {
          errorMsg = Messages.getString("GridFileDialog.error_only_files"); //$NON-NLS-1$
        }
        
        else if ( onlyDirs && ! isDir ) {
          errorMsg = Messages.getString("GridFileDialog.error_only_directories"); //$NON-NLS-1$
        }

      }
      
    }
    
    if ( errorMsg != null ) {
      valid = false;
      setErrorMessage( errorMsg );
    }
    
    Button button = getButton( IDialogConstants.OK_ID );
    if ( button != null ) {
      button.setEnabled( valid );
    }
    
    return valid;
    
  }
  
  /**
   * Enables or disables notifications about modifications of the uri
   * and filename combos and changes in the selection of the tree viewer.
   * 
   * @param b If <code>true</code> the notifications will be switched
   * on, if <code>false</code> they will be switched of.
   */
  protected void setNotificationEnabled( final boolean b ) {
    
    if ( b ) {
      
      if ( this.uriCombo != null ) {
        this.uriCombo.addModifyListener( this.uriListener );
      }
      
      if ( this.filenameCombo != null ) {
        this.filenameCombo.addModifyListener( this.filenameListener );
      }
      
      this.treeViewer.addSelectionChangedListener( this.selectionListener );
      
    }
    
    else {
   
      if ( this.uriCombo != null ) {
        this.uriCombo.removeModifyListener( this.uriListener );
      }
      
      if ( this.filenameCombo != null ) {
        this.filenameCombo.removeModifyListener( this.filenameListener );
      }
      
      this.treeViewer.removeSelectionChangedListener( this.selectionListener );
      
    }
    
  }
  
}
