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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Interface for bidirectional connections.
 */
public interface IBidirectionalConnection {
  /**
   * Returns the InputStream of the connection.
   * @return the InputStream of the connection.
   * @throws IOException thrown if a connection error occurs.
   */
  public InputStream getInputStream() throws IOException;

  /**
   * Returns the OutputStream of the connection.
   * @return the OutputStream of the connection.
   * @throws IOException thrown if a connection error occurs.
   */
  public OutputStream getOutputStream() throws IOException;

  /**
   * Closes the connection.
   */
  public void close();
}
