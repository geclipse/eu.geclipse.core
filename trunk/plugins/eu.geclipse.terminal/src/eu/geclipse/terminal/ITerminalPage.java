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

import org.eclipse.swt.graphics.Font;

/**
 * Interface to the page containing the terminal widget.
 */
public interface ITerminalPage {
  /**
   * Sets the name of the terminal page's tab.
   * 
   * @param name new name.
   */
  public abstract void setTabName( final String name );

  /**
   * Returns the name of the terminal page's tab.
   * 
   * @return the name of the terminal page's tab.
   */
  public abstract String getTabName();

  /**
   * Sets the description label above the terminal widget.
   * (For example for the terminal window title or the hostname)
   * 
   * @param desc text to display.
   */
  public abstract void setDescription( final String desc );

  /**
   * Returns the current text in the description label above the terminal
   * widget.
   * 
   * @return current text of the description label.
   */
  public abstract String getDescription();

  /**
   * Sets the font of the terminal emulator.
   * @param font font of the terminal emulator.
   */
  public abstract void setFont( final Font font );

  /**
   * Returns the current font of the terminal emulator.
   * @return current font of the terminal emulator.
   */
  public abstract Font getFont();
}
