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

package eu.geclipse.core.filesystem;

import java.net.URI;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import eu.geclipse.core.filesystem.internal.Activator;
import eu.geclipse.core.filesystem.internal.filesystem.ConnectionElement;
import eu.geclipse.core.filesystem.internal.filesystem.ConnectionRoot;
import eu.geclipse.core.filesystem.internal.filesystem.IFileSystemProperties;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;

/**
 * Grid element creator for file system mounts. This class both creates
 * root mounts for file systems and element mounts for child nodes.
 */
public class FileSystemCreator
    extends AbstractGridElementCreator {

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#canCreate(java.lang.Class)
   */
  public boolean canCreate( final Class<? extends IGridElement> elementType ) {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#create(eu.geclipse.core.model.IGridContainer)
   */
  public IGridElement create( final IGridContainer parent )
      throws GridModelException {
    
    IGridElement result = null;
    IResource resource = ( IResource ) getObject();
    
    if ( isFileSystemLink( resource ) ) {
      result = createConnectionRoot( ( IFolder ) resource );
    } else {
      result = createConnectionElement( resource );
    }
    
    return result;
    
  }
  
  /**
   * Creates a connection element from the specified resource.
   * 
   * @param resource The resource for creating the element.
   * @return A {@link ConnectionElement} that corresponds to the specified
   * resource.
   */
  private ConnectionElement createConnectionElement( final IResource resource ) {
    ConnectionElement element = new ConnectionElement( resource );
    return element;
  }
  
  /**
   * Creates a connection root from the specified linked folder.
   * 
   * @param folder The linked folder from which to create the root.
   * @return A {@link ConnectionRoot} that corresponds to the specified
   * folder.
   */
  private ConnectionRoot createConnectionRoot( final IFolder folder ) {
    ConnectionRoot connection = new ConnectionRoot( folder );
    try {
      IFileInfo info = connection.getConnectionFileInfo();
      if ( info instanceof FileInfo ) {
        ( ( FileInfo ) info ).setExists( true );
      }
    } catch ( CoreException cExc ) {
      // Should never happen, if it does just log it
      Activator.logException( cExc );
    }
    return connection;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElementCreator#internalCanCreate(java.lang.Object)
   */
  @Override
  protected boolean internalCanCreate( final Object fromObject ) {
    
    boolean result = false;
    
    if ( fromObject instanceof IResource ) {
      result = isFileSystemElement( ( IResource ) fromObject );
    }
    
    return result;
    
  }
  
  /**
   * Determines if the specified resource is part of a linked connection.
   * 
   * @param resource The resource to be tested.
   * @return True if the specified resource either is a linked folder
   * or is a direct or indirect child of a linked folder.  
   */
  private boolean isFileSystemElement( final IResource resource ) {
    
    boolean result = isFileSystemLink( resource );
    
    if ( ! result && ( resource != null ) ) {
      result = isFileSystemElement( resource.getParent() );
    }
    
    return result;
    
  }
  
  /**
   * Determines if the specified resource is a linked folder.
   * 
   * @param resource The resource to be tested.
   * @return True if the specified resource is a linked folder and
   * the link is a g-Eclipse URI.
   */
  private boolean isFileSystemLink( final IResource resource ) {
    
    boolean result = false;
    
    if ( ( resource != null ) && ( resource instanceof IFolder ) ) {
      IFolder folder = ( IFolder ) resource;
      if ( folder.isLinked() ) {
        URI uri = folder.getRawLocationURI();
        if ( uri != null ) { // uri is null if the link target does not exist anymore
          String scheme = uri.getScheme();
          if ( IFileSystemProperties.SCHEME.equals( scheme ) ) {
            result = true;
          }
        }
      }
    }

    return result;
    
  }

}
