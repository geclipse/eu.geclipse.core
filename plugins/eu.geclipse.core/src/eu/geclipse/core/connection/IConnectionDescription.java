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
 * Interface to specify the behavior of class describing {@link IConnection}
 * 
 * @author katis
 */
public interface IConnectionDescription {

  /**
   * Method creating new connection from this description
   * 
   * @return The newly created connection.
   */
  public AbstractConnection createConnection();

  /**
   * Method returning the URI of resource to which {@link IConnection} created
   * from this description should be established
   * 
   * @return The URI of the target of the connection.
   */
  public URI getFileSystemURI();
}
