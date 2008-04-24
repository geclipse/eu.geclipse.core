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

package eu.geclipse.ui.providers;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;

import eu.geclipse.ui.dialogs.GridFileDialog;

/**
 * Dedicated content provider for the {@link GridFileDialog} handles {@link IFileStore}s
 * in addition to grid elements.
 */
public class GridFileDialogContentProvider
    extends GridModelContentProvider {

  /* (non-Javadoc)
   * @see eu.geclipse.ui.providers.GridModelContentProvider#getChildren(java.lang.Object)
   */
  @Override
  public Object[] getChildren( final Object parentElement ) {
    Object[] children = null;
    if ( parentElement instanceof IFileStore ) {
      try {
        children = ( ( IFileStore ) parentElement ).childStores( EFS.NONE , null );
      } catch (CoreException e) {
        // Silently ignored
      }
    } else {
      children = super.getChildren( parentElement );
    }
    return children;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.providers.GridModelContentProvider#getParent(java.lang.Object)
   */
  @Override
  public Object getParent( final Object element ) {
    Object parent = null;
    if ( element instanceof IFileStore ) {
      parent = ( ( IFileStore ) element ).getParent();
    } else {
      parent = super.getParent( element );
    }
    return parent;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.providers.GridModelContentProvider#hasChildren(java.lang.Object)
   */
  @Override
  public boolean hasChildren( final Object element ) {
    boolean result = false;
    if ( element instanceof IFileStore ) {
      Object[] children = getChildren( element );
      result = ( children != null ) && ( children.length > 0 ); 
    } else {
      result = super.hasChildren( element );
    }
    return result;
  }
  
}
