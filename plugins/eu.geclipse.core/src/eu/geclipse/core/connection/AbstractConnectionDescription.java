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
 *    Katarzyna Bylec - initial API and implementation
 *****************************************************************************/
package eu.geclipse.core.connection;

import java.net.URI;

/**
 * Abstract implementation of {@link IConnectionDescription}
 * 
 * @author katis
 */
public abstract class AbstractConnectionDescription
  implements IConnectionDescription
{

  /**
   * Name of the project in which this connection is being created.
   */
  protected String projectName;
  /**
   * Mounting point to which connection should be made
   */
  protected String mountingPoint;
  /**
   * URI defining destination of the connection
   */
  protected URI fileSystemURI;

  public abstract AbstractConnection createConnection();

  public URI getFileSystemURI() {
    return this.fileSystemURI;
  }

  /**
   * Returns the mounting point of the connection (in a local or remote
   * filesystem to which this connection is established)
   * 
   * @return mounting point of filesystem to which connection was established
   */
  public String getMountingPoint() {
    return this.mountingPoint;
  }

  /**
   * Returns Grid Project's name in which connection is created
   * 
   * @return name of the Grid Project
   */
  public String getProjectName() {
    // TODO katis - do we need this method?
    return this.projectName;
  }
}
