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

public class CertificateManager
    extends BaseSecurityManager
    implements ICertificateManager {
  
  public static final Pattern CERT_FILE_PATTERN = Pattern.compile( ".*\\.[0-9]" ); //$NON-NLS-1$
  
  private static CertificateManager singleton;
  
  private List< X509CertificateHandle > trusted;
  
  private List< X509CertificateHandle > untrusted;
  
  private CertificateManager() {
    this.trusted = new ArrayList< X509CertificateHandle >();
    this.untrusted = new ArrayList< X509CertificateHandle >();
    update();
  }
  
  public static CertificateManager getManager() {
    if ( singleton == null ) {
      singleton = new CertificateManager();
    }
    return singleton;
  }
  
  public static IPath getCertificateLocation() {
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
                "Unable to create security configuration directory"
            )
        );
      }
    }
    return location;
  }
  
  public ICertificateHandle addCertificate( final X509Certificate c, final CertTrust trust )
      throws ProblemException {
    
    X509CertificateHandle result = internalAddCertificate( c, trust );
    
    if ( result != null ) {
      fireContentChanged();
    }
    
    return result;
    
  }
  
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
  
  public List< ICertificateHandle > getAllCertificates() {
    List< ICertificateHandle > result = new ArrayList< ICertificateHandle >( this.trusted );
    result.addAll( this.untrusted );
    return result;
  }
  
  public List< ICertificateHandle > getTrustedCertificates() {
    return new ArrayList< ICertificateHandle >( this.trusted );
  }
  
  public Set< TrustAnchor > getTrustAnchors() {
    List< ICertificateHandle > trustedCertificates = getTrustedCertificates();
    Set< TrustAnchor > set = new HashSet< TrustAnchor >( trustedCertificates.size() );
    for ( ICertificateHandle handle : trustedCertificates ) {
      set.add( new TrustAnchor( handle.getCertificate(), null ) );
    }
    return set;
  }
  
  public List< ICertificateHandle > getUntrustedCertificates() {
    return new ArrayList< ICertificateHandle >( this.untrusted );
  }
  
  public void removeCertificate( final ICertificateHandle c ) {
    if ( internalRemoveCertificate( c ) ) {
      fireContentChanged();
    }
  }
  
  public void removeCertificates( final ICertificateHandle[] list ) {
    
    boolean changed = false;
    
    for ( ICertificateHandle c : list ) {
      changed |= internalRemoveCertificate( c );
    }
    
    if ( changed ) {
      fireContentChanged();
    }
    
  }
  
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
