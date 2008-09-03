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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import eu.geclipse.core.auth.AbstractCaCertificate;

/**
 * Default implementation for PEM encoded certificates.
 */
public class PEMCertificate
    extends AbstractCaCertificate {
  
  public static final String PEM_FILE_EXTENSION
    = "pem"; //$NON-NLS-1$
  
  public static final String CERT_FILE_EXTENSION
    = "0"; //$NON-NLS-1$

  public static final String LIST_FILE_EXTENSION
    = "pemcert"; //$NON-NLS-1$
  
  public PEMCertificate( final String id,
                  final byte[] certificateData ) {
    super( id, certificateData );
  }
  
  public static PEMCertificate readFromFile( final IPath filePath )
      throws IOException {
    
    PEMCertificate result = null;
    
    if ( CERT_FILE_EXTENSION.equals( filePath.getFileExtension() )
        || PEM_FILE_EXTENSION.equals( filePath.getFileExtension() ) ) {    
      String id = filePath.removeFileExtension().lastSegment();
      byte[] certData = read( filePath );
      result = new PEMCertificate( id, certData );
    }
    
    else if ( LIST_FILE_EXTENSION.equals( filePath.getFileExtension() ) ) {
      
      FileReader fReader = new FileReader( filePath.toFile() );
      BufferedReader bReader = new BufferedReader( fReader );
      
      try {
        
        String line = bReader.readLine();
        IPath certFilePath = new Path( line );
        String id = certFilePath.removeFileExtension().lastSegment();
        byte[] certData = read( certFilePath );
        
        result = new PEMCertificate( id, certData );
        
      } finally {
        secureClose( bReader );
        secureClose( fReader );
      }
      
    }
    
    return result;
    
  }

  @Override
  protected String[] getFileNames() {
    return new String[] {
      new Path( getID() ).addFileExtension( LIST_FILE_EXTENSION ).toString(),
      new Path( getID() ).addFileExtension( CERT_FILE_EXTENSION ).toString()
    };
  }

  public void write( final IPath toDirectory ) throws IOException {
    
    String fileName = getID();
    IPath filePath = toDirectory.append( fileName );
    
    IPath listFilePath = filePath.addFileExtension( LIST_FILE_EXTENSION );
    IPath certFilePath = filePath.addFileExtension( CERT_FILE_EXTENSION );
    
    FileWriter fWriter = new FileWriter( listFilePath.toFile() );
    BufferedWriter bWriter = new BufferedWriter( fWriter );
    try {
      bWriter.write( certFilePath.toString() );
    } finally {
      secureClose( bWriter );
      secureClose( fWriter );
    }
    
    write( getCertificateData(), certFilePath );
    
  }
  
  protected static byte[] read( final IPath filePath )
      throws IOException {

    ByteArrayOutputStream baoStream = new ByteArrayOutputStream();

    File file = filePath.toFile();
    FileInputStream fiStream = null;
    byte[] oneKBbuffer = new byte[ 1024 ];

    try {
      fiStream = new FileInputStream( file );
      for ( int bytesRead = fiStream.read( oneKBbuffer ) ; bytesRead != -1 ; bytesRead = fiStream.read( oneKBbuffer ) ) {
        if ( bytesRead > 0 ) {
          baoStream.write( oneKBbuffer, 0, bytesRead );
        }
      }
    } finally {
      secureClose( fiStream );
    }

    return baoStream.toByteArray();

  }
  
  protected static void secureClose( final InputStream iStream ) {
    if ( iStream != null ) {
      try {
        iStream.close();
      } catch (IOException e) {
        // Do nothing here, just catch
      }
    }
  }
  
  protected static void secureClose( final OutputStream oStream ) {
    if ( oStream != null ) {
      try {
        oStream.close();
      } catch (IOException e) {
        // Do nothing here, just catch
      }
    }
  }
  
  protected static void secureClose( final Reader reader ) {
    if ( reader != null ) {
      try {
        reader.close();
      } catch (IOException e) {
        // Do nothing here, just catch
      }
    }
  }
  
  protected static void secureClose( final Writer writer ) {
    if ( writer != null ) {
      try {
        writer.close();
      } catch (IOException e) {
        // Do nothing here, just catch
      }
    }
  }
  
  protected static void write( final byte[] data, final IPath filePath )
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
