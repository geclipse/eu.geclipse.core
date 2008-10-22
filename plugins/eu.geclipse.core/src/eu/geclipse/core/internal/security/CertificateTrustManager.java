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

public class CertificateTrustManager
    implements X509TrustManager {
  
  private X509TrustManager slaveTrustManager;
  
  public CertificateTrustManager() {
    this( null );
  }
  
  public CertificateTrustManager( final X509TrustManager slave ) {
    this.slaveTrustManager = slave;
  }

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

  public X509Certificate[] getAcceptedIssuers() {
    return this.slaveTrustManager != null ? this.slaveTrustManager.getAcceptedIssuers() : new X509Certificate[ 0 ];
  }
  
  private void checkTrust( final X509Certificate[] chain,
                           final String authType )
      throws CertificateException {
    
    Set< TrustAnchor > trustAnchors = CertificateManager.getManager().getTrustAnchors();
    
    boolean needsFurtherVerification = true;
    
    if ( ( trustAnchors != null ) && ( trustAnchors.size() > 0 ) ) {
    
      needsFurtherVerification = false;
      
      try {
      
        CertificateFactory certFactory = CertificateFactory.getInstance( "X.509" );
        List< X509Certificate > certList = Arrays.asList( chain );
        CertPath certPath = certFactory.generateCertPath( certList );

        CertPathValidator validator = CertPathValidator.getInstance( "PKIX" );

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
      throw new CertificateException( "The certificate chain could not be validated" );
    }
    
  }
  
}
