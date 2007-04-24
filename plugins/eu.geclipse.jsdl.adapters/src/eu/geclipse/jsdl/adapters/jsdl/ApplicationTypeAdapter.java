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
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import eu.geclipse.jsdl.ApplicationType;
import eu.geclipse.jsdl.JsdlFactory;
import eu.geclipse.jsdl.JsdlPackage;


/**
 * @author nickl
 *
 */
public class ApplicationTypeAdapter {
  
  private ApplicationType applicationType;
 
  Hashtable< Integer, Text > widgetFeaturesMap = new Hashtable< Integer, Text >();
  
  /*
   * Class Constructor
   */
  public ApplicationTypeAdapter (final EObject rootJsdlElement){
    
    this.applicationType = JsdlFactory.eINSTANCE.createApplicationType();    
    getTypeForAdapter(rootJsdlElement);
    
  } // End Constructor
  
  
  
  /*
   * Get the Application Type Element from the root Jsdl Element.
   */  
  private void  getTypeForAdapter(final EObject rootJsdlElement){
    
    TreeIterator iterator = rootJsdlElement.eAllContents();
    
    while ( iterator.hasNext (  )  )  {  
   
      EObject testType = (EObject) iterator.next();
          
      if ( testType instanceof ApplicationType ) {
        this.applicationType = (ApplicationType) testType;  

      } //endif ApplicationType
      
    } //End while   

   } // End getTypeforAdapter
  
  
  
  /*
   * Allows to set the adapter content on demand and not through the constructor.
   */
  public void setContent(final EObject rootJsdlElement){
    getTypeForAdapter( rootJsdlElement );    
  }
  
  
  
  /*
   * Adapter interface to attach to the ApplicationName widget.
   */
  public void attachToApplicationName(final Text widget){    
    this.widgetFeaturesMap.put( JsdlPackage.APPLICATION_TYPE__APPLICATION_NAME
                                , widget );
        
     widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        applicationType.setApplicationName(widget.getText());    
      }
      public void focusGained( FocusEvent e ) { }
     
    } );
  }
  
  
  
  /*
   * Adapter interface to attach to the ApplicationVersion widget.
   */
  public void attachToApplicationVersion(final Text widget){    
    this.widgetFeaturesMap.put( JsdlPackage.APPLICATION_TYPE__APPLICATION_VERSION
                                , widget );
        
     widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        applicationType.setApplicationVersion(widget.getText());    
      }
      public void focusGained( FocusEvent e ) { }
     
    } );
  }
  
  
  
  /*
   * Adapter interface to attach to the ApplicationDescription widget.
   */
  public void attachToApplicationDescription(final Text widget){    
    this.widgetFeaturesMap.put( JsdlPackage.APPLICATION_TYPE__DESCRIPTION
                                , widget );
        
     widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        applicationType.setDescription(widget.getText());    
      }
      public void focusGained( FocusEvent e ) { }
     
    } );
  }
  
  
  public void attachToApplicationSection(final ExpandableComposite section){    
           
     section.addExpansionListener( new ExpansionAdapter() {
       public void expansionStateChanged(ExpansionEvent e) {
            if (e.data.equals(true)) {
              System.out.println("Expanded");
              createApplicationType();
            }
            else{
              System.out.println("NOT Expanded");
             // removeApplicationType();
            }
        }
    } );
  }
  
 
  /*
   * This loads the model content to the widgets registered with the adapter
   */
  public void load()
  {
    EObject object = this.applicationType;
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
  
  
  /*
   * Creates the Application Type Elements in the JSDL document.
   */
  
  private void createApplicationType(){
   // System.out.println("create");
    getParent( this.applicationType );
    
        
  }
  
  private EObject getParent(final EObject child){
    
    EObject parent = null;
    parent = child.eContainer();    
    
    System.out.println("Parent:" + parent.toString());
    
    return parent;
  }
  
  
  /*
   * Check if adapter is Empty.
   */
  public boolean isEmpty(){
    boolean status = false;

    if (!this.applicationType.equals( null )){       
      status = true;
    }
    
    return status;
  }
  
  
  
} // End Class