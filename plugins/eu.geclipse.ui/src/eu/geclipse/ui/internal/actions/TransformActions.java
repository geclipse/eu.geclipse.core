package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.search.ui.IContextMenuConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionGroup;

public class TransformActions extends ActionGroup {
  
  private IWorkbenchPartSite site;
  
  private TransformMenu transformMenu;
  
  public TransformActions( final IWorkbenchPartSite site ) {
    this.site = site;
    this.transformMenu = new TransformMenu();
    ISelectionProvider selectionProvider = site.getSelectionProvider();
    selectionProvider.addSelectionChangedListener( this.transformMenu );
  }
  
  @Override
  public void dispose() {
    ISelectionProvider selectionProvider = this.site.getSelectionProvider();
    selectionProvider.removeSelectionChangedListener( this.transformMenu );
    this.transformMenu.dispose();
  }
  
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    
    if ( this.transformMenu.isVisible() ) {
      IMenuManager subMenu = new MenuManager( "Transform To" );
      menu.appendToGroup( IContextMenuConstants.GROUP_GENERATE, subMenu );
      subMenu.add( this.transformMenu );
    }
    
    super.fillContextMenu( menu );
    
  }

}
