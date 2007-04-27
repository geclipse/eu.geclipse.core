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

import java.util.List;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ResourceTransfer;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.views.GridModelViewPart;

/**
 * Paste action for the copy/paste machanism.
 */
public class PasteAction extends TransferAction {
  
  /**
   * The {@link GridModelViewPart} this action belongs to.
   */
  GridModelViewPart view;
  
  /**
   * Create a new paste action for the specified view using the specified
   * clipboard.
   * 
   * @param view The {@link GridModelViewPart} for which to create this action.
   * @param clipboard The {@link Clipboard} used for the copy/paste operation.
   */
  public PasteAction( final GridModelViewPart view,
                      final Clipboard clipboard ) {
    super( Messages.getString("PasteAction.paste_action_text"), clipboard ); //$NON-NLS-1$
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    ImageDescriptor pasteImage 
        = sharedImages.getImageDescriptor( ISharedImages.IMG_TOOL_PASTE );
    setImageDescriptor( pasteImage );
    this.view = view;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    IStructuredSelection selection = getStructuredSelection();
    if ( selection.size() == 1 ) {
      final Object element = selection.getFirstElement();
      if ( isDropTarget( element ) ) {
        IContainer destination
          = ( IContainer )( ( IGridContainer ) element ).getResource();
        ResourceTransfer transfer = ResourceTransfer.getInstance();
        Object contents = getClipboard().getContents( transfer );
        if ( ( contents != null ) && ( contents instanceof IResource[] ) ) {
          IResource[] resources = ( IResource[] ) contents;
          try {
            copyResources( resources, destination );
          } catch( CoreException cExc ) {
            // TODO mathias proper error handling
            Activator.logException( cExc );
          }
        }
        Display display = PasteAction.this.view.getSite().getShell().getDisplay();
        display.syncExec( new Runnable() {
          public void run() {
            IGridContainer container = ( IGridContainer ) element;
            container.setDirty();
            PasteAction.this.view.refreshViewer( container );
          }
        } );
      }
    }
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    
    boolean result = false;
      
    List< ? > list = selection.toList();
    if ( list.size() == 1 ) {
      Object obj = list.get( 0 );
      if ( isDropTarget( obj ) ) {
        result = true;
      }
    }
    
    return result;
    
  }
  
}
