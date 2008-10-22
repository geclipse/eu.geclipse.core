package eu.geclipse.core.internal.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.security.auth.x500.X500Principal;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.security.ICertificateHandle;
import eu.geclipse.core.security.X509Util;
import eu.geclipse.core.security.ICertificateManager.CertTrust;

public class X509CertificateHandle
    implements ICertificateHandle {
  
  private X509Certificate certificate;
  
  private File file;
  
  private CertTrust trust;
  
  public X509CertificateHandle( final X509Certificate c, final CertTrust trust )
      throws ProblemException {
    if ( trust == CertTrust.AlwaysTrusted ) {
      this.file = save( c );
    }
    this.certificate = c;
    this.trust = trust;
  }
  
  X509CertificateHandle( final File f )
      throws ProblemException {
    this.certificate = load( f );
    this.file = f;
    this.trust = CertTrust.AlwaysTrusted;
  }
  
  public boolean delete() {
    return this.file != null ? this.file.delete() : true;
  }
  
  @Override
  public boolean equals( final Object o ) {
    
    boolean result = false;
    
    if ( o instanceof X509CertificateHandle ) {
      result = ( ( X509CertificateHandle ) o ).getCertificate().equals( getCertificate() );
    }
    
    return result;
    
  }
  
  public X509Certificate getCertificate() {
    return this.certificate;
  }
  
  public CertTrust getTrust() {
    return this.trust;
  }
  
  private static String getPrincipalPart( final X500Principal principal, final String dnID ) {

    String result = ""; //$NON-NLS-1$
    
    String name = principal.getName();
    int i1 = name.indexOf( dnID+"=" ); //$NON-NLS-1$
    int i2 = name.indexOf( ",", i1+1 ); //$NON-NLS-1$
    
    if ( i1 >= 0 ) {
      if ( i2 < 0 ) {
        i2 = name.length();
      }
      result = name.substring( i1+dnID.length()+1, i2 );
    }
    
    return result;
    
  }
  
  private X509Certificate load( final File f )
      throws ProblemException {
    try {
      FileInputStream fis = new FileInputStream( f );
      return X509Util.loadCertificate( fis );
    } catch( FileNotFoundException fnfExc ) {
      throw new ProblemException( ICoreProblems.SECURITY_CERT_LOAD_FAILED,
                                  "Unable to create a certificate object from file " + f.getName(),
                                  fnfExc,
                                  Activator.PLUGIN_ID );
    }
  }
  
  private File save( final X509Certificate c )
      throws ProblemException {
    
    File result = null;
    
    int hash = -1;
    
    try {
      hash = subjectDNHash( c );
    } catch( NoSuchAlgorithmException nsaExc ) {
      Activator.logStatus( new Status( IStatus.WARNING,
                                       Activator.PLUGIN_ID,
                                       "Could not create MD5 hash as certificate file name. Using alternative hash instead.",
                                       nsaExc ) );
      hash = c.getSubjectX500Principal().hashCode();
    }
    
    String name = String.format( "%08x", Integer.valueOf( hash ) ); //$NON-NLS-1$
    IPath filepath = CertificateManager.getCertificateLocation().append( name );
    
    for ( int i = 0 ; i <= 9 ; i++ ) {
      result = filepath.addFileExtension( String.valueOf( i ) ).toFile();
      if ( ! result.exists() ) {
        try {
          X509Util.saveCertificate( c, new FileOutputStream( result ) );
        } catch ( FileNotFoundException fnfExc ) {
          throw new ProblemException( ICoreProblems.SECURITY_CERT_SAVE_FAILED,
                                      fnfExc,
                                      Activator.PLUGIN_ID );
        }
        break;
      } else if ( i == 9 ) {
        throw new ProblemException( ICoreProblems.SECURITY_CERT_SAVE_FAILED,
                                    "No appropriate file name ending found",
                                    Activator.PLUGIN_ID );
      }
    }
    
    return result;
    
  }
  
  private int subjectDNHash( final X509Certificate c )
      throws NoSuchAlgorithmException {
    
    X500Principal subjectDN = c.getSubjectX500Principal();
    byte[] encoded = subjectDN.getEncoded();
    MessageDigest md5 = MessageDigest.getInstance( "MD5" ); //$NON-NLS-1$
    md5.reset();
    byte[] digest = md5.digest( encoded );
    
    int result = digest[ 0 ] & 0xFF
                | ( ( digest[ 1 ] & 0xFF ) << 8 )
                | ( ( digest[ 2 ] & 0xFF ) << 16 )
                | ( ( digest[ 3 ] & 0xFF ) << 24 );
    
    return result;
    
  }
  
}
