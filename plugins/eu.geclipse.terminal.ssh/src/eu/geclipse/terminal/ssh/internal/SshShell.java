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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jsch.core.IJSchService;
import org.eclipse.swt.widgets.Display;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import eu.geclipse.core.IBidirectionalConnection;
import eu.geclipse.core.portforward.ForwardType;
import eu.geclipse.core.portforward.IForward;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.terminal.ITerminalListener;
import eu.geclipse.terminal.ITerminalPage;
import eu.geclipse.terminal.ITerminalView;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.widgets.IDropDownEntry;

/**
 * A terminal factory which allows to open SSH connected terminals.
 */
public class SshShell implements IDropDownEntry<ITerminalView>, ITerminalListener {
  ChannelShell channel;
  ITerminalPage terminal;
  SSHConnectionInfo userInfo;
  private int preConnectCols = -1;
  private int preConnectLines;
  private int preConnectXPix;
  private int preConnectYPix;

  public void windowSizeChanged( final int cols, final int lines, final int xPixels, final int yPixels ) {
    if ( this.channel.isConnected() ) {
      this.channel.setPtySize( cols, lines, xPixels, yPixels );
    } else {
      this.preConnectCols = cols;
      this.preConnectLines = lines;
      this.preConnectXPix = xPixels;
      this.preConnectYPix = yPixels;
    }
  }

  public void action( final ITerminalView terminalView ) {
    SSHWizard sshWizard = new SSHWizard();
    sshWizard.init( terminalView );
    WizardDialog wizardDialog = new WizardDialog( Display.getCurrent().getActiveShell(),
                                                  sshWizard );
    wizardDialog.open();
  }

  /**
   * Creates a new SSH terminal session using the specified ssh connection 
   * information in the specified terminal view.
   * @param terminalView the terminal view to add the ssh terminal session to.
   * @param sshConnectionInfo the connection information describing the ssh
   *                          session.
   * @param forwards list of port forwards to create.
   */
  public void createTerminal( final ITerminalView terminalView,
                              final SSHConnectionInfo sshConnectionInfo,
                              final List<IForward> forwards ) {
    try {
      IJSchService service = Activator.getDefault().getJSchService();
      if (service == null) {
        Display.getDefault().asyncExec( new Runnable() {
          public void run() {
            ProblemException problemException = new ProblemException( "eu.geclipse.terminal.ssh.problem.no_ssh_service", Activator.PLUGIN_ID ); //$NON-NLS-1$
            ProblemDialog.openProblem( null,
                                       Messages.getString( "SshShell.sshTerminal" ), //$NON-NLS-1$
                                       Messages.getString( "SshShell.couldNotGetService" ), //$NON-NLS-1$
                                       problemException );
          }
        } );
      } else {
        this.userInfo = sshConnectionInfo;
        final Session session = service.createSession( this.userInfo.getHostname(),
                                                       this.userInfo.getPort(),
                                                       this.userInfo.getUsername() );
        session.setUserInfo( this.userInfo );
        if ( forwards != null ) {
          for ( IForward forward : forwards ) {
            if (forward.getType() == ForwardType.LOCAL ) {
              session.setPortForwardingL( forward.getBindPort(),
                                          forward.getHostname(),
                                          forward.getPort() );
            } else {
              session.setPortForwardingR( forward.getBindPort(),
                                          forward.getHostname(),
                                          forward.getPort() );
            }
          }
        }
        session.connect();
        final IBidirectionalConnection connection = new IBidirectionalConnection() {
          public void close() {
            SshShell.this.channel.disconnect();
            session.disconnect();
          }
          public InputStream getInputStream() throws IOException {
            return SshShell.this.channel.getInputStream();
          }
          public OutputStream getOutputStream() throws IOException {
            return SshShell.this.channel.getOutputStream();
          }
        };
  
        this.channel = (ChannelShell) session.openChannel( "shell" ); //$NON-NLS-1$
        Display.getDefault().syncExec( new Runnable() {
          public void run() {
            try {
              SshShell.this.terminal = terminalView.addTerminal( connection, SshShell.this );
              SshShell.this.terminal.setTabName( SshShell.this.userInfo.getHostname() );
              SshShell.this.terminal.setDescription( Messages.formatMessage( "SshShell.descriptionWithoutWinTitle", //$NON-NLS-1$
                                                     SshShell.this.userInfo.getUsername(),
                                                     SshShell.this.userInfo.getHostname() ) );
            } catch( final IOException exception ) {
              Activator.logException( exception );
            }
          }
        } );
        this.channel.connect();
        if ( this.preConnectCols != -1 ) {
          windowSizeChanged( this.preConnectCols, this.preConnectLines,
                             this.preConnectXPix, this.preConnectYPix );
        }
      }
    } catch ( final JSchException exception ) {
      if ( !this.userInfo.getCanceledPWValue() ) {
        Display.getDefault().asyncExec( new Runnable() {
          public void run() {
            ProblemException problemException = new ProblemException( "eu.geclipse.terminal.ssh.problem.auth_failed", exception, Activator.PLUGIN_ID );
            ProblemDialog.openProblem( null,
                                       Messages.getString( "SshShell.sshTerminal" ), //$NON-NLS-1$
                                       Messages.getString( "SshShell.authFailed" ), //$NON-NLS-1$
                                       problemException );
          }
        } );
      }
    } catch( Exception exception ){
      Activator.logException( exception );
    }
  }

  public void windowTitleChanged( final String windowTitle ) {
    this.terminal.setDescription( Messages.formatMessage( "SshShell.descriptionWithWinTitle", //$NON-NLS-1$
                                  this.userInfo.getUsername(),
                                  this.userInfo.getHostname(),
                                  windowTitle ) );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.terminal.ITerminalListener#terminated()
   */
  public void terminated() {
    // not needed
  }
}
