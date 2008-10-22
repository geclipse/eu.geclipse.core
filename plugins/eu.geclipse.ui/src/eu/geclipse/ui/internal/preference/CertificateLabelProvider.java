package eu.geclipse.ui.internal.preference;

import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Hashtable;
import java.util.Map.Entry;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import eu.geclipse.core.security.ICertificateHandle;
import eu.geclipse.core.security.ICertificateManager.CertTrust;
import eu.geclipse.ui.internal.Activator;

public class CertificateLabelProvider
    extends ColumnLabelProvider
    implements ITableLabelProvider {
  
  private static final String CERT_IMG = "icons/obj16/cert_obj.gif"; //$NON-NLS-1$
  
  private static final String ORG_IMG = "icons/obj16/ca_obj.gif"; //$NON-NLS-1$
  
  private static final String NEVER_TRUST_IMG = "icons/obj16/cert_trust_never_obj.gif"; //$NON-NLS-1$
  
  private static final String SESSION_TRUST_IMG = "icons/obj16/cert_trust_session_obj.gif"; //$NON-NLS-1$
  
  private static final String PERMANENT_TRUST_IMG = "icons/obj16/cert_trust_permanent_obj.gif"; //$NON-NLS-1$
  
  private static final String NA_STRING = "N/A"; //$NON-NLS-1$
  
  private static Hashtable< String, Image > images
    = new Hashtable< String, Image >();
  
  public Image getColumnImage( final Object element, final int columnIndex ) {
    
    Image result = null;
    
    if ( columnIndex == 0 ) {
      if ( element instanceof Entry< ?, ? > ) {
        result = getCertImage( ORG_IMG );
      } else if ( element instanceof ICertificateHandle ) {
        CertTrust trust = ( ( ICertificateHandle ) element ).getTrust();
        if ( trust == CertTrust.Untrusted ) {
          result = getCertImage( NEVER_TRUST_IMG );
        } else if ( trust == CertTrust.Trusted ) {
          result = getCertImage( SESSION_TRUST_IMG );
        } else if ( trust == CertTrust.AlwaysTrusted ) {
          result = getCertImage( PERMANENT_TRUST_IMG );
        } else {
          result = getCertImage( CERT_IMG );
        }
      }
    }
    
    return result;
    
  }

  public String getColumnText( final Object element, final int columnIndex ) {
    
    String result = ""; //$NON-NLS-1$
    
    if ( ( element instanceof Entry< ?, ? > ) && ( columnIndex == 0 ) ) {
      Object key = ( ( Entry< ?, ? > ) element ).getKey();
      result = key.toString();
    }
    
    else if ( element instanceof ICertificateHandle ) {
      ICertificateHandle c = ( ICertificateHandle ) element;
      switch ( columnIndex ) {
        case 0:
          result = CertificateContentProvider.getSubjectDN( c.getCertificate(), "CN" ); //$NON-NLS-1$
          break;
        case 1:
          result = c.getTrust().toString();
          break;
        case 2:
          result = c.getCertificate().getNotAfter().toString();
          break;
      }
    }
    
    return result;
    
  }
  
  private static Image getCertImage( final String path ) {
    
    Image image = images.get( path );
    
    if ( image == null ) {
      image = loadCertImage( path );
      images.put( path, image );
    }
    
    return image;
    
  }
  
  private static Image loadCertImage( final String path ) {
    URL url = Activator.getDefault().getBundle().getEntry( path );
    ImageDescriptor descriptor = ImageDescriptor.createFromURL( url );
    return descriptor.createImage();
  }
  
  private static String getSubjectCommonName( final X509Certificate c ) {
    String result = NA_STRING;
    String[] parts = c.getSubjectX500Principal().getName().split( "[,/]" );
    for ( String s : parts ) {
      if ( s.trim().toUpperCase().startsWith( "CN=" ) ) {
        result = s.trim().substring( 3 );
        break;
      }
    }
    return result;
  }

}
