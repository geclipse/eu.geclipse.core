/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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

package eu.geclipse.core.config;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import eu.geclipse.core.reporting.ProblemException;

/**
 * Base interface of the configuration framework. A configurator is used to
 * configure g-Eclipse for the use for a specific middleware or infrastructure.
 * This configuration can contain the import of trusted certificates, the
 * definition of one or more VOs and the generation of one or more grid
 * projects.
 */
public interface IConfigurator {
  
  /**
   * Start the configuration process.
   * 
   * @param monitor A progress monitor used to monitor the progress of the
   * configuration process.
   * @return An {@link IStatus} object containing status information about the
   * configuration process.
   * @throws ProblemException If the configuration failed for some reason.
   */
  public IStatus configure( final IProgressMonitor monitor ) throws ProblemException;
  
}
