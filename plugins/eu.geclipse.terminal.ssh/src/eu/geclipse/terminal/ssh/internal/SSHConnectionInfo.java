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

package eu.geclipse.terminal.ssh.internal;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import com.jcraft.jsch.UserInfo;
import eu.geclipse.ui.dialogs.PasswordDialog;

class SSHConnectionInfo implements UserInfo {
  private String user;
  private String host;
  private String passwd;
  private String passphrase;
  private int port;
  private boolean canceledPWValue;

  SSHConnectionInfo( final String username, final String hostname,
                     final String password, final String passphrase,
                     final int portNumber ) {
    this.user = username;
    this.host = hostname;
    this.passwd = password;
    this.passphrase = passphrase;
    this.port = portNumber;
    this.canceledPWValue = false;
  }
  
  public String getPassword() {
    return this.passwd;
  }
  
  String getUsername() {
    return this.user;
  }
  
  String getHostname() {
    return this.host;
  }
  
  int getPort() {
    return this.port;
  }

  public boolean promptYesNo( final String str ) {
    return MessageDialog.openQuestion( null,
                                       Messages.getString( "SshShell.sshTerminal" ), //$NON-NLS-1$
                                       str );
  }

  public String getPassphrase() {
    return this.passphrase;
  }

  public boolean promptPassphrase( final String message ){
    PasswordDialog dlg = new PasswordDialog( Display.getCurrent().getActiveShell(),
                                             Messages.getString( "SshShell.sshTerminal" ), //$NON-NLS-1$
                                             message, null, null);
    int result = dlg.open();
    if ( result == Window.OK )
      this.passphrase = dlg.getValue();
    else
      this.canceledPWValue = true;
    return result == Window.OK;
  }

  public boolean promptPassword( final String message ) {
    PasswordDialog dlg = new PasswordDialog( Display.getCurrent().getActiveShell(),
                                             Messages.getString( "SshShell.sshTerminal" ), //$NON-NLS-1$
                                             message, null, null);
    int result = dlg.open();
    if ( result == Window.OK )
      this.passwd = dlg.getValue();
    else
      this.canceledPWValue = true;
    return result == Window.OK;
  }

  public void showMessage( final String message ) {
    MessageDialog.openInformation( null,
                                   Messages.getString( "SshShell.sshTerminal" ), //$NON-NLS-1$
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
