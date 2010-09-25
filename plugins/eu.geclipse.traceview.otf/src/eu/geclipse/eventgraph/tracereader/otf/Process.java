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

import java.util.ArrayList;
import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.utils.AbstractProcessFileCache;

/**
 * OTF Process
 */
public final class Process extends AbstractProcessFileCache {

  private int logClock = 0;
  private ArrayList<Integer[]> previousEvents; // TODO store information in tracecache 
  
  Process( final int processId, final OTFReader trace ) {
    super( trace, processId );
    this.previousEvents = new ArrayList<Integer[]>();
    this.previousEvents.add( new Integer[0] );
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
    // set this since it is the last field in the event and if it is not set
    // there will be an out of bounds exception if it is accessed
    event.setMessageGroup( -1 );
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
    // set this since it is the last field in the event and if it is not set
    // there will be an out of bounds exception if it is accessed
    event.setMessageGroup( -1 );
    this.logClock++;
  }

  public Event getEventByLogicalClock( final int index ) throws IndexOutOfBoundsException {
    Event event = new Event( index, this ); 
    return event;
  }
  
  @Override
  @SuppressWarnings("boxing")
  public Event[] getEventsByPhysicalClock( final int timeStart,
                                                    final int timeStop )
  {
    ArrayList<Event> events = new ArrayList<Event>();
    try {
      Event event = (Event) getPhysicalEvent( timeStart );
      if( event != null ) {
        if( event.getPhysicalStartClock() <= timeStop )
          events.add( event );
        event = event.getNextEvent();
        while( event != null && event.getPhysicalStartClock() < timeStop ) {
          events.add( event );
          event = event.getNextEvent();
        }
      }
    } catch( IndexOutOfBoundsException e ) {
      // empty
    }
    
    Event event = null;
    if( events.size() != 0 ) {
      event = events.get( 0 );
    } else {
      event = (Event) getPhysicalEvent( timeStart );
    }
    if( event != null ) {
      int index = event.getLogicalClock();
      event = event.getPreviousEvent();
      for( int i = index % 1000; i > 0; i-- ) {
        if( event.getPhysicalStopClock() > timeStart ) {
          events.add( 0, event);
        }
        event = event.getPreviousEvent();
      }
      if( this.previousEvents.size() > index / 1000 ) {
        Integer[] indices = this.previousEvents.get( index / 1000 );
        for( int i = indices.length - 1; i >= 0; i-- ) {
          events.add( 0, getEventByLogicalClock( indices[ i ] ) );
        }
      }
    }
    return events.toArray( new Event[ 0 ] );
  }

  /**
   * Add events for the previous time frame.
   * 
   * @param events
   */
  protected void addPreviousEvents(final Integer[] events) {
    this.previousEvents.add( events );
  }
}
