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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
  *****************************************************************************/
package eu.geclipse.jsdl.ui.adapters.jsdl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.jsdl.model.base.BoundaryType;
import eu.geclipse.jsdl.model.base.CPUArchitectureType;
import eu.geclipse.jsdl.model.base.CandidateHostsType;
import eu.geclipse.jsdl.model.base.ExactType;
import eu.geclipse.jsdl.model.base.FileSystemType;
import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.base.JobDescriptionType;
import eu.geclipse.jsdl.model.base.JsdlFactory;
import eu.geclipse.jsdl.model.base.JsdlPackage;
import eu.geclipse.jsdl.model.base.OperatingSystemType;
import eu.geclipse.jsdl.model.base.OperatingSystemTypeEnumeration;
import eu.geclipse.jsdl.model.base.OperatingSystemTypeType;
import eu.geclipse.jsdl.model.base.ProcessorArchitectureEnumeration;
import eu.geclipse.jsdl.model.base.RangeValueType;
import eu.geclipse.jsdl.model.base.ResourcesType;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.pages.JobDefinitionPage;



/**
 * ResourcesTypeAdapter Class
 * 
 * This class provides adapters for manipulating <b>Resources</b> elements through the
 * Resources Page of the JSDL editor. Supported Resources elements are:
 * <p>
 * - Candidate Hosts
 * <p>
 * - FileSystem
 * <p>
 * - OperatingSystem
 * <p>
 * - CPUArchitecture
 *
 *@deprecated. This class is deprecated.
 */


public final class ResourcesTypeAdapter extends JsdlAdaptersFactory {

  protected static final String LOWER_BOUND = JsdlPackage.Literals.RANGE_VALUE_TYPE__LOWER_BOUND.getName();
  protected static final String UPPER_BOUND = JsdlPackage.Literals.RANGE_VALUE_TYPE__UPPER_BOUND.getName();
  protected static final String EXACT = JsdlPackage.Literals.RANGE_VALUE_TYPE__EXACT.getName();
  
  
  protected static final String[] RESOURCES_BOUNDARY_ITEMS = { "", //$NON-NLS-1$
                                                            LOWER_BOUND,
                                                            UPPER_BOUND,
                                                            EXACT };
  
  private static final String EMPTY_STRING = ""; //$NON-NLS-1$   
  
  protected Hashtable< Integer, Text > widgetFeaturesMap = new Hashtable< Integer, Text >();
  protected Hashtable< Integer, TableViewer > viewerFeaturesMap = new Hashtable< Integer, TableViewer >();
  protected Hashtable< Integer, Combo > comboFeaturesMap = new Hashtable< Integer, Combo >();
 
  protected Hashtable<String, EStructuralFeature> eStructuralFeaturesMap 
                                  = new Hashtable<String, EStructuralFeature>();
  
  protected JobDescriptionType jobDescriptionType = 
                               JsdlFactory.eINSTANCE.createJobDescriptionType();
  
  protected ResourcesType resourcesType 
                                  = JsdlFactory.eINSTANCE.createResourcesType();
  
  protected CandidateHostsType candidateHosts 
                             = JsdlFactory.eINSTANCE.createCandidateHostsType();
  
  protected OperatingSystemType operatingSystemType 
                            = JsdlFactory.eINSTANCE.createOperatingSystemType();
  
  protected OperatingSystemTypeType operatingSystemTypeType
                        = JsdlFactory.eINSTANCE.createOperatingSystemTypeType();
  
  protected FileSystemType fileSystemType 
                                 = JsdlFactory.eINSTANCE.createFileSystemType();
  
  protected CPUArchitectureType cpuArchitectureType = 
                              JsdlFactory.eINSTANCE.createCPUArchitectureType();
  
  protected BoundaryType boundaryType = JsdlFactory.eINSTANCE.createBoundaryType();
  
  protected ExactType exactType = JsdlFactory.eINSTANCE.createExactType();
  
  protected RangeValueType rangeValueType = JsdlFactory.eINSTANCE.createRangeValueType();
  
  private boolean adapterRefreshed = false;
  private boolean isNotifyAllowed = true;  
  
  
  
  
  
  /**
   * Constructs a new <code> {@link ResourcesTypeAdapter} </code>
   * 
   * @param jobDefinitionRoot . The root element of a JSDL document ({@link JobDefinitionType}).
   */
  public ResourcesTypeAdapter( final JobDefinitionType jobDefinitionRoot ) {
        
    getTypeForAdapter( jobDefinitionRoot );
      
  } // End Class Constructor
  
  
  
  /*
   * Get the Application Type Element from the root Jsdl Element.
   */  
  private void  getTypeForAdapter(final JobDefinitionType jobDefinitionRoot){
    
    this.jobDescriptionType = jobDefinitionRoot.getJobDescription();
    
    if ( this.jobDescriptionType.getResources() != null ) {
      this.resourcesType = this.jobDescriptionType.getResources();
    }

   } // End getTypeforAdapter()
  
  
  
  /**
   * Allows to set the adapter's content on demand and not through the constructor.
   * 
   * @param jobDefinitionRoot The root element of a JSDL document.
   */
  public void setContent( final JobDefinitionType jobDefinitionRoot ) { 
    
    this.adapterRefreshed = true;
    getTypeForAdapter( jobDefinitionRoot );
    
  }
  
  
  protected void contentChanged() {
    
    if (this.isNotifyAllowed){
      fireNotifyChanged( null);
    }
    
  }
  
 
  
  /**
   * The attach point that handles the {@link TableViewer} widget which is responsible for the 
   * Resources <b>CandidateHosts</b> element.
   * 
   * @param widget The TableViewer widget responsible for Resources CandidateHosts element.
   */
  public void attachToHostName(final TableViewer widget){
    
    Integer featureID = Integer.valueOf(JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS);
    this.viewerFeaturesMap.put( featureID , widget );
       
  } // End attachToHostName()
  
  
  protected void deleteElement( final EObject eStructuralFeature ) {
   
    try {
      EcoreUtil.remove( eStructuralFeature );  
    } catch( Exception e ) {
      Activator.logException( e );
    }
    
  } //end void deleteElement()
  
  
  /**
   * The attach point that handles the {@link TableViewer} widget which is responsible for the 
   * Resources <b>FileSystem</b> element.
   * 
   * @param widget The TableViewer widget responsible for Resources FileSystem element.
   */
  public void attachToFileSystems( final TableViewer widget ) {
    
    Integer featureID = Integer.valueOf(JsdlPackage.RESOURCES_TYPE__FILE_SYSTEM);
    this.viewerFeaturesMap.put( featureID , widget );
       
  } // End attachToHostName()
  
  
  
  /**
   * Adapter interface to attach to the Delete button.
   * 
   * @param button The SWT {@link Button} which is associated with an SWT list on the page
   * and is responsible to delete elements from this list.
   * 
   * @param tableViewer The SWT {@link TableViewer} containing the elements to be deleted.
   * 
   */
  public void attachToDelete(final Button button, final TableViewer tableViewer ){ 

    
    button.addSelectionListener(new SelectionListener() {

      public void widgetSelected(final SelectionEvent event) {
        performDelete( tableViewer  );
      }

      public void widgetDefaultSelected(final SelectionEvent event) {
          // Do Nothing - Required method
      }
    });
    
    
  } // edit void attachToDelete()
  
  
  
  /**
   * 
   * Method that adds a list of Candidate Hosts .
   * 
   * @param tableViewer The {@link TableViewer} that will hold the Candidate Hosts.
   * @param value The list of Candidate Hosts.
   */
  @SuppressWarnings("unchecked")
  public void addCandidateHosts(final TableViewer tableViewer, final Object[] value) {
    
    if (value == null) {
      return;
    }

    Collection<String> collection = new ArrayList<String>();

     
    EList <String> newInputList = ( EList<String> )tableViewer.getInput(); 
        
    
    if (newInputList == null) {
      newInputList = new BasicEList<String>();
    }
    
    for (int i=0; i < value.length; i++) {
      
      newInputList.add( (String)value[i] );  
    }

    tableViewer.setInput( newInputList  );
    
    
    for ( int i=0; i<tableViewer.getTable().getItemCount(); i++ ) {      
      collection.add( (String) tableViewer.getElementAt( i ) );
    }
    
    checkCandidateHostsElement();
    this.candidateHosts.getHostName().clear();
    this.candidateHosts.getHostName().addAll( collection );


    this.contentChanged();
    

    collection = null;
    
  } // end void addCandidateHosts()
  
  
  
  /**
   * 
   * Method that adds a list of File Systems.
   * 
   * @param tableViewer The {@link TableViewer} that will hold the File Systems.
   * @param value The list of File Systems.
   */
  @SuppressWarnings("unchecked")
  public void addFileSystem( final TableViewer tableViewer, final Object[] value ) {
    
    if (value == null) {
      return;
    }

    Collection<FileSystemType> collection = new ArrayList<FileSystemType>();

     
    EList <FileSystemType> newInputList = ( EList<FileSystemType> )tableViewer.getInput(); 
        
    
    if (newInputList == null) {
      newInputList = new BasicEList<FileSystemType>();
    }
    
    for (int i=0; i < value.length; i++) {
      
      newInputList.add( (FileSystemType) value[i] );  
    }

    tableViewer.setInput( newInputList  );
    
    
    for ( int i=0; i<tableViewer.getTable().getItemCount(); i++ ) {      
      collection.add( (FileSystemType) tableViewer.getElementAt( i ) );
    }
    
    checkFileSystemElement();
    this.resourcesType.getFileSystem().clear();
    this.resourcesType.getFileSystem().addAll( collection );
  


    this.contentChanged();
    

    collection = null;
    
  } // end addFileSystem()
    
  
  
  /**
   * 
   * Method that edits a list a FileSystem .
   * 
   * @param tableViewer The {@link TableViewer} that will hold the Candidate Hosts.
   * @param value The new value of the File System.
   */
  @SuppressWarnings("unchecked")
  public void performEdit(final TableViewer tableViewer, final Object value) {
    
    if (value == null) {
      return;
    }
    
    /*
     * Get the TableViewer Selection
     */
    IStructuredSelection structSelection 
                              = ( IStructuredSelection ) tableViewer.getSelection();
  
    /* If the selection is not null then Change the selected element */
    if (structSelection != null) {
      
      
      Object feature = structSelection.getFirstElement();
    
    /* Get the Index of the Element that needs to be changed */

      int idx = this.resourcesType.getFileSystem().indexOf( feature );
      
    /* Change the element. The element is located through it's index position
     * in the list.
     */

      try {
        this.resourcesType.getFileSystem().set( idx, value );
      } catch( Exception e ) {
        Activator.logException( e );
      }
      
      
  
    /* Refresh the table viewer and notify the editor that
     *  the page content has changed. 
     */
      tableViewer.refresh();
      contentChanged();
    
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
      if ( !this.resourcesType.isExclusiveExecution() && this.resourcesType.eContents().size() == 0) {
        EcoreUtil.remove( this.resourcesType );
      }
    }

    
    
  } // end void checkResourcesElement()
  
  
  
  protected void checkCandidateHostsElement() {
    
    checkResourcesElement();    
    EStructuralFeature eStructuralFeature = this.resourcesType.eClass()
    .getEStructuralFeature( JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS );
    
    if ( !this.resourcesType.eIsSet( eStructuralFeature ) ) {      
      this.resourcesType.eSet( eStructuralFeature, this.candidateHosts );
    }
    
  } // end void checkCandidateHostsElement()
    
  
  
  protected void performDelete( final TableViewer viewer ){
    
    
    IStructuredSelection structSelection 
                              = ( IStructuredSelection ) viewer.getSelection();
    

    if (structSelection != null) {
      
    
      Iterator<?> it = structSelection.iterator();

      /*
       * Iterate over the selections and delete them from the model.
       */
      while ( it.hasNext() ) {
    
        Object feature = it.next();
           
        try {
          /* Delete only Multi-Valued Elements */
          
          if ( !this.adapterRefreshed ) {
            
            /*
             * Check the instance of the Selection.
             */            
            if ( feature instanceof String) {
              
              /*
               * If this feature is an instance of String then, this 
               * is a CandidateHosts instance. Therefore, remove the
               * CandidataHosts instance.
               */
      
              this.candidateHosts.getHostName().remove( feature );
              
              if ( this.candidateHosts.getHostName().size() == 0 ) {
                
                EcoreUtil.remove( this.candidateHosts );
                
                checkResourcesElement();
              
              }
            }
            
            /*
             * Then this is a FileSystem instance, so we have to remove
             * the instance from the parent( ResourceType ) object.
             */
            else {
              this.resourcesType.getFileSystem().remove( feature );
              if ( this.resourcesType.getFileSystem().size() == 0 ) {
                EcoreUtil.remove(this.fileSystemType);
                checkResourcesElement();

              }
              
            }
            
            contentChanged();
    
          }
        else {
          viewer.remove( feature );
        }
          
          
      } //end try
      catch ( Exception e ) {
        Activator.logException( e );
      }
    
    /*
     * Refresh the table viewer and notify the editor that the page content has
     * changed.
     */  
    viewer.refresh();
    
      } // end While
      
    }// end_if
    
    
  } //end performDelete()
  
  
  
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
  
  
  
  protected void checkFileSystemElement() {
    
    checkResourcesElement();
    
    EStructuralFeature eStructuralFeature = this.resourcesType.eClass()
    .getEStructuralFeature( JsdlPackage.RESOURCES_TYPE__FILE_SYSTEM);
    
    Collection<FileSystemType> collection = new ArrayList<FileSystemType>();

    // Make sure the FileSystem Type is not NULL. If NULL then instantiate it.
    if (this.fileSystemType == null) {
        this.fileSystemType = JsdlFactory.eINSTANCE.createFileSystemType();
    }
    collection.add( this.fileSystemType );
    
    if ( !this.resourcesType.eIsSet( eStructuralFeature ) ) {   
      this.resourcesType.eSet( eStructuralFeature, collection );
    }
    
  } // end void checkFileSystemElement()
  

  
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
  
  
  
  protected EObject checkProxy( final EObject refEObject ) {
    
    EObject eObject = refEObject;
    
    if (eObject != null && eObject.eIsProxy() ) {
     
      eObject =  EcoreUtil.resolve( eObject, 
                                  ResourcesTypeAdapter.this.resourcesType );
    }
        
    return eObject;
    
  } // end EObject checkProxy()


   
  /**
   * The attach point that handles the {@link Combo} widget which is responsible for the 
   * Resources <b>OperatingSystem</b> element. This attach point provides a {@link SelectionListener}
   * that listens to changes in the text box and commits this changes to the underlying
   * model.
   * 
   * @param widget The Text widget responsible for Resources Operating System element.
   */
  public void attachToOSType( final Combo widget ) {     
    
    Integer featureID = Integer.valueOf(JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM);
    this.comboFeaturesMap.put( featureID , widget );
        
    /* Populate the Combo Box with the CPU Architecture Literals */    
    EEnum cFEnum = JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_ENUMERATION;
 
    // Adding Empty String to allow disabling O/S Type.
    widget.add(EMPTY_STRING);
    
    for (int i=0; i<cFEnum.getELiterals().size(); i++){         
         widget.add( cFEnum.getEEnumLiteral( i ).toString() );
    }
    
    String[] sortedTypes = widget.getItems();
    Arrays.sort( sortedTypes );
    widget.setItems( sortedTypes );      
    
        
    widget.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent e) {
          
        String selectedOSName = widget.getItem( widget.getSelectionIndex() );
        if ( selectedOSName == EMPTY_STRING ) { 
          
          deleteElement( ResourcesTypeAdapter.this.operatingSystemType );
          ResourcesTypeAdapter.this.operatingSystemType = null;
                    
        }
        else {
          checkOSElement();   
          

          ResourcesTypeAdapter.this.operatingSystemTypeType
                           .setOperatingSystemName(OperatingSystemTypeEnumeration
                                   .get( selectedOSName ) );
        
        
          ResourcesTypeAdapter.this.operatingSystemType
                                  .setOperatingSystemType(
                                  ResourcesTypeAdapter.this.operatingSystemTypeType);
        
          
        }
        ResourcesTypeAdapter.this.contentChanged();
      }

      public void widgetDefaultSelected(final SelectionEvent e) {
          //Do Nothing
      }
    });
     
  } // End attachToCPUArchitecture
  
  
  
  /**
   * The attach point that handles the {@link Combo} widget which is responsible for the 
   * Resources <b>CPUArchitecture</b> element. This attach point provides a {@link SelectionListener}
   * that listens to changes in the text box and commits this changes to the underlying
   * model.
   * 
   * @param widget The Text widget responsible for Resources CPUArchitecture element.
   */
  public void attachToCPUArchitecture(final Combo widget){
    
    Integer featureID = Integer.valueOf (JsdlPackage.RESOURCES_TYPE__CPU_ARCHITECTURE);

    this.comboFeaturesMap.put( featureID, widget );
        
    /* Populate the Combo Box with the CPU Architecture Literals */    
    EEnum cFEnum = JsdlPackage.Literals.PROCESSOR_ARCHITECTURE_ENUMERATION;
    /* 
     * Add an EMPTY item value so that the user can disable the specific 
     * feature 
     */
    widget.add(""); //$NON-NLS-1$
    
    /*
     * Add the CPUArchitecture Enumeration Literals to the 
     * appropriate SWT Combo widget.
     */
    for (int i=0; i<cFEnum.getELiterals().size(); i++){         
         widget.add( cFEnum.getEEnumLiteral( i ).toString() );
    }
    cFEnum = null;
    
    String[] sortedTypes = widget.getItems();
    Arrays.sort( sortedTypes );
    widget.setItems( sortedTypes );    
    
    
          
        
    widget.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent e) {
        
        String selectedCPUArch = widget.getItem( widget.getSelectionIndex() );
        
        if (widget.getItem( widget.getSelectionIndex() ) == "") { //$NON-NLS-1$
          
          deleteElement( ResourcesTypeAdapter.this.cpuArchitectureType );
          ResourcesTypeAdapter.this.cpuArchitectureType = null;
                    
        }
        else {
          checkCPUArch();
          ResourcesTypeAdapter.this.cpuArchitectureType.setCPUArchitectureName(
                                              ProcessorArchitectureEnumeration
                                             .get( selectedCPUArch ) );
          
          ResourcesTypeAdapter.this.cpuArchitectureType = (CPUArchitectureType) checkProxy( ResourcesTypeAdapter.this.cpuArchitectureType );
          
          ResourcesTypeAdapter.this.resourcesType
              .setCPUArchitecture( ResourcesTypeAdapter.this.cpuArchitectureType );

        }
        contentChanged();
      }

      public void widgetDefaultSelected(final SelectionEvent e) {
       // Do Nothing 
      }
    });
     
  } // End attachToCPUArchitecture
  
  
  
  /**
   * The attach point that handles the {@link Text} widget which is responsible for the 
   * Resources <b>OperatingSystemVersion</b> element. This attach point provides a {@link ModifyListener}
   * that listens to changes in the text box and commits this changes to the underlying
   * model.
   * 
   * @param widget The Text widget responsible for Resources OperatingSystemVersion element.
   */
  public void attachToOSVersion(final Text widget){
    
    Integer featureID = Integer.valueOf (JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_VERSION);
    this.widgetFeaturesMap.put( featureID , widget );
    
    widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        checkOSElement();
        ResourcesTypeAdapter.this.operatingSystemType
                                 .setOperatingSystemVersion( widget.getText() );
        contentChanged();
          
        }
      } );
    
         
  } // End attachToOSVersion()
  
  
  
  /**
   * The attach point that handles the {@link Text} widget which is responsible for the 
   * Resources <b>OperatingSystemDescription</b> element. This attach point provides a {@link ModifyListener}
   * that listens to changes in the text box and commits this changes to the underlying
   * model.
   * 
   * @param widget The Text widget responsible for Resources OperatingSystemDescription element.
   */
  public void attachToOSDescription(final Text widget){
    
    Integer featureID = Integer.valueOf(JsdlPackage.DOCUMENT_ROOT__DESCRIPTION);
    this.widgetFeaturesMap.put( featureID , widget );
    
    widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        checkOSElement();
        
        if (!widget.getText().equals( EMPTY_STRING ) ) {
          ResourcesTypeAdapter.this.operatingSystemType
                                            .setDescription( widget.getText() );
        }else{
          ResourcesTypeAdapter.this.operatingSystemType.setDescription( null );
        }
          
        contentChanged();
          
        }
      } );   
        
  } // End attachToOSDescription()
  
  
  /**
   * The attach point that handles the {@link Text} widget which is responsible for the 
   * Resources <b>IndividualCPUSpeed</b> element. This attach point provides a {@link ModifyListener}
   * that listens to changes in the text box and commits this changes to the underlying
   * model.
   * 
   * @param text The Text widget responsible for Resources IndividualCPUSpeed element.
   * @param combo The Combo widget responsible for Resources IndividualCPUSpeed boundary.
   */
  public void attachToIndividualCPUSpeed(final Text text, final Combo combo){
    
    Integer featureID = Integer.valueOf(JsdlPackage.DOCUMENT_ROOT__INDIVIDUAL_CPU_SPEED);
    this.widgetFeaturesMap.put( featureID , text );
    this.comboFeaturesMap.put( featureID, combo );
    
    combo.setItems( RESOURCES_BOUNDARY_ITEMS );
    
    combo.addSelectionListener( new SelectionListener() {

      public void widgetDefaultSelected( final SelectionEvent e ) {
        // Auto-generated method stub
        
        
      }

      public void widgetSelected( final SelectionEvent e ) {
        
        if (combo.getItem( combo.getSelectionIndex() ) == EMPTY_STRING ) {
          
          ResourcesTypeAdapter.this.resourcesType.setIndividualCPUSpeed( null );
                              
        }
        else if (combo.getItem( combo.getSelectionIndex() ) == UPPER_BOUND ) {
          ResourcesTypeAdapter.this.boundaryType.setValue( Double.parseDouble( text.getText()) );
          ResourcesTypeAdapter.this.rangeValueType.setUpperBound( ResourcesTypeAdapter.this.boundaryType );
          ResourcesTypeAdapter.this.resourcesType.setIndividualCPUSpeed( ResourcesTypeAdapter.this.rangeValueType );
        }
        else if (combo.getItem( combo.getSelectionIndex() ) == LOWER_BOUND ) {
          ResourcesTypeAdapter.this.boundaryType.setValue( Double.parseDouble( text.getText()) );
          ResourcesTypeAdapter.this.rangeValueType.setLowerBound( ResourcesTypeAdapter.this.boundaryType );
          ResourcesTypeAdapter.this.resourcesType.setIndividualCPUSpeed( ResourcesTypeAdapter.this.rangeValueType );
        }
        else if (combo.getItem( combo.getSelectionIndex() ) == EXACT ) {
          ResourcesTypeAdapter.this.rangeValueType.setLowerBound( null );
          ResourcesTypeAdapter.this.rangeValueType.setUpperBound( null );
          ResourcesTypeAdapter.this.exactType.setValue( Double.parseDouble( text.getText()) );
          ResourcesTypeAdapter.this.rangeValueType.getExact().add( ResourcesTypeAdapter.this.exactType );
          ResourcesTypeAdapter.this.resourcesType.setIndividualCPUSpeed( ResourcesTypeAdapter.this.rangeValueType );
        }
        else {
          
        }
      
        contentChanged();
      }
    });
    
    text.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        checkOSElement();
        
        if (!text.getText().equals( EMPTY_STRING ) ) {
          
        }else{
          ResourcesTypeAdapter.this.resourcesType.setIndividualCPUSpeed( null );
        }
          
        contentChanged();
          
        }
      } );   
        
  } // End attachToOSDescription()
 
  
  
  /**
   * The attach point that handles the {@link Combo} widget which is responsible for the 
   * Resources <b>ExclusiveExecution</b> element. This attach point provides a {@link SelectionListener}
   * that listens to changes in the text box and commits this changes to the underlying
   * model.
   * 
   * @param widget The Text widget responsible for Resources ExclusiveExecution element.
   */
  public void attachToExclusiveExecution( final Combo widget ){
    
    Integer featureID = Integer.valueOf(JsdlPackage.RESOURCES_TYPE__EXCLUSIVE_EXECUTION);
    this.comboFeaturesMap.put( featureID , widget );       
    /* 
     * Add an EMPTY item value so that the user can disable the specific 
     * feature 
     */
    widget.add(EMPTY_STRING);
    widget.add( "true" ); //$NON-NLS-1$
    widget.add( "false" ); //$NON-NLS-1$
        
    widget.addSelectionListener( new SelectionListener() {
      public void widgetSelected( final SelectionEvent e ) {
        
        /*
         * If the EMPTY item is selected then the ExclusiveExecution
         * element has to be unset.
         */
        if (widget.getItem( widget.getSelectionIndex() ) == "") { //$NON-NLS-1$
          
          ResourcesTypeAdapter.this.resourcesType.unsetExclusiveExecution();
                              
        }
        else {

        checkResourcesElement();
         ResourcesTypeAdapter.this.resourcesType
               .setExclusiveExecution( Boolean.parseBoolean( widget.getText() ) );
        }
        
         contentChanged();
        
      }

      public void widgetDefaultSelected( final SelectionEvent e ) {
        // Do Nothing
      }
    });
     
  } // End attachToFileSystemType()
  
  
    
  
  /**
   * Method which populates the content of the underlying model to the widgets that are
   * attached to this adapter. The method is called from the {@link JobDefinitionPage} when
   * the appropriate widgets are created and also every time the page becomes active. 
   */
  @SuppressWarnings("unchecked")
  public void load() {
    
    this.isNotifyAllowed = false;
    Text widgetName = null;    
    TableViewer tableViewer = null;
    Combo comboName = null;
    
    // Test if eObject is not empty.
    if( this.resourcesType != null ) {
      EClass eClass = this.resourcesType.eClass();
      int featureID;
             
      EList<EStructuralFeature> eAllStructuralFeaures = eClass.getEAllStructuralFeatures();
      
      for( EStructuralFeature eStructuralFeature : eAllStructuralFeaures ) {
       
        featureID = eStructuralFeature.getFeatureID();

        if ( this.resourcesType.eIsSet( eStructuralFeature ) ){
        
        if (eStructuralFeature instanceof EReference) {
          
          
          switch( featureID ) {
            case JsdlPackage.RESOURCES_TYPE__ANY :
            break;            
            case JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS :{
              tableViewer = this.viewerFeaturesMap.get(  Integer.valueOf( featureID ) );
              
              this.candidateHosts = (CandidateHostsType) this.resourcesType
                                                    .eGet( eStructuralFeature );
              
              EList<String> valueEList = this.candidateHosts.getHostName();
          
              tableViewer.setInput( valueEList  );
              
            }
            break;
            case JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM : {
              
             this.operatingSystemType = (OperatingSystemType) this.resourcesType
                                                    .eGet( eStructuralFeature );
             
             if ( this.operatingSystemType.getOperatingSystemType() !=null ) {
               
               comboName = this.comboFeaturesMap.get( Integer.valueOf( featureID  ) );
               comboName.setText(this.operatingSystemType.getOperatingSystemType()
                                       .getOperatingSystemName().getLiteral());
             
             }
             
             if (this.operatingSystemType.getOperatingSystemVersion() != null ) {
               
               widgetName = this.widgetFeaturesMap.get( Integer.valueOf
                        ( JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_VERSION ) );
               widgetName.setText( this.operatingSystemType
                                                  .getOperatingSystemVersion());
            
             }
            
             if (this.operatingSystemType.getDescription() != null ) {
               
             
               widgetName = this.widgetFeaturesMap
                    .get( Integer.valueOf(JsdlPackage.DOCUMENT_ROOT__DESCRIPTION) );
            
               widgetName.setText( this.operatingSystemType.getDescription());
             }
             
            }
            break;            
            case JsdlPackage.RESOURCES_TYPE__FILE_SYSTEM: {
            
              tableViewer = this.viewerFeaturesMap.get( Integer.valueOf(featureID) );
              EList<FileSystemType> valueEList = (EList<FileSystemType>) this.resourcesType           
                                                    .eGet( eStructuralFeature );
              
                
            
            if(/* !this.adapterRefreshed
                && */this.viewerFeaturesMap.containsKey( Integer.valueOf(featureID))) {
              
              for (Iterator<FileSystemType>  it = valueEList.iterator(); it.hasNext();){                    
                this.fileSystemType = it.next();                   
                tableViewer.setInput( valueEList );
                
                } // End Iterator
                         
              } // Endif
              
            }
            break;
            case JsdlPackage.RESOURCES_TYPE__CPU_ARCHITECTURE:{
              this.cpuArchitectureType = (CPUArchitectureType) this.resourcesType
                                                    .eGet( eStructuralFeature );
                            
             comboName = this.comboFeaturesMap.get( Integer.valueOf(featureID) );
             comboName.setText( this.cpuArchitectureType.getCPUArchitectureName()
                                                                 .getLiteral());
            }
              
              
            break;
            
            default:
            break;
          }
        }// end_if EReference
        
        else {
          /* Check EAttribures */
          switch( featureID ) {
            
            case JsdlPackage.RESOURCES_TYPE__EXCLUSIVE_EXECUTION: {
              boolean value = this.resourcesType.isExclusiveExecution();
                            
             comboName = this.comboFeaturesMap.get( Integer.valueOf(featureID) );
             comboName.setText( Boolean.toString( value ) );
            }
              
              
            break;
            default:
            break;
          }
        
        } // End Else for EAttributes
        
        } // End isSet()
        
      } // End Iterator
       
    } //end if null    
    
    this.isNotifyAllowed = true;
    
    if ( this.adapterRefreshed ) {
      this.adapterRefreshed = false;
    }
    
  }// end load()
  
} // End ResourcesTypeAdapter Class

