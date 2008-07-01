/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.pages.sections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import eu.geclipse.jsdl.model.base.ApplicationType;
import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.base.JobDescriptionType;
import eu.geclipse.jsdl.model.base.JsdlFactory;
import eu.geclipse.jsdl.model.base.JsdlPackage;
import eu.geclipse.jsdl.model.posix.ArgumentType;
import eu.geclipse.jsdl.model.posix.DocumentRoot;
import eu.geclipse.jsdl.model.posix.EnvironmentType;
import eu.geclipse.jsdl.model.posix.FileNameType;
import eu.geclipse.jsdl.model.posix.POSIXApplicationType;
import eu.geclipse.jsdl.model.posix.PosixFactory;
import eu.geclipse.jsdl.model.posix.PosixPackage;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.dialogs.ArgumentsDialog;
import eu.geclipse.jsdl.ui.internal.dialogs.EnvironmentVarDialog;
import eu.geclipse.jsdl.ui.internal.pages.FormSectionFactory;
import eu.geclipse.jsdl.ui.internal.pages.Messages;
import eu.geclipse.jsdl.ui.providers.FeatureContentProvider;
import eu.geclipse.jsdl.ui.providers.FeatureLabelProvider;


/**
 * @author nloulloud
 *
 */
public class PosixApplicationSection extends JsdlFormPageSection {
  
  private static final int WIDGET_HEIGHT = 100;
  protected Text txtExecutable = null;
  protected Text txtPosixName = null;
  protected Text txtInput = null;
  protected Text txtOutput = null;
  protected Text txtError = null;  
  protected Button btnArgAdd = null;
  protected Button btnArgEdit = null;
  protected Button btnArgDel = null;
  protected Button btnEnVarAdd = null;
  protected Button btnEnVarEdit = null;
  protected Button btnEnVarDel = null;
  protected Label lblPosixName = null;
  protected Label lblExecutable = null;
  protected Label lblArgument = null;
  protected Label lblInput = null;
  protected Label lblOutput = null;
  protected Label lblError = null;
  protected Label lblEnvironment = null;
  protected Table tblEnvironment = null;
  protected TableViewer environmentViewer = null;
  protected Table tblArgument = null;
  protected TableViewer argumentViewer = null;
  protected Object value = null;
  protected JobDefinitionType jobDefinitionType = null;
  protected JobDescriptionType jobDescriptionType = null;
  protected ApplicationType applicationType = null;
  protected DocumentRoot documentRoot = PosixFactory.eINSTANCE.createDocumentRoot();
  protected POSIXApplicationType posixApplicationType ;
  protected EnvironmentType environmentType = PosixFactory.eINSTANCE.createEnvironmentType();
  protected ArgumentType argumentType = PosixFactory.eINSTANCE.createArgumentType();
  
  private TableColumn column;
  private Composite containerComposite = null;
  
  
  /**
   * @param formPage The FormPage that contains this Section
   * @param parent The parent composite.
   * @param toolkit The parent Form Toolkit.
   */
  public PosixApplicationSection( final Composite parent, final FormToolkit toolkit ){    
    
    this.containerComposite = parent;
    createSection( parent, toolkit );
    
  }
  
  
  /*
   * Create the Posix Application Section which includes the following:
   * -Executable (String) -Argument (String) -Input (String) -Output (String)
   * -Error (String) -Environment (String)
   */
  private void createSection( final Composite parent,
                              final FormToolkit toolkit ){
    
    
    String sectionTitle = Messages.getString( "JobApplicationPage_PosixApplicationtitle" ); //$NON-NLS-1$
    String sectionDescripiton = Messages.getString( "JobApplicationPage_PosixApplicationDescription" ); //$NON-NLS-1$
    GridData gd;
    
    Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                                                   parent,
                                                                   sectionTitle,
                                                                   sectionDescripiton,
                                                                   4 );
    gd = new GridData();
    gd.grabExcessHorizontalSpace = true;
    gd.verticalAlignment = GridData.CENTER;
    gd.verticalSpan = 1;
    gd.horizontalSpan = 3;
    gd.widthHint = 330;
    
    /* ===================== Posix Name Widget ======================= */
    this.lblPosixName = toolkit.createLabel( client,
                                             Messages.getString( "JobApplicationPage_PosixName" ) ); //$NON-NLS-1$
    this.txtPosixName = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtPosixName.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        checkPosixApplicationElement();
                
        if (!PosixApplicationSection.this.txtPosixName.getText().equals( EMPTY_STRING )){
                            
          PosixApplicationSection.this.posixApplicationType.setName( PosixApplicationSection
                                                                                        .this.txtPosixName.getText() );
        }
        else{
          
          PosixApplicationSection.this.posixApplicationType.setName( null );
        }
        contentChanged();
        
      }
    } );

    this.txtPosixName.setLayoutData( gd );
    
    /* =========================== Executable Widget ======================== */
    gd = new GridData();
    gd.horizontalSpan = 1;
    this.lblExecutable = toolkit.createLabel( client,
                                              Messages.getString( "JobApplicationPage_Executable" ) ); //$NON-NLS-1$
    this.lblExecutable.setLayoutData( gd );
    this.txtExecutable = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtExecutable.addModifyListener( new ModifyListener() {      
      FileNameType fileName = null;
      public void modifyText( final ModifyEvent e ) {
        checkPosixApplicationElement();
        if ( !PosixApplicationSection.this.txtExecutable.getText().equals( EMPTY_STRING ) ) { 
          if (null == this.fileName) {
            this.fileName = PosixFactory.eINSTANCE.createFileNameType();
          }
          this.fileName.setValue( PosixApplicationSection.this.txtExecutable.getText() );
          this.fileName = (FileNameType) checkProxy( this.fileName );
          PosixApplicationSection.this.posixApplicationType.setExecutable(this.fileName);
        }
      else{
        
        if ( null != this.fileName){
          this.fileName = (FileNameType) checkProxy( this.fileName );
          this.fileName = null;
        }
        PosixApplicationSection.this.posixApplicationType.setExecutable(null);
      }
      contentChanged();
      
      }
    } );

    
    
    gd = new GridData();
    gd.widthHint = 330;
    gd.horizontalSpan = 3;
    gd.grabExcessHorizontalSpace = true;
    gd.verticalAlignment = GridData.CENTER;
    gd.verticalSpan = 1;
    this.txtExecutable.setLayoutData( gd );
    
    /* ============================= Argument Widget ======================== */
    gd = new GridData();
    gd.verticalSpan = 2;
    gd.horizontalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    this.lblArgument = toolkit.createLabel(client, Messages.getString( "JobApplicationPage_Argument" ) ); //$NON-NLS-1$
    this.lblArgument.setLayoutData( gd );
    gd = new GridData();
    gd.horizontalAlignment = GridData.FILL;
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 3;
    gd.horizontalSpan = 1;
    gd.widthHint = 250;
    gd.heightHint = WIDGET_HEIGHT;
    
    this.argumentViewer = new TableViewer( client, SWT.BORDER | SWT.FULL_SELECTION );
    this.tblArgument = this.argumentViewer.getTable();
    this.tblArgument.setHeaderVisible( true );
    this.argumentViewer.setContentProvider( new FeatureContentProvider() );
    this.argumentViewer.setLabelProvider( new FeatureLabelProvider() );
    this.column = new TableColumn( this.tblArgument, SWT.NONE );
    this.column.setText( "File System Name" ); //$NON-NLS-1$
    this.column.setWidth( 150 );
    this.column = new TableColumn( this.tblArgument, SWT.NONE );
    this.column.setText( "Value" ); //$NON-NLS-1$
    this.column.setWidth( 100 );

    this.argumentViewer.addSelectionChangedListener( new ISelectionChangedListener() {

      public void selectionChanged( final SelectionChangedEvent event ) {
        updateButtons( ( TableViewer )event.getSource() );
      }
    } );
    this.tblArgument.setData( FormToolkit.KEY_DRAW_BORDER );
    this.tblArgument.setLayoutData( gd );
    
    /* Create "Add" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    this.btnArgAdd = toolkit.createButton( client,
                                           Messages.getString( "JsdlEditor_AddButton" ), //$NON-NLS-1$
                                           SWT.PUSH );
    this.btnArgAdd.addSelectionListener( new SelectionListener() {

      public void widgetSelected( final SelectionEvent event ) {
        handleArguments( Messages.getString( "JobApplicationPage_ArgumentDialog" ), //$NON-NLS-1$
                         ( Button )event.getSource() );
        
       addNewArgument( PosixApplicationSection.this.argumentViewer, PosixApplicationSection.this.value );
      }

      public void widgetDefaultSelected( final SelectionEvent event ) {
        // Do Nothing - Required method
      }
    } );
    this.btnArgAdd.setLayoutData( gd );
    /* Create "Edit" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    this.btnArgEdit = toolkit.createButton( client,
                                            Messages.getString( "JsdlEditor_EditButton" ), //$NON-NLS-1$
                                            SWT.PUSH );
    
    this.btnArgEdit.addSelectionListener( new SelectionListener() {

      public void widgetSelected( final SelectionEvent event ) {
        handleArguments( Messages.getString( "JobApplicationPage_ArgumentDialog" ), //$NON-NLS-1$
                         ( Button )event.getSource() );
        
        performEdit( PosixApplicationSection.this.argumentViewer,
                                                 PosixApplicationSection.this.value );
      }

      public void widgetDefaultSelected( final SelectionEvent event ) {
        // Do Nothing - Required method
      }
    } );
    
    this.btnArgEdit.setEnabled( false );
    this.btnArgEdit.setLayoutData( gd );
    /* Create "Remove" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    gd.verticalAlignment = GridData.BEGINNING;
    this.btnArgDel = toolkit.createButton( client,
                                           Messages.getString( "JsdlEditor_RemoveButton" ), //$NON-NLS-1$
                                           SWT.PUSH );
    this.btnArgDel.setEnabled( false );
    this.btnArgDel.addSelectionListener(new SelectionListener() {

      public void widgetSelected( final SelectionEvent event ) {        
        performDelete( PosixApplicationSection.this.argumentViewer );
        }

      public void widgetDefaultSelected( final SelectionEvent event ) {
          // Do Nothing - Required method
      }
    });
    
    this.btnArgDel.setLayoutData( gd );
    
    /* ============================= Input Widget =========================== */
    gd = new GridData();
    gd.horizontalSpan = 1;
    this.lblInput = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_Input" ) ); //$NON-NLS-1$
    this.lblInput.setLayoutData( gd );
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.horizontalSpan = 3;
    gd.widthHint = 330;
    this.txtInput = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    
    this.txtInput.addModifyListener( new ModifyListener() {
      FileNameType fileName = null;
      public void modifyText( final ModifyEvent e ) {
      
        checkPosixApplicationElement();
        if ( !PosixApplicationSection.this.txtInput.getText().equals( EMPTY_STRING ) ) {
          if (null == this.fileName) {
            this.fileName = PosixFactory.eINSTANCE.createFileNameType();
          }
          this.fileName.setValue( PosixApplicationSection.this.txtInput.getText() );
          this.fileName = (FileNameType) checkProxy( this.fileName );
          PosixApplicationSection.this.posixApplicationType.setInput(this.fileName);
        }
        else{
          if ( null != this.fileName){
            this.fileName = (FileNameType) checkProxy( this.fileName );
            this.fileName = null;
          }
          PosixApplicationSection.this.posixApplicationType.setInput(null);
        }
        contentChanged();
        
      }
    } ); 

    this.txtInput.setLayoutData( gd );
    
    /* ============================= Output Widget =========================== */
    gd = new GridData();
    this.lblOutput = toolkit.createLabel( client,
                                          Messages.getString( "JobApplicationPage_Output" ) ); //$NON-NLS-1$
    this.lblOutput.setLayoutData( gd );
    this.txtOutput = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtOutput.addModifyListener( new ModifyListener() {
      FileNameType fileName = null;
      public void modifyText( final ModifyEvent e ) {
        checkPosixApplicationElement();
        if ( !PosixApplicationSection.this.txtOutput.getText().equals( EMPTY_STRING ) ) { 
          if (null == this.fileName) {
            this.fileName = PosixFactory.eINSTANCE.createFileNameType();
          }
          this.fileName.setValue( PosixApplicationSection.this.txtOutput.getText() );
          this.fileName = (FileNameType) checkProxy( this.fileName );
          PosixApplicationSection.this.posixApplicationType.setOutput(this.fileName);
        }
        else{
          if ( null != this.fileName){
            this.fileName = (FileNameType) checkProxy( this.fileName );
            this.fileName = null;
          }
          PosixApplicationSection.this.posixApplicationType.setOutput(null);
        }
        contentChanged();
        
      }
    } ); 

    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.horizontalSpan = 3;
    gd.widthHint = 330;
    this.txtOutput.setLayoutData( gd );
    
    /* ============================= Error Widget =========================== */
    gd = new GridData();
    this.lblError = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_Error" ) ); //$NON-NLS-1$
    this.lblError.setLayoutData( gd );
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.horizontalSpan = 3;
    gd.widthHint = 330;
    this.txtError = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtError.addModifyListener( new ModifyListener() {   
      FileNameType fileName = null;
      public void modifyText( final ModifyEvent e ) {
        checkPosixApplicationElement();
        if ( !PosixApplicationSection.this.txtError.getText().equals( EMPTY_STRING ) ) {
          if (null == this.fileName) {
            this.fileName = PosixFactory.eINSTANCE.createFileNameType();
          }
          this.fileName.setValue( PosixApplicationSection.this.txtError.getText() );
          this.fileName = (FileNameType) checkProxy( this.fileName );
          PosixApplicationSection.this.posixApplicationType.setError(this.fileName);
        }
        else{
          if ( null != this.fileName){
            this.fileName = (FileNameType) checkProxy( this.fileName );
            this.fileName = null;
          }
          PosixApplicationSection.this.posixApplicationType.setError(null);
        }
        contentChanged();
        
      }
    } );

    this.txtError.setLayoutData( gd );
    
    /* ======================= Environment Widget =========================== */
    gd = new GridData();
    gd.verticalSpan = 2;
    gd.horizontalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    this.lblEnvironment = toolkit.createLabel( client,
                                               Messages.getString( "JobApplicationPage_Environment" ) ); //$NON-NLS-1$
    this.lblEnvironment.setLayoutData( gd );
    gd = new GridData();
    gd.horizontalAlignment = GridData.FILL;
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 3;
    gd.horizontalSpan = 1;
    gd.widthHint = 250;
    gd.heightHint = WIDGET_HEIGHT;
    this.environmentViewer = new TableViewer( client, SWT.BORDER | SWT.FULL_SELECTION );
    this.tblEnvironment = this.environmentViewer.getTable();
    this.tblEnvironment.setHeaderVisible( true );
    this.environmentViewer.setContentProvider( new FeatureContentProvider() );
    this.environmentViewer.setLabelProvider( new FeatureLabelProvider() );
    this.column = new TableColumn( this.tblEnvironment, SWT.NONE );
    this.column.setText( "Name" ); //$NON-NLS-1$
    this.column.setWidth( 60 );
    this.column = new TableColumn( this.tblEnvironment, SWT.NONE );
    this.column.setText( "File System Name" ); //$NON-NLS-1$
    this.column.setWidth( 130 );
    this.column = new TableColumn( this.tblEnvironment, SWT.NONE );
    this.column.setText( "Value" ); //$NON-NLS-1$
    this.column.setWidth( 60 );
    
    this.environmentViewer.addSelectionChangedListener( new ISelectionChangedListener()
    {

      public void selectionChanged( final SelectionChangedEvent event ) {
        updateButtons( ( TableViewer )event.getSource() );
      }
    } );
    this.tblEnvironment.setData( FormToolkit.KEY_DRAW_BORDER );
    this.tblEnvironment.setLayoutData( gd );
    
    /* Create "Add" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    
    this.btnEnVarAdd = toolkit.createButton( client,
                                             Messages.getString( "JsdlEditor_AddButton" ), //$NON-NLS-1$
                                             SWT.BUTTON1 );
    
    this.btnEnVarAdd.addSelectionListener( new SelectionListener() {

      public void widgetSelected( final SelectionEvent event ) {
        handleEnvironmentVar( Messages.getString( "JobApplicationPage_EnvironmentDialog" ), //$NON-NLS-1$
                              ( Button )event.getSource() );
        
        addNewEnvVariable( PosixApplicationSection.this.environmentViewer, PosixApplicationSection.this.value );
      }

      public void widgetDefaultSelected( final SelectionEvent event ) {
        // Do Nothing - Required method
      }
    } );
    
    this.btnEnVarAdd.setLayoutData( gd );
    /* Create "Edit" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    this.btnEnVarEdit = toolkit.createButton( client,
                                              Messages.getString( "JsdlEditor_EditButton" ), //$NON-NLS-1$
                                              SWT.PUSH );
    
    this.btnEnVarEdit.addSelectionListener( new SelectionListener() {

      public void widgetSelected( final SelectionEvent event ) {
        handleEnvironmentVar( Messages.getString( "JobApplicationPage_EnvironmentDialog" ), //$NON-NLS-1$
                              ( Button )event.getSource() );
        
        performEdit( PosixApplicationSection.this.environmentViewer, PosixApplicationSection.this.value );
        
      }

      public void widgetDefaultSelected( final SelectionEvent event ) {
        // Do Nothing - Required method
      }
    } );
    
    this.btnEnVarEdit.setEnabled( false );
    this.btnEnVarEdit.setLayoutData( gd );
    /* Create "Remove" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    gd.verticalAlignment = GridData.BEGINNING;
    this.btnEnVarDel = toolkit.createButton( client,
                                             Messages.getString( "JsdlEditor_RemoveButton" ), //$NON-NLS-1$
                                             SWT.PUSH );
    this.btnEnVarDel.setEnabled( false );
    this.btnEnVarDel.addSelectionListener(new SelectionListener() {

      public void widgetSelected( final SelectionEvent event ) {        
        performDelete( PosixApplicationSection.this.environmentViewer );
      }

      public void widgetDefaultSelected( final SelectionEvent event ) {
          // Do Nothing - Required method
      }
    });
    this.btnEnVarDel.setLayoutData( gd );
    toolkit.paintBordersFor( client );
    
  }
  
  
  protected void updateButtons( final TableViewer tableViewer ) {
    
    ISelection selection = tableViewer.getSelection();
    boolean selectionAvailable = !selection.isEmpty();
    
    if( tableViewer == this.argumentViewer ) {
      
      this.btnArgAdd.setEnabled( true );
      this.btnArgEdit.setEnabled( selectionAvailable );
      this.btnArgDel.setEnabled( selectionAvailable );
      
    } else {
      
      this.btnEnVarAdd.setEnabled( true );
      this.btnEnVarEdit.setEnabled( selectionAvailable );
      this.btnEnVarDel.setEnabled( selectionAvailable );
      
    } // end else
    
  } // End updateButtons
  
  
  protected void handleArguments( final String dialogTitle, final Button button ) {
    
    this.value = null;
    
    ArgumentsDialog dialog = new ArgumentsDialog( this.containerComposite.getShell(),
                                                  dialogTitle,
                                                  this.jobDefinitionType);
    
       
    if ( button == this.btnArgEdit ) {
      IStructuredSelection structSelection = ( IStructuredSelection )this.argumentViewer.getSelection();
      ArgumentType argType = ( ArgumentType )structSelection.getFirstElement();
      
      dialog.setInput( argType );

      
    }
    
    if( dialog.open() != Window.OK ) {
      return;
    } // end if ( dialog.open() )
  
    this.value = dialog.getValue();
    
  } // end void handleArguments
  
  

  protected void handleEnvironmentVar( final String dialogTitle, final Button button ) {
    
    this.value = null;
    
    EnvironmentVarDialog dialog = new EnvironmentVarDialog( this.containerComposite.getShell(),
                                                            dialogTitle,
                                                            this.jobDefinitionType);
    
       
    if ( button == this.btnEnVarEdit ) {
      IStructuredSelection structSelection = ( IStructuredSelection )this.environmentViewer.getSelection();
      EnvironmentType envType = ( EnvironmentType )structSelection.getFirstElement();
      
      dialog.setInput( envType );

      
    }
    
    if( dialog.open() != Window.OK ) {
      return;
    } // end if ( dialog.open() )
  
    this.value = dialog.getValue();
    
  } // end void handleArguments
  
  
//  protected void contentChanged() {
//    
//    if( this.isNotifyAllowed ) {
//      fireNotifyChanged( null );
//    }
//  }
  
  
  /**
   * @param jobDefinition The JSDL Job Definition element.
   */
  public void setInput( final JobDefinitionType jobDefinition ) {
    
    this.adapterRefreshed = true;
    if( null != jobDefinition ) {
      this.jobDefinitionType = jobDefinition;
      
      TreeIterator<EObject> iterator = this.jobDefinitionType.eAllContents();
      
      while ( iterator.hasNext (  )  )  {  
        
        EObject testType = iterator.next();         
        
        if (testType instanceof JobDescriptionType) {
          this.jobDescriptionType = (JobDescriptionType) testType;
        }
        else if (testType instanceof ApplicationType) {
          this.applicationType = (ApplicationType) testType;
           
        }
        else if ( testType instanceof POSIXApplicationType ) {
          this.posixApplicationType = (POSIXApplicationType) testType;
          this.posixApplicationType.eAdapters().add( this );
         
        } 
        
      }
      
      fillFields();
    }
    
  }
  
  
  
  /* 
   * If the POSIX Application Element is set, check for any possible contents which may
   * be set. If none of the above are true, then delete the Posix Application elemet from it's
   * container (ApplicationType).
   */
  @Override
  public void notifyChanged(final Notification msg){
     
    if ( this.isNotifyAllowed ){
      if ( null != this.posixApplicationType && this.posixApplicationType.eContents().size() == 0) {
          EcoreUtil.remove( this.posixApplicationType );
          this.posixApplicationType = null;
      }
    }

  }
  
  
  private void fillFields() {
    
    this.isNotifyAllowed = false;
    
    if( null != this.posixApplicationType ) {
      if( null != this.posixApplicationType.getName() ){
        this.txtPosixName.setText( this.posixApplicationType.getName() );
      }else{
        this.txtPosixName.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getExecutable() ) {
        this.txtExecutable.setText( this.posixApplicationType.getExecutable().getValue() );
      }else{
        this.txtExecutable.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getInput() ) {
        this.txtInput.setText( this.posixApplicationType.getInput().getValue() );
      }else{
        this.txtInput.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getOutput() ) {
        this.txtOutput.setText( this.posixApplicationType.getOutput().getValue() );
      }else{
        this.txtOutput.setText( EMPTY_STRING );
      }
      if ( null != this.posixApplicationType.getError() ) {
        this.txtError.setText( this.posixApplicationType.getError().getValue() );
      }else{
        this.txtError.setText( EMPTY_STRING );
      }
      
      this.argumentViewer.setInput( this.posixApplicationType.getArgument() );
      this.environmentViewer.setInput( this.posixApplicationType.getEnvironment() );
      
    }
    this.isNotifyAllowed = true;
    
    if( this.adapterRefreshed ) {
      this.adapterRefreshed = false;
    }
    
  }
  
  
  
  /*
   * Check if the EObject is lazy loaded.
   */
  protected EObject checkProxy( final EObject refEObject ) {
    
    EObject eObject = refEObject;
    
    if (eObject != null && eObject.eIsProxy() ) {
     
      eObject =  EcoreUtil.resolve( eObject, this.posixApplicationType );
    }
        
    return eObject;
    
  }
  
  
  protected void checkApplicationElement() {
    
    EStructuralFeature eStructuralFeature = this.jobDescriptionType.eClass()
          .getEStructuralFeature( JsdlPackage.JOB_DESCRIPTION_TYPE__APPLICATION );
    
    /*
     * Check if the Application element is not set. If not set then set it to its 
     * container (JobDescriptionType).
     */
    if (!this.jobDescriptionType.eIsSet( eStructuralFeature )) {      
      if ( null == this.applicationType ) {
        this.applicationType = JsdlFactory.eINSTANCE.createApplicationType();
      }
      this.jobDescriptionType.eSet( eStructuralFeature, this.applicationType );
    }
  }


  
  protected void checkPosixApplicationElement() {
    
    EStructuralFeature eStructuralFeature = this.documentRoot.eClass()
    .getEStructuralFeature( PosixPackage.DOCUMENT_ROOT__POSIX_APPLICATION );
    
    checkApplicationElement();
        
    if ( !this.applicationType.eIsSet( eStructuralFeature ) ){  
      if ( null == this.posixApplicationType){
        this.posixApplicationType = PosixFactory.eINSTANCE.createPOSIXApplicationType();
        Collection<POSIXApplicationType> collection = new ArrayList<POSIXApplicationType>();
        collection.add( this.posixApplicationType );
//        this.applicationType = (ApplicationType) checkProxy( this.applicationType );
        this.applicationType.eSet( eStructuralFeature, collection );
      }      

    } 
  }
  
  
  
  /**
   * Add a new Argument element to the associated Table Viewers input.
   * 
   * @param tableViewer The {@link TableViewer} in which the new element will be added.
   * @param innerValue The new element that will be added.
   */
  @SuppressWarnings("unchecked")
  protected void addNewArgument ( final TableViewer tableViewer, final Object innerValue ) {
    
    boolean elementExists = false;
    String newElement;
        
    if ( innerValue == null ) {
      return;
    }
    
    EList <ArgumentType> newInputList = ( EList<ArgumentType> )tableViewer.getInput(); 
     
    if (newInputList == null ) {
      newInputList = new BasicEList<ArgumentType>();
    }
        

    
    /* Check if PosixApplication Element Exists */
    checkPosixApplicationElement();
          
    this.argumentType = PosixFactory.eINSTANCE.createArgumentType();
    this.argumentType = (ArgumentType) innerValue;
    newElement = this.argumentType.getValue();
    
    Iterator<ArgumentType> iter = newInputList.iterator();
    
    while ( iter.hasNext() && !elementExists ){
      elementExists = iter.next().getValue().equals( newElement );
    }    
     
    if (!elementExists){
      newInputList.add( this.argumentType );
        
      /* Add the Argument to PosixApplication */
      this.posixApplicationType.getArgument().addAll( newInputList );
      tableViewer.setInput( this.posixApplicationType.getArgument() );
    
      tableViewer.refresh();   
      contentChanged();
      
    } else {
      MessageDialog.openError( tableViewer.getControl().getShell(),
                              Messages.getString( "Arguments_DuplicateEntryDialog_Title" ), //$NON-NLS-1$
                              Messages.getString( "Arguments_New_DuplicateEntryDialog_Message" ) ); //$NON-NLS-1$
    }
    newInputList = null;
  }
  
  
  /**
   * Add a new Argument element to the associated Table Viewers input.
   * 
   * @param tableViewer The {@link TableViewer} in which the new element will be added.
   * @param innerValue The new element that will be added.
   */
  @SuppressWarnings("unchecked")
  protected void addNewEnvVariable ( final TableViewer tableViewer, final Object innerValue ) {
    
    boolean elementExists = false;
    String newElement;
        
    if ( innerValue == null ) {
      return;
    }
    
    EList <EnvironmentType> newInputList = ( EList<EnvironmentType> )tableViewer.getInput(); 
     
    if (newInputList == null ) {
      newInputList = new BasicEList<EnvironmentType>();
    }
        
    /* Check if PosixApplication Element Exists */
    checkPosixApplicationElement();

    this.environmentType = PosixFactory.eINSTANCE.createEnvironmentType();
    this.environmentType = (EnvironmentType) innerValue;
    newElement = this.environmentType.getValue();
        
    Iterator<EnvironmentType> iter = newInputList.iterator();
    
    while ( iter.hasNext() && !elementExists ){
      elementExists = iter.next().getValue().equals( newElement );
    }    
     
    if (!elementExists){
      newInputList.add( this.environmentType );
        
      /* Add the Argument to PosixApplication */
      this.posixApplicationType.getEnvironment().addAll( newInputList );
      tableViewer.setInput( this.posixApplicationType.getEnvironment() );
    
      tableViewer.refresh();   
      contentChanged();
      
    } else {
      MessageDialog.openError( tableViewer.getControl().getShell(),
                              Messages.getString("EnvironmentalVar_DuplicateEntryDialog_Title" ), //$NON-NLS-1$
                              Messages.getString("EnvironmentalVar_New_DuplicateEntryDialog_Message" ) ); //$NON-NLS-1$
    }
    newInputList = null;
  }
  
  
  
  protected void deleteElement( final int featureID ) {
    
    EStructuralFeature eStructuralFeature = this.posixApplicationType.eClass().getEStructuralFeature( featureID );
    
    EcoreUtil.remove( eStructuralFeature );
    
  }
  
  
  
  protected void performDelete(final TableViewer viewer ) {
    
    IStructuredSelection structSelection 
                               = ( IStructuredSelection ) viewer.getSelection();
    
    Iterator<?> it = structSelection.iterator();

    /*
     * Iterate over the selections and delete them from the model.
     */
    while ( it.hasNext() ) {
    
      Object feature = it.next();
        
      if (feature instanceof ArgumentType) {
      
        ArgumentType argument = (ArgumentType) feature;    

        try {
          EcoreUtil.remove( argument);
        
        } catch( Exception e ) {
          Activator.logException( e );
        }
        
      } //end ArgumentType
      else if (feature instanceof EnvironmentType) {
        EnvironmentType environment = (EnvironmentType) feature;
        
        try {
          EcoreUtil.remove( environment );
          viewer.setInput( this.posixApplicationType.getEnvironment() );
        } catch( Exception e ) {
          Activator.logException( e );
        }
      
      } // end EnvironmentType
    }
    
    viewer.refresh();
    contentChanged();
    
  } // End void performDelete()
  
  
  /**
   * Edit an element that appears in a Table Viewers.
   * 
   * @param tableViewer The {@link TableViewer} in which the new element appears.
   * @param innerValue The new value of the element.
   */
  @SuppressWarnings("unchecked")
  public void performEdit( final TableViewer tableViewer, final Object innerValue ) {
    
    boolean elementExists = false;
    String newElement;
        
    int featureID;
    
    if (innerValue == null) {
      return;
    }
        
    EStructuralFeature eStructuralFeature;
    
    /*
     * Get the TableViewer Selection
     */
    IStructuredSelection structSelection 
                              = ( IStructuredSelection ) tableViewer.getSelection();
    
    /* If the selection is not null then Change the selected element */
    if (structSelection != null) {
    
      Object feature = structSelection.getFirstElement();
      
       
      

      if ( tableViewer == this.argumentViewer ) {
      
        EList <ArgumentType> newInputList = ( EList<ArgumentType> )tableViewer.getInput();
        
        if (newInputList == null ) {
          newInputList = new BasicEList<ArgumentType>();
        }
        
        Iterator<ArgumentType> iter = newInputList.iterator();
    
        featureID = PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT;
        
        eStructuralFeature = this.posixApplicationType.eClass()
                                            .getEStructuralFeature( featureID );

        

        /* Get the Index of the Element that needs to be changed */
        int index = (( java.util.List<Object> )this.posixApplicationType
                                 .eGet(eStructuralFeature)).indexOf( feature  );

        /* 
         * Create a new Argument Type EObject with the new values that will 
         * substitute the old EObject.
         */
        this.argumentType = PosixFactory.eINSTANCE.createArgumentType();
        this.argumentType = (ArgumentType) innerValue;
        newElement = this.argumentType.getValue();
        
        
        /* Change the element. The element is located through it's index position
         *   in the list.
         */
        while ( iter.hasNext() && !elementExists ){
          
          ArgumentType tempArgumentType = iter.next();
          if( ( tempArgumentType.getValue().equals( newElement ) ) 
              && ( !tempArgumentType.equals( feature )) ){
            elementExists = true;          
          } 
        } 
        
        if (!elementExists){
          
          (( java.util.List<Object> )this.posixApplicationType
                     .eGet( eStructuralFeature )).set( index, this.argumentType );
          contentChanged();
          
        }else {
          MessageDialog.openError( tableViewer.getControl().getShell(),
                                   Messages.getString( "Arguments_DuplicateEntryDialog_Title" ), //$NON-NLS-1$
                                   Messages.getString( "Arguments_New_DuplicateEntryDialog_Message" ) ); //$NON-NLS-1$
         }
        
      } 
      else {
        
        EList <EnvironmentType> newInputList = ( EList<EnvironmentType> )tableViewer.getInput();
        
        if (newInputList == null ) {
          newInputList = new BasicEList<EnvironmentType>();
        }
        
        Iterator<EnvironmentType> iter = newInputList.iterator();
        
        featureID = PosixPackage.POSIX_APPLICATION_TYPE__ENVIRONMENT;
        
        eStructuralFeature = this.posixApplicationType.eClass()
                                            .getEStructuralFeature( featureID );

        
        /* Get the Index of the Element that needs to be changed */
        int index = (( java.util.List<Object> )this.posixApplicationType
                                 .eGet(eStructuralFeature)).indexOf( feature  );

        /* 
         * Create a new Environment Type EObject with the new values that will 
         * substitute the old EObject.
         */
        this.environmentType = PosixFactory.eINSTANCE.createEnvironmentType();
        this.environmentType = ( EnvironmentType ) innerValue;
        newElement = this.environmentType.getName();
       

        /* Change the element. The element is located through it's index position
         *   in the list.
         */
        while ( iter.hasNext() && !elementExists ){
          
          EnvironmentType tempEnvironmentType = iter.next();
          if( (tempEnvironmentType.getName().equals( newElement )) 
              && (!tempEnvironmentType.equals( feature )) ){
            elementExists = true;          
          } 
        }
        
        if (!elementExists){
          
          (( java.util.List<Object> )this.posixApplicationType
                  .eGet( eStructuralFeature )).set( index, this.environmentType );
          contentChanged();
          
        }else {
          MessageDialog.openError( tableViewer.getControl().getShell(),
                              Messages.getString("EnvironmentalVar_DuplicateEntryDialog_Title" ), //$NON-NLS-1$
                              Messages.getString("EnvironmentalVar_New_DuplicateEntryDialog_Message" ) ); //$NON-NLS-1$
         }
            
      }
        
      feature = null;
      eStructuralFeature = null;
      tableViewer.refresh();
      
    }
  }
 
}
