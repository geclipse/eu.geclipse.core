package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.actions.DeleteResourceAction;
import org.eclipse.ui.navigator.ICommonMenuConstants;

public class FileActions extends ActionGroup {
  
  private Clipboard clipboard;
  
  private IWorkbenchSite site;
  
  private CopyAction copyAction;
  
  private PasteAction pasteAction;
  
  private DeleteResourceAction deleteAction;
  
  public FileActions( final IWorkbenchSite site ) {
    
    this.site = site;
    Shell shell = site.getShell();
    ISelectionProvider provider = site.getSelectionProvider();
    
    this.clipboard = new Clipboard( shell.getDisplay() );
    
    this.copyAction = new CopyAction( this.clipboard );
    this.pasteAction = new PasteAction( this.clipboard );
    this.deleteAction = new DeleteResourceAction( shell );
    
    provider.addSelectionChangedListener( this.copyAction );
    provider.addSelectionChangedListener( this.pasteAction );
    provider.addSelectionChangedListener( this.deleteAction );
    
  }
  
  @Override
  public void dispose() {
    ISelectionProvider provider = this.site.getSelectionProvider();
    provider.removeSelectionChangedListener( this.copyAction );
    provider.removeSelectionChangedListener( this.pasteAction );
    provider.removeSelectionChangedListener( this.deleteAction );
    this.clipboard.dispose();
  }
  
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    if ( this.deleteAction.isEnabled() ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_EDIT, 
                          this.copyAction );
      menu.appendToGroup( ICommonMenuConstants.GROUP_EDIT, 
                          this.pasteAction );
      menu.appendToGroup( ICommonMenuConstants.GROUP_EDIT, 
                          this.deleteAction );
    }
    super.fillContextMenu(menu);
  }
  
}
