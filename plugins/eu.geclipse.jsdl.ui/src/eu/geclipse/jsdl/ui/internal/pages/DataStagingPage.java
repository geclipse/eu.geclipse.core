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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
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
import eu.geclipse.jsdl.jsdl.DataStagingAdapter;
import eu.geclipse.jsdl.ui.internal.Activator;


public class DataStagingPage extends FormPage{
  
  Composite jobDataStaging = null;
  
  List lstFileName = null;
  Text txtFileSystemName = null;
  Text txtSource = null;
  Text txtTarget = null;
  Text txtName = null;
   
  Label lblFileName = null;
  Label lblFileSystemName = null;
  Label lblCreationFlag = null;
  Label lblDelOnTerm = null;
  Label lblSource = null;
  Label lblTarget = null;
  Label lblName = null;
  
  Button btnAdd = null;
  Button btnDel = null;
  
  Combo comboCreationFlag = null;
  Combo comboDelOnTerm = null;

  
  private DataStagingAdapter dataStagingAdapter;
  private Boolean contentRefreshed = Boolean.FALSE;
  
  
  /* Class Constructor */
  public DataStagingPage( final FormEditor editor )
                            
   {
    
    super(editor,Messages.getString("DataStagingPage_pageId"), 
          Messages.getString("DataStagingPage_PageTitle"));
   
    }
  
  
  
  
  
  /* This method is used to create the Forms content by
   * creating the form layout and then creating the form
   * sub sections.
   */
  @Override
  protected void createFormContent(final IManagedForm managedForm) {
    
        
    ScrolledForm form = managedForm.getForm();
    form.setText(Messages.getString("DataStagingPage_DataStagingPageTitle")); 
  
    ColumnLayout layout = new ColumnLayout();
   
    layout.leftMargin = 10;
    layout.rightMargin = 10;
    /*  Set Max and Min Columns to be 1... */
    layout.maxNumColumns = 1;
    layout.minNumColumns = 1;    
    form.getBody().setLayout(layout);
      

        
   this.jobDataStaging = createDataStagingSection(managedForm,
                                Messages.getString("DataStagingPage_PageTitle"), 
                        Messages.getString("DataStagingPage_DataStagingDescr"));  
      
   this.dataStagingAdapter.load();
   
   /* Set Form Background */
   form.setBackgroundImage(Activator.getDefault().
                           getImageRegistry().get( "formsbackground" ));
  }
  

  @Override
  public void setActive(final boolean active) {
    
    if (active){
      if (isContentRefreshed()){
        this.dataStagingAdapter.load();
      }//endif isContentRefreshed
    } // endif active
  }



  private boolean isContentRefreshed(){          
    return this.contentRefreshed.booleanValue();
  }
  
  
  
  /* Must be called from the MPE Editor */ 
    public void setPageContent(final EObject rootJsdlElement, 
                             final boolean refreshStatus){

   if (refreshStatus) {
      this.contentRefreshed = Boolean.TRUE;
      this.dataStagingAdapter.setContent( rootJsdlElement );
    }
   else{     
      this.dataStagingAdapter = new DataStagingAdapter(rootJsdlElement);     
   }
          
  } // End void getPageContent() 


  /* This method is used to create individual subsections */
  
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
   
   toolkit.paintBordersFor( client );
   
   return client;
   
   }
  
  
  
  private Composite createSubSection(final Composite composite, 
                                     final IManagedForm mform,
                                     final String title, 
                                     final String desc, 
                                     final int numColumns,
                                     final int width,
                                     final int height ) 
  {
    
    FormToolkit toolkit = mform.getToolkit();
    
    Section subSection = toolkit.createSection(composite, ExpandableComposite.TITLE_BAR 
                                               | Section.DESCRIPTION 
                                               | SWT.WRAP);    
    subSection.setText(title);
    subSection.setDescription(desc);
    toolkit.createCompositeSeparator(subSection);
    GridData gd = new GridData();
    gd.widthHint =  width;
    gd.heightHint = height;
    subSection.setLayoutData( gd );
    
    Composite clientsubSection = toolkit.createComposite(subSection);
    GridLayout gridlayout = new GridLayout();
    gridlayout.numColumns = numColumns;
    clientsubSection.setLayout(gridlayout);
    
    
    subSection.setClient( clientsubSection );
       
          
    /* ========================== File Name Widgets ========================= */
    
    gd = new GridData();
    gd.verticalSpan = 2;
    gd.horizontalSpan = 1;
    this.lblFileName = toolkit.createLabel(clientsubSection,
                                 Messages.getString("DataStagingPage_FileName"));
    
    this.lstFileName = new List(clientsubSection, SWT.NONE | SWT.V_SCROLL);
    this.lstFileName.setData( FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER );    
    this.dataStagingAdapter.attachToFileName( this.lstFileName );
    gd.horizontalAlignment = GridData.FILL;
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 2;
    gd.horizontalSpan = 1;
    gd.widthHint = 220;
    gd.heightHint = 100;
    this.lstFileName.setLayoutData( gd );
    
    /*  Create "Add" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    this.btnAdd = toolkit.createButton(clientsubSection,
                                     Messages.getString("JsdlEditor_AddButton"),
                                     SWT.PUSH);
    this.dataStagingAdapter.attachToAdd( this.btnAdd );
    this.btnAdd.setLayoutData( gd );
    
    
    /* Create "Remove" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    gd.verticalAlignment = GridData.BEGINNING;
    this.btnDel = toolkit.createButton(clientsubSection,
                                  Messages.getString("JsdlEditor_RemoveButton"),
                                   SWT.PUSH);
    this.dataStagingAdapter.attachToDelete( this.btnDel );
    this.btnDel.setLayoutData( gd );
    
    
    /* =================== File-System Name Widgets ========================= */

    gd = new GridData();
    this.lblFileSystemName = toolkit.createLabel(clientsubSection,
                          Messages.getString("DataStagingPage_FileSystemName"));
    this.txtFileSystemName = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.dataStagingAdapter.attachToFileSystemName(this.txtFileSystemName );
    gd.horizontalSpan=3;
    gd.widthHint = 300;
    this.txtFileSystemName.setLayoutData(gd);
    
    
    /* ====================== Creation Flag Widgets ========================= */
    
    this.lblCreationFlag = toolkit.createLabel(clientsubSection,
                            Messages.getString("DataStagingPage_CreationFlag"));
    
    this.comboCreationFlag = new Combo(clientsubSection, SWT.NONE);
    this.comboCreationFlag.setData( FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER );
    this.dataStagingAdapter.attachToCreationFlag( this.comboCreationFlag );
    gd.horizontalSpan=3;
    gd.widthHint = 300;
    this.comboCreationFlag.setLayoutData(gd);

    /* =================== Delete On Termination Widgets ==================== */
    
    this.lblDelOnTerm = toolkit.createLabel(clientsubSection,
                     Messages.getString("DataStagingPage_DeleteOnTermination"));
    
    this.comboDelOnTerm = new Combo(clientsubSection, SWT.NONE);
    this.comboDelOnTerm.setData( FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER );    
    this.comboDelOnTerm.add(Messages.getString("DataStagingPage_true"));
    this.comboDelOnTerm.add(Messages.getString("DataStagingPage_false"));
    this.dataStagingAdapter.attachToDelOnTermination( this.comboDelOnTerm );
    gd.horizontalSpan=3;
    gd.widthHint = 300;
    this.comboDelOnTerm.setLayoutData(gd);
    
    /* ===================== Source Location Widgets ======================== */
    this.lblSource = toolkit.createLabel(clientsubSection,
                                  Messages.getString("DataStagingPage_Source"));
    
    this.txtSource = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.dataStagingAdapter.attachToSource( this.txtSource );
    gd.horizontalSpan=3;
    gd.widthHint = 300;
    this.txtSource.setLayoutData(gd);
    
    /* ====================== Target Location Widgets ======================= */
    
    this.lblTarget = toolkit.createLabel(clientsubSection,
                                  Messages.getString("DataStagingPage_Target"));
    this.txtTarget = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.dataStagingAdapter.attachToTarget( this.txtTarget );
    gd.horizontalSpan=3;
    gd.widthHint = 300;
    this.txtTarget.setLayoutData(gd);
    
    /* ========================== Name Widgets ============================ */
    this.lblName = toolkit.createLabel(clientsubSection,
                                    Messages.getString("DataStagingPage_Name"));
    this.txtName= toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.dataStagingAdapter.attachToName( this.txtName );
    gd.horizontalSpan=3;
    gd.widthHint = 300;
    this.txtName.setLayoutData(gd);
    
    toolkit.paintBordersFor( clientsubSection );
    
    return clientsubSection;
  }
  
  

  /* 
   * Create the Data Staging Section
   * 
   */
  private Composite createDataStagingSection(final IManagedForm mform, 
                                          final String title, final String desc)
  {
   
    Composite client = createSection(mform, title, desc, 2);
    
    /* Create the Staged Files Sub-Section */
    this.jobDataStaging = createSubSection (client,mform,
                                 Messages.getString("DataStagingPage_Section"),
                              Messages.getString("DataStagingPage_SectionDesc"),
                              4,490,320);    
  
    
    return client;
  }


} //End Class
