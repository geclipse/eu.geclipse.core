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
 *    Christof Klausecker GUP, JKU - implementation based on eu.geclipse.traceview.nope.tracereader.Process
 *****************************************************************************/

package eu.geclipse.traceview.utils;

import java.util.ArrayList;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.ILamportEvent;
import eu.geclipse.traceview.ILamportProcess;
import eu.geclipse.traceview.IPhysicalEvent;
import eu.geclipse.traceview.IPhysicalProcess;
import eu.geclipse.traceview.internal.Activator;

public abstract class AbstractProcess
  implements ILamportProcess, IPhysicalProcess
{
  protected int startTimeOffset;
  private static ILamportEvent[] EMPTY_LAMPORT_EVENT_ARRAY = new ILamportEvent[0];

  @Override
  public String toString() {
    return "Process: " + getProcessId(); //$NON-NLS-1$
  }

  // *****************************************************
  // * IPropertySource
  // *****************************************************
  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
   */
  public IPropertyDescriptor[] getPropertyDescriptors() {
    return new IPropertyDescriptor[ 0 ];
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
   */
  public Object getPropertyValue( final Object id ) {
    // no properties by default
    return null;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object,
   *      java.lang.Object)
   */
  public void setPropertyValue( final Object id, final Object value ) {
    // no editable properties
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
   */
  public boolean isPropertySet( final Object id ) {
    return false;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(java.lang.Object)
   */
  public void resetPropertyValue( final Object id ) {
    // no editable properties
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
   */
  public Object getEditableValue() {
    // no editable properties
    return null;
  }

  // *****************************************************
  // * IProcess
  // *****************************************************
  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IProcess#getEventsByLogicalClock(int, int)
   */
  public IEvent[] getEventsByLogicalClock( final int fromIndex,
                                           final int toIndex )
    throws IndexOutOfBoundsException
  {
    // TODO Auto-generated method stub
    return null;
  }

  // *****************************************************
  // * ILamportProcess
  // *****************************************************
  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.ILamportProcess#getMaximumLamportClock()
   */
  public int getMaximumLamportClock() {
	ILamportEvent event = ( ILamportEvent )getEventByLogicalClock( getMaximumLogicalClock() );
	int clock = event.getLamportClock();
    return clock;
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.ILamportProcess#getEventByLamportClock(int)
   */
  public ILamportEvent getEventByLamportClock( final int index ) {
	  return getEventByLamportClock(index, MATCH_MODE_EXACT);
  }

  public ILamportEvent getEventByLamportClock( final int index, final int matchMode ) {
    ILamportEvent result = null;
    int low = 0;
    int high = getMaximumLogicalClock();
    while( low <= high ) {
      int mid = ( low + high ) / 2;
      result = ( ILamportEvent )getEventByLogicalClock( mid );
      int lamClk = result.getLamportClock();
      if( lamClk > index ) {
        high = mid - 1;
      } else if( lamClk < index ) {
        low = mid + 1;
      } else if (lamClk == index) {
        return result;
      }
    }
    if (result == null || matchMode == MATCH_MODE_EXACT) {
      return null;
    }
    int lamClk = result.getLamportClock();
    if (lamClk < index) {
      switch ( matchMode ) {
        case MATCH_MODE_LOWER_ALLOWED:
        case MATCH_MODE_LOWER_ALLOWED_GREATER_ALLOWED:
          return result;
        case MATCH_MODE_GREATER_ALLOWED:
        case MATCH_MODE_GREATER_ALLOWED_LOWER_ALLOWED:
          ILamportEvent next = (ILamportEvent) result.getNextEvent();
          if (next == null) return matchMode == MATCH_MODE_GREATER_ALLOWED_LOWER_ALLOWED ? result : null;
          return next;
      }
	}
    // lamClk > index:
    switch ( matchMode ) {
      case MATCH_MODE_LOWER_ALLOWED:
      case MATCH_MODE_LOWER_ALLOWED_GREATER_ALLOWED:
        int prevEventNr = result.getLogicalClock() - 1;
        if ( prevEventNr < 0 ) return matchMode == MATCH_MODE_LOWER_ALLOWED_GREATER_ALLOWED ? result : null;
        return (ILamportEvent) getEventByLogicalClock( prevEventNr );
      case MATCH_MODE_GREATER_ALLOWED:
      case MATCH_MODE_GREATER_ALLOWED_LOWER_ALLOWED:
        return result;
    }
    return result;
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.ILamportProcess#getEventsByLamportClock(int,
   *      int)
   */
  public ILamportEvent[] getEventsByLamportClock( final int from, final int to ) {
    return getEventsByLamportClock(from, false, to, false);
  }

  public ILamportEvent[] getEventsByLamportClock( final int from, boolean allowPrev, final int to, boolean allowNext )
  {
    ILamportEvent[] events = null;
    try {
      // get from
      ILamportEvent fromEvent = getEventByLamportClock(from, allowPrev ? MATCH_MODE_LOWER_ALLOWED_GREATER_ALLOWED : MATCH_MODE_GREATER_ALLOWED);
      if( fromEvent == null ) {
        return EMPTY_LAMPORT_EVENT_ARRAY;
      }
      // get to
      ILamportEvent toEvent = getEventByLamportClock( to, allowNext ? MATCH_MODE_GREATER_ALLOWED_LOWER_ALLOWED : MATCH_MODE_LOWER_ALLOWED );
      if( toEvent == null ) {
        return EMPTY_LAMPORT_EVENT_ARRAY;
      }
      int length = toEvent.getLogicalClock() - fromEvent.getLogicalClock() + 1;
      if ( length <= 0 ) {
        return EMPTY_LAMPORT_EVENT_ARRAY;
      }
      events = new ILamportEvent[ length ];
      events[ 0 ] = fromEvent;
      for( int i = 1 ; i < length; i++ ) {
        events[ i ] = ( ILamportEvent ) events[ i-1 ].getNextEvent();
      }
    } catch( Exception exception ) {
      Activator.logException( exception );
    }
    return events;
  }

  // *****************************************************
  // * IPhysicalProcess
  // *****************************************************
  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IPhysicalProcess#getMaximumPhysicalClock()
   */
  public int getMaximumPhysicalClock() {
	IPhysicalEvent event = ( IPhysicalEvent )getEventByLogicalClock( getMaximumLogicalClock() );
	int clock = event.getPhysicalStopClock();
    return clock;
  }

  protected IPhysicalEvent getPhysicalEvent( final int time ) {
    IPhysicalEvent result = null;
    int low = 0;
    int high = getMaximumLogicalClock();
    while( low <= high ) {
      int mid = ( low + high ) / 2;
      IPhysicalEvent event = ( IPhysicalEvent )getEventByLogicalClock( mid );
      if( event.getPhysicalStartClock() > time ) {
        high = mid - 1;
      } else if( event.getPhysicalStopClock() < time ) {
        low = mid + 1;
      } else {
        result = event;
        break;
      }
    }
    if( result == null && low <= getMaximumLogicalClock() ) {
      result = ( IPhysicalEvent )getEventByLogicalClock( low );
    }
    return result;
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IPhysicalProcess#getEventsByPhysicalClock(int,
   *      int)
   */
  public IPhysicalEvent[] getEventsByPhysicalClock( final int timeStart,
                                                    final int timeStop )
  {
    ArrayList<IPhysicalEvent> events = new ArrayList<IPhysicalEvent>();
    try {
      IPhysicalEvent event = getPhysicalEvent( timeStart );
      if( event != null ) {
        if( event.getPhysicalStartClock() <= timeStop )
          events.add( event );
        event = ( IPhysicalEvent )event.getNextEvent();
        while( event != null && event.getPhysicalStartClock() < timeStop ) {
          events.add( event );
          event = ( IPhysicalEvent )event.getNextEvent();
        }
      }
    } catch( IndexOutOfBoundsException e ) {
      // empty
    }
    return events.toArray( new IPhysicalEvent[ 0 ] );
  }

  public int getStartTimeOffset() {
    return startTimeOffset;
  }

  public void setStartTimeOffset( int startTimeOffset ) {
    this.startTimeOffset = startTimeOffset;
  }
}
