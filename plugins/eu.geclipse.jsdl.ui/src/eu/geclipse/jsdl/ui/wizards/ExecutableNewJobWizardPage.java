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
package eu.geclipse.jsdl.ui.wizards;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
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

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.jsdl.ui.Extensions;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.preference.ApplicationSpecificRegistry;
import eu.geclipse.jsdl.ui.wizards.nodes.BasicWizardPart;
import eu.geclipse.jsdl.ui.wizards.nodes.SpecificWizardPart;
import eu.geclipse.jsdl.ui.wizards.specific.ApplicationSpecificPage;
import eu.geclipse.jsdl.ui.wizards.specific.IApplicationSpecificPage;
import eu.geclipse.ui.dialogs.GridFileDialog;
import eu.geclipse.ui.dialogs.NewGridFileDialog;
import eu.geclipse.ui.widgets.StoredCombo;

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
   * Button for opening {@link GridFileDialog} - a dialog for choosing local or
   * remote files
   */
  private Button gridFileDialogButton;
  /**
   * Holds name of the application
   */
  private Combo applicationName;
  private Map<String, String> appsWithExtraAttributes;
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
    this.appsWithParametersFromPrefs = ApplicationSpecificRegistry.getInstance()
      .getApplicationDataMapping();
    this.internalPages = internalPages;
  }

  @Override
  public IWizardPage getNextPage() {
    // If in application specific settings basic JSDL file is given its copy - a
    // temporary jsdl file - is created in workspace. This file is used to
    // generate JSDLJobDescription object which will be passed to next wizard's
    // page.
    Integer aspID = this.appsWithParametersFromPrefs.get( this.applicationName.getText() );
    if( aspID != null ) {
      IPath path = ApplicationSpecificRegistry.getInstance()
        .getApplicationData( aspID.intValue() )
        .getJsdlPath();
      if( path != null && !path.toOSString().equals( "" ) ) { //$NON-NLS-1$
        // getting jsdl source
        // creating temp Eclipse's resource
        // IPath workspacePath = ResourcesPlugin.getWorkspace()
        // .getRoot()
        // .getLocation();
        // workspacePath = workspacePath.append( ".tempJSDL.jsdl" );
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
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    Image fileImage = sharedImages.getImage( ISharedImages.IMG_OBJ_FILE );
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
    for( String value : this.appsWithExtraAttributes.values() ) {
      this.applicationName.add( value );
    }
    for( String value : this.appsWithParametersFromPrefs.keySet() ) {
      this.applicationName.add( value );
    }
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
    this.gridFileDialogButton.setImage( fileImage );
    layout = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_CENTER );
    this.gridFileDialogButton.setLayoutData( layout );
    this.gridFileDialogButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        int style = NewGridFileDialog.STYLE_ALLOW_ONLY_FILES | NewGridFileDialog.STYLE_MULTI_SELECTION;
        NewGridFileDialog dialog = new NewGridFileDialog( getShell(), style );
        dialog.addFileTypeFilter( "txt", "Text files (*.txt)" );
        dialog.addFileTypeFilter( "exe", "Windows executables (*.exe)" );
        if ( dialog.open() == dialog.OK ) {
          URI[] result = dialog.getSelectedURIs();
          if ( result != null ) {
            ExecutableNewJobWizardPage.this.executableFile.setText( result[ 0 ].toString() );
          }
        }
        /*IGridConnectionElement connection = GridFileDialog.openFileDialog( getShell(),
                                                                           Messages.getString( "ExecutableNewJobWizardPage.grid_file_dialog_title" ), //$NON-NLS-1$
                                                                           null,
                                                                           true );
        if( connection != null ) {
          String filename = getSelectedElementDisplayName( connection );
          if( filename != null ) {
            ExecutableNewJobWizardPage.this.executableFile.setText( filename );
          }
        }*/
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
    this.chooseButton.setImage( fileImage );
    layout = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_CENTER );
    this.chooseButton.setLayoutData( layout );
    this.chooseButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        IGridConnectionElement connection = GridFileDialog.openFileDialog( getShell(),
                                                                           Messages.getString( "ExecutableNewJobWizardPage.grid_file_dialog_title" ), //$NON-NLS-1$
                                                                           null,
                                                                           true );
        if( connection != null ) {
          String filename = getSelectedElementDisplayName( connection );
          if( filename != null ) {
            ExecutableNewJobWizardPage.this.stdin.setText( filename );
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
    this.outButton.setImage( fileImage );
    layout = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_CENTER );
    this.outButton.setLayoutData( layout );
    this.outButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        IGridConnectionElement connection = GridFileDialog.openFileDialog( getShell(),
                                                                           Messages.getString( "ExecutableNewJobWizardPage.grid_file_dialog_title" ), //$NON-NLS-1$
                                                                           null,
                                                                           true );
        if( connection != null ) {
          String filename = getSelectedElementDisplayName( connection );
          if( filename != null ) {
            ExecutableNewJobWizardPage.this.stdout.setText( filename );
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
    this.errButton.setImage( fileImage );
    layout = new GridData( GridData.HORIZONTAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_FILL
                           | GridData.VERTICAL_ALIGN_CENTER );
    this.errButton.setLayoutData( layout );
    this.errButton.addSelectionListener( new SelectionAdapter() {

      @Override
      public void widgetSelected( final SelectionEvent e ) {
        IGridConnectionElement connection = GridFileDialog.openFileDialog( getShell(),
                                                                           Messages.getString( "ExecutableNewJobWizardPage.grid_file_dialog_title" ), //$NON-NLS-1$
                                                                           null,
                                                                           true );
        if( connection != null ) {
          String filename = getSelectedElementDisplayName( connection );
          if( filename != null ) {
            ExecutableNewJobWizardPage.this.stderr.setText( filename );
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
      result = ApplicationSpecificRegistry.getInstance()
        .getApplicationData( ( this.appsWithParametersFromPrefs.get( result ) ).intValue() )
        .getAppName();
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
      IPath pathA = ApplicationSpecificRegistry.getInstance()
        .getApplicationData( appId )
        .getXmlPath();
      Path path = new Path( pathA.toFile().getPath() );
      try {
        setSelectedNode( new SpecificWizardPart( this.basicNode,
        // this.getWizard(),
                                                 path ) );
        this.executableFile.setText( ApplicationSpecificRegistry.getInstance()
          .getApplicationData( appId )
          .getAppPath() );
      } catch( SAXException e1 ) {
        // TODO katis what to do with this exception
      } catch( ParserConfigurationException e1 ) {
        // TODO katis what to do with this exception
      } catch( IOException e1 ) {
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
      IPath path = ApplicationSpecificRegistry.getInstance()
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
            // ((NewJobWizard)this.getWizard()).updateBasicJSDL(this.basicJSDL);
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
}
