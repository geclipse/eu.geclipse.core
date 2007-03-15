package eu.geclipse.ui.internal.actions;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.actions.BuildAction;
import org.eclipse.ui.actions.RefreshAction;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import eu.geclipse.ui.internal.Activator;

public class BuildActions extends ActionGroup {
  
  private IWorkbenchSite site;
  
  private BuildAction buildAction;
  
  private RefreshAction refreshAction;
  
  public BuildActions( final IWorkbenchSite site ) {
    
    this.site = site;
    Shell shell = site.getShell();
    ISelectionProvider provider = site.getSelectionProvider();
    
    this.buildAction = new BuildAction( shell, IncrementalProjectBuilder.INCREMENTAL_BUILD );
    this.refreshAction = new RefreshAction( shell );
    this.refreshAction.setText( "Re&fresh@F5" );

    ImageRegistry imgReg = Activator.getDefault().getImageRegistry();
    Image image = imgReg.get( "refresh" ); //$NON-NLS-1$
    ImageDescriptor refreshImage = ImageDescriptor.createFromImage( image );
    this.refreshAction.setImageDescriptor( refreshImage );

    provider.addSelectionChangedListener( this.buildAction );
    provider.addSelectionChangedListener( this.refreshAction );
    
  }
  
  @Override
  public void dispose() {
    ISelectionProvider provider = this.site.getSelectionProvider();
    provider.removeSelectionChangedListener( this.buildAction );
    provider.removeSelectionChangedListener( this.refreshAction );
  }
  
  @Override
  public void fillContextMenu( final IMenuManager menu ) {
    if ( this.buildAction.isEnabled() ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_BUILD, 
                          this.buildAction );
    }
    if ( this.refreshAction.isEnabled() ) {
      menu.appendToGroup( ICommonMenuConstants.GROUP_BUILD,
                          this.refreshAction );
    }
    super.fillContextMenu(menu);
  }
  
}
