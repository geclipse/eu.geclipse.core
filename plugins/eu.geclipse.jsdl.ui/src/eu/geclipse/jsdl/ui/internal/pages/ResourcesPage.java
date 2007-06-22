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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import eu.geclipse.jsdl.jsdl.ResourcesTypeAdapter;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.dialogs.MultipleInputDialog;


public class ResourcesPage extends FormPage implements INotifyChangedListener {
  
   
  
  protected ResourcesTypeAdapter resourcesTypeAdapter;
  protected Object value = null;  
  protected Composite jobRescComposite = null;
  protected Label lblDescr = null;
  protected Label lblMountPoint = null;
  protected Label lblMountSource = null;
  protected Label lblDiskSpace = null;
  protected Label lblFileSystemType = null;
  protected Label lblOperSystType = null;
  protected Label lblOperSystVer = null;
  protected Label lblDescrip = null;
  protected Label lblCPUArchName = null;
  protected Label lblIndCPUSpl = null;
  protected Label lblIndCPUTime = null;
  protected Label lblIndCPUCount = null;
  protected Label lblIndNetBand = null;
  protected Label lblPhysMem = null;
  protected Label lblVirtMem = null;
  protected Label lblIndDiskSpac = null;
  protected Label lblCPUTime = null;
  protected Label lblCPUCount = null;
  protected Label lblTotPhMem = null;
  protected Label lblTotVirtMem = null;
  protected Label lblTotDiskSp = null;
  protected Label lblTotResCount = null;
  protected Label lblFileSystemName = null;
  
  protected Button btnAdd = null;
  protected Button btnDel = null;
  
  protected List lstHostName = null;
 
  private Text txtFileSystemName = null;
  private Text txtFileSystemDescr = null;
  private Text txtMountPoint = null;  
  private Text txtDiskSpace = null;  
  private Text txtOperSystVer = null;
  private Text txtOSDescr = null;  
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
  
  private Combo cmbOperSystType = null;
  private Combo cmbCPUArchName = null;
  private Combo cmbFileSystemType = null;
  private Combo cmbDiskSpaceRange = null;
  
  
  
  private boolean contentRefreshed = false;
  private boolean dirtyFlag = false;
  
  
  
  private final int TXT_LENGTH = 300;
  private final int WIDGET_HEIGHT = 100;
  
  
  /*
   * Class Constructor.
   */
  public ResourcesPage( final FormEditor editor )
                            
   {
   
    super(editor,Messages.getString("ResourcesPage_pageId") , //$NON-NLS-1$
          Messages.getString("ResourcesPage_PageTitle")); //$NON-NLS-1$
        
    } // End Class Constructor.

  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.forms.editor.FormPage#setActive(boolean)
   */
  @Override
  public void setActive(final boolean active) {
    
    if (active){
      if (isContentRefreshed()){    
        this.resourcesTypeAdapter.load();        
      }//endif (isContentRefreshed())
    } // endif (active)
  } // End void setActive()
  
  
  /*
   * Checks if the Page Content has been refreshed. 
   */
  private boolean isContentRefreshed(){          
    return this.contentRefreshed;
     }
  
  /*
   * Public Method called from the MPE Editor to set the 
   * page content.
   */
  public void setPageContent(final EObject rootJsdlElement, 
                             final boolean refreshStatus){

   if (refreshStatus) {
      this.contentRefreshed = true;
      this.resourcesTypeAdapter.setContent( rootJsdlElement );      
    }
   else{
      this.resourcesTypeAdapter = new ResourcesTypeAdapter(rootJsdlElement);  
      this.resourcesTypeAdapter.addListener( this );
   }
          
  } // End void getPageContent() 
  
  
  @Override
  public boolean isDirty() {
    
    return this.dirtyFlag;
    
  }

  
  
  public void setDirty(final boolean dirtyFlag) {
    if (this.dirtyFlag != dirtyFlag) {
      this.dirtyFlag = dirtyFlag;     
      this.getEditor().editorDirtyStateChanged();  
    }
    
  }
  
  
  
  @Override
  /*
   *  This method is used to create the Forms content by
   * creating the form layout and then creating the form
   * Sub-Sections
   */
  protected void createFormContent(final IManagedForm managedForm) {
    
        
    ScrolledForm form = managedForm.getForm();
    form.setText(Messages.getString("ResourcesPage_ResourcePageTitle"));  //$NON-NLS-1$
  
    ColumnLayout layout = new ColumnLayout();
    
    
    layout.leftMargin = 10;
    layout.rightMargin = 10;
    /*  Set Max and Min Columns to be 1... */
    layout.maxNumColumns = 1;
    layout.minNumColumns = 1;    
    form.getBody().setLayout(layout);
      

        
   this.jobRescComposite = createResourcesSection(managedForm,
                       Messages.getString("ResourcesPage_PageTitle"), //$NON-NLS-1$
                         Messages.getString("ResourcesPage_ResourcePageDescr")); //$NON-NLS-1$
   
   this.resourcesTypeAdapter.load();
   
   /* Set Form Background */
   form.setBackgroundImage(Activator.getDefault().
                           getImageRegistry().get( "formsbackground" )); //$NON-NLS-1$

  }
  


  /* Private method that creates the Resources Parent Section that 
   * is hosts all other Sub-Sections.    
   */
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
   
   } // End Composite createSection()
  

  /*
   * Private Method that creates the Candidate Hosts Sub-Section
   */
  private void CandidateHostsSubSection (final Composite composite, 
                                         final IManagedForm mform,
                                         final String title, final String desc, 
                                         final int numColumns, final int width,
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
    
    /* ========================= Host Name Widgets ===========================*/
    
    Label lblHostName = toolkit.createLabel(clientsubSection, 
                                  Messages.getString("ResourcesPage_HostName")); //$NON-NLS-1$
    GridData lblgd = new GridData();
    lblgd.verticalSpan = 3;
    lblgd.verticalAlignment = GridData.BEGINNING;
    lblHostName.setLayoutData( lblgd );
         
    this.lstHostName = new List(clientsubSection, SWT.NONE);
    this.lstHostName.setData( FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER );
    this.resourcesTypeAdapter.attachToHostName( this.lstHostName );
    GridData gdlst = new GridData();
    gdlst = new GridData();
    gdlst.verticalSpan = 3;
    gdlst.horizontalSpan = 1;
    gdlst.heightHint = this.WIDGET_HEIGHT;
    gdlst.widthHint = this.TXT_LENGTH;
    this.lstHostName.setLayoutData(gdlst);
 
    //Create the Add button
    GridData gdBut = new GridData();
    gdBut.verticalSpan = 2;
    gdBut.verticalAlignment = GridData.END;
    gdBut.horizontalAlignment = GridData.FILL;
    this.btnAdd = toolkit.createButton(clientsubSection,
                                    Messages.getString("JsdlEditor_AddButton"), //$NON-NLS-1$
                                    SWT.BUTTON1);  
    
    this.btnAdd.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent event) {
        handleAddDialog(Messages.getString( "ResourcesPage_HostNameDialog" )); //$NON-NLS-1$
        ResourcesPage.this.resourcesTypeAdapter.performAdd(ResourcesPage.this.lstHostName,
                                                           "lstJobProject", //$NON-NLS-1$
                                                           ResourcesPage.this.value);
      }

       public void widgetDefaultSelected(final SelectionEvent event) {
           // Do Nothing - Required method
       }
     });
    
    this.btnAdd.setLayoutData( gdBut);
    
    //Create the Remove button
    gdBut = new GridData();
    gdBut.verticalSpan = 1;
    gdBut.verticalAlignment = GridData.BEGINNING;
    gdBut.horizontalAlignment = GridData.FILL;
    this.btnDel = toolkit.createButton(clientsubSection, 
                                 Messages.getString("JsdlEditor_RemoveButton"), //$NON-NLS-1$
                                 SWT.BUTTON1);
    this.resourcesTypeAdapter.attachToDelete( this.btnDel, this.lstHostName );
    this.btnDel.setLayoutData( gdBut);
    
    toolkit.paintBordersFor( clientsubSection);
    
  } //End void CandidateHostsSubSection()
  
  
  
  /*
   * Private Method that creates the File System Sub-Section
   */
  private void FileSystemSubSection (final Composite composite, final IManagedForm mform,
                                     final String title, final String desc, 
                                     final int numColumns,
                                     final int width,
                                     final int height){
    
    FormToolkit toolkit = mform.getToolkit();
    
    Section subSection = toolkit.createSection(composite,
                                               ExpandableComposite.TITLE_BAR 
                                               | Section.DESCRIPTION 
                                               | SWT.WRAP);    
    subSection.setText(title);
    subSection.setDescription(desc);
    toolkit.createCompositeSeparator(subSection);
    
    GridData gd = new GridData(); 
    GridData lblgd = new GridData();
    
    gd.widthHint =  width;
    gd.heightHint = height;
    subSection.setLayoutData( gd );
    
    Composite clientsubSection = toolkit.createComposite(subSection);
    GridLayout gridlayout = new GridLayout();
    gridlayout.numColumns = numColumns;
    clientsubSection.setLayout(gridlayout);
    
    subSection.setClient( clientsubSection );
    
    lblgd.horizontalSpan = 1;
    
    /* ======================== File System Name Widgets =====================*/
    this.lblFileSystemName = toolkit.createLabel(clientsubSection,
                            Messages.getString("ResourcesPage_FileSystemName")); //$NON-NLS-1$
    
    this.lblFileSystemName.setLayoutData( lblgd );
    
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.widthHint = this.TXT_LENGTH;
    
    this.txtFileSystemName = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.resourcesTypeAdapter.attachToFileSystemName( this.txtFileSystemName );
    this.txtFileSystemName.setLayoutData(gd);
    
    
    /* ========================= Description Widgets =========================*/
    
    this.lblDescr = toolkit.createLabel(clientsubSection,
                               Messages.getString("ResourcesPage_Description")); //$NON-NLS-1$
    this.lblDescr.setLayoutData( lblgd );
    
   
    this.txtFileSystemDescr = toolkit.createText(clientsubSection, "", //$NON-NLS-1$
                                 SWT.NONE |SWT.H_SCROLL|SWT.V_SCROLL| SWT.WRAP); 
    this.resourcesTypeAdapter.attachToFileSystemDescription( this.txtFileSystemDescr );
    
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.widthHint = 285;
    gd.heightHint = this.WIDGET_HEIGHT;
    this.txtFileSystemDescr.setLayoutData(gd);
    
    /* ========================= Mount Point Widgets =========================*/
    this.lblMountPoint = toolkit.createLabel(clientsubSection,
                                Messages.getString("ResourcesPage_MountPoint")); //$NON-NLS-1$
    this.lblMountPoint.setLayoutData( lblgd );
    
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.widthHint = this.TXT_LENGTH;
    this.txtMountPoint = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.resourcesTypeAdapter.attachToFileSystemMountPoint( this.txtMountPoint );
    this.txtMountPoint.setLayoutData(gd);        
        
    gd = new GridData();
    gd.widthHint = 120;
   
    /* ======================== Disk Space Widgets ===========================*/
    this.lblDiskSpace = toolkit.createLabel(clientsubSection,
                                 Messages.getString("ResourcesPage_DiskSpace")); //$NON-NLS-1$
    
    this.lblDiskSpace.setLayoutData( lblgd );

    this.txtDiskSpace = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtDiskSpace.setLayoutData( gd );

    gd = new GridData();
    gd.widthHint = 175;
    this.cmbDiskSpaceRange = new Combo(clientsubSection, SWT.NONE | SWT.READ_ONLY);
    this.cmbDiskSpaceRange.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbDiskSpaceRange.setLayoutData( gd );
    
    /* ========================= File System Widgets =========================*/
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.widthHint = this.TXT_LENGTH + 5 ;
    this.lblFileSystemType = toolkit.createLabel(clientsubSection,
                               Messages.getString("ResourcesPage_FileSysType")); //$NON-NLS-1$
    
    lblgd.widthHint=120;
    this.lblFileSystemType.setLayoutData( lblgd);
    this.cmbFileSystemType = new Combo(clientsubSection, SWT.NONE | SWT.READ_ONLY);
    this.cmbFileSystemType.setData( FormToolkit.KEY_DRAW_BORDER );    
    this.resourcesTypeAdapter.attachToFileSystemType( this.cmbFileSystemType );
    this.cmbFileSystemType.setLayoutData(gd);
    
    this.resourcesTypeAdapter
      .attachToFileSystemDiskSpace( this.txtDiskSpace, this.cmbDiskSpaceRange );
    
    
    toolkit.paintBordersFor( clientsubSection);
    
  } //End void FileSystemSubSection()
  
  
  /*
   * Private Method that creates the Operating System Sub-Section
   */
  private void osSubSection (final Composite composite, final IManagedForm mform,
                             final String title, final String desc, 
                             final int numColumns,
                             final int width,
                             final int height)
  
  {
    FormToolkit toolkit = mform.getToolkit();
    
    Section subSection = toolkit.createSection(composite, 
                                               ExpandableComposite.TITLE_BAR 
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

    /*==================== Operating System Type Widgets =====================*/
    this.lblOperSystType = toolkit.createLabel(clientsubSection,
                              Messages.getString("ResourcesPage_OperSystType")); //$NON-NLS-1$
    this.cmbOperSystType = new Combo(clientsubSection, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
    this.cmbOperSystType.setData( FormToolkit.KEY_DRAW_BORDER);    
    this.resourcesTypeAdapter.attachToOSType( this.cmbOperSystType );
    this.cmbOperSystType.setLayoutData( gd );
    
    /*================= Operating System Version Widgets =====================*/
    
    this.lblOperSystVer = toolkit.createLabel(clientsubSection,
                           Messages.getString("ResourcesPage_OperSystVersion")); //$NON-NLS-1$
    this.txtOperSystVer = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.resourcesTypeAdapter.attachToOSVersion( this.txtOperSystVer );
    this.txtOperSystVer.setLayoutData(gd);
    
    /*========================== Description Widgets =========================*/
    
    gd = new GridData();
    this.lblDescrip = toolkit.createLabel(clientsubSection,
                                     Messages.getString("ResourcesPage_DEscr")); //$NON-NLS-1$
    this.txtOSDescr = toolkit.createText(clientsubSection, "", SWT.NONE//$NON-NLS-1$
                      |SWT.H_SCROLL |SWT.V_SCROLL | SWT.WRAP);
    this.resourcesTypeAdapter.attachToOSDescription( this.txtOSDescr );

    gd.widthHint = 265;
    gd.heightHint=this.WIDGET_HEIGHT;
    this.txtOSDescr.setLayoutData(gd);
    
    toolkit.paintBordersFor( clientsubSection);
  
  } // End void osSubSection()
  
  
  /*
   * Private Method that creates the CPU Architecture Sub-Section
   */
  private void cPUArch (final Composite composite, final IManagedForm mform,
                        final String title, final String desc, 
                        final int numColumns,
                        final int width,
                        final int height)
  {
    
    FormToolkit toolkit = mform.getToolkit();
    
    Section subSection = toolkit.createSection(composite,
                                               ExpandableComposite.TITLE_BAR 
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
    gd.widthHint = this.TXT_LENGTH-20;
    
    /*========================== CPU Architecture Widgets ====================*/
    
    this.lblCPUArchName = toolkit.createLabel(clientsubSection
                             , Messages.getString("ResourcesPage_CPUArchName")); //$NON-NLS-1$
    
    this.cmbCPUArchName = new Combo(clientsubSection, SWT.SIMPLE 
                                               | SWT.DROP_DOWN | SWT.READ_ONLY);
    
    this.cmbCPUArchName.setData( FormToolkit.KEY_DRAW_BORDER );    
    this.resourcesTypeAdapter.attachToCPUArchitecture( this.cmbCPUArchName );
    this.cmbCPUArchName.setLayoutData(gd);
    
    toolkit.paintBordersFor( clientsubSection);
    
  } //End void cPUArch()
  
  
  
  /*
   * Private Method that creates the Additional Elements Sub-Section
   */
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
    
    this.lblIndCPUSpl = toolkit.createLabel(clientsubSection,
                               Messages.getString("ResourcesPage_IndCPUSpeed")); //$NON-NLS-1$
    createCombo (clientsubSection);
    gd = new GridData();
    gd.widthHint = 120;
    this.txtIndCPUSp = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtIndCPUSp.setLayoutData(gd);
    
    this.lblIndCPUTime = toolkit.createLabel(clientsubSection,
                                Messages.getString("ResourcesPage_IndCPUTime")); //$NON-NLS-1$
    createCombo (clientsubSection);
    this.txtIndCPUTime = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtIndCPUTime.setLayoutData(gd);

    this.lblIndCPUCount = toolkit.createLabel(clientsubSection,
                                Messages.getString("ResourcesPage_IndCPUCount")); //$NON-NLS-1$
    createCombo (clientsubSection);
    this.txtIndCPUCount = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtIndCPUCount.setLayoutData(gd);
    
    this.lblIndNetBand = toolkit.createLabel(clientsubSection,
                          Messages.getString("ResourcesPage_IndNetwBandwidth")); //$NON-NLS-1$
    createCombo (clientsubSection);
    this.txtIndNetBand = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtIndNetBand.setLayoutData(gd);

    this.lblPhysMem = toolkit.createLabel(clientsubSection,
                                   Messages.getString("ResourcesPage_PhysMem")); //$NON-NLS-1$
    createCombo (clientsubSection);
    this.txtPhysMem = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtPhysMem.setLayoutData(gd);
    
    this.lblVirtMem = toolkit.createLabel(clientsubSection,
                                Messages.getString("ResourcesPage_VirtualMem")); //$NON-NLS-1$
    createCombo (clientsubSection);
    this.txtVirtMem = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtVirtMem.setLayoutData(gd);
    
    this.lblIndDiskSpac = toolkit.createLabel(clientsubSection,
                              Messages.getString("ResourcesPage_IndDiskSpace")); //$NON-NLS-1$
    createCombo (clientsubSection);
    this.txtIndDiskSpac = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtIndDiskSpac.setLayoutData(gd);

    this.lblCPUTime = toolkit.createLabel(clientsubSection,
                                   Messages.getString("ResourcesPage_CPUTime")); //$NON-NLS-1$
    createCombo (clientsubSection);
    this.txtCPUTime = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtCPUTime.setLayoutData(gd);

    this.lblCPUCount = toolkit.createLabel(clientsubSection,
                               Messages.getString("ResourcesPage_TotCPUCount")); //$NON-NLS-1$
    createCombo (clientsubSection);
    this.txtCPUCount = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtCPUCount.setLayoutData(gd);

    this.lblTotPhMem = toolkit.createLabel(clientsubSection,
                                Messages.getString("ResourcesPage_TotPhysMem")); //$NON-NLS-1$
    createCombo (clientsubSection);
    this.txtTotPhMem = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtTotPhMem.setLayoutData(gd);

    this.lblTotVirtMem = toolkit.createLabel(clientsubSection,
                             Messages.getString("ResourcesPage_TotVirtualMem")); //$NON-NLS-1$
    createCombo (clientsubSection);
    this.txtTotVirtMem = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtTotVirtMem.setLayoutData(gd);
   
    this.lblTotDiskSp = toolkit.createLabel(clientsubSection,
                              Messages.getString("ResourcesPage_TotDiskSpace")); //$NON-NLS-1$
    createCombo (clientsubSection);
    this.txtTotDiskSp = toolkit.createText(clientsubSection, "", SWT.NONE); //$NON-NLS-1$
    this.txtTotDiskSp.setLayoutData(gd);

    this.lblTotResCount = toolkit.createLabel(clientsubSection,
                               Messages.getString("ResourcesPage_TotRescCount")); //$NON-NLS-1$
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
                            Messages.getString("ResourcesPage_CanHost"), //$NON-NLS-1$
                            Messages.getString("ResourcesPage_CandHostDesc") //$NON-NLS-1$
                            ,3,480,270);
    
    FileSystemSubSection( client,mform,
                          Messages.getString("ResourcesPage_FileSystem"),  //$NON-NLS-1$
                          Messages.getString("ResourcesPage_FileSystemDesc"), //$NON-NLS-1$
                          3,480,270);
    
    osSubSection(client,mform,
                 Messages.getString("ResourcesPage_OperSyst"), //$NON-NLS-1$
                 Messages.getString("ResourcesPage_OperSystDescr"), //$NON-NLS-1$
                 2,480,220);
    
    cPUArch( client,mform,
             Messages.getString("ResourcesPage_CPUArch"), //$NON-NLS-1$
             Messages.getString("ResourcesPage_CPUArchDescr"), //$NON-NLS-1$
             2,480,210);
    
    addElementsSubSection( client,mform,
                         Messages.getString("ResourcesPage_AddElementRangeVal"), //$NON-NLS-1$
                 Messages.getString("ResourcesPage_AddElementsRangeValueDescr"), //$NON-NLS-1$
                 3,515,450 );
    
  
      
     return client;
}

  private Combo createCombo(final Composite composite){
    
    Combo comboRang = new Combo(composite,SWT.DROP_DOWN);
    comboRang.add(""); //$NON-NLS-1$
    comboRang.add(Messages.getString("ResourcesPage_LowBoundRange")); //$NON-NLS-1$
    comboRang.add(Messages.getString("ResourcesPage_UpBoundRange")); //$NON-NLS-1$
    comboRang.add(Messages.getString("ResourcesPage_Exact")); //$NON-NLS-1$
    GridData gd = new GridData();
    gd.widthHint = 170;
    comboRang.setLayoutData(gd);
    
    return comboRang;
    
  }
  
  
  protected void handleAddDialog(final String dialogTitle){
    MultipleInputDialog dialog = new MultipleInputDialog( this.getSite().getShell(),
                                                         dialogTitle );
        
    dialog.addTextField( Messages.getString( "ResourcesPage_Value" ), "", false ); //$NON-NLS-1$ //$NON-NLS-2$    
    if( dialog.open() != Window.OK ) {
      return;
    }
    this.value = dialog.getStringValue( Messages.getString( "ResourcesPage_Value" ) ) ; //$NON-NLS-1$
    
    
  }

  public void notifyChanged( Notification notification ) {
    setDirty( true );
    
  }

}
