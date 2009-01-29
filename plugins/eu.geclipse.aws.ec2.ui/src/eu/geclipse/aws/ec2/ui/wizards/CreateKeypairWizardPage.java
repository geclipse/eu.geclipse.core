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

import java.io.File;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;

/**
 * The {@link WizardPage} for the {@link CreateKeypairWizard}. It provides the
 * form elements to input name and location of key file of the newly created
 * keypair.
 * 
 * @author Moritz Post
 */
public class CreateKeypairWizardPage extends WizardPage {

  /** The id of this wizard page. */
  private static final String WIZARD_PAGE_ID = "eu.geclipse.aws.ec2.ui.wizards.createKeypairWizardPage"; //$NON-NLS-1$

  /** The {@link Text} widget containing the name of the keypair. */
  private Text textName;

  /** The {@link Text} widget containing the path to the key store file. */
  private Text textKeyPath;

  /**
   * Creates a new {@link WizardPage} with the aws access id in the aws vo.
   * 
   * @param awsVo the container of the aws access id
   */
  public CreateKeypairWizardPage( final AWSVirtualOrganization awsVo ) {
    super( CreateKeypairWizardPage.WIZARD_PAGE_ID, Messages.getString("CreateKeypairWizardPage.page_title"), null ); //$NON-NLS-1$
    String awsAccessId = null;
    try {
      awsAccessId = awsVo.getProperties().getAwsAccessId();
      setDescription( Messages.getString("CreateKeypairWizardPage.page_description1") + awsAccessId + Messages.getString("CreateKeypairWizardPage.page_description2") ); //$NON-NLS-1$ //$NON-NLS-2$
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
    group.setLayout( new GridLayout( 3, false ) );
    group.setText( Messages.getString("CreateKeypairWizardPage.group_keypair_title") ); //$NON-NLS-1$

    // name
    Label labelName = new Label( group, SWT.NONE );
    labelName.setLayoutData( new GridData( SWT.BEGINNING,
                                           SWT.CENTER,
                                           false,
                                           false ) );
    labelName.setText( Messages.getString("CreateKeypairWizardPage.label_name") ); //$NON-NLS-1$

    this.textName = new Text( group, SWT.SINGLE | SWT.LEAD | SWT.BORDER );
    this.textName.setLayoutData( new GridData( SWT.FILL,
                                               SWT.CENTER,
                                               true,
                                               false,
                                               2,
                                               0 ) );
    this.textName.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {
        validatePage();
      }

    } );

    // path
    Label labelKeyPath = new Label( group, SWT.NONE );
    labelKeyPath.setLayoutData( new GridData( SWT.BEGINNING,
                                              SWT.CENTER,
                                              false,
                                              false ) );
    labelKeyPath.setText( Messages.getString("CreateKeypairWizardPage.label_key_path") ); //$NON-NLS-1$

    this.textKeyPath = new Text( group, SWT.SINGLE | SWT.LEAD | SWT.BORDER );
    this.textKeyPath.setLayoutData( new GridData( SWT.FILL,
                                                  SWT.CENTER,
                                                  true,
                                                  false ) );
    this.textKeyPath.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent e ) {
        validatePage();
      }

    } );

    Button button = new Button( group, SWT.PUSH );
    button.setLayoutData( new GridData( SWT.END, SWT.CENTER, false, false ) );
    button.setText( Messages.getString("CreateKeypairWizardPage.button_browse") ); //$NON-NLS-1$
    button.addListener( SWT.Selection, new Listener() {

      public void handleEvent( final Event event ) {
        FileDialog fileDialog = new FileDialog( getContainer().getShell(),
                                                SWT.OPEN );
        fileDialog.setText( Messages.getString("CreateKeypairWizardPage.save_key_as") ); //$NON-NLS-1$
        String dialogResult = fileDialog.open();
        CreateKeypairWizardPage.this.textKeyPath.setText( dialogResult );
      }

    } );
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
      error = Messages.getString("CreateKeypairWizardPage.error_name_required"); //$NON-NLS-1$
    } else if( this.textKeyPath.getText().trim().length() == 0 ) {
      error = Messages.getString("CreateKeypairWizardPage.error_file_path_required"); //$NON-NLS-1$
    }

    File keyFile = new File( this.textKeyPath.getText().trim() );
    if( keyFile.isDirectory() ) {
      error = Messages.getString("CreateKeypairWizardPage.error_fiel_path_can_not_be_directory"); //$NON-NLS-1$
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
   * A getter for the keypair name contained within the {@link #textName}
   * widget. The returned text is treated with {@link String#trim()}.
   * 
   * @return the keypair name or an empty {@link String} when no text has been
   *         set
   */
  public String getKeypairName() {
    return this.textName.getText().trim();
  }

  /**
   * A getter for the keypair path contained within the {@link #textKeyPath}
   * widget. The returned text is treated with {@link String#trim()}.
   * 
   * @return the keypair path or an empty {@link String} when no text has been
   *         set
   */
  public String getKeypairPath() {
    return this.textKeyPath.getText().trim();
  }
}