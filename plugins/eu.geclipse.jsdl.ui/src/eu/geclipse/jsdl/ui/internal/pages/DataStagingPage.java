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
package eu.geclipse.jsdl.ui.internal.pages;

import java.net.URL;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;

import eu.geclipse.jsdl.model.DataStagingType;
import eu.geclipse.jsdl.model.JobDefinitionType;
import eu.geclipse.jsdl.ui.adapters.jsdl.DataStageTypeAdapter;
import eu.geclipse.jsdl.ui.editors.JsdlEditor;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.providers.DataStageInLabelProvider;
import eu.geclipse.jsdl.ui.providers.DataStageOutLabelProvider;
import eu.geclipse.jsdl.ui.providers.FeatureContentProvider;
import eu.geclipse.jsdl.ui.providers.FeatureLabelProvider;
import eu.geclipse.jsdl.ui.widgets.DataStagingInDialog;
import eu.geclipse.jsdl.ui.widgets.DataStagingOutDialog;


/**
 * @author nloulloud
 *
 */
public class DataStagingPage extends FormPage implements INotifyChangedListener {

  protected static final String PAGE_ID = "DATA_STAGING"; //$NON-NLS-1$
  protected Composite body = null;
  protected Composite stageInSection = null;
  protected Composite stageOutSection = null;  
  protected List lstStageInFileName = null;
  protected Text txtStageInFileSystemName = null;
  protected Text txtSource = null;
  protected Text txtTarget = null;
  protected Text txtStageInName = null;   
  protected Label lblStageInFileName = null;
  protected Label lblStageInFileSystemName = null;
  protected Label lblCreationFlag = null;
  protected Label lblDelOnTerm = null;
  protected Label lblSource = null;
  protected Label lblTarget = null;
  protected Label lblStageInName = null;  
  protected Button btnStageInAdd = null;
  protected Button btnStageInEdit = null;
  protected Button btnStageInDel = null; 
  protected Button btnStageOutAdd = null;
  protected Button btnStageOutEdit = null;
  protected Button btnStageOutDel = null;  
  protected Combo cmbCreationFlag = null;
  protected Combo cmbDelOnTerm = null;  
  protected Table tblStageIn = null;
  protected Table tblStageOut = null;   
  protected TableViewer stageInViewer = null;  
  protected TableViewer stageOutViewer = null;   
  protected Object[] value = null;  
  protected DataStageTypeAdapter dataStageTypeAdapter;    
  protected FeatureContentProvider featureContentProvider = new FeatureContentProvider();
  protected FeatureLabelProvider featureLabelProvider = new FeatureLabelProvider();
  
  private ImageDescriptor helpDesc = null;
  private TableColumn column;    
  private final int WIDGET_HEIGHT = 100;
  private boolean contentRefreshed = false;
  private boolean dirtyFlag = false;
  
  
  /**
   * This class provides the DataStaging Page page that appears in the JSDL editor.
   * It provides a graphical user interface to the following DataStaging elements
   * of a JSDL document:
   * 
   * - FileName
   * - FileSystemName
   * - Source/Target Location
   * - CreationFlag
   * - DeleteOnTermination
   * 
   * @param editor The parent {@link JsdlEditor}. 
   * 
   * 
   */
  public DataStagingPage( final FormEditor editor )
  
  {
   
   super( editor, PAGE_ID , Messages.getString("DataStagingPage_PageTitle") ); //$NON-NLS-1$
  
   }
  
  
  public void notifyChanged( final Notification arg0 ) {
    setDirty( true );
  }
  
  
  
  /**
   * This method set's the dirty status of the page.
   * 
   * @param dirty
   * If TRUE then the page is Dirty and a Save operation is needed.
   * 
   */
  public void setDirty( final boolean dirty ) {
    
    if ( this.dirtyFlag != dirty ) {
      this.dirtyFlag = dirty;     
      this.getEditor().editorDirtyStateChanged();  
    }
    
  }
  
  
  
  @Override
  public boolean isDirty() {
    
    return this.dirtyFlag;
    
  }  
  
  
  
  @Override
  public void setActive( final boolean active ) {
    
    if ( active ){
      if ( isContentRefreshed() ) {
        this.dataStageTypeAdapter.load();
      }//end_if isContentRefreshed()
    } // end_if active
    
  } //End void setActive()
  
  
  
  private boolean isContentRefreshed() {
    
    return this.contentRefreshed;
    
  } //End boolean isContentRefreshed()
  
  
  
  @Override
  protected void createFormContent( final IManagedForm managedForm ) {
    
    
    ScrolledForm form = managedForm.getForm();
    FormToolkit toolkit = managedForm.getToolkit();
    
    form.setText( Messages.getString( "DataStagingPage_DataStagingPageTitle" ) );  //$NON-NLS-1$
    
    this.body = form.getBody();
    this.body.setLayout( FormLayoutFactory.createFormTableWrapLayout( false, 2) );
    
    
    this.stageInSection = toolkit.createComposite( this.body );
    this.stageInSection.setLayout(FormLayoutFactory.createFormPaneTableWrapLayout(false, 1));
    this.stageInSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

    /* Create the Stage-In Section */
    createStageInSection( this.stageInSection , toolkit );
   
    this.stageOutSection = toolkit.createComposite( this.body );
    this.stageOutSection.setLayout(FormLayoutFactory.createFormPaneTableWrapLayout(false, 1));
    this.stageOutSection.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));

    /* Create the Stage-Out Section */
    createStageOutSection( this.stageInSection, toolkit );    

    /*Load the Adapters for this page */
    
    this.dataStageTypeAdapter.load();
    
    /* Also add the help system */
    addFormPageHelp( form );
    
  }
 
 
 
  /**
   * Method that set's the DataStage Page content. The content is the root 
   * JSDL element. Also this method is responsible to initialize the associated 
   * type adapters for the elements of this page.  This method must be called only
   * from the JSDL Editor.
   * 
   * Associated Type Adapters for this page are: 
   * @see DataStageTypeAdapter
   *  
   * @param jobDefinitionRoot
   * 
   * @param refreshStatus
   * Set to TRUE if the original page content is already set, but there is a need
   * to refresh the page because there was a change to this content
   * from an outside editor.
   * 
   */
  public void setPageContent( final JobDefinitionType jobDefinitionRoot, 
                              final boolean refreshStatus ) {

  if ( refreshStatus ) {
     this.contentRefreshed = true;
     this.dataStageTypeAdapter.setContent( jobDefinitionRoot );
   }
  else {     
     this.dataStageTypeAdapter = new DataStageTypeAdapter( jobDefinitionRoot );
     this.dataStageTypeAdapter.addListener( this );
   }
         
  } // End void getPageContent()
 
 
 
  /* This function creates the Stage-In Section of the DateStage Page */
  private void createStageInSection( final Composite parent,
                                     final FormToolkit toolkit ) {
   
   String sectionTitle = Messages.getString( "DataStagingPage_StageInSection" ); //$NON-NLS-1$
   String sectionDescription = Messages.getString( "DataStagingPage_StageInDescr" );   //$NON-NLS-1$
   
   GridData gd;
      
   Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                          parent,
                                          sectionTitle,
                                          sectionDescription,
                                          3 );
     
   
   gd = new GridData();
   
   gd.horizontalAlignment = GridData.FILL;
   gd.verticalAlignment = GridData.FILL;
   gd.verticalSpan = 3;
   gd.horizontalSpan = 1;
   gd.widthHint = 600;
   gd.heightHint = this.WIDGET_HEIGHT;
   
   //FIXME This is a work-around for the Bug#: 201705 for Windows.
   this.stageInViewer = new TableViewer( client, SWT.BORDER                                                                            
                                        | SWT.FULL_SELECTION
                                        | SWT.MULTI);
   
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
   
   
   this.dataStageTypeAdapter.attachToStageIn( this.stageInViewer  );
   
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
                handleEventDialog( DataStagingPage.this.stageInViewer, null );
                DataStagingPage.this.dataStageTypeAdapter
                               .performAdd( DataStagingPage.this.stageInViewer ,
                                                   DataStagingPage.this.value );
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
       handleEventDialog( DataStagingPage.this.stageInViewer, 
               getViewerSelectionObject( DataStagingPage.this.stageInViewer ) );
       DataStagingPage.this.dataStageTypeAdapter
                               .performEdit( DataStagingPage.this.stageInViewer,
                                                   DataStagingPage.this.value );
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
  
   this.dataStageTypeAdapter.attachToDelete( this.btnStageInDel, 
                                                           this.stageInViewer );

   this.btnStageInDel.setLayoutData( gd );
   
   /* Update Buttons so as to reflect the initial status of the TableViewer */
   updateButtons( this.stageInViewer  );
      
   toolkit.paintBordersFor( client );  
     
 }
 
 
 /* This function creates the Stage-Out Section of the DateStage Page */
  private void createStageOutSection( final Composite parent,
                                      final FormToolkit toolkit )                                             
  {

   String sectionTitle = Messages.getString( "DataStagingPage_StageOutSection" ); //$NON-NLS-1$
   String sectionDescription = Messages.getString( "DataStagingPage_StageOutDescr" );   //$NON-NLS-1$
   
   GridData gd;
      
   Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                          parent,
                                          sectionTitle,
                                          sectionDescription,
                                          3 );
     
   
   gd = new GridData();
   
   gd.horizontalAlignment = GridData.FILL;
   gd.verticalAlignment = GridData.FILL;
   gd.verticalSpan = 3;
   gd.horizontalSpan = 1;
   gd.widthHint = 600;
   gd.heightHint = this.WIDGET_HEIGHT;
   
   //X
   this.stageOutViewer = new TableViewer( client, SWT.BORDER                                                                                
                                        | SWT.FULL_SELECTION
                                        | SWT.MULTI );
   
   
   this.tblStageOut = this.stageOutViewer.getTable();
   this.tblStageOut .setHeaderVisible( true );
   this.tblStageOut.setLinesVisible( true );  
   /* Set the common Content Provider  */
   this.stageOutViewer.setContentProvider( new FeatureContentProvider() );
   /* Set the dedicated Label Provider for DataStage-Out elements */
   this.stageOutViewer.setLabelProvider( new DataStageOutLabelProvider() );
   
     
   this.column = new TableColumn( this.tblStageOut, SWT.CENTER );    
   this.column.setText( Messages.getString( "DataStagingPage_FileName" ) ); //$NON-NLS-1$
   this.column.setWidth( 150 );
   this.column = new TableColumn( this.tblStageOut, SWT.LEFT  );
   this.column.setText( Messages.getString( "DataStagingPage_Target" ) ); //$NON-NLS-1$
   this.column.setWidth( 200 );
   this.column = new TableColumn( this.tblStageOut, SWT.CENTER );    
   this.column.setText( Messages.getString( "DataStagingPage_CreationFlag" ) ); //$NON-NLS-1$
   this.column.setWidth( 100 );
   this.column = new TableColumn( this.tblStageOut, SWT.CENTER );    
   this.column.setText( Messages.getString( "DataStagingPage_DeleteOnTermination" ) ); //$NON-NLS-1$
   this.column.setWidth( 100 );
   
   /* Based on the Table Viewer selection, update the status of the respective
    * buttons.
    */
   this.stageOutViewer .addSelectionChangedListener( new ISelectionChangedListener()
   {

     public void selectionChanged( final SelectionChangedEvent event ) {
       updateButtons( (TableViewer) event.getSource() );
     }
   } );
   
   this.dataStageTypeAdapter.attachToStageOut( this.stageOutViewer  );   
   this.tblStageOut.setData(  FormToolkit.KEY_DRAW_BORDER , FormToolkit.TEXT_BORDER);
   this.tblStageOut.setLayoutData( gd);
   
   
   /* Create "Add" Button */
   gd = new GridData();
   gd.horizontalSpan = 2;
   gd.verticalSpan = 1;
   gd.widthHint = 60;
   this.btnStageOutAdd = toolkit.createButton( client,
                                    Messages.getString( "JsdlEditor_AddButton" ) //$NON-NLS-1$
                                    , SWT.PUSH );
   
   this.btnStageOutAdd.addSelectionListener( new SelectionListener() {
     public void widgetSelected( final SelectionEvent event) {
       handleEventDialog( DataStagingPage.this.stageOutViewer, null );
       DataStagingPage.this.dataStageTypeAdapter
                               .performAdd( DataStagingPage.this.stageOutViewer ,
                                                   DataStagingPage.this.value );
     }

      public void widgetDefaultSelected( final SelectionEvent event ) {
          // Do Nothing - Required method
      }
    });
   
   this.btnStageOutAdd.setLayoutData( gd );
   
   /* Create "Edit..." Button */
   gd = new GridData();
   gd.horizontalSpan = 2;
   gd.verticalSpan = 1;
   gd.widthHint = 60;
   this.btnStageOutEdit = toolkit.createButton( client,
                                      Messages.getString( "JsdlEditor_EditButton" ) //$NON-NLS-1$
                                      , SWT.PUSH );
   
   this.btnStageOutEdit.addSelectionListener( new SelectionListener() {
     public void widgetSelected( final SelectionEvent event) {
       handleEventDialog( DataStagingPage.this.stageOutViewer, 
                         getViewerSelectionObject( DataStagingPage.this.stageOutViewer ) );
                 DataStagingPage.this.dataStageTypeAdapter
                              .performEdit( DataStagingPage.this.stageOutViewer,
                                                   DataStagingPage.this.value );
                 }

      public void widgetDefaultSelected( final SelectionEvent event ) {
          // Do Nothing - Required method
      }
    });
   
   this.btnStageOutEdit.setLayoutData( gd );
   
   
   /* Create "Remove" Button */
   gd = new GridData();
   gd.horizontalSpan = 2;
   gd.verticalSpan = 1;
   gd.widthHint = 60;
   gd.verticalAlignment = GridData.BEGINNING;
   
   this.btnStageOutDel = toolkit.createButton( client,
                                   Messages.getString( "JsdlEditor_RemoveButton" ) //$NON-NLS-1$
                                   , SWT.PUSH );
   
   this.dataStageTypeAdapter.attachToDelete( this.btnStageOutDel, 
                                                            this.stageOutViewer );

   this.btnStageOutDel.setLayoutData( gd );
   
   /* Update Buttons so as to reflect the initial status of the TableViewer */
   updateButtons( this.stageOutViewer  );
   
   toolkit.paintBordersFor( client );

  } // end createStageOutSection()
  
  
  
  private void addFormPageHelp( final ScrolledForm form ) {
    
    final String href = getHelpResource();
    if ( href != null ) {
        IToolBarManager manager = form.getToolBarManager();
        Action helpAction = new Action( "help" ) { //$NON-NLS-1$
            @Override
            public void run() {
                BusyIndicator.showWhile(form.getDisplay(), new Runnable() {
                    public void run() {
                        PlatformUI.getWorkbench().getHelpSystem().displayHelpResource( href );
                    }
                });
            }
        };
        helpAction.setToolTipText( Messages.getString( "DataStagingPage_Help" ) );  //$NON-NLS-1$
        URL stageInURL = Activator.getDefault().getBundle().getEntry( "icons/help.gif" ); //$NON-NLS-1$       
        this.helpDesc = ImageDescriptor.createFromURL( stageInURL ) ;   
        helpAction.setImageDescriptor( this.helpDesc );
        manager.add( helpAction );
        form.updateToolBar();
    }
    
  }
  
  
  protected String getHelpResource() {
    return "/eu.geclipse.doc.user/html/concepts/jobmanagement/editorpages/datastaging.html"; //$NON-NLS-1$
  }
 
 
 
 /*
  * This function updates the status of the buttons related to
  * the respective Stage-In and Stage-Out Table Viewers. The Status of the 
  * buttons is adjusted according to the selection and content of the respective
  * table viewer.
  * 
  */ 
   protected void updateButtons( final TableViewer tableViewer ) {
   
   ISelection selection = tableViewer.getSelection();
   boolean selectionAvailable = !selection.isEmpty();
   
   if (tableViewer == this.stageInViewer){
   
     this.btnStageInAdd.setEnabled( true );
     this.btnStageInDel.setEnabled( selectionAvailable );
     this.btnStageInEdit.setEnabled( selectionAvailable );
   }
   else{
     
     this.btnStageOutAdd.setEnabled( true );
     this.btnStageOutDel.setEnabled( selectionAvailable );
     this.btnStageOutEdit.setEnabled( selectionAvailable );
   }
 } // End updateButtons
   
   
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
  protected void handleEventDialog( final TableViewer tableViewer, 
                                    final DataStagingType selectedObject ) {
     this.value = new Object[4];
     
     if (tableViewer == this.stageInViewer ) {
       DataStagingInDialog dialog;
     
       if( selectedObject == null ) {
       
         dialog = new DataStagingInDialog( this.body.getShell(), 
                                           DataStagingInDialog.ADVANCED_DIALOG);
         if( dialog.open() == Window.OK ) {         
           this.value[0] = dialog.getPath();
           this.value[1] = dialog.getName();
           this.value[2] = dialog.getCreationFlag();
           this.value[3] = dialog.getDeleteOnTermination();
      
         }

       } else {
       
         dialog = new DataStagingInDialog( this.body.getShell(),
                                           DataStagingInDialog.ADVANCED_DIALOG,
                                           selectedObject.getFileName(),
                                           selectedObject.getSource().getURI(),
                                           selectedObject.getCreationFlag().toString(),
                                           selectedObject.isDeleteOnTermination() );
         
         
         if( dialog.open() == Window.OK ) {        
           
           this.value[0] = dialog.getPath();
           this.value[1] = dialog.getName();
           this.value[2] = dialog.getCreationFlag();
           this.value[3] = dialog.getDeleteOnTermination();
           
         }

       }
     }
     else{
       
       DataStagingOutDialog dialog;
       
       if( selectedObject == null ) {
         
         dialog = new DataStagingOutDialog( this.body.getShell(),
                                            DataStagingInDialog.ADVANCED_DIALOG);
         if( dialog.open() == Window.OK ) {         
           this.value[0] = dialog.getName();
           this.value[1] = dialog.getPath();  
           this.value[2] = dialog.getCreationFlag();
           this.value[3] = dialog.getDeleteOnTermination();
         }

       } else {
       
         dialog = new DataStagingOutDialog( this.body.getShell(),
                                            DataStagingInDialog.ADVANCED_DIALOG,
                                            selectedObject.getFileName(),
                                            selectedObject.getTarget().getURI(),
                                            selectedObject.getCreationFlag().toString(),
                                            selectedObject.isDeleteOnTermination() );
         
         if( dialog.open() == Window.OK ) {         
           this.value[0] = dialog.getName();
           this.value[1] = dialog.getPath();         
           this.value[2] = dialog.getCreationFlag();
           this.value[3] = dialog.getDeleteOnTermination();
         }

       }
       
     }
   }
  
} // End Class

