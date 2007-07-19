package eu.geclipse.core.internal.model.notify;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;

public class NotificationService {
  
  private static NotificationService instance;
  
  private List< IGridModelListener > listeners
    = new ArrayList< IGridModelListener >();
  
  private List< IGridModelEvent > eventList
    = new ArrayList< IGridModelEvent >();

  private int lockCounter;
  
  private NotificationService() {
    this.lockCounter = 0;
  }
  
  public static final NotificationService getInstance() {
    
    if ( instance == null ) {
      instance = new NotificationService();
    }
    
    return instance;
    
  }
  
  public void addListener( final IGridModelListener listener ) {
    synchronized ( this.listeners ) {
      if ( ! this.listeners.contains( listener ) ) {
        this.listeners.add( listener );
      }
    }
  }
  
  public void lock() {
    this.lockCounter++;
  }
  
  public void queueEvent( final IGridModelEvent event ) {
    incorporateEvent( event );
    processEvents();
  }
  
  public void removeListener( final IGridModelListener listener ) {
    synchronized ( this.listeners ) {
      this.listeners.remove( listener );
    }
  }
  
  public void unlock() {
    if ( this.lockCounter > 0 ) {
      this.lockCounter--;
      if ( this.lockCounter == 0 ) {
        processEvents();
      }
    }
  }
  
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
      if ( index < this.eventList.size() ) {
        
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
