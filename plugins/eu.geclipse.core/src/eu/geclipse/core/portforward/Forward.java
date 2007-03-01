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
 * Implementation of the {@link IForward} interface, provided for convenience.
 * Allows to set all values of a port forward.
 */
public class Forward implements IForward {
  protected int bindPort;
  protected String hostname;
  protected int port;
  protected ForwardType type;

  /**
   * Creates an empty forward. Values have to be set using the set-methods.
   */
  public Forward() {
    // empty constructor
  }
  
  /**
   * Creates a new forward. Allows to set all values at once.
   * 
   * @param type type of the forward (LOCAL or REMOTE).
   * @param bindPort port to bind on.
   * @param hostname name of the host to connect to.
   * @param port port on the host to connect to.
   */
  public Forward( final ForwardType type, final int bindPort,
                  final String hostname, final int port ) {
    this.type = type;
    this.bindPort = bindPort;
    this.hostname = hostname;
    this.port = port;
  }
  
  public int getBindPort() {
    return this.bindPort;
  }

  /**
   * Sets the port to listens for new connections.
   * @param bindPort port to bind on.
   */
  public void setBindPort( final int bindPort ) {
    this.bindPort = bindPort;
  }

  public String getHostname() {
    return this.hostname;
  }

  /**
   * Sets the port of the host to forward connections to.
   * 
   * @param hostname host to connect to.
   */
  public void setHostname( final String hostname ) {
    this.hostname = hostname;
  }

  public int getPort() {
    return this.port;
  }

  /**
   * Sets the port on the host to connect to.
   * 
   * @param port port to connect to.
   */
  public void setPort( final int port ) {
    this.port = port;
  }

  public ForwardType getType() {
    return this.type;
  }

  /**
   * Sets the type of forward (LOCAL or REMOTE).
   * 
   * @param type type of the forward.
   */
  public void setType( final ForwardType type ) {
    this.type = type;
  }
}
