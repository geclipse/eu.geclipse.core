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

package eu.geclipse.terminal;

import java.io.IOException;
import eu.geclipse.core.IBidirectionalConnection;

/**
 * Interface to the terminal view, allows to add new terminal pages.
 */
public interface ITerminalView {

  /**
   * Creates a new terminal page which is connected to the specified
   * Input- and Output streams.
   * 
   * @param connection bidirectional connection from which the terminal reads
   *        the stream to print and which the keyboard input is sent to.
   * @param listener Instance of ITerminalListener, used for notification if
   *        the terminal properties changed.
   * @return the page containing the terminal.
   * @throws IOException thrown if a connection error occurs.
   */
  public abstract ITerminalPage addTerminal( final IBidirectionalConnection connection,
                                             final ITerminalListener listener ) throws IOException ;
}
