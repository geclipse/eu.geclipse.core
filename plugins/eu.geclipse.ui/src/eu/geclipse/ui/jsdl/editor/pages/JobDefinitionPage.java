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
import java.util.Collection;
import java.util.Iterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
import eu.geclipse.jsdl.JobDefinitionType;
import eu.geclipse.jsdl.JobDescriptionType;
import eu.geclipse.jsdl.JobIdentificationType;

public class JobDefinitionPage extends FormPage {
  
  
  Text txtId = null;
  Text txtDescription = null;
  
  Composite jobDefComposite;
  Composite jobIdentComposite;

  JobDefinitionType jobDefType = null;
  JobDescriptionType jobDescrType = null;
  JobIdentificationType jobIdentType = null;
  EditingDomain editingDomain;
 
  Collection savedResources = new ArrayList ();
    
  
  public JobDefinitionPage( final FormEditor editor, 
                            final ArrayList<EObject> list,
                            final EditingDomain editDomain) {   
    
           super(editor, Messages.JobDefinitionPage_jobDefinitionId, 
                                Messages.JobDefinitionPage_JobDefinitionTitle);
           
           this.editingDomain = editDomain;
           breakTypes(list);
                    
           
           }
  
  private void breakTypes(final ArrayList<EObject> list){
   Iterator<EObject> it = list.iterator();
   EObject type;
   
   
   while (it.hasNext()){
    type = (EObject) it.next();
    
    if (type instanceof JobDefinitionType){
      this.jobDefType = (JobDefinitionType) type;
    }else if (type instanceof JobDescriptionType){
      this.jobDescrType = (JobDescriptionType) type;  
    }else if (type instanceof JobIdentificationType){
      this.jobIdentType = (JobIdentificationType) type;  
    } 
    
   } 
   
   }

  
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
    layout.maxNumColumns = 1;
    layout.minNumColumns = 1;    
    form.getBody().setLayout(layout);
      

        
   this.jobDefComposite = createJobDefinitionSection(managedForm,
                                 Messages.JobDefinitionPage_JobDefinitionTitle, 
                                 Messages.JobDefinitionPage_JobDefinitionDescr);  
           
   

    this.jobIdentComposite = createJobIdentificationSection(managedForm, 
                             Messages.JobDefinitionPage_JobIdentificationTitle, 
                             Messages.JobDefinitionPage_JobIdentificationDescr); 

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
   layout.verticalSpacing = 5;
   layout.marginTop = 10;
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
    GridData gd = new GridData();
    //gd.horizontalSpan = 2;
    
    Label lblJobId = toolkit.createLabel(client,
                                    Messages.JobDefinitionPage_JobDefinitionId);
    
    this.txtId = toolkit.createText(client, this.jobDefType.getId(), SWT.BORDER);
    this.txtId.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        JobDefinitionPage.this.jobDefType.setId(JobDefinitionPage.this.
                                                txtId.getText());
      }
      public void focusGained(final org.eclipse.swt.events.FocusEvent e ) {
        //
      }
    } );
  
    gd = new GridData();
    gd.widthHint = 300;
    this.txtId.setLayoutData(gd);

     
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
   
    Composite client = createSection(mform, title, desc, 2);
    FormToolkit toolkit = mform.getToolkit();
       

    GridData gd ;
    GridData lblgd;
    
    lblgd = new GridData();
    
    gd = new GridData();
    gd.widthHint = 300;
   
      
    toolkit.createLabel(client, Messages.JobDefinitionPage_JobName);
    
    Text txtJobName = toolkit.createText(client, this.jobIdentType.getJobName(), SWT.BORDER); 
    txtJobName.setLayoutData(gd);
    
    
    Label lblJobDescripiton = toolkit.createLabel (client,
                                           Messages.JobDefinitionPage_JobDescr);
    
    this.txtDescription = toolkit.createText(client, 
                                             this.jobIdentType.getDescription(),
                                             SWT.BORDER | SWT.MULTI 
                                             | SWT.V_SCROLL | SWT.WRAP); 
    
    this.txtDescription.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        JobDefinitionPage.this.jobIdentType.setDescription( JobDefinitionPage.
                                                 this.txtDescription.getText());
        }
      public void focusGained( final org.eclipse.swt.events.FocusEvent e ) {
        //
      }
    } );

    gd = new GridData();
    gd.verticalSpan = 3;
    gd.widthHint = 300;
    gd.heightHint = 150;
    this.txtDescription.setLayoutData( gd );
    
    lblgd = new GridData();
    lblgd.verticalSpan = 3;
    lblJobDescripiton.setLayoutData (lblgd);
    
    Label lblJobAnnotation = toolkit.createLabel(client,
                                      Messages.JobDefinitionPage_JobAnnotation);   
   
    lblgd = new GridData();
    lblgd.verticalSpan = 3;
    lblJobAnnotation.setLayoutData( lblgd );
    
    
    List lstJobAnnotation = new List(client,SWT.MULTI| SWT.BORDER);      
    lstJobAnnotation.setLayoutData( gd );
    
    gd = new GridData();
    gd.widthHint = 300;
    gd.heightHint = 150;
 
 
    for (int i=0; i<this.jobIdentType.getJobAnnotation().size(); i++){
      lstJobAnnotation.add( this.jobIdentType.getJobAnnotation().get( i ).toString() );
    }
    
              
    Label lblJobProject = toolkit.createLabel(client,
                                         Messages.JobDefinitionPage_JobProject);
    
    lblgd = new GridData();
    lblgd.verticalSpan = 3;
    lblJobProject.setLayoutData( lblgd );
    
    List lstJobProject = new List(client,SWT.MULTI| SWT.BORDER);  
    gd = new GridData();
    gd.widthHint = 300;
    gd.heightHint = 150;
    lstJobProject.setLayoutData( gd );
 
    for (int i=0; i<this.jobIdentType.getJobProject().size(); i++){
      lstJobProject.add( this.jobIdentType.getJobProject().get( i ).toString() );
    }
  
    return client;
     
  }
  
}
    
  
  