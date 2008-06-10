/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.jsdl.ui.internal.pages.sections;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;

import eu.geclipse.jsdl.model.base.DataStagingType;
import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.model.base.JobDescriptionType;
import eu.geclipse.jsdl.model.base.JsdlFactory;
import eu.geclipse.jsdl.model.base.JsdlPackage;
import eu.geclipse.jsdl.ui.adapters.jsdl.JsdlAdaptersFactory;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.dialogs.DataStagingInDialog;
import eu.geclipse.jsdl.ui.internal.pages.FormSectionFactory;
import eu.geclipse.jsdl.ui.internal.pages.Messages;
import eu.geclipse.jsdl.ui.providers.DataStageInLabelProvider;
import eu.geclipse.jsdl.ui.providers.FeatureContentProvider;


/**
 * @author nloulloud
 *
 */
public class DataStageInSection extends JsdlAdaptersFactory {
  
  
  private static final int WIDGET_HEIGHT = 170;
  protected Button btnStageInAdd = null;
  protected Button btnStageInEdit = null;
  protected Button btnStageInDel = null;
  protected Table tblStageIn = null;
  protected TableViewer stageInViewer = null;
  protected ArrayList<DataStagingType>dataStageList = new ArrayList<DataStagingType>();
  
  private TableColumn column;  
  private Composite containerComposite = null;
  private boolean adapterRefreshed = false;
  private boolean isNotifyAllowed = true;
  private DataStagingType dataStagingType = JsdlFactory.eINSTANCE.createDataStagingType();
  private JobDescriptionType jobDescriptionType = null;
  private EList<DataStagingType> dataStageInputList = null;
  
  
  
  public DataStageInSection( final Composite parent, final FormToolkit toolkit ){
    
    this.containerComposite = parent;
    createSection( parent, toolkit );
    
  }
  
  
  
  /* This function creates the Stage-In Section of the DateStage Page */
  private void createSection( final Composite parentComposite, final FormToolkit toolkit ) {
   
   String sectionTitle = Messages.getString( "DataStagingPage_StageInSection" ); //$NON-NLS-1$
   String sectionDescription = Messages.getString( "DataStagingPage_StageInDescr" );   //$NON-NLS-1$
   
   GridData gd;
      
   Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                          parentComposite,
                                          sectionTitle,
                                          sectionDescription,
                                          3 );
     
   
   gd = new GridData();
   
   gd.horizontalAlignment = GridData.FILL;
   gd.verticalAlignment = GridData.FILL;
   gd.verticalSpan = 3;
   gd.horizontalSpan = 1;
   gd.widthHint = 600;
   gd.heightHint = WIDGET_HEIGHT;
   
   this.stageInViewer = new TableViewer( client, SWT.BORDER| SWT.FULL_SELECTION | SWT.MULTI);
   
   this.tblStageIn = this.stageInViewer.getTable();
   this.tblStageIn .setHeaderVisible( true);   
   this.tblStageIn.setLinesVisible( true );
   
   /* Set the common Content Provider  */
   this.stageInViewer.setContentProvider( new FeatureContentProvider() );
   /* Set the dedicated Label Provider for DataStage-In elements */
   this.stageInViewer.setLabelProvider( new DataStageInLabelProvider() );   
   
   this.column = new TableColumn( this.tblStageIn, SWT.LEFT );    
   this.column.setText( Messages.getString( "DataStagingPage_Source" ) ); //$NON-NLS-1$
   this.column.setWidth( 200 );
   this.column = new TableColumn( this.tblStageIn, SWT.CENTER );    
   this.column.setText( Messages.getString( "DataStagingPage_FileName") ); //$NON-NLS-1$
   this.column.setWidth( 150 );
   this.column = new TableColumn( this.tblStageIn, SWT.CENTER );    
   this.column.setText( Messages.getString( "DataStagingPage_CreationFlag" ) ); //$NON-NLS-1$
   this.column.setWidth( 100 );
   this.column = new TableColumn( this.tblStageIn, SWT.CENTER );    
   this.column.setText( Messages.getString( "DataStagingPage_DeleteOnTermination" ) ); //$NON-NLS-1$
   this.column.setWidth( 100 );
   
   
   this.stageInViewer.addFilter( new ViewerFilter() {
     @Override
     public boolean select( final Viewer viewer, final Object parent, final Object element ) {
       return ( ( DataStagingType ) element).getSource()!= null;
     }
   });
   
   /* Based on the Table Viewer selection, update the status of the respective
    * buttons.
    */
   this.stageInViewer .addSelectionChangedListener( new ISelectionChangedListener()
   {

     public void selectionChanged( final SelectionChangedEvent event ) {       
       updateButtons( ( TableViewer )event.getSource() );
     }
   } );
   
         
   this.tblStageIn.setData(  FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER );
   this.tblStageIn.setLayoutData( gd );
   
   /* Create "Add" Button */
   gd = new GridData();
   gd.horizontalSpan = 2;
   gd.verticalSpan = 1;
   gd.widthHint = 60;
   this.btnStageInAdd = toolkit.createButton( client,
                                      Messages.getString( "JsdlEditor_AddButton" ) //$NON-NLS-1$
                                      , SWT.PUSH);
   
   this.btnStageInAdd.addSelectionListener( new SelectionListener() {
     public void widgetSelected( final SelectionEvent event ) {
                handleEventDialog( null );
                performAdd( DataStageInSection.this.stageInViewer ,DataStageInSection.this.dataStageList );
     }

      public void widgetDefaultSelected( final SelectionEvent event ) {
          // Do Nothing - Required method
      }
    });
   
   this.btnStageInAdd.setLayoutData( gd );
   
   /* Create "Edit..." Button */
   gd = new GridData();
   gd.horizontalSpan = 2;
   gd.verticalSpan = 1;
   gd.widthHint = 60;
   this.btnStageInEdit = toolkit.createButton( client,
                                      Messages.getString("JsdlEditor_EditButton") //$NON-NLS-1$
                                      , SWT.PUSH );
   
   this.btnStageInEdit.addSelectionListener( new SelectionListener() {
     public void widgetSelected( final SelectionEvent event ) {
       handleEventDialog( getViewerSelectionObject( DataStageInSection.this.stageInViewer ) );
       performEdit( DataStageInSection.this.stageInViewer, DataStageInSection.this.dataStageList );
     }

      public void widgetDefaultSelected( final SelectionEvent event ) {
          // Do Nothing - Required method
      }
    });
   
   this.btnStageInEdit.setLayoutData( gd );
   
   
   /* Create "Remove" Button */
   gd = new GridData();
   gd.horizontalSpan = 2;
   gd.verticalSpan = 1;
   gd.widthHint = 60;
   gd.verticalAlignment = GridData.BEGINNING;
   
   this.btnStageInDel = toolkit.createButton( client,
                                   Messages.getString( "JsdlEditor_RemoveButton" ) //$NON-NLS-1$
                                   , SWT.PUSH );   
  
   this.btnStageInDel.addSelectionListener( new SelectionListener() {

     public void widgetSelected( final SelectionEvent event ) {        
       performDelete( DataStageInSection.this.stageInViewer );
     }

     public void widgetDefaultSelected( final SelectionEvent event ) {
         // Do Nothing - Required method
     }
   });

   this.btnStageInDel.setLayoutData( gd );
   
   /* Update Buttons so as to reflect the initial status of the TableViewer */
   updateButtons( this.stageInViewer  );
      
   toolkit.paintBordersFor( client );  
     
  }
  
  
  protected DataStagingType getViewerSelectionObject( final TableViewer tableViewer ) {
    DataStagingType result = null;
     
    IStructuredSelection selection = ( IStructuredSelection )tableViewer.getSelection();
    Object obj = selection.getFirstElement();
    if( obj instanceof DataStagingType ) {
      result = ( DataStagingType )obj;
    }
    return result;
     
  }
  
  
  @SuppressWarnings("boxing")
  protected void handleEventDialog( final DataStagingType selectedObject ) {
    
     DataStagingInDialog dialog;
     
     if( selectedObject == null ) {
       
       dialog = new DataStagingInDialog( this.containerComposite.getShell(), 
                                           DataStagingInDialog.ADVANCED_DIALOG);
       if( dialog.open() == Window.OK ) {         
         this.dataStageList = dialog.getDataStageInList();      
       }

     } else {     
       dialog = new DataStagingInDialog( this.containerComposite.getShell(),
                                         DataStagingInDialog.ADVANCED_DIALOG,
                                         selectedObject );
                  
       if( dialog.open() == Window.OK ) {        
         this.dataStageList = dialog.getDataStageInList();
           
       }
     }
  
  } // end void handleEventDialog()
  
  
  /**
   * Add's a new DataStaging element in the JSDL model. The new DataStaging element
   * is added as a child of the JobDescription element. The new DataStaging element
   * is a Stage-In element (containing a Source URI). 
   * 
   * @param tableViewer The {@link TableViewer} to add the new DataStaging element.
   * If the new DataStaging element is a Stage-In element, then the table viewer 
   * responsible for such elements should be passed here.
   * 
   * @param innerDataStageList The list containing the new DataStaqe elements which were 
   * retrieved from the respective Stage-In dialog 
   * 
   */
  @SuppressWarnings({
    "boxing", "unchecked"
  })
  public void performAdd ( final TableViewer tableViewer, final ArrayList<DataStagingType> innerDataStageList) {
    
    
   /* Check if the values from the dialog are null. */
    if (innerDataStageList.isEmpty()) {
      return;
    }
    
    EList <DataStagingType> newInputList = ( EList<DataStagingType> )tableViewer.getInput(); 
    
    if ( newInputList == null ) {
      newInputList = new BasicEList<DataStagingType>();
    }
    
      
    /* Check if the new Data Stage element is not already in the table viewer's
     * input
     */    
    for (int i=0; i<innerDataStageList.size(); i++){
      
      boolean exists = doesElementExists( this.dataStagingType, innerDataStageList.get( i ), newInputList ); 
    
      if ( !exists ) {
        newInputList.add( innerDataStageList.get( i ) );
    
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
        contentChanged();
      
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
   * @param innerDataStageList The list containing the selected DataStage elements.
   *  
   */
  @SuppressWarnings({
    "unchecked", "boxing"
  })
  public void performEdit( final TableViewer tableViewer, 
                           final ArrayList<DataStagingType> innerDataStageList) {
    


    /* Check if the values from the dialog are null. */
    if (innerDataStageList.isEmpty()) {
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
      for (int i=0; i<innerDataStageList.size(); i++){
      eStructuralFeature = this.jobDescriptionType.eClass()
                                            .getEStructuralFeature( featureID );

      Object oldDataStageElement = structSelection.getFirstElement();
    
      /* Get the Index of the Element that needs to be changed */
      int index = ( ( java.util.List<Object> ) this.jobDescriptionType.eGet(eStructuralFeature))
                                                           .indexOf( oldDataStageElement  );
      
      
      /* Instantiate new DataStage and SourceTarge objects */
      this.dataStagingType = JsdlFactory.eINSTANCE.createDataStagingType();      
      
      this.dataStagingType = innerDataStageList.get( i );
      
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
          if( !data.equals( oldDataStage ) && data.getSource() != null 
              && data.getFileName().equals( newDataStage.getFileName() )
              && data.getSource().getURI().equals( newDataStage.getSource().getURI() ) ) {
            
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
  
  
  protected void contentChanged() {
    
    if ( this.isNotifyAllowed )  {
      fireNotifyChanged( null);
    }
  } // End void contenctChanged()
  
  
  /**
   * @param jobDefinition The JSDL Job Definition element.
   */
  @SuppressWarnings("unchecked")
  public void setInput( final JobDefinitionType jobDefinition ) {
    
    this.adapterRefreshed = true;
    if( null != jobDefinition ) {
      this.jobDescriptionType = jobDefinition.getJobDescription();
      if (null != this.jobDescriptionType ) {
        if ( null != this.jobDescriptionType.getDataStaging() ){
          this.dataStageInputList = this.jobDescriptionType.getDataStaging();
          fillFields();
        }
                 
      }
            
    }
    
  }
  
  
  private void fillFields() {
    
    this.isNotifyAllowed = false;
    
    this.stageInViewer.setInput( this.dataStageInputList );

    this.isNotifyAllowed = true;
    
    if( this.adapterRefreshed ) {
      this.adapterRefreshed = false;
    }
    
  }
  
  
  /*
   * This function updates the status of the buttons related to
   * the Stage-In Table Viewer. The Status of the buttons is adjusted 
   * according to the selection and content of the respective
   * table viewer.
   * 
   */ 
    protected void updateButtons( final TableViewer tableViewer ) {
    
    ISelection selection = tableViewer.getSelection();
    boolean selectionAvailable = !selection.isEmpty();
        
    
      this.btnStageInAdd.setEnabled( true );
      this.btnStageInDel.setEnabled( selectionAvailable );
      this.btnStageInEdit.setEnabled( selectionAvailable );
    
  } // End updateButtons
    
    
}
