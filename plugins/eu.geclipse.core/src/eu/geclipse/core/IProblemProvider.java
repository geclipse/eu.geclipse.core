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
 *    Pawel Wolniewicz, PSNC 
 *****************************************************************************/

package eu.geclipse.core;

/**
 * A problem provider maps unique IDs for problems to concrete implementations
 * of the {@link IProblem} interface. 
 */
public interface IProblemProvider {
  
  /**
   * Get a concrete implementation of the {@link IProblem} interface for the
   * specified unique ID and pass the specified exception to this problem.
   * The exception may also be <code>null</code>.
   * 
   * @param problemID The unique ID for which a problem should be created.
   * @param exc The exception that is the reason for the problem. May also be
   * <code>null</code>.
   * @return An implementation of the {@link IProblem} interface that
   * represents the specified problem ID.
   */
  public IProblem getProblem( final int problemID,
                              final Throwable exc );
  
}
