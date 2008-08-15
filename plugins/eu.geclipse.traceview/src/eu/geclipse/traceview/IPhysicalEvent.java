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
 * Interface for events with physical clocks.
 */
public interface IPhysicalEvent extends IEvent {

  /**
   * Returns the physical start clock of the event.
   * 
   * @return start clock
   */
  int getPhysicalStartClock();

  /**
   * Returns the physical end clock of the event.
   * 
   * @return stop clock
   */
  int getPhysicalStopClock();
  
  /**
   * Returns the physical start clock of the event.
   * 
   * @return start clock
   */
  int getPartnerPhysicalStartClock();
  
  /**
   * Returns the physical stop clock of the partner event.
   * 
   * @return stop clock
   */
  int getPartnerPhysicalStopClock();
}
