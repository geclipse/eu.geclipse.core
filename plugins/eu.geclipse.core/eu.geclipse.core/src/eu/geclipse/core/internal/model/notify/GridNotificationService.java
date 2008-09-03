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
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.runtime.Assert;

import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;

/**
 * The <code>NotificationService</code> is the central point that
 * manages all event notifications from the Grid model to the
 * outside world. It collects and merges events according to their
 * source. If there is no lock for the source the events are delivered
 * immediately. Otherwise the events are collected until the lock for
 * the owner is released. 
 */
public class GridNotificationService {
  
  private static GridNotificationService instance;
  
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
  private Hashtable< IGridElement, Integer > locks;
  
  /**
   * Private constructor.
   */
  private GridNotificationService() {
    this.locks = new Hashtable< IGridElement, Integer >();
  }
  
  /**
   * Get the singleton service instance.
   * 
   * @return The singleton instance.
   */
  public static GridNotificationService getInstance() {
    
    if ( instance == null ) {
      instance = new GridNotificationService();
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
   * Locks the notification service for a specific owner. A locked service will
   * collect all events having the owner as the events source without delivering
   * them to the listeners unless it is unlocked by this owner again.
   * For each lock an unlock has to be called. An internal counter in incremented
   * when {@link #lock(IGridElement)} is called and decremented again when
   * {@link #unlock(IGridElement)} is called. So only if all locks for a specific
   * owner are unlocked again the pending events will be delivered.
   * 
   * @param owner The owner of the lock.
   * @see #unlock(IGridElement)
   */
  public void lock( final IGridElement owner ) {
    
    Assert.isNotNull( owner );
    
    Integer counter = this.locks.get( owner );
    
    if ( counter == null ) {
      counter = Integer.valueOf( 1 );
    } else {
      counter = Integer.valueOf( counter.intValue() + 1 );
    }
    
    this.locks.put( owner, counter );
    
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
    processEvents( event.getSource() );
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
   * Unlock the service for the specified owner. If the lock counter
   * for the owner is equal or less than 0 all pending events are
   * delivered.
   * 
   * @param owner The owner of the lock.
   * @see #lock(IGridElement)
   */
  public void unlock( final IGridElement owner ) {
    
    Assert.isNotNull( owner );
    
    Integer counter = this.locks.get( owner );
    
    if ( counter != null ) {
      counter = Integer.valueOf( counter.intValue() - 1 );
    } else {
      counter = Integer.valueOf( 0 );
    }
    
    if ( counter.intValue() <= 0 ) {
      this.locks.remove( owner );
    } else {
      this.locks.put( owner, counter );
    }
    
    processEvents( owner );
    
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
  private void processEvents( final IGridElement owner ) {
    
    Assert.isNotNull( owner );
    
    Integer counter = this.locks.get( owner );
    
    if ( ( counter == null ) || ( counter.intValue() <= 0 ) ) {
      
      List< IGridModelEvent > localCopy = new ArrayList< IGridModelEvent >();
    
      synchronized ( this.eventList ) {
        
        for ( IGridModelEvent event : this.eventList ) {
          if ( ( owner == null ) || ( event.getSource() == owner ) ) {
            localCopy.add( event );
          }
        }
        
        for ( IGridModelEvent event : localCopy ) {
          this.eventList.remove( event );
        }
        
      }
        
      for ( IGridModelEvent event : localCopy ) {
        synchronized ( this.listeners ) {
          for ( IGridModelListener listener : this.listeners ) {
            listener.gridModelChanged( event );
          }
        }
      }
      
    }
    
  }
  
}
