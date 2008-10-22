package eu.geclipse.core.security;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.X509Certificate;
import java.util.Hashtable;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.reporting.ProblemException;

public interface ICertificateLoader {
  
  public static class CertificateID {
    
    private URI dir;
    
    private String name;
    
    private Hashtable< String, Object > data;
    
    protected CertificateID( final URI dir, final String name ) {
      this.dir = dir;
      this.name = name;
    }
    
    public URI getDirectory() {
      return this.dir;
    }
    
    public String getName() {
      return this.name;
    }
    
    public URI getURI() {
      
      URI result = null;
      
      IPath path = new Path( this.dir.getPath() );
      path = path.append( this.name );
      
      try {
        result = new URI( this.dir.getScheme(),
                          this.dir.getUserInfo(),
                          this.dir.getHost(),
                          this.dir.getPort(),
                          path.toString(),
                          this.dir.getQuery(),
                          this.dir.getFragment() );
      } catch( URISyntaxException uriExc ) {
        // Should never happen. If it does anyways we're just logging it.
        Activator.logException( uriExc );
      }

      return result;
    
    }
    
    protected Object getData( final String key ) {
      
      Object result = null;
      
      if ( this.data != null ) {
        result = this.data.get( key );
      }
      
      return result;
      
    }
    
    protected void setData( final String key, final Object data ) {
      if ( this.data == null ) {
        this.data = new Hashtable< String, Object >();
      }
      this.data.put( key, data );
    }
    
  }
  
  public X509Certificate fetchCertificate( final CertificateID id,
                                           final IProgressMonitor monitor )
    throws ProblemException;
  
  public CertificateID[] listAvailableCertificates( final URI uri,
                                                    final IProgressMonitor monitor )
    throws ProblemException;
  
}
