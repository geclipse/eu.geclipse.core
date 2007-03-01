package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.search.ui.IContextMenuConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionGroup;

public class MountActions extends ActionGroup {
  
  private IWorkbenchPartSite site;
  
  private MountMenu mountMenu;
  
  protected MountActions( final IWorkbenchPartSite site ) {
    this.site = site;
    this.mountMenu = new MountMenu();
    ISelectionProvider selectionProvider
      = this.site.getSelectionProvider();
    selectionProvider.addSelectionChangedListener( this.mountMenu );
  }
  
  @Override
  public void dispose() {
    ISelectionProvider selectionProvider
      = this.site.getSelectionProvider();
    selectionProvider.removeSelectionChangedListener( this.mountMenu );
    this.mountMenu.dispose();
  }
  
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    if ( this.mountMenu.isVisible() ) {
      IMenuManager subMenu = new MenuManager( "Mount" );
      menu.appendToGroup( IContextMenuConstants.GROUP_OPEN, subMenu );
      subMenu.add( this.mountMenu );
    }
  }
  
}
