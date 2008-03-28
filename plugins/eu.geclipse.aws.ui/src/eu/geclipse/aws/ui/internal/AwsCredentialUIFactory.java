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
 *    Thomas Koeckerbauer - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.ui.internal;

import org.eclipse.swt.widgets.Shell;
import eu.geclipse.aws.auth.AwsCredentialDescription;
import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.auth.IAuthenticationTokenDescription;
import eu.geclipse.ui.AbstractAuthTokenUIFactory;
import eu.geclipse.ui.dialogs.AuthTokenInfoDialog;

/**
 * This UI factory is dedicated to AWS credentials and provides the ui elements
 * that are dealing with AWS credentials.
 */
public class AwsCredentialUIFactory extends AbstractAuthTokenUIFactory {

  /* (non-Javadoc)
   * @see eu.geclipse.ui.IAuthTokenUIFactory#getInfoDialog(eu.geclipse.core.auth.IAuthenticationToken, org.eclipse.swt.widgets.Shell)
   */
  public AuthTokenInfoDialog getInfoDialog( final IAuthenticationToken token, final Shell parentShell ) {
    AuthTokenInfoDialog dialog = new AuthTokenInfoDialog( token, parentShell );
    return dialog;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.ui.IAuthTokenUIFactory#getSupportedDescription()
   */
  public IAuthenticationTokenDescription getSupportedDescription() {
    return new AwsCredentialDescription();
  }
}
