/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.auth;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import eu.geclipse.aws.internal.Activator;
import eu.geclipse.aws.internal.Messages;
import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.auth.AbstractAuthenticationToken;
import eu.geclipse.core.auth.AuthenticationException;
import eu.geclipse.core.auth.IAuthenticationTokenDescription;

/**
 * This {@link AWSAuthToken} class is able to provide detailed information
 * regarding the credentials. It acts as a container to hand around, providing
 * authorization credentials.
 * 
 * @author Moritz Post
 */
public class AWSAuthToken extends AbstractAuthenticationToken {

  /** The counter used to generate unique ids. */
  private static int awsAuthTokenCounter;

  /** The flag indicating the validity state of the token */
  private boolean isValid;

  /** The flag denoting the active state. */
  private boolean isActive;

  /** The id of this token. */
  private String tokenId;

  /**
   * Creates a new {@link AWSAuthToken} and generate a uniquely identifying id
   * for it.
   * 
   * @param description the {@link AWSAuthTokenDescription} to fill this token
   *          with credentials
   */
  public AWSAuthToken( final IAuthenticationTokenDescription description ) {
    super( description );
    this.tokenId = generateId();
    this.isActive = true;
  }

  public String getID() {
    return this.tokenId;
  }

  /**
   * Generates an Id used to display a unique identifier to the user.
   * 
   * @return the {@link String} identifying the {@link AWSAuthToken}
   */
  private String generateId() {
    AWSAuthTokenDescription description = ( AWSAuthTokenDescription )getDescription();
    String name = description.getTokenTypeName();

    String awsAccessId = null;
    if( description.getAwsAccessId() != null ) {
      awsAccessId = description.getAwsAccessId();
    }

    Integer counter = Integer.valueOf( ++AWSAuthToken.awsAuthTokenCounter );

    StringBuilder strBuilder = new StringBuilder( name );
    strBuilder.append( " # " + counter ); //$NON-NLS-1$
    if( awsAccessId != null ) {
      strBuilder.append( " @ " + awsAccessId ); //$NON-NLS-1$
    }

    return strBuilder.toString();
  }

  public long getTimeLeft() {
    return -1;
  }

  public boolean isActive() {
    return this.isActive;
  }

  public boolean isValid() {
    return this.isValid;
  }

  public void setActive( final boolean active, final IProgressMonitor monitor )
    throws AuthenticationException
  {
    // Check if the token is valid, otherwise throw an exception
    validate();
    if( !isValid() ) {
      throw new AuthenticationException( ICoreProblems.AUTH_TOKEN_NOT_YET_VALID,
                                         Activator.PLUGIN_ID );
    }

    // Check the description of this description for validity
    IAuthenticationTokenDescription aDesc = getDescription();
    if( !( aDesc instanceof AWSAuthTokenDescription ) ) {
      throw new AuthenticationException( ICoreProblems.AUTH_INVALID_TOKEN_DESCRIPTION,
                                         Activator.PLUGIN_ID );
    }

    this.isActive = active;
  }

  @Override
  public void setActive( final boolean active ) throws AuthenticationException {
    IProgressMonitor monitor = new NullProgressMonitor();
    setActive( active, monitor );
  }

  public void validate( IProgressMonitor monitor )
    throws AuthenticationException
  {
    if( monitor == null ) {
      monitor = new NullProgressMonitor();
    }
    monitor.beginTask( Messages.getString( "AWSAuthToken.validation_progress_title" ), 2 ); //$NON-NLS-1$

    // TODO validate token against one of the AWS services if desired

    monitor.worked( 1 );

    this.isValid = true;
    monitor.done();
  }

  @Override
  public void validate() throws AuthenticationException {
    IProgressMonitor monitor = new NullProgressMonitor();
    validate( monitor );
  }
}
