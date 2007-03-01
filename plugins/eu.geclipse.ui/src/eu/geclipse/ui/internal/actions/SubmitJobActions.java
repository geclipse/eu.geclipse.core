package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;


public class SubmitJobActions extends ActionGroup {
  
  private IWorkbenchSite site;
  
  private SubmitJobAction submitAction;
  
  protected SubmitJobActions( final IWorkbenchSite site ) {
    this.site = site;
    ISelectionProvider provider = site.getSelectionProvider();
    this.submitAction = new SubmitJobAction( site );
    provider.addSelectionChangedListener( this.submitAction );
  }
  
  @Override
  public void dispose() {
    ISelectionProvider provider = this.site.getSelectionProvider();
    provider.removeSelectionChangedListener( this.submitAction );
  }
  
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    if ( this.submitAction.isEnabled() ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_BUILD, 
                          this.submitAction );
    }
    super.fillContextMenu(menu);
  }
  
}
