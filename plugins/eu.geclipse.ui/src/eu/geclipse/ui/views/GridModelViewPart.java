/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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

package eu.geclipse.ui.views;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.DelegatingDragAdapter;
import org.eclipse.jface.util.DelegatingDropAdapter;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.part.ViewPart;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.ui.decorators.GridProjectFolderDecorator;
import eu.geclipse.ui.internal.actions.ActionGroupManager;
import eu.geclipse.ui.internal.actions.CommonActions;
import eu.geclipse.ui.internal.actions.FileActions;
import eu.geclipse.ui.internal.actions.MountActions;
import eu.geclipse.ui.internal.actions.OpenActions;
import eu.geclipse.ui.internal.actions.TreeViewerActions;
import eu.geclipse.ui.internal.transfer.SelectionTransferDragAdapter;
import eu.geclipse.ui.internal.transfer.SelectionTransferDropAdapter;

/**
 * Abstract superclass of all views that show
 * {@link eu.geclipse.core.model.GridModel} related data. Views that
 * extend this class mainly consist of a {@link StructuredViewer} and
 * may contribute actions to context menus for instance. The root
 * element of the viewer has to be an {@link IGridElement} that is returned
 * by the {@link #getRootElement()} method. Subclasses also have to
 * specified the content and label providers that are used to render
 * the data. These providers are specified by the
 * {@link #createContentProvider()} and {@link #createLabelProvider()}
 * methods.
 */
public abstract class GridModelViewPart
    extends ViewPart
    implements IGridModelListener {
  
  /**
   * The viewer that is used to render the data.
   */
  StructuredViewer viewer;
  
  private ActionGroup actions;
  
  private OpenActions openActions;
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
   */
  @Override
  public void createPartControl( final Composite parent ) {
    
    // Initialize the viewer
    this.viewer = createViewer( parent );
    initViewer( this.viewer );
    
    this.getSite().setSelectionProvider( this.viewer );
    
    this.actions = createActions();
    fillActionBars( this.actions );
    createContextMenu( this.viewer );
    
    GridModel.getRoot().addGridModelListener( this );
    
    updateActionBars();
  }
  
  private void updateActionBars() {
    if( this.actions != null) {
      ISelection selection = this.viewer.getSelection();
      this.actions.setContext(new ActionContext(selection));
      this.actions.updateActionBars();
    }
  }

  @Override
  public void dispose() {
    
    if ( this.actions != null ) {
      this.actions.dispose();
    }
    
    GridModel.getRoot().removeGridModelListener( this );
    
    super.dispose();
    
  }
  
  /**
   * Get the {@link StructuredViewer} that is associated with this
   * view.
   * 
   * @return The viewer that is responsible to show the content of this
   * view.
   */
  public StructuredViewer getViewer() {
    return this.viewer;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridModelListener#gridModelChanged(eu.geclipse.core.model.IGridModelEvent)
   */
  public void gridModelChanged( final IGridModelEvent event ) {
    if ( ( event.getType() == IGridModelEvent.ELEMENTS_ADDED )
        || ( event.getType() == IGridModelEvent.ELEMENTS_REMOVED ) ) {
      refreshViewer( event.getSource() );
    } else if ( event.getType() == IGridModelEvent.PROJECT_FOLDER_CHANGED ) {
      GridProjectFolderDecorator decorator
        = GridProjectFolderDecorator.getDecorator();
      if ( decorator != null ) {
        decorator.refresh( event.getElements() );
      }
    }
  }
  
  /**
   * Determines if drag'n'drop should be made available for the specified
   * element.
   * 
   * @param element The element for which drag'n'drop operations should
   * be either allowed or forbidden.
   * @return True if drag'n'drop should be enabled for the specified
   * element, false otherwise.
   */
  public boolean isDragSource( final IGridElement element ) {
    return !element.isVirtual();
  }
  
  /**
   * Refresh the associated {@link StructuredViewer}. Calls the
   * {@link StructuredViewer#refresh()} method and ensures that
   * this method is called in an UI thread.
   * 
   * @see StructuredViewer#refresh()
   */
  public void refreshViewer() {
    refreshViewer( null );
  }
  
  /**
   * Refresh the associated {@link StructuredViewer}. Calls the
   * {@link StructuredViewer#refresh(Object)} method and ensures that
   * this method is called in an UI thread.
   * 
   * @param element The element that should be refreshed recursively.
   * @see StructuredViewer#refresh(Object)
   */
  public void refreshViewer( final IGridElement element ) {
    if ( this.viewer != null ) {
      Control control = this.viewer.getControl();
      if ( ! control.isDisposed() ) {
        Display display = control.getDisplay();
        display.asyncExec( new Runnable() {
          public void run() {
            if ( ! GridModelViewPart.this.viewer.getControl().isDisposed() ) {
              if ( element == null ) {
                GridModelViewPart.this.viewer.refresh( false );
              } else {
                GridModelViewPart.this.viewer.refresh( element, false );
              }
            }
          }
        } );
      }
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
   */
  @Override
  public void setFocus() {
    this.viewer.getControl().setFocus();
  }
  
  protected void addDragSourceListeners( final DelegatingDragAdapter adapter ) {
    adapter.addDragSourceListener( new SelectionTransferDragAdapter( this ) );
  }
  
  protected void addDropTargetListeners( final DelegatingDropAdapter adapter ) {
    adapter.addDropTargetListener( new SelectionTransferDropAdapter() );
  }
  
  protected ActionGroup createActions() {
    ActionGroupManager manager = new ActionGroupManager();
    contributeAdditionalActions( manager );
    contributeStandardActions( manager );
    return manager;
  }
  
  protected void contributeStandardActions( final ActionGroupManager groups ) {
    
    this.openActions = new OpenActions( this );
    groups.addGroup( this.openActions );
    
    FileActions fileActions = new FileActions( this );
    groups.addGroup( fileActions );
    
    MountActions mountActions = new MountActions( getSite() );
    groups.addGroup( mountActions );
     
    CommonActions commonActions = new CommonActions( this );
    groups.addGroup( commonActions );
    
    StructuredViewer sViewer = getViewer();
    if ( sViewer instanceof TreeViewer ) {
      TreeViewer tViewer = ( TreeViewer ) sViewer;
      TreeViewerActions treeViewerActions
        = new TreeViewerActions( tViewer );
      groups.addGroup( treeViewerActions );
    }
  }
  
  protected void contributeAdditionalActions( @SuppressWarnings("unused")
                                              final ActionGroupManager groups ) {
    // empty implementation
  }
  
  /**
   * Create and return an instance of an {@link IContentProvider} that is
   * used to provide the content to be rendered in the viewer. This method
   * is called once on initialization and may not be called subsequently.
   * 
   * @return The newly created {@link IContentProvider} that is responsible
   * for providing the data rendered in the associated {@link StructuredViewer}.
   * @see #createViewer(Composite)
   * @see #createLabelProvider()
   */
  protected abstract IContentProvider createContentProvider();
  
  /**
   * Create the context menu for the specified viewer. This is the viewer obtained
   * by {@link #createContentProvider()}. This method is called
   * automatically by {@link #createPartControl(Composite)}.
   * @param sViewer The {@link StructuredViewer} to be initialised.
   * @see #createViewer(Composite)
   * @see #createPartControl(Composite)
   */
  protected void createContextMenu( final StructuredViewer sViewer ) {
    MenuManager manager = new MenuManager();
    manager.setRemoveAllWhenShown( true );
    manager.addMenuListener( new IMenuListener() {
      public void menuAboutToShow( final IMenuManager mgr ) {
        fillContextMenu( mgr );
      }
    } );
    Menu menu = manager.createContextMenu( sViewer.getControl() );
    sViewer.getControl().setMenu(menu);
    getSite().registerContextMenu( manager, sViewer );
  }
  
  /**
   * Create the standard menu groups in the specified context
   * menu. Subclasses may overwrite this method in order to add
   * new groups or to completely change the groups.
   *  
   * @param menu The menu manager in which to create the groups.
   */
  protected void createContextMenuGroups( final IMenuManager menu ) {
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
  
  /**
   * Create and return an instance of an {@link IBaseLabelProvider} that is
   * used to render the content in the viewer. This method
   * is called once on initialization and may not be called subsequently.
   * 
   * @return The newly created {@link IBaseLabelProvider} that is responsible
   * for rendering the data in the associated {@link StructuredViewer}.
   * @see #createViewer(Composite)
   * @see #createContentProvider()
   */
  protected abstract IBaseLabelProvider createLabelProvider();
  
  /**
   * Create and return an instance of a {@link StructuredViewer} that is
   * used to render the data. This method
   * is called once on initialization and may not be called subsequently.
   * 
   * @param parent The parent composite.
   * @return The newly created {@link StructuredViewer}.
   * @see #createContentProvider()
   * @see #createLabelProvider()
   */
  protected abstract StructuredViewer createViewer( final Composite parent );
  
  /**
   * Fill the action bars of this view with the specified action group.
   * The group itself holds the functionality to fill the actions bars.
   * 
   * @param group The {@link ActionGroup} that is used to fill the
   * action bars.
   */
  protected void fillActionBars( final ActionGroup group ) {
    IActionBars actionBars = getViewSite().getActionBars();
    group.fillActionBars( actionBars );
  }
  
  /**
   * Fill the context menu from the actions of this view. This is
   * called interactively when the menu is about to be shown.
   * 
   * @param menu The menu to be filled.
   */
  protected void fillContextMenu( final IMenuManager menu ) {
    ISelection selection = this.viewer.getSelection();
    ActionContext context = new ActionContext( selection );
    createContextMenuGroups( menu );
    this.actions.setContext( context );
    this.actions.fillContextMenu( menu );
    this.actions.setContext( null );
  }
  
  /**
   * Get the root element that is used as the data for the structured
   * viewer.
   * 
   * @return The root data for the {@link StructuredViewer} of this
   * view.
   */
  protected abstract IGridElement getRootElement();
  
  /**
   * Handle a double click event that occurred in the viewer of this
   * view.
   * 
   * @param event The associated {@link DoubleClickEvent}.
   */
  protected void handleDoubleClick( @SuppressWarnings("unused")
                                    final DoubleClickEvent event ) {
    if ( this.viewer instanceof TreeViewer ) {
      TreeViewer treeViewer = ( TreeViewer ) this.viewer;
      ISelection selection = event.getSelection();
      if ( selection instanceof IStructuredSelection ) {
        Object element
          = ( ( IStructuredSelection ) selection ).getFirstElement();
        if ( treeViewer.isExpandable( element ) ) {
          boolean state = treeViewer.getExpandedState( element );
          treeViewer.setExpandedState( element, !state );
        }
      }
    }
  }
  
  /**
   * Handle an open event that occurred in the associated viewer.
   * 
   * @param event The associated {@link OpenEvent}.
   */
  protected void handleOpen( @SuppressWarnings("unused")
                             final OpenEvent event ) {
    this.openActions.delegateOpenEvent( event );
  }
  
  protected void initDragAndDrop( final StructuredViewer sViewer ) {
    initDrag( sViewer );
    initDrop( sViewer );
  }
  
  protected void initDrag( final StructuredViewer sViewer ) {
    int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
    DelegatingDragAdapter adapter = new DelegatingDragAdapter();
    addDragSourceListeners( adapter );
    sViewer.addDragSupport( operations, adapter.getTransfers(), adapter );
  }
  
  protected void initDrop( final StructuredViewer sViewer ) {
    int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
    DelegatingDropAdapter adapter = new DelegatingDropAdapter();
    addDropTargetListeners( adapter );
    sViewer.addDropSupport( operations, adapter.getTransfers(), adapter );
  }
  
  /**
   * Initialize the specified viewer. This is the viewer obtained
   * by {@link #createContentProvider()}. This method is called
   * automatically by {@link #createPartControl(Composite)}.
   * 
   * @param sViewer The {@link StructuredViewer} to be initialised.
   * @see #createViewer(Composite)
   * @see #createPartControl(Composite)
   */
  protected void initViewer( final StructuredViewer sViewer ) {
    sViewer.setLabelProvider( createLabelProvider() );
    sViewer.setContentProvider( createContentProvider() );
    sViewer.setInput( getRootElement() );
    registerViewerListeners( sViewer );
    initDragAndDrop( sViewer );
  }
  
  /**
   * Register the listeners for the specified viewer. This is the viewer obtained
   * by {@link #createContentProvider()}. This method is called
   * automatically by {@link #createPartControl(Composite)}.
   * 
   * @param sViewer The {@link StructuredViewer} to be initialised.
   * @see #createViewer(Composite)
   * @see #createPartControl(Composite)
   */
  protected void registerViewerListeners( final StructuredViewer sViewer ) {
    sViewer.addDoubleClickListener( new IDoubleClickListener() {
      public void doubleClick( final DoubleClickEvent event ) {
        handleDoubleClick( event );
      }
    } );
    sViewer.addOpenListener( new IOpenListener() {
      public void open( final OpenEvent event ) {
        handleOpen( event );
      }
    } );
  }

}
