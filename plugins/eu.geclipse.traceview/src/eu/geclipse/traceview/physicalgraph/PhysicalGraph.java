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
 *    Andreas Roesch - initial implementation based on eu.geclipse.traceview.logicalgraph 
 *    Christof Klausecker - source code clean-up, adaptions
 *    
 *****************************************************************************/

package eu.geclipse.traceview.physicalgraph;

import java.util.ArrayList;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IViewSite;

import eu.geclipse.traceview.IEventMarker;
import eu.geclipse.traceview.IPhysicalTrace;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.TraceVisualization;
import eu.geclipse.traceview.internal.Activator;
import eu.geclipse.traceview.internal.LineType;
import eu.geclipse.traceview.internal.Messages;
import eu.geclipse.traceview.views.TraceView;

/**
 * A physical clock view
 */
public class PhysicalGraph extends TraceVisualization {

  protected PhysicalGraphPaintListener eventGraphPaintListener;
  private ArrayList<IEventMarker> eventMarkers;
  private LineType hLines;
  private LineType vLines;
  private IPhysicalTrace trace;

  /**
   * Creates a new Logical Graph
   * 
   * @param parent
   * @param style
   * @param viewSite
   * @param trace
   */
  public PhysicalGraph( final Composite parent,
                        final int style,
                        final IViewSite viewSite,
                        final IPhysicalTrace trace )
  {
    super( parent, style | SWT.V_SCROLL | SWT.H_SCROLL );
    GridData layoutData = new GridData();
    layoutData.horizontalAlignment = SWT.FILL;
    layoutData.grabExcessHorizontalSpace = true;
    layoutData.verticalAlignment = SWT.FILL;
    layoutData.grabExcessVerticalSpace = true;
    this.setLayoutData( layoutData );
    Font font = getFont();
    this.hLines = LineType.Lines_1;
    this.vLines = LineType.Lines_1;
    this.eventMarkers = new ArrayList<IEventMarker>();
    this.eventGraphPaintListener = new PhysicalGraphPaintListener( this );
    this.eventGraphPaintListener.setHorizontalScrollBar( getHorizontalBar() );
    this.eventGraphPaintListener.setVerticalScrollBar( getVerticalBar() );
    this.eventGraphPaintListener.setFont( font );
    for( IConfigurationElement configurationElement : Platform.getExtensionRegistry()
      .getConfigurationElementsFor( "eu.geclipse.traceview.EventMarker" ) ) { //$NON-NLS-1$
      try {
        IEventMarker eventMarker = ( IEventMarker )configurationElement.createExecutableExtension( "class" ); //$NON-NLS-1$
        eventMarker.setTrace( trace );
        this.eventMarkers.add( eventMarker );
      } catch( CoreException coreException ) {
        coreException.printStackTrace();
      }
    }
    TraceView view = ( TraceView )Activator.getDefault()
      .getWorkbench()
      .getActiveWorkbenchWindow()
      .getActivePage()
      .getActivePart();
    Menu menu = view.getContextMenuManager().createContextMenu( this );
    setMenu( menu );
    addPaintListener( this.eventGraphPaintListener );
    addMouseListener( new PhysicalGraphMouseAdapter( this ) );
    addListener( SWT.Resize, new Listener() {

      public void handleEvent( final Event e ) {
        handleResize();
      }
    } );
    this.trace = trace;
    this.eventGraphPaintListener.setTrace( this.trace );
    addDisposeListener( new DisposeListener() {

      public void widgetDisposed( final DisposeEvent e ) {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        store.removePropertyChangeListener( PhysicalGraph.this.eventGraphPaintListener.listener );
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

  protected PhysicalGraphPaintListener getEventGraphPaintListener() {
    return this.eventGraphPaintListener;
  }

  // protected int getZoomFactor() {
  // return this.eventGraphPaintListener.getZoomfactor();
  // }
  protected int getVZoomFactor() {
    return this.eventGraphPaintListener.getVZoomfactor();
  }

  protected float getHZoomFactor() {
    return this.eventGraphPaintListener.getHZoomfactor();
  }

  protected void setHLines( final LineType lines ) {
    this.hLines = lines;
    redraw();
  }

  protected void setVLines( final LineType lines ) {
    this.vLines = lines;
    redraw();
  }

//  protected void setZoomFactor( final int zoomfactor ) {
//    this.eventGraphPaintListener.setZoomfactor( zoomfactor );
//    redraw();
//  }

  protected void setHZoomFactor( final float zoomfactor ) {
    this.eventGraphPaintListener.setHZoomfactor( zoomfactor );
    redraw();
  }

  protected void setVZoomFactor( final int zoomfactor ) {
    this.eventGraphPaintListener.setVZoomfactor( zoomfactor );
    redraw();
  }

  protected ArrayList<IEventMarker> getEventMarkers() {
    return this.eventMarkers;
  }

  protected void resetOrdering() {
    this.eventGraphPaintListener.resetOrdering();
    redraw();
  }

  protected void toggleMessageDrawing() {
    boolean drawMessages = this.eventGraphPaintListener.isDrawMessages();
    this.eventGraphPaintListener.setDrawMessages( !drawMessages );
  }

  protected void lineStyle( final int direction ) {
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

  @Override
  public IContributionItem[] getToolBarItems() {
    Action vzoomin = new Action( Messages.getString( "PhysicalGraph.VerticalZoomIn" ), //$NON-NLS-1$
                                 Activator.getImageDescriptor( "icons/vertical_zoom_in.gif" ) ) { //$NON-NLS-1$

      @Override
      public void run() {
        int zoom = getVZoomFactor();
        if( zoom < 20 )
          setVZoomFactor( ++zoom );
      }
    };
    Action vzoomout = new Action( Messages.getString( "PhysicalGraph.VerticalZoomOut" ), //$NON-NLS-1$
                                  Activator.getImageDescriptor( "icons/vertical_zoom_out.gif" ) ) { //$NON-NLS-1$

      @Override
      public void run() {
        int zoom = getVZoomFactor();
        if( zoom > 1 )
          setVZoomFactor( --zoom );
      }
    };
    Action hzoomin = new Action( Messages.getString( "PhysicalGraph.HorizontalZoomIn" ), //$NON-NLS-1$
                                 Activator.getImageDescriptor( "icons/horizontal_zoom_in.gif" ) ) { //$NON-NLS-1$

      @Override
      public void run() {
        float zoom = getHZoomFactor();
        if( zoom < 1 )
          setHZoomFactor( zoom * 2 );
        else if( zoom < 20 )
          setHZoomFactor( ++zoom );
      }
    };
    Action hzoomout = new Action( Messages.getString( "PhysicalGraph.HorizontalZoomOut" ), //$NON-NLS-1$
                                  Activator.getImageDescriptor( "icons/horizontal_zoom_out.gif" ) ) { //$NON-NLS-1$

      @Override
      public void run() {
        float zoom = getHZoomFactor();
        if( zoom > 1 )
          setHZoomFactor( --zoom );
        else
          setHZoomFactor( zoom / 2 );
      }
    };
    Action reset = new Action( Messages.getString( "PhysicalGraph.Reset" ), //$NON-NLS-1$
                               Activator.getImageDescriptor( "icons/reset.gif" ) ) { //$NON-NLS-1$

      @Override
      public void run() {
        resetOrdering();
      }
    };
    Action messages = new Action( Messages.getString( "PhysicalGraph.Toggle_Messages" ), //$NON-NLS-1$
                                  Activator.getImageDescriptor( "icons/toggle_messages.gif" ) ) { //$NON-NLS-1$

      @Override
      public int getStyle() {
        return IAction.AS_CHECK_BOX;
      }

      @Override
      public boolean isChecked() {
        return PhysicalGraph.this.eventGraphPaintListener.isDrawMessages();
      }

      @Override
      public void run() {
        toggleMessageDrawing();
      }
    };
    Vector<IContributionItem> items = new Vector<IContributionItem>();
    items.add( new ActionContributionItem( reset ) );
    items.add( new ActionContributionItem( messages ) );
    items.add( new ActionContributionItem( vzoomin ) );
    items.add( new ActionContributionItem( vzoomout ) );
    items.add( new ActionContributionItem( hzoomin ) );
    items.add( new ActionContributionItem( hzoomout ) );
    items.add( new Separator() );
    return items.toArray( new IContributionItem[ 0 ] );
  }

  @Override
  public ITrace getTrace() {
    return this.trace;
  }
}
