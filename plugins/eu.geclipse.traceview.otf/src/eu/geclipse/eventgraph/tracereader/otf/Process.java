/*****************************************************************************
 * Copyright (c) 2009, 2010 g-Eclipse Consortium 
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
 *    Thomas Koeckerbauer MNM-Team, LMU Munich - initial API and implementation
 *****************************************************************************/

package eu.geclipse.eventgraph.tracereader.otf;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.utils.AbstractProcessFileCache;

final class Process extends AbstractProcessFileCache {

  private int logClock = 0;

  Process( final int processId, final OTFReader trace ) {
    super( trace, processId );
  }

  void appendEvent( final EventType type, final int partnerId, final int timestamp ) {
    setMaximumLogicalClock( this.logClock );
    Event event = new Event( this.logClock, this );
    event.setType( type );
    event.setFunctionId( -1 );
    event.setPartnerProcessId( partnerId );
    event.setPartnerLogicalClock( -1 );
    event.setMessageType( -1 );
    event.setPartnerLamportClock( -1 );
    event.setLamportClock( -1 );
    event.setSentMessageLength( 0 );
    event.setReceivedMessageLength( 0 );
    event.setPhysicalStartClock( timestamp );
    this.logClock++;
  }

  void appendFunctionEvent( final EventType type, final int function, final int timestamp ) {
    setMaximumLogicalClock( this.logClock );
    Event event = new Event( this.logClock, this );
    event.setType( type );
    event.setFunctionId( function );
    event.setPartnerProcessId( -1 );
    event.setPartnerLogicalClock( -1 );
    event.setMessageType( -1 );
    event.setPartnerLamportClock( -1 );
    event.setSentMessageLength( 0 );
    event.setReceivedMessageLength( 0 );
    event.setLamportClock( -1 );
    event.setPhysicalStartClock( timestamp );
    this.logClock++;
  }

  public IEvent getEventByLogicalClock( final int index ) throws IndexOutOfBoundsException {
    return new Event( index, this );
  }
}
