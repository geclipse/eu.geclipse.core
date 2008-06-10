/******************************************************************************
 * Copyright (c) 2007-2008 g-Eclipse consortium
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

package eu.geclipse.info.glue;


/**
 * @author George Tsouloupas
 * TODO Write Comments
 */
public class GlueSubClusterSoftwareRunTimeEnvironment extends AbstractGlueTable
  implements java.io.Serializable
{

  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public GlueIndex glueIndex;

  /**
   * 
   */
  public String Value; // PK

  /**
   * 
   */
  public GlueSubCluster glueSubCluster; // GlueSubClusterUniqueID

  /* (non-Javadoc)
   * @see eu.geclipse.info.glue.AbstractGlueTable#getID()
   */
  @Override
  public String getID() {
    return this.Value;
  }

  /**
   * Set this.Value
   * @param id
   */
  public void setID( final String id ) {
    this.Value = id;
  }
}
