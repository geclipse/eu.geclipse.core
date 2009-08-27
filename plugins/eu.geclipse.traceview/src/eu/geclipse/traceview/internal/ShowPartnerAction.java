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
 *    Christof Klausecker - MNM-Team, LMU Munich
 *****************************************************************************/

package eu.geclipse.traceview.internal;

import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.ITrace;

public class ShowPartnerAction extends AbstractProcessAction {

  @Override
  void performAction( final AbstractGraphVisualization vis,
                      final IProcess[] procs )
  {
    if( procs[ 0 ] != null ) {
      ITrace trace = procs[ 0 ].getTrace();
      boolean[] hide = new boolean[ trace.getNumberOfProcesses() ];
      for( int i = 0; i < hide.length; i++ ) {
        hide[ i ] = true;
      }
      loop: for( IProcess process : procs ) {
        hide[ process.getProcessId() ] = false;
        IEvent event = process.getEventByLogicalClock( 0 );
        while( event != null ) {
          if (event.getPartnerProcessId() == -1) { // handle broadcast events
            for (int i = 0; i < hide.length; i++) {
              hide[ i ] = false;
            }
            break loop;
          }
          hide[ event.getPartnerProcessId() ] = false;
          event = event.getNextEvent();
        }
      }
      vis.setHideProcess( hide );
    }
  }
}
