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

import java.util.ArrayList;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.util.TokenValidator;
import eu.geclipse.info.glue.AbstractGlueTable;
import eu.geclipse.info.glue.GlueQuery;
import eu.geclipse.ui.widgets.NumberVerifier;
import eu.geclipse.ui.widgets.StoredCombo;

class SSHConnectionComposite extends Composite {
  private static final String sshDefaultPort = "22"; //$NON-NLS-1$
  private static final String USERNAMES = "usernames"; //$NON-NLS-1$  //  @jve:decl-index=0:
  private static final String HOSTNAMES = "hostnames"; //$NON-NLS-1$
  private Label usernameLabel = null;
  private StoredCombo usernameCombo = null;
  private Label hostnameLabel = null;
  private StoredCombo hostnameCombo = null;
  private Label passwordLabel = null;
  private Text passwordText = null;
  private Label portLabel = null;
  private Text portText = null;

  SSHConnectionComposite( final Composite parent, final int style, final Listener page, final String preSelectedHostname ) {
    super( parent, style );
    initialize();
    Activator activator = Activator.getDefault();
    IPreferenceStore preferenceStore = activator.getPreferenceStore();
    this.usernameCombo.setPreferences( preferenceStore, USERNAMES );
    this.hostnameCombo.setPreferences( preferenceStore, HOSTNAMES );
    if ( preSelectedHostname != null ) {
      this.hostnameCombo.setText( preSelectedHostname );
    }
    this.usernameCombo.addListener( SWT.Modify, page );
    this.hostnameCombo.addListener( SWT.Modify, page );
    this.passwordText.addListener( SWT.Modify, page );
    this.portText.addListener( SWT.Modify, page );
    this.portText.addListener( SWT.Verify, new NumberVerifier() );
    GridData portTextGridData = (GridData) this.portText.getLayoutData();
    GC gc = new GC( this.portText );
    FontMetrics fm = gc.getFontMetrics();
    portTextGridData.widthHint = 7 * fm.getAverageCharWidth();
    gc.dispose();
    addComputingElements();
  }

  private void addComputingElements() {
    try {
      IGridElement[] vos = GridModel.getVoManager().getChildren(null);
      for ( IGridElement vo : vos ) {
        ArrayList<AbstractGlueTable> ceTable = GlueQuery.getGlueTable( "GlueCE", "GlueCE", vo.getName() ); //$NON-NLS-1$ //$NON-NLS-2$
        for ( AbstractGlueTable table : ceTable ) {
          try {
            String hostname = ( String ) table.getFieldByName( "HostName" ); //$NON-NLS-1$
            if ( hostname != null && this.hostnameCombo.indexOf( hostname ) == -1 ) {
              this.hostnameCombo.add( hostname );
            }
          } catch( RuntimeException exception ) {
            Activator.logException( exception );
          } catch( IllegalAccessException exception ) {
            Activator.logException( exception );
          } catch( NoSuchFieldException exception ) {
            Activator.logException( exception );
          }
        }
      }
    } catch( GridModelException exception ) {
      Activator.logException( exception );
    }
  }

  private void initialize() {
    GridData portTextGridData = new GridData();
    portTextGridData.horizontalAlignment = GridData.FILL;
    portTextGridData.grabExcessHorizontalSpace = false;
    portTextGridData.verticalAlignment = GridData.CENTER;
    GridData passwordTextGridData = new GridData();
    passwordTextGridData.horizontalAlignment = GridData.FILL;
    passwordTextGridData.grabExcessHorizontalSpace = true;
    passwordTextGridData.horizontalSpan = 3;
    passwordTextGridData.verticalAlignment = GridData.CENTER;
    GridData hostnameComboGridData = new GridData();
    hostnameComboGridData.horizontalAlignment = GridData.FILL;
    hostnameComboGridData.grabExcessHorizontalSpace = true;
    hostnameComboGridData.verticalAlignment = GridData.CENTER;
    GridData usernameComboGridData = new GridData();
    usernameComboGridData.horizontalAlignment = GridData.FILL;
    usernameComboGridData.grabExcessHorizontalSpace = true;
    usernameComboGridData.horizontalSpan = 3;
    usernameComboGridData.verticalAlignment = GridData.CENTER;
    GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = 4;
    this.hostnameLabel = new Label(this, SWT.NONE);
    this.hostnameLabel.setText(Messages.getString("SSHConnectionComposite.hostName")); //$NON-NLS-1$
    this.hostnameCombo = new StoredCombo(this, SWT.DROP_DOWN);
    this.hostnameCombo.setLayoutData(hostnameComboGridData);
    this.portLabel = new Label(this, SWT.NONE);
    this.portLabel.setText(Messages.getString("SSHConnectionComposite.port")); //$NON-NLS-1$
    this.portText = new Text(this, SWT.BORDER);
    this.portText.setTextLimit(5);
    this.portText.setText(sshDefaultPort);
    this.portText.setLayoutData(portTextGridData);
    this.usernameLabel = new Label(this, SWT.NONE);
    this.usernameLabel.setText(Messages.getString("SSHConnectionComposite.userName")); //$NON-NLS-1$
    this.usernameCombo = new StoredCombo(this, SWT.DROP_DOWN);
    this.usernameCombo.setLayoutData(usernameComboGridData);
    this.passwordLabel = new Label(this, SWT.NONE);
    this.passwordLabel.setText(Messages.getString("SSHConnectionComposite.password")); //$NON-NLS-1$
    this.passwordText = new Text(this, SWT.BORDER | SWT.PASSWORD);
    this.passwordText.setLayoutData(passwordTextGridData);
    this.setLayout(gridLayout);
    setSize( new Point( 300, 200 ) );
  }

  IStatus getStatus() {
    Integer port = null;
    boolean validPort = true;

    try {
      port = new Integer( this.portText.getText() );
    } catch (NumberFormatException exception) {
      // ignore
    }
    if ( port == null || port.intValue() < 1 || port.intValue() > 0xffff ) {
      validPort = false;
    }

    IStatus status = new Status( IStatus.OK, Activator.PLUGIN_ID, IStatus.OK,
                                 "",  null ); //$NON-NLS-1$
    if ( this.usernameCombo.getText().length() == 0 ) {
      status = new Status( IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK,
                           Messages.getString("SSHConnectionComposite.userNameMustNotBeEmpty"), null ); //$NON-NLS-1$
    } else if ( this.hostnameCombo.getText().length() == 0 ) {
      status = new Status( IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK,
                           Messages.getString("SSHConnectionComposite.hostNameMustNotBeEmpty"), null ); //$NON-NLS-1$
    } else if ( !validPort ) {
      status = new Status( IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK,
                           Messages.getString("SSHConnectionComposite.AValidPortNumberMustBeEntered"), null ); //$NON-NLS-1$
    } else if ( !TokenValidator.validateFQHN( this.hostnameCombo.getText() ) ) {
      status = new Status( IStatus.WARNING, Activator.PLUGIN_ID, IStatus.OK,
                           Messages.getString("SSHConnectionComposite.invalidHostname"), null ); //$NON-NLS-1$
    } else if ( this.passwordText.getText().length() == 0 ) {
      status = new Status( IStatus.INFO, Activator.PLUGIN_ID, IStatus.OK,
                           Messages.getString("SSHConnectionComposite.passwordIsRequiredIfYouAreNotUsingPubKeyAuth"), //$NON-NLS-1$
                           null );
    }
    return status;
  }

  SSHConnectionInfo getConnectionInfo() {
    return new SSHConnectionInfo( this.usernameCombo.getText(),
                                  this.hostnameCombo.getText(),
                                  this.passwordText.getText(),
                                  null,
                                  new Integer( this.portText.getText() ).intValue() );
  }
}
