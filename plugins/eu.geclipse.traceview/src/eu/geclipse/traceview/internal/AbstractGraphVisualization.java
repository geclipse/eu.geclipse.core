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
 *    Thomas Koeckerbauer - MNM-Team, LMU Munich, code cleanup of logical and physical trace viewers
 *****************************************************************************/

package eu.geclipse.traceview.internal;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.window.DefaultToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IViewSite;

import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.IEventMarker;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.TraceVisualization;
import eu.geclipse.traceview.views.TraceView;


public abstract class AbstractGraphVisualization extends TraceVisualization {
  protected ArrayList<IEventMarker> eventMarkers;
  protected LineType hLines;
  protected LineType vLines;
  protected ITrace trace;

  public AbstractGraphVisualization( final Composite parent,
                       final int style,
                       final IViewSite viewSite,
                       final ITrace trace )
  {
    super( parent, style | SWT.V_SCROLL | SWT.H_SCROLL );
    this.trace = trace;
    // layout
    GridData layoutData = new GridData();
    layoutData.horizontalAlignment = SWT.FILL;
    layoutData.grabExcessHorizontalSpace = true;
    layoutData.verticalAlignment = SWT.FILL;
    layoutData.grabExcessVerticalSpace = true;
    this.setLayoutData( layoutData );
    // set defaults
    this.hLines = LineType.Lines_1;
    this.vLines = LineType.Lines_1;
    // get the Event Markers
    this.eventMarkers = new ArrayList<IEventMarker>();
    // TODO create possibility to select the eventmakers
    for( IConfigurationElement configurationElement : Platform.getExtensionRegistry()
      .getConfigurationElementsFor( "eu.geclipse.traceview.EventMarker" ) ) { //$NON-NLS-1$
      try {
        IEventMarker eventMarker = ( IEventMarker )configurationElement.createExecutableExtension( "class" ); //$NON-NLS-1$
        eventMarker.setTrace( trace );
        this.eventMarkers.add( eventMarker );
      } catch( CoreException coreException ) {
        Activator.logException( coreException );
      }
    }
    TraceView view = ( TraceView )Activator.getDefault()
      .getWorkbench()
      .getActiveWorkbenchWindow()
      .getActivePage()
      .getActivePart();
    Menu menu = view.getContextMenuManager().createContextMenu( this );
    setMenu( menu );
    addListener( SWT.Resize, new Listener() {
      public void handleEvent( final Event e ) {
        handleResize();
      }
    } );
    addDisposeListener( new DisposeListener() {
      public void widgetDisposed( final DisposeEvent e ) {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        store.removePropertyChangeListener( getEventGraphPaintListener().listener );
      }
    } );
  }

  public void registerPaintListener( final AbstractGraphPaintListener eventGraphPaintListener ) {
    eventGraphPaintListener.setHorizontalScrollBar( getHorizontalBar() );
    eventGraphPaintListener.setVerticalScrollBar( getVerticalBar() );
    eventGraphPaintListener.setFont( getFont() );
    eventGraphPaintListener.setTrace( this.trace );
    addPaintListener( eventGraphPaintListener );
  }

  public void registerMouseListener( final AbstractGraphMouseAdapter mouseAdapter ) {
    addMouseListener( mouseAdapter );
    @SuppressWarnings("unused")
    DefaultToolTip toolTip = new DefaultToolTip( this ) {
      @Override
      protected boolean shouldCreateToolTip( final Event event ) {
        Object obj = mouseAdapter.getObjectForPosition( event.x, event.y );
        return obj != null && obj.toString() != null;
      }

      @Override
      protected String getText( final Event event ) {
        String result = null;
        Object obj = mouseAdapter.getObjectForPosition( event.x, event.y );
        if( obj != null ) {
          result = obj.toString();
          if (obj instanceof IEvent) {
            for( IEventMarker eventmarker : getEventMarkers() ) {
              eventmarker.mark( (IEvent)obj );
              String markerString = eventmarker.getToolTip();
              if (markerString != null) {
                result += '\n' + markerString;
              }
            }
          }
        }
        return result;
      }
    };
  }

  private void setHLines( final LineType lines ) {
    this.hLines = lines;
    redraw();
  }

  private void setVLines( final LineType lines ) {
    this.vLines = lines;
    redraw();
  }

  protected void changeLineStyle( final int direction ) {
    LineType style = null;
    if( direction < 0 ) {
      style = getVLines();
    } else {
      style = getHLines();
    }
    if( style == LineType.Lines_None ) {
      style = LineType.Lines_1;
    } else if( style == LineType.Lines_1 ) {
      style = LineType.Lines_5;
    } else if( style == LineType.Lines_5 ) {
      style = LineType.Lines_10;
    } else if( style == LineType.Lines_10 ) {
      style = LineType.Lines_None;
    }
    if( direction < 0 ) {
      setVLines( style );
    } else {
      setHLines( style );
    }
  }

  public  ArrayList<IEventMarker> getEventMarkers() {
    return this.eventMarkers;
  }

  protected void handleResize() {
    getEventGraphPaintListener().handleResize();
  }

  public LineType getHLines() {
    return this.hLines;
  }

  public LineType getVLines() {
    return this.vLines;
  }

  @Override
  public ITrace getTrace() {
    return this.trace;
  }

  @Override
  public void printTrace( final GC gc ) {
    getEventGraphPaintListener().print( gc );
  }

  public abstract AbstractGraphPaintListener getEventGraphPaintListener();
}
