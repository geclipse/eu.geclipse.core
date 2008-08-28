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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Christodoulos Efstathiades (cs05ce1@cs.ucy.ac.cy)
 *
 *****************************************************************************/

package eu.geclipse.ui.simpleTest;

/**
 * Class to create instances of PortRange objects, which represent a range of ports to scan.
 * 
 * @author cs05ce1
 *
 */
public class PortRange {

  private int start;
  private int finish;
  
  public PortRange( final int from ){
    
    this.start = from;
    this.finish = 0;
  }
  
  public PortRange( final int from, final int to ){
    
    this.start = from;
    this.finish = to;
  } 

  public int getStart() {
    return this.start;
  }

  public int getFinish() {
    return this.finish;
  }
  
}
