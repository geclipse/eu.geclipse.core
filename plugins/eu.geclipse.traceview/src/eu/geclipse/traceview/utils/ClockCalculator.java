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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.traceview.utils;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.ILamportProcess;
import eu.geclipse.traceview.ILamportTrace;
import eu.geclipse.traceview.ITrace;

public class ClockCalculator {
  public static void calcLamportClock( final ILamportTrace trace ) {
    boolean allLamportClocksUpdated;
    boolean[] lamportClocksUpdated = new boolean[trace.getNumberOfProcesses()];
    int[] lastLogicalClock = new int[trace.getNumberOfProcesses()];
    int[] lastLamportClock = new int[trace.getNumberOfProcesses()];
    
    do {
      allLamportClocksUpdated = true;
      for( int procId = 0 ; procId < trace.getNumberOfProcesses(); procId++ ) {
        ILamportProcess process = (ILamportProcess) trace.getProcess(procId);
        if( !lamportClocksUpdated[procId] ) {
          while( lastLogicalClock[procId] <= process.getMaximumLogicalClock() ) {
            ILamportEventClockSetter event = ( ILamportEventClockSetter )process.getEventByLogicalClock( lastLogicalClock[procId] );
            if( event.getType() == EventType.RECV ) {
              ILamportEventClockSetter partnerEvent =  ( ILamportEventClockSetter )( ( ILamportProcess )trace.getProcess( event.getPartnerProcessId() ) ).getEventByLogicalClock( event.getPartnerLogicalClock() );
              int partnerLamClock = partnerEvent.getLamportClock();
              if( partnerLamClock == -1 ) {
                 break;
              }
              if( lastLamportClock[procId] < partnerLamClock + 1 ) {
              lastLamportClock[procId] = partnerLamClock + 1;
              }
                event.setPartnerLamportClock( partnerLamClock );              
                partnerEvent.setPartnerLamportClock( lastLamportClock[procId] );
            }
            event.setLamportClock( lastLamportClock[procId] );
  
            lastLamportClock[procId]++;
            lastLogicalClock[procId]++;
          }
          if( lastLogicalClock[procId] == process.getMaximumLogicalClock() + 1 ) {
            lamportClocksUpdated[procId] = true;
          }
        }
        allLamportClocksUpdated &= lamportClocksUpdated[procId];
      }
    } while (!allLamportClocksUpdated);
  }

  public static void calcPartnerLogicalClocks( final ITrace trace ) {
    int[] currentIndex = new int[ trace.getNumberOfProcesses() ];
    for( int i = 0; i < trace.getNumberOfProcesses(); i++ ) {
      for( int j = 0; j < trace.getNumberOfProcesses(); j++ ) {
        currentIndex[ j ] = 0;
      }
      for( int j = 0; j <= trace.getProcess(i).getMaximumLogicalClock(); j++ ) {
      IEventPartnerLogicalClockSetter event = ( IEventPartnerLogicalClockSetter )trace.getProcess(i).getEventByLogicalClock( j );
        if( event.getType() == EventType.RECV ) {
          int partnerId = event.getPartnerProcessId();
          IEventPartnerLogicalClockSetter partnerEvent;
          do {
            partnerEvent = ( IEventPartnerLogicalClockSetter )trace.getProcess(partnerId).getEventByLogicalClock( currentIndex[ partnerId ] );
            currentIndex[ partnerId ]++;
          } while( partnerEvent.getType() != EventType.SEND
                   || partnerEvent.getPartnerProcessId() != i );
          event.setPartnerLogicalClock( currentIndex[ partnerId ] - 1 );
          partnerEvent.setPartnerLogicalClock(event.getLogicalClock());
        }
      }
    }
  }
}
