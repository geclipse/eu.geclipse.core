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

package eu.geclipse.core.model;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

/**
 * This interface defines some global access points for preferences that
 * are not stored in the metadata area of the workspace.
 */
public interface IGridPreferences {

  /**
   * Creates a global connection, i.e. a connection that is not part
   * of a project.
   * 
   * @param name The name of the connection.
   * @param uri The URI from which to create the connection.
   * @throws GridModelException If an error occures during the creation
   * of the connection.
   */
  public void createGlobalConnection( final String name, final URI uri ) throws GridModelException;
  
  /**
   * Creates a temporary connection, i.e. a connection that is not shown
   * in the UI, neither in a project nor in the connection view. Temporary
   * connections are not referenced with a name.
   * 
   * @param uri The URI from which to create the connection.
   * @return The created connection.
   * @throws GridModelException If an error occures during the creation
   * of the connection.
   */
  public IGridConnection createTemporaryConnection( final URI uri ) throws GridModelException;
  
  public IGridTest createGridTest( final String name, final String extenstion, 
                                   final InputStream inputStream );
}
