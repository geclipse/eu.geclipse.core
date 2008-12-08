/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.servicejob.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import eu.geclipse.core.model.IServiceJob;
import eu.geclipse.servicejob.Activator;

/**
 * Class for easily accessing extensions of servicejobProvider extension point.
 */
public class Extensions {

  /**
   * Name of the service job element.
   */
  public static final String SERVICE_JOB_ELEMENT = "serviceJob"; //$NON-NLS-1$
  
  /**
   * Service job's name attribute element.
   */
  public static final String SERVICEJOB_NAME_ATTRIBUTE = "name"; //$NON-NLS-1$
  
  /**
   * ID of the Service Job Provider extension point.
   */
  public static final String SERVICEJOB_PROVIDER_EP_ID 
    = "eu.geclipse.servicejob.servicejobProvider"; //$NON-NLS-1$
  
  /**
   * Name of the wizard node attribute.
   */
  public static final String SERVICE_JOB_WIZARD_NODE_ATTRIBUTE = "class"; //$NON-NLS-1$
  private static final String SERVICE_JOB_ID_ATTRIBUTE = "id"; //$NON-NLS-1$
  private static final String SERVICE_JOB_CLASS = "servicejobClass"; //$NON-NLS-1$

  /**
   * Finds and creates instance of {@link IServiceJob} declared by contributor's
   * plug-in.
   * 
   * @param servicejobExtensionId id of contributor's plug-in
   * @return instance of {@link IServiceJob} used by contributor's plug-in or
   *         <code>null</code> if this class/plug-in cannot be found or
   *         created
   */
  public static IServiceJob getServiceJobInstance( final String servicejobExtensionId ) {
    IServiceJob result = null;
    IConfigurationElement[] elements = getElementsOfServiceJobProvider( servicejobExtensionId );
    for( IConfigurationElement element : elements ) {
      if( element.getName().equals( SERVICE_JOB_ELEMENT ) ) {
        try {
          result = ( IServiceJob )element.createExecutableExtension( SERVICE_JOB_CLASS );
        } catch( CoreException coreExc ) {
          Activator.logException( coreExc );
        }
      }
    }
    return result;
  }

  private static List<IExtension> getExtensions( final String extensionPointId )
  {
    List<IExtension> result = new ArrayList<IExtension>();
    IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
      .getExtensionPoint( extensionPointId );
    for( IExtension extension : extensionPoint.getExtensions() ) {
      result.add( extension );
    }
    return result;
  }

  private static IConfigurationElement[] getElementsOfServiceJobProvider( final String serviceJobId )
  {
    List<IConfigurationElement> result = new ArrayList<IConfigurationElement>();
    for( IExtension extension : getExtensions( Extensions.SERVICEJOB_PROVIDER_EP_ID ) )
    {
      for( IConfigurationElement confElement : extension.getConfigurationElements() )
      {
        if( confElement.getAttribute( SERVICE_JOB_ID_ATTRIBUTE )
          .equalsIgnoreCase( serviceJobId ) )
        {
          result.add( confElement );
        }
      }
    }
    IConfigurationElement[] result1 = new IConfigurationElement[ result.size() ];
    return result.toArray( result1 );
  }
}
