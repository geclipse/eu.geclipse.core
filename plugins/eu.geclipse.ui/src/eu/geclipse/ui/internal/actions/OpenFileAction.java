package eu.geclipse.ui.internal.actions;

import java.util.Iterator;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.SelectionListenerAction;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.part.FileEditorInput;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridRoot;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;

public class OpenFileAction
    extends SelectionListenerAction {
  
  protected IWorkbenchPage workbenchPage;
  
  private IEditorDescriptor editorDescriptor;
  
  private long SIZE_LIMIT_MB = 10;
  
  public OpenFileAction( final IWorkbenchPage page ) {
    this( page, null );
  }

  public OpenFileAction( final IWorkbenchPage page, final IEditorDescriptor descriptor ) {
    super( IDEWorkbenchMessages.OpenSystemEditorAction_text );
    this.workbenchPage = page;
    this.editorDescriptor = descriptor;
  }
  
  @Override
  public void run() {
    Iterator< ? > iter = getStructuredSelection().iterator();
    while ( iter.hasNext() ) {
      Object next = iter.next();
      if ( next instanceof IGridConnectionElement ) {
        try {
          openConnection( ( IGridConnectionElement ) next );
        } catch ( CoreException cExc ) {
          ProblemDialog.openProblem(
              this.workbenchPage.getWorkbenchWindow().getShell(),
              "Open failed",
              String.format( "Error while opening file %s", ( ( IGridConnectionElement ) next ).getName() ),
              cExc
          );
        }
      } else if ( next instanceof IAdaptable ) {
        IResource resource = ( IResource ) ( ( IAdaptable ) next ).getAdapter( IResource.class );
        if ( resource instanceof IFile ) {
          openFile( ( IFile ) resource );
        }
      }
    }
  }
  
  protected void openFile( final IFile file ) {
    try {
      boolean activate = OpenStrategy.activateOnOpen();
      if ( this.editorDescriptor == null ) {
        IDE.openEditor( this.workbenchPage, file, activate );
      } else {
        this.workbenchPage.openEditor(
            new FileEditorInput(file),
            this.editorDescriptor.getId(),
            activate
        );
      }
    } catch ( PartInitException piExc ) {
      ProblemDialog.openProblem(
          this.workbenchPage.getWorkbenchWindow().getShell(),
          "Open failed",
          String.format( "Error while opening file %s", file.getName() ),
          piExc
      );
    }
  }
  
  protected void openConnection( final IGridConnectionElement element ) throws CoreException {
    
    boolean confirm = true;
    long lengthMB = element.getConnectionFileInfo().getLength()/(1024*1024);
    
    if ( lengthMB > SIZE_LIMIT_MB ) {
      confirm = MessageDialog.openConfirm(
          this.workbenchPage.getWorkbenchWindow().getShell(),
          "Should the file really be opened?",
          String.format( "You are trying to open a large file of %d MB. It is not recommended to directly open files larger than %d MB since this may fail. Please consider to download this file and edit it locally.", lengthMB, SIZE_LIMIT_MB )
      );
    }
    
    if ( confirm ) {
    
      Job job = new Job( String.format( "Opening %s", element.getName() ) ) {
        @Override
        protected IStatus run( final IProgressMonitor monitor ) {
          SubMonitor sMonitor = SubMonitor.convert( monitor, String.format( "Opening %s", element.getName() ), 10 );
          try {
            sMonitor.subTask( "Establishing connection..." );
            IFileStore cfs = element.getCachedConnectionFileStore( sMonitor.newChild( 9 ) );
            sMonitor.subTask( "Opening editor..." );
            try {
              if ( ! sMonitor.isCanceled() ) {
                OpenFileAction.this.workbenchPage.getWorkbenchWindow().getShell().getDisplay().syncExec( new Runnable() {
                  public void run() {
                    openFile( ( IFile ) element.getResource() );
                  }
                } );
                sMonitor.worked( 1 );
              }
            } finally {
              element.releaseCachedConnectionFileStore( cfs );
            }
          } catch ( CoreException cExc ) {
            Activator.logException( cExc );
          } finally {
            sMonitor.done();
          }
          return Status.OK_STATUS;
        }
      };
      job.setUser( true );
      job.schedule();
      
    }
    
  }
  
  private IGridConnectionElement getConnectionElement( final IFile file ) {
    
    IGridConnectionElement result = null;
    
    IGridRoot gridRoot = GridModel.getRoot();
    IGridElement element = gridRoot.findElement( file );
    
    if ( ( element != null ) && ( element instanceof IGridConnectionElement ) ) {
      result = ( IGridConnectionElement ) element;
    }
    
    return result;
    
  }

}
