package eu.geclipse.ui.properties;

import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.model.IPropertiesProvider;



/**
 * Properties source for object implementing IPropertiesProvider
 */
public class PropertiesProviderSource 
extends AbstractPropertySource<IPropertiesProvider> 
{
  IPropertiesProvider propertyProvider;

  /**
   * @param sourceObject
   */
  public PropertiesProviderSource( final IPropertiesProvider sourceObject ) {
    super( sourceObject );
    this.propertyProvider = sourceObject;
  }

  @Override
  protected Class<? extends AbstractPropertySource<?>> getPropertySourceClass() {
    return PropertiesProviderSource.class;
  }

  @Override
  protected List<IProperty<IPropertiesProvider>> getStaticProperties() {
    List<IProperty<IPropertiesProvider>> list = new ArrayList<IProperty<IPropertiesProvider>>();

    for( String key : this.propertyProvider.getPropertyKeys() ) {
      list.add( createProperty( key ) );
    }
    
    return list;
  }
  
  // TODO mariusz Remove hardcoded "LFC". Get category from IPropertiesProvider
  private IProperty<IPropertiesProvider> createProperty( final String key ) {
    return new AbstractProperty<IPropertiesProvider>( key, "LFC", false ) {

      @Override
      public Object getValue( final IPropertiesProvider sourceObject ) {
        return PropertiesProviderSource.this.propertyProvider.getPropertyValue( key );
      }
    };
  }
}