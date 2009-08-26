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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.ui.PartInitException;

import eu.geclipse.traceview.IProcess;

public abstract class AbstractGraphMouseAdapter extends MouseAdapter implements MouseMoveListener {
  protected AbstractGraphVisualization graph;
  private int vRulerSelection;

  protected AbstractGraphMouseAdapter( final AbstractGraphVisualization graph ) {
    this.graph = graph;
  }

  public void mouseMove( final MouseEvent e ) {
    if ((e.stateMask & SWT.BUTTON1) != 0 && this.vRulerSelection != -1) {
      // TODO draw drag indicator in vruler
    }
  }

  @Override
  public void mouseUp( MouseEvent e ) {
    checkVRuler( e.x, e.y, false );
  }
  
  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.events.MouseEvent)
   */
  @Override
  public void mouseDown( final MouseEvent e ) {
    try {
      checkVRuler( e.x, e.y, true );
      Object[] objs = getObjectsForPosition( e.x, e.y );
      if( objs.length != 0 && (e.button == 1 || e.button == 3) ) {
        List<Object> objList = Arrays.asList( objs );
        ISelection selection = null;
        ISelectionProvider selectionProvider =
          Activator.getDefault()
            .getWorkbench()
            .getActiveWorkbenchWindow()
            .getActivePage()
            .showView( "eu.geclipse.traceview.views.TraceView" )
            .getSite()
            .getSelectionProvider();
        StructuredSelection oldSelection = ( StructuredSelection )selectionProvider.getSelection();
        if ((e.stateMask & SWT.CTRL) != 0 && oldSelection != null) {
          List oldSelectionList = new ArrayList( oldSelection.toList() );
          if (!oldSelectionList.containsAll( objList )) {
            if (e.button == 1) {
              for (Object obj : objs){
                if (!oldSelectionList.contains( obj )) oldSelectionList.add( obj );
              }
              selection = new StructuredSelection( oldSelectionList );
            }
          } else {
            selection = new StructuredSelection( oldSelectionList );
          }
        }
        if (selection == null) {
          selection = new StructuredSelection( objList );
        }
        selectionProvider.setSelection( selection );
        this.graph.redraw();
      }
    } catch( PartInitException exception ) {
      Activator.logException( exception );
    }
  }

  protected int getLineNumber( final int yPos ) {
    return getLineNumber( yPos, false );
  }

  protected int getLineNumber( final int yPos, boolean areaBelowProcess ) {
    int yOffset = this.graph.getEventGraphPaintListener().getYOffset();
    int eventSize = this.graph.getEventGraphPaintListener().getEventSize();
    int vSpace = this.graph.getEventGraphPaintListener().getVSpace();
    int numProcLines = this.graph.getLineToProcessMapping().size();
    int processLine = -1;
    int tmp = yPos + yOffset - eventSize / 2;
    if (areaBelowProcess) {
      processLine = tmp / vSpace;
    } else {
      if( tmp % vSpace <= eventSize / 2 ) {
        processLine = tmp / vSpace;
      }
      if( vSpace - ( tmp % vSpace ) <= eventSize / 2 ) {
        processLine = tmp / vSpace + 1;
      }
    }
    if ( processLine != -1 ) {
      processLine += this.graph.getEventGraphPaintListener().getFromProcessLine();
      if( processLine >= numProcLines ) {
        processLine = -1;
      }
    }
    return processLine;
  }
  
  public void checkVRuler( int xPos, int yPos, boolean clicked ) {
    int graphHeight = this.graph.getClientArea().height - 30;
    int y = getLineNumber( yPos );
    if (clicked) this.vRulerSelection = -1;
    if( xPos > 0 && yPos > 0 && xPos < 30 && yPos < graphHeight &&
        (clicked || this.vRulerSelection != -1)) {
      if (y != -1) {
        if (clicked) this.vRulerSelection = y;
        else {
          this.graph.mergeProcessLines( this.vRulerSelection, y );
        }
      } else {
        if (!clicked) {
          y = getLineNumber( yPos, true );
          if ( y <  this.vRulerSelection ) {
            if (y + 1 < this.graph.getLineToProcessMapping().size()) {
              this.graph.moveProcessLine( this.vRulerSelection, y + 1 );
            } 
          } else {
            this.graph.moveProcessLine( this.vRulerSelection, y );
          }
        }
      }
    }
  }

  public Object[] getObjectsForPosition( int xPos, int yPos ) {
    List<Object> objList = new LinkedList<Object>();
    int graphWidth = this.graph.getClientArea().width;
    int graphHeight = this.graph.getClientArea().height - 30;
    int y = getLineNumber( yPos );
    if( xPos > 30 && yPos > 0 && xPos < graphWidth && yPos < graphHeight ) {
      if( y != -1 ) {
        for (Integer proc : this.graph.getLineToProcessMapping().get( y ) ) {
          Object obj = getObjectOnProcess( xPos, proc.intValue() );
          if ( obj != null ) {
            objList.add( obj );
          }
        }
        if ( objList.size() == 0 ) {
          objList.addAll( getProcessesOnLine( y ) );
        }
      } else {
        objList.add( this.graph.getTrace() );
      }
    }
    return objList.toArray();
  }

  public List<IProcess> getProcessesOnLine( int line ) {
    List<IProcess> procList = new LinkedList<IProcess>();
    for (Integer proc : this.graph.getLineToProcessMapping().get( line ) ) {
      procList.add( this.graph.getTrace().getProcess( proc.intValue() ) );
    }
    return procList;
  }

  public abstract Object getObjectOnProcess( final int xPos, final int procNr );
}
