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

package eu.geclipse.core.internal.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.net.ssl.X509TrustManager;

/**
 * Internal implementation of the {@link X509TrustManager}.
 */
public class CertificateTrustManager
    implements X509TrustManager {
  
  /**
   * The slave manager.
   */
  private X509TrustManager slaveTrustManager;
  
  /**
   * Create a new manager without any slave.
   */
  public CertificateTrustManager() {
    this( null );
  }
  
  /**
   * Create a new manager with the specified slave.
   * @param slave
   */
  public CertificateTrustManager( final X509TrustManager slave ) {
    this.slaveTrustManager = slave;
  }

  /* (non-Javadoc)
   * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
   */
  public void checkClientTrusted( final X509Certificate[] chain,
                                  final String authType )
      throws CertificateException {
    if ( this.slaveTrustManager != null ) {
      try {
        this.slaveTrustManager.checkClientTrusted( chain, authType );
      } catch ( CertificateException certExc ) {
        checkTrust( chain, authType );
      }
    } else {
      checkTrust( chain, authType );
    }
  }

  /* (non-Javadoc)
   * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
   */
  public void checkServerTrusted( final X509Certificate[] chain,
                                  final String authType )
      throws CertificateException {
    if ( this.slaveTrustManager != null ) {
      try {
        this.slaveTrustManager.checkServerTrusted( chain, authType );
      } catch ( CertificateException certExc ) {
        checkTrust( chain, authType );
      }
    } else {
      checkTrust( chain, authType );
    }
  }

  /* (non-Javadoc)
   * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
   */
  public X509Certificate[] getAcceptedIssuers() {
    return this.slaveTrustManager != null ? this.slaveTrustManager.getAcceptedIssuers() : new X509Certificate[ 0 ];
  }
  
  /**
   * Check the trust for the specified certificate chain.
   * 
   * @param chain The chain to be checked.
   * @param authType The auth type.
   * @throws CertificateException If an error occurs.
   */
  private void checkTrust( final X509Certificate[] chain,
                           final String authType )
      throws CertificateException {
    
    Set< TrustAnchor > trustAnchors = CertificateManager.getManager().getTrustAnchors();
    
    boolean needsFurtherVerification = true;
    
    if ( ( trustAnchors != null ) && ( trustAnchors.size() > 0 ) ) {
    
      needsFurtherVerification = false;
      
      try {
      
        CertificateFactory certFactory = CertificateFactory.getInstance( "X.509" ); //$NON-NLS-1$
        List< X509Certificate > certList = Arrays.asList( chain );
        CertPath certPath = certFactory.generateCertPath( certList );

        CertPathValidator validator = CertPathValidator.getInstance( "PKIX" ); //$NON-NLS-1$

        PKIXParameters params = new PKIXParameters( trustAnchors );
        // TODO mathias incorporate CRLs
        params.setRevocationEnabled( false );

        validator.validate( certPath, params );
        
      } catch ( NoSuchAlgorithmException nsaExc ) {
        throw new CertificateException( nsaExc );
      } catch( InvalidAlgorithmParameterException iapExc ) {
        throw new CertificateException( iapExc );
      } catch( CertPathValidatorException cpvExc ) {
        // The cert chain did not validate, so try with trust verifiers
        needsFurtherVerification = true;
      }
        
    }

    if ( needsFurtherVerification
        && ! CertificateTrustVerifier.getVerifier().verifyTrust( chain ).isTrusted() ) {
      throw new CertificateException( Messages.getString("CertificateTrustManager.cert_chain_not_valid") ); //$NON-NLS-1$
    }
    
  }
  
}
