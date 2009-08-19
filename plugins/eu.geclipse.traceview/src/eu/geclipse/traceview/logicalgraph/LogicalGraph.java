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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewSite;

import eu.geclipse.traceview.IEventMarker;
import eu.geclipse.traceview.ILamportTrace;
import eu.geclipse.traceview.internal.AbstractGraphVisualization;
import eu.geclipse.traceview.internal.Activator;
import eu.geclipse.traceview.internal.Messages;

/**
 * Logical Clock View
 */
public class LogicalGraph extends AbstractGraphVisualization {
  private Action zoomin;
  private Action zoomout;
  private LogicalGraphPaintListener eventGraphPaintListener;

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
    super(parent, style, viewSite, trace );
    this.eventGraphPaintListener = new LogicalGraphPaintListener( this );
    registerPaintListener( this.eventGraphPaintListener );
    registerMouseListener( new LogicalGraphMouseAdapter( this ) );
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
//          case SWT.ARROW_UP:    scrollDec( getVerticalBar() ); break;
//          case SWT.ARROW_DOWN:  scrollInc( getVerticalBar() ); break;
//          case SWT.ARROW_LEFT:  scrollDec( getHorizontalBar() ); break;
//          case SWT.ARROW_RIGHT: scrollInc( getHorizontalBar() ); break;
        }
      }
    } );
  }

  public LogicalGraphPaintListener getEventGraphPaintListener() {
    return this.eventGraphPaintListener;
  }

  protected int getZoomFactor() {
    return this.eventGraphPaintListener.getZoomfactor();
  }

  /**
   * Returns the Event Markers
   *
   * @return IEventMarker
   */
  public ArrayList<IEventMarker> getEventMarkers() {
    return this.eventMarkers;
  }

  @Override
  public IContributionItem[] getToolBarItems() {
    IContributionItem[] superItems = super.getToolBarItems();
    Action hLinesAction = new Action( Messages.getString( "LogicalGraph.VLineStyle" ), //$NON-NLS-1$
                                      Activator.getImageDescriptor( "icons/vertical.gif" ) ) { //$NON-NLS-1$
      @Override
      public void run() {
        changeLineStyle( -1 );
      }
    };
    Action vLinesAction = new Action( Messages.getString( "LogicalGraph.HLineStyle" ), //$NON-NLS-1$
                                      Activator.getImageDescriptor( "icons/horizontal.gif" ) ) { //$NON-NLS-1$
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
    for ( IContributionItem item : superItems ) items.add( item );
    items.add( new ActionContributionItem( this.zoomin ) );
    items.add( new ActionContributionItem( this.zoomout ) );
    items.add( new ActionContributionItem( hLinesAction ) );
    items.add( new ActionContributionItem( vLinesAction ) );
    items.add( new Separator() );
    return items.toArray( new IContributionItem[ items.size() ] );
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

  
//  private void scrollDec( final ScrollBar scrollBar ) {
//    int oldPos = scrollBar.getSelection();
//    int newPos = oldPos - scrollBar.getIncrement();
//    if( newPos < 0 )
//      newPos = 0;
//    if( newPos != oldPos ) {
//      scrollBar.setSelection( newPos );
//      LogicalGraph.this.redraw();
//    }
//  }
//
//  private void scrollInc( final ScrollBar scrollBar ) {
//    int oldPos = scrollBar.getSelection();
//    int newPos = oldPos + scrollBar.getIncrement();
//    if( newPos > scrollBar.getMaximum() )
//      newPos = scrollBar.getMaximum();
//    if( newPos != oldPos ) {
//      scrollBar.setSelection( newPos );
//      LogicalGraph.this.redraw();
//    }
//  }   

}
