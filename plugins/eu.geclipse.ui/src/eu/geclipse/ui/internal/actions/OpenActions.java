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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;

/**
 * Action group holding all available open actions.
 */
public class OpenActions extends ActionGroup {
  
  /**
   * The workbench site this action belongs to.
   */
  private IWorkbenchSite site;
  
  /**
   * Action for opening Grid model elements.
   */
  private OpenElementAction openElementAction;
  
  /**
   * Construct a new open action group for the specified workbench site.
   * 
   * @param site The {@link IWorkbenchSite} for which to create this
   * open action group.
   */
  public OpenActions( final IWorkbenchSite site ) {
    
    this.site = site;
    IWorkbenchPage page = site.getPage();
    ISelectionProvider provider = site.getSelectionProvider();
    
    this.openElementAction = new OpenElementAction( page );
    
    provider.addSelectionChangedListener( this.openElementAction );
    
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

}
