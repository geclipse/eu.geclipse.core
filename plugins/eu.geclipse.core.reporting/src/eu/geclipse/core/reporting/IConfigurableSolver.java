/*****************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse Consortium 
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

import org.eclipse.core.runtime.IExecutableExtension;

/**
 * A configurable solver is configurable in the sense of an
 * {@link IExecutableExtension}.
 */
public interface IConfigurableSolver
    extends ISolver, IExecutableExtension {

  // No further definitions needed
  
}
