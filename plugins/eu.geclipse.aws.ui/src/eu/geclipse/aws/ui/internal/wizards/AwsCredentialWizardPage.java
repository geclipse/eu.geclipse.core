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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.ui.internal.wizards;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.aws.auth.AwsCredentialDescription;
import eu.geclipse.aws.ui.internal.Activator;
import eu.geclipse.core.auth.PasswordManager;
import eu.geclipse.ui.widgets.StoredCombo;

/**
 * Wizard page for creating AWS credentials.
 */
public class AwsCredentialWizardPage
    extends WizardPage {
  
  /**
   * This page's ID.
   */
  private static final String PAGE_ID
    = "eu.geclipse.amazon.auth.wizardpage"; //$NON-NLS-1$
  
  /**
   * ID used for the key stored combo.
   */
  private static final String KEY_ID_PREF_ID
    = "KEY_ID"; //$NON-NLS-1$
  
  /**
   * The Combo used for the key ID.
   */
  protected StoredCombo idCombo;
  
  /**
   * The Text used for the secret key.
   */
  private Text keyText;
  
  /**
   * Initial description coming from token requests.
   */
  private AwsCredentialDescription initialDescription;

  /**
   * Create a new credential page with the specified initial
   * description.
   * 
   * @param initialDescription The initial description coming
   * from the token request or <code>null</code> if no such
   * description is available.
   */
  protected AwsCredentialWizardPage( final AwsCredentialDescription initialDescription ) {
    super( PAGE_ID,
           Messages.getString("AwsCredentialWizardPage.page_title"), //$NON-NLS-1$
           null );
    setDescription( Messages.getString("AwsCredentialWizardPage.description") ); //$NON-NLS-1$
    this.initialDescription = initialDescription;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
   */
  public void createControl( final Composite parent ) {
    
    IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
    GridData gData;
    
    Composite mainComp = new Composite( parent, SWT.NONE );
    mainComp.setLayout( new GridLayout( 2, false ) );
    
    Label idLabel = new Label( mainComp, SWT.NULL );
    idLabel.setText( Messages.getString("AwsCredentialWizardPage.key_id_label") ); //$NON-NLS-1$
    gData = new GridData();
    idLabel.setLayoutData( gData );
    
    this.idCombo = new StoredCombo( mainComp, SWT.NULL );
    this.idCombo.setPreferences( prefStore, KEY_ID_PREF_ID );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.idCombo.setLayoutData( gData );
    
    Label keyLabel = new Label( mainComp, SWT.NULL );
    keyLabel.setText( Messages.getString("AwsCredentialWizardPage.secret_key_label") ); //$NON-NLS-1$
    gData = new GridData();
    keyLabel.setLayoutData( gData );
    
    this.keyText = new Text( mainComp, SWT.PASSWORD | SWT.BORDER );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.keyText.setLayoutData( gData );
    
    this.idCombo.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        String pwuid = AwsCredentialWizardPage.this.idCombo.getText();
        querySecretKey( pwuid );
      }
    } );
    
    if ( this.initialDescription != null ) {
      
      String keyID = this.initialDescription.getAccessKeyID();
      if ( keyID != null ) {
        this.idCombo.setText( keyID );
      }
      
      String key = this.initialDescription.getSecretAccessKey();
      if ( key != null ) {
        this.keyText.setText( key );
      }
      
    }
    
    querySecretKey( this.idCombo.getText() );
    
    setControl( mainComp );

  }
  
  /**
   * Get a credential description from the currently specified
   * values in this page's controls.
   * 
   * @return {@link AwsCredentialDescription} that may be
   * used to create a new credential.
   */
  public AwsCredentialDescription getCredentialDescription() {
    
    String id = this.idCombo.getText();
    String key = this.keyText.getText();
    
    PasswordManager.registerPassword( id, key );
    
    return new AwsCredentialDescription( id, key );
    
  }
  
  /**
   * Query the password manager for a secret key fitting the
   * specified password uid.
   *  
   * @param pwuid The access key ID.
   */
  protected void querySecretKey( final String pwuid ) {
    String pw = PasswordManager.getPassword( pwuid );
    if ( pw != null ) {
      this.keyText.setText( pw );
      setMessage( Messages.getString("AwsCredentialWizardPage.secret_key_found_info"), //$NON-NLS-1$
                  IMessageProvider.INFORMATION );
    } else {
      setMessage( null );
    }
  }

}
