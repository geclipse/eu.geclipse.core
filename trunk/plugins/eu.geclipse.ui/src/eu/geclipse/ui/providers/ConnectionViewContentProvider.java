package eu.geclipse.ui.providers;

import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridElement;

public class ConnectionViewContentProvider extends ConfigurableContentProvider {
  
  @Override
  protected boolean isVisible( final IGridElement element ) {
    boolean visible = element instanceof IGridConnectionElement;
    if ( !visible ) {
      visible = super.isVisible( element );
    }
    return visible;
  }
  
}
