package eu.geclipse.traceview.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

public class TraceCacheFile {
  private IntBuffer buffer;
  private RandomAccessFile randAccFile;

  public TraceCacheFile( final File dir, final int fileNr ) throws FileNotFoundException {
    openCacheFile( new File(dir, "cachefile_" + fileNr) );
  }

  public void close() throws IOException {
    if (randAccFile != null) randAccFile.close();
  }

  public void openCacheFile( final File file ) throws FileNotFoundException {
    randAccFile = new RandomAccessFile( file, "rw" ); //$NON-NLS-1$
  }
  
  public void enableMemoryMap() throws IOException {
    this.buffer = randAccFile.getChannel()
      .map( FileChannel.MapMode.READ_WRITE, 0, randAccFile.length() )
      .order( ByteOrder.nativeOrder() )
      .asIntBuffer();
  }

  public int read( final int offset ) throws IOException {
    int value;
    if (buffer != null) {
      value = buffer.get( offset );
    } else {
      randAccFile.seek( 4 * offset );
      value = randAccFile.readInt();
      if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
        value = swapInt( value );
      }
    }
    return value;
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
      randAccFile.seek( offset * 4 );
      randAccFile.writeInt( outVal );
    }
  }
}
