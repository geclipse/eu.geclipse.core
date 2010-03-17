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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jsch.core.IJSchService;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.HostKey;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import eu.geclipse.efs.sftp.Activator;
import eu.geclipse.efs.sftp.IUserInfoProvider;

/**
 * Manager for SFTP connections
 */
public class ConnectionManager {

  private static ConnectionManager instance = null;
  private final HashMap<ConnectionKey, ArrayList<Session>> sessions;
  private final HashMap<ConnectionKey, ArrayList<SFTPConnection>> connections;

  private ConnectionManager() {
    this.sessions = new HashMap<ConnectionKey, ArrayList<Session>>();
    this.connections = new HashMap<ConnectionKey, ArrayList<SFTPConnection>>();
  }

  /**
   * Returns the connection manager
   * 
   * @return connection manager
   */
  synchronized public static ConnectionManager getInstance() {
    if( instance == null ) {
      instance = new ConnectionManager();
    }
    return instance;
  }

  /**
   * Acquire a {@link SFTPConnection}
   * 
   * @param key
   * @return SFTPConnection
   * @throws CoreException
   */
  synchronized public SFTPConnection acquireConnection( final ConnectionKey key )
    throws CoreException
  {
    SFTPConnection result = null;
    ArrayList<Session> sessionsList = this.sessions.get( key );
    ArrayList<SFTPConnection> connectionsList = this.connections.get( key );
    if( sessionsList == null ) {
      sessionsList = new ArrayList<Session>();
      this.sessions.put( key, sessionsList );
    }
    if( connectionsList == null ) {
      connectionsList = new ArrayList<SFTPConnection>();
      this.connections.put( key, connectionsList );
    }
    for( SFTPConnection candidate : connectionsList ) {
      if( candidate != null && candidate.tryLock() == true ) {
        if( candidate.getChannel() == null
            || !candidate.getChannel().isConnected()
            || candidate.getChannel().isClosed() )
        {
          connectionsList.remove( candidate );
        } else {
          return candidate;
        }
      }
    }
    Session session = null;
    for( Session candidate : sessionsList ) {
      if( candidate.isConnected() ) {
        session = candidate;
      } else {
        sessionsList.remove( candidate );
      }
    }
    // create session
    try {
      if( session == null ) {
        IJSchService service = Activator.getDefault().getJSchService();
        session = service.createSession( key.getHost(),
                                         key.getPort(),
                                         key.getUsername() );
        if( key.getPassword() != null ) {
          session.setPassword( key.getPassword() );
        }
        Properties config = new Properties();
        config.setProperty( "StrictHostKeyChecking", "ask" ); //$NON-NLS-1$ //$NON-NLS-2$
        session.setConfig( config );
        session.setUserInfo( getUserInfo( key ) );
        session.connect();
        sessionsList.add( session );
      }
      // create channel
      ChannelSftp channel = ( ChannelSftp )session.openChannel( "sftp" ); //$NON-NLS-1$
      channel.connect();
      result = new SFTPConnection( channel );
      result.lock();
    } catch( JSchException jschException ) {
      throw new CoreException( new Status( IStatus.ERROR,
                                           Activator.PLUGIN_ID,
                                           jschException.getLocalizedMessage(),
                                           jschException ) );
    } catch( NullPointerException nullPointerException ) {
      throw new CoreException( new Status( IStatus.ERROR,
                                           Activator.PLUGIN_ID,
                                           nullPointerException.getLocalizedMessage(),
                                           nullPointerException ) );
    }
    connectionsList.add( result );
    return result;
  }

  private UserInfo getUserInfo( final ConnectionKey key ) {
    UserInfo userInfo = null;
    IConfigurationElement[] configurationElements = Platform.getExtensionRegistry()
      .getConfigurationElementsFor( "eu.geclipse.efs.sftp.UserInfo" ); //$NON-NLS-1$
    if( configurationElements.length > 0 ) {
      IUserInfoProvider provider;
      try {
        provider = ( IUserInfoProvider )configurationElements[ 0 ].createExecutableExtension( "class" ); //$NON-NLS-1$
        userInfo = provider.getUserInfo( key.getUsername(),
                                         key.getHost(),
                                         key.getPassword(),
                                         null,
                                         key.getPort() );
      } catch( CoreException e ) {
        e.printStackTrace();
      }
    }
    if( userInfo == null ) {
      userInfo = new SSHUserInfo( key.getUsername(),
                                  key.getHost(),
                                  key.getPassword(),
                                  null,
                                  key.getPort() );
    }
    return userInfo;
  }
}
