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
  String passphrase;
  String passwd;
  boolean canceledPWValue;
  private String user;
  private String host;
  private int port;
  private boolean promptPasswd;
  private boolean promptPassphrase;

  SSHConnectionInfo( final String username, final String hostname,
                     final String password, final String passphrase,
                     final int portNumber ) {
    this.user = username;
    this.host = hostname;
    this.passwd = password;
    this.promptPasswd = !( this.passwd != null && this.passwd.length() != 0 );
    this.passphrase = passphrase;
    this.promptPassphrase = !( this.passphrase != null && this.passphrase.length() != 0 );
    this.port = portNumber;
    this.canceledPWValue = false;
  }
  
  public String getPassword() {
    this.promptPasswd = true;
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
    final boolean[] result = { false };
    Display.getDefault().syncExec( new Runnable() {
      public void run() {
        result[0] = MessageDialog.openQuestion( null,
                                                Messages.getString( "SshShell.sshTerminal" ), //$NON-NLS-1$
                                                str );
      }
    } );
    return result[0];
  }

  public String getPassphrase() {
    this.promptPassphrase = true;
    return this.passphrase;
  }

  public boolean promptPassphrase( final String message ) {
    final int[] result = { Window.OK };

    if ( this.promptPassphrase ) {
      Display.getDefault().syncExec( new Runnable() {
        public void run() {
          PasswordDialog dlg = new PasswordDialog( Display.getCurrent().getActiveShell(),
                                                   Messages.getString( "SshShell.sshTerminal" ), //$NON-NLS-1$
                                                   message, null, null);
          result[0] = dlg.open();
          if ( result[0] == Window.OK )
            SSHConnectionInfo.this.passphrase = dlg.getValue();
          else
            SSHConnectionInfo.this.canceledPWValue = true;
        }
      } );
    }
    return result[0] == Window.OK;
  }

  public boolean promptPassword( final String message ) {
    final int[] result = { Window.OK };
    
    if ( this.promptPasswd ) {
      Display.getDefault().syncExec( new Runnable() {
        public void run() {
          PasswordDialog dlg = new PasswordDialog( Display.getCurrent().getActiveShell(),
                                                   Messages.getString( "SshShell.sshTerminal" ), //$NON-NLS-1$
                                                   message, null, null);
          result[0] = dlg.open();
          if ( result[0] == Window.OK )
            SSHConnectionInfo.this.passwd = dlg.getValue();
          else
            SSHConnectionInfo.this.canceledPWValue = true;
        }
      } );
    }
    return result[0] == Window.OK;
  }

  public void showMessage( final String message ) {
    if (message != null && message.trim().length() != 0) {
      Display.getDefault().syncExec( new Runnable() {
        public void run() {
          MessageDialog.openInformation( null,
                                         Messages.getString( "SshShell.sshTerminal" ), //$NON-NLS-1$
                                         message );
        }
      } );
    }
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
