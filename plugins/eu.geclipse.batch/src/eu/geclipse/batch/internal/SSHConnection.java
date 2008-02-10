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
package eu.geclipse.batch.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.eclipse.jsch.core.IJSchService;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.ICoreSolutions;
import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ISolution;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.reporting.ReportingPlugin;

import eu.geclipse.core.portforward.ForwardType;
import eu.geclipse.core.portforward.IForward;
import eu.geclipse.batch.IBatchProblems;
import eu.geclipse.batch.IBatchSolutions;
import eu.geclipse.batch.ISSHConnectionInfo;

/**
 * Class used to establish an ssh connection to a remote server.
 */
public class SSHConnection {
  private Session session;
  private ISSHConnectionInfo userInfo;

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
   * Tears down the established SSH session .
   */
  public void endSession( ) {
    if ( null != this.session )
      this.session.disconnect();
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
   * Execute a command on the remote host and returns the output.
   * @param command The command to be executed on the remote host.
   * @return Returns the output of the executed command if executed 
   * successfully, if no output of the successfully executed command 
   * <code>null</code> is returned.
   * @throws ProblemException If the command is not successfully executed.
   */
  public String execCommand( final String command ) throws ProblemException {
    String line;
    Channel channel = null;
    InputStream stdout = null;
    InputStream stderr = null;
    String result = new String();
    String errResult = new String();
    int exitStatus = -1;
    
    BufferedReader stdoutReader = null;
    BufferedReader stderrReader = null;
    
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
      channel = this.session.openChannel( "exec" );  //$NON-NLS-1$

      ( ( ChannelExec )channel ).setCommand( command );

      channel.setInputStream( null );
      stdout = channel.getInputStream();
      stderr = channel.getExtInputStream();

      channel.connect();

      stdoutReader = new BufferedReader( new InputStreamReader( stdout ) );
      stderrReader = new BufferedReader( new InputStreamReader( stderr ) );

      // Make sure that the command is executed before we exit
      while ( !channel.isClosed() ) {
        line = stdoutReader.readLine();
        while ( null != line ) {
          result = result + line + '\n';
          line = stdoutReader.readLine();
        }

        line = stderrReader.readLine();
        while ( null != line ) {
          errResult = errResult + line + '\n'; 
          line = stderrReader.readLine();
        }
         
        try { Thread.sleep( 1000 ); }catch( Exception ee ) {
          // Nothing to do here
        }
      }        

      // Read what is left after the channel is closed
      line = stdoutReader.readLine();
      while ( null != line ) {
        result = result + line + '\n'; 
        line = stdoutReader.readLine();
      }

      line = stderrReader.readLine();
      while ( null != line ) {
        errResult = errResult + line + '\n'; 
        line = stderrReader.readLine();
      }
        
      exitStatus = channel.getExitStatus();

      channel.disconnect();
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

      solution = ReportingPlugin.getReportingService().getSolution( IBatchSolutions.CHECK_USERNAME_AND_PASSWORD, null );
      problem.addSolution( solution );
      
      solution = ReportingPlugin.getReportingService()
                     .getSolution( ICoreSolutions.NET_CHECK_FIREWALL, null );
      problem.addSolution( solution );

      throw new ProblemException( problem );
    } catch ( IOException ioExc ) {
      IProblem problem;
      problem = ReportingPlugin.getReportingService()
                  .getProblem( IBatchProblems.CONNECTION_IO_ERROR,
                               null,
                               ioExc,
                               Activator.PLUGIN_ID );
      ISolution solution = ReportingPlugin.getReportingService()
                             .getSolution( ICoreSolutions.NET_CHECK_INTERNET_CONNECTION, null );
      problem.addSolution( solution );

      throw new ProblemException( problem );
    } finally {
      if ( null != stdoutReader ) {
        try {
          stdoutReader.close();
        } catch( IOException e ) {
          // Ignore this exception
        }
      }
      
      if ( null != stderrReader ) {
        try {
          stderrReader.close();
        } catch( IOException e ) {
          // Ignore this exception
        }
      }
    }

    // Was the command executed successfully 
    if ( ( 0 != exitStatus || 0 < errResult.length() ) && 0 == result.length() ) {
      if ( 0 < errResult.length() ) {
        IProblem problem;
        problem = ReportingPlugin.getReportingService()
                    .getProblem( IBatchProblems.COMMAND_FAILED,
                                 errResult,
                                 null,
                                 Activator.PLUGIN_ID );

        throw new ProblemException( problem );
      }

      IProblem problem;
      problem = ReportingPlugin.getReportingService()
                  .getProblem( IBatchProblems.COMMAND_FAILED,
                               null,
                               null,
                               Activator.PLUGIN_ID );

      throw new ProblemException( problem );
    }
    
    //  If no output 
    if ( 0 == result.length() )
      result = null;
    
    return result;
  }
}
