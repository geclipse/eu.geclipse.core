/*****************************************************************************
 * Copyright (c) 2009, 2010 g-Eclipse Consortium 
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
 *    Christof Klausecker MNM-Team, LMU Munich - improved event support
 *****************************************************************************/

package eu.geclipse.eventgraph.tracereader.otf;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.ILamportEvent;
import eu.geclipse.traceview.IPhysicalEvent;
import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.ISourceLocation;
import eu.geclipse.traceview.utils.AbstractEvent;
import eu.geclipse.traceview.utils.AbstractTraceFileCache;
import eu.geclipse.traceview.utils.IEventPartnerLogicalClockSetter;
import eu.geclipse.traceview.utils.ILamportEventClockSetter;

/**
 * OTF Event
 */
@SuppressWarnings("boxing")
public class Event extends AbstractEvent implements ILamportEvent, IPhysicalEvent, ISourceLocation, ILamportEventClockSetter, IEventPartnerLogicalClockSetter {

  private static final String PROP_FUNCTION_NAME = "OTF.FunctionName"; //$NON-NLS-1$
  private static final String PROP_FUNCTION_GROUP_NAME = "OTF.FunctionGroupName"; //$NON-NLS-1$
  private static final String PROP_PROCESS_GROUP_NAME = "OTF.MessageGroupName"; //$NON-NLS-1$
  private static final String PROP_SENT_MESSAGE_LENGTH = "OTF.SentMessageLength"; //$NON-NLS-1$
  private static final String PROP_RECEIVED_MESSAGE_LENGTH = "OTF.ReceivedMessageLength"; //$NON-NLS-1$
  private static final String PROP_MESSAGE_TYPE = "OTF.MessageType"; //$NON-NLS-1$
  // Trace Viewer internal Event Type
  private final static int typeOffset = 0;
  // Function ID
  private final static int functionIdOffset = 1;
  // Partner Information
  private final static int partnerProcessIdOffset = 2;
  private final static int partnerLogicalClockOffset = 3;
  private final static int partnerLamportClockOffset = 4;
  // Time Information
  private final static int lamportClockOffset = 5;
  private final static int timeStartOffset = 6;
  private final static int timeStopOffset = 7;
  // Message Information
  private final static int messageLengthSentOffset = 8;
  private final static int messageLengthReceivedOffset = 9;
  private final static int messageTypeOffset = 10;
  private final static int processGroupOffset = 11;
  // Properties
  private static IPropertyDescriptor[] otfEventPropertyDescriptors = new IPropertyDescriptor[]{
    new PropertyDescriptor( PROP_FUNCTION_NAME, "Function Name" ), //$NON-NLS-1$
    new PropertyDescriptor( PROP_FUNCTION_GROUP_NAME, "Function Group" ), //$NON-NLS-1$
    new PropertyDescriptor( PROP_PROCESS_GROUP_NAME, "Process Group Name" ), //$NON-NLS-1$
    new PropertyDescriptor( PROP_SENT_MESSAGE_LENGTH, "Sent Message Length" ), //$NON-NLS-1$
    new PropertyDescriptor( PROP_RECEIVED_MESSAGE_LENGTH, "Received Message Length" ), //$NON-NLS-1$
    new PropertyDescriptor( PROP_MESSAGE_TYPE, "Message Type" ), //$NON-NLS-1$
  };
  private final int logicalClock;
  private final Process process;

  Event( final int logicalClock, final Process process ) {
    this.logicalClock = logicalClock;
    this.process = process;
  }

  // TODO determine bit sizes
  static void addIds( final AbstractTraceFileCache cache, final OTFDefinitionReader otfDefinitionReader  ) {
    int maxType = 0;
    for (EventType type : EventType.values()){
      maxType = Math.max( maxType, type.id);
    }
    cache.addEntry( typeOffset, cache.getBitsForMaxValue( maxType ), false );
    cache.addEntry( functionIdOffset, cache.getBitsForMaxValue( otfDefinitionReader.getMaxFunctionID()), false );
    cache.addEntry( partnerProcessIdOffset, cache.getBitsForMaxValue( cache.getNumberOfProcesses() - 1 ) + 1, true );
    cache.addEntry( partnerLogicalClockOffset, cache.getBitsForMaxValue( cache.estimateMaxLogicalClock() ) + 2, true ); // XXX Wild guess?
    cache.addEntry( partnerLamportClockOffset, cache.getBitsForMaxValue( cache.estimateMaxLogicalClock() ) + 3, true ); // XXX Wild guess?
    cache.addEntry( lamportClockOffset, cache.getBitsForMaxValue( cache.estimateMaxLogicalClock() ) + 3, true ); // XXX Wild guess?
    cache.addEntry( timeStartOffset, 31, false ); //Integer.MAX_VALUE
    cache.addEntry( timeStopOffset, 31, false ); //Integer.MAX_VALUE
    cache.addEntry( messageLengthSentOffset, 31, false ); //Integer.MAX_VALUE
    cache.addEntry( messageLengthReceivedOffset, 31, false ); //Integer.MAX_VALUE
    cache.addEntry( messageTypeOffset, 31, true );
    cache.addEntry( processGroupOffset, cache.getBitsForMaxValue( otfDefinitionReader.getMaxProcessGroupID()), false );
  }

  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    IPropertyDescriptor[] abstractPropertyDescriptors = super.getPropertyDescriptors();
    IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[ abstractPropertyDescriptors.length + otfEventPropertyDescriptors.length ];
    System.arraycopy( abstractPropertyDescriptors, 0, propertyDescriptors, 0, abstractPropertyDescriptors.length );
    System.arraycopy( otfEventPropertyDescriptors, 0, propertyDescriptors, abstractPropertyDescriptors.length, otfEventPropertyDescriptors.length );
    return propertyDescriptors;
  }

  @Override
  public Object getPropertyValue( final Object id ) {
    Object result = null;
    // SourceLocation sourceLocation = ((OTFReader)this.process.getTrace()).getDefinition().getSourceLocation( getFunctionId() );
    if( PROP_FUNCTION_NAME.equals( id ) ) {
      OTFDefinitionReader otfDefinitionReader = ( ( OTFReader )this.process.getTrace() ).getDefinition();
      result = otfDefinitionReader.getFunctionName( getFunctionId() );
    } else if( PROP_FUNCTION_GROUP_NAME.equals( id ) ) {
      OTFDefinitionReader otfDefinitionReader = ( ( OTFReader )this.process.getTrace() ).getDefinition();
      int groupId = otfDefinitionReader.getFunctionGroupID( getFunctionId() );
      result = otfDefinitionReader.getFunctionGroupName( groupId );
    } else if( PROP_PROCESS_GROUP_NAME.equals( id ) ) {
      OTFDefinitionReader otfDefinitionReader = ( ( OTFReader )this.process.getTrace() ).getDefinition();
      result = otfDefinitionReader.getProcessGroupName( getProcessGroup() );
    } 
    else if( PROP_MESSAGE_TYPE.equals( id ) ) {
      result = getMessageType();
    }
    else if( PROP_SENT_MESSAGE_LENGTH.equals( id ) ) {
      result = getSentMessageLength();
    }
    else if( PROP_RECEIVED_MESSAGE_LENGTH.equals( id ) ) {
      result = getReceivedMessageLength();
    }
    else if( PROP_SOURCE_LINE.equals( id ) ) {
      OTFDefinitionReader otfDefinitionReader = ( ( OTFReader )this.process.getTrace() ).getDefinition();
      int sourceLocationID = otfDefinitionReader.getSourceLocationID( getFunctionId());
      if(sourceLocationID != -1){
        result = otfDefinitionReader.getSourceLineNumber( sourceLocationID );
      }
    }
    else if( PROP_SOURCE_FILE.equals( id ) ) {
      OTFDefinitionReader otfDefinitionReader = ( ( OTFReader )this.process.getTrace() ).getDefinition();
      int sourceLocationID = otfDefinitionReader.getSourceLocationID( getFunctionId());
      if(sourceLocationID != -1){
        int sourceFileID = otfDefinitionReader.getSourceFileID( sourceLocationID );
        result = otfDefinitionReader.getSourceFileName( sourceFileID );
      }
    }
    else {
      result = super.getPropertyValue( id );
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.IEvent#getProcess()
   */
  public IProcess getProcess() {
    return this.process;
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.IEvent#getProcessId()
   */
  public int getProcessId() {
    return this.process.getProcessId();
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.IEvent#getLogicalClock()
   */
  public int getLogicalClock() {
    return this.logicalClock;
  }

  /**
   * Returns the Function Id for the Event
   * 
   * @return Id of the Function
   */
  public int getFunctionId() {
    return this.process.read( this.logicalClock, functionIdOffset );
  }

  protected void setFunctionId( final int function ) {
    this.process.write( this.logicalClock, functionIdOffset, function );
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.IEvent#getType()
   */
  public EventType getType() {
    return EventType.getEventType( this.process.read( this.logicalClock, typeOffset ) );
  }

  protected void setType( final EventType type ) {
    this.process.write( this.logicalClock, typeOffset, type.id );
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.IEvent#getPartnerProcessId()
   */
  public int getPartnerProcessId() {
    return this.process.read( this.logicalClock, partnerProcessIdOffset );
  }

  protected void setPartnerProcessId( final int partnerProcess ) {
    this.process.write( this.logicalClock, partnerProcessIdOffset, partnerProcess );
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.IEvent#getPartnerLogicalClock()
   */
  public int getPartnerLogicalClock() {
    return this.process.read( this.logicalClock, partnerLogicalClockOffset );
  }

  public void setPartnerLogicalClock( final int partnerLogClock ) {
    this.process.write( this.logicalClock, partnerLogicalClockOffset, partnerLogClock );
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.ILamportEvent#getPartnerLamportClock()
   */
  public int getPartnerLamportClock() {
    return this.process.read( this.logicalClock, partnerLamportClockOffset );
  }

  public void setPartnerLamportClock( final int partnerLamportClock ) {
    this.process.write( this.logicalClock, partnerLamportClockOffset, partnerLamportClock );
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.ILamportEvent#getLamportClock()
   */
  public int getLamportClock() {
    return this.process.read( this.logicalClock, lamportClockOffset );
  }

  public void setLamportClock( final int lamportClock ) {
    this.process.write( this.logicalClock, lamportClockOffset, lamportClock );
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.IPhysicalEvent#getPhysicalStartClock()
   */
  public int getPhysicalStartClock() {
    return this.process.read( this.logicalClock, timeStartOffset ) + this.process.getStartTimeOffset();
  }

  void setPhysicalStartClock( final int timestamp ) {
    this.process.write( this.logicalClock, timeStartOffset, timestamp );
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.IPhysicalEvent#getPhysicalStopClock()
   */
  public int getPhysicalStopClock() {
    return this.process.read( this.logicalClock, timeStopOffset ) + this.process.getStartTimeOffset();
  }

  void setPhysicalStopClock( final int timestamp ) {
    this.process.write( this.logicalClock, timeStopOffset, timestamp );
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.utils.AbstractEvent#getName()
   */
  @Override
  public String getName() {
    return ( ( OTFReader )this.process.getTrace() ).getFunctionName( getFunctionId() );
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.IEvent#getPartnerEvent()
   */
  public Event getPartnerEvent() {
    return null;
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.IEvent#getNextEvent()
   */
  public Event getNextEvent() {
    Event result = null;
    try {
      result = this.process.getEventByLogicalClock( this.logicalClock + 1 );
    } catch( IndexOutOfBoundsException e ) {
      // ignore
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.IPhysicalEvent#getPartnerPhysicalStartClock()
   */
  public int getPartnerPhysicalStartClock() {
    Event partner = getPartnerEvent();
    int result = -1;
    if( partner != null ) {
      result = partner.getPhysicalStartClock();
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.IPhysicalEvent#getPartnerPhysicalStopClock()
   */
  public int getPartnerPhysicalStopClock() {
    Event partner = getPartnerEvent();
    int result = -1;
    if( partner != null ) {
      result = partner.getPhysicalStopClock();
    }
    return result;
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.ISourceLocation#getOccurrenceCount()
   */
  public int getOccurrenceCount() {
    return 0;
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.ISourceLocation#getSourceFilename()
   */
  public String getSourceFilename() {
    // return ((OTFReader)this.process.getTrace()).getDefinition().getSourceLocation( get )
    return null;
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.ISourceLocation#getSourceLineNumber()
   */
  public int getSourceLineNumber() {
    // TODO Auto-generated method stub
    return 0;
  }

  /*
   * (non-Javadoc)
   * @see eu.geclipse.traceview.utils.AbstractEvent#toString()
   */
  @Override
  public String toString() {
    return getName();
  }

  public int getSentMessageLength() {
    return this.process.read( this.logicalClock, messageLengthSentOffset );
  }

  protected void setSentMessageLength( final int length ) {
    this.process.write( this.logicalClock, messageLengthSentOffset, length );
  }
  
  public int getReceivedMessageLength() {
    return this.process.read( this.logicalClock, messageLengthReceivedOffset);
  }

  protected void setReceivedMessageLength( final int length ) {
    this.process.write( this.logicalClock, messageLengthReceivedOffset, length );
  }

  public int getMessageType() {
    return this.process.read( this.logicalClock, messageTypeOffset );
  }

  protected void setMessageType( final int type ) {
    this.process.write( this.logicalClock, messageTypeOffset, type );
  }

  public int getProcessGroup() {
    return this.process.read( this.logicalClock, processGroupOffset );
  }

  protected void setMessageGroup( final int group ) {
    this.process.write( this.logicalClock, processGroupOffset, group );
  }
}
