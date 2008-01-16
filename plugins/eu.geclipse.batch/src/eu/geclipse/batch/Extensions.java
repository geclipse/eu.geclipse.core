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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import eu.geclipse.core.ExtensionManager;

/**
 * This is a helper class that holds static fields and methods to
 * easily access extension of the g-Eclipse batch extension points.
 * 
 * @author harald
 */
public class Extensions {
  /**
   * The ID of the batch service extension point.
   */
  public static final String BATCH_SERVICE_POINT
    = "eu.geclipse.batch.batchService"; //$NON-NLS-1$
  
  /**
   * The ID of the batch service configuration element contained
   * in the batch service management extension point. 
   */
  public static final String BATCH_SERVICE_ELEMENT
    = "service"; //$NON-NLS-1$
  
  /**
   * The ID of the name attribute of the service element of the
   * batch service extension point.
   */
  public static final String BATCH_SERVICE_NAME_ATTRIBUTE
    = "name"; //$NON-NLS-1$
  
  /**
   * The ID of the executable extension of the batch service
   * provider element.
   */
  public static final String BATCH_SERVICE_PROVIDER_EXECUTABLE
    = "class"; //$NON-NLS-1$
  
  /**
   * The ID of the executable extension of the batch service description
   * configuration element.
   */
  public static final String BATCH_SERVICE_DESCRIPTION_EXECUTABLE
    = "description"; //$NON-NLS-1$
  
  /**
   * List that holds all known batch service descriptions.
   */
  private static List<IBatchServiceDescription> batchDescriptions;

  /**
   * List containing the names of all known batch services.
   */
  private static List<String> batchServiceNames;
  
  /**
   * Get a list with the names of all registered batch services.
   * The list will be sorted alphabetically.
   * 
   * @return A list containing the names of the types of all
   * currently available services.
   */
  public static List< String > getRegisteredBatchServiceNames() {
    if( batchServiceNames == null ) {
      List< String > resultList = new ArrayList< String >();
      ExtensionManager manager = new ExtensionManager();
      List< IConfigurationElement > cElements
        = manager.getConfigurationElements( BATCH_SERVICE_POINT,
                                            BATCH_SERVICE_ELEMENT );
      for ( IConfigurationElement element : cElements ) {
        String name = element.getAttribute( BATCH_SERVICE_NAME_ATTRIBUTE );
        if ( name != null ) {
          resultList.add( name );
        }
      }
      Collections.sort( resultList );
      batchServiceNames = resultList;
    }
    return batchServiceNames;
  }
  
  /**
   * Get a list of all currently registered batch service
   * description.
   * 
   * @return A list containing instances of all currently registered
   * extensions of the batch service description
   * configuration elements.
   */
  public static List< IBatchServiceDescription > getRegisteredBatchServiceDescriptions() {
    if( batchDescriptions == null ) {
      List< IBatchServiceDescription > resultList
      = new ArrayList< IBatchServiceDescription >();
      ExtensionManager manager = new ExtensionManager();
      List< Object > objectList
        = manager.getExecutableExtensions( BATCH_SERVICE_POINT,
                                           BATCH_SERVICE_ELEMENT,
                                           BATCH_SERVICE_DESCRIPTION_EXECUTABLE );
      for ( Object o : objectList ) {
        if ( o instanceof IBatchServiceDescription ) {
          resultList.add( ( IBatchServiceDescription ) o );
        }
      }
      batchDescriptions = resultList;
    }
    return batchDescriptions;
  }
}
