package eu.geclipse.core.security;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.Preferences;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.security.CertificateManager;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.util.tar.TarEntry;
import eu.geclipse.core.util.tar.TarInputStream;

public class GridPMACertificateLoader implements ICertificateLoader {
  
  private static final String TGZ_PREFIX
    = ".tgz"; //$NON-NLS-1$

  private static final String TAR_GZ_PREFIX
    = ".tar.gz"; //$NON-NLS-1$

  public X509Certificate fetchCertificate( final CertificateID id,
                                           final IProgressMonitor monitor )
      throws ProblemException {
    
    IProgressMonitor lMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;

    lMonitor.beginTask( String.format( "Loading certificate: %s", id.getName() ), 2 );

    X509Certificate result = null;

    try {

      lMonitor.subTask( "Contacting server" );
      URL url = id.getURI().toURL();
      URLConnection connection = Preferences.getURLConnection( url );
      try {
        connection.connect();
      } catch ( SocketTimeoutException toExc ) {
        throw new ProblemException( ICoreProblems.NET_CONNECTION_TIMEOUT, toExc, Activator.PLUGIN_ID );
      }
      lMonitor.worked( 1 );

      lMonitor.subTask( "Decompressing" );
      InputStream iStream = connection.getInputStream();
      BufferedInputStream bStream = new BufferedInputStream( iStream );
      GZIPInputStream zStream = new GZIPInputStream( bStream );
      TarInputStream tiStream = new TarInputStream( zStream );
      TarEntry tEntry;
      
      while ( ( tEntry = tiStream.getNextEntry() ) != null ) {
        if ( !tEntry.isDirectory() ) {
          IPath oPath = tEntry.getPath();
          if ( CertificateManager.CERT_FILE_PATTERN.matcher( oPath.lastSegment() ).matches() ) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            tiStream.copyEntryContents( baos );
            ByteArrayInputStream bais = new ByteArrayInputStream( baos.toByteArray() );
            result = X509Util.loadCertificate( bais );
            break;
          }
        }
      }
      lMonitor.worked( 1 );

    } catch ( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.NET_CONNECTION_FAILED, ioExc, Activator.PLUGIN_ID );
    } finally {
      lMonitor.done();
    }

    return result;

  }

  public CertificateID[] listAvailableCertificates( final URI uri,
                                                    final IProgressMonitor monitor )
      throws ProblemException {
    
    List< CertificateID > result = new ArrayList< CertificateID >();
    
    IProgressMonitor lMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
      
    lMonitor.beginTask( "Loading certificates", 3 );
    
    try {

      lMonitor.subTask( "Contacting server" );
      URLConnection connection = Preferences.getURLConnection( uri.toURL() );
      try {
        connection.connect();
      } catch ( SocketTimeoutException toExc ) {
        throw new ProblemException( ICoreProblems.NET_CONNECTION_TIMEOUT, toExc, Activator.PLUGIN_ID );
      }
      lMonitor.worked( 1 );

      lMonitor.subTask( "Loading certificate list" );
      InputStream iStream = connection.getInputStream();
      InputStreamReader iReader = new InputStreamReader( iStream );
      BufferedReader bReader = new BufferedReader( iReader );
      String line;
      StringBuffer buffer = new StringBuffer();
      while ( ( line = bReader.readLine() ) != null ) {
        buffer.append( line );
      }
      bReader.close();
      lMonitor.worked( 1 );

      lMonitor.subTask( "Parsing certificate list" );
      String content = buffer.toString().replaceAll( " ", "" ); //$NON-NLS-1$ //$NON-NLS-2$
      int index = -1;
      while ( ( index = content.indexOf( "ahref=\"", index+1 ) ) > 0 ) { //$NON-NLS-1$
        int endIndex = content.indexOf( "\">", index+7 ); //$NON-NLS-1$
        if ( endIndex > 0 ) {
          String file = content.substring( index+7, endIndex );
          if ( file.toLowerCase().endsWith( ".tar.gz" ) //$NON-NLS-1$
              || file.toLowerCase().endsWith( ".tgz" ) ) { //$NON-NLS-1$
            result.add( new CertificateID( uri, file ) );
          }
        }
        index = endIndex;
      }
      lMonitor.worked( 1 );

    } catch ( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.NET_CONNECTION_FAILED, ioExc, Activator.PLUGIN_ID );
    } finally {
      lMonitor.done();
    }

    return result.toArray( new CertificateID[ result.size() ] );

  }
  
}
