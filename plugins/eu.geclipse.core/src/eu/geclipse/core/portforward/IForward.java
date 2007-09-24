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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.portforward;

/**
 * Interface for retrieving information about a port forward.
 */
public interface IForward {
  /**
   * Type of the port forward (either LOCAL or REMOTE).
   * @return type of the port forward.
   */
  public abstract ForwardType getType();

  /**
   * Returns the port number of the listening server socket. (Bound on
   * the local host in case of a LOCAL port forward, bound on the remote host
   * in case of a REMOTE port forward. 
   * @return the port number ob the listening socket.
   */
  public abstract int getBindPort();

  /**
   * Returns the name of the host where the connections get redirected to.
   * @return name of the host to forward connections to.
   */
  public abstract String getHostname();

  /**
   * Port to connect on on the host where the redirections go to.
   * @return port on the host to connect to.
   */
  public abstract int getPort();
}
