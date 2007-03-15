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

import org.eclipse.core.runtime.CoreException;
import eu.geclipse.core.GridException;

/**
 * Initial implementation. Will soon be replaced or completely reimplemented.
 */
public class GridModelException extends GridException {

  private static final long serialVersionUID = 271834096070966136L;
  
  /**
   * Construct a new grid model exception from the specified
   * {@link CoreException}.
   */
  public GridModelException( final int problemID ) {
    super( problemID );
  }
  
  /**
   * Construct a new grid model exception from the specified
   * {@link IGridModelStatus}.
   */
  public GridModelException( final int problemID,
                             final Throwable exc ) {
    super( problemID, exc );
  }
  
}
