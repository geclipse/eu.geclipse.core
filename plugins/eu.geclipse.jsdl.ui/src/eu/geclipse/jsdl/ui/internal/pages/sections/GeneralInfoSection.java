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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.TableWrapData;

import eu.geclipse.jsdl.model.base.ApplicationType;
import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.base.JobDescriptionType;
import eu.geclipse.jsdl.model.base.JsdlFactory;
import eu.geclipse.jsdl.model.base.JsdlPackage;
import eu.geclipse.jsdl.model.posix.DocumentRoot;
import eu.geclipse.jsdl.model.posix.FileNameType;
import eu.geclipse.jsdl.model.posix.POSIXApplicationType;
import eu.geclipse.jsdl.model.posix.PosixFactory;
import eu.geclipse.jsdl.model.posix.PosixPackage;
import eu.geclipse.jsdl.ui.internal.pages.FormSectionFactory;
import eu.geclipse.jsdl.ui.internal.pages.Messages;


/**
 * @author nloulloud
 * 
 * This class is responsible for displaying the General Information section in the 
 * Overview Page of the JSDL editor. It provides an overview of the value of 
 * important JSDL elements. 
 */
public class GeneralInfoSection extends JsdlFormPageSection {
  
 
  private static final int WIDGET_MAX_WIDTH = 100;
  
  protected Label lblApplicationName = null;
  protected Label lblExecutable = null;
  protected Label lblInput = null;
  protected Label lblOutput = null;
  protected Label lblError = null;    
  protected FormText sectionContent = null;
  protected Text txtApplicationName = null;
  protected Text txtExecutable = null;
  protected Text txtInput = null;
  protected Text txtOutput = null;
  protected Text txtError = null;
  protected JobDefinitionType jobDefinitionType = null;
  protected JobDescriptionType jobDescriptionType = null;
  protected ApplicationType applicationType = null;
  protected POSIXApplicationType posixApplicationType = null;
  protected DocumentRoot documentRoot = PosixFactory.eINSTANCE.createDocumentRoot();
    
  /**
   * Class constructor. Creates the section.
   * 
   * @param parent The parent composite.
   * @param toolkit The parent Form Toolkit.
   */
  public GeneralInfoSection( final Composite parent, final FormToolkit toolkit ){
    
    createSection( parent, toolkit );
    
  }
  
  
  private Composite createSection( final Composite parentComposite, final FormToolkit toolkit ) {
    
    String sectionTitle =  Messages.getString("OverviewPage_GeneralInfoTitle");  //$NON-NLS-1$
    String sectionDescription = Messages.getString("OverviewPage_GeneralInfoDescription");   //$NON-NLS-1$
    
    Composite client = FormSectionFactory.createStaticSection( toolkit,
                                                                   parentComposite,
                                                                   sectionTitle,
                                                                   sectionDescription,
                                                                   2 );
    
    TableWrapData td ;
    
    /* ======================== Application Name ============================ */
    
    this.lblApplicationName = toolkit.createLabel( client,
                                   Messages.getString( "OverviewPage_ApplName" ) ); //$NON-NLS-1$
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    td.maxWidth = WIDGET_MAX_WIDTH;
    
    this.txtApplicationName = toolkit.createText( client,"", SWT.NONE );     //$NON-NLS-1$
    this.txtApplicationName.addModifyListener( new ModifyListener(){

      public void modifyText( final ModifyEvent e ) {
        
        if (!GeneralInfoSection.this.txtApplicationName.getText().equals( EMPTY_STRING )){
          GeneralInfoSection.this.applicationType.setApplicationName( 
                                                                 GeneralInfoSection.this.txtApplicationName.getText() );
        }else{
          GeneralInfoSection.this.applicationType.setApplicationName( null );
        }        
        
        contentChanged();        
      }
      
    });
        
    this.txtApplicationName.setLayoutData( td );
        
    /* ============================== Executable ============================ */
    
    this.lblExecutable = toolkit.createLabel( client, Messages.getString( "OverviewPage_Executable" ) ); //$NON-NLS-1$
                        
     td = new TableWrapData( TableWrapData.FILL_GRAB );
     td.maxWidth = WIDGET_MAX_WIDTH;
     
     this.txtExecutable = toolkit.createText( client, "", SWT.NONE );     //$NON-NLS-1$     
     this.txtExecutable.addModifyListener( new ModifyListener() {      
       FileNameType fileName = null;
       public void modifyText( final ModifyEvent e ) {
         checkPosixApplicationElement();
         if ( !GeneralInfoSection.this.txtExecutable.getText().equals( EMPTY_STRING ) ) { 
           if (null == this.fileName) {
             this.fileName = PosixFactory.eINSTANCE.createFileNameType();
           }
           this.fileName.setValue( GeneralInfoSection.this.txtExecutable.getText() );
           this.fileName = (FileNameType) checkProxy( this.fileName );
           GeneralInfoSection.this.posixApplicationType.setExecutable( this.fileName );
         }
       else{
         
         if ( null != this.fileName){
           this.fileName = (FileNameType) checkProxy( this.fileName );
           this.fileName = null;
         }
         GeneralInfoSection.this.posixApplicationType.setExecutable(null);
       }
       contentChanged();
       
       }
     } );
     
     this.txtExecutable.setLayoutData( td );
    
    /* ============================== Input File ============================ */
    
     this.lblInput = toolkit.createLabel( client, Messages.getString( "OverviewPage_Input" ) ); //$NON-NLS-1$
                                          
     td = new TableWrapData( TableWrapData.FILL_GRAB );
     td.maxWidth = WIDGET_MAX_WIDTH;
                       
     this.txtInput = toolkit.createText( client,"", SWT.NONE );     //$NON-NLS-1$
     
     this.txtInput.addModifyListener( new ModifyListener() {
       FileNameType fileName = null;
       public void modifyText( final ModifyEvent e ) {
       
         checkPosixApplicationElement();
         if ( !GeneralInfoSection.this.txtInput.getText().equals( EMPTY_STRING ) ) {
           if (null == this.fileName) {
             this.fileName = PosixFactory.eINSTANCE.createFileNameType();
           }
           this.fileName.setValue( GeneralInfoSection.this.txtInput.getText() );
           this.fileName = (FileNameType) checkProxy( this.fileName );
           GeneralInfoSection.this.posixApplicationType.setInput(this.fileName);
         }
         else{
           if ( null != this.fileName){
             this.fileName = (FileNameType) checkProxy( this.fileName );
             this.fileName = null;
           }
           GeneralInfoSection.this.posixApplicationType.setInput(null);
         }
         contentChanged();
         
       }
     } );
     
     this.txtInput.setLayoutData( td );
     
    /* ==============================Output File ============================ */
     
     this.lblOutput = toolkit.createLabel( client, Messages.getString( "OverviewPage_Output" ) ); //$NON-NLS-1$
                                                      
     td = new TableWrapData( TableWrapData.FILL_GRAB );
     td.maxWidth = WIDGET_MAX_WIDTH;
     
                                   
     this.txtOutput = toolkit.createText( client, "" , SWT.NONE );     //$NON-NLS-1$
     this.txtOutput.addModifyListener( new ModifyListener() {
       FileNameType fileName = null;
       public void modifyText( final ModifyEvent e ) {
         checkPosixApplicationElement();
         if ( !GeneralInfoSection.this.txtOutput.getText().equals( EMPTY_STRING ) ) { 
           if (null == this.fileName) {
             this.fileName = PosixFactory.eINSTANCE.createFileNameType();
           }
           this.fileName.setValue( GeneralInfoSection.this.txtOutput.getText() );
           this.fileName = (FileNameType) checkProxy( this.fileName );
           GeneralInfoSection.this.posixApplicationType.setOutput(this.fileName);
         }
         else{
           if ( null != this.fileName){
             this.fileName = (FileNameType) checkProxy( this.fileName );
             this.fileName = null;
           }
           GeneralInfoSection.this.posixApplicationType.setOutput(null);
         }
         contentChanged();
         
       }
     } ); 
     
     this.txtOutput.setLayoutData( td );     
    
    
    /* ============================== Error File ============================ */
     
     this.lblError = toolkit.createLabel( client, Messages.getString( "OverviewPage_Error" ) ); //$NON-NLS-1$
                                                               
     td = new TableWrapData( TableWrapData.FILL_GRAB );
     td.maxWidth = WIDGET_MAX_WIDTH;
                                            
     this.txtError = toolkit.createText( client,"", SWT.NONE );     //$NON-NLS-1$
     this.txtError.addModifyListener( new ModifyListener() {   
       FileNameType fileName = null;
       public void modifyText( final ModifyEvent e ) {
         checkPosixApplicationElement();
         if ( !GeneralInfoSection.this.txtError.getText().equals( EMPTY_STRING ) ) {
           if (null == this.fileName) {
             this.fileName = PosixFactory.eINSTANCE.createFileNameType();
           }
           this.fileName.setValue( GeneralInfoSection.this.txtError.getText() );
           this.fileName = (FileNameType) checkProxy( this.fileName );
           GeneralInfoSection.this.posixApplicationType.setError(this.fileName);
         }
         else{
           if ( null != this.fileName){
             this.fileName = (FileNameType) checkProxy( this.fileName );
             this.fileName = null;
           }
           GeneralInfoSection.this.posixApplicationType.setError(null);
         }
         contentChanged();
         
       }
     } );
     
     this.txtError.setLayoutData( td );          
    
    //Paint Flat Borders
    
    toolkit.paintBordersFor( client );
    
    return client;
    
  } //End Composite createSection()
  
  
  private void fillFields() {
    
    this.isNotifyAllowed = false;
    
    if (null != this.applicationType) {
      if ( null != this.applicationType.getApplicationName() ) {
        this.txtApplicationName.setText( this.applicationType.getApplicationName() );
      }else{
        this.txtApplicationName.setText( EMPTY_STRING );
      }
    }
    
    if( null != this.posixApplicationType ) {      
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
      
    }
    this.isNotifyAllowed = true;
    
    if( this.adapterRefreshed ) {
      this.adapterRefreshed = false;
    }
    
  }

  
  /* 
   * If the POSIX Application Element is set, check for any possible contents which may
   * be set. If none of the above are true, then delete the POSIX Application Element from it's
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
        this.applicationType.eSet( eStructuralFeature, collection );
      }      

    } 
  }
  
  
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
  
}
