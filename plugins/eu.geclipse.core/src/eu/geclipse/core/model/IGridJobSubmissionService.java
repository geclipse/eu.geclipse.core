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
 *    Pawel Wolniewicz
 *****************************************************************************/

package eu.geclipse.core.model;

import eu.geclipse.core.GridException;

/**
 * TODO pawel
 */
public interface IGridJobSubmissionService extends IGridService{

  /**
   * TODO pawel
   * 
   * @param parent TODO pawel
   * @return TODO pawel
   * @throws GridException TODO pawel
   */
  public IGridJobID submitJob( final IGridJobDescription parent ) throws GridException;

//  /**
//   * TODO pawel
//   * 
//   * @param parent TODO pawel
//   * @param destination TODO pawel
//   * @return TODO pawel
//   * @throws GridModelException TODO pawel
//   */
//  public IGridJobID submitJob( final IGridJobDescription parent, final String destination ) throws GridException;
  
}
