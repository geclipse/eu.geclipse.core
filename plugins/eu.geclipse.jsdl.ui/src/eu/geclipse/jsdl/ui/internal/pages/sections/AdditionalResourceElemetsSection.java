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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.TableWrapData;

import eu.geclipse.jsdl.model.base.BoundaryType;
import eu.geclipse.jsdl.model.base.ExactType;
import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.base.JobDescriptionType;
import eu.geclipse.jsdl.model.base.JsdlFactory;
import eu.geclipse.jsdl.model.base.JsdlPackage;
import eu.geclipse.jsdl.model.base.RangeValueType;
import eu.geclipse.jsdl.model.base.ResourcesType;
import eu.geclipse.jsdl.ui.adapters.jsdl.JsdlAdaptersFactory;
import eu.geclipse.jsdl.ui.internal.pages.FormSectionFactory;
import eu.geclipse.jsdl.ui.internal.pages.Messages;


/**
 * @author nloulloud
 *
 */
public class AdditionalResourceElemetsSection extends JsdlAdaptersFactory {
  
  protected static final String LOWER_BOUND = JsdlPackage.Literals.RANGE_VALUE_TYPE__LOWER_BOUND.getName();
  protected static final String UPPER_BOUND = JsdlPackage.Literals.RANGE_VALUE_TYPE__UPPER_BOUND.getName();
  protected static final String EXACT = JsdlPackage.Literals.RANGE_VALUE_TYPE__EXACT.getName();
  protected static final String EMPTY_STRING = ""; //$NON-NLS-1$
  
  protected static final String[] RESOURCES_BOUNDARY_ITEMS = { EMPTY_STRING,
                                                               LOWER_BOUND,
                                                               UPPER_BOUND,
                                                               EXACT };
  
  protected JobDescriptionType jobDescriptionType = JsdlFactory.eINSTANCE.createJobDescriptionType();
  protected ResourcesType resourcesType = JsdlFactory.eINSTANCE.createResourcesType();  
  protected BoundaryType boundaryType = JsdlFactory.eINSTANCE.createBoundaryType();  
  protected ExactType exactType = JsdlFactory.eINSTANCE.createExactType();  
  protected RangeValueType rangeValueType = JsdlFactory.eINSTANCE.createRangeValueType();

  protected Label lblIndCPUSpl = null;
  protected Label lblIndCPUTime = null;
  protected Label lblIndCPUCount = null;
  protected Label lblIndNetBand = null;
  protected Label lblPhysMem = null;
  protected Label lblVirtMem = null;
  protected Label lblIndDiskSpac = null;
  protected Label lblCPUTime = null;
  protected Label lblCPUCount = null;
  protected Label lblTotPhMem = null;
  protected Label lblTotVirtMem = null;
  protected Label lblTotDiskSp = null;
  protected Label lblTotResCount = null;
  
  protected Combo cmbIndividualCPUSpeed = null;
  protected Combo cmbIndividualCPUTime = null;
  protected Combo cmbIndividualCPUCount = null;
  protected Combo cmbIndividualNetworkBandwidth = null;
  protected Combo cmbIndividualPhysicalMemory = null;
  protected Combo cmbIndividualVirtualMesmory = null;
  protected Combo cmbIndividualDiskSpace = null;
  protected Combo cmbTotalCPUTime = null;
  protected Combo cmbTotalCPUCount = null;
  protected Combo cmbTotalPhysicalMemory = null;
  protected Combo cmbTotalVirtualMemory = null;
  protected Combo cmbTotalDiskSpace = null;
  protected Combo cmbTotalResourceCount = null;
  protected Text txtIndCPUSp = null;
  
  
  private Text txtIndCPUTime = null;
  private Text txtIndCPUCount = null;
  private Text txtIndNetBand = null;
  private Text txtVirtMem = null;
  private Text txtPhysMem = null;
  private Text txtIndDiskSpac = null;
  private Text txtCPUTime = null;
  private Text txtCPUCount = null;
  private Text txtTotPhMem = null;
  private Text txtTotVirtMem = null;
  private Text txtTotDiskSp = null;
  private Text txtTotResCount = null;  
  private boolean isNotifyAllowed = true;
  private boolean adapterRefreshed = false;
  
  
  
 /**
 * @param parent
 * @param toolkit
 */
  public AdditionalResourceElemetsSection( final Composite parent,
                                           final FormToolkit toolkit ) {
        
    createSection( parent, toolkit );
    
  }
  
  
  /**
   * @param jobDefinitionType
   */
  public void setInput( final JobDefinitionType jobDefinitionType ) { 
    
//    this.adapterRefreshed = true;
    
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
  
  
  private void createSection(final Composite parent, final FormToolkit toolkit) {
    String sectionTitle = Messages.getString( "ResourcesPage_AddElementRangeVal" ); //$NON-NLS-1$
    String sectionDescription = Messages.getString( "ResourcesPage_AddElementsRangeValueDescr" ); //$NON-NLS-1$

    TableWrapData td;
       
    Composite client = FormSectionFactory.createStaticSection( toolkit,
                                                               parent,
                                                               sectionTitle,
                                                               sectionDescription,
                                                               3
                                                               );
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    td.colspan = 1;

    /*=====================Individual CPU Speed Widgets ======================*/
    
    
    this.lblIndCPUSpl = toolkit.createLabel( client,
                               Messages.getString( "ResourcesPage_IndCPUSpeed" ) ); //$NON-NLS-1$

    this.cmbIndividualCPUSpeed = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualCPUSpeed.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualCPUSpeed.setItems( RESOURCES_BOUNDARY_ITEMS );  
    this.txtIndCPUSp = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtIndCPUSp.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {    
        
        if (!AdditionalResourceElemetsSection.this.txtIndCPUSp.getText().equals( EMPTY_STRING ) ) {
          
          AdditionalResourceElemetsSection.this.boundaryType.setValue( Double.parseDouble( AdditionalResourceElemetsSection.this.txtIndCPUSp.getText()) );
          AdditionalResourceElemetsSection.this.rangeValueType.setLowerBound( AdditionalResourceElemetsSection.this.boundaryType );
          AdditionalResourceElemetsSection.this.resourcesType.setIndividualCPUSpeed( AdditionalResourceElemetsSection.this.rangeValueType );
        }else{
          AdditionalResourceElemetsSection.this.resourcesType.setIndividualCPUSpeed( null );
        }
          
        contentChanged();
          
        }
      } );   
//    this.txtIndCPUSp.addListener( SWT.Verify, new NumberVerifier() );
    this.txtIndCPUSp.setLayoutData( td );    
   
    
    /*=====================Individual CPU Time Widgets =======================*/
        
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblIndCPUTime = toolkit.createLabel( client,
                              Messages.getString( "ResourcesPage_IndCPUTime" ) ); //$NON-NLS-1$
    this.cmbIndividualCPUTime = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualCPUTime.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualCPUTime.setItems( RESOURCES_BOUNDARY_ITEMS );        
    this.txtIndCPUTime = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$    
    this.txtIndCPUTime.setLayoutData( td );

    /*=====================Individual CPU Count Widgets ======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblIndCPUCount = toolkit.createLabel( client,
                            Messages.getString( "ResourcesPage_IndCPUCount" ) ); //$NON-NLS-1$
    
    this.cmbIndividualCPUCount = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualCPUCount.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualCPUCount.setItems( RESOURCES_BOUNDARY_ITEMS );
    this.txtIndCPUCount = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$

    this.txtIndCPUCount.setLayoutData( td );

    /*===============Individual Network Bandwidth Widgets ====================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblIndNetBand = toolkit.createLabel( client,
                       Messages.getString( "ResourcesPage_IndNetwBandwidth" ) ); //$NON-NLS-1$
    
    this.cmbIndividualNetworkBandwidth = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualNetworkBandwidth.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualNetworkBandwidth.setItems( RESOURCES_BOUNDARY_ITEMS );      
    
    this.txtIndNetBand = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtIndNetBand.setLayoutData( td );
    
    /*===============Individual Physical Memory Widgets ======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblPhysMem = toolkit.createLabel( client,
                                 Messages.getString( "ResourcesPage_PhysMem" ) ); //$NON-NLS-1$
    
    this.cmbIndividualPhysicalMemory = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualPhysicalMemory.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualPhysicalMemory.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtPhysMem = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$

    this.txtPhysMem.setLayoutData( td );
    
    /*================Individual Virtual Memory Widgets ======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblVirtMem = toolkit.createLabel( client,
                                Messages.getString( "ResourcesPage_VirtualMem" ) ); //$NON-NLS-1$
    
    this.cmbIndividualVirtualMesmory = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualVirtualMesmory.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualVirtualMesmory.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtVirtMem = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$

    this.txtVirtMem.setLayoutData( td );
    
    /*================Individual Disk Space Widgets ==========================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblIndDiskSpac = toolkit.createLabel( client,
                              Messages.getString( "ResourcesPage_IndDiskSpace" ) ); //$NON-NLS-1$
    
    this.cmbIndividualDiskSpace = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualDiskSpace.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualDiskSpace.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtIndDiskSpac = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtIndDiskSpac.setLayoutData( td );

    /*=========================Total CPU Time Widgets ========================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblCPUTime = toolkit.createLabel( client,
                                   Messages.getString( "ResourcesPage_CPUTime" ) ); //$NON-NLS-1$
    
    this.cmbTotalCPUTime = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalCPUTime.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalCPUTime.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtCPUTime = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtCPUTime.setLayoutData( td );
    
    /*========================Total CPU Count Widgets ========================*/
     
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblCPUCount = toolkit.createLabel( client,
                               Messages.getString( "ResourcesPage_TotCPUCount" ) ); //$NON-NLS-1$

    this.cmbTotalCPUCount = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalCPUCount.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalCPUCount.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtCPUCount = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtCPUCount.setLayoutData( td );
    
    /*=====================Total Physical Memory Widgets =====================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblTotPhMem = toolkit.createLabel( client,
                                Messages.getString( "ResourcesPage_TotPhysMem" ) ); //$NON-NLS-1$
    
    this.cmbTotalPhysicalMemory = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalPhysicalMemory.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalPhysicalMemory.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtTotPhMem = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtTotPhMem.setLayoutData( td );

    /*======================Total Virtual Memory Widgets =====================*/
    
    td = new TableWrapData(TableWrapData.FILL_GRAB);
    this.lblTotVirtMem = toolkit.createLabel(client,
                             Messages.getString("ResourcesPage_TotVirtualMem")); //$NON-NLS-1$
    
    this.cmbTotalVirtualMemory = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalVirtualMemory.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalVirtualMemory.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtTotVirtMem = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$    
    this.txtTotVirtMem.setLayoutData( td );
   
    /*========================Total Disk Space Widgets =======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblTotDiskSp = toolkit.createLabel( client,
                           Messages.getString( "ResourcesPage_TotDiskSpace" ) ); //$NON-NLS-1$
    
    this.cmbTotalDiskSpace = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalDiskSpace.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalDiskSpace.setItems( RESOURCES_BOUNDARY_ITEMS );
    this.txtTotDiskSp = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtTotDiskSp.setLayoutData( td );

    /*====================Total Resource Count Widgets =======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblTotResCount = toolkit.createLabel( client,
                             Messages.getString( "ResourcesPage_TotRescCount" ) ); //$NON-NLS-1$
    
    this.cmbTotalResourceCount = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalResourceCount.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalResourceCount.setItems( RESOURCES_BOUNDARY_ITEMS );
    this.txtTotResCount = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtTotResCount.setLayoutData( td );

    toolkit.paintBordersFor( client );
    
  }
  
  protected EObject checkProxy( final EObject refEObject ) {
    
    EObject eObject = refEObject;
    
    if (eObject != null && eObject.eIsProxy() ) {
     
      eObject =  EcoreUtil.resolve( eObject, 
                                  AdditionalResourceElemetsSection.this.resourcesType );
    }
        
    return eObject;
    
  } // end EObject checkProxy()
  
  
  private void fillFields() {
    
    this.isNotifyAllowed = false;
 
    this.rangeValueType = this.resourcesType.getIndividualCPUSpeed();
    
    this.rangeValueType = (RangeValueType) checkProxy( this.rangeValueType );
    
    
    if (this.resourcesType.getIndividualCPUSpeed() != null ){
    
      if (this.resourcesType.getIndividualCPUSpeed().getLowerBound() != null ) {
    
        this.boundaryType = this.resourcesType.getIndividualCPUSpeed().getLowerBound();
        
        /* check for Lazy Loading */
        this.boundaryType = (BoundaryType) checkProxy( this.boundaryType );
    
        this.txtIndCPUSp.setText( Double.toString( this.boundaryType.getValue() ) );
    

      }
    }
   
    this.isNotifyAllowed = true;
    
    if ( this.adapterRefreshed ) {
      this.adapterRefreshed = false;
    }
  }
  
   
}
