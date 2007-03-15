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

package eu.geclipse.core.model;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * This is the root element of the whole grid model. it is the
 * parent of all currently contained projects. It also holds the
 * various managers as children. The managers are not available
 * via the {@link IGridContainer#getChildren(IProgressMonitor monitor)}
 * method but only via the dedicated getManager-methods.
 */
public interface IGridRoot
    extends IGridContainer, IGridModelNotifier {
  
  /**
   * Search the model for an element contained in the specified path.
   * It is therefore assumed that the path is relative to the
   * grid root, i.e. a path that is returned by
   * {@link eu.geclipse.core.model.IGridElement#getPath()}.
   * 
   * @param path The path pointing to the element.
   * @return The element or <code>null</code> if no element could
   * be found in the specified path.
   */
  public IGridElement findElement( final IPath path );
  
  /**
   * Recursively search the root's children for an element that
   * corresponds to the specified <code>IResource</code>.
   *  
   * @param resource The resource that is searched.
   * @return The grid element that corresponds to the specified
   * resource.
   */
  public IGridElement findElement( final IResource resource );
  
}
