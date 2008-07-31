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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.editor.FormPage;
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
import eu.geclipse.jsdl.ui.internal.pages.FormSectionFactory;
import eu.geclipse.jsdl.ui.internal.pages.Messages;
import eu.geclipse.ui.widgets.DoubleNumberVerifier;


/**
 * @author nloulloud
 *
 */
public class AdditionalResourceElemetsSection extends JsdlFormPageSection {
  
  protected static final String LOWER_BOUND = JsdlPackage.Literals.RANGE_VALUE_TYPE__LOWER_BOUND.getName();
  protected static final String UPPER_BOUND = JsdlPackage.Literals.RANGE_VALUE_TYPE__UPPER_BOUND.getName();
  protected static final String EXACT = JsdlPackage.Literals.RANGE_VALUE_TYPE__EXACT.getName();
  protected static final String[] RESOURCES_BOUNDARY_ITEMS = { EMPTY_STRING,
                                                               LOWER_BOUND,
                                                               UPPER_BOUND,
                                                               EXACT };
  
  protected JobDescriptionType jobDescriptionType = JsdlFactory.eINSTANCE.createJobDescriptionType();
  protected ResourcesType resourcesType = JsdlFactory.eINSTANCE.createResourcesType();  

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
  
  
  protected Text txtIndCPUTime = null;
  protected Text txtIndCPUCount = null;
  protected Text txtIndNetBand = null;
  protected Text txtVirtMem = null;
  protected Text txtPhysMem = null;
  protected Text txtIndDiskSpac = null;
  protected Text txtCPUTime = null;
  protected Text txtCPUCount = null;
  protected Text txtTotPhMem = null;
  protected Text txtTotVirtMem = null;
  protected Text txtTotDiskSp = null;
  protected Text txtTotResCount = null;  
//  private FormPage parentPage = null;
  
  /**
 * @param parent
 * @param toolkit
 */
  public AdditionalResourceElemetsSection( final FormPage formPage,
                                           final Composite parent,
                                           final FormToolkit toolkit ) {
        
    this.parentPage = formPage;
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
    this.cmbIndividualCPUSpeed.addSelectionListener( new SelectionListener(){

      public void widgetDefaultSelected( final SelectionEvent e ) {
        // TODO Auto-generated method stub
        
      }

      public void widgetSelected( final SelectionEvent e ) {
        
        setResourceElement( AdditionalResourceElemetsSection.this.txtIndCPUSp,
                            AdditionalResourceElemetsSection.this.cmbIndividualCPUSpeed,
                            JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED );
        
      }
      
    });
    
    
    this.txtIndCPUSp = toolkit.createText( client, EMPTY_STRING, SWT.NONE ); 
    this.txtIndCPUSp.addModifyListener( new ModifyListener() {
      
     
      public void modifyText( final ModifyEvent e ) {    
        
        
        setResourceElement( AdditionalResourceElemetsSection.this.txtIndCPUSp,
                            AdditionalResourceElemetsSection.this.cmbIndividualCPUSpeed,
                            JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_SPEED );
          
        }
      } );   
    this.txtIndCPUSp.addListener( SWT.Verify, new DoubleNumberVerifier() );
    this.txtIndCPUSp.setLayoutData( td );    
   
    
    /*=====================Individual CPU Time Widgets =======================*/
        
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblIndCPUTime = toolkit.createLabel( client,
                              Messages.getString( "ResourcesPage_IndCPUTime" ) ); //$NON-NLS-1$
    this.cmbIndividualCPUTime = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualCPUTime.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualCPUTime.setItems( RESOURCES_BOUNDARY_ITEMS );
    this.cmbIndividualCPUTime.addSelectionListener( new SelectionListener(){

      public void widgetDefaultSelected( final SelectionEvent e ) {
        // TODO Auto-generated method stub
        
      }

      public void widgetSelected( final SelectionEvent e ) {
        
        setResourceElement( AdditionalResourceElemetsSection.this.txtIndCPUTime,
                            AdditionalResourceElemetsSection.this.cmbIndividualCPUTime,
                            JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_TIME );
        
      }
      
    });
    
    
    this.txtIndCPUTime = toolkit.createText( client, EMPTY_STRING , SWT.NONE ); 
    this.txtIndCPUTime.addModifyListener( new ModifyListener() {
      
      
      public void modifyText( final ModifyEvent e ) {    
        
        
        setResourceElement( AdditionalResourceElemetsSection.this.txtIndCPUTime,
                            AdditionalResourceElemetsSection.this.cmbIndividualCPUTime,
                            JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_TIME );
          
        }
      } );   
    this.txtIndCPUTime.addListener( SWT.Verify, new DoubleNumberVerifier() );
    this.txtIndCPUTime.setLayoutData( td );

    /*=====================Individual CPU Count Widgets ======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblIndCPUCount = toolkit.createLabel( client,
                            Messages.getString( "ResourcesPage_IndCPUCount" ) ); //$NON-NLS-1$
    
    this.cmbIndividualCPUCount = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualCPUCount.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualCPUCount.setItems( RESOURCES_BOUNDARY_ITEMS );
    this.cmbIndividualCPUCount.addSelectionListener( new SelectionListener(){

      public void widgetDefaultSelected( final SelectionEvent e ) {
        // TODO Auto-generated method stub
        
      }

      public void widgetSelected( final SelectionEvent e ) {
        
        setResourceElement( AdditionalResourceElemetsSection.this.txtIndCPUCount,
                            AdditionalResourceElemetsSection.this.cmbIndividualCPUCount,
                            JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT );
        
      }
      
    });
    
    this.txtIndCPUCount = toolkit.createText( client, EMPTY_STRING, SWT.NONE );
    this.txtIndCPUCount.addModifyListener( new ModifyListener() {
      
      
      public void modifyText( final ModifyEvent e ) {    
        
        
        setResourceElement( AdditionalResourceElemetsSection.this.txtIndCPUCount,
                            AdditionalResourceElemetsSection.this.cmbIndividualCPUCount,
                            JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_CPU_COUNT );
          
        }
      } );  
    
    this.txtIndCPUCount.addListener( SWT.Verify, new DoubleNumberVerifier() );
    this.txtIndCPUCount.setLayoutData( td );

    /*===============Individual Network Bandwidth Widgets ====================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblIndNetBand = toolkit.createLabel( client,
                       Messages.getString( "ResourcesPage_IndNetwBandwidth" ) ); //$NON-NLS-1$
    
    this.cmbIndividualNetworkBandwidth = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualNetworkBandwidth.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualNetworkBandwidth.setItems( RESOURCES_BOUNDARY_ITEMS );
    this.cmbIndividualNetworkBandwidth.addSelectionListener( new SelectionListener(){

      public void widgetDefaultSelected( final SelectionEvent e ) {
        // TODO Auto-generated method stub
        
      }

      public void widgetSelected( final SelectionEvent e ) {
        
        setResourceElement( AdditionalResourceElemetsSection.this.txtIndNetBand,
                            AdditionalResourceElemetsSection.this.cmbIndividualNetworkBandwidth,
                            JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH );
        
      }
      
    });
    
    this.txtIndNetBand = toolkit.createText( client, EMPTY_STRING, SWT.NONE );
    this.txtIndNetBand.addModifyListener( new ModifyListener() {
      
      
      public void modifyText( final ModifyEvent e ) {    
        
        
        setResourceElement( AdditionalResourceElemetsSection.this.txtIndNetBand,
                            AdditionalResourceElemetsSection.this.cmbIndividualNetworkBandwidth,
                            JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_NETWORK_BANDWIDTH );
          
        }
      } );  
    this.txtIndNetBand.addListener( SWT.Verify, new DoubleNumberVerifier() );
    this.txtIndNetBand.setLayoutData( td );
    
    /*===============Individual Physical Memory Widgets ======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblPhysMem = toolkit.createLabel( client,
                                 Messages.getString( "ResourcesPage_PhysMem" ) ); //$NON-NLS-1$
    
    this.cmbIndividualPhysicalMemory = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualPhysicalMemory.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualPhysicalMemory.setItems( RESOURCES_BOUNDARY_ITEMS );
    this.cmbIndividualPhysicalMemory.addSelectionListener( new SelectionListener(){

      public void widgetDefaultSelected( final SelectionEvent e ) {
        // TODO Auto-generated method stub
        
      }

      public void widgetSelected( final SelectionEvent e ) {
        
        setResourceElement( AdditionalResourceElemetsSection.this.txtPhysMem,
                            AdditionalResourceElemetsSection.this.cmbIndividualPhysicalMemory,
                            JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY );
        
      }
      
    });    
    
    
    this.txtPhysMem = toolkit.createText( client, EMPTY_STRING, SWT.NONE );
    this.txtPhysMem.addListener( SWT.Verify, new DoubleNumberVerifier() );
    this.txtPhysMem.addModifyListener( new ModifyListener() {
      
      
      public void modifyText( final ModifyEvent e ) {    
        
        
        setResourceElement( AdditionalResourceElemetsSection.this.txtPhysMem,
                            AdditionalResourceElemetsSection.this.cmbIndividualPhysicalMemory,
                            JsdlPackage.RESOURCES_TYPE__INDIVIDUAL_PHYSICAL_MEMORY );
          
        }
      } );  
    this.txtPhysMem.setLayoutData( td );
    
    /*================Individual Virtual Memory Widgets ======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblVirtMem = toolkit.createLabel( client,
                                Messages.getString( "ResourcesPage_VirtualMem" ) ); //$NON-NLS-1$
    
    this.cmbIndividualVirtualMesmory = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualVirtualMesmory.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualVirtualMesmory.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtVirtMem = toolkit.createText( client, EMPTY_STRING, SWT.NONE );
    this.txtVirtMem.addListener( SWT.Verify, new DoubleNumberVerifier() );
    this.txtVirtMem.setLayoutData( td );
    
    /*================Individual Disk Space Widgets ==========================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblIndDiskSpac = toolkit.createLabel( client,
                              Messages.getString( "ResourcesPage_IndDiskSpace" ) ); //$NON-NLS-1$
    
    this.cmbIndividualDiskSpace = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualDiskSpace.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualDiskSpace.setItems( RESOURCES_BOUNDARY_ITEMS );    
    
    this.txtIndDiskSpac = toolkit.createText( client, EMPTY_STRING, SWT.NONE );
    this.txtIndDiskSpac.addListener( SWT.Verify, new DoubleNumberVerifier() );
    this.txtIndDiskSpac.setLayoutData( td );

    /*=========================Total CPU Time Widgets ========================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblCPUTime = toolkit.createLabel( client,
                                   Messages.getString( "ResourcesPage_CPUTime" ) ); //$NON-NLS-1$
    
    this.cmbTotalCPUTime = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalCPUTime.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalCPUTime.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtCPUTime = toolkit.createText( client, EMPTY_STRING, SWT.NONE );
    this.txtCPUTime.addListener( SWT.Verify, new DoubleNumberVerifier() );
    this.txtCPUTime.setLayoutData( td );
    
    /*========================Total CPU Count Widgets ========================*/
     
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblCPUCount = toolkit.createLabel( client,
                               Messages.getString( "ResourcesPage_TotCPUCount" ) ); //$NON-NLS-1$

    this.cmbTotalCPUCount = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalCPUCount.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalCPUCount.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtCPUCount = toolkit.createText( client, EMPTY_STRING, SWT.NONE );
    this.txtCPUCount.addListener( SWT.Verify, new DoubleNumberVerifier() );
    this.txtCPUCount.setLayoutData( td );
    
    /*=====================Total Physical Memory Widgets =====================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblTotPhMem = toolkit.createLabel( client,
                                Messages.getString( "ResourcesPage_TotPhysMem" ) ); //$NON-NLS-1$
    
    this.cmbTotalPhysicalMemory = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalPhysicalMemory.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalPhysicalMemory.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.cmbTotalPhysicalMemory.addSelectionListener( new SelectionListener(){

      public void widgetDefaultSelected( final SelectionEvent e ) {
        // TODO Auto-generated method stub
        
      }

      public void widgetSelected( final SelectionEvent e ) {
        
        setResourceElement( AdditionalResourceElemetsSection.this.txtTotPhMem,
                            AdditionalResourceElemetsSection.this.cmbTotalPhysicalMemory,
                            JsdlPackage.RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY );
        
      }
      
    });    
    this.txtTotPhMem = toolkit.createText( client, EMPTY_STRING, SWT.NONE );
    this.txtTotPhMem.addListener( SWT.Verify, new DoubleNumberVerifier() );
    this.txtTotPhMem.addModifyListener( new ModifyListener() {
      
      
      public void modifyText( final ModifyEvent e ) {    
        
        
        setResourceElement( AdditionalResourceElemetsSection.this.txtTotPhMem,
                            AdditionalResourceElemetsSection.this.cmbTotalPhysicalMemory,
                            JsdlPackage.RESOURCES_TYPE__TOTAL_PHYSICAL_MEMORY );
          
        }
      } );  
    this.txtTotPhMem.setLayoutData( td );

    /*======================Total Virtual Memory Widgets =====================*/
    
    td = new TableWrapData(TableWrapData.FILL_GRAB);
    this.lblTotVirtMem = toolkit.createLabel(client,
                             Messages.getString("ResourcesPage_TotVirtualMem")); //$NON-NLS-1$
    
    this.cmbTotalVirtualMemory = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalVirtualMemory.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalVirtualMemory.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtTotVirtMem = toolkit.createText( client, EMPTY_STRING, SWT.NONE );
    this.txtTotVirtMem.addListener( SWT.Verify, new DoubleNumberVerifier() );
    this.txtTotVirtMem.setLayoutData( td );
   
    /*========================Total Disk Space Widgets =======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblTotDiskSp = toolkit.createLabel( client,
                           Messages.getString( "ResourcesPage_TotDiskSpace" ) ); //$NON-NLS-1$
    
    this.cmbTotalDiskSpace = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalDiskSpace.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalDiskSpace.setItems( RESOURCES_BOUNDARY_ITEMS );
    this.txtTotDiskSp = toolkit.createText( client, EMPTY_STRING, SWT.NONE );
    this.txtTotDiskSp.addListener( SWT.Verify, new DoubleNumberVerifier() );
    this.txtTotDiskSp.setLayoutData( td );

    /*====================Total Resource Count Widgets =======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblTotResCount = toolkit.createLabel( client,
                             Messages.getString( "ResourcesPage_TotRescCount" ) ); //$NON-NLS-1$
    
    this.cmbTotalResourceCount = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalResourceCount.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalResourceCount.setItems( RESOURCES_BOUNDARY_ITEMS );
    this.txtTotResCount = toolkit.createText( client, EMPTY_STRING, SWT.NONE );
    this.txtTotResCount.addListener( SWT.Verify, new DoubleNumberVerifier() );
    this.txtTotResCount.setLayoutData( td );

    toolkit.paintBordersFor( client );
    
  }
  
  protected EObject checkProxy( final EObject refEObject ) {
    
    EObject eObject = refEObject;
    
    if (eObject != null && eObject.eIsProxy() ) {
     
      eObject =  EcoreUtil.resolve( eObject, AdditionalResourceElemetsSection.this.resourcesType );
    }
        
    return eObject;
    
  } // end EObject checkProxy()
    
  
  private void fillFields() {
    
    this.isNotifyAllowed = false;

    BoundaryType boundaryType = JsdlFactory.eINSTANCE.createBoundaryType();
    EList<?> exactTypeList;
    ExactType exactType;
    
    /* Total Individual CPU Speed */ 
    if (this.resourcesType.getIndividualCPUSpeed() != null ){
      if (this.resourcesType.getIndividualCPUSpeed().getLowerBound() != null ) {    
        boundaryType = this.resourcesType.getIndividualCPUSpeed().getLowerBound();        
        /* check for Lazy Loading */  
        boundaryType = (BoundaryType) checkProxy( boundaryType );    
        this.txtIndCPUSp.setText( Double.toString( boundaryType.getValue() ) );
        this.cmbIndividualCPUSpeed.setText( LOWER_BOUND );
      } else if ( this.resourcesType.getIndividualCPUSpeed().getUpperBound() != null ) {        
          boundaryType = this.resourcesType.getIndividualCPUSpeed().getUpperBound();        
          /* check for Lazy Loading */  
          boundaryType = (BoundaryType) checkProxy( boundaryType );    
          this.txtIndCPUSp.setText( Double.toString( boundaryType.getValue() ) );
          this.cmbIndividualCPUSpeed.setText( UPPER_BOUND );        
      } else {
          exactTypeList  = this.resourcesType.getIndividualCPUSpeed().getExact();
          Iterator<?> it = exactTypeList.iterator();
          
          while (it.hasNext()) {
            Object testType = it.next();
            if (testType instanceof ExactType){
              exactType = (ExactType)testType;          
              this.txtIndCPUSp.setText( Double.toString( exactType.getValue() ) );
              this.cmbIndividualCPUSpeed.setText( EXACT );
            }          
        }       
          
    }   
    
  } 
  /* Total Individual CPU Time */ 
  if (this.resourcesType.getIndividualCPUTime() != null ){
    if (this.resourcesType.getIndividualCPUTime().getLowerBound() != null ) {    
      boundaryType = this.resourcesType.getIndividualCPUTime().getLowerBound();        
      /* check for Lazy Loading */  
      boundaryType = (BoundaryType) checkProxy( boundaryType );    
      this.txtIndCPUTime.setText( Double.toString( boundaryType.getValue() ) );
      this.cmbIndividualCPUTime.setText( LOWER_BOUND );
    } else if ( this.resourcesType.getIndividualCPUTime().getUpperBound() != null ) {        
      boundaryType = this.resourcesType.getIndividualCPUTime().getUpperBound();        
      /* check for Lazy Loading */  
      boundaryType = (BoundaryType) checkProxy( boundaryType );    
      this.txtIndCPUTime.setText( Double.toString( boundaryType.getValue() ) );
      this.cmbIndividualCPUTime.setText( UPPER_BOUND );        
    } else {
      exactTypeList  = this.resourcesType.getIndividualCPUSpeed().getExact();
      Iterator<?> it = exactTypeList.iterator();
      while (it.hasNext()) {
        Object testType = it.next();
        if (testType instanceof ExactType){
          exactType = (ExactType)testType;          
          this.txtIndCPUTime.setText( Double.toString( exactType.getValue() ) );
          this.cmbIndividualCPUTime.setText( EXACT );
        }
      }       
     }
   }
  /* Total Individual CPU Count */ 
  if (this.resourcesType.getIndividualCPUCount() != null ){
      
    if (this.resourcesType.getIndividualCPUCount().getLowerBound() != null ) {    
      boundaryType = this.resourcesType.getIndividualCPUCount().getLowerBound();        
      /* check for Lazy Loading */  
      boundaryType = (BoundaryType) checkProxy( boundaryType );    
      this.txtIndCPUCount.setText( Double.toString( boundaryType.getValue() ) );
      this.cmbIndividualCPUCount.setText( LOWER_BOUND );
    } else if ( this.resourcesType.getIndividualCPUCount().getUpperBound() != null ) {        
        boundaryType = this.resourcesType.getIndividualCPUCount().getUpperBound();        
        /* check for Lazy Loading */  
        boundaryType = (BoundaryType) checkProxy( boundaryType );    
        this.txtIndCPUCount.setText( Double.toString( boundaryType.getValue() ) );
        this.cmbIndividualCPUCount.setText( UPPER_BOUND );        
    } else {
        exactTypeList  = this.resourcesType.getIndividualCPUCount().getExact();
        Iterator<?> it = exactTypeList.iterator();
          
        while (it.hasNext()) {
          Object testType = it.next();
          if (testType instanceof ExactType){
            exactType = (ExactType)testType;          
            this.txtIndCPUCount.setText( Double.toString( exactType.getValue() ) );
            this.cmbIndividualCPUCount.setText( EXACT );
          }          
        }       
        
    }
  }
  /* Total Netwokr Bandwidth */
  if (this.resourcesType.getIndividualNetworkBandwidth() != null ){
    
    if (this.resourcesType.getIndividualNetworkBandwidth().getLowerBound() != null ) {    
      boundaryType = this.resourcesType.getIndividualNetworkBandwidth().getLowerBound();        
      /* check for Lazy Loading */  
      boundaryType = (BoundaryType) checkProxy( boundaryType );    
      this.txtIndNetBand.setText( Double.toString( boundaryType.getValue() ) );
      this.cmbIndividualNetworkBandwidth.setText( LOWER_BOUND );
    } else if ( this.resourcesType.getIndividualNetworkBandwidth().getUpperBound() != null ) {        
        boundaryType = this.resourcesType.getIndividualNetworkBandwidth().getUpperBound();        
        /* check for Lazy Loading */  
        boundaryType = (BoundaryType) checkProxy( boundaryType );    
        this.txtIndNetBand.setText( Double.toString( boundaryType.getValue() ) );
        this.cmbIndividualNetworkBandwidth.setText( UPPER_BOUND );        
    } else {
        exactTypeList  = this.resourcesType.getIndividualNetworkBandwidth().getExact();
        Iterator<?> it = exactTypeList.iterator();
          
        while (it.hasNext()) {
          Object testType = it.next();
          if (testType instanceof ExactType){
            exactType = (ExactType)testType;          
            this.txtIndNetBand.setText( Double.toString( exactType.getValue() ) );
            this.cmbIndividualNetworkBandwidth.setText( EXACT );
          }          
        }       
        
    }
  }
  /* Individual Physical Memory */
  if (this.resourcesType.getIndividualPhysicalMemory() != null ){
    
    if (this.resourcesType.getIndividualPhysicalMemory().getLowerBound() != null ) {    
      boundaryType = this.resourcesType.getIndividualPhysicalMemory().getLowerBound();        
      /* check for Lazy Loading */  
      boundaryType = (BoundaryType) checkProxy( boundaryType );    
      this.txtPhysMem.setText( Double.toString( boundaryType.getValue() ) );
      this.cmbIndividualPhysicalMemory.setText( LOWER_BOUND );
    } else if ( this.resourcesType.getIndividualPhysicalMemory().getUpperBound() != null ) {        
        boundaryType = this.resourcesType.getIndividualPhysicalMemory().getUpperBound();        
        /* check for Lazy Loading */  
        boundaryType = (BoundaryType) checkProxy( boundaryType );    
        this.txtPhysMem.setText( Double.toString( boundaryType.getValue() ) );
        this.cmbIndividualPhysicalMemory.setText( UPPER_BOUND );        
    } else {
        exactTypeList  = this.resourcesType.getIndividualPhysicalMemory().getExact();
        Iterator<?> it = exactTypeList.iterator();
          
        while (it.hasNext()) {
          Object testType = it.next();
          if (testType instanceof ExactType){
            exactType = (ExactType)testType;          
            this.txtPhysMem.setText( Double.toString( exactType.getValue() ) );
            this.cmbIndividualPhysicalMemory.setText( EXACT );
          }          
        }       
        
    }
  }
  /* Total Physical Memory */
  if (this.resourcesType.getTotalPhysicalMemory() != null ){
    
    if (this.resourcesType.getTotalPhysicalMemory().getLowerBound() != null ) {    
      boundaryType = this.resourcesType.getTotalPhysicalMemory().getLowerBound();        
      /* check for Lazy Loading */  
      boundaryType = (BoundaryType) checkProxy( boundaryType );    
      this.txtTotPhMem.setText( Double.toString( boundaryType.getValue() ) );
      this.cmbTotalPhysicalMemory.setText( LOWER_BOUND );
    } else if ( this.resourcesType.getTotalPhysicalMemory().getUpperBound() != null ) {        
        boundaryType = this.resourcesType.getTotalPhysicalMemory().getUpperBound();        
        /* check for Lazy Loading */  
        boundaryType = (BoundaryType) checkProxy( boundaryType );    
        this.txtTotPhMem.setText( Double.toString( boundaryType.getValue() ) );
        this.cmbTotalPhysicalMemory.setText( UPPER_BOUND );        
    } else {
        exactTypeList  = this.resourcesType.getTotalPhysicalMemory().getExact();
        Iterator<?> it = exactTypeList.iterator();
          
        while (it.hasNext()) {
          Object testType = it.next();
          if (testType instanceof ExactType){
            exactType = (ExactType)testType;          
            this.txtTotPhMem.setText( Double.toString( exactType.getValue() ) );
            this.cmbTotalPhysicalMemory.setText( EXACT );
          }          
        }       
        
    }
  }
    
  boundaryType = null;
  this.isNotifyAllowed = true;
    
  if ( this.adapterRefreshed ) {
    this.adapterRefreshed = false;
  }
  
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
      this.resourcesType = (ResourcesType) checkProxy( this.resourcesType );
      if ( !this.resourcesType.isExclusiveExecution() && this.resourcesType.eContents().size() == 0) {
        EcoreUtil.remove( this.resourcesType );
      }
    }
    
  } // end void checkResourcesElement()
  
  
  @SuppressWarnings("unchecked")
  protected void setResourceElement(final Text text, final Combo combo, final int feature ){
    
    RangeValueType rangeValueType = null;
    BoundaryType boundaryType = null;
    ExactType exactType = null;
    String value =  text.getText();
    String type = combo.getText();
    this.resourcesType = (ResourcesType)checkProxy( this.resourcesType );
    EStructuralFeature eFeature = this.resourcesType.eClass().getEStructuralFeature( feature );
    
    
    if ( !text.getText().equals( EMPTY_STRING ) ) {
    
      checkResourcesElement();
      
      boundaryType = JsdlFactory.eINSTANCE.createBoundaryType();
      boundaryType.setValue( Double.parseDouble( value ) );    
       
      if ( type.equals( LOWER_BOUND ) ){
        clearErrorMessage();
        rangeValueType = JsdlFactory.eINSTANCE.createRangeValueType();
        rangeValueType.setLowerBound( boundaryType );
        contentChanged();
      } else if ( type.equals( UPPER_BOUND )  ){
        clearErrorMessage();
        rangeValueType = JsdlFactory.eINSTANCE.createRangeValueType();
        rangeValueType.setUpperBound( boundaryType );
        contentChanged();
      } else if ( type.equals( EXACT )  ){
        clearErrorMessage();
        rangeValueType = JsdlFactory.eINSTANCE.createRangeValueType();
        exactType = JsdlFactory.eINSTANCE.createExactType();
        exactType.setValue( Double.parseDouble( value )  );
        Collection<ExactType> collection = new ArrayList<ExactType>();
        collection.add( exactType );
        rangeValueType.getExact().addAll( collection );
        contentChanged();
      } else{       
          setMessage( "Please select Boundary !!!!", IMessageProvider.ERROR );        
      }
      
      
      this.resourcesType.eSet( eFeature, rangeValueType );
    
    }else{
      if( this.resourcesType.eIsSet( eFeature )) {
        this.resourcesType.eSet( eFeature, null );
        contentChanged();
      }
      
    }  
    
  }
  
  private void clearErrorMessage(){
    if ( null != getMessage() ) {
      if ( !getMessage().equals( EMPTY_STRING ) ){
        setMessage( EMPTY_STRING, IMessageProvider.NONE);
    }
    }
  }
  
  
  protected void setMessage(final String message, final int type) {
    
    if ( null == getMessage() ) {
      this.parentPage.getManagedForm().getForm().setMessage( EMPTY_STRING, IMessageProvider.NONE);
    }
    
    if ( !getMessage().equals( message ) ) {
      this.parentPage.getManagedForm().getForm().setMessage( message, type );
    }   
    
  }
  
  protected String getMessage() {
    
    return this.parentPage.getManagedForm().getForm().getMessage();
  }
  
  
   
}
