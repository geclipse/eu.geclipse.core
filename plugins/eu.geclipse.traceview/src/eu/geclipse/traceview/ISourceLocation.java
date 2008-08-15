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

/**
 * Interface for the source location of an event
 */
public interface ISourceLocation {

  /**
   * Returns the source filename
   * 
   * @return source filename
   */
  public String getSourceFilename();

  /**
   * Returns the source line on which the event happened.
   * 
   * @return source line
   */
  public int getSourceLineNumber();

  /**
   * Returns the number of times the source line has been hit up to this event.
   * 
   * @return occurrence count
   */
  public int getOccurrenceCount();
}
