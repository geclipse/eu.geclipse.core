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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import eu.geclipse.jsdl.model.ApplicationType;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.jsdl.model.JsdlPackage;



/**
 * ApplicationTypeAdapter Class.
 * <p>
 * This class provides adapters for manipulating <b>Application </b> 
 * elements  through the Application Page of the JSDL editor. 
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
  
  
  protected ApplicationType applicationType = JsdlFactory.eINSTANCE
                                                       .createApplicationType();
  private boolean isNotifyAllowed = true;
//  private boolean adapterRefreshed = false;
  
  private Hashtable< Integer, Text > widgetFeaturesMap = 
                                               new Hashtable< Integer, Text >();
  
    
  /**
   * ApplicationTypeAdapter Class Constructor
   * 
   * @param rootJsdlElement The root element of a JSDL document.
   */
  public ApplicationTypeAdapter (final EObject rootJsdlElement){

    getTypeForAdapter(rootJsdlElement);
    
  } // End Constructor
  
  
  
  protected void contentChanged(){
    
    if (this.isNotifyAllowed){
      fireNotifyChanged( null);
    }
    
  }
    
  
  /*
   * Get the Application Type Element from the root Jsdl Element.
   */  
  private void  getTypeForAdapter(final EObject rootJsdlElement){
    
    TreeIterator<EObject> iterator = rootJsdlElement.eAllContents();
    
    while ( iterator.hasNext (  )  )  {  
   
      EObject testType = iterator.next();
          
      if ( testType instanceof ApplicationType ) {
        this.applicationType = (ApplicationType) testType;  

      } //endif ApplicationType
      
    } //End while   

   } // End getTypeforAdapter
  
  
  
  
  /**
   * Allows to set the adapter's content on demand and not through the constructor.
   * 
   * @param rootJsdlElement The root element of a JSDL document.
   */
  public void setContent(final EObject rootJsdlElement){

    getTypeForAdapter( rootJsdlElement );  
    
  }
  
  
  
  /**
   * Adapter interface to attach to the ApplicationName text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * ApplicationName element of the JSDL document.
   */
  public void attachToApplicationName(final Text widget){
    Integer featureID = new Integer(JsdlPackage.APPLICATION_TYPE__APPLICATION_NAME);
    this.widgetFeaturesMap.put( featureID, widget );
    
    widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        ApplicationTypeAdapter.this.applicationType.setApplicationName(widget.getText());
        contentChanged();
        
      }
    } );
  }
  
  
  /**
   * Adapter interface to attach to the ApplicationName text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * ApplicationVersion element of the JSDL document.
   */  
  public void attachToApplicationVersion(final Text widget){
    Integer featureID = new Integer(JsdlPackage.APPLICATION_TYPE__APPLICATION_VERSION);
    this.widgetFeaturesMap.put( featureID , widget );
        
    widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        ApplicationTypeAdapter.this.applicationType.setApplicationVersion(widget.getText());
        contentChanged();
        
      }
    } ); 
   }
  
  
  /**
   * Adapter interface to attach to the ApplicationDescription text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * ApplicationDescription element of the JSDL document.
   */ 
  public void attachToApplicationDescription(final Text widget){
    Integer featureID = new Integer(JsdlPackage.APPLICATION_TYPE__DESCRIPTION);
    this.widgetFeaturesMap.put( featureID , widget );
    
    widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        ApplicationTypeAdapter.this.applicationType.setDescription(widget.getText());
        contentChanged();
        
      }
    } );    
  }
  
  
  /**
   * @param section The SWT ExpandableComposite that hosts the widgets related
   * to Application elements.
   */
  public void attachToApplicationSection(final ExpandableComposite section){    
           
     section.addExpansionListener( new ExpansionAdapter() {
       @Override
      public void expansionStateChanged(final ExpansionEvent e) {
            if (e.data.equals(Boolean.TRUE)) {
          //    createApplicationType();
            }
            else{              
             // removeApplicationType();
            }
        }
    } );
  }
  
 
  /**
   * This method populates the model content to the widgets registered with the
   * ApplicationType adapter.
   */
  public void load()
  {
    this.isNotifyAllowed = false;
    EObject object = this.applicationType;
    Text widgetName = null;
    //EDataType dataType = null;
    
    // Test if eObject is not empty.
    if(object != null) {
      EClass eClass = object.eClass();
      
      EList<EAttribute> allAttributes = eClass.getEAllAttributes();
      for( EAttribute attribute : allAttributes ) {
                                               
        //Get Attribute Value.
        Object value = object.eGet( attribute );        
             
        Integer featureID = new Integer(attribute.getFeatureID());
              
        //Check if Attribute has any value
        if (object.eIsSet( attribute ) 
            && this.widgetFeaturesMap.containsKey( featureID )) {
          
           widgetName = this.widgetFeaturesMap.get( featureID );

           if (attribute.getFeatureID() != JsdlPackage.APPLICATION_TYPE__ANY){             
             widgetName.setText(value.toString());
         } //end if
                   
        } //end if
      } //end for
    } //end if
    this.isNotifyAllowed = true;
    
  } // End void load()
  
  
  
  /**
   * @return TRUE if the adapter is empty. If it is empty, it means that there 
   * is no JobDefinition element in the JSDL document. 
   */
  public boolean isEmpty(){
    boolean status = false;

    if (!this.applicationType.equals( null )){       
      status = true;
    }
    
    return status;
  } // End boolean isEmpty()
  
  
  
} // End Class