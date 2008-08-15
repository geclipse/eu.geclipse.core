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

package eu.geclipse.traceview.utils;

import java.util.Comparator;

import eu.geclipse.traceview.IVectorEvent;

/**
 * Comparator for IVectorEvents
 */
public class VectorEventComparator implements Comparator<IVectorEvent> {

  public int compare( final IVectorEvent event1, final IVectorEvent event2 ) {
    int[] vectorClock1 = event1.getVectorClock();
    int[] vectorClock2 = event2.getVectorClock();
    int smaller = 0;
    int larger = 0;
    for( int i = 0; i < vectorClock1.length; i++ ) {
      if( vectorClock1[ i ] < vectorClock2[ i ] ) {
        smaller++;
      } else if( vectorClock1[ i ] > vectorClock2[ i ] ) {
        larger++;
      }
    }
    int result = 0;
    if( smaller == 0 && larger != 0 )
      result = 1;
    if( larger == 0 && smaller != 0 )
      result = -1;
    if( smaller == 0 && larger == 0 )
      result = -2; // TODO change this since it breaks the Comperator interface
    return result;
  }
}
