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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.s3.internal.auth;

import org.eclipse.core.runtime.IProgressMonitor;
import org.jets3t.service.security.AWSCredentials;

import eu.geclipse.aws.s3.auth.AwsCredentialDescription;
import eu.geclipse.core.auth.AbstractAuthenticationToken;
import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.auth.IAuthenticationTokenDescription;

/**
 * Authentication token implementation for AWS credentials. 
 */
public class AwsCredential
    extends AbstractAuthenticationToken
    implements IAuthenticationToken {
  
  /**
   * Static variable that is used to make the credential ids unique.
   */
  private static int awsCredentialCounter = 0;
 
  /**
   * The credentials once they are loaded.
   */
  private AWSCredentials credentials;
  
  /**
   * The ID of this credential.
   */
  private String id;

  /**
   * Create a new <code>AwsCredential</code> from the specified
   * token description.
   * 
   * @param description The token description providing the parameters
   * of this credential.
   */
  public AwsCredential( final IAuthenticationTokenDescription description ) {
    super( description );
  }
  
  /**
   * Get the {@link AWSCredentials} corresponding to this token.
   * 
   * @return The {@link AWSCredentials} or <code>null</code> if
   * the token is not active. 
   */
  public AWSCredentials getCredentials() {
    return this.credentials;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthenticationToken#getID()
   */
  public String getID() {
    if ( this.id == null ) {
      this.id = createID();
    }
    return this.id;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthenticationToken#getTimeLeft()
   */
  public long getTimeLeft() {
    return -1;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthenticationToken#isActive()
   */
  public boolean isActive() {
    return this.credentials != null;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthenticationToken#isValid()
   */
  public boolean isValid() {
    return true;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthenticationToken#setActive(boolean, org.eclipse.core.runtime.IProgressMonitor)
   */
  public void setActive( final boolean active, final IProgressMonitor progress ) {
    
    if ( isActive() != active ) {
    
      if ( active ) {
        
        AwsCredentialDescription description
          = ( AwsCredentialDescription ) getDescription();
        
        String keyID = description.getAccessKeyID();
        String key = description.getSecretAccessKey();
        
        this.credentials = new AWSCredentials( keyID, key );
        
      } else {
        this.credentials = null;
      }
      
      fireTokenStateChanged();
      
    }

  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthenticationToken#validate(org.eclipse.core.runtime.IProgressMonitor)
   */
  public void validate( final IProgressMonitor progress ) {
    // AWS credentials do not have to be validated
  }
  
  /**
   * Create a new ID for this credential.
   * 
   * @return The credentials ID.
   */
  protected String createID() {
    IAuthenticationTokenDescription desc = getDescription();
    String name = desc.getTokenTypeName();
    Integer counter = Integer.valueOf( ++awsCredentialCounter );
    return String.format( "%1$s#%2$02d", name, counter ); //$NON-NLS-1$
  }

}
