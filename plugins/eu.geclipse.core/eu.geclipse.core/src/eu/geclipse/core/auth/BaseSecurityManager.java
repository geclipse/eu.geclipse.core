/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.auth;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract implementation of the {@link ISecurityManager} that serves
 * as a common base class for any concrete implementations.
 */
public abstract class BaseSecurityManager
    implements ISecurityManager {
  
  /**
   * Internal list of all registered listeners.
   */
  private List< ISecurityManagerListener > listeners;

  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.ISecurityManager#addListener(eu.geclipse.core.auth.ISecurityManagerListener)
   */
  public void addListener( final ISecurityManagerListener l ) {
    
    if ( this.listeners == null ) {
      this.listeners = new ArrayList< ISecurityManagerListener >();
    }
    
    if ( ! this.listeners.contains( l ) ) {
      this.listeners.add( l );
    }
    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.auth.ISecurityManager#removeListener(eu.geclipse.core.auth.ISecurityManagerListener)
   */
  public void removeListener( final ISecurityManagerListener l ) {
    if ( this.listeners != null ) {
      this.listeners.remove( l );
    }
  }
  
  /**
   * Notify all registered listeners that the content of this manager
   * has changed.
   */
  protected void fireContentChanged() {
    if ( this.listeners != null ) {
      for ( ISecurityManagerListener l : this.listeners ) {
        l.contentChanged( this );
      }
    }
  }

}
