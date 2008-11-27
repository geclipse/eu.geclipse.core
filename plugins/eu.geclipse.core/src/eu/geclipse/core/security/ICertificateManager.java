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
import java.util.List;

import eu.geclipse.core.reporting.ProblemException;

/**
 * Central certificate manager definition.
 */
public interface ICertificateManager
    extends ISecurityManager {
  
  /**
   * Trust mode for the certificates managed by this manager.
   */
  public enum CertTrust {
    
    /**
     * The certificate is not trusted at all.
     */
    Untrusted,
    
    /**
     * The certificate is trusted within the whole session. 
     */
    Trusted,
    
    /**
     * The certificate is trusted permanently.
     */
    AlwaysTrusted;
    
    /**
     * Returns if this CertTrust represents a trusted state.
     * 
     * @return <code>True</code> if either the state is <code>Trusted</code>
     * or <code>AlwaysTrusted</code>. 
     */
    public boolean isTrusted() {
      return this.equals( Trusted ) || this.equals( AlwaysTrusted );
    }
    
    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
      String result = super.toString();
      if ( this.equals( Untrusted ) ) {
        result = Messages.getString("ICertificateManager.cert_trust_untrusted"); //$NON-NLS-1$
      } else if ( this.equals( Trusted ) ) {
        result = Messages.getString("ICertificateManager.cert_trust_trusted"); //$NON-NLS-1$
      } else if ( this.equals( AlwaysTrusted ) ) {
        result = Messages.getString("ICertificateManager.cert_trust_alwaystrusted"); //$NON-NLS-1$
      }
      return result;
    }
    
  }
  
  /**
   * Add the specified certificate with the specified trust mode to this
   * manager. 
   * 
   * @param c The certificate to be added.
   * @param trust The trust mode of this certificate.
   * @return The {@link ICertificateHandle} that is created to internally handle
   * certificates.
   * @throws ProblemException If the certificate could not be added due to an
   * error.
   */
  public ICertificateHandle addCertificate( final X509Certificate c, final CertTrust trust ) throws ProblemException;
  
  /**
   * Add the specified certificates with the specified trust mode to this
   * manager. 
   * 
   * @param list The certificates to be added.
   * @param trust The trust mode of these certificates.
   * @return The {@link ICertificateHandle}s that are created to internally
   * handle certificates.
   * @throws ProblemException If the certificates could not be added due to an
   * error.
   */
  public ICertificateHandle[] addCertificates( final X509Certificate[] list, final CertTrust trust ) throws ProblemException;
  
  /**
   * Get all currently managed certificates.
   * 
   * @return The {@link ICertificateHandle}s of the currently managed
   * certificates.
   */
  public List< ICertificateHandle > getAllCertificates();
  
  /**
   * Get all currently trusted certificates.
   * 
   * @return The {@link ICertificateHandle}s of the currently trusted
   * certificates.
   */
  public List< ICertificateHandle > getTrustedCertificates();
  
  /**
   * Get all currently not trusted certificates.
   * 
   * @return The {@link ICertificateHandle}s of the currently not trusted
   * certificates.
   */
  public List< ICertificateHandle > getUntrustedCertificates();
  
  /**
   * Remove the specified certificate from this manager.
   * 
   * @param c The {@link ICertificateHandle} of the certificate to be removed.
   */
  public void removeCertificate( final ICertificateHandle c );
  
  /**
   * Remove the specified certificates from this manager.
   * 
   * @param list The {@link ICertificateHandle}s of the certificates to be removed.
   */
  public void removeCertificates( final ICertificateHandle[] list );
  
}
