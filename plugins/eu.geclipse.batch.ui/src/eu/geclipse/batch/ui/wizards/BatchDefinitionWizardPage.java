/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.batch.BatchConnectionInfo;
import eu.geclipse.batch.ui.internal.Activator;
import eu.geclipse.batch.ui.internal.Messages;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.info.glue.AbstractGlueTable;
import eu.geclipse.info.glue.GlueQuery;
import eu.geclipse.ui.widgets.StoredCombo;

/**
 * Dialog for the user to fill in the necessary information to connect to a
 * batch service.
 */
public class BatchDefinitionWizardPage extends WizardPage {
  private static final String HOSTNAME_STRINGS = "hostname_string"; //$NON-NLS-1$

  private Composite mainComp;

  private Label hostNameLabel;

  private Label accountUserNameLabel;

  private Label batchTypeLabel;

  private Label intervalLabel;

  private StoredCombo hostNameCombo;

  private Text accountUserNameText;

  private Spinner intervalSpinner;

  private Combo batchTypeCombo;

  /**
   * The default constructor.
   */
  public BatchDefinitionWizardPage(  ) {
    super( "batchDefinitionPage" ); //$NON-NLS-1$
    this.setTitle( Messages.getString( "BatchDefinitionWizardPage.WindowTitle" ) ); //$NON-NLS-1$
    this.setDescription( Messages.getString( "BatchDefinitionWizardPage.Description" ) ); //$NON-NLS-1$
  }

  /**
   *
   */
  public void createControl( final Composite parent ) {

    GridData gData, lData;

    lData = new GridData();
    lData.minimumHeight = 0;

    this.mainComp = new Composite( parent, SWT.NONE );
    this.mainComp.setLayout( new GridLayout( 2, false ) );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.mainComp.setLayoutData( gData );

    this.hostNameLabel = new Label( this.mainComp, SWT.NONE );
    this.hostNameLabel.setText( Messages.getString( "BatchDefinitionWizardPage.HostName" ) ); //$NON-NLS-1$
    this.hostNameLabel.setLayoutData( lData );
    
    Activator activator = Activator.getDefault();
    IPreferenceStore preferenceStore = activator.getPreferenceStore();
    this.hostNameCombo = new StoredCombo( this.mainComp, SWT.DROP_DOWN );
    this.hostNameCombo.setPreferences( preferenceStore, HOSTNAME_STRINGS );
    this.hostNameCombo.setLayoutData( gData );
    this.hostNameCombo.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        updateUI();
      }
    } );
    
    this.accountUserNameLabel = new Label( this.mainComp, SWT.NONE );
    this.accountUserNameText = createEditorField( this.mainComp, 
                                           this.accountUserNameLabel, 
                                           Messages.getString( "BatchDefinitionWizardPage.UserName" ) ); //$NON-NLS-1$
    this.accountUserNameText.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        updateUI();
      }
    } );
    
    this.batchTypeLabel = new Label( this.mainComp, SWT.NONE );
    this.batchTypeLabel.setText( Messages.getString( "BatchDefinitionWizardPage.BatchType" ) ); //$NON-NLS-1$
    this.batchTypeLabel.setLayoutData( lData );

    this.batchTypeCombo = new Combo( this.mainComp, SWT.BORDER | SWT.READ_ONLY );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    gData.minimumHeight = 0;
    this.batchTypeCombo.setLayoutData( gData );
    
    this.initializeServiceCombo( this.batchTypeCombo );
    
    // Make sure that there are some implementations (extension points) for batch systems 
    if ( 0 == this.batchTypeCombo.getItemCount() )
      setErrorMessage( Messages.getString( "BatchDefinitionWizardPage.BatchTypeError" ) ); //$NON-NLS-1$
    else
      this.batchTypeCombo.select(0);

    this.intervalLabel = new Label( this.mainComp, SWT.RIGHT );
    this.intervalLabel.setText( Messages.getString( "BatchDefinitionWizardPage.Interval" ) ); //$NON-NLS-1$
    this.intervalLabel.setLayoutData( lData );

    this.intervalSpinner = new Spinner( this.mainComp, SWT.BORDER );
    this.intervalSpinner.setValues( 10, 5, 60, 0, 1, 10 );
    gData = new GridData( GridData.FILL_HORIZONTAL );
    gData.grabExcessHorizontalSpace = true;
    this.intervalSpinner.setLayoutData( gData );

    addComputingElements();

    setControl( this.mainComp );

    // Validate the fields
    isInputValid();
  }

  /**
   * Helper method to combine a label and a text field.
   * @param parent The composite.
   * @param label The label.
   * @param text The text for the label.
   * @return Returns the edit text field.
   */
  protected Text createEditorField( final Composite parent, final Label label, final String text ) {
    label.setText( text );
    GridData lData = new GridData();
    lData.minimumHeight = 0;
    label.setLayoutData( lData );

    Text editor = new Text( parent, SWT.BORDER );
    editor.setTextLimit( 40 );
    GridData eData = new GridData( GridData.FILL_HORIZONTAL );
    eData.grabExcessHorizontalSpace = true;
    eData.minimumHeight = 0;
    editor.setLayoutData( eData );

    return editor;
  }

  protected void initializeServiceCombo( final Combo combo ) {
    List< String > serviceNames
      = eu.geclipse.batch.Extensions.getRegisteredBatchServiceNames();
    String[] schemeArray
      = serviceNames.toArray( new String[ serviceNames.size() ] );
    combo.setItems( schemeArray );
  }

  protected void updateUI() {
    setPageComplete( isInputValid() );
  }
  
  /**
   * Validates the input of the fields and displays error messages
   * @return true if input is valid, false if invalid
   */
  public boolean isInputValid(){
    boolean ret = true; 
    if ( 0 == this.batchTypeCombo.getItemCount() ) {
      setErrorMessage( Messages.getString( "BatchDefinitionWizardPage.BatchTypeError" ) ); //$NON-NLS-1$      
      ret = false;
    } else if ( 0 == this.hostNameCombo.getText().trim().length() ) {
      setErrorMessage( Messages.getString( "BatchDefinitionWizardPage.HostNameError" ) ); //$NON-NLS-1$
      ret = false;
    } else if ( 0 == this.accountUserNameText.getText().trim().length() ) {
      setErrorMessage( Messages.getString( "BatchDefinitionWizardPage.UserNameError" ) ); //$NON-NLS-1$
      ret = false;
    } else
      this.setErrorMessage( null ); 
    
    return ret;
  }
  
  /**
   * This method will be invoked, when the "Finish" button is pressed.
   *
   * @see BatchCreationWizard#performFinish()
   */
  protected boolean finish( final IFile file ) throws GridModelException{
    BatchConnectionInfo info = null;
    boolean ret = false;
    String batchName;
    String account;
    String batchType;
    int updateInterval;

    if ( 0 < this.batchTypeCombo.getItemCount() ) {
      batchName = this.hostNameCombo.getText().trim();
      account = this.accountUserNameText.getText().trim();
      batchType = this.batchTypeCombo.getText();
      updateInterval = this.intervalSpinner.getSelection();

      // We don't accept empty fields for these.
      if ( batchName.length() == 0 )
        setErrorMessage( Messages.getString( "BatchDefinitionWizardPage.HostNameError" ) ); //$NON-NLS-1$
      else if ( account.length() == 0 )
        setErrorMessage( Messages.getString( "BatchDefinitionWizardPage.UserNameError" ) ); //$NON-NLS-1$
      else {
        info = new BatchConnectionInfo( file );
        info.setConnectionInfo( batchName, account, batchType, updateInterval );
        info.save();
        ret = true;
      }
    }
    return ret;
  }

  /**
   * Provides a default list of computing elements in the Host field.
   */
  private void addComputingElements() {
    ArrayList<AbstractGlueTable> ceTable = 
      GlueQuery.getGlueTable( "GlueCE", "GlueCE", null ); //$NON-NLS-1$ //$NON-NLS-2$
    for ( AbstractGlueTable table : ceTable ) {
      try {
        String hostname = ( String ) table.getFieldByName( "HostName" ); //$NON-NLS-1$
        if ( hostname != null && this.hostNameCombo.indexOf( hostname ) == -1 ) {
          this.hostNameCombo.add( hostname );
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
}
