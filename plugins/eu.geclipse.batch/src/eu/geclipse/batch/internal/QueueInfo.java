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
package eu.geclipse.batch.internal;

import eu.geclipse.batch.IQueueInfo;


/**
  * Class for holding information about a specific queue.
  */
public class QueueInfo implements IQueueInfo {
  private String queueName;
  private int memory;
  private String timeCPU;
  private String timeWall;
  private String node;
  private int run;
  private int que;
  private String lm;
  private QueueState state;
  private QueueRunState runState;

  /**
   * Create a new QueueInfo holder, from the arguments.
   *
   * @param queueName The name of the queue.
   * @param memory The amount of memory allocated for the queue.
   * @param timeCPU The total CPU time for any job in this queue.
   * @param timeWall The total wall-clock time for any job in this queue.
   * @param node
   * @param run How many of the jobs are currently running
   * @param que How many jobs resides in the queue.
   * @param lm
   * @param state State of this queue, E | D.
   * @param runState The running state of this queue, R | S.
   */
  public QueueInfo ( final String queueName, final int memory, final String timeCPU, final String timeWall, 
                     final String node, final int run, final int que, final String lm, final QueueState state, 
                     final QueueRunState runState ) {
    this.queueName = queueName;
    this.memory = memory;
    this.timeCPU = timeCPU;
    this.timeWall = timeWall;
    this.node = node;
    this.run = run;
    this.que = que;
    this.lm = lm;
    this.state = state;
    this.runState = runState;
  }

  /**
   * @return the lm
   */
  public String getLm() {
    return this.lm;
  }

  /**
   * @param lm the lm to set
   */
  public void setLm( final String lm ) {
    this.lm = lm;
  }

  /**
   * @return the memory
   */
  public int getMemory() {
    return this.memory;
  }

  /**
   * @param memory the memory to set
   */
  public void setMemory( final int memory ) {
    this.memory = memory;
  }

  /**
   * @return the node
   */
  public String getNode() {
    return this.node;
  }

  /**
   * @param node the node to set
   */
  public void setNode( final String node ) {
    this.node = node;
  }

  /**
   * @return the que
   */
  public int getQue() {
    return this.que;
  }

  /**
   * @param que the que to set
   */
  public void setQue( final int que ) {
    this.que = que;
  }

  /**
   * @return the run
   */
  public int getRun() {
    return this.run;
  }

  /**
   * @param run the run to set
   */
  public void setRun( final int run ) {
    this.run = run;
  }

  /**
   * @return the state
   */
  public QueueState getState() {
    return this.state;
  }

  /**
   * Returns a string representation of the state.
   *
   * @return The state as a string.
   */
  public String getStateAsString( ) {
    String str = null;
    switch (this.state) {
      case E:
        str = "enable"; //$NON-NLS-1$
        break;
      case D:
        str = "disable"; //$NON-NLS-1$
        break;
      default:
        str = ""; //$NON-NLS-1$
    }

    return str;
  }

  /**
   * @param state the state to set
   */
  public void setState( final QueueState state ) {
    this.state = state;
  }

  /**
   * @return the runState
   */
  public QueueRunState getRunState() {
    return this.runState;
  }

  /**
   * Returns a string representation of the runState.
   *
   * @return The runState as a string.
   */
  public String getRunStateAsString( ) {
    String str = null;
    switch (this.runState) {
      case R:
        str = "running"; //$NON-NLS-1$
        break;
      case S:
        str = "stopped"; //$NON-NLS-1$
        break;
      default:
        str = ""; //$NON-NLS-1$
    }

    return str;
  }

  /**
   * @param runState the run state to set
   */
  public void setRunState( final QueueRunState runState ) {
    this.runState = runState;
  }

  /**
   * @return the timeCPU
   */
  public String getTimeCPU() {
    return this.timeCPU;
  }

  /**
   * @param timeCPU the timeCPU to set
   */
  public void setTimeCPU( final String timeCPU ) {
    this.timeCPU = timeCPU;
  }

  /**
   * @return the timeWall
   */
  public String getTimeWall() {
    return this.timeWall;
  }

  /**
   * @param timeWall the timeWall to set
   */
  public void setTimeWall( final String timeWall ) {
    this.timeWall = timeWall;
  }

  /**
   * @return the queueName
   */
  public String getQueueName() {
    return this.queueName;
  }
}
