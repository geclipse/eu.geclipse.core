/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 */
import java.net.URL;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;

import eu.geclipse.jsdl.model.base.JobDefinitionType;
import eu.geclipse.jsdl.ui.adapters.jsdl.ApplicationTypeAdapter;
import eu.geclipse.jsdl.ui.adapters.posix.PosixApplicationTypeAdapter;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.pages.sections.ApplicationSection;
import eu.geclipse.jsdl.ui.internal.pages.sections.PosixApplicationSection;

/**
 * This class provides the Job Application page that appears in the JSDL editor.
 * It provides a graphical user interface to the generic and POSIX elements of a JSDL
 * document. 
 */
public final class JobApplicationPage extends FormPage implements INotifyChangedListener {

  protected static final String PAGE_ID = "APPLICATION"; //$NON-NLS-1$  
  protected Composite body = null;
  protected Composite applicationSectionComposit = null;
  protected Composite pS = null;
  protected Composite apS = null;
  protected Text txtExecutable = null;
  protected Text txtPosixName = null;
  protected Text txtInput = null;
  protected Text txtOutput = null;
  protected Text txtError = null;
  protected Text txtApplicationName = null;
  protected Text txtApplicationVersion = null;
  protected Text txtDescription = null;
  protected Text txtWallTimeLimit = null;
  protected Text txtFileSizeLimit = null;
  protected Text txtCoreDumpLimit = null;
  protected Text txtDataSegmentLimit = null;
  protected Text txtLockedMemoryLimit = null;
  protected Text txtMemoryLimit = null;
  protected Text txtOpenDescriptorsLimit = null;
  protected Text txtPipeSizeLimit = null;
  protected Text txtStackSizeLimit = null;
  protected Text txtCPUTimeLimit = null;
  protected Text txtProcessCountLimit = null;
  protected Text txtVirtualMemoryLimit = null;
  protected Text txtThreadCountLimit = null;
  protected Text txtUserName = null;
  protected Text txtGroupName = null;
  protected Text txtFileSystemName = null;
  protected Text txtWorkingDirectory = null;
  protected Button btnArgAdd = null;
  protected Button btnArgEdit = null;
  protected Button btnArgDel = null;
  protected Button btnEnVarAdd = null;
  protected Button btnEnVarEdit = null;
  protected Button btnEnVarDel = null;
  protected Label lblApplicationName = null;
  protected Label lblApplicationVersion = null;
  protected Label lblApplicationDescription = null;
  protected Label lblWorkingDirectory = null;
  protected Label lblPosixName = null;
  protected Label lblExecutable = null;
  protected Label lblArgument = null;
  protected Label lblInput = null;
  protected Label lblOutput = null;
  protected Label lblError = null;
  protected Label lblEnvironment = null;
  protected Label lblWallTimeLimit = null;
  protected Label lblFileSizeLimit = null;
  protected Label lblCoreDumpLimit = null;
  protected Label lblDataSegmentLimit = null;
  protected Label lblLockedMemoryLimit = null;
  protected Label lblMemoryLimit = null;
  protected Label lblOpenDescriptorsLimit = null;
  protected Label lblPipeSizeLimit = null;
  protected Label lblStackSizeLimit = null;
  protected Label lblCPUTimeLimit = null;
  protected Label lblProcessCountLimit = null;
  protected Label lblVirtualMemoryLimit = null;
  protected Label lblThreadCountLimit = null;
  protected Label lblUserName = null;
  protected Label lblGroupName = null;
  protected Label lblUnits = null;
  protected Table tblEnvironment = null;
  protected TableViewer environmentViewer = null;
  protected Table tblArgument = null;
  protected TableViewer argumentViewer = null;
  protected JobDefinitionType jobDefinitionType = null;
  protected Object value = null;
  
  private ApplicationSection applicationSection = null;
  private PosixApplicationSection posixApplicationSection = null;
  private ImageDescriptor helpDesc = null;
  private boolean contentRefreshed = false;
  private boolean dirtyFlag = false;
  


  
  
  /**
   * <code>JobApplicationPage</code> class constructor. Creates the page by
   * passing as an argument the container JSDL editor.
   * 
   * @param editor The JSDL editor.
   */
  public JobApplicationPage( final FormEditor editor ) {
    super( editor, PAGE_ID, Messages.getString( "JobApplicationPage_PageTitle" ) ); //$NON-NLS-1$
  } // End Class Constructor

  
  
  @Override
  public void setActive( final boolean active ) {
    if( active ) {
      if( isContentRefreshed() ) {
        this.applicationSection.setInput( this.jobDefinitionType );
        this.posixApplicationSection.setInput( this.jobDefinitionType );
      }// end_if isContentRefreshed
    } // end_if active
  } // End void setActive()

  
  
  @Override
  public boolean isDirty() {
    return this.dirtyFlag;
  } // End boolean isDirty()
  

  @Override
  public void dispose() {
    
    super.dispose();
    this.jobDefinitionType = null;      
    
  }

  
  
  /**
   * This method set's the dirty status of the page.
   * 
   * @param dirty TRUE when the page is Dirty (content has been changed) and hence a 
   * Save operation is needed.
   * 
   */
  public void setDirty( final boolean dirty ) {
    if( this.dirtyFlag != dirty ) {
      this.dirtyFlag = dirty;
      this.getEditor().editorDirtyStateChanged();
    }
  } // End void setDirty()

  
  
  private boolean isContentRefreshed() {
    return this.contentRefreshed;
  }

    
  
  /**
   * Method that set's the JobApplication Page content. The content is the root
   * JSDL element. Also this method is responsible to initialize the associated
   * type adapters for the elements of this page. This method must be called
   * only from the JSDL Editor. Associated Type Adapters for this page are:
   * 
   * @see ApplicationTypeAdapter
   * @see PosixApplicationTypeAdapter
   * @param jobDefinitionRoot
   * @param refreshStatus Set to TRUE if the original page content is already
   *            set, but there is a need to refresh the page because there was a
   *            change to this content from an outside editor.
   */
  public void setPageContent( final JobDefinitionType jobDefinitionRoot,
                              final boolean refreshStatus )
  {
    if( refreshStatus ) {
      this.contentRefreshed = true;
      this.jobDefinitionType = jobDefinitionRoot;
    }
    this.jobDefinitionType = jobDefinitionRoot;      
  } // End void getPageContent()

  
  
  /*
   * This method is used to create the Forms content by creating the form layout
   * and then creating the form sub sections.
   */
  @Override
  protected void createFormContent( final IManagedForm managedForm ) {
    
    ScrolledForm form = managedForm.getForm();
    FormToolkit toolkit = managedForm.getToolkit();
    
    form.setText( Messages.getString( "JobApplicationPage_ApplicationTitle" ) ); //$NON-NLS-1$
    this.body = form.getBody();
    this.body.setLayout( FormLayoutFactory.createFormTableWrapLayout( false, 2 ) );
    
    this.applicationSectionComposit = toolkit.createComposite( this.body );
    this.applicationSectionComposit.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1 ) );
    this.applicationSectionComposit.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );
    
    this.applicationSection = new ApplicationSection( this.applicationSectionComposit, toolkit);
    this.applicationSection.setInput( this.jobDefinitionType );
    this.applicationSection.addListener( this );
    
    this.apS = toolkit.createComposite( this.body );
    this.apS.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1 ) );
    this.apS.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );
    createAdditionalPosixSection( this.apS, toolkit );

    
    this.posixApplicationSection = new PosixApplicationSection(this, this.applicationSectionComposit, toolkit);
    this.posixApplicationSection.setInput( this.jobDefinitionType );
    this.posixApplicationSection.addListener( this );
    
    /* Also add the help system */
    addFormPageHelp( form );
    
  }


  private void createAdditionalPosixSection( final Composite parent,
                                             final FormToolkit toolkit )
  {
    String sectionTitle = Messages.getString( "JobApplicationPage_additionalPosixApplElementTitle" ); //$NON-NLS-1$
    String sectionDescripiton = Messages.getString( "JobApplicationPage_additionalPosixApplDescr" ); //$NON-NLS-1$
    TableWrapData td;
    Composite client = FormSectionFactory.createExpandableSection( toolkit,
                                                                   parent,
                                                                   sectionTitle,
                                                                   sectionDescripiton,
                                                                   3,
                                                                   true );
    
    
    /* ========================= Working Directory =========================== */
    this.lblWorkingDirectory = toolkit.createLabel( client,
                                                    Messages.getString( "JobApplicationPage_WorkingDirectory" ) ); //$NON-NLS-1$
    
    this.txtWorkingDirectory = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtWorkingDirectory.setLayoutData( td );
//    this.posixApplicationTypeAdapter.attachToWorkingDirectory( this.txtWorkingDirectory );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_empty" ) ); //$NON-NLS-1$
    
    
    /* ============================ Wall Time Limit ========================= */
    this.lblWallTimeLimit = toolkit.createLabel( client,
                                                 Messages.getString( "JobApplicationPage_WallTimeLimit" ) ); //$NON-NLS-1$
    this.txtWallTimeLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtWallTimeLimit.setLayoutData( td );
//    this.posixApplicationTypeAdapter.attachToWallTimeLimit(this.txtWallTimeLimit);
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_time" ) ); //$NON-NLS-1$

    
    /* ============================ File Size Limit ========================== */
    this.lblFileSizeLimit = toolkit.createLabel( client,
                                                 Messages.getString( "JobApplicationPage_FileSizeLimit" ) ); //$NON-NLS-1$
    this.txtFileSizeLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtFileSizeLimit.setLayoutData( td );
//    this.posixApplicationTypeAdapter.attachToFileSizeLimit( this.txtFileSizeLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$

    
     
    /* ============================ Core Dump Limit ========================== */
    this.lblCoreDumpLimit = toolkit.createLabel( client,
                                                 Messages.getString( "JobApplicationPage_CoreDumpLimit" ) ); //$NON-NLS-1$
    this.txtCoreDumpLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtCoreDumpLimit.setLayoutData( td );
//    this.posixApplicationTypeAdapter.attachToCoreDumpLimit( this.txtCoreDumpLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
     
     
    /* ==========================Data Segment Limit ========================== */
    this.lblDataSegmentLimit = toolkit.createLabel( client,
                                                    Messages.getString( "JobApplicationPage_DataSegmentLimit" ) ); //$NON-NLS-1$
    
    this.txtDataSegmentLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtDataSegmentLimit.setLayoutData( td );
//    this.posixApplicationTypeAdapter.attachToDataSegmentLimit( this.txtDataSegmentLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    
    /* ======================== Locked Memory Limit ========================== */
    this.lblLockedMemoryLimit = toolkit.createLabel( client,
                                                     Messages.getString( "JobApplicationPage_LockedMemoryLimit" ) ); //$NON-NLS-1$
    this.txtLockedMemoryLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtLockedMemoryLimit.setLayoutData( td );
//    this.posixApplicationTypeAdapter.attachToLockedMemoryLimit( this.txtLockedMemoryLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    
    /* =============================== Memory Limit ========================== */
    this.lblMemoryLimit = toolkit.createLabel( client,
                                               Messages.getString( "JobApplicationPage_MemoryLimit" ) ); //$NON-NLS-1$
    this.txtMemoryLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtMemoryLimit.setLayoutData( td );
//    this.posixApplicationTypeAdapter.attachToMemoryLimit( this.txtMemoryLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    
    /* ====================== Open Descriptors Limit ========================= */
    this.lblOpenDescriptorsLimit = toolkit.createLabel( client,
                                                        Messages.getString( "JobApplicationPage_OpenDescriptorsLimit" ) ); //$NON-NLS-1$
    this.txtOpenDescriptorsLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtOpenDescriptorsLimit.setLayoutData( td );
//    this.posixApplicationTypeAdapter.attachToOpenDesciptorsLimit( this.txtOpenDescriptorsLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_empty" ) ); //$NON-NLS-1$
    
    
    /* ============================ Pipe Size Limit ========================== */
    this.lblPipeSizeLimit = toolkit.createLabel( client,
                                                 Messages.getString( "JobApplicationPage_PipeSizeLimit" ) ); //$NON-NLS-1$
    this.txtPipeSizeLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtPipeSizeLimit.setLayoutData( td );
//    this.posixApplicationTypeAdapter.attachToPipeSizeLimit( this.txtPipeSizeLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    /* =========================== Stack Size Limit ========================== */
    this.lblStackSizeLimit = toolkit.createLabel( client,
                                                  Messages.getString( "JobApplicationPage_StackSizeLimit" ) ); //$NON-NLS-1$
    this.txtStackSizeLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtStackSizeLimit.setLayoutData( td );
//    this.posixApplicationTypeAdapter.attachToStackSizeLimit( this.txtStackSizeLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    /* ============================= CPU Time Limit ========================== */
    this.lblCPUTimeLimit = toolkit.createLabel( client,
                                                Messages.getString( "JobApplicationPage_CPUTimeLimit" ) ); //$NON-NLS-1$
    this.txtCPUTimeLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtCPUTimeLimit.setLayoutData( td );
//    this.posixApplicationTypeAdapter.attachToCPUTimeLimit( this.txtCPUTimeLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_time" ) ); //$NON-NLS-1$
    
    
    /* ====================== Process Count Limit============================ */
    this.lblProcessCountLimit = toolkit.createLabel( client,
                                                     Messages.getString( "JobApplicationPage_ProcessCountLimit" ) ); //$NON-NLS-1$
    this.txtProcessCountLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtProcessCountLimit.setLayoutData( td );
//    this.posixApplicationTypeAdapter.attachToProcessCountLimit( this.txtProcessCountLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_process" ) ); //$NON-NLS-1$
    
    
    /* ============================= Virtual Memory Limit ==================== */
    this.lblVirtualMemoryLimit = toolkit.createLabel( client,
                                                      Messages.getString( "JobApplicationPage_VirtualMemoryLimit" ) ); //$NON-NLS-1$
    this.txtVirtualMemoryLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtVirtualMemoryLimit.setLayoutData( td );
//    this.posixApplicationTypeAdapter.attachToVirtualMemoryLimit( this.txtVirtualMemoryLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    
    /* ========================= Thread Count Limit ========================== */
    this.lblThreadCountLimit = toolkit.createLabel( client,
                                                    Messages.getString( "JobApplicationPage_ThreadCountLimit" ) ); //$NON-NLS-1$
    this.txtThreadCountLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtThreadCountLimit.setLayoutData( td );
//    this.posixApplicationTypeAdapter.attachToThreadCountLimit( this.txtThreadCountLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_threads" ) ); //$NON-NLS-1$
    
    /* ============================= User Name Limit ======================== */
    this.lblUserName = toolkit.createLabel( client,
                                            Messages.getString( "JobApplicationPage_UserName" ) ); //$NON-NLS-1$
    this.txtUserName = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtUserName.setLayoutData( td );
//    this.posixApplicationTypeAdapter.attachToUserName( this.txtUserName );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_empty" ) ); //$NON-NLS-1$
    
    /* ============================= Group Name Limit ======================== */
    this.lblGroupName = toolkit.createLabel( client,
                                             Messages.getString( "JobApplicationPage_GroupName" ) ); //$NON-NLS-1$
    this.txtGroupName = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtGroupName.setLayoutData( td );
//    this.posixApplicationTypeAdapter.attachToGroupName( this.txtGroupName );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_empty" ) ); //$NON-NLS-1$
    
    toolkit.paintBordersFor( client );
    
  }
  
  
  public void notifyChanged( final Notification notification ) {
    setDirty( true );
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
                        PlatformUI.getWorkbench().getHelpSystem().displayHelpResource(href);
                    }
                } );
            }
        };
        helpAction.setToolTipText( Messages.getString( "JobApplicationPage_Help" ) );  //$NON-NLS-1$
        URL stageInURL = Activator.getDefault().getBundle().getEntry( "icons/help.gif" ); //$NON-NLS-1$       
        this.helpDesc = ImageDescriptor.createFromURL( stageInURL ) ;   
        helpAction.setImageDescriptor( this.helpDesc );
        manager.add( helpAction );
        form.updateToolBar();
    }
    
  }
  
  
  protected String getHelpResource() {
    return "/eu.geclipse.doc.user/html/concepts/jobmanagement/editorpages/application.html"; //$NON-NLS-1$
  }
  
  
} // End JobApplicationPage Class
