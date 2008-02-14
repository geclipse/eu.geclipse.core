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
 *****************************************************************************/

package eu.geclipse.core.model;

import eu.geclipse.core.GridException;

/**
 * Grid exception that is specific for problems occurring in the model.
 */
public class GridModelException extends GridException {

  /**
   * The serial version UID.
   */
  private static final long serialVersionUID = 271834096070966136L;
  
  /**
   * Construct a new <code>GridModelException</code> with the specified
   * problem ID.
   * 
   * @param problemID The unique ID of the problem that occurred.
   */
  public GridModelException( final int problemID ) {
    super( problemID );
  }
  
  /**
   * Construct a new <code>GridModelException</code> with the specified
   * problem ID and a specific problem description.
   * 
   * @param problemID The unique ID of the problem that occurred.
   * @param description A more detailed description of the problem. 
   */
  public GridModelException( final int problemID,
                             final String description ) {
    super( problemID, description );
  }

  /**
   * Construct a new <code>GridModelException</code> with the specified
   * problem ID and an exception that caused this problem.
   * 
   * @param problemID The unique ID of the problem that occurred.
   * @param exc The exception that caused this problem. 
   */
  public GridModelException( final int problemID,
                             final Throwable exc ) {
    super( problemID, exc );
  }
  
  /**
   * Construct a new <code>GridModelException</code> with the specified
   * problem ID, an exception that caused this problem and a specific
   * problem description.
   * 
   * @param problemID The unique ID of the problem that occurred.
   * @param exc The exception that caused this problem.
   * @param description A more detailed description of the problem.
   */
  public GridModelException( final int problemID,
                             final Throwable exc,
                             final String description ) {
    super( problemID, exc, description );
  }
  
}
