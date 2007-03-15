package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import eu.geclipse.ui.views.GridModelViewPart;

public class FileActions extends ActionGroup {
  
  private Clipboard clipboard;
  
  private IWorkbenchSite site;
  
  private CopyAction copyAction;
  
  private PasteAction pasteAction;
  
  private DeleteElementAction deleteAction;
  
  public FileActions( final GridModelViewPart view ) {
    
    this.site = view.getSite();
    Shell shell = this.site.getShell();
    ISelectionProvider provider = this.site.getSelectionProvider();
    
    this.clipboard = new Clipboard( shell.getDisplay() );
    
    this.copyAction = new CopyAction( this.clipboard );
    this.pasteAction = new PasteAction( view, this.clipboard );
    this.deleteAction = new DeleteElementAction( view );
    
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
