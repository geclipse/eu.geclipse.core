package eu.geclipse.ui.internal.actions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.OpenFileAction;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.views.GridJobDetailsView;

public class OpenElementAction
    extends Action
    implements ISelectionChangedListener {
  
  private OpenFileAction openFileAction;
  final IWorkbenchPage workbenchPage;
  
  protected OpenElementAction( final IWorkbenchPage page ) {
    super( "&Open@F3" );
    workbenchPage = page;
    this.openFileAction = new OpenFileAction( page );
  }
  
  @Override
  public void run() {
    IStructuredSelection selection = this.openFileAction.getStructuredSelection();
    selection = remapSelection( selection );
    this.openFileAction.selectionChanged( selection );
    this.openFileAction.run();
  }
  
  public void selectionChanged( final SelectionChangedEvent event ) {
    this.openFileAction.selectionChanged( event );
    setEnabled( this.openFileAction.isEnabled() );
  }
  
  public void selectionChanged( final IStructuredSelection selection ) {
    this.openFileAction.selectionChanged( selection );
    setEnabled( this.openFileAction.isEnabled() );
  }
    
  protected IStructuredSelection remapSelection( final IStructuredSelection selection ) {
    IStructuredSelection result = selection;    
      List< Object > list = new ArrayList< Object >();
      for ( Object obj : selection.toArray() ) {
        if ( obj instanceof IGridConnectionElement ) {
          try {
            obj = getFile( ( IGridConnectionElement ) obj );
          } catch( CoreException cExc ) {
            Activator.logException( cExc );
          }
          list.add( obj );
        } else if( obj instanceof IGridJob  ){
          openJob( ( IGridJob )obj );
        } else {
          list.add( obj );
        }        
      }
      result = new StructuredSelection( list );
    return result;
  }
  
  protected IFile getFile( final IGridConnectionElement connection )
      throws CoreException {
    IFile file = null;
    if ( !connection.isFolder() ) {
      String name = connection.getName();
      IFileStore fileStore = connection.getConnectionFileStore();
      try {
        InputStream contents = fileStore.openInputStream( EFS.NONE, null );
        IGridProject project = connection.getProject();
        file = project.createTempFile( name, contents );
      } catch ( CoreException cExc ) {
        // Nothing to do, just catch
      }
    }
    return file;
  }

  private void openJob( final IGridJob job ){
    try {
      GridJobDetailsView view = ( GridJobDetailsView )this.workbenchPage.showView( GridJobDetailsView.ID, job.getPath().toString(), IWorkbenchPage.VIEW_ACTIVATE );
      view.setInputJob( job );
    } catch( PartInitException e ) {
      e.printStackTrace();
    }
  }
}
