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


/**
 * @author nloulloud
 *
 */
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
import eu.geclipse.jsdl.model.BoundaryType;
import eu.geclipse.jsdl.model.CPUArchitectureType;
import eu.geclipse.jsdl.model.CandidateHostsType;
import eu.geclipse.jsdl.model.FileSystemType;
import eu.geclipse.jsdl.model.FileSystemTypeEnumeration;
import eu.geclipse.jsdl.model.JobDefinitionType;
import eu.geclipse.jsdl.model.JobDescriptionType;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.jsdl.model.OperatingSystemType;
import eu.geclipse.jsdl.model.OperatingSystemTypeEnumeration;
import eu.geclipse.jsdl.model.OperatingSystemTypeType;
import eu.geclipse.jsdl.model.ProcessorArchitectureEnumeration;
import eu.geclipse.jsdl.model.RangeValueType;
import eu.geclipse.jsdl.model.ResourcesType;
import eu.geclipse.jsdl.ui.internal.Activator;



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
 */


public final class ResourcesTypeAdapter extends JsdlAdaptersFactory {
  
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
  
  
    
  private boolean adapterRefreshed = false;
  private boolean isNotifyAllowed = true;  
  
  
  /**
   * ResourcesTypeAdatper Class Constructor
   * 
   * @param rootJsdlElement The root element of a JSDL document.
   */
  public ResourcesTypeAdapter( final EObject rootJsdlElement ) {
    
    getTypeForAdapter(rootJsdlElement);
      
  } // End Class Constructor
  
  
  
  private void  getTypeForAdapter(final EObject rootJsdlElement){
    
    this.jobDescriptionType = ((JobDefinitionType ) rootJsdlElement).getJobDescription();
    
    if (this.jobDescriptionType.getResources() != null ) {
      this.resourcesType = this.jobDescriptionType.getResources();
    }

   } // End getTypeforAdapter()
  
  
  
  /**
   * JobIdentificationTypeAdapter Class Constructor
   * 
   * @param rootJsdlElement The root element of a JSDL document.
   */
  public void setContent(final EObject rootJsdlElement){
    
    this.adapterRefreshed = true;
    getTypeForAdapter( rootJsdlElement );
    
  }
  
  
  
  protected void contentChanged(){
    
    if (this.isNotifyAllowed){
      fireNotifyChanged( null);
    }
    
  }
  
 
  
  /**
   * Adapter interface to attach to the CandidateHosts list widget.
   * 
   * @param widget The SWT list widget which is associated with the 
   * CandidateHosts element of the JSDL document.
   */
  public void attachToHostName(final TableViewer widget){
    
    Integer featureID = new Integer(JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS);
    this.viewerFeaturesMap.put( featureID , widget );
       
  } // End attachToHostName()
  
  
  protected void deleteElement( final int featureID ) {
    
    EStructuralFeature eStructuralFeature = this.resourcesType.eClass().getEStructuralFeature( featureID );
    
    EcoreUtil.remove( eStructuralFeature );
    
  }
  
  
  
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
    
    
  }
  
  
  /**
   * @param tableViewer The SWT TableViewer that contains the Structural Features
   * @param value 
   */
  @SuppressWarnings("unchecked")
  public void performAdd(final TableViewer tableViewer, final Object[] value) {
    
    if (value == null) {
      return;
    }
    
    EStructuralFeature eStructuralFeature = null;
    Collection<String> collection = new ArrayList<String>();
    int featureID = JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS;
     
    EList <String> newInputList = ( EList<String> )tableViewer.getInput(); 
        
    
    if (newInputList == null) {
      newInputList = new BasicEList<String>();
    }
    
    for (int i=0; i < value.length; i++) {
      
      newInputList.add( (String)value[i] );  
    }

    tableViewer.setInput( newInputList  );
    
    
    eStructuralFeature = this.candidateHosts.eClass().getEStructuralFeature( featureID );
    
    for ( int i=0; i<tableViewer.getTable().getItemCount(); i++ ) {      
      collection.add( (String) tableViewer.getElementAt( i ) );
    }
    
    checkCandidateHostsElement();
    this.candidateHosts.eSet(eStructuralFeature, collection);

    this.contentChanged();
    
    eStructuralFeature = null;
    collection = null;
    
  }
  
  
  
  /**
   * @param tableViewer The SWT TableViewer that contains the Structural Features
   * @param value 
   */
  @SuppressWarnings("unchecked")
  public void performEdit(final TableViewer tableViewer, final Object value) {
    
    if (value == null) {
      return;
    }
    
    EStructuralFeature eStructuralFeature;
    
    int featureID = JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS;
    
    /*
     * Get the TableViewer Selection
     */
    IStructuredSelection structSelection 
                              = ( IStructuredSelection ) tableViewer.getSelection();
  
    /* If the selection is not null the Change the selected element */
    if (structSelection != null) {
      
      eStructuralFeature = this.candidateHosts.eClass()
                                            .getEStructuralFeature( featureID );

      Object feature = structSelection.getFirstElement();
    
    /* Get the Index of the Element that needs to be changed */
      int index = (( java.util.List<Object> )this.candidateHosts.eGet(eStructuralFeature))
                                                           .indexOf( feature  );
      
    /* Change the element. The element is located through it's index position
     * in the list.
     */
      (( java.util.List<Object> )this.candidateHosts.eGet( eStructuralFeature ))
            .set( index, value );
  
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
    
    if (!this.jobDescriptionType.eIsSet( eStructuralFeature )) {      
      this.jobDescriptionType.eSet( eStructuralFeature, this.resourcesType );


    }
  }

  
  
  
  protected void checkCandidateHostsElement() {
    checkResourcesElement();    
    EStructuralFeature eStructuralFeature = this.resourcesType.eClass()
    .getEStructuralFeature( JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS );
    
    if ( !this.resourcesType.eIsSet( eStructuralFeature ) ) {      
      this.resourcesType.eSet( eStructuralFeature, this.candidateHosts );
    }
  }
  
  
  protected void performDelete( final TableViewer viewer ){
    
    EStructuralFeature eStructuralFeature;
    
    int featureID = JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS;
    
    eStructuralFeature = this.candidateHosts.eClass()
                                            .getEStructuralFeature( featureID );
    
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
          
          if (!this.adapterRefreshed) {
      
//            ((java.util.List<?>)this.candidateHosts.eGet(eStructuralFeature))
//                                                             .remove(feature);
            
            this.candidateHosts.getHostName().remove( feature );
            
            if ( this.candidateHosts.getHostName().size() == 0 ) {
              EcoreUtil.remove( this.candidateHosts );
              
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
    
    
  }
  
  
  protected void checkOSElement() {
    
    checkResourcesElement();    
    EStructuralFeature eStructuralFeature = this.resourcesType.eClass()
    .getEStructuralFeature( JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM );
    
    if ( !this.resourcesType.eIsSet( eStructuralFeature ) ) {      
      this.resourcesType.eSet( eStructuralFeature, this.operatingSystemType );
    }
  }
  
  
  
  protected void checkFileSystemElement() {
    
    checkResourcesElement();
    
    EStructuralFeature eStructuralFeature = this.resourcesType.eClass()
    .getEStructuralFeature( JsdlPackage.RESOURCES_TYPE__FILE_SYSTEM);
    
    Collection<FileSystemType> collection = new ArrayList<FileSystemType>();
    collection.add( this.fileSystemType );
    
    if ( !this.resourcesType.eIsSet( eStructuralFeature ) ) {      
      this.resourcesType.eSet( eStructuralFeature, collection );
    }
  }
  

  
  protected void checkCPUArch() {
    
    checkResourcesElement();
    
    EStructuralFeature eStructuralFeature = this.resourcesType.eClass()
    .getEStructuralFeature( JsdlPackage.RESOURCES_TYPE__CPU_ARCHITECTURE );
    
     
    if ( !this.resourcesType.eIsSet( eStructuralFeature ) ) {      
      this.resourcesType.eSet( eStructuralFeature, this.cpuArchitectureType );
    }
    
  }
  
  
  protected EObject checkProxy(final EObject refEObject) {
    
    EObject eObject = refEObject;
    
    if (eObject != null && eObject.eIsProxy() ) {
     
      eObject =  EcoreUtil.resolve( eObject, 
                                  ResourcesTypeAdapter.this.resourcesType );
    }
        
    return eObject;
    
  }


   
  /**
   * Adapter interface to attach to the Operating System Type Combo widget.
   * 
   * @param widget The SWT {@link Combo} widget which is associated with the 
   * OperatingSystemType element of the JSDL document.
   */
  public void attachToOSType( final Combo widget ) {     
    
    Integer featureID = new Integer(JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM);
    this.comboFeaturesMap.put( featureID , widget );
        
    /* Populate the Combo Box with the CPU Architecture Literals */    
    EEnum cFEnum = JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_ENUMERATION;
 
    
    for (int i=0; i<cFEnum.getELiterals().size(); i++){         
         widget.add( cFEnum.getEEnumLiteral( i ).toString() );
    }
    
    String[] sortedTypes = widget.getItems();
    Arrays.sort( sortedTypes );
    widget.setItems( sortedTypes );      
    
        
    widget.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent e) {
        checkOSElement();   
        String selectedOSName = widget.getItem( widget.getSelectionIndex() );

        ResourcesTypeAdapter.this.operatingSystemTypeType
         .setOperatingSystemName(OperatingSystemTypeEnumeration
                                 .get( selectedOSName ) );
        
        ResourcesTypeAdapter.this.operatingSystemType
                             .setOperatingSystemType(
                             ResourcesTypeAdapter.this.operatingSystemTypeType);
        ResourcesTypeAdapter.this.contentChanged();
      }

      public void widgetDefaultSelected(final SelectionEvent e) {
          //Do Nothing
      }
    });
     
  } // End attachToCPUArchitecture
  
  
  
  /**
   * Adapter interface to attach to the CPU Architecture combo widget.
   * 
   * @param widget The SWT combo widget which is associated with the 
   * CPUArchitecture element of the JSDL document.
   */
  public void attachToCPUArchitecture(final Combo widget){
    
    Integer featureID = new Integer (JsdlPackage.RESOURCES_TYPE__CPU_ARCHITECTURE);

    this.comboFeaturesMap.put( featureID, widget );
        
    /* Populate the Combo Box with the CPU Architecture Literals */    
    EEnum cFEnum = JsdlPackage.Literals.PROCESSOR_ARCHITECTURE_ENUMERATION;
       for (int i=0; i<cFEnum.getELiterals().size(); i++){         
         widget.add( cFEnum.getEEnumLiteral( i ).toString() );
       }
       cFEnum = null;
          
        
    widget.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent e) {
        checkCPUArch();
        ResourcesTypeAdapter.this.cpuArchitectureType.setCPUArchitectureName(
                                              ProcessorArchitectureEnumeration
                                             .get( widget.getSelectionIndex()));
        
          ResourcesTypeAdapter.this.resourcesType
            .setCPUArchitecture( ResourcesTypeAdapter.this.cpuArchitectureType );

        
        ResourcesTypeAdapter.this.contentChanged();
      }

      public void widgetDefaultSelected(final SelectionEvent e) {
       // Do Nothing 
      }
    });
     
  } // End attachToCPUArchitecture
  
  
  /**
   * Adapter interface to attach to the Operating System Version text widget.
   * 
   * @param widget The SWT combo widget which is associated with the 
   * OperatingSystemVersion element of the JSDL document.
   */
  public void attachToOSVersion(final Text widget){
    
    Integer featureID = new Integer (JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_VERSION);
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
   * Adapter interface to attach to the Operating System Description text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * OperatingSystem Description element of the JSDL document.
   */
  public void attachToOSDescription(final Text widget){
    
    Integer featureID = new Integer(JsdlPackage.DOCUMENT_ROOT__DESCRIPTION);
    this.widgetFeaturesMap.put( featureID , widget );
    
    widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        checkOSElement();
        ResourcesTypeAdapter.this.operatingSystemType
                                            .setDescription( widget.getText() );  
        contentChanged();
          
        }
      } );   
        
  } // End attachToOSDescription()
  
  
  /**
   * Adapter interface to attach to the FileSystemType Name text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * Name attribute of the FileSystemType element in a JSDL document.
   */
  public void attachToFileSystemName( final Text widget ){
    Integer featureID = new Integer( JsdlPackage.FILE_SYSTEM_TYPE__NAME );
    this.widgetFeaturesMap.put( featureID , widget );
    
    widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
//        checkFileSystemElement();
        ResourcesTypeAdapter.this.fileSystemType.setName( widget.getText() );
        contentChanged();
          
        }
      } );
    
  } // End attachToFileSystemName()
  
  
  
  /**
   * Adapter interface to attach to the FileSystemType Description text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * FileSystemType Description element of the JSDL document.
   */
  public void attachToFileSystemDescription(final Text widget){
    
    Integer featureID = new Integer( JsdlPackage.FILE_SYSTEM_TYPE__DESCRIPTION );
    this.widgetFeaturesMap.put( featureID , widget );
    
    widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        checkFileSystemElement();
        ResourcesTypeAdapter.this.fileSystemType.setDescription( widget.getText() );
        contentChanged();          
        }
      } );
         
  } // End attachToFileSystemDescription()
  
  
  /**
   * Adapter interface to attach to the Mount Point text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * MountPoint element of the JSDL document.
   */
  public void attachToFileSystemMountPoint(final Text widget){
    
    Integer featureID = new Integer( JsdlPackage.FILE_SYSTEM_TYPE__MOUNT_POINT );
    this.widgetFeaturesMap.put( featureID , widget );
    
    widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        
        if ( !widget.getText().equals( "" ) ) { //$NON-NLS-1$ 
        
          checkFileSystemElement();
          ResourcesTypeAdapter.this.fileSystemType.setMountPoint( widget.getText() );
          
        }
        else {
          ResourcesTypeAdapter.this.fileSystemType.setMountPoint( null );
        }
        contentChanged();
      }
      
    } );    
          
  } // End attachToFileSystemMountPoint()
  
  
  
  /**
   * Adapter interface to attach to the Disk Space text widget and to Range Value
   * combo widget.
   * 
   * @param text The SWT text widget which is associated with the 
   * DiskSpace element of the JSDL document.
   * @param combo The SWT {@link Combo} widget which is associated with the Disk Space
   * Range Value element.
   */
  public void attachToFileSystemDiskSpace(final Text text, final Combo combo){
    
    this.widgetFeaturesMap.put (new Integer(JsdlPackage.FILE_SYSTEM_TYPE__DISK_SPACE)
                                , text );
    this.comboFeaturesMap.put( new Integer(JsdlPackage.DOCUMENT_ROOT__DISK_SPACE)
                                , combo );
    
    
    
    /* Event Listener for the Disk Space Text Widget */
    text.addModifyListener( new ModifyListener() {
      BoundaryType boundaryType = JsdlFactory.eINSTANCE.createBoundaryType();    
      RangeValueType rangeValueType = JsdlFactory.eINSTANCE.createRangeValueType();
      
      public void modifyText( final ModifyEvent e ) {
        
        
        if ( !text.getText().equals( "" ) ) { //$NON-NLS-1$ 
          
        checkFileSystemElement();  
        this.boundaryType.setValue( Double.parseDouble( text.getText() ) );        
        switch( combo.getSelectionIndex() ) {
          /* INDEX 0 = UPPER RANGE */
          case 0 : this.rangeValueType.setLowerBoundedRange( this.boundaryType ); 
          break;
          /* INDEX 1 = UPPER RANGE */
          case 1 : this.rangeValueType.setUpperBoundedRange( this.boundaryType );
          break;
          default:
          break;
        }
        
        this.rangeValueType = (RangeValueType) checkProxy( this.rangeValueType );       
        ResourcesTypeAdapter.this.fileSystemType.setDiskSpace( this.rangeValueType );
        }
        
        else{
          ResourcesTypeAdapter.this.fileSystemType.setDiskSpace( null );
        }
        contentChanged();
        
      }
    } );
    
    /* Event Listener for the Disk Space Range Combo Widget */
    combo.addSelectionListener(new SelectionListener() {
      BoundaryType boundaryType = JsdlFactory.eINSTANCE.createBoundaryType();    
      RangeValueType rangeValueType = JsdlFactory.eINSTANCE.createRangeValueType();
      
      public void widgetSelected(final SelectionEvent e) {
        if (!text.getText().equals( "" )) { //$NON-NLS-1$
          
        checkFileSystemElement();
        
        this.boundaryType.setValue( Double.parseDouble( text.getText() ) );        
        switch( combo.getSelectionIndex() ) {
          /* INDEX 0 = UPPER RANGE */
          case 0 : this.rangeValueType.setLowerBoundedRange( this.boundaryType ); 
          break;
          /* INDEX 1 = UPPER RANGE */
          case 1 : this.rangeValueType.setUpperBoundedRange( this.boundaryType );
          break;
          default:
          break;
        }
     
        ResourcesTypeAdapter.this.fileSystemType.setDiskSpace( this.rangeValueType );
        ResourcesTypeAdapter.this.contentChanged();
        
        } // end_if equals ""
        
      }

      public void widgetDefaultSelected(final SelectionEvent e) {
          //Do Nothing
      }
    });
    
                
  }
  
  

  /**
   * Adapter interface to attach to the FileSystemType combo widget.
   * 
   * @param widget The SWT combo widget which is associated with the 
   * FileSystemType element of a JSDL document.
   */
  public void attachToFileSystemType(final Combo widget){
    
    Integer featureID = new Integer(JsdlPackage.DOCUMENT_ROOT__FILE_SYSTEM_TYPE);
    this.comboFeaturesMap.put( featureID , widget );
        
    /* Populate the Combo Box with the File System Type Literals */    
    EEnum cFEnum = JsdlPackage.Literals.FILE_SYSTEM_TYPE_ENUMERATION;
       for (int i=0; i<cFEnum.getELiterals().size(); i++){         
         widget.add( cFEnum.getEEnumLiteral( i ).toString() );
       }
       cFEnum = null;
          
        
    widget.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent e) {
//        checkFileSystemElement();
        ResourcesTypeAdapter.this.fileSystemType
                 .setFileSystemType(FileSystemTypeEnumeration.get( 
                                                 widget.getSelectionIndex() ) );
        
        ResourcesTypeAdapter.this.contentChanged();
        
      }

      public void widgetDefaultSelected(final SelectionEvent e) {
        // Do Nothing
      }
    });
     
  } // End attachToFileSystemType()
  
  
  
  /**
   * This method populates the model content to the widgets registered with the
   * ResourcesType adapter.
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
              tableViewer = this.viewerFeaturesMap.get( new Integer( featureID ) );
              
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
               
               comboName = this.comboFeaturesMap.get( new Integer( featureID  ) );
               comboName.setText(this.operatingSystemType.getOperatingSystemType()
                                       .getOperatingSystemName().getLiteral());
             
             }
             
             if (this.operatingSystemType.getOperatingSystemVersion() != null ) {
               
               widgetName = this.widgetFeaturesMap.get( new Integer
                        ( JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_VERSION ) );
               widgetName.setText( this.operatingSystemType
                                                  .getOperatingSystemVersion());
            
             }
            
             if (this.operatingSystemType.getDescription() != null ) {
               
             
               widgetName = this.widgetFeaturesMap
                    .get( new Integer(JsdlPackage.DOCUMENT_ROOT__DESCRIPTION) );
            
               widgetName.setText( this.operatingSystemType.getDescription());
             }
             
            }
            break;            
            case JsdlPackage.RESOURCES_TYPE__FILE_SYSTEM: {
                            
              EList<FileSystemType> valueEList = (EList<FileSystemType>) this.resourcesType           
                                                    .eGet( eStructuralFeature );
              
              for (Iterator<FileSystemType> it = valueEList.iterator(); it.hasNext();){
                
                this.fileSystemType = it.next();
                
                if (this.fileSystemType.getName() != null ) {
                
                widgetName = this.widgetFeaturesMap
                        .get( new Integer(JsdlPackage.FILE_SYSTEM_TYPE__NAME) );
                
                widgetName.setText( this.fileSystemType.getName() );
                
                }
                
                if (this.fileSystemType.getDescription() != null ) {
                  
                widgetName = this.widgetFeaturesMap
                  .get(new Integer(JsdlPackage.FILE_SYSTEM_TYPE__DESCRIPTION) );
                
                widgetName.setText( this.fileSystemType.getDescription() );
                
                }
                
                if (this.fileSystemType.getMountPoint() != null ) {
                  
                widgetName = this.widgetFeaturesMap
                 .get( new Integer(JsdlPackage.FILE_SYSTEM_TYPE__MOUNT_POINT) );
                
                widgetName.setText( this.fileSystemType.getMountPoint() );
                
                }
                
                if (this.fileSystemType.getDiskSpace() != null ) {
                  
                
                widgetName = this.widgetFeaturesMap
                  .get( new Integer(JsdlPackage.FILE_SYSTEM_TYPE__DISK_SPACE) );
                
                comboName = this.comboFeaturesMap
                   .get( new Integer(JsdlPackage.DOCUMENT_ROOT__DISK_SPACE) );
                
                if ( this.fileSystemType.getDiskSpace().getLowerBoundedRange() != null ) {
                  
                  widgetName.setText( Double.toString( this.fileSystemType.getDiskSpace()
                                      .getLowerBoundedRange().getValue() ) );
                  
                  /* Select the Lower Bound */                  
                  comboName.select( 0 );
                 
                }
                
                else {
                  widgetName.setText( Double.toString( this.fileSystemType.getDiskSpace()
                                      .getUpperBoundedRange().getValue() ) );
                  /* Select the Lower Bound */
                  comboName.select( 1 );
                }
                

                comboName = this.comboFeaturesMap
                .get (new Integer(JsdlPackage.DOCUMENT_ROOT__FILE_SYSTEM_TYPE));
                
                comboName.setText(this.fileSystemType.getFileSystemType()
                                                                 .getLiteral());
                }               
                
              } // End Iterator
           
              
            }
            break;
            case JsdlPackage.RESOURCES_TYPE__CPU_ARCHITECTURE:{
              this.cpuArchitectureType = (CPUArchitectureType) this.resourcesType
                                                    .eGet( eStructuralFeature );
                            
             comboName = this.comboFeaturesMap.get( new Integer(featureID) );
             comboName.setText( this.cpuArchitectureType.getCPUArchitectureName()
                                                                 .getLiteral());
            }
              
              
            break;
            default:
            break;
          }
        }// end_if EReference
        
        else {

             // Do Nothing    
        
        } // End Else
        
        } // End isSet()
        
      } // End Iterator
       
    } //end if null    
    
    this.isNotifyAllowed = true;
    
    if ( this.adapterRefreshed ) {
      this.adapterRefreshed = false;
    }
    
  }// end load()
  
} // End ResourcesTypeAdapter Class

