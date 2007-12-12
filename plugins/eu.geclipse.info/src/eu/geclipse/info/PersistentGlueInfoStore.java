/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - George Tsouloupas (georget@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.info;

/**
 * @author George Tsouloupas
 */
public abstract class PersistentGlueInfoStore implements IGlueInfoStore {

  /**
   * Write into a file
   * @param Filename the name of the file to write into
   */
  public void write( final String Filename ) {
    //To be implemented by subclasses
  }

  /**
   * Read from the file
   * @param Filename the filename to read from
   */
  public void read( final String Filename ) {
    //To be implemented by subclasses
  }
}
