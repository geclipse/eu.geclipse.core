package eu.geclipse.eventgraph.tracereader.otf;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.ILamportEvent;
import eu.geclipse.traceview.IPhysicalEvent;
import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.utils.AbstractEvent;
import eu.geclipse.traceview.utils.IEventPartnerLogicalClockSetter;
import eu.geclipse.traceview.utils.ILamportEventClockSetter;

class Event extends AbstractEvent implements ILamportEvent,
                                IPhysicalEvent, ILamportEventClockSetter,
                                IEventPartnerLogicalClockSetter {
  private final static int typeOffset = 0;
  private final static int partnerProcessOffset = 1;
  private final static int partnerLogicalClockOffset = 2;
  private final static int partnerLamportClockOffset = 3;
  private final static int lamportClockOffset = 4;
  private final static int timestampOffset = 5;
  private int logicalClock;
  private Process processCache;

  Event( final int logicalClock, final Process processCache) {
    this.logicalClock = logicalClock;
    this.processCache = processCache;
  }

  public IProcess getProcess() {
    return this.processCache;
  }

  public int getLamportClock() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + lamportClockOffset );
  }

  public int getLogicalClock() {
    return this.logicalClock;
  }

  protected void setPartnerProcess( final int partnerProcess ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + partnerProcessOffset,
                                       partnerProcess );
  }

  public int getPartnerProcessId() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + partnerProcessOffset );
  }

  public int getPartnerLamportClock() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + partnerLamportClockOffset );
  }

  public int getPartnerLogicalClock() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + partnerLogicalClockOffset );
  }

  protected void setType( final EventType type ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + typeOffset,
                                       type.id );
  }

  public EventType getType() {
    return EventType.getEventType( this.processCache.getBuffer()
      .get( this.logicalClock * getSize() / 4 + typeOffset ) );
  }

  public void setLamportClock( final int lamportClock ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + lamportClockOffset,
                                       lamportClock );
  }

  public void setPartnerLamportClock( final int partnerLamportClock ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + partnerLamportClockOffset,
                                       partnerLamportClock );
  }

  public void setPartnerLogicalClock( final int partnerLogClock ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + partnerLogicalClockOffset,
                                       partnerLogClock );
  }

  public int getProcessId() {
    return this.processCache.getProcessId();
  }

  public int getPhysicalStartClock() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + timestampOffset );
  }

  public int getPhysicalStopClock() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + timestampOffset );
  }

  void setTimestamp( final int timestamp ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + timestampOffset,
                                       timestamp );
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
      this.processCache.getEventByLogicalClock( this.logicalClock + 1 );
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
