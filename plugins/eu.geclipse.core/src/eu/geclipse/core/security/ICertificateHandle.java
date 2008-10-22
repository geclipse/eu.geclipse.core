package eu.geclipse.core.security;

import java.security.cert.X509Certificate;

import eu.geclipse.core.security.ICertificateManager.CertTrust;

public interface ICertificateHandle {
  
  public boolean delete();
  
  public X509Certificate getCertificate();
  
  public CertTrust getTrust();
  
}
