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

package eu.geclipse.core.reporting;

import java.lang.reflect.InvocationTargetException;

/**
 * The {@link ISolver} interface is part of the problem reporting
 * mechanism and may be used to define dedicated problem solving
 * strategies.
 */
public interface ISolver {

  /**
   * Try to solve an associated problem.
   * 
   * @throws InvocationTargetException If an exception occurs
   * during the attempt to solve the problem. The exception will be
   * wrapped by an {@link InvocationTargetException}.
   */
  public void solve() throws InvocationTargetException;
  
}
