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
 * Interface that all Information stores should implement
 * @author George Tsouloupas
 * 
 */
public interface IGlueInfoStore {

  public void addListener(IGlueStoreChangeListerner listener, String objectName);

  public void removeListener(IGlueStoreChangeListerner listener, String resourceTypeName);

  public void removeAllListeners();

  public void addStateListener(IGlueStoreStateChangeListerner listener);

  public void removeStateListener(IGlueStoreStateChangeListerner listener);

  public void removeAllStateListeners();
    
}
