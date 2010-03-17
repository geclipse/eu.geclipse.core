/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
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
 *    Christof Klausecker GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.efs.sftp.internal;

import java.net.URI;

/**
 * SSH Connection Key
 */
public class ConnectionKey {

  private String hostname;
  private String username;
  private String password;
  private int port = 22;

  /**
   * Creates a new ssh connection key
   * 
   * @param uri
   */
  public ConnectionKey( final URI uri ) {
    String userInfo = uri.getUserInfo();
    if( userInfo != null ) {
      int seperator = userInfo.indexOf( ':' );
      if( seperator != -1 ) {
        this.username = userInfo.substring( 0, seperator );
        this.password = userInfo.substring( seperator + 1 );
      } else {
        this.username = userInfo;
        this.password = null;
      }
    }
    this.hostname = uri.getHost();
    if( uri.getPort() != -1 ) {
      this.port = uri.getPort();
    }
  }

  /**
   * Returns the user name
   * 
   * @return user name
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Returns the password
   * 
   * @return password
   */
  @SuppressWarnings("unchecked")
  public String getPassword() {
    return this.password;
  }

  /**
   * Returns the Host
   * 
   * @return host
   */
  public String getHost() {
    return this.hostname;
  }

  /**
   * Returns the port
   * 
   * @return port
   */
  public int getPort() {
    return this.port;
  }
}
