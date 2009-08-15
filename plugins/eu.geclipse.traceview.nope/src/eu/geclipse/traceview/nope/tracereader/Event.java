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
import eu.geclipse.traceview.utils.AbstractEvent;
import eu.geclipse.traceview.utils.ILamportEventClockSetter;

/**
 * NOPE (NOndeterministic Program Evaluator) Event
 */
public class Event extends AbstractEvent
  implements ILamportEvent, IPhysicalEvent, ISourceLocation,
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

  protected final int logicalClock;
  protected final Process process;

  /**
   * Creates the Event from the processCache based on the logicalClock.
   *
   * @param logicalClock
   * @param process
   */
  public Event( final int logicalClock, final Process process ) {
    this.logicalClock = logicalClock;
    this.process = process;
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
      descriptors = buildPropertyDescriptors();
    }
    return descriptors;
  }

  protected IPropertyDescriptor[] buildPropertyDescriptors() {
    IPropertyDescriptor[] desc;
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
    desc = new IPropertyDescriptor[ descriptors1.length
                                           + descriptors2.length ];
    System.arraycopy( descriptors1, 0, desc, 0, descriptors1.length );
    System.arraycopy( descriptors2,
                      0,
                      desc,
                      descriptors1.length,
                      descriptors2.length );
    return desc;
  }

  public IProcess getProcess() {
    return this.process;
  }

  @Override
  public Object getPropertyValue( final Object id ) {
    Object result = super.getPropertyValue( id );
    if( result == null ) {
      if( id.equals( PROP_SUBTYPE ) )
        result = EventSubtype.getCommunicationEventName( getSubType() );
      else if( id.equals( PROP_BLOCKING ) )
        result = Integer.valueOf( getBlocking() );
      else if( id.equals( PROP_SUPPOSED_PARTNER_PROCESS ) )
        result = Integer.valueOf( getSupposedPartnerProcess() );
      else if( id.equals( PROP_ACCEPTED_MESSAGE_TYPE ) )
        result = Integer.valueOf( getAcceptedMessageType() );
      else if( id.equals( PROP_SUPPOSED_MESSAGE_TYPE ) )
        result = Integer.valueOf( getSupposedMessageType() );
      else if( id.equals( PROP_ACCEPTED_MESSAGE_LENGTH ) )
        result = Integer.valueOf( getAcceptedMessageLength() );
      else if( id.equals( PROP_SUPPOSED_MESSAGE_LENGTH ) )
        result = Integer.valueOf( getSupposedMessageLength() );
      else if( id.equals( PROP_SOURCE_IGNORE_COUNT ) )
        result = Integer.valueOf( getIgnoreCount() );
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
    return EventType.getEventType( process.read( logicalClock, typeOffset ) );
  }

  protected void setType( final EventType type ) {
    process.write( logicalClock, typeOffset, type.id );
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IEvent#getProcessId()
   */
  public int getProcessId() {
    return this.process.getProcessId();
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IEvent#getPartnerProcessId()
   */
  public int getPartnerProcessId() {
    return process.read( logicalClock, acceptedPartnerProcessOffset );
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
    return process.read( logicalClock, partnerLogicalClockOffset );
  }

  protected void setPartnerLogicalClock( final int partnerLogicalClock ) {
    process.write( logicalClock, partnerLogicalClockOffset, partnerLogicalClock );
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IEvent#getNextEvent()
   */
  public IEvent getNextEvent() {
    return this.process.getEventByLogicalClock( this.getLogicalClock() + 1 );
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
    return process.read( logicalClock, lamportClockOffset );
  }

  public void setLamportClock( final int lamportClock ) {
    process.write( logicalClock, lamportClockOffset, lamportClock );
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.ILamportEvent#getPartnerLamportClock()
   */
  public int getPartnerLamportClock() {
    return process.read( logicalClock, partnerLamportClockOffset );
  }

  public void setPartnerLamportClock( final int partnerLamportClock ) {
    process.write( logicalClock, partnerLamportClockOffset, partnerLamportClock );
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
    return process.read( logicalClock, timeStartOffset ) + process.getStartTimeOffset();
  }

  protected void setPhysicalStartClock( final int timeStart ) {
    process.write( logicalClock, timeStartOffset, timeStart );
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.IPhysicalEvent#getPhysicalStopClock()
   */
  public int getPhysicalStopClock() {
    return process.read( logicalClock, timeStopOffset ) + process.getStartTimeOffset();
  }

  protected void setPhysicalStopClock( final int timeStop ) {
    process.write( logicalClock, timeStopOffset, timeStop );
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
  // * ISourceLocation
  // *****************************************************
  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.ISourceLocation#getSourceFilename()
   */
  public String getSourceFilename() {
    int index = process.read( logicalClock, sourceFilenameIndexOffset );
    return this.process.getSourceFilenameForIndex( index );
  }

  /*
   * (non-Javadoc)
   *
   * @see eu.geclipse.traceview.ISourceLocation#getSourceLineNumber()
   */
  public int getSourceLineNumber() {
    return process.read( logicalClock, sourceLineNrOffset );
  }

  protected void setSourceLineNumber( final int sourceLineNr ) {
    process.write( logicalClock, sourceLineNrOffset, sourceLineNr );
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
    return process.read( logicalClock, subTypeOffset );
  }

  protected void setSubType( final int subType ) {
    process.write( logicalClock, subTypeOffset, subType );
  }

  /**
   * Returns the accepted partner process
   *
   * @return length of the accepted message
   */
  public int getAcceptedPartnerProcess() {
    return process.read( logicalClock, acceptedPartnerProcessOffset );
  }

  protected void setAcceptedPartnerProcess( final int acceptedPartnerProcess ) {
    process.write( logicalClock, acceptedPartnerProcessOffset, acceptedPartnerProcess );
  }

  /**
   * Returns the supposed partner process
   *
   * @return length of the accepted message
   */
  public int getSupposedPartnerProcess() {
    return process.read( logicalClock, supposedPartnerProcessOffset );
  }

  protected void setSupposedPartnerProcess( final int supposedPartnerProcess ) {
    process.write( logicalClock, supposedPartnerProcessOffset, supposedPartnerProcess );
  }

  /**
   * Returns the accepted message length
   *
   * @return length of the accepted message
   */
  public int getAcceptedMessageLength() {
    return process.read( logicalClock, acceptedMessageLengthOffset );
  }

  protected void setAcceptedMessageLength( final int acceptedMessageLength ) {
    process.write( logicalClock, acceptedMessageLengthOffset, acceptedMessageLength );
  }

  /**
   * Returns the supposed message length
   *
   * @return supposed length of the message
   */
  public int getSupposedMessageLength() {
    return process.read( logicalClock, supposedMessageLengthOffset );
  }

  protected void setSupposedMessageLength( final int supposedMessageLength ) {
    process.write( logicalClock, supposedMessageLengthOffset, supposedMessageLength );
  }

  /**
   * Returns the accepted message type
   *
   * @return type of the accepted message
   */
  public int getAcceptedMessageType() {
    return process.read( logicalClock, acceptedMessageTypeOffset );
  }

  protected void setAcceptedMessageType( final int acceptedMessageType ) {
    process.write( logicalClock, acceptedMessageTypeOffset, acceptedMessageType );
  }

  /**
   * Returns the supposed message type
   *
   * @return supposed type of the message
   */
  public int getSupposedMessageType() {
    return process.read( logicalClock, supposedMessageTypeOffset );
  }

  protected void setSupposedMessageType( final int supposedMessageType ) {
    process.write( logicalClock, supposedMessageTypeOffset, supposedMessageType );
  }

  /**
   * Returns blocking
   *
   * @return blocking
   */
  public int getBlocking() {
    return process.read( logicalClock, blockingOffset );
  }

  protected void setBlocking( final int blocking ) {
    process.write( logicalClock, blockingOffset, blocking );
  }

  /**
   * Returns the times this event already occurred
   *
   * @return event occurrence
   */
  public int getIgnoreCount() {
    return process.read( logicalClock, ignoreCountOffset );
  }

  protected void setIgnoreCount( final int ignoreCount ) {
    process.write( logicalClock, ignoreCountOffset, ignoreCount );
  }

  protected void setSourceFilenameIndex( final int sourceFilenameIndex ) {
    process.write( logicalClock, sourceFilenameIndexOffset, sourceFilenameIndex );
  }

  public int getOccurrenceCount() {
    return this.getIgnoreCount();
  }
}
