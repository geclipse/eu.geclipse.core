package eu.geclipse.core.internal.security;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.security.ICertificateHandle;
import eu.geclipse.core.security.ICertificateTrustVerifier;
import eu.geclipse.core.security.ICertificateManager.CertTrust;

public class CertificateTrustVerifier
    implements ICertificateTrustVerifier {
  
  private static CertificateTrustVerifier singleton;
  
  private List< ICertificateTrustVerifier > verifierList;
  
  private CertificateTrustVerifier() {
    // empty implementation
  }
  
  public static CertificateTrustVerifier getVerifier() {
    if ( singleton == null ) {
      singleton = new CertificateTrustVerifier();
    }
    return singleton;
  }

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
  
  private List< ICertificateTrustVerifier > getVerifiers() {
    
    if ( this.verifierList == null ) {
      this.verifierList = new ArrayList< ICertificateTrustVerifier >();
      ExtensionManager manager = new ExtensionManager();
      List< Object > list = manager.getExecutableExtensions(
          "eu.geclipse.core.certificateTrustVerifier",
          "verifier",
          "class"
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
