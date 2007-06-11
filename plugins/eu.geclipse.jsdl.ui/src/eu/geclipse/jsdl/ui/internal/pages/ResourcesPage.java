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
import eu.geclipse.jsdl.ui.internal.Activator;


public class ResourcesPage extends FormPage {
  
  static final int TXT_LENGTH = 300;
  
  Composite jobRescComposite = null;
  Label lblDescr = null;
  Label lblMountPoint = null;
  Label lblMountSource = null;
  Label lblDiskSpace = null;
  Label lblFileSystemType = null;
  Label lblOperSystType = null;
  Label lblOperSystVer = null;
  Label lblDescrip = null;
  Label lblCPUArchName = null;
  Label lblIndCPUSpl = null;
  Label lblIndCPUTime = null;
  Label lblIndCPUCount = null;
  Label lblIndNetBand = null;
  Label lblPhysMem = null;
  Label lblVirtMem = null;
  Label lblIndDiskSpac = null;
  Label lblCPUTime = null;
  Label lblCPUCount = null;
  Label lblTotPhMem = null;
  Label lblTotVirtMem = null;
  Label lblTotDiskSp = null;
  Label lblTotResCount = null;
  
  Button btnAdd = null;
  Button btnDel = null;
  
  List lstHostName = null;
 
  
  private Text txtDescr = null;
  private Text txtMountPoint = null;
  private Text txtMountSource = null;
  private Text txtDiskSpace = null;
  private Text txtFileSystemType = null;
  private Text txtOperSystType = null;
  private Text txtOperSystVer = null;
  private Text txtDescrip = null;
  private Text txtCPUArchName = null;
  private Text txtIndCPUSp = null;
  private Text txtIndCPUTime = null;
  private Text txtIndCPUCount = null;
  private Text txtIndNetBand = null;
  private Text txtVirtMem = null;
  private Text txtPhysMem = null;
  private Text txtIndDiskSpac = null;
  private Text txtCPUTime = null;
  private Text txtCPUCount = null;
  private Text txtTotPhMem = null;
  private Text txtTotVirtMem = null;
  private Text txtTotDiskSp = null;
  private Text txtTotResCount = null;
  
  
  
  
  // Constructor
  public ResourcesPage( final FormEditor editor )
                            
   {
    
    super(editor,Messages.ResourcesPage_pageId , 
          Messages.ResourcesPage_PageTitle);
    
   // breakTypes(list);
   
    }
  
  @Override
  
  // This method is used to create the Forms content by
  // creating the form layout and then creating the form
  // sub sections.
  protected void createFormContent(final IManagedForm managedForm) {
    
        
    ScrolledForm form = managedForm.getForm();
    form.setText(Messages.ResourcesPage_ResourcePageTitle); 
  
    ColumnLayout layout = new ColumnLayout();
    
    
    layout.leftMargin = 10;
    layout.rightMargin = 10;
    /*  Set Max and Min Columns to be 1... */
    layout.maxNumColumns = 1;
    layout.minNumColumns = 1;    
    form.getBody().setLayout(layout);
      

        
   this.jobRescComposite = createResourcesSection(managedForm,
                            Messages.ResourcesPage_PageTitle,
                            Messages.ResourcesPage_ResourcePageDescr);
   
   /* Set Form Background */
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
   layout.marginHeight = 0;
   layout.marginWidth = 0;
   layout.numColumns = numColumns;
   client.setLayout(layout);
   
   section.setClient(client);
      
   return client;
   
   }

  
  private void CandidateHostsSubSection (final Composite composite, final IManagedForm mform,
                                         final String title, final String desc, 
                                         final int numColumns,
                                         final int width,
                                         final int height)
  
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
    
    Label lblHostName = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_HostName);
    GridData lblgd = new GridData();
    lblgd.verticalSpan = 3;
    lblgd.verticalAlignment = GridData.BEGINNING;
    lblHostName.setLayoutData( lblgd );
         
    this.lstHostName = new List(clientsubSection, SWT.NONE);
    this.lstHostName.setData( FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER );  
    GridData gdlst = new GridData();
    gdlst = new GridData();
    gdlst.verticalSpan = 3;
    gdlst.horizontalSpan = 1;
    gdlst.heightHint = 150;
    gdlst.widthHint = ResourcesPage.TXT_LENGTH;
    this.lstHostName.setLayoutData(gdlst);
 
    //Create the button
    GridData gdBut = new GridData();
    //gdBut.widthHint = 40;     
    gdBut.verticalSpan = 2;
    gdBut.verticalAlignment = GridData.END;
    gdBut.horizontalAlignment = GridData.FILL;
    this.btnAdd = toolkit.createButton(clientsubSection, Messages.JsdlEditor_AddButton, SWT.BUTTON1);  
    this.btnAdd.setLayoutData( gdBut);
    
    //Create the button
    gdBut = new GridData();
    //gdBut.widthHint = 40;
    gdBut.verticalSpan = 1;
    gdBut.verticalAlignment = GridData.BEGINNING;
    gdBut.horizontalAlignment = GridData.FILL;
    this.btnDel = toolkit.createButton(clientsubSection, Messages.JsdlEditor_RemoveButton, SWT.BUTTON1);  
    this.btnDel.setLayoutData( gdBut);
    
    toolkit.paintBordersFor( clientsubSection);
  }
  
  
  
  private void FileSystemSubSection (final Composite composite, final IManagedForm mform,
                                     final String title, final String desc, 
                                     final int numColumns,
                                     final int width,
                                     final int height){
    
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
    
    this.lblDescr = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_Description); 
    this.txtDescr = toolkit.createText(clientsubSection, "", SWT.NONE  //$NON-NLS-1$
                        |SWT.H_SCROLL|SWT.V_SCROLL| SWT.WRAP); 
    
    gd = new GridData();    
    gd.widthHint = 285;
    gd.heightHint = 100;
    this.txtDescr.setLayoutData(gd);
    
    gd = new GridData();
    gd.widthHint = ResourcesPage.TXT_LENGTH;    
    this.lblMountPoint = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_MountPoint);
    this.txtMountPoint = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtMountPoint.setLayoutData(gd);
    this.lblMountSource = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_MountSource);
    this.txtMountSource = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtMountSource.setLayoutData(gd);    
    this.lblDiskSpace = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_DiskSpace);
    this.txtDiskSpace = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtDiskSpace.setLayoutData(gd);    this.lblFileSystemType = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_FileSysType);
    this.txtFileSystemType = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtFileSystemType.setLayoutData(gd);
    
    
    toolkit.paintBordersFor( clientsubSection);
  }
  
  private void OSsubSection (final Composite composite, final IManagedForm mform,
                             final String title, final String desc, 
                             final int numColumns,
                             final int width,
                             final int height)
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
    GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = numColumns;
    clientsubSection.setLayout(gridLayout);
    
    subSection.setClient( clientsubSection );
    
    gd = new GridData();
    gd.widthHint = 280;    

    this.lblOperSystType = toolkit.createLabel(clientsubSection, Messages.ResourcesPage_OperSystType);
    this.txtOperSystType = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtOperSystType.setLayoutData( gd );
    this.lblOperSystVer = toolkit.createLabel(clientsubSection, Messages.ResourcesPage_OperSystVersion);
    this.txtOperSystVer = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtOperSystVer.setLayoutData(gd);

    gd = new GridData();
    this.lblDescrip = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_DEscr);
    this.txtDescrip = toolkit.createText(clientsubSection, "", SWT.NONE//$NON-NLS-1$
                      |SWT.H_SCROLL |SWT.V_SCROLL | SWT.WRAP);

    gd.widthHint = 265;
    gd.heightHint=100;
    this.txtDescrip.setLayoutData(gd);
    
    toolkit.paintBordersFor( clientsubSection);
  
  }
  
  private void cPUArch (final Composite composite, final IManagedForm mform,
                        final String title, final String desc, 
                        final int numColumns,
                        final int width,
                        final int height)
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
    GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = numColumns;
    clientsubSection.setLayout(gridLayout);
    
    subSection.setClient( clientsubSection );
    
    gd = new GridData();
    gd.widthHint = ResourcesPage.TXT_LENGTH-20;
    this.lblCPUArchName = toolkit.createLabel(clientsubSection, Messages.ResourcesPage_CPUArchName);
    this.txtCPUArchName = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtCPUArchName.setLayoutData(gd);
    
    toolkit.paintBordersFor( clientsubSection);
  }
  
  
  private void addElementsSubSection (final Composite composite, final IManagedForm mform,
                                      final String title, final String desc, 
                                      final int numColumns,
                                      final int width,
                                      final int height)
  {
    FormToolkit toolkit = mform.getToolkit();
    
    Section subSection = toolkit.createSection(composite, ExpandableComposite.TITLE_BAR 
                                               | Section.DESCRIPTION
                                              | ExpandableComposite.TWISTIE );  
    
    subSection.setText(title);
    subSection.setDescription(desc);
    toolkit.createCompositeSeparator(subSection);
    
    
    GridData gd = new GridData();   
    gd.widthHint =  width;
    gd.heightHint = height;
    gd.horizontalSpan = 2;
    subSection.setLayoutData( gd );
    
    Composite clientsubSection = toolkit.createComposite(subSection);
    GridLayout gridlayout = new GridLayout();
    gridlayout.numColumns = numColumns;
    clientsubSection.setLayout(gridlayout);


    subSection.setClient( clientsubSection );
    
    this.lblIndCPUSpl = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_IndCPUSpeed);
    createCombo (clientsubSection);
    gd = new GridData();
    gd.widthHint = 120;
    this.txtIndCPUSp = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtIndCPUSp.setLayoutData(gd);
    
    this.lblIndCPUTime = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_IndCPUTime);
    createCombo (clientsubSection);
    this.txtIndCPUTime = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtIndCPUTime.setLayoutData(gd);

    this.lblIndCPUCount = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_IndCPUCount);
    createCombo (clientsubSection);
    this.txtIndCPUCount = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtIndCPUCount.setLayoutData(gd);
    
    this.lblIndNetBand = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_IndNetwBandwidth);
    createCombo (clientsubSection);
    this.txtIndNetBand = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtIndNetBand.setLayoutData(gd);

    this.lblPhysMem = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_PhysMem);
    createCombo (clientsubSection);
    this.txtPhysMem = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtPhysMem.setLayoutData(gd);
    
    this.lblVirtMem = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_VirtualMem);
    createCombo (clientsubSection);
    this.txtVirtMem = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtVirtMem.setLayoutData(gd);
    
    this.lblIndDiskSpac = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_IndDiskSpace);
    createCombo (clientsubSection);
    this.txtIndDiskSpac = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtIndDiskSpac.setLayoutData(gd);

    this.lblCPUTime = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_CPUTime);
    createCombo (clientsubSection);
    this.txtCPUTime = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtCPUTime.setLayoutData(gd);

    this.lblCPUCount = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_TotCPUCount);
    createCombo (clientsubSection);
    this.txtCPUCount = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtCPUCount.setLayoutData(gd);

    this.lblTotPhMem = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_TotPhysMem);
    createCombo (clientsubSection);
    this.txtTotPhMem = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtTotPhMem.setLayoutData(gd);

    this.lblTotVirtMem = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_TotVirtualMem);
    createCombo (clientsubSection);
    this.txtTotVirtMem = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtTotVirtMem.setLayoutData(gd);
   
    this.lblTotDiskSp = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_TotDiskSpace);
    createCombo (clientsubSection);
    this.txtTotDiskSp = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtTotDiskSp.setLayoutData(gd);

    this.lblTotResCount = toolkit.createLabel(clientsubSection,Messages.ResourcesPage_TotRescCount);
    createCombo (clientsubSection);
    this.txtTotResCount = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtTotResCount.setLayoutData(gd);

    toolkit.paintBordersFor( clientsubSection );
    
  }
  
  /* 
   * Create the Resources Section
   * 
   */
  private Composite createResourcesSection(final IManagedForm mform, 
                                          final String title, 
                                          final String desc)
  {
   
    Composite client = createSection(mform, title, desc, 2);

    CandidateHostsSubSection (client,mform,
                            Messages.ResourcesPage_CanHost,
                            Messages.ResourcesPage_CandHostDesc,3,480,270);
    
    FileSystemSubSection( client,mform,
                          Messages.ResourcesPage_FileSystem, 
                          Messages.ResourcesPage_FileSystemDesc,2,480,270);
    
    OSsubSection(client,mform,
                 Messages.ResourcesPage_OperSyst,
                 Messages.ResourcesPage_OperSystDescr,2,480,210);
    
    cPUArch( client,mform,
             Messages.ResourcesPage_CPUArch,
             Messages.ResourcesPage_CPUArchDescr,2,480,210);
    
    addElementsSubSection( client,mform,
                           Messages.ResourcesPage_AddElementRangeVal,
                           Messages.ResourcesPage_AddElementsRangeValueDescr,3,515,450 );
  
      
     return client;
}

  private Combo createCombo(final Composite composite){
    
    Combo comboRang = new Combo(composite,SWT.DROP_DOWN);
    comboRang.add(""); //$NON-NLS-1$
    comboRang.add(Messages.ResourcesPage_LowBoundRange);
    comboRang.add(Messages.ResourcesPage_UpBoundRange);
    comboRang.add(Messages.ResourcesPage_Exact);
    GridData gd = new GridData();
    gd.widthHint = 170;
    comboRang.setLayoutData(gd);
    
    return comboRang;
    
  }

}
