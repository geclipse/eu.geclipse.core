package eu.geclipse.core.internal.auth;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.auth.ICaCertificate;

public class EUGridPMACertificate implements ICaCertificate {
  
  static final String CERT_FILE_EXTENSION
    = "0"; //$NON-NLS-1$

  static final String INFO_FILE_EXTENSION
    = "info"; //$NON-NLS-1$
  
  private static final String INVALID_ID = "N/A"; //$NON-NLS-1$
  
  private byte[] certificateData;
  
  private byte[] infoData;
  
  private String id;
  
  EUGridPMACertificate( final byte[] certificateData,
                        final byte[] infoData ) {
    Assert.isNotNull( certificateData );
    Assert.isNotNull( infoData );
    this.certificateData = certificateData;
    this.infoData = infoData;
    this.id = getIDFromInfo( infoData );
  }
  
  public void delete( final IPath fromDirectory ) {
    // TODO mathias
  }

  public String getID() {
    return this.id;
  }
  
  public void write( final IPath toDirectory )
      throws IOException {
    
    String filename = getID();
    
    IPath certFilePath
      = toDirectory.append( filename ).addFileExtension( CERT_FILE_EXTENSION );
    write( this.certificateData, certFilePath );
    
    IPath infoFilePath
      = toDirectory.append( filename ).addFileExtension( INFO_FILE_EXTENSION );
    write( this.infoData, infoFilePath );
    
  }
  
  private String getIDFromInfo( final byte[] info ) {
    
    String result = INVALID_ID;
    
    ByteArrayInputStream baiStream = null;
    InputStreamReader isReader = null;
    BufferedReader bReader = null;
    
    try {
      
      baiStream = new ByteArrayInputStream( info );
      isReader = new InputStreamReader( baiStream );
      bReader = new BufferedReader( isReader );
      
      String line;
      while ( ( line = bReader.readLine() ) != null ) {
        String[] parts = line.split( "=" ); //$NON-NLS-1$
        String  fieldID = parts[0].trim();
        if ( fieldID.equalsIgnoreCase( "alias" ) && ( parts.length > 1 ) ) { //$NON-NLS-1$
          result = parts[1].trim();
          break;
        }
      }
      
    } catch ( IOException ioExc ) {
      // Don't handle this, just return an invalid ID
    } finally {
      secureClose( bReader );
      secureClose( isReader );
      secureClose( baiStream );
    }
  
    return result;
  
  }
  
  private void secureClose( final InputStream iStream ) {
    if ( iStream != null ) {
      try {
        iStream.close();
      } catch (IOException e) {
        // Do nothing here, just catch
      }
    }
  }
  
  private void secureClose( final OutputStream oStream ) {
    if ( oStream != null ) {
      try {
        oStream.close();
      } catch (IOException e) {
        // Do nothing here, just catch
      }
    }
  }
  
  private void secureClose( final Reader reader ) {
    if ( reader != null ) {
      try {
        reader.close();
      } catch (IOException e) {
        // Do nothing here, just catch
      }
    }
  }
  
  private void write( final byte[] data, final IPath filePath )
      throws IOException {
    File file = filePath.toFile();
    FileOutputStream foStream = null;
    try {
      foStream = new FileOutputStream( file );
      foStream.write( data );
    } finally {
      secureClose( foStream );
    }
  }

}
