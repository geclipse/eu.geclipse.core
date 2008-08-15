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
 * Interface for an event providing a logical clock.
 */
public interface IEvent extends IPropertySource {

  /**
   * Returns the type of the event.
   * 
   * @return type of the event.
   */
  EventType getType();

  /**
   * Returns the id of the process this event belongs to.
   * 
   * @return process
   */
  int getProcessId();

  /**
   * Returns the id of the partner process.
   * 
   * @return the id of the partner process.
   */
  int getPartnerProcessId();

  /**
   * Returns the logical clock of the event.
   * 
   * @return the logical clock of the event.
   */
  int getLogicalClock();

  /**
   * Returns the logical clock of the partner event.
   * 
   * @return the logical clock of the partner event.
   */
  int getPartnerLogicalClock();

  /**
   * Returns the next event or null if there is no next event.
   * 
   * @return the next event.
   */
  IEvent getNextEvent();

  /**
   * Returns the send/receive partner of this event or null if there is no
   * partner.
   * 
   * @return the partner event.
   */
  IEvent getPartnerEvent();

  /**
   * Returns the process this event belongs to.
   * 
   * @return the process this event belongs to.
   */
  IProcess getProcess();

  /**
   * Returns the name of the Event
   * 
   * @return name of the Event
   */
  String getName();
}
