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
 *****************************************************************************/

package eu.geclipse.traceview.debug.listeners;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchesListener2;
import org.eclipse.debug.core.model.IBreakpoint;

import eu.geclipse.traceview.debug.Activator;
import eu.geclipse.traceview.debug.EventBreakpoint;

/**
 * Re-enables the breakpoints at the end.
 */
public class LaunchListener implements ILaunchesListener2 {

  public void launchesTerminated( final ILaunch[] launches ) {
    IBreakpoint[] breakpoints = DebugPlugin.getDefault()
      .getBreakpointManager()
      .getBreakpoints();
    for( IBreakpoint breakpoint : breakpoints ) {
      if( breakpoint instanceof EventBreakpoint ) {
        try {
          // reenable disabled breakpoints
          breakpoint.setEnabled( true );
        } catch( CoreException coreException ) {
          Activator.logException( coreException );
        }
      }
    }
  }

  public void launchesChanged( final ILaunch[] launches ) {
    // empty
  }

  public void launchesAdded( final ILaunch[] launches ) {
    // empty
  }

  public void launchesRemoved( final ILaunch[] launches ) {
    // empty
  }
}
