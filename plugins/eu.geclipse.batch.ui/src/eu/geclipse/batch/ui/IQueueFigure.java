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

import eu.geclipse.batch.IQueueInfo.QueueRunState;
import eu.geclipse.batch.IQueueInfo.QueueState;

/**
 *  Interface for a Queue Figure.
 */
public interface IQueueFigure {
  /**
   * @param name The name of the Queue
   */
  public void setQueueName( final String name );

  /**
   * @param state The current operating state.
   * @param runState The current run state.
   */
  public void setState( final QueueState state, final QueueRunState runState );

  /**
   * @param runState The current run state.
   * @param state The current operating state.
   */
  public void setRunState( final QueueRunState runState, final QueueState state );

}
