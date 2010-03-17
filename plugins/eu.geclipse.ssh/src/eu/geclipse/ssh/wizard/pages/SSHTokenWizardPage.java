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

package eu.geclipse.ssh.wizard.pages;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.ssh.Activator;
import eu.geclipse.ssh.auth.SSHTokenDescription;
import eu.geclipse.ssh.internal.Messages;
import eu.geclipse.ui.widgets.StoredCombo;

/**
 * SSH Token Wizard Page
 */
public class SSHTokenWizardPage extends WizardPage {

  private static final String USERNAMES = "usernames"; //$NON-NLS-1$
  private static final String HOSTNAMES = "hostnames"; //$NON-NLS-1$
  private StoredCombo usernameCombo = null;
  private StoredCombo hostnameCombo = null;
  private Text passwordText;
  private Text passphraseText;

  /**
   * Creates a new SSH Token Wizard Page.
   * 
   * @param pageName
   */
  public SSHTokenWizardPage( final String pageName ) {
    super( pageName );
    setTitle( Messages.getString( "SSHTokenWizardPage.Title" ) ); //$NON-NLS-1$
    setDescription( Messages.getString( "SSHTokenWizardPage.Description" ) ); //$NON-NLS-1$
  }

  public void createControl( final Composite parent ) {
    Activator activator = Activator.getDefault();
    IPreferenceStore preferenceStore = activator.getPreferenceStore();
    Composite composite = new Composite( parent, SWT.NONE );
    GridLayout layout = new GridLayout();
    GridData layoutData = new GridData( SWT.FILL, SWT.FILL, true, true );
    layout.numColumns = 2;
    composite.setLayout( layout );
    composite.setLayoutData( layoutData );
    // username
    Label usernameLabel = new Label( composite, SWT.NONE );
    usernameLabel.setText( Messages.getString( "SSHTokenWizardPage.username" ) ); //$NON-NLS-1$
    this.usernameCombo = new StoredCombo( composite, SWT.DROP_DOWN );
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    this.usernameCombo.setLayoutData( layoutData );
    this.usernameCombo.setPreferences( preferenceStore, USERNAMES );
    // password
    Label passwordLabel = new Label( composite, SWT.NONE );
    passwordLabel.setText( Messages.getString( "SSHTokenWizardPage.password" ) ); //$NON-NLS-1$
    this.passwordText = new Text( composite, SWT.PASSWORD | SWT.BORDER );
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    this.passwordText.setLayoutData( layoutData );
    setControl( composite );
    // passphrase
    Label passphraseLabel = new Label( composite, SWT.NONE );
    passphraseLabel.setText( Messages.getString( "SSHTokenWizardPage.passphrase" ) ); //$NON-NLS-1$
    this.passphraseText = new Text( composite, SWT.PASSWORD | SWT.BORDER );
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    this.passphraseText.setLayoutData( layoutData );
    setControl( composite );
    // hostname
    Label hostnameLabel = new Label( composite, SWT.NONE );
    hostnameLabel.setText( Messages.getString( "SSHTokenWizardPage.hostname" ) ); //$NON-NLS-1$
    this.hostnameCombo = new StoredCombo( composite, SWT.DROP_DOWN );
    layoutData = new GridData( SWT.FILL, SWT.FILL, true, false );
    this.hostnameCombo.setLayoutData( layoutData );
    this.hostnameCombo.setPreferences( preferenceStore, HOSTNAMES );
  }

  /**
   * Returns the tokenDescription
   * 
   * @return tokenDescription
   */
  public SSHTokenDescription getTokenDescription() {
    SSHTokenDescription description = new SSHTokenDescription( this.usernameCombo.getText(),
                                                               this.hostnameCombo.getText(),
                                                               this.passwordText.getText(),
                                                               this.passphraseText.getText(),
                                                               22 );
    return description;
  }
}
