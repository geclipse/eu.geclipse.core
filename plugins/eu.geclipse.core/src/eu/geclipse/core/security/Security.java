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

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.internal.security.CertificateManager;
import eu.geclipse.core.internal.security.CertificateTrustManager;

/**
 * Base class of the g-Eclipse security framework.
 */
public class Security {
  
  /**
   * Default provider for creating the {@link SSLSocketFactory}.
   */
  private static final String DEFAULT_SOCKET_PROVIDER = "SunX509"; //$NON-NLS-1$
  
  /**
   * The default SSL context protocol used to create the {@link SSLSocketFactory}.
   */
  private static final String DEFAULT_SSL_CONTEXT_PROTOCOL = "TLS"; //$NON-NLS-1$
  
  /**
   * Singleton for the socket factory.
   */
  private static SSLSocketFactory socketFactory;
  
  /**
   * Get the location where the {@link ICertificateManager} singleton
   * implementation stores its certificates.
   * 
   * @return The certificate path in the local file system.
   */
  public static IPath getCertificateLocation() {
    return CertificateManager.getManager().getCertificateLocation();
  }
  
  /**
   * Get the {@link ICertificateManager} singleton implementation.
   * 
   * @return The {@link ICertificateManager}.
   */
  public static ICertificateManager getCertificateManager() {
    return CertificateManager.getManager();
  }
  
  /**
   * Get a {@link X509TrustManager} wrapping up the specified slave.
   * The returned manager will use the {@link ICertificateTrustVerifier}s that
   * are specified via the
   * <code>eu.geclipse.core.certificateTrustVerifier</code> extension point to
   * verify trust on certificates that are not yet known as trusted or
   * untrusted.
   * 
   * @param slave The slave manager that is used at the very first step to
   * verify certificate trust.
   * @return A trust manager wrapping up the specified slave.
   */
  public static X509TrustManager getTrustManager( final X509TrustManager slave ) {
    return new CertificateTrustManager( slave );
  }
  
  /**
   * Get a singleton implementation of an {@link SSLSocketFactory} that hooks in
   * the g-Eclipse trust management into the SSL/TLS framework.
   * 
   * @return A {@link SSLSocketFactory} singleton.
   * @throws NoSuchAlgorithmException
   * @throws KeyManagementException
   * @throws UnrecoverableKeyException
   * @throws KeyStoreException
   * @throws InvalidAlgorithmParameterException
   */
  public static final SSLSocketFactory getSocketFactory()
      throws NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException, KeyStoreException, InvalidAlgorithmParameterException {
    
    if ( socketFactory == null ) {
      
      KeyManagerFactory kmf = KeyManagerFactory.getInstance( DEFAULT_SOCKET_PROVIDER );
      kmf.init( null, null );
      
      TrustManagerFactory tmf = TrustManagerFactory.getInstance( DEFAULT_SOCKET_PROVIDER );
      tmf.init( ( KeyStore ) null );

      TrustManager[] trustManagers = tmf.getTrustManagers();

      for ( int i = 0 ; i < trustManagers.length ; i++ ) {
        if ( trustManagers[ i ] instanceof X509TrustManager ) {
          trustManagers[ i ] = new CertificateTrustManager( ( X509TrustManager ) trustManagers[ i ] );
        }
      }
      
      SSLContext ctx = SSLContext.getInstance( DEFAULT_SSL_CONTEXT_PROTOCOL );
      ctx.init( kmf.getKeyManagers(), trustManagers, null );
      
      ctx.getClientSessionContext().setSessionTimeout( 10 );
      
      socketFactory = ctx.getSocketFactory();
      
    }
    
    return socketFactory;
    
  }
  
  public static void setCertificateLocation( final IPath location ) {
    CertificateManager.getManager().setCertificateLocation( location );
  }
  
}
