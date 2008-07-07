/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *    Pawel Wolniewicz - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.model;


/**
 * The job ID is (for each middleware) a unique identifier for a job running
 * on the Grid.
 */
public interface IGridJobID {

  /**
   * Gets the job's unique ID.
   * 
   * @return A string containing the unique identifier for a job. 
   */
  String getJobID();

}
