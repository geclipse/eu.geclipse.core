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
 *    Pawel Wolniewicz - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.model;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import eu.geclipse.core.GridException;


/**
 * TODO pawel
 */
public interface IGridJobID {

  /**
   * TODO pawel
   * @return TODO pawel
   */
  String getJobID();

  /**
   * @param jobFolder folder, in which Output folder will be created 
   * @param monitor the progress monitor
   * @return status
   * @throws GridException
   * @see IGridJobID#downloadOutputs
   */
  IStatus downloadOutputs(IFolder jobFolder, IProgressMonitor monitor) throws GridException;

}
