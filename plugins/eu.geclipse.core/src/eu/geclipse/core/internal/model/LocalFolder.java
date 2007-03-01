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
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;

/**
 * This class represents local folders in the workspace. Each
 * <code>IFolder</code> that is not handled by any registered
 * {@link eu.geclipse.core.model.IGridElementCreator} will be
 * represented by a <code>LocalFolder</code> - at least if this
 * <code>IFolder</code> is visible within the
 * {@link eu.geclipse.core.model.GridModel}. 
 */
public class LocalFolder extends LocalResource implements IGridContainer {
  
  /**
   * The children of this folder.
   */
  private List< IGridElement > children
    = new ArrayList< IGridElement >();
  
  /**
   * Construct a new local folder with the specified parent and the
   * specified corresponding <code>IFolder</code>.
   *  
   * @param parent The parent element of this folder.
   * @param folder The corresponding <code>IFolder</code>.
   */
  LocalFolder( final IGridContainer parent,
               final IFolder folder ) {
    super( parent, folder );
    initChildren();
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
  public IGridElement create( final IGridElementCreator creator )
    throws GridModelException {
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
    for ( IGridElement child : this.children ) {
      child.dispose();
    }
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
    return this.children.size();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#getChildren()
   */
  public IGridElement[] getChildren( final IProgressMonitor monitor ) {
    return this.children.toArray( new IGridElement[ this.children.size() ] );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#hasChildren()
   */
  public boolean hasChildren() {
    return !this.children.isEmpty();
  }
  
  public boolean isDirty() {
    return false;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#isLazy()
   */
  public boolean isLazy() {
    return false;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridContainer#setDirty()
   */
  public void setDirty() {
    // empty implementation
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
   * Convenience method that returns the <code>IFolder</code> out
   * of the resource.
   * 
   * @return The corresponding <code>IFolder</code>.
   */
  protected IFolder getFolder() {
    return ( IFolder ) getResource();
  }
  
  private IGridElementCreator findCreator( final IResource resource ) {
    IGridElementCreator result = null;
    List<IGridElementCreator> creators
      = Extensions.getRegisteredElementCreators();
    for ( IGridElementCreator creator : creators ) {
      if ( creator.canCreate( resource ) ) {
        result = creator;
        break;
      }
    }
    if ( result == null ) {
      result = findStandardCreator( resource );
    }
    return result;
  }
  
  private IGridElementCreator findStandardCreator( final IResource resource ) {
    IGridElementCreator result = null;
    List< IGridElementCreator > standardCreators
      = GridModel.getStandardCreators();
    for ( IGridElementCreator creator : standardCreators ) {
      if ( creator.canCreate( resource ) ) {
        result = creator;
        break;
      }
    }
    return result;
  }
  
  private void initChildren() {
    IFolder folder = getFolder();
    try {
      IResource[] members = folder.members();
      for ( IResource member : members ) {
        IGridElementCreator creator = findCreator( member );
        if ( creator != null ) {
          create( creator );
        }
      }
    } catch( CoreException cExc ) {
      Activator.logException( cExc );
    }
  }
  
}
