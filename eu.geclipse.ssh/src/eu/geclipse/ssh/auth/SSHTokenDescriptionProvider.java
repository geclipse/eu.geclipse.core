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

import org.eclipse.swt.widgets.Display;

import com.jcraft.jsch.UserInfo;

import eu.geclipse.core.auth.AuthenticationException;
import eu.geclipse.core.auth.AuthenticationTokenManager;
import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.auth.IAuthenticationTokenDescription;
import eu.geclipse.efs.sftp.IUserInfoProvider;
import eu.geclipse.ssh.Activator;

/**
 * SSHTokenDescriptionProvider
 */
public class SSHTokenDescriptionProvider implements IUserInfoProvider {

  UserInfo userInfo = null;

  public UserInfo getUserInfo( final String username,
                               final String hostname,
                               final String password,
                               final String passphrase,
                               final int portNumber )
  {
    for( IAuthenticationToken authenticationToken : AuthenticationTokenManager.getManager()
      .getTokens() )
    {
      if( authenticationToken instanceof SSHToken ) {
        SSHToken sshToken = ( SSHToken )authenticationToken;
        IAuthenticationTokenDescription description = sshToken.getDescription();
        if( description instanceof SSHTokenDescription ) {
          SSHTokenDescription sshTokenDescription = ( SSHTokenDescription )description;
          if( sshTokenDescription.getUsername().equals( username )
              && sshTokenDescription.getHostname().equals( hostname )
              && sshTokenDescription.getPort() == portNumber )
          {
            this.userInfo = new SSHTokenDescription( username,
                                                     hostname,
                                                     sshTokenDescription.getPassword(),
                                                     sshTokenDescription.getPassphrase(),
                                                     portNumber );
            break;
          }
        }
      }
    }
    if( this.userInfo == null ) {
      Display.getDefault().syncExec( new Runnable() {

        public void run() {
          AuthenticationTokenManager manager = AuthenticationTokenManager.getManager();
          final SSHTokenDescription description = new SSHTokenDescription( username,
                                                                           hostname,
                                                                           password,
                                                                           passphrase,
                                                                           portNumber );
          try {
            manager.createToken( description );
            SSHTokenDescriptionProvider.this.userInfo = description;
          } catch( AuthenticationException authenticationException ) {
            Activator.logException( authenticationException );
          }
        }
      } );
    }
    return this.userInfo;
  }
}
