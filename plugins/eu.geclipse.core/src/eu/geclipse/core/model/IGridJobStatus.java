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

/**
 * This interface defines some informational fields related to the status
 * of jobs in the Grid.
 */
public interface IGridJobStatus {

  public final static int UNKNOWN=0; 
  public final static int RUNNING=1; 
  public final static int SUBMITTED=2; 
  public final static int DONE=3; 
  public final static int ABORTED=4; 
  public final static int ABANDONED=-1; 
  public final static int WAITING=5; 
  
  /**
   * Return true if the status of the job can be changed
   * in the future yet. Return false if status if final
   * (e.g. Aborted, Done, Failed)
   * To check if job succeeded use method isSuccessfull  
   * 
   * @return true if status can change yet
   */
  public boolean canChange();

  /**
   * Return true if the job was finished successfully.
   * This method shoul be called only if method canChange() 
   * returns true.
   * For statuses which are not final the behaviour of is Successfull()
   * method is undefined.
   * To check if job succeeded use method isSuccessfull()  
   * 
   * @return true if the job was finished successfully
   */
  public boolean isSuccessful();

  /**
   * Returns the name of the status. This can be any string
   * and is not interpreted. It will be just printed in the status 
   * window to show the status to the user. To check the meening 
   * of the status use methods canChange(), isSuccessful() or middleware
   * specific implementation.
   * 
   * @return Name of the status
   */
  public String getName();
  
  /**
   * Returns the type of the status. This is integer value and defines 
   * the meaning of the status. This value will be used for filtering 
   * job list to those with a given status type. Type can be also used 
   * by user interface to mark jobs belonging to the same type with the 
   * same grahpical elements (e.g. icons, colors)  
   * 
   * @return Name of the status
   */
  public int getType();

  
}
