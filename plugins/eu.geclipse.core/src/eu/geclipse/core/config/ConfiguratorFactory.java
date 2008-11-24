/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

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

/**
 * Factory class for {@link IConfigurator}s.
 */
public class ConfiguratorFactory {
  
  /**
   * Create an {@link IConfigurator} from the specified
   * {@link IConfigurationElement}. This element has to correspond to an
   * extension of the <code>eu.geclipse.core.configurator</code> extension
   * point.
   * 
   * @param element The configurator extension.
   * @return An {@link IConfiguration} object corresponding to the specified
   * extension.
   * @throws ProblemException If the configuration could not be parsed.
   */
  public static IConfigurator createConfigurator( final IConfigurationElement element )
      throws ProblemException {
    
    Configurator result = new Configurator();
    
    try { // TODO mathias fine-grained error handling
      
      IConfigurationElement[] certificates = element.getChildren( Extensions.CONFIG_CERTIFICATES_ELEMENT );
      
      if ( certificates != null ) {
        
        for ( IConfigurationElement cert : certificates ) {
          
          String loaderID = cert.getAttribute( Extensions.CONFIG_CERTIFICATES_LOADER_ID_ATTRIBUTE );
          ICertificateLoader certLoader = getCertificateLoader( loaderID );
          
          List< URI > uris = new ArrayList< URI >();
          IConfigurationElement[] loaders = cert.getChildren();
          
          for ( IConfigurationElement loader : loaders ) {
            
            String certURI = null;
            
            if ( loader.getName().equals( Extensions.CONFIG_CERTIFICATE_DISTRIBUTION_ELEMENT ) ) {
              String authorityID = loader.getAttribute( Extensions.CONFIG_CERTIFICATE_DISTRIBUTION_AUTHORITY_ID_ATTRIBUTE );
              String distributionID = loader.getAttribute( Extensions.CONFIG_CERTIFICATE_DISTRIBUTION_DISTRIBUTION_ID_ATTRIBUTE );
              certURI = getCertLoaderURI( loaderID, authorityID, distributionID );
            } else if ( loader.getName().equals( Extensions.CONFIG_CERTIFICATE_URL_ELEMENT ) ) {
              certURI = loader.getAttribute( Extensions.CONFIG_CERTIFICATE_URL_URL_ATTRIBUTE );
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
      
      IConfigurationElement[] vos = element.getChildren( Extensions.CONFIG_VO_ELEMENT );
      if ( vos != null ) {
        for ( IConfigurationElement vo : vos ) {
          String voName = vo.getAttribute( Extensions.CONFIG_VO_NAME_ATTRIBUTE );
          String creatorID = vo.getAttribute( Extensions.CONFIG_VO_CREATOR_ID_ATTRIBUTE );
          IConfigurableElementCreator voCreator = getVoCreator( creatorID );
          IConfigurationElement[] tokens = vo.getChildren();
          Configuration config = new Configuration();
          for ( IConfigurationElement token : tokens ) {
            String key = token.getAttribute( Extensions.CONFIG_VO_PARAMETER_KEY_ATTRIBUTE );
            IConfigurationElement[] values = token.getChildren( Extensions.CONFIG_PARAMETER_VALUE_ELEMENT );
            if ( ( values != null ) && ( values.length > 0 ) ) {
              String[] valueStrings = new String[ values.length ];
              for ( int i = 0 ; i < values.length ; i++ ) {
                valueStrings[ i ] = values[ i ].getAttribute( Extensions.CONFIG_PARAMETER_VALUE_ATTRIBUTE );
              }
              config.setParameters( key, valueStrings );
            }
          }
          result.addVoCreator( voCreator, voName, config );
        }
      }
      
      IConfigurationElement[] projects = element.getChildren( Extensions.CONFIG_PROJECT_ELEMENT );
      if ( projects != null ) {
        for ( IConfigurationElement project : projects ) {
          String projectName = project.getAttribute( Extensions.CONFIG_PROJECT_NAME_ATTRIBUTE );
          String voName = project.getAttribute( Extensions.CONFIG_PROJECT_VO_NAME_ATTRIBUTE );
          IConfigurationElement[] folderElements = project.getChildren();
          Hashtable< String, String > projectFolders = new Hashtable< String, String >();
          for ( IConfigurationElement folderElement : folderElements ) {
            String folderID = folderElement.getAttribute( Extensions.CONFIG_PROJECT_FOLDER_ID_ATTRIBUTE );
            String folderName = folderElement.getAttribute( Extensions.CONFIG_PROJECT_FOLDER_NAME_ATTRIBUTE );
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
