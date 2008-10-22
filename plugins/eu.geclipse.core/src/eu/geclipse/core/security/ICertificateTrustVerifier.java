package eu.geclipse.core.security;

import java.security.cert.X509Certificate;

public interface ICertificateTrustVerifier {
  
  public enum TrustMode {
    
    None,
    Never,
    NotNow,
    Temporarily,
    Session,
    Permanent;
    
    public boolean isTrusted() {
      return ordinal() > NotNow.ordinal();
    }
    
    public boolean isValid() {
      return ( ordinal() >= Never.ordinal() ) && ( ordinal() <= Permanent.ordinal() );
    }
    
  }
  
  public TrustMode verifyTrust( final X509Certificate[] chain );
  
}
