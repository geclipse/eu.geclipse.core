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


import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridRoot;
import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.ui.adapters.jsdl.JobDefinitionTypeAdapter;
import eu.geclipse.jsdl.ui.adapters.jsdl.JobIdentificationTypeAdapter;
import eu.geclipse.jsdl.ui.adapters.jsdl.ResourcesTypeAdapter;
import eu.geclipse.jsdl.ui.editors.JsdlEditor;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.dialogs.CandidateHostsDialog;
import eu.geclipse.jsdl.ui.internal.dialogs.FileSystemsDialog;
import eu.geclipse.jsdl.ui.internal.pages.sections.AdditionalResourceElemetsSection;
import eu.geclipse.jsdl.ui.internal.pages.sections.CandidateHostsSection;
import eu.geclipse.jsdl.ui.internal.pages.sections.CpuArchitectureSection;
import eu.geclipse.jsdl.ui.internal.pages.sections.ExclusiveExecutionSection;
import eu.geclipse.jsdl.ui.internal.pages.sections.FileSystemSection;
import eu.geclipse.jsdl.ui.internal.pages.sections.OperatingSystemSection;
import eu.geclipse.jsdl.ui.providers.FeatureContentProvider;
import eu.geclipse.jsdl.ui.providers.FeatureLabelProvider;
import eu.geclipse.ui.widgets.NumberVerifier;


/**
 * This class provides the Resources page that appears in the JSDL editor.
 */
public final class ResourcesPage extends FormPage implements INotifyChangedListener {
  
   
  protected static final String PAGE_ID = "RESOURCES";  //$NON-NLS-1$
  private static final int TXT_LENGTH = 300;
  private static final int WIDGET_HEIGHT = 100;
  private static final String[] RESOURCES_BOUNDARY_ITEMS = { "", //$NON-NLS-1$
                             Messages.getString( "ResourcesPage_LowBoundRange" ).trim(),  //$NON-NLS-1$
                             Messages.getString( "ResourcesPage_UpBoundRange" ).trim(), //$NON-NLS-1$
                             Messages.getString( "ResourcesPage_Exact" ).trim()}; //$NON-NLS-1$ 
  
  protected JobDefinitionType jobDefinitionType;
  protected ResourcesTypeAdapter resourcesTypeAdapter;
  protected Object[] value = null;  
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
  protected Label lblExclExecution = null;
  protected Combo cmbIndividualCPUSpeed = null;
  protected Combo cmbIndividualCPUTime = null;
  protected Combo cmbIndividualCPUCount = null;
  protected Combo cmbIndividualNetworkBandwidth = null;
  protected Combo cmbIndividualPhysicalMemory = null;
  protected Combo cmbIndividualVirtualMesmory = null;
  protected Combo cmbIndividualDiskSpace = null;
  protected Combo cmbTotalCPUTime = null;
  protected Combo cmbTotalCPUCount = null;
  protected Combo cmbTotalPhysicalMemory = null;
  protected Combo cmbTotalVirtualMemory = null;
  protected Combo cmbTotalDiskSpace = null;
  protected Combo cmbTotalResourceCount = null;  
  protected Button btnHostsAdd = null;
  protected Button btnHostsDel = null;
  protected Button btnFileSystemAdd = null;
  protected Button btnFileSystemDel = null;
  protected Button btnFileSystemEdit = null;  
  protected TableViewer hostsViewer = null;
  protected TableViewer fileSystemsViewer = null; 

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
  private Combo cmbExclExec = null;  
  private ImageDescriptor helpDesc = null; 
  private Table tblFileSystems = null;
  private TableColumn column = null;
  private boolean contentRefreshed = false;
  private boolean dirtyFlag = false;
  private AdditionalResourceElemetsSection additionalResourceElemetsSection = null;
  private CandidateHostsSection candidateHostsSection = null;
  private OperatingSystemSection operatingSystemSection = null;
  private CpuArchitectureSection cpuArchitectureSection = null;
  private FileSystemSection fileSystemSection = null;
  private ExclusiveExecutionSection exclusiveExecutionSection = null;
  
  
  /**
   * <code>ResourcesPage</code> class constructor. Creates the page by
   * passing as an argument the container JSDL editor.
   * 
   * @param editor The JSDL editor.
   */
  public ResourcesPage( final FormEditor editor ) {
   
    super( editor, PAGE_ID ,
           Messages.getString("ResourcesPage_PageTitle") ); //$NON-NLS-1$
        
  } // End Class Constructor.
  
  
  
  /**
   * Returns the instance of the JSDL Editor that contains this page  
   *
   * @return JSDL editor that contains this page. 
   */
  public JsdlEditor getParentEditor() {
    return ( JsdlEditor )getEditor();
  }

  
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.forms.editor.FormPage#setActive(boolean)
   */
  @Override
  public void setActive(final boolean active) {
    
    if ( active ){
      if ( isContentRefreshed() ) {    
//        this.resourcesTypeAdapter.load();
        this.candidateHostsSection.setInput( this.jobDefinitionType );
        this.additionalResourceElemetsSection.setInput( this.jobDefinitionType );
        this.operatingSystemSection.setInput( this.jobDefinitionType );
        this.cpuArchitectureSection.setInput( this.jobDefinitionType );
        this.fileSystemSection.setInput( this.jobDefinitionType );
        this.exclusiveExecutionSection.setInput( this.jobDefinitionType );
        
      } // end_if (isContentRefreshed())
    } //  end_if (active)
    
  } // End void setActive()
  
  
  
  /*
   * Checks if the Page Content has been refreshed. 
   */
  private boolean isContentRefreshed() {
    
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
   * @param jobDefinitionRoot
   * 
   * @param refreshStatus
   * Set to TRUE if the original page content is already set, but there is a need
   * to refresh the page because there was a change to this content
   *  from an outside editor.
   * 
   */
  public void setPageContent( final JobDefinitionType jobDefinitionRoot, 
                              final boolean refreshStatus ){

   if ( refreshStatus ) {
      this.contentRefreshed = true;
      this.jobDefinitionType = jobDefinitionRoot;
//      this.resourcesTypeAdapter.setContent( jobDefinitionRoot );
//      this.additionalResourceElemetsSection.setInput( jobDefinitionRoot );
    }
   else{
      this.jobDefinitionType = jobDefinitionRoot;
//      this.resourcesTypeAdapter = new ResourcesTypeAdapter(jobDefinitionRoot);  
//      this.resourcesTypeAdapter.addListener( this );
   }
          
  } // End void getPageContent() 
  
  
  
  @Override
  public boolean isDirty() {
    
    return this.dirtyFlag;
    
  }

  
  
  /**
   * This method set's the dirty status of the page.
   * 
   * @param dirty TRUE when the page is Dirty (content has been changed) and hence a 
   * Save operation is needed.
   * 
   */
  public void setDirty( final boolean dirty ) {
    if ( this.dirtyFlag != dirty ) {
      this.dirtyFlag = dirty;     
      this.getEditor().editorDirtyStateChanged();  
    }
    
  }

 
  
  @Override
  /*
   * This method is used to create the Forms content by
   * creating the form layout and then creating the form
   * Sub-Sections
   */
  protected void createFormContent( final IManagedForm managedForm ) {
            
    ScrolledForm form = managedForm.getForm();    
    FormToolkit toolkit = managedForm.getToolkit();
    
    form.setText( Messages.getString( "ResourcesPage_ResourcePageTitle") );  //$NON-NLS-1$
    
    this.body = form.getBody();
    this.body.setLayout( FormLayoutFactory.createFormTableWrapLayout( false, 2 ) );
  
  
    
    this.left = toolkit.createComposite( this.body );
    this.left.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout (false, 1) );
    this.left.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
    
    this.right = toolkit.createComposite( this.body );
    this.right.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1 ) );
    this.right.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );
    
    /* Create the Candidate Hosts Section */
//    createCandidateHostsSection ( this.left , toolkit );
    this.candidateHostsSection = new CandidateHostsSection(this, this.left, toolkit);
    this.candidateHostsSection.setInput( this.jobDefinitionType );
    this.candidateHostsSection.addListener( this );
//   
//    /* Create the Operating System Section */
//    createOSSection( this.left , toolkit  );
      this.operatingSystemSection = new OperatingSystemSection(this.left, toolkit);
      this.operatingSystemSection.setInput( this.jobDefinitionType );
      this.operatingSystemSection.addListener( this );
//    
//    /* Create the File System Section */
//    createFileSystemSection( this.left, toolkit );
      
      this.fileSystemSection = new FileSystemSection(this.left, toolkit);
      this.fileSystemSection.setInput( this.jobDefinitionType );
      this.fileSystemSection.addListener( this );
//    
//    /* Create the CPU Architecture Section */
//    createCPUArch( this.right, toolkit );
      this.cpuArchitectureSection = new CpuArchitectureSection(this.right, toolkit);
      this.cpuArchitectureSection.setInput( this.jobDefinitionType );
      this.cpuArchitectureSection.addListener( this );
//    
//    /* Create the Exclusive Execution Section */
//    createExclusiveExecutionSection( this.right, toolkit );
      this.exclusiveExecutionSection = new ExclusiveExecutionSection(this.right, toolkit);
      this.exclusiveExecutionSection.setInput( this.jobDefinitionType );
      this.exclusiveExecutionSection.addListener( this );
      
      
    
    /* Create the Additional Elements Section */
//    createAddElementsSection( this.right, toolkit );
    this.additionalResourceElemetsSection = new AdditionalResourceElemetsSection(this.right, toolkit);
    this.additionalResourceElemetsSection.setInput( this.jobDefinitionType );
    this.additionalResourceElemetsSection.addListener( this );
//   this.resourcesTypeAdapter.load();
   
   /* Set Form Background */
   form.setBackgroundImage( Activator.getDefault().
                            getImageRegistry().get( "formsbackground" ) ); //$NON-NLS-1$

   /* Also add the help system */
   addFormPageHelp( form );
   
  }
  

  
  /*
   * Private Method that creates the Candidate Hosts Sub-Section
   */
  private void createCandidateHostsSection ( final Composite parent,
                                             final FormToolkit toolkit)
  
  {
    String sectionTitle = Messages.getString( "ResourcesPage_CanHost" ); //$NON-NLS-1$txtFileSystemName
    String sectionDescription = Messages.getString( "ResourcesPage_CandHostDesc" ); //$NON-NLS-1$
    
    GridData gd;
       
    Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                           parent,
                                           sectionTitle,
                                           sectionDescription,
                                           2 );
    
    gd = new GridData();
    
    /* ========================= Host Name Widgets ===========================*/
   
    
    Table tblHosts = new Table( client, SWT.BORDER | SWT.H_SCROLL 
                                      | SWT.V_SCROLL | SWT.MULTI );
    gd = new GridData( GridData.FILL_BOTH );
    gd.grabExcessHorizontalSpace = true;
    gd.grabExcessVerticalSpace = true;
    gd.verticalSpan = 5;
    gd.horizontalSpan = 1;
    gd.heightHint = ResourcesPage.WIDGET_HEIGHT;
    gd.widthHint = ResourcesPage.TXT_LENGTH;

    tblHosts.setLayoutData( gd );
    
    //FIXME This is a work-around for the Bug#: 201705 for Windows.
    this.hostsViewer = new TableViewer( tblHosts );
    tblHosts = this.hostsViewer.getTable();    
    this.hostsViewer.setContentProvider( new FeatureContentProvider() );
    this.hostsViewer.setLabelProvider( new FeatureLabelProvider() );
        
    this.hostsViewer.addSelectionChangedListener( new ISelectionChangedListener()
    {

      public void selectionChanged( final SelectionChangedEvent event ) {
        updateButtons( ( TableViewer )event.getSource() );
      }
    } );
    
    
    tblHosts.setData(  FormToolkit.KEY_DRAW_BORDER );


    this.resourcesTypeAdapter.attachToHostName( this.hostsViewer );
    
    /* Create the Add button */
    gd = new GridData();
    gd.verticalSpan = 2;
    gd.verticalAlignment = GridData.END;
    gd.horizontalAlignment = GridData.FILL;
    this.btnHostsAdd = toolkit.createButton( client,
                                        Messages.getString( "JsdlEditor_AddButton" ), //$NON-NLS-1$
                                        SWT.BUTTON1);  
    
    this.btnHostsAdd.addSelectionListener( new SelectionListener() {
      public void widgetSelected( final SelectionEvent event ) {
        handleAddDialog(Messages.getString( "ResourcesPage_NewHostNameDialog" ) ); //$NON-NLS-1$
                                                    
        ResourcesPage.this.resourcesTypeAdapter.addCandidateHosts(ResourcesPage.this.hostsViewer,                                                          
                                                           ResourcesPage.this.value );
      }

       public void widgetDefaultSelected( final SelectionEvent event ) {
           // Do Nothing - Required method
       }
     });
    
    this.btnHostsAdd.setLayoutData( gd);
    
    
    /* Create the Remove button */
    gd = new GridData();
    gd.verticalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    gd.horizontalAlignment = GridData.FILL;
    this.btnHostsDel = toolkit.createButton(client, 
                                 Messages.getString( "JsdlEditor_RemoveButton" ), //$NON-NLS-1$
                                 SWT.BUTTON1 );
    
    this.btnHostsDel.setEnabled( false );
    
    this.resourcesTypeAdapter.attachToDelete( this.btnHostsDel, this.hostsViewer );
    this.btnHostsDel.setLayoutData( gd);
        
    toolkit.paintBordersFor( client );
    
    updateButtons( this.hostsViewer );
    
  } //End void CandidateHostsSubSection()
  
 
   
  private void createFileSystemSection  ( final Composite parent,
                                          final FormToolkit toolkit ) {
    
    String sectionTitle = Messages.getString( "ResourcesPage_FileSystem") ;  //$NON-NLS-1$
    String sectionDescription = Messages.getString( "ResourcesPage_FileSystemDesc" ); //$NON-NLS-1$
    

       
    Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                                               parent,
                                                               sectionTitle,
                                                               sectionDescription,
                                                               2 );
    GridData gd;
    gd = new GridData();
    
    /* ========================= File System Widgets ===========================*/
    
   
    
    this.tblFileSystems = new Table( client, SWT.BORDER | SWT.H_SCROLL 
                                            | SWT.V_SCROLL | SWT.MULTI );
    gd = new GridData( GridData.FILL_BOTH );
    gd.grabExcessHorizontalSpace = true;
    gd.grabExcessVerticalSpace = true;
    gd.verticalSpan = 5;
    gd.horizontalSpan = 1;
    gd.heightHint = ResourcesPage.WIDGET_HEIGHT;
    gd.widthHint = ResourcesPage.TXT_LENGTH;
    
    this.tblFileSystems.setLayoutData( gd );
    
    //FIXME This is a work-around for the Bug#: 201705 for Windows.
    this.fileSystemsViewer = new TableViewer( this.tblFileSystems );
    this.tblFileSystems = this.fileSystemsViewer.getTable();    
    this.fileSystemsViewer.setContentProvider( new FeatureContentProvider() );
    this.fileSystemsViewer.setLabelProvider( new FeatureLabelProvider() );
    this.tblFileSystems.setHeaderVisible( true );
    this.column = new TableColumn( this.tblFileSystems, SWT.NONE );
    this.column.setText( "Name" ); //$NON-NLS-1$
    this.column.setWidth( 150 );
    this.column = new TableColumn( this.tblFileSystems, SWT.NONE );
    this.column.setText( "Type" ); //$NON-NLS-1$
    this.column.setWidth( 60 );
    this.column = new TableColumn( this.tblFileSystems, SWT.NONE );
    this.column.setText( "Mount Point" ); //$NON-NLS-1$
    this.column.setWidth( 60 );
        
    this.fileSystemsViewer.addSelectionChangedListener( new ISelectionChangedListener()
    {

      public void selectionChanged( final SelectionChangedEvent event ) {
        updateButtons( ( TableViewer )event.getSource() );
      }
    } );
    
    

    this.tblFileSystems.setData(  FormToolkit.KEY_DRAW_BORDER );


    this.resourcesTypeAdapter.attachToFileSystems( this.fileSystemsViewer );
    
    /* Create the Add button */
    gd = new GridData();
    gd.verticalSpan = 2;
    gd.verticalAlignment = GridData.END;
    gd.horizontalAlignment = GridData.FILL;
    
    this.btnFileSystemAdd = toolkit.createButton( client,
                                        Messages.getString( "JsdlEditor_AddButton" ), //$NON-NLS-1$
                                        SWT.BUTTON1);  
    
    this.btnFileSystemAdd.addSelectionListener( new SelectionListener() {
      public void widgetSelected( final SelectionEvent event ) {
        handleAddFsDialog(Messages.getString( "ResourcesPage_NewFileSystemsDialog" ), //$NON-NLS-1$
                                                 ( Button ) event.getSource() ); 
                
        ResourcesPage.this.resourcesTypeAdapter.addFileSystem(ResourcesPage.this.fileSystemsViewer,                                                          
                                                           ResourcesPage.this.value );
      }

       public void widgetDefaultSelected( final SelectionEvent event ) {
           // Do Nothing - Required method
       }
     });
    
    this.btnFileSystemAdd.setLayoutData( gd );
    
    //FIXME Un-comment for Edit Functionality
    
    /* Create the Edit button */
    gd = new GridData();
    gd.verticalSpan = 2;
    gd.verticalAlignment = GridData.END;
    gd.horizontalAlignment = GridData.FILL;
    this.btnFileSystemEdit = toolkit.createButton(client,
                                    Messages.getString("JsdlEditor_EditButton"), //$NON-NLS-1$
                                    SWT.BUTTON1);  
    
    this.btnFileSystemEdit.addSelectionListener(new SelectionListener() {
      public void widgetSelected(final SelectionEvent event) {
        handleAddFsDialog( Messages.getString( "ResourcesPage_EditHostNameDialog" ), //$NON-NLS-1$
                                                    (Button) event.getSource()); 
        
//        ResourcesPage.this.resourcesTypeAdapter.performEdit(ResourcesPage.this.fileSystemsViewer,                                                          
//                                                           ResourcesPage.this.value[0]);
      }

       public void widgetDefaultSelected(final SelectionEvent event) {
           // Do Nothing - Required method
       }
     });
    this.btnFileSystemEdit.setEnabled( false );
    this.btnFileSystemEdit.setLayoutData( gd );
    
    
    /* Create the Remove button */
    gd = new GridData();
    gd.verticalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    gd.horizontalAlignment = GridData.FILL;
    
    this.btnFileSystemDel = toolkit.createButton(client, 
                                 Messages.getString( "JsdlEditor_RemoveButton" ), //$NON-NLS-1$
                                 SWT.BUTTON1 );
    
    this.btnFileSystemDel.setEnabled( false );
//    this.resourcesTypeAdapter.attachToDelete( this.btnFileSystemDel, this.fileSystemsViewer );
    this.btnFileSystemDel.setLayoutData( gd );
        
    toolkit.paintBordersFor( client );
    
  } //End void FileSystemsSection()
   
  
  
  /*
   * Private Method that creates the Operating System Sub-Section
   */
  private void createOSSection ( final Composite parent,
                                 final FormToolkit toolkit ) {
    
    String sectionTitle = Messages.getString( "ResourcesPage_OperSyst" ); //$NON-NLS-1$
    String sectionDescription = Messages.getString( "ResourcesPage_OperSystDescr" ); //$NON-NLS-1$
    
    GridData gd;
       
    Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                           parent,
                                           sectionTitle,
                                           sectionDescription,
                                           2 );
          
    gd = new GridData();
    gd.widthHint = 280;    

    /*==================== Operating System Type Widgets =====================*/
    this.lblOperSystType = toolkit.createLabel( client,
                              Messages.getString( "ResourcesPage_OperSystType" ) ); //$NON-NLS-1$
    this.cmbOperSystType = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbOperSystType.setData( FormToolkit.KEY_DRAW_BORDER );
//    this.resourcesTypeAdapter.attachToOSType( this.cmbOperSystType );
    this.cmbOperSystType.setLayoutData( gd );
    
    /*================= Operating System Version Widgets =====================*/
    
    this.lblOperSystVer = toolkit.createLabel( client,
                           Messages.getString( "ResourcesPage_OperSystVersion" ) ); //$NON-NLS-1$
    this.txtOperSystVer = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
//    this.resourcesTypeAdapter.attachToOSVersion( this.txtOperSystVer );
    this.txtOperSystVer.setLayoutData( gd );
    
    /*========================== Description Widgets =========================*/
    
    gd = new GridData();
    gd.verticalAlignment = GridData.BEGINNING;
    this.lblOSDescr = toolkit.createLabel(client,
                                     Messages.getString("ResourcesPage_DEscr")); //$NON-NLS-1$
    this.lblOSDescr.setLayoutData( gd );
    gd = new GridData();
    this.txtOSDescr = toolkit.createText(client, "", SWT.NONE//$NON-NLS-1$
                      |SWT.H_SCROLL |SWT.V_SCROLL | SWT.WRAP);
//    this.resourcesTypeAdapter.attachToOSDescription( this.txtOSDescr );

    gd.widthHint = 265;
    gd.heightHint=ResourcesPage.WIDGET_HEIGHT;
    this.txtOSDescr.setLayoutData(gd);
    
    toolkit.paintBordersFor( client);
  
  } // End void osSubSection()
  

 
  /*
   * Private Method that creates the CPU Architecture Sub-Section
   */
  private void createCPUArch ( final Composite parent,
                               final FormToolkit toolkit ) {
    
    String sectionTitle = Messages.getString( "ResourcesPage_CPUArch" ); //$NON-NLS-1$
    String sectionDescription = Messages.getString( "ResourcesPage_CPUArchDescr" ); //$NON-NLS-1$

    TableWrapData td;
       
    Composite client = FormSectionFactory.createStaticSection( toolkit,
                                           parent,
                                           sectionTitle,
                                           sectionDescription,
                                           2 );
     


     td = new TableWrapData( TableWrapData.FILL_GRAB );
    
    /*========================== CPU Architecture Widgets ====================*/
    
    this.lblCPUArchName = toolkit.createLabel( client,
                             Messages.getString( "ResourcesPage_CPUArchName" ) ); //$NON-NLS-1$
    
    this.cmbCPUArchName = new Combo( client, SWT.SIMPLE 
                                              | SWT.DROP_DOWN | SWT.READ_ONLY );
    
    this.cmbCPUArchName.setData( FormToolkit.KEY_DRAW_BORDER );    
//    this.resourcesTypeAdapter.attachToCPUArchitecture( this.cmbCPUArchName );
    this.cmbCPUArchName.setLayoutData( td );
    
    toolkit.paintBordersFor( client);    
    
  } //End void cPUArch()
  
  
  
  /*
   * Private Method that creates the CPU Architecture Sub-Section
   */
  private void createExclusiveExecutionSection ( final Composite parent,
                                                 final FormToolkit toolkit ) {
    
    String sectionTitle = Messages.getString( "ResourcesPage_ExclExecSection" ); //$NON-NLS-1$
    String sectionDescription = Messages.getString( "ResourcesPage_ExclExecDescr" ); //$NON-NLS-1$

    TableWrapData td;
       
    Composite client = FormSectionFactory.createStaticSection( toolkit,
                                           parent,
                                           sectionTitle,
                                           sectionDescription,
                                           2 );
     
     td = new TableWrapData( TableWrapData.FILL_GRAB );
    
    /*======================= Exclusive Execution Widgets ====================*/
    
    this.lblExclExecution = toolkit.createLabel( client,
                             Messages.getString( "ResourcesPage_ExclExec" ) ); //$NON-NLS-1$
    
    this.cmbExclExec = new Combo( client, SWT.SIMPLE 
                                              | SWT.DROP_DOWN | SWT.READ_ONLY );
    
    this.cmbExclExec.setData( FormToolkit.KEY_DRAW_BORDER );    
//    this.resourcesTypeAdapter.attachToExclusiveExecution( this.cmbExclExec );
    this.cmbExclExec.setLayoutData( td );
    
    toolkit.paintBordersFor( client);    

  } //End void cPUArch()
  
  
  
  /*
   * Private Method that creates the Additional Elements Sub-Section
   */
  private void createAddElementsSection (final Composite parent,
                                         final FormToolkit toolkit )
  {
   
    String sectionTitle = Messages.getString( "ResourcesPage_AddElementRangeVal" ); //$NON-NLS-1$
    String sectionDescription = Messages.getString( "ResourcesPage_AddElementsRangeValueDescr" ); //$NON-NLS-1$

    TableWrapData td;
       
    Composite client = FormSectionFactory.createStaticSection( toolkit,
                                                               parent,
                                                               sectionTitle,
                                                               sectionDescription,
                                                               3
                                                               );
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    td.colspan = 1;

    /*=====================Individual CPU Speed Widgets ======================*/
    
    
    this.lblIndCPUSpl = toolkit.createLabel( client,
                               Messages.getString( "ResourcesPage_IndCPUSpeed" ) ); //$NON-NLS-1$

    this.cmbIndividualCPUSpeed = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualCPUSpeed.setData( FormToolkit.KEY_DRAW_BORDER );
    this.txtIndCPUSp = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtIndCPUSp.addListener( SWT.Verify, new NumberVerifier() );    
//    this.resourcesTypeAdapter.attachToIndividualCPUSpeed( this.txtIndCPUSp, this.cmbIndividualCPUSpeed );
    this.txtIndCPUSp.setLayoutData( td );    
   
    
    /*=====================Individual CPU Time Widgets =======================*/
        
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblIndCPUTime = toolkit.createLabel( client,
                              Messages.getString( "ResourcesPage_IndCPUTime" ) ); //$NON-NLS-1$
    this.cmbIndividualCPUTime = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualCPUTime.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualCPUTime.setItems( RESOURCES_BOUNDARY_ITEMS );        
    this.txtIndCPUTime = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$    
    this.txtIndCPUTime.setLayoutData( td );

    /*=====================Individual CPU Count Widgets ======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblIndCPUCount = toolkit.createLabel( client,
                            Messages.getString( "ResourcesPage_IndCPUCount" ) ); //$NON-NLS-1$
    
    this.cmbIndividualCPUCount = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualCPUCount.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualCPUCount.setItems( RESOURCES_BOUNDARY_ITEMS );
    this.txtIndCPUCount = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$

    this.txtIndCPUCount.setLayoutData( td );

    /*===============Individual Network Bandwidth Widgets ====================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblIndNetBand = toolkit.createLabel( client,
                       Messages.getString( "ResourcesPage_IndNetwBandwidth" ) ); //$NON-NLS-1$
    
    this.cmbIndividualNetworkBandwidth = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualNetworkBandwidth.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualNetworkBandwidth.setItems( RESOURCES_BOUNDARY_ITEMS );      
    
    this.txtIndNetBand = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtIndNetBand.setLayoutData( td );
    
    /*===============Individual Physical Memory Widgets ======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblPhysMem = toolkit.createLabel( client,
                                 Messages.getString( "ResourcesPage_PhysMem" ) ); //$NON-NLS-1$
    
    this.cmbIndividualPhysicalMemory = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualPhysicalMemory.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualPhysicalMemory.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtPhysMem = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$

    this.txtPhysMem.setLayoutData( td );
    
    /*================Individual Virtual Memory Widgets ======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblVirtMem = toolkit.createLabel( client,
                                Messages.getString( "ResourcesPage_VirtualMem" ) ); //$NON-NLS-1$
    
    this.cmbIndividualVirtualMesmory = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualVirtualMesmory.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualVirtualMesmory.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtVirtMem = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$

    this.txtVirtMem.setLayoutData( td );
    
    /*================Individual Disk Space Widgets ==========================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblIndDiskSpac = toolkit.createLabel( client,
                              Messages.getString( "ResourcesPage_IndDiskSpace" ) ); //$NON-NLS-1$
    
    this.cmbIndividualDiskSpace = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbIndividualDiskSpace.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbIndividualDiskSpace.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtIndDiskSpac = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtIndDiskSpac.setLayoutData( td );

    /*=========================Total CPU Time Widgets ========================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblCPUTime = toolkit.createLabel( client,
                                   Messages.getString( "ResourcesPage_CPUTime" ) ); //$NON-NLS-1$
    
    this.cmbTotalCPUTime = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalCPUTime.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalCPUTime.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtCPUTime = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtCPUTime.setLayoutData( td );
    
    /*========================Total CPU Count Widgets ========================*/
     
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblCPUCount = toolkit.createLabel( client,
                               Messages.getString( "ResourcesPage_TotCPUCount" ) ); //$NON-NLS-1$

    this.cmbTotalCPUCount = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalCPUCount.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalCPUCount.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtCPUCount = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtCPUCount.setLayoutData( td );
    
    /*=====================Total Physical Memory Widgets =====================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblTotPhMem = toolkit.createLabel( client,
                                Messages.getString( "ResourcesPage_TotPhysMem" ) ); //$NON-NLS-1$
    
    this.cmbTotalPhysicalMemory = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalPhysicalMemory.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalPhysicalMemory.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtTotPhMem = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtTotPhMem.setLayoutData( td );

    /*======================Total Virtual Memory Widgets =====================*/
    
    td = new TableWrapData(TableWrapData.FILL_GRAB);
    this.lblTotVirtMem = toolkit.createLabel(client,
                             Messages.getString("ResourcesPage_TotVirtualMem")); //$NON-NLS-1$
    
    this.cmbTotalVirtualMemory = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalVirtualMemory.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalVirtualMemory.setItems( RESOURCES_BOUNDARY_ITEMS ); 
    this.txtTotVirtMem = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$    
    this.txtTotVirtMem.setLayoutData( td );
   
    /*========================Total Disk Space Widgets =======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblTotDiskSp = toolkit.createLabel( client,
                           Messages.getString( "ResourcesPage_TotDiskSpace" ) ); //$NON-NLS-1$
    
    this.cmbTotalDiskSpace = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalDiskSpace.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalDiskSpace.setItems( RESOURCES_BOUNDARY_ITEMS );
    this.txtTotDiskSp = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtTotDiskSp.setLayoutData( td );

    /*====================Total Resource Count Widgets =======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblTotResCount = toolkit.createLabel( client,
                             Messages.getString( "ResourcesPage_TotRescCount" ) ); //$NON-NLS-1$
    
    this.cmbTotalResourceCount = new Combo( client, SWT.SIMPLE | SWT.DROP_DOWN | SWT.READ_ONLY );
    this.cmbTotalResourceCount.setData( FormToolkit.KEY_DRAW_BORDER );
    this.cmbTotalResourceCount.setItems( RESOURCES_BOUNDARY_ITEMS );
    this.txtTotResCount = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtTotResCount.setLayoutData( td );

    toolkit.paintBordersFor( client );
    
  }

  
  
  private void addFormPageHelp( final ScrolledForm form ) {
    
    final String href = getHelpResource();
    if ( href != null ) {
        IToolBarManager manager = form.getToolBarManager();
        Action helpAction = new Action( "help" ) { //$NON-NLS-1$
            @Override
            public void run() {
                BusyIndicator.showWhile( form.getDisplay(), new Runnable() {
                    public void run() {
                        PlatformUI.getWorkbench().getHelpSystem().displayHelpResource( href );
                    }
                } );
            }
        };
        helpAction.setToolTipText( Messages.getString( "ResourcesPage_Help" ) );  //$NON-NLS-1$
        URL stageInURL = Activator.getDefault().getBundle().getEntry( "icons/help.gif" ); //$NON-NLS-1$       
        this.helpDesc = ImageDescriptor.createFromURL( stageInURL ) ;   
        helpAction.setImageDescriptor( this.helpDesc);
        manager.add( helpAction );
        form.updateToolBar();
    }
    
  }
  
  
  
  /*
   * Method which opens a Dialog for selecting Candidate Hosts for Job Submission.
   */
  @SuppressWarnings("unchecked")
  protected void handleAddDialog( final String dialogTitle ) {
    
    this.value = null;
    
    CandidateHostsDialog hostsDialog = new CandidateHostsDialog( this.body.getShell(), dialogTitle );
    
    IFile file = ( (IFileEditorInput) this.getEditor().getEditorInput() ).getFile();
    IGridRoot root = GridModel.getRoot();
    IGridElement element = root.findElement( file );
    hostsDialog.setDialogInput( element );
    hostsDialog.setExistingCandidateHosts( this.hostsViewer.getInput() );
 
    if( hostsDialog.open() != Window.OK ) {
      return;
        
    }    
      this.value = hostsDialog.getValue();
    
  }
  
  
  
  /*
   * Method which opens a Dialog for adding new File Systems.
   */
  protected void handleAddFsDialog( final String dialogTitle, final Button button ){
    
    this.value = null;
    
    FileSystemsDialog fileSystemDialog = new FileSystemsDialog( this.body.getShell(), dialogTitle );

    /* Edit Element */ 
    if (button != this.btnFileSystemAdd ) {
       IStructuredSelection structSelection 
                   = ( IStructuredSelection ) this.fileSystemsViewer.getSelection();
       
       fileSystemDialog.setInput( structSelection.getFirstElement() );

    }  
 
    if( fileSystemDialog.open() != Window.OK ) {
      return;        
    }
    
      this.value = fileSystemDialog.getValue();
    
  }
  
  

  public void notifyChanged( final Notification notification ) {
    setDirty( true );    
  }
  
  
  
  protected String getHelpResource() {
    return "/eu.geclipse.doc.user/html/concepts/jobmanagement/editorpages/resources.html"; //$NON-NLS-1$
  }
  
  /*
   * This function updates the status of the buttons related to
   * the respective Stage-In and Stage-Out Table Viewers. The Status of the 
   * buttons is adjusted according to the selection and content of the respective
   * table viewer.
   * 
   */ 
    protected void updateButtons( final TableViewer tableViewer ) {
    
    ISelection selection = tableViewer.getSelection();
    boolean selectionAvailable = !selection.isEmpty();    
    
    if (tableViewer == this.fileSystemsViewer) {
    
      this.btnFileSystemAdd.setEnabled( true );
      this.btnFileSystemEdit.setEnabled( selectionAvailable );
      this.btnFileSystemDel.setEnabled( selectionAvailable );
    }
    else {     
      this.btnHostsAdd.setEnabled( true );
      this.btnHostsDel.setEnabled( selectionAvailable );
    }
    
  } // End updateButtons    


} // end ResourcesPage class
