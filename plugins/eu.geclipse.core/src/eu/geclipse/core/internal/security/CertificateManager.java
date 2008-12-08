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
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.security.BaseSecurityManager;
import eu.geclipse.core.security.ICertificateHandle;
import eu.geclipse.core.security.ICertificateManager;

/**
 * Internal singleton implementation of the {@link ICertificateManager}.
 */
public class CertificateManager
    extends BaseSecurityManager
    implements ICertificateManager {
  
  /**
   * Pattern for globus style certificate file names.
   */
  public static final Pattern CERT_FILE_PATTERN = Pattern.compile( ".*\\.[0-9]" ); //$NON-NLS-1$
  
  /**
   * The singleton instance.
   */
  private static CertificateManager singleton;
  
  /**
   * List containing the currently trusted certificates.
   */
  private List< X509CertificateHandle > trusted;
  
  /**
   * List containing the currently untrusted certificates.
   */
  private List< X509CertificateHandle > untrusted;
  
  private IPath certificateLocation;
  
  /**
   * Private constructor.
   */
  private CertificateManager() {
    this.trusted = new ArrayList< X509CertificateHandle >();
    this.untrusted = new ArrayList< X509CertificateHandle >();
    update();
  }
  
  /**
   * Get the singleton of this certificate manager.
   * 
   * @return The singleton instance.
   */
  public static CertificateManager getManager() {
    if ( singleton == null ) {
      singleton = new CertificateManager();
    }
    return singleton;
  }
  
  public static IPath getDefaultCertificateLocation() {
    IPath location = Activator.getDefault().getStateLocation();
    if ( !location.hasTrailingSeparator() ) {
      location = location.addTrailingSeparator();
    }
    location = location.append( ".security" ); //$NON-NLS-1$
    File file = location.toFile();
    if ( !file.exists() ) {
      if ( ! file.mkdir() ) {
        Activator.logStatus(
            new Status(
                IStatus.WARNING,
                Activator.PLUGIN_ID,
                Messages.getString("CertificateManager.cert_location_creation_failed") //$NON-NLS-1$
            )
        );
      }
    }
    return location;
  }
  
  /**
   * Get the location where this certificate manager stores its trusted
   * certificates.
   * 
   * @return The certificate location.
   */
  public IPath getCertificateLocation() {
    if ( this.certificateLocation == null ) {
      this.certificateLocation = getDefaultCertificateLocation();
    }
    return this.certificateLocation;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.security.ICertificateManager#addCertificate(java.security.cert.X509Certificate, eu.geclipse.core.security.ICertificateManager.CertTrust)
   */
  public ICertificateHandle addCertificate( final X509Certificate c, final CertTrust trust )
      throws ProblemException {
    
    X509CertificateHandle result = internalAddCertificate( c, trust );
    
    if ( result != null ) {
      fireContentChanged();
    }
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.security.ICertificateManager#addCertificates(java.security.cert.X509Certificate[], eu.geclipse.core.security.ICertificateManager.CertTrust)
   */
  public ICertificateHandle[] addCertificates( final X509Certificate[] list, final CertTrust trust )
      throws ProblemException {
    
    List< X509CertificateHandle > result = new ArrayList< X509CertificateHandle >();
    
    for ( X509Certificate c : list ) {
      X509CertificateHandle o = internalAddCertificate( c, trust );
      if ( o != null ) {
        result.add( o );
      }
    }
    
    if ( ! result.isEmpty() ) {
      fireContentChanged();
    }
    
    return result.toArray( new X509CertificateHandle[ result.size() ] );
    
  }
  
  /**
   * Find the {@link ICertificateHandle} corresponding to the specified
   * certificate.
   * 
   * @param cert The certificate to be looked up.
   * @return The handle or <code>null</code> if no such handle currently
   * exists.
   */
  public ICertificateHandle findHandle( final X509Certificate cert ) {
    
    ICertificateHandle result = null;
    List< ICertificateHandle > certificates = getAllCertificates();
    
    for ( ICertificateHandle handle : certificates ) {
      if ( handle.getCertificate().equals( cert ) ) {
        result = handle;
        break;
      }
    }
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.security.ICertificateManager#getAllCertificates()
   */
  public List< ICertificateHandle > getAllCertificates() {
    List< ICertificateHandle > result = new ArrayList< ICertificateHandle >( this.trusted );
    result.addAll( this.untrusted );
    return result;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.security.ICertificateManager#getTrustedCertificates()
   */
  public List< ICertificateHandle > getTrustedCertificates() {
    return new ArrayList< ICertificateHandle >( this.trusted );
  }
  
  /**
   * Get a {@link Set} of {@link TrustAnchor}s initialised with the trusted
   * certificates known by this manager.
   * 
   * @return The trust anchor set.
   */
  public Set< TrustAnchor > getTrustAnchors() {
    List< ICertificateHandle > trustedCertificates = getTrustedCertificates();
    Set< TrustAnchor > set = new HashSet< TrustAnchor >( trustedCertificates.size() );
    for ( ICertificateHandle handle : trustedCertificates ) {
      set.add( new TrustAnchor( handle.getCertificate(), null ) );
    }
    return set;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.security.ICertificateManager#getUntrustedCertificates()
   */
  public List< ICertificateHandle > getUntrustedCertificates() {
    return new ArrayList< ICertificateHandle >( this.untrusted );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.security.ICertificateManager#removeCertificate(eu.geclipse.core.security.ICertificateHandle)
   */
  public void removeCertificate( final ICertificateHandle c ) {
    if ( internalRemoveCertificate( c ) ) {
      fireContentChanged();
    }
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.security.ICertificateManager#removeCertificates(eu.geclipse.core.security.ICertificateHandle[])
   */
  public void removeCertificates( final ICertificateHandle[] list ) {
    
    boolean changed = false;
    
    for ( ICertificateHandle c : list ) {
      changed |= internalRemoveCertificate( c );
    }
    
    if ( changed ) {
      fireContentChanged();
    }
    
  }
  
  public void setCertificateLocation( final IPath location ) {
    this.certificateLocation = location;
    update();
  }
  
  /**
   * Internal helper method to properly add the specified certificate to this
   * manager.
   * 
   * @param c The certificate to be added.
   * @param trust The trust mode of the certificate.
   * @return The {@link X509CertificateHandle} corresponding to the added
   * certificate.
   * @throws ProblemException If the operation failed.
   */
  private X509CertificateHandle internalAddCertificate( final X509Certificate c, final CertTrust trust )
      throws ProblemException {
    
    X509CertificateHandle result = null;
    
    result = new X509CertificateHandle( c, trust );
    if ( trust.isTrusted() && ! this.trusted.contains( result ) ) {
      this.trusted.add( result );
    } else if ( ! trust.isTrusted() && ! this.untrusted.contains( result ) ) {
      this.untrusted.add( result );
    } else {
      result = null;
    }

    return result;
    
  }
  
  /**
   * Internal helper method to properly remove the specified certificate from
   * this manager.
   * 
   * @param c The certificate to be removed.
   * @return <code>True</code> if the certificate could be removed properly.
   */
  private boolean internalRemoveCertificate( final ICertificateHandle c ) {
    
    boolean result = false;
    
    if ( c.getTrust().isTrusted() && this.trusted.contains( c ) && c.delete() ) {
      this.trusted.remove( c );
      result = true;
    }
    
    else if ( ! c.getTrust().isTrusted() && this.untrusted.contains( c ) && c.delete() ) {
      this.untrusted.remove( c );
      result = true;
    }
    
    return result;
    
  }
  
  /**
   * Update the manager's content, i.e. reset all certificate lists and reload
   * the certificates from disk.
   */
  private void update() {
    
    this.trusted.clear();
    File dir = getCertificateLocation().toFile();
    
    if ( dir.exists() && dir.isDirectory() ) {
      File[] files = dir.listFiles();
      for ( File file : files ) {
        if ( CERT_FILE_PATTERN.matcher( file.getName() ).matches() ) {
          try {
            X509CertificateHandle certificate = new X509CertificateHandle( file );
            this.trusted.add( certificate );
          } catch( ProblemException pExc ) {
            Activator.logException( pExc );
          }
        }
      }
    }
    
    fireContentChanged();
    
  }
  
}
