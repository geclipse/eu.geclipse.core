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

package eu.geclipse.core.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * This class serves as a container for a bunch of monitors. If an
 * operation needs to be monitored by multiple monitors these monitors
 * can be specified as slaves of the master monitor and the master
 * monitor can be provided as single access point to the operation.
 * Any operation on the master monitor will be forwarded to the slaves.
 * 
 * A master monitor is thought to be canceled if any of its slaves
 * was canceled.
 */
public class MasterMonitor implements IProgressMonitor {
  
  /**
   * The list of slaves of this master monitor.
   */
  private List< IProgressMonitor > slaves;
  
  /**
   * Create a new master monitor without slaves.
   */
  public MasterMonitor() {
    this( null, null );
  }
  
  /**
   * Create a new master monitor with one slave. The slave can also be
   * <code>null</code> which makes {@link NullProgressMonitor}s obsolete.
   * 
   * @param slave The slave of this monitor.
   */
  public MasterMonitor( final IProgressMonitor slave ) {
    this( slave, null );
  }
  
  /**
   * Create a new master monitor with two slaves.
   * 
   * @param slave1 The first slave.
   * @param slave2 The second slave.
   */
  public MasterMonitor( final IProgressMonitor slave1, final IProgressMonitor slave2 ) {
    this.slaves = new ArrayList< IProgressMonitor >();
    addSlave( slave1 );
    addSlave( slave2 );
  }
  
  /**
   * Add a slave to this master monitor. This method should not be called
   * after any operation was performed on the monitor. Especially when
   * called after {@link #beginTask(String, int)} the result of adding a
   * new slave is not predicted.
   * 
   * @param slave The new slave to be added.
   */
  public void addSlave( final IProgressMonitor slave ) {
    if ( ( slave != null ) && ! ( slave instanceof NullProgressMonitor ) ) {
      this.slaves.add( slave );
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IProgressMonitor#beginTask(java.lang.String, int)
   */
  public void beginTask( final String name, final int totalWork ) {
    for ( IProgressMonitor m : this.slaves ) {
      m.beginTask( name, totalWork );
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IProgressMonitor#done()
   */
  public void done() {
    for ( IProgressMonitor m : this.slaves ) {
      m.done();
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IProgressMonitor#internalWorked(double)
   */
  public void internalWorked( final double work ) {
    for ( IProgressMonitor m : this.slaves ) {
      m.internalWorked( work );
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IProgressMonitor#isCanceled()
   */
  public boolean isCanceled() {
    
    boolean result = false;
    
    for ( IProgressMonitor m : this.slaves ) {
      result |= m.isCanceled();
      if ( result ) {
        break;
      }
    }
    
    if ( result ) {
      for ( IProgressMonitor m : this.slaves ) {
        m.setCanceled( true );
      }
    }
    
    return result;
    
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IProgressMonitor#setCanceled(boolean)
   */
  public void setCanceled( final boolean value ) {
    for ( IProgressMonitor m : this.slaves ) {
      m.setCanceled( value );
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IProgressMonitor#setTaskName(java.lang.String)
   */
  public void setTaskName( final String name ) {
    for ( IProgressMonitor m : this.slaves ) {
      m.setTaskName( name );
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IProgressMonitor#subTask(java.lang.String)
   */
  public void subTask( final String name ) {
    for ( IProgressMonitor m : this.slaves ) {
      m.subTask( name );
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IProgressMonitor#worked(int)
   */
  public void worked( final int work ) {
    for ( IProgressMonitor m : this.slaves ) {
      m.worked( work );
    }
  }

}
