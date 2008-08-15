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

package eu.geclipse.traceview.debug;

import java.util.Comparator;

import eu.geclipse.traceview.ISourceLocation;

/**
 * Compares two IEvents
 */
public class EventComparator implements Comparator<ISourceLocation> {

  public int compare( final ISourceLocation io1, final ISourceLocation io2 ) {
    int result = 0;
    if( io1.getOccurrenceCount() < io2.getOccurrenceCount() )
      result = -1;
    if( io1.getOccurrenceCount() == io2.getOccurrenceCount() )
      result = 0;
    if( io1.getOccurrenceCount() > io2.getOccurrenceCount() )
      result = 1;
    return result;
  }
}
