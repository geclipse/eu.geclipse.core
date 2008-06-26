package eu.geclipse.info;

import java.util.ArrayList;

import eu.geclipse.info.model.IGlueInfoStore;
import eu.geclipse.info.model.IGlueStoreChangeListerner;


/**
 * This class is responsible to handle the listener updates of the 
 * @author tnikos
 *
 */
public class InfoCacheListenerHandler implements IGlueInfoStore {

  private static InfoCacheListenerHandler singleton = null;
  private ArrayList<IGlueStoreChangeListerner> listeners = new ArrayList<IGlueStoreChangeListerner>();
 
  
  private InfoCacheListenerHandler()
  {
    // make constructor private
  }
  
  /**
   * return a sigleton instance
   * @return a InfoCacheListenerHandler object
   */
  public static InfoCacheListenerHandler getInstance()
  {
    if (singleton == null)
      singleton = new InfoCacheListenerHandler();
    
    return singleton;
  }
  
  public void addListener( final IGlueStoreChangeListerner listener )
  {
    this.listeners.add( listener );
  }
  
  public void notifyListeners( ) {
    for( IGlueStoreChangeListerner listener : this.listeners ) {
      listener.infoChanged(  );
    }
  }

  public void removeAllListeners() {
    while( this.listeners.size()>0 ) {
      removeListener( this.listeners.get( 0 ) );
    }
  }

  public void removeListener( final IGlueStoreChangeListerner listener ) {
    if(this.listeners.contains( listener )){
      this.listeners.remove( listener );
    }
  }
}
