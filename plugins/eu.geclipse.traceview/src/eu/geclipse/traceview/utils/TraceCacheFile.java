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

package eu.geclipse.traceview.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

import eu.geclipse.traceview.internal.BufferedRandomAccessFile;

final public class TraceCacheFile {
  private IntBuffer buffer;
  final private BufferedRandomAccessFile randAccFile;

  public TraceCacheFile( final File dir, final int fileNr ) throws FileNotFoundException {
    File file = new File(dir, "cachefile_" + fileNr);
    randAccFile = new BufferedRandomAccessFile( file, "rw" ); //$NON-NLS-1$
  }

  public void close() throws IOException {
    if (randAccFile != null) randAccFile.close();
  }
  
  public void enableMemoryMap() throws IOException {
    this.buffer = randAccFile.getChannel()
      .map( FileChannel.MapMode.READ_WRITE, 0, randAccFile.length() )
      .order( ByteOrder.nativeOrder() )
      .asIntBuffer();
    randAccFile.close();
  }

  public int read( final int offset ) throws IOException {
    int value;
    if (buffer != null) {
      value = buffer.get( offset );
    } else {
      synchronized( randAccFile ) {
        randAccFile.seek( 4 * offset );
        value = randAccFile.readInt();
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
          value = swapInt( value );
        }
      }
    }
    return value;
  }

  public void read( final int offset, final int[] data ) throws IOException {
    if (buffer != null) {
      synchronized( buffer ) {
    	for (int i = 0; i < data.length; i++) {
    	  data[i] = buffer.get( offset + i );
    	}
      }
    } else {
      synchronized( randAccFile ) {
        randAccFile.seek( offset * 4 );
        for (int i = 0; i < data.length; i++) {
          int value = randAccFile.readInt();
          if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            value = swapInt( value );
          }
          data[i] = value;
        }
      }
    }
  }

  private static int swapInt( final int value ) {
    int b1 = (value >>  0) & 0xff;
    int b2 = (value >>  8) & 0xff;
    int b3 = (value >> 16) & 0xff;
    int b4 = (value >> 24) & 0xff;

    return b1 << 24 | b2 << 16 | b3 << 8 | b4 << 0;
  }

  public void write( final int offset, final int value ) throws IOException {
    if (buffer != null) {
      buffer.put( offset, value );
    } else {
      int outVal = value;
      if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
        outVal = swapInt( value );
      }
      synchronized( randAccFile ) {
        randAccFile.seek( offset * 4 );
        randAccFile.writeInt( outVal );
      }
    }
  }

  public void write( final int offset, final int[] value ) throws IOException {
    if (buffer != null) {
      synchronized( buffer ) {
        buffer.position( offset );
        buffer.put( value, 0, value.length );
      }
    } else {
      synchronized( randAccFile ) {
        randAccFile.seek( offset * 4 );
        for (int i = 0; i < value.length; i++) {
          int outVal = value[i];
          if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            outVal = swapInt( outVal );
          }
          randAccFile.writeInt( outVal );
        }
      }
    }
  }
}
