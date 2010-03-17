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

package eu.geclipse.ssh.wizard;

import org.eclipse.jface.wizard.Wizard;

import eu.geclipse.core.auth.AuthenticationException;
import eu.geclipse.core.auth.AuthenticationTokenManager;
import eu.geclipse.ssh.Activator;
import eu.geclipse.ssh.auth.SSHTokenDescription;
import eu.geclipse.ssh.wizard.pages.SSHTokenWizardPage;

/**
 * SSH Token Wizard
 */
public class SSHTokenWizard extends Wizard {

  SSHTokenWizardPage sshTokenWizardPage;

  /**
   * Creates a new SSH Token Wizard.
   */
  public SSHTokenWizard() {
    setWindowTitle( "Create a new SSH Token" ); //$NON-NLS-1$
  }

  @Override
  public void addPages() {
    this.sshTokenWizardPage = new SSHTokenWizardPage( "page0" ); //$NON-NLS-1$
    addPage( this.sshTokenWizardPage );
  }

  @Override
  public boolean performFinish() {
    final AuthenticationTokenManager authenticationTokenManager = AuthenticationTokenManager.getManager();
    final SSHTokenDescription description = this.sshTokenWizardPage.getTokenDescription();
    try {
      authenticationTokenManager.createToken( description );
    } catch( AuthenticationException authenticationException ) {
      Activator.logException( authenticationException );
    }
    return true;
  }
}
