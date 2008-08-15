/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
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
 *    Christof Klausecker GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.traceview;

/**
 * Interface for a process containing lamport clock events.
 */
public interface ILamportProcess extends IProcess {

  /**
   * Returns the maximum lamport clock for this process.
   * 
   * @return the maximum lamport clock.
   */
  int getMaximumLamportClock();

  /**
   * Returns the event on the specified lamport clock of this process or null if
   * there is not such event.
   * 
   * @param index the lamport clock of the event to return.
   * @return the event on this index.
   * @throws IndexOutOfBoundsException thrown if the event index is out of
   *             bounds.
   */
  ILamportEvent getEventByLamportClock( int index ) throws IndexOutOfBoundsException;

  /**
   * Returns an array containing ILamportEvents of the specified range.
   * 
   * @param fromIndex The lamport clock of the first event to be included.
   * @param toIndex The lamport clock of the last event to be included.
   * @return Array containing events.
   * @throws IndexOutOfBoundsException 
   */
  ILamportEvent[] getEventsByLamportClock( int fromIndex, int toIndex ) throws IndexOutOfBoundsException;
}
