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

/**
 * Listener for changes of terminal properties.
 */
public interface ITerminalListener {
  /**
   * Window size changed.
   * 
   * @param cols new number of columns.
   * @param lines new number of lines.
   * @param xPixels new width in pixels.
   * @param yPixels new height in pixels.
   */
  void windowSizeChanged( final int cols, final int lines, final int xPixels, final int yPixels );

  /**
   * Window title changed. (Possible through xterm ESC sequence)
   * 
   * @param windowTitle new window title.
   */
  void windowTitleChanged( final String windowTitle );
  
  /**
   * Session terminated.
   */
  void terminated();
}
