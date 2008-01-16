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
 *    nloulloud
 *****************************************************************************/
package eu.geclipse.info.glue;


public class GriaService extends AbstractGlueTable{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public String uniqueId;
  public String name;
  public String uri;
  
  @Override
  public String getID() {
    return this.uniqueId;
  }
  
  public void setID( final String id ) {
    this.uniqueId = id;
  }
}
