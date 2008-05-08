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

package eu.geclipse.core.model.impl;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;

/**
 * Element creator for creating elements from files. Therefore
 * the internalCanCreate(Object) method delegates the decision
 * to the internalCanCreate(String) method who decides with the
 * help of the file extension of the file's name if this creator is
 * able to create an element from the specified {@link IFile}.
 */
public abstract class AbstractFileElementCreator
    extends AbstractGridElementCreator {
  
  private static final String EMPTY_STRING = ""; //$NON-NLS-1$

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElementCreator#internalCanCreate(java.lang.Object)
   */
  @Override
  protected boolean internalCanCreate( final Object fromObject ) {
    boolean result = false;
    if ( fromObject instanceof IFile ) {
      IPath location = ( ( IFile ) fromObject ).getFullPath();
      String fileExtension = location == null ? EMPTY_STRING : location.getFileExtension();
      result = internalCanCreate( fileExtension );
    }
    return result;
  }
  
  /**
   * Determine if this creator is potentially able to create elements
   * from an {@link IFile} whose name has the specified extension.
   * 
   * @param fileExtension The file extension of the specified
   * {@link IFile} object.
   * @return True if this creator is potentially able to create a new
   * element an {@link IFile} with the specified file extension.
   */
  protected abstract boolean internalCanCreate( final String fileExtension );
  
}
