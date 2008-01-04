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
package eu.geclipse.info.views;

import eu.geclipse.info.glue.AbstractGlueTable;

/**
 *
 */
public class ItemInfo {

  /**
   * 
   */
  public boolean selected = false;

  /**
   * 
   */
  public float x;

  /**
   * 
   */
  public float y;

  /**
   * 
   */
  public float z;
  
  /**
   * 
   */
  public float transparency = 1f;
  /**
   * 
   */
  public AbstractGlueTable agt;
  
  int colocationCount = 0;
  int colocationSerial = 0;

  double distanceFrom( final ItemInfo ii ) {
    return Math.sqrt( ( ii.x - this.x )
                      * ( ii.x - this.x )
                      + ( ii.y - this.y )
                      * ( ii.y - this.y )
                      + ( ii.z - this.z )
                      * ( ii.z - this.z ) );
  }
}
