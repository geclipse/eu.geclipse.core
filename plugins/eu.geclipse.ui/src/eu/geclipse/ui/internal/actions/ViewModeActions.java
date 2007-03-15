package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionGroup;
import eu.geclipse.ui.providers.ConfigurableContentProvider;
import eu.geclipse.ui.views.ElementManagerViewPart;

public class ViewModeActions extends ActionGroup {
  
  private ViewModeAction flatAction;

  private ViewModeAction hierarchicalAction;
  
  public ViewModeActions( final ElementManagerViewPart view ) {
    this.flatAction
      = new ViewModeAction( "Flat",
                            ConfigurableContentProvider.MODE_FLAT,
                            view );
    this.hierarchicalAction
      = new ViewModeAction( "Hierarchical",
                            ConfigurableContentProvider.MODE_HIERARCHICAL,
                            view );
    registerAction( this.flatAction );
    registerAction( this.hierarchicalAction );
  }
  
  @Override
  public void dispose() {
    unregisterAction( this.flatAction );
    unregisterAction( this.hierarchicalAction );
  }
  
  @Override
  public void fillActionBars( final IActionBars actionBars ) {
    IMenuManager menuManager = actionBars.getMenuManager();
    menuManager.add( this.flatAction );
    menuManager.add( this.hierarchicalAction );
  }
  
  protected void registerAction( final ViewModeAction action ) {
    ConfigurableContentProvider contentProvider
      = this.flatAction.getContentProvider();
    if ( contentProvider != null ) {
      contentProvider.addConfigurationListener( action );
    }
  }
  
  protected void unregisterAction( final ViewModeAction action ) {
    ConfigurableContentProvider contentProvider
      = this.flatAction.getContentProvider();
    if ( contentProvider != null ) {
      contentProvider.removeConfigurationListener( action );
    }
  }
  
}
