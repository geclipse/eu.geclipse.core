/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.internal;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import eu.geclipse.ui.dialogs.PasswordDialog;
import eu.geclipse.batch.ISSHConnectionInfo;
import eu.geclipse.core.auth.PasswordManager;

/**
 * Class containing information needed to establish an ssh connection.
 */
public class SSHConnectionInfo implements ISSHConnectionInfo {
  private String user;
  private String host;
  private String passwd;
  private String passphrase;
  private int port;
  private boolean canceledPWValue;
  private int promptPasswd;
  /**
   * Create a new SSHConnectionInfo holder, from the arguments.
   *
   * @param username The username for the account on the remote host.
   * @param hostname The hostname of the remote host.
   * @param password The password for the account on the remote host.
   * @param passphrase The passphrase to access the private key vault.
   * @param portNumber The port of the remote host.
   */
  public SSHConnectionInfo( final String username, final String hostname,
                     final String password, final String passphrase,
                     final int portNumber ) {
    this.user = username;
    this.host = hostname;
    
    if ( null == password ) {
      this.passwd = PasswordManager.getPassword( username + '@' + hostname ); 
    } else if ( password.length() != 0 ){
      PasswordManager.registerPassword( username + '@' + hostname, password );
      this.passwd = password;
    }
    
    if ( this.passwd != null && this.passwd.length() != 0 )
      this.promptPasswd = 0;
    else
      this.promptPasswd = 1;
    
    this.passphrase = passphrase;
    this.port = portNumber;
    this.canceledPWValue = false;
  }

  /**
   * @return the password of the user account on the remote server.
   */
  public String getPassword() {
    ++this.promptPasswd;
    return this.passwd;
  }

  /**
   * @return the username of the account on the remote server.
   */
  public String getUsername() {
    return this.user;
  }

  /**
   * @return the hostname of the remote server.
   */
  public String getHostname() {
    return this.host;
  }

  /**
   * @return the port of the remote server.
   */
  public int getPort() {
    return this.port;
  }

  /**
   * @return the passphrase for the private key vault.
   */
  public String getPassphrase() {
    return this.passphrase;
  }

  /* (non-Javadoc)
   * @see com.jcraft.jsch.UserInfo#promptYesNo(java.lang.String)
   */
  public boolean promptYesNo( final String str ) {
    return MessageDialog.openQuestion( null,
                                       Messages.getString( "SSHConnectionInfo.PWDialog" ), //$NON-NLS-1$
                                       str );
  }

  /* (non-Javadoc)
   * @see com.jcraft.jsch.UserInfo#promptPassphrase(java.lang.String)
   */
  public boolean promptPassphrase( final String message ){
    PasswordDialog dlg = new PasswordDialog( Display.getCurrent().getActiveShell(),
                                             Messages.getString( "SSHConnectionInfo.PWDialog" ), //$NON-NLS-1$
                                             message, null, null);
    int result = dlg.open();
    if ( result == Window.OK )
      this.passphrase = dlg.getValue();
    else
      this.canceledPWValue = true;

    return result == Window.OK;
  }

  /* (non-Javadoc)
   * @see com.jcraft.jsch.UserInfo#promptPassword(java.lang.String)
   */
  public boolean promptPassword( final String message ) {    
    boolean ret = false;
    
    // We found the pw in the register
    if ( 0 == this.promptPasswd )
      ret = true;
    else {
      // Entered wrong pw multiple times
      if ( 1 < this.promptPasswd ) {
        --this.promptPasswd;
        MessageDialog.openError( null,
                                 Messages.getString( "SSHConnectionInfo.error_wrongPW_title" ), //$NON-NLS-1$
                                 Messages.getString( "SSHConnectionInfo.error_wrongPW_msg" ) ); //$NON-NLS-1$
      }
      
      PasswordDialog dlg = new PasswordDialog( Display.getCurrent().getActiveShell(),
                                             Messages.getString( "SSHConnectionInfo.PWDialog" ), //$NON-NLS-1$
                                             message, null, null);
      int result = dlg.open();
      if ( result == Window.OK ) {
        this.passwd = dlg.getValue();
        PasswordManager.registerPassword( this.user + '@' + this.host, this.passwd );
      } else
        this.canceledPWValue = true;

      ret = result == Window.OK;
    }
    
    return ret;
  }
  
  /* (non-Javadoc)
   * @see com.jcraft.jsch.UserInfo#showMessage(java.lang.String)
   */
  public void showMessage( final String message ) {
    MessageDialog.openInformation( null,
                                   Messages.getString( "SSHConnectionInfo.PWDialog" ), //$NON-NLS-1$
                                   message );
  }

  /**
   * Returns if the user pushed cancel when queried for password for the ssh session.
   * @return Returns <code>true</code> if the user pushed cancel when asked for the pw,
   * <code>true</code> otherwise.
   */
  public boolean getCanceledPWValue() {
    return this.canceledPWValue;
  }
}
