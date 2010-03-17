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

package eu.geclipse.ssh.auth;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;

import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.auth.IAuthenticationTokenDescription;
import eu.geclipse.ssh.internal.Messages;
import eu.geclipse.ui.dialogs.PasswordDialog;

/**
 * SSHTokenDescription
 */
public class SSHTokenDescription
  implements UserInfo, UIKeyboardInteractive, IAuthenticationTokenDescription
{

  protected String username;
  protected String hostname;
  protected String password;
  protected String passphrase;
  protected int port;
  protected boolean promptPassword = false;
  protected boolean promptPassphrase = false;
  protected boolean cancelled = false;

  /**
   * Creates a new SSHTokenDescription
   * 
   * @param username
   * @param hostname
   * @param password
   * @param passphrase
   * @param portNumber
   */
  public SSHTokenDescription( final String username,
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
    if( this.password == null || this.password.length() == 0 ) {
      this.promptPassword = true;
      this.promptPassphrase = true;
    }
    if( this.passphrase != null && this.passphrase.length() != 0 ) {
      this.promptPassphrase = false;
    }
  }

  /**
   * Returns the username
   * 
   * @return username
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Returns the hostname
   * 
   * @return hostname
   */
  public String getHostname() {
    return this.hostname;
  }

  /**
   * Returns the port number
   * 
   * @return port number
   */
  public int getPort() {
    return this.port;
  }

  public String getPassword() {
    return this.password;
  }

  public String getPassphrase() {
    return this.passphrase;
  }

  public boolean promptPassphrase( final String message ) {
    final int[] result = {
      Window.OK
    };
    if( this.promptPassphrase ) {
      Display.getDefault().syncExec( new Runnable() {

        public void run() {
          PasswordDialog passwordDialog = new PasswordDialog( Display.getCurrent()
                                                                .getActiveShell(),
                                                              Messages.getString( "SSHTokenDescription.SSHAuthentication" ), //$NON-NLS-1$
                                                              message,
                                                              null,
                                                              null );
          result[ 0 ] = passwordDialog.open();
          if( result[ 0 ] == Window.OK ) {
            SSHTokenDescription.this.passphrase = passwordDialog.getValue();
          }
        }
      } );
    }
    return result[ 0 ] == Window.OK;
  }

  public boolean promptPassword( final String message ) {
    final int[] result = {
      Window.OK
    };
    if( this.promptPassword ) {
      Display.getDefault().syncExec( new Runnable() {

        public void run() {
          PasswordDialog passwordDialog = new PasswordDialog( Display.getCurrent()
                                                                .getActiveShell(),
                                                              Messages.getString( "SSHTokenDescription.SSHAuthentication" ), //$NON-NLS-1$
                                                              message,
                                                              null,
                                                              null );
          result[ 0 ] = passwordDialog.open();
          if( result[ 0 ] == Window.OK ) {
            SSHTokenDescription.this.password = passwordDialog.getValue();
          } else {
            SSHTokenDescription.this.cancelled = true;
          }
        }
      } );
    }
    return result[ 0 ] == Window.OK;
  }

  public boolean promptYesNo( final String message ) {
    final boolean[] result = {
      false
    };
    Display.getDefault().syncExec( new Runnable() {

      public void run() {
        result[ 0 ] = MessageDialog.openQuestion( null,
                                                  Messages.getString( "SSHTokenDescription.SSHAuthentication" ), message ); //$NON-NLS-1$
      }
    } );
    return result[ 0 ];
  }

  public String[] promptKeyboardInteractive( final String arg0,
                                             final String arg1,
                                             final String arg2,
                                             final String[] arg3,
                                             final boolean[] arg4 )
  {
    String[] result = null;
    if( this.promptPassword && !this.cancelled ) {
      Display.getDefault().syncExec( new Runnable() {

        public void run() {
          PasswordDialog passwordDialog = new PasswordDialog( Display.getCurrent()
                                                                .getActiveShell(),
                                                              Messages.getString( "SSHTokenDescription.SSHAuthentication" ), //$NON-NLS-1$
                                                              "Password for "
                                                                  + arg0,
                                                              null,
                                                              null );
          if( passwordDialog.open() == Window.OK ) {
            SSHTokenDescription.this.password = passwordDialog.getValue();
          } else {
            SSHTokenDescription.this.cancelled = true;
          }
        }
      } );
    }
    if( this.password != null && this.password.length() > 0 ) {
      result = new String[]{
        this.password
      };
    }
    if( this.cancelled ) {
      result = null;
    }
    return result;
  }

  public void showMessage( final String message ) {
    if( message != null && message.trim().length() != 0 ) {
      Display.getDefault().syncExec( new Runnable() {

        public void run() {
          MessageDialog.openInformation( null,
                                         Messages.getString( "SSHTokenDescription.SSHAuthentication" ), message ); //$NON-NLS-1$
        }
      } );
    }
  }

  public IAuthenticationToken createToken() {
    return new SSHToken( this );
  }

  public String getTokenTypeName() {
    return "SSH Token"; //$NON-NLS-1$
  }

  public String getWizardId() {
    return "eu.geclipse.ssh.auth.wizard"; //$NON-NLS-1$
  }

  public boolean matches( final IAuthenticationTokenDescription otherToken ) {
    boolean result = false;
    if( otherToken instanceof SSHTokenDescription ) {
      SSHTokenDescription sshTokenDescription = ( SSHTokenDescription )otherToken;
      if( this.username.equals( sshTokenDescription.username )
          && this.password.equals( sshTokenDescription.password )
          && this.passphrase.equals( sshTokenDescription.passphrase )
          && this.hostname.equals( sshTokenDescription.hostname )
          && this.port == sshTokenDescription.port )
      {
        result = true;
      }
    }
    return result;
  }
}
