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
package eu.geclipse.jsdl.posix;

import java.util.Hashtable;
import java.util.Iterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.jsdl.model.posix.ArgumentType;
import eu.geclipse.jsdl.model.posix.EnvironmentType;
import eu.geclipse.jsdl.model.posix.FileNameType;
import eu.geclipse.jsdl.model.posix.POSIXApplicationType;
import eu.geclipse.jsdl.model.posix.PosixFactory;
import eu.geclipse.jsdl.model.posix.PosixPackage;


/**
 * @author nickl
 *
 */
public class PosixApplicationTypeAdapter {
  
  Hashtable< Integer, Text > widgetFeaturesMap = new Hashtable< Integer, Text >();
  Hashtable< Integer, Table > tableFeaturesMap = new Hashtable< Integer, Table >();
  Hashtable<String, EStructuralFeature> eStructuralFeaturesMap 
                                  = new Hashtable<String, EStructuralFeature>();
  
  private POSIXApplicationType posixApplicationType;
  
  private EnvironmentType environmentType = PosixFactory.eINSTANCE.
                                                        createEnvironmentType();
  
  private ArgumentType argumentType = PosixFactory.eINSTANCE.createArgumentType();
  
  private FileNameType fileName = null;
  private Boolean adapterRefreshed = false;
  
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
      
//       LimitsTypeAdapter lt = new LimitsTypeAdapter(posixApplicationType);
     } 
     
   }    

  } // End getTypeforAdapters()
 
  
  
 /*
  * Method of setting the content of the Adapter.
  */
 public void setContent(final EObject rootJsdlElement){
   this.adapterRefreshed = true; 
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
  
  
  public void attachToPosixApplicationArgument(final Table widget){    
    this.tableFeaturesMap.put( PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT
                                , widget );
        
     widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        // List Add Functionality    
      }
      public void focusGained(final FocusEvent e ) { }
     
    } );
  }
  
  
  
  public void attachToPosixApplicationEnvironment(final Table widget){    
    this.tableFeaturesMap.put( PosixPackage.POSIX_APPLICATION_TYPE__ENVIRONMENT
                                , widget );
        
     widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        // List Add Functionality    
      }
      public void focusGained(final FocusEvent e ) { }
     
    } );
  }
  
  
  
  public void attachToDelete(final Button button, final Table table){
    button.addSelectionListener(new SelectionListener() {

      public void widgetSelected(final SelectionEvent event) {
        performDelete( table.getItem( table.getSelectionIndex() ) );
      }

      public void widgetDefaultSelected(final SelectionEvent event) {
          // Do Nothing - Required method
      }
    });
    
    
  }
  
  
  /*
   * Method of checking if the adapter is Empty..
   */
  public boolean isEmpty(){
    boolean status = false;

    if (!this.posixApplicationType.equals( null )){       
      status = true;
    }
    
    return status;
  } //End isEmpty()
  
  
private void performDelete(final TableItem item){
    
    Table tableWidget;
        
    String key = item.getText( 0 );    
    
    /* Get EStructuralFeature */
    EStructuralFeature eStructuralFeature = this.eStructuralFeaturesMap.get( key );
    
    /* Delete only Multi-Valued Elements */
    if (FeatureMapUtil.isMany(this.posixApplicationType, eStructuralFeature)){
      
      ((java.util.List<?>)this.posixApplicationType.eGet(eStructuralFeature))
                                                                   .remove( key );
    }    

          
    /* Get Structural Feature ID to remove it from the associated SWT List */
    int featureID = eStructuralFeature.getFeatureID();
    tableWidget = this.tableFeaturesMap.get( featureID );
    tableWidget.remove( tableWidget.getSelectionIndex() );
    
  }
  
  
  public void load()
  {
    EObject object = this.posixApplicationType;
    Text widgetName = null;
    Table tableName = null;
     
    // Test if eObject is not empty.
    if(object != null) {
      EClass eClass = object.eClass();
      
      for (Iterator iterRef = eClass.getEAllStructuralFeatures().iterator(); iterRef.hasNext();){
        
        EStructuralFeature eStructuralFeature = (EStructuralFeature) iterRef.next();
           
        Integer featureID = eStructuralFeature.getFeatureID();
        
        if (eStructuralFeature instanceof EReference) {

          //Check for the features Multiplicity.
          
          if (object.eIsSet( eStructuralFeature ) 
            && eStructuralFeature.getUpperBound() 
            != EStructuralFeature.UNBOUNDED_MULTIPLICITY ){
        
           EObject eObject = (EObject) object.eGet( eStructuralFeature );
                
           Object eStrFeatValue = null;
          
           if (ExtendedMetaData.INSTANCE.getContentKind(eObject.eClass()) == ExtendedMetaData.SIMPLE_CONTENT){
             eStrFeatValue = eObject.eGet(ExtendedMetaData.INSTANCE.getSimpleFeature(eObject.eClass())); 
            
           }
                
           
           
           // Check if Reference has been set.
           if (object.eIsSet( eStructuralFeature ) 
               && eStructuralFeature.getName().toString() != "anyAttribute" ){          
             widgetName = this.widgetFeaturesMap.get( featureID );
             widgetName.setText(eStrFeatValue.toString());            
             
           }
          } // End UNBOUNDED_MULTIPLICITY
          else {            

            
            switch( featureID ) {
              case PosixPackage.POSIX_APPLICATION_TYPE__ENVIRONMENT:                
              {
                
                tableName = this.tableFeaturesMap.get( featureID );
                EList valueList = (EList) object.eGet( eStructuralFeature );                
                if(!this.adapterRefreshed) {
                  for (Iterator it = valueList.iterator(); it.hasNext();){
                   this.environmentType = (EnvironmentType) it.next(); 
                   
                   this.eStructuralFeaturesMap.put( this.environmentType.getFilesystemName(),
                                                  eStructuralFeature );
                     TableItem tableItem  = new TableItem(tableName, SWT.NONE);
                     tableItem.setText( 0 , this.environmentType.getName());
                     tableItem.setText( 1 , this.environmentType.getFilesystemName());
                     tableItem.setText( 2 , this.environmentType.getValue());
                    } // End Iterator
                             
                  } // Endif
              }                
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT:                
              {                
                tableName = this.tableFeaturesMap.get( featureID );
                EList valueList = (EList) object.eGet( eStructuralFeature );                
                if(!this.adapterRefreshed) {
                  for (Iterator it = valueList.iterator(); it.hasNext();){
                    
                   this.argumentType = (ArgumentType) it.next();          
                   this.eStructuralFeaturesMap.put( this.argumentType.getFilesystemName(),
                                                    eStructuralFeature );
                   
                     TableItem tableItem  = new TableItem(tableName, SWT.NONE);                     
                     tableItem.setText( 0 , this.argumentType.getFilesystemName());
                     tableItem.setText( 1 , this.argumentType.getValue());                     
                    } // End Iterator
                             
                  } // Endif
              }                
              break;
              
              default:
              break;
            }

            
          } // End Else
        } // End else EReference
        
        // Then this is an attribute.
        else if (eStructuralFeature instanceof EAttribute) {
          
          // Get Attribute Value.
          Object value = object.eGet( eStructuralFeature );        
               
          
                
          // Check if Attribute has any value
          if (object.eIsSet( eStructuralFeature )){          
             widgetName = this.widgetFeaturesMap.get( featureID );
             
             if (eStructuralFeature.getName().toString() != "any"){ //$NON-NLS-1$
               widgetName.setText(value.toString());
           } // End if
          
          } // End if for eIsSet

        } // End else attribute
        
        else{
          //Do Nothing.
        }

     } // End Iterator.
      
      
    } // End if    
  } // End void load()
    
  
} // End Class
