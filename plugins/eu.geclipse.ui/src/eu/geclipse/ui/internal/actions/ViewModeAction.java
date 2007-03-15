package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.providers.ConfigurableContentProvider;
import eu.geclipse.ui.providers.IConfigurationListener;
import eu.geclipse.ui.views.ElementManagerViewPart;

public class ViewModeAction
    extends Action
    implements IConfigurationListener {
  
  private ElementManagerViewPart view;
  
  private int viewMode;
  
  protected ViewModeAction( final String name,
                            final int viewMode,
                            final ElementManagerViewPart view ) {
    super( name );
    this.view = view;
    this.viewMode = viewMode;
    updateActionState();
    ImageRegistry imageRegistry
      = Activator.getDefault().getImageRegistry();
    ImageDescriptor desc = null;
    if ( viewMode == ConfigurableContentProvider.MODE_FLAT ) {
      desc = imageRegistry.getDescriptor( "view_flat" );
    } else if ( viewMode == ConfigurableContentProvider.MODE_HIERARCHICAL ) {
      desc = imageRegistry.getDescriptor( "view_hierarchical" );
    }
    setImageDescriptor( desc );
  }
  
  public void configurationChanged( final ConfigurableContentProvider source ) {
    if ( source == getContentProvider() ) {
      updateActionState();
    }
  }
 
  @Override
  public void run() {
    ConfigurableContentProvider contentProvider
      = getContentProvider();
    if ( contentProvider != null ) {
      contentProvider.setMode( this.viewMode );
      this.view.refreshViewer();
    }
    updateActionState();
  }
  
  public ConfigurableContentProvider getContentProvider() {
    StructuredViewer viewer = this.view.getViewer();
    ConfigurableContentProvider result = null;
    IContentProvider contentProvider = viewer.getContentProvider();
    if ( contentProvider instanceof ConfigurableContentProvider ) {
      result = ( ConfigurableContentProvider ) contentProvider;
    }
    return result;
  }
  
  protected int getProviderMode() {
    int result = 0;
    ConfigurableContentProvider contentProvider
      = getContentProvider();
    if ( contentProvider != null ) {
      result = contentProvider.getMode();
    }
    return result;
  }
  
  protected void updateActionState() {
    int pMode = getProviderMode();
    setChecked( pMode == this.viewMode );
  }
  
}
