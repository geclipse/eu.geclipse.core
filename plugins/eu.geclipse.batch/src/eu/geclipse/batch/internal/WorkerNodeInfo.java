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

import java.util.List;
import eu.geclipse.batch.IWorkerNodeInfo;

/**
 * Class for holding information about a specific workernode.
 */
public class WorkerNodeInfo implements IWorkerNodeInfo {

  private String wnFQN;
  private WorkerNodeState state;
  private int np;
  private String properties;
  private String type;
  private String status;
  private List< String > jobs;

  /**
   * Create a new WorkerNodeInfo holder, from the arguments.
   *
   * @param wnFQN The Fully Qualified Name of the workernode.
   * @param state What is the state of this workernode in, free | down |
   *          offline.
   * @param np The number of processors for this workernode.
   * @param properties The properties of this wn.
   * @param type What type this wn is.
   * @param status String with various os info and status about this wn.
   * @param jobs Currently running jobs.
   */
  public WorkerNodeInfo( final String wnFQN,
                         final WorkerNodeState state,
                         final int np,
                         final String properties,
                         final String type,
                         final String status,
                         final List< String > jobs )
  {
    this.wnFQN = wnFQN;
    this.state = state;
    this.np = np;
    this.properties = properties;
    this.type = type;
    this.status = status;
    this.jobs = jobs;
  }

  /**
   * @return the np
   */
  public int getNp() {
    return this.np;
  }

  /**
   * @param np the np to set
   */
  public void setNp( final int np ) {
    this.np = np;
  }

  /**
   * @return the properties
   */
  public String getProperties() {
    return this.properties;
  }

  /**
   * @param properties the properties to set
   */
  public void setProperties( final String properties ) {
    this.properties = properties;
  }

  /**
   * @return the state
   */
  public WorkerNodeState getState() {
    return this.state;
  }

  /**
   * Returns a string representation of the status.
   *
   * @return The status as a string.
   */
  public String getStateAsString( ) {
    String str = null;
    switch (this.state) {
      case free:
        str = "free"; //$NON-NLS-1$
        break;
      case down:
        str = "down"; //$NON-NLS-1$
        break;
      case offline:
        str = "offline"; //$NON-NLS-1$
        break;
      case job_exclusive:
        str = "job-exclusive"; //$NON-NLS-1$
        break;
      case unknown:
        str = "unknown"; //$NON-NLS-1$
        break;
      default:
        str = ""; //$NON-NLS-1$
    }

    return str;
  }

  /**
   * @param state the state to set
   */
  public void setState( final WorkerNodeState state ) {
    this.state = state;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return this.status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus( final String status ) {
    this.status = status;
  }

  /**
   * @return the type
   */
  public String getType() {
    return this.type;
  }

  /**
   * @param type the type to set
   */
  public void setType( final String type ) {
    this.type = type;
  }

  /**
   * @return the wnFQN
   */
  public String getWnFQN() {
    return this.wnFQN;
  }

  /**
   * Converts the state from a string to WorkerNodeState.
   * @param state The state as a String
   * @return Returns the state as a <code>WorkerNodeState</code>
   */
  public static WorkerNodeState getStateFromString( final String state ) {
    WorkerNodeState retState = null;

    if ( 0 == state.compareTo( "free" )) //$NON-NLS-1$
      retState = WorkerNodeState.free;
    else if ( 0 == state.compareTo( "down" )) //$NON-NLS-1$
      retState = WorkerNodeState.down;
    else if ( 0 == state.compareTo( "offline" )) //$NON-NLS-1$
      retState = WorkerNodeState.offline;
    else if ( 0 == state.compareTo( "job-exclusive" )) //$NON-NLS-1$
      retState = WorkerNodeState.job_exclusive;
    else if ( 0 == state.compareTo( "busy" )) //$NON-NLS-1$
      retState = WorkerNodeState.busy;
    else if ( 0 == state.compareTo( "unknown" )) //$NON-NLS-1$
      retState = WorkerNodeState.unknown;

    return retState;
  }

  /**
   * @return the Kernel version.
   */
  public String getKernelVersion() {
    String kernel = null;
    int beginIndex, endIndex;

    if ( null != this.status ) {
      beginIndex = this.status.indexOf( this.wnFQN ) + this.wnFQN.length()+1;
      if ( -1 != beginIndex ) {
        endIndex = this.status.indexOf( ' ', beginIndex );

        if ( -1 != endIndex )
          kernel = this.status.substring( beginIndex, endIndex );
        else
          kernel = Messages.getString( "WorkerNodeInfo.Unknown" ); //$NON-NLS-1$
      } else
        kernel = Messages.getString( "WorkerNodeInfo.Unknown" ); //$NON-NLS-1$
    } else
      kernel = Messages.getString( "WorkerNodeInfo.Unknown" ); //$NON-NLS-1$

    return kernel;
  }

  /**
   * @return the total amount of RAM.
   */
  public String getTotalMem() {
    String mem = null;
    String id = "physmem="; //$NON-NLS-1$
    int beginIndex, endIndex;

    if ( null != this.status ) {
      beginIndex = this.status.indexOf( id ) + id.length();
      if ( -1 != beginIndex ) {
        endIndex = this.status.indexOf( ',', beginIndex );

        if ( -1 != endIndex )
          mem = this.status.substring( beginIndex, endIndex );
        else
          mem = Messages.getString( "WorkerNodeInfo.Unknown" ); //$NON-NLS-1$
      } else
        mem = Messages.getString( "WorkerNodeInfo.Unknown" ); //$NON-NLS-1$
    } else
      mem = Messages.getString( "WorkerNodeInfo.Unknown" ); //$NON-NLS-1$

    return mem;
  }

  /**
   * @return A <code>List</code> of jobIds of jobs currently running on this
   * wn, <code>null</code> of no jobs running.
   */
  public List< String > getJobs() {
    return this.jobs;
  }

}
