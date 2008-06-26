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

package eu.geclipse.core.model;

import java.net.URI;

import eu.geclipse.core.model.impl.GenericGridInstallParameters;

/**
 * The <code>IGridInstallParameters</code> holds all necessary information to 
 * install an application on the Grid. Middleware-specific implementations may
 * extends {@link GenericGridInstallParameters}.
 */
public interface IGridInstallParameters {

  /**
   * Get the sources for the installation. This array holds a list of
   * {@link URI}s pointing to files that are intended to be installed.
   * The type of these files depends on the middleware-specific
   * requirements of the application installation process.
   * 
   * @return The sources of the application installation, i.e. the
   * files that should be installed or that are needed for a
   * installation.
   */
  public URI[] getSources();
  
  /**
   * Get the targets of the installation, i.e. the computings where
   * the sources should be installed to.
   * 
   * @return The installation's targets.
   */
  public IGridComputing[] getTargets();
  
  /**
   * Get a tag that is used to refer to this installation afterwards.
   * This may not be used by the underlying installation process.
   * 
   * @return The installation's software tag.
   */
  public String getTag();
  
}
