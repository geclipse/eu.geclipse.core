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
import java.util.List;
import java.util.LinkedList;
import java.util.Set;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.PartInitException;

import eu.geclipse.traceview.IProcess;

public abstract class AbstractGraphMouseAdapter implements MouseListener, MouseMoveListener {
  protected AbstractGraphVisualization graph;
  private int vRulerSelection;
  private Cursor moveProcCursor;
  private Rectangle selectionRect;

  protected AbstractGraphMouseAdapter( final AbstractGraphVisualization graph ) {
    this.graph = graph;
    this.moveProcCursor= new Cursor(this.graph.getDisplay(), SWT.CURSOR_SIZENS);
  }

  public void mouseDoubleClick( MouseEvent e ) {
    int graphHeight = this.graph.getClientArea().height - 30;
    if( e.x > 0 && e.y > 0 && e.x < 30 && e.y < graphHeight ) {
      int y = getLineNumber( e.y );
      if (y != -1) {
        boolean[] hideProcs = this.graph.getHideProcess();
        Set<Integer> procs = this.graph.getLineToProcessMapping().get( y );
        for (Integer proc : procs) {
          hideProcs[proc.intValue()] ^= true;
        }
        this.graph.setHideProcess( hideProcs );
      }
    }
  }

  public void mouseMove( final MouseEvent e ) {
    if ((e.stateMask & SWT.BUTTON1) != 0) {
      if (this.vRulerSelection != -1) {
        GC rulerGC = new GC(this.graph);
        this.graph.getEventGraphPaintListener().drawVRulerWithMovingLine( rulerGC, this.vRulerSelection, e.y );
        rulerGC.dispose();
      }
      if ( selectionRect != null ) {
        this.graph.getEventGraphPaintListener().updateSelectionRectangle(selectionRect, e);
      }
    } else if ((e.stateMask & SWT.BUTTON1) == 0) {
      updateMouseCursor( e );
    }
  }
  
  protected void updateMouseCursor( final MouseEvent e ) {
    int graphHeight = this.graph.getClientArea().height - 30;
    if( e.x > 0 && e.y > 0 && e.x < 30 && e.y < graphHeight &&
        getLineNumber( e.y ) != -1 ) {
      this.graph.setCursor( this.moveProcCursor );
    } else {
      this.graph.setCursor( null );
    }
  }

  public void mouseUp( MouseEvent e ) {
    checkVRuler( e.x, e.y, false );
    updateMouseCursor( e );
    if ( selectionRect != null ) {
      this.graph.getEventGraphPaintListener().updateSelectionRectangle(selectionRect, null);
      Object[] objs = getObjectsForArea(selectionRect);
      List<Object> objList = Arrays.asList( objs );
      if (!objList.isEmpty()) {
        updateSelection(e, objList);
        this.graph.redraw();
      }
      selectionRect = null;
    }
  }
  
  private void updateSelection(final MouseEvent e, final List<Object> newSelection) {
    try {
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
        if (!oldSelectionList.containsAll( newSelection )) {
          if (e.button == 1) {
            for (Object obj : newSelection){
              if (!oldSelectionList.contains( obj )) oldSelectionList.add( obj );
            }
            selection = new StructuredSelection( oldSelectionList );
          }
        } else {
          selection = new StructuredSelection( oldSelectionList );
        }
      }
      if (selection == null) {
        selection = new StructuredSelection( newSelection );
      }
      selectionProvider.setSelection( selection );
    } catch( PartInitException exception ) {
      Activator.logException( exception );
    }  
  }

  public void mouseDown( final MouseEvent e ) {
    checkVRuler( e.x, e.y, true );
    Object[] objs = getObjectsForPosition( e.x, e.y, (e.stateMask & SWT.CTRL) == 0 );
    if( objs.length != 0 && (e.button == 1 || e.button == 3) ) {
      List<Object> objList = Arrays.asList( objs );
      updateSelection(e, objList);
      this.graph.redraw();
    }
    if (e.button == 1 && this.graph.getEventGraphPaintListener().isInGraphArea(e.x, e.y)) {
      selectionRect = new Rectangle(e.x, e.y, 0, 0);
    }
  }

  protected int getLineNumber( final int yPos ) {
    return getLineNumber( yPos, false, false );
  }

  protected int getLineNumber( final int yPos, boolean areaBelowProcess, boolean mapAreaBelowLastLineToLastLine ) {
    int yOffset = this.graph.getEventGraphPaintListener().getYOffset();
    int eventSize = this.graph.getEventGraphPaintListener().getEventSize();
    int vSpace = this.graph.getEventGraphPaintListener().getVSpace();
    int numProcLines = this.graph.getLineToProcessMapping().size();
    int processLine = -1;
    int tmp = yPos + yOffset - eventSize / 2;
    if (areaBelowProcess) {
      if (tmp < 0) processLine = -1;
      else processLine = tmp / vSpace;
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
        if (mapAreaBelowLastLineToLastLine) {
          processLine = numProcLines - 1;
        } else {
          processLine = -1;
        }
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
          y = getLineNumber( yPos, true, true );
          if ( y <  this.vRulerSelection ) {
            if (y + 1 < this.graph.getLineToProcessMapping().size()) {
              this.graph.moveProcessLine( this.vRulerSelection, y + 1 );
            } 
          } else {
            this.graph.moveProcessLine( this.vRulerSelection, y );
          }
        }
      }
    } else if ( !clicked && this.vRulerSelection != -1) {
      this.vRulerSelection = -1;
      this.graph.redraw();
    }
  }

  public Object[] getObjectsForPosition( int xPos, int yPos, boolean allowTraceObj ) {
    List<Object> objList = new LinkedList<Object>();
    int y = getLineNumber( yPos );
    if( this.graph.getEventGraphPaintListener().isInGraphArea(xPos, yPos) ) {
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
        if (allowTraceObj) objList.add( this.graph.getTrace() );
      }
    }
    return objList.toArray();
  }

  public Object[] getObjectsForArea( final Rectangle rect ) {
    Rectangle rect2 = new Rectangle( rect.x, rect.y, rect.width, rect.height );
    if( rect2.width < 0 ) {
      rect2.x += rect2.width;
      rect2.width *= -1;
    }
    if( rect2.height < 0 ) {
      rect2.y += rect2.height;
      rect2.height *= -1;
    }
    List<Object> objList = new LinkedList<Object>();
    int yStart = getLineNumber( rect2.y, true, true ) + 1;
    int yEnd = getLineNumber( rect2.y + rect2.height, true, true );
    for( int y = yStart; y <= yEnd; y++ ) {
      if( y != -1 ) {
        for (Integer proc : this.graph.getLineToProcessMapping().get( y ) ) {
          List<Object> objs = getObjectsOnProcess( rect2.x, rect2.x + rect2.width, proc.intValue() );
          objList.addAll( objs );
        }
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

  public abstract List<Object> getObjectsOnProcess( final int xStart, final int xEnd, final int procNr );
}
