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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.gvid;

/**
 * Interface to the GVid client page.
 */
public interface IGVidPage {
  /**
   * Sets the name of the page's tab.
   * 
   * @param name new name.
   */
  public abstract void setTabName( final String name );

  /**
   * Returns the name of the page's tab.
   * 
   * @return the name of the page's tab.
   */
  public abstract String getTabName();
}
