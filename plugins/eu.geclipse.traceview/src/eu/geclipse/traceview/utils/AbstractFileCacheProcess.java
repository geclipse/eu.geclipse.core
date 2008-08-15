/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
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

package eu.geclipse.traceview.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

import eu.geclipse.traceview.ITrace;

public abstract class AbstractFileCacheProcess extends AbstractProcess {

  protected final static int sizeInc = 1000;
  protected IntBuffer buffer;
  protected int eventSize;
  protected RandomAccessFile file;
  protected File cacheDir;
  protected int processId;
  protected List<String> sourceFilenames;
  protected ITrace trace;

  
  public AbstractFileCacheProcess(final ITrace trace, final int processId, final List<String> sourceFilenames, final File cacheDir) {
   this.trace = trace;
   this.processId = processId;
   this.cacheDir = cacheDir;
   this.sourceFilenames = sourceFilenames;
  }
  
  public int getMaximumLogicalClock() {
    return (this.buffer.limit() / ( this.eventSize / 4 )) - 1;
  }

  public int getProcessId() {
    return this.processId;
  }

  public String getSourceFilenameForIndex( final int index ) {
    return this.sourceFilenames.get( index );
  }

  public IntBuffer getBuffer() {
    return this.buffer;
  }

  public ITrace getTrace() {
    return this.trace;
  }

  public int getEventSize() {
    return this.eventSize;
  }

  public void checkCacheLimit( final int logClock ) throws IOException {
    if( getMaximumLogicalClock() < logClock ) {
      if( logClock < this.buffer.capacity() / ( this.eventSize / 4 ) ) {
        this.buffer.limit( ( logClock + 1 ) * ( this.eventSize / 4 ) );
      } else {
        this.buffer = this.file.getChannel()
          .map( FileChannel.MapMode.READ_WRITE,
                0,
                ( logClock + sizeInc ) * this.eventSize )
          .order( ByteOrder.nativeOrder() )
          .asIntBuffer();
        this.buffer.limit( ( logClock + 1 ) * ( this.eventSize / 4 ) );
      }
    }
  }

  public void truncateToTraceSize() throws IOException {
    int nrOfEvents = getMaximumLogicalClock() + 1;
    this.buffer = null;
    File out = new File( this.cacheDir, Integer.toString( this.processId ) );
    RandomAccessFile ran = new RandomAccessFile( out, "rw" ); //$NON-NLS-1$
    this.file.getChannel().transferTo( 0, this.file.length(), ran.getChannel() );
    ran.setLength( nrOfEvents * this.eventSize );
    this.buffer = ran.getChannel()
      .map( FileChannel.MapMode.READ_WRITE, 0, ran.length() )
      .order( ByteOrder.nativeOrder() )
      .asIntBuffer();
  }
}
