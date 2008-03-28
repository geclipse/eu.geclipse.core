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

package eu.geclipse.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Repacement for {@link java.io.PipedInputStream}.
 * (because of Java bug nr. 4404700)
 */
public class PipedInputStream extends InputStream {
  private int bufferSize = 100000;
  private byte[] buffer;
  private int readPos;
  private int writePos;
  private boolean closed = false;
  
  /**
   * @see java.io.PipedInputStream#PipedInputStream(java.io.PipedOutputStream)
   * @param out instance of the replacement PipedOutputStream implementation
   * @throws IOException not thrown, for API compatibility to
   *                     java.io.PipedInputStream
   */
  public PipedInputStream( final PipedOutputStream out ) throws IOException {
    this();
    connect( out );
  }

  /**
   * @see java.io.PipedInputStream#PipedInputStream()
   */
  public PipedInputStream() {
    this.buffer = new byte[ this.bufferSize ];
    this.readPos = 0;
    this.writePos = 0;
  }

  /**
   * @see java.io.PipedInputStream#connect(java.io.PipedOutputStream)
   * @param out instance of the replacement PipedOutputStream implementation
   * @throws IOException not thrown, for API compatibility to
   *                     java.io.PipedInputStream
   */
  public void connect( final PipedOutputStream out ) throws IOException {
    out.connect( this );
  }

  /* (non-Javadoc)
   * @see java.io.InputStream#read()
   */
  @Override
  public int read() throws IOException {
    int result = -1;
    try {
      if ( !this.closed ) {
        synchronized( this ) {
          while ( this.readPos == this.writePos ) {
            notifyAll();
            wait();
            // TODO check if stream got closed
          }      
          result = this.buffer[ this.readPos ];
          this.readPos++;
          this.readPos %= this.buffer.length;
          notifyAll();
        }
      }
    } catch( InterruptedException exception ) {
      throw new IOException( exception.getLocalizedMessage() );
    }
    return result;
  }

  /* (non-Javadoc)
   * @see java.io.InputStream#read(byte[], int, int)
   */
  @Override
  public int read( final byte[] b, final int offset, final int length) throws IOException {
    int result = -1;
    int off = offset;
    int len = length;
    int realLen;
    int len1 = 0;
    if ( !this.closed ) {
      if ( offset < 0 || len < 0 || offset + length > b.length ) {
        throw new ArrayIndexOutOfBoundsException();
      }
      if ( len == 0 ) {
        result = 0;
      } else {
        synchronized( this ) {
          b[ off ] = ( byte ) read();
          // TODO check for read result -1
          off++;
          len--;
          realLen = Math.min( len , available() );
          len1 = Math.min( this.buffer.length - this.readPos, realLen );
          System.arraycopy( this.buffer, this.readPos, b, off, len1 );
          this.readPos += len1;
          this.readPos %= this.buffer.length;
          int len2 = realLen - len1;
          if ( len2 > 0 ) {
            off += len1;
            System.arraycopy( this.buffer, this.readPos, b, off, len2 );
            this.readPos += len2;
            this.readPos %= this.buffer.length;
          }
          notifyAll();
          result = realLen + 1; 
        }
      }
    }
    return result;
  }

  /* (non-Javadoc)
   * @see java.io.InputStream#available()
   */
  @Override
  public int available() {
    int result;
    synchronized( this ) {
      result = ( this.bufferSize + this.writePos - this.readPos ) % this.bufferSize;
    }
    return result;
  }

  /* (non-Javadoc)
   * @see java.io.InputStream#close()
   */
  @Override
  public void close() {
    synchronized( this ) {
      this.closed = true;
    }
  }

  /**
   * Writes a single byte to the internal ring buffer, should only be called by
   * PipedOutputStream.
   * @param b the byte to write
   * @throws IOException thrown if an {@link InterruptedException} occurs.
   */
  void write( final int b ) throws IOException {
    try {
      synchronized( this ) {
        while ( this.readPos == (this.writePos+1) % this.buffer.length ) {
          notifyAll();
          wait();
        }
        this.buffer[ this.writePos ] = (byte) b;
        this.writePos++;
        this.writePos %= this.buffer.length;
        notifyAll();
      }
    } catch( InterruptedException exception ) {
      throw new IOException( exception.getLocalizedMessage() );
    }
  }

  /**
   * Writes several bytes to the internal ring buffer, should only be called by
   * PipedOutputStream.
   * @param b array containing the bytes to write.
   * @param offset start offset of the write operation.
   * @param length number of bytes to write.
   * @throws IOException thrown if an {@link InterruptedException} occurs.
   */
  void write( final byte[] b, final int offset, final int length ) throws IOException {
    try {
      int off = offset;
      int len = length;
      synchronized( this ) {
        do {
          int spaceAvail = this.buffer.length - available() - 1;
          if ( spaceAvail > 0 ) {
            int writeLen = Math.min( len, spaceAvail );
            int writeOnceLen = Math.min( this.buffer.length - this.writePos, writeLen );
            System.arraycopy( b, off, this.buffer, this.writePos, writeOnceLen );
            off += writeOnceLen;
            len -= writeOnceLen;
            this.writePos += writeOnceLen;
            this.writePos %= this.buffer.length;
          } else {
            notifyAll();
            wait();
          }
        } while( len > 0 );
        notifyAll();
      }
    } catch( InterruptedException exception ) {
      throw new IOException( exception.getLocalizedMessage() );
    }
  }
}
