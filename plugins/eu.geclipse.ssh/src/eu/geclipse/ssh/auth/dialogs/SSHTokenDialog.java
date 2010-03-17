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

package eu.geclipse.ssh.auth.dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.ssh.auth.SSHToken;
import eu.geclipse.ssh.auth.SSHTokenDescription;
import eu.geclipse.ssh.internal.Messages;
import eu.geclipse.ui.dialogs.AuthTokenInfoDialog;

/**
 * SSH Token Dialog
 */
public class SSHTokenDialog extends AuthTokenInfoDialog {

  Text usernameText;
  Text hostnameText;
  Text passwordText;
  Text passphraseText;

  /**
   * Creates a new SSH Token Dialog
   * 
   * @param token
   * @param parentShell
   */
  public SSHTokenDialog( final IAuthenticationToken token,
                         final Shell parentShell )
  {
    super( token, parentShell );
  }

  @Override
  protected Control createInfoArea( final Composite parent ) {
    SSHToken token = ( SSHToken )getToken();
    SSHTokenDescription description = ( SSHTokenDescription )token.getDescription();
    Composite composite = new Composite( parent, SWT.NONE );
    GridLayout layout = new GridLayout();
    GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
    layout.numColumns = 2;
    composite.setLayout( layout );
    composite.setLayoutData( layoutData );
    // username
    Label usernameLabel = new Label( composite, SWT.NONE );
    usernameLabel.setText( Messages.getString( "SSHTokenDialog.username" ) ); //$NON-NLS-1$
    this.usernameText = new Text( composite, SWT.BORDER );
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    this.usernameText.setLayoutData( layoutData );
    this.usernameText.setText( description.getUsername() );
    // password
    Label passwordLabel = new Label( composite, SWT.NONE );
    passwordLabel.setText( Messages.getString( "SSHTokenDialog.password" ) ); //$NON-NLS-1$
    this.passwordText = new Text( composite, SWT.PASSWORD | SWT.BORDER );
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    this.passwordText.setLayoutData( layoutData );
    this.passwordText.setText( "Joshua" ); //$NON-NLS-1$
    // passphrase
    Label passphraseLabel = new Label( composite, SWT.NONE );
    passphraseLabel.setText( Messages.getString( "SSHTokenDialog.passphrase" ) ); //$NON-NLS-1$
    this.passphraseText = new Text( composite, SWT.PASSWORD | SWT.BORDER );
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    this.passphraseText.setLayoutData( layoutData );
    this.passphraseText.setText( "Joshua" ); //$NON-NLS-1$
    // hostname
    Label hostnameLabel = new Label( composite, SWT.NONE );
    hostnameLabel.setText( Messages.getString( "SSHTokenDialog.hostname" ) ); //$NON-NLS-1$
    this.hostnameText = new Text( composite, SWT.BORDER );
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    this.hostnameText.setLayoutData( layoutData );
    this.hostnameText.setText( description.getHostname() );
    return composite;
  }
}
