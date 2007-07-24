/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.internal.model.notify;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;

/**
 * The <code>NotificationService</code> is the central point that
 * manages all event notifications from the Grid model to the
 * outside world.
 */
public class NotificationService {
  
  /**
   * The singleton instance.
   */
  private static NotificationService instance;
  
  /**
   * List containing all registered listeners.
   */
  private List< IGridModelListener > listeners
    = new ArrayList< IGridModelListener >();
  
  /**
   * List containing all pending events.
   */
  private List< IGridModelEvent > eventList
    = new ArrayList< IGridModelEvent >();

  /**
   * Counter for all applied locks.
   */
  private int lockCounter;
  
  /**
   * Private constructor.
   */
  private NotificationService() {
    this.lockCounter = 0;
  }
  
  /**
   * Get the singleton instance of the <code>NotificationService</code>.
   * 
   * @return The singleton. If not yet happened the singleton will be
   * created.
   */
  public static final NotificationService getInstance() {
    
    if ( instance == null ) {
      instance = new NotificationService();
    }
    
    return instance;
    
  }
  
  /**
   * Add a {@link IGridModelListener} to the service. The listener will be
   * informed about all events.
   * 
   * @param listener The listener to be added.
   */
  public void addListener( final IGridModelListener listener ) {
    synchronized ( this.listeners ) {
      if ( ! this.listeners.contains( listener ) ) {
        this.listeners.add( listener );
      }
    }
  }
  
  /**
   * Locks the notification service. A locked service will collect all events
   * without delivering them to the listeners unless it is unlocked again.
   * For each lock an unlock has to be called. An internal counter in incremented
   * when <code>lock</code> is called and decremented again when {@link #unlock()}
   * is called. So only if all locks are unlocked again the pending events will
   * be delivered.
   * 
   * @see #unlock()
   */
  public void lock() {
    this.lockCounter++;
  }
  
  /**
   * Add an event to this service's event queue. If the service is not locked
   * the event is delivered immediately, otherwise it is stored until the
   * service is unlocked again.
   * 
   * @param event The event to be queued.
   */
  public void queueEvent( final IGridModelEvent event ) {
    incorporateEvent( event );
    processEvents();
  }
  
  /**
   * Remove the specified listener from the list of listeners. The listener
   * will not retrieve any further notifications.
   * 
   * @param listener The listener to be removed.
   */
  public void removeListener( final IGridModelListener listener ) {
    synchronized ( this.listeners ) {
      this.listeners.remove( listener );
    }
  }
  
  /**
   * Unlock the service.
   * 
   * @see #lock()
   */
  public void unlock() {
    if ( this.lockCounter > 0 ) {
      this.lockCounter--;
      if ( this.lockCounter == 0 ) {
        processEvents();
      }
    }
  }
  
  /**
   * Try to merge the specified event with an event in the event queue.
   * Events with the same type and the same source are merged in order
   * to optimize the event notification.
   * 
   * @param event The event to be incorporated.
   */
  private void incorporateEvent( final IGridModelEvent event ) {
    
    IGridElement source = event.getSource();
    int type = event.getType();
    
    synchronized ( this.eventList ) {
      
      int index = 0;
      IGridModelEvent oldEvent = null;
      
      // Test if the event list already contains an event from the source
      // and of the specified type
      for ( ; index < this.eventList.size() ; index++ ) {
        oldEvent = this.eventList.get( index );
        if ( ( source == oldEvent.getSource() ) && ( type == oldEvent.getType() ) ) {
          break;
        }
      }
      
      // If an event was found merge the two events
      if ( ( index < this.eventList.size() ) && ( oldEvent != null ) ) {
        
        IGridElement[] elements = event.getElements();
        IGridElement[] oldElements = oldEvent.getElements();
        List< IGridElement > newElementList = new ArrayList< IGridElement >();
        
        for ( IGridElement e : oldElements ) {
          newElementList.add( e );
        }
        
        for ( IGridElement e : elements ) {
          if ( ! newElementList.contains( e ) ) {
            newElementList.add( e );
          }
        }
        
        IGridElement[] newElements = newElementList.toArray( new IGridElement[ newElementList.size() ] );
        IGridModelEvent newEvent = new GridModelEvent( type, source, newElements );
        this.eventList.remove( index );
        this.eventList.add( index, newEvent );
        
      } else {
        this.eventList.add( event );
      }
      
    }
    
  }
  
  /**
   * Process all pending events. Tests if the service is locked. If the
   * service is not locked all pending events are delivered to the registered
   * listeners.
   */
  private void processEvents() {
    
    if ( this.lockCounter == 0 ) {
      
      List< IGridModelEvent > localCopy;
      
      synchronized ( this.eventList ) {
        localCopy = new ArrayList< IGridModelEvent >( this.eventList );
        this.eventList.clear();
      }
      
      for ( IGridModelEvent event : localCopy ) {
        for ( IGridModelListener listener : this.listeners ) {
          listener.gridModelChanged( event );
        }
      }
      
    }
    
  }

}
