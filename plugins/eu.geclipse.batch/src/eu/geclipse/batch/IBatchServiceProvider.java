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
 * A batch service provider maps unique IDs for service to concrete implementations
 * of the {@link IBatchService} interface. 
 */
public interface IBatchServiceProvider {

  /**
   * Get a concrete implementation of the {@link IBatchService} interface for the
   * specified unique ID.
   * 
   * @param serviceID The unique ID for which a batch service should be created.
   * @return An implementation of the {@link IBatchService} interface that
   * represents the specified service ID.
   */
  public IBatchService getService( final String serviceID );

}
