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

import java.net.URI;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.GridModelProblems;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;

/**
 * An {@link IGridElementCreator} for {@link IGridConnection}s.
 */
public class GridConnectionCreator extends AbstractGridElementCreator {

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#canCreate(java.lang.Class)
   */
  public boolean canCreate( final Class<? extends IGridElement> elementType ) {
    return IGridConnection.class.isAssignableFrom( elementType );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#create(eu.geclipse.core.model.IGridContainer)
   */
  public IGridElement create( final IGridContainer parent )
      throws GridModelException {
    
    IGridElement result = null;
    Object o = getObject();
    
    if ( isFsFile( o ) ) {
      if ( o instanceof IFile ) {
        result = createGridConnection( parent, ( IFile ) o );
      } else if ( o instanceof IFileStore ) {
        result = createGridConnection( parent, ( IFileStore ) o );
      }
    }
    
    else if ( o instanceof URI ) {
      result = createGridConnection( parent, ( URI ) o );
    }
    
    return result;
    
  }
    
  /**
   * Create a new {@link IGridConnection} from the contact string
   * contained in the specified connection file.
   * 
   * @param parent The parent element of the new connection.
   * @param fsFile A file containing a contact string from which
   * the new connection will be created.
   * @return The newly created connection.
   * @throws GridModelException If an error occures while creating
   * the connection.
   * @see GridConnection#loadFromFsFile(IFileStore)
   */
  protected IGridConnection createGridConnection( final IGridContainer parent,
                                                  final IFile fsFile )
      throws GridModelException {
    URI uri = fsFile.getLocationURI();
    IFileSystem localFileSystem = EFS.getLocalFileSystem();
    IFileStore fsFileStore = localFileSystem.getStore( uri );
    return createGridConnection( parent, fsFileStore );
  }
  
  protected IGridConnection createGridConnection( final IGridContainer parent,
                                                  final IFileStore fsFile )
      throws GridModelException {
    IFileStore fileStore = GridConnection.loadFromFsFile( fsFile );
    IGridConnection connection = null;
    if( fileStore != null ) {
      connection = new GridConnection( parent, fileStore, fsFile.getName() );
    }
    return connection;
  }
  
  protected IGridConnection createGridConnection( @SuppressWarnings("unused")
                                                  final IGridContainer parent,
                                                  final URI uri )
      throws GridModelException {
    
    IGridConnection connection = null;
    
    try {
      String scheme = uri.getScheme();
      IFileSystem fileSystem = EFS.getFileSystem( scheme );
      IFileStore fileStore = fileSystem.getStore( uri );
      connection = new TemporaryGridConnection( fileStore );
    } catch ( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_CREATE_FAILED, cExc );
    }
    
    return connection;
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.impl.AbstractGridElementCreator#internalCanCreate(java.lang.Object)
   */
  @Override
  protected boolean internalCanCreate( final Object fromObject ) {
    return isFsFile( fromObject ) || ( fromObject instanceof URI );
  }
  
  /**
   * Test if the specified object is a connection file. A connection
   * file's name has to start with a period and has to have ".fs"
   * as file extension.
   *  
   * @param object The object to be tested.
   * @return True if the specified object is a file and its name
   * fits the connection file's naming scheme.
   */
  private boolean isFsFile( final Object object ) {
    return ( ( object instanceof IFile )
      && isFsFile( ( ( IFile ) object ).getName() ) )
      || ( ( object instanceof IFileStore )
          && isFsFile( ( ( IFileStore ) object ).getName() ) );
  }
  
  private boolean isFsFile( final String filename ) {
    return filename.startsWith( "." ) //$NON-NLS-1$
      && filename.endsWith( GridConnection.FILE_EXTENSION );
  }
  
}
