/******************************************************************************
  * Copyright (c) 2006 g-Eclipse consortium
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License v1.0
  * which accompanies this distribution, and is available at
  * http://www.eclipse.org/legal/epl-v10.html
  *
  * Initialial development of the original code was made for
  * project g-Eclipse founded by European Union
  * project number: FP6-IST-034327  http://www.geclipse.eu/
  *
  * Contributor(s):
  *     FZK (http://www.fzk.de)
  *      - Mathias Stuempert (mathias.stuempert@iwr.fzk.de)
  *****************************************************************************/

package eu.geclipse.ui;

import org.eclipse.swt.widgets.Shell;
import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.auth.IAuthenticationTokenDescription;
import eu.geclipse.ui.dialogs.AuthTokenInfoDialog;

/**
 * An <code>IAuthTokenUIFactory</code> is responsible to provide
 * UI elements for handling a certain kind of authentication tokens.
 * 
 * @author stuempert-m
 */
public interface IAuthTokenUIFactory {
  
  /**
   * Get an info dialog that is used to present some more detailed
   * information about an authentication token of a specific type.
   * 
   * @param token The token from which to show the information.
   * @param parentShell The parent shell for the dialog.
   * @return An {@link AuthTokenInfoDialog} that is dedicated
   * to a certain type of authentication tokens.
   */
  public AuthTokenInfoDialog getInfoDialog( final IAuthenticationToken token, final Shell parentShell );
  
  /**
   * Get an {@link IAuthenticationTokenDescription} that represents the tokens
   * and descriptions that are supported by this factory.
   * 
   * @return An authentication token description describing the supported tokens.
   */
  public IAuthenticationTokenDescription getSupportedDescription(); 
  
}
