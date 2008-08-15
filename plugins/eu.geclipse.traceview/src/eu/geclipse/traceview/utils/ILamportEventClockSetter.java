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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.traceview.utils;

import eu.geclipse.traceview.ILamportEvent;

/**
 * Interface for setting the lamport clock of an event. Used by the
 * ClockCalculator class.
 */
public interface ILamportEventClockSetter extends ILamportEvent {
  /**
   * Sets the lamport clock of the event.
   * @param lamportClock the new lamport clock
   */
  void setLamportClock( int lamportClock );

  /**
   * Sets the lamport clock of the partner event. 
   * @param lamportClock the new lamport clock of the partner event
   */
  void setPartnerLamportClock( int lamportClock );
}
