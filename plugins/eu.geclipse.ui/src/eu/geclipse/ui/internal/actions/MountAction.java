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

package eu.geclipse.ui.internal.actions;

import java.net.URI;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;

import eu.geclipse.core.filesystem.FileSystem;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.ui.internal.Activator;

/**
 * Action for mounting storage elements as Grid connections.
 */
public class MountAction extends Action {
  
  /**
   * The access protocol used for the mount.
   */
  private String accessProtocol;
  
  /**
   * The storage elements to be mounted.
   */
  private IGridStorage[] sources;
  
  /**
   * Construct a new <code>MountAction</code> for the specified
   * storage elements using the specified protocol.
   * 
   * @param sources The elements that should be mounted.
   * @param protocol The protocol that should be used for the mount.
   */
  protected MountAction( final IGridStorage[] sources,
                         final String protocol ) {
    super( protocol );
    this.accessProtocol = protocol;
    this.sources = sources;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    for ( IGridStorage source : this.sources ) {
      try {
        createMount( source );
      } catch( CoreException cExc ) {
        Activator.logException( cExc );
      }
    }
  }
  
  /**
   * Create a mount file for the specified storage element. The mount
   * file is created in the filesystems folder of the selected project.
   * 
   * @param source The storage element to be mounted.
   * @throws CoreException If the mount file count not be created.
   */
  protected void createMount( final IGridStorage source )
      throws CoreException {
    IGridProject project = source.getProject();
    String mountPointName = IGridProject.DIR_MOUNTS;
    IGridElement mountPoint = project.findChild( mountPointName );
    if ( mountPoint instanceof IGridContainer ) {
      IContainer container = ( IContainer ) mountPoint.getResource();
      if ( container != null ) {
        IPath path = new Path( getMountName( source ) );
        IFolder folder = container.getFolder( path );
        URI accessToken = findAccessToken( this.accessProtocol, source );
        URI masterURI = FileSystem.createMasterURI( accessToken );
        folder.createLink( masterURI, IResource.ALLOW_MISSING_LOCAL, null );
      }
    }
  }
  
  /**
   * Tests if the specified storage element supports the specified protocol
   * and returns it. If it does not support the protocol <code>null</code>
   * will be returned.
   * 
   * @param protocol The protocol to be tested.
   * @param source The source to be tested.
   * @return The protocol or <code>null</code>.
   */
  protected URI findAccessToken( final String protocol,
                                 final IGridStorage source ) {
    URI result = null;
    URI[] accessTokens = source.getAccessTokens();
    for ( URI token : accessTokens ) {
      if ( MountMenu.getProtocol( token ).equalsIgnoreCase( protocol ) ) {
        result = token;
        break;
      }
    }
    return result;
  }
  
  /**
   * Construct the default name of a mount file for the specified
   * storage element.
   * 
   * @param source The storage element for which to create a file name.
   * @return The default file name for the mount file.s
   */
  protected String getMountName( final IGridStorage source ) {
    String[] parts = this.accessProtocol.split( ":" ); //$NON-NLS-1$
    String name = parts[0] + '.' + source.getName() + '.' + parts[1];
    return name;
  }
  
}
