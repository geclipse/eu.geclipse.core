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

/**
 * @author nickl
 *
 */

import java.util.Hashtable;
import java.util.Iterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.jsdl.model.JobDefinitionType;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.jsdl.model.JsdlPackage;


public class JobDefinitionTypeAdapter extends JsdlAdapters {
  
  Hashtable< Integer, Text > widgetFeaturesMap = new Hashtable< Integer, Text >();
  private JobDefinitionType jobDefinitionType = JsdlFactory.eINSTANCE.createJobDefinitionType(); 
  
  
  public JobDefinitionTypeAdapter(final EObject rootJsdlElement) {
    getTypeForAdapter(rootJsdlElement);
      
  }
  
  public void attachID(final Text widget){    
    this.widgetFeaturesMap.put( JsdlPackage.JOB_DEFINITION_TYPE__ID, widget );
        
     widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        jobDefinitionType.setId(widget.getText());    
      }
      public void focusGained(final org.eclipse.swt.events.FocusEvent e ) { }
    } );
  }
  
  public void setContent(final EObject rootJsdlElement){
    getTypeForAdapter( rootJsdlElement );
  }
  
        
   private void  getTypeForAdapter(final EObject rootJsdlElement){      
        this.jobDefinitionType = (JobDefinitionType) rootJsdlElement;         
   }
 
   public void load()
   {
     EObject object = this.jobDefinitionType;
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
   } // End void populateAttributes()
   
   
   
   public boolean isEmpty(){
     boolean status = false;
 
     if (!this.jobDefinitionType.equals( null )){       
       status = true;
     }
     
     return status;
   }
    
}