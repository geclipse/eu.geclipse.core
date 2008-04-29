/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.IContentChangeListener;
import org.eclipse.compare.IContentChangeNotifier;
import org.eclipse.core.runtime.ListenerList;
import eu.geclipse.core.reporting.ProblemException;


/**
 * The <code>BatchServiceManager</code> manages all types of batch services.
 * It holds an internal list of all currently available batch services. Although the
 * batch service manager is the base class of the batch service system, non-core
 * classes should use an {@link eu.geclipse.batch.IBatchServiceProvider} to request a service.

 * @author harald
 *
 */
public class BatchServiceManager implements IContentChangeNotifier {
  
  /**
   * The singleton that holds the instance of this
   * <code>BatchServiceManager</code>.
   */
  static private BatchServiceManager singleton = null;
  
  /**
   * The internal list of managed services.
   */
  private List<IBatchService> services = new ArrayList<IBatchService>();
  
  /**
   * This list holds the currently registered IContentChangeListeners. 
   */
  private ListenerList ccListeners = new ListenerList();
  
  /**
   * Private constructor. Use the {@link getManager} method to get the singleton.
   */
  private BatchServiceManager() {
    // empty implementation
  }
  
  /**
   * Get the singleton. There is only one instance of the <code>BatchServiceManager</code>
   * that is created and returned by this method.
   * 
   * @return The singleton.
   */
  public static BatchServiceManager getManager() {
    if ( singleton == null ) {
      singleton = new BatchServiceManager();
    }
    return singleton;
  }
  
  /**
   * Create a new batch service from the specified service description and adds it
   * to the list of managed services.
   * 
   * @param description The description from which to create the new batch service.
   * @param name batch service name, i.e. the configuration file that were 
   *             used to instantiate this service
   * @return A new batch service constructed from the specified description.
   * @throws ProblemException If an error occurs while creating the service
   * @see IBatchServiceDescription#createService( String name )
   */
  public IBatchService createService( final IBatchServiceDescription description, final String name )
    throws ProblemException
  {
    IBatchService service = description.createService( name );
    addService( service );
    return service;
  }
  
  /**
   * Searches for a batch service that matches the specified description. If no service
   * could be found <code>null</code> is returned.
   * 
   * @param description The {@link IBatchServiceDescription} that describes the service.
   * @return A service that matches the specified description or null if no such service could be found.
   */
  public IBatchService findToken( final IBatchServiceDescription description ) {
    IBatchService resultService = null;
    for ( IBatchService service : this.services ) {
      if ( service.getDescription().equals( description ) ) {
        resultService = service;
        break;
      }
    }
    return resultService;
  }
  
  /**
   * Get all services that are currently available.
   * 
   * @return A copy of the internal list of managed services.
   */
  public List< IBatchService > getServices() {
    return new ArrayList< IBatchService >( this.services );
  }
  
  
 /**
 * Get all services that are currently available based on their unique type name.
 *
 * @param typeName The unique type name.
 * @return A copy of the internal list of managed services.
 */
  public List< IBatchService > getServices( final String typeName) {
  List<IBatchService> newList = new ArrayList< IBatchService >();
      
      for (IBatchService service : this.services ){
        if (service.getDescription().getServiceTypeName().equals( typeName )){
          newList.add( service );
        }
          
      }
      return newList;
  }

  /**
   * Destroy the specified {@link IBatchService}. This means especially that the
   * specified service is removed from the list of currently managed services. Therefore this
   * service is not longer accessible from this manager and should not longer be used.
   *  
   * @param service The {@link IBatchService} to be destroyed.
   */
  public void destroyService( final IBatchService service ) {
    removeService( service );
    service.disconnectFromServer();
  }
  
  /**
   * Get the number of currently available services.
   * 
   * @return The number of services that are currently contained in the internal list of
   * managed services.
   */
  public int getServiceCount() {
    return this.services.size();
  }
  
  /**
   * Determine if this service manager's list of managed services is currently empty.
   * 
   * @return True if there are no services available, false otherwise.
   */
  public boolean isEmpty() {
    return 0 == getServiceCount();
  }
 
  /**
   * Add a new {@link IContentChangeListener} to the list of listeners. The listeners are
   * always informed when a new service is added to or an old service is removed from the
   * internal list of managed services.
   * 
   * @param listener The {@link IContentChangeListener} that should be added to the list
   *                 of listeners.
   * @see #removeContentChangeListener(IContentChangeListener)
   * @see org.eclipse.compare.IContentChangeNotifier#addContentChangeListener(org.eclipse.compare.IContentChangeListener)
   */
  public void addContentChangeListener( final IContentChangeListener listener ) {
    this.ccListeners.add( listener );
  }

  
  /**
   * Remove the specified {@link IContentChangeListener} from the list of listener. This
   * listener will not longer be informed about changes made to the list of managed services.
   * 
   * @param listener The {@link IContentChangeListener} that should be removed.
   * @see org.eclipse.compare.IContentChangeNotifier#removeContentChangeListener(org.eclipse.compare.IContentChangeListener)
   */
  public void removeContentChangeListener( final IContentChangeListener listener ) {
    this.ccListeners.remove( listener );
  }

  /**
   * Add the specified IBatchService to the list of managed services.
   * 
   * @param service The service to be added.
   */
  protected void addService( final IBatchService service ) {
    this.services.add( service );
    fireContentChanged();
  }
  
  /**
   * Remove the specified service from the list of managed services.
   * 
   * @param service The service to be removed.
   * @return True if the service was contained in the list of managed services.
   */
  protected boolean removeService( final IBatchService service ) {
    boolean result = this.services.remove( service );

    if ( result ) {
      fireContentChanged();
    }
    return result;
  }

  /**
   * Notify all registered IContentChangeListeners about content changes, i.e. a new service was
   * added or an existing service was removed.
   */
  protected void fireContentChanged() {
    Object[] list = this.ccListeners.getListeners();
    for ( int i = 0 ; i < list.length ; i++ ) {
      if ( list[i] instanceof IContentChangeListener ) {
        IContentChangeListener listener = ( IContentChangeListener ) list[i];
        listener.contentChanged( this );
      }
    }
  }
}
