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
package eu.geclipse.core.model;

import java.net.URI;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.reporting.ProblemException;


/**
 * This interface defines some global access points for preferences that are not
 * stored in the metadata area of the workspace.
 */
public interface IGridPreferences {

  /**
   * Creates a global connection, i.e. a connection that is not part of a
   * project.
   * 
   * @param name The name of the connection.
   * @param uri The URI from which to create the connection.
   * @param monitor Used to monitor the progress of this operation.
   * @throws ProblemException If an error occurs during the creation of the
   *             connection.
   */
  public void createGlobalConnection( final String name, final URI uri, final IProgressMonitor monitor )
    throws ProblemException;

  /**
   * Creates a temporary connection, i.e. a connection that is not shown in the
   * UI, neither in a project nor in the connection view. Temporary connections
   * are not referenced with a name.
   * 
   * @param uri The URI from which to create the connection.
   * @return The created connection.
   * @throws ProblemException If an error occurs during the creation of the
   *             connection.
   */
  public IGridConnection createTemporaryConnection( final URI uri )
    throws ProblemException;
  
  /**
   * @return temporary folder in hidden project
   * @throws CoreException
   */
  public IFolder getTemporaryFolder() throws CoreException;
}
