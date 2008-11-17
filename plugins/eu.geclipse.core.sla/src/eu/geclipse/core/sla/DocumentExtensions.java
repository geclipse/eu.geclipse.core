/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *      NEC Laboratories Europe 
 *      - Harald Kornmayer (harald.kornmayer@it.neclab.eu)
 *
 *****************************************************************************/
package eu.geclipse.core.sla;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.reporting.ProblemException;

/**
 * This is a helper class that holds static fields and methods to easily access
 * extension of the g-Eclipse sla document extension points.
 * 
 * @author harald
 */
public class DocumentExtensions {

  /**
   * The ID of the sla service extension point.
   */
  public static final String SLA_SERVICE_POINT = "eu.geclipse.core.sla.slaDocumentService"; //$NON-NLS-1$
  /**
   * The ID of the sla service configuration element contained in the sla
   * service management extension point.
   */
  public static final String SLA_SERVICE_ELEMENT = "Service"; //$NON-NLS-1$
  /**
   * The ID of the name attribute of the service element of the sla service
   * extension point.
   */
  public static final String SLA_SERVICE_NAME_ATTRIBUTE = "name"; //$NON-NLS-1$
  /**
   * The ID of the executable extension of the sla service provider element.
   */
  public static final String SLA_SERVICE_PROVIDER_EXECUTABLE = "class"; //$NON-NLS-1$
  /**
   * The ID of the sla service extension point.
   */
  public static final String SLA_SERVICE_SELECTOR_POINT = "eu.geclipse.core.sla.slaDocumentServiceSelector"; //$NON-NLS-1$
  /**
   * The ID of the sla service selector configuration element contained in the
   * sla service selector management extension point.
   */
  public static final String SLA_SERVICE_SELECTOR_ELEMENT = "serviceselector"; //$NON-NLS-1$
  /**
   * The ID of the name attribute of the service selector element of the sla
   * service selector extension point.
   */
  public static final String SLA_SERVICE_SELECTOR_NAME_ATTRIBUTE = "name"; //$NON-NLS-1$
  /**
   * The ID of the executable extension of the sla service selector provider
   * element.
   */
  public static final String SLA_SERVICE_SELECTOR_PROVIDER_EXECUTABLE = "class"; //$NON-NLS-1$
  /**
   * List containing the names of all known sla services.
   */
  private static List<String> slaServiceNames;

  /**
   * Get a list with the names of all registered sla services. The list will be
   * sorted alphabetically.
   * 
   * @return A list containing the names of the types of all currently available
   *         services.
   */
  public static List<String> getRegisteredSlaServiceNames() {
    if( slaServiceNames == null ) {
      List<String> resultList = new ArrayList<String>();
      ExtensionManager manager = new ExtensionManager();
      List<IConfigurationElement> cElements = manager.getConfigurationElements( SLA_SERVICE_POINT,
                                                                                SLA_SERVICE_ELEMENT );
      for( IConfigurationElement element : cElements ) {
        String name = element.getAttribute( SLA_SERVICE_NAME_ATTRIBUTE );
        if( name != null ) {
          resultList.add( name );
        }
      }
      Collections.sort( resultList );
      slaServiceNames = resultList;
    }
    return slaServiceNames;
  }

  /**
   * returns the number of registered SLAServices in the Extension point
   * registry
   * 
   * @return number of registered SLAServices
   */
  public static int getNumberRegisteredSlaServices() {
    ExtensionManager manager = new ExtensionManager();
    List<IConfigurationElement> cElements = manager.getConfigurationElements( SLA_SERVICE_POINT,
                                                                              SLA_SERVICE_ELEMENT );
    return cElements.size();
  }

  /**
   * returns one SLAService implementation from all registered services. If
   * there are more than one services registered, a GUI pops up and one of the
   * registred SLAService implementations must be selected.
   * 
   * @return service
   * @throws ProblemException 
   */
  public static ISLADocumentService getSlaServiceImpl() throws ProblemException
  {
    Object[] result = null;
    // select the SLA Service implementation to be used
    int nbRegService = DocumentExtensions.getNumberRegisteredSlaServices();
    nbRegService = 0;
    if( nbRegService == 0 ) {
      throw new ProblemException( ISLAProblems.MISSING_DOCUMENT_SERVICE,
                                  Activator.PLUGIN_ID );
    } else if( nbRegService == 1 ) // if only on SLAService is supported
    {
      result = DocumentExtensions.getSlaServiceName( nbRegService - 1 )
        .toArray();
    } else // there are more than one services, select one
    {
      ISLADocumentServiceSelector selector = DocumentExtensions.getSlaServiceSelector();
      result = selector.selectSLAImplementation();
    }
    String serviceName = ( String )result[ 0 ];
    ISLADocumentService service = DocumentExtensions.getSLAserviceFromString( serviceName );
    return service;
  }

  private static ISLADocumentService getSLAserviceFromString( final String result )
  {
    ISLADocumentService slaService = null;
    IExtensionRegistry reg = Platform.getExtensionRegistry();
    IConfigurationElement[] extensions = reg.getConfigurationElementsFor( DocumentExtensions.SLA_SERVICE_POINT );
    for( int i = 0; i < extensions.length; i++ ) {
      IConfigurationElement element = extensions[ i ];
      String name = element.getAttribute( DocumentExtensions.SLA_SERVICE_NAME_ATTRIBUTE );
      if( result.equals( name ) ) {
        try {
          slaService = ( ISLADocumentService )element.createExecutableExtension( DocumentExtensions.SLA_SERVICE_PROVIDER_EXECUTABLE );
        } catch( CoreException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    return slaService;
  }

  /**
   * returns the i-th SLA service name from the list of registered SLAService
   * implementations
   * 
   * @param i
   * @return resultList
   */
  public static List<String> getSlaServiceName( final int i ) {
    getRegisteredSlaServiceNames();
    List<String> resultList = new ArrayList<String>();
    resultList.add( slaServiceNames.get( i ) );
    return resultList;
  }

  /**
   * use the extension point SLA_SERVICE_SELECTOR_POINT in order to separate the
   * GUI component from the core component of the SLA package.
   * 
   * @return the last implementation of the SLA_SERVICE_SELECTOR_POINT found in
   *         the Extension registry.
   */
  public static ISLADocumentServiceSelector getSlaServiceSelector() {
    ISLADocumentServiceSelector result = null;
    IExtensionRegistry reg = Platform.getExtensionRegistry();
    IConfigurationElement[] extensions = reg.getConfigurationElementsFor( DocumentExtensions.SLA_SERVICE_SELECTOR_POINT );
    for( int i = 0; i < extensions.length; i++ ) {
      IConfigurationElement element = extensions[ i ];
      try {
        element.createExecutableExtension( DocumentExtensions.SLA_SERVICE_SELECTOR_PROVIDER_EXECUTABLE );
        result = ( ISLADocumentServiceSelector )element.createExecutableExtension( DocumentExtensions.SLA_SERVICE_SELECTOR_PROVIDER_EXECUTABLE );
      } catch( CoreException e ) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch( Exception e ) {
        e.printStackTrace();
      }
    }
    return result;
  }
}
