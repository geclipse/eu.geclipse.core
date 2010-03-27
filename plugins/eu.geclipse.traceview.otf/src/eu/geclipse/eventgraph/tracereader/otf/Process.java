package eu.geclipse.eventgraph.tracereader.otf;

import java.io.IOException;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.utils.AbstractProcessFileCache;

final class Process extends AbstractProcessFileCache {

  private int logClock = 0;

  Process( final int processId, final OTFReader trace ) throws IOException {
    super( trace, processId ); //$NON-NLS-1$
  }

  void appendEvent( final EventType type,
                    final int partnerId,
                    final int timestamp ) throws IOException
  {
    setMaximumLogicalClock( this.logClock );
    Event event = new Event( this.logClock, this );
    event.setType( type );
    event.setPartnerProcess( partnerId );
    event.setTimestamp( timestamp );
    event.setLamportClock( -1 );
    event.setPartnerLamportClock( -1 );
    this.logClock++;
  }

  public IEvent getEventByLogicalClock( final int index )
    throws IndexOutOfBoundsException {
    return new Event( index, this );
  }
}
