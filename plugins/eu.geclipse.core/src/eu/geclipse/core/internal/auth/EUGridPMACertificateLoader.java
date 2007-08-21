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

import eu.geclipse.core.CoreProblems;
import eu.geclipse.core.GridException;
import eu.geclipse.core.Preferences;
import eu.geclipse.core.auth.ICaCertificate;
import eu.geclipse.core.auth.ICaCertificateLoader;
import eu.geclipse.core.util.tar.TarArchiveException;
import eu.geclipse.core.util.tar.TarEntry;
import eu.geclipse.core.util.tar.TarInputStream;

public class EUGridPMACertificateLoader
    implements ICaCertificateLoader {
  
  private static final String DEFAULT_LOCATION
    = "http://www.eugridpma.org/distribution/igtf/current/accredited/tgz"; //$NON-NLS-1$
  
  public ICaCertificate getCertificate( final IPath path )
      throws GridException {
    try {
      return EUGridPMACertificate.readFromFile( path );
    } catch ( IOException ioExc ) {
      throw new GridException( CoreProblems.UNSPECIFIED_IO_PROBLEM, ioExc );
    }
  }
  
  public ICaCertificate getCertificate( final URI uri, final String certID, final IProgressMonitor monitor )
      throws GridException {
    
    IProgressMonitor lMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
      
    lMonitor.beginTask( "Loading certificate " + certID + "...", 3 );
    
    ICaCertificate result = null;
    
    try {
      
      byte[] certificateData = null;
      byte[] infoData = null;
      
      lMonitor.subTask( "Contacting server" );
      URL url = uri.toURL();
      url = new URL( url.toString() + "/" + certID );
      URLConnection connection = Preferences.getURLConnection( url );
      try {
        connection.connect();
      } catch ( SocketTimeoutException toExc ) {
        throw new GridException( CoreProblems.CONNECTION_TIMEOUT, toExc );
      }
      lMonitor.worked( 1 );
      
      lMonitor.subTask( "Decompressing file" );
      InputStream iStream = connection.getInputStream();
      BufferedInputStream bStream = new BufferedInputStream( iStream );
      GZIPInputStream zStream = new GZIPInputStream( bStream );
      TarInputStream tiStream = new TarInputStream( zStream );
      TarEntry tEntry;
      while ( ( tEntry = tiStream.getNextEntry() ) != null ) {
        if ( !tEntry.isDirectory() ) {
          
          IPath oPath = tEntry.getPath();
          String extension = oPath.getFileExtension();
          
          if ( EUGridPMACertificate.CERT_FILE_EXTENSION.equalsIgnoreCase( extension ) ) {
            certificateData = readFromStream( tiStream );
          }
          
          else if ( EUGridPMACertificate.INFO_FILE_EXTENSION.equalsIgnoreCase( extension ) ) {
            infoData = readFromStream( tiStream );
          }
          
        }
      }
      lMonitor.done();
      
      lMonitor.subTask( "Generating certificate" );
      if ( ( certificateData != null ) && ( infoData != null ) ) {
        result = new EUGridPMACertificate( certificateData, infoData );
      }
      lMonitor.worked( 1 );
      
    } catch ( IOException ioExc ) {
      throw new GridException( CoreProblems.CONNECTION_FAILED, ioExc );
    } finally {
      lMonitor.done();
    }

    return result;
    
  }

  public String[] getCertificateList( final URI uri, final IProgressMonitor monitor )
      throws GridException {
    
    IProgressMonitor lMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
    
    lMonitor.beginTask( "Fetching certificate list...", 3 );
    
    List< String > result = new ArrayList< String >();
    
    try {
      
      lMonitor.subTask( "Contacting server" );
      URLConnection connection = Preferences.getURLConnection( uri.toURL() );
      try {
        connection.connect();
      } catch ( SocketTimeoutException toExc ) {
        throw new GridException( CoreProblems.CONNECTION_TIMEOUT, toExc );
      }
      lMonitor.worked( 1 );
      
      lMonitor.subTask( "Loading list" );
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

      lMonitor.subTask( "Parsing list" );
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
      throw new GridException( CoreProblems.CONNECTION_FAILED, ioExc );
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
      throws TarArchiveException {
    ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
    inputStream.copyEntryContents( baoStream );
    return baoStream.toByteArray();
  }

}
