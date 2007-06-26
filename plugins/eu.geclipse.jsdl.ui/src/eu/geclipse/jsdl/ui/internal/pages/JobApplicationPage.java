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

package eu.geclipse.jsdl.ui.internal.pages;

/**
 * @author nickl
 *
 */

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import eu.geclipse.jsdl.jsdl.ApplicationTypeAdapter;
import eu.geclipse.jsdl.posix.PosixApplicationTypeAdapter;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.dialogs.MultipleInputDialog;

/**
 * This class provides the Job Application page that appears in the JSDL editor.
 * It provides a graphical user interface to the following elements of a JSDL 
 * document:
 * 
 * Application Name
 * Application Version
 * Application Description 
 *
 */
public final class JobApplicationPage extends FormPage 
                                             implements INotifyChangedListener{
  
  
  protected Composite aS = null;
  protected Composite pS = null;
  protected Composite apS = null ;
    
  protected Text txtExecutable = null;
  protected Text txtPosixName = null;
  protected Text txtInput = null;
  protected Text txtOutput = null;  
  protected Text txtError = null;
  protected Text txtApplicationName = null;
  protected Text txtApplicationVersion = null;
  protected Text txtDescription = null;
  protected Text txtWallTimeLimit = null;
  protected Text txtFileSizeLimit = null;
  protected Text txtCoreDumpLimit = null;
  protected Text txtDataSegmentLimit = null; 
  protected Text txtLockedMemoryLimit = null;
  protected Text txtMemoryLimit = null;
  protected Text txtOpenDescriptorsLimit = null;
  protected Text txtPipeSizeLimit = null;
  protected Text txtStackSizeLimit = null;
  protected Text txtCPUTimeLimit = null;
  protected Text txtProcessCountLimit = null;
  protected Text txtVirtualMemoryLimit = null;
  protected Text txtThreadCountLimit = null;
  protected Text txtUserName = null;
  protected Text txtGroupName = null;
  protected Text txtWorkingDirectory = null;
  
  protected Button btnDel = null;
  protected Button btnAdd = null;
  
  protected Button lblWorkingDirectory = null;
  
  protected Label lblPosixName = null;
  protected Label lblExecutable = null ;
  protected Label lblArgument = null; 
  protected Label lblInput = null;
  protected Label lblOutput = null;
  protected Label lblError = null;
  protected Label lblEnvironment = null;
  
  protected Button lblWallTimeLimit = null;
  protected Button lblFileSizeLimit = null;
  protected Button lblCoreDumpLimit = null;
  protected Button lblDataSegmentLimit = null;
  protected Button lblLockedMemoryLimit = null;
  protected Button lblMemoryLimit = null;
  protected Button lblOpenDescriptorsLimit = null;
  protected Button lblPipeSizeLimit = null;
  protected Button lblStackSizeLimit = null;
  protected Button lblCPUTimeLimit = null;
  protected Button lblProcessCountLimit = null;
  protected Button lblVirtualMemoryLimit = null;
  protected Button lblThreadCountLimit = null;
  protected Button lblUserName = null;
  protected Button lblGroupName  = null;
  
  
  
  protected Table tblEnvironment = null;
  protected TableViewer environmentViewer = null;   
  protected Table tblArgument = null;
  protected TableViewer argumentViewer = null;

  protected ApplicationTypeAdapter applicationTypeAdapter;
  protected PosixApplicationTypeAdapter posixApplicationTypeAdapter;
  protected Object[] value = null;
  
  
  private TableColumn column;    
  private boolean contentRefreshed = false;  
  private boolean dirtyFlag = false;
  

  private final int widgetHeight = 100; 
  
   

  /**
   * JobApplicationPage class constructor. Initialiazes the JobApplica Page 
   * by passing as an argument the container JSDL editor.
   * @param editor
   */
  public JobApplicationPage( final FormEditor editor ) {
    
    super(editor,Messages.getString("JobApplicationPage_pageId") ,  //$NON-NLS-1$
          Messages.getString("JobApplicationPage_PageTitle")); //$NON-NLS-1$
   
   
  } // End Class Constructor
  
  
  
  @Override
  public void setActive(final boolean active) {
    
    if (active){
      if (isContentRefreshed()){    
        this.applicationTypeAdapter.load();  
        this.posixApplicationTypeAdapter.load();
      }//endif isContentRefreshed
    } // endif active
  } // End void setActive()
  
  
  
  @Override
  public boolean isDirty() {
    
    return this.dirtyFlag;
    
  } //End boolean isDirty()

  
  /**
   * This method set's the dirty status of the page.
   * 
   * @param dirtyFlag
   * If TRUE then the page is Dirty and a Save operation is needed.
   * 
   */
  public void setDirty (final boolean dirtyFlag) {
    if (this.dirtyFlag != dirtyFlag) {
      this.dirtyFlag = dirtyFlag;     
      this.getEditor().editorDirtyStateChanged();  
    }
    
  } //End void setDirty()
  
  
  
  private boolean isContentRefreshed(){
    
    return this.contentRefreshed;
    
  } 
  
  
  /**
   * Method that set's the JobApplication Page content. The content is the root 
   * JSDL element. Also this method is responsible to initialize the associated 
   * type adapters for the elements of this page.  This method must be called only
   * from the JSDL Editor.
   * 
   * Associated Type Adapters for this page are: 
   * @see ApplicationTypeAdapter
   * @see PosixApplicationTypeAdapter
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
      this.applicationTypeAdapter.setContent( rootJsdlElement );
      this.posixApplicationTypeAdapter.setContent( rootJsdlElement );

    }
   
   else{
     
      /* Initialize the ApplicationTypeAdapter for this page */
      this.applicationTypeAdapter = new ApplicationTypeAdapter(rootJsdlElement);
      /*Add Save State change notification listener. */
      this.applicationTypeAdapter.addListener( this );
      /* Initialize the PosixApplicationTypeAdapter for this page */
      this.posixApplicationTypeAdapter = new PosixApplicationTypeAdapter(rootJsdlElement);
      /*Add Save State change notification listener. */
      this.posixApplicationTypeAdapter.addListener( this );

   } // End else
          
  } // End void getPageContent() 
  
 
    
  /*
   * This method is used to create the Forms content by
  * creating the form layout and then creating the form
  * sub sections.
  * 
  */
  
  @Override
  protected void createFormContent(final IManagedForm managedForm) {
    
    
    ScrolledForm form = managedForm.getForm();
    form.setText(Messages.getString("JobApplicationPage_ApplicationTitle"));  //$NON-NLS-1$
  
    ColumnLayout layout = new ColumnLayout();
    layout.leftMargin = 10;
    layout.rightMargin = 10;
    /*  Set Max and Min Columns to be 2. */
    layout.maxNumColumns = 2;
    layout.minNumColumns = 2;
    form.getBody().setLayout(layout);

    
    this.aS = createApplicationSection(managedForm, 
                      Messages.getString("JobApplicationPage_Applicationtitle"), //$NON-NLS-1$
              Messages.getString("JobApplicationPage_ApplicationDescription") );   //$NON-NLS-1$
    
    this.applicationTypeAdapter.load();

    
    this.apS = createAdditionalPosixSection(managedForm, 
       Messages.getString("JobApplicationPage_additionalPosixApplElementTitle"), //$NON-NLS-1$
       Messages.getString("JobApplicationPage_additionalPosixApplDescr"));   //$NON-NLS-1$
    
    

    
    this.pS = createPosixSection(managedForm, 
                 Messages.getString("JobApplicationPage_PosixApplicationtitle"),  //$NON-NLS-1$
          Messages.getString("JobApplicationPage_PosixApplicationDescription")); //$NON-NLS-1$
           
    this.posixApplicationTypeAdapter.load();
       
 
    /* Set Form Background */
    form.setBackgroundImage(Activator.getDefault().
                            getImageRegistry().get( "formsbackground" )); //$NON-NLS-1$
    

  }
  
  /* This method is used to create individual subsections */
  
  private Composite createSection(final IManagedForm mform, final String title,
                                  final String desc, final int numColumns,
                                  final boolean expandable) {
      
    final ScrolledForm form = mform.getForm();
    FormToolkit toolkit = mform.getToolkit();
            
    Section section;
    
    if (expandable) {
      
      section = toolkit.createSection(form.getBody(), 
                                            ExpandableComposite.TITLE_BAR
                                            |Section.DESCRIPTION
                                            |ExpandableComposite.TWISTIE
                                            | SWT.WRAP);
    } else {
     
      section = toolkit.createSection(form.getBody(), 
                                              ExpandableComposite.TITLE_BAR 
                                              |Section.DESCRIPTION
                                              |SWT.WRAP);
    }

    
    
    this.applicationTypeAdapter.attachToApplicationSection( section );
    
    section.clientVerticalSpacing = 10;      
    section.setText(title);
    section.setDescription(desc);
    toolkit.createCompositeSeparator(section);
    Composite client = toolkit.createComposite(section);
               
    GridLayout layout = new GridLayout();
    layout.verticalSpacing = 8;
    layout.horizontalSpacing = 8;
    layout.marginTop = 10;
    layout.marginBottom = 10;
    layout.marginWidth =  0; 
    layout.marginHeight = 0;
    layout.numColumns = numColumns;    
    client.setLayout(layout);    
    section.setClient(client);
    
     
    return client;
    
  } //End Composite createSection
  
  
  
  /* 
   * Create the Application Section which includes the following:
   *  -Application Name (String)
   *  -Application Version (String)
   *  -Application Description (String)
   */
  private Composite createApplicationSection(final IManagedForm mform, 
                                             final String title, 
                                             final String desc )                                             
  {
    GridData gd;
       
    Composite client = createSection(mform, title, desc, 2 ,false);
    FormToolkit toolkit = mform.getToolkit();

    //Create Label and Text for Application Name Element
    toolkit.createLabel(client,
                      Messages.getString("JobApplicationPage_ApplicationName")); //$NON-NLS-1$
    
    this.txtApplicationName = toolkit.createText(client, "", SWT.NONE);  //$NON-NLS-1$
    gd = new GridData();
    gd.widthHint = 300;
    this.txtApplicationName.setLayoutData(gd);
    this.applicationTypeAdapter.attachToApplicationName( this.txtApplicationName );
    
    

    //Create Label and Text for Application Version     
    toolkit.createLabel(client,
                   Messages.getString("JobApplicationPage_ApplicationVersion"));  //$NON-NLS-1$
    this.txtApplicationVersion = toolkit.createText(client, "", SWT.NONE);  //$NON-NLS-1$
    gd = new GridData();
    gd.widthHint = 300;
    this.txtApplicationVersion.setLayoutData(gd);
    
    this.applicationTypeAdapter.
                       attachToApplicationVersion( this.txtApplicationVersion );

    //Create Label and Text for Application Description Element    
    toolkit.createLabel(client,
                     Messages.getString("JobApplicationPage_ApplicationDescr"));  //$NON-NLS-1$
    this.txtDescription = toolkit.createText(client, "",  //$NON-NLS-1$
                                                             SWT.NONE 
                                                             | SWT.V_SCROLL
                                                             | SWT.WRAP); 
    gd = new GridData();
    gd.verticalSpan = 3;
    gd.widthHint = 285;
    gd.heightHint = this.widgetHeight;
    this.txtDescription.setLayoutData(gd);
    this.applicationTypeAdapter
                         .attachToApplicationDescription( this.txtDescription );
       
    toolkit.paintBordersFor( client );
    
    return client;
    
  } // End Composite createApplicationSection()
  
  
  
   /* 
    * Create the Posix Application Section which includes the following:
    *  -Executable (String)
    *  -Argument (String)
    *  -Input (String)
    *  -Output (String)
    *  -Error (String)
    *  -Environment (String)
    */
  private Composite createPosixSection(final IManagedForm mform, 
                                       final String title, 
                                       final String desc)
   {    
    GridData lblgd;
    GridData gd;
  
    /* ===================== Posix Application Widget ======================= */

    Composite client = createSection(mform, title, desc, 4 ,true);
    FormToolkit toolkit = mform.getToolkit();
       
    gd = new GridData();
    gd.grabExcessHorizontalSpace = true;
    gd.verticalAlignment = GridData.CENTER;
    gd.verticalSpan = 1;
    gd.horizontalSpan = 3;
    gd.widthHint = 330;
    
    
    /* Posix Name Widget */
    this.lblPosixName = toolkit.createLabel(client,
                            Messages.getString("JobApplicationPage_PosixName"));  //$NON-NLS-1$
    
    this.txtPosixName = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    this.posixApplicationTypeAdapter.attachPosixApplicationName(this.txtPosixName);
    this.txtPosixName.setLayoutData(gd);
    
    /* =========================== Executable Widget ======================== */
    
    lblgd = new GridData();
    lblgd.horizontalSpan = 1;
    
    this.lblExecutable = toolkit.createLabel(client,
                           Messages.getString("JobApplicationPage_Executable")); //$NON-NLS-1$
    
    this.lblExecutable.setLayoutData( lblgd ); 
    
    this.txtExecutable = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    
    this.posixApplicationTypeAdapter
                        .attachPosixApplicationExecutable(this.txtExecutable);
    
    gd = new GridData();
    gd.widthHint = 330;
    gd.horizontalSpan=3;
    gd.grabExcessHorizontalSpace = true;
    gd.verticalAlignment = GridData.CENTER;
    gd.verticalSpan = 1;
    this.txtExecutable.setLayoutData(gd);
 
    
    /* ============================= Argument Widget ======================== */
    lblgd = new GridData();
    lblgd.verticalSpan = 2;
    lblgd.horizontalSpan = 1;
    
    this.lblArgument = toolkit.createLabel(client,
                             Messages.getString("JobApplicationPage_Argument")); //$NON-NLS-1$
    
    this.lblArgument.setLayoutData( lblgd );
    
    gd = new GridData();
    gd.horizontalAlignment = GridData.FILL;
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 2;
    gd.horizontalSpan = 1;
    gd.widthHint = 250;
    gd.heightHint = this.widgetHeight;
    
    
    this.argumentViewer = new TableViewer(client, SWT.BORDER);
    this.tblArgument = this.argumentViewer.getTable();
    this.tblArgument.setHeaderVisible( true);
    this.argumentViewer.setContentProvider( new FeatureContentProvider() );
    this.argumentViewer.setLabelProvider( new FeatureLabelProvider() );
    
    this.column = new TableColumn(this.tblArgument, SWT.NONE);    
    this.column.setText( "File System Name" ); //$NON-NLS-1$
    this.column.setWidth( 150 );
    this.column = new TableColumn(this.tblArgument, SWT.NONE);    
    this.column.setText( "Value" ); //$NON-NLS-1$
    this.column.setWidth( 100 );
    
    this.posixApplicationTypeAdapter
                       .attachToPosixApplicationArgument( this.argumentViewer );
        
    this.tblArgument.setData(  FormToolkit.KEY_DRAW_BORDER );
    this.tblArgument.setLayoutData( gd);
     
                
    //Create "Add" Button
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    this.btnAdd = toolkit.createButton(client,
                                       Messages.getString("JsdlEditor_AddButton") //$NON-NLS-1$
                                       , SWT.PUSH);
    
    this.btnAdd.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent event) {
        handleAddDialog(Messages.getString( "JobApplicationPage_ArgumentDialog" )); //$NON-NLS-1$
//        JobApplicationPage.this.posixApplicationTypeAdapter.performAdd(
//                                         JobApplicationPage.this.argumentViewer,
//                                                "argumentViewer", //$NON-NLS-1$
//                                                JobApplicationPage.this.value );
      
      }

       public void widgetDefaultSelected(final SelectionEvent event) {
           // Do Nothing - Required method
       }
     });
    
    this.btnAdd.setLayoutData( gd );
    
    
    //Create "Remove" Button
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    gd.verticalAlignment = GridData.BEGINNING;
    
    this.btnDel = toolkit.createButton(client,
                                    Messages.getString("JsdlEditor_RemoveButton") //$NON-NLS-1$
                                    , SWT.PUSH);
    this.posixApplicationTypeAdapter.attachToDelete( this.btnDel, 
                                                             this.argumentViewer );

    this.btnDel.setLayoutData( gd );
      
    /* ============================= Input Widget =========================== */
    
    lblgd = new GridData();   
    lblgd.horizontalSpan = 1;
    
    this.lblInput = toolkit.createLabel(client,
                                Messages.getString("JobApplicationPage_Input")); //$NON-NLS-1$
    this.lblInput.setLayoutData(lblgd);
    
    
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;   
    gd.horizontalSpan = 3;
    
    gd.widthHint = 330;
    this.txtInput = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    this.posixApplicationTypeAdapter.attachPosixApplicationInput( this.txtInput );
    this.txtInput.setLayoutData(gd);
   
    /* ============================= Output Widget =========================== */
    
    lblgd = new GridData();    
    this.lblOutput = toolkit.createLabel(client,
                               Messages.getString("JobApplicationPage_Output")); //$NON-NLS-1$
    
    this.lblOutput.setLayoutData( lblgd );
    
    this.txtOutput = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    this.posixApplicationTypeAdapter.attachPosixApplicationOutput( this.txtOutput );
    
    gd = new GridData();    
    gd.verticalAlignment = GridData.FILL;    
    gd.horizontalSpan = 3;
    gd.widthHint = 330;
    this.txtOutput.setLayoutData( gd );
        
    
    /* ============================= Error Widget =========================== */ 
    
    lblgd = new GridData();    
    this.lblError = toolkit.createLabel(client,
                                Messages.getString("JobApplicationPage_Error")); //$NON-NLS-1$
    
    this.lblError.setLayoutData( lblgd );
    
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.horizontalSpan = 3;
    gd.widthHint = 330;
    this.txtError = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    this.posixApplicationTypeAdapter.attachPosixApplicationError( this.txtError );
    this.txtError.setLayoutData(gd);
       
    /* ======================= Envorinment Widget =========================== */   
    
    lblgd = new GridData();
    lblgd.verticalSpan = 2;
    lblgd.horizontalSpan = 1;
    
    this.lblEnvironment = toolkit.createLabel(client,
                          Messages.getString("JobApplicationPage_Environment")); //$NON-NLS-1$
    
    this.lblEnvironment.setLayoutData( lblgd );
        
    gd = new GridData();
    gd.horizontalAlignment = GridData.FILL;
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 2;
    gd.horizontalSpan = 1;
    gd.widthHint = 250;
    gd.heightHint = this.widgetHeight;
    
    
    this.environmentViewer = new TableViewer(client, SWT.BORDER);
    this.tblEnvironment = this.environmentViewer.getTable();
    this.tblEnvironment.setHeaderVisible( true);
    this.environmentViewer.setContentProvider( new FeatureContentProvider() );
    this.environmentViewer.setLabelProvider( new FeatureLabelProvider() );
    
    
    this.column = new TableColumn(this.tblEnvironment, SWT.NONE);       
    this.column.setText( "Name" );         //$NON-NLS-1$
    this.column.setWidth( 60 );
    this.column = new TableColumn(this.tblEnvironment, SWT.NONE);    
    this.column.setText( "File System Name" ); //$NON-NLS-1$
    this.column.setWidth( 130 );
    this.column = new TableColumn(this.tblEnvironment, SWT.NONE);    
    this.column.setText( "Value" ); //$NON-NLS-1$
    this.column.setWidth( 60 );

    
    this.posixApplicationTypeAdapter.
                   attachToPosixApplicationEnvironment( this.environmentViewer );
    
        
    this.tblEnvironment.setData(  FormToolkit.KEY_DRAW_BORDER );
    this.tblEnvironment.setLayoutData( gd);
     
    /* Create "Add" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    this.btnAdd = toolkit.createButton(client,
                                     Messages.getString("JsdlEditor_AddButton"), //$NON-NLS-1$
                                       SWT.BUTTON1);
    
    this.btnAdd.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent event) {
        handleAddDialog(Messages.getString( "JobApplicationPage_EnvironmentDialog" )); //$NON-NLS-1$
        
      
      }

       public void widgetDefaultSelected(final SelectionEvent event) {
           // Do Nothing - Required method
       }
     });
    
   
    
    this.btnAdd.setLayoutData( gd);
    
    /* Create "Remove" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    gd.verticalAlignment = GridData.BEGINNING;
    
    this.btnDel = toolkit.createButton(client,
                                  Messages.getString("JsdlEditor_RemoveButton"), //$NON-NLS-1$
                                  SWT.PUSH); 
    
    this.posixApplicationTypeAdapter.attachToDelete( this.btnDel, 
                                                       this.environmentViewer );
    this.btnDel.setLayoutData( gd );
       
    toolkit.paintBordersFor( client );
    
    return client;
     }
  

  /* 
   * Create Additional Posix Application Section which includes the following:
   *  -Working Directory (String)
   *  -Wall Time Limit (nonNegativeInteger)
   *  -File Size Limit (nonNegativeInteger)
   *  -Core Dump Limit (nonNegativeInteger)
   *  -Data Segment Limit (nonNegativeInteger)
   *  -Locked Memory Limit (nonNegativeInteger)
   *  -Memory Limit (nonNegativeInteger)
   *  -Open Descriptors Limit (nonNegativeInteger)
   *  -Pipe Size Limit (nonNegativeInteger)
   *  -Stack Size Limit (nonNegativeInteger)
   *  -CPU Time Limit (nonNegativeInteger)
   *  -Process Count Limit (nonNegativeInteger)
   *  -Virtual Memory Limit (nonNegativeInteger)
   *  -Thread Count Limit (nonNegativeInteger)
   *  -User Name (String)
   *  -Group Name (String)
   */
  private Composite createAdditionalPosixSection(final IManagedForm mform, 
                                             final String title, 
                                             final String desc )
  {
   GridData gd;
   Composite client = createSection(mform, title, desc, 2, true);
   FormToolkit toolkit = mform.getToolkit();
   
   
      
   gd = new GridData();
   
   /* ========================= Working Directory =========================== */
   
   this.lblWorkingDirectory = toolkit.createButton(client, Messages.
                               getString("JobApplicationPage_WorkingDirectory"), //$NON-NLS-1$
                               SWT.CHECK);   
   this.lblWorkingDirectory.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
     @Override
    public void widgetSelected(final org.eclipse.swt.events.SelectionEvent e) {
//     Determines if the checkBox is checked or not
     boolean selected = JobApplicationPage.this.lblWorkingDirectory.getSelection();
//      If checked then execute this code
     if (selected == true) {
       // currently empty
     }
//      If not checked, then execute this one
     else
     { // Wow, commenting out bad style (comment from Mathias)

       }
     }
     });

   
   this.txtWorkingDirectory = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtWorkingDirectory.setLayoutData(gd);
//   this.txtWorkingDirectory.addFocusListener( new org.eclipse.swt.events.FocusListener(){
//     public void focusLost( final org.eclipse.swt.events.FocusEvent e  ) {
//      DirectoryNameType directoryName = PosixFactory.eINSTANCE.createDirectoryNameType();
//      directoryName.setValue( JobApplicationPage.this.txtWorkingDirectory.getText() );
//      JobApplicationPage.this.posixApplType.setWorkingDirectory( directoryName );
//     }
//     public void focusGained( final org.eclipse.swt.events.FocusEvent e  ) {
//     // 
//     } 
//   });
   
   /* ============================ Wall Time Limit  ========================= */
   
   this.lblWallTimeLimit = toolkit.createButton(client, 
                          Messages.getString("JobApplicationPage_WallTimeLimit") //$NON-NLS-1$
                          ,SWT.CHECK); 
   
   this.txtWallTimeLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$-NLS-1$
   gd.widthHint = 200;
   this.txtWallTimeLimit.setLayoutData(gd);
      
   /* ============================ File Size Limit ========================== */

   this.lblFileSizeLimit = toolkit.createButton(client,
                          Messages.getString("JobApplicationPage_FileSizeLimit") //$NON-NLS-1$
                          ,SWT.CHECK); 
   
   this.txtFileSizeLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtFileSizeLimit.setLayoutData(gd);
   
   /* ============================ Core Dump Limit ========================== */
   
   this.lblCoreDumpLimit = toolkit.createButton(client, 
                          Messages.getString("JobApplicationPage_CoreDumpLimit") //$NON-NLS-1$
                           ,SWT.CHECK); 
   
   this.txtCoreDumpLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtCoreDumpLimit.setLayoutData(gd);

   /* ==========================Data Segment Limit ========================== */
   
   this.lblDataSegmentLimit = toolkit.createButton(client,
                       Messages.getString("JobApplicationPage_DataSegmentLimit") //$NON-NLS-1$
                      ,SWT.CHECK); 
   
   this.txtDataSegmentLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtDataSegmentLimit.setLayoutData(gd);

   /* ======================== Locked Memory Limit ========================== */
   
   this.lblLockedMemoryLimit = toolkit.createButton(client,
                    Messages.getString("JobApplicationPage_LockedMemoryLimit"),  //$NON-NLS-1$
                    SWT.CHECK); 
   
   this.txtLockedMemoryLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtLockedMemoryLimit.setLayoutData(gd);
   
   /* =============================== Memory Limit ========================== */
   
   this.lblMemoryLimit = toolkit.createButton(client,
                           Messages.getString("JobApplicationPage_MemoryLimit"), //$NON-NLS-1$
                           SWT.CHECK); 
   
   this.txtMemoryLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$  
   gd.widthHint = 200;
   this.txtMemoryLimit.setLayoutData(gd);
   
   /* ====================== Open Descriptors Limit ========================= */
   
   this.lblOpenDescriptorsLimit = toolkit.createButton(client,
                  Messages.getString("JobApplicationPage_OpenDescriptorsLimit"),  //$NON-NLS-1$
                    SWT.CHECK); 
   
   this.txtOpenDescriptorsLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtOpenDescriptorsLimit.setLayoutData(gd);
   
   /* ============================ Pipe Size Limit ========================== */
   
   this.lblPipeSizeLimit = toolkit.createButton(client,
                         Messages.getString("JobApplicationPage_PipeSizeLimit"), //$NON-NLS-1$
                                                SWT.CHECK); 
   
   this.txtPipeSizeLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtPipeSizeLimit.setLayoutData(gd);
   
   /* =========================== Stack Size Limit ========================== */
   
   this.lblStackSizeLimit = toolkit.createButton(client,
                        Messages.getString("JobApplicationPage_StackSizeLimit"), //$NON-NLS-1$
                        SWT.CHECK); 
   
   this.txtStackSizeLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtStackSizeLimit.setLayoutData(gd);
   
   /* ============================= CPU Time Limit ========================== */
   
   this.lblCPUTimeLimit = toolkit.createButton(client,
                          Messages.getString("JobApplicationPage_CPUTimeLimit"), //$NON-NLS-1$
                          SWT.CHECK); 
   
   this.txtCPUTimeLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$   
   gd.widthHint = 200;
   this.txtCPUTimeLimit.setLayoutData(gd);
   
   /* ====================== Process Count Limit ============================ */
   
   this.lblProcessCountLimit = toolkit.createButton(client,
                     Messages.getString("JobApplicationPage_ProcessCountLimit"), //$NON-NLS-1$
                     SWT.CHECK); 
   
   this.txtProcessCountLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtProcessCountLimit.setLayoutData(gd);
   
   /* ============================= Virtual Memory Limit ==================== */
   
   this.lblVirtualMemoryLimit = toolkit.createButton(client,
                    Messages.getString("JobApplicationPage_VirtualMemoryLimit"),  //$NON-NLS-1$
                    SWT.CHECK); 
   
   this.txtVirtualMemoryLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtVirtualMemoryLimit.setLayoutData(gd);
   
   /* ========================= Thread Count Limit ========================== */
   
   this.lblThreadCountLimit = toolkit.createButton(client,
                      Messages.getString("JobApplicationPage_ThreadCountLimit"), //$NON-NLS-1$
                      SWT.CHECK); 
   
   this.txtThreadCountLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$  
   gd.widthHint = 200;
   this.txtThreadCountLimit.setLayoutData(gd);

   /* ============================= User Name Limit ======================== */
   
   this.lblUserName = toolkit.createButton(client,
                              Messages.getString("JobApplicationPage_UserName"), //$NON-NLS-1$
                              SWT.CHECK); 
   
   this.txtUserName = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtUserName.setLayoutData(gd);
//   this.txtUserName.addFocusListener( new org.eclipse.swt.events.FocusListener(){
//     public void focusLost( final org.eclipse.swt.events.FocusEvent e  ) {
//      UserNameType userName = PosixFactory.eINSTANCE.createUserNameType();
//      userName.setValue( JobApplicationPage.this.txtUserName.getText() );
//      JobApplicationPage.this.posixApplType.setUserName( userName );
//     }
//     public void focusGained( final org.eclipse.swt.events.FocusEvent e  ) {
//     // 
//     } 
//   });

   /* ============================= Group Name Limit ======================== */  
   this.lblGroupName = toolkit.createButton(client,
                             Messages.getString("JobApplicationPage_GroupName"), //$NON-NLS-1$
                             SWT.CHECK); 
   
   this.txtGroupName = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtGroupName.setLayoutData(gd);
//   this.txtGroupName.addFocusListener( new org.eclipse.swt.events.FocusListener(){
//     public void focusLost( final org.eclipse.swt.events.FocusEvent e  ) {
//      GroupNameType groupName = PosixFactory.eINSTANCE.createGroupNameType();
//      groupName.setValue( JobApplicationPage.this.txtGroupName.getText() );
//      JobApplicationPage.this.posixApplType.setGroupName( groupName );
//     }
//     public void focusGained( final org.eclipse.swt.events.FocusEvent e  ) {
//     // 
//     } 
//   });

   toolkit.paintBordersFor( client );
   
   return client;
   }
  
  
  protected void handleAddDialog(final String dialogTitle){
    
    
    MultipleInputDialog dialog = new MultipleInputDialog( this.getSite().getShell(),
                                                         dialogTitle );
    if (dialogTitle == Messages.getString("JobApplicationPage_ArgumentDialog" )){ //$NON-NLS-1$
      
      this.value = new Object[2];
      dialog.addTextField( Messages.getString( "JobApplicationPage_FileSystemName" ), "", false ); //$NON-NLS-1$ //$NON-NLS-2$
      dialog.addTextField( Messages.getString( "JobApplicationPage_Value" ), "", false ); //$NON-NLS-1$ //$NON-NLS-2$
                       
    }
    else
    {
      
      this.value = new Object[3];
      dialog.addTextField( Messages.getString( "JobApplicationPage_Name" ), "", false ); //$NON-NLS-1$ //$NON-NLS-2$
      dialog.addTextField( Messages.getString( "JobApplicationPage_FileSystemName" ), "", false ); //$NON-NLS-1$ //$NON-NLS-2$
      dialog.addTextField( Messages.getString( "JobApplicationPage_Value" ), "", false ); //$NON-NLS-1$ //$NON-NLS-2$
           
    }
        
    if( dialog.open() != Window.OK ) {
      return;
    }
    
    if (this.value.length == 2){
      this.value[0] = dialog.getStringValue( Messages.getString( "JobApplicationPage_FileSystemName" ) ) ; //$NON-NLS-1$
      this.value[1] = dialog.getStringValue( Messages.getString( "JobApplicationPage_Value" ) ) ;     //$NON-NLS-1$
    }
    else {
      
    this.value[0] = dialog.getStringValue( Messages.getString( "JobApplicationPage_Name" ) ) ; //$NON-NLS-1$
    this.value[1] = dialog.getStringValue( Messages.getString( "JobApplicationPage_FileSystemName" ) ) ; //$NON-NLS-1$  
    this.value[2] = dialog.getStringValue( Messages.getString( "JobApplicationPage_Value" ) ) ;     //$NON-NLS-1$
    }
    
  }



  public void notifyChanged( Notification notification ) {
      setDirty( true );
    
  }
  
} // End Class
