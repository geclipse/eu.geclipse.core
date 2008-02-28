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
package eu.geclipse.batch.ui.internal.adapters;

import java.util.HashMap;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Spinner;

import eu.geclipse.batch.model.qdl.IntegerBoundaryType;
import eu.geclipse.batch.model.qdl.IntegerExactType;
import eu.geclipse.batch.model.qdl.IntegerRangeValueType;
import eu.geclipse.batch.model.qdl.QdlFactory;
import eu.geclipse.batch.model.qdl.QdlPackage;
import eu.geclipse.batch.model.qdl.QueueType;


/**
 * @author nickl
 *
 */
public class AdvancedQueueAdapter extends QdlAdaptersFactory {
  
  
  protected HashMap<Integer, Spinner>spinnerWidgetMap = new HashMap<Integer, Spinner>();
  protected HashMap<Integer, Button>buttonWidgetMap = new HashMap<Integer, Button>();
  protected QueueType queue = QdlFactory.eINSTANCE.createQueueType();
  
  
  private boolean adapterRefreshed = false;
  private boolean isNotifyAllowed = true;
  
  /**
   * AdvancedQueueAdapter class default constructor.
   * 
   * Get's as parameter the input type for this adapter {@link QueueType}
   * 
   * @param queue
   */
  public AdvancedQueueAdapter( final QueueType queue ) {
    
    getTypeForAdapter (queue);
    
  } 
  
  
  protected void contentChanged(){
    if ( this.isNotifyAllowed ){
      
      fireNotifyChanged( null);
    }
    
  } // End void contentChanged()
  
  
  
  /**
   * Allows to set the adapter's content on demand and not through the constructor.
   * 
   * @param q
   */
  public void setContent(final QueueType q){
    
    getTypeForAdapter( q );
    
  } // End void setContent()
  
  
  
  /*
   * Get the Queue Element from the QDL Element.
   */
   private void  getTypeForAdapter(final QueueType q) {
     
        this.queue = q;
        
   } // End void getTypeForAdapter()
   
   
   
   protected EObject checkProxy(final EObject refEObject) {
     
     EObject eObject = refEObject;
     
     if (eObject != null && eObject.eIsProxy() ) {
      
       eObject =  EcoreUtil.resolve( eObject, 
                                   AdvancedQueueAdapter.this.queue );
     }
         
     return eObject;
     
   }
   
   
   /**
    * Adapter interface to attach to the Priority Spinner widget.
    * 
    * @param spinner The {@link Spinner} which is associated with the 
    * PRIORITY element of the QDL document.
    */
   public void attachPrioritySpinner( final Spinner spinner ){
     
     Integer featureID = new Integer (QdlPackage.QUEUE_TYPE__PRIORITY);
     this.spinnerWidgetMap.put( featureID,spinner );
     
     spinner.addModifyListener( new ModifyListener(){
       IntegerRangeValueType integerRangeValueType = QdlFactory.eINSTANCE.createIntegerRangeValueType();
       IntegerBoundaryType integerBoundaryType = QdlFactory.eINSTANCE.createIntegerBoundaryType();
      public void modifyText( final ModifyEvent e ) {
        
        this.integerBoundaryType.setValue( Integer.valueOf( spinner.getSelection() ) );
        this.integerBoundaryType = (IntegerBoundaryType) checkProxy( this.integerBoundaryType );
        this.integerRangeValueType = (IntegerRangeValueType) checkProxy( this.integerRangeValueType );
        this.integerRangeValueType.setUpperBoundedRange( this.integerBoundaryType );
        AdvancedQueueAdapter.this.queue.setPriority( this.integerRangeValueType );
        contentChanged();
        
      }
       
     });
     
   }
   
   
   
   /**
    * Adapter interface to attach to the Priority Spinner widget.
    * 
    * @param spinner The {@link Spinner} which is associated with the 
    * RUNNING_JOBS element of the QDL document.
    * @param button The {@link Button} which is associated with the 
    * unlimited value.
    */
   public void attachRunningJobsSpinner( final Spinner spinner,
                                         final Button button ){
     
     Integer featureID = new Integer (QdlPackage.QUEUE_TYPE__RUNNING_JOBS);
     this.spinnerWidgetMap.put( featureID,spinner );
     this.buttonWidgetMap.put( featureID, button );
     
     spinner.addModifyListener( new ModifyListener(){
       IntegerRangeValueType integerRangeValueTupe = QdlFactory.eINSTANCE.createIntegerRangeValueType();
       IntegerBoundaryType integerBoundaryType = QdlFactory.eINSTANCE.createIntegerBoundaryType();
      public void modifyText( final ModifyEvent e ) {
        
        this.integerBoundaryType.setValue( Integer.valueOf( spinner.getSelection() ) );
        this.integerBoundaryType = (IntegerBoundaryType) checkProxy( this.integerBoundaryType );
        this.integerRangeValueTupe = (IntegerRangeValueType) checkProxy( this.integerRangeValueTupe );
        this.integerRangeValueTupe.setUpperBoundedRange( this.integerBoundaryType );
        AdvancedQueueAdapter.this.queue.setRunningJobs( this.integerRangeValueTupe );
        contentChanged();
        
      }
       
     });
     
   }
   
   
   /**
    * Adapter interface to attach to the Priority Spinner widget.
    * 
    * @param spinner The {@link Spinner} which is associated with the 
    * JOBS_IN_QUEUE element of the QDL document.
    * @param button The {@link Button} which is associated with the 
    * unlimited value.
    */
   public void attachJobsInQueueSpinner( final Spinner spinner,
                                         final Button button){
     
     Integer featureID = new Integer (QdlPackage.QUEUE_TYPE__JOBS_IN_QUEUE);
     this.spinnerWidgetMap.put( featureID,spinner );
     this.buttonWidgetMap.put( featureID, button );
     
     spinner.addModifyListener( new ModifyListener(){
       IntegerRangeValueType integerRangeValueTupe = QdlFactory.eINSTANCE.createIntegerRangeValueType();
       IntegerBoundaryType boundaryType = QdlFactory.eINSTANCE.createIntegerBoundaryType();
      public void modifyText( final ModifyEvent e ) {
        
        this.boundaryType.setValue( Integer.valueOf( spinner.getSelection() ) );
        this.boundaryType = (IntegerBoundaryType) checkProxy( this.boundaryType );
        this.integerRangeValueTupe = (IntegerRangeValueType) checkProxy( this.integerRangeValueTupe );
        this.integerRangeValueTupe.setUpperBoundedRange( this.boundaryType );
        AdvancedQueueAdapter.this.queue.setJobsInQueue( this.integerRangeValueTupe );
        contentChanged();
        
      }
       
     });
     
   }
   
   
   
   /**
    * Adapter interface to attach to the Priority Spinner widget.
    * 
    * @param spinner The {@link Spinner} which is associated with the 
    * ASSIGNED_RESOURCES element of the QDL document.
    */
   public void attachAssignedResourcesSpinner( final Spinner spinner ){
     
     Integer featureID = new Integer (QdlPackage.QUEUE_TYPE__ASSIGNED_RESOURCES);
     this.spinnerWidgetMap.put( featureID,spinner );
     
     spinner.addModifyListener( new ModifyListener(){
       IntegerRangeValueType integerRangeValueTupe = QdlFactory.eINSTANCE.createIntegerRangeValueType();
       IntegerExactType exactType = QdlFactory.eINSTANCE.createIntegerExactType();
      public void modifyText( final ModifyEvent e ) {
        
        this.exactType.setValue( Integer.valueOf( spinner.getSelection() ) );
        this.exactType = (IntegerExactType) checkProxy( this.exactType );
        this.integerRangeValueTupe = (IntegerRangeValueType) checkProxy( this.integerRangeValueTupe );
        this.integerRangeValueTupe.getExact().add( this.exactType );
        AdvancedQueueAdapter.this.queue.setAssignedResources( this.integerRangeValueTupe );
        contentChanged();
        
      }
       
     });
     
   }
   
   
   /**
   * Method to set the Maximum Running Jobs in a Queue to the maximum value an integer number
   * can be have, 2<sup>31</sup>-1.
   * 
   */
  public void setUnlimitedRunningJobs() {
     
     IntegerRangeValueType integerRangeValueTupe = QdlFactory.eINSTANCE.createIntegerRangeValueType();
     IntegerBoundaryType integerBoundaryType = QdlFactory.eINSTANCE.createIntegerBoundaryType();
     
     integerBoundaryType.setValue( Integer.valueOf( Integer.MAX_VALUE ) );
     integerBoundaryType = (IntegerBoundaryType) checkProxy( integerBoundaryType );
     integerRangeValueTupe = (IntegerRangeValueType) checkProxy( integerRangeValueTupe );
     integerRangeValueTupe.setUpperBoundedRange( integerBoundaryType );
     AdvancedQueueAdapter.this.queue.setRunningJobs( integerRangeValueTupe );
     contentChanged();
     
   }
   
   
   
  /**
   * Method to set the Maximum Jobs in a Queue to the maximum value an integer number
   * can be have, 2<sup>31</sup>-1.
   * 
   */
  public void setUnlimitedJobsInQueue() {
     
     IntegerRangeValueType integerRangeValueType = QdlFactory.eINSTANCE.createIntegerRangeValueType();
     IntegerBoundaryType integerBoundaryType = QdlFactory.eINSTANCE.createIntegerBoundaryType();
     
     integerBoundaryType.setValue( Integer.valueOf( Integer.MAX_VALUE ) );
     integerBoundaryType = (IntegerBoundaryType) checkProxy( integerBoundaryType );
     integerRangeValueType = (IntegerRangeValueType) checkProxy( integerRangeValueType );
     integerRangeValueType.setUpperBoundedRange( integerBoundaryType );
     AdvancedQueueAdapter.this.queue.setJobsInQueue( integerRangeValueType );
     contentChanged();
     
   }
   
   
   
   /**
    * Loads the Queue Attributes from the QDL file to the Editor Page
    */
   public void load() {

     this.isNotifyAllowed = false;
     Spinner spinner = null;
     Button button = null;
     
     IntegerBoundaryType integerBoundaryType = null;
     IntegerExactType integerExactType = null;
     IntegerRangeValueType integerRangeValueType = null;
     
     
     // Test if eObject is not empty.
     if ( this.queue != null ) {
       EClass eClass = this.queue.eClass();
       int featureID;
       
       EList<EStructuralFeature> eAllStructuralFeaures = eClass.getEAllStructuralFeatures();
       
       for( EStructuralFeature eStructuralFeature : eAllStructuralFeaures ) {
        
         featureID = eStructuralFeature.getFeatureID();

         if ( this.queue.eIsSet( eStructuralFeature ) ){
         
           if (eStructuralFeature instanceof EReference) {
             
             switch( featureID ) {
               case QdlPackage.QUEUE_TYPE__PRIORITY: {
                 if (this.queue.getCPUTimeLimit() != null ){
                   spinner = this.spinnerWidgetMap.get( new Integer(QdlPackage.QUEUE_TYPE__PRIORITY) );
                   
                   integerRangeValueType = this.queue.getPriority();
                   if ( integerRangeValueType.getUpperBoundedRange() != null ) {
                     integerBoundaryType = integerRangeValueType.getUpperBoundedRange();
                   }
                   else{
                     integerBoundaryType = integerRangeValueType.getLowerBoundedRange();
                   }
                   spinner.setSelection( integerBoundaryType.getValue() );
                   
                 }
               }                  
               break;
               case QdlPackage.QUEUE_TYPE__RUNNING_JOBS: {
                 if (this.queue.getCPUTimeLimit() != null ){
                   spinner = this.spinnerWidgetMap.get( new Integer(QdlPackage.QUEUE_TYPE__RUNNING_JOBS) );
                   button = this.buttonWidgetMap.get( new Integer(QdlPackage.QUEUE_TYPE__RUNNING_JOBS) );
                   
                   integerRangeValueType = this.queue.getRunningJobs();
                   if ( integerRangeValueType.getUpperBoundedRange() != null ) {
                     integerBoundaryType = integerRangeValueType.getUpperBoundedRange();
                   }
                   else{
                     integerBoundaryType = integerRangeValueType.getLowerBoundedRange();
                   }
                   int value = integerBoundaryType.getValue();
                   spinner.setSelection( value );
                   
                   if ( value == Integer.MAX_VALUE) {
                     spinner.setEnabled( false );
                     button.setSelection( true );
                     
                     
                   }
                   
                 }
               }                  
               break;
               case QdlPackage.QUEUE_TYPE__JOBS_IN_QUEUE: {
                 if (this.queue.getCPUTimeLimit() != null ){
                   spinner = this.spinnerWidgetMap.get( new Integer(QdlPackage.QUEUE_TYPE__JOBS_IN_QUEUE) );
                   button = this.buttonWidgetMap.get( new Integer(QdlPackage.QUEUE_TYPE__JOBS_IN_QUEUE) );
                   
                   integerRangeValueType = this.queue.getJobsInQueue();
                   if ( integerRangeValueType.getUpperBoundedRange() != null ) {
                     integerBoundaryType = integerRangeValueType.getUpperBoundedRange();
                   }
                   else{
                     integerBoundaryType = integerRangeValueType.getLowerBoundedRange();
                   }
                   
                   int value = integerBoundaryType.getValue();
                   spinner.setSelection( value );
                   
                   if ( value == Integer.MAX_VALUE) {
                     spinner.setEnabled( false );
                     button.setSelection( true );
                   }                     
                   
                 }
               }                  
               break;
               case QdlPackage.QUEUE_TYPE__ASSIGNED_RESOURCES: {
                 if (this.queue.getCPUTimeLimit() != null ){
                   spinner = this.spinnerWidgetMap.get( new Integer(QdlPackage.QUEUE_TYPE__ASSIGNED_RESOURCES) );                      
                   integerRangeValueType = this.queue.getAssignedResources();                   
                   if ( integerRangeValueType.getUpperBoundedRange() != null ) {
                     integerBoundaryType = integerRangeValueType.getUpperBoundedRange();
                     spinner.setSelection( integerBoundaryType.getValue() );
                   }
                   else if ( integerRangeValueType.getLowerBoundedRange() != null ){
                     integerBoundaryType = integerRangeValueType.getLowerBoundedRange();
                     spinner.setSelection( integerBoundaryType.getValue() );
                   }
                   else{
                     integerExactType = integerRangeValueType.getExact().get( 0 );
                   }
                   spinner.setSelection( integerExactType.getValue() );                 }
               }                  
               break;
               default:
               break;
             } // end switch
             
           } // end_if (eStructuralFeature instanceof EReference)
           
           else {             
             // Do Nothing             
           } //end else
           
         }
       }
       
     } // end_if ( this.queue != null )
     
     this.isNotifyAllowed = true;
     
     if ( this.adapterRefreshed ) {
       this.adapterRefreshed = false;
     }
     
     
    } // end void load()
   
}

