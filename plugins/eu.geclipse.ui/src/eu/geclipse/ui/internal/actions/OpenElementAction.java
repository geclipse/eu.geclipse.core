package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.OpenFileAction;
import eu.geclipse.core.model.IGridElement;

public class OpenElementAction
    extends Action
    implements ISelectionChangedListener {
  
  private OpenFileAction openFileAction;
  
  protected OpenElementAction( final IWorkbenchPage page ) {
    super( "Open" );
    this.openFileAction = new OpenFileAction( page );
  }
  
  public void run() {
    IStructuredSelection selection = this.openFileAction.getStructuredSelection();
    selection = remapSelection( selection );
    this.openFileAction.selectionChanged( selection );
    this.openFileAction.run();
  }
  
  public void selectionChanged( final SelectionChangedEvent event ) {
    this.openFileAction.selectionChanged( event );
    setEnabled( openFileAction.isEnabled() );
  }
  
  public final void selectionChanged( final IStructuredSelection selection ) {
    this.openFileAction.selectionChanged( selection );
    setEnabled( openFileAction.isEnabled() );
  }
  
  protected boolean containsGridElements( final IStructuredSelection selection ) {
    boolean result = false;
    for ( Object obj : selection.toArray() ) {
      if ( obj instanceof IGridElement ) {
        result = true;
        break;
      }
    }
    return result;
  }
  
  protected IStructuredSelection remapSelection( final IStructuredSelection selection ) {
    IStructuredSelection result = selection;
    if ( containsGridElements( selection ) ) {
      
    }
    return result;
  }
  
}
