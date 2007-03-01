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

package eu.geclipse.gvid.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.eclipse.core.runtime.IStatus;
import eu.geclipse.gvid.Activator;
import eu.geclipse.gvid.IConnection;

/**
 * Connection class implementing a stream buffer.
 */
public class Connection implements IConnection {
  private final static int READ_BUFFER_SIZE = 2 * 1024 * 1024;
  private long bytesReceived, bytesSent, oldBytesReceived, oldBytesSent;
  private long sendSpeed, recvSpeed;
  private long lastSendMeasureTime, lastRecvMeasureTime;
  private byte[] readBuffer;
  private int readBufferDataStart = 0, bytesInBuffer;
  private InputStream inputStream;
  private OutputStream outputStream;

  /**
   * Creates a new buffering connection instance.
   * @param in stream to read the input (video) from.
   * @param out stream to write the output (events) to.
   */
  public Connection( final InputStream in, final OutputStream out ) {
    this.inputStream = in;
    this.outputStream = out;
    this.bytesReceived = 0;
    this.bytesSent = 0;
    this.oldBytesReceived = 0;
    this.oldBytesSent = 0;
    this.sendSpeed = 0;
    this.recvSpeed = 0;
    this.lastSendMeasureTime = System.currentTimeMillis();
    this.lastRecvMeasureTime = System.currentTimeMillis();
    this.readBuffer = new byte[ READ_BUFFER_SIZE ];
    this.readBufferDataStart = 0;
    this.bytesInBuffer = 0;
  }

  /**
   * @brief Writes data to the connection.
   * @param data pointer to the data.
   * @param len length of data in bytes.
   * @return number of bytes successfully written.
   * @throws IOException if an I/O error occurs.
   */
  public int writeData( final byte[] data, final int len ) throws IOException {
    this.outputStream.write( data, 0, len );
    this.bytesSent += len;
    return len;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.internal.IConnection#fillBuffer(boolean)
   */
  public void fillBuffer( final boolean blocking ) throws IOException {
    if( this.readBufferDataStart > READ_BUFFER_SIZE / 2 ) {
      System.arraycopy( this.readBuffer,
                        this.readBufferDataStart,
                        this.readBuffer,
                        0,
                        this.bytesInBuffer );
      this.readBufferDataStart = 0;
    }
    if( this.bytesInBuffer < READ_BUFFER_SIZE ) {
      if( blocking || this.inputStream.available() > 0 ) {
        int readResult = this.inputStream.read( this.readBuffer,
                                                this.readBufferDataStart
                                                    + this.bytesInBuffer,
                                                READ_BUFFER_SIZE
                                                    - this.bytesInBuffer
                                                    - this.readBufferDataStart );
        if( readResult < 0 ) {
          throw new IOException( Messages.getString("Connection.readFailed") ); //$NON-NLS-1$
        }
        this.bytesReceived += readResult;
        this.bytesInBuffer += readResult;
      }
    }
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.internal.IConnection#getNumBytesInBuffer()
   */
  public int getNumBytesInBuffer() throws IOException {
    fillBuffer( false );
    return this.bytesInBuffer;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.internal.IConnection#dropBytes(int)
   */
  public void dropBytes( final int amount ) throws IOException {
    int realAmount = amount;
    
    if( amount > this.bytesInBuffer ) {
      Activator.logMessage( IStatus.ERROR, 
                            Messages.formatMessage( "Connection.amountGreaterBufferContents", //$NON-NLS-1$
                                                     new Integer( amount ),
                                                     new Integer( this.bytesInBuffer ) ) );
      realAmount = this.bytesInBuffer;
    }
    this.bytesInBuffer -= realAmount;
    this.readBufferDataStart += realAmount;
    fillBuffer( false );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.internal.IConnection#getDataStart()
   */
  public int getDataStart() {
    return this.readBufferDataStart;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.internal.IConnection#getDataBuffer()
   */
  public byte[] getDataBuffer() {
    return this.readBuffer;
  }

  /** @see java.io.OutputStream#flush() */
  public void flush() {
    try {
      this.outputStream.flush();
    } catch( IOException ioException ) {
      Activator.logException( ioException );
    }
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.internal.IConnection#readData(byte[], int)
   */
  public int readData( final byte[] data, final int maxLen ) throws IOException {
    return readData( data, maxLen, 0 );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.internal.IConnection#readData(byte[], int, int)
   */
  public int readData( final byte[] data, final int maxLen, final int startPos )
    throws IOException {
    fillBuffer( this.bytesInBuffer == 0 );
    int amount = maxLen;
    if( this.bytesInBuffer < amount )
      amount = this.bytesInBuffer;
    System.arraycopy( this.readBuffer, this.readBufferDataStart,
                      data, startPos, amount );
    dropBytes( amount );
    return amount;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.internal.IConnection#getCurrentSendSpeed()
   */
  public long getCurrentSendSpeed() {
    long currentTime;
    long timeDiff;
    currentTime = System.currentTimeMillis();
    timeDiff = currentTime - this.lastSendMeasureTime;
    if( timeDiff > 1000 ) {
      this.lastSendMeasureTime = currentTime;
      this.sendSpeed = ( ( this.bytesSent - this.oldBytesSent ) * 1000 ) / timeDiff;
      this.oldBytesSent = this.bytesSent;
    }
    return this.sendSpeed;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.internal.IConnection#getCurrentRecvSpeed()
   */
  public long getCurrentRecvSpeed() {
    long currentTime;
    long timeDiff;
    currentTime = System.currentTimeMillis();
    timeDiff = currentTime - this.lastRecvMeasureTime;
    if( timeDiff > 1000 ) {
      this.lastRecvMeasureTime = currentTime;
      this.recvSpeed = ( ( this.bytesReceived - this.oldBytesReceived ) * 1000 ) / timeDiff;
      this.oldBytesReceived = this.bytesReceived;
    }
    return this.recvSpeed;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.internal.IConnection#readByte()
   */
  public byte readByte() throws IOException {
    byte[] b = new byte[ 1 ];
    readData( b, 1 );
    return b[ 0 ];
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.internal.IConnection#readUint16()
   */
  public short readUint16() throws IOException {
    short val = 0;
    val = ( short )( 0x000000FF & readByte() );
    val <<= 8;
    val += ( 0x000000FF & readByte() );
    return val;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.gvid.internal.IConnection#readUint32()
   */
  public int readUint32() throws IOException {
    int val = 0;
    val = ( 0x000000FF & readByte() );
    val <<= 8;
    val += ( 0x000000FF & readByte() );
    val <<= 8;
    val += ( 0x000000FF & readByte() );
    val <<= 8;
    val += ( 0x000000FF & readByte() );
    return val;
  }
}
