package eu.geclipse.core.security;

import java.security.cert.X509Certificate;
import java.util.List;

import eu.geclipse.core.reporting.ProblemException;

public interface ICertificateManager
    extends ISecurityManager {
  
  public enum CertTrust {
    
    Untrusted,
    Trusted,
    AlwaysTrusted;
    
    public boolean isTrusted() {
      return this.equals( Trusted ) || this.equals( AlwaysTrusted );
    }
    
    public String toString() {
      String result = super.toString();
      if ( this.equals( Untrusted ) ) {
        result = "Untrusted";
      } else if ( this.equals( Trusted ) ) {
        result = "Trusted";
      } else if ( this.equals( AlwaysTrusted ) ) {
        result = "Always Trusted";
      }
      return result;
    }
    
  }
  
  public ICertificateHandle addCertificate( final X509Certificate c, final CertTrust trust ) throws ProblemException;
  
  public ICertificateHandle[] addCertificates( final X509Certificate[] list, final CertTrust trust ) throws ProblemException;
  
  public List< ICertificateHandle > getAllCertificates();
  
  public List< ICertificateHandle > getTrustedCertificates();
  
  public List< ICertificateHandle > getUntrustedCertificates();
  
  public void removeCertificate( final ICertificateHandle c );
  
  public void removeCertificates( final ICertificateHandle[] list );
  
}
