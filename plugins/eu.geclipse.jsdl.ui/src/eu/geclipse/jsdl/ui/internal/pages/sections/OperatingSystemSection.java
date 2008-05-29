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

import java.util.Arrays;

import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.base.JobDescriptionType;
import eu.geclipse.jsdl.model.base.JsdlFactory;
import eu.geclipse.jsdl.model.base.JsdlPackage;
import eu.geclipse.jsdl.model.base.OperatingSystemType;
import eu.geclipse.jsdl.model.base.OperatingSystemTypeEnumeration;
import eu.geclipse.jsdl.model.base.OperatingSystemTypeType;
import eu.geclipse.jsdl.model.base.ResourcesType;
import eu.geclipse.jsdl.ui.adapters.jsdl.JsdlAdaptersFactory;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.pages.FormSectionFactory;
import eu.geclipse.jsdl.ui.internal.pages.Messages;


/**
 * @author nloulloud
 *
 */
public class OperatingSystemSection extends JsdlAdaptersFactory {
  
  private static final String EMPTY_STRING = ""; //$NON-NLS-1$   
  private static final int WIDGET_HEIGHT = 100;
  protected JobDescriptionType jobDescriptionType = JsdlFactory.eINSTANCE.createJobDescriptionType();
  protected ResourcesType resourcesType = JsdlFactory.eINSTANCE.createResourcesType();  
  protected OperatingSystemType operatingSystemType = JsdlFactory.eINSTANCE.createOperatingSystemType();
  protected OperatingSystemTypeType operatingSystemTypeType = JsdlFactory.eINSTANCE.createOperatingSystemTypeType();
  protected Label lblOperSystType = null;
  protected Label lblOperSystVer = null;
  protected Label lblOSDescr = null;
  protected Combo cmbOperSystType = null;
  protected Text txtOSDescr = null;  
  protected Text txtOperSystVer = null;
    
  
  private boolean adapterRefreshed = false;
  private boolean isNotifyAllowed = true;
  
  
  
  /**
   * @param parent
   * @param toolkit
   */
  public OperatingSystemSection(final Composite parent,
                                final FormToolkit toolkit ){
    
      createSection( parent, toolkit );
    
  }
  
  
  
  /**
   * @param jobDefinitionType
   */
  public void setInput( final JobDefinitionType jobDefinitionType ) { 

    this.adapterRefreshed = true;
    this.jobDescriptionType = jobDefinitionType.getJobDescription();
    
    if ( this.jobDescriptionType.getResources() != null ) {
      this.resourcesType = this.jobDescriptionType.getResources();
    }
    fillFields();
    
  }
    
  
  
  protected void contentChanged() {
    
    if (this.isNotifyAllowed){
      fireNotifyChanged( null);
    }
    
  }
  
  
  
  private void createSection (final Composite parent,
                              final FormToolkit toolkit ) {
    
    String sectionTitle = Messages.getString( "ResourcesPage_OperSyst" ); //$NON-NLS-1$
    String sectionDescription = Messages.getString( "ResourcesPage_OperSystDescr" ); //$NON-NLS-1$
    
    GridData gd;
       
    Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                           parent,
                                           sectionTitle,
                                           sectionDescription,
                                           2 );
          
    gd = new GridData();
    gd.widthHint = 280;    

    /*==================== Operating System Type Widgets =====================*/
    this.lblOperSystType = toolkit.createLabel( client,
                              Messages.getString( "ResourcesPage_OperSystType" ) ); //$NON-NLS-1$
    this.cmbOperSystType = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbOperSystType.setData( FormToolkit.KEY_DRAW_BORDER );
    
    /* Populate the Combo Box with the CPU Architecture Literals */    
    EEnum cFEnum = JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_ENUMERATION;
 
    // Adding Empty String to allow disabling O/S Type.
    this.cmbOperSystType.add(EMPTY_STRING);
    
    for (int i=0; i<cFEnum.getELiterals().size(); i++){         
      this.cmbOperSystType.add( cFEnum.getEEnumLiteral( i ).toString() );
    }
    
    String[] sortedTypes = this.cmbOperSystType.getItems();
    Arrays.sort( sortedTypes );
    this.cmbOperSystType.setItems( sortedTypes );      
    
        
    this.cmbOperSystType.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent e) {
          
        String selectedOSName = OperatingSystemSection.this.cmbOperSystType
                                          .getItem( OperatingSystemSection.this.cmbOperSystType.getSelectionIndex() );
        
        if ( selectedOSName.equals( EMPTY_STRING ) ) { 
          
          deleteElement( OperatingSystemSection.this.operatingSystemType );
          
          /* Clear also the O/S Version and O/S Description */
          OperatingSystemSection.this.txtOperSystVer.setText( EMPTY_STRING );
          OperatingSystemSection.this.txtOSDescr.setText( EMPTY_STRING );
          OperatingSystemSection.this.operatingSystemType = null;
                    
        }
        else {
          checkOSElement();   
          

          OperatingSystemSection.this.operatingSystemTypeType
                           .setOperatingSystemName(OperatingSystemTypeEnumeration
                                   .get( selectedOSName ) );
        
        
          OperatingSystemSection.this.operatingSystemType
                                  .setOperatingSystemType(
                                  OperatingSystemSection.this.operatingSystemTypeType);
        
          
        }
        contentChanged();
      }

      public void widgetDefaultSelected(final SelectionEvent e) {
          //Do Nothing
      }
    });  

    this.cmbOperSystType.setLayoutData( gd );
    
    
    
    /* ================= Operating System Version Widgets ===================== */
    
    this.lblOperSystVer = toolkit.createLabel( client,
                           Messages.getString( "ResourcesPage_OperSystVersion" ) ); //$NON-NLS-1$
    this.txtOperSystVer = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    
    this.txtOperSystVer.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {

        
        if ( !OperatingSystemSection.this.txtOperSystVer.getText().equals( "" ) ) { //$NON-NLS-1$
        checkOSElement();          
          OperatingSystemSection.this.operatingSystemType
          .setOperatingSystemVersion( OperatingSystemSection.this.txtOperSystVer.getText() );
        }
        else{
          OperatingSystemSection.this.operatingSystemType
          .setOperatingSystemVersion( null );
        }
        
        contentChanged();
          
        }
      } );

    this.txtOperSystVer.setLayoutData( gd );
    
    
    /*========================== Description Widgets =========================*/
    
    gd = new GridData();
    gd.verticalAlignment = GridData.BEGINNING;
    this.lblOSDescr = toolkit.createLabel(client,
                                     Messages.getString("ResourcesPage_DEscr")); //$NON-NLS-1$
    this.lblOSDescr.setLayoutData( gd );
    gd = new GridData();
    this.txtOSDescr = toolkit.createText(client, "", SWT.NONE//$NON-NLS-1$
                      |SWT.H_SCROLL |SWT.V_SCROLL | SWT.WRAP);

    this.txtOSDescr.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        
        
        if (!OperatingSystemSection.this.txtOSDescr.getText().equals( EMPTY_STRING ) ) {
          checkOSElement();
          OperatingSystemSection.this.operatingSystemType
                                            .setDescription( OperatingSystemSection.this.txtOSDescr.getText() );
        }else{
          OperatingSystemSection.this.operatingSystemType.setDescription( null );
        }
          
        contentChanged();
          
        }
      } );   


    gd.widthHint = 265;
    gd.heightHint = WIDGET_HEIGHT;
    this.txtOSDescr.setLayoutData(gd);
    
    toolkit.paintBordersFor( client);
  
  }
  
  
  
  protected void checkResourcesElement() {
    
    EStructuralFeature eStructuralFeature = this.jobDescriptionType.eClass()
          .getEStructuralFeature( JsdlPackage.JOB_DESCRIPTION_TYPE__RESOURCES );
    
    /*
     * Check if the Resources element is not set. If not set then set it to its 
     * container (JobDescriptionType).
     */
    if (!this.jobDescriptionType.eIsSet( eStructuralFeature )) {      
      this.jobDescriptionType.eSet( eStructuralFeature, this.resourcesType );
    }
    /* 
     * If the Resources Element is set, check for any possible contents which may
     * be set. Also check if the Exclusive Execution attribute is set.
     * If none of the above are true, then delete the Resources Element from it's
     * container (JobDescriptionType).
     */
    else {
      if ( !this.resourcesType.isExclusiveExecution() && this.resourcesType.eContents().size() == 0) {
        EcoreUtil.remove( this.resourcesType );
      }
    }
    
  } // end void checkResourcesElement()
  
  
  
  
  protected void checkOSElement() {
    
    checkResourcesElement();    
    EStructuralFeature eStructuralFeature = this.resourcesType.eClass()
    .getEStructuralFeature( JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM );
    
    if ( !this.resourcesType.eIsSet( eStructuralFeature ) ) {
      // Make sure the Operating System Type is not NULL. If NULL then instantiate it.
      if (this.operatingSystemType == null) {
        this.operatingSystemType = JsdlFactory.eINSTANCE.createOperatingSystemType();
      }
      this.resourcesType.eSet( eStructuralFeature, this.operatingSystemType );
    }
    
  } // end void checkOSElement()
  
  
  
  protected void deleteElement( final EObject eStructuralFeature ) {
   
    try {
      EcoreUtil.remove( eStructuralFeature ); 
      checkResourcesElement();
    } catch( Exception e ) {
      Activator.logException( e );
    }
    
  } //end void deleteElement()
  
  
  
  private void fillFields() {
    
    this.isNotifyAllowed = false; 
    
    if( null != this.resourcesType.getOperatingSystem() ) {
      
      this.operatingSystemType = this.resourcesType.getOperatingSystem();
      
      if( null != this.operatingSystemType.getOperatingSystemType() ) {
        
        this.cmbOperSystType.setText( this.operatingSystemType.getOperatingSystemType()
          .getOperatingSystemName()
          .getLiteral() );
      }
      if( null != this.operatingSystemType.getOperatingSystemVersion() ) {
        this.txtOperSystVer.setText( this.operatingSystemType.getOperatingSystemVersion() );
      } else {
        this.txtOperSystVer.setText( EMPTY_STRING );
      }
      if( null != this.operatingSystemType.getDescription() ) {
        this.txtOSDescr.setText( this.operatingSystemType.getDescription() );
      } else {
        this.txtOSDescr.setText( EMPTY_STRING );
      }
    }
    
    this.isNotifyAllowed = true;
    
    if ( this.adapterRefreshed ) {
      this.adapterRefreshed = false;
    }
    
  }
    
}
