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

package eu.geclipse.core.security;

import java.security.cert.X509Certificate;

/**
 * Base interface for classes that verify the trust of a certificate chain.
 * Classes can be registered with the eu.geclipse.core.certificateTrustVerifier
 * extension point.
 */
public interface ICertificateTrustVerifier {
  
  /**
   * The trust mode of the certificate.
   */
  public enum TrustMode {
    
    /**
     * Trust mode could not be established.
     */
    None,
    
    /**
     * Trust mode for never trusting a certificate.
     */
    Never,
    
    /**
     * Trust mode for temporarily not trusting a certificate.
     */
    NotNow,
    
    /**
     * Trust mode for temporarily trusting a certificate.
     */
    Temporarily,
    
    /**
     * Trust mode for trusting a certificate within the current session.
     */
    Session,
    
    /**
     * Trust mode for permanently trusting a certificate.
     */
    Permanent;
    
    /**
     * Determine if the trust mode denotes a trust state.
     * 
     * @return <code>True</code> if the trust mode is <code>Temporarily</code>.
     * <code>Session</code> or <code>Permanent</code>.
     */
    public boolean isTrusted() {
      return ordinal() > NotNow.ordinal();
    }
    
    /**
     * Determine if the trust mode is a valid mode. Invalid modes are either
     * non-specified modes or <code>None</code>.
     * 
     * @return <code>True</code> if the trust mode is a valid one.
     */
    public boolean isValid() {
      return ( ordinal() >= Never.ordinal() ) && ( ordinal() <= Permanent.ordinal() );
    }
    
  }
  
  /**
   * Verify the trust mode of the specified certificate chain.
   * 
   * @param chain The certificate chain that should be verified.
   * @return One of the defined trust modes.
   */
  public TrustMode verifyTrust( final X509Certificate[] chain );
  
}
