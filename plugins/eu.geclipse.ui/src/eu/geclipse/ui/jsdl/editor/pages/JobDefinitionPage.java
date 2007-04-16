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

import java.util.Hashtable;
import java.util.Iterator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
import eu.geclipse.jsdl.JobDefinitionType;
import eu.geclipse.jsdl.JobDescriptionType;
import eu.geclipse.jsdl.JobIdentificationType;
import eu.geclipse.jsdl.JsdlFactory;

public class JobDefinitionPage extends FormPage {

  Text txtId = null;
  Text txtDescription = null;
  Text txtJobName = null;
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
  
  
  JobDefinitionType jobDefType = JsdlFactory.eINSTANCE.createJobDefinitionType();
  JobDescriptionType jobDescrType = JsdlFactory.eINSTANCE.createJobDescriptionType();
  JobIdentificationType jobIdentType = JsdlFactory.eINSTANCE.createJobIdentificationType();
 
  private boolean contentRefreshed = false;
  
  public JobDefinitionPage( final FormEditor editor/*, 
                            final ArrayList<EObject> list*/) {
   
    
    super(editor, Messages.JobDefinitionPage_jobDefinitionId, 
                                Messages.JobDefinitionPage_JobDefinitionTitle);
    
  }
  
  public void setActive(final boolean active) {
    
    if (isContentRefreshed()){
      populateAttributes( jobDefType );
      populateAttributes( jobIdentType );
    }    
  }
  
  // Checks if the content of the model for this page is refreshed.
  private boolean isContentRefreshed(){          
    return this.contentRefreshed;
     }

  // Responsible for setting the content to this page.
  public void setPageContent(final EObject rootJsdlElement, 
                             final boolean refreshStatus){
    
    TreeIterator iterator = rootJsdlElement.eAllContents();
    
    if (refreshStatus) {
      this.contentRefreshed = true;
    }
    // Get the Job Definition Element...which is actually the root
    // element.
    this.jobDefType = (JobDefinitionType) rootJsdlElement;
    
    while ( iterator.hasNext (  )  )  {  
     
      EObject testType = (EObject) iterator.next();
          
      if ( testType instanceof JobIdentificationType ) {
        this.jobIdentType = (JobIdentificationType) testType;
      } //end if JobIdentificationType
      else {
        // do Nothing
      }
        
    } //End while   

    
  } // End void getPageContent()    
  
  /*
   * Responsible for populating the attributes of the elements to each 
   * associated widget in the form.
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
      System.out.println(attributeName+" " + value.toString());
        //Check if Attribute has any value
        if (object.eIsSet( attribute )){          
           widgetName = this.widgetFeaturesMap.get( attributeName );
                 
         //FIXME - any check should be removed..check cause of it.
         if (attribute.getName().toString() != "any"){ //$NON-NLS-1$
           System.out.println(widgetName.toString());
           widgetName.setText(value.toString());
         } //end if
                   
        } //end if
      } //end for
    } //end if
  } // End void populateAttributes()
    

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
           
   if (!this.jobDefType.equals( null )){
     populateAttributes( this.jobDefType );
   }
     

    this.jobIdentComposite = createJobIdentificationSection(managedForm, 
                             Messages.JobDefinitionPage_JobIdentificationTitle, 
                             Messages.JobDefinitionPage_JobIdentificationDescr);
    
    if (!this.jobIdentType.equals( null )){
      populateAttributes( this.jobIdentType );
    }

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
   
    String prefix = this.jobDefType.eClass().getName().toString();
    
    Composite client = createSection(mform, title, desc, 2);
    FormToolkit toolkit = mform.getToolkit();
    GridData gd = new GridData();
    //gd.horizontalSpan = 2;
   
    this.lblJobId = toolkit.createLabel(client,
                                    Messages.JobDefinitionPage_JobDefinitionId);
    
    this.txtId = toolkit.createText(client,"", SWT.BORDER);
    this.widgetFeaturesMap.put( prefix + "_id", this.txtId ); //$NON-NLS-1$
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
    Composite client = createSection(mform, title, desc, 3);
    FormToolkit toolkit = mform.getToolkit();
       
    String prefix = this.jobIdentType.eClass().getName().toString();
    
    GridData gd ;
    GridData lblgd;
    lblgd = new GridData();
    gd = new GridData();
    gd.widthHint = 300;
    
    //Create the Label and Text for the Job Name Element
    toolkit.createLabel(client, Messages.JobDefinitionPage_JobName);
    GridData gridData = new GridData();
    gridData.horizontalAlignment = GridData.FILL;
    gridData.horizontalSpan = 2;
    this.txtJobName = toolkit.createText(client,"", SWT.BORDER); 
    this.widgetFeaturesMap.put( prefix + "_jobName", this.txtJobName ); //$NON-NLS-1$
    txtJobName.setLayoutData(gridData);

    txtJobName.setLayoutData(gridData);
    
    
    //Create the Label and Text for the Job Description Element
    this.lblJobDescripiton = toolkit.createLabel (client,
                                           Messages.JobDefinitionPage_JobDescr);
    lblgd = new GridData();
    lblgd.verticalSpan = 1;
    lblJobDescripiton.setLayoutData (lblgd);
    
    this.txtDescription = toolkit.createText(client,"",SWT.BORDER | SWT.MULTI 
                                                   | SWT.V_SCROLL | SWT.WRAP);
    
  
    gridData = new GridData();
    gridData.verticalAlignment = GridData.FILL;
    gridData.verticalSpan = 1;
    gridData.horizontalAlignment = GridData.FILL;
    gridData.horizontalSpan = 2;
    gridData.widthHint = 300;
    gridData.heightHint = 150;
    this.txtDescription.setLayoutData(gridData);
    
    this.widgetFeaturesMap.put( prefix + "_description", this.txtDescription ); //$NON-NLS-1$
    this.txtDescription.addFocusListener( new org.eclipse.swt.events.FocusListener() {
      public void focusLost( final org.eclipse.swt.events.FocusEvent e ) {
        JobDefinitionPage.this.jobIdentType.setDescription( JobDefinitionPage.
                                                 this.txtDescription.getText());
        }
      public void focusGained( final org.eclipse.swt.events.FocusEvent e ) {
        //
      }
    } );
    
    
    //Create the Label and List for Job Annotation Element
    this.lblJobAnnotation = toolkit.createLabel(client,
                                      Messages.JobDefinitionPage_JobAnnotation); 
    lblgd = new GridData();
    lblgd.verticalSpan = 3;
    lblJobAnnotation.setLayoutData( lblgd );
   
    gridData = new GridData();
    gridData.verticalAlignment = GridData.FILL;
    gridData.verticalSpan = 3;
    gridData.horizontalSpan = 1;
    gridData.widthHint = 300;
    gridData.heightHint = 150;
    final List lstJobAnnotation = new List(client,SWT.MULTI| SWT.BORDER);      
    lstJobAnnotation.setLayoutData( gridData );

    //Create Button ADD
    gd = new GridData();
    gd.verticalSpan = 2;
    gd.verticalAlignment = GridData.END;
    gd.widthHint = 40;
    this.btnADD = toolkit.createButton(client, Messages.JobDefinitionPage_ButtADD, SWT.PUSH);
    this.btnADD.setLayoutData( gd );

    //Create Button DEL

    gd = new GridData();
    gd.verticalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    gd.widthHint = 40;
    this.btnDel = toolkit.createButton(client, Messages.JobDefinitionPage_ButtDEL, SWT.PUSH); 
    this.btnDel.setLayoutData( gd );
 
    for (int i=0; i<this.jobIdentType.getJobAnnotation().size(); i++){
      lstJobAnnotation.add( this.jobIdentType.getJobAnnotation().get( i ).toString() );
    }
    
    //Create the Label and List for Job Project Element
     this.lblJobProject = toolkit.createLabel(client,
                                       Messages.JobDefinitionPage_JobProject);
     lblgd = new GridData();
     lblgd.verticalSpan = 3;
     lblJobProject.setLayoutData( lblgd );
     
     gridData = new GridData();
     gridData.verticalSpan = 3;
     gridData.horizontalSpan = 1;
     gridData.widthHint = 300;
     gridData.heightHint = 150;
     List lstJobProject = new List(client,SWT.MULTI| SWT.BORDER);      
     lstJobProject.setLayoutData( gridData );
     
     //Create Button ADD
     gd = new GridData();
     gd.verticalAlignment = GridData.END;
     gd.verticalSpan = 2;
     gd.widthHint = 40;
     this.btnADD = toolkit.createButton(client, Messages.JobDefinitionPage_ButtADD , SWT.PUSH); 
     this.btnADD.setLayoutData( gd );
     
     //Create Button DEL
     gd = new GridData();
     gd.verticalSpan = 1;
     gd.verticalAlignment = GridData.BEGINNING;
     gd.widthHint = 40;
     this.btnDel = toolkit.createButton(client, Messages.JobDefinitionPage_ButtDEL, SWT.PUSH); 
     this.btnDel.setLayoutData( gd );

    for (int i=0; i<this.jobIdentType.getJobProject().size(); i++){
      lstJobProject.add( this.jobIdentType.getJobProject().get( i ).toString() );
    }
  
    return client;
     
  }
  
  

 }
  

    
  
  