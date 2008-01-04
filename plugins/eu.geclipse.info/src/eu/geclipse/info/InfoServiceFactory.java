package eu.geclipse.info;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.info.model.IExtentedGridInfoService;


public class InfoServiceFactory {
  
  public static IExtentedGridInfoService getInfoService(final String voType, final IVirtualOrganization vo)
  {
    IExtentedGridInfoService infoService = null, tempInfoService = null;;
    ArrayList<IExtentedGridInfoService> infoServicesArray = getAllExistingInfoService();
    
    for (int i=0; i<infoServicesArray.size(); i++)
    {  
      tempInfoService = infoServicesArray.get( i );
      if (voType.equals( tempInfoService.getVoType() ))
      {
        infoService = infoServicesArray.get( i );
        infoService.setVO( vo );
      }
    }
    
    return infoService;
  }
  
  public static ArrayList<IExtentedGridInfoService> getAllExistingInfoService()
  {
    ArrayList<IExtentedGridInfoService> infoServiceArray = new ArrayList<IExtentedGridInfoService>();
    IExtentedGridInfoService infoService = null;
    IExtensionRegistry myRegistry = Platform.getExtensionRegistry();
    IExtensionPoint extensionPoint = myRegistry.getExtensionPoint( "eu.geclipse.info.infoService"); //$NON-NLS-1$
    IExtension[] extensions = extensionPoint.getExtensions();
    
    for (int i = 0; i < extensions.length; i++)
    {
      IExtension extension = extensions[i];
 
      IConfigurationElement[] elements = extension.getConfigurationElements();
      for( IConfigurationElement element : elements ) {
        
        try {
          infoService = (IExtentedGridInfoService) element.createExecutableExtension( "class" ); //$NON-NLS-1$
          if (infoService != null)
          {
            infoServiceArray.add( infoService );
          }
        } catch( CoreException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    
    return infoServiceArray;
  }
}
