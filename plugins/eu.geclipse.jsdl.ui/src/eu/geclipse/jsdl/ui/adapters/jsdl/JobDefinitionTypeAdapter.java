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


import java.util.HashMap;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.jsdl.model.JobDefinitionType;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.jsdl.ui.internal.pages.JobDefinitionPage;


/**
 * <code>JobDefinitionTypeAdapter</code> Class.
 * <p>
 * This class provides adapters for manipulating <b>Job Definition </b> 
 * elements through the Job Definition Page of the JSDL editor. 
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
   * @param jobDefinitionRoot . The root element of a JSDL document ({@link JobDefinitionType}).
   */
  public JobDefinitionTypeAdapter(final JobDefinitionType jobDefinitionRoot) {
    
    getTypeForAdapter( jobDefinitionRoot );
      
  } // End class Constructor
  
  
  
  protected void contentChanged() {
    
    if ( this.isNotifyAllowed ) { 
      
      fireNotifyChanged( null);
    }
    
  } // End void contentChanged()
  
  
  
  /**
   * The attach point that handles the {@link Text} widget which is responsible for the 
   * JobDefinition ID element. This attach point provides a {@link ModifyListener}
   * that listens to changes in the text box and commits this changes to the underlying
   * model.
   * 
   * @param widget The Text widget responsible for JobDefinition ID element.
   */
  public void attachID( final Text widget ) {
    
    if ( widget != null) {
      
    Integer featureID = Integer.valueOf( JsdlPackage.JOB_DEFINITION_TYPE__ID);
    this.widgetFeaturesMap.put( featureID, widget );
    
      widget.addModifyListener( new ModifyListener() {
              
      public void modifyText( final ModifyEvent e ) {
        JobDefinitionTypeAdapter.this.jobDefinitionType.setId( widget.getText() );
        contentChanged();
        
      }
    } );
      
    }
      
  } // End void attachID()
  
  
    
  /**
   * Allows to set the adapter's content on demand and not through the adapter constructor.
   * 
   * @param documentRoot. The root element of a JSDL document ({@link JobDefinitionType}). 
   */
  public void setContent( final JobDefinitionType documentRoot ) {
    
    getTypeForAdapter( documentRoot );
    
  } // End void setContent()
  
  
   
  /*
   * Get the JobDefinition Elements from the Root JSDL Element.
   */
   private void  getTypeForAdapter( final JobDefinitionType jobDefinitionRoot ) {
     
        this.jobDefinitionType = jobDefinitionRoot;
        
   } // End void getTypeForAdapter()
   
   
 
   /**
   * Method which populates the content of the underlying model to the widgets that are
   * attached to this adapter. The method is called from the {@link JobDefinitionPage} when
   * the appropriate widgets are created and also every time the page becomes active. 
   */
  public void load()
   {
    
     this.isNotifyAllowed = false;
     Text widgetName = null;
       
     // Test if eObject is not empty.
     if ( this.jobDefinitionType != null ) {
       EClass eClass = this.jobDefinitionType.eClass();
              
      EList <EAttribute> allAttributes = eClass.getEAllAttributes();
      for( EAttribute attribute : allAttributes ) {
                                               
         //Get Attribute Value.
         Object value = this.jobDefinitionType.eGet( attribute );
         
         int featureID =  attribute.getFeatureID();
         
         if (this.jobDefinitionType.eIsSet( attribute )){ 
           widgetName = this.widgetFeaturesMap.get( Integer.valueOf(  featureID ) );
      
           switch( featureID ) {
             
            case JsdlPackage.JOB_DEFINITION_TYPE__ID:
              widgetName.setText(value.toString());
            break;
            default:
            break;
            
           }// end switch
         }   // end_if eIsSet()

       } //end for
     } //end if
     this.isNotifyAllowed = true;
     
   } // End void populateAttributes()
   
   
   
   /**
    * Method that check whether the adapter is empty. 
    * 
    * @return TRUE if the adapter is empty. If it is empty, it means that there 
    * is no JobDefinition element in the JSDL document. 
    */
  public boolean isEmpty() {
     boolean status = true;
 
     if ( !this.jobDefinitionType.equals( null ) ) {       
       status = false;
     }
     
     return status;
     
   }// End boolean isEmpty()
    
}// End Class