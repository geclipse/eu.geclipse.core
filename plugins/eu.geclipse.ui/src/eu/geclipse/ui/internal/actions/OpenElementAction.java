package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.actions.OpenFileAction;
import org.eclipse.ui.editors.text.EditorsUI;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridJob;

public class OpenElementAction
    extends Action
    implements ISelectionChangedListener {
  
  private OpenFileAction openFileAction;
  
  private OpenFileAction openConnectionAction;
  
  private OpenJobAction openJobAction;
  
  private BaseSelectionListenerAction activeAction;
  
  protected OpenElementAction( final IWorkbenchPage page ) {
    
    super( "&Open@F3" );
    
    IEditorRegistry editorRegistry
      = page.getWorkbenchWindow().getWorkbench().getEditorRegistry();
    IEditorDescriptor defaultTextEditor
      = editorRegistry.findEditor( EditorsUI.DEFAULT_TEXT_EDITOR_ID );
    
    this.openFileAction = new OpenFileAction( page );
    this.openConnectionAction = new OpenFileAction( page, defaultTextEditor );
    this.openJobAction = new OpenJobAction( page );
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    if ( this.activeAction != null ) {
      this.activeAction.run();
    }
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
   */
  public void selectionChanged( final SelectionChangedEvent event ) {
    ISelection selection = event.getSelection();
    if ( selection instanceof IStructuredSelection ) {
      selectionChanged( ( IStructuredSelection ) selection );
    } else {
      selectionChanged( StructuredSelection.EMPTY );
    }
  }
  
  public void selectionChanged( final IStructuredSelection selection ) {
    
    this.activeAction = null;
    
    if ( isGridConnection( selection ) ) {
      this.activeAction = this.openConnectionAction;
    } else if ( isGridJob( selection ) ) {
      this.activeAction = this.openJobAction;
    } else {
      this.activeAction = this.openFileAction;
    }
    
    if ( this.activeAction != null ) {
      this.activeAction.selectionChanged( selection );
      setEnabled( this.activeAction.isEnabled() );
    } else {
      setEnabled( false );
    }
    
  }
  
  protected boolean isGridConnection( final IStructuredSelection selection ) {
    return
      ( selection.size() == 1 )
      && ( selection.getFirstElement() instanceof IGridConnectionElement );
  }
  
  protected boolean isGridJob( final IStructuredSelection selection ) {
    return
    ( selection.size() == 1 )
    && ( selection.getFirstElement() instanceof IGridJob );
  }
    
}
