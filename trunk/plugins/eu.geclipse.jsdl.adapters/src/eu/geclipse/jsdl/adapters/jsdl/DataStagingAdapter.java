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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.List;
import eu.geclipse.jsdl.DataStagingType;
import eu.geclipse.jsdl.JsdlFactory;
import eu.geclipse.jsdl.JsdlPackage;


/**
 * @author nickl
 *
 */
public class DataStagingAdapter {
  
  Hashtable< String, Integer > fileNameRandMap = new Hashtable< String, Integer >();
  Hashtable< Integer, Integer > randFeaturesMap = new Hashtable< Integer, Integer >();
  Hashtable< Integer, Object > widgetFeaturesMap = new Hashtable< Integer, Object >();
  private DataStagingType dataStagingType;
  private Collection<EObject> dataStageList = new ArrayList<EObject>();
  
  
  
  /*
   * Class Constructor
   */
  public DataStagingAdapter (final EObject rootJsdlElement){
    
    this.dataStagingType = JsdlFactory.eINSTANCE.createDataStagingType();    
    getTypeForAdapter(rootJsdlElement);
    
  }
  
  private void  getTypeForAdapter(final EObject rootJsdlElement){
    
    TreeIterator iterator = rootJsdlElement.eAllContents();
    
    while ( iterator.hasNext (  )  )  {  
   
      EObject testType = (EObject) iterator.next();
          
      if ( testType instanceof DataStagingType ) {
        this.dataStageList.add( testType );  

      } //endif ApplicationType
      
    } //End while
    
   } // End getTypeforAdapter

  public void setContent(final EObject rootJsdlElement){
    
  getTypeForAdapter( rootJsdlElement );
  
  }
    
  public void attachToApplicationName(final List widget){    
  this.widgetFeaturesMap.put( JsdlPackage.APPLICATION_TYPE__APPLICATION_NAME
                              , widget );     
   
  }
  
  
  public void load()
  {
    for (Iterator iterator = dataStageList.iterator(); iterator.hasNext();){
        
      
    }
//    EObject object = this.dataStagingType;
//    Text widgetName = null;
//    //EDataType dataType = null;
//    
//    // Test if eObject is not empty.
//    if(object != null) {
//      EClass eClass = object.eClass();
//             
//        
//      for (Iterator iter = eClass.getEAllAttributes().iterator(); iter.hasNext();) {      
//        EAttribute attribute = (EAttribute) iter.next();
//                                               
//        //Get Attribute Value.
//        Object value = object.eGet( attribute );        
//             
//        Integer featureID = attribute.getFeatureID();
//              
//        //Check if Attribute has any value
//        if (object.eIsSet( attribute )){          
//           widgetName = this.widgetFeaturesMap.get( featureID );
//                 
//         
//           if (attribute.getName().toString() != "any"){ //$NON-NLS-1$
//             widgetName.setText(value.toString());
//         } //end if
//                   
//        } //end if
//      } //end for
//    } //end if    
  } // End void load()
  
  
  
  public boolean isEmpty(){
    boolean status = false;

    if (!this.dataStagingType.equals( null )){       
      status = true;
    }
    
    return status;
  }

  
} // End Class

