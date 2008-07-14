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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch;

import java.util.Collection;

import eu.geclipse.core.model.IGridContainer;


/**
 * A Grid element that describes configuration parameters for a Queue on Grid Site.
 * Such descriptors are used to create Queue configurations.
 *
 */
public interface IGridBatchQueueDescription extends IGridContainer {
  
  /**
   * @return Queue Name
   */
  String getQueueName();
  
  /**
   * @return Queue Type
   */
  String getQueueType();
  
  /**
   * @return Queue Status
   */
  String getQueueStatus();
  
  /**
   * @return The list of Virtual Organizations that are allowed to use the Queue. 
   */
  Collection<String> getAllowedVirtualOrganizations();  
  
}
