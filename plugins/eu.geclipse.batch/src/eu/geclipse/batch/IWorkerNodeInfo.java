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

import java.util.List;

import eu.geclipse.batch.internal.Messages;

/**
 * Interface for holding information about a specific workernode.
 */
public interface IWorkerNodeInfo {
  /**
   * This field determines the type of the state of this WorkerNode.
   */
  public static enum WorkerNodeState {

    /**
     * The worker node is free.
     */
    free,

    /**
     * The worker node is down, i.e. no contact with this workernode.
     */
    down,

    /**
     * The batch service have set this worker node to offline.
     */
    offline,

    /**
     * The worker node currently have a job execution on it.
     */
    job_exclusive,

    /**
     * The worker node currently have a job execution on it and are busy.
     */
    busy,

    /**
     * The state of the worker node is unknown at this time.
     */
    unknown;

    @Override
    public String toString() {
      String str = null;

      switch ( this ) {
        case free: 
          str = Messages.getString( "IWorkerNodeInfo.WNStateFree" ); //$NON-NLS-1$
          break;
        case down:
          str = Messages.getString( "IWorkerNodeInfo.WNStateDown" ); //$NON-NLS-1$
          break;
        case offline:
          str = Messages.getString( "IWorkerNodeInfo.WNStateOffline" ); //$NON-NLS-1$
          break;
        case job_exclusive:
          str = Messages.getString( "IWorkerNodeInfo.WNStateJob-exclusive" ); //$NON-NLS-1$
          break;
        case busy:
          str = Messages.getString( "IWorkerNodeInfo.WNStateBusy" ); //$NON-NLS-1$
          break;
        case unknown:
        default:
          str = Messages.getString( "IWorkerNodeInfo.WNStateUnknown" ); //$NON-NLS-1$
          break;
      }
      return str;
    }

    /**
     * Due to the prohibition of - in enum identifiers, this method was added.
     * @param val The String value to be "matched" to a enum identifier.
     * @return The enum identifier for the incoming string.
     */
    public static WorkerNodeState valueOfEnhanced( final String val ) {
      WorkerNodeState ret;
      if ( 0 == val.compareTo( "job-exclusive" )) //$NON-NLS-1$
        ret = job_exclusive;
      else
        ret = valueOf( val );
      return ret;
    }
  }

  /**
   * @return the np
   */
  public int getNp();

  /**
   * @param np the np to set
   */
  public void setNp( final int np );

  /**
   * @return the properties
   */
  public String getProperties();

  /**
   * @param properties the properties to set
   */
  public void setProperties( final String properties );

  /**
   * @return the state
   */
  public WorkerNodeState getState();

  /**
   * @param state the state to set
   */
  public void setState( final WorkerNodeState state );

  /**
   * Returns a string representation of the status.
   * @return The status as a string.
   */
  public String getStateAsString();

  /**
   * @return the status
   */
  public String getStatus();

  /**
   * @param status the status to set
   */
  public void setStatus( final String status );

  /**
   * @return the type
   */
  public String getType();

  /**
   * @param type the type to set
   */
  public void setType( final String type );

  /**
   * @return the wnFQN
   */
  public String getWnFQN();

  /**
   * @return the Kernel version.
   */
  public String getKernelVersion();

  /**
   * @return the total amount of RAM.
   */
  public String getTotalMem();

  /**
   * @return A <code>List</code> of jobIds of jobs currently running on this wn
   */
  public List<String> getJobs();
}
