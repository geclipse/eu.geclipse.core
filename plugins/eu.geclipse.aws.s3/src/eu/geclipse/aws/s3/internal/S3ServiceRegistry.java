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
 *    Moritz Post - Changed the service mapping from awsAccessId/service
 *                  to awsAuthToken/service. Improved singleton pattern.
 */

package eu.geclipse.aws.s3.internal;

import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.security.AWSCredentials;

import eu.geclipse.aws.auth.AWSAuthToken;
import eu.geclipse.aws.auth.AWSAuthTokenDescription;
import eu.geclipse.aws.s3.IS3Constants;
import eu.geclipse.aws.s3.IS3Problems;
import eu.geclipse.core.auth.AbstractAuthTokenProvider;
import eu.geclipse.core.auth.AuthTokenRequest;
import eu.geclipse.core.auth.AuthenticationTokenManager;
import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.auth.ISecurityManager;
import eu.geclipse.core.auth.ISecurityManagerListener;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Class that manages all instances of S3Services.
 */
public class S3ServiceRegistry implements ISecurityManagerListener {

  /**
   * The SingletonHolder is loaded on the first execution of
   * SingletonHolder.getInstance() or the first access to
   * SingletonHolder.instance
   */
  private static class SingletonHolder {

    /** The store of the {@link S3ServiceRegistry} instance. */
    private final static S3ServiceRegistry instance = new S3ServiceRegistry();
  }

  /**
   * Get the instance of the {@link S3ServiceRegistry}.
   * 
   * @return the instantiated singleton
   */
  public static S3ServiceRegistry getRegistry() {
    return SingletonHolder.instance;
  }

  /** The mapping table for the auth token to service. */
  private Hashtable<AWSAuthToken, S3Service> services;

  /**
   * Private constructor to refuse public instantiation.
   */
  private S3ServiceRegistry() {
    this.services = new Hashtable<AWSAuthToken, S3Service>();
    AuthenticationTokenManager.getManager().addListener( this );
  }

  /**
   * Clears the list of services.
   */
  public void clear() {
    this.services.clear();
  }

  /**
   * Get the service for accessing the account represented by the specified
   * access key ID. If the service is not yet in the cache it is created and
   * cached afterwards.
   * 
   * @return The service to make operations on the account with the specified
   *         access key ID or <code>null</code> if no service could be created
   * @throws ProblemException If the service was not yet in the cache and its
   *             creation failed
   */
  public S3Service getService() throws ProblemException {

    // get the auth token
    AWSAuthTokenDescription awsAuthTokenDesc = new AWSAuthTokenDescription();

    AuthTokenRequest request = new AuthTokenRequest( awsAuthTokenDesc,
                                                     Messages.getString( "S3ServiceRegistry.auth_requester" ), //$NON-NLS-1$
                                                     Messages.getString( "S3ServiceRegistry.auth_request" ) ); //$NON-NLS-1$
    AWSAuthToken awsAuthToken = ( AWSAuthToken )AbstractAuthTokenProvider.staticRequestToken( request );

    if( awsAuthToken != null ) {
      // try to fetch the service
      S3Service existingService = this.services.get( awsAuthToken );
      if( existingService == null ) {

        // create a new service
        awsAuthTokenDesc = ( AWSAuthTokenDescription )awsAuthToken.getDescription();
        AWSCredentials awsCredentials = new AWSCredentials( awsAuthTokenDesc.getAwsAccessId(),
                                                            awsAuthTokenDesc.getAwsSecretId() );
        RestS3Service service = null;
        try {
          service = new RestS3Service( awsCredentials,
                                       IS3Constants.APP_TAG,
                                       null );
        } catch( S3ServiceException s3sExc ) {
          throw new ProblemException( IS3Problems.REST_SERVICE_CREATION_FAILED,
                                      Activator.PLUGIN_ID );
        }

        // put new service into the map
        this.services.put( awsAuthToken, service );
        return service;
      }
      return existingService;
    }
    return null;
  }

  public void contentChanged( final ISecurityManager manager ) {
    if( manager instanceof AuthenticationTokenManager ) {
      AuthenticationTokenManager authTokenManager = ( AuthenticationTokenManager )manager;

      List<IAuthenticationToken> tokens = authTokenManager.getTokens();
      Set<Entry<AWSAuthToken, S3Service>> entrySet = this.services.entrySet();

      boolean found;

      // remove authtoken/s3service mappings if not existing in the authtoken
      // manager list
      for( Entry<AWSAuthToken, S3Service> entry : entrySet ) {
        found = false;

        for( IAuthenticationToken authToken : tokens ) {
          if( entry.getKey() == authToken ) {
            found = true;
          }
        }

        if( !found ) {
          this.services.remove( entry.getKey() );
        }
      }

    }
  }
}
