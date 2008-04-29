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
 *      - Neophytos Theodorou (TODO)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.layout.GridLayout;
import eu.geclipse.batch.ui.internal.Messages;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * Optional page for the create Queue wizard 
 */
public class AddQueueWizardOptionalPage extends WizardPage {
  
  protected Spinner prioritySpin;
  
  protected Spinner maxRunSpin;
  
  protected Spinner maxQueueSpin;
  
  protected Button maxRunUnlimitedButton;
  
  protected Button maxQueueUnlimitedButton;
  
  protected Spinner assignedResourcesSpin;
  
  private Composite mainComp;
  
  private Label priorityLabel;
  
  private Label maxRunLabel;
  
  private Label maxQueueLabel;
  
  private Label assignedResourcesLabel;
  
  /**
   * @param pageName
   */
  protected AddQueueWizardOptionalPage() {
    super( "addQueueOptionalPage" ); //$NON-NLS-1$
    this.setTitle( Messages.getString( "AddQueueOptionalPage.Title" ) ); //$NON-NLS-1$
    this.setDescription( Messages.getString( "AddQueueOptionalPage.Description" ) ); //$NON-NLS-1$
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
   */
  public void createControl( final Composite parent ) {
    this.mainComp = new Composite( parent, SWT.NONE );
    this.mainComp.setLayout( new GridLayout( 3, true ) );
    
    this.priorityLabel = new Label( this.mainComp, SWT.NONE );
    this.priorityLabel.setText( Messages.getString( "AddQueueOptionalPage.Priority" ) );//$NON-NLS-1$
    
    this.prioritySpin = new Spinner( this.mainComp, SWT.NONE );
    this.prioritySpin.setValues( 80, 0, Integer.MAX_VALUE, 0, 10, 10 );
    
    new Label( this.mainComp, SWT.NONE ); //Place holder
        
    this.maxRunLabel = new Label( this.mainComp, SWT.NONE );
    this.maxRunLabel.setText( Messages.getString( "AddQueueOptionalPage.MaxRun" ) );//$NON-NLS-1$
    
    this.maxRunSpin = new Spinner( this.mainComp, SWT.NONE );
    this.maxRunSpin.setValues( 5, 0, Integer.MAX_VALUE, 0, 1, 1 );
    
    this.maxRunUnlimitedButton = new Button( this.mainComp, SWT.CHECK );
    this.maxRunUnlimitedButton.setText( Messages.getString( "AddQueueOptionalPage.Unlimited" ) ); //$NON-NLS-1$
    this.maxRunUnlimitedButton.setSelection( false );
    this.maxRunUnlimitedButton.addSelectionListener( new SelectionAdapter(){
      @SuppressWarnings("unqualified-field-access")
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        maxRunSpin.setEnabled( !( maxRunUnlimitedButton.getSelection() ) );
      }     
    } );
    
    this.maxQueueLabel = new Label( this.mainComp, SWT.NONE );
    this.maxQueueLabel.setText( Messages.getString( "AddQueueOptionalPage.MaxQueue" ) );//$NON-NLS-1$
    
    this.maxQueueSpin = new Spinner( this.mainComp, SWT.NONE );
    this.maxQueueSpin.setValues( 10, 0, Integer.MAX_VALUE, 0, 1, 1 );
    
    this.maxQueueUnlimitedButton = new Button( this.mainComp, SWT.CHECK );
    this.maxQueueUnlimitedButton.setText( Messages.getString( "AddQueueOptionalPage.Unlimited" ) ); //$NON-NLS-1$
    this.maxQueueUnlimitedButton.setSelection( false );
    this.maxQueueUnlimitedButton.addSelectionListener( new SelectionAdapter(){
      @SuppressWarnings("unqualified-field-access")
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        maxQueueSpin.setEnabled( !( maxQueueUnlimitedButton.getSelection() ) );
      }     
    } );
    
    
    this.assignedResourcesLabel = new Label ( this.mainComp, SWT.NONE );
    this.assignedResourcesLabel.setText( Messages.getString( "AddQueueOptionalPage.AssignedResources" ) ); //$NON-NLS-1$
    
    this.assignedResourcesSpin = new Spinner( this.mainComp, SWT.NONE );
    this.assignedResourcesSpin.setValues( 2, 0, Integer.MAX_VALUE, 0, 1, 1 );
    
    setControl( this.mainComp );
  }
  
  protected int getPriority() {
    return this.prioritySpin.getSelection();
  }
  
  protected int getMaxRun() {
    return this.maxRunUnlimitedButton.getSelection()?Integer.MAX_VALUE:this.maxRunSpin.getSelection();
  }
  
  protected int getMaxQueue() {
    return this.maxQueueUnlimitedButton.getSelection()?Integer.MAX_VALUE:this.maxQueueSpin.getSelection();
  }
  
  protected int getAssignedResources() {
    return this.assignedResourcesSpin.getSelection();
  }
  
}
