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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.GridModelProblems;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElementManager;

/**
 * Implementation of the {@link IGridConnection} interface.
 */
public class GridConnection
    extends GridConnectionElement
    implements IGridConnection {
  
  /**
   * File extension for connection files.
   */
  protected static final String FILE_EXTENSION = ".fs"; //$NON-NLS-1$
  
  /**
   * The name of the connection file.
   */
  private String fsFileName;
  
  /**
   * Construct a new <code>GridConnection</code> from the specified
   * parameters.
   * 
   * @param parent The parent container of this grid connection.
   * @param fileStore The file store pointing to the associated
   * connection.
   * @param fsFileName The name of the connection file.
   */
  protected GridConnection( final IGridContainer parent,
                            final IFileStore fileStore,
                            final String fsFileName ) {
    super( parent, fileStore, null );
    this.fsFileName = fsFileName;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElement#getFileStore()
   */
  @Override
  public IFileStore getFileStore() {
    return getParent().getFileStore().getChild( this.fsFileName );
  }
  
  /**
   * Get the connection file containing the contact string of
   * this connection.
   * 
   * @return This connection's connection file.
   */
  public IFile getFsFile() {
    IFile result = null;
    IContainer parentResource = ( IContainer ) getParent().getResource();
    if ( parentResource != null ) {
      result = parentResource.getFile( new Path( this.fsFileName ) );
    }
    return result;
  }
  
  public IFileStore getFsFileStore() {
    IFileStore result = getParent().getFileStore();
    result = result.getChild( this.fsFileName );
    return result;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IManageable#getManager()
   */
  public IGridElementManager getManager() {
    return GridModel.getConnectionManager();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.internal.model.VirtualGridElement#getName()
   */
  @Override
  public String getName() {
    return this.fsFileName.substring( 1, this.fsFileName.length() - FILE_EXTENSION.length() );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IStorableElement#load()
   */
  public void load() throws GridModelException {
    // TODO mathias
  }
  
  /**
   * Read the connection string from the specified file store and
   * construct a file store from it.
   * 
   * @param fsFileStore The location of the connection file containing
   * the contact string.
   * @return A file store created from the connection string that
   * was found in the connection file.
   * @throws GridModelException If an error occures while creating
   * the connection. This may be due an {@link IOException} when the
   * connection file is read or a {@link CoreException} when the
   * connection file's input stream is requested or an
   * {@link URISyntaxException} when the {@link URI} is created
   * for the new file store.
   */
  protected static IFileStore loadFromFsFile( final IFileStore fsFileStore )
      throws GridModelException {
    IFileStore result = null;
    try {
      InputStream iStream = fsFileStore.openInputStream( EFS.NONE, null );
      InputStreamReader iReader = new InputStreamReader( iStream );
      BufferedReader bReader = new BufferedReader( iReader );
      String line = bReader.readLine();
      while( ( line != null )
             && ( line.trim().length() == 0 )
             && !line.trim().startsWith( "#" ) ) { //$NON-NLS-1$
        line = bReader.readLine();
      }
      bReader.close();
      if( line != null ) {
        URI uri = new URI( line );
        String scheme = uri.getScheme();
        IFileSystem fileSystem = EFS.getFileSystem( scheme );
        result = fileSystem.getStore( uri );
      }
    } catch( CoreException cExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_LOAD_FAILED, cExc );
    } catch( IOException ioExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_LOAD_FAILED,
                                    ioExc );
    } catch( URISyntaxException usExc ) {
      throw new GridModelException( GridModelProblems.ELEMENT_LOAD_FAILED,
                                    usExc );
    } catch( NullPointerException nullExc ) {
      /*
       * Message from Kasia: In this case new GridModelException cannot be
       * thrown, because, when an exception is thrown no other Grid Elements
       * will be created (in current project)
       */
      Activator.logException( nullExc );
    }
    return result;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IStorableElement#save()
   */
  public void save() throws GridModelException {
    // TODO mathias
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnection#getURI()
   */
  public URI getURI() {
    URI result = null;
    try {
      result = this.getConnectionFileStore().toURI();
    } catch( CoreException ex ) {
      // TODO pawel Log error
    }
    return result;
  }
  
}
