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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

import eu.geclipse.jsdl.model.CreationFlagEnumeration;
import eu.geclipse.jsdl.model.DataStagingType;
import eu.geclipse.jsdl.model.DocumentRoot;
import eu.geclipse.jsdl.model.JobDefinitionType;
import eu.geclipse.jsdl.model.JobDescriptionType;
import eu.geclipse.jsdl.model.JsdlFactory;
import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.jsdl.model.SourceTargetType;
import eu.geclipse.jsdl.ui.internal.pages.Messages;



/**
 * @author nloulloud
 *
 */


/**
 * DataStageTypeAdapter Class
 * <p>
 * This class provides adapters for manipulating <b>Data Staging </b> 
 * elements  through the Data Staging Page of the JSDL editor. 
 * Supported DataStaging elements are:
 * <p>
 * - FileName
 * <p>
 * - FileSystemName
 * <p>
 * - CreationFlag
 * <p>
 * - DeleteOnTermination
 * <p>
 * - Source
 * <p>
 * - Target
 *
 */
public class DataStageTypeAdapter extends JsdlAdaptersFactory {
  
  private boolean isNotifyAllowed = true;
  private boolean adapterRefreshed = false;
  
  private DocumentRoot documentRoot = 
                                    JsdlFactory.eINSTANCE.createDocumentRoot();
  
  
  private DataStagingType dataStagingType =
                                  JsdlFactory.eINSTANCE.createDataStagingType();
  
  private SourceTargetType sourceTargetType = 
                                 JsdlFactory.eINSTANCE.createSourceTargetType();
  
    
  private Hashtable< Integer, TableViewer > tableFeaturesMap = 
                                        new Hashtable< Integer, TableViewer >();
  
  private JobDescriptionType jobDescriptionType = 
                               JsdlFactory.eINSTANCE.createJobDescriptionType();
  
  
  /**
   * DataStageTypeAdapter Class Constructor
   * 
   * @param rootJsdlElement The root element of a JSDL document.
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
//    this.jobDescriptionType.getDataStaging();
    
   } // End void getTypeforAdapter()
  
  
  
  /**
   * Allows to set the adapter's content on demand and not through the constructor.
   * 
   * @param rootJsdlElement The root element of a JSDL document.
   */
  public void setContent(final EObject rootJsdlElement) {
    
    this.adapterRefreshed = true;
    getTypeForAdapter( rootJsdlElement );
    
  } // End void setContent()
  
  
  
  /**
   * Adapter interface to attach to the DataStaging Stage-In Table Viewer widget
   * 
   * @param widget The {@link TableViewer} in which all the Stage-In elements are
   * displayed. The Stage-In elements are those elements that contain only the 
   * Source URI. The Target URI on these elements is null. Elements which
   * contain Stage-In and Stage-Out are also shown in this table viewer.
   * 
   * This method add's a filter to the table viewer so as to show only DataStage 
   * elements that contain a Source URI.
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
    
  } // End void attactToStageIn()
  
  
  /**
   * Adapter interface to attach to the DataStaging Stage-Out Table Viewer widget
   * 
   * @param widget The {@link TableViewer} in which all the Stage-In elements are
   * displayed. The Stage-Out elements are those elements that contain only the 
   * Target location. The Source location on these elements is null. Elements which
   * contain Stage-In and Stage-Out are also shown in this table viewer.
   * 
   * This method add's a filter to the table viewer so as to show only DataStage 
   * elements that contain a Target URI.
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
    
  } //End void attactToStageOut()
  
  
  
  /**
   * 
   * Adapter interface to attach to the DataStage Delete button
   * widget.
   *  
   * @param button The SWT Button that triggered the Delete event.
   * @param viewer The {@link org.eclipse.jface.viewers.TableViewer}
   * containing the element to be deleted.
   */
  public void attachToDelete(final Button button, final TableViewer viewer){

    button.addSelectionListener(new SelectionListener() {

      public void widgetSelected(final SelectionEvent event) {        
        performDelete(viewer);
      }

      public void widgetDefaultSelected(final SelectionEvent event) {
          // Do Nothing - Required method
      }
    });
    
    
  } // End void attachToDelete()
  
  
  
  
  /**
   * Add's a new DataStaging element in the JSDL model. The new DataStaging element
   * is added as a child of the JobDescription element. The new DataStaging element
   * can be a Stage-In element (containing a Source URI) or a Stage-Out element 
   * (containing a Target URI). 
   * 
   * @param tableViewer The {@link TableViewer} to add the new DataStaging element.
   * If the new DataStaging element is a Stage-In element, then the table viewer 
   * responsible for such elements should be passed here. The opposite is valid 
   * for Stage-Out elements. 
   * 
   * 
   * @param value The value array obtained from the Dialog.
   */
  
  @SuppressWarnings({
    "boxing", "unchecked"
  })
  public void performAdd ( final TableViewer tableViewer, final Object[] value) {
    
    TableViewer stageInViewer = null;

    /* Assume that the table viewer is the one responsible for the Stage-In
     * elements.
     */ 
    stageInViewer = this.tableFeaturesMap
                     .get( new Integer(JsdlPackage.DATA_STAGING_TYPE__SOURCE) );

    /* Check if the values from the dialog are null. */
    if (value[0] == null) {
      return;
    }
    
    EStructuralFeature eStructuralFeature = null;   
    Collection<DataStagingType> collection = new ArrayList<DataStagingType>();
    int featureID = JsdlPackage.JOB_DESCRIPTION_TYPE__DATA_STAGING;

    
    EList <DataStagingType> newInputList = (EList<DataStagingType>)tableViewer.getInput(); 
    
    if (newInputList == null) {
      newInputList = new BasicEList<DataStagingType>();
    }
    
    /* Instantiate new DataStage and SourceTarge objects */
    
    this.dataStagingType = JsdlFactory.eINSTANCE.createDataStagingType();
    this.sourceTargetType = JsdlFactory.eINSTANCE.createSourceTargetType();
    
    /*
     * If the assumption was correct and the table viewer is the one responsible
     * for the Stage-In elements, create a new data stage element and assign the 
     * values retrieved from the dialog. 
     * 
     */     
    if (tableViewer == stageInViewer) {
       
      this.dataStagingType.setName(value[0].toString() );    
      this.sourceTargetType.setURI( value[1].toString() );
      this.dataStagingType.setSource( this.sourceTargetType );
      this.dataStagingType.setTarget( null );
      this.dataStagingType.setFileName( value[0].toString() );
      this.dataStagingType.setFilesystemName( value[0].toString() );
      this.dataStagingType.setCreationFlag(CreationFlagEnumeration.get( (Integer) value[2]));
      this.dataStagingType.setDeleteOnTermination( (Boolean) value[3] );
      
    }
    else {
      
      this.dataStagingType.setName(value[0].toString() );    
      this.sourceTargetType.setURI( value[1].toString() );
      this.dataStagingType.setSource( null );
      this.dataStagingType.setTarget( this.sourceTargetType );
      this.dataStagingType.setFileName( value[0].toString() );
      this.dataStagingType.setFilesystemName( value[0].toString() );
      this.dataStagingType.setCreationFlag(CreationFlagEnumeration.get( (Integer) value[2]));
      this.dataStagingType.setDeleteOnTermination( (Boolean) value[3] );
    }
      
    /* Check if the new Data Stage element is not already in the table viewer's
     * input
     */
    if (!doesElementExists( this.dataStagingType, newInputList )) {
      newInputList.add( this.dataStagingType );
    
      eStructuralFeature = this.jobDescriptionType.eClass().getEStructuralFeature( featureID );
    
      // Change the table viewers input.
      tableViewer.setInput(newInputList);
    
      for ( int i=0; i<tableViewer.getTable().getItemCount(); i++ ) {      
        collection.add( (DataStagingType) tableViewer.getElementAt( i ) );
      }
      
      this.jobDescriptionType.eSet(eStructuralFeature, collection);
    
      /* Refresh the table viewer and notify the editor that
       *  the page content has changed. 
       */
      tableViewer.refresh();   
      this.contentChanged();
      
    }
    else {
      
      /*
       * Show the Error Dialog, this means that the new values that are to be added 
       * are already contained in the table viewer Input. 
       */
      MessageDialog.openError( tableViewer.getControl().getShell(),
                               Messages.getString( "DataStagingPage_DuplicateEntryDialog_Title"), //$NON-NLS-1$
                               Messages.getString( "DataStagingPage_New_DuplicateEntryDialog_Message" ) ); //$NON-NLS-1$
    }
    
    eStructuralFeature = null;
    newInputList = null;
    collection = null;
  
    
  } // end void performAdd()
  
  
  
  protected void checkDataStageElement(){
    
    EStructuralFeature eStructuralFeature = this.documentRoot.eClass()
    .getEStructuralFeature( JsdlPackage.JOB_DESCRIPTION_TYPE__DATA_STAGING );
    
    Collection<DataStagingType> collection = 
                                          new ArrayList<DataStagingType>();
    collection.add( this.dataStagingType);
        
    if (!this.jobDescriptionType.eIsSet( eStructuralFeature )){      
      this.jobDescriptionType.eSet( eStructuralFeature, collection );


    }
  } // end void checkDataStageElement()
  
  
  /**
   * Delete the selected Element in the TableViewer. The selected element must
   * be of type: {@link DataStagingType}
   * 
   */
  protected void performDelete(final TableViewer viewer ) {
    
    /* Get the table viewer selection.  */
    IStructuredSelection structSelection 
                               = ( IStructuredSelection ) viewer.getSelection();
    
    /* Get the First Element of the selection. */
    Object feature = structSelection.getFirstElement();
    
    /* Cast the first element to DataStageingType */
    DataStagingType selectedDataStage = (DataStagingType) feature;    

    /* Remove the selected DataStage object from it's container (JobDescription) */
    EcoreUtil.remove( selectedDataStage);
        
    /* Refresh the viewer and notify the editor that the page content has 
     * changed. */
    viewer.refresh();
    contentChanged();
    
  } // End void performDelete()
  
  
  
  /**
   * Edit the attributes of the selected object in the TableViewer. 
   * 
   * @param tableViewer The SWT TableViewer that contains the Structural Features
   * @param value 
   */
  @SuppressWarnings({
    "unchecked", "boxing"
  })
  public void performEdit(final TableViewer tableViewer, final Object[] value) {
    
    TableViewer stageInViewer = null;
    
    /* Assume that the table viewer is the one responsible for the Stage-In
     * elements.
     */ 
    stageInViewer = this.tableFeaturesMap
                     .get( new Integer(JsdlPackage.DATA_STAGING_TYPE__SOURCE) );

    /* Check if the values from the dialog are null. */
    if (value[0] == null) {
      return;
    }
    
    //Get the table viewer's input.
    EList <DataStagingType> newInputList = (EList<DataStagingType>)tableViewer.getInput();
    
    EStructuralFeature eStructuralFeature;
    
    int featureID = JsdlPackage.JOB_DESCRIPTION_TYPE__DATA_STAGING;
    
    /*
     * Get the TableViewer Selection
     */
    IStructuredSelection structSelection 
                              = ( IStructuredSelection ) tableViewer.getSelection();
  
    /* If the selection is not null the Change the selected element */
    if (structSelection != null) {
      
      eStructuralFeature = this.jobDescriptionType.eClass()
                                            .getEStructuralFeature( featureID );

      Object feature = structSelection.getFirstElement();
    
    /* Get the Index of the Element that needs to be changed */
      int index = (( java.util.List<Object> )this.jobDescriptionType.eGet(eStructuralFeature))
                                                           .indexOf( feature  );
      
      
      /* Instantiate new DataStage and SourceTarge objects */
      this.dataStagingType = JsdlFactory.eINSTANCE.createDataStagingType();      
      this.sourceTargetType = JsdlFactory.eINSTANCE.createSourceTargetType();
      
     /*
      * If the assumption was correct and the table viewer is the one responsible
      * for the Stage-In elements, create a new data stage element and assign the 
      * values retrieved from the dialog. 
      * 
      */
      if (tableViewer == stageInViewer) {
        
        this.sourceTargetType.setURI( value[0].toString() );
        this.dataStagingType.setSource( this.sourceTargetType );  
        this.dataStagingType.setFileName( value[1].toString() );        
        this.dataStagingType.setCreationFlag(CreationFlagEnumeration.get( (Integer) value[2]));
        this.dataStagingType.setDeleteOnTermination( (Boolean) value[3] );
        
        

      } // end_if (tableViewer == stageInViewer)
      
      /*
       * Else the table viewer is the one responsible for the Stage-Out Data Stage
       * elements. 
       */
      else {
        
        this.dataStagingType.setFileName( value[0].toString() );
        this.sourceTargetType.setURI( value[1].toString() );
        this.dataStagingType.setTarget( this.sourceTargetType ); 
        this.dataStagingType.setCreationFlag(CreationFlagEnumeration.get( (Integer) value[2]));
        this.dataStagingType.setDeleteOnTermination( (Boolean) value[3] );
        
      } // End else
      
      /* Check if the new Data Stage element is not already in the table viewer's
       * input
       */        
      if (!doesElementExists( this.dataStagingType, newInputList )) {  
      
        /* Change the element. The element is located through it's index position
         * in the list.
         */
        (( java.util.List<Object> )this.jobDescriptionType.eGet( eStructuralFeature ))
            .set( index, this.dataStagingType );
  
        /* Refresh the table viewer and notify the editor that
         *  the page content has changed. 
         */
        tableViewer.refresh();       

        contentChanged();
    
    } // end_if doesElementExits()
      
    else {
      /*
       * Show the Error Dialog, this means that the new values that are edited 
       * are already contained in the table viewer Input. 
       */
      MessageDialog.openError( tableViewer.getControl().getShell(),
              Messages.getString( "DataStagingPage_DuplicateEntryDialog_Title"), //$NON-NLS-1$
              Messages.getString( "DataStagingPage_Edit_DuplicateEntryDialog_Message" ) ); //$NON-NLS-1$
      
    } // End else
      
   } // end_if structSelection
    
    
  } // End void performEdit()
  
  
  /*
   * Private function that checks whether a specific Data Stage element already
   * exists in the input of the table viewer. The criteria for an element match
   * are the equality of Location(either Source or Target) and the FileName.
   * 
   * Returns TRUE if the new element already exists and FALSE if it doesn't. 
   */
  private boolean doesElementExists (final DataStagingType newDataStage,
                                     final EList <DataStagingType> inputList) {
    
    boolean result = false; 
    
    /*
     * Check if the new Element is Stage-In element or if is a Stage-Out Element
     */
    if (newDataStage.getSource() != null) {
    
      // Iterate over all the table viewer input
      for( DataStagingType data : inputList ) {
        if( data.getFileName().equals( newDataStage.getFileName() )
            && data.getSource().getURI().equals( newDataStage.getSource().getURI() ) )
        {
          result = true;
        }
      }
    }
    
    else {
      // Iterate over all the table viewer input
      for( DataStagingType data : inputList ) {
        if( data.getFileName().equals( newDataStage.getFileName() )
            && data.getTarget().getURI().equals( newDataStage.getTarget().getURI() ) )
        {
          result = true;
        }
      }
    }
    
    return result;
    
  } // End boolean doesElementExists()
   
   
    
  /**
   * 
   * This method populates the model content to the widgets registered with the
   * DataStageType adapter.
   */
  @SuppressWarnings("unchecked")
  public void load()
  {
    
    this.isNotifyAllowed = false;
    EObject jobDescrEObject  = this.jobDescriptionType;    
    TableViewer tableName = null;
    TableViewer tableName2 = null;
     
    /* Test if eObject is not empty. */
    
    if (jobDescrEObject != null) {
      EClass eClass = jobDescrEObject.eClass();
      
      /* Iterate over all EStructural Features of the Job Description element
       * so as to fine the DataStage Element.
       */
      
      for (Iterator<EStructuralFeature> iterRef = 
            eClass.getEAllStructuralFeatures().iterator(); iterRef.hasNext();) {
        
        EStructuralFeature eStructuralFeature = iterRef.next();
           
        int featureID =  eStructuralFeature.getFeatureID();        
              
        // Check if the eStructural Reference is an EReferece.
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
                
                       
          
                EList<EStructuralFeature > valueList = (EList<EStructuralFeature>) jobDescrEObject.eGet( eStructuralFeature );
                
                
                  // The two table viewers for Stage-In and Stage-Out
                  // have the same input, the only difference between them is 
                  // the different LabelProviders
                  tableName.setInput( valueList );
                  tableName2.setInput( valueList);

               } // end case DATA_STAGING
                              
              break;
              default:
              break;
          } // end Switch()
            
          } // end_if eIsSet()
          
        } //End EReference
        
      } // End For
      
    } // End_if null
    
    /* 
     * If the refresh flag is set the unset it since we finished the refresh of
     * the widgets
     */
    if ( this.adapterRefreshed ) {
      this.adapterRefreshed = false;
    }
    
    this.isNotifyAllowed = true;
    
  }// end load()
  
  
  
  /**
   * @return TRUE if the adapter is empty. If it is empty, it means that there 
   * is no DataStaging element in the JSDL document. 
   */ 
  public boolean isEmpty() {
    boolean status = false;

    if ( !this.dataStagingType.equals( null ) ) {       
      status = true;
    }
    
    return status;
  } // End isEmpty()
  
  
  
} // End Class
