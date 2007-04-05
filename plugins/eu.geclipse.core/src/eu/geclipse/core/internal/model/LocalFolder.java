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

import org.eclipse.core.resources.IFolder;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.ResourceGridContainer;

/**
 * This class represents local folders in the workspace. Each
 * <code>IFolder</code> that is not handled by any registered
 * {@link eu.geclipse.core.model.IGridElementCreator} will be
 * represented by a <code>LocalFolder</code> - at least if this
 * <code>IFolder</code> is visible within the
 * {@link eu.geclipse.core.model.GridModel}. 
 */
public class LocalFolder
    extends ResourceGridContainer {
  
  /**
   * Construct a new local folder with the specified parent and the
   * specified corresponding <code>IFolder</code>.
   *  
   * @param parent The parent element of this folder.
   * @param folder The corresponding <code>IFolder</code>.
   */
  LocalFolder( final IFolder folder ) {
    super( folder );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#canContain(eu.geclipse.core.model.IGridElement)
   */
  @Override
  public boolean canContain( final IGridElement element ) {
    return true;
  }
  
  /**
   * Convenience method that returns the <code>IFolder</code> out
   * of the resource.
   * 
   * @return The corresponding <code>IFolder</code>.
   */
  protected IFolder getFolder() {
    return ( IFolder ) getResource();
  }
  
}
