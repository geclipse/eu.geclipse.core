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
package eu.geclipse.batch.ui.internal.pages;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;

import eu.geclipse.batch.model.qdl.QueueType;
import eu.geclipse.batch.ui.editors.QueueEditor;
import eu.geclipse.batch.ui.internal.Messages;
import eu.geclipse.batch.ui.internal.adapters.AdvancedQueueAdapter;



/**
 * @author nloulloud
 *
 */
public class AdvancedQueueConfigPage extends FormPage
  implements INotifyChangedListener
{
  
  protected static final String PAGE_ID = "ADVANCED_PAGE";  //$NON-NLS-1$
  protected Composite composite = null;
  protected Composite body = null;  
  protected Label lblPriority = null;
  protected Label lblJobsinQueue;
  protected Label lblMaxJobs;
  protected Label lblAssignedResources = null;
  protected Spinner prioritySpin;
  protected Spinner maxJobsSpin;
  protected Spinner jobsInQueueSpin;
  protected Spinner resourcesSpin;
  protected Button btnUnlimitedRunningJobs = null;
  protected Button btnUnlimitedJobsInQueue = null;
  
  
  protected AdvancedQueueAdapter advancedQueueAdapter = null;  
  private boolean contentRefreshed = false;
  private boolean dirtyFlag = false;


  

  /**
   * @param editor
   */
  public AdvancedQueueConfigPage( final QueueEditor editor ) {
    super( editor, 
           PAGE_ID,
           Messages.getString("AdvancedQueueConfigPage_TabTitle")); //$NON-NLS-1$

  }
  
  
  @Override
  public void setActive( final boolean active ) {
    
    if ( active ) {
      if ( isContentRefreshed() ) {    
        this.advancedQueueAdapter.load();
      } // end_if isContentRefreshed
      
    } // end_if active
    
  }
  
  
  /**
   * Method that set's the SimpleQueueConfig Page content. The content is the root 
   * QDL element (Queue). Also this method is responsible to initialize the associated 
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

  
  public void setPageContent( final QueueType queue, 
                              final boolean refreshStatus ){

       
   if ( refreshStatus ) {
      this.contentRefreshed = true;
      this.advancedQueueAdapter.setContent( queue );
    }
   else {     
      this.advancedQueueAdapter = new AdvancedQueueAdapter( queue );
      this.advancedQueueAdapter.addListener( this );
   }
          
  } // End void setPageContent()
  
  
  
  /* This method is used to create the Forms content by
  * creating the form layout and then creating the form
  * sub sections.
  */
  @Override
  protected void createFormContent( final IManagedForm managedForm ) {
     
         
     ScrolledForm form = managedForm.getForm();
     FormToolkit toolkit = managedForm.getToolkit();
     
     form.setText( Messages.getString( "AdvancedQueueConfigPage_Title" ) );  //$NON-NLS-1$
     this.body = form.getBody();
     this.body.setLayout( FormLayoutFactory.createFormTableWrapLayout( true, 1 ));
         

     this.composite = toolkit.createComposite( this.body );
     this.composite.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1 ) );
     this.composite.setLayoutData( new TableWrapData(TableWrapData.FILL_GRAB ) );
     /* Create Job Definition Section */
     createAdvancedSection( this.composite , toolkit );
     
     /* Load the JobDefinition Adapter for this page */
     this.advancedQueueAdapter.load();
     
     /* Also add the help system */
//     addFormPageHelp( form );

  }
  
   
 /* 
  * Create the Advanced Section which includes the following:
  *  
  */   
  private void createAdvancedSection( final Composite parent,
                                      final FormToolkit toolkit )
  {
    
    String sectionTitle =  Messages.getString( "SimpleQueueConfigPage_AdvancedTitle" );  //$NON-NLS-1$
    String sectionDescription = Messages.getString( "AdvancedQueueConfigPage_Descr" );   //$NON-NLS-1$
        
    

    GridData gd;    
    
    Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                                                   parent,
                                                                   sectionTitle,
                                                                   sectionDescription,
                                                                   3 );
    /*==================== Priority Widgets =====================*/    
    Composite priorityComp;

    
    gd = new GridData();
    gd.horizontalSpan = 1;

    this.lblPriority = toolkit.createLabel( client,
                                            Messages.getString( "AdvancedQueueConfigPage_Priority" ) ); //$NON-NLS-1$
   
    priorityComp = new Composite( client, SWT.NONE );
    priorityComp.setLayout( new GridLayout( 1, false ) );
    
    this.prioritySpin = new Spinner( priorityComp, SWT.BORDER );
    this.prioritySpin.setValues( 80, 0, Integer.MAX_VALUE, 0, 10, 10  );
    this.advancedQueueAdapter.attachPrioritySpinner( this.prioritySpin );


    gd.horizontalSpan = 2;
    priorityComp.setLayoutData( gd );
    
    /*==================== Max. Running Jobs Widgets =====================*/
    
    gd = new GridData();
    gd.horizontalSpan = 1;
    
    Composite runningJobsComp;
    
    gd = new GridData();
    gd.horizontalSpan = 1;

    this.lblMaxJobs = toolkit.createLabel( client,
                                            Messages.getString( "AdvancedQueueConfigPage_MaxRunningJobs" ) ); //$NON-NLS-1$
   
    runningJobsComp = new Composite( client, SWT.NONE );
    runningJobsComp.setLayout( new GridLayout( 1, false ) );
    
    this.maxJobsSpin = new Spinner( runningJobsComp, SWT.BORDER );
    this.maxJobsSpin.setValues( 5, 0, Integer.MAX_VALUE, 0, 1, 1 );
    
    gd.horizontalSpan = 1;
    runningJobsComp.setLayoutData( gd );
    
    this.btnUnlimitedRunningJobs = new Button( client, SWT.CHECK );
    this.btnUnlimitedRunningJobs.setText( Messages.getString( "AdvancedQueueConfigPage.Unlimited" ) ); //$NON-NLS-1$
    this.btnUnlimitedRunningJobs.setSelection( false );     
    this.btnUnlimitedRunningJobs.addSelectionListener( new SelectionAdapter(){      
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        AdvancedQueueConfigPage.this.maxJobsSpin
                                    .setEnabled( !( AdvancedQueueConfigPage.this.btnUnlimitedRunningJobs.getSelection() ) );
        AdvancedQueueConfigPage.this.advancedQueueAdapter.setUnlimitedRunningJobs();
      }     
    } );
    
    this.advancedQueueAdapter.attachRunningJobsSpinner( this.maxJobsSpin,
                                                        this.btnUnlimitedRunningJobs );
    

    
    /*==================== Jobs in Queue Widgets =====================*/    
    Composite maxJobsComp;

    
    gd = new GridData();
    gd.horizontalSpan = 1;

    this.lblJobsinQueue = toolkit.createLabel( client,
                                            Messages.getString( "AdvancedQueueConfigPage_MaxJobsInQueue" ) ); //$NON-NLS-1$
   
    maxJobsComp = new Composite( client, SWT.NONE );
    maxJobsComp.setLayout( new GridLayout( 3, false ) );
    
    this.jobsInQueueSpin = new Spinner( maxJobsComp, SWT.BORDER );
    this.jobsInQueueSpin.setValues( 10, 0, Integer.MAX_VALUE, 0, 1, 1 );
    
    gd.horizontalSpan = 1;
    maxJobsComp.setLayoutData( gd );
    
    
    this.btnUnlimitedJobsInQueue = new Button( client, SWT.CHECK );
    this.btnUnlimitedJobsInQueue.setText( Messages.getString( "AdvancedQueueConfigPage.Unlimited" ) ); //$NON-NLS-1$
    this.btnUnlimitedJobsInQueue.setSelection( false );     
    this.btnUnlimitedJobsInQueue.addSelectionListener( new SelectionAdapter() {
      @Override
      public void widgetSelected( final SelectionEvent e ) {
        AdvancedQueueConfigPage.this.jobsInQueueSpin
                                    .setEnabled( !( AdvancedQueueConfigPage.this.btnUnlimitedJobsInQueue.getSelection() ) );
        AdvancedQueueConfigPage.this.advancedQueueAdapter.setUnlimitedJobsInQueue();        
      }     
    } );
    
    this.advancedQueueAdapter.attachJobsInQueueSpinner( this.jobsInQueueSpin,
                                                        this.btnUnlimitedJobsInQueue );
    
    
    
    /*==================== AssignedResources Widgets =====================*/    
    Composite resourcesComp;

    
    gd = new GridData();
    gd.horizontalSpan = 1;

    this.lblAssignedResources = toolkit.createLabel( client,
                                            Messages.getString( "AdvancedQueueConfigPage_AssignedResources" ) ); //$NON-NLS-1$
   
    resourcesComp = new Composite( client, SWT.NONE );
    resourcesComp.setLayout( new GridLayout( 3, false ) );
    
    this.resourcesSpin = new Spinner( resourcesComp, SWT.BORDER );
    this.resourcesSpin.setValues( 2, 1, 32000, 0, 1, 1  );
    this.advancedQueueAdapter.attachAssignedResourcesSpinner( this.resourcesSpin );
    
    gd.horizontalSpan = 2;
    resourcesComp.setLayoutData( gd );
                          
    
    /* Paint Flat Borders */    
    toolkit.paintBordersFor( client );
    
  } // end void createAdvancedSection
  
  
  
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
  
}
