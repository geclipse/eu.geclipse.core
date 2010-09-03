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

package eu.geclipse.traceview.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.IEventMarker;
import eu.geclipse.traceview.IVectorEvent;
import eu.geclipse.traceview.utils.AbstractEventMarker;
import eu.geclipse.traceview.utils.VectorEventComparator;

/**
 * Cause Effect Marker
 */
public class CauseEffectMarker extends AbstractEventMarker {
  static String MARKER_ID = "eu.geclipse.traceview.markers.CauseEffectMarker"; //$NON-NLS-1$
  private final Color beforeColor = Display.getDefault().getSystemColor( SWT.COLOR_BLUE );
  private final Color afterColor = Display.getDefault().getSystemColor( SWT.COLOR_RED );
  private final Color identicalColor = Display.getDefault().getSystemColor( SWT.COLOR_GREEN );
  private final Color unknownColor = Display.getDefault().getSystemColor( SWT.COLOR_WHITE );
  Color background;

  @Override
  public Color getBackgroundColor( final int type ) {
    return this.background;
  }

  @Override
  public Color getForegroundColor( final int type ) {
    return Display.getDefault().getSystemColor( SWT.COLOR_BLACK );
  }

  @Override
  public int mark( final IEvent event ) {
    IVectorEvent causeEvent = ( IVectorEvent )this.trace.getUserData( MARKER_ID );
    int result = 0;
    if( causeEvent != null && event instanceof IVectorEvent ) {
      IVectorEvent vectorEvent = ( IVectorEvent )event;
      if( causeEvent.getVectorClock().length == vectorEvent.getVectorClock().length ) {
        VectorEventComparator c = new VectorEventComparator();
        int compare = c.compare( causeEvent, vectorEvent );
        if( compare == -2 ) {
          this.background = identicalColor;
        } else if( compare < 0 ) {
          this.background = afterColor;
        } else if( compare > 0 ) {
          this.background = beforeColor;
        } else {
          this.background = unknownColor;
        }
        result = IEventMarker.Ellipse_Event;
      }
    }
    return result;
  }
}
