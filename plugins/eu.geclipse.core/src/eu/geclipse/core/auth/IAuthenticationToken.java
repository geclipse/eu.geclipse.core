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

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * This interface is the base of the authentication/authorisation mechanisms
 * within g-Eclipse. It defines methods for activating and for validating
 * authentication tokens. An authentication token has an inner state. It can
 * be active or inactive. An active token is ready to be used for
 * authentication/authorisation. An inactive token holds the values (files,
 * CA locations) that are needed to activate this token.
 * 
 * @author stuempert-m
 */
public interface IAuthenticationToken {
  
  /**
   * Either activates or deactivates this token. Depending on the type of token
   * this method may open a connection to the Grid. If the activation procedure
   * is time consuming and/or consists of different independent steps use the
   * {@link #setActive(boolean, IProgressMonitor)} method instead. 
   * 
   * @param active If true the token will be activated, otherwise it will be deactivated.
   * @throws AuthenticationException If an error occures while activating this token.
   * @see #setActive(boolean, IProgressMonitor)
   */
  public void setActive( final boolean active ) throws AuthenticationException;
  
  /**
   * Either activates or deactivates this token. Depending on the type of token
   * this method may open a connection to the Grid.
   * 
   * @param active If true the token will be activated, otherwise it will be deactivated.
   * @param progress A progress monitor to show the progress of the activation procedure.
   * Normally this is not used when deactivating the token. If the activation procedure is
   * not time critical use {@link #setActive(boolean)} instead.
   * @throws AuthenticationException If an error occures while activating this token.
   * @see #setActive(boolean)
   */
  public void setActive( final boolean active, final IProgressMonitor progress ) throws AuthenticationException;
  
  /**
   * Tries to validate this token, i.e. reads the underlying files or tries to connect to
   * specified authentication authorities in order to verify the settings that are needed
   * to use/activate this token. Depending on the type of token this method may open a
   * connection to the Grid. After the token was successfully validated it should have an
   * unique ID.
   * 
   * @throws AuthenticationException If an error occures while validating this token.
   * @see #validate(IProgressMonitor)
   */
  public void validate() throws AuthenticationException;
  
  /**
   * Tries to validate this token, i.e. reads the underlying files or tries to connect to
   * specified authentication authorities in order to verify the settings that are needed
   * to use/activate this token. Depending on the type of token this method may open a
   * connection to the Grid. After the token was successfully validated it should have an
   * unique ID.
   * 
   * @param progress A progress monitor to show the progress of the validation procedure.
   * Normally this is not used when deactivating the token. If the activation procedure is
   * not time critical use {@link #validate()} instead.
   * @throws AuthenticationException If an error occures while validating this token.
   * @see #validate()
   */
  public void validate( final IProgressMonitor progress ) throws AuthenticationException;
  
  /**
   * Get the current state of this token, i.e. if this token is either active or
   * inactive.
   * 
   * @return True if the token is currently active.
   */
  public boolean isActive();
  
  /**
   * Returns if this token is currently valid. To validate an authentication token
   * use {@link #validate()}.
   * 
   * @return True if the token was already validated.
   */
  public boolean isValid();
  
  /**
   * Get the authentication token description that was used to create this token.
   * 
   * @return The description from which this token was created.
   */
  public IAuthenticationTokenDescription getDescription();
  
  /**
   * Get the remaining lifetime of this token or -1 if the token will never
   * expire or is inactive.
   * 
   * @return The remaining lifetime if this token is currently active and its
   * lifetime is limited. Otherwise this method returns -1.
   */
  public long getTimeLeft();
  
  /**
   * Get an ID that is used to identify this authentication token. The ID should be
   * generated from the validated information of this token. That also means that an
   * invalid token has no valid ID.
   * 
   * @return An identifier that is used to represent this token.
   * @see #validate()
   */
  public String getID();
  
  /**
   * Get the path to the file that contains the token. The token file is created
   * when the token is activated. Before the activation the path may point to
   * an invalid filename.
   *  
   * @return The path to the token file.
   */
  public IPath getTokenFile();
  
}
