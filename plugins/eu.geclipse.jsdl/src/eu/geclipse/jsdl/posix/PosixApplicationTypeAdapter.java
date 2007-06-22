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
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
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
public class PosixApplicationTypeAdapter extends PosixAdaptersFactory {
  
  protected POSIXApplicationType posixApplicationType = 
                            PosixFactory.eINSTANCE.createPOSIXApplicationType();
  
  protected EnvironmentType environmentType = PosixFactory.eINSTANCE.
                                                        createEnvironmentType();

  protected ArgumentType argumentType = PosixFactory.eINSTANCE.createArgumentType();
  
  protected FileNameType fileName = null;
  
  private Hashtable< Integer, Text > widgetFeaturesMap = new Hashtable< Integer, Text >();
  private Hashtable< Integer, TableViewer > tableFeaturesMap = new Hashtable< Integer, TableViewer >();

  
  
  
  
  private boolean adapterRefreshed = false;
  private boolean isNotifyAllowed = true;
  
  
  /**
   * PosixApplicationTypeAdapter Class Constructor
   * @param rootJsdlElement
   */
  public PosixApplicationTypeAdapter(final EObject rootJsdlElement){
    
     getTypeForAdapter(rootJsdlElement);
    
  }
  
  
  
  /*
  * Get the Application Type Element from the root Jsdl Element.
  */  
  private void  getTypeForAdapter(final EObject rootJsdlElement){
   
   TreeIterator<EObject> iterator = rootJsdlElement.eAllContents();
   
   while ( iterator.hasNext (  )  )  {  
  
     EObject testType = iterator.next();
         
     if ( testType instanceof POSIXApplicationType ) {
       this.posixApplicationType = (POSIXApplicationType) testType;  
      
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
  
  
  
  protected void contentChanged(){
    if (this.isNotifyAllowed){
      fireNotifyChanged( null);
    }
  }
  
 
  public void attachPosixApplicationName(final Text widget){
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__NAME);
    this.widgetFeaturesMap.put( featureID, widget );
    
     widget.addModifyListener( new ModifyListener() {
      
      public void modifyText( final ModifyEvent e ) {
        PosixApplicationTypeAdapter.this.posixApplicationType.setName(widget.getText());
        contentChanged();
        
      }
    } );    
  }
  
  
  
  public void attachPosixApplicationExecutable(final Text widget){
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__EXECUTABLE);
    this.widgetFeaturesMap.put( featureID , widget );    
    this.fileName = PosixFactory.eINSTANCE.createFileNameType();
   
    widget.addModifyListener( new ModifyListener() {
    public void modifyText( final ModifyEvent e ) {
      PosixApplicationTypeAdapter.this.fileName.setValue( widget.getText() );
      PosixApplicationTypeAdapter.this.posixApplicationType
                      .setExecutable(PosixApplicationTypeAdapter.this.fileName);
      contentChanged();
      
    }
  } );     
  }
  
  
  
  public void attachPosixApplicationInput(final Text widget){   
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__INPUT);
    this.widgetFeaturesMap.put( featureID , widget );
    this.fileName = PosixFactory.eINSTANCE.createFileNameType();
    
    widget.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        PosixApplicationTypeAdapter.this.fileName.setValue( widget.getText() );
        PosixApplicationTypeAdapter.this.posixApplicationType
                          .setInput(PosixApplicationTypeAdapter.this.fileName);
        contentChanged();
        
      }
    } ); 
     
  }

  
  
  public void attachPosixApplicationOutput(final Text widget){    
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__OUTPUT);
    this.widgetFeaturesMap.put( featureID, widget );
    this.fileName = PosixFactory.eINSTANCE.createFileNameType();
    
    widget.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        PosixApplicationTypeAdapter.this.fileName.setValue( widget.getText() );
        PosixApplicationTypeAdapter.this.posixApplicationType
                          .setOutput(PosixApplicationTypeAdapter.this.fileName);
        contentChanged();
        
      }
    } );    
  }
  
  
  
  public void attachPosixApplicationError(final Text widget){
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__ERROR);
    this.widgetFeaturesMap.put( featureID , widget );
    this.fileName = PosixFactory.eINSTANCE.createFileNameType();   
    
    widget.addModifyListener( new ModifyListener() {
      public void modifyText( final ModifyEvent e ) {
        PosixApplicationTypeAdapter.this.fileName.setValue( widget.getText() );
        PosixApplicationTypeAdapter.this.posixApplicationType
                          .setError(PosixApplicationTypeAdapter.this.fileName);
        contentChanged();
        
      }
    } );
  }
  
  
  public void attachToPosixApplicationArgument(final TableViewer widget){
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT);
    this.tableFeaturesMap.put( featureID , widget );      
  }
  
  
  
  public void attachToPosixApplicationEnvironment(final TableViewer widget){
    Integer featureID = new Integer(PosixPackage.POSIX_APPLICATION_TYPE__ENVIRONMENT);
    this.tableFeaturesMap.put( featureID , widget );       
  }
  
  
  
  public void attachToDelete(final Button button, final TableViewer viewer){

    button.addSelectionListener(new SelectionListener() {

      public void widgetSelected(final SelectionEvent event) {        
        performDelete(viewer);
      }

      public void widgetDefaultSelected(final SelectionEvent event) {
          // Do Nothing - Required method
      }
    });
    
    
  }
  
  
  /**
   * @return true if the PosixApplicationTypeAdapter is Empty. 
   */
  public boolean isEmpty(){
    boolean status = false;

    if (!this.posixApplicationType.equals( null )){       
      status = true;
    }
    
    return status;
  } //End isEmpty()
  
  
  
  public void performAdd (final TableViewer tableViewer,
                        final String name, final Object[] value) {
    
//    EStructuralFeature eStructuralFeature = null;
//    Collection<Object> collection = new ArrayList<Object>();
//    int featureID;
//    
//    if (name == "argumentViewer"){ //$NON-NLS-1$
//      featureID = PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT;
//    }
//    else{
//      featureID = PosixPackage.POSIX_APPLICATION_TYPE__ENVIRONMENT;
//    }
//    
//    // Get EStructural Feature.
//    eStructuralFeature = this.posixApplicationType.eClass().getEStructuralFeature( featureID );
//       
//    
//    tableViewer.add(value);
//    
//     for ( int i=0; i<tableViewer.getTable().getItemCount(); i++ ) {
//        collection.add( tableViewer.getElementAt( i ) );
//      
//     }
//     
//
//    this.posixApplicationType.eSet(eStructuralFeature, collection);
//    
//    tableViewer.refresh();
//   
//    this.contentChanged();
//    
//    eStructuralFeature = null;
//    collection = null;
    
  }
  
  
  
  protected void performDelete(final TableViewer viewer ){
    
    EStructuralFeature eStructuralFeature = null;
    IStructuredSelection structSelection 
                               = ( IStructuredSelection ) viewer.getSelection();
    
    Object feature = structSelection.getFirstElement();
    
    
    if (feature instanceof ArgumentType){
        ArgumentType argument = (ArgumentType) feature;
       
        
        eStructuralFeature = argument.eContainmentFeature();
        EcoreUtil.remove( argument);
        
    }
    else if (feature instanceof EnvironmentType) {
      EnvironmentType environment = (EnvironmentType) feature;
      eStructuralFeature = environment.eContainingFeature();
      EcoreUtil.remove( environment );
    }
    
    viewer.refresh();
    contentChanged();
  }
  
  
 
  
  public void load()
  {
    this.isNotifyAllowed = false;
    EObject posixEObject = this.posixApplicationType;
    Text widgetName = null;
    TableViewer tableName = null;
     
    // Test if eObject is not empty.
    if(posixEObject != null) {
      EClass eClass = posixEObject.eClass();
      
      for (Iterator<EStructuralFeature> iterRef = eClass.getEAllStructuralFeatures().iterator(); iterRef.hasNext();){
        
        EStructuralFeature eStructuralFeature = iterRef.next();
           
        int featureID =  eStructuralFeature.getFeatureID();
        
        if (eStructuralFeature instanceof EReference) {

          //Check for the features Multiplicity.
          
          if (posixEObject.eIsSet( eStructuralFeature ) 
            && eStructuralFeature.getUpperBound() 
            != ETypedElement.UNBOUNDED_MULTIPLICITY ){
        
           EObject eObject = (EObject) posixEObject.eGet( eStructuralFeature );
                
           Object eStrFeatValue = null;
          
           if (ExtendedMetaData.INSTANCE.getContentKind(eObject.eClass()) == ExtendedMetaData.SIMPLE_CONTENT){
             eStrFeatValue = eObject.eGet(ExtendedMetaData.INSTANCE.getSimpleFeature(eObject.eClass())); 
            
           }
           
           // Check if Reference has been set.
           if (posixEObject.eIsSet( eStructuralFeature ) 
               && eStructuralFeature.getFeatureID() != PosixPackage.POSIX_APPLICATION_TYPE__ANY_ATTRIBUTE ){          
             widgetName = this.widgetFeaturesMap.get( new Integer(featureID) );
             widgetName.setText(eStrFeatValue.toString());            
             
           }
          } // End UNBOUNDED_MULTIPLICITY
          else {            

            
            switch( featureID ) {
              case PosixPackage.POSIX_APPLICATION_TYPE__ENVIRONMENT:                
              {
                
                tableName = this.tableFeaturesMap.get( new Integer(featureID) );
                               
                EList valueList = (EList) posixEObject.eGet( eStructuralFeature );                
                if(!this.adapterRefreshed) {
                  for (Iterator  it = valueList.iterator(); it.hasNext();){                    
                    this.environmentType = (EnvironmentType) it.next();                   
                    tableName.setInput( valueList );
                    } // End Iterator
                             
                  } // Endif
              }                
              break;
              case PosixPackage.POSIX_APPLICATION_TYPE__ARGUMENT:                
              {                
                tableName = this.tableFeaturesMap.get( new Integer(featureID) );
                
                EList<ArgumentType> valueList = (EList) posixEObject.eGet( eStructuralFeature );
                
                
                if(!this.adapterRefreshed) {
                  
                  for (Iterator <ArgumentType> it = valueList.iterator(); it.hasNext();) {                   
                    this.argumentType =  it.next();        
                     tableName.setInput( valueList );
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
          Object value = posixEObject.eGet( eStructuralFeature );
          
          // Check if Attribute has any value
          if (posixEObject.eIsSet( eStructuralFeature )){          
             widgetName = this.widgetFeaturesMap.get( new Integer(featureID) );
             
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
    this.isNotifyAllowed = true;
  } // End void load()
    
  
} // End Class
