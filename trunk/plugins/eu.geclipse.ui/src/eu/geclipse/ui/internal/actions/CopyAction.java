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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import eu.geclipse.core.model.IGridElement;

/**
 * Copy action for the copy/paste machanism.
 */
public class CopyAction extends TransferAction {
  
  /**
   * Create a new copy action using the specified clipboard.
   * 
   * @param clipboard The {@link Clipboard} used by this copy
   * action.
   */
  protected CopyAction( final Clipboard clipboard ) {
    super( Messages.getString("CopyAction.copy_action_text"), clipboard ); //$NON-NLS-1$
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    ImageDescriptor pasteImage 
        = sharedImages.getImageDescriptor( ISharedImages.IMG_TOOL_COPY );
    setImageDescriptor( pasteImage );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    List< ? > data = getStructuredSelection().toList();
    Object[] dataArray = getData( data.toArray( new Object[ data.size() ] ) );
    Transfer[] dataTypes = getDataTypes( dataArray );
    getClipboard().setContents( dataArray, dataTypes );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    
    boolean enabled = super.updateSelection( selection );
    
    if ( enabled ) {
      
      IPath referenceLocation = null;
      List< ? > selectedObjects = selection.toList();
      
      for ( Object selectedObject : selectedObjects ) {
        
        enabled = isDragSource( selectedObject );
        if ( !enabled ) {
          break;
        }
        
        IPath location = getLocation( selectedObject );
        if ( location == null ) {
          enabled = false;
        }
        
        if ( referenceLocation == null ) {
          referenceLocation = location;
        } else if ( referenceLocation.equals( location ) ) {
          enabled = false;
        }
        
        if ( !enabled ) {
          break;
        }
        
      }
    }
    
    return enabled;
    
  }
  
  /**
   * Get the location in the workspace of the specified object. Works for {@link IResource}
   * and {@link IGridElement}.
   * 
   * @param obj The object for which to retrieve the location. 
   * @return The location in the workspace for the specified object or <code>null</code>
   * if the specified object is neither an {@link IResource} nor an {@link IGridElement}.
   */
  private IPath getLocation( final Object obj ) {
    IPath location = null;
    if ( obj instanceof IResource ) {
      location = ( ( IResource ) obj ).getFullPath();
    } else if ( obj instanceof IGridElement ) {
      location = ( ( IGridElement ) obj ).getPath(); 
    }
    return location;
  }
  
}
