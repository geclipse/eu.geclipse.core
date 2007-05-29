package eu.geclipse.ui.internal.actions;

import java.net.URL;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.providers.ConfigurableContentProvider;

public class ViewModeToggleAction extends Action {
  
  private ConfigurableContentProvider provider;
  
  public ViewModeToggleAction( final ConfigurableContentProvider provider ) {
    
    super( "Enable Hierarchical Mode", IAction.AS_CHECK_BOX );
  
    this.provider = provider;
    
    URL hierarchicalUrl
      = Activator.getDefault().getBundle().getEntry( ViewModeAction.VIEW_HIERARCHICAL_IMAGE );
    ImageDescriptor hierarchicalDescriptor
      = ImageDescriptor.createFromURL( hierarchicalUrl );
    setImageDescriptor( hierarchicalDescriptor );
    
  }
  
  @Override
  public void run() {
    int mode
      = this.isChecked()
      ? ConfigurableContentProvider.MODE_HIERARCHICAL
      : ConfigurableContentProvider.MODE_FLAT;
    this.provider.setMode( mode );
  }
  
}
