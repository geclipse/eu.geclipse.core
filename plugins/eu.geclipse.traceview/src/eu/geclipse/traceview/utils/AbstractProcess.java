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

public abstract class AbstractProcess
  implements ILamportProcess, IPhysicalProcess
{

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
    return ( ( ILamportEvent )getEventByLogicalClock( getMaximumLogicalClock() ) ).getLamportClock();
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.traceview.ILamportProcess#getEventByLamportClock(int)
   */
  public ILamportEvent getEventByLamportClock( final int index ) {
    ILamportEvent result = null;
    int low = 0;
    int high = getMaximumLogicalClock();
    int mid;
    while( low <= high ) {
      mid = ( low + high ) / 2;
      result = ( ILamportEvent )getEventByLogicalClock( mid );
      if( result.getLamportClock() > index ) {
        high = mid - 1;
      } else if( result.getLamportClock() < index ) {
        low = mid + 1;
      } else {
        break;
      }
    }
    if( result.getLamportClock() != index )
      result = null;
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.traceview.ILamportProcess#getEventsByLamportClock(int,
   *      int)
   */
  public ILamportEvent[] getEventsByLamportClock( final int from, final int to )
  {
    ILamportEvent[] events = null;
    try {
      // get from
      int searchFrom = from;
      ILamportEvent fromEvent = null;
      while( fromEvent == null && searchFrom <= to ) {
        fromEvent = getEventByLamportClock( searchFrom );
        searchFrom++;
      }
      if( fromEvent == null ) {
        return new ILamportEvent[ 0 ];
      }
      searchFrom--;
      // get to
      int searchTo;
      if( to < this.getMaximumLamportClock() ) {
        searchTo = to;
      } else {
        searchTo = this.getMaximumLamportClock();
      }
      ILamportEvent toEvent = null;
      // TODO investigate
      while( toEvent == null && searchTo >= searchFrom ) {
        toEvent = getEventByLamportClock( searchTo );
        searchTo--;
      }
      if( toEvent == null ) {
        return new ILamportEvent[ 0 ];
      }
      int length = toEvent.getLogicalClock() - fromEvent.getLogicalClock() + 1;
      events = new ILamportEvent[ length ];
      for( int i = 0, logicalClock = fromEvent.getLogicalClock(); i < length; logicalClock++, i++ )
      {
        events[ i ] = ( ILamportEvent )getEventByLogicalClock( logicalClock );
      }
    } catch( Exception e ) {
      e.printStackTrace();
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
    return ( ( IPhysicalEvent )getEventByLogicalClock( getMaximumLogicalClock() ) ).getPhysicalStopClock();
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
        event = ( IPhysicalEvent )getEventByLogicalClock( event.getLogicalClock() + 1 );
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
}
