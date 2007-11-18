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
 * @author nloulloud
 *
 */

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
import eu.geclipse.jsdl.model.JobDefinitionType;
import eu.geclipse.jsdl.ui.adapters.jsdl.JobDefinitionTypeAdapter;
import eu.geclipse.jsdl.ui.adapters.jsdl.JobIdentificationTypeAdapter;
import eu.geclipse.jsdl.ui.adapters.jsdl.ResourcesTypeAdapter;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.dialogs.CandidateHostsDialog;
import eu.geclipse.jsdl.ui.internal.dialogs.FileSystemsDialog;
import eu.geclipse.jsdl.ui.providers.FeatureContentProvider;
import eu.geclipse.jsdl.ui.providers.FeatureLabelProvider;


/**
 * This class provides the Resources Page page that appears in the JSDL editor.
 * It provides a graphical user interface to the following elements of a JSDL 
 * document:
 * 
 * 
 * 
 * 
 */
public final class ResourcesPage extends FormPage 
                                            implements INotifyChangedListener {
  
   
  protected static final String PAGE_ID = "RESOURCES";  //$NON-NLS-1$
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
  protected Button btnAdd = null;
  protected Button btnDel = null;
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
  private final int TXT_LENGTH = 300;
  private final int WIDGET_HEIGHT = 100;
  
  
  
  /**
   * 
   * ResourcesPage class constructor. Initializes the Resources Page by 
   * passing as an argument the container JSDL editor.
   * @param editor
   * 
   */
  public ResourcesPage( final FormEditor editor )
                            
   {
   
    super( editor, PAGE_ID ,
           Messages.getString("ResourcesPage_PageTitle") ); //$NON-NLS-1$
        
    } // End Class Constructor.

  /*
   * (non-Javadoc)
   * @see org.eclipse.ui.forms.editor.FormPage#setActive(boolean)
   */
  @Override
  public void setActive(final boolean active) {
    
    if ( active ){
      if ( isContentRefreshed() ) {    
        this.resourcesTypeAdapter.load();        
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
      this.resourcesTypeAdapter.setContent( jobDefinitionRoot );      
    }
   else{
      this.resourcesTypeAdapter = new ResourcesTypeAdapter(jobDefinitionRoot);  
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
   * @param dirty
   * If TRUE then the page is Dirty and a Save operation is needed.
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
   *  This method is used to create the Forms content by
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
    createCandidateHostsSection ( this.left , toolkit );
   
    /* Create the Operating System Section */
    createOSSection( this.left , toolkit  );
    
    /* Create the File System Section */
    createFileSystemSection( this.left, toolkit );
    
    /* Create the CPU Architecture Section */
    createCPUArch( this.right, toolkit );
    
    /* Create the Exclusive Execution Section */
    createExclusiveExecutionSection( this.right, toolkit );
    
    /* Create the Additional Elements Section */
    createAddElementsSection( this.right, toolkit );
    
    
    
    
  

   this.resourcesTypeAdapter.load();
   
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
    gd.heightHint = this.WIDGET_HEIGHT;
    gd.widthHint = this.TXT_LENGTH;

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
    this.btnAdd = toolkit.createButton( client,
                                        Messages.getString( "JsdlEditor_AddButton" ), //$NON-NLS-1$
                                        SWT.BUTTON1);  
    
    this.btnAdd.addSelectionListener( new SelectionListener() {
      public void widgetSelected( final SelectionEvent event ) {
        handleAddDialog(Messages.getString( "ResourcesPage_NewHostNameDialog" ), //$NON-NLS-1$
                                                    ( Button ) event.getSource() ); 
        ResourcesPage.this.resourcesTypeAdapter.addCandidateHosts(ResourcesPage.this.hostsViewer,                                                          
                                                           ResourcesPage.this.value );
      }

       public void widgetDefaultSelected( final SelectionEvent event ) {
           // Do Nothing - Required method
       }
     });
    
    this.btnAdd.setLayoutData( gd);
    
    //FIXME Uncomment for Edit Functionality
    
    /* Create the Edit button */
//    gd = new GridData();
//    gd.verticalSpan = 2;
//    gd.verticalAlignment = GridData.END;
//    gd.horizontalAlignment = GridData.FILL;
//    this.btnEdit = toolkit.createButton(client,
//                                    Messages.getString("JsdlEditor_EditButton"), //$NON-NLS-1$
//                                    SWT.BUTTON1);  
//    
//    this.btnEdit.addSelectionListener(new SelectionListener() {
//      public void widgetSelected(final SelectionEvent event) {
//        handleAddDialog(Messages.getString( "ResourcesPage_EditHostNameDialog" ), //$NON-NLS-1$
//                                                    (Button) event.getSource()); 
//        ResourcesPage.this.resourcesTypeAdapter.performEdit(ResourcesPage.this.hostsViewer,                                                          
//                                                           ResourcesPage.this.value);
//      }
//
//       public void widgetDefaultSelected(final SelectionEvent event) {
//           // Do Nothing - Required method
//       }
//     });
//    
//    this.btnEdit.setLayoutData( gd);
    
    
    /* Create the Remove button */
    gd = new GridData();
    gd.verticalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    gd.horizontalAlignment = GridData.FILL;
    this.btnDel = toolkit.createButton(client, 
                                 Messages.getString( "JsdlEditor_RemoveButton" ), //$NON-NLS-1$
                                 SWT.BUTTON1 );
    
    this.btnDel.setEnabled( true );
    
    this.resourcesTypeAdapter.attachToDelete( this.btnDel, this.hostsViewer );
    this.btnDel.setLayoutData( gd);
        
    toolkit.paintBordersFor( client );
    updateButtons( this.hostsViewer );
    
  } //End void CandidateHostsSubSection()
  
  
  
  /*
   * Private Method that creates the File System Sub-Section
   */ 
//  private void createFileSystemSection ( final Composite parent,
//                                         final FormToolkit toolkit ){
//    
// 
//    String sectionTitle = Messages.getString( "ResourcesPage_FileSystem") ;  //$NON-NLS-1$
//    String sectionDescription = Messages.getString( "ResourcesPage_FileSystemDesc" ); //$NON-NLS-1$
//    
//    GridData gd;
//       
//    Composite client = FormSectionFactory.createGridStaticSection( toolkit,
//                                           parent,
//                                           sectionTitle,
//                                           sectionDescription,
//                                           3 );
//    
//     
//    
//    gd = new GridData(); 
//    
//   
//    gd.horizontalSpan = 1;
//    
  // /* ======================== File System Name Widgets
  // =====================*/
  // this.lblFileSystemName = toolkit.createLabel( client,
  // Messages.getString( "ResourcesPage_FileSystemName" ) ); //$NON-NLS-1$
  //    
  // this.lblFileSystemName.setLayoutData( gd );
  //    
  // gd = new GridData(GridData.FILL_BOTH);
  // gd.horizontalSpan = 2;
  // gd.widthHint = this.TXT_LENGTH;
  //    
  // this.txtFileSystemName = toolkit.createText( client, "", SWT.NONE );
  // //$NON-NLS-1$
  // this.resourcesTypeAdapter.attachToFileSystemName( this.txtFileSystemName );
  // this.txtFileSystemName.setLayoutData( gd );
  //    
  //    
  // /* ========================= Description Widgets
  // =========================*/
  //    
  // gd = new GridData();
  // gd.verticalAlignment = GridData.BEGINNING;
  // this.lblFileSystemDescr = toolkit.createLabel( client,
  // Messages.getString( "ResourcesPage_Description" ) ); //$NON-NLS-1$
  // this.lblFileSystemDescr.setLayoutData( gd );
  //    
  //   
  // this.txtFileSystemDescr = toolkit.createText( client, "", //$NON-NLS-1$
  // SWT.MULTI |SWT.H_SCROLL|SWT.V_SCROLL| SWT.WRAP );
  // this.resourcesTypeAdapter.attachToFileSystemDescription(
  // this.txtFileSystemDescr );
  //    
  // gd = new GridData( GridData.FILL_BOTH );
  // gd.verticalAlignment = GridData.FILL;
  // gd.grabExcessVerticalSpace = true;
  // gd.horizontalSpan = 2;
  // gd.widthHint = 285;
  // gd.heightHint = this.WIDGET_HEIGHT;
  // this.txtFileSystemDescr.setLayoutData( gd );
  //    
  // /* ========================= Mount Point Widgets
  // =========================*/
  // gd = new GridData();
  // this.lblMountPoint = toolkit.createLabel( client,
  // Messages.getString( "ResourcesPage_MountPoint" ) ); //$NON-NLS-1$
  // this.lblMountPoint.setLayoutData( gd );
  //    
  // gd = new GridData( GridData.FILL_BOTH );
  // gd.horizontalSpan = 2;
  // gd.widthHint = this.TXT_LENGTH;
  // this.txtMountPoint = toolkit.createText( client, "", SWT.NONE );
  // //$NON-NLS-1$
  // this.resourcesTypeAdapter.attachToFileSystemMountP   /* Create the Edit button */
//gd = new GridData();
//gd.verticalSpan = 2;
//gd.verticalAlignment = GridData.END;
//gd.horizontalAlignment = GridData.FILL;
//this.btnEdit = toolkit.createButton(client,
//                                Messages.getString("JsdlEditor_EditButton"), //$NON-NLS-1$
//                                SWT.BUTTON1);  
//
//this.btnEdit.addSelectionListener(new SelectionListener() {
//  public void widgetSelected(final SelectionEvent event) {
//    handleAddDialog(Messages.getString( "ResourcesPage_EditHostNameDialog" ), //$NON-NLS-1$
//                                                (Button) event.getSource()); 
//    ResourcesPage.this.resourcesTypeAdapter.performEdit(ResourcesPage.this.hostsViewer,                                                          
//                                                       ResourcesPage.this.value);
//  }
//
//   public void widgetDefaultSelected(final SelectionEvent event) {
//       // Do Nothing - Required method
//   }
// });
//
//this.btnEdit.setLayoutData( gd);oint( this.txtMountPoint
  // );
  // this.txtMountPoint.setLayoutData(gd);
  //        
  // gd = new GridData();
  // gd.widthHint = 120;
  //   
  // /* ======================== Disk Space Widgets
  // ===========================*/
  // this.lblDiskSpace = toolkit.createLabel( client,
  // Messages.getString( "ResourcesPage_DiskSpace" ) ); //$NON-NLS-1$
  //    
  // this.lblDiskSpace.setLayoutData( gd );
  //
  // this.txtDiskSpace = toolkit.createText( client, "", SWT.NONE );
  // //$NON-NLS-1$
  // this.txtDiskSpace.setLayoutData( gd );
  //
  // gd = new GridData(GridData.FILL_BOTH);
  // gd.widthHint = 175;
  // this.cmbDiskSpaceRange = new Combo( client, SWT.DROP_DOWN | SWT.READ_ONLY
  // );
  // this.cmbDiskSpaceRange.add( Messages.getString(
  // "ResourcesPage_LowBoundRange" ) ); //$NON-NLS-1$
  // this.cmbDiskSpaceRange.add( Messages.getString(
  // "ResourcesPage_UpBoundRange" ) ); //$NON-NLS-1$
  //
  // this.cmbDiskSpaceRange.setData( FormToolkit.KEY_   /* Create the Edit button */
//gd = new GridData();
//gd.verticalSpan = 2;
//gd.verticalAlignment = GridData.END;
//gd.horizontalAlignment = GridData.FILL;
//this.btnEdit = toolkit.createButton(client,
//                                Messages.getString("JsdlEditor_EditButton"), //$NON-NLS-1$
//                                SWT.BUTTON1);  
//
//this.btnEdit.addSelectionListener(new SelectionListener() {
//  public void widgetSelected(final SelectionEvent event) {
//    handleAddDialog(Messages.getString( "ResourcesPage_EditHostNameDialog" ), //$NON-NLS-1$
//                                                (Button) event.getSource()); 
//    ResourcesPage.this.resourcesTypeAdapter.performEdit(ResourcesPage.this.hostsViewer,                                                          
//                                                       ResourcesPage.this.value);
//  }
//
//   public void widgetDefaultSelected(final SelectionEvent event) {
//       // Do Nothing - Required method
//   }
// });
//
//this.btnEdit.setLayoutData( gd);DRAW_BORDER );
  //    
  // this.cmbDiskSpaceRange.setLayoutData( gd );
  //    
  // /* ========================= File System Widgets
  // =========================*/
  // gd = new GridData();
  // gd.horizontalSpan = 1;
  // gd.widthHint = this.TXT_LENGTH + 5 ;
  // this.lblFileSystemType = toolkit.createLabel( client,
  // Messages.getString( "ResourcesPage_FileSysType" ) ); //$NON-NLS-1$
  //    
  // gd.widthHint=120;
  // this.lblFileSystemType.setLayoutData( gd);
  //    
  // gd = new GridData( GridData.FILL_BOTH );
  // gd.horizontalSpan = 2;
  // this.cmbFileSystemType = new Combo(client, SWT.NONE | SWT.READ_ONLY);
  // this.cmbFileSystemType.setData( FormToolkit.KEY_DRAW_BORDER );
  // this.resourcesTypeAdapter.attachToFileSystemType( this.cmbFileSystemType );
  // this.cmbFileSystemType.setLayoutData(gd);
  //    
  //    this.resourcesTypeAdapter
  //      .attachToFileSystemDiskSpace( this.txtDiskSpace, this.cmbDiskSpaceRange );
  //    
  //     toolkit.paintBordersFor( client);
//    
//  } //End void FileSystemSubSection()
  
  
  
  
  private void createFileSystemSection  ( final Composite parent,
                                          final FormToolkit toolkit )
  
  {
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
    gd.heightHint = this.WIDGET_HEIGHT;
    gd.widthHint = this.TXT_LENGTH;
    
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
    
    this.btnAdd = toolkit.createButton( client,
                                        Messages.getString( "JsdlEditor_AddButton" ), //$NON-NLS-1$
                                        SWT.BUTTON1);  
    
    this.btnAdd.addSelectionListener( new SelectionListener() {
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
    
    this.btnAdd.setLayoutData( gd );
    
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
        
        ResourcesPage.this.resourcesTypeAdapter.performEdit(ResourcesPage.this.fileSystemsViewer,                                                          
                                                           ResourcesPage.this.value[0]);
      }

       public void widgetDefaultSelected(final SelectionEvent event) {
           // Do Nothing - Required method
       }
     });
    
    this.btnFileSystemEdit.setLayoutData( gd );
    
    
    /* Create the Remove button */
    gd = new GridData();
    gd.verticalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    gd.horizontalAlignment = GridData.FILL;
    
    this.btnDel = toolkit.createButton(client, 
                                 Messages.getString( "JsdlEditor_RemoveButton" ), //$NON-NLS-1$
                                 SWT.BUTTON1 );
    
    this.btnDel.setEnabled( true );
    
    this.resourcesTypeAdapter.attachToDelete( this.btnDel, this.fileSystemsViewer );
    this.btnDel.setLayoutData( gd );
        
    toolkit.paintBordersFor( client );
    
  } //End void FileSystemsSection()
  
  
  
  
  /*
   * Private Method that creates the Operating System Sub-Section
   */
  private void createOSSection ( final Composite parent,
                                 final FormToolkit toolkit )
  
  {
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
    this.resourcesTypeAdapter.attachToOSType( this.cmbOperSystType );
    this.cmbOperSystType.setLayoutData( gd );
    
    /*================= Operating System Version Widgets =====================*/
    
    this.lblOperSystVer = toolkit.createLabel( client,
                           Messages.getString( "ResourcesPage_OperSystVersion" ) ); //$NON-NLS-1$
    this.txtOperSystVer = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.resourcesTypeAdapter.attachToOSVersion( this.txtOperSystVer );
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
    this.resourcesTypeAdapter.attachToOSDescription( this.txtOSDescr );

    gd.widthHint = 265;
    gd.heightHint=this.WIDGET_HEIGHT;
    this.txtOSDescr.setLayoutData(gd);
    
    toolkit.paintBordersFor( client);
  
  } // End void osSubSection()
  

  
  
  /*
   * Private Method that creates the CPU Architecture Sub-Section
   */
  private void createCPUArch ( final Composite parent,
                               final FormToolkit toolkit )
  {
    
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
    this.resourcesTypeAdapter.attachToCPUArchitecture( this.cmbCPUArchName );
    this.cmbCPUArchName.setLayoutData( td );
    
    toolkit.paintBordersFor( client);    
    
  } //End void cPUArch()
  
  
  
  /*
   * Private Method that creates the CPU Architecture Sub-Section
   */
  private void createExclusiveExecutionSection ( final Composite parent,
                                                 final FormToolkit toolkit )
  {
    
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
    this.resourcesTypeAdapter.attachToExclusiveExecution( this.cmbExclExec );
    this.cmbExclExec.setLayoutData( td );
    
    toolkit.paintBordersFor( client);    
    
    updateButtons( this.fileSystemsViewer );
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
    

    /*=====================Individual CPU Speed Widgets ======================*/

    td = new TableWrapData( TableWrapData.FILL_GRAB );
    
    this.lblIndCPUSpl = toolkit.createLabel( client,
                               Messages.getString( "ResourcesPage_IndCPUSpeed" ) ); //$NON-NLS-1$
    createCombo( client );
    this.txtIndCPUSp = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtIndCPUSp.setLayoutData( td );
    
    /*=====================Individual CPU Time Widgets =======================*/
        
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblIndCPUTime = toolkit.createLabel( client,
                              Messages.getString( "ResourcesPage_IndCPUTime" ) ); //$NON-NLS-1$
    createCombo ( client );
    this.txtIndCPUTime = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$    
    this.txtIndCPUTime.setLayoutData( td );

    /*=====================Individual CPU Count Widgets ======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblIndCPUCount = toolkit.createLabel( client,
                            Messages.getString( "ResourcesPage_IndCPUCount" ) ); //$NON-NLS-1$
    createCombo ( client );
    this.txtIndCPUCount = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$

    this.txtIndCPUCount.setLayoutData( td );

    /*===============Individual Network Bandwidth Widgets ====================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblIndNetBand = toolkit.createLabel( client,
                       Messages.getString( "ResourcesPage_IndNetwBandwidth" ) ); //$NON-NLS-1$
    createCombo ( client );
    this.txtIndNetBand = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtIndNetBand.setLayoutData( td );
    
    /*===============Individual Physical Memory Widgets ======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblPhysMem = toolkit.createLabel( client,
                                 Messages.getString( "ResourcesPage_PhysMem" ) ); //$NON-NLS-1$
    createCombo (client );
    this.txtPhysMem = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$

    this.txtPhysMem.setLayoutData( td );
    
    /*================Individual Virtual Memory Widgets ======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblVirtMem = toolkit.createLabel( client,
                                Messages.getString( "ResourcesPage_VirtualMem" ) ); //$NON-NLS-1$
    createCombo (client);
    this.txtVirtMem = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$

    this.txtVirtMem.setLayoutData( td );
    
    /*================Individual Disk Space Widgets ==========================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblIndDiskSpac = toolkit.createLabel( client,
                              Messages.getString( "ResourcesPage_IndDiskSpace" ) ); //$NON-NLS-1$
    createCombo ( client );
    this.txtIndDiskSpac = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtIndDiskSpac.setLayoutData( td );

    /*=========================Total CPU Time Widgets ========================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblCPUTime = toolkit.createLabel( client,
                                   Messages.getString( "ResourcesPage_CPUTime" ) ); //$NON-NLS-1$
    createCombo( client );
    this.txtCPUTime = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtCPUTime.setLayoutData( td );
    
    /*========================Total CPU Count Widgets ========================*/
     
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblCPUCount = toolkit.createLabel( client,
                               Messages.getString( "ResourcesPage_TotCPUCount" ) ); //$NON-NLS-1$
    createCombo(client);
    this.txtCPUCount = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtCPUCount.setLayoutData( td );
    
    /*=====================Total Physical Memory Widgets =====================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblTotPhMem = toolkit.createLabel( client,
                                Messages.getString( "ResourcesPage_TotPhysMem" ) ); //$NON-NLS-1$
    createCombo( client );
    this.txtTotPhMem = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtTotPhMem.setLayoutData( td );

    /*======================Total Virtual Memory Widgets =====================*/
    
    td = new TableWrapData(TableWrapData.FILL_GRAB);
    this.lblTotVirtMem = toolkit.createLabel(client,
                             Messages.getString("ResourcesPage_TotVirtualMem")); //$NON-NLS-1$
    createCombo( client );
    this.txtTotVirtMem = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$    
    this.txtTotVirtMem.setLayoutData( td );
   
    /*========================Total Disk Space Widgets =======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblTotDiskSp = toolkit.createLabel( client,
                           Messages.getString( "ResourcesPage_TotDiskSpace" ) ); //$NON-NLS-1$
    createCombo( client );
    this.txtTotDiskSp = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtTotDiskSp.setLayoutData( td );

    /*====================Total Resource Count Widgets =======================*/
    
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.lblTotResCount = toolkit.createLabel( client,
                             Messages.getString( "ResourcesPage_TotRescCount" ) ); //$NON-NLS-1$
    createCombo(client );
    this.txtTotResCount = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.txtTotResCount.setLayoutData( td );

    toolkit.paintBordersFor( client );
    
  }

  

  private Combo createCombo( final Composite composite ) {
    
    Combo comboRang = new Combo( composite, SWT.WRAP | SWT.DROP_DOWN | SWT.READ_ONLY );    
 
    comboRang.add( Messages.getString( "ResourcesPage_LowBoundRange" ).trim() ); //$NON-NLS-1$
    comboRang.add( Messages.getString( "ResourcesPage_UpBoundRange" ).trim() ); //$NON-NLS-1$
    comboRang.add( Messages.getString( "ResourcesPage_Exact" ).trim() ); //$NON-NLS-1$
    TableWrapData td = new TableWrapData( TableWrapData.FILL_GRAB );    
    comboRang.setLayoutData( td );
    
    return comboRang;
    
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
  
  
  
  @SuppressWarnings("unchecked")
  protected void handleAddDialog( final String dialogTitle, final Button button ){
    
    this.value = null;
    
    CandidateHostsDialog hostsDialog = new CandidateHostsDialog( this.body.getShell(), dialogTitle );
    
    IFile file = ( (IFileEditorInput) this.getEditor().getEditorInput() ).getFile();
    IGridRoot root = GridModel.getRoot();
    IGridElement element = root.findElement( file );
    hostsDialog.setDialogInput( element );
    hostsDialog.setExistingCandidateHosts( this.hostsViewer.getInput() );

    if (button != this.btnAdd ) {
    //FIXME Un-comment for Edit Functionality
//       IStructuredSelection structSelection 
//                   = ( IStructuredSelection ) this.hostsViewer.getSelection();
//    
       
//       java.util.List<String> list =  structSelection.toList();
       

    }
  
 
    if( hostsDialog.open() != Window.OK ) {
    
        return;
        
    }
    
      this.value = hostsDialog.getValue();
    
  }
  
  
  protected void handleAddFsDialog( final String dialogTitle, final Button button ){
    
    this.value = null;
    
    FileSystemsDialog fileSystemDialog = new FileSystemsDialog( this.body.getShell(), dialogTitle );

    if (button != this.btnAdd ) {
    //FIXME Un-comment for Edit Functionality
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
    
      this.btnAdd.setEnabled( true );
      this.btnDel.setEnabled( selectionAvailable );
//      FIXME Un-comment for Edit Functionality
      this.btnFileSystemEdit.setEnabled( selectionAvailable );
    }
    else {
      this.btnAdd.setEnabled( true );
      this.btnDel.setEnabled( selectionAvailable );
    }
    
  } // End updateButtons
    


}
