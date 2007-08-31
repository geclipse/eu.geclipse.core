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

package eu.geclipse.info.glue;

import java.util.Date;

/**
 * @author George Tsouloupas
 * TODO Write Comments
 */
public class GlueServiceAssociation extends AbstractGlueTable
  implements java.io.Serializable
{

  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  public GlueService glueService1; // GlueService_UniqueId_1

  /**
   * 
   */
  public GlueService glueService2; // GlueService_UniqueId_2

  /**
   * 
   */
  public Date MeasurementDate;

  /**
   * 
   */
  public Date MeasurementTime;

  /**
   * @param id
   */
  public void setID( final String id ) {
    this.key = id;
  }
}
