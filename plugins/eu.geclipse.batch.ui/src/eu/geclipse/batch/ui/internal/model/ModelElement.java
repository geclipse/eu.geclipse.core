/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.internal.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Root of the model hierarchy
 */
public abstract class ModelElement implements IPropertySource {

  /**
   * An empty property descriptor.
   */
  private static final IPropertyDescriptor[] EMPTY_ARRAY = new IPropertyDescriptor[0];

  private static final long serialVersionUID = 1;

  /**
   * Delegate used to implement property-change-support.
   */
  protected transient PropertyChangeSupport pcsDelegate = new PropertyChangeSupport(this);

  /**
   * Attach a non-null PropertyChangeListener to this object.
   * @param l a non-null PropertyChangeListener instance.
   * @throws IllegalArgumentException if the parameter is <code>null</code>.
   */
  public synchronized void addPropertyChangeListener( final PropertyChangeListener l ) {
    if ( null == l ) {
       throw new IllegalArgumentException();
    }

    // TODO Why this is needed sometimes with a fresh project and a fresh file??
    if ( null == this.pcsDelegate )
      this.pcsDelegate = new PropertyChangeSupport(this);
    this.pcsDelegate.addPropertyChangeListener(l);
  }

  /**
   * Report a property change to registered listeners (for example edit parts).
   * @param property the programmatic name of the property that changed.
   * @param oldValue the old value of this property.
   * @param newValue the new value of this property.
   */
  protected void firePropertyChange( final String property, final Object oldValue, final Object newValue ) {
    if ( this.pcsDelegate.hasListeners( property ) ) {
      this.pcsDelegate.firePropertyChange( property, oldValue, newValue );
    }
  }

  /**
   * Returns a value for this property source that can be edited in a property sheet.
   * @return this instance
   */
  public Object getEditableValue() {
    return this;
  }

  /**
   * Children should override this. The default implementation returns an empty array.
   * @return Returns an empty array.
   */
  public IPropertyDescriptor[] getPropertyDescriptors() {
    return EMPTY_ARRAY;
  }

  /**
   * Children should override this. The default implementation returns null.
   * @param id The object id.
   * @return Returns <code>null</code>.
   */
  public Object getPropertyValue( final Object id ) {
    return null;
  }

  /**
   * Children should override this. The default implementation returns false.
   * @param id The object id.
   * @return Returns <code>false</code>.
   */
  public boolean isPropertySet( final Object id ) {
    return false;
  }

  /**
   * Remove a PropertyChangeListener from this component.
   * @param l a PropertyChangeListener instance
   */
  public synchronized void removePropertyChangeListener( final PropertyChangeListener l ) {
    if ( null != l ) {
      this.pcsDelegate.removePropertyChangeListener( l );
    }
  }

  /**
   * Children should override this. The default implementation does nothing.
   * @param id The object id.
   */
  public void resetPropertyValue( final Object id ) {
    // do nothing
  }

  /**
   * Children should override this. The default implementation does nothing.
   * @param id The object id.
   * @param value The value.
   */
  public void setPropertyValue( final Object id, final Object value ) {
    // do nothing
  }
}
