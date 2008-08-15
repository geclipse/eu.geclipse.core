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
 *    Christof Klausecker GUP, JKU - initial API and implementation
 *    Thomas Koeckerbauer GUP, JKU - change to Java new I/O
 *****************************************************************************/

package eu.geclipse.traceview.nope.tracereader;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.ILamportEvent;
import eu.geclipse.traceview.IPhysicalEvent;
import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.ISourceLocation;
import eu.geclipse.traceview.IVectorEvent;
import eu.geclipse.traceview.utils.AbstractEvent;
import eu.geclipse.traceview.utils.ILamportEventClockSetter;

/**
 * NOPE (NOndeterministic Program Evaluator) Event
 */
public class Event extends AbstractEvent
  implements ILamportEvent, IPhysicalEvent, IVectorEvent, ISourceLocation,
  ILamportEventClockSetter
{

  /** Sub Type */
  public static final String PROP_SUBTYPE = "Event.SubType"; //$NON-NLS-1$
  /** Blocking */
  public static final String PROP_BLOCKING = "Event.Blocking"; //$NON-NLS-1$
  /** Supposed Partner Process */
  public static final String PROP_SUPPOSED_PARTNER_PROCESS = "Event.SupposedPartnerProcess"; //$NON-NLS-1$
  /** Accepted Message Type */
  public static final String PROP_ACCEPTED_MESSAGE_TYPE = "Event.AcceptedMessageType"; //$NON-NLS-1$
  /** Supposed Message Type */
  public static final String PROP_SUPPOSED_MESSAGE_TYPE = "Event.SupposedMessageType"; //$NON-NLS-1$
  /** Accepted Message Length */
  public static final String PROP_ACCEPTED_MESSAGE_LENGTH = "Event.AcceptedMessageLength"; //$NON-NLS-1$
  /** Supposed Message Type */
  public static final String PROP_SUPPOSED_MESSAGE_LENGTH = "Event.SupposedMessageLength"; //$NON-NLS-1$
  /** Source Line Ignore Count */
  public static final String PROP_SOURCE_IGNORE_COUNT = "Event.IgnoreCount"; //$NON-NLS-1$
  private static IPropertyDescriptor[] descriptors;
  private final static int typeOffset = 0;
  private final static int blockingOffset = 1;
  private final static int subTypeOffset = 2;
  private final static int acceptedMessageTypeOffset = 3;
  private final static int supposedMessageTypeOffset = 4;
  private final static int acceptedMessageLengthOffset = 5;
  private final static int supposedMessageLengthOffset = 6;
  private final static int acceptedPartnerProcessOffset = 7;
  private final static int supposedPartnerProcessOffset = 8;
  private final static int partnerLogicalClockOffset = 9;
  private final static int lamportClockOffset = 10;
  private final static int partnerLamportClockOffset = 11;
  private final static int sourceFilenameIndexOffset = 12;
  private final static int sourceLineNrOffset = 13;
  private final static int ignoreCountOffset = 14;
  private final static int timeStartOffset = 15;
  private final static int timeStopOffset = 16;
  private final static int vectorClockOffset = 17;
  private Process processCache;
  private int logicalClock;

  /**
   * Creates the Event from the processCache based on the logicalClock.
   *
   * @param logicalClock
   * @param processCache
   */
  public Event( final int logicalClock, final Process processCache ) {
    this.logicalClock = logicalClock;
    this.processCache = processCache;
  }

  // *****************************************************
  // * IPropertySource
  // *****************************************************
  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
   */
  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    if( descriptors == null ) {
      IPropertyDescriptor[] descriptors1 = super.getPropertyDescriptors();
      IPropertyDescriptor[] descriptors2 = new IPropertyDescriptor[]{
        new PropertyDescriptor( PROP_SUBTYPE, "Message Subtype" ), //$NON-NLS-1$
        new PropertyDescriptor( PROP_BLOCKING, "Blocking" ), //$NON-NLS-1$
        new PropertyDescriptor( PROP_SUPPOSED_PARTNER_PROCESS,
                                "Supposed Partner Process" ), //$NON-NLS-1$
        new PropertyDescriptor( PROP_ACCEPTED_MESSAGE_TYPE,
                                "Accepted Message Type" ), //$NON-NLS-1$
        new PropertyDescriptor( PROP_SUPPOSED_MESSAGE_TYPE,
                                "Supposed Message Type" ), //$NON-NLS-1$
        new PropertyDescriptor( PROP_ACCEPTED_MESSAGE_LENGTH,
                                "Accepted Message Length" ), //$NON-NLS-1$
        new PropertyDescriptor( PROP_SUPPOSED_MESSAGE_LENGTH,
                                "Supposed Message Length" ), //$NON-NLS-1$
        new PropertyDescriptor( PROP_SOURCE_IGNORE_COUNT, "Ignore Count" )}; //$NON-NLS-1$
      descriptors = new IPropertyDescriptor[ descriptors1.length
                                             + descriptors2.length ];
      System.arraycopy( descriptors1, 0, descriptors, 0, descriptors1.length );
      System.arraycopy( descriptors2,
                        0,
                        descriptors,
                        descriptors1.length,
                        descriptors2.length );
    }
    return descriptors;
  }

  public IProcess getProcess() {
    return this.processCache;
  }

  @Override
  public Object getPropertyValue( final Object id ) {
    Object result = super.getPropertyValue( id );
    if( result == null ) {
      if( id.equals( PROP_SUBTYPE ) )
        result = EventSubtype.getCommunicationEventName( getSubType() );
      else if( id.equals( PROP_BLOCKING ) )
        result = new Integer( getBlocking() );
      else if( id.equals( PROP_SUPPOSED_PARTNER_PROCESS ) )
        result = new Integer( getSupposedPartnerProcess() );
      else if( id.equals( PROP_ACCEPTED_MESSAGE_TYPE ) )
        result = new Integer( getAcceptedMessageType() );
      else if( id.equals( PROP_SUPPOSED_MESSAGE_TYPE ) )
        result = new Integer( getSupposedMessageType() );
      else if( id.equals( PROP_ACCEPTED_MESSAGE_LENGTH ) )
        result = new Integer( getAcceptedMessageLength() );
      else if( id.equals( PROP_SUPPOSED_MESSAGE_LENGTH ) )
        result = new Integer( getSupposedMessageLength() );
      else if( id.equals( PROP_SOURCE_IGNORE_COUNT ) )
        result = new Integer( getIgnoreCount() );
    }
    return result;
  }

  // *****************************************************
  // * IEvent
  // *****************************************************
  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IEvent#getType()
   */
  public EventType getType() {
    return EventType.getEventType( this.processCache.getBuffer()
      .get( this.logicalClock * getSize() / 4 + typeOffset ) );
  }

  protected void setType( final EventType type ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + typeOffset,
                                       type.id );
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IEvent#getProcessId()
   */
  public int getProcessId() {
    return this.processCache.getProcessId();
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IEvent#getPartnerProcessId()
   */
  public int getPartnerProcessId() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + acceptedPartnerProcessOffset );
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IEvent#getLogicalClock()
   */
  public int getLogicalClock() {
    return this.logicalClock;
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IEvent#getPartnerLogicalClock()
   */
  public int getPartnerLogicalClock() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + partnerLogicalClockOffset );
  }

  protected void setPartnerLogicalClock( final int partnerLogicalClock ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + partnerLogicalClockOffset,
                                       partnerLogicalClock );
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IEvent#getNextEvent()
   */
  public IEvent getNextEvent() {
    return this.processCache.getEventByLogicalClock( this.getLogicalClock() + 1 );
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IEvent#getPartnerEvent()
   */
  public IEvent getPartnerEvent() {
    IEvent result = null;
    if( this.getPartnerProcessId() != -1 ) {
      result = this.getProcess().getTrace().getProcess( this.getPartnerProcessId() )
        .getEventByLogicalClock( getPartnerLogicalClock() );
    }
    return result;
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IEvent#getName()
   */
  @Override
  public String getName() {
    return EventSubtype.getCommunicationEventName( getSubType() );
  }

  // *****************************************************
  // * ILamportEvent
  // *****************************************************
  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.ILamportEvent#getLamportClock()
   */
  public int getLamportClock() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + lamportClockOffset );
  }

  public void setLamportClock( final int lamportClock ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + lamportClockOffset,
                                       lamportClock );
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.ILamportEvent#getPartnerLamportClock()
   */
  public int getPartnerLamportClock() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + partnerLamportClockOffset );
  }

  public void setPartnerLamportClock( final int partnerLamportClock ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + partnerLamportClockOffset,
                                       partnerLamportClock );
  }

  // *****************************************************
  // * IPhysicalEvent
  // *****************************************************
  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IPhysicalEvent#getPhysicalStartClock()
   */
  public int getPhysicalStartClock() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + timeStartOffset );
  }

  protected void setPhysicalStartClock( final int timeStart ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + timeStartOffset,
                                       timeStart );
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IPhysicalEvent#getPhysicalStopClock()
   */
  public int getPhysicalStopClock() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + timeStopOffset );
  }

  protected void setPhysicalStopClock( final int timeStop ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + timeStopOffset,
                                       timeStop );
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IPhysicalEvent#getPartnerPhysicalStartClock()
   */
  public int getPartnerPhysicalStartClock() {
    Event partner = ( Event )getPartnerEvent();
    int result = -1;
    if( partner != null ) {
      result = partner.getPhysicalStartClock();
    }
    return result;
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IPhysicalEvent#getPartnerPhysicalStopClock()
   */
  public int getPartnerPhysicalStopClock() {
    Event partner = ( Event )getPartnerEvent();
    int result = -1;
    if( partner != null ) {
      result = partner.getPhysicalStopClock();
    }
    return result;
  }

  // *****************************************************
  // * IVectorEvent
  // *****************************************************
  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IVectorEvent#getVectorClock()
   */
  public int[] getVectorClock() {
    int[] result = new int[ getProcess().getTrace().getNumberOfProcesses() ];
    this.processCache.getBuffer().position( this.logicalClock
                                            * getSize()
                                            / 4
                                            + Event.vectorClockOffset );
    this.processCache.getBuffer().get( result );
    return result;
  }

  protected void setVectorClock( final int[] src ) {
    this.processCache.getBuffer().position( this.logicalClock
                                            * getSize()
                                            / 4
                                            + Event.vectorClockOffset );
    this.processCache.getBuffer().put( src );
  }

  // *****************************************************
  // * ISourceLocation
  // *****************************************************
  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.ISourceLocation#getSourceFilename()
   */
  public String getSourceFilename() {
    int index = this.processCache.getBuffer().get( this.logicalClock
                                                   * getSize()
                                                   / 4
                                                   + sourceFilenameIndexOffset );
    return this.processCache.getSourceFilenameForIndex( index );
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.ISourceLocation#getSourceLineNumber()
   */
  public int getSourceLineNumber() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + sourceLineNrOffset );
  }

  protected void setSourceLineNumber( final int sourceLineNr ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + sourceLineNrOffset,
                                       sourceLineNr );
  }

  // *****************************************************
  // * Event
  // *****************************************************
  /**
   * Returns the sub type of the message
   *
   * @return sub type
   */
  public int getSubType() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + subTypeOffset );
  }

  protected void setSubType( final int subType ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + subTypeOffset,
                                       subType );
  }

  /**
   * Returns the accepted partner process
   *
   * @return length of the accepted message
   */
  public int getAcceptedPartnerProcess() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + acceptedPartnerProcessOffset );
  }

  protected void setAcceptedPartnerProcess( final int acceptedPartnerProcess ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + acceptedPartnerProcessOffset,
                                       acceptedPartnerProcess );
  }

  /**
   * Returns the supposed partner process
   *
   * @return length of the accepted message
   */
  public int getSupposedPartnerProcess() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + supposedPartnerProcessOffset );
  }

  protected void setSupposedPartnerProcess( final int supposedPartnerProcess ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + supposedPartnerProcessOffset,
                                       supposedPartnerProcess );
  }

  /**
   * Returns the accepted message length
   *
   * @return length of the accepted message
   */
  public int getAcceptedMessageLength() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + acceptedMessageLengthOffset );
  }

  protected void setAcceptedMessageLength( final int acceptedMessageLength ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + acceptedMessageLengthOffset,
                                       acceptedMessageLength );
  }

  /**
   * Returns the supposed message length
   *
   * @return supposed length of the message
   */
  public int getSupposedMessageLength() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + supposedMessageLengthOffset );
  }

  protected void setSupposedMessageLength( final int supposedMessageLength ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + supposedMessageLengthOffset,
                                       supposedMessageLength );
  }

  /**
   * Returns the accepted message type
   *
   * @return type of the accepted message
   */
  public int getAcceptedMessageType() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + acceptedMessageTypeOffset );
  }

  protected void setAcceptedMessageType( final int acceptedMessageType ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + acceptedMessageTypeOffset,
                                       acceptedMessageType );
  }

  /**
   * Returns the supposed message type
   *
   * @return supposed type of the message
   */
  public int getSupposedMessageType() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + supposedMessageTypeOffset );
  }

  protected void setSupposedMessageType( final int supposedMessageType ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + supposedMessageTypeOffset,
                                       supposedMessageType );
  }

  /**
   * Returns blocking
   *
   * @return blocking
   */
  public int getBlocking() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + blockingOffset );
  }

  protected void setBlocking( final int blocking ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + blockingOffset,
                                       blocking );
  }

  /**
   * Returns the times this event already occurred
   *
   * @return event occurrence
   */
  public int getIgnoreCount() {
    return this.processCache.getBuffer().get( this.logicalClock
                                              * getSize()
                                              / 4
                                              + ignoreCountOffset );
  }

  protected void setIgnoreCount( final int ignoreCount ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + ignoreCountOffset,
                                       ignoreCount );
  }

  protected int getSize() {
    return this.processCache.getEventSize();
  }

  protected void setSourceFilenameIndex( final int sourceFilenameIndex ) {
    this.processCache.getBuffer().put( this.logicalClock
                                           * getSize()
                                           / 4
                                           + Event.sourceFilenameIndexOffset,
                                       sourceFilenameIndex );
  }

  public int getOccurrenceCount() {
    return this.getIgnoreCount();
  }
}
