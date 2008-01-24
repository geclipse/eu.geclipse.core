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

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.actions.DeleteResourceAction;
import org.eclipse.ui.actions.RenameResourceAction;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import eu.geclipse.ui.views.GridModelViewPart;

/**
 * Action group holding all file related actions like copy, paste and delete.
 */
public class FileActions extends ActionGroup {
  
  /**
   * Clipboard used for the copy and paste mechanism.
   */
  private Clipboard clipboard;
  
  /**
   * The workbench site for which to create this action.
   */
  private IWorkbenchSite site;
  
  /**
   * The copy action of the copy and paste mechanism.
   */
  private CopyAction copyAction;
  
  /**
   * The paste action of the copy and paste mechanism.
   */
  private PasteAction pasteAction;
  
  /**
   * The action for deleting resources in the workspace.
   */
  private DeleteResourceAction deleteAction;
  
  /**
   * The action for renaming resources in the workspace.
   */
  private RenameResourceAction renameAction;
  
  /**
   * Construct a new file action group for the specified
   * {@link GridModelViewPart}.
   * 
   * @param view The {@link GridModelViewPart} for which to create
   * this action group.
   */
  public FileActions( final GridModelViewPart view ) {
    
    this.site = view.getSite();
    Shell shell = this.site.getShell();
    ISelectionProvider provider = this.site.getSelectionProvider();
    
    this.clipboard = new Clipboard( shell.getDisplay() );
    
    this.copyAction = new CopyAction( this.clipboard );
    this.pasteAction = new PasteAction( view, this.clipboard );
    this.deleteAction = new DeleteResourceAction( shell );
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    ImageDescriptor deleteImage 
        = sharedImages.getImageDescriptor( ISharedImages.IMG_TOOL_DELETE );
    this.deleteAction.setImageDescriptor( deleteImage );
    this.renameAction = new RenameResourceAction( shell );
    
    provider.addSelectionChangedListener( this.copyAction );
    provider.addSelectionChangedListener( this.pasteAction );
    provider.addSelectionChangedListener( this.deleteAction );
    provider.addSelectionChangedListener( this.renameAction );
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#dispose()
   */
  @Override
  public void dispose() {
    ISelectionProvider provider = this.site.getSelectionProvider();
    provider.removeSelectionChangedListener( this.copyAction );
    provider.removeSelectionChangedListener( this.pasteAction );
    provider.removeSelectionChangedListener( this.deleteAction );
    provider.removeSelectionChangedListener( this.renameAction );
    this.clipboard.dispose();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    if ( this.deleteAction.isEnabled() ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_EDIT, 
                          this.copyAction );
      menu.appendToGroup( ICommonMenuConstants.GROUP_EDIT, 
                          this.pasteAction );
      menu.appendToGroup( ICommonMenuConstants.GROUP_EDIT, 
                          this.deleteAction );
      menu.appendToGroup( ICommonMenuConstants.GROUP_EDIT,
                          this.renameAction );
    }
    super.fillContextMenu(menu);
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
    
    this.copyAction.selectionChanged( selection );
    this.pasteAction.selectionChanged( selection );
    this.deleteAction.selectionChanged( selection );
    this.renameAction.selectionChanged( selection );
  }
  
}
