package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.ui.views.GridJobDetailsView;

public class OpenJobAction
    extends BaseSelectionListenerAction {
  
  private IWorkbenchPage workbenchPage;

  protected OpenJobAction( final IWorkbenchPage workbenchPage ) {
    super( "Open Job" );
    this.workbenchPage = workbenchPage;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    Object element
      = getStructuredSelection().getFirstElement();
    if ( ( element != null ) && ( element instanceof IGridJob ) ) {
      IGridJob job = ( IGridJob ) element;
      GridJobDetailsView view;
      try {
        view = ( GridJobDetailsView ) this.workbenchPage.showView( GridJobDetailsView.ID,
                                                              job.getPath().toString(),
                                                              IWorkbenchPage.VIEW_ACTIVATE );
        view.setInputJob( job );
      } catch( PartInitException e ) {
        // Just ignore this exception and do not open the job
      }
    }
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    return
      ( selection.size() == 1 )
      && ( selection.getFirstElement() instanceof IGridJob );
  }
  
}
