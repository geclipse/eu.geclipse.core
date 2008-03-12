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

package eu.geclipse.aws.auth;

import eu.geclipse.aws.internal.auth.AwsCredential;
import eu.geclipse.core.auth.IAuthenticationToken;
import eu.geclipse.core.auth.IAuthenticationTokenDescription;

/**
 * Authentication token description for AWS credentials.
 */
public class AwsCredentialDescription
    implements IAuthenticationTokenDescription {
  
  /**
   * The type name of this token.
   */
  private static final String TOKEN_TYPE_NAME
    = "AWS Credential"; //$NON-NLS-1$
  
  /**
   * The id of the wizard used to create this type of token.
   */
  private static final String WIZARD_ID
    = "eu.geclipse.aws.ui.wizards.AwsCredentialWizard"; //$NON-NLS-1$
  
  /**
   * The credential's access key ID.
   */
  private String accessKeyID;
  
  /**
   * The credential's secret access key.
   */
  private String secretAccessKey;
  
  /**
   * Default constructor. May not be used for token creations
   * but for token requests.
   */
  public AwsCredentialDescription() {
    this( null, null ); 
  }
  
  /**
   * Constructor with a known access key ID. May not be used for
   * token creations but for token requests.
   * 
   * @param accessKeyID The access key ID for the token.
   */
  public AwsCredentialDescription( final String accessKeyID ) {
    this( accessKeyID, null );
  }
  
  /**
   * Full constructor of an AWS credential. May be used for token
   * creation.
   * 
   * @param accessKeyID The access key ID for the token.
   * @param secretAccessKey The access key itself.
   */
  public AwsCredentialDescription( final String accessKeyID, final String secretAccessKey ) {
    this.accessKeyID = accessKeyID;
    this.secretAccessKey = secretAccessKey;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthenticationTokenDescription#createToken()
   */
  public IAuthenticationToken createToken() {
    return new AwsCredential( this );
  }
  
  /**
   * Get the access key ID of this token description.
   * 
   * @return The credential's access key ID.
   */
  public String getAccessKeyID() {
    return this.accessKeyID;
  }
  
  /**
   * Get the secret access key of this token description.
   * 
   * @return The credential's secret access key.
   */
  public String getSecretAccessKey() {
    return this.secretAccessKey;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthenticationTokenDescription#getTokenTypeName()
   */
  public String getTokenTypeName() {
    return TOKEN_TYPE_NAME;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthenticationTokenDescription#getWizardId()
   */
  public String getWizardId() {
    return WIZARD_ID;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.IAuthenticationTokenDescription#matches(eu.geclipse.core.auth.IAuthenticationTokenDescription)
   */
  public boolean matches( final IAuthenticationTokenDescription otherToken ) {
    
    boolean result = false;
    
    if ( otherToken instanceof AwsCredentialDescription ) {
      
      AwsCredentialDescription otherDesc = ( AwsCredentialDescription ) otherToken;
      
      String thisID = getAccessKeyID();
      String otherID = otherDesc.getAccessKeyID();
      String thisKey = getSecretAccessKey();
      String otherKey = otherDesc.getSecretAccessKey();
      
      result
        =  ( ( thisID == null ) || ( thisID.equals( otherID ) ) )
        && ( ( thisKey  == null ) || ( thisKey.equals( otherKey ) ) );
      
    }
    
    return result;
    
  }

}
