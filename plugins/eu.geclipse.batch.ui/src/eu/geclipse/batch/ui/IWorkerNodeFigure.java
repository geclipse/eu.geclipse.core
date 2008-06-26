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
import eu.geclipse.batch.IWorkerNodeInfo.WorkerNodeState;
/**
 * Interface for a Worker Node Figure.
 */
public interface IWorkerNodeFigure extends IFigure {
  /**
   * Sets the name of the Worker Node.
   * @param name The name of the Worker Node.
   */
  public void setFQDN( final String name );

  /**
   * @param state The current operating state.
   * @param numJobs How many jobs are currently executing on this wn
   */
  public void setState( final WorkerNodeState state, final int numJobs );
}
