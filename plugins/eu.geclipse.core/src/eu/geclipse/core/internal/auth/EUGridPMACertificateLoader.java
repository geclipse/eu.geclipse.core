/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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

package eu.geclipse.core.internal.auth;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.Preferences;
import eu.geclipse.core.auth.ICaCertificate;
import eu.geclipse.core.auth.ICaCertificateLoader;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.util.tar.TarEntry;
import eu.geclipse.core.util.tar.TarInputStream;

/**
 * Certificate loader for certificates from the
 * European Policy Management Authority for Grid Authentication.
 */
public class EUGridPMACertificateLoader
    implements ICaCertificateLoader {
  
  private static final String DEFAULT_LOCATION
    = "http://www.eugridpma.org/distribution/igtf/current/accredited/tgz"; //$NON-NLS-1$
  
  public ICaCertificate getCertificate( final IPath path )
      throws ProblemException {
    try {
      return EUGridPMACertificate.readFromFile( path );
    } catch ( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.IO_UNSPECIFIED_PROBLEM, ioExc, Activator.PLUGIN_ID );
    }
  }
  
  public ICaCertificate getCertificate( final URI uri, final String certID, final IProgressMonitor monitor )
      throws ProblemException {
    
    IProgressMonitor lMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
      
    lMonitor.beginTask( String.format( Messages.getString("EUGridPMACertificateLoader.load_cert_task"), certID ), 3 ); //$NON-NLS-1$
    
    ICaCertificate result = null;
    
    try {
      
      byte[] certificateData = null;
      byte[] infoData = null;
      
      lMonitor.subTask( Messages.getString("EUGridPMACertificateLoader.contact_server_task") ); //$NON-NLS-1$
      URL url = uri.toURL();
      url = new URL( url.toString() + "/" + certID ); //$NON-NLS-1$
      URLConnection connection = Preferences.getURLConnection( url );
      try {
        connection.connect();
      } catch ( SocketTimeoutException toExc ) {
        throw new ProblemException( ICoreProblems.NET_CONNECTION_TIMEOUT, toExc, Activator.PLUGIN_ID );
      }
      lMonitor.worked( 1 );
      
      lMonitor.subTask( Messages.getString("EUGridPMACertificateLoader.decomp_file_task") ); //$NON-NLS-1$
      InputStream iStream = connection.getInputStream();
      BufferedInputStream bStream = new BufferedInputStream( iStream );
      GZIPInputStream zStream = new GZIPInputStream( bStream );
      TarInputStream tiStream = new TarInputStream( zStream );
      TarEntry tEntry;
      while ( ( tEntry = tiStream.getNextEntry() ) != null ) {
        if ( !tEntry.isDirectory() ) {

          IPath oPath = tEntry.getPath();
          String extension = oPath.getFileExtension();

          if ( PEMCertificate.CERT_FILE_EXTENSION.equalsIgnoreCase( extension ) ) {
            certificateData = readFromStream( tiStream );
          } else if ( EUGridPMACertificate.INFO_FILE_EXTENSION.equalsIgnoreCase( extension ) ) {
            infoData = readFromStream( tiStream );
          }
        }
      }
      
      lMonitor.done();
      
      lMonitor.subTask( Messages.getString("EUGridPMACertificateLoader.generate_cert_task") ); //$NON-NLS-1$
      if ( ( certificateData != null ) && ( infoData != null ) ) {
        result = new EUGridPMACertificate( certificateData, infoData );
      }
      lMonitor.worked( 1 );
      
    } catch ( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.NET_CONNECTION_FAILED, ioExc, Activator.PLUGIN_ID );
    } finally {
      lMonitor.done();
    }

    return result;
    
  }

  public String[] getCertificateList( final URI uri, final IProgressMonitor monitor )
      throws ProblemException {
    
    IProgressMonitor lMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
    
    lMonitor.beginTask( Messages.getString("EUGridPMACertificateLoader.fetch_list_task"), 3 ); //$NON-NLS-1$
    
    List< String > result = new ArrayList< String >();
    
    try {
      
      lMonitor.subTask( Messages.getString("EUGridPMACertificateLoader.contact_server_task") ); //$NON-NLS-1$
      URLConnection connection = Preferences.getURLConnection( uri.toURL() );
      try {
        connection.connect();
      } catch ( SocketTimeoutException toExc ) {
        throw new ProblemException( ICoreProblems.NET_CONNECTION_TIMEOUT, toExc, Activator.PLUGIN_ID );
      }
      lMonitor.worked( 1 );
      
      lMonitor.subTask( Messages.getString("EUGridPMACertificateLoader.load_list_task") ); //$NON-NLS-1$
      InputStream iStream = connection.getInputStream();
      InputStreamReader iReader = new InputStreamReader( iStream );
      BufferedReader bReader = new BufferedReader( iReader );
      String line;
      StringBuffer buffer = new StringBuffer();
      while ( ( line = bReader.readLine() ) != null ) {
        buffer.append( line );
      }
      bReader.close();
      lMonitor.done();

      lMonitor.subTask( Messages.getString("EUGridPMACertificateLoader.parse_list_task") ); //$NON-NLS-1$
      String content = buffer.toString().replaceAll( " ", "" ); //$NON-NLS-1$ //$NON-NLS-2$
      int index = -1;
      while ( ( index = content.indexOf( "ahref=\"", index+1 ) ) > 0 ) { //$NON-NLS-1$
        int endIndex = content.indexOf( "\">", index+7 ); //$NON-NLS-1$
        if ( endIndex > 0 ) {
          String file = content.substring( index+7, endIndex );
          if ( file.toLowerCase().endsWith( ".tar.gz" ) ) { //$NON-NLS-1$
            result.add( file );
          }
        }
        index = endIndex;
      }
      lMonitor.done();
      
    } catch ( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.NET_CONNECTION_FAILED, ioExc, Activator.PLUGIN_ID );
    } finally {
      lMonitor.done();
    }
    
    return result.toArray( new String[ result.size() ] );
    
  }
  
  public URI[] getPredefinedRemoteLocations() {
    URI[] defaultList = null;
    URI defaultURI;
    try {
      defaultURI = new URI( DEFAULT_LOCATION );
      defaultList = new URI[] {
          defaultURI
      };
    } catch (URISyntaxException e) {
      // Nothing to do, just catch
    }
    return defaultList;
  }
  
  private byte[] readFromStream( final TarInputStream inputStream )
      throws ProblemException {
    ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
    inputStream.copyEntryContents( baoStream );
    return baoStream.toByteArray();
  }

}
