/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *   nloulloud
 *****************************************************************************/
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

/**
 * This class is responsible to query the extension point registry in order to
 * get a specific information service or all the existing ones.
 * @author tnikos
 *
 */
public class InfoServiceFactory {
  
  /**
   * Returns an information service for a specific VO type.
   * @param voType The type of the VO. It can be "GRIA VO" or "Voms VO"
   * @param vo The vo to pass to the information service
   * @return An infoservice implementing IExtentedGridInfoService interface or null
   */
  public static IExtentedGridInfoService getInfoService(final String voType, final IVirtualOrganization vo)
  {
    IExtentedGridInfoService infoService = null, tempInfoService = null;
    ArrayList<IExtentedGridInfoService> infoServicesArray = getAllExistingInfoService();
    
    for (int i=0; infoService == null && i<infoServicesArray.size(); i++)
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
  
  /**
   * Returns all the existing information services.
   * @return An array with all the information services or an empty array if 
   * no information services that extend the extension point "eu.geclipse.info.infoService"
   * exist.
   */
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
          // do nothing
        }
      }
    }
    
    return infoServiceArray;
  }
}
