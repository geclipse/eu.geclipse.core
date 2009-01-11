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

import eu.geclipse.aws.internal.Activator;
import eu.geclipse.aws.internal.Messages;
import eu.geclipse.aws.vo.AWSVirtualOrganization;
import eu.geclipse.core.auth.AuthenticationException;
import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.auth.IAuthenticationTokenDescription;
import eu.geclipse.core.auth.PasswordManager;
import eu.geclipse.core.reporting.ProblemException;

/**
 * This {@link IAuthenticationTokenDescription} provides the authentication
 * credentials for the AWS services. It creates {@link AWSAuthToken} instances
 * to use for the actual connection.
 * 
 * @author Moritz Post
 * @see AWSAuthToken
 */
public class AWSAuthTokenDescription implements IAuthenticationTokenDescription
{

  /** The path for AWS related secure storage data. */
  public static final String SECURE_STORAGE_NODE = "/eu/geclipse/aws/"; //$NON-NLS-1$

  /** The token type name. */
  private static final String TOKEN_TYPE_NAME = Messages.getString( "AWSAuthTokenDescription.token_type_name" ); //$NON-NLS-1$

  /** The ID of the AWS authentication token wizard. */
  private static final String WIZARD_ID = "eu.geclipse.aws.ui.wizards.awsAuthTokenWizard"; //$NON-NLS-1$

  /** The ID of the problem regarding VO settings. */
  private static final String PROBLEM_CHECK_VO_SETTINGS_ID = "eu.geclipse.core.solution.auth.checkVoSettings"; //$NON-NLS-1$

  /**
   * The AWS access id to use when contacting the AWS services.
   */
  private String awsAccessId;

  /** The AWS secret id to use when contacting the AWS services. */
  private String awsSecretId;

  /** The aws vo providing the services. */
  private AWSVirtualOrganization awsVo;

  /**
   * Create a new {@link AWSAuthTokenDescription} with the data from the AWSVo
   * 
   * @param awsVo
   */
  public AWSAuthTokenDescription( final AWSVirtualOrganization awsVo ) {
    if( awsVo != null ) {
      this.awsVo = awsVo;
      try {
        this.awsAccessId = awsVo.getProperties().getAwsAccessId();
      } catch( ProblemException problemEx ) {
        Activator.log( "Could not obtain aws properties", problemEx ); //$NON-NLS-1$
      }
    }
  }

  /**
   * Creates an new {@link AWSAuthTokenDescription} with the given aws access id
   * as its initial value.
   * 
   * @param awsAccessId the aws access id to use as the basis of the auth token
   */
  public AWSAuthTokenDescription( final String awsAccessId ) {
    this.awsAccessId = awsAccessId;
  }

  public IAuthenticationToken createToken() throws AuthenticationException {
    if( this.awsVo == null ) {
      throw new AuthenticationException( AWSAuthTokenDescription.PROBLEM_CHECK_VO_SETTINGS_ID,
                                         Messages.getString( "AWSAuthTokenDescription.problem_description_no_aws_vo" ), //$NON-NLS-1$
                                         Activator.PLUGIN_ID );
    }
    return new AWSAuthToken( this );
  }

  public String getTokenTypeName() {
    return AWSAuthTokenDescription.TOKEN_TYPE_NAME;
  }

  public String getWizardId() {
    return AWSAuthTokenDescription.WIZARD_ID;
  }

  public boolean matches( final IAuthenticationTokenDescription otherToken ) {

    boolean result = true;

    if( otherToken instanceof AWSAuthTokenDescription ) {
      AWSAuthTokenDescription awsAuthTokenDesc = ( AWSAuthTokenDescription )otherToken;

      if( this.awsAccessId != null
          && !this.awsAccessId.equals( awsAuthTokenDesc.getAwsAccessId() ) )
      {
        result = false;
      }
      if( this.awsSecretId != null
          && !this.awsSecretId.equals( awsAuthTokenDesc.getAwsSecretId() ) )
      {
        result = false;
      }
    } else {
      result = false;
    }
    return result;
  }

  /**
   * Getter for the awsAccessId used to authenticate against the AWS Amazon
   * Webservices.
   * 
   * @return the access id
   */
  public String getAwsAccessId() {
    return this.awsAccessId;
  }

  /**
   * Getter for the aws secret id used to authenticate against the Amazon
   * Webservices. At first an attempt is started to fetch the secret id from the
   * {@link PasswordManager}. If no secret id is found the secret id in this
   * {@link AWSAuthTokenDescription} is returned.
   * 
   * @return the secret id or <code>null</code> if none is specified
   */
  public String getAwsSecretId() {
    if( this.awsAccessId != null ) {
      String secretId = PasswordManager.getPassword( AWSAuthTokenDescription.SECURE_STORAGE_NODE
                                                     + this.awsAccessId );
      if( secretId != null ) {
        return secretId;
      }
    }
    return this.awsSecretId;
  }

  /**
   * Getter for the {@link #awsVo} field.
   * 
   * @return the awsVo
   */
  public AWSVirtualOrganization getAwsVo() {
    return this.awsVo;
  }

  /**
   * @param awsVo the awsVo to set
   */
  public void setAwsVo( final AWSVirtualOrganization awsVo ) {
    this.awsVo = awsVo;
  }

}
