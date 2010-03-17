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

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.auth.AbstractAuthenticationToken;

/**
 * SSH Token
 */
public class SSHToken extends AbstractAuthenticationToken {

  private boolean isActive;

  /**
   * Creates a new SSH Token
   * 
   * @param description
   */
  public SSHToken( final SSHTokenDescription description ) {
    super( description );
    setActive( true );
  }

  public String getID() {
    SSHTokenDescription description = ( SSHTokenDescription )getDescription();
    return "SSH Token (" + description.getUsername() + "@" + description.getHostname() + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
  }

  public long getTimeLeft() {
    return -1;
  }

  @Override
  public IPath getTokenFile() {
    return null;
  }

  public boolean isActive() {
    return this.isActive;
  }

  public boolean isValid() {
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void setActive( final boolean active ) {
    this.isActive = active;
    fireTokenStateChanged();
  }

  public void setActive( final boolean active, final IProgressMonitor progress )
  {
    setActive( active );
  }

  @Override
  public void validate() {
    // empty
  }

  public void validate( final IProgressMonitor progress ) {
    // empty
  }
}