/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
  *****************************************************************************/

package eu.geclipse.jsdl.ui.internal.pages;

/**
 * @author nickl
 *
 */

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;

import eu.geclipse.jsdl.ui.adapters.jsdl.JobDefinitionTypeAdapter;
import eu.geclipse.jsdl.ui.adapters.jsdl.JobIdentificationTypeAdapter;
import eu.geclipse.jsdl.ui.adapters.jsdl.ResourcesTypeAdapter;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.dialogs.MultipleInputDialog;


/**
 * This class provides the Resources Page page that appears in the JSDL editor.
 * It provides a graphical user interface to the following elements of a JSDL 
 * document:
 * 
 * 
 */
public final class ResourcesPage extends FormPage 
                                            implements INotifyChangedListener {
  
   
  protected static final String PAGE_ID = "RESOURCES";  //$NON-NLS-1$
  protected ResourcesTypeAdapter resourcesTypeAdapter;
  protected Object value = null;  
  protected Composite body = null;
  protected Composite jobRescComposite = null;
  protected Composite left = null;
  protected Composite right = null; 
  protected Label lblMountPoint = null;
  protected Label lblMountSource = null;
  protected Label lblDiskSpace = null;
  protected Label lblFileSystemType = null;
  protected Label lblFileSystemDescr = null;
  protected Label lblOperSystType = null;
  protected Label lblOperSystVer = null;
  protected Label lblOSDescr = null;
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
  
  
  
  /**
   * 
   * ResourcesPage class constructor. Initialiazes the Resources Page by 
   * passing as an argument the container JSDL editor.
   * @param editor
   * 
   */
  public ResourcesPage( final FormEditor editor )
                            
   {
   
    super(editor, PAGE_ID ,
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
  
  /**
   * Method that set's the Resources Page content. The content is the root 
   * JSDL element. Also this method is responsible to initialize the associated 
   * type adapters for the elements of this page. This method must be called only
   * from the JSDL Editor.
   * 
   * Associated Type Adapters for this page are: 
   * @see JobDefinitionTypeAdapter
   * @see JobIdentificationTypeAdapter
   *  
   * @param rootJsdlElement
   * 
   * @param refreshStatus
   * Set to TRUE if the original page content is already set, but there is a need
   * to refresh the page because there was a change to this content
   *  from an outside editor.
   * 
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

  
  /**
   * This method set's the dirty status of the page.
   * 
   * @param dirtyFlag
   * If TRUE then the page is Dirty and a Save operation is needed.
   * 
   */
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
    FormToolkit toolkit = managedForm.getToolkit();
    
    form.setText(Messages.getString("ResourcesPage_ResourcePageTitle"));  //$NON-NLS-1$
    
    this.body = form.getBody();
    this.body.setLayout(FormLayoutFactory.createFormTableWrapLayout(false, 2));
  
  
    
    this.left = toolkit.createComposite( this.body );
    this.left.setLayout(FormLayoutFactory.createFormPaneTableWrapLayout(false, 1));
    this.left.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
    
    this.right = toolkit.createComposite( this.body );
    this.right.setLayout(FormLayoutFactory.createFormPaneTableWrapLayout(false, 1));
    this.right.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
    
    
    createCandidateHostsSection ( this.left , toolkit );
   
    
    createFileSystemSection( this.right, toolkit );
    
    createOSSection( this.left , toolkit  );
    
    createCPUArch( this.right, toolkit );
    
    createAddElementsSection( this.left, toolkit );

   this.resourcesTypeAdapter.load();
   
   /* Set Form Background */
   form.setBackgroundImage(Activator.getDefault().
                           getImageRegistry().get( "formsbackground" )); //$NON-NLS-1$

  }
  


  

  /*
   * Private Method that creates the Candidate Hosts Sub-Section
   */
  private void createCandidateHostsSection (final Composite parent,
                                            final FormToolkit toolkit)
  
  {
    String sectionTitle = Messages.getString("ResourcesPage_CanHost"); //$NON-NLS-1$
    String sectionDescription = Messages.getString("ResourcesPage_CandHostDesc"); //$NON-NLS-1$
    
    GridData gd;
       
    Composite client = FormSectionFactory.createGridStaticSection(toolkit,
                                           parent,
                                           sectionTitle,
                                           sectionDescription,
                                           3);
    
    gd = new GridData();
    
    /* ========================= Host Name Widgets ===========================*/
    
    Label lblHostName = toolkit.createLabel(client, 
                                  Messages.getString("ResourcesPage_HostName")); //$NON-NLS-1$
    
    gd.verticalSpan = 3;
    gd.verticalAlignment = GridData.BEGINNING;
    lblHostName.setLayoutData( gd );
         
    this.lstHostName = new List(client, SWT.NONE);
    this.lstHostName.setData( FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER );
    this.resourcesTypeAdapter.attachToHostName( this.lstHostName );
    
    gd = new GridData(GridData.FILL_BOTH);
    gd = new GridData();
    gd.verticalSpan = 3;
    gd.horizontalSpan = 1;
    gd.heightHint = this.WIDGET_HEIGHT;
    gd.widthHint = this.TXT_LENGTH;
    this.lstHostName.setLayoutData(gd);
 
    //Create the Add button
    gd = new GridData();
    gd.verticalSpan = 2;
    gd.verticalAlignment = GridData.END;
    gd.horizontalAlignment = GridData.FILL;
    this.btnAdd = toolkit.createButton(client,
                                    Messages.getString("JsdlEditor_AddButton"), //$NON-NLS-1$
                                    SWT.BUTTON1);  
    
    this.btnAdd.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent event) {
        handleAddDialog(Messages.getString( "ResourcesPage_HostNameDialog" )); //$NON-NLS-1$
        ResourcesPage.this.resourcesTypeAdapter.performAdd(ResourcesPage.this.lstHostName,                                                          
                                                           ResourcesPage.this.value);
      }

       public void widgetDefaultSelected(final SelectionEvent event) {
           // Do Nothing - Required method
       }
     });
    
    this.btnAdd.setLayoutData( gd);
    
    //Create the Remove button
    gd = new GridData();
    gd.verticalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    gd.horizontalAlignment = GridData.FILL;
    this.btnDel = toolkit.createButton(client, 
                                 Messages.getString("JsdlEditor_RemoveButton"), //$NON-NLS-1$
                                 SWT.BUTTON1);
    
    this.btnDel.setEnabled( false );
    
    this.resourcesTypeAdapter.attachToDelete( this.btnDel, this.lstHostName );
    this.btnDel.setLayoutData( gd);
    
    toolkit.paintBordersFor( client);
    
  } //End void CandidateHostsSubSection()
  
  
  
  /*
   * Private Method that creates the File System Sub-Section
   */
  private void createFileSystemSection (final Composite parent,
                                        final FormToolkit toolkit){
    
 
    String sectionTitle = Messages.getString("ResourcesPage_FileSystem");  //$NON-NLS-1$
    String sectionDescription = Messages.getString("ResourcesPage_FileSystemDesc"); //$NON-NLS-1$
    
    GridData gd;
       
    Composite client = FormSectionFactory.createGridStaticSection(toolkit,
                                           parent,
                                           sectionTitle,
                                           sectionDescription,
                                           3);
    
     
    
    gd = new GridData(); 
    
   
    gd.horizontalSpan = 1;
    
    /* ======================== File System Name Widgets =====================*/
    this.lblFileSystemName = toolkit.createLabel(client,
                            Messages.getString("ResourcesPage_FileSystemName")); //$NON-NLS-1$
    
    this.lblFileSystemName.setLayoutData( gd );
    
    gd = new GridData(GridData.FILL_BOTH);
    gd.horizontalSpan = 2;
    gd.widthHint = this.TXT_LENGTH;
    
    this.txtFileSystemName = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    this.resourcesTypeAdapter.attachToFileSystemName( this.txtFileSystemName );
    this.txtFileSystemName.setLayoutData(gd);
    
    
    /* ========================= Description Widgets =========================*/
    
    gd = new GridData();
    gd.verticalAlignment = GridData.BEGINNING;
    this.lblFileSystemDescr = toolkit.createLabel(client,
                               Messages.getString("ResourcesPage_Description")); //$NON-NLS-1$
    this.lblFileSystemDescr.setLayoutData( gd );
    
   
    this.txtFileSystemDescr = toolkit.createText(client, "", //$NON-NLS-1$
                                 SWT.NONE |SWT.H_SCROLL|SWT.V_SCROLL| SWT.WRAP); 
    this.resourcesTypeAdapter.attachToFileSystemDescription( this.txtFileSystemDescr );
    
    gd = new GridData(GridData.FILL_BOTH);
    gd.horizontalSpan = 2;
    gd.widthHint = 285;
    gd.heightHint = this.WIDGET_HEIGHT;
    this.txtFileSystemDescr.setLayoutData(gd);
    
    /* ========================= Mount Point Widgets =========================*/
    gd = new GridData();
    this.lblMountPoint = toolkit.createLabel(client,
                                Messages.getString("ResourcesPage_MountPoint")); //$NON-NLS-1$
    this.lblMountPoint.setLayoutData( gd );
    
    gd = new GridData(GridData.FILL_BOTH);
    gd.horizontalSpan = 2;
    gd.widthHint = this.TXT_LENGTH;
    this.txtMountPoint = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    this.resourcesTypeAdapter.attachToFileSystemMountPoint( this.txtMountPoint );
    this.txtMountPoint.setLayoutData(gd);        
        
    gd = new GridData();
    gd.widthHint = 120;
   
    /* ======================== Disk Space Widgets ===========================*/
    this.lblDiskSpace = toolkit.createLabel(client,
                                 Messages.getString("ResourcesPage_DiskSpace")); //$NON-NLS-1$
    
    this.lblDiskSpace.setLayoutData( gd );

    this.txtDiskSpace = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    this.txtDiskSpace.setLayoutData( gd );

    gd = new GridData(GridData.FILL_BOTH);
    gd.widthHint = 175;
    this.cmbDiskSpaceRange = new Combo(client, SWT.NONE | SWT.READ_ONLY);
    this.cmbDiskSpaceRange.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbDiskSpaceRange.setLayoutData( gd );
    
    /* ========================= File System Widgets =========================*/
    gd = new GridData();
    gd.horizontalSpan = 1;
    gd.widthHint = this.TXT_LENGTH + 5 ;
    this.lblFileSystemType = toolkit.createLabel(client,
                               Messages.getString("ResourcesPage_FileSysType")); //$NON-NLS-1$
    
    gd.widthHint=120;
    this.lblFileSystemType.setLayoutData( gd);
    
    gd = new GridData(GridData.FILL_BOTH);
    gd.horizontalSpan = 2;
    this.cmbFileSystemType = new Combo(client, SWT.NONE | SWT.READ_ONLY);
    this.cmbFileSystemType.setData( FormToolkit.KEY_DRAW_BORDER );    
    this.resourcesTypeAdapter.attachToFileSystemType( this.cmbFileSystemType );
    this.cmbFileSystemType.setLayoutData(gd);
    
    this.resourcesTypeAdapter
      .attachToFileSystemDiskSpace( this.txtDiskSpace, this.cmbDiskSpaceRange );
    
    
    toolkit.paintBordersFor( client);
    
  } //End void FileSystemSubSection()
  
  
  /*
   * Private Method that creates the Operating System Sub-Section
   */
  private void createOSSection (final Composite parent,
                                final FormToolkit toolkit)
  
  {
    String sectionTitle = Messages.getString("ResourcesPage_OperSyst"); //$NON-NLS-1$
    String sectionDescription = Messages.getString("ResourcesPage_OperSystDescr"); //$NON-NLS-1$
    
    GridData gd;
       
    Composite client = FormSectionFactory.createGridStaticSection(toolkit,
                                           parent,
                                           sectionTitle,
                                           sectionDescription,
                                           2);
      

    
    gd = new GridData();
    gd.widthHint = 280;    

    /*==================== Operating System Type Widgets =====================*/
    this.lblOperSystType = toolkit.createLabel(client,
                              Messages.getString("ResourcesPage_OperSystType")); //$NON-NLS-1$
    this.cmbOperSystType = new Combo(client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY);
    this.cmbOperSystType.setData( FormToolkit.KEY_DRAW_BORDER);    
    this.resourcesTypeAdapter.attachToOSType( this.cmbOperSystType );
    this.cmbOperSystType.setLayoutData( gd );
    
    /*================= Operating System Version Widgets =====================*/
    
    this.lblOperSystVer = toolkit.createLabel(client,
                           Messages.getString("ResourcesPage_OperSystVersion")); //$NON-NLS-1$
    this.txtOperSystVer = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    this.resourcesTypeAdapter.attachToOSVersion( this.txtOperSystVer );
    this.txtOperSystVer.setLayoutData(gd);
    
    /*========================== Description Widgets =========================*/
    
    gd = new GridData();
    gd.verticalAlignment = GridData.BEGINNING;
    this.lblOSDescr = toolkit.createLabel(client,
                                     Messages.getString("ResourcesPage_DEscr")); //$NON-NLS-1$
    this.lblOSDescr.setLayoutData( gd );
    gd = new GridData();
    this.txtOSDescr = toolkit.createText(client, "", SWT.NONE//$NON-NLS-1$
                      |SWT.H_SCROLL |SWT.V_SCROLL | SWT.WRAP);
    this.resourcesTypeAdapter.attachToOSDescription( this.txtOSDescr );

    gd.widthHint = 265;
    gd.heightHint=this.WIDGET_HEIGHT;
    this.txtOSDescr.setLayoutData(gd);
    
    toolkit.paintBordersFor( client);
  
  } // End void osSubSection()
  
  
  /*
   * Private Method that creates the CPU Architecture Sub-Section
   */
  private void createCPUArch (final Composite parent,
                              final FormToolkit toolkit)
  {
    
    String sectionTitle = Messages.getString("ResourcesPage_CPUArch"); //$NON-NLS-1$
    String sectionDescription = Messages.getString("ResourcesPage_CPUArchDescr"); //$NON-NLS-1$

    TableWrapData td;
       
    Composite client = FormSectionFactory.createStaticSection(toolkit,
                                           parent,
                                           sectionTitle,
                                           sectionDescription,
                                           2);
      

    
    

     td = new TableWrapData(TableWrapData.FILL_GRAB);
    
    /*========================== CPU Architecture Widgets ====================*/
    
    this.lblCPUArchName = toolkit.createLabel(client
                             , Messages.getString("ResourcesPage_CPUArchName")); //$NON-NLS-1$
    
    this.cmbCPUArchName = new Combo(client, SWT.SIMPLE 
                                               | SWT.DROP_DOWN | SWT.READ_ONLY);
    
    this.cmbCPUArchName.setData( FormToolkit.KEY_DRAW_BORDER );    
    this.resourcesTypeAdapter.attachToCPUArchitecture( this.cmbCPUArchName );
    this.cmbCPUArchName.setLayoutData(td);
    
    toolkit.paintBordersFor( client);    
    
  } //End void cPUArch()
  
  
  
  /*
   * Private Method that creates the Additional Elements Sub-Section
   */
  private void createAddElementsSection (final Composite parent,
                                      final FormToolkit toolkit)
  {
   
    String sectionTitle = Messages.getString("ResourcesPage_AddElementRangeVal"); //$NON-NLS-1$
    String sectionDescription = Messages.getString("ResourcesPage_AddElementsRangeValueDescr"); //$NON-NLS-1$

    TableWrapData td;
       
    Composite client = FormSectionFactory.createExpandableSection(toolkit,
                                           parent,
                                           sectionTitle,
                                           sectionDescription,
                                           3,
                                           false);
    
    
    
    /*==========================  =========================*/

    td = new TableWrapData(TableWrapData.FILL_GRAB);
    
    this.lblIndCPUSpl = toolkit.createLabel(client,
                               Messages.getString("ResourcesPage_IndCPUSpeed")); //$NON-NLS-1$
    createCombo (client);
    this.txtIndCPUSp = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    this.txtIndCPUSp.setLayoutData(td);
    
    /*==========================  =========================*/
        
    td = new TableWrapData(TableWrapData.FILL_GRAB);
    this.lblIndCPUTime = toolkit.createLabel(client,
                                Messages.getString("ResourcesPage_IndCPUTime")); //$NON-NLS-1$
    createCombo (client);
    this.txtIndCPUTime = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$    
    this.txtIndCPUTime.setLayoutData(td);

    /*==========================  =========================*/
    
    td = new TableWrapData(TableWrapData.FILL_GRAB);
    this.lblIndCPUCount = toolkit.createLabel(client,
                                Messages.getString("ResourcesPage_IndCPUCount")); //$NON-NLS-1$
    createCombo (client);
    this.txtIndCPUCount = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$

    this.txtIndCPUCount.setLayoutData(td);

    /*==========================  =========================*/
    
    td = new TableWrapData(TableWrapData.FILL_GRAB);
    this.lblIndNetBand = toolkit.createLabel(client,
                          Messages.getString("ResourcesPage_IndNetwBandwidth")); //$NON-NLS-1$
    createCombo (client);
    this.txtIndNetBand = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    this.txtIndNetBand.setLayoutData(td);
    
    /*==========================  =========================*/
    
    td = new TableWrapData(TableWrapData.FILL_GRAB);
    this.lblPhysMem = toolkit.createLabel(client,
                                   Messages.getString("ResourcesPage_PhysMem")); //$NON-NLS-1$
    createCombo (client);
    this.txtPhysMem = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$

    this.txtPhysMem.setLayoutData(td);
    
    /*==========================  =========================*/
    
    td = new TableWrapData(TableWrapData.FILL_GRAB);
    this.lblVirtMem = toolkit.createLabel(client,
                                Messages.getString("ResourcesPage_VirtualMem")); //$NON-NLS-1$
    createCombo (client);
    this.txtVirtMem = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$

    this.txtVirtMem.setLayoutData(td);
    
    /*==========================  =========================*/
    
    td = new TableWrapData(TableWrapData.FILL_GRAB);
    this.lblIndDiskSpac = toolkit.createLabel(client,
                              Messages.getString("ResourcesPage_IndDiskSpace")); //$NON-NLS-1$
    createCombo (client);
    this.txtIndDiskSpac = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    this.txtIndDiskSpac.setLayoutData(td);

    /*==========================  =========================*/
    
    td = new TableWrapData(TableWrapData.FILL_GRAB);
    this.lblCPUTime = toolkit.createLabel(client,
                                   Messages.getString("ResourcesPage_CPUTime")); //$NON-NLS-1$
    createCombo (client);
    this.txtCPUTime = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    this.txtCPUTime.setLayoutData(td);
    
    /*==========================  =========================*/
    
    td = new TableWrapData(TableWrapData.FILL_GRAB);
    this.lblCPUCount = toolkit.createLabel(client,
                               Messages.getString("ResourcesPage_TotCPUCount")); //$NON-NLS-1$
    createCombo (client);
    this.txtCPUCount = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    this.txtCPUCount.setLayoutData(td);
    
    /*==========================  =========================*/
    
    td = new TableWrapData(TableWrapData.FILL_GRAB);
    this.lblTotPhMem = toolkit.createLabel(client,
                                Messages.getString("ResourcesPage_TotPhysMem")); //$NON-NLS-1$
    createCombo (client);
    this.txtTotPhMem = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    this.txtTotPhMem.setLayoutData(td);

    /*==========================  =========================*/
    
    td = new TableWrapData(TableWrapData.FILL_GRAB);
    this.lblTotVirtMem = toolkit.createLabel(client,
                             Messages.getString("ResourcesPage_TotVirtualMem")); //$NON-NLS-1$
    createCombo (client);
    this.txtTotVirtMem = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$    
    this.txtTotVirtMem.setLayoutData(td);
   
    /*==========================  =========================*/
    
    td = new TableWrapData(TableWrapData.FILL_GRAB);
    this.lblTotDiskSp = toolkit.createLabel(client,
                              Messages.getString("ResourcesPage_TotDiskSpace")); //$NON-NLS-1$
    createCombo (client);
    this.txtTotDiskSp = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    this.txtTotDiskSp.setLayoutData(td);

    /*==========================  =========================*/
    
    td = new TableWrapData(TableWrapData.FILL_GRAB);
    this.lblTotResCount = toolkit.createLabel(client,
                               Messages.getString("ResourcesPage_TotRescCount")); //$NON-NLS-1$
    createCombo (client);
    this.txtTotResCount = toolkit.createText(client, "", SWT.NONE); //$NON-NLS-1$
    this.txtTotResCount.setLayoutData(td);

    toolkit.paintBordersFor( client );
    
  }
  
  /* 
   * Create the Resources Section
   * 
   */
  /*
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
*/

  private Combo createCombo(final Composite composite){
    
    Combo comboRang = new Combo(composite,SWT.DROP_DOWN);
    comboRang.add(""); //$NON-NLS-1$
    comboRang.add(Messages.getString("ResourcesPage_LowBoundRange")); //$NON-NLS-1$
    comboRang.add(Messages.getString("ResourcesPage_UpBoundRange")); //$NON-NLS-1$
    comboRang.add(Messages.getString("ResourcesPage_Exact")); //$NON-NLS-1$
    TableWrapData td = new TableWrapData(TableWrapData.FILL_GRAB);    
    comboRang.setLayoutData(td);
    
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
