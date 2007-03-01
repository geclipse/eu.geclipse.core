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

package eu.geclipse.core.internal.model;

import org.eclipse.core.resources.IFile;
import eu.geclipse.core.model.IGridContainer;

/**
 * This class represents local files in the workspace. Each
 * <code>IFile</code> that is not handled by any registered
 * {@link eu.geclipse.core.model.IGridElementCreator} will be
 * represented by a <code>LocalFile</code> - at least if this
 * <code>IFile</code> is visible within the
 * {@link eu.geclipse.core.model.GridModel}. 
 */
public class LocalFile extends LocalResource {
  
  /**
   * Construct a new local file from the specified <code>IFile</code>.
   * 
   * @param parent The parent element of this local file.
   * @param file The corresponding <code>IFile</code>.
   */
  LocalFile( final IGridContainer parent,
             final IFile file ) {
    super( parent, file );
  }
  
  /**
   * Convenience method that returns the <code>IFile</code> out
   * of the resource.
   * 
   * @return The corresponding <code>IFile</code>.
   */
  protected IFile getFile() {
    return ( IFile ) getResource();
  }
  
}
