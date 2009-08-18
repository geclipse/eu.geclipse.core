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

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.ui.PartInitException;

import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.IProcess;
import eu.geclipse.traceview.ITrace;

public abstract class AbstractGraphMouseAdapter extends MouseAdapter {
  protected AbstractGraphVisualization graph;

  protected AbstractGraphMouseAdapter( final AbstractGraphVisualization graph ) {
    this.graph = graph;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.events.MouseEvent)
   */
  @Override
  public void mouseDown( final MouseEvent e ) {
    Object obj = getObjectForPosition( e.x, e.y );
    if( e.button == 1 || e.button == 3 ) {
      if( obj instanceof IEvent || obj instanceof IProcess
          || obj instanceof ITrace ) {
        try {
          ISelection selection = new StructuredSelection( obj );
          Activator.getDefault()
            .getWorkbench()
            .getActiveWorkbenchWindow()
            .getActivePage()
            .showView( "eu.geclipse.traceview.views.TraceView" )
            .getSite()
            .getSelectionProvider()
            .setSelection( selection );
          this.graph.redraw();
        } catch( PartInitException exception ) {
          Activator.logException( exception );
        }
      }
    }
  }

  protected int getLineNumber( final int yPos ) {
    int yOffset = this.graph.getEventGraphPaintListener().getYOffset();
    int eventSize = this.graph.getEventGraphPaintListener().getEventSize();
    int vSpace = this.graph.getEventGraphPaintListener().getVSpace();
    int numProc = this.graph.getTrace().getNumberOfProcesses();
    int process = -1;
    int tmp = yPos + yOffset - eventSize / 2;
    if( tmp % vSpace <= eventSize / 2 ) {
      process = tmp / vSpace;
    }
    if( vSpace - ( tmp % vSpace ) <= eventSize / 2 ) {
      process = tmp / vSpace + 1;
    }
    if ( process != -1 ) {
      process += this.graph.getEventGraphPaintListener().getFromProcess();
      if( process >= numProc ) {
        process = -1;
      }
    }
    return process;
  }

  public Object getObjectForPosition( int xPos, int yPos ) {
    Object obj = null;
    int graphWidth = this.graph.getClientArea().width;
    int graphHeight = this.graph.getClientArea().height - 30;
    int y = getLineNumber( yPos );
    if( xPos > 30 && yPos > 0 && xPos < graphWidth && yPos < graphHeight ) {
      if( y != -1 ) {
        int procNr = y; // TODO map line number to process number
        obj = getObjectOnProcess( xPos, procNr );
      } else {
        obj = this.graph.getTrace();
      }
    }
    return obj;
  }

  public abstract Object getObjectOnProcess( final int xPos, final int procNr );
}
