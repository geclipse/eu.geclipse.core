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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;

/**
 * The {@link WizardPage} for the {@link CreateSecurityGroupWizard}. It provides
 * the form elements to input the name and the description of the newly created
 * security group.
 * 
 * @author Moritz Post
 */
public class CreateSecurityGroupWizardPage extends WizardPage {

  /** The id of this wizard page. */
  private static final String WIZARD_PAGE_ID = "eu.geclipse.aws.ec2.ui.wizards.createSecurityGroupWizardPage"; //$NON-NLS-1$

  /** The {@link Text} widget containing the name of the security group. */
  private Text textName;

  /** The {@link Text} widget containing the description of the security group. */
  private Text textDescription;

  /**
   * Creates a new {@link WizardPage} with the aws access id in the aws vo.
   * 
   * @param awsVo the container of the aws access id
   */
  public CreateSecurityGroupWizardPage( final AWSVirtualOrganization awsVo ) {
    super( CreateSecurityGroupWizardPage.WIZARD_PAGE_ID,
           Messages.getString( "CreateSecurityGroupWizardPage.page_title" ), //$NON-NLS-1$
           null );
    String awsAccessId = null;
    try {
      awsAccessId = awsVo.getProperties().getAwsAccessId();
      setDescription( Messages.getString( "CreateSecurityGroupWizardPage.page_description1" ) + awsAccessId + Messages.getString( "CreateSecurityGroupWizardPage.page_description2" ) ); //$NON-NLS-1$ //$NON-NLS-2$
    } catch( ProblemException problemEx ) {
      Activator.log( "Could not read aws vo properties", problemEx ); //$NON-NLS-1$
    }
    URL imgUrl = Activator.getDefault()
      .getBundle()
      .getEntry( "icons/wizban/security_wiz.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );

    setPageComplete( false );

  }

  public void createControl( final Composite parent ) {

    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    mainComp.setLayout( new GridLayout( 1, true ) );

    Group group = new Group( mainComp, SWT.NONE );
    group.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false ) );
    group.setLayout( new GridLayout( 2, false ) );
    group.setText( Messages.getString( "CreateSecurityGroupWizardPage.group_security_group_title" ) ); //$NON-NLS-1$

    // name
    Label labelName = new Label( group, SWT.NONE );
    labelName.setLayoutData( new GridData( SWT.BEGINNING,
                                           SWT.CENTER,
                                           false,
                                           false ) );
    labelName.setText( Messages.getString( "CreateSecurityGroupWizardPage.label_name" ) ); //$NON-NLS-1$

    this.textName = new Text( group, SWT.SINGLE | SWT.LEAD | SWT.BORDER );
    this.textName.setLayoutData( new GridData( SWT.FILL,
                                               SWT.CENTER,
                                               true,
                                               false ) );
    this.textName.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {
        validatePage();
      }

    } );

    // description
    Label labelDescription = new Label( group, SWT.NONE );
    labelDescription.setLayoutData( new GridData( SWT.BEGINNING,
                                                  SWT.CENTER,
                                                  false,
                                                  false ) );
    labelDescription.setText( Messages.getString( "CreateSecurityGroupWizardPage.label_description" ) ); //$NON-NLS-1$

    this.textDescription = new Text( group, SWT.SINGLE | SWT.LEAD | SWT.BORDER );
    this.textDescription.setLayoutData( new GridData( SWT.FILL,
                                                      SWT.CENTER,
                                                      true,
                                                      false ) );

    setControl( group );
  }

  /**
   * Validates the fields in the wizard pages form.
   * 
   * @return <code>true</code> if the forms entries are valid or
   *         <code>false</code> otherwise.
   */
  private boolean validatePage() {
    String error = null;

    if( this.textName.getText().trim().length() == 0 ) {
      error = Messages.getString( "CreateSecurityGroupWizardPage.error_security_group_requires_name" ); //$NON-NLS-1$
    }

    if( error != null ) {
      setErrorMessage( error );
      setPageComplete( false );
      return false;
    }
    setErrorMessage( null );
    setPageComplete( true );
    return true;
  }

  /**
   * A getter for the security group name contained within the {@link #textName}
   * widget. The returned text is treated with {@link String#trim()}.
   * 
   * @return the security group name or an empty {@link String} when no text has
   *         been set
   */
  public String getSecurityGroupName() {
    return this.textName.getText().trim();
  }

  /**
   * A getter for the security group description contained within the
   * {@link #textName} widget. The returned text is treated with
   * {@link String#trim()}.
   * 
   * @return the security group name or an empty {@link String} when no text has
   *         been set
   */
  public String getSecurityGroupDescription() {
    return this.textDescription.getText().trim();
  }

}
