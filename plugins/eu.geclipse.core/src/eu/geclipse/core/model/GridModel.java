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

package eu.geclipse.core.model;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.filesystem.IFileStore;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.internal.model.ConnectionManager;
import eu.geclipse.core.internal.model.GridConnectionCreator;
import eu.geclipse.core.internal.model.GridModelEvent;
import eu.geclipse.core.internal.model.GridProjectCreator;
import eu.geclipse.core.internal.model.GridRoot;
import eu.geclipse.core.internal.model.JobManager;
import eu.geclipse.core.internal.model.LocalResourceCreator;
import eu.geclipse.core.internal.model.VoManager;

/**
 * The <code>GridModel</code> class is mainly a helper class for dealing
 * with grid model elements. It defines a method the retrieve the root
 * element of the model (@link {@link #getRoot()}) and several other methods
 * to get element creators for specific use cases. 
 */
public class GridModel {
  
  /**
   * A list containing the standard creators.
   * This list is created statically and on the fly when
   * {@link #getStandardCreators()} is called.
   */
  private static List< IGridElementCreator > standardCreators;
  
  /**
   * Get the manager that is dedicated to the management of
   * {@link IGridConnection}s.
   * 
   * @return The core implementation of the {@link IConnectionManager}
   * interface. 
   */
  public static IConnectionManager getConnectionManager() {
    return ConnectionManager.getManager();
  }
  
  /**
   * Get the manager that is dedicated to the management of
   * {@link IGridJob}s.
   * 
   * @return The core implementation of the {@link IJobManager}
   * interface. 
   */
  public static IJobManager getJobManager() {
    return JobManager.getManager();
  }

  /**
   * Get the root element of the grid model tree. This is an immutable
   * internal implementation of the {@link IGridRoot} interface. It is
   * created as singleton the first time this method is called. In
   * subsequent calls this method returns the singleton instance that
   * was created formerly.
   * 
   * @return The root of all evil, i.e. the root element of the grid model.
   * @see GridRoot#getInstance()
   */
  public static IGridRoot getRoot() {
    IGridRoot root=null;
    try{
    root=GridRoot.getInstance();
    }catch(RuntimeException e){
      e.printStackTrace();
      throw e;
    }
    return root;
  }
  
  /**
   * Get a list of all know standard creators, i.e. {@link IGridElementCreator}s
   * that are not specified as extensions of the
   * <code>eu.geclipse.core.gridElementCreator</code> extension point but that
   * belong to the core functionality to create elements. Examples for such
   * creators are the {@link GridProjectCreator} or the
   * {@link LocalResourceCreator}.
   *  
   * @return A list containing all know standard implementations of the
   * {@link IGridElementCreator} interface.
   */
  public static List< IGridElementCreator > getStandardCreators() {
    if ( standardCreators == null ) {
      standardCreators = new ArrayList< IGridElementCreator >();
      standardCreators.add( new GridProjectCreator() );
      standardCreators.add( new LocalResourceCreator() );
      //standardCreators.add( new GridMountCreator() );
      standardCreators.add( new GridConnectionCreator() );
    }
    return standardCreators;
  }
  
  /**
   * Get a list of all element creators that are defined as extensions
   * of the <code>eu.geclipse.core.gridElementCreator</code> extension
   * point.
   * 
   * @return All externally defined element creators.
   * @see Extensions#getRegisteredElementCreators()
   */
  public static List< IGridElementCreator > getElementCreators() {
    return Extensions.getRegisteredElementCreators();
  }
  
  /**
   * Get a list of all element creators that are able to create
   * {@link IGridElement}s of the specified type. Note that this method
   * only searches the extended creators and not the standard creators.
   * 
   * @param elementType The type of the element that should be
   * created.
   * @return A list containing all currently registered
   * {@link IGridElementCreator}s that are able to create elements
   * of the specified type.
   * @see #getElementCreators()
   */
  public static List< IGridElementCreator > getElementCreators( final Class< ? extends IGridElement > elementType ) {
    List< IGridElementCreator > resultList
      = new ArrayList< IGridElementCreator >();
    List<IGridElementCreator> creators
      = getElementCreators();
    for ( IGridElementCreator creator : creators ) {
      if ( creator.canCreate( elementType ) ) {
        resultList.add( creator );
      }
    }
    return resultList;
  }
  
  /**
   * Get all element creators that implement the
   * {@link IStorableElementCreator} interface. Note that this method
   * only searches the extended creators and not the standard creators.
   * 
   * @return A list containing all currently registered element creators
   * that implement the {@link IStorableElementCreator} interface.
   * @see #getElementCreators()
   */
  public static List< IStorableElementCreator > getStorableElementCreators() {
    List< IStorableElementCreator > jobCreators
      = new ArrayList< IStorableElementCreator >();
    List< IGridElementCreator > elementCreators
      = getElementCreators();
    for ( IGridElementCreator creator : elementCreators ) {
      if ( creator instanceof IStorableElementCreator ) {
        jobCreators.add( ( IStorableElementCreator ) creator ); 
      }
    }
    return jobCreators;
  }
  
  /**
   * Get an {@link IStorableElementCreator} that is able to create
   * an element from the specified file store. Searches the list returned
   * by {@link #getStorableElementCreators}.
   * 
   * @param fileStore The {@link IFileStore} from which to create
   * an element.
   * @return A creator that is able to create an element from the
   * specified file store or <code>null</code> if no such creator
   * could be found.
   * @see #getStorableElementCreators()
   */
  public static IStorableElementCreator getStorableElementCreator( final IFileStore fileStore ) {
    IStorableElementCreator result = null;
    List<IStorableElementCreator> creators
      = getStorableElementCreators();
    for ( IStorableElementCreator creator : creators ) {
      if ( creator.canCreate( fileStore ) ) {
        result = creator;
        break;
      }
    }
    return result;
  }
  
  /**
   * Get a list of all element creators that implement the
   * {@link IGridJobCreator} interface. Note that this method
   * only searches the extended creators and not the standard creators.
   * 
   * @return All currently registered job creators.
   * @see #getElementCreators()
   */
  public static List< IGridJobCreator > getJobCreators() {
    List< IGridJobCreator > jobCreators
      = new ArrayList< IGridJobCreator >();
    List< IGridElementCreator > elementCreators
      = getElementCreators();
    for ( IGridElementCreator creator : elementCreators ) {
      if ( creator instanceof IGridJobCreator ) {
        jobCreators.add( ( IGridJobCreator ) creator ); 
      }
    }
    return jobCreators;
  }
  
  /**
   * Get a list of all job creators that are able to create a job from
   * the specified {@link IGridJobDescription}. Searches the list returned
   * by {@link #getJobCreators()}.
   * 
   * @param description The job description from which to create a job.
   * @return All currently registered job managers that are able to create
   * a job from the specified description.
   * @see #getJobCreators()
   */
  public static List< IGridJobCreator > getJobCreators( final IGridJobDescription description ) {
    List< IGridJobCreator > resultList
      = new ArrayList< IGridJobCreator >();
    List<IGridJobCreator> creators = getJobCreators();
    for ( IGridJobCreator creator : creators ) {
      if ( creator.canCreate( description ) ) {
        resultList.add( creator );
      }
    }
    return resultList;
  }
  
  /**
   * Get all element creators that are able to create
   * {@link IVirtualOrganization}s.
   *  
   * @return A list of all {@link IGridElementCreator}s that
   * are able to create virtual organizations.
   * @see #getElementCreators(Class)
   */
  public static List< IGridElementCreator > getVoCreators() {
    return getElementCreators( IVirtualOrganization.class );
  }
  
  /**
   * Get the manager that is dedicated to the management of
   * {@link IVirtualOrganization}s.
   * 
   * @return The core implementation of the {@link IVoManager}
   * interface.
   */
  public static IVoManager getVoManager() {
    return VoManager.getManager();
  }
  
}
