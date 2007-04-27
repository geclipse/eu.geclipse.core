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


public class ResourcesPage extends FormPage {
  
  Composite jobRescComposite;
  Label lblDescr;
  Label lblMountPoint;
  Label lblMountSource;
  Label lblDiskSpace;
  Label lblFileSystemType;
  Label lblOperSystType;
  Label lblOperSystVer;
  Label lblDescrip;
  Label lblCPUArchName;
  Label lblIndCPUSpl;
  Label lblIndCPUTime;
  Label lblIndCPUCount;
  Label lblIndNetBand;
  Label lblPhysMem;
  Label lblVirtMem;
  Label lblIndDiskSpac;
  Label lblCPUTime;
  Label lblCPUCount;
  Label lblTotPhMem;
  Label lblTotVirtMem;
  Label lblTotDiskSp;
  Label lblTotResCount;
 
  
  private Text txtDescr;
  private Text txtMountPoint;
  private Text txtMountSource;
  private Text txtDiskSpace;
  private Text txtFileSystemType;
  private Text txtOperSystType;
  private Text txtOperSystVer;
  private Text txtDescrip;
  private Text txtCPUArchName;
  private Text txtIndCPUSp;
  private Text txtIndCPUTime;
  private Text txtIndCPUCount;
  private Text txtIndNetBand;
  private Text txtVirtMem;
  private Text txtPhysMem;
  private Text txtIndDiskSpac;
  private Text txtCPUTime;
  private Text txtCPUCount;
  private Text txtTotPhMem;
  private Text txtTotVirtMem;
  private Text txtTotDiskSp;
  private Text txtTotResCount;
  

  
  
  
  // Constructor
  public ResourcesPage( final FormEditor editor
                        )
                            
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
    
    // Set Max and Min Columns to be 2...
    layout.maxNumColumns = 1;
    layout.minNumColumns = 1;    
    form.getBody().setLayout(layout);
      

        
   this.jobRescComposite = createResourcesSection(managedForm,
                            Messages.ResourcesPage_PageTitle,
                            Messages.ResourcesPage_ResourcePageDescr);  

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
   layout.verticalSpacing = 20;
   layout.horizontalSpacing=20;
   layout.marginTop = 5;
   layout.marginWidth = 5;
   layout.marginHeight = 5;
   layout.numColumns = numColumns;
   client.setLayout(layout);
   
   section.setClient(client);
      
   return client;
   
   }
 
  
  // This method is used to create the individual sections which contain the elements and their attributes 
  private Composite createsubSection(final Composite composite, final IManagedForm mform,
                                     final String title, final String desc, 
                                     final int numColumns,final int width,final int height) 
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

    return clientsubSection;
  }
  
  /* 
   * Create the Resources Section
   * 
   */
  private Composite createResourcesSection(final IManagedForm mform, 
                                          final String title, final String desc)
  {
   
    Composite client = createSection(mform, title, desc, 2);
    FormToolkit toolkit = mform.getToolkit();
    GridData gd = new GridData();

  
    //Create the widgets that Resources Section contains.
   
    //Create the Expandable Section Candidate Hosts
  Composite sectionCandHosts = createsubSection (client,mform,
  Messages.ResourcesPage_CanHost,Messages.ResourcesPage_CandHostDesc,3,550,300);

//    Composite sectionCandHosts = createExpSection (client,mform,
//    "Candidate Hosts","Describes the set of named hosts which may be selected for running the job. ",3,550,300);
    
     //Create the Label and the List for the Host Name Element
     Label lblHostName = toolkit.createLabel(sectionCandHosts,Messages.ResourcesPage_HostName);
     GridData lblgd = new GridData();
     lblgd.verticalSpan = 3;
     lblHostName.setLayoutData( lblgd );
          
     List lstHostName = new List(sectionCandHosts,SWT.MULTI| SWT.BORDER);
     GridData gdlst = new GridData();
     gdlst = new GridData();
     gdlst.verticalSpan = 3;
     gdlst.horizontalSpan = 1;
     gdlst.heightHint = 150;
     gdlst.widthHint =300;
     lstHostName.setLayoutData(gdlst);
  
     //Create the button
     GridData gdBut = new GridData();
     gdBut.widthHint = 40;
     gdBut.verticalSpan = 2;
     gdBut.verticalAlignment = GridData.END;
     Button btnAdd = toolkit.createButton(sectionCandHosts, Messages.ResourcesPage_ButtAdd, SWT.BUTTON1);  
     btnAdd.setLayoutData( gdBut);
     
     //Create the button
     gdBut = new GridData();
     gdBut.widthHint = 40;
     gdBut.verticalSpan = 1;
     gdBut.verticalAlignment = GridData.BEGINNING;
     Button btnDel = toolkit.createButton(sectionCandHosts, Messages.ResourcesPage_ButtDel, SWT.BUTTON1);  
     btnDel.setLayoutData( gdBut);
    
    
     //Create the expandable section for File System
     Composite sectionFileSyst = createsubSection (client,mform,Messages.ResourcesPage_FileSystem, 
                         Messages.ResourcesPage_FileSystemDesc,2,550,300);      
     
     
     //Create the Label and Text boxes for the elements that File System supports
     this.lblDescr = toolkit.createLabel(sectionFileSyst,Messages.ResourcesPage_Description); 
     this.txtDescr = toolkit.createText(sectionFileSyst, "", SWT.BORDER  //$NON-NLS-1$
                         |SWT.H_SCROLL|SWT.V_SCROLL| SWT.WRAP); 
     GridData gdBtxt = new GridData();
     gdBtxt.widthHint = 300;
     gdBtxt.heightHint=100;
     this.txtDescr.setLayoutData(gdBtxt);
     
     this.lblMountPoint = toolkit.createLabel(sectionFileSyst,Messages.ResourcesPage_MountPoint);
     this.txtMountPoint = toolkit.createText(sectionFileSyst, "", SWT.BORDER); //$NON-NLS-1$
     GridData gdstxt = new GridData();
     gdstxt.widthHint = 300;
     this.txtMountPoint.setLayoutData(gdstxt);
     
     this.lblMountSource = toolkit.createLabel(sectionFileSyst,Messages.ResourcesPage_MountSource);
     this.txtMountSource = toolkit.createText(sectionFileSyst, "", SWT.BORDER); //$NON-NLS-1$
     this.txtMountSource.setLayoutData(gdstxt);
     
     
     this.lblDiskSpace = toolkit.createLabel(sectionFileSyst,Messages.ResourcesPage_DiskSpace);
     this.txtDiskSpace = toolkit.createText(sectionFileSyst, "", SWT.BORDER); //$NON-NLS-1$
     this.txtDiskSpace.setLayoutData(gdstxt);
     
     this.lblFileSystemType = toolkit.createLabel(sectionFileSyst,Messages.ResourcesPage_FileSysType);
     this.txtFileSystemType = toolkit.createText(sectionFileSyst, "", SWT.BORDER); //$NON-NLS-1$
     this.txtFileSystemType.setLayoutData(gdstxt);
    
     //Create the Section Exclusive Execution
     Composite sectionExclusive = createsubSection (client,mform,
     Messages.ResourcesPage_ExclExec,Messages.ResourcesPage_ExclExecDescr,2,550,300);
   
     //Create the Label and the Combo for Exclusive Execution
     Combo comboExclExec = new Combo(sectionExclusive,SWT.DROP_DOWN);
     comboExclExec.add(""); //$NON-NLS-1$
     comboExclExec.add(Messages.ResourcesPage_true);
     comboExclExec.add(Messages.ResourcesPage_false);
     gd.widthHint = 300;
     comboExclExec.setLayoutData(gd);

     //Create the expandable section for Operating System Element
     Composite sectionOperSyst = createsubSection (client,mform,
             Messages.ResourcesPage_OperSyst,Messages.ResourcesPage_OperSystDescr,2,550,300);
     
     this.lblOperSystType = toolkit.createLabel(sectionOperSyst,Messages.ResourcesPage_OperSystType);
     this.txtOperSystType = toolkit.createText(sectionOperSyst, "", SWT.BORDER); //$NON-NLS-1$
     this.txtOperSystType.setLayoutData(gdstxt);
     
     this.lblOperSystVer = toolkit.createLabel(sectionOperSyst,Messages.ResourcesPage_OperSystVersion);
     this.txtOperSystVer = toolkit.createText(sectionOperSyst, "", SWT.BORDER); //$NON-NLS-1$
     this.txtOperSystVer.setLayoutData(gdstxt);
     
     this.lblDescrip = toolkit.createLabel(sectionOperSyst,Messages.ResourcesPage_DEscr);
     this.txtDescrip = toolkit.createText(sectionOperSyst, "", SWT.BORDER//$NON-NLS-1$
                         |SWT.H_SCROLL|SWT.V_SCROLL| SWT.WRAP);  
     this.txtDescrip.setLayoutData(gdBtxt);
     
     //Create the expandable section for CPU Architecture
     Composite sectionCPUArch = createsubSection (client,mform,
    Messages.ResourcesPage_CPUArch,Messages.ResourcesPage_CPUArchDescr,2,550,300);
     
     this.lblCPUArchName = toolkit.createLabel(sectionCPUArch,Messages.ResourcesPage_CPUArchName);
     this.txtCPUArchName = toolkit.createText(sectionCPUArch, "", SWT.BORDER); //$NON-NLS-1$
     this.txtCPUArchName.setLayoutData(gdstxt);

     //Create the Section RangeValue_Type Elements
     Composite sectionRangeValue_Type = createsubSection (client,mform,
     Messages.ResourcesPage_AddElementRangeVal,Messages.ResourcesPage_AddElementsRangeValueDescr,3,620,500);
   
     this.lblIndCPUSpl = toolkit.createLabel(sectionRangeValue_Type,Messages.ResourcesPage_IndCPUSpeed);
     createCombo (sectionRangeValue_Type);
     GridData gdstxt1 = new GridData();
     gdstxt1.widthHint = 200;
     this.txtIndCPUSp = toolkit.createText(sectionRangeValue_Type, "", SWT.BORDER); //$NON-NLS-1$
     this.txtIndCPUSp.setLayoutData(gdstxt1);
     
     this.lblIndCPUTime = toolkit.createLabel(sectionRangeValue_Type,Messages.ResourcesPage_IndCPUTime);
     createCombo (sectionRangeValue_Type);
     this.txtIndCPUTime = toolkit.createText(sectionRangeValue_Type, "", SWT.BORDER); //$NON-NLS-1$
     this.txtIndCPUTime.setLayoutData(gdstxt1);

     this.lblIndCPUCount = toolkit.createLabel(sectionRangeValue_Type,Messages.ResourcesPage_IndCPUCount);
     createCombo (sectionRangeValue_Type);
     this.txtIndCPUCount = toolkit.createText(sectionRangeValue_Type, "", SWT.BORDER); //$NON-NLS-1$
     this.txtIndCPUCount.setLayoutData(gdstxt1);
     
     this.lblIndNetBand = toolkit.createLabel(sectionRangeValue_Type,Messages.ResourcesPage_IndNetwBandwidth);
     createCombo (sectionRangeValue_Type);
     this.txtIndNetBand = toolkit.createText(sectionRangeValue_Type, "", SWT.BORDER); //$NON-NLS-1$
     this.txtIndNetBand.setLayoutData(gdstxt1);

     this.lblPhysMem = toolkit.createLabel(sectionRangeValue_Type,Messages.ResourcesPage_PhysMem);
     createCombo (sectionRangeValue_Type);
     this.txtPhysMem = toolkit.createText(sectionRangeValue_Type, "", SWT.BORDER); //$NON-NLS-1$
     this.txtPhysMem.setLayoutData(gdstxt1);
     
     this.lblVirtMem = toolkit.createLabel(sectionRangeValue_Type,Messages.ResourcesPage_VirtualMem);
     createCombo (sectionRangeValue_Type);
     this.txtVirtMem = toolkit.createText(sectionRangeValue_Type, "", SWT.BORDER); //$NON-NLS-1$
     this.txtVirtMem.setLayoutData(gdstxt1);
     
     this.lblIndDiskSpac = toolkit.createLabel(sectionRangeValue_Type,Messages.ResourcesPage_IndDiskSpace);
     createCombo (sectionRangeValue_Type);
     this.txtIndDiskSpac = toolkit.createText(sectionRangeValue_Type, "", SWT.BORDER); //$NON-NLS-1$
     this.txtIndDiskSpac.setLayoutData(gdstxt1);

     this.lblCPUTime = toolkit.createLabel(sectionRangeValue_Type,Messages.ResourcesPage_CPUTime);
     createCombo (sectionRangeValue_Type);
     this.txtCPUTime = toolkit.createText(sectionRangeValue_Type, "", SWT.BORDER); //$NON-NLS-1$
     this.txtCPUTime.setLayoutData(gdstxt1);

     this.lblCPUCount = toolkit.createLabel(sectionRangeValue_Type,Messages.ResourcesPage_TotCPUCount);
     createCombo (sectionRangeValue_Type);
     this.txtCPUCount = toolkit.createText(sectionRangeValue_Type, "", SWT.BORDER); //$NON-NLS-1$
     this.txtCPUCount.setLayoutData(gdstxt1);

     this.lblTotPhMem = toolkit.createLabel(sectionRangeValue_Type,Messages.ResourcesPage_TotPhysMem);
     createCombo (sectionRangeValue_Type);
     this.txtTotPhMem = toolkit.createText(sectionRangeValue_Type, "", SWT.BORDER); //$NON-NLS-1$
     this.txtTotPhMem.setLayoutData(gdstxt1);

     this.lblTotVirtMem = toolkit.createLabel(sectionRangeValue_Type,Messages.ResourcesPage_TotVirtualMem);
     createCombo (sectionRangeValue_Type);
     this.txtTotVirtMem = toolkit.createText(sectionRangeValue_Type, "", SWT.BORDER); //$NON-NLS-1$
     this.txtTotVirtMem.setLayoutData(gdstxt1);
    
     this.lblTotDiskSp = toolkit.createLabel(sectionRangeValue_Type,Messages.ResourcesPage_TotDiskSpace);
     createCombo (sectionRangeValue_Type);
     this.txtTotDiskSp = toolkit.createText(sectionRangeValue_Type, "", SWT.BORDER); //$NON-NLS-1$
     this.txtTotDiskSp.setLayoutData(gdstxt1);

     this.lblTotResCount = toolkit.createLabel(sectionRangeValue_Type,Messages.ResourcesPage_TotRescCount);
     createCombo (sectionRangeValue_Type);
     this.txtTotResCount = toolkit.createText(sectionRangeValue_Type, "", SWT.BORDER); //$NON-NLS-1$
     this.txtTotResCount.setLayoutData(gdstxt1);

     
     return client;
}

  private Combo createCombo(final Composite composite){
    
    Combo comboRang = new Combo(composite,SWT.DROP_DOWN);
    comboRang.add(""); //$NON-NLS-1$
    comboRang.add(Messages.ResourcesPage_LowBoundRange);
    comboRang.add(Messages.ResourcesPage_UpBoundRange);
    comboRang.add(Messages.ResourcesPage_Exact);
    GridData gd = new GridData();
    gd.widthHint = 180;
    comboRang.setLayoutData(gd);
    
    return comboRang;
    
  }

}
