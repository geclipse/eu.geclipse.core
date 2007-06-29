package eu.geclipse.ui.internal;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import eu.geclipse.core.model.IGridConnectionElement;


public class GridConnectionProtocolFilter extends ViewerFilter {

  private List<String> protocolsToFilter = new ArrayList<String>();
  
  public void addFilterProtocol( final String protocol ){
    if (! this.protocolsToFilter.contains( protocol )){
      this.protocolsToFilter.add( protocol );
    }
  }
  
  @Override
  public boolean select( final Viewer viewer, final Object parentElement, final Object element )
  {
    boolean result = false;
    if ( element instanceof IGridConnectionElement ) {
      result = select( ( IGridConnectionElement  ) element );
    }
    return result;
  }

  private boolean select( final IGridConnectionElement element ) {
    boolean result = true;
    try {
      String protocol = element.getConnectionFileStore().getFileSystem().getScheme();
      if (this.protocolsToFilter.contains( protocol )){
        result = false;
      }
    } catch( CoreException e ) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return result;
  }
}
