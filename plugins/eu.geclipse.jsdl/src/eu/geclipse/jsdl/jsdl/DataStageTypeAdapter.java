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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.events.FocusEvent;
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
public final class DataStageTypeAdapter extends JsdlAdaptersFactory {
  
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
  
  
  /**
   * DataStageTypeAdapter Class Constructor
   * 
   * @param rootJsdlElement The root element of a JSDL document.
   */
  public DataStageTypeAdapter (final EObject rootJsdlElement){


    getTypeForAdapter(rootJsdlElement);
    
  }
  
  
  protected void contentChanged(){
    
    if (this.isNotifyAllowed){
      fireNotifyChanged( null);
    }
  } //end void contenctChanged()
  
  
  
  /**
   * Adapter interface to attach to the DataStaging FileName list widget.
   * 
   * @param widget The SWT list widget which is associated with the 
   * DataStaging FileName elements of the JSDL document.
   */
  public void attachToFileName(final List widget){ 
    Integer featureID = new Integer(JsdlPackage.DATA_STAGING_TYPE__FILE_NAME);
    this.listFeaturesMap.put( featureID , widget );
        
    widget.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(final SelectionEvent event) {
       List list = (List) event.getSource();
       String [] str = list.getSelection();       
       for (int i=0; i<str.length; i++){
         DataStageTypeAdapter.this.dataStageEntryKey  = str[i];
         navigateDataStaging(DataStageTypeAdapter.this.dataStageEntryKey );
         
       }
        
     }

     @Override
    public void widgetDefaultSelected(final SelectionEvent event) {
       // Do Nothing
     }
    });
     
  } // End attachToFileName()
  
  
  
  /**
   * Adapter interface to attach to the DataStaging FileSystemName text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * DataStaging FileSystemName element of the JSDL document.
   */
  public void attachToFileSystemName(final Text widget){
    Integer featureID = new Integer(JsdlPackage.DATA_STAGING_TYPE__FILESYSTEM_NAME);
    this.widgetFeaturesMap.put( featureID , widget );
       
    
    widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        EObject eObject = DataStageTypeAdapter.this.stageMap
                            .get( DataStageTypeAdapter.this.dataStageEntryKey );
        eObject.eSet( JsdlPackage.Literals.DATA_STAGING_TYPE__FILESYSTEM_NAME,
                                        widget.getText() );
        eObject = null;
        //contentChanged();
      }
      public void focusGained(final FocusEvent e ) {//
        
      }
     
    } );
          
  } // End attachToFileSystem()
  
  
  
  
  /**
   * Adapter interface to attach to the DataStaging Name text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * DataStaging Name element of the JSDL document.
   */
  public void attachToName(final Text widget){
    Integer featureID = new Integer(JsdlPackage.DATA_STAGING_TYPE__NAME);
    this.widgetFeaturesMap.put( featureID , widget );
    
    widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        EObject eObject = DataStageTypeAdapter.this.stageMap
                              .get( DataStageTypeAdapter.this.dataStageEntryKey );

        eObject.eSet( JsdlPackage.Literals.DATA_STAGING_TYPE__NAME,
                                    widget.getText() );
        eObject = null;
        contentChanged();
      }
      public void focusGained(final FocusEvent e ) {//
        
      }
     
    } );
   
  } // End attactToName()
  
  
  
  /**
   * Adapter interface to attach to the DataStaging Source text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * DataStaging Source element of the JSDL document.
   */
  public void attachToSource(final Text widget){  
    Integer featureID = new Integer(JsdlPackage.DATA_STAGING_TYPE__SOURCE);
    this.widgetFeaturesMap.put( featureID , widget );
    
    widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        EObject eObject = DataStageTypeAdapter.this.stageMap
                            .get( DataStageTypeAdapter.this.dataStageEntryKey );
        eObject.eSet( JsdlPackage.Literals.DATA_STAGING_TYPE__SOURCE,
        widget.getText() );
        eObject = null;
        contentChanged();
      }
      public void focusGained(final FocusEvent e ) {//
        
      }
     
    } );
  } // End attachToSource()
  
  
  /**
   * Adapter interface to attach to the DataStaging Target text widget.
   * 
   * @param widget The SWT text widget which is associated with the 
   * DataStaging Target element of the JSDL document.
   */
  public void attachToTarget(final Text widget){    
    Integer featureID = new Integer(JsdlPackage.DATA_STAGING_TYPE__TARGET);
    this.widgetFeaturesMap.put( featureID , widget );
      
      widget.addFocusListener( new org.eclipse.swt.events.FocusListener() {
        public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
          
          EObject eObject = DataStageTypeAdapter.this.stageMap
                            .get( DataStageTypeAdapter.this.dataStageEntryKey );
          
          eObject.eSet( JsdlPackage.Literals.DATA_STAGING_TYPE__SOURCE,
                        widget.getText() );
          eObject = null;
        }
        public void focusGained(final FocusEvent e ) {
          // Do Nothing
        }
       
      } );

  } // End attachToTarget()
  
  
  
  /**
   * Adapter interface to attach to the DataStaging CreationFlag combo widget.
   * 
   * @param widget The SWT combo widget which is associated with the 
   * DataStaging CreationFlag element of the JSDL document.
   */
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
       EObject eObject = DataStageTypeAdapter.this.stageMap
                             .get( DataStageTypeAdapter.this.dataStageEntryKey );
        eObject.eSet( JsdlPackage.Literals.DATA_STAGING_TYPE__CREATION_FLAG,
                      CreationFlagEnumeration.get( widget.getSelectionIndex()));
        eObject = null;
        contentChanged();
      }

      public void widgetDefaultSelected(final SelectionEvent e) {
        // Do Nothing
      }
    });
    
  } // End attachToCreationFlag()
  
  
  
  /**
   * Adapter interface to attach to the DataStaging DeleteOnTermination combo
   *  widget.
   * 
   * @param widget The SWT combo widget which is associated with the 
   * DataStaging DeleteOnTermination element of the JSDL document.
   */
  public void attachToDelOnTermination(final Combo widget){
    Integer featureID = new Integer(JsdlPackage.DATA_STAGING_TYPE__DELETE_ON_TERMINATION);
    this.comboFeaturesMap.put( featureID , widget );
        
    widget.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent e) {
       EObject eObject = DataStageTypeAdapter.this.stageMap
                             .get( DataStageTypeAdapter.this.dataStageEntryKey );
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
  

  /**
   * 
   * Adapter interface to attach to the DataStaging Delete button
   * widget.
   *  
   * @param list The SWT list containing the DataStage element to be deleted.
   * @param button The SWT Button that triggered the Delete event.
   */
  public void attachToDelete(final List list , final Button button){
    
    list.addSelectionListener(new SelectionListener() {
      

      public void widgetSelected(final SelectionEvent e ) {
        if (list.getItemCount()>0){
          
          button.setEnabled( true );
        }     
      }

      public void widgetDefaultSelected( final SelectionEvent e ) {
        //  Auto-generated method stub   
      }
    });
    
    
    button.addSelectionListener(new SelectionListener() {

      public void widgetSelected(final SelectionEvent event) {        
        performDelete(list, DataStageTypeAdapter.this.dataStageEntryKey);
        if (list.getItemCount() == 0) {
          button.setEnabled( false ); 
        }
      }

      public void widgetDefaultSelected(final SelectionEvent event) {
          // Do Nothing - Required method
      }
    });
    
    
  }
  
  
  
  /**
   * 
   * Adapter interface to attach to the DataStaging Add button
   * widget.
   *
   * @param button The SWT Button that triggered the Add. event.
   */
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
  
  
  
  /**
   * Allows to set the adapter's content on demand and not through the constructor.
   * 
   * @param rootJsdlElement The root element of a JSDL document.
   */
  public void setContent(final EObject rootJsdlElement) {
    
    this.adapterRefreshed = true;
    getTypeForAdapter( rootJsdlElement );
    
  } // End setContent()
  
  
  
  /**
   * 
   * This method populates the model content to the widgets registered with the
   * DataStageType adapter.
   */
  public void load()
  {
    this.isNotifyAllowed = false;
    for (Iterator <EObject> itList = this.dataStageList.iterator(); itList.hasNext();)
    {
     
      EObject eObject = itList.next();
      List listName = null;
       
      // Test if eObject is not empty.
      if(eObject != null) {
        EClass eClass = eObject.eClass();
        
//        for (Iterator iterRef = eClass.getEAllStructuralFeatures().iterator(); iterRef.hasNext();){
//          
//          EStructuralFeature eStructuralFeature = (EStructuralFeature) iterRef.next();
        
        EList<EStructuralFeature> allEStructuralFeatures = eClass.getEAllStructuralFeatures();
        for( EStructuralFeature eStructuralFeature : allEStructuralFeatures) {


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

      EList<EStructuralFeature> allEStructuralFeatures = eClass.getEAllStructuralFeatures();
      for( EStructuralFeature eStructuralFeature : allEStructuralFeatures) {
        
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
    contentChanged();
    
    
  }
  
  
  /**
   * @return TRUE if the adapter is empty. If it is empty, it means that there 
   * is no JobDefinition element in the JSDL document. 
   */  
  public boolean isEmpty(){
    boolean status = false;

    if (!this.dataStagingType.equals( null )){       
      status = true;
    }
    
    return status;
    
  } // End isEmpty()

  
} // End Class

