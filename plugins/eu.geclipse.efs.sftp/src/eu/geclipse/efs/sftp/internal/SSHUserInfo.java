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

import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

/**
 * SSH UserInfo
 */
public class SSHUserInfo implements UserInfo, UIKeyboardInteractive {

  private String username;
  private String hostname;
  private String password;
  private String passphrase;
  private int port;
  //private boolean promptPassword;
  //private boolean promptPhrase;

  /**
   * Creates a new SSH UserInfo
   * 
   * @param username
   * @param hostname
   * @param password
   * @param passphrase
   * @param portNumber
   */
  public SSHUserInfo( final String username,
                      final String hostname,
                      final String password,
                      final String passphrase,
                      final int portNumber )
  {
    this.username = username;
    this.hostname = hostname;
    this.password = password;
    this.passphrase = passphrase;
    this.port = portNumber;
    //this.promptPassword = false;
    //this.promptPhrase = false;
  }

  String getUsername() {
    return this.username;
  }

  String getHostname() {
    return this.hostname;
  }

  int getPort() {
    return this.port;
  }
  
  public String getPassword() {
    return this.password;
  }

  public String getPassphrase() {
    return this.passphrase;
  }
  
  public boolean promptYesNo( final String str ) {
    return false;
  }

  public boolean promptPassword( final String message ) {
    return false;
  }

  public boolean promptPassphrase( final String message ) {
    return false;
  }

  public void showMessage( final String message ) {
    // empty
  }

  public String[] promptKeyboardInteractive( final String arg0,
                                             final String arg1,
                                             final String arg2,
                                             final String[] arg3,
                                             final boolean[] arg4 )
  {
    return new String[]{
      this.password
    };
  }
}
