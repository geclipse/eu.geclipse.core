/*****************************************************************************
 * Copyright (c) 2010 g-Eclipse Consortium All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html Initial development of the original code was made for the g-Eclipse project founded by European Union project number:
 * FP6-IST-034327 http://www.geclipse.eu/ Contributors: Christof Klausecker MNM-Team, LMU Munich - initial API and implementation
 *****************************************************************************/
package eu.geclipse.traceview.otf.ui.markers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import eu.geclipse.eventgraph.tracereader.otf.Event;
import eu.geclipse.eventgraph.tracereader.otf.OTFDefinitionReader;
import eu.geclipse.eventgraph.tracereader.otf.OTFReader;
import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.IEventMarker;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.utils.AbstractEventMarker;

/**
 * Event marker for OTF Traces
 */
public class OTFEventMarker extends AbstractEventMarker {

  OTFDefinitionReader otfDefinitionReader;
  private Color[] colors;
  private Color foregroundColor;
  private Color backgroundColor;
  private Color canvasBackgroundColor;

  /**
   * Constructs a new OTF Event Marker
   */
  public OTFEventMarker() {
    this.foregroundColor = Display.getDefault().getSystemColor( SWT.COLOR_BLACK );
    this.backgroundColor = null;
    this.canvasBackgroundColor = null;
  }

  @Override
  public Color getForegroundColor( final int type ) {
    return this.foregroundColor;
  }

  @Override
  public Color getBackgroundColor( final int type ) {
    return this.backgroundColor;
  }

  @Override
  public Color getCanvasBackgroundColor() {
    return this.canvasBackgroundColor;
  }

  @Override
  public int mark( final IEvent event ) {
    int result = 0;
    if( event instanceof Event ) {
      Event otfEvent = ( Event )event;
      int id = this.otfDefinitionReader.getFunctionGroupID( otfEvent.getFunctionId() );
      if( id < this.colors.length ) {
        this.backgroundColor = this.colors[ id ];
        if( this.backgroundColor != null ) {
          result = IEventMarker.Ellipse_Event;
        }
      }
    }
    return result;
  }

  @Override
  public void setTrace( final ITrace trace ) {
    super.setTrace( trace );
    if( trace instanceof OTFReader ) {
      OTFReader otfTrace = ( OTFReader )trace;
      this.otfDefinitionReader = otfTrace.getDefinition();
      String[] functionGroupNames = this.otfDefinitionReader.getFunctionGroupNames();
      this.colors = new Color[ functionGroupNames.length + 1];
      for( int i = 0; i < functionGroupNames.length; i++ ) {
        if( "Application".equals( functionGroupNames[ i ] ) ) { //$NON-NLS-1$
          this.colors[ i + 1] = Display.getDefault().getSystemColor( SWT.COLOR_CYAN );
        } else if( "MPI".equals( functionGroupNames[ i ] ) ) { //$NON-NLS-1$
          this.colors[ i + 1 ] = Display.getDefault().getSystemColor( SWT.COLOR_RED );
        } else if( "LIBC".equals( functionGroupNames[ i ] ) ) { //$NON-NLS-1$
          this.colors[ i + 1 ] = Display.getDefault().getSystemColor( SWT.COLOR_BLUE );
        } else if( "VT_API".equals( functionGroupNames[ i ] ) ) { //$NON-NLS-1$
          this.colors[ i + 1 ] = Display.getDefault().getSystemColor( SWT.COLOR_GRAY );
        } else {
          this.colors[ i + 1 ] = null;
        }
      }
    }
  }
}
