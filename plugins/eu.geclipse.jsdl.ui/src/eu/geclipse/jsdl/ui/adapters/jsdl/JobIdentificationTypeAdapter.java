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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
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
 * JobIdentificationTypeAdapter Class
 * <p>
 * This class provides adapters for manipulating <b>Job Identification </b> 
 * elements  through the Job Definition Page of the JSDL editor. 
 * Supported JobIdentification elements are:
 * <p>
 * - JobName
 * <p>
 * - JobDescription
 * <p>
 * - JobAnnotation
 * <p>
 * - JobProject
 *
 */
public final class JobIdentificationTypeAdapter extends JsdlAdaptersFactory {
  
  protected JobIdentificationType jobIdentificationType = 
                            JsdlFactory.eINSTANCE.createJobIdentificationType();
  
  
  Hashtable< Integer, Text > widgetFeaturesMap = new Hashtable< Integer, Text >(); 
  HashMap< Integer, List > listFeaturesMap = new HashMap< Integer, List >();
  Hashtable<String, EStructuralFeature> eStructuralFeaturesMap 
                                 = new Hashtable<String, EStructuralFeature>();
    
  
  private boolean isNotifyAllowed = true;
  private boolean adapterRefreshed = false;
  
  
  /**
   * JobIdentificationTypeAdapter Class Constructor
   * 
   * @param rootJsdlElement The root element of a JSDL document.
   */
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
  
  
  
  /**
   * Allows to set the adapter's content on demand and not through the constructor.
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
   * Adapter interface to attach to the JobName text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * JobName element of the JSDL document.
   */
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
  
  
  
  /**
   * Adapter interface to attach to the JobDescription text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * JobDescription element of the JSDL document.
   */
  public void attachToJobDescription(final Text widget){    
    
    Integer featureID = new Integer (JsdlPackage.JOB_IDENTIFICATION_TYPE__DESCRIPTION);
    this.widgetFeaturesMap.put( featureID , widget );
    
    widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        JobIdentificationTypeAdapter.this.jobIdentificationType.setDescription(widget.getText());
        contentChanged();
          
        }
      } );       

  }
  
  
  
  /**
   * Adapter interface to attach to the JobProject list widget.
   * 
   * @param widget The SWT list widget which is associated with the 
   * JobProject element of the JSDL document.
   */
  public void attachToJobProject(final List widget){  
    
    Integer featureID = new Integer (JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_PROJECT);
    this.listFeaturesMap.put( featureID, widget );
    
  }
  

  /**
   * Adapter interface to attach to the JobAnnotation list widget.
   * 
   * @param widget The SWT list widget which is associated with the 
   * JobAnnotation element of the JSDL document.
   */
  public void attachToJobAnnotation(final List widget){
    
    Integer featureID = new Integer(JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_ANNOTATION);
    this.listFeaturesMap.put( featureID, widget );
    
  }
  
 
  
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
        //Do Nothing
    
      }
    });    
    
  }  
  
  
  
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
  
  
  
  /**
   * @param list The SWT list that contains the Structural Features
   * @param name The name of the SWT list.
   * @param value 
   */
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
  
  
  
  /**
   * This method populates the model content to the widgets registered with the
   * JobIdentificationType adapter.
   */
  public void load()
  {
    
    this.isNotifyAllowed = false;
    EObject object = this.jobIdentificationType;
    Text widgetName = null;
    List listName = null;
    
    // Test if eObject is not empty.
    if(object != null) {
      EClass eClass = object.eClass();
             
        
        EList<EStructuralFeature> allEStructuralFeatures =
                                             eClass.getEAllStructuralFeatures();
        for( EStructuralFeature eStructuralFeature : allEStructuralFeatures) {
      
        
        //Get Attribute Value.
        Object value = object.eGet( eStructuralFeature );        
             
        Integer featureID =  new Integer(eStructuralFeature.getFeatureID());
      
        //Check if Attribute has any value
        if (object.eIsSet( eStructuralFeature )
            && this.widgetFeaturesMap.containsKey( featureID )){   
          
        
          if (eStructuralFeature.getUpperBound() == 1){ 
          
           widgetName = this.widgetFeaturesMap.get( featureID );
        
           
             if (eStructuralFeature.getFeatureID() 
                                   != JsdlPackage.JOB_IDENTIFICATION_TYPE__ANY){
               widgetName.setText(value.toString());
             } //end if "ANY"
           }//end if UpperBound == 1                        
              
        // Add Multiplicity-Many Elements to attached Lists.
        else if (eStructuralFeature.getUpperBound()  
                                 == ETypedElement.UNBOUNDED_MULTIPLICITY) {
                        
            listName = this.listFeaturesMap.get( featureID );
                     
            EList valueArray = (EList) value;       
            
             
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
  
    
  
  /**
   * @return TRUE if the adapter is empty. If it is empty, it means that there 
   * is no JobDefinition element in the JSDL document. 
   */ 
  public boolean isEmpty(){
    boolean status = false;

    if (!this.jobIdentificationType.equals( null )){       
      status = true;
    }
    
    return status;
  }
  
} // End Class
