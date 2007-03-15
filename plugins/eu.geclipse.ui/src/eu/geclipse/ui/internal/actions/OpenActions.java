package eu.geclipse.ui.internal.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;

public class OpenActions extends ActionGroup {
  
  private IWorkbenchSite site;
  
  private OpenElementAction openElementAction;
  
  public OpenActions( final IWorkbenchSite site ) {
    
    this.site = site;
    IWorkbenchPage page = site.getPage();
    ISelectionProvider provider = site.getSelectionProvider();
    
    this.openElementAction = new OpenElementAction( page );
    
    provider.addSelectionChangedListener( this.openElementAction );
    
  }
  
  @Override
  public void dispose() {
    ISelectionProvider provider = this.site.getSelectionProvider();
    provider.removeSelectionChangedListener( this.openElementAction );
  }
  
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    if ( this.openElementAction.isEnabled() ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_OPEN, 
                          this.openElementAction );
    }
    super.fillContextMenu(menu);
  }
  
  public void delegateOpenEvent( final OpenEvent event ) {
    ISelection selection = event.getSelection();
    if ( selection instanceof IStructuredSelection ) {
      IStructuredSelection sSelection = ( IStructuredSelection ) selection;
      Object element = sSelection.getFirstElement();
      if ( !(element instanceof IResource ) && ( element instanceof IAdaptable ) ) {
        element = ( ( IAdaptable ) element ).getAdapter( IResource.class );
      }
      if (element instanceof IFile) {
        this.openElementAction.selectionChanged( sSelection );
        this.openElementAction.run();
      }
    }
  }

}
