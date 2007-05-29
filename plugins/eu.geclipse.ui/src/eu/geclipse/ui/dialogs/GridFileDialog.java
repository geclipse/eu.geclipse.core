package eu.geclipse.ui.dialogs;

import java.util.Iterator;
import org.eclipse.jface.action.Action;
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
import eu.geclipse.ui.internal.actions.NewConnectionAction;
import eu.geclipse.ui.internal.actions.ViewModeToggleAction;
import eu.geclipse.ui.providers.ConfigurableContentProvider;
import eu.geclipse.ui.providers.ConnectionViewContentProvider;
import eu.geclipse.ui.providers.ConnectionViewLabelProvider;
import eu.geclipse.ui.providers.IConfigurationListener;
import eu.geclipse.ui.views.ElementManagerViewPart;

public class GridFileDialog
    extends Dialog
    implements IGridModelListener {
  
  protected TreeViewer treeViewer;
  
  private GridConnectionFilter filter;
  
  private String title;
  
  private TreeColumn projectColumn;
  
  private int lastProjectColumnWidth = 100;
  
  private Text filenameText;
  
  private Combo filetypeCombo;
  
  private IGridConnectionElement selectedElement;
  
  public GridFileDialog( final Shell parent,
                         final String title ) {
    super( parent );
    setShellStyle( SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE );
    this.title = title;
    this.filter = new GridConnectionFilter();
  }
  
  public static IGridConnectionElement openFileDialog( final Shell parent,
                                                       final String title,
                                                       final String[] filteredFileExtensions ) {
    GridFileDialog dialog = new GridFileDialog( parent, title );
    dialog.setFilteredFileExtensions( filteredFileExtensions );
    dialog.open();
    return dialog.getSelectedElement();
  }
  
  @Override
  public boolean close() {
    IConnectionManager cManager = GridModel.getConnectionManager();
    cManager.removeGridModelListener( this );
    return super.close();
  }
  
  public void setFilteredFileExtensions( final String[] extensions ) {
    this.filter.clearFileExtensionFilters();
    if ( extensions != null ) {
      for ( String ext : extensions ) {
        this.filter.addFileExtensionFilter( ext );
      }
    }
  }
  
  @Override
  protected void configureShell( final Shell shell ) {
    super.configureShell(shell);
    shell.setText( this.title );
  }
  
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
    
    int treeStyle = SWT.VIRTUAL | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL;
    this.treeViewer = new TreeViewer( treeComp, treeStyle );
    ConnectionViewContentProvider cProvider
      = new ConnectionViewContentProvider();
    this.treeViewer.setContentProvider( cProvider );
    ConnectionViewLabelProvider lProvider
      = new ConnectionViewLabelProvider();
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
    nameColumn.setText( "Name" );
    nameColumn.setAlignment( SWT.LEFT );
    nameColumn.setWidth( 300 );
    
    this.projectColumn = new TreeColumn( tree, SWT.NONE );
    this.projectColumn.setText( "Project" );
    this.projectColumn.setAlignment( SWT.LEFT );
    this.projectColumn.setWidth( this.lastProjectColumnWidth );
    
    IWorkbenchWindow wWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
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
    filenameLabel.setText( "Filename:" );
    gData = new GridData();
    gData.horizontalAlignment = GridData.BEGINNING;
    filenameLabel.setLayoutData( gData );
    
    this.filenameText = new Text( fileComp, SWT.BORDER );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.filenameText.setLayoutData( gData );
    
    Label filetypeLabel = new Label( fileComp, SWT.NONE );
    filetypeLabel.setText( "Filetype:" );
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
    
    cManager.addGridModelListener( this );
    
    cProvider.addConfigurationListener( new IConfigurationListener() {
      public void configurationChanged( final ConfigurableContentProvider source ) {
        handleConfigurationChanged( source );
      }
    } );
    
    filter.link( this.treeViewer, this.filetypeCombo );
    
    return mainComp;
    
  }
  
  public IGridConnectionElement getSelectedElement() {
    return getReturnCode() == OK ? this.selectedElement : null;
  }
  
  public void gridModelChanged( final IGridModelEvent event ) {
    IGridElement source = event.getSource();
    refreshViewer( source );
  }
  
  protected void handleConfigurationChanged( final ConfigurableContentProvider provider ) {
    int mode = provider.getMode();
    if ( mode == ConfigurableContentProvider.MODE_FLAT ) {
      this.projectColumn.setWidth( this.lastProjectColumnWidth );
    } else {
      this.lastProjectColumnWidth = this.projectColumn.getWidth();
      this.projectColumn.setWidth( 0 );
    }
    refreshViewer();
  }
  
  protected void handleDoubleClick() {
    IStructuredSelection selection
      = ( IStructuredSelection ) this.treeViewer.getSelection();
    Object object
      = selection.getFirstElement();
    if ( this.treeViewer.isExpandable( object ) ) {
      boolean state = treeViewer.getExpandedState( object );
      treeViewer.setExpandedState( object, !state );
    } else if ( object instanceof IGridConnectionElement ) {
      IGridConnectionElement element
        = ( IGridConnectionElement ) object;
      if ( ! element.isFolder() ) {
        this.selectedElement = element;
        setReturnCode( OK );
        close();
      }
    }
  }
  
  protected void handleSelectionChanged( final ISelection selection ) {
    this.filenameText.setText( "" ); //$NON-NLS-1$
    this.selectedElement = null;
    if ( selection instanceof IStructuredSelection ) {
      IStructuredSelection sSelection
        = ( IStructuredSelection ) selection;
      Iterator< ? > iter = sSelection.iterator();
      while ( iter.hasNext() ) {
        Object object = iter.next();
        if ( object instanceof IGridConnectionElement ) {
          this.selectedElement
            = ( IGridConnectionElement ) object;
          if ( this.selectedElement.isFolder() ) {
            this.selectedElement = null;
          } else {
            String name = this.selectedElement.getName();
            this.filenameText.setText( name );
            break;
          }
        }
      }
    }
  }
  
  private void refreshViewer() {
    refreshViewer( null );
  }
  
  private void refreshViewer( final IGridElement element ) {
    Display display = this.treeViewer.getControl().getDisplay();
    display.syncExec( new Runnable() {
      public void run() {
        if ( element != null ) {
          if ( element instanceof IGridContainer ) {
            IGridContainer container = ( IGridContainer ) element;
            if ( container.isLazy() && container.isDirty() ) {
              GridFileDialog.this.treeViewer.setChildCount( element, 0 );
              GridFileDialog.this.treeViewer.setChildCount( element, 1 );
            }
          }
          GridFileDialog.this.treeViewer.refresh( element );
        } else {
          GridFileDialog.this.treeViewer.refresh();
        }
      }
    } );
  }
  
}
