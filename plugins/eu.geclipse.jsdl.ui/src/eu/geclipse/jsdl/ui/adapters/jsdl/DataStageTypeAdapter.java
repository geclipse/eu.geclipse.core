/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initialia development of the original code was made for
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
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import eu.geclipse.jsdl.model.DataStagingType;
import eu.geclipse.jsdl.model.JobDefinitionType;
import eu.geclipse.jsdl.model.JobDescriptionType;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.jsdl.model.JsdlPackage;



/**
 * @author nickl
 *
 */
public class DataStageTypeAdapter extends JsdlAdaptersFactory {
  
  private boolean isNotifyAllowed = true;
  private boolean adapterRefreshed = false;
  
  
  private DataStagingType dataStagingType =
                                  JsdlFactory.eINSTANCE.createDataStagingType();
  
  
  private Hashtable< Integer, TableViewer > tableFeaturesMap = new Hashtable< Integer, TableViewer >();
  private JobDescriptionType jobDescriptionType = JsdlFactory.eINSTANCE.createJobDescriptionType();
  
  
  /**
   * @param rootJsdlElement
   */
  public DataStageTypeAdapter (final EObject rootJsdlElement){

    getTypeForAdapter(rootJsdlElement);
    
  } // End Constructor
  
  
  
  protected void contentChanged(){
    
    if (this.isNotifyAllowed){
      fireNotifyChanged( null);
    }
  } // End void contenctChanged()
  
  
  
  private void  getTypeForAdapter(final EObject rootJsdlElement) {

    this.jobDescriptionType = ((JobDefinitionType ) rootJsdlElement).getJobDescription();
    this.jobDescriptionType.getDataStaging();
    
   } // End void getTypeforAdapter()
  
  
  
  /**
   * @param rootJsdlElement
   */
  public void setContent(final EObject rootJsdlElement) {
    
    this.adapterRefreshed = true;
    getTypeForAdapter( rootJsdlElement );
    
  } // End void setContent()
  
  
  
  /**
   * @param widget
   */
  public void attachToStageIn(final TableViewer widget) {
    
    Integer featureID = new Integer(JsdlPackage.DATA_STAGING_TYPE__SOURCE);
    this.tableFeaturesMap.put( featureID , widget );
    
    widget.addFilter(new ViewerFilter() {
      @Override
      public boolean select(final Viewer viewer, final Object parent, final Object element) {
        return ((DataStagingType) element).getSource()!= null;
      }
    });
  }
  
  
  
  /**
   * @param widget
   */
  public void attachToStageOut(final TableViewer widget) {
    
    Integer featureID = new Integer(JsdlPackage.DATA_STAGING_TYPE__TARGET);
    this.tableFeaturesMap.put( featureID , widget );
    widget.addFilter(new ViewerFilter() {
      @Override
      public boolean select(final Viewer viewer, final Object parent, final Object element) {
        return ((DataStagingType) element).getTarget()!= null;
      }
    });
  }
  
  
  
  /**
   * 
   * This method populates the model content to the widgets registered with the
   * DataStageType adapter.
   */
  public void load()
  {
    this.isNotifyAllowed = false;
    EObject jobDescrEObject  = this.jobDescriptionType;    
    TableViewer tableName = null;
    TableViewer tableName2 = null;
     
    /* Test if eObject is not empty. */
    
    if (jobDescrEObject != null) {
      EClass eClass = jobDescrEObject.eClass();
      
      for (Iterator<EStructuralFeature> iterRef = 
            eClass.getEAllStructuralFeatures().iterator(); iterRef.hasNext();) {
        
        EStructuralFeature eStructuralFeature = iterRef.next();
           
        int featureID =  eStructuralFeature.getFeatureID();        
              
        
        if (eStructuralFeature instanceof EReference) {
          
          /* Check if Feature is Set ? */
          if (jobDescrEObject.eIsSet( eStructuralFeature )) {
            switch( featureID ) {
              case JsdlPackage.JOB_DESCRIPTION_TYPE__DATA_STAGING:               
              {                     
                tableName = this.tableFeaturesMap
                    .get( new Integer(JsdlPackage.DATA_STAGING_TYPE__SOURCE) );
                tableName2 = this.tableFeaturesMap
                     .get(new Integer( JsdlPackage.DATA_STAGING_TYPE__TARGET ));
                
                EList valueList = (EList) jobDescrEObject.eGet( eStructuralFeature );                
                if( !this.adapterRefreshed ) {               

                  tableName.setInput( valueList );
                  tableName2.setInput( valueList);
                  
                  
                  } // End_if adapterRefreshed
               } // end case DATA_STAGING
                              
              break;
              default:
              break;
          } // end Switch()
            
          } // end_if eIsSet()
          
        } //End EReference
        
      } // End For
      
    } // End_if null
    
  }// end load()
  
} // End Class
