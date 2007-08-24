/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initialia development of the original code was made for
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
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

import eu.geclipse.jsdl.model.DataStagingType;
import eu.geclipse.jsdl.model.DocumentRoot;
import eu.geclipse.jsdl.model.JobDefinitionType;
import eu.geclipse.jsdl.model.JobDescriptionType;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.jsdl.model.SourceTargetType;



/**
 * @author nickl
 *
 */
public class DataStageTypeAdapter extends JsdlAdaptersFactory {
  
  private boolean isNotifyAllowed = true;
  private boolean adapterRefreshed = false;
  
  private DocumentRoot documentRoot = 
                                    JsdlFactory.eINSTANCE.createDocumentRoot();
  
  
  private DataStagingType dataStagingType =
                                  JsdlFactory.eINSTANCE.createDataStagingType();
  
  private SourceTargetType sourceTargetType = 
                                 JsdlFactory.eINSTANCE.createSourceTargetType();
  
  
  private Hashtable< Integer, TableViewer > tableFeaturesMap = new Hashtable< Integer, TableViewer >();
  private JobDescriptionType jobDescriptionType = JsdlFactory.eINSTANCE.createJobDescriptionType();
  
  
  
  /**
   * @param rootJsdlElement
   */
  public DataStageTypeAdapter (final EObject rootJsdlElement){

    getTypeForAdapter(rootJsdlElement);
    
  } // End Constructor
  
  
  
  protected void contentChanged(){
    
    if (this.isNotifyAllowed){
      fireNotifyChanged( null);
    }
  } // End void contenctChanged()
  
  
  
  private void  getTypeForAdapter(final EObject rootJsdlElement) {

    this.jobDescriptionType = ((JobDefinitionType ) rootJsdlElement).getJobDescription();
    this.jobDescriptionType.getDataStaging();
    
   } // End void getTypeforAdapter()
  
  
  
  /**
   * @param rootJsdlElement
   */
  public void setContent(final EObject rootJsdlElement) {
    
    this.adapterRefreshed = true;
    getTypeForAdapter( rootJsdlElement );
    
  } // End void setContent()
  
  
  
  /**
   * @param widget
   */
  public void attachToStageIn(final TableViewer widget) {
    
    Integer featureID = new Integer(JsdlPackage.DATA_STAGING_TYPE__SOURCE);
    this.tableFeaturesMap.put( featureID , widget );
    
    widget.addFilter(new ViewerFilter() {
      @Override
      public boolean select(final Viewer viewer, final Object parent, final Object element) {
        return ((DataStagingType) element).getSource()!= null;
      }
    });
    
  } // End void attactToStageIn()
  
  
  
  /**
   * @param widget
   */
  public void attachToStageOut(final TableViewer widget) {
    
    Integer featureID = new Integer(JsdlPackage.DATA_STAGING_TYPE__TARGET);
    this.tableFeaturesMap.put( featureID , widget );
    widget.addFilter(new ViewerFilter() {
      @Override
      public boolean select(final Viewer viewer, final Object parent, final Object element) {
        return ((DataStagingType) element).getTarget()!= null;
      }
    });
    
  } //End void attactToStageOut()
  
  
  
  /**
   * 
   * Adapter interface to attach to the DataStage Delete button
   * widget.
   *  
   * @param button The SWT Button that triggered the Delete event.
   * @param viewer The {@link org.eclipse.jface.viewers.TableViewer}
   * containing the element to be deleted.
   */
  public void attachToDelete(final Button button, final TableViewer viewer){

    button.addSelectionListener(new SelectionListener() {

      public void widgetSelected(final SelectionEvent event) {        
        performDelete(viewer);
      }

      public void widgetDefaultSelected(final SelectionEvent event) {
          // Do Nothing - Required method
      }
    });
    
    
  } // End void attachToDelete()
  
  
  
  
  /**
   * @param tableViewer
   * @param value
   */
  
  public void performAdd ( final TableViewer tableViewer, final Object[] value) {
    
    TableViewer stageInViewer = null;
    
    stageInViewer = this.tableFeaturesMap
                     .get( new Integer(JsdlPackage.DATA_STAGING_TYPE__SOURCE) );
    
    if (value[0] == null) {
      return;
    }
    
    EStructuralFeature eStructuralFeature = null;   
    Collection<DataStagingType> collection = new ArrayList<DataStagingType>();
    int featureID = JsdlPackage.JOB_DESCRIPTION_TYPE__DATA_STAGING;

    
    EList <DataStagingType> newInputList = (EList<DataStagingType>)tableViewer.getInput(); 
    
    if (newInputList == null) {
      newInputList = new BasicEList<DataStagingType>();
    }
     
    if (tableViewer == stageInViewer) {
        
      this.dataStagingType.setName(value[0].toString() );    
      this.sourceTargetType.setURI( value[1].toString() );
      this.dataStagingType.setSource( this.sourceTargetType );
      this.dataStagingType.setTarget( null );
      this.dataStagingType.setFileName( value[0].toString() );
      this.dataStagingType.setFilesystemName( value[0].toString() );
//    this.dataStagingType.setCreationFlag( CreationFlagEnumeration.APPEND_LITERAL );
//    this.dataStagingType.setDeleteOnTermination( false );
      
    }
    else {
      
      this.dataStagingType.setName(value[0].toString() );    
      this.sourceTargetType.setURI( value[1].toString() );
      this.dataStagingType.setSource( null );
      this.dataStagingType.setTarget( this.sourceTargetType );
      this.dataStagingType.setFileName( value[0].toString() );
      this.dataStagingType.setFilesystemName( value[0].toString() );
//    this.dataStagingType.setCreationFlag( CreationFlagEnumeration.APPEND_LITERAL );
//    this.dataStagingType.setDeleteOnTermination( false );
    }
      
    
    newInputList.add( this.dataStagingType );
    
    eStructuralFeature = this.jobDescriptionType.eClass().getEStructuralFeature( featureID );
    
    tableViewer.setInput(newInputList);
    
    for ( int i=0; i<tableViewer.getTable().getItemCount(); i++ ) {      
      collection.add( (DataStagingType) tableViewer.getElementAt( i ) );
    }
               

    this.jobDescriptionType.eSet(eStructuralFeature, collection);
    
    tableViewer.refresh();
   
    this.contentChanged();
    
    eStructuralFeature = null;
    newInputList = null;
    collection = null;
  
    
  } // end void performAdd()
  
  
  
  protected void checkDataStageElement(){
    
    EStructuralFeature eStructuralFeature = this.documentRoot.eClass()
    .getEStructuralFeature( JsdlPackage.JOB_DESCRIPTION_TYPE__DATA_STAGING );
    
    Collection<DataStagingType> collection = 
                                          new ArrayList<DataStagingType>();
    collection.add( this.dataStagingType);
        
    if (!this.jobDescriptionType.eIsSet( eStructuralFeature )){      
      this.jobDescriptionType.eSet( eStructuralFeature, collection );


    }
  } // end void checkDataStageElement()
  
  
  
  protected void performDelete(final TableViewer viewer ){
    
    IStructuredSelection structSelection 
                               = ( IStructuredSelection ) viewer.getSelection();
    
    Object feature = structSelection.getFirstElement();
    
         
      DataStagingType selectedDataStage = (DataStagingType) feature;    

      EcoreUtil.remove( selectedDataStage);
        
    
    viewer.refresh();
    contentChanged();
    
  } // End void performDelete()
  
  
  
  /**
   * @param tableViewer The SWT TableViewer that contains the Structural Features
   * @param value 
   */
  @SuppressWarnings("unchecked")
  public void performEdit(final TableViewer tableViewer, final Object[] value) {
    
    TableViewer stageInViewer = null;
    
    stageInViewer = this.tableFeaturesMap
                     .get( new Integer(JsdlPackage.DATA_STAGING_TYPE__SOURCE) );

    
    if (value[0] == null) {
      return;
    }
    
    EStructuralFeature eStructuralFeature;
    
    int featureID = JsdlPackage.JOB_DESCRIPTION_TYPE__DATA_STAGING;
    
    /*
     * Get the TableViewer Selection
     */
    IStructuredSelection structSelection 
                              = ( IStructuredSelection ) tableViewer.getSelection();
  
    /* If the selection is not null the Change the selected element */
    if (structSelection != null) {
      
      eStructuralFeature = this.jobDescriptionType.eClass()
                                            .getEStructuralFeature( featureID );

      Object feature = structSelection.getFirstElement();
    
    /* Get the Index of the Element that needs to be changed */
      int index = (( java.util.List<Object> )this.jobDescriptionType.eGet(eStructuralFeature))
                                                           .indexOf( feature  );
      
      
      
      this.dataStagingType = JsdlFactory.eINSTANCE.createDataStagingType();      
      this.sourceTargetType = JsdlFactory.eINSTANCE.createSourceTargetType();
     
      if (tableViewer == stageInViewer) {
        this.sourceTargetType.setURI( value[0].toString() );
        this.dataStagingType.setSource( this.sourceTargetType );  
        this.dataStagingType.setFileName( value[1].toString() );

      }
      else {
        this.dataStagingType.setFileName( value[0].toString() );
        this.sourceTargetType.setURI( value[1].toString() );
        this.dataStagingType.setTarget( this.sourceTargetType );  
      }
        
      
      
    /* Change the element. The element is located through it's index position
     * in the list.
     */
      (( java.util.List<Object> )this.jobDescriptionType.eGet( eStructuralFeature ))
            .set( index, this.dataStagingType );
  
    /* Refresh the table viewer and notify the editor that
     *  the page content has changed. 
     */
      tableViewer.refresh();
      contentChanged();
    
    }  
    
  }
  
   
  
  
  /**
   * 
   * This method populates the model content to the widgets registered with the
   * DataStageType adapter.
   */
  @SuppressWarnings("unchecked")
  public void load()
  {
    this.isNotifyAllowed = false;
    EObject jobDescrEObject  = this.jobDescriptionType;    
    TableViewer tableName = null;
    TableViewer tableName2 = null;
     
    /* Test if eObject is not empty. */
    
    if (jobDescrEObject != null) {
      EClass eClass = jobDescrEObject.eClass();
      
      for (Iterator<EStructuralFeature> iterRef = 
            eClass.getEAllStructuralFeatures().iterator(); iterRef.hasNext();) {
        
        EStructuralFeature eStructuralFeature = iterRef.next();
           
        int featureID =  eStructuralFeature.getFeatureID();        
              
        
        if (eStructuralFeature instanceof EReference) {
          
          /* Check if Feature is Set ? */
          if (jobDescrEObject.eIsSet( eStructuralFeature )) {
            switch( featureID ) {
              case JsdlPackage.JOB_DESCRIPTION_TYPE__DATA_STAGING:               
              {                     
                tableName = this.tableFeaturesMap
                    .get( new Integer(JsdlPackage.DATA_STAGING_TYPE__SOURCE) );
                tableName2 = this.tableFeaturesMap
                     .get(new Integer( JsdlPackage.DATA_STAGING_TYPE__TARGET ));
                
                       
          
                EList<EStructuralFeature > valueList = (EList<EStructuralFeature>) jobDescrEObject.eGet( eStructuralFeature );                

                  tableName.setInput( valueList );
                  tableName2.setInput( valueList);

               } // end case DATA_STAGING
                              
              break;
              default:
              break;
          } // end Switch()
            
          } // end_if eIsSet()
          
        } //End EReference
        
      } // End For
      
    } // End_if null
    
    /* 
     * If the refresh flag is set the unset it since we finished the refresh of
     * the widgets
     */
    if ( this.adapterRefreshed ) {
      this.adapterRefreshed = false;
    }
    
    this.isNotifyAllowed = true;
    
  }// end load()
  
  
  
  /**
   * @return TRUE if the adapter is empty. If it is empty, it means that there 
   * is no DataStage element in the JSDL document. 
   */ 
  public boolean isEmpty() {
    boolean status = false;

    if (!this.dataStagingType.equals( null )){       
      status = true;
    }
    
    return status;
  } // End isEmpty()
  
  
  
} // End Class
