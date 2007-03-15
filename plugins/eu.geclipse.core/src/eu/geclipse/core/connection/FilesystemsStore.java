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
 *    Katarzyna Bylec - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.connection;

import org.eclipse.core.resources.IFile;

/**
 * TODO Kasia
 */
public class FilesystemsStore {
  
  /**
   * TODO Kasia
   */
  private IFile file;
  
  /**
   * TODO Kasia
   * 
   * @param file TODO Kasia
   */
  public FilesystemsStore( final IFile file ) {
    this.file = file;
  }
  
  /**
   * TODO Kasia
   * 
   * @return TODO Kasia
   */
  public IFile getFile() {
    return this.file;
  }
  
}
