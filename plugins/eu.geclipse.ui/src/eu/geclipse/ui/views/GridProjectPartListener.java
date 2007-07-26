package eu.geclipse.ui.views;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;
import eu.geclipse.ui.internal.actions.EditorActions;

public class GridProjectPartListener implements IPartListener2 {
  
  private EditorActions editorActions;
  
  protected GridProjectPartListener( final EditorActions editorActions ) {
    this.editorActions = editorActions;
  }

  public void partActivated( final IWorkbenchPartReference partRef ) {
    if ( partRef.getPart( true ) instanceof IEditorPart ) {
      this.editorActions.delegateLinkEditorAction();
    }
  }

  public void partBroughtToTop( final IWorkbenchPartReference partRef ) {
    if ( partRef.getPart( true ) == this.editorActions.getView() ) {
      this.editorActions.delegateLinkEditorAction();
    }
  }

  public void partClosed( final IWorkbenchPartReference partRef ) {
    // empty implementation
  }

  public void partDeactivated( final IWorkbenchPartReference partRef ) {
    // empty implementation
  }

  public void partHidden( final IWorkbenchPartReference partRef ) {
    // empty implementation
  }

  public void partInputChanged( final IWorkbenchPartReference partRef ) {
    // empty implementation
  }

  public void partOpened( final IWorkbenchPartReference partRef ) {
    if ( partRef.getPart( true ) == this.editorActions.getView() ) {
      this.editorActions.delegateLinkEditorAction();
    }
  }

  public void partVisible( final IWorkbenchPartReference partRef ) {
    if ( partRef.getPart( true ) == this.editorActions.getView() ) {
      this.editorActions.delegateLinkEditorAction();
    }
  }
  
}
