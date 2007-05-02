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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jsch.internal.core.IConstants;
import org.eclipse.jsch.internal.core.JSchCorePlugin;
import org.eclipse.swt.widgets.Display;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import eu.geclipse.core.IBidirectionalConnection;
import eu.geclipse.core.portforward.ForwardType;
import eu.geclipse.core.portforward.IForward;
import eu.geclipse.terminal.ITerminalListener;
import eu.geclipse.terminal.ITerminalPage;
import eu.geclipse.terminal.ITerminalView;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.dialogs.Solution;
import eu.geclipse.ui.widgets.IDropDownEntry;

/**
 * A terminal factory which allows to open SSH connected terminals.
 */
public class SshShell implements IDropDownEntry<ITerminalView>, ITerminalListener {
  private static final String SSH_HOME_DEFAULT = null;
  ChannelShell channel;
  private ITerminalPage terminal;
  private SSHConnectionInfo userInfo;
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

  private void loadPrivateKeys( final JSch jsch ) {
    Preferences preferences = JSchCorePlugin.getPlugin().getPluginPreferences();
    String privateKeys = preferences.getString( IConstants.KEY_PRIVATEKEY );
    String sshHome = preferences.getString( IConstants.KEY_SSH2HOME );
    String[] keyFilenames = privateKeys.split( "," ); //$NON-NLS-1$
    for( String keyFilename : keyFilenames ) {
      File file = new File( keyFilename );
      if ( !file.isAbsolute() ) {
        file = new File( sshHome, keyFilename );
      }
      if ( file.exists() ) {
        try {
          jsch.addIdentity( file.getPath() );
        } catch( JSchException exception ) {
          Activator.logException( exception );
        }
      }
    }
  }

  private void loadKnownHosts( final JSch jsch ) {                                                                                                                                                                                                             
    Preferences preferences = JSchCorePlugin.getPlugin().getPluginPreferences();
    String sshHome = preferences.getString( IConstants.KEY_SSH2HOME );

    if ( sshHome.length() == 0 ) {
      sshHome = SSH_HOME_DEFAULT;
    }

    try {
      File file;
      file=new File( sshHome, "known_hosts" ); //$NON-NLS-1$
      jsch.setKnownHosts( file.getPath() );
    } catch ( Exception exception ) {
      Activator.logException( exception );
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
   * inforamtion in the specified terminal view.
   * @param terminalView the terminal view to add the ssh terminal session to.
   * @param sshConnectionInfo the connection information describing the ssh
   *                          session.
   * @param forwards list of port forwards to create.
   */
  public void createTerminal( final ITerminalView terminalView,
                              final SSHConnectionInfo sshConnectionInfo,
                              final List<IForward> forwards ) {
    try {
      this.userInfo = sshConnectionInfo;
      JSch jsch = new JSch();
      loadKnownHosts( jsch );
      loadPrivateKeys( jsch );
      final Session session = jsch.getSession( this.userInfo.getUsername(),
                                               this.userInfo.getHostname(),
                                               this.userInfo.getPort() );
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
      IBidirectionalConnection connection = new IBidirectionalConnection() {
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
      this.terminal = terminalView.addTerminal( connection, this );
      this.terminal.setTabName( this.userInfo.getHostname() );
      this.terminal.setDescription( Messages.formatMessage( "SshShell.descriptionWithoutWinTitle", //$NON-NLS-1$
                                                            this.userInfo.getUsername(),
                                                            this.userInfo.getHostname() ) );
      this.channel.connect();
      if ( this.preConnectCols != -1 ) {
        windowSizeChanged( this.preConnectCols, this.preConnectLines,
                           this.preConnectXPix, this.preConnectYPix );
      }
    } catch ( JSchException exception ) {
      String message = exception.getLocalizedMessage();
      if ( message == null ) message = exception.getClass().getName();
      IStatus status = new Status( IStatus.ERROR, Activator.PLUGIN_ID,
                                   IStatus.OK, message, exception );
      Solution[] solutions = { // TODO implement solve methods
        new Solution( Messages.getString("SshShell.checkSSHConfig") ), //$NON-NLS-1$
        new Solution( Messages.getString("SshShell.checkUsernameAndPasswd") ) //$NON-NLS-1$
      };
      ProblemDialog.openProblem( null,
                                 Messages.getString( "SshShell.sshTerminal" ), //$NON-NLS-1$
                                 Messages.getString( "SshShell.authFailed" ), //$NON-NLS-1$
                                 status,
                                 solutions );
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
}
