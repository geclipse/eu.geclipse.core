package eu.geclipse.ui.views;

import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.part.ViewPart;
import eu.geclipse.core.connection.ConnectionManager;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.ui.internal.actions.GridProjectActions;
import eu.geclipse.ui.providers.GridModelContentProvider;
import eu.geclipse.ui.providers.GridModelLabelProvider;

public class GridProjectView extends ViewPart
    implements IResourceChangeListener, IContentChangeListener {
  
  protected TreeViewer treeViewer;
  
  private GridProjectActions actions;
  
  @Override
  public void dispose() {
    
    if ( this.actions != null ) {
      this.actions.dispose();
    }
    
    ResourcesPlugin.getWorkspace().removeResourceChangeListener( this );
    ConnectionManager.getManager().removeContentChangeListener( this );
    
    super.dispose();
 
  }

  @Override
  public void createPartControl( final Composite parent ) {
    
    IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
    
    this.treeViewer = new TreeViewer( parent, SWT.VIRTUAL | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER );
    this.getSite().setSelectionProvider( this.treeViewer );
      
    //LazyTreeContentProvider contentProvider = new LazyTreeContentProvider();
    /*GridProjectContentProvider contentProvider = new GridProjectContentProvider();//new LazyTreeContentProvider();
    contentProvider.setComparator( new FileStoreComparator() );*/
    GridModelContentProvider contentProvider = new GridModelContentProvider();
    this.treeViewer.setContentProvider( contentProvider );
    
    /*TreeNodeLabelProvider labelProvider = new TreeNodeLabelProvider();
    labelProvider.addProvider( new FileStoreLabelProvider() );
    labelProvider.addProvider( new ResourceLabelProvider() );*/
    GridModelLabelProvider labelProvider = new GridModelLabelProvider();
    this.treeViewer.setLabelProvider( labelProvider );
    
    this.treeViewer.setInput( GridModel.getRoot() );
    this.treeViewer.addDoubleClickListener( new IDoubleClickListener() {
      public void doubleClick( final DoubleClickEvent event ) {
        handleDoubleClick( event );
      }
    } );
    this.treeViewer.addOpenListener( new IOpenListener() {
      public void open( final OpenEvent event ) {
        handleOpen( event );
      }
    } );
    
    createActions();
    createContextMenu();
    
    ResourcesPlugin.getWorkspace().addResourceChangeListener( this );
    ConnectionManager.getManager().addContentChangeListener( this );
    
  }

  @Override
  public void setFocus() {
    this.treeViewer.getControl().setFocus();
  }
  
  /**
   * Fill the context menu belonging to the token table.
   * 
   * @param menu The manager that takes responsible for the context menu.
   */
  protected void fillContextMenu( final IMenuManager menu ) {
    createMenuGroups( menu );
    this.actions.setContext( new ActionContext( this.treeViewer.getSelection() ) );
    this.actions.fillContextMenu( menu );
    this.actions.setContext( null );
  }
  
  private void createMenuGroups( final IMenuManager menu ) {
    menu.add( new Separator( ICommonMenuConstants.GROUP_NEW ) );
    menu.add( new GroupMarker( ICommonMenuConstants.GROUP_GOTO ) );
    menu.add( new GroupMarker( ICommonMenuConstants.GROUP_OPEN ) );
    menu.add( new GroupMarker( ICommonMenuConstants.GROUP_OPEN_WITH ) );
    menu.add( new Separator( ICommonMenuConstants.GROUP_EDIT ) );
    menu.add( new GroupMarker( ICommonMenuConstants.GROUP_SHOW ) );
    menu.add( new GroupMarker( ICommonMenuConstants.GROUP_REORGANIZE ) );
    menu.add( new GroupMarker( ICommonMenuConstants.GROUP_PORT ) );
    menu.add( new Separator( ICommonMenuConstants.GROUP_GENERATE ) );
    menu.add( new Separator( ICommonMenuConstants.GROUP_SEARCH ) );
    menu.add( new Separator( ICommonMenuConstants.GROUP_BUILD ) );
    menu.add( new Separator( ICommonMenuConstants.GROUP_ADDITIONS ) );
    menu.add( new Separator( ICommonMenuConstants.GROUP_PROPERTIES ) );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
   */
  public void resourceChanged( final IResourceChangeEvent event ) {
    refresh();
  }

  public void contentChanged( final IContentChangeNotifier source ) {
    refresh();
  }
  
  protected void refresh() {
    if ( this.treeViewer != null ) {
      this.getSite().getShell().getDisplay().syncExec( new Runnable() {
        public void run() {
          GridProjectView.this.treeViewer.refresh();
        }
      } );
    }
  }
  
  protected void handleDoubleClick( final DoubleClickEvent event ) {
    ISelection selection = event.getSelection();
    if ( selection instanceof IStructuredSelection ) {
      Object element
        = ( ( IStructuredSelection ) selection ).getFirstElement();
      if ( this.treeViewer.isExpandable( element ) ) {
        boolean state = this.treeViewer.getExpandedState( element );
        this.treeViewer.setExpandedState( element, !state );
      }
    }
  }
  
  protected void handleOpen( final OpenEvent event ) {
    this.actions.delegateOpenEvent( event );
  }
  
  /**
   * Create the actions of this view.
   */
  private void createActions() {
    this.actions = new GridProjectActions( this );
  }
  
  /**
   * Create the context menu for the tree.
   */
  private void createContextMenu() {
    MenuManager manager = new MenuManager();
    manager.setRemoveAllWhenShown( true );
    manager.addMenuListener( new IMenuListener() {
      public void menuAboutToShow( final IMenuManager mgr ) {
        fillContextMenu( mgr );
      }
    } );
    Menu menu = manager.createContextMenu( this.treeViewer.getControl() );
    this.treeViewer.getControl().setMenu(menu);
    getSite().registerContextMenu( manager, this.treeViewer );
  }

}
