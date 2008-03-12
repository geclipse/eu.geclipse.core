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

package eu.geclipse.aws.internal;

import java.util.Hashtable;

import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;

import eu.geclipse.aws.auth.AwsCredentialDescription;
import eu.geclipse.aws.internal.auth.AwsCredential;
import eu.geclipse.aws.internal.s3.IS3Constants;
import eu.geclipse.core.auth.AbstractAuthTokenProvider;
import eu.geclipse.core.auth.AuthTokenRequest;
import eu.geclipse.core.auth.IAuthenticationTokenDescription;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Class that manages all instances of S3Services.
 */
public class ServiceRegistry {
  
  private static ServiceRegistry singleton;
  
  private Hashtable< String, S3Service > services;
  
  private ServiceRegistry() {
    this.services = new Hashtable< String, S3Service >();
  }
  
  /**
   * Get the singleton of this class.
   * 
   * @return The registry.
   */
  public static ServiceRegistry getRegistry() {
    if ( singleton == null ) {
      singleton = new ServiceRegistry();
    }
    return singleton;
  }
  
  /**
   * Clears the list of services.
   */
  public void clear() {
    this.services.clear();
  }
  
  /**
   * Get the service for accessing the account represented by the specified
   * access key ID. If the service is not yet in the cache it is created
   * and cached afterwards if <code>create</code> is <code>true</code>.
   * 
   * @param accessKeyID The access key ID representing the account.
   * @param create If <code>true</code> a new service will be created if none
   * could be found.
   * @return The service to make operations on the account with the specified
   * access key ID.
   * @throws ProblemException If the service was not yet in the cache and
   * its creation failed.
   */
  public S3Service getService( final String accessKeyID, final boolean create )
      throws ProblemException {
    
    S3Service service = this.services.get( accessKeyID );
    
    if ( ( service == null ) && create ) {
      service = createRestService( accessKeyID );
      if ( service != null ) {
        this.services.put( accessKeyID, service );
      }
    }
    
    return service;
    
  }
  
  /**
   * Create a service for accessing the account with the specified access
   * key ID.
   * 
   * @param accessKeyID The ID for accessing the account.
   * @return The newly created service.
   * @throws ProblemException If the service creation failed.
   */
  private static RestS3Service createRestService( final String accessKeyID )
      throws ProblemException {
    
    IAuthenticationTokenDescription description = new AwsCredentialDescription( accessKeyID );
    AuthTokenRequest request = new AuthTokenRequest(
        description,
        Messages.getString("ServiceRegistry.auth_requester"), //$NON-NLS-1$
        Messages.getString("ServiceRegistry.auth_request") ); //$NON-NLS-1$
    AwsCredential token
      = ( AwsCredential ) AbstractAuthTokenProvider.staticRequestToken( request );
    
    RestS3Service service = null;
    
    if ( token != null ) {
      try {
        service = new RestS3Service( token.getCredentials(), IS3Constants.APP_TAG, null );
      } catch ( S3ServiceException s3sExc ) {
        throw new ProblemException(
            IAwsProblems.REST_SERVICE_CREATION_FAILED,
            Activator.PLUGIN_ID );
      }
    }
    
    return service;
    
  }

}
