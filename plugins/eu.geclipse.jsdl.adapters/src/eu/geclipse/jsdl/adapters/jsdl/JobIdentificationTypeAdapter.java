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
package eu.geclipse.jsdl.adapters.jsdl;

import java.util.Hashtable;
import java.util.Iterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.jsdl.JobIdentificationType;
import eu.geclipse.jsdl.JsdlFactory;
import eu.geclipse.jsdl.JsdlPackage;


/**
 * @author nickl
 *
 */

public class JobIdentificationTypeAdapter {
  
  Hashtable< Integer, Text > widgetFeaturesMap = new Hashtable< Integer, Text >();
  Hashtable< Integer, List > listFeaturesMap = new Hashtable< Integer, List >();   
    
  private JobIdentificationType jobIdentificationType = 
                            JsdlFactory.eINSTANCE.createJobIdentificationType();
                                
  
  public JobIdentificationTypeAdapter(final EObject rootJsdlElement) {
    getTypeForAdapter(rootJsdlElement);
      
  }  // End Constructor
  
  
  private void  getTypeForAdapter(final EObject rootJsdlElement){
    
    TreeIterator iterator = rootJsdlElement.eAllContents();
    
    while ( iterator.hasNext (  )  )  {  
   
      EObject testType = (EObject) iterator.next();
          
      if ( testType instanceof JobIdentificationType ) {
        this.jobIdentificationType = (JobIdentificationType) testType;        
      } //endif JobIdentificationType
      
    } //End while   

   } // End getTypeforAdapter
  
  public void setContent(final EObject rootJsdlElement){
    getTypeForAdapter( rootJsdlElement );
  }
  
  public void attachToJobName(final Text widget){    
    this.widgetFeaturesMap.put( JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_NAME
                                , widget );
        
     widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        jobIdentificationType.setJobName(widget.getText());    
      }
      public void focusGained(final FocusEvent e ) { }
     
    } );
  }
  
  public void attachToJobDescription(final Text widget){    
    this.widgetFeaturesMap.put( JsdlPackage.JOB_IDENTIFICATION_TYPE__DESCRIPTION
                                , widget );
        
     widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        jobIdentificationType.setDescription(widget.getText());    
      }
      public void focusGained(final FocusEvent e ) { }
     
    } );
  }
  
  public void attachToJobProject(final List widget){    
    this.listFeaturesMap.put( JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_PROJECT
                                , widget );
        
     widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        // List Add Functionality    
      }
      public void focusGained(final FocusEvent e ) { }
     
    } );
  }
  
  
  public void attachToJobAnnotation(final List widget){    
    this.listFeaturesMap.put( JsdlPackage.JOB_IDENTIFICATION_TYPE__JOB_ANNOTATION
                                , widget );
        
     widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        // List Add Functionality    
      }
      public void focusGained(final FocusEvent e ) { }
     
    } );
  }
  
  
  public void load()
  {
    EObject object = this.jobIdentificationType;
    Text widgetName = null;
    List listName = null;
    
    // Test if eObject is not empty.
    if(object != null) {
      EClass eClass = object.eClass();
             
        
      for (Iterator iter = eClass.getEAllAttributes().iterator(); iter.hasNext();) {      
        EAttribute attribute = (EAttribute) iter.next();
      
        //if (attribute.getUpperBound() == 1){ 
        
        //Get Attribute Value.
        Object value = object.eGet( attribute );        
             
        Integer featureID = attribute.getFeatureID();
      
        //Check if Attribute has any value
        if (object.eIsSet( attribute )){   
          
        
          if (attribute.getUpperBound() == 1){ 
          
           widgetName = this.widgetFeaturesMap.get( featureID );
        
           
             if (attribute.getName().toString() != "any"){ //$NON-NLS-1$
               widgetName.setText(value.toString());
             } //end if "any"
           }//end if UpperBound == 1                        
              
        // Add Multiplicity-Many Elements to attached Lists.
        else if (attribute.getUpperBound() == EStructuralFeature.UNBOUNDED_MULTIPLICITY) {
                        
            listName = this.listFeaturesMap.get( featureID );
                      
            EList valueArray = (EList) value;

            for (Iterator iterA = valueArray.iterator(); iterA.hasNext();){
                 listName.add( iterA.next().toString() );
                       
            } // End for

          }// End UNBOUNDED_MULTIPLICITY
          
        }
      } //end for eIsSet()
    } //end if null
  } // End void load()
  
  
  
  public boolean isEmpty(){
    boolean status = false;

    if (!this.jobIdentificationType.equals( null )){       
      status = true;
    }
    
    return status;
  }
  
} // End Class
