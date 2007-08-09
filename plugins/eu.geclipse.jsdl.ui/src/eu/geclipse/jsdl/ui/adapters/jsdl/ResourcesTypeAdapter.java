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
 * @author nickl
 *
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.jsdl.model.CPUArchitectureType;
import eu.geclipse.jsdl.model.CandidateHostsType;
import eu.geclipse.jsdl.model.DocumentRoot;
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
import eu.geclipse.jsdl.model.ResourcesType;



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
  protected Hashtable< Integer, List > listFeaturesMap = new Hashtable< Integer, List >();
  protected Hashtable< Integer, Combo > comboFeaturesMap = new Hashtable< Integer, Combo >();
  protected Hashtable<String, EStructuralFeature> eStructuralFeaturesMap 
                                  = new Hashtable<String, EStructuralFeature>();
  
  protected DocumentRoot documentRoot = 
                                     JsdlFactory.eINSTANCE.createDocumentRoot();
  
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
  
  
  protected EStructuralFeature parentFeature = null;
  
  private boolean adapterRefreshed = false;
  private boolean isNotifyAllowed = true;  
  
  
  /**
   * ResourcesTypeAdatper Class Constructor
   * 
   * @param rootJsdlElement The root element of a JSDL document.
   */
  public ResourcesTypeAdapter(final EObject rootJsdlElement) {
    
    getTypeForAdapter(rootJsdlElement);
      
  } // End Class Constructor
  
  
  
  private void  getTypeForAdapter(final EObject rootJsdlElement){
    
   
    TreeIterator <EObject> iterator = rootJsdlElement.eAllContents();
    
    while ( iterator.hasNext (  )  )  {  
   
      EObject eObject = iterator.next();
          
      if (eObject instanceof JobDescriptionType) {
        this.jobDescriptionType = (JobDescriptionType) eObject;
      }
      else if ( eObject instanceof ResourcesType ) {
        
        this.resourcesType = (ResourcesType) eObject;        
        
        
      } // endif ResourcesType
      
    } // End while   

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
  public void attachToHostName(final List widget){
    
    Integer featureID = new Integer(JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS);
    this.listFeaturesMap.put( featureID , widget );
       
  } // End attachToHostName()
  
  
  
  /**
   * Adapter interface to attach to the Delete button.
   * 
   * @param button The SWT button which is associated with an SWT list on the page
   * and is responsible to delete elements from this list.
   * 
   * @param list The SWT list containing the elements to be deleted.
   * 
   */
  public void attachToDelete(final Button button, final List list){ 
    
    list.addSelectionListener(new SelectionListener() {
      

      public void widgetSelected(final SelectionEvent e ) {
        if (list.getItemCount()>0){
          
          button.setEnabled( true );
        }     
      }

      public void widgetDefaultSelected( final SelectionEvent e ) {
        //  Auto-generated method stub   
      }
    });
    
    
    button.addSelectionListener(new SelectionListener() {

      public void widgetSelected(final SelectionEvent event) {        
        performDelete(list, list.getItem( list.getSelectionIndex() ) );
        if (list.getItemCount() == 0) {
          button.setEnabled( false ); 
        }
      }

      public void widgetDefaultSelected(final SelectionEvent event) {
          // Do Nothing - Required method
      }
    });
    
    
  }
  
  
  /**
   * @param list The SWT list that contains the Structural Features
   * @param value 
   */
  public void performAdd(final List list, final Object value) {
    
    if (value == null) {
      return;
    }
    
    EStructuralFeature eStructuralFeature = null;
    Collection<String> collection = new ArrayList<String>();
    int featureID = JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS;
     
    
    list.add( value.toString() );
    for ( int i=0; i<list.getItemCount(); i++ ) {
      collection.add( list.getItem( i ) );
    }

    checkResourcesElement();
    eStructuralFeature = this.candidateHosts.eClass().getEStructuralFeature( featureID );
    
    // Create association of EStructural Feature in the FeatureMap.
    
    this.eStructuralFeaturesMap.put( value.toString(), eStructuralFeature );
    this.candidateHosts.eSet(eStructuralFeature, collection);
    
    if (!this.resourcesType.eIsSet( eStructuralFeature ) ){
     this.resourcesType.setCandidateHosts( this.candidateHosts ); 
    }
     
        

   
    this.contentChanged();
    
    eStructuralFeature = null;
    collection = null;
    
  }
    
   
  
  protected void checkResourcesElement(){
    EStructuralFeature eStructuralFeature = this.jobDescriptionType.eClass()
    .getEStructuralFeature( JsdlPackage.JOB_DESCRIPTION_TYPE__RESOURCES );
    
    if (!this.jobDescriptionType.eIsSet( eStructuralFeature )){      
      this.jobDescriptionType.eSet( eStructuralFeature, this.resourcesType );


    }
  }
  
  
  
  protected void checkOSElement (){
    checkResourcesElement();    
    EStructuralFeature eStructuralFeature = this.resourcesType.eClass()
    .getEStructuralFeature( JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM );
    
    if (!this.resourcesType.eIsSet( eStructuralFeature )){      
      this.resourcesType.eSet( eStructuralFeature, this.operatingSystemType );
    }
  }
  
  
  
  protected void checkFileSystemElement(){
    checkResourcesElement();
    
    EStructuralFeature eStructuralFeature = this.resourcesType.eClass()
    .getEStructuralFeature( JsdlPackage.RESOURCES_TYPE__FILE_SYSTEM);
    
    Collection<FileSystemType> collection = new ArrayList<FileSystemType>();
    collection.add( this.fileSystemType );
    
    if (!this.resourcesType.eIsSet( eStructuralFeature )){      
      this.resourcesType.eSet( eStructuralFeature, collection );


    }
  }
  

  
  protected void checkCPUArch(){
    checkResourcesElement();
    
    EStructuralFeature eStructuralFeature = this.resourcesType.eClass()
    .getEStructuralFeature( JsdlPackage.RESOURCES_TYPE__CPU_ARCHITECTURE);
    
     
    if (!this.resourcesType.eIsSet( eStructuralFeature )){      
      this.resourcesType.eSet( eStructuralFeature, this.cpuArchitectureType );


    }
  }


  
  
  
  protected void performDelete(final List list, final String key){
    
    EStructuralFeature eStructuralFeature;
    
    /* Get EStructuralFeature */
     if (this.eStructuralFeaturesMap.containsKey( key ) ){
      eStructuralFeature = this.eStructuralFeaturesMap.get( key );
    
      /* Delete only Multi-Valued Elements */
        ((java.util.List<?>)this.candidateHosts.eGet(eStructuralFeature))
                                                                   .remove(key);
        list.remove( key );    
        
       
        if (list.getItemCount() == 0)
        {  
          EcoreUtil.remove( (EObject) this.resourcesType.eGet(eStructuralFeature) );         
        }
        this.removeFromMap( key );
        this.contentChanged();
    
    }
    
    eStructuralFeature = null;
  }
  
  
  
  private void removeFromMap (final Object key){
    
    this.eStructuralFeaturesMap.remove( key );
    
  }
    
   
  /**
   * Adapter interface to attach to the Operating System Type combo widget.
   * 
   * @param widget The SWT combo widget which is associated with the 
   * OperatingSystemType element of the JSDL document.
   */
  public void attachToOSType(final Combo widget){    
    
    Integer featureID = new Integer(JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM);
    this.comboFeaturesMap.put( featureID , widget );
        
    /* Populate the Combo Box with the CPU Architecture Literals */    
    EEnum cFEnum = JsdlPackage.Literals.OPERATING_SYSTEM_TYPE_ENUMERATION;
       for (int i=0; i<cFEnum.getELiterals().size(); i++){         
         widget.add( cFEnum.getEEnumLiteral( i ).toString() );
       }
       cFEnum = null;
          
        
    widget.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent e) {
        checkOSElement();        
        ResourcesTypeAdapter.this.operatingSystemTypeType
         .setOperatingSystemName(OperatingSystemTypeEnumeration
                                 .get( widget.getSelectionIndex() ) );
        
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
  public void attachToFileSystemName(final Text widget){
    Integer featureID = new Integer(JsdlPackage.FILE_SYSTEM_TYPE__NAME);
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
    
    Integer featureID = new Integer(JsdlPackage.FILE_SYSTEM_TYPE__DESCRIPTION);
    this.widgetFeaturesMap.put( featureID , widget );
    
    widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
//        checkFileSystemElement();
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
    
    Integer featureID = new Integer(JsdlPackage.FILE_SYSTEM_TYPE__MOUNT_POINT);
    this.widgetFeaturesMap.put( featureID , widget );
    
    widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
//        checkFileSystemElement();
        ResourcesTypeAdapter.this.fileSystemType.setMountPoint( widget.getText() );
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
   * @param combo The SWT combo widget which is associated with the Disk Space
   * Range Value element.
   */
  public void attachToFileSystemDiskSpace(final Text text, final Combo combo){
    
    this.widgetFeaturesMap.put (new Integer(JsdlPackage.FILE_SYSTEM_TYPE__DISK_SPACE)
                                , text );
    this.comboFeaturesMap.put( new Integer(JsdlPackage.FILE_SYSTEM_TYPE__DISK_SPACE)
                                , combo );
    
    /* Populate the Combo Box with the CPU Architecture Literals */    
//    EEnum cFEnum = JsdlPackage.Literals.B
//       for (int i=0; i<cFEnum.getELiterals().size(); i++){         
//         combo.add( cFEnum.getEEnumLiteral( i ).toString() );
//       }
//       cFEnum = null;
          
        
    combo.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent e) {
        //
      }

      public void widgetDefaultSelected(final SelectionEvent e) {
        //
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
  public void load() {
    
    this.isNotifyAllowed = false;
    Text widgetName = null;    
    List listName = null;
    Combo comboName = null;
    
    // Test if eObject is not empty.
    if(this.resourcesType != null) {
      EClass eClass = this.resourcesType.eClass();
             
      EList<EStructuralFeature> eAllStructuralFeaures = eClass.getEAllStructuralFeatures();
      
      for( EStructuralFeature eStructuralFeature : eAllStructuralFeaures ) {
            
       // EStructuralFeature eStructuralFeature = iter.next();
        
        int featureID = eStructuralFeature.getFeatureID();


        if (this.resourcesType.eIsSet( eStructuralFeature )){
        
        if (eStructuralFeature instanceof EReference) {
          
          
          switch( featureID ) {
            case JsdlPackage.RESOURCES_TYPE__ANY :
            break;            
            case JsdlPackage.RESOURCES_TYPE__CANDIDATE_HOSTS :{
              listName = this.listFeaturesMap.get( new Integer(featureID) );
              
              this.candidateHosts = (CandidateHostsType) this.resourcesType
                                                    .eGet( eStructuralFeature );
              
              EList<String> valueEList = this.candidateHosts.getHostName();
          
              
              Object eFeatureInstance = null;
              
              if(!this.adapterRefreshed) {
                
              for (Iterator<String> it = valueEList.iterator(); it.hasNext();){
                eFeatureInstance = it.next();              
                this.eStructuralFeaturesMap.put( eFeatureInstance.toString(),
                                                eStructuralFeature );
               
                  listName.add(eFeatureInstance.toString());
                } // End Iterator
                         
              } // Endif
            }
            break;
            case JsdlPackage.RESOURCES_TYPE__OPERATING_SYSTEM :{
             this.operatingSystemType = (OperatingSystemType) this.resourcesType
                                                    .eGet( eStructuralFeature );
             
             comboName = this.comboFeaturesMap.get( new Integer(featureID) );
             comboName.setText(this.operatingSystemType.getOperatingSystemType()
                               .getOperatingSystemName().getLiteral());
             
            widgetName = this.widgetFeaturesMap.get( new Integer
                        (JsdlPackage.DOCUMENT_ROOT__OPERATING_SYSTEM_VERSION) );
            widgetName.setText( this.operatingSystemType
                                                  .getOperatingSystemVersion());
             
            widgetName = this.widgetFeaturesMap
                    .get( new Integer(JsdlPackage.DOCUMENT_ROOT__DESCRIPTION) );
            
            widgetName.setText( this.operatingSystemType.getDescription());
            
            }
            break;            
            case JsdlPackage.RESOURCES_TYPE__FILE_SYSTEM: {
                            
              EList valueEList = (EList) this.resourcesType           
                                                    .eGet( eStructuralFeature );
              
              for (Iterator it = valueEList.iterator(); it.hasNext();){
                
                this.fileSystemType = (FileSystemType) it.next();
                
                widgetName = this.widgetFeaturesMap
                        .get( new Integer(JsdlPackage.FILE_SYSTEM_TYPE__NAME) );
                
                widgetName.setText( this.fileSystemType.getName() );
                
                widgetName = this.widgetFeaturesMap
                  .get(new Integer(JsdlPackage.FILE_SYSTEM_TYPE__DESCRIPTION) );
                
                widgetName.setText( this.fileSystemType.getDescription() );
                
                
                widgetName = this.widgetFeaturesMap
                 .get( new Integer(JsdlPackage.FILE_SYSTEM_TYPE__MOUNT_POINT) );
                
                widgetName.setText( this.fileSystemType.getMountPoint() );
                
                widgetName = this.widgetFeaturesMap
                  .get( new Integer(JsdlPackage.FILE_SYSTEM_TYPE__DISK_SPACE) );
                

                comboName = this.comboFeaturesMap
                .get (new Integer(JsdlPackage.DOCUMENT_ROOT__FILE_SYSTEM_TYPE));
                
                comboName.setText(this.fileSystemType.getFileSystemType()
                                  .getLiteral());
               
                
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
        }// endif EReference
        
        else {

             // Do Nothing    
        
        } // End Else
        
        } // End isSet()
        
      } // End Iterator
       
    } //end if null    
    
    this.isNotifyAllowed = true;
    
  }// end load()
  
} // End ResourcesTypeAdapter Class

