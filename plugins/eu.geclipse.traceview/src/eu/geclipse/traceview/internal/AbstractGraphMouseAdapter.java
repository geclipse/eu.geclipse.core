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

  public abstract Object getObjectForPosition( final int xPos, final int yPos );
}
