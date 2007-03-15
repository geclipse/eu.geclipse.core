package eu.geclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;


public class ActionGroupManager extends ActionGroup {
  
  private List< ActionGroup > groups;
  
  public ActionGroupManager() {
    this.groups = new ArrayList< ActionGroup >();
  }
  
  @Override
  public void dispose() {
    for ( ActionGroup group : this.groups ) {
      group.dispose();
    }
    super.dispose();
  }
  
  @Override
  public void setContext( final ActionContext context ) {
    super.setContext( context );
    for ( ActionGroup group : this.groups ) {
      group.setContext( context );
    }
  }
  
  @Override
  public void fillActionBars( final IActionBars actionBars ) {
    super.fillActionBars( actionBars );
    for ( ActionGroup group : this.groups ) {
      group.fillActionBars( actionBars );
    }
  }
  
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    super.fillContextMenu( menu );
    for ( ActionGroup group : this.groups ) {
      group.fillContextMenu( menu );
    }
  }
  
  @Override
  public void updateActionBars() {
    super.updateActionBars();
    for ( ActionGroup group : this.groups ) {
      group.updateActionBars();
    }
  }
 
  public void addGroup( final ActionGroup group ) {
    this.groups.add( group );
  }
  
  public List< ActionGroup > getGroups() {
    return this.groups;
  }
  
}
