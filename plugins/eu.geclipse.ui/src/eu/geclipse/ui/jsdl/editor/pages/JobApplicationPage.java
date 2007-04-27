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

package eu.geclipse.ui.jsdl.editor.pages;

/**
 * @author nickl
 *
 */

import java.math.BigInteger;
import java.util.Hashtable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import eu.geclipse.jsdl.adapters.jsdl.ApplicationTypeAdapter;
import eu.geclipse.jsdl.adapters.posix.PosixApplicationTypeAdapter;
import eu.geclipse.jsdl.posix.DirectoryNameType;
import eu.geclipse.jsdl.posix.GroupNameType;
import eu.geclipse.jsdl.posix.LimitsType;
import eu.geclipse.jsdl.posix.POSIXApplicationType;
import eu.geclipse.jsdl.posix.PosixFactory;
import eu.geclipse.jsdl.posix.UserNameType;

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
  
  Hashtable< String, Text > widgetMap = new Hashtable< String, Text >();
  Boolean contentRefreshed = false;
  
  private ApplicationTypeAdapter applicationTypeAdapter;
  private PosixApplicationTypeAdapter posixApplicationTypeAdapter;
  
//  private ApplicationType applType = JsdlFactory.eINSTANCE
//                                                      .createApplicationType();
  
  private POSIXApplicationType posixApplType = PosixFactory.eINSTANCE
                                                 .createPOSIXApplicationType();
 
  // Class Constructor
  public JobApplicationPage( final FormEditor editor ) {
    
    super(editor,Messages.JobApplicationPage_pageId , 
          Messages.JobApplicationPage_PageTitle);
   
   
  } // End Constructor
  
  
  
  public void setActive(final boolean active) {
    
    if (active){
      if (isContentRefreshed()){    
        this.applicationTypeAdapter.load();  
        this.posixApplicationTypeAdapter.load();
      }//endif isContentRefreshed
    } // endif active
  }
  
  
  
  private boolean isContentRefreshed(){          
    return this.contentRefreshed;
  }
  
  
  
  public void setPageContent(final EObject rootJsdlElement, 
                             final boolean refreshStatus){

   if (refreshStatus) {
      this.contentRefreshed = true;
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
    form.setText(Messages.JobApplicationPage_ApplicationTitle); 
  
    ColumnLayout layout = new ColumnLayout();
    layout.maxNumColumns = 2;
    layout.minNumColumns = 2;
    form.getBody().setLayout(layout);

    
    this.aS = createApplicationSection(managedForm, 
                                       Messages.JobApplicationPage_Applicationtitle,
                                       Messages.JobApplicationPage_ApplicationDescription );  
    
    this.applicationTypeAdapter.load();

    
    this.apS = createAdditionalPosixSection(managedForm, 
                                     Messages.JobApplicationPage_additionalPosixApplElementTitle,
                                     Messages.JobApplicationPage_additionalPosixApplDescr);  
    
    

    
    this.pS = createPosixSection(managedForm, 
                                 Messages.JobApplicationPage_PosixApplicationtitle, 
                                  Messages.JobApplicationPage_PosixApplicationDescription);
           
    this.posixApplicationTypeAdapter.load();
       
 
//    form.getBody().setBackground(form.getBody().getDisplay().
//                                 getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
    
    

  }
  
  // This method is used to create individual subsections
  private Composite createSection(final IManagedForm mform, final String title,
                                  final String desc, final int numColumns) {
      
    final ScrolledForm form = mform.getForm();
    FormToolkit toolkit = mform.getToolkit();
                       
    Section section = toolkit.createSection(form.getBody(), 
                                                    ExpandableComposite.TITLE_BAR 
                                                    | Section.TWISTIE | SWT.WRAP);

    
    this.applicationTypeAdapter.attachToApplicationSection( section );
    
    section.clientVerticalSpacing = 10;          
    section.setText(title);
    section.setDescription(desc);
    toolkit.createCompositeSeparator(section);
    Composite client = toolkit.createComposite(section);
               
    GridLayout layout = new GridLayout();
    layout.verticalSpacing = 10;
    layout.marginWidth =  0; 
    layout.marginHeight = 0;
    layout.numColumns = numColumns;
    client.setLayout(layout);
    section.setClient(client);
     
    return client;
  }
  
  /* 
   * Create the Application Section which includes the following:
   *  -Application Name (String)
   *  -Application Version (String)
   *  -Description (String)
   */
  private Composite createApplicationSection(final IManagedForm mform, 
                                             final String title, 
                                             final String desc )
  {
    GridData gd ;
       
    Composite client = createSection(mform, title, desc, 2);
    FormToolkit toolkit = mform.getToolkit();

    //Create Label and Text for Application Name Element
    toolkit.createLabel(client, Messages.JobApplicationPage_ApplicationName); 
    this.txtApplicationName = toolkit.createText(client, "", SWT.BORDER);  //$NON-NLS-1$
    gd = new GridData();
    gd.widthHint = 300;
    this.txtApplicationName.setLayoutData(gd);
    this.applicationTypeAdapter.attachToApplicationName( this.txtApplicationName );
    

    //Create Label and Text for Application Version     
    toolkit.createLabel(client, Messages.JobApplicationPage_ApplicationVersion); 
    this.txtApplicationVersion = toolkit.createText(client, "", SWT.BORDER);  //$NON-NLS-1$
    gd = new GridData();
    gd.widthHint = 300;
    this.txtApplicationVersion.setLayoutData(gd);
    this.applicationTypeAdapter.attachToApplicationVersion( this.txtApplicationVersion );

    //Create Label and Text for Application Description Element    
    toolkit.createLabel(client, Messages.JobApplicationPage_ApplicationDescr); 
    this.txtDescription = toolkit.createText(client, "",  //$NON-NLS-1$
                                                             SWT.BORDER 
                                                             | SWT.V_SCROLL
                                                             | SWT.WRAP); 
    gd = new GridData();
    gd.verticalSpan = 3;
    gd.widthHint = 300;
    gd.heightHint = 100;
    this.txtDescription.setLayoutData(gd);
    this.applicationTypeAdapter
                         .attachToApplicationDescription( this.txtDescription );
       
    return client;
  }
  
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
    //String prefix = posixApplType.eClass().getName().toString();
    
    GridData lblgd;
    GridData gd;
  

    Composite client = createSection(mform, title, desc, 3);
    FormToolkit toolkit = mform.getToolkit();
    gd = new GridData();
    gd.horizontalSpan = 2;
    
    
    /* Posix Name Widget */
    this.lblPosixName = toolkit.createLabel(client, Messages.JobApplicationPage_PosixName); 
    
    this.txtPosixName = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
    this.posixApplicationTypeAdapter.attachPosixApplicationName(this.txtPosixName);
    gd.widthHint = 300;
    this.txtPosixName.setLayoutData(gd);
    
    /* Executable Widget and Button */
    
    gd = new GridData();
    gd.verticalSpan = 3;
    this.lblExecutable = toolkit.createLabel(client, Messages.JobApplicationPage_Executable); 
    this.lblExecutable.setLayoutData( gd ); 
    
    this.txtExecutable = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
    this.posixApplicationTypeAdapter
                        .attachPosixApplicationExecutable(this.txtExecutable);
    
    gd = new GridData();
    gd.widthHint = 300;
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 3;
    gd.horizontalSpan = 1;
    this.txtExecutable.setLayoutData(gd);
    

       
    gd = new GridData();
    gd.widthHint = 40;
    gd.verticalSpan = 2;
    gd.verticalAlignment = GridData.END; 
    
    this.btnAdd = toolkit.createButton(client, Messages.JobApplicationPage_Add, SWT.PUSH); 
    this.btnAdd.setLayoutData( gd);
    gd = new GridData();
    gd.verticalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    gd.widthHint = 40;
    this.btnDel = toolkit.createButton(client, "DEL", SWT.PUSH); 
    this.btnDel.setLayoutData( gd );
    
    
    /* Argument Widget (List) and Button */
    lblgd = new GridData();
    lblgd.verticalSpan = 3;
    this.lblArgument = toolkit.createLabel(client, Messages.JobApplicationPage_Argument);
    this.lblArgument.setLayoutData( lblgd );
    
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 3;
    gd.horizontalSpan = 1;
    gd.widthHint = 300;
    gd.heightHint = 50;
    List lstArgument = new List(client,SWT.MULTI| SWT.BORDER);
    lstArgument.setLayoutData( gd );
     
      
    /*for (int i=0; i<this.posixApplType.getArgument().size(); i++){
       lstArgument.add( this.posixApplType.getArgument().get( i ).toString() );
     }*/
             
    gd = new GridData();
    gd.widthHint = 40;
    gd.verticalSpan = 2;
    gd.verticalAlignment = GridData.END; 
    Button btnTest1 = toolkit.createButton(client, Messages.JobApplicationPage_Add, SWT.BUTTON1); 
    btnTest1.setLayoutData( gd);
    
    
    gd = new GridData();
    gd.verticalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    gd.widthHint = 40;
    this.btnDel = toolkit.createButton(client, "DEL", SWT.PUSH); 
    this.btnDel.setLayoutData( gd );
      
    /* Input Widget and Button */
    lblgd = new GridData();
    lblgd.verticalSpan = 3;
    this.lblInput = toolkit.createLabel(client, Messages.JobApplicationPage_Input);
    this.lblInput.setLayoutData(lblgd);
    
    
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 3;
    gd.horizontalSpan = 1;
    gd.widthHint = 300;
    this.txtInput = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
    this.posixApplicationTypeAdapter.attachPosixApplicationInput( this.txtInput );
    txtInput.setLayoutData(gd);
    
          
    gd = new GridData();
    gd.widthHint = 40;
    gd.verticalSpan = 2;
    gd.verticalAlignment = GridData.END; 
    Button btnTest2 = toolkit.createButton(client, Messages.JobApplicationPage_Add, SWT.BUTTON1); 
    btnTest2.setLayoutData( gd);
    
    
    gd = new GridData();
    gd.verticalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    gd.widthHint = 40;
    this.btnDel = toolkit.createButton(client, "DEL", SWT.PUSH); 
    this.btnDel.setLayoutData( gd );

    /* Output Widget and Button */
    
    lblgd = new GridData();
    lblgd.verticalSpan = 3;
    this.lblOutput = toolkit.createLabel(client, Messages.JobApplicationPage_Output);
    this.lblOutput.setLayoutData( lblgd );
    
    this.txtOutput = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
    this.posixApplicationTypeAdapter.attachPosixApplicationOutput( this.txtOutput );
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 3;
    gd.horizontalSpan = 1;
    gd.widthHint = 300;
    this.txtOutput.setLayoutData( gd );
        
    gd = new GridData();
    gd.widthHint = 40;
    gd.verticalSpan = 2;
    gd.verticalAlignment = GridData.END; 
    Button btnTest3= toolkit.createButton(client, Messages.JobApplicationPage_Add, SWT.BUTTON1);
    btnTest3.setLayoutData( gd);
    
    gd = new GridData();
    gd.verticalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    gd.widthHint = 40;
    this.btnDel = toolkit.createButton(client, "DEL", SWT.PUSH); 
    this.btnDel.setLayoutData( gd );
    
    /* Error Widget and Button */ 
    
    lblgd = new GridData();
    lblgd.verticalSpan = 3;
    this.lblError = toolkit.createLabel(client, Messages.JobApplicationPage_Error); 
    this.lblError.setLayoutData( lblgd );
    
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 3;
    gd.horizontalSpan = 1;
    gd.widthHint = 300;
    this.txtError = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
    this.posixApplicationTypeAdapter.attachPosixApplicationError( this.txtError );
    this.txtError.setLayoutData(gd);
    
    gd = new GridData();
    gd.widthHint = 40;
    gd.verticalSpan = 2;
    gd.verticalAlignment = GridData.END; 
    Button btnError= toolkit.createButton(client, Messages.JobApplicationPage_Add, SWT.BUTTON1);
    btnError.setLayoutData( gd);
    
    gd = new GridData();
    gd.verticalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    gd.widthHint = 40;
    this.btnDel = toolkit.createButton(client, "DEL", SWT.PUSH); 
    this.btnDel.setLayoutData( gd );
    
    /* Environment Widget (List) and Button */   
    
    lblgd = new GridData();
    lblgd.verticalSpan = 3;
    this.lblEnvironment = toolkit.createLabel(client, Messages.JobApplicationPage_Environment); 
    this.lblEnvironment.setLayoutData( lblgd );
    
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 3;
    gd.horizontalSpan = 1;
    gd.widthHint = 300;
    gd.heightHint = 50;
    List lstEnvironment = new List(client,SWT.MULTI| SWT.BORDER);
    lstEnvironment.setLayoutData(gd);
       
    gd = new GridData();
    gd.widthHint = 40;
    gd.verticalSpan = 2;
    gd.verticalAlignment = GridData.END; 
    Button btnTest5 = toolkit.createButton(client, Messages.JobApplicationPage_Add, SWT.BUTTON1); 
    btnTest5.setLayoutData( gd);
    
    gd = new GridData();
    gd.verticalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    gd.widthHint = 40;
    this.btnDel = toolkit.createButton(client, "DEL", SWT.PUSH); 
    this.btnDel.setLayoutData( gd );
       
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
   
   Composite client = createSection(mform, title, desc, 3);
   FormToolkit toolkit = mform.getToolkit();
   
      
   
      
   GridData gd = new GridData();
   gd.horizontalSpan = 2;
   
   
   this.lblWorkingDirectory = toolkit.createButton(client, Messages.JobApplicationPage_WorkingDirectory,SWT.CHECK); 
   
   
   this.txtWorkingDirectory = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   //this.widgetMap.put( prefix +"_workingdirectory", this.txtWorkingDirectory ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtWorkingDirectory.setLayoutData(gd);
   this.txtWorkingDirectory.addFocusListener( new org.eclipse.swt.events.FocusListener(){
     public void focusLost( final org.eclipse.swt.events.FocusEvent e  ) {
      DirectoryNameType directoryName = PosixFactory.eINSTANCE.createDirectoryNameType();
      directoryName.setValue( JobApplicationPage.this.txtWorkingDirectory.getText() );
      JobApplicationPage.this.posixApplType.setWorkingDirectory( directoryName );
     }
     public void focusGained( final org.eclipse.swt.events.FocusEvent e  ) {
     // 
     } 
   });
   
   this.lblWallTimeLimit = toolkit.createButton(client, Messages.JobApplicationPage_WallTimeLimit,SWT.CHECK); 
   
   this.txtWallTimeLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   //this.widgetMap.put( prefix +"_walltimelimit", this.txtWallTimeLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtWallTimeLimit.setLayoutData(gd);
   
   this.txtWallTimeLimit.addFocusListener( new org.eclipse.swt.events.FocusListener(){
     public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
     POSIXApplicationType posAppType = PosixFactory.eINSTANCE.createPOSIXApplicationType(); 
     LimitsType limType = PosixFactory.eINSTANCE.createLimitsType();
     BigInteger intVal = new BigInteger(JobApplicationPage.this.txtWallTimeLimit.getText());
     limType.setValue(intVal);
     posAppType.setWallTimeLimit( limType);
     }

     public void focusGained( final org.eclipse.swt.events.FocusEvent e ) { }
});

   this.lblFileSizeLimit = toolkit.createButton(client, Messages.JobApplicationPage_FileSizeLimit,SWT.CHECK); 
   
   this.txtFileSizeLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   //this.widgetMap.put( prefix +"_filesizelimit", this.txtFileSizeLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtFileSizeLimit.setLayoutData(gd);
   

   this.lblCoreDumpLimit = toolkit.createButton(client, Messages.JobApplicationPage_CoreDumpLimit,SWT.CHECK); 
   
   this.txtCoreDumpLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   //this.widgetMap.put( prefix +"_coredumplimit", this.txtCoreDumpLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtCoreDumpLimit.setLayoutData(gd);

   this.lblDataSegmentLimit = toolkit.createButton(client, Messages.JobApplicationPage_DataSegmentLimit,SWT.CHECK); 
   
   this.txtDataSegmentLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   //this.widgetMap.put( prefix +"_datasegmentlimit", this.txtDataSegmentLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtDataSegmentLimit.setLayoutData(gd);
   
   this.lblLockedMemoryLimit = toolkit.createButton(client, Messages.JobApplicationPage_LockedMemoryLimit,SWT.CHECK); 
   
   this.txtLockedMemoryLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   //this.widgetMap.put( prefix +"_lockedmemorylimit", this.txtLockedMemoryLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtLockedMemoryLimit.setLayoutData(gd);
   
   this.lblMemoryLimit = toolkit.createButton(client, Messages.JobApplicationPage_MemoryLimit,SWT.CHECK); 
   
   this.txtMemoryLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   //this.widgetMap.put( prefix +"_memorylimit", this.txtMemoryLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtMemoryLimit.setLayoutData(gd);
   
   this.lblOpenDescriptorsLimit = toolkit.createButton(client, Messages.JobApplicationPage_OpenDescriptorsLimit,SWT.CHECK); 
   
   this.txtOpenDescriptorsLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
  // this.widgetMap.put( prefix +"_opendescriptorslimit", this.txtOpenDescriptorsLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtOpenDescriptorsLimit.setLayoutData(gd);
   
   this.lblPipeSizeLimit = toolkit.createButton(client, Messages.JobApplicationPage_PipeSizeLimit,SWT.CHECK); 
   
   this.txtPipeSizeLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   //this.widgetMap.put( prefix +"_pipesizelimit", this.txtPipeSizeLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtPipeSizeLimit.setLayoutData(gd);
   
   this.lblStackSizeLimit = toolkit.createButton(client, Messages.JobApplicationPage_StackSizeLimit,SWT.CHECK); 
   
   this.txtStackSizeLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   //this.widgetMap.put( prefix +"_stacksizelimit", this.txtStackSizeLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtStackSizeLimit.setLayoutData(gd);
   
   this.lblCPUTimeLimit = toolkit.createButton(client, Messages.JobApplicationPage_CPUTimeLimit, SWT.CHECK); 
   
   this.txtCPUTimeLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   //this.widgetMap.put( prefix +"_cputimelimit", this.txtCPUTimeLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtCPUTimeLimit.setLayoutData(gd);
   
   this.lblProcessCountLimit = toolkit.createButton(client, Messages.JobApplicationPage_ProcessCountLimit, SWT.CHECK); 
   
   this.txtProcessCountLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   //this.widgetMap.put( prefix +"_processcountlimit", this.txtProcessCountLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtProcessCountLimit.setLayoutData(gd);
   
   this.lblVirtualMemoryLimit = toolkit.createButton(client, Messages.JobApplicationPage_VirtualMemoryLimit, SWT.CHECK); 
   
   this.txtVirtualMemoryLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   //this.widgetMap.put( prefix +"_virtualmemorylimit", this.txtVirtualMemoryLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtVirtualMemoryLimit.setLayoutData(gd);
   
   this.lblThreadCountLimit = toolkit.createButton(client, Messages.JobApplicationPage_ThreadCountLimit, SWT.CHECK); 
   
   this.txtThreadCountLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   //this.widgetMap.put( prefix +"_threadcountlimit", this.txtThreadCountLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtThreadCountLimit.setLayoutData(gd);

   
   this.lblUserName = toolkit.createButton(client, Messages.JobApplicationPage_UserName, SWT.CHECK); 
   
   this.txtUserName = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   //this.widgetMap.put( prefix +"_username", this.txtUserName ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtUserName.setLayoutData(gd);
   this.txtUserName.addFocusListener( new org.eclipse.swt.events.FocusListener(){
     public void focusLost( final org.eclipse.swt.events.FocusEvent e  ) {
      UserNameType userName = PosixFactory.eINSTANCE.createUserNameType();
      userName.setValue( JobApplicationPage.this.txtUserName.getText() );
      JobApplicationPage.this.posixApplType.setUserName( userName );
     }
     public void focusGained( final org.eclipse.swt.events.FocusEvent e  ) {
     // 
     } 
   });

   
   this.lblGroupName = toolkit.createButton(client, Messages.JobApplicationPage_GroupName, SWT.CHECK); 
   
   this.txtGroupName = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   //this.widgetMap.put( prefix +"_groupname", this.txtGroupName ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtGroupName.setLayoutData(gd);
   this.txtGroupName.addFocusListener( new org.eclipse.swt.events.FocusListener(){
     public void focusLost( final org.eclipse.swt.events.FocusEvent e  ) {
      GroupNameType groupName = PosixFactory.eINSTANCE.createGroupNameType();
      groupName.setValue( JobApplicationPage.this.txtGroupName.getText() );
      JobApplicationPage.this.posixApplType.setGroupName( groupName );
     }
     public void focusGained( final org.eclipse.swt.events.FocusEvent e  ) {
     // 
     } 
   });

   
   
   return client;
   }
  
} // End Class
