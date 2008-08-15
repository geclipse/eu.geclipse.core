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

import org.eclipse.cdt.debug.internal.core.model.CThread;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.model.IBreakpoint;

import eu.geclipse.traceview.debug.Activator;
import eu.geclipse.traceview.debug.EventBreakpoint;

/**
 * Sets the IgnoreCount to the next value every time an EventBreakpoint is hit.
 */
public class DebugEventListener implements IDebugEventSetListener {

  public void handleDebugEvents( final DebugEvent[] events ) {
    for( DebugEvent event : events ) {
      if( event.getKind() == DebugEvent.SUSPEND ) {
        if( event.getDetail() == DebugEvent.BREAKPOINT ) {
          if( event.getSource() instanceof CThread ) {
            CThread thread = ( CThread )event.getSource();
            for( IBreakpoint breakpoint : thread.getBreakpoints() ) {
              if( breakpoint instanceof EventBreakpoint ) {
                EventBreakpoint eventBreakPoint = ( EventBreakpoint )breakpoint;
                try {
                  eventBreakPoint.next();
                } catch( CoreException coreException ) {
                  Activator.logException( coreException );
                }
              }
            }
          }
        }
      }
    }
  }
}
