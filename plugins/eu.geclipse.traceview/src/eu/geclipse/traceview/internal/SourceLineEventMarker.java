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
 *    Christof Klausecker - MNM-Team, LMU Munich
 *****************************************************************************/

package eu.geclipse.traceview.internal;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.IEventMarker;
import eu.geclipse.traceview.ISourceLocation;
import eu.geclipse.traceview.ITrace;

public class SourceLineEventMarker implements IEventMarker {

  Set<IEvent> selectedEvents = new HashSet<IEvent>();

  public SourceLineEventMarker() {
    ISelectionService selectionService = PlatformUI.getWorkbench()
      .getActiveWorkbenchWindow()
      .getSelectionService();
    selectionService.addSelectionListener( "eu.geclipse.traceview.views.TraceView", //$NON-NLS-1$
                                           new ISelectionListener() {

                                             public void selectionChanged( final IWorkbenchPart part,
                                                                           final ISelection selection )
                                             {
                                               StructuredSelection sSel = ( StructuredSelection )selection;
                                               SourceLineEventMarker.this.selectedEvents = new HashSet<IEvent>();
                                               for( Object obj : sSel.toList() )
                                               {
                                                 if( obj instanceof ISourceLocation )
                                                 {
                                                   IEvent event = ( IEvent )obj;
                                                   SourceLineEventMarker.this.selectedEvents.add( event );
                                                 }
                                               }
                                             }
                                           } );
  }

  public Color getBackgroundColor( final int type ) {
    return Display.getDefault().getSystemColor( SWT.COLOR_CYAN );
  }

  public Color getForegroundColor( final int type ) {
    return Display.getDefault().getSystemColor( SWT.COLOR_BLACK );
  }

  public int getLineStyle( final int type ) {
    return SWT.LINE_SOLID;
  }

  public int getLineWidth( final int type ) {
    return 1;
  }

  public int mark( final IEvent event ) {
    int result = 0;
    if( event instanceof ISourceLocation ) {
      ISourceLocation sourceLocation = ( ISourceLocation )event;
      for( IEvent selectedEvent : this.selectedEvents ) {
        ISourceLocation selectedISourceLocation = ( ISourceLocation )selectedEvent;
        if( sourceLocation.getSourceFilename() != null
            && sourceLocation.getSourceFilename()
              .equals( selectedISourceLocation.getSourceFilename() )
            && sourceLocation.getSourceLineNumber() == selectedISourceLocation.getSourceLineNumber()
            && event.getProcess().getTrace() == selectedEvent.getProcess()
              .getTrace() )
        {
          result = IEventMarker.Ellipse_Event;
          break;
        }
      }
    }
    return result;
  }

  public String getToolTip() {
    return null;
  }

  public Color getCanvasBackgroundColor() {
    return null;
  }

  public void setTrace( final ITrace trace ) {
  }
}
