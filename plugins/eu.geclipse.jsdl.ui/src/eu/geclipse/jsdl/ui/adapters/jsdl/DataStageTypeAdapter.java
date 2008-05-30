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

import eu.geclipse.jsdl.model.base.DataStagingType;
import eu.geclipse.jsdl.model.base.DocumentRoot;
import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.base.JobDescriptionType;
import eu.geclipse.jsdl.model.base.JsdlFactory;
import eu.geclipse.jsdl.model.base.JsdlPackage;
import eu.geclipse.jsdl.model.base.SourceTargetType;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.pages.Messages;


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
   * Constructs a new <code> {@link DataStageTypeAdapter} </code>
   * 
   * @param jobDefinitionRoot . The root element of a JSDL document ({@link JobDefinitionType}).
   */
  public DataStageTypeAdapter ( final JobDefinitionType jobDefinitionRoot ) {

    getTypeForAdapter(jobDefinitionRoot);
    
  } // End Constructor
  
  
  
  protected void contentChanged() {
    
    if ( this.isNotifyAllowed )  {
      fireNotifyChanged( null);
    }
  } // End void contenctChanged()
  
  
  /*
   * Get the DataStage Type Element from the root Jsdl Element.
   */ 
  private void  getTypeForAdapter( final JobDefinitionType jobDefinitionRoot ) {

    this.jobDescriptionType = jobDefinitionRoot.getJobDescription();
    
  } // End void getTypeforAdapter()
  
  
  
  /**
   * Allows to set the adapter's content on demand and not through the constructor.
   * 
   * @param jobDefinitionRoot The root element of a JSDL document.
   */
  public void setContent( final JobDefinitionType jobDefinitionRoot ) {
    
    this.adapterRefreshed = true;
    getTypeForAdapter( jobDefinitionRoot );
    
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
  public void attachToStageIn( final TableViewer widget ) {
     
    Integer featureID = Integer.valueOf( JsdlPackage.DATA_STAGING_TYPE__SOURCE );
    this.tableFeaturesMap.put( featureID , widget );
    
    widget.addFilter(new ViewerFilter() {
      @Override
      public boolean select( final Viewer viewer, final Object parent, final Object element ) {
        return ( ( DataStagingType ) element).getSource()!= null;
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
  public void attachToStageOut( final TableViewer widget ) {
    
    Integer featureID = Integer.valueOf(  JsdlPackage.DATA_STAGING_TYPE__TARGET );
    this.tableFeaturesMap.put( featureID , widget );
    widget.addFilter( new ViewerFilter() {
      @Override
      public boolean select( final Viewer viewer, final Object parent, final Object element ) {
        return ( ( DataStagingType) element ).getTarget() != null;
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
  public void attachToDelete( final Button button, final TableViewer viewer ) {

    button.addSelectionListener( new SelectionListener() {

      public void widgetSelected( final SelectionEvent event ) {        
        performDelete( viewer );
      }

      public void widgetDefaultSelected( final SelectionEvent event ) {
          // Do Nothing - Required method
      }
    });
    
    
  } // End void attachToDelete()
  
  
  
  

  
//  @SuppressWarnings({
//    "boxing", "unchecked"
//  })
//  public void performAdd ( final TableViewer tableViewer, final Object[] value) {
//    
//    TableViewer stageInViewer = null;
//
//    /* Assume that the table viewer is the one responsible for the Stage-In
//     * elements.
//     */ 
//    stageInViewer = this.tableFeaturesMap
//                     .get( Integer.valueOf(  JsdlPackage.DATA_STAGING_TYPE__SOURCE ) );
//
//    /* Check if the values from the dialog are null. */
//    if (value[0] == null) {
//      return;
//    }
//    
//    EList <DataStagingType> newInputList = ( EList<DataStagingType> )tableViewer.getInput(); 
//    
//    if ( newInputList == null ) {
//      newInputList = new BasicEList<DataStagingType>();
//    }
//    
//    /* Instantiate new DataStage and SourceTarge objects */
//
//    DataStagingType newDataStagingType = JsdlFactory.eINSTANCE.createDataStagingType();
//    SourceTargetType newSourceTargetType = JsdlFactory.eINSTANCE.createSourceTargetType(); 
//
//
//    
//    /*
//     * If the assumption was correct and the table viewer is the one responsible
//     * for the Stage-In elements, create a new data stage element and assign the 
//     * values retrieved from the dialog. 
//     * 
//     */     
//    if ( tableViewer == stageInViewer ) {
//      
//      newSourceTargetType.setURI( value[0].toString() );      
//      newDataStagingType.setName(value[1].toString() );     
//      newDataStagingType.setSource( newSourceTargetType );
//      newDataStagingType.setTarget(null);      
//      newDataStagingType.setFileName( value[1].toString() );          
//      newDataStagingType.setFilesystemName( value[0].toString() );
//      newDataStagingType.setCreationFlag(CreationFlagEnumeration.get( (Integer) value[2]));
//      newDataStagingType.setDeleteOnTermination( (Boolean) value[3] );
//      
//      
//      
//    }
//    else {
//
//      newDataStagingType.setName(value[0].toString() );
//      newSourceTargetType.setURI( value[1].toString() );
//      newDataStagingType.setSource( null );
//      newDataStagingType.setTarget( newSourceTargetType );
//      newDataStagingType.setFileName( value[0].toString() );
//      newDataStagingType.setFilesystemName( value[0].toString() );
//      newDataStagingType.setCreationFlag(CreationFlagEnumeration.get( (Integer) value[2]));
//      newDataStagingType.setDeleteOnTermination( (Boolean) value[3] );
//      
//    }
//      
//    /* Check if the new Data Stage element is not already in the table viewer's
//     * input
//     */
//    boolean exists = doesElementExists( this.dataStagingType, newDataStagingType, newInputList ); 
//    
//    if ( !exists ) {
//      newInputList.add( newDataStagingType );
//    
//      /* Get the EStructural Feature of the DataStaging Type */
//    
//      /* Change the table viewers input. */
//          
//      try {
//        
//        this.jobDescriptionType.getDataStaging().addAll( newInputList );
//        tableViewer.setInput(this.jobDescriptionType.getDataStaging());  
//        
//      } catch( Exception e ) {
//        Activator.logException( e );
//      }
//    
//      /* Refresh the table viewer and notify the editor that
//       *  the page content has changed. 
//       */
//      tableViewer.refresh();   
//      this.contentChanged();
//      
//    }
//      
//    else {      
//            
//      /*
//       * Show the Error Dialog, this means that the new values that are to be added 
//       * are already contained in the table viewer Input. 
//       */
//      MessageDialog.openError( tableViewer.getControl().getShell(),
//                               Messages.getString( "DataStagingPage_DuplicateEntryDialog_Title"), //$NON-NLS-1$
//                               Messages.getString( "DataStagingPage_New_DuplicateEntryDialog_Message" ) ); //$NON-NLS-1$
//    }
//    
//    newInputList = null;
//    
//  } // end void performAdd()
  
  
  
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
   * @param dataStageList The list containing the new DataStaqe elements which were 
   * retrieved from the respective Stage-In or Stage-Out dialogs 
   * 
   */
  @SuppressWarnings({
    "boxing", "unchecked"
  })
  public void performAdd ( final TableViewer tableViewer, 
                           final ArrayList<DataStagingType> dataStageList) {
    
    TableViewer stageInViewer = null;
    
    /* Assume that the table viewer is the one responsible for the Stage-In
     * elements.
     */ 
    stageInViewer = this.tableFeaturesMap
                     .get( Integer.valueOf(  JsdlPackage.DATA_STAGING_TYPE__SOURCE ) );

    /* Check if the values from the dialog are null. */
    if (dataStageList.isEmpty()) {
      return;
    }
    
    EList <DataStagingType> newInputList = ( EList<DataStagingType> )tableViewer.getInput(); 
    
    if ( newInputList == null ) {
      newInputList = new BasicEList<DataStagingType>();
    }
    
      
    /* Check if the new Data Stage element is not already in the table viewer's
     * input
     */
    
    for (int i=0; i<dataStageList.size(); i++){
      
      boolean exists = doesElementExists( this.dataStagingType, dataStageList.get( i ), newInputList ); 
    
      if ( !exists ) {
        newInputList.add( dataStageList.get( i ) );
    
        /* Get the EStructural Feature of the DataStaging Type */
    
        /* Change the table viewers input. */
          
        try {
        
          this.jobDescriptionType.getDataStaging().addAll( newInputList );
          tableViewer.setInput(this.jobDescriptionType.getDataStaging());  
        
        } catch( Exception e ) {
          Activator.logException( e );
        }
    
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
    
    
    
    }
    newInputList = null;
    
  } // end void performAdd()
  
  
  
  protected void checkDataStageElement() {
    
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
    
 
  
    Iterator<?> it = structSelection.iterator();

    /*
     * Iterate over the selections and delete them from the model.
     */
      while ( it.hasNext() ) {
    
    /* Get the First Element of the selection. */
        Object feature = it.next();
    
    /* Cast the first element to DataStageingType */
        DataStagingType selectedDataStage = ( DataStagingType ) feature;    

    /* Remove the selected DataStage object from it's container (JobDescription) */
        try {
          EcoreUtil.remove( selectedDataStage );
      
        } catch( Exception e ) {
          Activator.logException( e );
          
      } // end while
    
        
    /* Refresh the viewer and notify the editor that the page content has 
     * changed. */
    viewer.refresh();
    contentChanged();
    
    } //end iterator
    
  } // End void performDelete()
  
  
  
  /**
   * Edit the attributes of the selected object in the TableViewer. 
   * 
   * @param tableViewer The SWT TableViewer that contains the Structural Features
   * @param dataStageList The list containing the selected DataStage elements.
   *  
   */
  @SuppressWarnings({
    "unchecked", "boxing"
  })
  public void performEdit( final TableViewer tableViewer, 
                           final ArrayList<DataStagingType> dataStageList) {
    
    TableViewer stageInViewer = null;
    
    /* Assume that the table viewer is the one responsible for the Stage-In
     * elements.
     */ 
    stageInViewer = this.tableFeaturesMap
                     .get( Integer.valueOf(  JsdlPackage.DATA_STAGING_TYPE__SOURCE ) );

    /* Check if the values from the dialog are null. */
    if (dataStageList.isEmpty()) {
      return;
    }
    
    //Get the table viewer's input.
    EList <DataStagingType> newInputList = ( EList<DataStagingType> )tableViewer.getInput();
    
    EStructuralFeature eStructuralFeature;
    
    int featureID = JsdlPackage.JOB_DESCRIPTION_TYPE__DATA_STAGING;
    
    /*
     * Get the TableViewer Selection
     */
    IStructuredSelection structSelection 
                              = ( IStructuredSelection ) tableViewer.getSelection();
  
    /* If the selection is not null then Change the selected element */
    if (structSelection != null) {
      for (int i=0; i<dataStageList.size(); i++){
      eStructuralFeature = this.jobDescriptionType.eClass()
                                            .getEStructuralFeature( featureID );

      Object oldDataStageElement = structSelection.getFirstElement();
    
    /* Get the Index of the Element that needs to be changed */
      int index = ( ( java.util.List<Object> ) this.jobDescriptionType.eGet(eStructuralFeature))
                                                           .indexOf( oldDataStageElement  );
      
      
      /* Instantiate new DataStage and SourceTarge objects */
      this.dataStagingType = JsdlFactory.eINSTANCE.createDataStagingType();      
      this.sourceTargetType = JsdlFactory.eINSTANCE.createSourceTargetType();
      
      
      this.dataStagingType = dataStageList.get( i );
      
      /* Check if the new Data Stage element is not already in the table viewer's
       * input
       */        
      if (!doesElementExists( ( DataStagingType ) oldDataStageElement,
                              this.dataStagingType, newInputList )) {  
      
        /* Change the element. The element is located through it's index position
         * in the list.
         */
        ( ( java.util.List<Object> ) this.jobDescriptionType.eGet( eStructuralFeature ) )
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
      }
   } // end_if structSelection
    
    
  } // End void performEdit()
  
  
  /*
   * Private function that checks whether a specific Data Stage element already
   * exists in the input of the table viewer. The criteria for an element match
   * are the equality of Location(either Source or Target) and the FileName.
   * 
   * Returns TRUE if the new element already exists and FALSE if it doesn't. 
   */
  private boolean doesElementExists ( final DataStagingType oldDataStage,
                                      final DataStagingType newDataStage,
                                      final EList <DataStagingType> inputList ) {
    
    boolean result = false;
    
    /*
     * Check if the oldDataStage is not null first.
     */

    /*
     * Check if the new Element is Stage-In element or if is a Stage-Out Element
     */
      if (newDataStage.getSource() != null) {
    
      
      /*
       *  Iterate over all the table viewer input.
       *  If the the new data-stage element is not the old one and
       *  the FileName and Source are the same, return TRUE because
       *  it means that there is a duplicate data-stage element,
       *   so the new one must not be add.
       *  
       */
      
        for( DataStagingType data : inputList ) {
          if( !data.equals( oldDataStage )
              && data.getSource() != null 
              && data.getFileName().equals( newDataStage.getFileName() )
              && data.getSource().getURI().equals( newDataStage.getSource().getURI() ) )
          {
            result = true;
          }
        }
      }
    
      else {
      
      /*
       *  Iterate over all the table viewer input.
       *  If the the new data-stage element is not the old one and
       *  the FileName and Target are the same, return TRUE because
       *  it means that there is a duplicate data-stage element,
       *   so the new one must not be add.
       *  
       */
        for( DataStagingType data : inputList ) {
          if( !data.equals( oldDataStage ) 
              && data.getTarget() != null 
              && data.getFileName().equals( newDataStage.getFileName() )
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
    JobDescriptionType jobDescrEObject  = this.jobDescriptionType;    
    TableViewer tableName = null;
    TableViewer tableName2 = null;
     
    /* Test if eObject is not empty. */
    
    if ( jobDescrEObject != null ) {
      EClass eClass = jobDescrEObject.eClass();
      int featureID;
      
      /* Iterate over all EStructural Features of the Job Description element
       * so as to find the DataStage Element.
       */
      
      for (Iterator<EStructuralFeature> iterRef = 
            eClass.getEAllStructuralFeatures().iterator(); iterRef.hasNext();) {
        
        EStructuralFeature eStructuralFeature = iterRef.next();
           
        featureID =  eStructuralFeature.getFeatureID();        
              
        // Check if the eStructural Reference is an EReferece.
        if (eStructuralFeature instanceof EReference) {
          
          /* Check if Feature is Set ? */
          if ( jobDescrEObject.eIsSet( eStructuralFeature ) ) {
            
            switch( featureID ) {
              case JsdlPackage.JOB_DESCRIPTION_TYPE__DATA_STAGING:               
              { 
                tableName = this.tableFeaturesMap
                    .get( Integer.valueOf( JsdlPackage.DATA_STAGING_TYPE__SOURCE) );
                tableName2 = this.tableFeaturesMap
                     .get( Integer.valueOf( JsdlPackage.DATA_STAGING_TYPE__TARGET ));
                
                       
          
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
 
  
  
} // End Class
