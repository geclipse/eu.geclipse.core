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

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.Preferences;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.reporting.ProblemException;

/**
 * An {@link ICertificateLoader} used to import the CA certificate for the
 * Gilda training infrastructure.
 */
public class GildaCertificateLoader
    implements ICertificateLoader {
  
  /**
   * The location of the gilda CA.
   */
  private static final String CA_DIR = "getCA.php"; //$NON-NLS-1$
  
  /**
   * The certificate ID for the gilda CA certificate.
   */
  private static final String GILDA_CERT_ID = "gildacert"; //$NON-NLS-1$

  /* (non-Javadoc)
   * @see eu.geclipse.core.security.ICertificateLoader#fetchCertificate(eu.geclipse.core.security.ICertificateLoader.CertificateID, org.eclipse.core.runtime.IProgressMonitor)
   */
  public X509Certificate fetchCertificate( final CertificateID id,
                                           final IProgressMonitor monitor )
      throws ProblemException {
    return ( X509Certificate ) id.getData( GILDA_CERT_ID );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.security.ICertificateLoader#listAvailableCertificates(java.net.URI, org.eclipse.core.runtime.IProgressMonitor)
   */
  public CertificateID[] listAvailableCertificates( final URI uri,
                                                    final IProgressMonitor monitor )
      throws ProblemException {
    
    CertificateID[] result = new CertificateID[ 0 ];
    
    IPath rootPath = new Path( uri.getPath() );
    String path = rootPath.append( CA_DIR ).toString();
    
    try {
      
      URI newURI = new URI( uri.getScheme(),
                            uri.getUserInfo(),
                            uri.getHost(),
                            uri.getPort(),
                            path,
                            uri.getQuery(),
                            uri.getFragment() );
      
      String data = URLEncoder.encode( "CAformat", "UTF-8" ) //$NON-NLS-1$ //$NON-NLS-2$
                  + "=" + URLEncoder.encode( "PEM", "UTF-8" ) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                  + "&" + URLEncoder.encode( "submit", "UTF-8" ) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                  + "=" + URLEncoder.encode( "submit", "UTF-8" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      
      URLConnection connection = Preferences.getURLConnection( newURI.toURL() );
      connection.setDoOutput( true );
      OutputStreamWriter osWriter = new OutputStreamWriter( connection.getOutputStream() );
      osWriter.write( data );
      osWriter.flush();
  
      X509Certificate cert = X509Util.loadCertificate( connection.getInputStream() );
      String name = cert.getSubjectDN().getName();
      CertificateID id = new CertificateID( uri, name );
      id.setData( GILDA_CERT_ID, cert );
      result = new CertificateID[] { id };
      
    } catch( URISyntaxException uriExc ) {
      throw new ProblemException( ICoreProblems.NET_CONNECTION_FAILED, uriExc, Activator.PLUGIN_ID );
    } catch( UnsupportedEncodingException ueExc ) {
      throw new ProblemException( ICoreProblems.NET_CONNECTION_FAILED, ueExc, Activator.PLUGIN_ID );
    } catch( MalformedURLException murlExc ) {
      throw new ProblemException( ICoreProblems.NET_CONNECTION_FAILED, murlExc, Activator.PLUGIN_ID );
    } catch( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.NET_CONNECTION_FAILED, ioExc, Activator.PLUGIN_ID );
    }
    
    return result;
    
  }
  
}
