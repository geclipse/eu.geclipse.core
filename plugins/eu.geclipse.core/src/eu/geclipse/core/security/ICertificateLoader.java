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

/**
 * Defines the functionality to import certificates from a remote or local
 * repository. See also the eu.geclipse.core.certificateLoader extension point.
 */
public interface ICertificateLoader {
  
  /**
   * A helper class identifying a certificate.
   */
  public static class CertificateID {
    
    /**
     * The repository where to find the corresponding certificate.
     */
    private URI dir;
    
    /**
     * The filename of the corresponding certificate.
     */
    private String name;
    
    /**
     * User date cache.
     */
    private Hashtable< String, Object > dataCache;
    
    /**
     * Create a new certificate ID.
     * 
     * @param dir The repository where to find the corresponding certificate.
     * @param name The filename of the corresponding certificate.
     */
    protected CertificateID( final URI dir, final String name ) {
      this.dir = dir;
      this.name = name;
    }
    
    /**
     * Get the repository where to find the corresponding certificate.
     * 
     * @return The certificate's location.
     */
    public URI getDirectory() {
      return this.dir;
    }
    
    /**
     * Get the filename of the certificate.
     * 
     * @return The certificate's name.
     */
    public String getName() {
      return this.name;
    }
    
    /**
     * Get the full {@link URI} of the certificate, i.e. the concatenation
     * of the directory and the name.
     * 
     * @return The endpoint of the certificate.
     */
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
    
    /**
     * Get the user data with the specified key.
     * 
     * @param key The key of the data.
     * @return The data itself or <code>null</code>.
     */
    protected Object getData( final String key ) {
      
      Object result = null;
      
      if ( this.dataCache != null ) {
        result = this.dataCache.get( key );
      }
      
      return result;
      
    }
    
    /**
     * Set some user data.
     * 
     * @param key The key of this data.
     * @param data The data itself.
     */
    protected void setData( final String key, final Object data ) {
      if ( this.dataCache == null ) {
        this.dataCache = new Hashtable< String, Object >();
      }
      this.dataCache.put( key, data );
    }
    
  }
  
  /**
   * Fetch the certificates from the specified certificate IDs.
   * 
   * @param id The id of the certificate to be fetched.
   * @param monitor A progress monitor.
   * @return The certificate.
   * @throws ProblemException If an error occurred.
   */
  public X509Certificate fetchCertificate( final CertificateID id,
                                           final IProgressMonitor monitor )
    throws ProblemException;
  
  /**
   * List the certificates that are available from the specified repository.
   * 
   * @param uri The repository to be listed.
   * @param monitor A progress monitor.
   * @return A list of available certificates represented by their
   * {@link CertificateID}s.
   * @throws ProblemException If a problem occurs.
   */
  public CertificateID[] listAvailableCertificates( final URI uri,
                                                    final IProgressMonitor monitor )
    throws ProblemException;
  
}
