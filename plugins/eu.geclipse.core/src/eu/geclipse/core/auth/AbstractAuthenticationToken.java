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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.auth;

import java.io.File;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * Abstract implementation of the {@link IAuthenticationToken} interface.
 * 
 * @author stuempert-m
 */
public abstract class AbstractAuthenticationToken implements IAuthenticationToken {
  
  /**
   * The token description that was used to initialise this token.
   */
  private IAuthenticationTokenDescription description;
  
  /**
   * Construct a new <code>AbstractAuthenticationToken</code> from the
   * specified {@link IAuthenticationTokenDescription}.
   * 
   * @param description The {@link IAuthenticationTokenDescription} from which
   *                    this token should be created.
   */
  public AbstractAuthenticationToken( final IAuthenticationTokenDescription description ) {
    this.description = description;
  }
  
  /**
   * Get the location in the file system where tokens are saved. Note
   * that only activated tokens are saved and that they are deleted
   * if they are deactivated.
   * 
   * @return An IPath pointing to the location in the file system
   * where all activated tokens are saved.
   */
  static public IPath getTokenLocation() {
    IPath location = eu.geclipse.core.internal.Activator.getDefault().getStateLocation();
    if ( !location.hasTrailingSeparator() ) {
      location = location.addTrailingSeparator();
    }
    location = location.append( ".tokens" ); //$NON-NLS-1$
    File file = location.toFile();
    if ( !file.exists() ) {
      file.mkdir();
    }
    return location;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthenticationToken#getDescription()
   */
  public IAuthenticationTokenDescription getDescription() {
    return this.description;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthenticationToken#setActive(boolean)
   */
  public void setActive( final boolean active ) throws AuthenticationException {
    IProgressMonitor monitor = new NullProgressMonitor();
    setActive( active, monitor );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthenticationToken#validate()
   */
  public void validate() throws AuthenticationException {
    IProgressMonitor monitor = new NullProgressMonitor();
    validate( monitor );
  }
  
  /**
   * This method notifies the token manager that this token has changed its state,
   * i.e. the token became in-/valid or in-/active. Subclasses of the
   * <code>AbstractAuthenticationToken</code> have to call this method whenever the
   * state of this token changes. 
   */
  protected void fireTokenStateChanged() {
    AuthenticationTokenManager.getManager().fireContentChanged();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthenticationToken#getTokenPath()
   */
  public IPath getTokenFile() {
    IPath path = getTokenLocation();
    path = path.addTrailingSeparator().append( getID() );
    return path;
  }
  
}
