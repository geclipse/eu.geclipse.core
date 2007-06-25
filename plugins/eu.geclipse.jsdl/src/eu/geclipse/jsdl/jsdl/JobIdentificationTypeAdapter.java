/******************************************************************************
  * Copyright (c) 2007 g-Eclipse consortium
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License v1.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v10.html
  *
  * Initialial development of the original code was made for
  * project g-Eclipse founded by European Union
  * project number: FP6-IST-034327  http://www.geclipse.eu/
  *
  * Contributor(s):
  *     UCY (http://www.ucy.cs.ac.cy)
  *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
  *
  *****************************************************************************/
package eu.geclipse.jsdl.jsdl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.jsdl.model.JobIdentificationType;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.jsdl.model.JsdlPackage;



/**
 * @author nickl
 *
 */

public class JobIdentificationTypeAdapter extends JsdlAdaptersFactory {
  
  protected JobIdentificationType jobIdentificationType = 
                            JsdlFactory.eINSTANCE.createJobIdentificationType();
  
  
  Hashtable< Integer, Text > widgetFeaturesMap = new Hashtable< Integer, Text >();
  //Hashtable< Integer, TableViewer > listFeaturesMap = new Hashtable< Integer, TableViewer >();
  HashMap< Integer, List > listFeaturesMap = new HashMap< Integer, List >();
  Hashtable<String, EStructuralFeature> eStructuralFeaturesMap 
                                 = new Hashtable<String, EStructuralFeature>();
    
  
  private boolean isNotifyAllowed = true;
  private boolean adapterRefreshed = false;
  
  
  public JobIdentificationTypeAdapter(final EObject rootJsdlElement) {
    
    getTypeForAdapter(rootJsdlElement);
      
  }  // End Constructor
  
  
  private void  getTypeForAdapter(final EObject rootJsdlElement){
        
    TreeIterator<EObject> iterator = rootJsdlElement.eAllContents();
    
    while ( iterator.hasNext (  )  )  {  
   
      EObject testType = iterator.next();
          
      if ( testType instanceof JobIdentificationType ) {
        this.jobIdentificationType = (JobIdentificationType) testType;        
      } //endif JobIdentificationType
      
    } //End while   

   } // End getTypeforAdapter
  
  
  
  public void setContent(final EObject rootJsdlElement){
    this.adapterRefreshed = true;
    getTypeForAdapter( rootJsdlElement );
  }
  
  
  protected void contentChanged(){
    if (this.isNotifyAllowed){
      fireNotifyChanged( null);
    }
  }
  
  
  
  public void attachToJobName(final Text widget){ 
    Integer featureID = new Integer (JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_NAME);
    this.widgetFeaturesMap.put( featureID, widget );
        
    widget.addModifyListener( new ModifyListener() {
      
    public void modifyText( final ModifyEvent e ) {
      JobIdentificationTypeAdapter.this.jobIdentificationType.setJobName(widget.getText());
      contentChanged();
        
      }
    } );
     
  }
  
  
  
  public void attachToJobDescription(final Text widget){    
    Integer featureID = new Integer (JsdlPackage.JOB_IDENTIFICATION_TYPE__DESCRIPTION);
    this.widgetFeaturesMap.put( featureID , widget );
    
    widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        JobIdentificationTypeAdapter.this.jobIdentificationType.setDescription(widget.getText());
        contentChanged();
          
        }
      } );
        
//     widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
//      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
//        jobIdentificationType.setDescription(widget.getText());    
//      }
//      public void focusGained(final FocusEvent e ) { }
//     
//    } );
  }
  
  public void attachToJobProject(final List widget){  
    Integer featureID = new Integer (JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_PROJECT);
    this.listFeaturesMap.put( featureID, widget );        
  }
  
  
//  public void attachToJobAnnotation(final TableViewer widget){    
//    this.listFeaturesMap.put( JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_ANNOTATION
//                                , widget );
//        
//     widget.getTable().addFocusListener( new org.eclipse.swt.events.FocusListener() {
//      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
//        // List Add Functionality    
//      }
//      public void focusGained(final FocusEvent e ) { }
//     
//    } );
//  }
  
  
  
  public void attachToJobAnnotation(final List widget){
    
    Integer featureID = new Integer(JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_ANNOTATION);
    this.listFeaturesMap.put( featureID, widget );
  }
  
  
  
  public void attachToDelete(final Button button, final List list){
    button.addSelectionListener(new SelectionListener() {

      public void widgetSelected(final SelectionEvent event) { 
        performDelete(list, list.getItem( list.getSelectionIndex() ) );
      }

      public void widgetDefaultSelected(final SelectionEvent event) {

        
      }
    });
    
    
  }
  
  
//  private void performDelete(final TableViewer list, final ISelection key){
//    
//
//    
//    EStructuralFeature eStructuralFeature = (EStructuralFeature) list.getInput();
//   
//    /* Get EStructuralFeature */
////     if (this.eStructuralFeaturesMap.containsKey( key ) ){
////      eStructuralFeature = this.eStructuralFeaturesMap.get( key );
////    
//    /* Delete only Multi-Valued Elements */
//      if (FeatureMapUtil.isMany(this.jobIdentificationType, eStructuralFeature)){
//        
//        ((java.util.List<?>)this.jobIdentificationType.eGet(eStructuralFeature))
//                                                                   .remove(key);
//      }
//    
//    //}
//  
//    list.remove( key );   
//    
//    eStructuralFeature = null;
//  }
  
  
  
  protected void performDelete(final List list, final String key){
    

    
    EStructuralFeature eStructuralFeature;
   
    /* Get EStructuralFeature */
     if (this.eStructuralFeaturesMap.containsKey( key ) ){
      eStructuralFeature = this.eStructuralFeaturesMap.get( key );
    
    /* Delete only Multi-Valued Elements */
      if (FeatureMapUtil.isMany(this.jobIdentificationType, eStructuralFeature)){        
        
        ((java.util.List<?>)this.jobIdentificationType.eGet(eStructuralFeature))
                                                                   .remove(key);
        this.removeFromMap( key );
        this.contentChanged();
      }
    
    }
  
    list.remove( key );   
    
    eStructuralFeature = null;
  }
  
  
  
  public void performAdd(final List list, final String name, final Object value) {

    if (value == null) {
      return;
    }
    
    EStructuralFeature eStructuralFeature = null;
    Collection<String> collection = new ArrayList<String>();
    int featureID;
    
    if (name == "lstJobAnnotation"){ //$NON-NLS-1$
      featureID = JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_ANNOTATION;
    }
    else{
      featureID = JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_PROJECT;
    }
    
    // Get EStructural Feature.
    eStructuralFeature = this.jobIdentificationType.eClass().getEStructuralFeature( featureID );
    // Create a new entry in the FeatureMap so as to maintain associations.
    this.eStructuralFeaturesMap.put( value.toString(), eStructuralFeature );
    
    list.add( value.toString() );
    for ( int i=0; i<list.getItemCount(); i++ ) {
      collection.add( list.getItem( i ) );
    }
   
    
    this.jobIdentificationType.eSet(eStructuralFeature, collection);
    
   
    this.contentChanged();
    
    eStructuralFeature = null;
    collection = null;
    
  }
  
  
  public void load()
  {
    this.isNotifyAllowed = false;
    EObject object = this.jobIdentificationType;
    Text widgetName = null;
//    TableViewer listName = null;
    List listName = null;
    
    // Test if eObject is not empty.
    if(object != null) {
      EClass eClass = object.eClass();
             
        
      for (Iterator iter = eClass.getEAllStructuralFeatures().iterator(); iter.hasNext();) {      
        EStructuralFeature eStructuralFeature = (EStructuralFeature) iter.next();
      
        
        //Get Attribute Value.
        Object value = object.eGet( eStructuralFeature );        
             
        Integer featureID =  new Integer(eStructuralFeature.getFeatureID());
      
        //Check if Attribute has any value
        if (object.eIsSet( eStructuralFeature )){   
          
        
          if (eStructuralFeature.getUpperBound() == 1){ 
          
           widgetName = this.widgetFeaturesMap.get( featureID );
        
           
             if (eStructuralFeature.getFeatureID() 
                     != JsdlPackage.JOB_IDENTIFICATION_TYPE__ANY){
               widgetName.setText(value.toString());
             } //end if "any"
           }//end if UpperBound == 1                        
              
        // Add Multiplicity-Many Elements to attached Lists.
        else if (eStructuralFeature.getUpperBound()  
                                 == EStructuralFeature.UNBOUNDED_MULTIPLICITY) {
                        
            listName = this.listFeaturesMap.get( featureID );
                     
            EList valueArray = (EList) value;       
            
//            listName.setInput( valueArray);
            
             
            Object eFeatureInst = null;
          
            if(!this.adapterRefreshed) {
            for (Iterator it = valueArray.iterator(); it.hasNext();){
                            
              eFeatureInst = it.next();             
              this.eStructuralFeaturesMap.put( eFeatureInst.toString(),
                                              eStructuralFeature );
              
              listName.add( eFeatureInst.toString());
                       
            } // End for
            } // End if
          }// End UNBOUNDED_MULTIPLICITY
          
        else {
          //Do Nothing
        }
          
        }
      } //end for eIsSet()
    } //end if null
    this.isNotifyAllowed = true;
  } // End void load()
  
  
  
  private void removeFromMap (final Object key){
    this.eStructuralFeaturesMap.remove( key );
  }
  
    
  
  public boolean isEmpty(){
    boolean status = false;

    if (!this.jobIdentificationType.equals( null )){       
      status = true;
    }
    
    return status;
  }
  
} // End Class
