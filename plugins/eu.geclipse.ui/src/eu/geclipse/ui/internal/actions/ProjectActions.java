package eu.geclipse.ui.internal.actions;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.actions.CloseResourceAction;
import org.eclipse.ui.actions.OpenResourceAction;
import org.eclipse.ui.navigator.ICommonMenuConstants;

public class ProjectActions extends ActionGroup {
  
  private IWorkbenchSite site;
  
  private OpenResourceAction openAction;
  
  private CloseResourceAction closeAction;
  
  public ProjectActions( final IWorkbenchSite site ) {
  
    this.site = site;
    Shell shell = site.getShell();
    ISelectionProvider provider = site.getSelectionProvider();
    IWorkspace workspace= ResourcesPlugin.getWorkspace();
    
    this.openAction = new OpenResourceAction( shell );
    this.closeAction = new CloseResourceAction( shell );
    
    provider.addSelectionChangedListener( this.openAction );
    provider.addSelectionChangedListener( this.closeAction );
    
    workspace.addResourceChangeListener( this.openAction );
    workspace.addResourceChangeListener( this.closeAction );
    
  }
  
  @Override
  public void dispose() {
    ISelectionProvider provider = this.site.getSelectionProvider();
    provider.removeSelectionChangedListener( this.openAction );
    provider.removeSelectionChangedListener( this.closeAction );
  }
  
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    super.fillContextMenu( menu );
    if ( this.openAction.isEnabled() ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_BUILD,
                          this.openAction );
    }
    if ( this.closeAction.isEnabled() ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_BUILD,
                          this.closeAction );
    }
  }
  
}
