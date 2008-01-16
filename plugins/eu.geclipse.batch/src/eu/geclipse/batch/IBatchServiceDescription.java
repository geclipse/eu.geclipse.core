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

/**
 * This interface is the base for all classes that describe specialized
 * batch services like pbs. 
 * 
 * @author harald
 */

public interface IBatchServiceDescription { 

  /**
   * Create a service from this <code>IBatchServiceDescription</code>.
   *
   * @param name batch service name, i.e. the configuration file that were 
   *             used to instantiate this service
   * @return A new batch service that is created from the settings
   *         of this <code>IBatchServiceDescription</code>.
   * @throws BatchException If the service could not be created due
   *                        to some error.
   */
  public IBatchService createService( final String name ) throws BatchException;
  
  /**
   * Get the name of the service type that is described by this description. This
   * is a short name like "PBS" or "LSF" that is used in UI
   * components to reference this description.
   * 
   * @return The name of the type of service that is described by this description.
   */
  public String getServiceTypeName();
  
  /**
   * Returns whether this implementation can support the requested service. 
   *  
   * @param service The service to test for
   * @return Returns <code>true</code> if this implementation supports the service, 
   * <code>false</code> otherwise. 
   */
  public boolean supportsService( final String service );
}