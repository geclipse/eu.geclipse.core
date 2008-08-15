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
 *    Thomas Koeckerbauer GUP, JKU - implementation based on eu.geclipse.traceview.nope.tracereader.Event
 *****************************************************************************/

package eu.geclipse.traceview.utils;

import java.util.Arrays;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.ILamportEvent;
import eu.geclipse.traceview.IPhysicalEvent;
import eu.geclipse.traceview.ISourceLocation;
import eu.geclipse.traceview.IVectorEvent;

/**
 * An abstract Event
 *
 */
public abstract class AbstractEvent implements IEvent {
  /** Type */
  public static final String PROP_TYPE = "Event.Type"; //$NON-NLS-1$
  /** Process */
  public static final String PROP_PROCESS = "Event.Process"; //$NON-NLS-1$
  /** Partner Process */
  public static final String PROP_PARTNER_PROCESS = "Event.PartnerProcess"; //$NON-NLS-1$
  /** Logical Clock */
  public static final String PROP_LOGICALCLOCK = "Event.LogicalClock"; //$NON-NLS-1$
  /** Partner Logical Clock */
  public static final String PROP_PARTNER_LOGICALCLOCK = "Event.PartnerLogicalClock"; //$NON-NLS-1$
  /** Lamport Clock */
  public static final String PROP_LAMPORTCLOCK = "LamportEvent.LamportClock"; //$NON-NLS-1$
  /** Partner Lamport Clock */
  public static final String PROP_PARTNER_LAMPORTCLOCK = "LamportEvent.PartnerLamportClock"; //$NON-NLS-1$
  /** Source File */
  public static final String PROP_SOURCE_FILE = "SourceLocation.SourceFile"; //$NON-NLS-1$
  /** Source Line */
  public static final String PROP_SOURCE_LINE = "SourceLocation.SourceLine"; //$NON-NLS-1$
  /** Time Start */
  public static final String PROP_TIME_START = "PhysicalEvent.TimeStart"; //$NON-NLS-1$
  /** Time Stop */
  public static final String PROP_TIME_END = "PhysicalEvent.TimeEnd"; //$NON-NLS-1$
  /** Vector Clock */
  public static final String PROP_VECTORCLOCK = "VectorEvent.VectorClock"; //$NON-NLS-1$

  private static IPropertyDescriptor[] eventDescriptors = new IPropertyDescriptor[] {
    new PropertyDescriptor( PROP_TYPE, "Message Type" ), //$NON-NLS-1$
    new PropertyDescriptor( PROP_PROCESS, "Process" ), //$NON-NLS-1$
    new PropertyDescriptor( PROP_LOGICALCLOCK, "Logical Clock" ), //$NON-NLS-1$
    new PropertyDescriptor( PROP_PARTNER_LOGICALCLOCK,
                            "Partner Logical Clock" )}; //$NON-NLS-1$
  private static IPropertyDescriptor[] lamportEventDescriptors = new IPropertyDescriptor[] {
    new PropertyDescriptor( PROP_LAMPORTCLOCK, "Lamport Clock" ), //$NON-NLS-1$
    new PropertyDescriptor( PROP_PARTNER_LAMPORTCLOCK,
                            "Partner Lamport Clock" )}; //$NON-NLS-1$
  private static IPropertyDescriptor[] sourceLocationDescriptors = new IPropertyDescriptor[] {
    new PropertyDescriptor( PROP_SOURCE_FILE, "Source File" ), //$NON-NLS-1$
    new PropertyDescriptor( PROP_SOURCE_LINE, "Source Line" )}; //$NON-NLS-1$
  private static IPropertyDescriptor[] physicalEventDescriptors = new IPropertyDescriptor[] {
    new PropertyDescriptor( PROP_TIME_START, "Time Start" ), //$NON-NLS-1$
    new PropertyDescriptor( PROP_TIME_END, "Time End" )}; //$NON-NLS-1$
  private static IPropertyDescriptor[] vectorEventDescriptors = new IPropertyDescriptor[] {
    new PropertyDescriptor( PROP_VECTORCLOCK, "Vector Clock" )}; //$NON-NLS-1$

  @Override
  public String toString() {
    return "Type: " + getType().name(); //$NON-NLS-1$
  }

  public String getName() {
    return getType().name();
  }

  // *****************************************************
  // * IPropertySource
  // *****************************************************
  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
   */
  public IPropertyDescriptor[] getPropertyDescriptors() {
    IPropertyDescriptor[] descriptors;
    int count = eventDescriptors.length;
    if (this instanceof ILamportEvent) count += lamportEventDescriptors.length;
    if (this instanceof IPhysicalEvent) count += physicalEventDescriptors.length;
    if (this instanceof ISourceLocation) count += sourceLocationDescriptors.length;
    if (this instanceof IVectorEvent) count += vectorEventDescriptors.length;
    descriptors = new IPropertyDescriptor[count];
    int pos = 0;
    System.arraycopy( eventDescriptors, 0, descriptors, 0, eventDescriptors.length );
    pos += eventDescriptors.length;
    if (this instanceof ILamportEvent) {
      System.arraycopy( lamportEventDescriptors, 0, descriptors, pos, lamportEventDescriptors.length );
      pos += lamportEventDescriptors.length;
    }
    if (this instanceof IPhysicalEvent) {
      System.arraycopy( physicalEventDescriptors, 0, descriptors, pos, physicalEventDescriptors.length );
      pos += physicalEventDescriptors.length;
    }
    if (this instanceof ISourceLocation) {
      System.arraycopy( sourceLocationDescriptors, 0, descriptors, pos, sourceLocationDescriptors.length );
      pos += sourceLocationDescriptors.length;
    }
    if (this instanceof IVectorEvent) {
      System.arraycopy( vectorEventDescriptors, 0, descriptors, pos, vectorEventDescriptors.length );
      pos += vectorEventDescriptors.length;
    }
    return descriptors;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
   */
  public Object getPropertyValue( final Object id ) {
    Object result = null;

    if( id.equals( PROP_TYPE ) )
      result = getType();
    else if( id.equals( PROP_PROCESS ) )
      result = Integer.valueOf( getProcessId() );
    else if( id.equals( PROP_PARTNER_PROCESS ) )
      result = Integer.valueOf( getPartnerProcessId() );
    else if( id.equals( PROP_LOGICALCLOCK ) )
      result = Integer.valueOf( getLogicalClock() );
    else if( id.equals( PROP_PARTNER_LOGICALCLOCK ) )
      result = Integer.valueOf( getPartnerLogicalClock() );
    // ILamportEvent
    else if( this instanceof ILamportEvent && id.equals( PROP_LAMPORTCLOCK ) )
      result = Integer.valueOf( ((ILamportEvent)this).getLamportClock() );
    else if( this instanceof ILamportEvent && id.equals( PROP_PARTNER_LAMPORTCLOCK ) )
      result = Integer.valueOf( ((ILamportEvent)this).getPartnerLamportClock() );
    // IPhysicalEvent
    else if( this instanceof IPhysicalEvent && id.equals( PROP_TIME_START ) )
      result = Integer.valueOf( ((IPhysicalEvent)this).getPhysicalStartClock() );
    else if( this instanceof IPhysicalEvent && id.equals( PROP_TIME_END ) )
      result = Integer.valueOf( ((IPhysicalEvent)this).getPhysicalStopClock() );
    // ISourceLocation
    else if( this instanceof ISourceLocation && id.equals( PROP_SOURCE_FILE ) )
      result = ((ISourceLocation)this).getSourceFilename();
    else if( this instanceof ISourceLocation && id.equals( PROP_SOURCE_LINE ) )
      result = Integer.valueOf( ((ISourceLocation)this).getSourceLineNumber() );
    // IVectorEvent
    else if( this instanceof IVectorEvent && id.equals( PROP_VECTORCLOCK )
            /* && ( ( Trace )this.getTrace() ).supportsVectorClocks()*/ )
      result = Arrays.toString( ((IVectorEvent)this).getVectorClock() );
    return result;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.lang.Object,
   *      java.lang.Object)
   */
  public void setPropertyValue( final Object id, final Object value ) {
    // no editable properties
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.Object)
   */
  public boolean isPropertySet( final Object id ) {
    return false;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(java.lang.Object)
   */
  public void resetPropertyValue( final Object id ) {
    // no editable properties
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
   */
  public Object getEditableValue() {
    // no editable properties
    return null;
  }
}
