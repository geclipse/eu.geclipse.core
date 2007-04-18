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
package eu.geclipse.jsdl.adapters.posix;

import java.util.Hashtable;
import java.util.Iterator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.jsdl.posix.FileNameType;
import eu.geclipse.jsdl.posix.POSIXApplicationType;
import eu.geclipse.jsdl.posix.PosixFactory;
import eu.geclipse.jsdl.posix.PosixPackage;


/**
 * @author nickl
 *
 */
public class PosixApplicationTypeAdapter {
  
  private POSIXApplicationType posixApplicationType;
  Hashtable< Integer, Text > widgetFeaturesMap = new Hashtable< Integer, Text >();
  private FileNameType fileName = null;
  
  /*
   * Class Constructor
   */
  public PosixApplicationTypeAdapter(final EObject rootJsdlElement){
    
    this.posixApplicationType = PosixFactory.eINSTANCE.createPOSIXApplicationType();
    getTypeForAdapter(rootJsdlElement);
    
  }
  
  
  
  /*
  * Get the Application Type Element from the root Jsdl Element.
  */  
  private void  getTypeForAdapter(final EObject rootJsdlElement){
   
   TreeIterator iterator = rootJsdlElement.eAllContents();
   
   while ( iterator.hasNext (  )  )  {  
  
     EObject testType = (EObject) iterator.next();
         
     if ( testType instanceof POSIXApplicationType ) {
       this.posixApplicationType = (POSIXApplicationType) testType;  
//       //FIXME - Just a check.       
//       LimitsTypeAdapter lt = new LimitsTypeAdapter(posixApplicationType);
     } 
     
   }    

  } // End getTypeforAdapters()
 
  
  
 
 public void setContent(final EObject rootJsdlElement){
    
   getTypeForAdapter( rootJsdlElement );   
   
  } // End setContent()
  
  
  public void attachPosixApplicationName(final Text widget){    
    this.widgetFeaturesMap.put( PosixPackage.POSIX_APPLICATION_TYPE__NAME, widget );    
        
     widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        posixApplicationType.setName(widget.getText());    
      }
      public void focusGained(final org.eclipse.swt.events.FocusEvent e ) { }
    } );
  }
  
  public void attachPosixApplicationExecutable(final Text widget){    
    this.widgetFeaturesMap.put( PosixPackage.POSIX_APPLICATION_TYPE__EXECUTABLE, widget );    
    fileName = PosixFactory.eINSTANCE.createFileNameType();    
     widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        fileName.setValue( widget.getText() );
        posixApplicationType.setExecutable(fileName);    
      }      
      public void focusGained(final org.eclipse.swt.events.FocusEvent e ) { }
    } );
  }
  
  public void attachPosixApplicationInput(final Text widget){    
    this.widgetFeaturesMap.put( PosixPackage.POSIX_APPLICATION_TYPE__INPUT, widget );
    fileName = PosixFactory.eINSTANCE.createFileNameType();    
     widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        fileName.setValue( widget.getText() );
        posixApplicationType.setInput(fileName);    
      }      
      public void focusGained(final org.eclipse.swt.events.FocusEvent e ) { }
    } );
  }

  public void attachPosixApplicationOutput(final Text widget){    
    this.widgetFeaturesMap.put( PosixPackage.POSIX_APPLICATION_TYPE__OUTPUT, widget );
    fileName = PosixFactory.eINSTANCE.createFileNameType();    
     widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        fileName.setValue( widget.getText() );
        posixApplicationType.setOutput(fileName);    
      }      
      public void focusGained(final org.eclipse.swt.events.FocusEvent e ) { }
    } );   
  }
  
  
  public void attachPosixApplicationError(final Text widget){    
    this.widgetFeaturesMap.put( PosixPackage.POSIX_APPLICATION_TYPE__ERROR, widget );
    fileName = PosixFactory.eINSTANCE.createFileNameType();    
     widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        fileName.setValue( widget.getText() );
        posixApplicationType.setError(fileName);    
      }      
      public void focusGained(final org.eclipse.swt.events.FocusEvent e ) { }
    } );
  }
  
  
  public boolean isEmpty(){
    boolean status = false;

    if (!this.posixApplicationType.equals( null )){       
      status = true;
    }
    
    return status;
  } //End isEmpty()
  
  
  public void load()
  {
    EObject object = this.posixApplicationType;
    Text widgetName2 = null;
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
      
      
      
      for (Iterator iterRef = eClass.getEAllReferences().iterator(); iterRef.hasNext();){
        EReference reference = (EReference) iterRef.next();
        
        Object valueRef = object.eGet(reference);       
        
        Integer referenceID = reference.getFeatureID();
                          
        if (object.eIsSet( reference )){          
          widgetName2 = this.widgetFeaturesMap.get( referenceID );
                         
//        //FIXME - any check should be removed..check cause of it.
          if (reference.getName().toString() != "any"){ //$NON-NLS-1$
            widgetName2.setText(valueRef.toString());            
          }
               
        }

     }
      
      
      
      
    } //end if    
  } // End void load()
  
  
  
} // End Class
