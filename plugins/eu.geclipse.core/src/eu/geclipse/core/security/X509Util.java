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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Utility class for handling {@link X509Certificate}s.
 */
public class X509Util {
  
  /**
   * ID for the X.509 certificate provider.
   */
  private static final String X509_CERTIFICATE_FACTORY_PROVIDER = "X.509"; //$NON-NLS-1$
  
  /**
   * Begin tag for PEM formatted certificates.
   */
  private static final String BEGIN_CERT = "-----BEGIN CERTIFICATE-----"; //$NON-NLS-1$
  
  /**
   * End tag for PEM formatted certificates.
   */
  private static final String END_CERT = "-----END CERTIFICATE-----"; //$NON-NLS-1$
  
  /**
   * OS specific line separator.
   */
  private static final String LINE_SEPARATOR = System.getProperty( "line.separator" ); //$NON-NLS-1$
  
  /**
   * Get a proper string representation of a certificate's serial number.
   * 
   * @param serial The certificate's serial number.
   * @return A string representation of the serial number.
   */
  public static String formatSerialNumber( final BigInteger serial ) {
    
    StringBuffer b = new StringBuffer( serial.toString( 16 ).toUpperCase() );
    
    if ( ( b.length() % 2 ) != 0 ) {
      b.insert( 0, "0" ); //$NON-NLS-1$
    }
    
    int index = 2;
    for ( index = 2 ; index < b.length() ; index += 3 ) {
      b.insert( index, ":" ); //$NON-NLS-1$
    }
    
    return  b.toString();
    
  }
  
  /**
   * Get a proper string representation of the specified encrypted data.
   * 
   * @param data The encrypted data. 
   * @return A string representing the specified data.
   */
  public static String formatEncodedData( final byte[] data ) {
    
    StringBuffer buffer = new StringBuffer();
    
    for ( int i = 0 ; i < data.length ; i++ ) {
      if ( i > 0 ) {
        if ( ( i % 16 ) == 0 ) {
          buffer.append( LINE_SEPARATOR );
        } else {
          buffer.append( " " ); //$NON-NLS-1$
        }
      }
      buffer.append( String.format( "%02X", Byte.valueOf( data[ i ] ) ) ); //$NON-NLS-1$
    }
    
    return buffer.toString();
    
  }
  
  /**
   * Load a certificate from the specified input stream.
   * 
   * @param iStream The input stream pointing to the certificates raw data.
   * @return The certificate itself.
   * @throws ProblemException If no certificate could be loaded from the
   * specified input stream.
   */
  public static X509Certificate loadCertificate( final InputStream iStream )
      throws ProblemException {
    
    X509Certificate result = null;
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] buffer = new byte[ 1024 ];
    int bytesRead;
    
    try {
      do {
        bytesRead = iStream.read( buffer );
        if ( bytesRead > 0 ) {
          baos.write( buffer );
        }
      } while ( bytesRead == buffer.length );
    } catch ( IOException ioExc ) {
      throw new ProblemException( ICoreProblems.AUTH_CERTIFICATE_LOAD_FAILED, ioExc, Activator.PLUGIN_ID );
    }
    
    try {
      iStream.close();
      baos.close();
    } catch ( IOException ioExc ) {
      Activator.logException( ioExc );
    }
    
    String s = new String( baos.toByteArray() );
    int i1 = s.indexOf( BEGIN_CERT );
    int i2 = s.indexOf( END_CERT );
    
    try {
      
      CertificateFactory factory = CertificateFactory.getInstance( X509_CERTIFICATE_FACTORY_PROVIDER );
      
      if ( ( i1 >= 0 ) && ( i2 >= 0 ) && ( i2 > i1 ) ) {
        String cString = s.substring( i1, i2 + END_CERT.length() );
        byte[] bytes = cString.getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream( bytes );
        result = ( X509Certificate ) factory.generateCertificate( bais );
      }
      
      else {
        ByteArrayInputStream bais = new ByteArrayInputStream( baos.toByteArray() );
        result = ( X509Certificate ) factory.generateCertificate( bais );
      }
      
    } catch ( CertificateException certExc ) {
      throw new ProblemException( ICoreProblems.AUTH_CERTIFICATE_LOAD_FAILED, certExc, Activator.PLUGIN_ID );
    }
    
    return result;
    
  }
  
  /**
   * Save the specified certificate formatted as PEM to the specified output
   * stream.
   * 
   * @param c The certificate to be saved.
   * @param oStream The output stream where the certificate data should be
   * written.
   * @throws ProblemException If An error occurs.
   */
  public static void saveCertificate( final X509Certificate c, final OutputStream oStream )
      throws ProblemException {
    
    try {
      
      byte[] encoded = c.getEncoded();
      Base64 base64 = new Base64();
      byte[] base64encoded = base64.encode( encoded );
      
      PrintStream pStream = new PrintStream( oStream );
      
      pStream.println( BEGIN_CERT );
      
      for ( int off = 0 ; off < base64encoded.length ; off += 64 ) {
        int len = off + 64 <= base64encoded.length ? 64 : base64encoded.length - off;
        pStream.write( base64encoded, off, len );
        pStream.println();
      }
      
      pStream.println( END_CERT );
      
      pStream.close();
      
    } catch ( CertificateEncodingException ceExc ) {
      throw new ProblemException( ICoreProblems.AUTH_CREDENTIAL_SAVE_FAILED, ceExc, Activator.PLUGIN_ID );
    }
    
  }
  
}
