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

package eu.geclipse.gvid;

import java.io.IOException;

import eu.geclipse.core.IBidirectionalConnection;

/**
 * Interface to the GVid view, allows to add new GVid pages.
 */
public interface IGVidView {
  /**
   * Adds a new GVid client tab to the GVid view.
   * @param connection bidirectional connection to read the videodata from and
   *                   write the input event data to.
   * @return the created GVid page.
   * @throws IOException thrown if a connection error occurs.
   */
  public GVidPage addGVidPage( final IBidirectionalConnection connection ) throws IOException;
}
