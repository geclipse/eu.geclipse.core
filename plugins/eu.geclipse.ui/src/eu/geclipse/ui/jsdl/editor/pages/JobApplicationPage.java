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
  *      - Emilia Stamou (emstamou@cs.ucy.ac.cy)
  *****************************************************************************/

package eu.geclipse.ui.jsdl.editor.pages;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
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
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import eu.geclipse.jsdl.ApplicationType;
import eu.geclipse.jsdl.posix.POSIXApplicationType;

public class JobApplicationPage extends FormPage {
  
  Composite aS = null;
  Composite pS = null;
  Composite apS = null ;
  ApplicationType applType = null;
  Text txtApplicationVersion = null;
  Text txtDescription = null;
  Hashtable< String, Text > widgetMap = new Hashtable< String, Text >();
  private Button btnTest;
  private Text txtExecutable;
  private Text txtPosixName;
  private Text txtOutput;
  private POSIXApplicationType posixApplType = null;
  private Text txtApplicationName = null;
  private Text txtWallTimeLimit;
  private Text txtFileSizeLimit;
  private Text txtCoreDumpLimit;
  private Text txtDataSegmentLimit;
  private Text txtLockedMemoryLimit;
  private Text txtMemoryLimit;
  private Text txtOpenDescriptorsLimit;
  private Text txtPipeSizeLimit;
  private Text txtStackSizeLimit;
  private Text txtCPUTimeLimit;
  private Text txtProcessCountLimit;
  private Text txtVirtualMemoryLimit;
  private Text txtThreadCountLimit;
  private Text txtUserName;
  private Text txtGroupName;
  private Text txtWorkingDirectory;
  
 
  // Constructor
  public JobApplicationPage( final FormEditor editor, 
                             final ArrayList<EObject> list,
                             final ArrayList<EObject> plist 
                             ) {
    
    super(editor,Messages.JobApplicationPage_pageId , 
          Messages.JobApplicationPage_PageTitle);
    
    breakTypes(list);
   
    breakpTypes(plist);
   
    }
  
  private void breakTypes(final ArrayList<EObject> list){
    Iterator<EObject> it = list.iterator();
    EObject type;
    
    while (it.hasNext()){
     type = (EObject) it.next();
    
   if (type instanceof ApplicationType){
       this.applType = (ApplicationType) type;
   }
   }
  }
  
  private void breakpTypes(final ArrayList<EObject> plist){
    Iterator<EObject> pit = plist.iterator();
    EObject ptype;
    
    while (pit.hasNext()){
      ptype = (EObject) pit.next();
    
    if (ptype instanceof POSIXApplicationType){
       this.posixApplType = (POSIXApplicationType) ptype;
    }
   }
  }
    
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
                                       Messages.JobApplicationPage_ApplicationDescription,
                                       this.applType);  
    
    populateAttributes(this.applType);

    
    this.apS = createAdditionalPosixSection(managedForm, 
                                     Messages.JobApplicationPage_additionalPosixApplElementTitle,
                                     Messages.JobApplicationPage_additionalPosixApplDescr,
                                     this.posixApplType);  

    
    this.pS = createPosixSection(managedForm, 
                                 Messages.JobApplicationPage_PosixApplicationtitle, 
                                  Messages.JobApplicationPage_PosixApplicationDescription,
                                  this.posixApplType);
           
       
    populateAttributes(this.posixApplType);
 
//    form.getBody().setBackground(form.getBody().getDisplay().
//                                 getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
    
    

  }
  
   
  private Composite createSection(final IManagedForm mform, final String title,
                                  final String desc, final int numColumns) {
      
            final ScrolledForm form = mform.getForm();
            FormToolkit toolkit = mform.getToolkit();
                        
            Section section = toolkit.createSection(form.getBody(), 
                                                    ExpandableComposite.TITLE_BAR 
                                                    | Section.DESCRIPTION | SWT.WRAP);

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
  
  
  private Composite createApplicationSection(final IManagedForm mform, 
                                               final String title, 
                                               final String desc,
                                               final ApplicationType object)
   {
    
    String prefix = object.eClass().getName().toString();

    GridData gd ;
    
   
    Composite client = createSection(mform, title, desc, 2);
    FormToolkit toolkit = mform.getToolkit();

    toolkit.createLabel(client, Messages.JobApplicationPage_ApplicationName); 
    this.txtApplicationName = toolkit.createText(client, "", SWT.BORDER);  //$NON-NLS-1$
    gd = new GridData();
    gd.widthHint = 300;
    this.txtApplicationName.setLayoutData(gd);
    this.widgetMap.put( prefix + "_applicationName", this.txtApplicationName ); //$NON-NLS-1$
    this.txtApplicationName.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        JobApplicationPage.this.applType.setApplicationName( JobApplicationPage.
                                                 this.txtApplicationName.getText());
        }
      public void focusGained( final org.eclipse.swt.events.FocusEvent e ) {
        //
      }
    } );

         
    toolkit.createLabel(client, Messages.JobApplicationPage_ApplicationVersion); 
    this.txtApplicationVersion = toolkit.createText(client, "", SWT.BORDER);  //$NON-NLS-1$
    gd = new GridData();
    gd.widthHint = 300;
    this.txtApplicationVersion.setLayoutData(gd);
    this.widgetMap.put( prefix +"_applicationVersion", this.txtApplicationVersion ); //$NON-NLS-1$
    this.txtApplicationVersion.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        JobApplicationPage.this.applType.setApplicationVersion( JobApplicationPage.
                                                 this.txtApplicationVersion.getText());
        }
      public void focusGained( final org.eclipse.swt.events.FocusEvent e ) {
        //
      }
    } );



        
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
    this.widgetMap.put( prefix +"_description", this.txtDescription ); //$NON-NLS-1$
    this.txtDescription.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        JobApplicationPage.this.applType.setDescription( JobApplicationPage.
                                                 this.txtDescription.getText());
        }
      public void focusGained( final org.eclipse.swt.events.FocusEvent e ) {
        //
      }
    } );

        
    return client;
    }

  
  private Composite createPosixSection(final IManagedForm mform, 
                                          final String title, 
                                          final String desc,
                                          final POSIXApplicationType object)
     {
    String prefix = object.eClass().getName().toString();
    
    GridData lblgd;
    GridData gd;
  

    Composite client = createSection(mform, title, desc, 3);
    FormToolkit toolkit = mform.getToolkit();
    gd = new GridData();
    gd.horizontalSpan = 2;
    
    /* Posix Name Widget */
    Label lblPosixName = toolkit.createLabel(client, Messages.JobApplicationPage_PosixName); 
    
    this.txtPosixName = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
    this.widgetMap.put( prefix +"_name", this.txtPosixName ); //$NON-NLS-1$
    gd.widthHint = 300;
    this.txtPosixName.setLayoutData(gd);
    /* Executable Widget and Button */
    
    gd = new GridData();
    Label lblExecutable = toolkit.createLabel(client, Messages.JobApplicationPage_Executable); 
        
    this.txtExecutable = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
    this.widgetMap.put( prefix +"_applicationName", this.txtExecutable ); //$NON-NLS-1$
    gd = new GridData();
    gd.widthHint = 300;
    this.txtExecutable.setLayoutData(gd);
       
    gd = new GridData();
    gd.widthHint = 40;
        
    this.btnTest = toolkit.createButton(client, Messages.JobApplicationPage_Add, SWT.PUSH); 
        
    this.btnTest.setLayoutData( gd);
    
    
    /* Argument Widget (List) and Button */
    lblgd = new GridData();
    gd = new GridData();
    gd.widthHint = 300;
    gd.heightHint = 50;
    Label lblArgument = toolkit.createLabel(client, Messages.JobApplicationPage_Argument);
    lblArgument.setLayoutData( lblgd );
    List lstArgument = new List(client,SWT.MULTI| SWT.BORDER);
    lstArgument.setLayoutData( gd );
     
      
    /*for (int i=0; i<this.posixApplType.getArgument().size(); i++){
       lstArgument.add( this.posixApplType.getArgument().get( i ).toString() );
     }*/
             
    gd = new GridData();
    gd.widthHint = 40;
    Button btnTest1 = toolkit.createButton(client, Messages.JobApplicationPage_Add, SWT.BUTTON1); 
    btnTest1.setLayoutData( gd);
      
    /* Input Widget and Button */
    lblgd = new GridData();
    Label lblInput = toolkit.createLabel(client, Messages.JobApplicationPage_Input);
    lblInput.setLayoutData(lblgd);
    
    gd = new GridData();
    gd.widthHint = 300;
    Text txtInput = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
    txtInput.setLayoutData(gd);
    
          
    gd = new GridData();
    gd.widthHint = 40;
    Button btnTest2 = toolkit.createButton(client, Messages.JobApplicationPage_Add, SWT.BUTTON1); 
    btnTest2.setLayoutData( gd);

    /* Output Widget and Button */
    
    lblgd = new GridData();
    Label lblOutput = toolkit.createLabel(client, Messages.JobApplicationPage_Output);
    lblOutput.setLayoutData( lblgd );
    
    this.txtOutput = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
    this.txtOutput.setLayoutData(gd);
      
    gd = new GridData();
    gd.widthHint = 300;
    this.txtOutput.setLayoutData( gd );
        
    gd = new GridData();
    gd.widthHint = 40;
    Button btnTest3= toolkit.createButton(client, Messages.JobApplicationPage_Add, SWT.BUTTON1);
    btnTest3.setLayoutData( gd);
    
    /* Error Widget and Button */ 
    
    lblgd = new GridData();
    Label lblError = toolkit.createLabel(client, Messages.JobApplicationPage_Error); 
    lblError.setLayoutData( lblgd );
    
    gd = new GridData();
    gd.widthHint = 300;
    Text txtError = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
    txtError.setLayoutData(gd);
    
    gd = new GridData();
    gd.widthHint = 40;
    Button btnError= toolkit.createButton(client, Messages.JobApplicationPage_Add, SWT.BUTTON1);
    btnError.setLayoutData( gd);
    
    /* Environment Widget (List) and Button */   
    
    lblgd = new GridData();
    Label lblEnvironment = toolkit.createLabel(client, Messages.JobApplicationPage_Environment); 
    lblEnvironment.setLayoutData( lblgd );
    
    gd = new GridData();
    gd.widthHint = 300;
    gd.heightHint = 50;
    List lstEnvironment = new List(client,SWT.MULTI| SWT.BORDER);
    lstEnvironment.setLayoutData(gd);
       
    gd = new GridData();
    gd.widthHint = 40;
    Button btnTest5 = toolkit.createButton(client, Messages.JobApplicationPage_Add, SWT.BUTTON1); 
    btnTest5.setLayoutData( gd);
       
    return client;
     }
  

 
  
  private Composite createAdditionalPosixSection(final IManagedForm mform, 
                                             final String title, 
                                             final String desc,
                                             final POSIXApplicationType object)
  {
          
   String prefix = object.eClass().getName().toString();

   Composite client = createSection(mform, title, desc, 3);
   FormToolkit toolkit = mform.getToolkit();
   GridData gd = new GridData();
   gd.horizontalSpan = 2;
   
   
   Label lblWorkingDirectory = toolkit.createLabel(client, Messages.JobApplicationPage_WorkingDirectory); 
   
   this.txtWorkingDirectory = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   this.widgetMap.put( prefix +"_workingdirectory", this.txtWorkingDirectory ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtWorkingDirectory.setLayoutData(gd);

   Label lblWallTimeLimit = toolkit.createLabel(client, Messages.JobApplicationPage_WallTimeLimit); 
   
   this.txtWallTimeLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   this.widgetMap.put( prefix +"_walltimelimit", this.txtWallTimeLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtWallTimeLimit.setLayoutData(gd);


   Label lblFileSizeLimit = toolkit.createLabel(client, Messages.JobApplicationPage_FileSizeLimit); 
   
   this.txtFileSizeLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   this.widgetMap.put( prefix +"_filesizelimit", this.txtFileSizeLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtFileSizeLimit.setLayoutData(gd);
   

   Label lblCoreDumpLimit = toolkit.createLabel(client, Messages.JobApplicationPage_CoreDumpLimit); 
   
   this.txtCoreDumpLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   this.widgetMap.put( prefix +"_coredumplimit", this.txtCoreDumpLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtCoreDumpLimit.setLayoutData(gd);

   Label lblDataSegmentLimit = toolkit.createLabel(client, Messages.JobApplicationPage_DataSegmentLimit); 
   
   this.txtDataSegmentLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   this.widgetMap.put( prefix +"_datasegmentlimit", this.txtDataSegmentLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtDataSegmentLimit.setLayoutData(gd);
   
   Label lblLockedMemoryLimit = toolkit.createLabel(client, Messages.JobApplicationPage_LockedMemoryLimit); 
   
   this.txtLockedMemoryLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   this.widgetMap.put( prefix +"_lockedmemorylimit", this.txtLockedMemoryLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtLockedMemoryLimit.setLayoutData(gd);
   
   Label lblMemoryLimit = toolkit.createLabel(client, Messages.JobApplicationPage_MemoryLimit); 
   
   this.txtMemoryLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   this.widgetMap.put( prefix +"_memorylimit", this.txtMemoryLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtMemoryLimit.setLayoutData(gd);
   
   Label lblOpenDescriptorsLimit = toolkit.createLabel(client, Messages.JobApplicationPage_OpenDescriptorsLimit); 
   
   this.txtOpenDescriptorsLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   this.widgetMap.put( prefix +"_opendescriptorslimit", this.txtOpenDescriptorsLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtOpenDescriptorsLimit.setLayoutData(gd);
   
   Label lblPipeSizeLimit = toolkit.createLabel(client, Messages.JobApplicationPage_PipeSizeLimit); 
   
   this.txtPipeSizeLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   this.widgetMap.put( prefix +"_pipesizelimit", this.txtPipeSizeLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtPipeSizeLimit.setLayoutData(gd);
   
   Label lblStackSizeLimit = toolkit.createLabel(client, Messages.JobApplicationPage_StackSizeLimit); 
   
   this.txtStackSizeLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   this.widgetMap.put( prefix +"_stacksizelimit", this.txtStackSizeLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtStackSizeLimit.setLayoutData(gd);
   
   Label lblCPUTimeLimit = toolkit.createLabel(client, Messages.JobApplicationPage_CPUTimeLimit); 
   
   this.txtCPUTimeLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   this.widgetMap.put( prefix +"_cputimelimit", this.txtCPUTimeLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtCPUTimeLimit.setLayoutData(gd);
   
   Label lblProcessCountLimit = toolkit.createLabel(client, Messages.JobApplicationPage_ProcessCountLimit); 
   
   this.txtProcessCountLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   this.widgetMap.put( prefix +"_processcountlimit", this.txtProcessCountLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtProcessCountLimit.setLayoutData(gd);
   
   Label lblVirtualMemoryLimit = toolkit.createLabel(client, Messages.JobApplicationPage_VirtualMemoryLimit); 
   
   this.txtVirtualMemoryLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   this.widgetMap.put( prefix +"_virtualmemorylimit", this.txtVirtualMemoryLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtVirtualMemoryLimit.setLayoutData(gd);
   
   Label lblThreadCountLimit = toolkit.createLabel(client, Messages.JobApplicationPage_ThreadCountLimit); 
   
   this.txtThreadCountLimit = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   this.widgetMap.put( prefix +"_threadcountlimit", this.txtThreadCountLimit ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtThreadCountLimit.setLayoutData(gd);
   
   Label lblUserName = toolkit.createLabel(client, Messages.JobApplicationPage_UserName); 
   
   this.txtUserName = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   this.widgetMap.put( prefix +"_username", this.txtUserName ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtUserName.setLayoutData(gd);
   
   Label lblGroupName = toolkit.createLabel(client, Messages.JobApplicationPage_GroupName); 
   
   this.txtGroupName = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
   this.widgetMap.put( prefix +"_groupname", this.txtGroupName ); //$NON-NLS-1$
   gd.widthHint = 300;
   this.txtGroupName.setLayoutData(gd);
   
   
   return client;
   }
  
  

  /* 
     This method populates attribute data to widgets on the form.
     Takes as argument an EObject and goes through it's attributes.
     
    */
  private void populateAttributes (final EObject object)
  {
    
    Text widgetName = null;
    //EDataType dataType = null;
    
    // Test if eObject is not empty.
    if(object != null) {
      EClass eClass = object.eClass();
             
        
      for (Iterator iter = eClass.getEAllAttributes().iterator(); iter.hasNext();) {      
        EAttribute attribute = (EAttribute) iter.next();
        //get Attribute Data Type.
        //dataType = attribute.getEAttributeType();
        
        //Get Prefix
        String prefix = attribute.getContainerClass().getSimpleName().toString();
        
        //Get Attribute Name.
        String attributeName = prefix + "_" + attribute.getName(); //$NON-NLS-1$
                       
        //Get Attribute Value.
        Object value = object.eGet( attribute );
      
        //Check if Attribute has any value
        if (object.eIsSet( attribute )){
           widgetName = this.widgetMap.get( attributeName );
         
         //FIXME - any check should be removed..check cause of it.
         if (attribute.getName().toString() != "any"){ //$NON-NLS-1$
           widgetName.setText(value.toString());
         }
                   
        } 
      }//for iter
    
    }// null check
  }//void

  
}
