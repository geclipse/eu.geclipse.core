package eu.geclipse.ui.properties;

import java.util.List;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.views.properties.IPropertySource;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.Extensions;


/**
 * Adapter factory for properties Register here new class inheriting
 * {@link AbstractPropertySource}
 */
public class PropertiesAdapterFactory implements IAdapterFactory {

  static private PropertiesAdapterFactory singleton;

  @SuppressWarnings("unchecked")
  public Class[] getAdapterList()
  {
    return new Class[]{
      IPropertySource.class
    };
  }

  @SuppressWarnings("unchecked")
  public Object getAdapter( final Object adaptableObject,
                            final Class adapterType )
  {
    IPropertySource propertySource = null;
    if( adapterType == IPropertySource.class ) {
      // TODO mariusz add factories caching
      List<IPropertiesFactory> factoriesList = Extensions.getPropertiesFactories( adaptableObject.getClass() );
      
      if( !factoriesList.isEmpty() ) {
        propertySource = new PropertySourceProxy( factoriesList, adaptableObject );
      }      
    }
    return propertySource;
  }

  /**
   * Register adapter in eclipse
   */
  static public void register() {
    if( singleton == null ) {
      singleton = new PropertiesAdapterFactory();
      Platform.getAdapterManager().registerAdapters( singleton,
                                                     IGridElement.class );
    }
  }

  /**
   * Unregister adapter
   */
  static public void unregister() {
    if( singleton != null ) {
      Platform.getAdapterManager().unregisterAdapters( singleton );
      singleton = null;
    }
  }
  
  
}
