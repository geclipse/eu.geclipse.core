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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
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

public class JobApplicationPage extends FormPage {
  
  
  Composite aS = null;
  Composite pS = null;
  Composite apS = null ;
    
  Text txtExecutable = null;
  Text txtPosixName = null;
  Text txtInput = null;
  Text txtOutput = null;  
  Text txtError = null;
  Text txtApplicationName = null;
  Text txtApplicationVersion = null;
  Text txtDescription = null;
  Text txtWallTimeLimit = null;
  Text txtFileSizeLimit = null;
  Text txtCoreDumpLimit = null;
  Text txtDataSegmentLimit = null; 
  Text txtLockedMemoryLimit = null;
  Text txtMemoryLimit = null;
  Text txtOpenDescriptorsLimit = null;
  Text txtPipeSizeLimit = null;
  Text txtStackSizeLimit = null;
  Text txtCPUTimeLimit = null;
  Text txtProcessCountLimit = null;
  Text txtVirtualMemoryLimit = null;
  Text txtThreadCountLimit = null;
  Text txtUserName = null;
  Text txtGroupName = null;
  Text txtWorkingDirectory = null;
  
  Button btnDel = null;
  Button btnAdd = null;
  
  Button lblWorkingDirectory = null;
  
  Label lblPosixName = null;
  Label lblExecutable = null ;
  Label lblArgument = null; 
  Label lblInput = null;
  Label lblOutput = null;
  Label lblError = null;
  Label lblEnvironment = null;
  
  Button lblWallTimeLimit = null;
  Button lblFileSizeLimit = null;
  Button lblCoreDumpLimit = null;
  Button lblDataSegmentLimit = null;
  Button lblLockedMemoryLimit = null;
  Button lblMemoryLimit = null;
  Button lblOpenDescriptorsLimit = null;
  Button lblPipeSizeLimit = null;
  Button lblStackSizeLimit = null;
  Button lblCPUTimeLimit = null;
  Button lblProcessCountLimit = null;
  Button lblVirtualMemoryLimit = null;
  Button lblThreadCountLimit = null;
  Button lblUserName = null;
  Button lblGroupName  = null;
  
  
  
  Table tblEnvironment = null;
  Table tblArgument = null;
  TableColumn column; 
  
  
  Boolean contentRefreshed = Boolean.FALSE;
  
  private ApplicationTypeAdapter applicationTypeAdapter;
  private PosixApplicationTypeAdapter posixApplicationTypeAdapter;
  

 
 
  /* Class Constructor */
  public JobApplicationPage( final FormEditor editor ) {
    
    super(editor,Messages.getString("JobApplicationPage_pageId") , 
          Messages.getString("JobApplicationPage_PageTitle"));
   
   
  } // End Constructor
  
  
  
  @Override
  public void setActive(final boolean active) {
    
    if (active){
      if (isContentRefreshed()){    
        this.applicationTypeAdapter.load();  
        this.posixApplicationTypeAdapter.load();
      }//endif isContentRefreshed
    } // endif active
  } // End void setActive()
  
  
  
  private boolean isContentRefreshed(){          
    return this.contentRefreshed.booleanValue();
  }
  
  
  
  public void setPageContent(final EObject rootJsdlElement, 
                             final boolean refreshStatus){

   if (refreshStatus) {
      this.contentRefreshed = Boolean.TRUE;
      this.applicationTypeAdapter.setContent( rootJsdlElement );
      this.posixApplicationTypeAdapter.setContent( rootJsdlElement );

    }
   else{
      this.applicationTypeAdapter = new ApplicationTypeAdapter(rootJsdlElement);
      this.posixApplicationTypeAdapter = new PosixApplicationTypeAdapter(rootJsdlElement);

   }
          
  } // End void getPageContent() 
  
 
    
  //This method is used to create the Forms content by
  // creating the form layout and then creating the form
  // sub sections.
  
  @Override
  protected void createFormContent(final IManagedForm managedForm) {
    
    
    ScrolledForm form = managedForm.getForm();
    form.setText(Messages.getString("JobApplicationPage_ApplicationTitle")); 
  
    ColumnLayout layout = new ColumnLayout();
    layout.leftMargin = 10;
    layout.rightMargin = 10;
    /*  Set Max and Min Columns to be 2. */
    layout.maxNumColumns = 2;
    layout.minNumColumns = 2;
    form.getBody().setLayout(layout);

    
    this.aS = createApplicationSection(managedForm, 
                      Messages.getString("JobApplicationPage_Applicationtitle"),
              Messages.getString("JobApplicationPage_ApplicationDescription") );  
    
    this.applicationTypeAdapter.load();

    
    this.apS = createAdditionalPosixSection(managedForm, 
       Messages.getString("JobApplicationPage_additionalPosixApplElementTitle"),
       Messages.getString("JobApplicationPage_additionalPosixApplDescr"));  
    
    

    
    this.pS = createPosixSection(managedForm, 
                 Messages.getString("JobApplicationPage_PosixApplicationtitle"), 
          Messages.getString("JobApplicationPage_PosixApplicationDescription"));
           
    this.posixApplicationTypeAdapter.load();
       
 
    /* Set Form Background */
    form.setBackgroundImage(Activator.getDefault().
                            getImageRegistry().get( "formsbackground" ));
    

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
                                            | Section.TWISTIE | SWT.WRAP);
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
                      Messages.getString("JobApplicationPage_ApplicationName"));
    
    this.txtApplicationName = toolkit.createText(client, "", SWT.NONE);  //$NON-NLS-1$
    gd = new GridData();
    gd.widthHint = 300;
    this.txtApplicationName.setLayoutData(gd);
    this.applicationTypeAdapter.attachToApplicationName( this.txtApplicationName );
    
    

    //Create Label and Text for Application Version     
    toolkit.createLabel(client,
                   Messages.getString("JobApplicationPage_ApplicationVersion")); 
    this.txtApplicationVersion = toolkit.createText(client, "", SWT.NONE);  //$NON-NLS-1$
    gd = new GridData();
    gd.widthHint = 300;
    this.txtApplicationVersion.setLayoutData(gd);
    
    this.applicationTypeAdapter.
                       attachToApplicationVersion( this.txtApplicationVersion );

    //Create Label and Text for Application Description Element    
    toolkit.createLabel(client,
                     Messages.getString("JobApplicationPage_ApplicationDescr")); 
    this.txtDescription = toolkit.createText(client, "",  //$NON-NLS-1$
                                                             SWT.NONE 
                                                             | SWT.V_SCROLL
                                                             | SWT.WRAP); 
    gd = new GridData();
    gd.verticalSpan = 3;
    gd.widthHint = 285;
    gd.heightHint = 100;
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
                            Messages.getString("JobApplicationPage_PosixName")); 
    
    this.txtPosixName = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    this.posixApplicationTypeAdapter.attachPosixApplicationName(this.txtPosixName);
    this.txtPosixName.setLayoutData(gd);
    
    /* =========================== Executable Widget ======================== */
    
    lblgd = new GridData();
    lblgd.horizontalSpan = 1;
    
    this.lblExecutable = toolkit.createLabel(client,
                           Messages.getString("JobApplicationPage_Executable"));
    
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
                             Messages.getString("JobApplicationPage_Argument"));
    
    this.lblArgument.setLayoutData( lblgd );
    
    gd = new GridData();
    gd.horizontalAlignment = GridData.FILL;
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 2;
    gd.horizontalSpan = 1;
    gd.widthHint = 250;
    gd.heightHint = 100;
    
    this.tblArgument = new Table(client,SWT.SINGLE);
    this.tblArgument.setHeaderVisible( true);
    
    this.column = new TableColumn(this.tblArgument, SWT.NONE);    
    this.column.setText( "File System Name" );
    this.column.setWidth( 150 );
    this.column = new TableColumn(this.tblArgument, SWT.NONE);    
    this.column.setText( "Value" );
    this.column.setWidth( 100 );
    
    this.posixApplicationTypeAdapter.attachToPosixApplicationArgument( this.tblArgument );
        
    tblArgument.setData(  FormToolkit.KEY_DRAW_BORDER );
    tblArgument.setLayoutData( gd);
     
                
    //Create "Add" Button
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    this.btnAdd = toolkit.createButton(client,
                                       Messages.getString("JsdlEditor_AddButton")
                                       , SWT.PUSH);
    this.btnAdd.setLayoutData( gd );
    
    
    //Create "Remove" Button
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    gd.verticalAlignment = GridData.BEGINNING;
    
    this.btnDel = toolkit.createButton(client,
                                    Messages.getString("JsdlEditor_RemoveButton")
                                    , SWT.PUSH);
    this.posixApplicationTypeAdapter.attachToDelete( this.btnDel, 
                                                             this.tblArgument );
    
    this.btnDel.setLayoutData( gd );
      
    /* ============================= Input Widget =========================== */
    
    lblgd = new GridData();   
    lblgd.horizontalSpan = 1;
    
    this.lblInput = toolkit.createLabel(client,
                                Messages.getString("JobApplicationPage_Input"));
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
                               Messages.getString("JobApplicationPage_Output"));
    
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
                                Messages.getString("JobApplicationPage_Error"));
    
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
                          Messages.getString("JobApplicationPage_Environment"));
    
    this.lblEnvironment.setLayoutData( lblgd );
        
    gd = new GridData();
    gd.horizontalAlignment = GridData.FILL;
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 2;
    gd.horizontalSpan = 1;
    gd.widthHint = 250;
    gd.heightHint = 100;

    tblEnvironment = new Table(client,SWT.SINGLE);
    tblEnvironment.setHeaderVisible( true);
   
   
    column = new TableColumn(this.tblEnvironment, SWT.NONE);       
    column.setText( "Name" );        
    column.setWidth( 60 );
    column = new TableColumn(this.tblEnvironment, SWT.NONE);    
    column.setText( "File System Name" );
    column.setWidth( 130 );
    column = new TableColumn(this.tblEnvironment, SWT.NONE);    
    column.setText( "Value" );
    column.setWidth( 60 );

    this.posixApplicationTypeAdapter.
                     attachToPosixApplicationEnvironment( this.tblEnvironment );

        
    tblEnvironment.setData(  FormToolkit.KEY_DRAW_BORDER );
    tblEnvironment.setLayoutData( gd);
     
    /* Create "Add" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    this.btnAdd = toolkit.createButton(client,
                                     Messages.getString("JsdlEditor_AddButton"),
                                       SWT.BUTTON1); 
    this.btnAdd.setLayoutData( gd);
    
    /* Create "Remove" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    gd.verticalAlignment = GridData.BEGINNING;
    
    this.btnDel = toolkit.createButton(client,
                                  Messages.getString("JsdlEditor_RemoveButton"),
                                  SWT.PUSH); 
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
                               getString("JobApplicationPage_WorkingDirectory"),
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
                          Messages.getString("JobApplicationPage_WallTimeLimit")
                          ,SWT.CHECK); 
   
   this.txtWallTimeLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$-NLS-1$
   gd.widthHint = 200;
   this.txtWallTimeLimit.setLayoutData(gd);
      
   /* ============================ File Size Limit ========================== */

   this.lblFileSizeLimit = toolkit.createButton(client,
                          Messages.getString("JobApplicationPage_FileSizeLimit")
                          ,SWT.CHECK); 
   
   this.txtFileSizeLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtFileSizeLimit.setLayoutData(gd);
   
   /* ============================ Core Dump Limit ========================== */
   
   this.lblCoreDumpLimit = toolkit.createButton(client, 
                          Messages.getString("JobApplicationPage_CoreDumpLimit")
                           ,SWT.CHECK); 
   
   this.txtCoreDumpLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtCoreDumpLimit.setLayoutData(gd);

   /* ==========================Data Segment Limit ========================== */
   
   this.lblDataSegmentLimit = toolkit.createButton(client,
                       Messages.getString("JobApplicationPage_DataSegmentLimit")
                      ,SWT.CHECK); 
   
   this.txtDataSegmentLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtDataSegmentLimit.setLayoutData(gd);

   /* ======================== Locked Memory Limit ========================== */
   
   this.lblLockedMemoryLimit = toolkit.createButton(client,
                    Messages.getString("JobApplicationPage_LockedMemoryLimit"), 
                    SWT.CHECK); 
   
   this.txtLockedMemoryLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtLockedMemoryLimit.setLayoutData(gd);
   
   /* =============================== Memory Limit ========================== */
   
   this.lblMemoryLimit = toolkit.createButton(client,
                           Messages.getString("JobApplicationPage_MemoryLimit"),
                           SWT.CHECK); 
   
   this.txtMemoryLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$  
   gd.widthHint = 200;
   this.txtMemoryLimit.setLayoutData(gd);
   
   /* ====================== Open Descriptors Limit ========================= */
   
   this.lblOpenDescriptorsLimit = toolkit.createButton(client,
                  Messages.getString("JobApplicationPage_OpenDescriptorsLimit"), 
                    SWT.CHECK); 
   
   this.txtOpenDescriptorsLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtOpenDescriptorsLimit.setLayoutData(gd);
   
   /* ============================ Pipe Size Limit ========================== */
   
   this.lblPipeSizeLimit = toolkit.createButton(client,
                         Messages.getString("JobApplicationPage_PipeSizeLimit"),
                                                SWT.CHECK); 
   
   this.txtPipeSizeLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtPipeSizeLimit.setLayoutData(gd);
   
   /* =========================== Stack Size Limit ========================== */
   
   this.lblStackSizeLimit = toolkit.createButton(client,
                        Messages.getString("JobApplicationPage_StackSizeLimit"),
                        SWT.CHECK); 
   
   this.txtStackSizeLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtStackSizeLimit.setLayoutData(gd);
   
   /* ============================= CPU Time Limit ========================== */
   
   this.lblCPUTimeLimit = toolkit.createButton(client,
                          Messages.getString("JobApplicationPage_CPUTimeLimit"),
                          SWT.CHECK); 
   
   this.txtCPUTimeLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$   
   gd.widthHint = 200;
   this.txtCPUTimeLimit.setLayoutData(gd);
   
   /* ====================== Process Count Limit ============================ */
   
   this.lblProcessCountLimit = toolkit.createButton(client,
                     Messages.getString("JobApplicationPage_ProcessCountLimit"),
                     SWT.CHECK); 
   
   this.txtProcessCountLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtProcessCountLimit.setLayoutData(gd);
   
   /* ============================= Virtual Memory Limit ==================== */
   
   this.lblVirtualMemoryLimit = toolkit.createButton(client,
                    Messages.getString("JobApplicationPage_VirtualMemoryLimit"), 
                    SWT.CHECK); 
   
   this.txtVirtualMemoryLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
   gd.widthHint = 200;
   this.txtVirtualMemoryLimit.setLayoutData(gd);
   
   /* ========================= Thread Count Limit ========================== */
   
   this.lblThreadCountLimit = toolkit.createButton(client,
                      Messages.getString("JobApplicationPage_ThreadCountLimit"),
                      SWT.CHECK); 
   
   this.txtThreadCountLimit = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$  
   gd.widthHint = 200;
   this.txtThreadCountLimit.setLayoutData(gd);

   /* ============================= User Name Limit ======================== */
   
   this.lblUserName = toolkit.createButton(client,
                              Messages.getString("JobApplicationPage_UserName"),
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
                             Messages.getString("JobApplicationPage_GroupName"),
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
  
} // End Class
