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
 *    Yifan Zhou
 *****************************************************************************/

package eu.geclipse.core;

import java.net.URI;

import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.model.IGridElement;


/**
 * An interface for the ApplicationDeployment extension point.
 */
public interface IApplicationDeployment {
  
  /**
   * This method is used to deploy the source element to the target.
   *
   * @param deploySource
   * @param deployTarget
   * @param deploytag
   * @param monitor
   */
  public void deploy( final URI[] deploySource, 
                      final IGridElement[] deployTarget, 
                      final String deploytag, 
                      final IProgressMonitor monitor);
  
}
