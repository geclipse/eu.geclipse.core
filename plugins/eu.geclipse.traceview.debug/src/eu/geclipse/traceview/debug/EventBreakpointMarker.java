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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.debug.core.model.ICLineBreakpoint;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.ISourceLocation;
import eu.geclipse.traceview.utils.AbstractEventMarker;

/**
 * Marks the events on which a breakpoint is set.
 */
public class EventBreakpointMarker extends AbstractEventMarker {
  private List<IBreakpoint> breakpoints = new ArrayList<IBreakpoint>();
  private final Color bgColor = Display.getDefault().getSystemColor( SWT.COLOR_GRAY );
  private final Color fgColor = Display.getDefault().getSystemColor( SWT.COLOR_RED );

  @Override
  public Color getBackgroundColor( final int type ) {
    return bgColor;
  }

  @Override
  public Color getForegroundColor( final int type ) {
    return fgColor;
  }

  @Override
  public int getLineWidth( final int type ) {
    return 2;
  }

  @Override
  public void startMarking() {
    this.breakpoints.clear();
    for( IBreakpoint breakpoint : DebugPlugin.getDefault().getBreakpointManager().getBreakpoints() ) {
      if( breakpoint instanceof EventBreakpoint
          || breakpoint instanceof ICLineBreakpoint ) {
        this.breakpoints.add(breakpoint);
      }
    }
  }

  @Override
  public int mark( final IEvent event ) {
    int result = 0;
    if( event instanceof ISourceLocation ) {
      for( IBreakpoint breakpoint : this.breakpoints ) {
        if( breakpoint instanceof EventBreakpoint ) {
          EventBreakpoint eventBreakpoint = ( EventBreakpoint )breakpoint;
          try {
            if( eventBreakpoint.containtsEvent( event ) ) {
              result = Diamond_Event;
            }
          } catch( CoreException coreException ) {
            Activator.logException( coreException );
          }
        } else if( breakpoint instanceof ICLineBreakpoint ) {
          ICLineBreakpoint cLineBreakpoint = ( ICLineBreakpoint )breakpoint;
          try {
            ISourceLocation sourceLocation = ( ISourceLocation )event;
            if( sourceLocation.getSourceLineNumber() == cLineBreakpoint.getLineNumber() ) {
              String filename = sourceLocation.getSourceFilename();
              filename = new Path( filename ).lastSegment();
              if( filename.equals( cLineBreakpoint.getFileName() ) ) {
                if(cLineBreakpoint.getIgnoreCount() == 0){
                  result = Rectangle_Event;
                }else if (cLineBreakpoint.getIgnoreCount() <= sourceLocation.getOccurrenceCount()){
                  result = Rectangle_Event;
                }
              }
            }
          } catch( CoreException e ) {
            // empty
          }
        }
      }
    }
    return result;
  }
}
