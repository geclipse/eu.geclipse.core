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
package eu.geclipse.ui.wizards.jobs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.impl.JSDLJobDescription;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.internal.wizards.jobs.FileType;

/**
 * Wizard for creating new jsdl file
 * 
 * @author katis
 */
public class NewJobWizard extends Wizard implements INewWizard {

  private IStructuredSelection selection;
  private WizardNewFileCreationPage firstPage;
  private IFile file;
  // private FilesInputNewJobWizardPage inputFilesPage;
  private ExecutableNewJobWizardPage executablePage;
  private FilesOutputNewJobWizardPage outputFilesPage;
//  private ResourcesNewJobWizardPage resourcesPage;
//  private HostsNewJobWizardPage hostsPage;

  @Override
  public void addPages()
  {
    this.firstPage = new FirstPage( Messages.getString( "NewJobWizard.first_page_name" ), //$NON-NLS-1$
                                    this.selection );
    this.firstPage.setTitle( Messages.getString( "NewJobWizard.first_page_title" ) ); //$NON-NLS-1$
    this.firstPage.setDescription( Messages.getString( "NewJobWizard.first_page_description" ) ); //$NON-NLS-1$
    this.firstPage.setFileName( Messages.getString( "NewJobWizard.first_page_default_new_file_name" ) ); //$NON-NLS-1$
    addPage( this.firstPage );
    ArrayList<WizardPage> internal = new ArrayList<WizardPage>();
    // this.inputFilesPage = new FilesInputNewJobWizardPage( Messages.getString(
    // "NewJobWizard.files_input_new_job_page_name" ) ); //$NON-NLS-1$
    // // addPage( this.inputFilesPage );
    // internal.add( this.inputFilesPage );
    this.outputFilesPage = new FilesOutputNewJobWizardPage( Messages.getString( "NewJobWizard.files_output_new_job_page_name" ) ); //$NON-NLS-1$;
    // addPage( this.outputFilesPage );
    internal.add( this.outputFilesPage );
//    this.hostsPage = new HostsNewJobWizardPage( Messages.getString( "NewJobWizard.host_page" ), JSDLJobDescription.getOSTypes(), JSDLJobDescription.getCPUArchitectures() ); //$NON-NLS-1$
//    internal.add( this.hostsPage );
//    this.resourcesPage = new ResourcesNewJobWizardPage( Messages.getString( "NewJobWizard.job_resources" ) ); //$NON-NLS-1$
//    internal.add( this.resourcesPage );
    this.executablePage = new ExecutableNewJobWizardPage( Messages.getString( "NewJobWizard.executablePageName" ), internal ); //$NON-NLS-1$
    addPage( this.executablePage );
  }

  public void init( final IWorkbench workbench, final IStructuredSelection sel )
  {
    setWindowTitle( Messages.getString( "NewJobWizard.windowTitle" ) ); //$NON-NLS-1$
    this.selection = sel;
  }
  
  
  @Override
  public boolean performFinish()
  {
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
  
  private void openFile(){
    try {
      IDE.openEditor( Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage(), this.file, true );
    } catch( PartInitException partInitException ) {
      Activator.logException( partInitException );
    }
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
  private void setInitialModel( final JSDLJobDescription jsdl )
  {
    this.executablePage.getApplicationSpecificPage();
    jsdl.createRoot();
    jsdl.addJobDescription();
    jsdl.addJobIdentification( this.executablePage.getApplicationName(), null );
    if( !this.executablePage.getApplicationName().equals( "" ) ) { //$NON-NLS-1$
      jsdl.addApplication();
      String in = null;
      String out = null;
      String inName = null;
      String outName = null;
      // if( this.inputFilesPage.isCreated() ) {
      // in = this.inputFilesPage.getStdin();
      // out = this.inputFilesPage.getStdout();
      // if( in.equals( Messages.getString(
      // "FilesInputNewJobWizardPage.stdin_info" ) ) ) { //$NON-NLS-1$
      // in = null;
      // } else {
      // inName = this.inputFilesPage.getStdinName();
      // }
      // if( out.equals( Messages.getString(
      // "FilesInputNewJobWizardPage.stdin_info" ) ) ) { //$NON-NLS-1$
      // out = null;
      // } else {
      // outName = this.inputFilesPage.getStdOutName();
      // }
      // }
      in = this.executablePage.getStdin();
      out = this.executablePage.getStdout();
      if( in.equals( Messages.getString( "FilesInputNewJobWizardPage.stdin_info" ) ) ) { //$NON-NLS-1$
        in = null;
      } else {
        inName = "stdIn"; //$NON-NLS-1$
      }
      if( out.equals( Messages.getString( "FilesInputNewJobWizardPage.stdin_info" ) ) ) { //$NON-NLS-1$
        out = null;
      } else {
        outName = "stdOut"; //$NON-NLS-1$
      }
      if( !this.executablePage.getExecutableFile().equals( "" ) ) { //$NON-NLS-1$
        jsdl.addPOSIXApplicationDetails( this.executablePage.getApplicationName(),
                                         this.executablePage.getExecutableFile(),
                                         in,
                                         inName,
                                         out,
                                         outName );
      }
    }
    if( this.outputFilesPage.isCreated() ) {
      HashMap<String, String> outFiles = this.outputFilesPage.getFiles( FileType.OUTPUT );
      if( !outFiles.isEmpty() ) {
        for( String name : outFiles.keySet() ) {
          jsdl.setOutDataStaging( name, outFiles.get( name ) );
        }
      }
      outFiles = this.outputFilesPage.getFiles( FileType.INPUT );
      if( !outFiles.isEmpty() ) {
        for( String name : outFiles.keySet() ) {
          jsdl.setInDataStaging( name, outFiles.get( name ) );
        }
      }
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
    // if (! this.resourcesPage.getCpuList().equals( "" )){
    // jsdl.setCPUArchitecture( this.resourcesPage.getCpuList());
    // }
//    if( this.hostsPage.isCreated() ) {
//      if( !this.hostsPage.getOS().equals( "" ) ) { //$NON-NLS-1$
//        jsdl.setOS( this.hostsPage.getOS() );
//      }
//      if( !this.hostsPage.getArch().equals( "" ) ) { //$NON-NLS-1$
//        jsdl.setCPUArchitecture( this.hostsPage.getArch() );
//      }
//      jsdl.addCandidateHosts( this.hostsPage.getCandidateHosts() );
//      for( Range range : this.resourcesPage.getIndividualCPUSpeedRanges() ) {
//        jsdl.setInidividialCPUSpeedRange( range.getStart(),
//                                          range.getEnd(),
//                                          true );
//      }
//    }
//    if( this.resourcesPage.isCreated() ) {
//      for( ValueWithEpsilon value : this.resourcesPage.getIndividualCPUSValues() )
//      {
//        jsdl.setIndividualCPUSpeedValue( value.getValue(), value.getEpsilon() );
//      }
//      for( Range range : this.resourcesPage.getTotalCPUCount() ) {
//        jsdl.setTotalCPUCount( range.getStart(), range.getEnd(), true );
//      }
//      for( ValueWithEpsilon value : this.resourcesPage.getTotalCPUCountValues() )
//      {
//        jsdl.setTotalCPUCountValue( value.getValue(), value.getEpsilon() );
//      }
//      for( Range range : this.resourcesPage.getTotalPhysicalMemory() ) {
//        jsdl.setTotalPhysicalMemory( range.getStart(), range.getEnd(), true );
//      }
//      for( ValueWithEpsilon value : this.resourcesPage.getTotalPhysicalMemoryValues() )
//      {
//        jsdl.setTotalPhysicalMemoryValue( value.getValue(), value.getEpsilon() );
//      }
//    }
    jsdl.getDataStagingIn();
    jsdl.getLocalDataStagingIn();
    jsdl.getStdInputDataType();
  }
  class FirstPage extends WizardNewFileCreationPage {

    private IStructuredSelection iniSelection;

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
    protected void initialPopulateContainerNameField()
    {
      {
        Object obj = this.iniSelection.getFirstElement();
        if( obj instanceof IGridContainer ) {
          IGridElement element = ( IGridElement )obj;
          IGridProject project = element.getProject();
          if( project != null ) {
            IGridElement descriptions = project.findChild( IGridProject.DIR_JOBDESCRIPTIONS );
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
      }
    }
  }
}