package eu.geclipse.core.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.IConfigurableElementCreator;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.security.ICertificateLoader;


public class ConfiguratorFactory {
  
  public static IConfigurator createConfigurator( final IConfigurationElement element )
      throws ProblemException {
    
    Configurator result = new Configurator();
    
    try { // TODO mathias fine-grained error handling
      
      IConfigurationElement[] certificates = element.getChildren( "certificates" );
      
      if ( certificates != null ) {
        
        for ( IConfigurationElement cert : certificates ) {
          
          String loaderID = cert.getAttribute( "loaderID" );
          ICertificateLoader certLoader = getCertificateLoader( loaderID );
          
          List< URI > uris = new ArrayList< URI >();
          IConfigurationElement[] loaders = cert.getChildren();
          
          for ( IConfigurationElement loader : loaders ) {
            
            String certURI = null;
            
            if ( loader.getName().equals( "certificateDistribution" ) ) {
              String authorityID = loader.getAttribute( "authorityID" );
              String distributionID = loader.getAttribute( "distributionID" );
              certURI = getCertLoaderURI( loaderID, authorityID, distributionID );
            } else if ( loader.getName().equals( "certificateURL" ) ) {
              certURI = loader.getAttribute( "url" );
            }
            
            if ( certURI != null ) {
              try {
                URI uri = new URI( certURI );
                uris.add( uri );
              } catch ( URISyntaxException uriExc ) {
                // TODO mathias proper error handling, for the moment just log this one
                Activator.logException( uriExc );
              }
            }
            
          }
        
          result.addCertificateLoader( certLoader, uris.toArray( new URI[ uris.size() ] ) );
          
        }
      
      }
      
      IConfigurationElement[] vos = element.getChildren( "vo" );
      if ( vos != null ) {
        for ( IConfigurationElement vo : vos ) {
          String voName = vo.getAttribute( "voName" );
          String creatorID = vo.getAttribute( "creatorID" );
          IConfigurableElementCreator voCreator = getVoCreator( creatorID );
          IConfigurationElement[] tokens = vo.getChildren();
          Configuration config = new Configuration();
          for ( IConfigurationElement token : tokens ) {
            String key = token.getAttribute( "key" );
            IConfigurationElement[] values = token.getChildren( "parameterValue" );
            if ( ( values != null ) && ( values.length > 0 ) ) {
              String[] valueStrings = new String[ values.length ];
              for ( int i = 0 ; i < values.length ; i++ ) {
                valueStrings[ i ] = values[ i ].getAttribute( "value" );
              }
              config.setParameters( key, valueStrings );
            }
          }
          result.addVoCreator( voCreator, voName, config );
        }
      }
      
      IConfigurationElement[] projects = element.getChildren( "project" );
      if ( projects != null ) {
        for ( IConfigurationElement project : projects ) {
          String projectName = project.getAttribute( "projectName" );
          String voName = project.getAttribute( "voName" );
          IConfigurationElement[] folderElements = project.getChildren();
          Hashtable< String, String > projectFolders = new Hashtable< String, String >();
          for ( IConfigurationElement folderElement : folderElements ) {
            String folderID = folderElement.getAttribute( "folderID" );
            String folderName = folderElement.getAttribute( "folderName" );
            projectFolders.put( folderID, folderName );
          }
          result.addProjectConfiguration( projectName, voName, projectFolders );
        }
      }
      
    } catch ( ProblemException pExc ) {
      throw pExc;
    } catch ( CoreException cExc ) {
      throw new ProblemException( cExc.getStatus() );
    }
    
    return result;
    
  }
  
  private static ICertificateLoader getCertificateLoader( final String loaderID )
      throws CoreException {
    
    ICertificateLoader result = null;
    
    ExtensionManager manager = new ExtensionManager();
    List< IConfigurationElement > loaders
      = manager.getConfigurationElements( Extensions.CERT_LOADER_POINT, Extensions.CERT_LOADER_ELEMENT );
    
    for ( IConfigurationElement loader : loaders ) {
      if ( loader.getAttribute( Extensions.CERT_LOADER_ID_ATTRIBUTE ).equals( loaderID ) ) {
        result = ( ICertificateLoader ) loader.createExecutableExtension( Extensions.CERT_LOADER_CLASS_ATTRIBUTE );
        break;
      }
    }
    
    return result;
    
  }
  
  private static IConfigurableElementCreator getVoCreator( final String creatorID )
      throws CoreException {

    IConfigurableElementCreator result = null;

    ExtensionManager manager = new ExtensionManager();
    List< IConfigurationElement > loaders
      = manager.getConfigurationElements( Extensions.GRID_ELEMENT_CREATOR_POINT, Extensions.GRID_ELEMENT_CREATOR_ELEMENT );

    for ( IConfigurationElement loader : loaders ) {
      if ( loader.getAttribute( Extensions.GRID_ELEMENT_CREATOR_ID_ATTRIBUTE ).equals( creatorID ) ) {
        try {
          result = ( IConfigurableElementCreator ) loader.createExecutableExtension( Extensions.CERT_LOADER_CLASS_ATTRIBUTE );
        } catch ( ClassCastException ccExc ) {
          throw new ProblemException( ICoreProblems.CONFIG_WRONG_VO_CREATOR, ccExc, Activator.PLUGIN_ID );
        }
        break;
      }
    }

    return result;

  }
  
  private static String getCertLoaderURI( final String loaderID,
                                          final String authorityID,
                                          final String distributionID ) {
    
    String result = null;
    
    ExtensionManager manager = new ExtensionManager();
    List< IConfigurationElement > loaders
      = manager.getConfigurationElements( Extensions.CERT_LOADER_POINT, Extensions.CERT_LOADER_ELEMENT );
    
    for ( IConfigurationElement loader : loaders ) {
      
      if ( loader.getAttribute( Extensions.CERT_LOADER_ID_ATTRIBUTE ).equals( loaderID ) ) {
        
        IConfigurationElement[] authorities = loader.getChildren();
        
        for ( IConfigurationElement authority : authorities ) {
          
          if ( authority.getAttribute( Extensions.CERT_LOADER_AUTHORITY_ID_ATTRIBUTE  ).equals( authorityID ) ) {
            
            IConfigurationElement[] distros = authority.getChildren();
            
            for ( IConfigurationElement distro : distros ) {
              
              if ( distro.getAttribute( Extensions.CERT_LOADER_DISTRIBUTION_ID_ATTRIBUTE ).equals( distributionID ) ) {
                
                result = distro.getAttribute( Extensions.CERT_LOADER_DISTRIBUTION_URL_ATTRIBUTE );
                break;
                
              }
              
            }
            
            break;
            
          }
          
        }
        
        break;
        
      }
      
    }
    
    return result;
    
  }
  
}
