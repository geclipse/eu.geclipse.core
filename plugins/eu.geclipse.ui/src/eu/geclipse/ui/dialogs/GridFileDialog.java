/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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

import java.util.Iterator;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IConnectionManager;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.ui.internal.GridConnectionFilter;
import eu.geclipse.ui.internal.GridConnectionProtocolFilter;
import eu.geclipse.ui.internal.actions.NewConnectionAction;
import eu.geclipse.ui.internal.actions.ViewModeToggleAction;
import eu.geclipse.ui.providers.ConfigurableContentProvider;
import eu.geclipse.ui.providers.ConnectionViewContentProvider;
import eu.geclipse.ui.providers.ConnectionViewLabelProvider;
import eu.geclipse.ui.providers.IConfigurationListener;

/**
 * This is an implementation of a file dialog for browsing remote connections
 * via EFS that are defined in the {@link IConnectionManager}. The dialog allows
 * to select a remote file and also provides functionality to filter files by
 * there file extensions.
 */
public class GridFileDialog extends Dialog implements IGridModelListener {

  private static final String LOCAL_FILTER = "file"; //$NON-NLS-1$
  /**
   * The {@link TreeViewer} used to display the remote file structure.
   */
  protected TreeViewer treeViewer;
  /**
   * A filter used to filter the displayed files by there file extensions.
   */
  private GridConnectionFilter filter;
  /**
   * The dialog's title.
   */
  private String title;
  /**
   * The {@link TreeColumn} used to display the associated project of a
   * connection.
   */
  private TreeColumn projectColumn;
  /**
   * Field used to temporarily store the project column's width.
   */
  private int lastProjectColumnWidth = 100;
  /**
   * The text field containing the selected file's name.
   */
  private Text filenameText;
  /**
   * The combo box used to specify the currently applied file extension filter.
   */
  private Combo filetypeCombo;
  /**
   * The currently selected connection element.
   */
  private IGridConnectionElement selectedElement;
  private GridConnectionProtocolFilter protocolFilter;

  /**
   * Create a new <code>GridFileDialog</code> with the specified parent
   * {@link Shell} and the specified dialog title.
   * 
   * @param parent The parent {@link Shell} of this dialog.
   * @param title The dialog's title.
   * @param allowLocal when <code>true</code> - both local and remote
   *            connections will be shown in dialog, if <code>false</code> -
   *            only remote connections
   */
  public GridFileDialog( final Shell parent,
                         final String title,
                         final boolean allowLocal )
  {
    super( parent );
    setShellStyle( SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE );
    this.title = title;
    this.filter = new GridConnectionFilter();
    if( !allowLocal ) {
//      this.protocolFilter = new ViewerFilter() {
//
//        @Override
//        public boolean select( final Viewer viewer,
//                               final Object parentElement,
//                               final Object element )
//        {
//          boolean result = true;
//          if( element instanceof IGridConnectionElement ) {
//            if( ( ( IGridConnectionElement )element ).isLocal() ) {
//              result = false;
//            }
//          }
//          return result;
//        }
//      };
       this.protocolFilter = new GridConnectionProtocolFilter();
       this.protocolFilter.addFilterProtocol( LOCAL_FILTER );
    }
  }

  /**
   * Create and open a new <code>GridFileDialog</code>. This static method
   * returns either the selected {@link IGridConnectionElement} if the dialog's
   * return status is <code>OK</code> or <code>null</code> if the dialog was
   * closed with cancel or with no element selected.
   * 
   * @param parent The parent {@link Shell} of this dialog.
   * @param title The dialog's title.
   * @param filteredFileExtensions A string array containing the applied
   *            filters. This array has to contain the filtered file extensions
   *            without the leading period character. It may also be
   *            <code>null</code>. In that case the wildcard filter is used
   *            and therefore all files are shown.
   * @param allowLocal when <code>true</code> - both local and remote
   *            connections will be shown in dialog, if <code>false</code> -
   *            only remote connections
   * @return The selected {@link IGridConnectionElement} if the dialog's return
   *         status was <code>OK</code> and a valid remote file was selected
   *         or <code>null</code>.
   */
  public static IGridConnectionElement openFileDialog( final Shell parent,
                                                       final String title,
                                                       final String[] filteredFileExtensions,
                                                       final boolean allowLocal )
  {
    GridFileDialog dialog = new GridFileDialog( parent, title, allowLocal );
    dialog.setFilteredFileExtensions( filteredFileExtensions );
    dialog.open();
    return dialog.getSelectedElement();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.Dialog#close()
   */
  @Override
  public boolean close() {
    GridModel.getRoot().removeGridModelListener( this );
    return super.close();
  }

  /**
   * Set the filters that are used to filter the displayed connections.
   * 
   * @param extensions A string array containing the applied filters. This array
   *            has to contain the filtered file extensions without the leading
   *            period character. It may also be <code>null</code>. In that
   *            case the wildcard filter is used and therefore all files are
   *            shown.
   */
  public void setFilteredFileExtensions( final String[] extensions ) {
    this.filter.clearFileExtensionFilters();
    if( extensions != null ) {
      for( String ext : extensions ) {
        this.filter.addFileExtensionFilter( ext );
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
   */
  @Override
  protected void configureShell( final Shell shell ) {
    super.configureShell( shell );
    shell.setText( this.title );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
   */
  @Override
  protected Control createDialogArea( final Composite parent ) {
    
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 1, false ) );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    gData.widthHint = 400;
    gData.heightHint = 400;
    mainComp.setLayoutData( gData );
    
    Composite treeComp = new Composite( mainComp, SWT.BORDER );
    GridLayout treeCompLayout = new GridLayout( 1, false );
    treeCompLayout.marginWidth = 0;
    treeCompLayout.marginHeight = 0;
    treeCompLayout.horizontalSpacing = 0;
    treeCompLayout.verticalSpacing = 0;
    treeComp.setLayout( treeCompLayout );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    treeComp.setLayoutData( gData );
    
    ToolBarManager tbManager = new ToolBarManager( SWT.FLAT );
    ToolBar toolBar = tbManager.createControl( treeComp );
    gData = new GridData();
    gData.horizontalAlignment = GridData.FILL;
    gData.verticalAlignment = GridData.BEGINNING;
    toolBar.setLayoutData( gData );
    
    int treeStyle = SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL;
    this.treeViewer = new TreeViewer( treeComp, treeStyle );
    ConnectionViewContentProvider cProvider = new ConnectionViewContentProvider();
    this.treeViewer.setContentProvider( cProvider );
    ConnectionViewLabelProvider lProvider = new ConnectionViewLabelProvider();
    this.treeViewer.setLabelProvider( lProvider );
    IConnectionManager cManager = GridModel.getConnectionManager();
    this.treeViewer.setInput( cManager );
    
    Tree tree = this.treeViewer.getTree();
    tree.setHeaderVisible( true );
    gData = new GridData( GridData.FILL_BOTH );
    gData.grabExcessHorizontalSpace = true;
    gData.grabExcessVerticalSpace = true;
    tree.setLayoutData( gData );
    
    TreeColumn nameColumn = new TreeColumn( tree, SWT.NONE );
    nameColumn.setText( Messages.getString( "GridFileDialog.name_column" ) ); //$NON-NLS-1$
    nameColumn.setAlignment( SWT.LEFT );
    nameColumn.setWidth( 300 );
    
    this.projectColumn = new TreeColumn( tree, SWT.NONE );
    this.projectColumn.setText( Messages.getString( "GridFileDialog.project_column" ) ); //$NON-NLS-1$
    this.projectColumn.setAlignment( SWT.LEFT );
    this.projectColumn.setWidth( this.lastProjectColumnWidth );
    
    IWorkbenchWindow wWindow = PlatformUI.getWorkbench()
      .getActiveWorkbenchWindow();
    IAction newConnectionAction = new NewConnectionAction( wWindow );
    tbManager.add( newConnectionAction );
    IAction modeAction = new ViewModeToggleAction( cProvider );
    tbManager.add( modeAction );
    tbManager.update( true );
    
    Composite fileComp = new Composite( mainComp, SWT.NONE );
    fileComp.setLayout( new GridLayout( 2, false ) );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    fileComp.setLayoutData( gData );
    
    Label filenameLabel = new Label( fileComp, SWT.NONE );
    filenameLabel.setText( Messages.getString( "GridFileDialog.filename_label" ) ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalAlignment = GridData.BEGINNING;
    filenameLabel.setLayoutData( gData );
    
    this.filenameText = new Text( fileComp, SWT.BORDER );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.filenameText.setLayoutData( gData );
    
    Label filetypeLabel = new Label( fileComp, SWT.NONE );
    filetypeLabel.setText( Messages.getString( "GridFileDialog.filetype_label" ) ); //$NON-NLS-1$
    gData = new GridData();
    gData.horizontalAlignment = GridData.BEGINNING;
    filetypeLabel.setLayoutData( gData );
    
    this.filetypeCombo = new Combo( fileComp, SWT.BORDER | SWT.READ_ONLY );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.filetypeCombo.setLayoutData( gData );
    
    this.treeViewer.addSelectionChangedListener( new ISelectionChangedListener() {
      public void selectionChanged( final SelectionChangedEvent event ) {
        ISelection selection = event.getSelection();
        handleSelectionChanged( selection );
      }
    } );
    this.treeViewer.addDoubleClickListener( new IDoubleClickListener() {
      public void doubleClick( final DoubleClickEvent event ) {
        handleDoubleClick();
      }
    } );
    GridModel.getRoot().addGridModelListener( this );
    cProvider.addConfigurationListener( new IConfigurationListener() {
      public void configurationChanged( final ConfigurableContentProvider source ) {
        handleConfigurationChanged( source );
      }
    } );
    
    this.filter.link( this.treeViewer, this.filetypeCombo );
    
    if( this.protocolFilter != null ) {
      this.treeViewer.addFilter( this.protocolFilter );
    }
    
    return mainComp;
    
  }

  /**
   * Get the currently selected {@link IGridConnectionElement} or
   * <code>null</code> if either no valid connection is selected or the
   * dialog's return code is not <code>OK</code>.
   * 
   * @return The selected {@link IGridConnectionElement} if the dialog's return
   *         status was <code>OK</code> and a valid remote file was selected
   *         or <code>null</code>.
   */
  public IGridConnectionElement getSelectedElement() {
    return getReturnCode() == OK
                                ? this.selectedElement
                                : null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridModelListener#gridModelChanged(eu.geclipse.core.model.IGridModelEvent)
   */
  public void gridModelChanged( final IGridModelEvent event ) {
    if ( ( event.getType() == IGridModelEvent.ELEMENTS_ADDED )
        || ( event.getType() == IGridModelEvent.ELEMENTS_REMOVED ) ) {
      refreshViewer( event.getSource() );
    }
  }

  /**
   * Handler method for handling configuration changes of the
   * {@link ConfigurableContentProvider} that is used to display the
   * connections.
   * 
   * @param provider The {@link ConfigurableContentProvider} that changed its
   *            configuration.
   */
  protected void handleConfigurationChanged( final ConfigurableContentProvider provider ) {
    int mode = provider.getMode();
    if( mode == ConfigurableContentProvider.MODE_FLAT ) {
      this.projectColumn.setWidth( this.lastProjectColumnWidth );
    } else {
      this.lastProjectColumnWidth = this.projectColumn.getWidth();
      this.projectColumn.setWidth( 0 );
    }
    refreshViewer();
  }

  /**
   * Handler method for handling double clicks in the {@link TreeViewer}. This
   * handler expands or collapses tree nodes if the associated
   * {@link IGridConnectionElement} is a folder or selects a connection and
   * closes the dialog if the associated connection is a file.
   */
  protected void handleDoubleClick() {
    IStructuredSelection selection = ( IStructuredSelection )this.treeViewer.getSelection();
    Object object = selection.getFirstElement();
    if( this.treeViewer.isExpandable( object ) ) {
      boolean state = this.treeViewer.getExpandedState( object );
      this.treeViewer.setExpandedState( object, !state );
    } else if( object instanceof IGridConnectionElement ) {
      IGridConnectionElement element = ( IGridConnectionElement )object;
      if( !element.isFolder() ) {
        this.selectedElement = element;
        setReturnCode( OK );
        close();
      }
    }
  }

  /**
   * Handler method that handles selection changes in the {@link TreeViewer}.
   * The handler searches for the first valid remote file in the selection. If
   * such a file is found the current selection of the dialog is changed and the
   * file text field is updated with the name of this file.
   * 
   * @param selection The new selection of the {@link TreeViewer}.
   */
  protected void handleSelectionChanged( final ISelection selection ) {
    this.filenameText.setText( "" ); //$NON-NLS-1$
    this.selectedElement = null;
    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection sSelection = ( IStructuredSelection )selection;
      Iterator<?> iter = sSelection.iterator();
      while( iter.hasNext() ) {
        Object object = iter.next();
        if( object instanceof IGridConnectionElement ) {
          this.selectedElement = ( IGridConnectionElement )object;
          if( this.selectedElement.isFolder() ) {
            this.selectedElement = null;
          } else {
            String name = this.selectedElement.getName();
            this.filenameText.setText( name );
          }
        }
      }
    }
  }

  /**
   * Refreshed the whole {@link TreeViewer}. This is fully equivalent to
   * <code>refreshViewer(null)</code>.
   */
  private void refreshViewer() {
    refreshViewer( null );
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
    Display display = this.treeViewer.getControl().getDisplay();
    display.asyncExec( new Runnable() {
      public void run() {
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
    } );
  }
  
}
