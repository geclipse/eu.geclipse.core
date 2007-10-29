/*******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium All rights reserved. This program and
 * the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Initial development of the original
 * code was made for project g-Eclipse founded by European Union project number:
 * FP6-IST-034327 http://www.geclipse.eu/ Contributor(s): UCY
 * (http://www.ucy.cs.ac.cy) - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 ******************************************************************************/
package eu.geclipse.jsdl.ui.internal.pages;

/**
 * @author nloulloud
 */
import java.net.URL;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;
import eu.geclipse.jsdl.model.posix.ArgumentType;
import eu.geclipse.jsdl.model.posix.EnvironmentType;
import eu.geclipse.jsdl.ui.adapters.jsdl.ApplicationTypeAdapter;
import eu.geclipse.jsdl.ui.adapters.posix.PosixApplicationTypeAdapter;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.dialogs.MultipleInputDialog;
import eu.geclipse.jsdl.ui.providers.FeatureContentProvider;
import eu.geclipse.jsdl.ui.providers.FeatureLabelProvider;

/**
 * This class provides the Job Application page that appears in the JSDL editor.
 * It provides a graphical user interface to the following elements of a JSDL
 * document: Application Name Application Version Application Description
 */
public final class JobApplicationPage extends FormPage
  implements INotifyChangedListener
{

  protected static final String PAGE_ID = "APPLICATION"; //$NON-NLS-1$
  protected Composite body = null;
  protected Composite aS = null;
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
  protected ApplicationTypeAdapter applicationTypeAdapter;
  protected PosixApplicationTypeAdapter posixApplicationTypeAdapter;
  protected Object[][] value = null;
  
  private ImageDescriptor helpDesc = null;
  private TableColumn column;
  private boolean contentRefreshed = false;
  private boolean dirtyFlag = false;
  private final int WIDGET_HEIGHT = 100;

  
  
  /**
   * JobApplicationPage class constructor. Initializes the JobApplica Page by
   * passing as an argument the container JSDL editor.
   * 
   * @param editor
   */
  public JobApplicationPage( final FormEditor editor ) {
    super( editor, PAGE_ID, Messages.getString( "JobApplicationPage_PageTitle" ) ); //$NON-NLS-1$
  } // End Class Constructor

  
  
  @Override
  public void setActive( final boolean active ) {
    if( active ) {
      if( isContentRefreshed() ) {
        this.applicationTypeAdapter.load();
        this.posixApplicationTypeAdapter.load();
      }// end_if isContentRefreshed
    } // end_if active
  } // End void setActive()

  
  
  @Override
  public boolean isDirty() {
    return this.dirtyFlag;
  } // End boolean isDirty()

  
  
  /**
   * This method set's the dirty status of the page.
   * 
   * @param dirty If TRUE then the page is Dirty and a Save operation is needed.
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
   * @param rootJsdlElement
   * @param refreshStatus Set to TRUE if the original page content is already
   *            set, but there is a need to refresh the page because there was a
   *            change to this content from an outside editor.
   */
  public void setPageContent( final EObject rootJsdlElement,
                              final boolean refreshStatus )
  {
    if( refreshStatus ) {
      this.contentRefreshed = true;
      this.applicationTypeAdapter.setContent( rootJsdlElement );
      this.posixApplicationTypeAdapter.setContent( rootJsdlElement );
    } else {
      /* Initialize the ApplicationTypeAdapter for this page */
      this.applicationTypeAdapter = new ApplicationTypeAdapter( rootJsdlElement );
      /* Add Save State change notification listener. */
      this.applicationTypeAdapter.addListener( this );
      /* Initialize the PosixApplicationTypeAdapter for this page */
      this.posixApplicationTypeAdapter = new PosixApplicationTypeAdapter( rootJsdlElement );
      /* Add Save State change notification listener. */
      this.posixApplicationTypeAdapter.addListener( this );
    } // End else
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
    
    this.aS = toolkit.createComposite( this.body );
    this.aS.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1 ) );
    this.aS.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );
    
    createApplicationSection( this.aS, toolkit );
    this.apS = toolkit.createComposite( this.body );
    this.apS.setLayout( FormLayoutFactory.createFormPaneTableWrapLayout( false, 1 ) );
    this.apS.setLayoutData( new TableWrapData( TableWrapData.FILL_GRAB ) );
    createAdditionalPosixSection( this.apS, toolkit );
    createPosixSection( this.aS, toolkit );
    
    /* Load the Adapters for this page */
    this.applicationTypeAdapter.load();
    this.posixApplicationTypeAdapter.load();
    
    /* Also add the help system */
    addFormPageHelp( form );
    
  }

  
  
  /*
   * Create the Application Section which includes the following: -Application
   * Name (String) -Application Version (String) -Application Description
   * (String)
   */
  private void createApplicationSection( final Composite parent,
                                         final FormToolkit toolkit )
  {
    String sectionTitle = Messages.getString( "JobApplicationPage_Applicationtitle" ); //$NON-NLS-1$
    String sectionDescription = Messages.getString( "JobApplicationPage_ApplicationDescription" ); //$NON-NLS-1$
    GridData gd;
    Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                                                   parent,
                                                                   sectionTitle,
                                                                   sectionDescription,
                                                                   2 );
    // Create Label and Text for Application Name Element
    this.lblApplicationName = toolkit.createLabel( client,
                                                   Messages.getString( "JobApplicationPage_ApplicationName" ) ); //$NON-NLS-1$
    this.txtApplicationName = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    gd = new GridData();
    gd.widthHint = 300;
    this.txtApplicationName.setLayoutData( gd );
    this.applicationTypeAdapter.attachToApplicationName( this.txtApplicationName );
    // Create Label and Text for Application Version
    this.lblApplicationVersion = toolkit.createLabel( client,
                                                      Messages.getString( "JobApplicationPage_ApplicationVersion" ) ); //$NON-NLS-1$
    this.txtApplicationVersion = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    gd = new GridData();
    gd.widthHint = 300;
    this.txtApplicationVersion.setLayoutData( gd );
    this.applicationTypeAdapter.attachToApplicationVersion( this.txtApplicationVersion );
    // Create Label and Text for Application Description Element
    this.lblApplicationDescription = toolkit.createLabel( client,
                                                          Messages.getString( "JobApplicationPage_ApplicationDescr" ) ); //$NON-NLS-1$
    this.txtDescription = toolkit.createText( client, "", //$NON-NLS-1$
                                              SWT.NONE
                                                  | SWT.V_SCROLL
                                                  | SWT.WRAP );
    gd = new GridData();
    gd.verticalSpan = 3;
    gd.widthHint = 285;
    gd.heightHint = this.WIDGET_HEIGHT;
    this.txtDescription.setLayoutData( gd );
    this.applicationTypeAdapter.attachToApplicationDescription( this.txtDescription );
    toolkit.paintBordersFor( client );
  } // End void createApplicationSection()
  
  
  

  /*
   * Create the Posix Application Section which includes the following:
   * -Executable (String) -Argument (String) -Input (String) -Output (String)
   * -Error (String) -Environment (String)
   */
  private void createPosixSection( final Composite parent,
                                   final FormToolkit toolkit )
  {
    String sectionTitle = Messages.getString( "JobApplicationPage_PosixApplicationtitle" ); //$NON-NLS-1$
    String sectionDescripiton = Messages.getString( "JobApplicationPage_PosixApplicationDescription" ); //$NON-NLS-1$
    GridData gd;
    /* ===================== Posix Application Widget ======================= */
    Composite client = FormSectionFactory.createGridStaticSection( toolkit,
                                                                   parent,
                                                                   sectionTitle,
                                                                   sectionDescripiton,
                                                                   4 );
    gd = new GridData();
    gd.grabExcessHorizontalSpace = true;
    gd.verticalAlignment = GridData.CENTER;
    gd.verticalSpan = 1;
    gd.horizontalSpan = 3;
    gd.widthHint = 330;
    /* Posix Name Widget */
    this.lblPosixName = toolkit.createLabel( client,
                                             Messages.getString( "JobApplicationPage_PosixName" ) ); //$NON-NLS-1$
    this.txtPosixName = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.posixApplicationTypeAdapter.attachPosixApplicationName( this.txtPosixName );
    this.txtPosixName.setLayoutData( gd );
    /* =========================== Executable Widget ======================== */
    gd = new GridData();
    gd.horizontalSpan = 1;
    this.lblExecutable = toolkit.createLabel( client,
                                              Messages.getString( "JobApplicationPage_Executable" ) ); //$NON-NLS-1$
    this.lblExecutable.setLayoutData( gd );
    this.txtExecutable = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.posixApplicationTypeAdapter.attachPosixApplicationExecutable( this.txtExecutable );
    gd = new GridData();
    gd.widthHint = 330;
    gd.horizontalSpan = 3;
    gd.grabExcessHorizontalSpace = true;
    gd.verticalAlignment = GridData.CENTER;
    gd.verticalSpan = 1;
    this.txtExecutable.setLayoutData( gd );
    /* ============================= Argument Widget ======================== */
    gd = new GridData();
    gd.verticalSpan = 2;
    gd.horizontalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    this.lblArgument = toolkit.createLabel( client,
                                            Messages.getString( "JobApplicationPage_Argument" ) ); //$NON-NLS-1$
    this.lblArgument.setLayoutData( gd );
    gd = new GridData();
    gd.horizontalAlignment = GridData.FILL;
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 3;
    gd.horizontalSpan = 1;
    gd.widthHint = 250;
    gd.heightHint = this.WIDGET_HEIGHT;
    this.argumentViewer = new TableViewer( client, SWT.BORDER | SWT.FULL_SELECTION );
    this.tblArgument = this.argumentViewer.getTable();
    this.tblArgument.setHeaderVisible( true );
    this.argumentViewer.setContentProvider( new FeatureContentProvider() );
    this.argumentViewer.setLabelProvider( new FeatureLabelProvider() );
    this.column = new TableColumn( this.tblArgument, SWT.NONE );
    this.column.setText( "File System Name" ); //$NON-NLS-1$
    this.column.setWidth( 150 );
    this.column = new TableColumn( this.tblArgument, SWT.NONE );
    this.column.setText( "Value" ); //$NON-NLS-1$
    this.column.setWidth( 100 );
    this.posixApplicationTypeAdapter.attachToPosixApplicationArgument( this.argumentViewer );
    this.argumentViewer.addSelectionChangedListener( new ISelectionChangedListener()
    {

      public void selectionChanged( final SelectionChangedEvent event ) {
        updateButtons( ( TableViewer )event.getSource() );
      }
    } );
    this.tblArgument.setData( FormToolkit.KEY_DRAW_BORDER );
    this.tblArgument.setLayoutData( gd );
    /* Create "Add" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    this.btnArgAdd = toolkit.createButton( client,
                                           Messages.getString( "JsdlEditor_AddButton" ), //$NON-NLS-1$
                                           SWT.PUSH );
    this.btnArgAdd.addSelectionListener( new SelectionListener() {

      public void widgetSelected( final SelectionEvent event ) {
        handleAddDialog( Messages.getString( "JobApplicationPage_ArgumentDialog" ), //$NON-NLS-1$
                         ( Button )event.getSource() );
        JobApplicationPage.this.posixApplicationTypeAdapter.performAdd( JobApplicationPage.this.argumentViewer,
                                                                        "argumentViewer", //$NON-NLS-1$
                                                                        JobApplicationPage.this.value );
      }

      public void widgetDefaultSelected( final SelectionEvent event ) {
        // Do Nothing - Required method
      }
    } );
    this.btnArgAdd.setLayoutData( gd );
    /* Create "Edit" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    this.btnArgEdit = toolkit.createButton( client,
                                            Messages.getString( "JsdlEditor_EditButton" ), //$NON-NLS-1$
                                            SWT.PUSH );
    this.btnArgEdit.addSelectionListener( new SelectionListener() {

      public void widgetSelected( final SelectionEvent event ) {
        handleAddDialog( Messages.getString( "JobApplicationPage_ArgumentDialog" ), //$NON-NLS-1$
                         ( Button )event.getSource() );
        JobApplicationPage.this.posixApplicationTypeAdapter.performEdit( JobApplicationPage.this.argumentViewer,
                                                                         JobApplicationPage.this.value );
      }

      public void widgetDefaultSelected( final SelectionEvent event ) {
        // Do Nothing - Required method
      }
    } );
    this.btnArgEdit.setLayoutData( gd );
    /* Create "Remove" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    gd.verticalAlignment = GridData.BEGINNING;
    this.btnArgDel = toolkit.createButton( client,
                                           Messages.getString( "JsdlEditor_RemoveButton" ), //$NON-NLS-1$
                                           SWT.PUSH );
    this.btnArgDel.setEnabled( false );
    this.posixApplicationTypeAdapter.attachToDelete( this.btnArgDel,
                                                     this.argumentViewer );
    this.btnArgDel.setLayoutData( gd );
    /* ============================= Input Widget =========================== */
    gd = new GridData();
    gd.horizontalSpan = 1;
    this.lblInput = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_Input" ) ); //$NON-NLS-1$
    this.lblInput.setLayoutData( gd );
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.horizontalSpan = 3;
    gd.widthHint = 330;
    this.txtInput = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.posixApplicationTypeAdapter.attachPosixApplicationInput( this.txtInput );
    this.txtInput.setLayoutData( gd );
    /* ============================= Output Widget =========================== */
    gd = new GridData();
    this.lblOutput = toolkit.createLabel( client,
                                          Messages.getString( "JobApplicationPage_Output" ) ); //$NON-NLS-1$
    this.lblOutput.setLayoutData( gd );
    this.txtOutput = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.posixApplicationTypeAdapter.attachPosixApplicationOutput( this.txtOutput );
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.horizontalSpan = 3;
    gd.widthHint = 330;
    this.txtOutput.setLayoutData( gd );
    /* ============================= Error Widget =========================== */
    gd = new GridData();
    this.lblError = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_Error" ) ); //$NON-NLS-1$
    this.lblError.setLayoutData( gd );
    gd = new GridData();
    gd.verticalAlignment = GridData.FILL;
    gd.horizontalSpan = 3;
    gd.widthHint = 330;
    this.txtError = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    this.posixApplicationTypeAdapter.attachPosixApplicationError( this.txtError );
    this.txtError.setLayoutData( gd );
    /* ======================= Environment Widget =========================== */
    gd = new GridData();
    gd.verticalSpan = 2;
    gd.horizontalSpan = 1;
    gd.verticalAlignment = GridData.BEGINNING;
    this.lblEnvironment = toolkit.createLabel( client,
                                               Messages.getString( "JobApplicationPage_Environment" ) ); //$NON-NLS-1$
    this.lblEnvironment.setLayoutData( gd );
    gd = new GridData();
    gd.horizontalAlignment = GridData.FILL;
    gd.verticalAlignment = GridData.FILL;
    gd.verticalSpan = 3;
    gd.horizontalSpan = 1;
    gd.widthHint = 250;
    gd.heightHint = this.WIDGET_HEIGHT;
    this.environmentViewer = new TableViewer( client, SWT.BORDER | SWT.FULL_SELECTION );
    this.tblEnvironment = this.environmentViewer.getTable();
    this.tblEnvironment.setHeaderVisible( true );
    this.environmentViewer.setContentProvider( new FeatureContentProvider() );
    this.environmentViewer.setLabelProvider( new FeatureLabelProvider() );
    this.column = new TableColumn( this.tblEnvironment, SWT.NONE );
    this.column.setText( "Name" ); //$NON-NLS-1$
    this.column.setWidth( 60 );
    this.column = new TableColumn( this.tblEnvironment, SWT.NONE );
    this.column.setText( "File System Name" ); //$NON-NLS-1$
    this.column.setWidth( 130 );
    this.column = new TableColumn( this.tblEnvironment, SWT.NONE );
    this.column.setText( "Value" ); //$NON-NLS-1$
    this.column.setWidth( 60 );
    this.posixApplicationTypeAdapter.attachToPosixApplicationEnvironment( this.environmentViewer );
    this.environmentViewer.addSelectionChangedListener( new ISelectionChangedListener()
    {

      public void selectionChanged( final SelectionChangedEvent event ) {
        updateButtons( ( TableViewer )event.getSource() );
      }
    } );
    this.tblEnvironment.setData( FormToolkit.KEY_DRAW_BORDER );
    this.tblEnvironment.setLayoutData( gd );
    /* Create "Add" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    this.btnEnVarAdd = toolkit.createButton( client,
                                             Messages.getString( "JsdlEditor_AddButton" ), //$NON-NLS-1$
                                             SWT.BUTTON1 );
    this.btnEnVarAdd.addSelectionListener( new SelectionListener() {

      public void widgetSelected( final SelectionEvent event ) {
        handleAddDialog( Messages.getString( "JobApplicationPage_EnvironmentDialog" ), //$NON-NLS-1$
                         ( Button )event.getSource() );
        JobApplicationPage.this.posixApplicationTypeAdapter.performAdd( JobApplicationPage.this.environmentViewer,
                                                                        "environmentiewer", //$NON-NLS-1$
                                                                        JobApplicationPage.this.value );
      }

      public void widgetDefaultSelected( final SelectionEvent event ) {
        // Do Nothing - Required method
      }
    } );
    this.btnEnVarAdd.setLayoutData( gd );
    /* Create "Edit" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    this.btnEnVarEdit = toolkit.createButton( client,
                                              Messages.getString( "JsdlEditor_EditButton" ), //$NON-NLS-1$
                                              SWT.PUSH );
    this.btnEnVarEdit.addSelectionListener( new SelectionListener() {

      public void widgetSelected( final SelectionEvent event ) {
        handleAddDialog( Messages.getString( "JobApplicationPage_EnvironmentDialog" ), //$NON-NLS-1$
                         ( Button )event.getSource() );
        JobApplicationPage.this.posixApplicationTypeAdapter.performEdit( JobApplicationPage.this.environmentViewer,
                                                                         JobApplicationPage.this.value );
      }

      public void widgetDefaultSelected( final SelectionEvent event ) {
        // Do Nothing - Required method
      }
    } );
    this.btnEnVarEdit.setLayoutData( gd );
    /* Create "Remove" Button */
    gd = new GridData();
    gd.horizontalSpan = 2;
    gd.verticalSpan = 1;
    gd.widthHint = 60;
    gd.verticalAlignment = GridData.BEGINNING;
    this.btnEnVarDel = toolkit.createButton( client,
                                             Messages.getString( "JsdlEditor_RemoveButton" ), //$NON-NLS-1$
                                             SWT.PUSH );
    this.btnEnVarDel.setEnabled( false );
    this.posixApplicationTypeAdapter.attachToDelete( this.btnEnVarDel,
                                                     this.environmentViewer );
    this.btnEnVarDel.setLayoutData( gd );
    toolkit.paintBordersFor( client );
  }

  
  
  /*
   * Create Additional Posix Application Section which includes the following:
   * -Working Directory (String) -Wall Time Limit (nonNegativeInteger) -File
   * Size Limit (nonNegativeInteger) -Core Dump Limit (nonNegativeInteger) -Data
   * Segment Limit (nonNegativeInteger) -Locked Memory Limit
   * (nonNegativeInteger) -Memory Limit (nonNegativeInteger) -Open Descriptors
   * Limit (nonNegativeInteger) -Pipe Size Limit (nonNegativeInteger) -Stack
   * Size Limit (nonNegativeInteger) -CPU Time Limit (nonNegativeInteger)
   * -Process Count Limit (nonNegativeInteger) -Virtual Memory Limit
   * (nonNegativeInteger) -Thread Count Limit (nonNegativeInteger) -User Name
   * (String) -Group Name (String)
   */
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
    this.posixApplicationTypeAdapter.attachToWorkingDirectory( this.txtWorkingDirectory );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_empty" ) ); //$NON-NLS-1$
    
    
    /* ============================ Wall Time Limit ========================= */
    this.lblWallTimeLimit = toolkit.createLabel( client,
                                                 Messages.getString( "JobApplicationPage_WallTimeLimit" ) ); //$NON-NLS-1$
    this.txtWallTimeLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtWallTimeLimit.setLayoutData( td );
    this.posixApplicationTypeAdapter.attachToWallTimeLimit(this.txtWallTimeLimit);
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_time" ) ); //$NON-NLS-1$

    
    /* ============================ File Size Limit ========================== */
    this.lblFileSizeLimit = toolkit.createLabel( client,
                                                 Messages.getString( "JobApplicationPage_FileSizeLimit" ) ); //$NON-NLS-1$
    this.txtFileSizeLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtFileSizeLimit.setLayoutData( td );
    this.posixApplicationTypeAdapter.attachToFileSizeLimit( this.txtFileSizeLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$

    
     
    /* ============================ Core Dump Limit ========================== */
    this.lblCoreDumpLimit = toolkit.createLabel( client,
                                                 Messages.getString( "JobApplicationPage_CoreDumpLimit" ) ); //$NON-NLS-1$
    this.txtCoreDumpLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtCoreDumpLimit.setLayoutData( td );
    this.posixApplicationTypeAdapter.attachToCoreDumpLimit( this.txtCoreDumpLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
     
     
    /* ==========================Data Segment Limit ========================== */
    this.lblDataSegmentLimit = toolkit.createLabel( client,
                                                    Messages.getString( "JobApplicationPage_DataSegmentLimit" ) ); //$NON-NLS-1$
    this.txtDataSegmentLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtDataSegmentLimit.setLayoutData( td );
    this.posixApplicationTypeAdapter.attachToDataSegmentLimit( this.txtDataSegmentLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    
    /* ======================== Locked Memory Limit ========================== */
    this.lblLockedMemoryLimit = toolkit.createLabel( client,
                                                     Messages.getString( "JobApplicationPage_LockedMemoryLimit" ) ); //$NON-NLS-1$
    this.txtLockedMemoryLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtLockedMemoryLimit.setLayoutData( td );
    this.posixApplicationTypeAdapter.attachToLockedMemoryLimit( this.txtLockedMemoryLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    
    /* =============================== Memory Limit ========================== */
    this.lblMemoryLimit = toolkit.createLabel( client,
                                               Messages.getString( "JobApplicationPage_MemoryLimit" ) ); //$NON-NLS-1$
    this.txtMemoryLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtMemoryLimit.setLayoutData( td );
    this.posixApplicationTypeAdapter.attachToMemoryLimit( this.txtMemoryLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    
    /* ====================== Open Descriptors Limit ========================= */
    this.lblOpenDescriptorsLimit = toolkit.createLabel( client,
                                                        Messages.getString( "JobApplicationPage_OpenDescriptorsLimit" ) ); //$NON-NLS-1$
    this.txtOpenDescriptorsLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtOpenDescriptorsLimit.setLayoutData( td );
    this.posixApplicationTypeAdapter.attachToOpenDesciptorsLimit( this.txtOpenDescriptorsLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_empty" ) ); //$NON-NLS-1$
    
    
    /* ============================ Pipe Size Limit ========================== */
    this.lblPipeSizeLimit = toolkit.createLabel( client,
                                                 Messages.getString( "JobApplicationPage_PipeSizeLimit" ) ); //$NON-NLS-1$
    this.txtPipeSizeLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtPipeSizeLimit.setLayoutData( td );
    this.posixApplicationTypeAdapter.attachToPipeSizeLimit( this.txtPipeSizeLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    /* =========================== Stack Size Limit ========================== */
    this.lblStackSizeLimit = toolkit.createLabel( client,
                                                  Messages.getString( "JobApplicationPage_StackSizeLimit" ) ); //$NON-NLS-1$
    this.txtStackSizeLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtStackSizeLimit.setLayoutData( td );
    this.posixApplicationTypeAdapter.attachToStackSizeLimit( this.txtStackSizeLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    /* ============================= CPU Time Limit ========================== */
    this.lblCPUTimeLimit = toolkit.createLabel( client,
                                                Messages.getString( "JobApplicationPage_CPUTimeLimit" ) ); //$NON-NLS-1$
    this.txtCPUTimeLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtCPUTimeLimit.setLayoutData( td );
    this.posixApplicationTypeAdapter.attachToCPUTimeLimit( this.txtCPUTimeLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_time" ) ); //$NON-NLS-1$
    
    
    /* ====================== Process Count Limit============================ */
    this.lblProcessCountLimit = toolkit.createLabel( client,
                                                     Messages.getString( "JobApplicationPage_ProcessCountLimit" ) ); //$NON-NLS-1$
    this.txtProcessCountLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtProcessCountLimit.setLayoutData( td );
    this.posixApplicationTypeAdapter.attachToProcessCountLimit( this.txtProcessCountLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_process" ) ); //$NON-NLS-1$
    
    
    /* ============================= Virtual Memory Limit ==================== */
    this.lblVirtualMemoryLimit = toolkit.createLabel( client,
                                                      Messages.getString( "JobApplicationPage_VirtualMemoryLimit" ) ); //$NON-NLS-1$
    this.txtVirtualMemoryLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtVirtualMemoryLimit.setLayoutData( td );
    this.posixApplicationTypeAdapter.attachToVirtualMemoryLimit( this.txtVirtualMemoryLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_size" ) ); //$NON-NLS-1$
    
    
    /* ========================= Thread Count Limit ========================== */
    this.lblThreadCountLimit = toolkit.createLabel( client,
                                                    Messages.getString( "JobApplicationPage_ThreadCountLimit" ) ); //$NON-NLS-1$
    this.txtThreadCountLimit = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtThreadCountLimit.setLayoutData( td );
    this.posixApplicationTypeAdapter.attachToThreadCountLimit( this.txtThreadCountLimit );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_threads" ) ); //$NON-NLS-1$
    
    /* ============================= User Name Limit ======================== */
    this.lblUserName = toolkit.createLabel( client,
                                            Messages.getString( "JobApplicationPage_UserName" ) ); //$NON-NLS-1$
    this.txtUserName = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtUserName.setLayoutData( td );
    this.posixApplicationTypeAdapter.attachToUserName( this.txtUserName );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_empty" ) ); //$NON-NLS-1$
    
    /* ============================= Group Name Limit ======================== */
    this.lblGroupName = toolkit.createLabel( client,
                                             Messages.getString( "JobApplicationPage_GroupName" ) ); //$NON-NLS-1$
    this.txtGroupName = toolkit.createText( client, "", SWT.NONE ); //$NON-NLS-1$
    td = new TableWrapData( TableWrapData.FILL_GRAB );
    this.txtGroupName.setLayoutData( td );
    this.posixApplicationTypeAdapter.attachToGroupName( this.txtGroupName );
    this.lblUnits = toolkit.createLabel( client,
                                         Messages.getString( "JobApplicationPage_empty" ) ); //$NON-NLS-1$
        
    toolkit.paintBordersFor( client );
  }

  protected void handleAddDialog( final String dialogTitle, final Button button )
  {
    MultipleInputDialog dialog = new MultipleInputDialog( this.getSite()
      .getShell(), dialogTitle );
    if( dialogTitle == Messages.getString( "JobApplicationPage_ArgumentDialog" ) ) { //$NON-NLS-1$
      this.value = new Object[ 1 ][ 2 ];
      if( button == this.btnArgAdd ) {
        dialog.addStoredComboField( Messages.getString( "JobApplicationPage_FileSystemName" ), "", true, "JobApplicationPage_FileSystemName" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$      
        dialog.addStoredComboField( Messages.getString( "JobApplicationPage_Value" ), "", false , "JobApplicationPage_Value" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      } else {
        IStructuredSelection structSelection = ( IStructuredSelection )this.argumentViewer.getSelection();
        ArgumentType argType = ( ArgumentType )structSelection.getFirstElement();
        dialog.addStoredComboField( Messages.getString( "JobApplicationPage_FileSystemName" ), //$NON-NLS-1$
                             argType.getFilesystemName(),
                             true,
                             "JobApplicationPage_FileSystemName"); //$NON-NLS-1$
        dialog.addStoredComboField( Messages.getString( "JobApplicationPage_Value" ), //$NON-NLS-1$
                             argType.getValue(),
                             false,
                             "JobApplicationPage_Value"); //$NON-NLS-1$
      }
    } else {
      this.value = new Object[ 1 ][ 3 ];
      if( button == this.btnEnVarAdd ) {
        dialog.addStoredComboField( Messages.getString( "JobApplicationPage_Name" ), "", false, "JobApplicationPage_Name" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        dialog.addStoredComboField( Messages.getString( "JobApplicationPage_FileSystemName" ), "", true, "JobApplicationPage_FileSystemName" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        dialog.addStoredComboField( Messages.getString( "JobApplicationPage_Value" ), "", false, "JobApplicationPage_Value" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      } else {
        IStructuredSelection structSelection = ( IStructuredSelection )this.environmentViewer.getSelection();
        EnvironmentType envType = ( EnvironmentType )structSelection.getFirstElement();
        dialog.addStoredComboField( Messages.getString( "JobApplicationPage_Name" ), //$NON-NLS-1$
                             envType.getName(),
                             false,
                             "JobApplicationPage_Name"); //$NON-NLS-1$
        dialog.addStoredComboField( Messages.getString( "JobApplicationPage_FileSystemName" ), //$NON-NLS-1$
                             envType.getFilesystemName(),
                             true,
                             "JobApplicationPage_FileSystemName"); //$NON-NLS-1$
        dialog.addStoredComboField( Messages.getString( "JobApplicationPage_Value" ), //$NON-NLS-1$
                             envType.getValue(),
                             false,
                             "JobApplicationPage_Value"); //$NON-NLS-1$

      }
    }
    
    if( dialog.open() != Window.OK ) {
      return;
    }
    
    if( this.value[ 0 ].length == 2 ) {
      this.value[ 0 ][ 0 ] = dialog.getStringValue( Messages.getString( "JobApplicationPage_FileSystemName" ) ); //$NON-NLS-1$
      this.value[ 0 ][ 1 ] = dialog.getStringValue( Messages.getString( "JobApplicationPage_Value" ) ); //$NON-NLS-1$
    } else {
      this.value[ 0 ][ 0 ] = dialog.getStringValue( Messages.getString( "JobApplicationPage_Name" ) ); //$NON-NLS-1$
      this.value[ 0 ][ 1 ] = dialog.getStringValue( Messages.getString( "JobApplicationPage_FileSystemName" ) ); //$NON-NLS-1$  
      this.value[ 0 ][ 2 ] = dialog.getStringValue( Messages.getString( "JobApplicationPage_Value" ) ); //$NON-NLS-1$
    }
  }
  
  
  
  public void notifyChanged( final Notification notification ) {
    setDirty( true );
  }
  
  
  private void addFormPageHelp(final ScrolledForm form ) {
    
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
  

  
  protected void updateButtons( final TableViewer tableViewer ) {
    
    ISelection selection = tableViewer.getSelection();
    boolean selectionAvailable = !selection.isEmpty();
    
    if( tableViewer == this.argumentViewer ) {
      this.btnArgAdd.setEnabled( true );
      this.btnArgEdit.setEnabled( selectionAvailable );
      this.btnArgDel.setEnabled( selectionAvailable );
    } else {
      this.btnEnVarAdd.setEnabled( true );
      this.btnEnVarEdit.setEnabled( selectionAvailable );
      this.btnEnVarDel.setEnabled( selectionAvailable );
    }
    
  } // End updateButtons
  
  
  
} // End Class
