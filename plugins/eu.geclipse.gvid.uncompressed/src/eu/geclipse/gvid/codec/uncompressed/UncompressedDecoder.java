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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.gvid.codec.uncompressed;

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import eu.geclipse.gvid.IConnection;
import eu.geclipse.gvid.IDecoder;
import eu.geclipse.gvid.IEvents;

/**
 * Sample implementation of a video decoder for uncompressed video.
 */
public class UncompressedDecoder implements IDecoder {

  private IConnection connection;
  private IEvents events;
  private BufferedImage image;
  private byte[] imageData;
  private int framesDecoded;

  /**
   * Creates an instance of the "uncompressed video decoder".
   */
  public UncompressedDecoder() {
    this.image = new BufferedImage( 100, 100, BufferedImage.TYPE_4BYTE_ABGR );
    this.framesDecoded = 0;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.IDecoder#decodeNextFrame()
   */
  public boolean decodeNextFrame() throws IOException {
    boolean result = false;
    int x, y;
    int bytesPerPixel = 4;
    if ( this.connection.getNumBytesInBuffer() != 0 ) {
      x = this.connection.readUint32();
      y = this.connection.readUint32();
      if( this.image.getWidth() != x || this.image.getHeight() != y ) {
        int size = x * y * bytesPerPixel;
        this.imageData = new byte[ size ];
        DataBuffer dataBuffer = new DataBufferByte( this.imageData, size );
        ColorModel colorModel = new ComponentColorModel( ColorSpace.getInstance( ColorSpace.CS_sRGB ),
                                                         true,
                                                         true,
                                                         Transparency.OPAQUE,
                                                         DataBuffer.TYPE_BYTE );
        WritableRaster raster = Raster.createInterleavedRaster( dataBuffer,
                                                                x,
                                                                y,
                                                                x * bytesPerPixel,
                                                                bytesPerPixel,
                                                                new int[]{
                                                                  0, 1, 2, 3
                                                                },
                                                                null );
        this.image = new BufferedImage( colorModel, raster, false, null );
      }
      int data = 0;
      do {
        data += this.connection.readData( this.imageData,
                                          x * y * bytesPerPixel - data,
                                          data );
      } while( data != x * y * bytesPerPixel );
      this.framesDecoded++;
      this.events.sendFrameFinished( this.framesDecoded, 0, 0 );
      result = true;
    }
    return result;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.IDecoder#getImage()
   */
  public BufferedImage getImage() {
    return this.image;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.IDecoder#getXSize()
   */
  public int getXSize() {
    return this.image.getWidth();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.IDecoder#getYSize()
   */
  public int getYSize() {
    return this.image.getHeight();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.IDecoder#init(eu.geclipse.gvid.IConnection, eu.geclipse.gvid.IEvents)
   */
  public void init( final IConnection conn, final IEvents ev ) {
    this.connection = conn;
    this.events = ev;
  }
}
