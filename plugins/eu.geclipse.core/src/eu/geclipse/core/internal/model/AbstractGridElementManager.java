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

import java.util.Collection;
import java.util.Hashtable;
import org.eclipse.compare.IContentChangeListener;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.IGridModelStatus;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.impl.AbstractGridElement;
import eu.geclipse.core.model.impl.GridModelStatus;

/**
 * Internal abstract implementation of an {@link IGridElementManager}.  
 */
public abstract class AbstractGridElementManager
    extends AbstractGridElement
    implements IGridElementManager {
  
  /**
   * The internal element table that holds the managed elements.
   */
  private Hashtable< String, IGridElement > elements
    = new Hashtable< String, IGridElement >();
  
  private ListenerList ccListeners
    = new ListenerList();
  
  /**
   * Standard constructor.
   */
  protected AbstractGridElementManager() {
    super( null );
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
  public IGridElement create( final IGridElementCreator creator ) throws GridModelException {
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
    return this.elements.get( name );
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
  
  @Override
  public abstract String getName();
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getParent()
   */
  @Override
  public IGridContainer getParent() {
    return GridModel.getRoot();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getPath()
   */
  @Override
  public IPath getPath() {
    IPath parentPath = getParent().getPath();
    return parentPath.append( getName() );
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
  @Override
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
   * @see eu.geclipse.core.model.IGridElement#isVirtual()
   */
  public boolean isVirtual() {
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
  
  public void addContentChangeListener( final IContentChangeListener listener ) {
    this.ccListeners.add( listener );
  }

  public void removeContentChangeListener( final IContentChangeListener listener ) {
    this.ccListeners.remove( listener );
  }
  
  public void setDirty() {
    // empty implementation
  }
  
  protected boolean addElement( final IGridElement element ) throws GridModelException {
    boolean result = false;
    testCanManage( element );
    String name = element.getName();
    IGridElement oldElement = findChild( name );
    if ( element != oldElement ) {
      if ( oldElement != null ) {
        delete( oldElement );
      }
      this.elements.put( name, element );
      result = true;
      fireContentChanged();
    }
    return result;
  }
  
  protected boolean removeElement( final IGridElement element ) {
    String name = element.getName();
    boolean removed = ( this.elements.remove( name ) != null);
    if ( removed ) {
      fireContentChanged();
    }
    return removed;
  }
  
  protected void fireContentChanged() {
    Object[] listeners = this.ccListeners.getListeners();
    for ( int i = 0 ; i < listeners.length ; i++ ) {
      ( ( IContentChangeListener ) listeners[i] ).contentChanged( this );
    }
  }
  
  protected void testCanManage( final IGridElement element ) throws GridModelException {
    if ( !canManage( element ) ) {
      IGridModelStatus status = new GridModelStatus(
        IStatus.ERROR,
        IStatus.CANCEL,
        "The specified element can not be managed by this manager",
        null
      );
      throw new GridModelException( status );
    }
  }
  
}
