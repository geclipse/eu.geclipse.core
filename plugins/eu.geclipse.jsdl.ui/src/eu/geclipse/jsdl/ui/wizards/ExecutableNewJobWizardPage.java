/******************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse consortium 
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
package eu.geclipse.jsdl.ui.wizards;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.xml.sax.SAXException;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.preference.ApplicationParametersRegistry;
import eu.geclipse.jsdl.ui.wizards.nodes.BasicWizardPart;
import eu.geclipse.jsdl.ui.wizards.nodes.SpecificWizardPart;
import eu.geclipse.jsdl.ui.wizards.specific.ApplicationSpecificPage;
import eu.geclipse.jsdl.ui.wizards.specific.IApplicationSpecificPage;
import eu.geclipse.ui.dialogs.GridFileDialog;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.widgets.StoredCombo;
import eu.geclipse.ui.wizards.IVOSelectionProvider;

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
  boolean done;
  boolean firstTime = true;
  Text stdin;
  Text stdout;
  Text stderr;
  /**
   * Holds the name of the executable
   */
  StoredCombo executableFile;
  Composite parentP;
  /**
   * Button for opening {@link GridFileDialog} - a dialog for choosing local or
   * remote files
   */
  private Button gridFileDialogButton;
  /**
   * Holds name of the application
   */
  private Combo applicationName;
  private ArrayList<WizardPage> internalPages;
  private BasicWizardPart basicNode;
  private Button chooseButton;
  private Text argumentsLine;
  private Map<String, Integer> appsWithParametersFromPrefs;
  /**
   * Object representing basic JSDL content for an application specific
   * settings. It is parsed (created) when the "Finish" button is pressed - not
   * every time the application chosen by user changes. This is kind of lazy
   * loading. This object is passed to {@link DataStagingNewJobWizardPage} so it
   * can present data staging information form basic JSDL file to the user.
   */
  private JSDLJobDescription basicJSDL;
  private Group stdFilesGroup;
  private Button outButton;
  private Button errButton;
  private IVirtualOrganization virtualOrg;

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
    this.internalPages = internalPages;
    setMessage( Messages.getString("ExecutableNewJobWizardPage.fetching_apps"), //$NON-NLS-1$
                IMessageProvider.WARNING );
  }

  @Override
  public IWizardPage getNextPage() {
    // If in application specific settings basic JSDL file is given its copy - a
    // temporary jsdl file - is created in workspace. This file is used to
    // generate JSDLJobDescription object which will be passed to next wizard's
    // page.
    Integer aspID = this.appsWithParametersFromPrefs.get( this.applicationName.getText() );
    if( aspID != null ) {
      IPath path = ApplicationParametersRegistry.getInstance()
        .getApplicationData( aspID.intValue() )
        .getJsdlPath();
      if( path != null && !path.toOSString().equals( "" ) ) { //$NON-NLS-1$
        // getting jsdl source
        // creating temp Eclipse's resource
        IPath workspacePath = ( ( NewJobWizard )this.getWizard() ).getProject();
        workspacePath = workspacePath.append( ".tempJSDL.jsdl" ); //$NON-NLS-1$
        IFile newFileHandle = ResourcesPlugin.getWorkspace()
          .getRoot()
          .getFile( workspacePath );
        try {
          newFileHandle.createLink( path, IResource.REPLACE, null );
          IGridElement element = GridModel.getRoot()
            .findElement( newFileHandle );
          if( element instanceof JSDLJobDescription ) {
            this.basicJSDL = ( JSDLJobDescription )element;
            ( ( NewJobWizard )this.getWizard() ).updateBasicJSDL( this.basicJSDL,
                                                                  this.applicationName.getText() );
          }
        } catch( CoreException e ) {
          // TODO katis - error handling
        } finally {
          try {
            newFileHandle.delete( true, null );
          } catch( CoreException e ) {
            // TODO katis - error handling
          }
        }
      } else {
        ( ( NewJobWizard )this.getWizard() ).updateBasicJSDL( null,
                                                              this.applicationName.getText() );
      }
    } else {
      ( ( NewJobWizard )this.getWizard() ).updateBasicJSDL( null,
                                                            this.applicationName.getText() );
    }
    return super.getNextPage();
  }

  @Override
  public boolean isPageComplete() {
    return true;
  }

  public void createControl( final Composite parent ) {
    this.parentP = parent;
    // mainComposite
    Composite mainComp = new Composite( parent, SWT.NONE );
    IPreferenceStore prefs = Activator.getDefault().getPreferenceStore();
    URL openFileIcon = Activator.getDefault()
      .getBundle()
      .getEntry( "icons/obj16/open_file.gif" ); //$NON-NLS-1$
    Image openFileImage = ImageDescriptor.createFromURL( openFileIcon )
      .createImage();
    GridLayout gLayout = new GridLayout( 3, false );
    gLayout.horizontalSpacing = 10;
    gLayout.verticalSpacing = 12;
    mainComp.setLayout( gLayout );
    // Label for application name
    GridData layout = new GridData();
    Label applicationNameLabel = new Label( mainComp,
                                            GridData.HORIZONTAL_ALIGN_BEGINNING
                                                | GridData.VERTICAL_ALIGN_CENTER );
    applicationNameLabel.setText( Messages.getString( "ExecutableNewJobWizardPage.application_name_label" ) ); //$NON-NLS-1$
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    applicationNameLabel.setLayoutData( layout );
    // Combo - application name
    this.applicationName = new Combo( mainComp, SWT.SINGLE );
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    layout.horizontalSpan = 2;
    this.applicationName.setLayoutData( layout );
    this.applicationName.addModifyListener( this );
    // Label - executable file
    Label inputLabel = new Label( mainComp, GridData.HORIZONTAL_ALIGN_BEGINNING
                                            | GridData.VERTICAL_ALIGN_CENTER );
    inputLabel.setText( Messages.getString( "ExecutableNewJobWizardPage.exe_input_label" ) ); //$NON-NLS-1$
    layout = new GridData();
    layout.horizontalAlignment = GridData.FILL;
    inputLabel.setLayoutData( layout );
    // Stored combo - executable file
    this.executableFile = new StoredCombo( mainComp, SWT.DROP_DOWN );
    this.executableFile.setPreferences( prefs, INPUT_EXE_ID );
    this.executableFile.setText( "" ); //$NON-NLS-1$
    layout = new GridData( GridData.FILL_HORIZONTAL
                           | GridData.GRAB_HORIZONTAL
                           | GridData.VERTICAL_ALIGN_CENTER );
    this.executableFile.setLayoutData( layout );
    this.executableFile.addModifyListener( new ModifyListener() {

      public void modifyText( final ModifyEvent event ) {
        if( ExecutableNewJobWizardPage.this.executableFile.getText()
          .equals( "" ) ) { //$NON-NLS-1$
          setStdFilesGroupEnabled( false );
        } else {
          setStdFilesGroupEnabled( true );
        }
      }
    } );
    // Button - browsing for executable file
    this.gridFileDialogButton = new Button( mainComp, SWT.PUSH );
    this.gridFileDialogButton.setImage( openFileImage );
    layout = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_CENTER );
    this.gridFileDialogButton.setLayoutData( layout );
    this.gridFileDialogButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        GridFileDialog dialog = new GridFileDialog( getShell(),
                                                    GridFileDialog.STYLE_ALLOW_ONLY_EXISTING
                                                        | GridFileDialog.STYLE_ALLOW_ONLY_FILES );
        if( dialog.open() == Window.OK ) {
          URI[] uris = dialog.getSelectedURIs();
          if( ( uris != null ) && ( uris.length > 0 ) ) {
            ExecutableNewJobWizardPage.this.executableFile.setText( uris[ 0 ].toString() );
          } else {
            ExecutableNewJobWizardPage.this.executableFile.setText( "" ); //$NON-NLS-1$
          }
        }
      }
    } );
    // Label - arguments list
    Label argumentsLabel = new Label( mainComp, SWT.LEAD );
    argumentsLabel.setText( Messages.getString( "ExecutableNewJobWizardPage.arguments_line_label" ) ); //$NON-NLS-1$
    layout = new GridData();
    argumentsLabel.setLayoutData( layout );
    // Text - arguments list
    this.argumentsLine = new Text( mainComp, SWT.BORDER );
    layout = new GridData( GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL );
    layout.horizontalSpan = 2;
    this.argumentsLine.setLayoutData( layout );
    // Group - std files group
    this.stdFilesGroup = new Group( mainComp, SWT.NONE );
    this.stdFilesGroup.setText( Messages.getString( "ExecutableNewJobWizardPage.composite_group_title" ) ); //$NON-NLS-1$
    this.stdFilesGroup.setLayout( new GridLayout( 3, false ) );
    layout = new GridData( GridData.FILL_HORIZONTAL );
    layout.grabExcessHorizontalSpace = true;
    layout.horizontalSpan = 3;
    this.stdFilesGroup.setLayoutData( layout );
    // Label - stdin file
    Label stdinLabel = new Label( this.stdFilesGroup, SWT.LEAD );
    layout = new GridData( GridData.VERTICAL_ALIGN_CENTER
                           | GridData.HORIZONTAL_ALIGN_BEGINNING );
    stdinLabel.setLayoutData( layout );
    stdinLabel.setText( Messages.getString( "FilesInputNewJobWizardPage.stdin_label" ) ); //$NON-NLS-1$
    // Text - stdin file
    this.stdin = new Text( this.stdFilesGroup, SWT.NONE | SWT.BORDER );
    layout = new GridData( GridData.FILL_HORIZONTAL
                           | GridData.GRAB_HORIZONTAL
                           | GridData.VERTICAL_ALIGN_CENTER
                           | GridData.HORIZONTAL_ALIGN_CENTER );
    this.stdin.setLayoutData( layout );
    // Button - browsing for stdin file
    this.chooseButton = new Button( this.stdFilesGroup, SWT.PUSH );
    this.chooseButton.setImage( openFileImage );
    layout = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_CENTER );
    this.chooseButton.setLayoutData( layout );
    this.chooseButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        GridFileDialog dialog = new GridFileDialog( getShell(),
                                                    GridFileDialog.STYLE_ALLOW_ONLY_FILES
                                                        | GridFileDialog.STYLE_ALLOW_ONLY_EXISTING );
        if( dialog.open() == Window.OK ) {
          URI[] uris = dialog.getSelectedURIs();
          if( ( uris != null ) && ( uris.length > 0 ) ) {
            ExecutableNewJobWizardPage.this.stdin.setText( uris[ 0 ].toString() );
          }
        }
      }
    } );
    // Label - stdout file
    Label stdoutLabel = new Label( this.stdFilesGroup, SWT.NONE );
    layout = new GridData( GridData.VERTICAL_ALIGN_CENTER
                           | GridData.HORIZONTAL_ALIGN_BEGINNING );
    stdoutLabel.setLayoutData( layout );
    stdoutLabel.setText( Messages.getString( "FilesInputNewJobWizardPage.stdout_label" ) ); //$NON-NLS-1$
    // Text - stdout file
    this.stdout = new Text( this.stdFilesGroup, SWT.NONE | SWT.BORDER );
    layout = new GridData( GridData.FILL_HORIZONTAL
                           | GridData.GRAB_HORIZONTAL
                           | GridData.VERTICAL_ALIGN_CENTER
                           | GridData.HORIZONTAL_ALIGN_CENTER );
    this.stdout.setLayoutData( layout );
    // Button - browsing for stdout files (only remote)
    this.outButton = new Button( this.stdFilesGroup, SWT.PUSH );
    this.outButton.setImage( openFileImage );
    layout = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_CENTER );
    this.outButton.setLayoutData( layout );
    this.outButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        GridFileDialog dialog = new GridFileDialog( getShell(),
                                                    GridFileDialog.STYLE_ALLOW_ONLY_FILES );
        if( dialog.open() == Window.OK ) {
          URI[] uris = dialog.getSelectedURIs();
          if( ( uris != null ) && ( ( uris.length > 0 ) ) ) {
            ExecutableNewJobWizardPage.this.stdout.setText( uris[ 0 ].toString() );
          }
        }
      }
    } );
    // Label - stderr file
    Label stderrLabel = new Label( this.stdFilesGroup, SWT.NONE );
    layout = new GridData( GridData.VERTICAL_ALIGN_CENTER
                           | GridData.HORIZONTAL_ALIGN_BEGINNING );
    stderrLabel.setLayoutData( layout );
    stderrLabel.setText( Messages.getString( "FilesInputNewJobWizardPage.stderr_label" ) ); //$NON-NLS-1$
    // Text - stderr file (only remote)
    this.stderr = new Text( this.stdFilesGroup, SWT.NONE | SWT.BORDER );
    layout = new GridData( GridData.VERTICAL_ALIGN_CENTER
                           | GridData.HORIZONTAL_ALIGN_CENTER
                           | GridData.GRAB_HORIZONTAL
                           | GridData.FILL_HORIZONTAL );
    this.stderr.setLayoutData( layout );
    // Button - browsing for stderr file (only remote)
    this.errButton = new Button( this.stdFilesGroup, SWT.PUSH );
    this.errButton.setImage( openFileImage );
    layout = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_CENTER );
    this.errButton.setLayoutData( layout );
    this.errButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        GridFileDialog dialog = new GridFileDialog( getShell(),
                                                    GridFileDialog.STYLE_ALLOW_ONLY_FILES );
        if( dialog.open() == Window.OK ) {
          URI[] uris = dialog.getSelectedURIs();
          if( ( uris != null ) && ( uris.length > 0 ) ) {
            ExecutableNewJobWizardPage.this.stderr.setText( uris[ 0 ].toString() );
          }
        }
      }
    } );
    if( this.basicNode == null ) {
      this.basicNode = new BasicWizardPart( this.internalPages,
                                            this.getWizard() );
    }
    setSelectedNode( this.basicNode );
    setStdFilesGroupEnabled( false );
    setControl( mainComp );
  }

  void setStdFilesGroupEnabled( final boolean enabled ) {
    this.stdin.setEnabled( enabled );
    this.chooseButton.setEnabled( enabled );
    this.stdout.setEnabled( enabled );
    this.outButton.setEnabled( enabled );
    this.stderr.setEnabled( enabled );
    this.errButton.setEnabled( enabled );
  }

  String getSelectedElementDisplayName( final IGridConnectionElement element ) {
    String result = ""; //$NON-NLS-1$
    result = element.getURI().toString();
    try {
      if( element.getConnectionFileStore()
        .getFileSystem()
        .getScheme()
        .equalsIgnoreCase( "file" ) ) //$NON-NLS-1$
      {
        result = "file://" + result; //$NON-NLS-1$
      }
    } catch( CoreException coreExc ) {
      Activator.logException( coreExc );
    }
    return result;
  }

  /**
   * Returns name of executable to run on grid
   * 
   * @return name of executable
   */
  public String getExecutableFile() {
    String result = this.executableFile.getText();
    return result;
  }

  /**
   * Returns application name to be run on the grid
   * 
   * @return name of the application
   */
  String getApplicationName() {
    String result = this.applicationName.getText();
    if( this.appsWithParametersFromPrefs.keySet().contains( result ) ) {
      result = ApplicationParametersRegistry.getInstance()
        .getApplicationData( ( this.appsWithParametersFromPrefs.get( result ) ).intValue() )
        .getApplicationName();
    }
    return result;
  }

  public void modifyText( final ModifyEvent e ) {
    this.getContainer().updateButtons();
    if( this.basicNode == null ) {
      this.basicNode = new BasicWizardPart( this.internalPages,
                                            this.getWizard() );
    }
    if( this.appsWithParametersFromPrefs.keySet()
      .contains( this.applicationName.getText() ) )
    {
      int appId = this.appsWithParametersFromPrefs.get( this.applicationName.getText() )
        .intValue();
      IPath pathA = ApplicationParametersRegistry.getInstance()
        .getApplicationData( appId )
        .getXmlPath();
      Path path = new Path( pathA.toFile().getPath() );
      try {
        setSelectedNode( new SpecificWizardPart( this.basicNode, path ) );
        this.executableFile.setText( ApplicationParametersRegistry.getInstance()
          .getApplicationData( appId )
          .getApplicationPath() );
      } catch( SAXException e1 ) {
        // TODO katis what to do with this exception
      } catch( ParserConfigurationException e1 ) {
        // TODO katis what to do with this exception
      } catch( IOException e1 ) {
        // empty
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
  protected void setSelectedNode( final IWizardNode node ) {
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

  /**
   * Method to access job's arguments given by user.
   * 
   * @return list of arguments
   */
  public ArrayList<String> getArgumentsList() {
    ArrayList<String> result = new ArrayList<String>();
    String[] table = this.argumentsLine.getText().split( " " ); //$NON-NLS-1$
    for( String tableElement : table ) {
      result.add( tableElement );
    }
    return result;
  }

  /**
   * Method to access basic JSDL file used by application specific settings.
   * 
   * @return object representing information kept in basic JSDL file or
   *         <code>null</code> if no such file is present.
   */
  public JSDLJobDescription getBasicJSDL() {
    // If in application specific settings basic JSDL file is given its copy - a
    // temporary jsdl file - is created in workspace. This file is used to
    // generate JSDLJobDescription object which will be passed to next wizard's
    // page.
    Integer aspID = this.appsWithParametersFromPrefs.get( this.applicationName.getText() );
    if( aspID != null ) {
      IPath path = ApplicationParametersRegistry.getInstance()
        .getApplicationData( aspID.intValue() )
        .getJsdlPath();
      if( path != null && !path.toOSString().equals( "" ) ) { //$NON-NLS-1$
        IPath workspacePath = ( ( NewJobWizard )this.getWizard() ).getProject();
        workspacePath = workspacePath.append( ".tempJSDL.jsdl" ); //$NON-NLS-1$
        IFile newFileHandle = ResourcesPlugin.getWorkspace()
          .getRoot()
          .getFile( workspacePath );
        try {
          newFileHandle.createLink( path, IResource.REPLACE, null );
          IGridElement element = GridModel.getRoot()
            .findElement( newFileHandle );
          if( element instanceof JSDLJobDescription ) {
            this.basicJSDL = ( JSDLJobDescription )element;
          }
        } catch( CoreException e ) {
          // TODO katis - error handling
        } finally {
          try {
            newFileHandle.delete( true, null );
          } catch( CoreException e ) {
            // TODO katis - error handling
          }
        }
      } else {
        this.basicJSDL = null;
      }
    } else {
      this.basicJSDL = null;
    }
    return this.basicJSDL;
  }

  /**
   * Updates buttons as a reaction to changes in page's fields content.
   */
  public void updateButtons() {
    this.getContainer().updateButtons();
  }
  class ModifyTextListener implements ModifyListener {

    public void modifyText( final ModifyEvent event ) {
      ExecutableNewJobWizardPage.this.updateButtons();
    }
  }

  /**
   * Method to access value of path to standard error file
   * 
   * @return string value kept in standard error text field
   */
  public String getStderr() {
    return this.stderr.getText();
  }

  @Override
  public void setVisible( final boolean visible ) {
    final IVirtualOrganization vo = ( ( IVOSelectionProvider )getWizard() ).getVirtualOrganization();
    if( this.virtualOrg == null || this.virtualOrg != vo ) {
      this.firstTime = true;
      if( this.applicationName != null ) {
        this.applicationName.removeAll();
        this.applicationName.setText( "" );
      }
    }
    this.virtualOrg = vo;
    Job job = new Job( Messages.getString("ExecutableNewJobWizardPage.fetching_apps_job_name") ) { //$NON-NLS-1$

      @Override
      protected IStatus run( final IProgressMonitor monitor ) {
        if( visible == true && ExecutableNewJobWizardPage.this.firstTime ) {
          ExecutableNewJobWizardPage.this.firstTime = false;
          try {
            ApplicationParametersRegistry.getInstance()
              .updateApplicationsParameters( vo );
          } catch( ProblemException e ) {
            ProblemDialog.openProblem( getShell(),
                                       Messages.getString("ExecutableNewJobWizardPage.error_fetching_title"), //$NON-NLS-1$
                                       Messages.getString("ExecutableNewJobWizardPage.error_fetching_message"), //$NON-NLS-1$
                                       e );
          }
          IWorkbench workbench = PlatformUI.getWorkbench();
          Display display = workbench.getDisplay();
          final Map<String, Integer> map = ApplicationParametersRegistry.getInstance()
            .getApplicationDataMapping( vo );
          display.syncExec( new Runnable() {

            public void run() {
              setApplications( map );
            }
          } );
        }
        return Status.OK_STATUS;
      }
    };
    job.setUser( false );
    if( this.firstTime ) {
      setMessage( Messages.getString("ExecutableNewJobWizardPage.fetching_apps"), //$NON-NLS-1$
                  IMessageProvider.WARNING );
    }
    job.schedule();
    super.setVisible( visible );
  }

  void setApplications( final Map<String, Integer> map ) {
    this.appsWithParametersFromPrefs = map;
    for( String name : this.appsWithParametersFromPrefs.keySet() ) {
      this.applicationName.add( name );
    }
    this.setMessage( null );
  }
}
