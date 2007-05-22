package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.jface.window.SameShellProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.dialogs.PropertyDialogAction;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import eu.geclipse.ui.internal.GridElementSelectionAdapter;

public class CommonActions extends ActionGroup {
  
  private IWorkbenchSite site;
  
  private GridElementSelectionAdapter selectionAdapter;
  
  private PropertyDialogAction propertyAction;
  
  public CommonActions( final IWorkbenchSite site ) {
  
    this.site = site;
    Shell shell = site.getShell();
    IShellProvider shellProvider = new SameShellProvider( shell );
    ISelectionProvider selectionProvider = site.getSelectionProvider();
    
    this.selectionAdapter = new GridElementSelectionAdapter();
    selectionProvider.addSelectionChangedListener( this.selectionAdapter );
    
    this.propertyAction
      = new PropertyDialogAction( shellProvider, this.selectionAdapter );
    
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.ActionGroup#dispose()
   */
  @Override
  public void dispose() {
    ISelectionProvider selectionProvider = this.site.getSelectionProvider();
    selectionProvider.removeSelectionChangedListener( this.selectionAdapter );
  }
  
  public void fillContextMenu( final IMenuManager menu ) {
    if ( this.propertyAction.isEnabled() ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_PROPERTIES, 
                          this.propertyAction );
    }
    super.fillContextMenu(menu);
  }
  
}
