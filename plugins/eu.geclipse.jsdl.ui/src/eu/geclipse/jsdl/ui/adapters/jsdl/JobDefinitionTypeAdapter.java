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

import java.util.HashMap;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.jsdl.model.JobDefinitionType;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.jsdl.model.JsdlPackage;


/**
 * JobDefinitionTypeAdapter Class.
 * <p>
 * This class provides adapters for manipulating <b>Job Definition </b> 
 * elements  through the Job Definition Page of the JSDL editor. 
 * Supported JobDefinition attributes are:
 * <p>
 * - ID
 *
 */
public final class JobDefinitionTypeAdapter extends JsdlAdaptersFactory {
  
  protected JobDefinitionType jobDefinitionType =
                               JsdlFactory.eINSTANCE.createJobDefinitionType();
  protected HashMap< Integer, Text > widgetFeaturesMap = new HashMap< Integer, Text >();
   
  private boolean isNotifyAllowed = true;
  
  
  /**
   * Constructs a new <code> JobDefinitionTypeAdapter </code>
   * 
   * @param rootJsdlElement . The root element of a JSDL document.
   */
  public JobDefinitionTypeAdapter(final EObject rootJsdlElement) {
    
    getTypeForAdapter(rootJsdlElement);
      
  } // End class Constructor
  
  
  
  protected void contentChanged(){
    if (this.isNotifyAllowed){
      
      fireNotifyChanged( null);
    }
    
  } // End void contentChanged()
  
  
  
  /**
   * @param widget
   */
  public void attachID(final Text widget){
    
    Integer featureID = new Integer (JsdlPackage.JOB_DEFINITION_TYPE__ID);
    this.widgetFeaturesMap.put( featureID, widget );
    
      widget.addModifyListener( new ModifyListener() {
              
      public void modifyText( final ModifyEvent e ) {
        JobDefinitionTypeAdapter.this.jobDefinitionType.setId( widget.getText() );
        contentChanged();
        
      }
    } );   
      
  } // End void attachID()
  
  
    
  /**
   * Allows to set the adapter's content on demand and not through the constructor.
   * 
   * @param rootJsdlElement
   */
  public void setContent(final EObject rootJsdlElement){
    
    getTypeForAdapter( rootJsdlElement );
    
  } // End void setContent()
  
   
  /*
   * Get the JobDefinition Elements from the Root JSDL Element.
   */
   private void  getTypeForAdapter(final EObject rootJsdlElement){
     
        this.jobDefinitionType = (JobDefinitionType) rootJsdlElement;
        
   } // End void getTypeForAdapter()
   
   
 
   /**
   * 
   */
  public void load()
   {
    
     this.isNotifyAllowed = false;
     EObject eObject = this.jobDefinitionType;
     Text widgetName = null;
       
     // Test if eObject is not empty.
     if(eObject != null) {
       EClass eClass = eObject.eClass();
              
      EList<EAttribute> allAttributes = eClass.getEAllAttributes();
      for( EAttribute attribute : allAttributes ) {
                                               
         //Get Attribute Value.
         Object value = eObject.eGet( attribute );        
              
         Integer featureID = new Integer( attribute.getFeatureID() );
       
         //Check if Attribute has any value
         if (eObject.eIsSet( attribute )){          
            widgetName = this.widgetFeaturesMap.get( featureID );
              
            if (featureID != new Integer (JsdlPackage.JOB_DEFINITION_TYPE__ANY_ATTRIBUTE))
            {
              widgetName.setText(value.toString());
            } //end if
                    
         } //end if
       } //end for
     } //end if
     this.isNotifyAllowed = true;
     
   } // End void populateAttributes()
   
   
   
   /**
   * @return TRUE if the adapter is empty. If it is empty, it means that there 
   * is no JobDefinition element in the JSDL document. 
   */
  public boolean isEmpty(){
     boolean status = false;
 
     if (!this.jobDefinitionType.equals( null )){       
       status = true;
     }
     
     return status;
     
   }// End boolean isEmpty()
    
}// End Class