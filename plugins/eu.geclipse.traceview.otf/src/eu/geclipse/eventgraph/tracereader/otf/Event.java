/*****************************************************************************
 * Copyright (c) 2009 g-Eclipse Consortium 
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
import eu.geclipse.traceview.utils.AbstractEvent;
import eu.geclipse.traceview.utils.AbstractTraceFileCache;
import eu.geclipse.traceview.utils.IEventPartnerLogicalClockSetter;
import eu.geclipse.traceview.utils.ILamportEventClockSetter;

final class Event extends AbstractEvent implements ILamportEvent, IPhysicalEvent, ILamportEventClockSetter, IEventPartnerLogicalClockSetter {

  private static final String PROP_OTF_FUNCTIONID = "OTF.FunctionId"; //$NON-NLS-1$
  private final static int typeOffset = 0;
  private final static int functionIdOffset = 1;
  private final static int partnerProcessIdOffset = 2;
  private final static int partnerLogicalClockOffset = 3;
  private final static int partnerLamportClockOffset = 4;
  private final static int lamportClockOffset = 5;
  private final static int timeStartOffset = 6;
  private final static int timeStopOffset = 7;
  private static IPropertyDescriptor[] otfEventPropertyDescriptors = new IPropertyDescriptor[]{
    new PropertyDescriptor( PROP_OTF_FUNCTIONID, "OTF Function Id" )}; //$NON-NLS-1$
  private final int logicalClock;
  private final Process process;

  Event( final int logicalClock, final Process process ) {
    this.logicalClock = logicalClock;
    this.process = process;
  }

  static void addIds( final AbstractTraceFileCache cache ) {
    cache.addEntry( typeOffset, 2, false );
    cache.addEntry( functionIdOffset, 31, false ); // determine bit size
    cache.addEntry( partnerProcessIdOffset, cache.getBitsForMaxValue( cache.getNumberOfProcesses() - 1 ) + 1, true );
    cache.addEntry( partnerLogicalClockOffset, cache.getBitsForMaxValue( cache.estimateMaxLogicalClock() ) + 2, true );
    cache.addEntry( partnerLamportClockOffset, cache.getBitsForMaxValue( cache.estimateMaxLogicalClock() ) + 3, true );
    cache.addEntry( lamportClockOffset, cache.getBitsForMaxValue( cache.estimateMaxLogicalClock() ) + 3, true );
    cache.addEntry( timeStartOffset, 31, false );
    cache.addEntry( timeStopOffset, 31, false );
  }

  @Override
  public IPropertyDescriptor[] getPropertyDescriptors() {
    IPropertyDescriptor[] abstractPropertyDescriptors = super.getPropertyDescriptors();
    IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[ abstractPropertyDescriptors.length + 1 ];
    System.arraycopy( abstractPropertyDescriptors, 0, propertyDescriptors, 0, abstractPropertyDescriptors.length );
    System.arraycopy( otfEventPropertyDescriptors, 0, propertyDescriptors, abstractPropertyDescriptors.length, otfEventPropertyDescriptors.length );
    return propertyDescriptors;
  }

  @SuppressWarnings("boxing")
  @Override
  public Object getPropertyValue( final Object id ) {
    Object result;
    if( id.equals( PROP_OTF_FUNCTIONID ) ) {
      result = getFunctionId();
    } else {
      result = super.getPropertyValue( id );
    }
    return result;
  }

  public IProcess getProcess() {
    return this.process;
  }

  public int getProcessId() {
    return this.process.getProcessId();
  }

  public int getLogicalClock() {
    return this.logicalClock;
  }

  // File Cache
  /**
   * Returns the Function Id for the Event
   * 
   * @return Id of the Function
   */
  public int getFunctionId() {
    return this.process.read( this.logicalClock, functionIdOffset );
  }

  public void setFunctionId( final int function ) {
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

  @Override
  public String getName() {
    return ( ( OTFReader )this.process.getTrace() ).getFunctionName( getFunctionId() );
  }

  public Event getPartnerEvent() {
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
