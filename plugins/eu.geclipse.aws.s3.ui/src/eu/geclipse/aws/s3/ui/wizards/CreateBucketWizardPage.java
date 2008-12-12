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

package eu.geclipse.aws.s3.ui.wizards;

import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.jets3t.service.model.S3Bucket;

import eu.geclipse.aws.s3.ui.internal.Activator;

/**
 * A Wizard page to query for details on a new bucket.
 * 
 * @author Moritz Post
 */
public class CreateBucketWizardPage extends WizardPage {

  /** The id of this wizard page. */
  private static final String WIZARD_PAGE_ID = "eu.geclipse.aws.s3.ui.wizards.createBucketWizardPage"; //$NON-NLS-1$

  /** The {@link Text} widget containing the name of the bucket. */
  private Text textName;

  /** A {@link Combo} to display the available locations of a S3 bucket. */
  private Combo comboLocation;

  /**
   * Creates a new S3 bucket wizard page.
   */
  protected CreateBucketWizardPage() {
    super( CreateBucketWizardPage.WIZARD_PAGE_ID,
           Messages.getString( "CreateBucketWizardPage.page_title" ), null ); //$NON-NLS-1$
    setDescription( Messages.getString( "CreateBucketWizardPage.page_description" ) ); //$NON-NLS-1$
    URL imgUrl = Activator.getDefault()
      .getBundle()
      .getEntry( "icons/wizban/service_wiz.gif" ); //$NON-NLS-1$
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
    group.setText( Messages.getString( "CreateBucketWizardPage.groupBucketDetails_title" ) ); //$NON-NLS-1$

    // name
    Label labelName = new Label( group, SWT.NONE );
    labelName.setLayoutData( new GridData( SWT.BEGINNING,
                                           SWT.CENTER,
                                           false,
                                           false ) );
    labelName.setText( Messages.getString( "CreateBucketWizardPage.labelBucketName_text" ) ); //$NON-NLS-1$

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

    // location
    Label labelLocation = new Label( group, SWT.NONE );
    labelName.setLayoutData( new GridData( SWT.BEGINNING,
                                           SWT.CENTER,
                                           false,
                                           false ) );
    labelLocation.setText( Messages.getString( "CreateBucketWizardPage.labelBucketLocation_text" ) ); //$NON-NLS-1$

    this.comboLocation = new Combo( group, SWT.DROP_DOWN | SWT.READ_ONLY );
    this.comboLocation.setLayoutData( new GridData( SWT.FILL,
                                                    SWT.CENTER,
                                                    true,
                                                    false,
                                                    2,
                                                    0 ) );
    this.comboLocation.add( Messages.getString( "CreateBucketWizardPage.locationEU_text" ) ); //$NON-NLS-1$
    this.comboLocation.add( Messages.getString( "CreateBucketWizardPage.locationUS_text" ) ); //$NON-NLS-1$

    this.comboLocation.select( 0 );

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
      error = Messages.getString( "CreateBucketWizardPage.errorBucketNameRequired_text" ); //$NON-NLS-1$
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
   * Gets the text for the bucket name given by the user.
   * 
   * @return the new bucket name
   */
  public String getBucketName() {
    return this.textName.getText();
  }

  /**
   * Gets the text for the bucket location. The returned String is derived from
   * {@link S3Bucket} and can either be:
   * <ol>
   * <li> {@link S3Bucket#LOCATION_EUROPE}</li>
   * <li> {@link S3Bucket#LOCATION_US}</li>
   * </ol>
   * 
   * @return the new bucket location or <code>null</code> if an error occurred
   */
  public String getLocation() {
    int selectionIndex = this.comboLocation.getSelectionIndex();

    switch( selectionIndex ) {
      case 0:
        return S3Bucket.LOCATION_EUROPE;
      case 1:
        return S3Bucket.LOCATION_US;
      default:
        return null;
    }
  }
}
