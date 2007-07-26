package eu.geclipse.ui.adapters;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.model.IWorkbenchAdapter;


public class GridUIAdapterFactory implements IAdapterFactory {

  @SuppressWarnings("unchecked")
  public Object getAdapter( final Object adaptableObject, final Class adapterType ) {
    Object result = null;
    if(adapterType.isAssignableFrom( IWorkbenchAdapter.class)){
      result = new GridJobAdapter();
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public Class[] getAdapterList() {
    Class[] table = new Class[1];
    table[0]= IWorkbenchAdapter.class;
    return table;
  }
}
