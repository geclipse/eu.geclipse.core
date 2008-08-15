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

import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Interface for a process containing logical clock events.
 */
public interface IProcess extends IPropertySource {

  /**
   * Returns the process id
   * 
   * @return process id
   */
  int getProcessId();

  /**
   * Returns the maximum logical clock for this process.
   * 
   * @return the maximum logical clock.
   */
  int getMaximumLogicalClock();

  /**
   * Returns the event with the specified logical clock
   * 
   * @param index
   * @return event
   * @throws IndexOutOfBoundsException
   */
  IEvent getEventByLogicalClock( int index ) throws IndexOutOfBoundsException;

  /**
   * Returns an array containing IEvents of the specified range.
   * 
   * @param fromIndex The logical clock of the first event to be included.
   * @param toIndex The logical clock of the last event to be included.
   * @return Array containing events.
   * @throws IndexOutOfBoundsException
   */
  IEvent[] getEventsByLogicalClock( int fromIndex, int toIndex )
    throws IndexOutOfBoundsException;
  
  /**
   * Returns the trace this process belongs to.
   * @return the trace this process belongs to.
   */
  ITrace getTrace();
}
