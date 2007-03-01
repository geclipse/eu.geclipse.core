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
 *     PSNC - Katarzyna Bylec
 *           
 *****************************************************************************/

package eu.geclipse.ui.wizards.jobs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.wizards.jobs.internal.FileType;

/**
 * Wizard for creating new jsdl file
 * 
 * @author katis
 */
public class NewJobWizard extends Wizard implements INewWizard {

  private IStructuredSelection selection;
  private WizardNewFileCreationPage firstPage;
  private IFile file;
  private EnvNewJobWizardPage envPage;
  private FilesInputNewJobWizardPage inputFilesPage;
  private ExecutableNewJobWizardPage executablePage;
  private FilesOutputNewJobWizardPage outputFilesPage;

  @Override
  public void addPages()
  {
    this.firstPage = new FirstPage( Messages.getString( "NewJobWizard.first_page_name" ), //$NON-NLS-1$
                                    this.selection );
    this.firstPage.setTitle( Messages.getString( "NewJobWizard.first_page_title" ) ); //$NON-NLS-1$
    this.firstPage.setDescription( Messages.getString( "NewJobWizard.first_page_description" ) ); //$NON-NLS-1$
    this.firstPage.setFileName( Messages.getString( "NewJobWizard.first_page_default_new_file_name" ) ); //$NON-NLS-1$
    addPage( this.firstPage );
    this.executablePage = new ExecutableNewJobWizardPage( Messages.getString( "NewJobWizard.executablePageName" ) ); //$NON-NLS-1$
    addPage( this.executablePage );
    this.inputFilesPage = new FilesInputNewJobWizardPage( Messages.getString( "NewJobWizard.files_input_new_job_page_name" ) ); //$NON-NLS-1$
    addPage( this.inputFilesPage );
    this.outputFilesPage = new FilesOutputNewJobWizardPage( Messages.getString( "NewJobWizard.files_output_new_job_page_name" ) ); //$NON-NLS-1$;
    addPage( this.outputFilesPage );
    this.envPage = new EnvNewJobWizardPage( Messages.getString( "NewJobWizard.env_page_name" ) ); //$NON-NLS-1$
    addPage( this.envPage );
    // TabTestPage page = new TabTestPage("cos"); //$NON-NLS-1$
    // addPage( page );
  }

  public void init( final IWorkbench workbench, final IStructuredSelection sel )
  {
    setWindowTitle( Messages.getString( "NewJobWizard.windowTitle" ) ); //$NON-NLS-1$
    this.selection = sel;
  }

  @Override
  public boolean performFinish()
  {
    // IFile file = firstPage.createNewFile();
    // if (file == null) {
    // return false;
    // }
    // return true;
    boolean result = false;
    IRunnableWithProgress op = new IRunnableWithProgress() {

      public void run( final IProgressMonitor monitor )
        throws InvocationTargetException
      {
        try {
          createFile( monitor );
        } catch( CoreException e ) {
          throw new InvocationTargetException( e );
        } finally {
          monitor.done();
        }
      }
    };
    try {
      getContainer().run( false, true, op );
      result = true;
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

  protected void createFile( final IProgressMonitor monitor )
    throws CoreException
  {
    monitor.beginTask( Messages.getString( "NewJobWizard.creating_task" ) + this.firstPage.getFileName(), 2 ); //$NON-NLS-1$
    this.file = this.firstPage.createNewFile();
    monitor.worked( 1 );
    monitor.setTaskName( Messages.getString( "NewJobWizard.setting_contents_task" ) + this.firstPage.getFileName() ); //$NON-NLS-1$
    this.file.setContents( getInitialStream(), true, true, monitor );
    // jsdl -> jdl translation test
    // JSDLJobDescription descr = new JSDLJobDescription(file);
    // try {
    // WMSClient a = new WMSClient(new
    // URL("https://wms1.cyf-kr.edu.pl:7443/glite_wms_wmproxy_server"));
    // System.out.println(a.translateJSDLtoJDL( descr ));
    // } catch( MalformedURLException e ) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // end of test
    // catch( ServiceException e ) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    monitor.worked( 1 );
  }

  private InputStream getInitialStream() {
    String input = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"; //$NON-NLS-1$
    input = input
            + "<jsdl:JobDefinition xmlns=\"http://www.example.org/\" xmlns:jsdl=\"http://schemas.ggf.org/jsdl/2005/11/jsdl\" xmlns:jsdl-posix=\"http://schemas.ggf.org/jsdl/2005/11/jsdl-posix\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"; //$NON-NLS-1$
    String executableFile = this.executablePage.getExecutableFile();
    String jobName = this.executablePage.getJobName();
    String jobDescription = this.executablePage.getJobDescription();
    input = input + "<jsdl:JobDescription>\n"; //$NON-NLS-1$
    input = input + " <jsdl:JobIdentification>\n"; //$NON-NLS-1$
    input = input + "  <jsdl:JobName>" + jobName + "</jsdl:JobName>\n"; //$NON-NLS-1$ //$NON-NLS-2$
    input = input + "  <jsdl:Description>" + jobDescription; //$NON-NLS-1$
    input = input + "  </jsdl:Description>\n"; //$NON-NLS-1$
    input = input + " </jsdl:JobIdentification>\n"; //$NON-NLS-1$
    input = input + " <jsdl:Application>\n"; //$NON-NLS-1$
    input = input
            + "  <jsdl:ApplicationName>" + executableFile + "</jsdl:ApplicationName>\n"; //$NON-NLS-1$ //$NON-NLS-2$
    input = input + "  <jsdl-posix:POSIXApplication>\n"; //$NON-NLS-1$
    input = input + "   <jsdl-posix:Executable>\n" + executableFile + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
    input = input + "   </jsdl-posix:Executable>\n"; //$NON-NLS-1$
    input = input
            + "   <jsdl-posix:Input>\n" + this.inputFilesPage.getStdin() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
    input = input + "   </jsdl-posix:Input>\n"; //$NON-NLS-1$
    input = input
            + "   <jsdl-posix:Output>\n" + this.inputFilesPage.getStdout() + "\n"; //$NON-NLS-1$//$NON-NLS-2$
    input = input + "   </jsdl-posix:Output>\n"; //$NON-NLS-1$
    input = input + "  </jsdl-posix:POSIXApplication>\n"; //$NON-NLS-1$
    input = input + " </jsdl:Application>\n"; //$NON-NLS-1$
    HashMap<String, String> outFiles = this.outputFilesPage.getFiles( FileType.OUTPUT );
    if( !outFiles.isEmpty() ) {
      for( String name : outFiles.keySet() ) {
        input = input + " <jsdl:DataStaging>\n"; //$NON-NLS-1$
        input = input + "  <jsdl:FileName>" + name + "</jsdl:FileName>\n"; //$NON-NLS-1$//$NON-NLS-2$
        input = input + "  <jsdl:CreationFlag>overwrite</jsdl:CreationFlag>\n"; //$NON-NLS-1$
        input = input
                + "  <jsdl:DeleteOnTermination>true</jsdl:DeleteOnTermination>\n"; //$NON-NLS-1$
        input = input + "  <jsdl:Source>\n"; //$NON-NLS-1$
        input = input
                + "    <jsdl:URI>" + outFiles.get( name ) + "</jsdl:URI>\n"; //$NON-NLS-1$ //$NON-NLS-2$
        input = input + "  </jsdl:Source>\n"; //$NON-NLS-1$
        input = input + " </jsdl:DataStaging>\n"; //$NON-NLS-1$
      }
    }
    outFiles = this.outputFilesPage.getFiles( FileType.INPUT );
    if( !outFiles.isEmpty() ) {
      for( String name : outFiles.keySet() ) {
        input = input + " <jsdl:DataStaging>\n"; //$NON-NLS-1$
        input = input + "  <jsdl:FileName>" + name + "</jsdl:FileName>\n"; //$NON-NLS-1$//$NON-NLS-2$
        input = input + "  <jsdl:CreationFlag>overwrite</jsdl:CreationFlag>\n"; //$NON-NLS-1$
        input = input
                + "  <jsdl:DeleteOnTermination>true</jsdl:DeleteOnTermination>\n"; //$NON-NLS-1$
        input = input + "  <jsdl:Target>\n"; //$NON-NLS-1$
        input = input
                + "    <jsdl:URI>" + outFiles.get( name ) + "</jsdl:URI>\n"; //$NON-NLS-1$ //$NON-NLS-2$
        input = input + "  </jsdl:Target>\n"; //$NON-NLS-1$
        input = input + " </jsdl:DataStaging>\n"; //$NON-NLS-1$
      }
    }
    input = input + "</jsdl:JobDescription>\n"; //$NON-NLS-1$
    input = input + "</jsdl:JobDefinition>"; //$NON-NLS-1$
    return new ByteArrayInputStream( input.getBytes() );
  }
  class FirstPage extends WizardNewFileCreationPage {

    private IStructuredSelection iniSelection;

    public FirstPage( final String pageName, final IStructuredSelection selection ) {
      super( pageName, selection );
      this.iniSelection = selection;
    }

    @Override
    protected void initialPopulateContainerNameField()
    {
      {
        Object obj = this.iniSelection.getFirstElement();
        IGridContainer container = null;
        IGridContainer child = null;
        if( obj instanceof IGridContainer ) {
          container = ( IGridContainer )obj;
          while( container.getParent() != null ) {
            if( child != null ) {
              if( child.getName().equals( "Job Descriptions" ) ) { //$NON-NLS-1$
                break;
              }
            }
            child = container;
            container = container.getParent();
          }
          if( !child.getName().equals( "Job Descriptions" ) ) { //$NON-NLS-1$
            for( IGridElement element : child.getChildren( null ) ) {
              if( element.getName().equals( "Job Descriptions" ) //$NON-NLS-1$
                  && element instanceof IGridContainer )
              {
                child = ( IGridContainer )element;
              }
            }
          }
          super.setContainerFullPath( child.getPath() );
        } else {
          super.initialPopulateContainerNameField();
        }
      }
    }
  }
}