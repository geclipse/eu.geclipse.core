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

package eu.geclipse.core.security;

import java.io.ByteArrayOutputStream;

/**
 * This class is a helper class that provides encoding and decoding
 * functionality for Base64 encoding.
 */
public class Base64Codec {
  
  /**
   * The table used for encoding.
   */
  private static final byte[] encodingTable = new byte[] {
    (byte)'A', (byte)'B', (byte)'C', (byte)'D', (byte)'E', (byte)'F', (byte)'G',
    (byte)'H', (byte)'I', (byte)'J', (byte)'K', (byte)'L', (byte)'M', (byte)'N',
    (byte)'O', (byte)'P', (byte)'Q', (byte)'R', (byte)'S', (byte)'T', (byte)'U',
    (byte)'V', (byte)'W', (byte)'X', (byte)'Y', (byte)'Z',
    (byte)'a', (byte)'b', (byte)'c', (byte)'d', (byte)'e', (byte)'f', (byte)'g',
    (byte)'h', (byte)'i', (byte)'j', (byte)'k', (byte)'l', (byte)'m', (byte)'n',
    (byte)'o', (byte)'p', (byte)'q', (byte)'r', (byte)'s', (byte)'t', (byte)'u',
    (byte)'v', (byte)'w', (byte)'x', (byte)'y', (byte)'z',
    (byte)'0', (byte)'1', (byte)'2', (byte)'3', (byte)'4', (byte)'5', (byte)'6',
    (byte)'7', (byte)'8', (byte)'9',
    (byte)'+', (byte)'/'
  };
  
  /**
   * The table used for decoding.
   */
  private static final byte[] decodingTable = new byte[ 128 ];
  
  /**
   * The byte used as fillbyte.
   */
  private static byte fillbyte = (byte)'=';
  
  static {
    for ( int i = 0 ; i < decodingTable.length ; i++ ) {
      decodingTable[ i ] = 0;
    }
    for ( int i = 0 ; i < encodingTable.length ; i++ ) {
      decodingTable[ encodingTable[ i ] ] = ( byte ) i;
    }
  }
  
  /**
   * Encodes a given <code>String</code> into Base64
   * @param decoded the input string
   * @return the encoded string
   */
  public static String encode( final String decoded ) {
    return new String( encode( decoded.getBytes() ) );
  }
  
  /**
   * Encodes a given byte array into Base64
   * @param decoded the input byte array
   * @return the encoded byte array
   */
  public static byte[] encode( final byte[] decoded ) {
    
    /*byte[] encoded = new byte[ ( decoded.length + 2 ) / 3 * 4 ];
    int encodedIndex = 0;*/
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    
    for ( int i = 0 ; i < decoded.length ; i += 3 ) {
      byte d1 = decoded[i];
      byte d2 = ( i+1 < decoded.length ) ? decoded[i+1] : -1;
      byte d3 = ( i+2 < decoded.length ) ? decoded[i+2] : -1;
      int i1 = ( d1 >>> 2 ) & 0x3F;
      int i2 = ( d1 << 4) & 0x3F;
      int i3 = -1;
      int i4 = -1;
      if ( d2 >= 0 ) {
        i2 |= ( d2 >>> 4 ) & 0x3F;
        i3 = ( d2 << 2) & 0x3F;
      }
      if ( d3 >= 0 ) {
        i3 |= ( d3 >>> 6 ) & 0x3F;
        i4 = d3 & 0x3F;
      }
      out.write( encodingTable[ i1 ] );
      out.write( encodingTable[ i2 ] );
      out.write( i3 >= 0 ? encodingTable[ i3 ] : fillbyte );
      out.write( i4 >= 0 ? encodingTable[ i4 ] : fillbyte );
    }
    
    return out.toByteArray();

  }
  
  /**
   * Decodes a given <code>String</code> into Base64
   * @param encoded the input string
   * @return the decoded string
   */
  public static String decode( final String encoded ) {
    return new String( decode( encoded.getBytes() ) );
  }
  
  /**
   * Decodes a given byte array into Base64
   * @param encoded the input byte array
   * @return the decoded byte array
   */
  public static byte[] decode( final byte[] encoded ) {
    
    int encodedLength = encoded.length;
    while (  ( encoded[ encodedLength-1 ] == fillbyte ) 
            && ( encodedLength > 0 ) ) {
      encodedLength--;
    }
    
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    
    for ( int i = 0 ; i < encodedLength ; i += 4 ) {

      byte e1 = decodingTable[ encoded[ i ] ];
      byte e2 = ( i+1 < encodedLength ) ? decodingTable[ encoded[ i+1 ] ] : -1;
      byte e3 = ( i+2 < encodedLength ) ? decodingTable[ encoded[ i+2 ] ] : -1;
      byte e4 = ( i+3 < encodedLength ) ? decodingTable[ encoded[ i+3 ] ] : -1;
      
      byte d1 = ( byte ) ( ( e1 << 2 ) & 0xFF );
      byte d2 = -1;
      byte d3 = -1;
      if ( e2 >= 0 ) {
        d1 |= ( e2 >>> 4 ) & 0xFF;
        d2 = ( byte ) ( ( e2 << 4 ) & 0xFF );
      }
      if ( e3 >= 0 ) {
        d2 |= ( byte ) ( ( e3 >>> 2 ) & 0xFF );
        d3 = ( byte ) ( ( e3 << 6 ) & 0xFF );
      }
      if ( e4 >= 0 ) {
        d3 |= ( byte ) ( e4 & 0xFF );
      }
      
      out.write( d1 );
      if ( e2 >= 0 && d2 > 0 ) out.write( d2 );
      if ( e3 >= 0 && d3 > 0 ) out.write( d3 );
      
    }

    return out.toByteArray();
    
  }

}
