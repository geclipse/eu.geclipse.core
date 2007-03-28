package eu.geclipse.ui.properties;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.views.properties.IPropertySource;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobDescription;


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
    AbstractPropertySource propertySource = null;
    if( adapterType == IPropertySource.class ) {
      if( adaptableObject instanceof IGridJob ) {
        propertySource = new JobPropertySource( ( IGridJob )adaptableObject );
      } else if( adaptableObject instanceof IGridJobDescription ) {
        propertySource = new JobDescPropertySource( ( IGridJobDescription )adaptableObject );
      } else if( adaptableObject instanceof IGridElement ) {
        // Check elements inherited from IGridElement above this line!
        propertySource = new GridElementPropertySource( ( IGridElement )adaptableObject );
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
