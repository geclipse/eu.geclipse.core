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
 * Base interface for filesystem mounts.
 */
public interface IGridConnection
    extends IGridConnectionElement, IManageable, IStorableElement {
  
  /**
   * Get the {@link URI} of the filesystem.
   * 
   * @return The filesystem's {@link URI}.
   */
  public URI getURI();
  
  /**
   * Specifies if this connection is global. Global connections are
   * not part of a project and are therefore not located in the workspace.
   * The parent of a global connection is the connection manager itself.
   * 
   * @return True if this is a global connection.
   */
  public boolean isGlobal();
  
}
