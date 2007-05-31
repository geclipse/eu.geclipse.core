/******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse consortium 
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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.IWizardNode;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jface.wizard.WizardSelectionPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.xml.sax.SAXException;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.ui.Extensions;
import eu.geclipse.ui.dialogs.NewProblemDialog;
import eu.geclipse.ui.dialogs.gexplorer.GridFileDialog;
import eu.geclipse.ui.widgets.StoredCombo;
import eu.geclipse.ui.wizards.jobs.wizardnodes.BasicWizardPart;
import eu.geclipse.ui.wizards.jobs.wizardnodes.SpecificWizardPart;

/**
 * Wizard page that allows user to choose an executable for the grid job, name
 * of the job and its description
 */
public class ExecutableNewJobWizardPage extends WizardSelectionPage
  implements ModifyListener
{

  /**
   * Key for the executable file preference.
   */
  private static String INPUT_EXE_ID = "executable_file"; //$NON-NLS-1$
  Text stdin;
  Text stdout;
  Text stderr;
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
  // private Text jobName;
  /**
   * Holds description of the job
   */
  // private Text jobDescription;
  /**
   * Holds name of the application
   */
  private Combo applicationName;
  private Map<String, String> appsWithExtraAttributes;
  private ArrayList<WizardPage> internalPages;
  private BasicWizardPart basicNode;
  private Button chooseButton;

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
    // Label nameLabel = new Label( mainComp,
    // GridData.HORIZONTAL_ALIGN_BEGINNING
    // | GridData.VERTICAL_ALIGN_CENTER );
    // nameLabel.setText( Messages.getString(
    // "ExecutableNewJobWizardPage.job_name_label" ) ); //$NON-NLS-1$
    // layout.horizontalAlignment = GridData.FILL;
    // nameLabel.setLayoutData( layout );
    // this.jobName = new Text( mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
    // | GridData.VERTICAL_ALIGN_CENTER
    // | SWT.BORDER );
    // layout = new GridData();
    // layout.horizontalAlignment = GridData.FILL;
    // layout.horizontalSpan = 2;
    // this.jobName.setLayoutData( layout );
    Label applicationNameLabel = new Label( mainComp,
                                            GridData.HORIZONTAL_ALIGN_BEGINNING
                                                | GridData.VERTICAL_ALIGN_CENTER );
    applicationNameLabel.setText( Messages.getString( "ExecutableNewJobWizardPage.application_name_label" ) ); //$NON-NLS-1$
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    applicationNameLabel.setLayoutData( layout );
    this.applicationName = new Combo( mainComp, SWT.SINGLE );
    for( String value : this.appsWithExtraAttributes.values() ) {
      this.applicationName.add( value.toString() );
    }
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 2;
    this.applicationName.setLayoutData( layout );
    this.applicationName.addModifyListener( this );
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
    // Label descriptionLabel = new Label( mainComp,
    // GridData.HORIZONTAL_ALIGN_BEGINNING
    // | GridData.VERTICAL_ALIGN_CENTER );
    // descriptionLabel.setText( Messages.getString(
    // "ExecutableNewJobWizardPage.job_description_label" ) ); //$NON-NLS-1$
    // layout = new GridData();
    // layout.horizontalAlignment = GridData.FILL;
    // layout.horizontalSpan = 3;
    // descriptionLabel.setLayoutData( layout );
    // this.jobDescription = new Text( mainComp, SWT.MULTI
    // | SWT.BORDER
    // | SWT.WRAP
    // | SWT.V_SCROLL );
    // layout = new GridData();
    // layout.horizontalAlignment = GridData.FILL;
    // layout.verticalAlignment = GridData.FILL;
    // layout.horizontalSpan = 3;
    // layout.verticalSpan = 10;
    // layout.horizontalIndent = 25;
    // this.jobDescription.setLayoutData( layout );
    Group stdFilesGroup = new Group( mainComp, SWT.NONE );
    stdFilesGroup.setText( Messages.getString( "ExecutableNewJobWizardPage.composite_group_title" ) ); //$NON-NLS-1$
    stdFilesGroup.setLayout( new GridLayout( 3, false ) );
    layout = new GridData( GridData.FILL_HORIZONTAL );
    layout.grabExcessHorizontalSpace = true;
    layout.horizontalSpan = 3;
    stdFilesGroup.setLayoutData( layout );
    Label stdinLabel = new Label( stdFilesGroup, SWT.LEAD );
    layout = new GridData( GridData.VERTICAL_ALIGN_CENTER
                           | GridData.HORIZONTAL_ALIGN_BEGINNING );
    stdinLabel.setLayoutData( layout );
    stdinLabel.setText( Messages.getString( "FilesInputNewJobWizardPage.stdin_label" ) ); //$NON-NLS-1$
    this.stdin = new Text( stdFilesGroup, SWT.NONE | SWT.BORDER );
    layout = new GridData( GridData.FILL_HORIZONTAL
                           | GridData.GRAB_HORIZONTAL
                           | GridData.VERTICAL_ALIGN_CENTER
                           | GridData.HORIZONTAL_ALIGN_CENTER );
    this.stdin.setLayoutData( layout );
    this.stdin.setText( Messages.getString( "FilesInputNewJobWizardPage.stdin_info" ) ); //$NON-NLS-1$
    this.stdin.setEnabled( false );
    this.chooseButton = new Button( stdFilesGroup, SWT.PUSH );
    // ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    // Image fileImage = sharedImages.getImage( ISharedImages.IMG_OBJ_FILE );
    this.chooseButton.setImage( fileImage );
    layout = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_CENTER );
    this.chooseButton.setLayoutData( layout );
    Label stdoutLabel = new Label( stdFilesGroup, SWT.NONE );
    layout = new GridData( GridData.VERTICAL_ALIGN_CENTER
                           | GridData.HORIZONTAL_ALIGN_BEGINNING );
    stdoutLabel.setLayoutData( layout );
    stdoutLabel.setText( Messages.getString( "FilesInputNewJobWizardPage.stdout_label" ) ); //$NON-NLS-1$
    // this.stdOutName = new Text (mainComp, SWT.NONE | SWT.BORDER);
    // layout = new GridData( GridData.FILL_HORIZONTAL
    // | GridData.GRAB_HORIZONTAL
    // | GridData.VERTICAL_ALIGN_CENTER
    // | GridData.HORIZONTAL_ALIGN_CENTER );
    // this.stdOutName.setText( "stdOut" ); //$NON-NLS-1$
    // this.stdOutName.setLayoutData( layout );
    this.stdout = new Text( stdFilesGroup, SWT.NONE | SWT.BORDER );
    layout = new GridData( GridData.FILL_HORIZONTAL
                           | GridData.GRAB_HORIZONTAL
                           | GridData.VERTICAL_ALIGN_CENTER
                           | GridData.HORIZONTAL_ALIGN_CENTER );
    // gData.horizontalSpan = 2;
    this.stdout.setLayoutData( layout );
    this.stdout.setText( Messages.getString( "FilesInputNewJobWizardPage.stdin_info" ) ); //$NON-NLS-1$
    this.stdout.setEnabled( false );
    Button outButton = new Button( stdFilesGroup, SWT.PUSH );
    outButton.setImage( fileImage );
    layout = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_CENTER );
    outButton.setLayoutData( layout );
    Label stderrLabel = new Label( stdFilesGroup, SWT.NONE );
    layout = new GridData( GridData.VERTICAL_ALIGN_CENTER
                           | GridData.HORIZONTAL_ALIGN_BEGINNING );
    stderrLabel.setLayoutData( layout );
    stderrLabel.setText( Messages.getString( "FilesInputNewJobWizardPage.stderr_label" ) ); //$NON-NLS-1$
    // this.stdErrName = new Text (mainComp, SWT.NONE | SWT.BORDER);
    // layout = new GridData( GridData.FILL_HORIZONTAL
    // | GridData.GRAB_HORIZONTAL
    // | GridData.VERTICAL_ALIGN_CENTER
    // | GridData.HORIZONTAL_ALIGN_CENTER );
    // this.stdErrName.setText( "stdErr" ); //$NON-NLS-1$
    // this.stdErrName.setLayoutData( layout );
    this.stderr = new Text( stdFilesGroup, SWT.NONE | SWT.BORDER );
    layout = new GridData( GridData.VERTICAL_ALIGN_CENTER
                           | GridData.HORIZONTAL_ALIGN_CENTER
                           | GridData.GRAB_HORIZONTAL
                           | GridData.FILL_HORIZONTAL );
    // gData.horizontalSpan = 2;
    this.stderr.setLayoutData( layout );
    this.stderr.setText( Messages.getString( "FilesInputNewJobWizardPage.stdin_info" ) ); //$NON-NLS-1$
    this.stderr.setEnabled( false );
    Button errButton = new Button( stdFilesGroup, SWT.PUSH );
    errButton.setImage( fileImage );
    layout = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_CENTER );
    errButton.setLayoutData( layout );
    this.chooseButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e )
      {
        IGridConnectionElement connection
          = eu.geclipse.ui.dialogs.GridFileDialog.openFileDialog( getShell(), "Choose a file", null );
        if ( connection != null ) {
          try {
            String fs = connection.getConnectionFileStore().toString();
            ExecutableNewJobWizardPage.this.stdin.setText( fs );
          } catch ( CoreException cExc ) {
            NewProblemDialog.openProblem( getShell(), "error", "error", cExc );
          }
        }
        /*String filename = dialog.open();
        if( filename != null ) {
          ExecutableNewJobWizardPage.this.stdin.setText( filename );
        }*/
      }
    } );
    outButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e )
      {
        String[] filters = new String[] {
          "txt", "exe", "gif"
        };
        IGridConnectionElement connection
          = eu.geclipse.ui.dialogs.GridFileDialog.openFileDialog( getShell(), "Choose a file", filters );
        if ( connection != null ) {
          try {
            String fs = connection.getConnectionFileStore().toString();
            ExecutableNewJobWizardPage.this.stdout.setText( fs );
          } catch ( CoreException cExc ) {
            NewProblemDialog.openProblem( getShell(), "error", "error", cExc );
          }
        }
        /*GridFileDialog dialog = new GridFileDialog( getShell() );
        String filename = dialog.open();
        if( filename != null ) {
          ExecutableNewJobWizardPage.this.stdout.setText( filename );
        }*/
      }
    } );
    errButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e )
      {
        String[] filters = new String[] {
          "*", "txt", "exe", "gif"
        };
        IGridConnectionElement connection
          = eu.geclipse.ui.dialogs.GridFileDialog.openFileDialog( getShell(), "Choose a file", filters );
        if ( connection != null ) {
          try {
            String fs = connection.getConnectionFileStore().toString();
            ExecutableNewJobWizardPage.this.stderr.setText( fs );
          } catch ( CoreException cExc ) {
            NewProblemDialog.openProblem( getShell(), "error", "error", cExc );
          }
        }
        /*GridFileDialog dialog = new GridFileDialog( getShell() );
        String filename = dialog.open();
        if( filename != null ) {
          ExecutableNewJobWizardPage.this.stderr.setText( filename );
        }*/
      }
    } );
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
    if( this.basicNode == null ) {
      this.basicNode = new BasicWizardPart( this.internalPages,
                                            this.getWizard() );
    }
    setSelectedNode( this.basicNode );
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
  // public String getJobName() {
  // return this.jobName.getText();
  // }
  /**
   * Returns description of the job to run on the grid
   * 
   * @return description of the job
   */
  // public String getJobDescription() {
  // return this.jobDescription.getText();
  // }
  /**
   * Returns application name to be run on the grid
   * 
   * @return name of the application
   */
  String getApplicationName() {
    return this.applicationName.getText();
  }

  public void modifyText( final ModifyEvent e ) {
    this.getContainer().updateButtons();
    if( this.basicNode == null ) {
      this.basicNode = new BasicWizardPart( this.internalPages,
                                            this.getWizard() );
    }
    if( this.appsWithExtraAttributes.values()
      .contains( this.applicationName.getText() ) )
    {
      try {
        for( String bundleId : this.appsWithExtraAttributes.keySet() ) {
          if( this.appsWithExtraAttributes.get( bundleId )
            .equals( this.applicationName.getText() ) )
          {
            setSelectedNode( new SpecificWizardPart( this.basicNode,
                                                     this.getWizard(),
                                                     bundleId ) );
            this.executableFile.setText( Extensions.getJSDLExtensionExecutable( bundleId ) );
          }
        }
      } catch( SAXException e1 ) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      } catch( ParserConfigurationException e1 ) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      } catch( IOException e1 ) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    } else {
      setSelectedNode( this.basicNode );
    }
  }

  /**
   * Method to access application specific page
   * 
   * @return instance of {@link ApplicationSpecificPage} or null if this page
   *         wasn't used by wizard
   */
  public ApplicationSpecificPage getApplicationSpecificPage() {
    ApplicationSpecificPage result = null;
    return result;
  }

  @Override
  protected void setSelectedNode( final IWizardNode node )
  {
    super.setSelectedNode( node );
  }

  /**
   * Returns list of pages created from parsing XML with description of
   * additional pages specific to application
   * 
   * @return list of application specific pages
   */
  public List<IApplicationSpecificPage> getApplicationSpecificPages() {
    List<IApplicationSpecificPage> result = new ArrayList<IApplicationSpecificPage>();
    if( this.getSelectedNode() != null
        && this.getSelectedNode() != this.basicNode )
    {
      SpecificWizardPart specificNode = ( SpecificWizardPart )this.getSelectedNode();
      for( IWizardPage asp : specificNode.getPages() ) {
        result.add( ( IApplicationSpecificPage )asp );
      }
    }
    return result;
  }

  /**
   * Method to access value of field holding path to stdin file
   * 
   * @return String representing remote path to stdin file
   */
  public String getStdin() {
    return this.stdin.getText();
  }

  /**
   * Method to access value of a field holding path to stdout
   * 
   * @return String representing remote path to stdout file
   */
  public String getStdout() {
    return this.stdout.getText();
  }
}
