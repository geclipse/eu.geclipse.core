/******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium 
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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/

package eu.geclipse.ui.wizards.jobs;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import eu.geclipse.ui.Extensions;
import eu.geclipse.ui.dialogs.gexplorer.GridFileDialog;
import eu.geclipse.ui.widgets.StoredCombo;

/**
 * Wizard page that allows user to choose an executable for the grid job, name
 * of the job and its description
 * 
 * @author katis
 */
public class ExecutableNewJobWizardPage extends WizardSelectionPage
  implements ModifyListener
{
  /**
   * Key for the executable file preference.
   */
  private static String INPUT_EXE_ID = "executable_file"; //$NON-NLS-1$
  
  /**
   * Holds the name of the executable
   */
  StoredCombo executableFile;
  
  Composite parentP;

  
  /**
   * Button for oppening {@link GridFileDialog} - a dialog for chosing local or
   * remote files
   */
  private Button gridFileDialogButton;
  /**
   * Holds name of the job to run
   */
  private Text jobName;
  /**
   * Holds description of the job
   */
  private Text jobDescription;
  /**
   * Holds name of the application
   */
  private Combo applicationName;
//  private Combo application;
  private Map<String, String> appsWithExtraAttributes;
  private ArrayList<WizardPage> internalPages;
  private InternalWizard nodeInternal;
  
  
  /**
   * Creates new wizard page
   * 
   * @param pageName name of the page
   * @param internalPages
   */
  protected ExecutableNewJobWizardPage( final String pageName,
                                        final ArrayList<WizardPage> internalPages )
  {
    super( pageName );
    setTitle( Messages.getString( "ExecutableNewJobWizardPage.title" ) ); //$NON-NLS-1$
    setDescription( Messages.getString( "ExecutableNewJobWizardPage.description" ) ); //$NON-NLS-1$
    this.appsWithExtraAttributes = Extensions.getApplicationParametersXMLMap();
    this.internalPages = internalPages;
  }
  
  @Override
  public IWizardPage getNextPage()
  {
    // TODO Auto-generated method stub
    return super.getNextPage();
  }

  

  @Override
  public boolean isPageComplete()
  {
    return true;
  }

  

  public void createControl( final Composite parent ) {
    this.parentP = parent;
    Composite mainComp = new Composite( parent, SWT.NONE );
    IPreferenceStore prefs = eu.geclipse.ui.internal.Activator.getDefault()
      .getPreferenceStore();
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    Image fileImage = sharedImages.getImage( ISharedImages.IMG_OBJ_FILE );
    GridLayout gLayout = new GridLayout( 3, false );
    gLayout.horizontalSpacing = 10;
    gLayout.verticalSpacing = 12;
    mainComp.setLayout( gLayout );
    GridData layout = new GridData();
    Label nameLabel = new Label( mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                                           | GridData.VERTICAL_ALIGN_CENTER );
    nameLabel.setText( Messages.getString( "ExecutableNewJobWizardPage.job_name_label" ) ); //$NON-NLS-1$
    layout.horizontalAlignment = GridData.FILL;
    nameLabel.setLayoutData( layout );
    this.jobName = new Text( mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                                       | GridData.VERTICAL_ALIGN_CENTER
                                       | SWT.BORDER );
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 2;
    this.jobName.setLayoutData( layout );
    Label applicationNameLabel = new Label( mainComp,
                                            GridData.HORIZONTAL_ALIGN_BEGINNING
                                                | GridData.VERTICAL_ALIGN_CENTER );
    applicationNameLabel.setText( Messages.getString( "ExecutableNewJobWizardPage.application_name_label" ) ); //$NON-NLS-1$
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    nameLabel.setLayoutData( layout );
    this.applicationName = new Combo( mainComp, SWT.SINGLE );
    for( String value : this.appsWithExtraAttributes.values() ) {
      this.applicationName.add( value.toString() );
    }
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 2;
    this.applicationName.setLayoutData( layout );
    this.applicationName.addModifyListener( this );
    // label and combo for infromation from plugins
    // label
//    Label pluginNameLabel = new Label( mainComp,
//                                       GridData.HORIZONTAL_ALIGN_BEGINNING
//                                           | GridData.VERTICAL_ALIGN_CENTER );
//    layout = new GridData();
//    layout.horizontalAlignment = GridData.FILL;
//    pluginNameLabel.setText( "Plug-in name" );
//    pluginNameLabel.setLayoutData( layout );
    // combo
//    this.application = new Combo( mainComp, SWT.SINGLE );
//    for( String value : this.appsWithExtraAttributes.values() ) {
//      this.application.add( value.toString() );
//    }
//    layout = new GridData( GridData.HORIZONTAL_ALIGN_FILL );
//    layout.horizontalSpan = 2;
//    layout.grabExcessHorizontalSpace = true;
//    this.application.setLayoutData( layout );
//    this.application.addModifyListener( this );
    // end
    Label inputLabel = new Label( mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                                            | GridData.VERTICAL_ALIGN_CENTER );
    inputLabel.setText( Messages.getString( "ExecutableNewJobWizardPage.exe_input_label" ) ); //$NON-NLS-1$
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    inputLabel.setLayoutData( layout );
    this.executableFile = new StoredCombo( mainComp, SWT.DROP_DOWN );
    this.executableFile.setPreferences( prefs, INPUT_EXE_ID );
    this.executableFile.setText( "" ); //$NON-NLS-1$
    layout = new GridData( GridData.FILL_HORIZONTAL
                           | GridData.GRAB_HORIZONTAL
                           | GridData.VERTICAL_ALIGN_CENTER );
    this.executableFile.setLayoutData( layout );
    this.gridFileDialogButton = new Button( mainComp, SWT.PUSH );
    this.gridFileDialogButton.setImage( fileImage );
    layout = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_CENTER );
    this.gridFileDialogButton.setLayoutData( layout );
    Label descriptionLabel = new Label( mainComp,
                                        GridData.HORIZONTAL_ALIGN_BEGINNING
                                            | GridData.VERTICAL_ALIGN_CENTER );
    descriptionLabel.setText( Messages.getString( "ExecutableNewJobWizardPage.job_description_label" ) ); //$NON-NLS-1$
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 3;
    descriptionLabel.setLayoutData( layout );
    this.jobDescription = new Text( mainComp, SWT.MULTI
                                              | SWT.BORDER
                                              | SWT.WRAP
                                              | SWT.V_SCROLL );
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.verticalAlignment = GridData.FILL;
    layout.horizontalSpan = 3;
    layout.verticalSpan = 10;
    layout.horizontalIndent = 25;
    this.jobDescription.setLayoutData( layout );
    this.gridFileDialogButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e )
      {
        GridFileDialog dialog = new GridFileDialog( getShell() );
        String filename = dialog.open();
        if( filename != null ) {
          ExecutableNewJobWizardPage.this.executableFile.setText( filename );
        }
      }
    } );
    setControl( mainComp );
  }

  /**
   * Returns name of executable to run on grid
   * 
   * @return name of executable
   */
  public String getExecutableFile() {
    String result = this.executableFile.getText();
    try {
      URI newURI = new URI( this.executableFile.getText() );
      String scheme = newURI.getScheme();
      if( scheme != null ) {
        result = result.substring( scheme.length() + 2, result.length() );
      } else {
        // this means user has specified executable name from hand (not choosing
        // a file)
        result = this.executableFile.getText();
      }
    } catch( URISyntaxException URISyntaxExc ) {
      eu.geclipse.ui.internal.Activator.logException( URISyntaxExc );
    }
    return result;
  }

  /**
   * Returns name of the job to run on the grid
   * 
   * @return name of the job
   */
  public String getJobName() {
    return this.jobName.getText();
  }

  /**
   * Returns description of the job to run on the grid
   * 
   * @return description of the job
   */
  public String getJobDescription() {
    return this.jobDescription.getText();
  }

  /**
   * Returns application name to be run on the grid
   * 
   * @return name of the application
   */
  String getApplicationName() {
    return this.applicationName.getText();
  }

  // @Override
  // public IWizardPage getNextPage()
  // {
  // IWizardPage result = super.getNextPage();
  // if (! appsWithExtraAttributes.containsValue( application.getText())){
  // ApplicationSpecificPage appSP =
  // ApplicationSpecificPageFactory.getApplicationSpecificPage( "aaa",
  // this.parentP );
  // result.setPreviousPage( appSP );
  // result = appSP;
  // //tworzenie ApplicationSpecificPage()
  // result.setPreviousPage( this );
  //      
  // // result = ApplciationSpecificPage
  // }
  // return result;
  // }
  public void modifyText( final ModifyEvent e ) {
    this.getContainer().updateButtons();
    if( this.nodeInternal == null ) {
      this.nodeInternal = new InternalWizard( this.internalPages, this.getWizard() );
    }
    String newValue = null;
    if( this.appsWithExtraAttributes.containsValue( this.applicationName.getText() ) )
    {
      // for testing of DOM
      for( String el : this.appsWithExtraAttributes.keySet() ) {
        if( this.appsWithExtraAttributes.get( el )
          .equals( this.applicationName.getText() ) )
        {
          newValue = el;
          // ApplicationSpecificControlsFactory factory = new
          // ApplicationSpecificControlsFactory( Extensions.getXMLPath( el
          // ).toString() );
          // factory.parse();
          this.executableFile.setText( Extensions.getJSDLExtensionExecutable( el ) );
        }
      }
      
      // end testing of DOM
      // newValue = this.application.getText();
    } else {
      this.executableFile.setText( "" );
    }
    this.nodeInternal.changeFirstPage( newValue );
    this.setSelectedNode( this.nodeInternal );
    // this.getContainer().updateButtons();
    // if( this.appsWithExtraAttributes.containsValue(
    // this.application.getText() ) )
    // {
    // if( this.node == null ) {
    // this.node = new InternalWizard( this.internalPages, this.getWizard() );
    // }
    // }
    // this.node.changeFirstPage( this.application.getText() );
    // this.setSelectedNode( this.node );
  }

  /**
   * Method to access application specific page
   * 
   * @return instance of {@link ApplicationSpecificPage} or null if this page
   *         wasn't used by wizard
   */
  public ApplicationSpecificPage getApplicationSpecificPage() {
    ApplicationSpecificPage result = null;
    IWizardNode n = this.getSelectedNode();
    if( n instanceof InternalWizard ) {
      IWizardPage page = ( ( InternalWizard )n ).getInternalStartingPage();
      if( page instanceof ApplicationSpecificPage ) {
        result = ( ApplicationSpecificPage )page;
      }
    }
    return result;
  }

  @Override
  protected void setSelectedNode( final IWizardNode node )
  {
    super.setSelectedNode( node );
  }
  class InternalWizard extends Wizard implements IWizardNode {

    boolean isCreated = false;
    private ArrayList<WizardPage> internalPagesI;
    private IWizard parentWizard;
    private String extensionPointId;
    private ApplicationSpecificPage asp;
    // private boolean firstTime = true;
    private IWizardPage startingPage;
    

    /**
     * Creates instance of {@link InternalWizard}
     * 
     * @param internalPages pages to display after optional firts
     *          {@link ApplicationSpecificPage}
     * @param parentWizard parent Wizard containing this IWizardNode
     */
    public InternalWizard( final ArrayList<WizardPage> internalPages,
                           final IWizard parentWizard )
    {
      super();
      this.internalPagesI = internalPages;
      this.parentWizard = parentWizard;
      this.addPages();
      this.isCreated = true;
      
      
    }

    @Override
    public boolean performFinish()
    {
      return this.parentWizard.performFinish();
    }

    @Override
    public void addPages()
    {
      this.asp = new ApplicationSpecificPage( "asp" );
      // asp.createControl( ExecutableNewJobWizardPage.this.parentP );
      // asp.setText("cos");
      this.addPage( this.asp );
      // asp.setExtensionPointId( this.extensionPointId );
      for( WizardPage page : this.internalPagesI ) {
        this.addPage( page );
      }
      this.isCreated = true;
      
    }

    public Point getExtent() {
      // TODO Auto-generated method stub
      return null;
    }

    public IWizard getWizard() {
      return this;
    }

    public boolean isContentCreated() {
      return this.isCreated;
    }

    /**
     * Method to change first page of this wizard
     * 
     * @param id id of extension point providing description of first page or
     *          null
     */
    public void changeFirstPage( final String id ) {
      if( id != null ) {
        if( this.extensionPointId != null ) {
          if( !this.extensionPointId.equals( id ) ) {
            this.extensionPointId = id;
          }
        } else {
          this.extensionPointId = id;
        }
        this.asp.setExtensionPointId( this.extensionPointId );
//        this.asp.setRebuild( true );
      } else {
        this.extensionPointId = null;
        this.asp.setExtensionPointId( this.extensionPointId );
      }
      // this.firstTime = true;
    }

    // @Override
    // public IWizardPage getNextPage( final IWizardPage page )
    // {
    // IWizardPage result = super.getNextPage( page );
    // if ( this.extensionPointId == null && this.firstTime ){
    // result = this.getStartingPage();
    // }
    // return result;
    // }
    //
    // @Override
    // public IWizardPage getStartingPage()
    // {
    // IWizardPage result = super.getStartingPage();
    // if ( this.extensionPointId == null && this.firstTime ){
    // result = this.internalPages.get( 0 );
    // this.firstTime = false;
    // }
    // this.startingPage = result;
    // return result;
    // }
    @Override
    public IWizardPage getStartingPage()
    {
      IWizardPage result = super.getStartingPage();
      if( this.extensionPointId == null ) {
        result = this.internalPagesI.get( 0 );
      }
      this.startingPage = result;
      return result;
    }

    @Override
    public IWizardPage getNextPage( final IWizardPage page )
    {
      // TODO Auto-generated method stub
      return super.getNextPage( page );
    }

    /**
     * Method to access first page of this wizard
     * 
     * @return forst page to show in this wizard
     */
    public IWizardPage getInternalStartingPage() {
      this.getStartingPage();
      return this.startingPage;
    }
  }
}
