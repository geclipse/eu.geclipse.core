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

package eu.geclipse.core.internal.model;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;

/**
 * This class implements the standard behaviour of a virtual grid
 * container. 
 */
public class VirtualGridContainer
    extends VirtualGridElement
    implements IGridContainer {
  
  /**
   * The children of this container.
   */
  private List< IGridElement > children
    = new ArrayList< IGridElement >();
  
  /**
   * Determines if this container is dirty, i.e. if the children
   * have to be loaded again.
   */
  private boolean dirty;
  
  /**
   * Create a new virtual grid container.
   *  
   * @param parent The parent element.
   * @param name The name of this container.
   */
  public VirtualGridContainer( final IGridContainer parent,
                        final String name ) {
    super( parent, name );
    setDirty();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#contains(eu.geclipse.core.model.IGridElement)
   */
  public boolean contains( final IGridElement element ) {
    return this.children.contains( element );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#create(eu.geclipse.core.model.IGridElementCreator)
   */
  public IGridElement create( final IGridElementCreator creator ) throws GridModelException {
    IGridElement element = creator.create( this );
    element = addElement( element );
    return element;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#delete(eu.geclipse.core.model.IGridElement)
   */
  public void delete( final IGridElement child ) {
    this.children.remove( child );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.VirtualGridElement#dispose()
   */
  @Override
  public void dispose() {
    deleteAll();
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
      IResource resource = child.getResource();
      if ( ( resource != null ) && ( resource.getName().equals( resourceName ) ) ) {
        result = child;
        break;
      }
    }
    return result;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#getChildCount()
   */
  public int getChildCount() {
    return isDirty() ? 1 : this.children.size();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#getChildren()
   */
  public IGridElement[] getChildren( final IProgressMonitor monitor ) {
    testAndProcessDirty( monitor );
    return this.children.toArray( new IGridElement[ this.children.size() ] );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#hasChildren()
   */
  public boolean hasChildren() {
    return isDirty() ? true : !this.children.isEmpty();
  }
  
  /**
   * Determines if this virtual container is dirty. A dirty
   * container has to reload its children as soon as any
   * information concerning the children is asked.
   * 
   * @return True if the container is marked as dirty.
   * @see #setDirty()
   */
  public boolean isDirty() {
    return this.dirty;
  }
  
  public boolean isLazy() {
    return true;
  }
  
  /**
   * Set this container to be dirty.
   * 
   * @see #isDirty()
   */
  public void setDirty() {
    this.dirty = true;
  }
  
  /**
   * Add the specified element to the list of children of this
   * container. This method checks if an element with the same
   * name already exists. If one such element is found, this
   * old element will be deleted and replaced with the new
   * element.
   * 
   * @param element The element to be added.
   * @return The element itself or the old element if the specified
   * element is <code>null</code> and there was such an old element
   * found. <code>null</code> in all other cases. 
   */
  protected IGridElement addElement( final IGridElement element ) {
    IGridElement oldElement = null;
    if ( element != null ) {
      oldElement = findChild( element.getName() );
      if ( oldElement != null ) {
        delete( oldElement );
      }
      this.children.add( element );
    }
    return element == null ? oldElement : element;
  }
  
  /**
   * Delete all currently known children.
   */
  protected void deleteAll() {
    for ( IGridElement child : this.children ) {
      child.dispose();
    }
    this.children.clear();
  }
  
  /**
   * Dynamically fetch the children. This method is intended to be
   * used by lazy loading mechanisms. This default implementation
   * does nothing at the moment. A real implementation should fetch
   * the new content and add this new content with
   * {@link #addElement(IGridElement)} to the container. This method
   * should never be called directly. Instead the
   * {@link #testAndProcessDirty()} method should be called before any
   * relevant operation on the children of this container.
   * 
   * @param monitor A progress monitor used to indicate the progress.
   * It is assumed that this is never <code>null</code>.
   */
  protected void fetchChildren( @SuppressWarnings("unused")
                                final IProgressMonitor monitor ) {
    // empty implementation
  }
  
  /**
   * Tests if this container in currently marked as dirty. If it is
   * dirty the children are deleted and the {@link #fetchChildren()}
   * method is called in order to reload the children. After this
   * method has been executed the container is guaranteed to be not
   * dirty.
   * 
   * @param monitor The progress monitor or null. If this is <code>null</code>
   * a new {@link NullProgressMonitor} will be created before
   * {@link #fetchChildren(IProgressMonitor)} is called.
   * @return True if the container was dirty and the children were
   * therefore reloaded.
   */
  private boolean testAndProcessDirty( final IProgressMonitor monitor ) {
    boolean result = this.dirty;
    if ( result ) {
      deleteAll();
      IProgressMonitor localMonitor
        = monitor != null
        ? monitor
        : new NullProgressMonitor();
      fetchChildren( localMonitor );
      this.dirty = false;
    }
    return result;
  }
  
}
