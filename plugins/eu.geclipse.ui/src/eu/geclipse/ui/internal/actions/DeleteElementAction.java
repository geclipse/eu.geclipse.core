package eu.geclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.DeleteResourceAction;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.ui.views.GridModelViewPart;

public class DeleteElementAction
    extends NotifierAction
    implements ISelectionChangedListener, IActionListener {

  GridModelViewPart view;
  
  private DeleteResourceAction deleteResourceAction;
  
  protected DeleteElementAction( final GridModelViewPart view ) {
    super( "&Delete" );
    ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
    ImageDescriptor deleteImage 
        = sharedImages.getImageDescriptor( ISharedImages.IMG_TOOL_DELETE );
    setImageDescriptor( deleteImage );
    this.view = view;
    Shell shell = view.getSite().getShell();
    this.deleteResourceAction = new DeleteResourceAction( shell );
    addActionListener( this );
  }
  
  public void actionAboutToRun() {
    // empty implementation
  }

  public void actionFinished() {
    /*
    IStructuredSelection selection
      = this.deleteResourceAction.getStructuredSelection();
    final List< IGridContainer > dirtyContainers
      = findDirtyContainers( selection );
    Job refreshJob = new Job( "Refreshing view" ) {
      @Override
      protected IStatus run( final IProgressMonitor monitor ) {
        Display display = DeleteElementAction.this.view.getSite().getShell().getDisplay();
        display.syncExec( new Runnable() {
          public void run() {
            for ( IGridContainer container : dirtyContainers ) {
              container.setDirty();
              DeleteElementAction.this.view.refreshViewer( container );
            }
          }
        } );
        return Status.OK_STATUS;
      }
    };
    refreshJob.setPriority( Job.DECORATE );
    refreshJob.schedule();
    */
  }
  
  public void selectionChanged( final SelectionChangedEvent event ) {
    this.deleteResourceAction.selectionChanged( event );
    setEnabled( this.deleteResourceAction.isEnabled() );
  }

  @Override
  protected void internalRun() {
    this.deleteResourceAction.run();
  }
  
  protected List< IGridContainer > findDirtyContainers( final IStructuredSelection selection ) {
    List< IGridContainer > result
      = new ArrayList< IGridContainer >();
    for ( Object obj : selection.toArray() ) {
      if ( obj instanceof IGridConnectionElement ) {
        IGridContainer parent = ( ( IGridConnectionElement ) obj ).getParent();
        if ( ( parent instanceof IGridConnectionElement ) && !result.contains( parent ) ) {
          result.add( parent );
        }
      }
    }
    return result;
  }

}
