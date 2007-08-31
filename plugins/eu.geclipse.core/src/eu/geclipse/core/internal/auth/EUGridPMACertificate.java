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
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * Certificate implementation for certificates that are imported from
 * the European Policy Management Authority for Grid Authentication.
 */
public class EUGridPMACertificate
    extends PEMCertificate {
  
  static final String INFO_FILE_EXTENSION
    = "info"; //$NON-NLS-1$
  
  private static final String LIST_FILE_EXTENSION
    = "eugridpma"; //$NON-NLS-1$
  
  private static final String INVALID_ID = "N/A"; //$NON-NLS-1$
  
  private byte[] infoData;
  
  EUGridPMACertificate( final byte[] certificateData,
                        final byte[] infoData ) {
    super( getIDFromInfo( infoData ), certificateData );
    Assert.isNotNull( infoData );
    this.infoData = infoData;
  }
  
  static EUGridPMACertificate readFromFile( final IPath filePath )
      throws IOException {
    
    EUGridPMACertificate result = null;
    
    if ( LIST_FILE_EXTENSION.equals( filePath.getFileExtension() ) ) {
      
      FileReader fReader = new FileReader( filePath.toFile() );
      BufferedReader bReader = new BufferedReader( fReader );
      
      try {
        
        String line = bReader.readLine();
        IPath certFilePath = new Path( line );
        byte[] certData = read( certFilePath );
        
        line = bReader.readLine();
        IPath infoFilePath = new Path( line );
        byte[] infoData = read( infoFilePath );
        
        result = new EUGridPMACertificate( certData, infoData );
        
      } finally {
        secureClose( bReader );
        secureClose( fReader );
      }
      
    }
    
    return result;
    
  }
  
  /**
   * Get the raw info data of this certificate. This contains
   * for instance the alias of the certificate that is also
   * used as the certificate's ID.
   * 
   * @return The certificates raw info data.
   */
  public byte[] getInfoData() {
    return this.infoData;
  }
  
  @Override
  public void write( final IPath toDirectory )
      throws IOException {
    
    String fileName = getID();
    IPath filePath = toDirectory.append( fileName );
    
    IPath listFilePath = filePath.addFileExtension( LIST_FILE_EXTENSION );
    IPath certFilePath = filePath.addFileExtension( CERT_FILE_EXTENSION );
    IPath infoFilePath = filePath.addFileExtension( INFO_FILE_EXTENSION );
    
    FileWriter fWriter = new FileWriter( listFilePath.toFile() );
    BufferedWriter bWriter = new BufferedWriter( fWriter );
    try {
      bWriter.write( certFilePath.toString() );
      bWriter.newLine();
      bWriter.write( infoFilePath.toString() );
    } finally {
      secureClose( bWriter );
      secureClose( fWriter );
    }
    
    write( getCertificateData(), certFilePath );
    write( getInfoData(), infoFilePath );
    
  }
  
  @Override
  protected String[] getFileNames() {
    return new String[] {
      new Path( getID() ).addFileExtension( LIST_FILE_EXTENSION ).toString(),
      new Path( getID() ).addFileExtension( CERT_FILE_EXTENSION ).toString(),
      new Path( getID() ).addFileExtension( INFO_FILE_EXTENSION ).toString()
    };
  }
  
  private static String getIDFromInfo( final byte[] info ) {
    
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
  
}
