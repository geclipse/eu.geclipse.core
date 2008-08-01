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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nikolaos Tsioutsias
 *
 *****************************************************************************/
package eu.geclipse.info.glue;


public class GlueLocation extends AbstractGlueTable{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public String UniqueID;
  
  public String schemaVersionMinor;
  public String version;
  
  public String name;
  
  public String locationPath;
  
  public  String schemaVersionMajor;
  
  public GlueSubCluster subCluster;
  
  @Override
  public String getID() {
    return this.UniqueID;
  }

  /**
   * Set this.name
   * @param id
   */
  public void setID( final String id ) {
    this.UniqueID = id;
  }
}
