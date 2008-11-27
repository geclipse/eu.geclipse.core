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

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.security.ICertificateHandle;
import eu.geclipse.core.security.ICertificateTrustVerifier;
import eu.geclipse.core.security.ICertificateManager.CertTrust;

/**
 * Internal implementation of the {@link ICertificateTrustVerifier} that
 * delegates the trust verification to the extensions of the
 * eu.geclipse.core.certificateTrustVerifier extension point if the trust
 * could not be verified by this verifier itself.
 */
public class CertificateTrustVerifier
    implements ICertificateTrustVerifier {
  
  /**
   * The singleton instance.
   */
  private static CertificateTrustVerifier singleton;
  
  /**
   * All registered verifiers.
   */
  private List< ICertificateTrustVerifier > verifierList;
  
  /**
   * Private constructor.
   */
  private CertificateTrustVerifier() {
    // empty implementation
  }
  
  /**
   * Get the singleton instance of this trust verifier.
   * 
   * @return The verifier's singleton.
   */
  public static CertificateTrustVerifier getVerifier() {
    if ( singleton == null ) {
      singleton = new CertificateTrustVerifier();
    }
    return singleton;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.security.ICertificateTrustVerifier#verifyTrust(java.security.cert.X509Certificate[])
   */
  public TrustMode verifyTrust( final X509Certificate[] chain ) {
    
    TrustMode result = TrustMode.None;
    
    CertificateManager manager = CertificateManager.getManager();
    
    for ( X509Certificate cert : chain ) {
      ICertificateHandle handle = manager.findHandle( cert );
      if ( handle != null ) {
        CertTrust trust = handle.getTrust();
        if ( trust.isTrusted() ) {
          result = TrustMode.Temporarily;
        } else {
          result = TrustMode.Never;
          break;
        }
      }
    }
    
    if ( result.equals( TrustMode.None ) ) {
      List< ICertificateTrustVerifier > verifiers = getVerifiers();
      for ( ICertificateTrustVerifier verifier : verifiers ) {
        result = verifier.verifyTrust( chain );
        if ( result.isValid() ) {
          handleTrustMode( result, chain );
          break;
        }
      }
    }
    
    return result;

  }
  
  /**
   * Get a list of all registered trust verifiers.
   * 
   * @return All currently know verifiers.
   */
  private List< ICertificateTrustVerifier > getVerifiers() {
    
    if ( this.verifierList == null ) {
      this.verifierList = new ArrayList< ICertificateTrustVerifier >();
      ExtensionManager manager = new ExtensionManager();
      List< Object > list = manager.getExecutableExtensions(
          Extensions.CERT_TRUST_VERIFIER_POINT,
          Extensions.CERT_TRUST_VERIFIER_ELEMENT,
          Extensions.CERT_TRUST_VERIFIER_CLASS
      );
      for ( Object o : list ) {
        this.verifierList.add( ( ICertificateTrustVerifier ) o );
      }
    }
    
    return this.verifierList;
    
  }
  
  private void handleTrustMode( final TrustMode mode, final X509Certificate[] chain ) {

    if ( ( chain != null ) && ( chain.length > 0 ) ) {
      
      try {
      
        if ( mode == TrustMode.Never ) {
          CertificateManager.getManager().addCertificate( chain[ 0 ], CertTrust.Untrusted );
        }
        
        else if ( mode == TrustMode.Session ) {
          CertificateManager.getManager().addCertificates( chain, CertTrust.Trusted );
        }
        
        else if ( mode == TrustMode.Permanent ) {
          CertificateManager.getManager().addCertificates( chain, CertTrust.AlwaysTrusted );
        }
        
      } catch ( ProblemException pExc ) {
        Activator.logException( pExc );
      }
      
    }

  }

}
