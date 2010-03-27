package eu.geclipse.eventgraph.tracereader.otf;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.ILamportEvent;
import eu.geclipse.traceview.IPhysicalEvent;
import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.utils.AbstractEvent;
import eu.geclipse.traceview.utils.AbstractTraceFileCache;
import eu.geclipse.traceview.utils.IEventPartnerLogicalClockSetter;
import eu.geclipse.traceview.utils.ILamportEventClockSetter;

final class Event extends AbstractEvent implements ILamportEvent,
                                IPhysicalEvent, ILamportEventClockSetter,
                                IEventPartnerLogicalClockSetter {
  private final static int typeOffset = 0;
  private final static int partnerProcessOffset = 1;
  private final static int partnerLogicalClockOffset = 2;
  private final static int partnerLamportClockOffset = 3;
  private final static int lamportClockOffset = 4;
  private final static int timestampOffset = 5;
  private final int logicalClock;
  private final Process process;

  static void addIds(AbstractTraceFileCache cache) {
    cache.addEntry( typeOffset, 2, false );
    cache.addEntry( partnerProcessOffset, cache.getBitsForMaxValue( cache.getNumberOfProcesses()-1 )+1, true );
    cache.addEntry( partnerLogicalClockOffset, cache.getBitsForMaxValue( cache.estimateMaxLogicalClock() )+2, true );
    cache.addEntry( lamportClockOffset, cache.getBitsForMaxValue( cache.estimateMaxLogicalClock() )+3, true );
    cache.addEntry( partnerLamportClockOffset, cache.getBitsForMaxValue( cache.estimateMaxLogicalClock() )+3, true );
    cache.addEntry( timestampOffset, 31, false );
  }  

  Event( final int logicalClock, final Process process) {
    this.logicalClock = logicalClock;
    this.process = process;
  }

  public IProcess getProcess() {
    return this.process;
  }

  public int getLamportClock() {
    return process.read( logicalClock, lamportClockOffset );
  }

  public int getLogicalClock() {
    return this.logicalClock;
  }

  protected void setPartnerProcess( final int partnerProcess ) {
    process.write( logicalClock, partnerProcessOffset, partnerProcess );
  }

  public int getPartnerProcessId() {
    return process.read( logicalClock, partnerProcessOffset );
  }

  public int getPartnerLamportClock() {
    return process.read( logicalClock, partnerLamportClockOffset );
  }

  public int getPartnerLogicalClock() {
    return process.read( logicalClock, partnerLogicalClockOffset );
  }

  protected void setType( final EventType type ) {
    process.write( logicalClock, typeOffset, type.id );
  }

  public EventType getType() {
    return EventType.getEventType( process.read( logicalClock, typeOffset ) );
  }

  public void setLamportClock( final int lamportClock ) {
    process.write( logicalClock, lamportClockOffset, lamportClock );
  }

  public void setPartnerLamportClock( final int partnerLamportClock ) {
    process.write( logicalClock, partnerLamportClockOffset, partnerLamportClock );
  }

  public void setPartnerLogicalClock( final int partnerLogClock ) {
    process.write( logicalClock, partnerLogicalClockOffset, partnerLogClock );
  }

  public int getProcessId() {
    return this.process.getProcessId();
  }

  public int getPhysicalStartClock() {
    return process.read( logicalClock, timestampOffset ) + process.getStartTimeOffset();
  }

  public int getPhysicalStopClock() {
    return process.read( logicalClock, timestampOffset ) + process.getStartTimeOffset();
  }

  void setTimestamp( final int timestamp ) {
    process.write( logicalClock, timestampOffset, timestamp );
  }

  int getSize() {
    return 6 * 4;
  }

  public Event getPartnerEvent() {
    // TODO Auto-generated method stub
    return null;
  }

  public Event getNextEvent() {
    Event result = null;
    try {
      this.process.getEventByLogicalClock( this.logicalClock + 1 );
    } catch( IndexOutOfBoundsException e ) {
      // ignore
    }
    return result;
  }

  public int getPartnerPhysicalStartClock() {
    Event partner = getPartnerEvent();
    int result = -1;
    if( partner != null ) {
      result = partner.getPhysicalStartClock();
    }
    return result;
  }

  public int getPartnerPhysicalStopClock() {
    Event partner = getPartnerEvent();
    int result = -1;
    if( partner != null ) {
      result = partner.getPhysicalStopClock();
    }
    return result;
  }
}
