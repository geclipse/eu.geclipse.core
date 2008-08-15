package eu.geclipse.eventgraph.tracereader.otf;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.LinkedList;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.utils.AbstractFileCacheProcess;

class Process extends AbstractFileCacheProcess {

  private int logClock = 0;

  Process( final int processId, final OTFReader trace ) throws IOException {
    super( trace, processId, new LinkedList<String>(), new File( "/tmp" ) ); //$NON-NLS-1$
//    this.processCache = new FileProcessCache( new File( "/tmp" ), //$NON-NLS-1$
//                                              trace,
//                                              processId,
//                                              false,
//                                              new LinkedList<String>() );
    
    this.eventSize = 17 * 4;
    if( false ) {
      this.file = new RandomAccessFile( new File( this.cacheDir,
                                                  Integer.toString( processId ) ),
                                        "rw" ); //$NON-NLS-1$
      this.buffer = this.file.getChannel()
        .map( FileChannel.MapMode.READ_WRITE, 0, this.file.length() )
        .order( ByteOrder.nativeOrder() )
        .asIntBuffer();
      this.buffer.limit( ( int )this.file.length() / 4 );
    } else {
      this.file = new RandomAccessFile( new File( this.cacheDir,
                                                  Integer.toString( processId )
                                                      + "_workaround" ), "rw" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.buffer = this.file.getChannel()
        .map( FileChannel.MapMode.READ_WRITE, 0, this.eventSize * sizeInc )
        .order( ByteOrder.nativeOrder() )
        .asIntBuffer();
      this.buffer.limit( 0 );
    }
    
  }

  void appendEvent( final EventType type,
                    final int partnerId,
                    final int timestamp ) throws IOException
  {
    this.checkCacheLimit( this.logClock );
    Event event = new Event( this.logClock, this );
    event.setType( type );
    event.setPartnerProcess( partnerId );
    event.setTimestamp( timestamp );
    event.setLamportClock( -1 );
    event.setPartnerLamportClock( -1 );
    this.logClock++;
  }

  void doneReading() throws IOException {
    this.truncateToTraceSize();
  }

  public IEvent getEventByLogicalClock( final int index )
    throws IndexOutOfBoundsException {
    return new Event( index, this );
  }
}
