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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.xerox.amazonws.ec2.GroupDescription;

import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.aws.util.AWSUtil;

/**
 * This {@link WizardPage} provides the form elements to add group based access
 * permissions to an EC2 security group.
 * 
 * @author Moritz Post
 */
public class GroupPermissionWizardPage extends WizardPage {

  /** Id of this Wizard page. */
  private static final String WIZARD_PAGE_ID = "eu.geclipse.aws.ec2.ui.wizard.groupPermission"; //$NON-NLS-1$

  /** The {@link Text} widget containing the account id. */
  private Text textAccountId;

  /** The text widget containing the group name of the account. */
  private Text textGroup;

  /**
   * The constructor creates the {@link WizardPage} and sets title, description
   * and page image.
   * 
   * @param securityGroup the security group to add the cidr entry to
   */
  public GroupPermissionWizardPage( final GroupDescription securityGroup ) {
    super( GroupPermissionWizardPage.WIZARD_PAGE_ID,
           Messages.getString( "GroupPermissionWizardPage.page_title" ) + securityGroup.getName(), //$NON-NLS-1$
           null );
    setDescription( Messages.getString( "GroupPermissionWizardPage.page_description" ) //$NON-NLS-1$
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
    GridLayout gridLayout = new GridLayout( 2, false );
    mainComp.setLayout( gridLayout );

    // account id
    Label labelAccountId = new Label( mainComp, SWT.NONE );
    labelAccountId.setText( Messages.getString( "GroupPermissionWizardPage.label_account_id" ) ); //$NON-NLS-1$

    this.textAccountId = new Text( mainComp, SWT.BORDER );
    this.textAccountId.setLayoutData( new GridData( SWT.FILL,
                                                    SWT.CENTER,
                                                    true,
                                                    false ) );
    this.textAccountId.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {
        validateForm();
      }
    } );
    // group name
    Label labelGroup = new Label( mainComp, SWT.NONE );
    labelGroup.setText( Messages.getString( "GroupPermissionWizardPage.label_group" ) ); //$NON-NLS-1$

    this.textGroup = new Text( mainComp, SWT.BORDER );
    this.textGroup.setLayoutData( new GridData( SWT.FILL,
                                                SWT.CENTER,
                                                true,
                                                false ) );
    this.textGroup.addModifyListener( new ModifyListener() {

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

    String transformedAccountNumber = AWSUtil.transformAWSAccountNumber( this.textAccountId.getText() );

    if( transformedAccountNumber == null ) {
      error = Messages.getString( "GroupPermissionWizardPage.error_valid_account_id_required" ); //$NON-NLS-1$
    }

    if( error == null && this.textGroup.getText().trim().length() == 0 ) {
      error = Messages.getString( "GroupPermissionWizardPage.error_valid_security_group_required" ); //$NON-NLS-1$
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
   * A getter for the {@link #textAccountId} field.
   * 
   * @return the textAccountId
   */
  public String getAccountId() {
    return AWSUtil.transformAWSAccountNumber( this.textAccountId.getText()
      .trim() );
  }

  /**
   * A getter for the {@link #textGroup} field.
   * 
   * @return the textGroup
   */
  public String getGroup() {
    return this.textGroup.getText().trim();
  }

}
