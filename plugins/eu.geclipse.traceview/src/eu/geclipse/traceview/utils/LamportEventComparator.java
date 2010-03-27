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

import eu.geclipse.traceview.ILamportEvent;

/**
 * Comparator for ILamportEvents
 */
final public class LamportEventComparator implements Comparator<ILamportEvent> {

  public int compare( final ILamportEvent event1, final ILamportEvent event2 ) {
    int result = event1.getLamportClock() - event2.getLamportClock();
    if ( result == 0 ) result = event1.getProcessId() - event2.getProcessId();
    return result;
  }
}
