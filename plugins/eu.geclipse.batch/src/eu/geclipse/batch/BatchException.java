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

import eu.geclipse.core.GridException;

/**
 * This exception is thrown by methods dealing with batch services.
 * 
 * @author harald
 */
public class BatchException
    extends GridException {
  
  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -7949797013867988484L;

  /**
   * Create a new Batch Exception with the specified problem ID.
   * 
   * @param problemID The unique ID of the corresponding problem.
   */
  public BatchException( final int problemID ) {
    super( problemID );
  }
  
  /**
   * Create a new Batch Exception with the specified problem ID and
   * a string that contains the description.
   * 
   * @param problemID The unique ID of the corresponding problem.
   * @param description Description string of the problem
   */
  public BatchException( final int problemID,
                                  final String description ) {
    super( problemID, description );
  }

  /**
   * Create a new Batch Exception with the specified problem ID.
   * 
   * @param problemID The unique ID of the corresponding problem.
   * @param exc The exception that caused the problem.
   */
  public BatchException( final int problemID,
                                  final Throwable exc ) {
    super( problemID, exc );
  }

}
