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
 *     Nikolaos Tsioutsias - University of Cyprus
 *****************************************************************************/
package eu.geclipse.info.model;


/**
 * @author tnikos
 *
 */
public interface IGridSupportedService {
  
  /**
   * Returns the service type that is supported
   * @return A string with the type of the service
   */
  public String getType();
  
  /**
   * Gets the versions of the service type that are supported. If null is returned it is
   * be ignored.
   * @return An array of String
   */
  public String[] getVersion();
}
