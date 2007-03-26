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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;


public class DataStagingPage extends FormPage{
  
  Composite jobDataStagComposite;
  private Text txtFileName;
  private Text txtFileSystemName;
  private Text txtSource;
  private Text txtTarget;
  
  // Constructor
  public DataStagingPage( final FormEditor editor, 
                             final ArrayList<EObject> list)
                            
   {
    
    super(editor,Messages.DataStagingPage_pageId , 
          Messages.DataStagingPage_PageTitle);
    
   // breakTypes(list);
   
    }
  
  @Override
  
  // This method is used to create the Forms content by
  // creating the form layout and then creating the form
  // sub sections.
  protected void createFormContent(final IManagedForm managedForm) {
    
        
    ScrolledForm form = managedForm.getForm();
    form.setText(Messages.DataStagingPage_DataStagingPageTitle); 
  
    ColumnLayout layout = new ColumnLayout();
    
    // Set Max and Min Columns to be 1...
    layout.maxNumColumns = 1;
    layout.minNumColumns = 1;    
    form.getBody().setLayout(layout);
      

        
   this.jobDataStagComposite = createDataStagingSection(managedForm,
                                 Messages.DataStagingPage_PageTitle, 
                                 Messages.DataStagingPage_DataStagingDescr);  

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
   * Create the Data Staging Section
   * 
   */
  private Composite createDataStagingSection(final IManagedForm mform, 
                                          final String title, final String desc)
  {
   
    Composite client = createSection(mform, title, desc, 2);
    FormToolkit toolkit = mform.getToolkit();
    GridData gd = new GridData();
    
    //Create the widgets that Data Staging Section contains.
    //File Name Label and Text box
    Label lblFileName = toolkit.createLabel(client, Messages.DataStagingPage_FileName);
    this.txtFileName = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
    gd.widthHint = 300;
    this.txtFileName.setLayoutData(gd);
    
    //File System Name Label and Text box
    Label lblFileSystemName = toolkit.createLabel(client,Messages.DataStagingPage_FileSystemName);
    this.txtFileSystemName = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
    gd.widthHint = 300;
    this.txtFileSystemName.setLayoutData(gd);
    
    //Creation Flag Label and Combo box
    Label lblCreationFlag = toolkit.createLabel(client,Messages.DataStagingPage_CreationFlag);
    Combo comboCreationFlag = new Combo(client,SWT.DROP_DOWN);
    comboCreationFlag.add(""); //$NON-NLS-1$
    comboCreationFlag.add(Messages.DataStagingPage_overwrite);
    comboCreationFlag.add(Messages.DataStagingPage_append);
    comboCreationFlag.add(Messages.DataStagingPage_dontOverwrite);
    gd.widthHint = 300;
    comboCreationFlag.setLayoutData(gd);

    //Delete On Termination Label and Combo box
    Label lblDelOnTerm = toolkit.createLabel(client,Messages.DataStagingPage_DeleteOnTermination);
    Combo comboDelOnTerm = new Combo(client,SWT.DROP_DOWN);
    comboDelOnTerm.add(""); //$NON-NLS-1$
    comboDelOnTerm.add(Messages.DataStagingPage_true);
    comboDelOnTerm.add(Messages.DataStagingPage_false);
    gd.widthHint = 300;
    comboDelOnTerm.setLayoutData(gd);
    
    //Source Label and Text box
    Label lblSource = toolkit.createLabel(client,Messages.DataStagingPage_Source);
    this.txtSource = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
    gd.widthHint = 300;
    this.txtSource.setLayoutData(gd);
    
    //Target Label and Text box
    Label lblTarget = toolkit.createLabel(client,Messages.DataStagingPage_Target);
    this.txtTarget = toolkit.createText(client, "", SWT.BORDER); //$NON-NLS-1$
    gd.widthHint = 300;
    this.txtTarget.setLayoutData(gd);
    
    return client;
}


}
