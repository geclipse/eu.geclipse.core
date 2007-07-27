package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.navigator.ICommonMenuConstants;


public class MonitorActions extends ActionGroup {
  
  /**
   * The workbench this action group belongs to.
   */
  private IWorkbenchPartSite site;
  /**
   * The deploy action itself.
   */
  private MonitorComputingAction monCEAction;
  private MonitorJobAction monJobAction;
  
  public MonitorActions(final IWorkbenchPartSite site){
    this.site = site;
    ISelectionProvider selectionProvider = site.getSelectionProvider();
    this.monCEAction = new MonitorComputingAction( site );
 //   this.monJobAction = new MonitorJobAction (site);
    selectionProvider.addSelectionChangedListener( this.monCEAction );
  //  selectionProvider.addSelectionChangedListener( this.monJobAction );
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.actions.ActionGroup#dispose()
   */
  @Override
  public void dispose()
  {
    ISelectionProvider selectionProvider = this.site.getSelectionProvider();
    selectionProvider.removeSelectionChangedListener( this.monCEAction );
 //   selectionProvider.removeSelectionChangedListener( this.monJobAction );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
   */
  @Override
  public void fillContextMenu( final IMenuManager mgr )
  {
    if ( this.monCEAction.isEnabled() ) {
      mgr.appendToGroup( ICommonMenuConstants.GROUP_BUILD, this.monCEAction );
    }
   // if ( this.monJobAction.isEnabled() ) {
   //   mgr.appendToGroup( ICommonMenuConstants.GROUP_BUILD, this.monJobAction );
   // }
    super.fillContextMenu( mgr );
  }
  
  
  
}
