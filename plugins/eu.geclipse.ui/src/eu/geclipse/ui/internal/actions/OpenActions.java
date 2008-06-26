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

package eu.geclipse.ui.internal.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.search.ui.IContextMenuConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.actions.OpenWithMenu;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import eu.geclipse.ui.views.GridModelViewPart;

/**
 * Action group holding all available open actions.
 */
public class OpenActions extends ActionGroup {
  
  /**
   * Action for opening Grid model elements.
   */
  protected OpenElementAction openElementAction;
  
  protected OpenWithMenu openWithMenu;
  
  /**
   * The workbench site this action belongs to.
   */
  private IWorkbenchSite site;
  
  /**
   * Construct a new open action group for the specified workbench site.
   * 
   * @param part The {@link GridModelViewPart} for which to create this
   * open action group.
   */
  public OpenActions( final GridModelViewPart part ) {
    
    this.site = part.getSite();
    IWorkbenchPage page = this.site.getPage();
    ISelectionProvider provider = this.site.getSelectionProvider();
    
    this.openElementAction = new OpenElementAction( this.site );
    
    provider.addSelectionChangedListener( this.openElementAction );
    
    part.getViewer().getControl().addKeyListener( new KeyAdapter() {
      @Override
      public void keyPressed( final KeyEvent e ) {
        if ( e.keyCode == SWT.F3 ) {
          OpenActions.this.openElementAction.run();
        }
      }
    } );
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#dispose()
   */
  @Override
  public void dispose() {
    ISelectionProvider provider = this.site.getSelectionProvider();
    provider.removeSelectionChangedListener( this.openElementAction );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    
    if ( this.openElementAction.isEnabled() ) {
      
      menu.appendToGroup( ICommonMenuConstants.GROUP_OPEN, 
                          this.openElementAction );
      
      IAdaptable adaptable = getSelectedAdaptable();
      if ( adaptable != null ) {
        OpenWithMenu openWith = new OpenWithMenu( this.site.getPage(), adaptable );
        IMenuManager subMenu = new MenuManager( Messages.getString("OpenActions.open_with_menu_label") ); //$NON-NLS-1$
        menu.appendToGroup( IContextMenuConstants.GROUP_OPEN, subMenu );
        subMenu.add( openWith );
      }
      
    }
    
    super.fillContextMenu(menu);
    
  }
  
  /**
   * Delegate the specified {@link OpenEvent} to this action groups actions.
   * 
   * @param event The {@link OpenEvent} that should be delegated.
   */
  public void delegateOpenEvent( final OpenEvent event ) {
    ISelection selection = event.getSelection();
    if ( selection instanceof IStructuredSelection ) {
      IStructuredSelection sSelection = ( IStructuredSelection ) selection;
      Object element = sSelection.getFirstElement();
      if ( !(element instanceof IResource ) && ( element instanceof IAdaptable ) ) {
        element = ( ( IAdaptable ) element ).getAdapter( IResource.class );
      }
      if (element instanceof IFile) {
        this.openElementAction.selectionChanged( sSelection );
        this.openElementAction.run();
      }
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#updateActionBars()
   */
  @Override
  public void updateActionBars() {
    super.updateActionBars();
    
    IStructuredSelection selection = null;
    
    if( getContext() != null
        && getContext().getSelection() instanceof IStructuredSelection ) {
      selection = (IStructuredSelection)getContext().getSelection();
    }
    
    this.openElementAction.selectionChanged( selection );
    
  }
  
  private IAdaptable getSelectedAdaptable() {
    
    IAdaptable result = null;
    ActionContext context = getContext();
    
    if ( context != null ) {
      ISelection selection = context.getSelection();
      if ( ( selection != null ) && ( selection instanceof IStructuredSelection ) ) {
        if ( ( ( IStructuredSelection ) selection ).size() == 1 ) {
          Object element = ( ( IStructuredSelection ) selection ).getFirstElement();
          if ( element instanceof IAdaptable ) {
            result = ( IAdaptable ) element;
          }
        }
      }
    }
    
    return result;
    
  }

}
