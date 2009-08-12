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
 *    Thomas Koeckerbauer MNM-Team, LMU Munich - initial API and implementation
 *****************************************************************************/

package eu.geclipse.traceview.utils;

import eu.geclipse.traceview.ITrace;

public abstract class AbstractProcessFileCache extends AbstractProcess {
  protected int processId;
  protected AbstractTraceFileCache trace;

  public AbstractProcessFileCache( final AbstractTraceFileCache trace, final int processId ) {
    this.trace = trace;
    this.processId = processId;
  }

  public int getMaximumLogicalClock() {
    return trace.getMaximumLogicalClock( processId );
  }

  public void setMaximumLogicalClock( final int value ) {
    trace.setMaximumLogicalClock( processId, value );
  }

  public int getProcessId() {
    return processId;
  }

  public ITrace getTrace() {
    return trace;
  }

  public String getSourceFilenameForIndex( final int index) {
    return trace.getSourceFilenameForIndex( index );
  }

  public int addSourceFilename( final String filename ) {
    return trace.addSourceFilename( filename );
  }

  public void write( final int logicalClock, final int offset, final int value ) {
    trace.write( processId, logicalClock, offset, value );
  }

  public void write( final int logicalClock, final int offset, final int[] value ) {
    trace.write( processId, logicalClock, offset, value );
  }

  public int read( final int logicalClock, final int offset ) {
    return trace.read( processId, logicalClock, offset );
  }

  public void read( final int logicalClock, final int offset, final int[] data ) {
    trace.read( processId, logicalClock, offset, data );
  }
}
