package eu.geclipse.core.model.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.geclipse.core.config.Configuration;
import eu.geclipse.core.config.IConfiguration;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IVirtualOrganization;

public class VoConfiguration extends Configuration {
  
  private static final String VO_TYPE = "type";
  
  private static final String VO_NAME = "name";
  
  private static final String VO_SERVICES = "services";
  
  private static final String SERVICE_SEPARATOR = ":";
  
  public VoConfiguration( final Class< ? extends IVirtualOrganization > voType ) {
    setParameter( VO_TYPE, voType.getName() );
  }
  
  public VoConfiguration( final Class< ? extends IVirtualOrganization > voType,
                          final IConfiguration configuration ) {
    super( configuration );
    setParameter( VO_TYPE, voType.getName() );
  }
  
  public void addService( final IGridService service ) {
    addService( service.getClass(), service.getURI() );
  }
  
  public void addService( final Class< ? extends IGridService > type,
                          final URI endpoint ) {
    
    String[] parameters = getParameters( VO_SERVICES );
    List< String > list
      = parameters != null
      ? Arrays.asList( parameters )
      : new ArrayList< String >();
    
    String entry = type.getName() + SERVICE_SEPARATOR + endpoint.toString();
    if ( ! list.contains( entry ) ) {
      list.add( entry );
      String[] array = list.toArray( new String[ list.size() ] );
      setParameters( VO_SERVICES, array );
    }
    
  }
  
  public String getName() {
    return getParameter( VO_NAME );
  }
  
  public String getType() {
    return getParameter( VO_TYPE );
  }
  
  public void setName( final String name ) {
    setParameter( VO_NAME, name );
  }
  
  public URI[] getServices( final Class< ? extends IGridService > type ) {
    
    List< URI > result = new ArrayList< URI >();
    
    String[] services = getParameters( VO_SERVICES );
    if ( services != null ) {
      for ( String service : services ) {
        int sep = service.indexOf( SERVICE_SEPARATOR );
        if ( sep > -1 ) {
          String t = service.substring( 0, sep );
          if ( type.getName().equals( t ) ) {
            try {
              result.add( new URI( service.substring( sep + 1 ) ) );
            } catch( URISyntaxException uriExc ) {
              Activator.logException( uriExc );
            }
          }
        }
      }
    }
    
    return result.toArray( new URI[ result.size() ] );
    
  }
  
  public IGridService[] getServices() {
    // TODO mathias implementation similar to GenericVoProperties#load
    return null;
  }
  
}
