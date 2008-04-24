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

package eu.geclipse.core.filesystem.internal.filesystem;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

/**
 * Implementation of an {@link InputStream} that caches the whole content
 * of a slave stream. An indirect {@link ByteBuffer} is used for caching.
 * Therefore only relatively small streams should be cached in order to
 * avoid out of memory errors.
 */
public class CachedInputStream extends InputStream {
  
  /**
   * Size of the read buffer.
   */
  private static final int BUFFER_SIZE = 1024 * 1024;
  
  /**
   * The slave stream, i.e. the stream to be cached.
   */
  private InputStream slave;
  
  /**
   * The size of the cache, e.g. the file size.
   */
  private int bufferSize;
  
  /**
   * The buffer used for caching.
   */
  private ByteBuffer buffer;
  
  /**
   * Create a new <code>CachedInputStream</code> for the specified slave
   * stream. The cache will have the specified size. The size could for
   * instance be the size of the file represented by the input stream.
   * In this case the whole file would be cached.
   * 
   * Before any operation is done on this stream {@link #cache(IProgressMonitor)}
   * has to be called in order to initialize the caching. Note that one has
   * to call {@link #discard()} in order to free the cache. A simple {@link #close()}
   * will not release the cache and system resources are not freed.
   * 
   * @param slave The input stream to be cached.
   * @param cacheSize The size of the cache.
   */
  public CachedInputStream( final InputStream slave, final int cacheSize ) {
    this.slave = slave;
    this.bufferSize = cacheSize;
  }
  
  /* (non-Javadoc)
   * @see java.io.InputStream#available()
   */
  @Override
  public int available() throws IOException {
    int result = 0;
    if ( this.buffer != null ) {
      result = this.buffer.remaining();
    }
    return result;
  }
  
  /**
   * Cache the slave stream to a local buffer.
   * 
   * @param monitor An {@link IProgressMonitor} used to monitor the caching
   * procedure.
   * @throws IOException If an error occures while reading from the slave stream.
   */
  public void cache( final IProgressMonitor monitor )
      throws IOException {
    
    SubMonitor sMonitor
      = SubMonitor.convert(
          monitor,
          Messages.getString("CachedInputStream.caching_progress"), this.bufferSize / BUFFER_SIZE //$NON-NLS-1$
      );
    
    sMonitor.subTask( Messages.getString("CachedInputStream.allocating_progress") ); //$NON-NLS-1$
    this.buffer = ByteBuffer.allocate( this.bufferSize );
    
    int totalBytesRead = 0;
    int currentBlockRead = 0;
    
    try {
    
      int bytesRead = 0;
      byte[] readBuffer = new byte[ BUFFER_SIZE ];
      
      for ( ; ; ) {
        
        if ( sMonitor.isCanceled() ) {
          break;
        }
        
        bytesRead = this.slave.read( readBuffer );
        
        if ( bytesRead < 0 ) {
          break;
        }
        
        this.buffer.put( readBuffer, 0, bytesRead );
        
        totalBytesRead += bytesRead;
        currentBlockRead += bytesRead;
        
        if ( currentBlockRead >= BUFFER_SIZE ) {
          int steps = currentBlockRead / BUFFER_SIZE;
          currentBlockRead %= BUFFER_SIZE;
          double fraction = 100. / this.bufferSize * totalBytesRead;
          sMonitor.worked( steps );
          sMonitor.subTask(
              String.format( Messages.getString("CachedInputStream.progress_format"), //$NON-NLS-1$
              Integer.valueOf( totalBytesRead / 1024 ),
              Integer.valueOf( this.bufferSize / 1024 ),
              Double.valueOf( fraction ) ) );
        }
        
      }
      
      double fraction = 100. / this.bufferSize * totalBytesRead;
      sMonitor.subTask(
          String.format( Messages.getString("CachedInputStream.progress_format"), //$NON-NLS-1$
          Integer.valueOf( totalBytesRead / 1024 ),
          Integer.valueOf( this.bufferSize / 1024 ),
          Double.valueOf( fraction ) ) );
      
    } finally {
      this.slave.close();
      this.buffer.rewind();
      sMonitor.done();
    }
    
  }
  
  /* (non-Javadoc)
   * @see java.io.InputStream#close()
   */
  @Override
  public void close() throws IOException {
    this.buffer.rewind();
  }
  
  /**
   * Discard the cache and free all corresponding system resources.
   */
  public void discard() {
    this.buffer = null;
  }
  
  /* (non-Javadoc)
   * @see java.io.InputStream#mark(int)
   */
  @Override
  public synchronized void mark( final int readlimit ) {
    if ( this.buffer != null ) {
      this.buffer.mark();
    }
  }
  
  /* (non-Javadoc)
   * @see java.io.InputStream#markSupported()
   */
  @Override
  public boolean markSupported() {
    return true;
  }

  @Override
  public int read() throws IOException {
    int result = -1;
    if ( ( this.buffer != null ) && this.buffer.hasRemaining() ) {
      result = this.buffer.get();
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see java.io.InputStream#read(byte[], int, int)
   */
  @Override
  public int read( final byte[] b, final int offset, final int length )
      throws IOException {
    
    int result = -1;
    
    if ( this.buffer != null ) {
      int remaining = this.buffer.remaining();
      if ( remaining > 0 ) {
        result = Math.min( remaining, length );
        this.buffer.get( b, offset, result );
      }
    }
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see java.io.InputStream#reset()
   */
  @Override
  public synchronized void reset() throws IOException {
    if ( this.buffer != null ) {
      this.buffer.reset();
    }
  }
  
  /* (non-Javadoc)
   * @see java.io.InputStream#skip(long)
   */
  @Override
  public long skip( final long n ) throws IOException {
    
    int result = 0;
    
    if ( this.buffer != null ) {
      int remaining = this.buffer.remaining();
      if ( remaining > 0 ) {
        result = Math.min( remaining, ( int ) n );
        int position = this.buffer.position();
        this.buffer.position( position + result );
      }
    }
    
    return result;
    
  }

}
