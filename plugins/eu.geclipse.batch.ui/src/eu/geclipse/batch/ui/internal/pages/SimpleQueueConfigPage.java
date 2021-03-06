/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
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
package eu.geclipse.batch.ui.internal.pages;

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
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;

import eu.geclipse.batch.model.qdl.QueueType;
import eu.geclipse.batch.ui.dialogs.AllowedVOsDialog;
import eu.geclipse.batch.ui.editors.QueueEditor;
import eu.geclipse.batch.ui.internal.Activator;
import eu.geclipse.batch.ui.internal.Messages;
import eu.geclipse.batch.ui.internal.adapters.QueueAdapter;
import eu.geclipse.batch.ui.internal.providers.QueueContentProvider;
import eu.geclipse.batch.ui.internal.providers.QueueLabelProvider;




/**
 * @author nloulloud
 * 
 * This class is responsible for displaying the Simple Queue Configuration Page of
 * the Queue Configuration Editor.
 */
public class SimpleQueueConfigPage extends FormPage implements INotifyChangedListener
{

  protected static final String PAGE_ID = "SIMPLE_PAGE";  //$NON-NLS-1$ 

  protected Composite composite = null;
  protected Composite body = null;  
  protected Label lblQueueName = null;
  protected Label lblQueueType = null;
  protected Label lblQueueStatus = null;
  protected Label lblQueueStarted = null;
  protected Label lblMaxCPUTime = null;
  protected Label lblMaxWallTime = null;
  protected Label lblVOS = null;
  protected Label lblDivider;
  protected Label lblQueueDescripiton = null;
  protected Text txtQueueName = null;
  protected Text txtQueueDescription = null;
  protected Combo cmbQueueType = null;
  protected Combo cmbQueueStatus = null;
  protected Combo cmbQueueStarted = null;
  protected Spinner timeCPUHourSpin = null;
  protected Spinner timeCPUMinSpin = null;
  protected Spinner timeWallHourSpin = null;
  protected Spinner timeWallMinSpin = null;
  protected Table tblAllowedVOs = null;
  protected TableViewer tableViewer = null;
  protected TableColumn column = null;
  protected Button btnAdd = null;
  protected Button btnEdit = null;
  protected Button btnDel = null; 
  protected QueueAdapter queueAdapter = null;
  protected Object value = null;
  
  private ImageDescriptor helpDesc = null;
  private boolean contentRefreshed = false;
  private boolean dirtyFlag = false;
  private final int WIDGET_HEIGHT = 100;
  
  
  
  /**
   * @param editor
   */
  public SimpleQueueConfigPage( final QueueEditor editor ) {
    super( editor, PAGE_ID, Messages.getString("SimpleQueueConfigPage_TabTitle")); //$NON-NLS-1$

  }
  
  
  @Override
  public void setActive( final boolean active ) {
    
    if ( active ) {
      if ( isContentRefreshed() ) {    
        this.queueAdapter.load();
      } // end_if isContentRefreshed
      
    } // end_if active
    
  }
  
  
  /**
   * Method that set's the SimpleQueueConfig Page content. The content is the root 
   * QDL element (Queue). Also this method is responsible to initialise the associated 
   * type adapters for the elements of this page.  This method must be called only
   * from the Queue Editor.
   * 
   * Associated Type Adapters for this page are: 
   * 
   *  
   * @param queue
   * 
   * @param refreshStatus
   * Set to TRUE if the original page content is already set, but there is a need
   * to refresh the page because there was a change to this content
   * from an outside editor.
   * 
   */

  
  public void setPageContent( final QueueType queue, final boolean refreshStatus ) {
       
   if ( refreshStatus ) {
      this.contentRefreshed = true;
      this.queueAdapter.setContent( queue );
    }
   else {     
      this.queueAdapter = new QueueAdapter( queue );
      this.queueAdapter.addListener( this );
   }
          
  } // End void setPageContent()  
  
  
  
  @Override
  public boolean isDirty() {    
    return this.dirtyFlag;    
  }
  
  
  
  /**
   * This method set's the dirty status of the page.
   * 
   * @param dirty
   * If TRUE then the page is Dirty and a Save operation is needed.
   * 
   */
  public void setDirty( final boolean dirty ) {
    
    if (this.dirtyFlag != dirty) {
      this.dirtyFlag = dirty;     
      this.getEditor().editorDirtyStateChanged();  
    }
    
  }
  
  
  
  /* This method is used to create the Forms content by
   * creating the form layout and then creating the form
   * sub sections.
   */
   @Override
   protected void createFormContent( final IManagedForm managedForm ) {
     
         
     ScrolledForm form = managedForm.getForm();
     FormToolkit toolkit = managedForm.getToolkit();
     
     form.setText( Messages.getString( "SimpleQueueConfigPage_Title" ) );  //$NON-NLS-1$
     this.body = form.getBody();
     this.body.setLayout( FormLayoutFactory.createFormTableWrapLayout( true, 1 ));
         

     this.composite = toolkit.createComposite( this.body );
     this.composite.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1 ) );
     this.composite.setLayoutData( new TableWrapData(TableWrapData.FILL_GRAB ) );
     /* Create Job Definition Section */
     createSimpleSection( this.composite , toolkit );     
    
     
     /* Load the JobDefinition Adapter for this page */
     this.queueAdapter.load();
     
     /* Also add the help system */
     addFormPageHelp( form );

   }
                                          
   
  /* 
   * Create the Simple Section which includes the following:
   *  
   */  
   private void createSimpleSection( final Composite parent, final FormToolkit toolkit ) {
     
     String sectionTitle =  Messages.getString( "SimpleQueueConfigPage_RequiredTitle" );  //$NON-NLS-1$
     String sectionDescription = Messages.getString( "SimpleQueueConfigPage_RequiredDescr" );   //$NON-NLS-1$
         
     GridData gd;
     
     Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                                                    parent,
                                                                    sectionTitle,
                                                                    sectionDescription,
                                                                    4 );
   
     this.lblQueueName = toolkit.createLabel( client,
                                              Messages.getString( "SimpleQueueConfigPage_QueueName" ) ); //$NON-NLS-1$
     
     this.txtQueueName = toolkit.createText( client, "", SWT.NONE );     //$NON-NLS-1$
     this.queueAdapter.attachQueueName( this.txtQueueName );
  
     gd = new GridData();
     gd.grabExcessHorizontalSpace = true;
     gd.verticalAlignment = GridData.CENTER;
     gd.horizontalSpan = 3;
     gd.widthHint = 300;
     this.txtQueueName.setLayoutData( gd );
     
     /*==================== Queue Description Widgets =====================*/
     
     gd = new GridData();
     gd.horizontalSpan = 1;
     gd.verticalAlignment = GridData.BEGINNING;
     this.lblQueueDescripiton =  toolkit.createLabel( client,
                                      Messages.getString( "SimpleQueueConfigPage_QueueDescription" ) ); //$NON-NLS-1$
     
     this.lblQueueDescripiton.setLayoutData( gd );
     
     this.txtQueueDescription = toolkit.createText( client, "", SWT.NONE //$NON-NLS-1$
                                                              | SWT.V_SCROLL
                                                              | SWT.WRAP);
     
     gd = new GridData();
     gd.grabExcessHorizontalSpace = true;
     gd.verticalAlignment = GridData.CENTER;
     gd.horizontalSpan = 3;
     gd.widthHint = 285;
     gd.heightHint = 100;
     this.txtQueueDescription.setLayoutData( gd );
     
     
     /*==================== Queue Type Widgets =====================*/
     this.lblQueueType  = toolkit.createLabel( client,
                               Messages.getString( "SimpleQueueConfigPage_QueueType" ) ); //$NON-NLS-1$
     gd = new GridData();
     this.cmbQueueType = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
     this.cmbQueueType.setData( FormToolkit.KEY_DRAW_BORDER );
     this.queueAdapter.attachQueueType( this.cmbQueueType );
     gd.horizontalSpan = 3;
     gd.widthHint = 300;
     gd.grabExcessHorizontalSpace = true;
     gd.verticalAlignment = GridData.CENTER;
     this.cmbQueueType.setLayoutData( gd );
     
     /*==================== Queue Status Widgets =====================*/
     this.lblQueueStatus  = toolkit.createLabel( client,
                               Messages.getString( "SimpleQueueConfigPage_QueueStatus" ) ); //$NON-NLS-1$
     this.cmbQueueStatus = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
     this.cmbQueueStatus.setData( FormToolkit.KEY_DRAW_BORDER );
     this.queueAdapter.attachQueueStatus( this.cmbQueueStatus );
     gd.horizontalSpan = 3;
     this.cmbQueueStatus.setLayoutData( gd ); 
     /*==================== Queue Started Widgets =====================*/
     this.lblQueueStarted  = toolkit.createLabel( client,
                               Messages.getString( "SimpleQueueConfigPage_QueueStarted" ) ); //$NON-NLS-1$
     this.cmbQueueStarted = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
     this.cmbQueueStarted.setData( FormToolkit.KEY_DRAW_BORDER );
     this.queueAdapter.attachQueueStarted( this.cmbQueueStarted );
     gd.horizontalSpan = 3;
     this.cmbQueueStarted.setLayoutData( gd );
     /*==================== Max CPU Time Widgets =====================*/
     Composite timeComp;

     
     gd = new GridData();
     gd.horizontalSpan = 1;

     this.lblMaxCPUTime = 
       toolkit.createLabel( client, Messages.getString( "SimpleQueueConfigPage_MaxCPUTime" ) ); //$NON-NLS-1$
     
     timeComp = new Composite( client, SWT.NONE );
     timeComp.setLayout( new GridLayout( 3, false ) );
     
     this.timeCPUHourSpin = new Spinner( timeComp, SWT.BORDER );
     this.timeCPUHourSpin.setValues( 172800, 0, 9999999, 0, 1000, 2000 );

     this.lblDivider = toolkit.createLabel( timeComp, "sec" ); //$NON-NLS-1$
     this.queueAdapter.attachMaxCPUTimeSpinner( this.timeCPUHourSpin );
     gd.horizontalSpan = 3;
     timeComp.setLayoutData( gd );
     
     /*==================== Max Wall Time Widgets =====================*/

     gd = new GridData();
     gd.horizontalSpan = 1;

     this.lblMaxWallTime = 
       toolkit.createLabel( client, Messages.getString( "SimpleQueueConfigPage_MaxWallTime" ) ); //$NON-NLS-1$
     
     timeComp = new Composite( client, SWT.NONE );
     timeComp.setLayout( new GridLayout( 3, false ) );
     this.timeWallHourSpin = new Spinner( timeComp, SWT.BORDER );
     this.timeWallHourSpin.setValues( 172800, 0, 9999999, 0, 1000, 2000 );
     
     this.lblDivider = toolkit.createLabel(timeComp, "sec" ); //$NON-NLS-1$
     this.queueAdapter.attachMaxWallTimeSpinner( this.timeWallHourSpin );
     gd.horizontalSpan = 3;
     timeComp.setLayoutData( gd );
     
     /*==================== Allowed VOs Widgets =====================*/
     gd = new GridData();
     gd.verticalSpan = 3;
     gd.horizontalSpan = 1;
     gd.verticalAlignment = GridData.BEGINNING;
     this.lblVOS = 
       toolkit.createLabel( client, Messages.getString( "SimpleQueueConfigPage_AllowedVOs" ) ); //$NON-NLS-1$
     this.lblVOS.setLayoutData( gd );
     gd = new GridData();
     gd.horizontalAlignment = GridData.FILL;
     gd.verticalAlignment = GridData.FILL;
     gd.verticalSpan = 3;
     gd.horizontalSpan = 1;
     gd.widthHint = 250;
     gd.heightHint = this.WIDGET_HEIGHT;
     this.tableViewer = new TableViewer( client, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI );
     this.tblAllowedVOs = this.tableViewer.getTable();     
     this.tableViewer.setContentProvider( new QueueContentProvider() );
     this.tableViewer.setLabelProvider( new QueueLabelProvider() );    
     this.queueAdapter.attachAllowedVOs( this.tableViewer );
     this.tableViewer.addSelectionChangedListener( new ISelectionChangedListener() {

       public void selectionChanged( final SelectionChangedEvent event ) {
         updateButtons( ( TableViewer )event.getSource() );
       }
     } );
     this.tblAllowedVOs.setData( FormToolkit.KEY_DRAW_BORDER );
     this.tblAllowedVOs.setLayoutData( gd );
     
     /* Create "Add" Button */
     gd = new GridData();
     gd.horizontalSpan = 2;
     gd.verticalSpan = 1;
     gd.widthHint = 60;
     
     this.btnAdd = toolkit.createButton( client, Messages.getString( "QueueEditor_AddButton" ), //$NON-NLS-1$
                                         SWT.BUTTON1 );
     
     this.btnAdd.addSelectionListener( new SelectionListener() {

       public void widgetSelected( final SelectionEvent event ) {
         handleAddDialog ( Messages.getString( "QueueEditor_AllowedVOsDialog" ), //$NON-NLS-1$
                          ( Button )event.getSource() );
         SimpleQueueConfigPage.this.queueAdapter.addAllowedVO( SimpleQueueConfigPage.this.tableViewer,
                                                               SimpleQueueConfigPage.this.value );
       }

       public void widgetDefaultSelected( final SelectionEvent event ) {
         // Do Nothing - Required method
       }
     } );
     
     this.btnAdd.setLayoutData( gd );
     /* Create "Edit" Button */
     gd = new GridData();
     gd.horizontalSpan = 2;
     gd.verticalSpan = 1;
     gd.widthHint = 60;
     this.btnEdit = toolkit.createButton( client,
                                          Messages.getString( "QueueEditor_EditButton" ), //$NON-NLS-1$
                                          SWT.PUSH );
     
     this.btnEdit.addSelectionListener( new SelectionListener() {

       public void widgetSelected( final SelectionEvent event ) {
         handleAddDialog ( Messages.getString( "QueueEditor_AllowedVOsDialog" ), //$NON-NLS-1$
                           ( Button )event.getSource() );
          SimpleQueueConfigPage.this.queueAdapter.editAllowedVO( SimpleQueueConfigPage.this.tableViewer,
                                                                SimpleQueueConfigPage.this.value );
         
       }

       public void widgetDefaultSelected( final SelectionEvent event ) {
         // Do Nothing - Required method
       }
     } );
     
     this.btnEdit.setLayoutData( gd );
     /* Create "Remove" Button */
     gd = new GridData();
     gd.horizontalSpan = 2;
     gd.verticalSpan = 1;
     gd.widthHint = 60;
     gd.verticalAlignment = GridData.BEGINNING;
     this.btnDel = toolkit.createButton( client,
                                         Messages.getString( "QueueEditor_RemoveButton" ), //$NON-NLS-1$
                                         SWT.PUSH );
     this.btnDel.setEnabled( false );
     
     this.btnDel.addSelectionListener( new SelectionListener(){

      public void widgetDefaultSelected( final SelectionEvent e ) {
        // Auto-generated method stub
        
      }

      public void widgetSelected( final SelectionEvent e ) {
        SimpleQueueConfigPage.this.queueAdapter.deleteAllowedVo( SimpleQueueConfigPage.this.tableViewer );      
      }
       
     });
       
     this.btnDel.setLayoutData( gd );

     
     
     updateButtons( this.tableViewer );
     /* Paint Flat Borders */    
     toolkit.paintBordersFor( client );
     
   }
  
  
  /*
   *  Checks if the content of the model for this page is refreshed.
   */
  private boolean isContentRefreshed() {          
    return this.contentRefreshed;
  }
  
  
  /* (non-Javadoc)
   * @see org.eclipse.emf.edit.provider.INotifyChangedListener#notifyChanged(org.eclipse.emf.common.notify.Notification)
   */
  public void notifyChanged( final Notification notification ) {
    setDirty( true );
  }
  
  
  protected void updateButtons( final TableViewer tblViewer ) {
    
    ISelection selection = tblViewer.getSelection();
    boolean selectionAvailable = !selection.isEmpty();
    
          
      this.btnAdd.setEnabled( true );
      this.btnEdit.setEnabled( selectionAvailable );
      this.btnDel.setEnabled( selectionAvailable );
      
    
    
  } // End updateButtons
  
  
  
  protected void handleAddDialog( final String dialogTitle, final Button button ) {
    
    this.value = null;
    
    AllowedVOsDialog dialog = new AllowedVOsDialog( this.getSite().getShell(),
                                                  dialogTitle);
    
       
    if ( button == this.btnEdit ) {
      IStructuredSelection structSelection = ( IStructuredSelection )this.tableViewer.getSelection();
      String voName = ( String )structSelection.getFirstElement();
      
      dialog.setInput( voName );

      
    }
    
    if( dialog.open() != Window.OK ) {
      return;
    } // end if ( dialog.open() )
  
    this.value = dialog.getValue();
    
    
  } // end void handleAddDialog
  
  
  protected void addFormPageHelp( final ScrolledForm form ) {
    
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
        helpAction.setToolTipText( Messages.getString( "QdlEditorPage_HelpToolTip" ) );  //$NON-NLS-1$
        URL stageInURL = Activator.getDefault().getBundle().getEntry( "icons/help.gif" ); //$NON-NLS-1$       
        this.helpDesc = ImageDescriptor.createFromURL( stageInURL ) ;   
        helpAction.setImageDescriptor( this.helpDesc );
        manager.add( helpAction );
        form.updateToolBar();
    }
    
  }
  
  
  protected String getHelpResource() {
    return "/eu.geclipse.doc.user/html/concepts/siteadministration/editorpages/SimpleParametersPage.html"; //$NON-NLS-1$
  }
  
  
}

