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
 *    Mathias Stuempert - initial API and implementation
 *    Harald Gjermundrod - added the getHostName method
 *****************************************************************************/

package eu.geclipse.core.model;

import java.net.URI;

/**
 * Base interface for all grid elements that represents resources
 * on the Grid.
 */
public interface IGridResource extends IGridElement {
  
  /**
   * Get the <code>URI</code> that corresponds to this grid resource.
   * 
   * @return The resource's contact string. May be <code>null</code>
   * if the resource may not be represented by a <code>URI</code>.
   */
  public URI getURI();
  
  /**
   * Get the host name of the machine (physical or virtual) that represent
   * this resource. This name should either be the DNS entry or IP address
   * of this host. 
   *   
   * @return Returns the hostname of the machine, may be <code>null</code>
   * if the resource cannot provide its hostname.
   */
  public String getHostName();
}
