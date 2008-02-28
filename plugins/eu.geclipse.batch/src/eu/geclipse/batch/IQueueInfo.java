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
package eu.geclipse.batch;

import eu.geclipse.batch.internal.Messages;

/**
 * Interface for holding information about a specific queue.
 */
public interface IQueueInfo {

  /**
   * This field determines the type of the state of this Queue.
   */
  public static enum QueueState {

    /**
     * The queue is enabled.
     */
    E,

    /**
     * The queue is disabled.
     */
    D;

    @Override
    public String toString() {
      String str = null;

      switch ( this ) {
        case E: 
          str = Messages.getString( "IQueueInfo.QueueStateE" ); //$NON-NLS-1$
          break;
        case D:
          str = Messages.getString( "IQueueInfo.QueueStateD" ); //$NON-NLS-1$
          break;
        default:
          str = Messages.getString( "IQueueInfo.QueueStateUnknown" ); //$NON-NLS-1$
          break;
      }
      return str;
    }
  }

  /**
   * This field determines the type of the running state of this Queue.
   */
  public static enum QueueRunState {

    /**
     * The queue is running.
     */
    R,

    /**
     * The queue is stopped.
     */
    S;

    @Override
    public String toString() {
      String str = null;
      
      switch ( this ) {
        case R: 
          str = Messages.getString( "IQueueInfo.QueueStateR" ); //$NON-NLS-1$
          break;
        case S:
          str = Messages.getString( "IQueueInfo.QueueStateS" ); //$NON-NLS-1$
          break;
        default:
          str = Messages.getString( "IQueueInfo.QueueStateUnknown" ); //$NON-NLS-1$
          break;
      }
      return str;
    }

  }

  /**
   * This field determines the type of this Queue.
   */
  public static enum QueueType {

    /**
     * The queue is Execution.
     */
    execution,

    /**
     * The queue is disabled.
     */
    route
  }
  
  /**
   * @return the lm
   */
  public String getLm();

  /**
   * @param lm the lm to set
   */
  public void setLm( final String lm );

  /**
   * @return the memory
   */
  public int getMemory();

  /**
   * @param memory the memory to set
   */
  public void setMemory( final int memory );

  /**
   * @return the node
   */
  public String getNode();

  /**
   * @param node the node to set
   */
  public void setNode( final String node );

  /**
   * @return the que
   */
  public int getQue();

  /**
   * @param que the que to set
   */
  public void setQue( final int que );

  /**
   * @return the run
   */
  public int getRun();

  /**
   * @param run the run to set
   */
  public void setRun( final int run );

  /**
   * @return the state
   */
  public QueueState getState();

  /**
   * @param state the state to set
   */
  public void setState( QueueState state );

  /**
   * @return the runState
   */
  public QueueRunState getRunState();

  /**
   * @param runState the runState to set
   */
  public void setRunState( QueueRunState runState );
  
  /**
   * @return the timeCPU
   */
  public String getTimeCPU();

  /**
   * @param timeCPU the timeCPU to set
   */
  public void setTimeCPU( final String timeCPU );

  /**
   * @return the timeWall
   */
  public String getTimeWall();

  /**
   * @param timeWall the timeWall to set
   */
  public void setTimeWall( final String timeWall );

  /**
   * @return the queueName
   */
  public String getQueueName();
}
