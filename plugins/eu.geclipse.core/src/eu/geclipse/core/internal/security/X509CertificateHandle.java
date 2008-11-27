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

/**
 * Certificate handle dedicated to X.509 certificates.
 */
public class X509CertificateHandle
    implements ICertificateHandle {
  
  /**
   * The certificate itself.
   */
  private X509Certificate certificate;
  
  /**
   * A file linked to the certificate.
   */
  private File file;
  
  /**
   * The certificates's trust mode.
   */
  private CertTrust trust;
  
  /**
   * Create a new handle for the specified certificate. If the trust mode
   * is {@link CertTrust#AlwaysTrusted} the certificate is saved to disk.
   * 
   * @param c The certificate itself.
   * @param trust The trust mode of the certificate.
   * @throws ProblemException If saving the certificate failed.
   * @see {@link #save(X509Certificate)}
   */
  public X509CertificateHandle( final X509Certificate c, final CertTrust trust )
      throws ProblemException {
    if ( trust == CertTrust.AlwaysTrusted ) {
      this.file = save( c );
    }
    this.certificate = c;
    this.trust = trust;
  }
  
  /**
   * Create a new handle from the specified file.
   * 
   * @param f The file from which to load a certificate.
   * @throws ProblemException If no certificate could be loaded from the
   * specified file.
   */
  X509CertificateHandle( final File f )
      throws ProblemException {
    this.certificate = load( f );
    this.file = f;
    this.trust = CertTrust.AlwaysTrusted;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.security.ICertificateHandle#delete()
   */
  public boolean delete() {
    return this.file != null ? this.file.delete() : true;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals( final Object o ) {
    
    boolean result = false;
    
    if ( o instanceof X509CertificateHandle ) {
      result = ( ( X509CertificateHandle ) o ).getCertificate().equals( getCertificate() );
    }
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.security.ICertificateHandle#getCertificate()
   */
  public X509Certificate getCertificate() {
    return this.certificate;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.security.ICertificateHandle#getTrust()
   */
  public CertTrust getTrust() {
    return this.trust;
  }
  
  /**
   * Try to load a certificate from the specified file.
   * 
   * @param f The file from which to load the certificate.
   * @return The loaded certificate.
   * @throws ProblemException if no certificate could be loaded from the
   * specified file.
   */
  private X509Certificate load( final File f )
      throws ProblemException {
    try {
      FileInputStream fis = new FileInputStream( f );
      return X509Util.loadCertificate( fis );
    } catch( FileNotFoundException fnfExc ) {
      throw new ProblemException( ICoreProblems.SECURITY_CERT_LOAD_FAILED,
                                  Messages.getString("X509CertificateHandle.load_failed") + f.getName(), //$NON-NLS-1$
                                  fnfExc,
                                  Activator.PLUGIN_ID );
    }
  }
  
  /**
   * Try to save the specified certificate at
   * {@link CertificateManager#getCertificateLocation()}.
   * 
   * @param c The certificate to be saved.
   * @return The corresponding file.
   * @throws ProblemException If saving the certificate failed.
   */
  private File save( final X509Certificate c )
      throws ProblemException {
    
    File result = null;
    
    int hash = -1;
    
    try {
      hash = subjectDNHash( c );
    } catch( NoSuchAlgorithmException nsaExc ) {
      Activator.logStatus( new Status( IStatus.WARNING,
                                       Activator.PLUGIN_ID,
                                       Messages.getString("X509CertificateHandle.no_md5_hash"), //$NON-NLS-1$
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
                                    Messages.getString("X509CertificateHandle.inappropriate_file_name"), //$NON-NLS-1$
                                    Activator.PLUGIN_ID );
      }
    }
    
    return result;
    
  }
  
  /**
   * Create the MD5 hash of the specified certificate's subject DN.
   * 
   * @param c The certificate for which to create the hash.
   * @return The create hash.
   * @throws NoSuchAlgorithmException If no MD5 algorithm could be found.
   */
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
