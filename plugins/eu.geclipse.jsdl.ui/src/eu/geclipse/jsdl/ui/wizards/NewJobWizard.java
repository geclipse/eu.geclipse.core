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
 *     PSNC - Katarzyna Bylec
 *     Mathias Stuempert - Added transformation to other job descriptions
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;

import eu.geclipse.core.Extensions;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.jsdl.JSDLJobDescriptionCreator;
import eu.geclipse.jsdl.model.base.DataStagingType;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.wizards.FileType;
import eu.geclipse.jsdl.ui.preference.ApplicationSpecificRegistry;
import eu.geclipse.jsdl.ui.wizards.specific.IApplicationSpecificPage;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * Wizard for creating new JSDL file
 * 
 * @author katis
 */
public class NewJobWizard extends Wizard implements INewWizard {

  private IStructuredSelection selection;
  private FirstPage firstPage;
  private IFile file;
  private ExecutableNewJobWizardPage executablePage;
  private DataStagingNewJobWizardPage outputFilesPage;
  private JSDLJobDescription basicJSDL;
  private String appName = ""; //$NON-NLS-1$

  @Override
  public void addPages() {
    this.firstPage = new FirstPage( Messages.getString( "NewJobWizard.first_page_name" ), //$NON-NLS-1$
                                    this.selection );
    this.firstPage.setTitle( Messages.getString( "NewJobWizard.first_page_title" ) ); //$NON-NLS-1$
    this.firstPage.setDescription( Messages.getString( "NewJobWizard.first_page_description" ) ); //$NON-NLS-1$
    addPage( this.firstPage );
    ArrayList<WizardPage> internal = new ArrayList<WizardPage>();
    this.outputFilesPage = new DataStagingNewJobWizardPage( Messages.getString( "NewJobWizard.files_output_new_job_page_name" ) ); //$NON-NLS-1$;
    internal.add( this.outputFilesPage );
    this.executablePage = new ExecutableNewJobWizardPage( Messages.getString( "NewJobWizard.executablePageName" ), internal ); //$NON-NLS-1$
    addPage( this.executablePage );
  }

  public void init( final IWorkbench workbench, final IStructuredSelection sel )
  {
    setWindowTitle( Messages.getString( "NewJobWizard.windowTitle" ) ); //$NON-NLS-1$
    this.selection = sel;
  }

  @Override
  public boolean performFinish() {
    boolean result = false;
    IRunnableWithProgress op = new IRunnableWithProgress() {

      public void run( final IProgressMonitor monitor )
        throws InvocationTargetException
      {
        try {
          createFile( monitor );
        } finally {
          monitor.done();
        }
      }
    };
    try {
      getContainer().run( false, true, op );
      result = true;
      openFile();
    } catch( InterruptedException e ) {
      result = false;
    } catch( InvocationTargetException iTExc ) {
      Throwable realException = iTExc.getCause();
      Shell shell = PlatformUI.getWorkbench()
        .getActiveWorkbenchWindow()
        .getShell();
      ProblemDialog.openProblem( shell,
                                 Messages.getString( "NewJobWizard.invocation_error_title" ), //$NON-NLS-1$
                                 Messages.getString( "NewJobWizard.invocation_title_message" ), //$NON-NLS-1$
                                 realException );
      result = false;
    }
    return result;
  }

  private void openFile() {
    try {
      // if (this.file.getFileExtension().equals( "jsdl" ) ){
      //        
      // }
      if( this.file.exists() ) {
        IDE.openEditor( Activator.getDefault()
          .getWorkbench()
          .getActiveWorkbenchWindow()
          .getActivePage(), this.file, true );
      }
    } catch( PartInitException partInitException ) {
      Activator.logException( partInitException );
    }
  }

  IPath getProject() {
    return this.firstPage.getContainerFullPath();
  }

  protected void createFile( final IProgressMonitor monitor ) {
    monitor.beginTask( Messages.getString( "NewJobWizard.creating_task" ) + this.firstPage.getFileName(), 2 ); //$NON-NLS-1$
    IGridElementCreator creator = this.firstPage.getCreator();
    boolean translate = ( creator != null )
                        && !( creator instanceof JSDLJobDescriptionCreator );
    if( translate ) {
      String fileName = this.firstPage.getFileName();
      IPath path = new Path( fileName );
      fileName = path.removeFileExtension()
        .addFileExtension( "jsdl" ) //$NON-NLS-1$
        .lastSegment();
      this.firstPage.setFileName( fileName );
    }
    this.file = this.firstPage.createNewFile();
    JSDLJobDescription jsdlJobDescription = null;
    IGridElement element = GridModel.getRoot().findElement( this.file );
    if( element instanceof JSDLJobDescription ) {
      jsdlJobDescription = ( JSDLJobDescription )element;
    }
    monitor.worked( 1 );
    monitor.setTaskName( Messages.getString( "NewJobWizard.setting_contents_task" ) + this.firstPage.getFileName() ); //$NON-NLS-1$
    // this.file.setContents( getInitialStream(), true, true, monitor );
    if( jsdlJobDescription != null ) {
      setInitialModel( jsdlJobDescription );
      jsdlJobDescription.save( this.file );
    }
    if( translate && creator.canCreate( jsdlJobDescription ) ) {
      try {
        IGridElement newElement = creator.create( jsdlJobDescription.getParent() );
        this.file = ( IFile )newElement.getResource();
      } catch( GridModelException gmExc ) {
        ProblemDialog.openProblem( getShell(),
                                   Messages.getString("NewJobWizard.CreationFailed"), //$NON-NLS-1$
                                   Messages.getString("NewJobWizard.ErrorCreatingJobDescription"), //$NON-NLS-1$
                                   gmExc );
      } finally {
        try {
          jsdlJobDescription.getResource().delete( true, null );
        } catch( CoreException cExc ) {
          ProblemDialog.openProblem( getShell(),
                                     Messages.getString("NewJobWizard.DeletionFailed"), //$NON-NLS-1$
                                     Messages.getString("NewJobWizard.UnableToDelete"), //$NON-NLS-1$
                                     cExc );
        }
      }
    }
    monitor.worked( 1 );
  }

  
  void setInitialModel( final JSDLJobDescription jsdl ) {
    this.executablePage.getApplicationSpecificPage();
    if( getContainer().getCurrentPage() != this.outputFilesPage ) {
      JSDLJobDescription tempJSDL = this.executablePage.getBasicJSDL();
      String tempAppName = this.executablePage.getApplicationName();
      if( !tempAppName.equals( this.appName ) ) {
        // this.basicJSDL = this.executablePage.getBasicJSDL();
        updateBasicJSDL( tempJSDL, tempAppName );
        if( this.basicJSDL != null ) {
          jsdl.setRoot( this.basicJSDL.getRoot() );
        } else {
          jsdl.createRoot();
          jsdl.addJobDescription();
        }
      } else {
        if( this.basicJSDL != null ) {
          this.basicJSDL.removeDataStaging();
          jsdl.setRoot( this.basicJSDL.getRoot() );
        } else {
          jsdl.createRoot();
          jsdl.addJobDescription();
        }
      }
    } else {
      if( this.basicJSDL != null ) {
        this.basicJSDL.removeDataStaging();
        jsdl.setRoot( this.basicJSDL.getRoot() );
      } else {
        jsdl.createRoot();
        jsdl.addJobDescription();
      }
    }
    String applicationName = this.executablePage.getApplicationName();
    if( applicationName.equals( "" ) ) { //$NON-NLS-1$
      applicationName = this.file.getName();
      applicationName = applicationName.substring( 0,
                                                   applicationName.indexOf( "." ) ); //$NON-NLS-1$
    }
    jsdl.addJobIdentification( applicationName, null );
    String appName1 = ""; //$NON-NLS-1$
    if( this.executablePage.getApplicationName().equals( "" ) ) { //$NON-NLS-1$
      appName1 = applicationName;
    } else {
      appName1 = this.executablePage.getApplicationName();
    }
    jsdl.addApplication();
    String in = null;
    String out = null;
    String err = null;
    String inName = null;
    String outName = null;
    String errName = null;
    in = this.executablePage.getStdin();
    out = this.executablePage.getStdout();
    err = this.executablePage.getStderr();
    if( in.equals( "" ) ) { //$NON-NLS-1$
      in = null;
    } else {
      if( in.lastIndexOf( "/" ) != -1 ) { //$NON-NLS-1$
        inName = in.substring( in.lastIndexOf( "/" ) + 1 ); //$NON-NLS-1$
      } else {
        inName = "stdIn"; //$NON-NLS-1$
      }
    }
    if( out.equals( "" ) ) { //$NON-NLS-1$
      out = null;
    } else {
      if( out.lastIndexOf( "/" ) != -1 ) { //$NON-NLS-1$
        outName = out.substring( out.lastIndexOf( "/" ) + 1 ); //$NON-NLS-1$
      } else {
        outName = "stdOut"; //$NON-NLS-1$
      }
    }
    if( err.equals( "" ) ) { //$NON-NLS-1$
      err = null;
    } else {
      if( err.lastIndexOf( "/" ) != -1 ) { //$NON-NLS-1$
        errName = err.substring( err.lastIndexOf( "/" ) + 1 ); //$NON-NLS-1$
      } else {
        errName = "stdErr"; //$NON-NLS-1$
      }
    }
    String execName = this.executablePage.getExecutableFile();
    jsdl.setApplicationName( appName1 );
    if( !execName.equals( "" ) ) { //$NON-NLS-1$
      try {
        URI test = new URI( execName );
        if( test.getScheme() != null ) {
          String execNameTemp = test.toString()
            .substring( test.toString().lastIndexOf( "/" ) + 1, //$NON-NLS-1$
                        test.toString().length() );
          jsdl.setInDataStaging( execNameTemp, execName );
          execName = execNameTemp;
        }
      } catch( URISyntaxException e ) {
        // TODO katis what to do with this exception?
      }
      jsdl.addPOSIXApplicationDetails( appName1,
                                       execName,
                                       in,
                                       inName,
                                       out,
                                       outName,
                                       err,
                                       errName );
    }
    if( this.outputFilesPage.isCreated() ) {
      List<DataStagingType> outFiles;
      outFiles = this.outputFilesPage.getFiles( FileType.OUTPUT );
      if( !outFiles.isEmpty() ) {
        for( DataStagingType data : outFiles ) {
          jsdl.addDataStagingType( data );
        }
      }
      // HashMap<String, String> outFiles = this.outputFilesPage.getFiles(
      // FileType.OUTPUT );
      // if( !outFiles.isEmpty() ) {
      // for( String name : outFiles.keySet() ) {
      // jsdl.setOutDataStaging( name, outFiles.get( name ) );
      // }
      // }
      outFiles = this.outputFilesPage.getFiles( FileType.INPUT );
      if( !outFiles.isEmpty() ) {
        for( DataStagingType data : outFiles ) {
          jsdl.addDataStagingType( data );
        }
        // for( String name : outFiles.keySet() ) {
        // jsdl.setInDataStaging( name, outFiles.get( name ) );
        // }
      }
    }
    ArrayList<String> argList = this.executablePage.getArgumentsList();
    for( String argumentFormLine : argList ) {
      jsdl.addArgument( argumentFormLine );
    }
    List<IApplicationSpecificPage> aspList = this.executablePage.getApplicationSpecificPages();
    Map<String, ArrayList<String>> arguments;
    if( aspList != null ) {
      for( IApplicationSpecificPage asp : aspList ) {
        arguments = asp.getParametersValues();
        if( arguments != null ) {
          for( String argName : arguments.keySet() ) {
            jsdl.addArgumentForPosixApplication( argName,
                                                 arguments.get( argName ) );
          }
        }
        Map<String, Properties> stagingIn = asp.getStageInFiles();
        if( stagingIn != null ) {
          for( String argName : stagingIn.keySet() ) {
            // add agument
            Enumeration vals = stagingIn.get( argName ).propertyNames();
            ArrayList<String> values = new ArrayList<String>();
            while( vals.hasMoreElements() ) {
              values.add( ( String )vals.nextElement() );
            }
            jsdl.addArgumentForPosixApplication( argName, values );
            for( String value : values ) {
              jsdl.setInDataStaging( value, stagingIn.get( argName )
                .getProperty( value ) );
            }
          }
          Map<String, Properties> stagingOut = asp.getStageOutFiles();
          if( stagingOut != null ) {
            for( String argName : stagingOut.keySet() ) {
              // add agument
              Enumeration vals = stagingOut.get( argName ).propertyNames();
              ArrayList<String> values = new ArrayList<String>();
              while( vals.hasMoreElements() ) {
                values.add( ( String )vals.nextElement() );
              }
              jsdl.addArgumentForPosixApplication( argName, values );
              for( String value : values ) {
                jsdl.setOutDataStaging( value, stagingOut.get( argName )
                  .getProperty( value ) );
              }
            }
          }
        }
      }
    }
    // jsdl.getDataStagingIn();
    // jsdl.getLocalDataStagingIn();
    // jsdl.getStdInputDataType();
  }

  /**
   * @param newBasicJSDL
   * @param aspName name of application (its 'display name', shown on Name list
   *            in Job Wizard, see also
   *            {@link ApplicationSpecificRegistry#getApplicationDataMapping()})
   */
  void updateBasicJSDL( final JSDLJobDescription newBasicJSDL,
                        final String aspName )
  {
    boolean fromPreSet = false;
    if( this.basicJSDL != null ) {
      fromPreSet = true;
    }
    this.basicJSDL = newBasicJSDL;
    this.appName = aspName;
    if( this.basicJSDL != null ) {
      this.outputFilesPage.setInitialStagingOutModel( this.basicJSDL.getDataStagingOut() );
      this.outputFilesPage.setInitialStagingInModel( this.basicJSDL.getDataStagingIn() );
    } else {
      if( !fromPreSet ) {
        this.outputFilesPage.setInitialStagingInModel( this.outputFilesPage.getFiles( FileType.INPUT ) );
        this.outputFilesPage.setInitialStagingOutModel( this.outputFilesPage.getFiles( FileType.OUTPUT ) );
      } else {
        this.outputFilesPage.setInitialStagingOut( null );
        this.outputFilesPage.setInitialStagingIn( null );
      }
    }
  }

  @Override
  public boolean canFinish() {
    // TODO Auto-generated method stub
    return super.canFinish();
  }
  class FirstPage extends WizardNewFileCreationPage {

    private static final String JSDL_STANDARD = "JSDL - Job Submission Description Language (OGF Standard)"; //$NON-NLS-1$
    private IStructuredSelection iniSelection;
    private final String initFileName = Messages.getString( Messages.getString("NewJobWizard.DefaultFileName") ); //$NON-NLS-1$
    private Combo typeCombo;
    private Hashtable<String, IGridElementCreator> creators = new Hashtable<String, IGridElementCreator>();

    /**
     * Creates new instance of {@link FirstPage}
     * 
     * @param pageName name of this page
     * @param selection selection to be pass to new instance
     */
    public FirstPage( final String pageName,
                      final IStructuredSelection selection )
    {
      super( pageName, selection );
      this.iniSelection = selection;
    }

    @Override
    public void createControl( final Composite parent ) {
      Composite mainComp = new Composite( parent, SWT.NONE );
      mainComp.setLayout( new GridLayout( 1, false ) );
      Label typeLabel = new Label( mainComp, SWT.NONE );
      typeLabel.setText( Messages.getString("NewJobWizard.JobDescriptionType") ); //$NON-NLS-1$
      typeLabel.setLayoutData( new GridData( SWT.BEGINNING,
                                             SWT.CENTER,
                                             false,
                                             false ) );
      this.typeCombo = new Combo( mainComp, SWT.READ_ONLY );
      this.typeCombo.setLayoutData( new GridData( SWT.FILL,
                                                  SWT.CENTER,
                                                  true,
                                                  false ) );
      this.typeCombo.addSelectionListener( new SelectionAdapter() {

        @Override
        public void widgetSelected( final SelectionEvent e ) {
          updateFilename();
        }
      } );
      super.createControl( mainComp );
      Control control = getControl();
      if( control instanceof Composite ) {
        Layout layout = ( ( Composite )control ).getLayout();
        if( layout instanceof GridLayout ) {
          GridLayout gLayout = ( GridLayout )layout;
          gLayout.marginHeight = 0;
          gLayout.marginWidth = 0;
        }
      }
      setControl( mainComp );
      initTypeCombo( this.typeCombo );
      setFileName( getUniqueFileName() );
    }

    public IGridElementCreator getCreator() {
      String type = this.typeCombo.getText();
      return this.creators.get( type );
    }

    public String getDescriptionSuffix() {
      String result = Messages.getString("NewJobWizard.JSDL"); //$NON-NLS-1$
      String text = this.typeCombo.getText();
      if( text != null ) {
        int index = text.trim().indexOf( " " ); //$NON-NLS-1$
        if( index != -1 ) {
          result = text.substring( 0, index ).toLowerCase();
        }
      }
      return result;
    }

    public String getUniqueFileName() {
      IPath containerFullPath = getContainerFullPath();
      String fileName = this.initFileName;
      String extension = getDescriptionSuffix();
      if( containerFullPath == null ) {
        containerFullPath = new Path( "" ); //$NON-NLS-1$
      }
      if( fileName == null || fileName.trim().length() == 0 ) {
        fileName = "default"; //$NON-NLS-1$
      }
      IPath filePath = containerFullPath.append( fileName );
      if( extension != null && !extension.equals( filePath.getFileExtension() ) )
      {
        filePath = filePath.addFileExtension( extension );
      }
      extension = filePath.getFileExtension();
      fileName = filePath.removeFileExtension().lastSegment();
      int i = 0;
      while( ResourcesPlugin.getWorkspace().getRoot().exists( filePath ) ) {
        i++;
        filePath = containerFullPath.append( fileName + i );
        if( extension != null ) {
          filePath = filePath.addFileExtension( extension );
        }
      }
      return filePath.lastSegment();
    }

    protected void updateFilename() {
      String filename = getFileName();
      String extension = getDescriptionSuffix();
      if( filename == null ) {
        filename = getUniqueFileName();
      }
      IPath path = new Path( filename );
      setFileName( path.removeFileExtension()
        .addFileExtension( extension )
        .lastSegment() );
      validatePage();
    }

    @Override
    protected boolean validatePage() {
      boolean result = true;
      if( !super.validatePage() ) {
        result = false;
      }
      String extension = getDescriptionSuffix();
      IPath path = new Path( getFileName() );
      String currentExtension = path.getFileExtension();
      if( ( currentExtension == null )
          || !currentExtension.toLowerCase().endsWith( extension ) ) { 
        setErrorMessage( String.format( Messages.getString( "NewJobWizard.wrong_file_extension_error_message" ), extension ) ); //$NON-NLS-1$
        result = false;
      }
      return result;
    }

    protected IPath getFilePath() {
      IPath path = getContainerFullPath();
      if( path == null ) {
        path = new Path( "" ); //$NON-NLS-1$
      }
      String fileName = getFileName();
      if( fileName != null ) {
        path = path.append( fileName );
      }
      return path;
    }

    @Override
    public boolean canFlipToNextPage() {
      return super.canFlipToNextPage();
    }

    @Override
    protected void initialPopulateContainerNameField() {
      {
        Object obj = this.iniSelection.getFirstElement();
        if( obj instanceof IGridContainer ) {
          IGridElement element = ( IGridElement )obj;
          IGridProject project = element.getProject();
          if( project != null ) {
            IGridElement descriptions = project.getProjectFolder( IGridJobDescription.class );
            if( descriptions != null ) {
              IPath cPath = descriptions.getPath();
              IPath ePath = element.getPath();
              if( !cPath.isPrefixOf( ePath ) ) {
                element = descriptions;
              }
            }
          }
          super.setContainerFullPath( element.getPath() );
        } else {
          super.initialPopulateContainerNameField();
        }
        // setFileName( getUniqueFileName() );
      }
    }

    private void initTypeCombo( final Combo combo ) {
      List<IConfigurationElement> elements = Extensions.getRegisteredElementCreatorConfigurations( JSDLJobDescription.class,
                                                                                                   IGridJobDescription.class );
      List<String> names = new ArrayList<String>();
      for( IConfigurationElement element : elements ) {
        try {
          IGridElementCreator creator = ( IGridElementCreator )element.createExecutableExtension( Extensions.GRID_ELEMENT_CREATOR_EXECUTABLE );
          String name = element.getAttribute( Extensions.GRID_ELEMENT_CREATOR_NAME_ATTRIBUTE );
          this.creators.put( name, creator );
          names.add( name );
        } catch( CoreException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      names.add( JSDL_STANDARD );
      Collections.sort( names );
      for( String name : names ) {
        combo.add( name );
      }
      combo.setText( JSDL_STANDARD );
    }
  }
}