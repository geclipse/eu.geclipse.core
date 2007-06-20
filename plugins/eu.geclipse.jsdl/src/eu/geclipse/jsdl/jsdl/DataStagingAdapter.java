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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import eu.geclipse.jsdl.model.CreationFlagEnumeration;
import eu.geclipse.jsdl.model.DataStagingType;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.jsdl.model.SourceTargetType;


/**
 * @author nickl
 *
 */
public class DataStagingAdapter extends JsdlAdaptersFactory {
  
  protected String dataStageEntryKey = ""; //$NON-NLS-1$
  
  protected Hashtable< String, EObject > stageMap =
                                               new Hashtable<String, EObject>();
  
  protected Hashtable< Integer, Text > widgetFeaturesMap =
                                               new Hashtable< Integer, Text >();
  private Hashtable< Integer, List > listFeaturesMap =
                                               new Hashtable< Integer, List >();
  private Hashtable< Integer, Combo > comboFeaturesMap =
                                              new Hashtable< Integer, Combo >();
  
  

  private DataStagingType dataStagingType =
                                  JsdlFactory.eINSTANCE.createDataStagingType();
  private SourceTargetType sourceTargetType = 
                                  JsdlFactory.eINSTANCE.createSourceTargetType();

  private Collection<EObject> dataStageList = new ArrayList<EObject>();
  
  private boolean isNotifyAllowed = true;
  private boolean adapterRefreshed = false;
  
  
  /*
   * Class Constructor 
   */
  public DataStagingAdapter (final EObject rootJsdlElement){


    getTypeForAdapter(rootJsdlElement);
    
  }
  
  
  protected void contentChanged(){
    if (this.isNotifyAllowed){
      fireNotifyChanged( null);
    }
  }
  
  
  
  public void attachToFileName(final List widget){    
    this.listFeaturesMap.put( JsdlPackage.DATA_STAGING_TYPE__FILE_NAME
                                , widget );
        
    widget.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(final SelectionEvent event) {
       List list = (List) event.getSource();
       String [] str = list.getSelection();       
       for (int i=0; i<str.length; i++){
         DataStagingAdapter.this.dataStageEntryKey  = str[i];
         navigateDataStaging(DataStagingAdapter.this.dataStageEntryKey );
         
       }
        
     }

     public void widgetDefaultSelected(final SelectionEvent event) { }
    });
     
  } // End attachToFileName()
  
  
  
  public void attachToFileSystemName(final Text widget){
    Integer featureID = new Integer(JsdlPackage.DATA_STAGING_TYPE__FILESYSTEM_NAME);
    this.widgetFeaturesMap.put( featureID , widget );
       
    
    widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        EObject eObject = DataStagingAdapter.this.stageMap
                            .get( DataStagingAdapter.this.dataStageEntryKey );
        eObject.eSet( JsdlPackage.Literals.DATA_STAGING_TYPE__FILESYSTEM_NAME,
                                        widget.getText() );
        eObject = null;
        //contentChanged();
      }
      public void focusGained(final FocusEvent e ) {//
        
      }
     
    } );
          
  } // End attachToFileSystem()
  
  
  
  
  public void attachToName(final Text widget){
    Integer featureID = new Integer(JsdlPackage.DATA_STAGING_TYPE__NAME);
    this.widgetFeaturesMap.put( featureID , widget );
    
    widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        EObject eObject = DataStagingAdapter.this.stageMap
                              .get( DataStagingAdapter.this.dataStageEntryKey );

        eObject.eSet( JsdlPackage.Literals.DATA_STAGING_TYPE__NAME,
                                    widget.getText() );
        eObject = null;
        contentChanged();
      }
      public void focusGained(final FocusEvent e ) {//
        
      }
     
    } );
   
  } // End attactToName()
  
  
  
  public void attachToSource(final Text widget){  
    Integer featureID = new Integer(JsdlPackage.DATA_STAGING_TYPE__SOURCE);
    this.widgetFeaturesMap.put( featureID , widget );
    
    widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        EObject eObject = DataStagingAdapter.this.stageMap
                            .get( DataStagingAdapter.this.dataStageEntryKey );
        eObject.eSet( JsdlPackage.Literals.DATA_STAGING_TYPE__SOURCE,
        widget.getText() );
        eObject = null;
        contentChanged();
      }
      public void focusGained(final FocusEvent e ) {//
        
      }
     
    } );
  } // End attachToSource()
  
  
  
  public void attachToTarget(final Text widget){    
    Integer featureID = new Integer(JsdlPackage.DATA_STAGING_TYPE__TARGET);
    this.widgetFeaturesMap.put( featureID , widget );
    
    
      
      widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
        public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
          EObject eObject = stageMap.get( dataStageEntryKey );
          eObject.eSet( JsdlPackage.Literals.DATA_STAGING_TYPE__SOURCE,
                        widget.getText() );
          eObject = null;
        }
        public void focusGained(final FocusEvent e ) {
          //
        }
       
      } );

  } // End attachToTarget()
  
  
  
  public void attachToCreationFlag(final Combo widget){
    Integer featureID = new Integer(JsdlPackage.DATA_STAGING_TYPE__CREATION_FLAG);
    this.comboFeaturesMap.put( featureID , widget );
    
    /* Populate the Combo Box with the Creation Flag Literals */    
    EEnum cFEnum = JsdlPackage.Literals.CREATION_FLAG_ENUMERATION;
       for (int i=0; i<cFEnum.getELiterals().size(); i++){         
         widget.add( cFEnum.getEEnumLiteral( i ).toString() );
       }
       cFEnum = null;
          
        
    widget.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent e) {
       EObject eObject = DataStagingAdapter.this.stageMap
                             .get( DataStagingAdapter.this.dataStageEntryKey );
        eObject.eSet( JsdlPackage.Literals.DATA_STAGING_TYPE__CREATION_FLAG,
                      CreationFlagEnumeration.get( widget.getSelectionIndex()));
        eObject = null;
        contentChanged();
      }

      public void widgetDefaultSelected(final SelectionEvent e) {
        //
      }
    });
    
  } // End attachToCreationFlag()
  
  
  
  public void attachToDelOnTermination(final Combo widget){
    Integer featureID = new Integer(JsdlPackage.DATA_STAGING_TYPE__DELETE_ON_TERMINATION);
    this.comboFeaturesMap.put( featureID , widget );
        
    widget.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent e) {
       EObject eObject = DataStagingAdapter.this.stageMap
                             .get( DataStagingAdapter.this.dataStageEntryKey );
        eObject.eSet( JsdlPackage.Literals.DATA_STAGING_TYPE__DELETE_ON_TERMINATION,
                     Boolean.parseBoolean( widget.getItem( widget.getSelectionIndex() ) ) );
        eObject = null;    
        contentChanged();
      }

      public void widgetDefaultSelected(final SelectionEvent e) {
        // Do Nothing
      }
    });
    
  } //End attachToDelOnTermination()
  

  public void attachToDelete(final List list , final Button button){
    button.addSelectionListener(new SelectionListener() {

      public void widgetSelected(final SelectionEvent event) {        
        performDelete(list, DataStagingAdapter.this.dataStageEntryKey);
      }

      public void widgetDefaultSelected(final SelectionEvent event) {
          // Do Nothing - Required method
      }
    });
    
    
  }
  
  
  
  public void attachToAdd(final Button button){
    button.addSelectionListener(new SelectionListener() {

      public void widgetSelected(final SelectionEvent event) {        
        performAdd();
      }

      public void widgetDefaultSelected(final SelectionEvent event) {
          // Do Nothing - Required method
      }
    });
    
    
  }
  
  
  
  private void  getTypeForAdapter(final EObject rootJsdlElement){
    
    TreeIterator <EObject> iterator = rootJsdlElement.eAllContents();
    
    while ( iterator.hasNext (  )  )  {  
   
      EObject testType = iterator.next();
          
      if ( testType instanceof DataStagingType ) {
        this.dataStageList.add( testType );
        } //endif dataStageType
      
    } // End while    
   } // End getTypeforAdapter()
  
  
  
  public void setContent(final EObject rootJsdlElement) {
    
    this.adapterRefreshed = true;
    getTypeForAdapter( rootJsdlElement );
    
  } // End setContent()
  
  
  
  public void load()
  {
    this.isNotifyAllowed = false;
    for (Iterator itList = this.dataStageList.iterator(); itList.hasNext();)
    {
     
      EObject eObject = (EObject) itList.next();
      List listName = null;
       
      // Test if eObject is not empty.
      if(eObject != null) {
        EClass eClass = eObject.eClass();
        
        for (Iterator iterRef = eClass.getEAllStructuralFeatures().iterator(); iterRef.hasNext();){
          
          EStructuralFeature eStructuralFeature = (EStructuralFeature) iterRef.next();        

            if (eStructuralFeature instanceof EAttribute) {         
            // Get Attribute Value.
            Object value = eObject.eGet( eStructuralFeature );        
                 
            int featureID = eStructuralFeature.getFeatureID();
           
            
            if (eObject.eIsSet( eStructuralFeature )){
            switch (featureID) {
              case JsdlPackage.DATA_STAGING_TYPE__FILE_NAME:{
                listName = this.listFeaturesMap.get( new Integer(featureID) );
                if(!this.adapterRefreshed)
                  {listName.add( value.toString() );}
                this.stageMap.put( value.toString(), eObject );
                }              
              break;
              default: // Do Nothing;
              break;
            }
            }
            listName.setSelection( 0 );
            navigateDataStaging( listName.getItem( 0 ) );
          } // endif EAttribute

       } // End Iterator.
  
      } // endif eObject Null
    }// End list loop

    this.isNotifyAllowed = true;
  } // End void load()
  
 
  
  protected void navigateDataStaging(final String key){
    
    this.isNotifyAllowed = false;
        
    Text textWidget = null;
    Combo comboWidget = null;
    EObject eObject = this.stageMap.get( key );
    
    if(eObject != null) {
      EClass eClass = eObject.eClass();
      
      for (Iterator iterRef = eClass.getEAllStructuralFeatures().iterator(); iterRef.hasNext();){
        
        EStructuralFeature eStructuralFeature = (EStructuralFeature) iterRef.next();
        
        int featureID = eStructuralFeature.getFeatureID();
        //Check for the features Multiplicity.
        
        textWidget = this.widgetFeaturesMap.get( new Integer(featureID) );
        
        if (eObject.eIsSet( eStructuralFeature))
          {

          Object value = eObject.eGet( eStructuralFeature );
           
             switch (featureID) {
               case JsdlPackage.DATA_STAGING_TYPE__SOURCE:{
                 
                 this.sourceTargetType = (SourceTargetType) eObject
                                                    .eGet( eStructuralFeature );
                 
                 if (this.sourceTargetType.getURI() != null ){
                   textWidget.setText( this.sourceTargetType.getURI().toString() );  
                 }
                 else
                   { textWidget.setText(""); } //$NON-NLS-1$
               }                 
               break;
               case JsdlPackage.DATA_STAGING_TYPE__TARGET:{
                 
                 this.sourceTargetType = (SourceTargetType) eObject
                                                    .eGet( eStructuralFeature );
                 
                 if (this.sourceTargetType.getURI() != null ){
                   textWidget.setText( this.sourceTargetType.getURI().toString() );
                 }
                 else
                 { textWidget.setText(""); } //$NON-NLS-1$
               }
               break;   
               case JsdlPackage.DATA_STAGING_TYPE__FILESYSTEM_NAME:{
                 textWidget.setText( value.toString() ) ;
               }
               break;
               case JsdlPackage.DATA_STAGING_TYPE__NAME:{
                 textWidget.setText( value.toString() ) ;
               }
               break;
               case JsdlPackage.DATA_STAGING_TYPE__CREATION_FLAG: {
                 comboWidget = this.comboFeaturesMap.get( new Integer(featureID) );
                 comboWidget.setText( value.toString() );        
               }
               break;
               case JsdlPackage.DATA_STAGING_TYPE__DELETE_ON_TERMINATION:{
                 comboWidget = this.comboFeaturesMap.get( new Integer(featureID) );
                 comboWidget.setText( value.toString() );
               }
               break;
               default: // Do Nothing;
               break;
             } //End Switch

          } // endif eIsSet()
        
        else {
          switch( featureID ) {
            case JsdlPackage.DATA_STAGING_TYPE__ANY:
            break;
            case JsdlPackage.DATA_STAGING_TYPE__ANY_ATTRIBUTE:
            break;
            default:textWidget.setText( "" );               //$NON-NLS-1$
            break;
          }// End Switch
          
        } // End Else
        
      } //end Iterator            
    } // endif    
    this.isNotifyAllowed = true;
  } // End navigateDataStage()
  
  
  protected void performAdd(){
   // 
  }
  
  /* Method deletes the selected DataStaged Item from the list and 
   * the JSDL Model.
   */
  protected void performDelete(final List list ,final String key){
    
   
    /* Get EStructuralFeature */
    EObject selected = this.stageMap.get( key );
    
          
    /* Remove EStructuralFeature from Parent. */
    EcoreUtil.remove( selected );  
    list.remove( key );
    
//    if (listWidget.getItemCount()>0)
//    {
//      listWidget.setSelection( 0 );
//      navigateDataStaging(listWidget.getItem( listWidget.getSelectionIndex()));
//    }
//    
//    else
//    {
//      
//    }
        
    this.stageMap.remove( selected );
    
    
  }
  
  
    
  public boolean isEmpty(){
    boolean status = false;

    if (!this.dataStagingType.equals( null )){       
      status = true;
    }
    
    return status;
    
  } // End isEmpty()

  
} // End Class

