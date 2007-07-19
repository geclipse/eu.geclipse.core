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

package eu.geclipse.jsdl.ui.internal.pages;

/**
 * @author nickl
 *
 */


import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;
import eu.geclipse.jsdl.jsdl.JobDefinitionTypeAdapter;
import eu.geclipse.jsdl.jsdl.JobIdentificationTypeAdapter;
import eu.geclipse.jsdl.ui.editors.JsdlEditor;
import eu.geclipse.jsdl.ui.internal.dialogs.MultipleInputDialog;

/**
 * This class provides the Job Definition page that appears in the JSDL editor.
 * It provides a graphical user interface to the following elements of a JSDL 
 * document:
 * 
 * - Job Definition ID
 * - Job Name
 * - Job Description
 * - Job Annotation
 * - Job Project
 * 
 */
public final class JobDefinitionPage extends FormPage
                                            implements INotifyChangedListener {
  
  protected static final String PAGE_ID = "JOB_DEFINITION";  //$NON-NLS-1$
  protected Object value = null;

  protected Text txtId = null;
  protected Text txtDescription = null;
  protected Text txtJobName = null;
  
  protected List lstJobProject = null;
  protected List lstJobAnnotation = null;
  
  protected Label lblJobId = null;
  protected Label lblJobDescripiton = null;
  protected Label lblJobAnnotation = null;
  protected Label lblJobProject = null;
  
  protected Button btnAdd = null;
  protected Button btnDel = null;
  protected Button btnTest = null;
  
  protected Composite body = null;
  protected Composite jobDefComposite = null;
  protected Composite jobIdentComposite = null;
    
  protected JobDefinitionTypeAdapter jobDefinitionTypeAdapter ;
  protected JobIdentificationTypeAdapter jobIdentificationTypeAdapter;
    
  private boolean contentRefreshed = false;
  private boolean dirtyFlag = false;
  
  private final int widgetHeight = 100; 
   
  
  /**
   * JobDefinitionPage Class constructor.
   * @param editor
   */
  public JobDefinitionPage( final JsdlEditor editor) {
   
    
    super(editor, PAGE_ID,
                  Messages.getString("JobDefinitionPage_JobDefinitionTitle")); //$NON-NLS-1$   
    
  }
  
  @Override
  public void setActive(final boolean active) {
    
    if (active){
      if (isContentRefreshed()){    
        this.jobDefinitionTypeAdapter.load();
        this.jobIdentificationTypeAdapter.load();
      }//endif isContentRefreshed
    } // endif active
  }
  
  
  
  /*
   *  Checks if the content of the model for this page is refreshed.
   */
  private boolean isContentRefreshed(){          
    return this.contentRefreshed;
     }
  
  
  /**
   * Method that set's the JobDefinition Page content. The content is the root 
   * JSDL element. Also this method is responsible to initialize the associated 
   * type adapters for the elements of this page.  This method must be called only
   * from the JSDL Editor.
   * 
   * Associated Type Adapters for this page are: 
   * @see JobDefinitionTypeAdapter
   * @see JobIdentificationTypeAdapter
   *  
   * @param rootJsdlElement
   * 
   * @param refreshStatus
   * Set to TRUE if the original page content is already set, but there is a need
   * to refresh the page because there was a change to this content
   *  from an outside editor.
   * 
   */
  public void setPageContent(final EObject rootJsdlElement, 
                             final boolean refreshStatus){

   if (refreshStatus) {
      this.contentRefreshed = true;
      this.jobDefinitionTypeAdapter.setContent( rootJsdlElement );
      this.jobIdentificationTypeAdapter.setContent( rootJsdlElement );
    }
   else{     
      this.jobDefinitionTypeAdapter = new JobDefinitionTypeAdapter(rootJsdlElement);
      this.jobDefinitionTypeAdapter.addListener( this );
      this.jobIdentificationTypeAdapter = new JobIdentificationTypeAdapter(rootJsdlElement);
      this.jobIdentificationTypeAdapter.addListener( this );
   }
          
  } // End void setPageContent()   
  

  @Override
  public boolean isDirty() {
    
    return this.dirtyFlag;
    
  }
  
  /**
   * This method set's the dirty status of the page.
   * 
   * @param dirtyFlag
   * If TRUE then the page is Dirty and a Save operation is needed.
   * 
   */
  public void setDirty(final boolean dirtyFlag) {
    if (this.dirtyFlag != dirtyFlag) {
      this.dirtyFlag = dirtyFlag;     
      this.getEditor().editorDirtyStateChanged();  
    }
    
  }

 

  @Override
  
  // This method is used to create the Forms content by
  // creating the form layout and then creating the form
  // sub sections.
  protected void createFormContent(final IManagedForm managedForm) {
    
        
    ScrolledForm form = managedForm.getForm();
    FormToolkit toolkit = managedForm.getToolkit();
    
    form.setText(Messages.getString("JobDefinitionPage_JobDefinitionPageTitle"));  //$NON-NLS-1$
    this.body = form.getBody();
    this.body.setLayout(FormLayoutFactory.createFormTableWrapLayout(true, 2));
    
    
    this.jobDefComposite = toolkit.createComposite( this.body );
    this.jobDefComposite.setLayout(FormLayoutFactory.createFormPaneTableWrapLayout(false, 1));
    this.jobDefComposite.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
    createJobDefinitionSection(this.jobDefComposite , toolkit);
    
    this.jobDefinitionTypeAdapter.load();
    
    this.jobIdentComposite = toolkit.createComposite( this.body );
    this.jobIdentComposite.setLayout(FormLayoutFactory.createFormPaneTableWrapLayout(false, 1));
    this.jobIdentComposite.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
    
    createJobIdentificationSection (this.jobIdentComposite, toolkit);
    
    this.jobIdentificationTypeAdapter.load();

  }
  
  
  /* 
   * Create the Job Definition Section which includes the following:
   *  -Job ID (String)
   */
    
  private void createJobDefinitionSection(final Composite parent,
                                          final FormToolkit toolkit)
  {
    
    String sectionTitle =  Messages.getString("JobDefinitionPage_JobDefinitionTitle");  //$NON-NLS-1$
    String sectionDescription = Messages.getString("JobDefinitionPage_JobDefinitionDescr");   //$NON-NLS-1$
        
    
    GridData gd;
    
    
    Composite client = FormSectionFactory.createGridStaticSection(toolkit,
                                                                  parent,
                                                                  sectionTitle,
                                                                  sectionDescription,
                                                                  2);
  
    this.lblJobId = toolkit.createLabel(client,
                       Messages.getString("JobDefinitionPage_JobDefinitionId")); //$NON-NLS-1$
    
    this.txtId = toolkit.createText(client,"", SWT.NONE);     //$NON-NLS-1$
    this.jobDefinitionTypeAdapter.attachID( this.txtId );
    
  
    gd = new GridData();
    gd.grabExcessHorizontalSpace = true;
    gd.verticalAlignment = GridData.CENTER;
    gd.widthHint = 300;
    this.txtId.setLayoutData(gd);
    
    
    //Paint Flat Borders
    
    toolkit.paintBordersFor( client );
    
  }
  
  
  /* 
   * Create the Job Identification Section which includes the following:
   *  -Job Name (String)
   *  -Job Description (String)
   *  -Job Annotation (EList)
   *  -Job Project (EList)
   */
  private void createJobIdentificationSection(final Composite parent,
                                                   final FormToolkit toolkit)
  { 
    
    String sectionTitle = Messages
                         .getString("JobDefinitionPage_JobIdentificationTitle");  //$NON-NLS-1$
    
    String sectionDescription = Messages
                         .getString("JobDefinitionPage_JobIdentificationDescr"); //$NON-NLS-1$
    
    GridData gd;
       
    Composite client = FormSectionFactory.createGridStaticSection(toolkit,
                                           parent,
                                           sectionTitle,
                                           sectionDescription,
                                           4);
       
      
    
    
    /* ================================ Job Name ============================ */
    
    toolkit.createLabel(client, Messages.getString("JobDefinitionPage_JobName")); //$NON-NLS-1$
    gd = new GridData();
    gd.grabExcessHorizontalSpace = true;
    gd.verticalAlignment = GridData.CENTER;
    gd.horizontalSpan = 3;
    gd.widthHint = 300;
    
    this.txtJobName = toolkit.createText(client,"", SWT.NONE);  //$NON-NLS-1$
    this.jobIdentificationTypeAdapter.attachToJobName( this.txtJobName );

    this.txtJobName.setLayoutData(gd);
    
    
    /* ========================= Job Description ============================ */
    
    this.lblJobDescripiton = toolkit.createLabel (client,
                              Messages.getString("JobDefinitionPage_JobDescr")); //$NON-NLS-1$
    gd = new GridData();
    gd.verticalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    this.lblJobDescripiton.setLayoutData (gd);
    
    
    
    this.txtDescription = toolkit.createText(client, "", SWT.MULTI  //$NON-NLS-1$
                                                   | SWT.V_SCROLL | SWT.WRAP);
    
    this.jobIdentificationTypeAdapter.attachToJobDescription( this.txtDescription );
    
  
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 1;
    gd.horizontalSpan = 3;
    gd.widthHint = 285;
    gd.heightHint = this.widgetHeight;
    this.txtDescription.setLayoutData(gd);
 
    
    /* ========================= Job Annotation ============================= */
    
    this.lblJobAnnotation = toolkit.createLabel(client,
                         Messages.getString("JobDefinitionPage_JobAnnotation"));  //$NON-NLS-1$
    gd = new GridData();
    gd.horizontalSpan = 1;
    gd.verticalSpan = 2;  
    gd.verticalAlignment = GridData.BEGINNING;
    this.lblJobAnnotation.setLayoutData( gd );
   
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.horizontalSpan = 1;
    gd.verticalSpan = 2;    
    gd.widthHint = 250;
    gd.heightHint = this.widgetHeight;

    
    

    
    this.lstJobAnnotation = new List(client, SWT.None);
    this.lstJobAnnotation.setData( FormToolkit.KEY_DRAW_BORDER,
                                                      FormToolkit.TEXT_BORDER );
    
    this.jobIdentificationTypeAdapter.attachToJobAnnotation( this.lstJobAnnotation);
    this.lstJobAnnotation.setLayoutData( gd );
    

    /* Create Button ADD */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    this.btnAdd = toolkit.createButton(client, 
                                     Messages.getString("JsdlEditor_AddButton"), //$NON-NLS-1$
                                     SWT.PUSH);
    
    this.btnAdd.addSelectionListener(new SelectionListener() {
     public void widgetSelected(final SelectionEvent event) {
       handleAddDialog(Messages.getString( "JobDefinitionPage_JobAnnotationDialog" )); //$NON-NLS-1$
       
       JobDefinitionPage.this.jobIdentificationTypeAdapter
                       .performAdd(JobDefinitionPage.this.lstJobAnnotation,
                                  "lstJobAnnotation", //$NON-NLS-1$
                                   JobDefinitionPage.this.value);
       setDirty( true );
     }

      public void widgetDefaultSelected(final SelectionEvent event) {
          // Do Nothing - Required method
      }
    });
    
    this.btnAdd.setLayoutData( gd );

    /* Create Button DEL */
    gd = new GridData();    
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    gd.verticalAlignment = GridData.BEGINNING;

    this.btnDel = toolkit.createButton(client,
                       Messages.getString("JsdlEditor_RemoveButton"), SWT.PUSH);  //$NON-NLS-1$
    this.btnDel.setEnabled( false );
    
    this.jobIdentificationTypeAdapter.attachToDelete( this.btnDel, this.lstJobAnnotation);
    this.btnDel.setLayoutData( gd );
    
    /* ============================= Job Project =============================*/
    
     this.lblJobProject = toolkit.createLabel(client,
                            Messages.getString("JobDefinitionPage_JobProject")); //$NON-NLS-1$
     
     gd = new GridData();
     gd.verticalSpan = 2;
     gd.horizontalSpan = 1;
     gd.verticalAlignment = GridData.BEGINNING;
     this.lblJobProject.setLayoutData( gd );
     
     gd = new GridData();
     gd.verticalSpan = 2;
     gd.horizontalSpan = 1;
     gd.widthHint = 250;
     gd.heightHint = this.widgetHeight;
     

     this.lstJobProject = new List(client,  SWT.NONE);
     this.lstJobProject.setData( FormToolkit.KEY_DRAW_BORDER,
                                                      FormToolkit.TEXT_BORDER );
     
     this.jobIdentificationTypeAdapter.attachToJobProject(this.lstJobProject );
     this.lstJobProject.setLayoutData( gd );
     
     this.lstJobProject.setLayoutData( gd );
     
     // Create Button ADD     
     gd = new GridData();
     gd.horizontalSpan = 2;
     gd.verticalSpan = 1;
     gd.widthHint = 60;

     this.btnAdd = toolkit.createButton(client,
                                     Messages.getString("JsdlEditor_AddButton"), //$NON-NLS-1$
                                     SWT.PUSH);
     
     this.btnAdd.addSelectionListener(new SelectionListener() {
       public void widgetSelected(final SelectionEvent event) {

         handleAddDialog(Messages.getString( "JobDefinitionPage_JobProjectDialog" )); //$NON-NLS-1$
         JobDefinitionPage.this.jobIdentificationTypeAdapter
                           .performAdd(
                                           JobDefinitionPage.this.lstJobProject,
                                           "lstJobProject", //$NON-NLS-1$                                                       
                                           JobDefinitionPage.this.value);

       }

        public void widgetDefaultSelected(final SelectionEvent event) {
            // Do Nothing - Required method
        }
      });
     
     this.btnAdd.setLayoutData( gd );
     
     //Create Button DEL
     gd = new GridData();
     gd.horizontalSpan = 2;
     gd.widthHint = 60;
     gd.verticalSpan = 1;
     gd.verticalAlignment = GridData.BEGINNING;

     this.btnDel = toolkit.createButton(client,
                                  Messages.getString("JsdlEditor_RemoveButton"), //$NON-NLS-1$
                                  SWT.PUSH);
     this.btnDel.setEnabled( false );
     
     this.jobIdentificationTypeAdapter.attachToDelete( this.btnDel, this.lstJobProject);
     this.btnDel.setLayoutData( gd );
 
     toolkit.paintBordersFor( client );

     
  }
  
  protected void handleAddDialog(final String dialogTitle){
    MultipleInputDialog dialog = new MultipleInputDialog( this.getSite().getShell(),
                                                         dialogTitle );
        
    dialog.addTextField( Messages.getString( "JobDefinitionPage_Value" ), "", false ); //$NON-NLS-1$ //$NON-NLS-2$
    
    if( dialog.open() != Window.OK ) { 
      this.value = null;
      return;
    }
    
    this.value = dialog.getStringValue( Messages.getString( "JobDefinitionPage_Value" ) ) ; //$NON-NLS-1$
    
    
  }

  
  public void notifyChanged(Notification notification) {
    setDirty( true );
  }
  
  

 }
  