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
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.jsdl.JobIdentificationType;
import eu.geclipse.jsdl.JsdlFactory;
import eu.geclipse.jsdl.JsdlPackage;


/**
 * @author nickl
 *
 */

public class JobIdentificationTypeAdapter {
  
  private JobIdentificationType jobIdentificationType = 
                            JsdlFactory.eINSTANCE.createJobIdentificationType();
                                
  Hashtable< Integer, Text > widgetFeaturesMap = new Hashtable< Integer, Text >();
  
  
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
      public void focusGained( FocusEvent e ) { }
     
    } );
  }
  
  public void attachToJobDescription(final Text widget){    
    this.widgetFeaturesMap.put( JsdlPackage.JOB_IDENTIFICATION_TYPE__DESCRIPTION
                                , widget );
        
     widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        jobIdentificationType.setDescription(widget.getText());    
      }
      public void focusGained( FocusEvent e ) { }
     
    } );
  }
  
  public void load()
  {
    EObject object = this.jobIdentificationType;
    Text widgetName = null;
    //EDataType dataType = null;
    
    // Test if eObject is not empty.
    if(object != null) {
      EClass eClass = object.eClass();
             
        
      for (Iterator iter = eClass.getEAllAttributes().iterator(); iter.hasNext();) {      
        EAttribute attribute = (EAttribute) iter.next();
                                               
        //Get Attribute Value.
        Object value = object.eGet( attribute );        
             
        Integer featureID = attribute.getFeatureID();
      
        //Check if Attribute has any value
        if (object.eIsSet( attribute )){          
           widgetName = this.widgetFeaturesMap.get( featureID );
                 
         //FIXME - any check should be removed..check cause of it.
           if (attribute.getName().toString() != "any"){ //$NON-NLS-1$
             widgetName.setText(value.toString());
         } //end if
                   
        } //end if
      } //end for
    } //end if
  } // End void load()
  
  
  
  public boolean isEmpty(){
    boolean status = false;

    if (!this.jobIdentificationType.equals( null )){       
      status = true;
    }
    
    return status;
  }
  
} // End Class
