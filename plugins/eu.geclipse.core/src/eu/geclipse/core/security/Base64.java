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

import java.io.ByteArrayOutputStream;

/**
 * A configurable Base64 codec.
 */
public class Base64 {
  
  /**
   * Encoding table as used by conventional base64 codecs.
   */
  private static final byte[] DEFAULT_ENCODING_TABLE = new byte[] {
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
   * Fill byte as used by conventional base64 codecs.
   */
  private static final byte DEFAULT_FILL_BYTE = ( byte ) '=';
  
  /**
   * Encoding table used by this codec.
   */
  private byte[] encodingTable;
  
  /**
   * Decoding table used by this codec.
   */
  private byte[] decodingTable;
  
  /**
   * Fill byte used by this codec.
   */
  private byte fillbyte;
  
  /**
   * Create a defaut Base64 codec. The codec is initialized with the default
   * en-/decoding tables and the default fill byte.
   */
  public Base64() {
    setEncodingTable( DEFAULT_ENCODING_TABLE, true );
    setFillByte( DEFAULT_FILL_BYTE);
  }
  
  /**
   * Decode the specified string using the current decoding table
   * and the current fill byte.
   * 
   * @param encoded The encoded string.
   * @return The decoded string.
   */
  public String decode( final String encoded ) {
    return new String( decode( encoded.getBytes() ) );
  }
  
  /**
   * Decode the specified byte array using the current decoding table
   * and the current fill byte.
   * 
   * @param encoded The encoded byte array.
   * @return The decoded byte array.
   */
  public byte[] decode( final byte[] encoded ) {
    
    int encodedLength = encoded.length;
    while (  ( encoded[ encodedLength-1 ] == this.fillbyte ) 
            && ( encodedLength > 0 ) ) {
      encodedLength--;
    }
    
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    
    for ( int i = 0 ; i < encodedLength ; i += 4 ) {

      byte e1 = this.decodingTable[ encoded[ i ] ];
      byte e2 = ( i+1 < encodedLength ) ? this.decodingTable[ encoded[ i+1 ] ] : -1;
      byte e3 = ( i+2 < encodedLength ) ? this.decodingTable[ encoded[ i+2 ] ] : -1;
      byte e4 = ( i+3 < encodedLength ) ? this.decodingTable[ encoded[ i+3 ] ] : -1;
      
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
      //if ( e2 >= 0 && d2 > 0 ) out.write( d2 );
      //if ( e3 >= 0 && d3 > 0 ) out.write( d3 );
      if ( e2 >= 0 ) out.write( d2 );
      if ( e3 >= 0 ) out.write( d3 );
      
    }

    return out.toByteArray();
    
  }
  
  /**
   * Encode the specified string using the current encoding table
   * and the current fill byte.
   * 
   * @param decoded The decoded string.
   * @return The encoded string.
   */
  public String encode( final String decoded ) {
    return new String( encode( decoded.getBytes() ) );
  }
  
  /**
   * Encode the specified byte array using the current encoding table
   * and the current fill byte.
   * 
   * @param decoded The decoded byte array.
   * @return The encoded byte array.
   */
  public byte[] encode( final byte[] decoded ) {
    
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    
    for ( int i = 0 ; i < decoded.length ; i += 3 ) {
      byte d1 = decoded[i];
      byte d2 = ( i+1 < decoded.length ) ? decoded[i+1] : 0;
      byte d3 = ( i+2 < decoded.length ) ? decoded[i+2] : 0;
      int i1 = ( d1 >>> 2 ) & 0x3F;
      int i2 = ( d1 << 4) & 0x3F;
      int i3 = -1;
      int i4 = -1;
      if ( i+1 < decoded.length ) {
        i2 |= ( d2 >>> 4 ) & 0x0F;
        i3 = ( d2 << 2) & 0x3F;
      }
      if ( i+2 < decoded.length ) {
        i3 |= ( d3 >>> 6 ) & 0x03;
        i4 = d3 & 0x3F;
      }
      out.write( this.encodingTable[ i1 ] );
      out.write( this.encodingTable[ i2 ] );
      out.write( i3 >= 0 ? this.encodingTable[ i3 ] : this.fillbyte );
      out.write( i4 >= 0 ? this.encodingTable[ i4 ] : this.fillbyte );
    }
    
    return out.toByteArray();
    
  }
  
  /**
   * Set the current decoding table to the specified one.
   * 
   * @param table The new decoding table.
   */
  public void setDecodingTable( final byte[] table ) {
    this.decodingTable = new byte[ table.length ];
    this.encodingTable = new byte[ table.length ];
    for ( int i = 0 ; i < table.length ; i++ ) {
      this.decodingTable[ i ] = table[ i ];
    }
  }
  
  /**
   * Set the current encoding table to the specified one. This method changes
   * the current decoding table accordingly if changeDecoding is set to true.
   * 
   * @param table The new encoding table.
   * @param changeDecoding If true the decoding table is changed as well.
   */
  public void setEncodingTable( final byte[] table, final boolean changeDecoding ) {

    this.encodingTable = new byte[ table.length ];
    for ( int i = 0 ; i < this.encodingTable.length ; i++ ) {
      this.encodingTable[ i ] = table[ i ];
    }
  
    if ( changeDecoding ) {
    
      this.decodingTable = new byte[ 128 ];
      for ( int i = 0 ; i < this.decodingTable.length ; i++ ) {
        this.decodingTable[ i ] = 0;
      }

      for ( int i = 0 ; i < this.encodingTable.length ; i++ ) {
        this.decodingTable[ this.encodingTable[ i ] ] = ( byte ) i;
      }
      
    }
    
  }
  
  /**
   * Set the current fill byte to the specified one.
   * 
   * @param b The new fill byte.
   */
  public void setFillByte( final byte b ) {
    this.fillbyte = b;
  }
  
}
