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

import eu.geclipse.batch.ui.internal.Activator;
import eu.geclipse.batch.ui.internal.Messages;
import eu.geclipse.info.glue.AbstractGlueTable;
import eu.geclipse.info.glue.GlueQuery;
import eu.geclipse.ui.widgets.StoredCombo;

/**
 * The wizard page to move a job. 
 * @author harald
 *
 */
public class MoveJobWizardPage extends WizardPage {
  private static final String HOSTNAME_STRINGS = "hostname_string"; //$NON-NLS-1$

  private Composite mainComp;

  private Label computingElementLabel;

  private Label queueLabel;

  private StoredCombo computingElementCombo;

  private Text queueText;

  /**
   * The default constructor.
   */
  public MoveJobWizardPage() {
    super( "MoveJobWizardPage" ); //$NON-NLS-1$
    this.setTitle( Messages.getString( "MoveJobWizardPage.WindowTitle" ) ); //$NON-NLS-1$
    setDescription( Messages.getString( "MoveJobWizardPage.Description" ) ); //$NON-NLS-1$
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

    this.computingElementLabel = new Label( this.mainComp, SWT.NONE );
    this.computingElementLabel.setText( Messages.getString( "MoveJobWizardPage.ComputingElementName" ) ); //$NON-NLS-1$
    this.computingElementLabel.setLayoutData( lData );

    Activator activator = Activator.getDefault();
    IPreferenceStore preferenceStore = activator.getPreferenceStore();
    this.computingElementCombo = new StoredCombo( this.mainComp, SWT.DROP_DOWN );
    this.computingElementCombo.setPreferences( preferenceStore, HOSTNAME_STRINGS );
    this.computingElementCombo.setLayoutData( gData );
    
    this.computingElementCombo.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        updateUI();
      }
    } );
    
    this.queueLabel = new Label( this.mainComp, SWT.NONE );
    this.queueText = createEditorField( this.mainComp, this.queueLabel, 
                                                  Messages.getString( "MoveJobWizardPage.QueueName" ) ); //$NON-NLS-1$

    this.queueText.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        updateUI();
      }
    } );
    
    addComputingElements();

    setControl( this.mainComp );
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

  protected String getComputingElementName() {
    return this.computingElementCombo.getText().trim();
  }
  
  protected String getQueueName() {
    return this.queueText.getText().trim();
  }

  protected void updateUI() {
    setPageComplete( isInputValid() );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
   */
  @Override
  public boolean isPageComplete() {
    return super.isPageComplete() && this.isInputValid();
  }

  /**
   * Validates the input of the fields and displays error messages
   * @return true if input is valid, false if invalid
   */
  public boolean isInputValid(){
    boolean isValid = true;

    // We don't accept if both fields are empty.
    if ( getComputingElementName().length() == 0 && getQueueName().length() == 0 ) {
      setErrorMessage( Messages.getString( "MoveJobWizardPage.EmptyFieldError" ) ); //$NON-NLS-1$
      isValid = false;
    } else { //Everything is valid
      this.setErrorMessage( null );
    }

    return isValid;
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
        if ( hostname != null && this.computingElementCombo.indexOf( hostname ) == -1 ) {
          this.computingElementCombo.add( hostname );
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

