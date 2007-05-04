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

package eu.geclipse.ui.wizards.portforward;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.core.portforward.ForwardType;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.widgets.NumberVerifier;
import eu.geclipse.ui.widgets.StoredCombo;

class NewPortForwardComposite extends Composite {
  private static final String HOSTNAMES = "hostnames"; //$NON-NLS-1$
  private Label typeLabel = null;
  private Combo typeCombo = null;
  private Label bindPortLabel = null;
  private Label hostnameLabel = null;
  private Label portLabel = null;
  private Text bindPortText = null;
  private StoredCombo hostnameCombo = null;
  private Text portText = null;

  NewPortForwardComposite( final Composite parent, final int style, final Listener listener ) {
    super( parent, style );
    initialize();
    this.typeCombo.add( Messages.getString("NewPortForwardComposite.local") ); //$NON-NLS-1$
    this.typeCombo.add( Messages.getString("NewPortForwardComposite.remote") ); //$NON-NLS-1$
    this.typeCombo.select( 0 );
    Activator activator = Activator.getDefault();
    IPreferenceStore preferenceStore = activator.getPreferenceStore();
    this.hostnameCombo.setPreferences( preferenceStore, HOSTNAMES );
    NumberVerifier numberVerifier = new NumberVerifier();
    this.bindPortText.addListener( SWT.Modify, listener );
    this.hostnameCombo.addListener( SWT.Modify, listener );
    this.portText.addListener( SWT.Modify, listener );
    this.bindPortText.addListener( SWT.Verify, numberVerifier );
    this.portText.addListener( SWT.Verify, numberVerifier );
  }

  private void initialize() {
    GridData portTextGridData = new GridData();
    portTextGridData.horizontalAlignment = GridData.FILL;
    portTextGridData.grabExcessHorizontalSpace = true;
    portTextGridData.verticalAlignment = GridData.CENTER;
    GridData hostnameComboGridData = new GridData();
    hostnameComboGridData.horizontalAlignment = GridData.FILL;
    hostnameComboGridData.grabExcessHorizontalSpace = true;
    hostnameComboGridData.verticalAlignment = GridData.CENTER;
    GridData bindPortTextGridData = new GridData();
    bindPortTextGridData.horizontalAlignment = GridData.FILL;
    bindPortTextGridData.grabExcessHorizontalSpace = true;
    bindPortTextGridData.verticalAlignment = GridData.CENTER;
    GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = 2;
    this.typeLabel = new Label(this, SWT.NONE);
    this.typeLabel.setText(Messages.getString("NewPortForwardComposite.type")); //$NON-NLS-1$
    this.setLayout(gridLayout);
    createCombo();
    this.setSize(new Point(393, 217));
    this.bindPortLabel = new Label(this, SWT.NONE);
    this.bindPortLabel.setText(Messages.getString("NewPortForwardComposite.bindPort")); //$NON-NLS-1$
    this.bindPortText = new Text(this, SWT.BORDER);
    bindPortText.setTextLimit(5);
    this.bindPortText.setLayoutData(bindPortTextGridData);
    this.hostnameLabel = new Label(this, SWT.NONE);
    this.hostnameLabel.setText(Messages.getString("NewPortForwardComposite.hostname")); //$NON-NLS-1$
    this.hostnameCombo = new StoredCombo(this, SWT.BORDER);
    this.hostnameCombo.setLayoutData(hostnameComboGridData);
    this.portLabel = new Label(this, SWT.NONE);
    this.portLabel.setText(Messages.getString("NewPortForwardComposite.port")); //$NON-NLS-1$
    this.portText = new Text(this, SWT.BORDER);
    portText.setTextLimit(5);
    this.portText.setLayoutData(portTextGridData);
  }

  /**
   * This method initializes combo
   *
   */
  private void createCombo() {
    GridData gridData = new GridData();
    gridData.horizontalAlignment = GridData.FILL;
    gridData.grabExcessHorizontalSpace = true;
    gridData.verticalAlignment = GridData.CENTER;
    this.typeCombo = new Combo(this, SWT.READ_ONLY);
    this.typeCombo.setLayoutData(gridData);
  }

  IStatus getStatus() {
    Integer port = null;
    Integer bindPort = null;
    boolean validPort = true;
    boolean nonRootPort = true;
    try {
      port = new Integer( this.portText.getText() );
      bindPort = new Integer( this.bindPortText.getText() );
    } catch ( NumberFormatException exception ) {
      // ignore
    }
    if ( port == null || port.intValue() < 1 || port.intValue() > 0xffff
         || bindPort == null || bindPort.intValue() < 1
         || bindPort.intValue() > 0xffff) {
      validPort = false;
    } else if ( bindPort.intValue() < 1024 ) {
      nonRootPort = false;
    }
    
    IStatus status = new Status( IStatus.OK, Activator.PLUGIN_ID, IStatus.OK,
                                 "",  null ); //$NON-NLS-1$
    if ( this.bindPortText.getText().length() == 0 ) {
      status = new Status( IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK,
                           Messages.getString("NewPortForwardComposite.bindPortMustNotBeEmpty"), null ); //$NON-NLS-1$
    } else if ( this.hostnameCombo.getText().length() == 0 ) {
      status = new Status( IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK,
                           Messages.getString("NewPortForwardComposite.hostnameMustNotBeEmpty"), null ); //$NON-NLS-1$
    } else if ( this.portText.getText().length() == 0 ) {
      status = new Status( IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK,
                           Messages.getString("NewPortForwardComposite.portMustNotBeEmpty"), null ); //$NON-NLS-1$
    } else if ( !validPort ) {
      status = new Status( IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK,
                           Messages.getString("NewPortForwardComposite.PortsMustBeInValidRange"), null ); //$NON-NLS-1$
    } else if ( !nonRootPort ) {
      status = new Status( IStatus.WARNING, Activator.PLUGIN_ID, IStatus.OK,
                           Messages.getString("NewPortForwardComposite.portsBelow1024"), null ); //$NON-NLS-1$
    }
    return status;
  }

  IForwardTableEntry getForward() {
    ForwardType type;
    if ( this.typeCombo.getSelectionIndex() == 0 ) type = ForwardType.LOCAL;
    else type = ForwardType.REMOTE;
    ForwardTableEntry forward = new ForwardTableEntry( type,
                                             new Integer( this.bindPortText.getText() ).intValue(),
                                             this.hostnameCombo.getText(),
                                             new Integer( this.portText.getText() ).intValue(), 
                                             true );
    return forward;
  }
}  //  @jve:decl-index=0:visual-constraint="10,10"
