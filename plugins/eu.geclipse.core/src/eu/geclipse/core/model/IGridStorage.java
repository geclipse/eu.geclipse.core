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

import java.net.URI;

/**
 * An {@link IGridResource} that represents storage elements in the
 * Grid.
 */
public interface IGridStorage
    extends IGridResource, IMountable, ITestable {
  
  /**
   * Get tokens that offer ways to access this storage in the form of
   * an {@link URI}. The scheme part is used to determine the protocol
   * to access the token with the help of the <code>EFS</code>
   * mechanisms.
   * 
   * @return Access tokens to access this storage.
   */
  public URI[] getAccessTokens();
  
}
