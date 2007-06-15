package eu.geclipse.ui.adapters;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.model.IWorkbenchAdapter;


public class GridUIAdapterFactory implements IAdapterFactory {

  public Object getAdapter( final Object adaptableObject, final Class adapterType ) {

    if(adapterType.isAssignableFrom( IWorkbenchAdapter.class)){
      return new GridJobAdapter();
    }
    // TODO Auto-generated method stub
    return null;
  }

  public Class[] getAdapterList() {
    Class[] table = new Class[1];
    table[0]= IWorkbenchAdapter.class;
    return table;
  }
}
