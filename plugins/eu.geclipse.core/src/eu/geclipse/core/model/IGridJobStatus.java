/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Mathias Stuempert - initial API and implementation
 *    Pawel Wolniewicz - Some improvements
 *****************************************************************************/

package eu.geclipse.core.model;

import java.util.Date;

/**
 * This interface defines some informational fields related to the status of
 * jobs in the Grid.
 */
public interface IGridJobStatus {

  
  /**
   * Status for job which real status cannot be retrieved now
   */
  public final static int UNKNOWN=0x0; 

  /**
   * Status of the job which was submitted and is not scheduled by RB yet.
   */
  public final static int SUBMITTED=0x1; 

  /**
   * Status of the job which was scheduled by RB and is sent to its destination,
   * but computation was not started yet.
   */
  public final static int WAITING=0x2; 
  
  /**
   * Status of the job which is actually running
   */
  public final static int RUNNING=0x4; 

  /**
   * Status of the job which was finished successfully
   */
  public final static int DONE=0x8; 

  /**
   * Status of the job which was finished unsuccessfully
   */
  public final static int ABORTED=0x10; 

  /**
   * Status of the job for which status information is no longer available. e.g.
   * job information was removed from RB. The difference between UNKNOWN and
   * ABANDONED is that UNKNOWN can be changed in the future to the real status
   * and ABANDONED is a final status.
   */
  public final static int ABANDONED=0x20; 

  /**
   * <p>
   * Status of the job cannot be determined.
   * </p>
   * <p>
   * The difference between UNDEF and UNKNOWN is that UNKNOWN is a temporary
   * state (e.g. because some network problem) and UNDEF is more permanent state
   * (e.g. status cannot be retrieved because there is no access to status
   * service like no appropriate plugin is available)
   * </p>
   */
  public final static int UNDEF=0x40; 
  
  /**
   * <p>
   * Return true if the status of the job can be changed in the future yet.
   * Return false if status if final (e.g. Aborted, Done, Failed)
   * </p>
   * <p>
   * To check if job succeeded use method isSuccessfull
   * </p>
   * 
   * @return true if status can change yet
   */
  public boolean canChange();

  /**
   * <p>
   * Return true if the job was finished successfully. This method should be
   * called only if method canChange() returns true.
   * </p>
   * <p>
   * For statuses which are not final the behaviour of is Successfull() method
   * is undefined.
   * </p>
   * <p>
   * To check if job succeeded use method isSuccessfull()
   * </p>
   * 
   * @return true if the job was finished successfully
   */
  public boolean isSuccessful();

  /**
   * Returns the name of the status. This can be any string and is not
   * interpreted. It will be just printed in the status window to show the
   * status to the user. To check the meaning of the status use methods
   * canChange(), isSuccessful() or middleware specific implementation.
   * 
   * @return Name of the status
   */
  public String getName();
  
  /**
   * Returns the type of the status. This is integer value and defines the
   * meaning of the status. This value will be used for filtering job list to
   * those with a given status type. Type can be also used by user interface to
   * mark jobs belonging to the same type with the same graphical elements (e.g.
   * icons, colours)
   * 
   * @return Name of the status
   */
  public int getType();

  /**
   * Returns the date of last status update. It should be the time of last
   * contact with middleware service and not the time of last status change.
   * 
   * @return time of last update
   */
  public Date getLastUpdateTime();
  
  
  /**
   * Return the explanation why the job is in the specific state. Can give more
   * detailed information than just status name.
   * 
   * @return String with explanation
   */
  public String getReason();
}
