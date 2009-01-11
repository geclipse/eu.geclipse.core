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

import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.aws.util.AWSUtil;
import eu.geclipse.ui.widgets.StoredCombo;

/**
 * Creates a new {@link WizardPage} presenting the form elements for the
 * {@link AddAttributeWizard}.
 * 
 * @author Moritz Post
 */
public class AddAttributeWizardPage extends WizardPage {

  /** The id of this wizard page. */
  private static final String WIZARD_PAGE_ID = "eu.geclipse.aws.ec2.ui.wizards.addAttributeWizardPage"; //$NON-NLS-1$

  /** The widget to take the aws account id. */
  private StoredCombo storedComboAWSAccountId;

  /**
   * Create a new {@link WizardPage} and define title, description and image.
   */
  protected AddAttributeWizardPage() {
    super( AddAttributeWizardPage.WIZARD_PAGE_ID, Messages.getString("AddAttributeWizardPage.wizard_page_title"), null ); //$NON-NLS-1$
    setDescription( Messages.getString("AddAttributeWizardPage.wizard_page_description") ); //$NON-NLS-1$
    URL imgUrl = Activator.getDefault()
      .getBundle()
      .getEntry( "icons/wizban/service_wiz.gif" ); //$NON-NLS-1$
    setImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
    setPageComplete( false );
  }

  public void createControl( final Composite parent ) {

    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ) );
    mainComp.setLayout( new GridLayout( 2, false ) );

    Label label = new Label( mainComp, SWT.NONE );
    label.setLayoutData( new GridData( SWT.BEGINNING, SWT.CENTER, false, false ) );
    label.setText( Messages.getString("AddAttributeWizardPage.label_aws_account_id") ); //$NON-NLS-1$

    this.storedComboAWSAccountId = new StoredCombo( mainComp, SWT.SINGLE
                                                              | SWT.LEAD
                                                              | SWT.BORDER );
    this.storedComboAWSAccountId.setLayoutData( new GridData( SWT.FILL,
                                                              SWT.CENTER,
                                                              true,
                                                              false ) );
    this.storedComboAWSAccountId.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {
        validateInput();
      }

    } );

    setControl( mainComp );
  }

  /**
   * Validates the input in the form elements. If the input is not valid and
   * error message is presented and the {@link #setPageComplete(boolean)} flag
   * is set to <code>false</code>.
   */
  protected void validateInput() {
    String error = null;

    String transformedAccountNumber = AWSUtil.transformAWSAccountNumber( this.storedComboAWSAccountId.getText() );

    if( transformedAccountNumber == null ) {
      error = Messages.getString("AddAttributeWizardPage.error_valid_aws_account_id_required"); //$NON-NLS-1$
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
   * Returns the inserted aws account id if it complies to the accepted aws
   * account id format as determined in the
   * {@link AWSUtil#transformAWSAccountNumber(String)} method.
   * 
   * @return The valid aws account id or <code>null</code> if the provided id
   *         does not comply or is empty
   */
  public String getAWSAccountId() {
    return AWSUtil.transformAWSAccountNumber( this.storedComboAWSAccountId.getText() );
  }

}
