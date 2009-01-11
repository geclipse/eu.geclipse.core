/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.ec2.ui.wizards;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.xerox.amazonws.ec2.GroupDescription;

import eu.geclipse.aws.ec2.ui.internal.Activator;

/**
 * The CIDR Wizard page providing the form elements for the CIDR wizard.
 * 
 * @author Moritz Post
 */
public class CIDRPermissionWizardPage extends WizardPage {

  /** Id of this Wizard page. */
  private static final String WIZARD_PAGE_ID = "eu.geclipse.aws.ec2.ui.wizard.cidrPermission"; //$NON-NLS-1$

  /** The possible protocols supported by the amazon permission ruleset. */
  private static final String[] protocols = new String[]{
    "tcp", "udp", "icmp" //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
  };

  /** The regex pattern matching a cidr address. */
  private Pattern patternCidr = Pattern.compile( "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}/[0-9]{1,2}" ); //$NON-NLS-1$

  /** The {@link Text} widget element for the CIDR address. */
  private Text textCidr;

  /** The {@link Combo} widget element for the protocol. */
  private Combo comboProtocol;

  /** The {@link Text} widget element for the from port data. */
  private Text textFromPort;

  /** The {@link Text} widget element for the to port data. */
  private Text textToPort;

  /**
   * The constructor creates the {@link WizardPage} and sets title, description
   * and page image.
   * 
   * @param securityGroup the security group to add the cidr entry to
   */
  public CIDRPermissionWizardPage( final GroupDescription securityGroup ) {
    super( CIDRPermissionWizardPage.WIZARD_PAGE_ID,
           Messages.getString("CIDRPermissionWizardPage.page_title") + securityGroup.getName(), //$NON-NLS-1$
           null );
    setDescription( Messages.getString("CIDRPermissionWizardPage.page_description") //$NON-NLS-1$
                    + securityGroup.getName() );
    URL imgUrl = Activator.getDefault()
      .getBundle()
      .getEntry( "icons/wizban/security_wiz.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
    setPageComplete( false );
  }

  public void createControl( final Composite parent ) {

    Composite mainComp = new Composite( parent, SWT.NONE );
    GridData gData = new GridData( SWT.FILL, SWT.FILL, true, true );
    mainComp.setLayoutData( gData );
    GridLayout gridLayout = new GridLayout( 4, false );
    mainComp.setLayout( gridLayout );

    // cidr
    Label labelCidr = new Label( mainComp, SWT.NONE );
    labelCidr.setText( Messages.getString("CIDRPermissionWizardPage.label_cidr") ); //$NON-NLS-1$

    this.textCidr = new Text( mainComp, SWT.BORDER );
    this.textCidr.setText( "0.0.0.0/0" ); //$NON-NLS-1$
    this.textCidr.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {
        validateForm();
      }
    } );

    gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
    gData.horizontalSpan = 3;
    this.textCidr.setLayoutData( gData );

    // protocol
    Label labelProtocol = new Label( mainComp, SWT.NONE );
    labelProtocol.setText( Messages.getString("CIDRPermissionWizardPage.label_protcol") ); //$NON-NLS-1$

    this.comboProtocol = new Combo( mainComp, SWT.BORDER | SWT.READ_ONLY );
    for( String protocol : CIDRPermissionWizardPage.protocols ) {
      this.comboProtocol.add( protocol );
    }
    this.comboProtocol.select( 0 );
    gData = new GridData( SWT.FILL, SWT.CENTER, true, false );
    gData.horizontalSpan = 3;
    this.comboProtocol.setLayoutData( gData );

    // from port
    Label labelFromPort = new Label( mainComp, SWT.NONE );
    labelFromPort.setText( Messages.getString("CIDRPermissionWizardPage.label_from_port") ); //$NON-NLS-1$

    this.textFromPort = new Text( mainComp, SWT.BORDER );
    this.textFromPort.setLayoutData( new GridData( SWT.FILL,
                                                   SWT.CENTER,
                                                   true,
                                                   false ) );
    this.textFromPort.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {
        validateForm();
      }
    } );
    // to port
    Label labelToPort = new Label( mainComp, SWT.NONE );
    labelToPort.setText( Messages.getString("CIDRPermissionWizardPage.label_to_port") ); //$NON-NLS-1$

    this.textToPort = new Text( mainComp, SWT.BORDER );
    this.textToPort.setLayoutData( new GridData( SWT.FILL,
                                                 SWT.CENTER,
                                                 true,
                                                 false ) );
    this.textToPort.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {
        validateForm();
      }
    } );

    setControl( parent );
  }

  /**
   * Validate the form input and display an error message if validation failed.
   */
  private void validateForm() {

    String error = null;

    Matcher m = this.patternCidr.matcher( this.textCidr.getText().trim() );

    if( !m.matches() ) {
      error = Messages.getString("CIDRPermissionWizardPage.error_not_valid_cidr"); //$NON-NLS-1$
    }

    if( error == null ) {
      String fromPort = this.textFromPort.getText();
      try {
        Integer.parseInt( fromPort );
      } catch( NumberFormatException e ) {
        error = Messages.getString("CIDRPermissionWizardPage.error_from_port_not_valid"); //$NON-NLS-1$
      }
    }

    if( error == null ) {
      String toPort = this.textToPort.getText();
      try {
        Integer.parseInt( toPort );
      } catch( NumberFormatException e ) {
        error = Messages.getString("CIDRPermissionWizardPage.error_to_port_not_valid"); //$NON-NLS-1$
      }
    }

    if( error == null ) {
      setErrorMessage( null );
      setPageComplete( true );
    } else {
      setErrorMessage( error );
      setPageComplete( false );
    }
  }

  /**
   * Getter for the text in the {@link #textCidr} field.
   * 
   * @return the cidr address
   */
  public String getCidr() {
    return this.textCidr.getText().trim();
  }

  /**
   * Getter for the text in the {@link #comboProtocol} field.
   * 
   * @return the protocol
   */
  public String getProtocol() {
    return CIDRPermissionWizardPage.protocols[ this.comboProtocol.getSelectionIndex() ];
  }

  /**
   * Getter for the text in the {@link #textFromPort} field.
   * 
   * @return the start of the port range
   */
  public int getFromPort() {
    return Integer.parseInt( this.textFromPort.getText().trim() );
  }

  /**
   * Getter for the text in the {@link #textToPort} field.
   * 
   * @return the end of the port range
   */
  public int getToPort() {
    return Integer.parseInt( this.textToPort.getText().trim() );
  }

}
