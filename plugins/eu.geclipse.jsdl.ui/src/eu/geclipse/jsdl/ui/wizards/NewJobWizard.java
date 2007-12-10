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
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.jsdl.model.DataStagingType;
import eu.geclipse.jsdl.ui.internal.Activator;
import eu.geclipse.jsdl.ui.internal.preference.ApplicationSpecificRegistry;
import eu.geclipse.jsdl.ui.internal.wizards.FileType;
import eu.geclipse.jsdl.ui.wizards.specific.IApplicationSpecificPage;

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
//    this.firstPage.setFileName( this.firstPage.getUniqueFileName() );
//    this.firstPage.setFileName( Messages.getString( "NewJobWizard.first_page_default_new_file_name" ) ); //$NON-NLS-1$
    addPage( this.firstPage );
//    this.firstPage.setFileName( this.firstPage.getUniqueFileName() );
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
    } catch( InvocationTargetException e ) {
      Throwable realException = e.getTargetException();
      MessageDialog.openError( getShell(),
                               Messages.getString( "NewJobWizard.error_title" ), realException.getMessage() ); //$NON-NLS-1$
      result = false;
    }
    return result;
  }

  private void openFile() {
    try {
      IDE.openEditor( Activator.getDefault()
        .getWorkbench()
        .getActiveWorkbenchWindow()
        .getActivePage(), this.file, true );
    } catch( PartInitException partInitException ) {
      Activator.logException( partInitException );
    }
  }

  IPath getProject() {
    return this.firstPage.getContainerFullPath();
  }

  protected void createFile( final IProgressMonitor monitor ) {
    monitor.beginTask( Messages.getString( "NewJobWizard.creating_task" ) + this.firstPage.getFileName(), 2 ); //$NON-NLS-1$
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
    monitor.worked( 1 );
  }

  @SuppressWarnings("unchecked")
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
    jsdl.addJobIdentification( this.executablePage.getApplicationName(), null );
    String appName1 = ""; //$NON-NLS-1$
    if( this.executablePage.getApplicationName().equals( "" ) ) { //$NON-NLS-1$
      appName1 = "example_name"; //$NON-NLS-1$
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
      inName = "stdIn"; //$NON-NLS-1$
    }
    if( out.equals( "" ) ) { //$NON-NLS-1$
      out = null;
    } else {
      outName = "stdOut"; //$NON-NLS-1$
    }
    if( err.equals( "" ) ) { //$NON-NLS-1$
      err = null;
    } else {
      errName = "stdErr"; //$NON-NLS-1$
    }
    String execName = this.executablePage.getExecutableFile();
    jsdl.setApplicationName(appName1);
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

    private IStructuredSelection iniSelection;
    
    private final String initFileName = Messages.getString( "NewJobWizard.first_page_default_new_file_name" );

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
      super.createControl( parent );
      setFileName( getUniqueFileName() );
    }



    public String getUniqueFileName() {
      IPath containerFullPath = getContainerFullPath();
      String fileName = this.initFileName;
      String extension = "jsdl";
      if( containerFullPath  == null ) {
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



    @Override
    protected boolean validatePage() {
      boolean result = true;
      if( !super.validatePage() ) {
        result = false;
      }
      if( !getFilePath().toString().endsWith( ".jsdl" ) ) { //$NON-NLS-1$
        setErrorMessage( Messages.getString( "NewJobWizard.wrong_file_extension_error_message" ) ); //$NON-NLS-1$
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
//        setFileName( getUniqueFileName() );
      }
    }
  }
}