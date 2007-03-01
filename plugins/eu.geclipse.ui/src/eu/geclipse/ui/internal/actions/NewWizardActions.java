package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.search.ui.IContextMenuConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.actions.NewWizardMenu;

public class NewWizardActions extends ActionGroup {
  
  private IWorkbenchWindow window;
  
  public NewWizardActions( final IWorkbenchWindow window ) {
    super();
    this.window = window;
  }
  
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    super.fillContextMenu( menu );
    ISelection selection= getContext().getSelection();
    if (selection instanceof IStructuredSelection) {
      IStructuredSelection sselection= ( IStructuredSelection ) selection;
      if ( sselection.size() <= 1 ) {
        IMenuManager submenu = new MenuManager( Messages.getString( "NewWizardActions.new_action_group" ) ); //$NON-NLS-1$
        menu.appendToGroup( IContextMenuConstants.GROUP_NEW, submenu );
        submenu.add( new NewWizardMenu( this.window ) );
      }
    }
  }
  
}
