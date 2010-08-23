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
 * Enum of all supported Event types.
 */
public enum EventType {
  /** Send event */
  SEND(0),
  /** Receive event */
  RECV(1),
  /** Test event */
  TEST(2),
  /** Other event */
  OTHER(3),
  /** Collective event */
  COLLECTIVE(4);

  /** Id of the EventType */
  public final int id;

  private EventType( final int id ) {
    this.id = id;
  }

  /**
   * Returns the EventType for the given id
   * 
   * @param id
   * @return the EventType of the id
   */
  public static EventType getEventType( final int id ) {
    EventType result = null;
    if( id == SEND.id )
      result = SEND;
    else if( id == RECV.id )
      result = RECV;
    else if( id == TEST.id )
      result = TEST;
    else if( id == OTHER.id )
      result = OTHER;
    else if( id == COLLECTIVE.id )
      result = COLLECTIVE;
    return result;
  }
}
