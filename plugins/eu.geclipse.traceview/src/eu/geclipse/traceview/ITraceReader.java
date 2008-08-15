/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
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
 *    Christof Klausecker GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.traceview;

import java.io.IOException;

import org.eclipse.core.runtime.IPath;

/**
 * Interface for trace readers.
 */
public interface ITraceReader {

  /**
   * Opens a trace.
   * 
   * @param tracePath path of the trace to open
   * @return the opened trace
   * @throws IOException thrown if opening the trace fails
   */
  ITrace openTrace( IPath tracePath ) throws IOException;
}
