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

import java.util.Hashtable;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.jsdl.model.ApplicationType;
import eu.geclipse.jsdl.model.JobDefinitionType;
import eu.geclipse.jsdl.model.JobDescriptionType;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.jsdl.ui.internal.pages.JobDefinitionPage;



/**
 * ApplicationTypeAdapter Class.
 * <p>
 * This class provides adapters for manipulating generic <b>Application </b> 
 * elements through the Application Page of the JSDL editor. 
 * Supported Application elements are:
 * <p>
 * - ApplicationName
 * <p>
 * - ApplicationVersion
 * <p>
 * - Description
 * 
 */
public final class ApplicationTypeAdapter extends JsdlAdaptersFactory {
  
  protected JobDescriptionType jobDescriptionType = JsdlFactory
                                          .eINSTANCE.createJobDescriptionType();
  protected ApplicationType applicationType = JsdlFactory.eINSTANCE
                                                       .createApplicationType();
  private boolean isNotifyAllowed = true;
  
  private Hashtable< Integer, Text > widgetFeaturesMap = 
                                               new Hashtable< Integer, Text >();
  
    
  /**
   * Constructs a new <code> {@link ApplicationTypeAdapter} </code>
   * 
   * @param jobDefinitionRoot . The root element of a JSDL document ({@link JobDefinitionType}).
   */
  public ApplicationTypeAdapter ( final JobDefinitionType jobDefinitionType ){
 
    getTypeForAdapter( jobDefinitionType );
    
  } // End Constructor
  
  
  
  protected void contentChanged() {
    
    if ( this.isNotifyAllowed ){
      fireNotifyChanged( null );
    }
    
  }
    
  
  /*
   * Get the Application Type Element from the root Jsdl Element.
   */  
  private void  getTypeForAdapter( final JobDefinitionType jobDefinitionRoot ){

    if ( jobDefinitionRoot.getJobDescription() != null ) {
      this.jobDescriptionType = jobDefinitionRoot.getJobDescription();
      if ( this.jobDescriptionType.getApplication() != null ) {
        this.applicationType = this.jobDescriptionType.getApplication();    
      }
    }
    

  } // End getTypeforAdapter
  
  
  
  
  /**
   * Allows to set the adapter's content on demand and not through the constructor.
   * 
   * @param jobDefinitionRoot The root element of a JSDL document.
   */
  public void setContent( final JobDefinitionType jobDefinitionRoot ) {

    getTypeForAdapter( jobDefinitionRoot );  
    
  }
  
  
  
  /**
   * The attach point that handles the {@link Text} widget which is responsible for the 
   * JobApplication <b>Name</b> element. This attach point provides a {@link ModifyListener}
   * that listens to changes in the text box and commits this changes to the underlying
   * model.
   * 
   * @param widget The Text widget responsible for JobApplication Name element.
   */
  public void attachToApplicationName( final Text widget ) {
    
    Integer featureID = Integer.valueOf(JsdlPackage.APPLICATION_TYPE__APPLICATION_NAME);
    this.widgetFeaturesMap.put( featureID, widget );
    
    widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        ApplicationTypeAdapter.this.applicationType.setApplicationName(widget.getText());
        contentChanged();
        
      }
    } );
    
  } // end void attachToApplicationName()
  
  
  
  /**
   * The attach point that handles the {@link Text} widget which is responsible for the 
   * JobApplication <b>Version</b> element. This attach point provides a {@link ModifyListener}
   * that listens to changes in the text box and commits this changes to the underlying
   * model.
   * 
   * @param widget The Text widget responsible for JobApplication Version element.
   */ 
  public void attachToApplicationVersion( final Text widget ) {
    
    Integer featureID = Integer.valueOf( JsdlPackage.APPLICATION_TYPE__APPLICATION_VERSION );
    this.widgetFeaturesMap.put( featureID , widget );
        
    widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        ApplicationTypeAdapter.this.applicationType.setApplicationVersion(widget.getText());
        contentChanged();
        
      }
    } );
    
   } // end void attachToApplicationVersion()
  
  
  
  /**
   * The attach point that handles the {@link Text} widget which is responsible for the 
   * JobApplication <b>Description</b> element. This attach point provides a {@link ModifyListener}
   * that listens to changes in the text box and commits this changes to the underlying
   * model.
   * 
   * @param widget The Text widget responsible for JobApplication Description element.
   */
  public void attachToApplicationDescription( final Text widget ) {
    Integer featureID = Integer.valueOf( JsdlPackage.APPLICATION_TYPE__DESCRIPTION );
    this.widgetFeaturesMap.put( featureID , widget );
    
    widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        ApplicationTypeAdapter.this.applicationType.setDescription(widget.getText());
        contentChanged();
        
      }
    } );    
    
  } // end void attachToApplicationDescription()
  
 
  
  /**
   * Method which populates the content of the underlying model to the widgets that are
   * attached to this adapter. The method is called from the {@link JobDefinitionPage} when
   * the appropriate widgets are created and also every time the page becomes active. 
   */
  public void load()
  {
    this.isNotifyAllowed = false;
    EObject object = this.applicationType;
    Text widgetName = null;
    
    // Test if eObject is not empty.
    if(object != null) {
      EClass eClass = object.eClass();
      Object value;
      int featureID;
      
      EList<EAttribute> allAttributes = eClass.getEAllAttributes();
      for( EAttribute attribute : allAttributes ) {
                                               
        //Get Attribute Value.
        value = object.eGet( attribute );        
             
        featureID = attribute.getFeatureID();
        
                      
        //Check if Attribute has any value
        if (object.eIsSet( attribute ) 
            && this.widgetFeaturesMap.containsKey( Integer.valueOf( featureID  ) ) ) {
          
           widgetName = this.widgetFeaturesMap.get( Integer.valueOf( featureID ) );
           
           switch( featureID ) {             
             case JsdlPackage.APPLICATION_TYPE__APPLICATION_NAME:
               widgetName.setText( value.toString() );
             break;
             case JsdlPackage.APPLICATION_TYPE__APPLICATION_VERSION:
               widgetName.setText( value.toString() );
             break;
             case JsdlPackage.APPLICATION_TYPE__DESCRIPTION:
               widgetName.setText( value.toString() );
             break;
               
             default:
             break;
             
            }// end switch
                              
        } //end if
        
      } //end for
      
    } //end if
    
    this.isNotifyAllowed = true;
    
  } // End void load()
  
  
  
} // End Class