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

package eu.geclipse.core.filesystem.internal.filesystem;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Internal implementation of the {@link IGridConnection}.
 */
public class ConnectionRoot
    extends ConnectionElement
    implements IGridConnection {
  
  /**
   * Create a new connection root from the specified folder.
   * 
   * @param folder The folder from which to create a new element.
   * This folder has to be a folder linked to a g-Eclipse URI.
   */
  public ConnectionRoot( final IFolder folder ) {
    super( folder );
  }
  
  /**
   * Create a new link to file. File can be remotely.
   * @param file link to remote file (create by {@link IFolder#createLink(java.net.URI, int, org.eclipse.core.runtime.IProgressMonitor)}
   * This file has to be a file linked to a g-Eclipse URI.
   */
  public ConnectionRoot( final IFile file ) {
    super( file );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridConnection#isGlobal()
   */
  public boolean isGlobal() {
    IGridProject project = getProject();
    return ( project == null ) || project.isHidden();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.filesystem.internal.filesystem.ConnectionElement#isHidden()
   */
  @Override
  public boolean isHidden() {
    return getName().startsWith( "." ); //$NON-NLS-1$
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IManageable#getManager()
   */
  public IGridElementManager getManager() {
    return GridModel.getConnectionManager();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IStorableElement#load()
   */
  public void load() throws ProblemException {
    // TODO Auto-generated method stub
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IStorableElement#save()
   */
  public void save() throws ProblemException {
    // TODO Auto-generated method stub
  }

}
