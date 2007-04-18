package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionGroup;
import eu.geclipse.ui.views.GridModelViewPart;

public class ConnectionViewActions extends ActionGroup {
  
  private NewConnectionAction newConnectionAction;
  
  public ConnectionViewActions( final IWorkbenchWindow workbenchWindow ) {
    this.newConnectionAction = new NewConnectionAction( workbenchWindow );
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
   */
  @Override
  public void fillActionBars( final IActionBars actionBars ) {
    IToolBarManager manager = actionBars.getToolBarManager();
    manager.add( this.newConnectionAction );
  }
  
}
