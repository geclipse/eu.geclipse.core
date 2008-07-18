/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
package eu.geclipse.batch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.eclipse.jsch.core.IJSchService;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.ChannelDirectTCPIP;

import eu.geclipse.batch.internal.Activator;
import eu.geclipse.core.IBidirectionalConnection;
import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.ICoreSolutions;
import eu.geclipse.core.portforward.ForwardType;
import eu.geclipse.core.portforward.IForward;
import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ISolution;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.reporting.ReportingPlugin;


/**
 * Class used to establish a persistant ssh connection to a remote server.
 */
public class SSHPersistantConnection implements IBidirectionalConnection {
  private Session session = null;
  private ISSHConnectionInfo userInfo;
//  private ChannelDirectTCPIP channel;
  private ChannelShell channel;

  /**
   * Creates a new SSH session using the specified ssh connection
   * information.
   * @param sshConnectionInfo the connection information describing the ssh
   *                          session.
   * @param forwards list of port forwards to create.
   * @throws ProblemException If the ssh connection cannot be established
   */
  public void createSession( final ISSHConnectionInfo sshConnectionInfo,
                             final List<IForward> forwards ) throws ProblemException {
    try {
      IJSchService service = Activator.getDefault().getJSchService();
      if ( service == null ) {
        IProblem problem;
        problem = ReportingPlugin.getReportingService()
                    .getProblem( IBatchProblems.GET_SSH_SERVICE_FAILED,
                                 null,
                                 null,
                                 Activator.PLUGIN_ID );
        throw new ProblemException( problem );      
      }
      this.userInfo = sshConnectionInfo;
      this.session = service.createSession( this.userInfo.getHostname(),
                                              this.userInfo.getPort(),
                                              this.userInfo.getUsername() );
      this.session.setUserInfo( this.userInfo );
  
      if ( null != forwards ) {
        for ( IForward forward : forwards ) {
          if ( ForwardType.LOCAL == forward.getType() ) {
            this.session.setPortForwardingL( forward.getBindPort(),
                                             forward.getHostname(),
                                             forward.getPort() );
          } else {
            this.session.setPortForwardingR( forward.getBindPort(),
                                             forward.getHostname(),
                                             forward.getPort() );
          }
        }
      }
    
      this.session.connect();
    } catch ( JSchException exception ) {
      // The user wanted to cancel, so ignore the exception
      if ( !sshConnectionInfo.getCanceledPWValue() ) {
        IProblem problem;
        problem = ReportingPlugin.getReportingService()
                    .getProblem( IBatchProblems.LOGIN_FAILED,
                                 null,
                                 exception,
                                 Activator.PLUGIN_ID );
        ISolution solution;
        solution = ReportingPlugin.getReportingService()
                                     .getSolution( IBatchSolutions.CHECK_USERNAME_AND_PASSWORD, null );
        problem.addSolution( solution );
         
        solution = ReportingPlugin.getReportingService().getSolution( IBatchSolutions.CHECK_SSH_SERVER_CONFIG, null );
        problem.addSolution( solution );

        throw new ProblemException( problem );      
      }
    } catch( Exception exception ){
      Activator.logException( exception );
    }
  }

  /**
   * Test if the session to the server is established.
   *
   * @return Returns <code>true</code> if the session is established,
   * <code>false</code> otherwise.
   */
  public boolean isSessionActive() {
    boolean status = false;

    if ( null != this.session ) {
      status = this.session.isConnected();
    }

   return status;
  }

  /**
   * Tears down the established SSH session .
   */
  public void close() {
    if ( null != this.session )
      this.session.disconnect();
  }

  
  
  public InputStream getInputStream() throws IOException {
    InputStream ret = null;
    
    if ( null != this.channel ) 
      ret = this.channel.getInputStream();

    return ret;
  }

  public OutputStream getOutputStream() throws IOException {
    OutputStream ret = null;
    
    if ( null != this.channel ) 
      ret = this.channel.getOutputStream();

    return ret;
  }
  
  /**
   * Creates a SSH tunnel to the remote host.
   *    
   * @param inStream The input stream for the tunnel
   * @param outStream The output stream for the tunnel
   * @throws ProblemException If the tunnel could not successfully be established 
   */
  public void connect( final InputStream inStream, final OutputStream outStream ) throws ProblemException {
    
    // We throw exception if we don't have a session 
    if ( ! this.isSessionActive() ) {
      IProblem problem;
      problem = ReportingPlugin.getReportingService()
                  .getProblem( ICoreProblems.NET_CONNECTION_FAILED,
                               null,
                               null,
                               Activator.PLUGIN_ID );
      ISolution solution;
      solution = ReportingPlugin.getReportingService()
                   .getSolution( ICoreSolutions.NET_CHECK_INTERNET_CONNECTION, null );
      problem.addSolution( solution );
      
      throw new ProblemException( problem );
    }
    
    try {
//      this.channel = ( ChannelDirectTCPIP )this.session.openChannel( "direct-tcpip" ); //$NON-NLS-1$
      this.channel = ( ChannelShell )this.session.openChannel( "shell" ); //$NON-NLS-1$
      this.channel.setInputStream( inStream );
      this.channel.setOutputStream( outStream );
      this.channel.connect();
    } catch( JSchException jschExc ) {
      IProblem problem;
      problem = ReportingPlugin.getReportingService()
                  .getProblem( ICoreProblems.NET_CONNECTION_FAILED,
                               null,
                               jschExc,
                               Activator.PLUGIN_ID );
      ISolution solution;
      solution = ReportingPlugin.getReportingService()
                          .getSolution( ICoreSolutions.NET_CHECK_INTERNET_CONNECTION, null );
      problem.addSolution( solution );

      solution = ReportingPlugin.getReportingService().getSolution( 
                                                       IBatchSolutions.CHECK_USERNAME_AND_PASSWORD, null );
      problem.addSolution( solution );
      
      solution = ReportingPlugin.getReportingService()
                     .getSolution( ICoreSolutions.NET_CHECK_FIREWALL, null );
      problem.addSolution( solution );

      throw new ProblemException( problem );
    }    
  }
  
  
}
