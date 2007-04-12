package eu.geclipse.ui.properties;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.ResourcePropertySource;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.glite.info.BDIIService;


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
      if( adaptableObject instanceof IGridJob ) {
        propertySource = new GridJobSource( ( IGridJob )adaptableObject );
      } else if( adaptableObject instanceof IGridJobDescription ) {
        propertySource = new GridJobDescSource( ( IGridJobDescription )adaptableObject );
      } else if( adaptableObject instanceof IGridConnection ) {
        propertySource = new ConnectionPropertySource( ( IGridConnection )adaptableObject );
      } else if( adaptableObject instanceof IVirtualOrganization ) {
        propertySource = new VOPropertySource( ( IVirtualOrganization )adaptableObject );
      } else if( adaptableObject instanceof BDIIService ) {
        propertySource = new BDIIServicePropertySource( ( BDIIService )adaptableObject );
      }
      // Check elements inherited from IGridElement above this line!
      else if( adaptableObject instanceof IGridElement ) {
        IGridElement gridElement = ( IGridElement )adaptableObject;
        IResource resource = gridElement.getResource();
        if( resource == null ) {
          propertySource = new GridElementPropertySource( ( IGridElement )adaptableObject );
        } else {
          propertySource = new ResourcePropertySource( resource );
        }
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
