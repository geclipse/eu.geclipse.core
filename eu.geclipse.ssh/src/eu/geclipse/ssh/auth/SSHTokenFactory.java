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

package eu.geclipse.ssh.auth;

import org.eclipse.swt.widgets.Shell;

import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.auth.IAuthenticationTokenDescription;
import eu.geclipse.ssh.auth.dialogs.SSHTokenDialog;
import eu.geclipse.ui.IAuthTokenUIFactory;
import eu.geclipse.ui.dialogs.AuthTokenInfoDialog;

/**
 * SSH Token Factory
 */
public class SSHTokenFactory implements IAuthTokenUIFactory {

  public AuthTokenInfoDialog getInfoDialog( final IAuthenticationToken token,
                                            final Shell parentShell )
  {
    return new SSHTokenDialog( token, parentShell );
  }

  public IAuthenticationTokenDescription getSupportedDescription() {
    return new SSHTokenDescription( null, null, null, null, 0 );
  }
}
