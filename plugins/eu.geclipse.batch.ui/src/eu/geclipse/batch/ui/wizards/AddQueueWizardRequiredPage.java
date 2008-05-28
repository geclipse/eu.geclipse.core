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
 *      - Neophytos Theodorou (phytosth@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.wizards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.batch.BatchQueueDescription;
import eu.geclipse.batch.model.qdl.BoundaryType;
import eu.geclipse.batch.model.qdl.QdlFactory;
import eu.geclipse.batch.model.qdl.QueueTypeEnumeration;
import eu.geclipse.batch.model.qdl.RangeValueType;
import eu.geclipse.batch.ui.dialogs.AllowedVOsDialog;
import eu.geclipse.batch.ui.internal.Messages;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * Required page for the create Queue wizard
 */
public class AddQueueWizardRequiredPage extends WizardPage {

  // Controls

  // Protected
  protected Text nameText;

  protected Combo typeCombo;

  protected Button enabledButton;  //radio button

  protected Button disabledButton; //radio button

  protected Spinner timeCPUHourSpin;

  protected Spinner timeCPUMinSpin;

  protected Spinner timeWallHourSpin;

  protected Spinner timeWallMinSpin;

  protected Table allowedVoTable;

  // Private
  private Composite mainComp;

  private Label nameLabel;

  private Label typeLabel;

  private Label statusLabel;

  private Label timeCPULabel;

  private Label timeWallLabel;

  private Group statusGroup;

  private Composite tableButtonsComp;

  private Button addVoButton;

  private Button editVoButton;

  private Button deleteVoButton;

  /**
   * Create a new instance of the AddQueueWizardRequiredPage
   */
  protected AddQueueWizardRequiredPage() {
    super( "addQueueRequiredPage" ); //$NON-NLS-1$
    this.setTitle( Messages.getString( "AddQueueRequiredPage.Title" ) ); //$NON-NLS-1$
    this.setDescription( Messages.getString( "AddQueueRequiredPage.Description" ) ); //$NON-NLS-1$
  }

  /**
   * Create the controls to be displayed n the wizard.
   * @param parent The parent widget of the page.
   */
  public void createControl( final Composite parent ) {

    GridData longData, statusData, voButtonData;
    Composite timeComp;

    this.mainComp = new Composite( parent, SWT.NONE );
    this.mainComp.setLayout( new GridLayout( 4, false ) );

    // Queue Name text box
    longData = new GridData( SWT.FILL, SWT.FILL, true, false );
    longData.horizontalSpan = 3;

    this.nameLabel = new Label( this.mainComp, SWT.NONE );
    this.nameLabel.setText( Messages.getString( "AddQueueRequiredPage.Name" ) ); //$NON-NLS-1$

    this.nameText = new Text( this.mainComp, SWT.BORDER );
    this.nameText.setLayoutData( longData );

    this.nameText.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        updateUI();
      }
    } );

    // Queue Type ComboBox
    this.typeLabel = new Label( this.mainComp, SWT.NONE );
    this.typeLabel.setText( Messages.getString( "AddQueueRequiredPage.Type" ) ); //$NON-NLS-1$

    this.typeCombo = new Combo( this.mainComp, SWT.DROP_DOWN );
    this.typeCombo.add( Messages.getString( "AddQueueRequiredPage.Type_Execution" ), 0 ); //$NON-NLS-1$
    this.typeCombo.add( Messages.getString( "AddQueueRequiredPage.Type_Route" ), 1 ); //$NON-NLS-1$
    this.typeCombo.select( 0 );
    this.typeCombo.setLayoutData( longData );

    // Status enable and disable radio buttons

    this.statusGroup = new Group( this.mainComp, SWT.NONE );
    this.statusGroup.setText(  Messages.getString( "AddQueueRequiredPage.Status" )  ); //$NON-NLS-1$
    this.statusGroup.setLayout( new GridLayout( 1, false ) );
    this.statusGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false, 4, 1 ) );

    this.enabledButton = new Button( this.statusGroup, SWT.RADIO );
    this.enabledButton.setText( Messages.getString( "AddQueueRequiredPage.Enabled" ) ); //$NON-NLS-1$
    this.enabledButton.setSelection( true );

    this.disabledButton = new Button( this.statusGroup, SWT.RADIO );
    this.disabledButton.setText( Messages.getString( "AddQueueRequiredPage.Disabled" ) ); //$NON-NLS-1$

//    new Label(this.mainComp, SWT.NONE).setLayoutData( longData ); // Empty Label

    // Maximum Time CPU spinner
    this.timeCPULabel = new Label( this.mainComp, SWT.NONE );
    this.timeCPULabel.setText( Messages.getString( "AddQueueRequiredPage.TimeCPU" ) ); //$NON-NLS-1$

    timeComp = new Composite( this.mainComp, SWT.NONE );
    timeComp.setLayout( new GridLayout( 3, false ) );

    this.timeCPUHourSpin = new Spinner( timeComp, SWT.BORDER );
//    this.timeCPUHourSpin.setValues( 48, 0, 999, 0, 1, 2 );
    this.timeCPUHourSpin.setValues( 172800, 0, 9999999, 0, 1000, 2000 );

//    new Label( timeComp, SWT.NONE ).setText( ":" ); //$NON-NLS-1$
//
//    this.timeCPUMinSpin = new Spinner( timeComp, SWT.NONE );
//    this.timeCPUMinSpin.setValues( 0, 0, 59, 0, 1, 2 );

    // Maximum Time wall spinner
    this.timeWallLabel = new Label( this.mainComp, SWT.NONE );
    this.timeWallLabel.setText( Messages.getString( "AddQueueRequiredPage.TimeWall" ) ); //$NON-NLS-1$

    timeComp = new Composite( this.mainComp, SWT.NONE );
    timeComp.setLayout( new GridLayout( 3, false ) );

    this.timeWallHourSpin = new Spinner( timeComp, SWT.BORDER );
    this.timeWallHourSpin.setValues( 172800, 0, 9999999, 0, 1000, 2000 );
//    this.timeWallHourSpin.setValues( 72, 0, 999, 0, 1, 2 );

//    new Label( timeComp, SWT.NONE ).setText( ":" ); //$NON-NLS-1$
//
//    this.timeWallMinSpin = new Spinner( timeComp, SWT.NONE );
//    this.timeWallMinSpin.setValues( 0, 0, 59, 0, 1, 2 );

    GridLayout compLayout = new GridLayout(2, false);
    compLayout.marginHeight = 0;
    compLayout.marginWidth = 0;

    Composite tableComp = new Composite( this.mainComp, SWT.NONE );
    // Create the table that holds the result
    this.allowedVoTable = new Table( tableComp, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER );
    this.allowedVoTable.setHeaderVisible( true );
    this.allowedVoTable.setLinesVisible( true );
    this.allowedVoTable.addSelectionListener( new SelectionAdapter() {
      @SuppressWarnings("unqualified-field-access")
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        editVoButton.setEnabled( true );
        deleteVoButton.setEnabled( true );
      }
    } );

    GridData gd = new GridData( SWT.FILL, SWT.FILL, true, true );
    this.allowedVoTable.setLayoutData( gd );

    tableComp.setLayout( compLayout );
    tableComp.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 4, 1 ) );

    TableColumn hostColumn = new TableColumn( this.allowedVoTable, SWT.LEFT );
    hostColumn.setText( Messages.getString( "AddQueueRequiredPage.AllowedVos" ) );//$NON-NLS-1$
    hostColumn.setWidth( 250 );

    GridLayout buttonCompLayout = new GridLayout();
    buttonCompLayout.marginHeight = 0;
    buttonCompLayout.marginWidth = 0;

    this.tableButtonsComp = new Composite( tableComp, SWT.NONE );
    this.tableButtonsComp.setLayout( buttonCompLayout );
    this.tableButtonsComp.setLayoutData( new GridData( SWT.FILL, SWT.FILL, false, false ) );

    voButtonData = new GridData(SWT.FILL, SWT.FILL, true, false);

    this.addVoButton = new Button( this.tableButtonsComp, SWT.PUSH );
    this.addVoButton.setText( Messages.getString( "AddQueueRequiredPage.AddVo" ) ); //$NON-NLS-1$
    this.addVoButton.setLayoutData( voButtonData );
    this.addVoButton.addSelectionListener( new SelectionAdapter(){
      @SuppressWarnings("unqualified-field-access")
      @Override
      public void widgetSelected (final SelectionEvent e ){
        AllowedVOsDialog dialog =
          new AllowedVOsDialog( getShell(),
                                Messages.getString( "AddQueueRequiredPage.AddNewVOTitle" ) ); //$NON-NLS-1$
        if ( dialog.open() == Window.OK ) {
          new TableItem( allowedVoTable, SWT.NONE ).setText( dialog.getValue() );
          //allowedVoTable.clearAll();
        }
      }
    });

    this.editVoButton = new Button( this.tableButtonsComp, SWT.PUSH );
    this.editVoButton.setText( Messages.getString( "AddQueueRequiredPage.EditVo" ) ); //$NON-NLS-1$
    this.editVoButton.setLayoutData( voButtonData );
    this.editVoButton.setEnabled( false );
    this.editVoButton.addSelectionListener( new SelectionAdapter(){
      @SuppressWarnings("unqualified-field-access")
      @Override
      public void widgetSelected( final SelectionEvent e ){
        TableItem [] selectedItems = allowedVoTable.getSelection();
        if ( selectedItems.length == 1 ){
          AllowedVOsDialog dialog =
            new AllowedVOsDialog( getShell(),
                                  Messages.getString( "AddQueueRequiredPage.AddNewVOTitle" ) ); //$NON-NLS-1$

          dialog.setInput( selectedItems[0].getText() );

          if ( dialog.open() == Window.OK )
            selectedItems[0].setText( dialog.getValue() );
        }
      }
    });

    this.deleteVoButton = new Button( this.tableButtonsComp, SWT.PUSH );
    this.deleteVoButton.setText( Messages.getString( "AddQueueRequiredPage.DeleteVo" ) ); //$NON-NLS-1$
    this.deleteVoButton.setLayoutData( voButtonData );
    this.deleteVoButton.setEnabled( false );
    this.deleteVoButton.addSelectionListener( new SelectionAdapter(){
      @SuppressWarnings("unqualified-field-access")
      @Override
      public void widgetSelected( final SelectionEvent e ){
        int [] selectedItems = allowedVoTable.getSelectionIndices();
        if ( selectedItems.length > 0 ) {
          allowedVoTable.remove( selectedItems );
        }
      }
    });

    setControl( this.mainComp );
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

    if ( 0 == this.nameText.getText().length() ) {
      this.setErrorMessage( Messages.getString( "AddQueueRequiredPage.Error.QueueName" ) ); //$NON-NLS-1$
      isValid = false;
    }
    else { //Everything is valid
      this.setErrorMessage( null );
    }

    return isValid;
  }

  protected List<String> getVONames(){
    ArrayList<String> voList = null;

    if ( 0 != this.allowedVoTable.getItemCount() ) {
      voList = new ArrayList<String>();
      for( TableItem item:this.allowedVoTable.getItems() )
        voList.add( item.getText() );
    }

    return voList;
  }

//  protected String getTimeCPU() {
//    return String.format( "%02d:%02d:00", //$NON-NLS-1$
//                          new Integer( this.timeCPUHourSpin.getSelection() ),
//                          new Integer( this.timeCPUMinSpin.getSelection() ) );
//  }
//
//  protected String getTimeWall() {
//    return String.format( "%02d:%02d:00", //$NON-NLS-1$
//                          new Integer( this.timeWallHourSpin.getSelection() ),
//                          new Integer( this.timeWallMinSpin.getSelection() ) );
//  }


  protected int getTimeCPU() {
    return this.timeCPUHourSpin.getSelection();
  }

  protected int getTimeWall() {
    return this.timeWallHourSpin.getSelection();
  }


  /**
   * Creates a queue if user have the rights, if not an error dialog is displayed
   * @param qdlFile
   * @return ret
   *
   */
  protected boolean finish(final IFile qdlFile) {

    boolean ret = false;
    String str;
    BatchQueueDescription batchQueueDescription = new BatchQueueDescription(qdlFile);
    try {
      batchQueueDescription.setQueueName( this.nameText.getText().trim());

      str = this.typeCombo.getText();
      if ( str.equals( Messages.getString( "AddQueueRequiredPage.Type_Execution" ) ) ) //$NON-NLS-1$
        batchQueueDescription.queueType( QueueTypeEnumeration.EXECUTION );
      else
        batchQueueDescription.queueType( QueueTypeEnumeration.ROUTE );

      if ( this.enabledButton.getSelection() == true) {
        batchQueueDescription.enableQueue( true );
      }
      else {
        batchQueueDescription.enableQueue( false );
      }

      /* Set Max CPU Time Limit */
      RangeValueType rangeValueType = QdlFactory.eINSTANCE.createRangeValueType();
      BoundaryType boundaryType = QdlFactory.eINSTANCE.createBoundaryType();
      boundaryType.setValue( getTimeCPU() );
      rangeValueType.setUpperBoundedRange( boundaryType );

      batchQueueDescription.getRoot().getQueue().setCPUTimeLimit( rangeValueType );

      /* Set Max Time Wall Limit */
      rangeValueType = QdlFactory.eINSTANCE.createRangeValueType();
      boundaryType = QdlFactory.eINSTANCE.createBoundaryType();
      boundaryType.setValue( getTimeWall() );

      rangeValueType.setUpperBoundedRange( boundaryType );
      batchQueueDescription.getRoot().getQueue().setWallTimeLimit( rangeValueType );

      if (this.allowedVoTable.getItemCount() > 0 ) {
        Collection <String> allowedVoList = new ArrayList<String>();
        for(int i=0; i < this.allowedVoTable.getItemCount(); i++) {
          allowedVoList.add( this.allowedVoTable.getItem( i ).getText() );
        }
        batchQueueDescription.setAllowedVirtualOrganizations( allowedVoList );

      }
      batchQueueDescription.save(qdlFile);
      ret = true;
    } catch( Exception excp ) {
      // Display error dialog
      ProblemDialog.openProblem( this.getShell(),
                                    Messages.getString( "AddQueueWizard.error_manipulate_title" ),  //$NON-NLS-1$
                                    Messages.getString( "AddQueueWizard.error_manipulate_message" ), //$NON-NLS-1$
                                    excp );
    }
    return ret;
  }

}
