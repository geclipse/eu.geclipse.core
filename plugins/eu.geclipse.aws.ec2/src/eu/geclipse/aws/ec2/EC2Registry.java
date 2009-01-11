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

package eu.geclipse.aws.ec2;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import eu.geclipse.aws.auth.AWSAuthTokenDescription;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.auth.AuthenticationTokenManager;
import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.security.ISecurityManager;
import eu.geclipse.core.security.ISecurityManagerListener;

/**
 * Class that manages all instances of S3Services.
 */
public class EC2Registry implements ISecurityManagerListener {

  /**
   * The SingletonHolder is loaded on the first execution of
   * SingletonHolder.getInstance() or the first access to
   * SingletonHolder.instance
   */
  private static class SingletonHolder {

    /** The store of the {@link EC2Registry} instance. */
    private final static EC2Registry instance = new EC2Registry();
  }

  /**
   * Get the instance of the {@link EC2Registry}.
   * 
   * @return the instantiated singleton
   */
  public static EC2Registry getRegistry() {
    return SingletonHolder.instance;
  }

  /** The mapping table for the aws access id to service. */
  private Hashtable<String, IEC2> services;

  /**
   * Private constructor to refuse public instantiation.
   */
  private EC2Registry() {
    this.services = new Hashtable<String, IEC2>();
    AuthenticationTokenManager.getManager().addListener( this );
  }

  /**
   * Clears the list of services.
   */
  public void clear() {
    this.services.clear();
  }

  /**
   * Get the service for accessing the account represented by the specified aws
   * vo and its contained aws access id. If the service is not yet in the cache
   * it is created and cached afterwards.
   * 
   * @param awsVo contains the aws access id to bind and {@link IEC2} to
   * @return the {@link IEC2} object bound to the particular aws access id or
   *         <code>null</code> if no service could be created
   * @throws ProblemException thrown when accessing the aws vo properties failed
   */
  public IEC2 getEC2( final AWSVirtualOrganization awsVo )
    throws ProblemException
  {
    // try to fetch the service
    String awsAccessId = awsVo.getProperties().getAwsAccessId();

    IEC2 existingService = this.services.get( awsAccessId );

    if( existingService == null ) {

      // create a new IEC2 instance
      IEC2 ec2 = new EC2( awsVo );

      // put new service into the map
      this.services.put( awsAccessId, ec2 );
      return ec2;
    }
    return existingService;
  }

  /**
   * Get the service for accessing the account represented by the specified
   * access key ID. If the service is not yet in the cache it is created and
   * cached afterwards.
   * 
   * @param awsAccessId the access id to bind and {@link IEC2} to
   * @return the {@link IEC2} object bound to the particular aws access id or
   *         <code>null</code> if no service could be created
   */
  public IEC2 getEC2( final String awsAccessId ) {

    // try to fetch the service
    IEC2 existingService = this.services.get( awsAccessId );
    if( existingService == null ) {

      // create a new IEC2 instance
      IEC2 ec2 = new EC2( awsAccessId );

      // put new service into the map
      this.services.put( awsAccessId, ec2 );
      return ec2;
    }
    return existingService;
  }

  public void contentChanged( final ISecurityManager manager ) {
    if( manager instanceof AuthenticationTokenManager ) {
      AuthenticationTokenManager authTokenManager = ( AuthenticationTokenManager )manager;

      List<IAuthenticationToken> tokens = authTokenManager.getTokens();
      Set<Entry<String, IEC2>> entrySet = this.services.entrySet();

      List<String> elementsToRemove = new ArrayList<String>( this.services.size() );

      boolean found;

      // remove awsAccessId/IEC2 mappings if not existing in the authtoken
      // manager list
      for( Entry<String, IEC2> entry : entrySet ) {
        found = false;

        for( IAuthenticationToken authToken : tokens ) {

          if( authToken.getDescription() instanceof AWSAuthTokenDescription ) {
            AWSAuthTokenDescription awsAuthTokenDesc = ( AWSAuthTokenDescription )authToken.getDescription();

            if( entry.getKey().equals( awsAuthTokenDesc.getAwsAccessId() ) ) {
              found = true;
            }
          }
        }

        if( !found ) {
          elementsToRemove.add( entry.getKey() );
        }
      }

      for( String awsAccessId : elementsToRemove ) {
        this.services.remove( awsAccessId );
      }

    }
  }
}
