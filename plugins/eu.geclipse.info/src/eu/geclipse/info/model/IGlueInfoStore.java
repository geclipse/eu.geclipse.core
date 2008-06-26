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
package eu.geclipse.info.model;


/**
 * 
 * @author George Tsouloupas
 * 
 */
public interface IGlueInfoStore {

  /**
   * Add a listener
   * @param listener an IGlueStoreChangeListerner
   */
  public void addListener(IGlueStoreChangeListerner listener);

  /**
   * Remove a specific listener
   * @param listener
   */
  public void removeListener(IGlueStoreChangeListerner listener);

  /**
   * Remove all the listeners
   */
  public void removeAllListeners();

  
  /**
   * Notify that a change has been made.
   */
  public void notifyListeners( );
    
}
