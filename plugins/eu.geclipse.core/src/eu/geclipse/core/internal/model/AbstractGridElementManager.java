/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *    Ariel Garcia      - updated to new problem reporting
 *****************************************************************************/

package eu.geclipse.core.internal.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.model.notify.GridModelEvent;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridModelListener;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.impl.AbstractGridElement;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Internal abstract implementation of an {@link IGridElementManager}.  
 */
public abstract class AbstractGridElementManager
    extends AbstractGridElement
    implements IGridElementManager {
  
  /**
   * The internal element table that holds the managed elements.
   */
  private Hashtable< IPath, IGridElement > elements
    = new Hashtable< IPath, IGridElement >();
  
  /**
   * The list of {@link IGridModelListener}s.
   */
  private List< IGridModelListener > listeners
    = new ArrayList< IGridModelListener >();
  
  /**
   * Standard constructor.
   */
  protected AbstractGridElementManager() {
    super();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridModelNotifier#addGridModelListener(eu.geclipse.core.model.IGridModelListener)
   */
  public void addGridModelListener( final IGridModelListener listener ) {
    if ( !this.listeners.contains( listener ) ) {
      this.listeners.add( listener );
    }
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#canContain(eu.geclipse.core.model.IGridElement)
   */
  public boolean canContain( final IGridElement element ) {
    return canManage( element );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#contains(eu.geclipse.core.model.IGridElement)
   */
  public boolean contains( final IGridElement element ) {
    return this.elements.values().contains( element );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#create(eu.geclipse.core.model.IGridElementCreator)
   */
  public IGridElement create( final IGridElementCreator creator ) throws ProblemException {
    IGridElement newElement = creator.create( this );
    if ( newElement != null ) {
      addElement( newElement );
    }
    return newElement;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#delete(eu.geclipse.core.model.IGridElement)
   */
  public void delete( final IGridElement child ) {
    if ( removeElement( child ) ) {
      child.dispose();
    }
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#findChild(java.lang.String)
   */
  public IGridElement findChild( final String name ) {
    IGridElement result = null;
    for ( IGridElement element : this.elements.values() ) {
      if ( element.getName().equals( name ) ) {
        result = element;
        break;
      }
    } 
    return result;
  }
  
  /**
   * Try to find the element with the specified path.
   * 
   * @param path The path of the element.
   * @return The element matching the specified path or
   * <code>null</code> if no such element could be found.
   */
  public IGridElement findChild( final IPath path ) {
    return this.elements.get( path );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#findChildWithResource(java.lang.String)
   */
  public IGridElement findChildWithResource( final String resourceName ) {
    IGridElement result = null;
    for ( IGridElement element : this.elements.values() ) {
      IResource resource = element.getResource();
      if ( resource != null ) {
        if ( resource.getName().equals( resourceName ) ) {
          result = element;
          break;
        }
      } 
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#getChildCount()
   */
  public int getChildCount() {
    return this.elements.size();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#getChildren()
   */
  public IGridElement[] getChildren( final IProgressMonitor monitor ) {
    Collection< IGridElement > values = this.elements.values();
    return values.toArray( new IGridElement[ values.size() ] );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#hasChildren()
   */
  public boolean hasChildren() {
    return !this.elements.isEmpty();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#isDirty()
   */
  public boolean isDirty() {
    return false;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getFileStore()
   */
  public IFileStore getFileStore() {
    IFileStore managerStore = getManagerStore();
    IFileStore childStore = managerStore.getChild( getName() );
    IFileInfo childInfo = childStore.fetchInfo();
    if ( !childInfo.exists() ) {
      try {
        childStore.mkdir( EFS.NONE, null );
      } catch( CoreException cExc ) {
        Activator.logException( cExc );
      }
    }
    return childStore;
  }
  
  /**
   * Get the {@link IFileStore} were managers store their data.
   * 
   * @return The file store were all managers should save their
   * data to.
   */
  public static IFileStore getManagerStore() {
    IPath statePath = Activator.getDefault().getStateLocation();
    return EFS.getLocalFileSystem().getStore( statePath );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getParent()
   */
  public IGridContainer getParent() {
    return GridModel.getRoot();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getPath()
   */
  public IPath getPath() {
    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    IPath rootPath = root.getFullPath();
    return rootPath.append( getName() );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getProject()
   */
  @Override
  public IGridProject getProject() {
    return null;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getResource()
   */
  public IResource getResource() {
    return null;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#isLazy()
   */
  public boolean isLazy() {
    return false;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#isLocal()
   */
  public boolean isLocal() {
    return true;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getAdapter(java.lang.Class)
   */
  @Override
  @SuppressWarnings("unchecked")
  public Object getAdapter( final Class adapter ) {
    return null;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#refresh(org.eclipse.core.runtime.IProgressMonitor)
   */
  public void refresh( final IProgressMonitor monitor ) {
    // empty implementation
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridModelNotifier#removeGridModelListener(eu.geclipse.core.model.IGridModelListener)
   */
  public void removeGridModelListener( final IGridModelListener listener ) {
    this.listeners.remove( listener );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#setDirty()
   */
  public void setDirty() {
    // empty implementation
  }
  
  /**
   * Add the specified element to the managed elements of this manager.
   * If an old element with the same name exists this old element will
   * be deleted before the new one is added.
   * 
   * @param element The element to be added.
   * @return True if the operation was successful. 
   * @throws ProblemException If an error occurs.
   */
  public boolean addElement( final IGridElement element ) throws ProblemException {
    boolean result = false;
    testCanManage( element );
    IPath path = element.getPath();
    IGridElement oldElement = findChild( path );
    if ( element != oldElement ) {
      if ( oldElement != null ) {
        delete( oldElement );
      }
      this.elements.put( path, element );
      IGridModelEvent event
        = new GridModelEvent( IGridModelEvent.ELEMENTS_ADDED,
                              this,
                              new IGridElement[] { element } );
      fireGridModelEvent( event );
      result = true;
    }
    return result;
  }
  
  /**
   * Remove the specified element from this manager.
   * 
   * @param element The element to be removed.
   * @return True if the element was found and could be removed.
   */
  public boolean removeElement( final IGridElement element ) {
    IPath path = element.getPath();
    boolean removed = ( this.elements.remove( path ) != null);
    if ( removed ) {
      IGridModelEvent event
        = new GridModelEvent( IGridModelEvent.ELEMENTS_REMOVED,
                              this,
                              new IGridElement[] { element } );
      fireGridModelEvent( event );
    }
    return removed;
  }
  
  /**
   * Distribute the specified event to all registered
   * {@link IGridModelListener}s.
   * 
   * @param event The event to be distributed.
   */
  protected void fireGridModelEvent( final IGridModelEvent event ) {
    for ( IGridModelListener listener : this.listeners ) {
      listener.gridModelChanged( event );
    }
  }
  
  /**
   * Test if the specified element can be managed by this manager.
   * Throw an exception if this is not the case.
   * 
   * @param element The element to be tested.
   * @throws ProblemException If the tested element can not be
   * managed by this manager.
   */
  protected void testCanManage( final IGridElement element ) throws ProblemException {
    if ( ! canManage( element ) ) {
      throw new ProblemException( ICoreProblems.MODEL_ELEMENT_NOT_MANAGEABLE,
                                  Activator.PLUGIN_ID );
    }
  }
  
}
