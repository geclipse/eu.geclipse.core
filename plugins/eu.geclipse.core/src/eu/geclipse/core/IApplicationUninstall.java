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
 *    Jie Tao
 *****************************************************************************/

package eu.geclipse.core;

import java.net.URI;

import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.model.IGridElement;


/** This method is used to uninstall the applications 
   * which are installed on the CE via ApplicationDeploy
 * @author tao-j
 *
 */

public interface IApplicationUninstall {
  /**
   * @param deploySource
   * @param deployTarget
   * @param deploytags
   * @param monitor
   */
  public void uninstall( final URI[] deploySource, 
                      final IGridElement deployTarget, 
                      final String[] deploytags, 
                      final IProgressMonitor monitor);
  
}
