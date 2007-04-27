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

/**
 * Interface to specify behavior of connections to filesystems
 * @author katis
 *
 */
public interface IConnection {
  
  /**
   * Method used to establish connection to remote or local host
   *
   */
  public void connect();
  
  /**
   * Method to disconnect form local or remote host
   *
   */
  public void disconnect();

  /**
   * Method that returns description of this connection
   * @return description of this connection
   * @see IConnectionDescription
   */
  public IConnectionDescription getDescription();
  
}
