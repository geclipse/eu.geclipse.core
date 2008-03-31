/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.core.model;

import org.eclipse.core.resources.IFolder;

import eu.geclipse.core.internal.model.LocalFolder;

/**
 * Empty interface to make {@link LocalFolder} accessible outside of
 * eu.geclipse.core
 * 
 * @author katis
 */
public interface ILocalFolder {

  /**
   * Convenience method that returns the <code>IFolder</code> out of the
   * resource.
   * 
   * @return The corresponding <code>IFolder</code>.
   */
  public IFolder getFolder();
}
