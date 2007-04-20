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

package eu.geclipse.core.model.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import eu.geclipse.core.internal.model.GridModelEvent;
import eu.geclipse.core.internal.model.GridRoot;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.GridModelProblems;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IManageable;

/**
 * Base implementation of the {@link IGridContainer} interface that
 * implements basic functionalities of a grid container.
 */
public abstract class AbstractGridContainer
    extends AbstractGridElement
    implements IGridContainer {
  
  private int processEventsPolicy = 0;
  
  private Hashtable< Integer, List< IGridElement > > eventQueue
    = new Hashtable< Integer, List< IGridElement > >();
  
  /**
   * List of currently know children.
   */
  private List< IGridElement > children
    = new ArrayList< IGridElement >();
  
  /**
   * Dirty flag of this container.
   */
  private boolean dirty;

  protected AbstractGridContainer() {
    setDirty();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#canContain(eu.geclipse.core.model.IGridElement)
   */
  public boolean canContain( final IGridElement element ) {
    return false;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#contains(eu.geclipse.core.model.IGridElement)
   */
  public boolean contains( final IGridElement element ) {
    return this.children.contains( element  );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#create(eu.geclipse.core.model.IGridElementCreator)
   */
  public IGridElement create( final IGridElementCreator creator )
      throws GridModelException {
    IGridElement element = creator.create( this );
    element = addElement( element );    
    return element;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#delete(eu.geclipse.core.model.IGridElement)
   */
  public void delete( final IGridElement child )
      throws GridModelException {
    removeElement( child );
    if ( child instanceof IManageable ) {
      IGridElementManager manager
        = ( ( IManageable ) child ).getManager();
      manager.removeElement( child );
    }
    child.dispose();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#dispose()
   */
  @Override
  public void dispose() {
    deleteAll();
    super.dispose();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#getChildCount()
   */
  public int getChildCount() {
    int result;
    if ( isLazy() && isDirty() ) {
      result = 1;
    } else {
      result = this.children.size();
    }
    return result;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#getChildren(org.eclipse.core.runtime.IProgressMonitor)
   */
  public IGridElement[] getChildren( final IProgressMonitor monitor )
      throws GridModelException {
    if ( isLazy() && isDirty() ) {
      deleteAll();
      boolean result = fetchChildren( monitor );
      setDirty( !result );
    }
    return this.children.toArray( new IGridElement[this.children.size()] );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#hasChildren()
   */
  public boolean hasChildren() {
    return isLazy() || !this.children.isEmpty();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#findChild(java.lang.String)
   */
  public IGridElement findChild( final String name ) {
    IGridElement result = null;
    for ( IGridElement child : this.children ) {
      if ( child.getName().equals( name ) ) {
        result = child;
        break;
      } 
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#findChildWithResource(java.lang.String)
   */
  public IGridElement findChildWithResource( final String resourceName ) {
    IGridElement result = null;
    for ( IGridElement child : this.children ) {
      if ( !child.isVirtual() ) {
        if ( child.getResource().getName().equals( resourceName ) ) {
          result = child;
          break;
        }
      } 
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#isDirty()
   */
  public boolean isDirty() {
    return this.dirty;
  }
  
  public void refresh( final IProgressMonitor monitor )
      throws GridModelException {
    setProcessEvents( false );
    try {
      if ( isLocal() ) {
        IContainer container = ( IContainer ) getResource();
        try {
          container.refreshLocal( IResource.DEPTH_INFINITE, monitor );
        } catch( CoreException cExc ) {
          throw new GridModelException( GridModelProblems.REFRESH_FAILED, cExc );
        }
      } else {
        setDirty();
        try {
          getChildren( monitor );
        } catch ( GridModelException gmExc ) {
          throw new GridModelException( GridModelProblems.REFRESH_FAILED, gmExc );
        }
      }
    } finally {
      setProcessEvents( true );
    }
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#setDirty()
   */
  public void setDirty() {
    setDirty( true );
  }
  
  /**
   * Add an element as child to this container. If a child with the same
   * name is already contained in this contained this old child will
   * be deleted.
   * 
   * @param element The new child of this container or <code>null</code>
   * if an error occures.
   * @return The newly added element.
   */
  protected IGridElement addElement( final IGridElement element )
      throws GridModelException {
    if ( element != null ) {
      testCanContain( element );
      IGridElement oldChild = findChild( element.getName() );
      if ( oldChild != null ) {
        delete( oldChild );
      }
      this.children.add( element );
      GridRoot.registerElement( element );
      fireGridModelEvent( IGridModelEvent.ELEMENTS_ADDED, element );
    }
    return element;
  }
  
  /**
   * Remove all children from this container and call their
   * {@link #dispose()} methods.
   */
  protected void deleteAll() {
    if ( ( this.children != null ) && !this.children.isEmpty() ) {
      for ( IGridElement child : this.children ) {
        child.dispose();
      }
      IGridElement[] elements
        = this.children.toArray( new IGridElement[ this.children.size() ] );
      fireGridModelEvent( IGridModelEvent.ELEMENTS_REMOVED, elements );
      this.children.clear();
    }
  }
  
  /**
   * Fetch the children of this container. For a non-lazy container
   * the children are fetched when the container is constructed. For
   * lazy containers the children are fetched by the
   * {@link #getChildren(IProgressMonitor)} method if the container is
   * dirty.
   * 
   * @param monitor A progress monitor to monitor the progress of this
   * maybe long running method.
   * @return True if the operation was successful.
   */
  @SuppressWarnings("unused")
  protected boolean fetchChildren( @SuppressWarnings("unused")
                                   final IProgressMonitor monitor )
      throws GridModelException {
    return true;
  }
  
  protected void removeElement( final IGridElement element ) {
    boolean result = this.children.remove( element );
    if ( result ) {
      fireGridModelEvent( IGridModelEvent.ELEMENTS_REMOVED, element );
    }
  }
    
  /**
   * Set the dirty flag of this container.
   * 
   * @param d The new value of the container's dirty flag.
   */
  protected void setDirty( final boolean d ) {
    this.dirty = d;
  }
  
  protected void setProcessEvents( final boolean enabled ) {
    
    if ( enabled && ( this.processEventsPolicy > 0) ) {
      this.processEventsPolicy--;
    } else if ( !enabled ) {
      this.processEventsPolicy++;
    }
    
    if ( this.processEventsPolicy == 0 ) {
      processEvents();
    }
  }
  
  private void fireGridModelEvent( final int type,
                                   final IGridElement element ) {
    fireGridModelEvent( type, new IGridElement[] { element } );
  }
  
  private void fireGridModelEvent( final int type,
                                   final IGridElement[] elements ) {
    queueEvent( type, elements );
  }
  
  private void processEvents() {
    GridRoot gridRoot = GridRoot.getRoot();
    if ( gridRoot != null ) {
      for ( Map.Entry< Integer, List< IGridElement > > entry : this.eventQueue.entrySet() ) {
        int type = entry.getKey().intValue();
        List< IGridElement > elementList = entry.getValue();
        IGridElement[] elements = elementList.toArray( new IGridElement[ elementList.size() ] );
        IGridModelEvent event
          = new GridModelEvent( type,
                                this,
                                elements );
        gridRoot.fireGridModelEvent( event );
      }
    }
    this.eventQueue.clear();
  }
  
  private void queueEvent( final int type,
                           final IGridElement[] elements ) {
    
    Integer iType = new Integer( type );
    List< IGridElement > elementList = this.eventQueue.get( iType );
    
    if ( elementList == null ) {
      elementList = new ArrayList< IGridElement >();
      this.eventQueue.put( iType, elementList );
    }
    
    for ( IGridElement element : elements ) {
      if ( !elementList.contains( element ) ) {
        elementList.add( element );
      }
    }
    
    if ( this.processEventsPolicy == 0 ) {
      processEvents();
    }
    
  }
  
  /**
   * Test if this container can contain the specified element
   * and throw a {@link GridModelException} if this is not the
   * case.
   * 
   * @param element The element to be tested.
   * @throws GridModelException Thrown if {@link #canContain(IGridElement)}
   * returns false for the specified element.
   */
  private void testCanContain( final IGridElement element )
      throws GridModelException {
    if ( !canContain( element ) ) {
      throw new GridModelException( GridModelProblems.CONTAINER_CAN_NOT_CONTAIN );
    }
  }
  
}
