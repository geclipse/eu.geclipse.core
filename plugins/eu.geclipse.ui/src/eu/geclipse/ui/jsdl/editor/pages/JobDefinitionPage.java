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
import eu.geclipse.jsdl.adapters.jsdl.JobDefinitionTypeAdapter;
import eu.geclipse.jsdl.adapters.jsdl.JobIdentificationTypeAdapter;
import eu.geclipse.ui.internal.Activator;

public class JobDefinitionPage extends FormPage {

  Text txtId = null;
  Text txtDescription = null;
  Text txtJobName = null;
  
  List lstJobProject = null;
  List lstJobAnnotation = null;
  
  Label lblJobId = null;
  Label lblJobDescripiton = null;
  Label lblJobAnnotation = null;
  Label lblJobProject = null;
  
  Button btnADD = null;
  Button btnDel = null;
  Button btnTest = null;
  
  Composite jobDefComposite = null;
  Composite jobIdentComposite = null;
  
   
  Hashtable< String, Text > widgetFeaturesMap = new Hashtable< String, Text >();
  
  
  private JobDefinitionTypeAdapter jobDefinitionTypeAdapter ;
  private JobIdentificationTypeAdapter jobIdentificationTypeAdapter;
    
  private boolean contentRefreshed = false;
  
  public JobDefinitionPage( final FormEditor editor) {
   
    
    super(editor, Messages.JobDefinitionPage_jobDefinitionId, 
                                Messages.JobDefinitionPage_JobDefinitionTitle);
    
  }
  
  public void setActive(final boolean active) {
    
    if (active){
      if (isContentRefreshed()){    
        this.jobDefinitionTypeAdapter.load();
        this.jobIdentificationTypeAdapter.load();
      }//endif isContentRefreshed
    } // endif active
  }
  
  // Checks if the content of the model for this page is refreshed.
  private boolean isContentRefreshed(){          
    return this.contentRefreshed;
     }
  
  
  public void setPageContent(final EObject rootJsdlElement, 
                             final boolean refreshStatus){

   if (refreshStatus) {
      this.contentRefreshed = true;
      this.jobDefinitionTypeAdapter.setContent( rootJsdlElement );
      this.jobIdentificationTypeAdapter.setContent( rootJsdlElement );
    }
   else{
      this.jobDefinitionTypeAdapter = new JobDefinitionTypeAdapter(rootJsdlElement);
      this.jobIdentificationTypeAdapter = new JobIdentificationTypeAdapter(rootJsdlElement);
   }
          
  } // End void getPageContent()   

 

  @Override
  
  // This method is used to create the Forms content by
  // creating the form layout and then creating the form
  // sub sections.
  protected void createFormContent(final IManagedForm managedForm) {
    
        
    ScrolledForm form = managedForm.getForm();
    form.setText(Messages.JobDefinitionPage_JobDefinitionPageTitle); 
    
  
    ColumnLayout layout = new ColumnLayout();
    
    // Set Max and Min Columns to be 1...
    // I.e only one collumn is allowed
    layout.leftMargin = 10;
    layout.rightMargin = 10;
    layout.maxNumColumns = 1;
    layout.minNumColumns = 1;    
    form.getBody().setLayout(layout);
      

        
   this.jobDefComposite = createJobDefinitionSection(managedForm,
                                 Messages.JobDefinitionPage_JobDefinitionTitle, 
                                 Messages.JobDefinitionPage_JobDefinitionDescr);  
         
     
     jobDefinitionTypeAdapter.load();
 
     

    this.jobIdentComposite = createJobIdentificationSection(managedForm, 
                             Messages.JobDefinitionPage_JobIdentificationTitle, 
                             Messages.JobDefinitionPage_JobIdentificationDescr);
    
    jobIdentificationTypeAdapter.load();
    
    form.setBackgroundImage(Activator.getDefault().
                            getImageRegistry().get( "formsbackground" ));

  }
  
  
  // This method is used to create individual subsections
  private Composite createSection(final IManagedForm mform, final String title,
                                final String desc, final int numColumns) {
    
   final ScrolledForm form = mform.getForm();
   FormToolkit toolkit = mform.getToolkit();
       
                   
   Section section = toolkit.createSection(form.getBody(), 
                                                  ExpandableComposite.TITLE_BAR 
                                                  | Section.DESCRIPTION 
                                                  | SWT.WRAP);

   section.clientVerticalSpacing = 5;   
   section.setText(title);
   section.setDescription(desc);
   toolkit.createCompositeSeparator(section);
   Composite client = toolkit.createComposite(section);
          
   GridLayout layout = new GridLayout();
   layout.verticalSpacing = 8;
   layout.marginTop = 10;
   layout.marginBottom = 10;
   layout.marginWidth = 0;
   layout.marginHeight = 0;
   layout.numColumns = numColumns;
   client.setLayout(layout);
   
   section.setClient(client);
      
   return client;
   
   }
  
  /* 
   * Create the Job Definition Section which includes the following:
   *  -Job ID (String)
   */
  private Composite createJobDefinitionSection(final IManagedForm mform, 
                                          final String title, final String desc)
  {
    
    Composite client = createSection(mform, title, desc, 2);
    FormToolkit toolkit = mform.getToolkit();
    GridData gd;
  
    this.lblJobId = toolkit.createLabel(client,
                                    Messages.JobDefinitionPage_JobDefinitionId);
    
    this.txtId = toolkit.createText(client,"", SWT.NONE);    
    this.jobDefinitionTypeAdapter.attachID( this.txtId );
  
    gd = new GridData();
    gd.widthHint = 300;
    this.txtId.setLayoutData(gd);
     
    //Paint Flat Borders
    
    toolkit.paintBordersFor( client );
    
    return client;
}
  
  
  /* 
   * Create the Job Identification Section which includes the following:
   *  -Job Name (String)
   *  -Job Description (String)
   *  -Job Annotation (EList)
   *  -Job Project (EList)
   */
  private Composite createJobIdentificationSection(final IManagedForm mform, 
                                                   final String title, 
                                                   final String desc)
  {
    Composite client = createSection(mform, title, desc, 4);
    FormToolkit toolkit = mform.getToolkit();
       
    
    GridData gd ;
    GridData lblgd;
    
    
    /* ================================ Job Name ============================ */
    
    toolkit.createLabel(client, Messages.JobDefinitionPage_JobName);
    gd = new GridData();
    gd.grabExcessHorizontalSpace = true;
    gd.verticalAlignment = GridData.CENTER;
    gd.horizontalSpan = 3;
    gd.widthHint = 300;
    this.txtJobName = toolkit.createText(client,"", SWT.NONE); 
    this.jobIdentificationTypeAdapter.attachToJobName( this.txtJobName );

    txtJobName.setLayoutData(gd);
    
    
    /* ========================= Job Description ============================ */
    
    this.lblJobDescripiton = toolkit.createLabel (client,
                                           Messages.JobDefinitionPage_JobDescr);
    lblgd = new GridData();
    lblgd.verticalSpan = 1;
    lblJobDescripiton.setLayoutData (lblgd);
    
    this.txtDescription = toolkit.createText(client,"", SWT.MULTI 
                                                   | SWT.V_SCROLL | SWT.WRAP);
    
    this.jobIdentificationTypeAdapter.attachToJobDescription( this.txtDescription );
    
  
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 1;
    gd.horizontalSpan = 3;
    gd.widthHint = 285;
    gd.heightHint = 150;
    this.txtDescription.setLayoutData(gd);
 
    
    /* ========================= Job Annotation ============================= */
    
    this.lblJobAnnotation = toolkit.createLabel(client,
                                      Messages.JobDefinitionPage_JobAnnotation); 
    lblgd = new GridData();
    lblgd.horizontalSpan = 1;
    lblgd.verticalSpan = 2;    
    lblJobAnnotation.setLayoutData( lblgd );
   
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    //gd.horizontalAlignment = GridData.FILL;
    gd.horizontalSpan = 1;
    gd.verticalSpan = 2;    
    gd.widthHint = 235;
    gd.heightHint = 150;
    this.lstJobAnnotation = new List(client, SWT.None);
    this.lstJobAnnotation.setData( FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER );    
    this.jobIdentificationTypeAdapter.attachToJobAnnotation( this.lstJobAnnotation);
    lstJobAnnotation.setLayoutData( gd );

    //Create Button ADD
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    this.btnADD = toolkit.createButton(client, Messages.JsdlEditor_AddButton, SWT.PUSH);
    this.btnADD.setLayoutData( gd );

    //Create Button DEL

    gd = new GridData();    
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    gd.verticalAlignment = GridData.BEGINNING;         
    this.btnDel = toolkit.createButton(client, Messages.JsdlEditor_RemoveButton, SWT.PUSH); 
    this.btnDel.setLayoutData( gd );
    
    /* ============================= Job Project =============================*/
    
     this.lblJobProject = toolkit.createLabel(client,
                                       Messages.JobDefinitionPage_JobProject);
     
     lblgd = new GridData();
     lblgd.verticalSpan = 2;
     lblgd.horizontalSpan = 1;
     lblJobProject.setLayoutData( lblgd );
     
     gd = new GridData();
     gd.verticalSpan = 2;
     gd.horizontalSpan = 1;
     gd.widthHint = 235;
     gd.heightHint = 150;
     this.lstJobProject = new List(client,  SWT.NONE);
     this.lstJobProject.setData( FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER ); 
     this.jobIdentificationTypeAdapter.attachToJobProject(this.lstJobProject );
     this.lstJobProject.setLayoutData( gd );
     
//   Create Button ADD
     gd = new GridData();
     gd.horizontalSpan = 2;
     gd.verticalSpan = 1;
     gd.widthHint = 60;     
     this.btnDel = toolkit.createButton(client, Messages.JsdlEditor_AddButton, SWT.PUSH); 
     this.btnDel.setLayoutData( gd );
     
     //Create Button DEL
     gd = new GridData();
     gd.horizontalSpan = 2;
     gd.widthHint = 60;
     gd.verticalSpan = 1;
     gd.verticalAlignment = GridData.BEGINNING;
     this.btnDel = toolkit.createButton(client, Messages.JsdlEditor_RemoveButton, SWT.PUSH); 
     this.btnDel.setLayoutData( gd );
 
     toolkit.paintBordersFor( client );
     
    return client;
     
  }
  
  

 }
  