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
 * Interface for an event providing a lamport clock.
 */
public interface ILamportEvent extends IEvent {

  /**
   * Returns the lamport clock of the event.
   * 
   * @return the lamport clock of the event.
   */
  int getLamportClock();

  /**
   * Returns the lamport clock of the partner event.
   * 
   * @return the lamport clock of the partner event.
   */
  int getPartnerLamportClock();
}
