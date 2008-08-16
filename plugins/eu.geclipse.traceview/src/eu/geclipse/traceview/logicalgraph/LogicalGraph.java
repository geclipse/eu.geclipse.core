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

package eu.geclipse.traceview.logicalgraph;

import java.util.ArrayList;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.window.DefaultToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IViewSite;

import eu.geclipse.traceview.IEventMarker;
import eu.geclipse.traceview.ILamportTrace;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.TraceVisualization;
import eu.geclipse.traceview.internal.Activator;
import eu.geclipse.traceview.internal.LineType;
import eu.geclipse.traceview.internal.Messages;
import eu.geclipse.traceview.views.TraceView;

/**
 * Logical Clock View
 */
public class LogicalGraph extends TraceVisualization {

  protected LogicalGraphPaintListener eventGraphPaintListener;
  private ArrayList<IEventMarker> eventMarkers;
  private LineType hLines;
  private LineType vLines;
  private ILamportTrace trace;
  private Action zoomin;
  private Action zoomout;

  /**
   * Creates a new Logical Graph
   *
   * @param parent
   * @param style
   * @param viewSite
   * @param trace
   */
  public LogicalGraph( final Composite parent,
                       final int style,
                       final IViewSite viewSite,
                       final ILamportTrace trace )
  {
    super( parent, style | SWT.V_SCROLL | SWT.H_SCROLL );
    // layout
    GridData layoutData = new GridData();
    layoutData.horizontalAlignment = SWT.FILL;
    layoutData.grabExcessHorizontalSpace = true;
    layoutData.verticalAlignment = SWT.FILL;
    layoutData.grabExcessVerticalSpace = true;
    this.setLayoutData( layoutData );
    // gets the font
    Font font = getFont();
    // set defaults
    this.hLines = LineType.Lines_1;
    this.vLines = LineType.Lines_1;
    // create the PaintListener
    this.eventGraphPaintListener = new LogicalGraphPaintListener( this );
    this.eventGraphPaintListener.setHorizontalScrollBar( getHorizontalBar() );
    this.eventGraphPaintListener.setVerticalScrollBar( getVerticalBar() );
    this.eventGraphPaintListener.setFont( font );
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
    this.trace = trace;
    this.eventGraphPaintListener.setTrace( this.trace );
    TraceView view = ( TraceView )Activator.getDefault()
      .getWorkbench()
      .getActiveWorkbenchWindow()
      .getActivePage()
      .getActivePart();
    Menu menu = view.getContextMenuManager().createContextMenu( this );
    setMenu( menu );
    // add Listeners
    addPaintListener( this.eventGraphPaintListener );
    final LogicalGraphMouseAdapter mouseAdapter = new LogicalGraphMouseAdapter( this );
    addMouseListener( mouseAdapter );
    // addMouseMoveListener( mouseAdapter );
    @SuppressWarnings("unused")
    DefaultToolTip toolTip = new DefaultToolTip( this ) {

      @Override
      protected boolean shouldCreateToolTip( final Event event ) {
        Object obj = mouseAdapter.getObjectForPosition( event.x, event.y );
        return obj != null;
      }

      @Override
      protected String getText( final Event event ) {
        String result = null;
        Object obj = mouseAdapter.getObjectForPosition( event.x, event.y );
        if( obj != null ) {
          result = obj.toString();
        }
        return result;
      }
    };
    addKeyListener( new KeyAdapter() {

      @Override
      public void keyPressed( final KeyEvent event ) {
        switch( event.keyCode ) {
          case '+':
            zoomIn();
          break;
          case '-':
            zoomOut();
          break;
          /*
           * case SWT.ARROW_UP: scrollDec( getVerticalBar() ); break; case
           * SWT.ARROW_DOWN: scrollInc( getVerticalBar() ); break; case
           * SWT.ARROW_LEFT: scrollDec( getHorizontalBar() ); break; case
           * SWT.ARROW_RIGHT: scrollInc( getHorizontalBar() ); break;
           */
        }
      }
    } );
    addListener( SWT.Resize, new Listener() {

      public void handleEvent( final Event e ) {
        handleResize();
      }
    } );
    addDisposeListener( new DisposeListener() {

      public void widgetDisposed( final DisposeEvent e ) {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        store.removePropertyChangeListener( LogicalGraph.this.eventGraphPaintListener.listener );
      }
    } );
  }

  protected void handleResize() {
    this.eventGraphPaintListener.handleResize();
  }

  protected LineType getHLines() {
    return this.hLines;
  }

  protected LineType getVLines() {
    return this.vLines;
  }

  protected LogicalGraphPaintListener getEventGraphPaintListener() {
    return this.eventGraphPaintListener;
  }

  protected int getZoomFactor() {
    return this.eventGraphPaintListener.getZoomfactor();
  }

  private void setHLines( final LineType lines ) {
    this.hLines = lines;
    redraw();
  }

  private void setVLines( final LineType lines ) {
    this.vLines = lines;
    redraw();
  }

  /**
   * Returns the Event Markers
   *
   * @return IEventMarker
   */
  public ArrayList<IEventMarker> getEventMarkers() {
    return this.eventMarkers;
  }

  protected void changeLineStyle( final int direction ) {
    LineType style = null;
    if( direction < 0 ) {
      style = this.vLines;
    } else {
      style = this.hLines;
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

  @Override
  public IContributionItem[] getToolBarItems() {
    Action hLinesAction = new Action( Messages.getString( "LogicalGraph.VLineStyle" ), //$NON-NLS-1$
                                      Activator.getImageDescriptor( "icons/vertical.gif" ) ) //$NON-NLS-1$
    {

      @Override
      public void run() {
        changeLineStyle( -1 );
      }
    };
    Action vLinesAction = new Action( Messages.getString( "LogicalGraph.HLineStyle" ), //$NON-NLS-1$
                                      Activator.getImageDescriptor( "icons/horizontal.gif" ) ) //$NON-NLS-1$
    {

      @Override
      public void run() {
        changeLineStyle( 1 );
      }
    };
    this.zoomin = new Action( Messages.getString( "LogicalGraph.ZoomIn" ), //$NON-NLS-1$
                              Activator.getImageDescriptor( "icons/zin.gif" ) ) { //$NON-NLS-1$

      @Override
      public void run() {
        zoomIn();
      }
    };
    this.zoomout = new Action( Messages.getString( "LogicalGraph.ZoomOut" ), //$NON-NLS-1$
                               Activator.getImageDescriptor( "icons/zout.gif" ) ) { //$NON-NLS-1$

      @Override
      public void run() {
        zoomOut();
      }
    };
    this.zoomout.setEnabled( false );
    Vector<IContributionItem> items = new Vector<IContributionItem>();
    items.add( new ActionContributionItem( this.zoomin ) );
    items.add( new ActionContributionItem( this.zoomout ) );
    items.add( new ActionContributionItem( hLinesAction ) );
    items.add( new ActionContributionItem( vLinesAction ) );
    items.add( new Separator() );
    return items.toArray( new IContributionItem[ 0 ] );
  }

  void zoomIn() {
    int zoom = getZoomFactor();
    if( zoom < 8 ) {
      LogicalGraph.this.eventGraphPaintListener.setZoomfactor( ++zoom );
      LogicalGraph.this.redraw();
    }
    updateZoomButtons();
  }

  void zoomOut() {
    int zoom = getZoomFactor();
    if( zoom > 1 ) {
      LogicalGraph.this.eventGraphPaintListener.setZoomfactor( --zoom );
      LogicalGraph.this.redraw();
    }
    updateZoomButtons();
  }

  void updateZoomButtons() {
    int zoom = getZoomFactor();
    this.zoomout.setEnabled( zoom != 1 );
    this.zoomin.setEnabled( zoom != 8 );
  }

  /*
   * private void scrollDec( final ScrollBar scrollBar ) { int oldPos =
   * scrollBar.getSelection(); int newPos = oldPos - scrollBar.getIncrement();
   * if ( newPos < 0 ) newPos = 0; if ( newPos != oldPos ) {
   * scrollBar.setSelection( newPos ); LogicalGraph.this.redraw(); } } private
   * void scrollInc( final ScrollBar scrollBar ) { int oldPos =
   * scrollBar.getSelection(); int newPos = oldPos + scrollBar.getIncrement();
   * if ( newPos > scrollBar.getMaximum() ) newPos = scrollBar.getMaximum(); if (
   * newPos != oldPos ) { scrollBar.setSelection( newPos );
   * LogicalGraph.this.redraw(); } }
   */
  @Override
  public ITrace getTrace() {
    return this.trace;
  }
}
