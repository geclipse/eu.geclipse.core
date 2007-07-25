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

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;

/**
 * Base interface for elements of a filesystem mount.
 */
public interface IGridConnectionElement extends IGridResource, IGridContainer {
  
  /**
   * Get the {@link IFileStore} object corresponding to this connection.
   * 
   * @return The file store of this connection.
   * @throws CoreException If a problem occurs when creating the file store.
   */
  public IFileStore getConnectionFileStore() throws CoreException;
  
  /**
   * Get the {@link IFileInfo} object corresponding to this connection.
   * May be <code>null</code>.
   * 
   * @return The file info or <code>null</code>.
   * @throws CoreException If a problem occures when creating the file info.
   */
  public IFileInfo getConnectionFileInfo() throws CoreException;
  
  /**
   * Get an error message that describes an error that occured
   * during the last operation. This functionality is not yet
   * fully defined.
   * 
   * @return An error string.
   */
  public String getError();
  
  /**
   * Determine if this grid mount object specifies a folder. If it
   * is not a folder it has to be a file.
   * 
   * @return True if this is a folder.
   */
  public boolean isFolder();
  
  /**
   * Determine if this mount is valid. There are several reasons why
   * a grid mount may be invalid. For instance it could request
   * a file system that is currently not available.
   *  
   * @return True if this mount is valid and can be browsed.
   */
  public boolean isValid();
  
}
