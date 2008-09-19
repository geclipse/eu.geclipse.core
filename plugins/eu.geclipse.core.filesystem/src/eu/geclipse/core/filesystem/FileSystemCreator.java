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
 *****************************************************************************/

package eu.geclipse.core.filesystem;

import java.net.URI;

import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.provider.FileInfo;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.filesystem.internal.Activator;
import eu.geclipse.core.filesystem.internal.filesystem.ConnectionElement;
import eu.geclipse.core.filesystem.internal.filesystem.ConnectionRoot;
import eu.geclipse.core.model.ICreatorSourceMatcher;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.impl.AbstractGridElementCreator;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Grid element creator for file system mounts. This class both creates
 * root mounts for file systems and element mounts for child nodes.
 */
public class FileSystemCreator
    extends AbstractGridElementCreator
    implements ICreatorSourceMatcher {

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridElementCreator#create(eu.geclipse.core.model.IGridContainer)
   */
  public IGridElement create( final IGridContainer parent )
      throws ProblemException {
    
    IGridElement result = null;
    IResource resource = ( IResource ) getSource();
    
    if ( isFileSystemLink( resource )
        && !isJobFile( resource ) ) {
      result = createConnectionRoot( resource );
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
    
    try {
      IFileInfo info = element.getConnectionFileInfo();      
      if ( info instanceof FileInfo ) {
        FileInfo fileInfo = (FileInfo)info;
        ( ( FileInfo ) info ).setExists( true );
        if( resource instanceof IFile ) {
          fileInfo.setDirectory( false );
        }
      }
    } catch ( CoreException cExc ) {
      // Should never happen, if it does just log it
      Activator.logException( cExc );
    }    
    return element;
  }
  
  /**
   * Creates a connection root from the specified linked folder.
   * 
   * @param folder The linked folder from which to create the root.
   * @return A {@link ConnectionRoot} that corresponds to the specified
   * folder.
   */
  private ConnectionElement createConnectionRoot( final IResource resource ) {
    ConnectionElement connection = null;
    
    if( resource instanceof IFolder ) {
      connection = new ConnectionRoot( (IFolder)resource );
    } else if( resource instanceof IFile ) {
      connection = new ConnectionRoot( (IFile)resource );
    }
    
    if ( connection != null ) {
      try {
        IFileInfo info = connection.getConnectionFileInfo();
        if ( info instanceof FileInfo ) {
          FileInfo fileInfo = (FileInfo)info;
          fileInfo.setName( resource.getName() );
          fileInfo.setExists( true );
          fileInfo.setDirectory( resource instanceof IFolder );
        }
      } catch ( CoreException cExc ) {
        // Should never happen, if it does just log it
        Activator.logException( cExc );
      }
    }
    
    return connection;
    
  }
  
  public boolean canCreate( final Object source ) {
    
    boolean result = false;
    
    if ( source instanceof IResource ) {
      result = isFileSystemElement( ( IResource ) source );
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
   * Determines if the specified resource is a linked resource.
   * 
   * @param resource The resource to be tested.
   * @return True if the specified resource is a linked resource and
   * the link is a g-Eclipse URI.
   */
  private boolean isFileSystemLink( final IResource resource ) {
    
    boolean result = false;
    
    if ( ( resource != null ) 
        && ( resource instanceof IFolder || resource instanceof IFile ) ) {      
      if ( resource.isLinked() ) {
        URI uri = resource.getRawLocationURI();
        if ( uri != null ) { // uri is null if the link target does not exist anymore
          String scheme = uri.getScheme();
          if ( GEclipseURI.getScheme().equals( scheme ) ) {
            result = true;
          }
        }
      }
    }

    return result;
  }

  private boolean isJobFile( final IResource resource ) {
    boolean jobFile = false;
    IContainer parent = resource.getParent();
    
    while( parent != null 
        && !( parent instanceof IProject )
        && !jobFile ) {
      IPath location = parent.getLocation();
      
      if( location != null ) {
        jobFile = "job".equals( location.getFileExtension() );
      }
      parent= parent.getParent();
    }
    
    return jobFile;
  }

}
