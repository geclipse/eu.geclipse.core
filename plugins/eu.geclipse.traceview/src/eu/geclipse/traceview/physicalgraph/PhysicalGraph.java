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

import java.util.Vector;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewSite;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.IPhysicalEvent;
import eu.geclipse.traceview.IPhysicalProcess;
import eu.geclipse.traceview.IPhysicalTrace;
import eu.geclipse.traceview.internal.AbstractGraphVisualization;
import eu.geclipse.traceview.internal.Activator;
import eu.geclipse.traceview.internal.Messages;

/**
 * A physical clock view
 */
public class PhysicalGraph extends AbstractGraphVisualization {
  private PhysicalGraphPaintListener eventGraphPaintListener;

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
    super( parent, style, viewSite, trace );
    this.eventGraphPaintListener = new PhysicalGraphPaintListener( this );
    registerPaintListener( this.eventGraphPaintListener );
    registerMouseListener( new PhysicalGraphMouseAdapter( this ) );
  }

  public PhysicalGraphPaintListener getEventGraphPaintListener() {
    return this.eventGraphPaintListener;
  }

  protected int getVZoomFactor() {
    return this.eventGraphPaintListener.getVZoomfactor();
  }

  protected float getHZoomFactor() {
    return this.eventGraphPaintListener.getHZoomfactor();
  }

  protected void setHZoomFactor( final float zoomfactor ) {
    this.eventGraphPaintListener.setHZoomfactor( zoomfactor );
    redraw();
  }

  protected void setVZoomFactor( final int zoomfactor ) {
    this.eventGraphPaintListener.setVZoomfactor( zoomfactor );
    redraw();
  }

  protected void resetOrdering() {
    this.eventGraphPaintListener.resetOrdering();
    redraw();
  }
  
  protected void calcStartTimeOffset() {
    for (int i = 0; i < trace.getNumberOfProcesses(); i++) {
      IPhysicalProcess proc = ( IPhysicalProcess )trace.getProcess( i );
      for(int j = 0; j < Math.min( 100, proc.getMaximumLogicalClock() ); j++) {
        IPhysicalEvent event = ( IPhysicalEvent )proc.getEventByLogicalClock( j );
        if ((event.getType().equals( EventType.SEND ) ||
             event.getType().equals( EventType.RECV ))
             && event.getPartnerEvent() != null) {
          int diff = event.getPhysicalStopClock() - event.getPartnerPhysicalStopClock();
          if (event.getType() == EventType.RECV) diff *= -1;
          if (diff > 0) {
            IPhysicalProcess partnerProc = (IPhysicalProcess)trace.getProcess( event.getPartnerProcessId() );
            partnerProc.setStartTimeOffset( partnerProc.getStartTimeOffset() + diff );
          }
        }
      }
    }
  }

  protected void toggleMessageDrawing() {
    boolean drawMessages = this.eventGraphPaintListener.isDrawMessages();
    this.eventGraphPaintListener.setDrawMessages( !drawMessages );
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
    Action calcStartTimeOffset = new Action( Messages.getString("PhysicalGraph.calcStartTimeOffset"), //$NON-NLS-1$
                                             Activator.getImageDescriptor( "icons/toggle_messages.gif" ) ) { //$NON-NLS-1$

      @Override
      public void run() {
        calcStartTimeOffset();
        PhysicalGraph.this.eventGraphPaintListener.updateMaxTimeStop();
        redraw();
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
    items.add( new ActionContributionItem( calcStartTimeOffset ) );
    items.add( new ActionContributionItem( reset ) );
    items.add( new ActionContributionItem( messages ) );
    items.add( new ActionContributionItem( vzoomin ) );
    items.add( new ActionContributionItem( vzoomout ) );
    items.add( new ActionContributionItem( hzoomin ) );
    items.add( new ActionContributionItem( hzoomout ) );
    items.add( new Separator() );
    return items.toArray( new IContributionItem[ 0 ] );
  }
}
