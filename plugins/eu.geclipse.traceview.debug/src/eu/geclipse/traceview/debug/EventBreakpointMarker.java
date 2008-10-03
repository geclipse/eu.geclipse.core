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

import org.eclipse.cdt.debug.core.model.ICLineBreakpoint;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.IEventMarker;
import eu.geclipse.traceview.ISourceLocation;
import eu.geclipse.traceview.ITrace;

/**
 * Marks the events on which a breakpoint is set.
 */
public class EventBreakpointMarker implements IEventMarker {

  private int lineWidth = 2;
  private int lineStyle = SWT.LINE_SOLID;

  public Color getBackgroundColor( final int type ) {
    Color result = Display.getDefault().getSystemColor( SWT.COLOR_GRAY );
    return result;
  }

  public Color getForegroundColor( final int type ) {
    Color result = Display.getDefault().getSystemColor( SWT.COLOR_RED );
    return result;
  }

  public int getLineWidth( final int type ) {
    return this.lineWidth;
  }

  public int getLineStyle( final int type ) {
    return this.lineStyle;
  }

  public int mark( final IEvent event ) {
    int result = 0;
    if( event instanceof ISourceLocation ) {
      for( IBreakpoint breakpoint : DebugPlugin.getDefault()
        .getBreakpointManager()
        .getBreakpoints() )
      {
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
          if( event instanceof ISourceLocation ) {
            ISourceLocation sourceLocation = ( ISourceLocation )event;
            try {
              if( sourceLocation.getSourceFilename()
                .equals( cLineBreakpoint.getFileName() )
                  && sourceLocation.getSourceLineNumber() == cLineBreakpoint.getLineNumber() )
              {
                if(cLineBreakpoint.getIgnoreCount() == 0){
                  result = Triangle_Event;
                }else if (cLineBreakpoint.getIgnoreCount() <= sourceLocation.getOccurrenceCount()){
                  result = Triangle_Event;
                }
                
              }
            } catch( CoreException e ) {
              // empty
            }
          }
        }
      }
    }
    return result;
  }

  public void setTrace( final ITrace trace ) {
    // empty
  }

  public String getToolTip() {
    return null;
  }
}
