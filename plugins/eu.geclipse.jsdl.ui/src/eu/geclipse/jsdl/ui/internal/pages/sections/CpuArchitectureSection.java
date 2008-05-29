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
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.TableWrapData;

import eu.geclipse.jsdl.model.base.CPUArchitectureType;
import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.base.JobDescriptionType;
import eu.geclipse.jsdl.model.base.JsdlFactory;
import eu.geclipse.jsdl.model.base.JsdlPackage;
import eu.geclipse.jsdl.model.base.ProcessorArchitectureEnumeration;
import eu.geclipse.jsdl.model.base.ResourcesType;
import eu.geclipse.jsdl.ui.adapters.jsdl.JsdlAdaptersFactory;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.pages.FormSectionFactory;
import eu.geclipse.jsdl.ui.internal.pages.Messages;


/**
 * @author nloulloud
 *
 */
public class CpuArchitectureSection extends JsdlAdaptersFactory {
  
  protected JobDescriptionType jobDescriptionType = JsdlFactory.eINSTANCE.createJobDescriptionType();
  protected ResourcesType resourcesType = JsdlFactory.eINSTANCE.createResourcesType();  
  protected CPUArchitectureType cpuArchitectureType = JsdlFactory.eINSTANCE.createCPUArchitectureType();
  protected Label lblCPUArchName = null;
  protected Combo cmbCPUArchName = null;
      
  private boolean adapterRefreshed = false;
  private boolean isNotifyAllowed = true;
  
  
  /**
   * @param parent
   * @param toolkit
   */
  public CpuArchitectureSection (final Composite parent,
                                 final FormToolkit toolkit) {
    
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
  
  
  
  private void createSection ( final Composite parent,
                               final FormToolkit toolkit ) {
    
    String sectionTitle = Messages.getString( "ResourcesPage_CPUArch" ); //$NON-NLS-1$
    String sectionDescription = Messages.getString( "ResourcesPage_CPUArchDescr" ); //$NON-NLS-1$

    TableWrapData td;
       
    Composite client = FormSectionFactory.createStaticSection( toolkit,
                                           parent,
                                           sectionTitle,
                                           sectionDescription,
                                           2 );
     


     td = new TableWrapData( TableWrapData.FILL_GRAB );
    
    /*========================== CPU Architecture Widgets ====================*/
    
    this.lblCPUArchName = toolkit.createLabel( client,
                             Messages.getString( "ResourcesPage_CPUArchName" ) ); //$NON-NLS-1$
    
    this.cmbCPUArchName = new Combo( client, SWT.SIMPLE 
                                              | SWT.DROP_DOWN | SWT.READ_ONLY );
    
    this.cmbCPUArchName.setData( FormToolkit.KEY_DRAW_BORDER );
    
    /* Populate the Combo Box with the CPU Architecture Literals */    
    EEnum cFEnum = JsdlPackage.Literals.PROCESSOR_ARCHITECTURE_ENUMERATION;
    /* 
     * Add an EMPTY item value so that the user can disable the specific 
     * feature 
     */
    this.cmbCPUArchName.add(""); //$NON-NLS-1$ 
        
    /*
     * Add the CPUArchitecture Enumeration Literals to the 
     * appropriate SWT Combo widget.
     */
    for (int i=0; i<cFEnum.getELiterals().size(); i++){         
      this.cmbCPUArchName.add( cFEnum.getEEnumLiteral( i ).toString() );
    }
    cFEnum = null;
    
    String[] sortedTypes = this.cmbCPUArchName.getItems();
    Arrays.sort( sortedTypes );
    this.cmbCPUArchName.setItems( sortedTypes );    
        
    this.cmbCPUArchName.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent e) {
        
        String selectedCPUArch = CpuArchitectureSection.this.cmbCPUArchName
                                            .getItem( CpuArchitectureSection.this.cmbCPUArchName.getSelectionIndex() );
        
        if (CpuArchitectureSection.this.cmbCPUArchName
                      .getItem( CpuArchitectureSection.this.cmbCPUArchName.getSelectionIndex() ) == "") { //$NON-NLS-1$
          
          deleteElement( CpuArchitectureSection.this.cpuArchitectureType );
          CpuArchitectureSection.this.cpuArchitectureType = null;
                    
        }
        else {
          checkCPUArch();
          CpuArchitectureSection.this.cpuArchitectureType.setCPUArchitectureName(
                                              ProcessorArchitectureEnumeration
                                             .get( selectedCPUArch ) );
          
          CpuArchitectureSection.this.resourcesType.setCPUArchitecture(CpuArchitectureSection.this.cpuArchitectureType);

        }
        contentChanged();
      }

      public void widgetDefaultSelected(final SelectionEvent e) {
       // Do Nothing 
      }
    });

    this.cmbCPUArchName.setLayoutData( td );
    
    toolkit.paintBordersFor( client);    
    
  } //End void cPUArch()
  
  
  
  protected void deleteElement( final EObject eStructuralFeature ) {
    
    try {
      EcoreUtil.remove( eStructuralFeature );  
    } catch( Exception e ) {
      Activator.logException( e );
    }
    
  } //end void deleteElement()
  
  
  
  protected EObject checkProxy( final EObject refEObject ) {
    
    EObject eObject = refEObject;
    
    if (eObject != null && eObject.eIsProxy() ) {
     
      eObject =  EcoreUtil.resolve( eObject, 
                                  this.resourcesType );
    }
        
    return eObject;
    
  } // end EObject checkProxy()
  
  
  
  protected void checkCPUArch() {
    
    checkResourcesElement();
    
    EStructuralFeature eStructuralFeature = this.resourcesType.eClass()
    .getEStructuralFeature( JsdlPackage.RESOURCES_TYPE__CPU_ARCHITECTURE );
    
     
    if ( !this.resourcesType.eIsSet( eStructuralFeature ) ) {
      // Make sure the CPU Architecture Type is not NULL. If NULL then instantiate it.
      if (this.cpuArchitectureType == null) {
        this.cpuArchitectureType = JsdlFactory.eINSTANCE.createCPUArchitectureType();
      }
      this.resourcesType.eSet( eStructuralFeature, this.cpuArchitectureType );
    }
    
  } //end void checkCPUArch()
  
  
  
  
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
    
  }
  
  
  
  private void fillFields(){
    
    this.isNotifyAllowed = false;
    
    if ( null != this.resourcesType.getCPUArchitecture() ){
      this.cpuArchitectureType = this.resourcesType.getCPUArchitecture();
      
      this.cmbCPUArchName.setText( this.cpuArchitectureType.getCPUArchitectureName()
                                   .getLiteral());
      
    }

    this.isNotifyAllowed = true;
    
    if ( this.adapterRefreshed ) {
      this.adapterRefreshed = false;
    }
    
    
  }
  
  
  
}
