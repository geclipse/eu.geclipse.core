/*****************************************************************************
 * Copyright (c) 2009 g-Eclipse Consortium 
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
 *    Thomas Koeckerbauer MNM-Team, LMU Munich - initial API and implementation
 *****************************************************************************/

package eu.geclipse.traceview.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * This class is a buffered version of java.io.RandomAccessFile.
 * It is not a subclass of RandomAccessFile and it does not behave exactly like
 * RandomAccessFile. It only implements enough to make TraceFileCache happy.
 */
public class BufferedRandomAccessFile {
  private final static int BUFFER_SIZE = 64*1024;
  private final static int PRE_SEEK_BUFFER = 2*1024;
  private byte[] buffer = new byte[BUFFER_SIZE];
  private boolean dirty = false;
  private long startOffset = 0;
  private int size = 0;
  private int bufferPos = 0;
  private RandomAccessFile file;

  public BufferedRandomAccessFile( File file, String mode ) throws FileNotFoundException {
    this.file = new RandomAccessFile( file, mode );
    try {
      this.seek(0);
    } catch( IOException e ) {
      throw new FileNotFoundException( e.getMessage() );
    }
  }

  public void write( int b ) throws IOException {
    if (this.bufferPos == BUFFER_SIZE) seek(this.startOffset + BUFFER_SIZE);
    this.buffer[this.bufferPos++] = (byte) b;
    if (this.size < this.bufferPos) this.size = this.bufferPos;
    this.dirty = true;
  }
  
  public int read() throws IOException {
    if (this.bufferPos == BUFFER_SIZE) seek(this.startOffset + BUFFER_SIZE);
    return this.buffer[this.bufferPos++];
  }
  
  public void close() throws IOException {
    flush();
    this.file.close();
  }
  
  public void seek( long pos ) throws IOException {
    if ( pos < this.startOffset || pos >= this.startOffset + BUFFER_SIZE ) {
      // pos not in buffer
      flush();
      this.startOffset = pos - PRE_SEEK_BUFFER;
      this.bufferPos = PRE_SEEK_BUFFER;
      if (this.startOffset < 0) {
        this.startOffset = 0;
        this.bufferPos = (int) pos;
      }
      if ( this.file.length() > this.startOffset ) {
        // startOffset inside of file
        this.file.seek( this.startOffset );
        this.size = this.file.read( this.buffer );
      } else {
        // startOffset outside of file
        this.size = 0;
      }
    } else {
      // pos in buffer
      this.bufferPos = (int) (pos - this.startOffset);
    }
  }

  public void flush() throws IOException {
    if ( this.dirty ) {
      this.file.seek( this.startOffset );
      this.file.write( this.buffer, 0, this.size );
      this.dirty = false;
    }
  }

  public FileChannel getChannel() throws IOException {
    flush();
    return this.file.getChannel();
  }

  public long length() throws IOException {
    return this.file.length();
  }

  public int readInt() throws IOException {
    int a = this.read();
    int b = this.read();
    int c = this.read();
    int d = this.read();
    return ( a << 24 ) + ( b << 16 ) + ( c << 8 ) + d ;
  }

  public void writeInt( int value ) throws IOException {
    write( ( value >>> 24 ) & 0xFF );
    write( ( value >>> 16 ) & 0xFF );
    write( ( value >>> 8 ) & 0xFF );
    write( value & 0xFF );
  }
}
