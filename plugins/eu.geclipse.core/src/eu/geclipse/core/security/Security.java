package eu.geclipse.core.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertPathParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.util.Set;

import javax.net.ssl.CertPathTrustManagerParameters;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.internal.security.CertificateManager;
import eu.geclipse.core.internal.security.CertificateTrustManager;

public class Security {
  
  private static SSLSocketFactory socketFactory;
  
  public static IPath getCertificateLocation() {
    return CertificateManager.getManager().getCertificateLocation();
  }
  
  public static ICertificateManager getCertificateManager() {
    return CertificateManager.getManager();
  }
  
  public static X509TrustManager getTrustManager( final X509TrustManager slave ) {
    return new CertificateTrustManager( slave );
  }
  
  public static final SSLSocketFactory getSocketFactory()
      throws NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException, KeyStoreException, InvalidAlgorithmParameterException {
    
    if ( socketFactory == null ) {
      
      KeyManagerFactory kmf = KeyManagerFactory.getInstance( "SunX509" );
      kmf.init( null, null );
      
      TrustManagerFactory tmf = TrustManagerFactory.getInstance( "SunX509" );
      tmf.init( ( KeyStore ) null );

      TrustManager[] trustManagers = tmf.getTrustManagers();

      for ( int i = 0 ; i < trustManagers.length ; i++ ) {
        if ( trustManagers[ i ] instanceof X509TrustManager ) {
          trustManagers[ i ] = new CertificateTrustManager( ( X509TrustManager ) trustManagers[ i ] );
        }
      }
      
      SSLContext ctx = SSLContext.getInstance( "TLS" );
      ctx.init( kmf.getKeyManagers(), trustManagers, null );
      
      ctx.getClientSessionContext().setSessionTimeout( 10 );
      
      socketFactory = ctx.getSocketFactory();
      
    }
    
    return socketFactory;
    
  }
  
}
