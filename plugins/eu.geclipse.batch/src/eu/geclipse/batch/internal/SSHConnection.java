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
import eu.geclipse.core.CoreProblems;
import eu.geclipse.core.GridException;
import eu.geclipse.core.IProblem;
import eu.geclipse.core.SolutionRegistry;
import eu.geclipse.core.portforward.ForwardType;
import eu.geclipse.core.portforward.IForward;
import eu.geclipse.batch.BatchException;
import eu.geclipse.batch.BatchProblems;
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
   * @throws GridException If the ssh connection cannot be established
   */
  public void createSession( final ISSHConnectionInfo sshConnectionInfo,
                             final List<IForward> forwards ) throws GridException {
    try {
      IJSchService service = Activator.getDefault().getJSchService();
      if ( service == null )
        throw new GridException( CoreProblems.GET_SSH_SERVICE_FAILED );

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
        GridException gridException = new GridException( CoreProblems.LOGIN_FAILED, exception );
        IProblem problem = gridException.getProblem();
        problem.addSolution( SolutionRegistry.CHECK_USERNAME_AND_PASSWORD );
        problem.addSolution( SolutionRegistry.CHECK_SSH_SERVER_CONFIG );
        
        throw gridException;
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
   * @throws BatchException If the command is not successfully executed.
   */
  public String execCommand( final String command ) throws BatchException {
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
      BatchException batchException = new BatchException( BatchProblems.CONNECTION_FAILED );
      IProblem problem = batchException.getProblem();
      problem.addSolution( SolutionRegistry.SERVER_DOWN );
      
      throw batchException;
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
      BatchException batchException = new BatchException( BatchProblems.CONNECTION_FAILED );
      IProblem problem = batchException.getProblem();
      problem.addSolution( SolutionRegistry.CHECK_USERNAME_AND_PASSWORD );
      problem.addSolution( SolutionRegistry.SERVER_DOWN );
      
      throw batchException;
    } catch( IOException ioExc ) {
      BatchException batchException = new BatchException( BatchProblems.CONNECTION_IO_ERROR );
      IProblem problem = batchException.getProblem();
      problem.addSolution( SolutionRegistry.SERVER_DOWN );
      
      throw batchException;
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
        BatchException batchException = new BatchException( BatchProblems.COMMAND_FAILED, errResult );
        throw batchException;
      }

      throw new BatchException( BatchProblems.COMMAND_FAILED );  
    }
    
    //  If no output 
    if ( 0 == result.length() )
      result = null;
    
    return result;
  }
}
