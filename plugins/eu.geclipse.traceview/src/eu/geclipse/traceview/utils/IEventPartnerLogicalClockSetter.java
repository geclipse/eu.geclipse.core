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

import eu.geclipse.traceview.IEvent;

/**
 * Interface for setting the logical clock of an event. Used by the
 * ClockCalculator class.
 */
public interface IEventPartnerLogicalClockSetter extends IEvent {
  /**
   * Sets the logical clock.
   * @param logClock the new logical clock
   */
  void setPartnerLogicalClock( int logClock );
}
