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
 *    Thomas Koeckerbauer GUP, JKU - implementation based on eu.geclipse.traceview.nope.tracereader.Trace
 *****************************************************************************/

package eu.geclipse.traceview.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import eu.geclipse.traceview.ITrace;

/**
 * Abstract Trace
 */
public abstract class AbstractTrace implements ITrace {

  /** Number of Processes */
  public static final String PROP_NUMPROCS = "Trace.NumberOfProcesses"; //$NON-NLS-1$
  /** Trace Name */
  public static final String PROP_NAME = "Trace.Name"; //$NON-NLS-1$
  private static IPropertyDescriptor[] traceDescriptors = new IPropertyDescriptor[]{
    new PropertyDescriptor( PROP_NUMPROCS, "Number of Processes" ), //$NON-NLS-1$
    new PropertyDescriptor( PROP_NAME, "Trace Name" ) //$NON-NLS-1$
  };
  protected Map<String, Object> userData;

  /**
   * Abstract Trace
   */
  public AbstractTrace() {
    this.userData = new HashMap<String, Object>();
  }

  @Override
  public String toString() {
    return null;
  }

  public void setUserData( final String id, final Object data ) {
    this.userData.put( id, data );
  }

  public Object getUserData( final String id ) {
    return this.userData.get( id );
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
    return traceDescriptors;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.lang.Object)
   */
  public Object getPropertyValue( final Object id ) {
    Object result = null;
    if( id.equals( PROP_NUMPROCS ) ) {
      result = Integer.valueOf( getNumberOfProcesses() );
    } else if( id.equals( PROP_NAME ) ) {
      result = getName();
    }
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
