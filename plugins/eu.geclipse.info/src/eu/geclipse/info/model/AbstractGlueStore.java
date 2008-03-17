/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *     Nikolaos Tsioutsias - University of Cyprus
 *****************************************************************************/
package eu.geclipse.info.model;

import java.util.ArrayList;

import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.info.glue.AbstractGlueTable;

/**
 * This is an abstract class that stores all the glue elements. The elements are
 * fetched by the info services. It has some listeners that are used to inform the info 
 * services that they need to re-fetch the content.
 * @author tnikos
 */
public abstract class AbstractGlueStore implements IGlueInfoStore{

  /**
   * The listeners that this store has
   */
  public ArrayList<IGlueStoreChangeListerner> listeners = new ArrayList<IGlueStoreChangeListerner>();
  /**
   * The state listsners that this store has
   */
  public ArrayList<IGlueStoreStateChangeListerner> stateListeners = new ArrayList<IGlueStoreStateChangeListerner>();

  /**
   * The constructor of the GlueStore. The method setVoList is called.
   * @param voList An ArrayList of IVirtualOrganization
   */
  public AbstractGlueStore( final ArrayList<IVirtualOrganization> voList ) {
    setVoList( voList );
  }

  /**
   * This is an abstract method that must be implemented 
   * @param voList
   */
  public abstract void setVoList( final ArrayList<IVirtualOrganization> voList );
  
  /**
   * Adds a listener to the store
   */
  public void addListener( final IGlueStoreChangeListerner listener, final String resourceTypeName ) {
    this.listeners.add( listener );
  }

  /**
   * Removes a listener from the store
   */
  public synchronized void removeListener( final IGlueStoreChangeListerner listener, final String resourceTypeName ) {
    if(this.listeners.contains( listener )){
      this.listeners.remove( listener );
    }
  }

  /** 
   * Adds a state listener to the store
   */
  public void addStateListener( final IGlueStoreStateChangeListerner stateListener ) {
    this.stateListeners.add( stateListener );
  }

  /**
   * Removes a state listener from the store
   */
  public synchronized void removeStateListener( final IGlueStoreStateChangeListerner stateListener ) {
    this.stateListeners.remove( stateListener );
  }

  /**
   * Notify that the information has changed
   */
  public void notifyListeners( final ArrayList<AbstractGlueTable> agtList ) {
    for( IGlueStoreChangeListerner listener : this.listeners ) {
      listener.infoChanged( agtList );
    }
  }

  /**
   * 
   * @param state
   */
  public void notifyStateListeners( final String state ) {
    for( IGlueStoreStateChangeListerner listener : this.stateListeners ) {
      listener.stateChanged( state );
    }
  }

  /**
   * Removes all the listeners
   */
  public void removeAllListeners() {
    while( this.listeners.size()>0 ) {
      removeListener( this.listeners.get( 0 ),null );
    }
  }

  /**
   * Removes all the state listsners
   */
  public void removeAllStateListeners() {
    while( this.stateListeners.size()>0 ) {
      removeStateListener( this.stateListeners.get( 0 ) );
    }
  }
}