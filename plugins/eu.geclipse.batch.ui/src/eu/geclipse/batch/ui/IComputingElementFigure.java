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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui;

import org.eclipse.draw2d.IFigure;

/**
 * Interface for a Computing Element Figure.
 */
public interface IComputingElementFigure extends IFigure {
  /**
   * Sets the name of the computing element.
   * @param name The name of the computing element.
   */
  public void setFQDN( final String name );

  /**
   * Sets the type of the batch service.
   * @param type The batch service type.
   */
  public void setType( final String type );

  /**
   * Sets the number of queues, that this ce controls.
   * @param queues The number of queues.
   */
  public void setNumQueues( final int queues );

  /**
   * Sets the number of worker nodes, that this ce controls.
   * @param wns The number of worker nodes.
   */
  public void setNumWNs( final int wns );

  /**
   * Sets the number of jobs, that this ce currently handles.
   * @param jobs The number of jobs.
   */
  public void setNumJobs( final int jobs );
}
