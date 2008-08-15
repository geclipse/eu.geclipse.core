/*******************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Christof Klausecker GUP, JKU - initial API and implementation
 ******************************************************************************/
package eu.geclipse.traceview.debug.actions;

import static eu.geclipse.traceview.debug.Activator.logException;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.core.model.ISourceRoot;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.cdt.debug.core.CDebugCorePlugin;
import org.eclipse.cdt.debug.core.model.ICBreakpoint;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.traceview.EventType;
import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.ISourceLocation;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.ITraceView;
import eu.geclipse.traceview.IVectorEvent;
import eu.geclipse.traceview.debug.Activator;
import eu.geclipse.traceview.debug.EventBreakpoint;
import eu.geclipse.traceview.utils.VectorEventComparator;

/**
 * Global Breakpoint Action
 */
public class GlobalBreakpointAction extends AbstractEventBreakpointAction {

  private int[] first;
  private int[] last;

  public void run( final IAction action ) {
    if( this.selectedObject instanceof IEvent ) {
      IEvent event = ( IEvent )this.selectedObject;
      if( event.getType() == EventType.RECV ) {
        event = event.getPartnerEvent();
      }
      ITrace trace = event.getProcess().getTrace();
      // calculate breakpoint set
      boolean valid = createSet( event );
      if( !valid ) {
        ErrorDialog.openError( Display.getDefault().getActiveShell(),
                               "Cannot set global breakpoint",
                               "Please select a different event.",
                               new Status( IStatus.INFO,
                                           Activator.PLUGIN_ID,
                                           "There is no global breakpoint for this event." ) );
      } else {
        // set breakpoints
        ArrayList<EventBreakpoint> eventBreakpoints = new ArrayList<EventBreakpoint>();
        try {
          for( int i = 0; i < trace.getNumberOfProcesses(); i++ ) {
            if( this.last[ i ] != -1
                && this.last[ i ] <= trace.getProcess( i )
                  .getMaximumLogicalClock() )
            {
              EventBreakpoint eventBreakpoint = createEventBreakpoint( trace.getProcess( i )
                .getEventByLogicalClock( this.last[ i ] ) );
              eventBreakpoints.add( eventBreakpoint );
            }
          }
          DebugPlugin.getDefault()
            .getBreakpointManager()
            .addBreakpoints( eventBreakpoints.toArray( new EventBreakpoint[ 0 ] ) );
        } catch( IndexOutOfBoundsException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch( CoreException e ) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        // redraw traceview
        Display.getDefault().asyncExec( new Runnable() {

          public void run() {
            try {
              ITraceView traceView = ( ITraceView )PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow()
                .getActivePage()
                .showView( "eu.geclipse.traceview.views.TraceView" ); //$NON-NLS-1$
              traceView.redraw();
            } catch( PartInitException partInitException ) {
              Activator.logException( partInitException );
            }
          }
        } );
      }
    }
  }

  private boolean createSet( final IEvent event ) {
    ITrace trace = event.getProcess().getTrace();
    this.last = new int[ trace.getNumberOfProcesses() ];
    this.first = new int[ trace.getNumberOfProcesses() ];
    // get first and last
    IVectorEvent vectorEvent = ( IVectorEvent )event;
    VectorEventComparator comparator = new VectorEventComparator();
    IVectorEvent vectorEvent2 = null;
    for( int i = 0; i < trace.getNumberOfProcesses(); i++ ) {
      if( i != event.getProcessId() ) {
        // first
        this.first[ i ] = vectorEvent.getLogicalClock();
        if( trace.getProcess( i ).getMaximumLogicalClock() < this.first[ i ] ) {
          this.first[ i ] = trace.getProcess( i ).getMaximumLogicalClock();
        }
        vectorEvent2 = ( IVectorEvent )trace.getProcess( i )
          .getEventByLogicalClock( this.first[ i ] );
        if( comparator.compare( vectorEvent2, vectorEvent ) < 0 ) {
          do {
            this.first[ i ]++;
            vectorEvent2 = ( IVectorEvent )trace.getProcess( i )
              .getEventByLogicalClock( this.first[ i ] );
          } while( vectorEvent2 != null
                   && comparator.compare( vectorEvent2, vectorEvent ) < 0 );
        } else if( comparator.compare( vectorEvent2, vectorEvent ) >= 0 ) {
          do {
            this.first[ i ]--;
            vectorEvent2 = ( IVectorEvent )trace.getProcess( i )
              .getEventByLogicalClock( this.first[ i ] );
          } while( this.first[ i ] > -1
                   && vectorEvent2 != null
                   && comparator.compare( vectorEvent2, vectorEvent ) >= 0 );
          this.first[ i ]++;
        }
        // last
        this.last[ i ] = vectorEvent.getLogicalClock();
        if( trace.getProcess( i ).getMaximumLogicalClock() < this.last[ i ] ) {
          this.last[ i ] = trace.getProcess( i ).getMaximumLogicalClock();
        }
        vectorEvent2 = ( IVectorEvent )trace.getProcess( i )
          .getEventByLogicalClock( this.last[ i ] );
        if( comparator.compare( vectorEvent2, vectorEvent ) <= 0 ) {
          do {
            this.last[ i ]++;
            vectorEvent2 = ( IVectorEvent )trace.getProcess( i )
              .getEventByLogicalClock( this.last[ i ] );
          } while( vectorEvent2 != null
                   && comparator.compare( vectorEvent2, vectorEvent ) <= 0 );
        } else if( comparator.compare( vectorEvent2, vectorEvent ) > 0 ) {
          do {
            this.last[ i ]--;
            vectorEvent2 = ( IVectorEvent )trace.getProcess( i )
              .getEventByLogicalClock( this.last[ i ] );
          } while( this.last[ i ] > -1
                   && vectorEvent2 != null
                   && comparator.compare( vectorEvent2, vectorEvent ) > 0 );
          this.last[ i ]++;
        }
      } else {
        this.first[ i ] = event.getLogicalClock();
        this.last[ i ] = event.getLogicalClock();
      }
      System.out.println( i + " " + this.first[ i ] + " " + this.last[ i ] );
    }
    // calc
    boolean changed = true;
    while( changed ) {
      changed = false;
      for( int i = 0; i < trace.getNumberOfProcesses(); i++ ) {
        int start = this.first[ i ];
        while( start <= this.last[ i ] && !changed ) {
          IEvent e = trace.getProcess( i ).getEventByLogicalClock( start );
          if( e != null
              && e.getPartnerLogicalClock() > this.last[ e.getPartnerProcessId() ] )
          {
            if( start != this.last[ i ] ) {
              this.last[ i ] = start;
              changed = true;
            }
          }
          start++;
        }
      }
    }
    // for( int i = 0; i < trace.getNumberOfProcesses(); i++ ) {
    // System.out.println( i + " " + this.first[ i ] + " " + this.last[ i ] );
    // }
    // check
    boolean valid = true;
    for( int i = 0; i < trace.getNumberOfProcesses(); i++ ) {
      if( this.first[ i ] != 0 ) {
        IEvent e = trace.getProcess( i )
          .getEventByLogicalClock( this.first[ i ] - 1 );
        if( e.getPartnerLogicalClock() > this.last[ e.getPartnerProcessId() ] )
        {
          // System.out.println( i + " " + ( this.first[ i ] - 1 ) );
          valid = false;
          break;
        }
      }
      for( int j = this.first[ i ]; j < this.last[ i ]; j++ ) {
        IEvent e = trace.getProcess( i ).getEventByLogicalClock( j );
        if( e.getPartnerLogicalClock() > this.last[ e.getPartnerProcessId() ] )
        {
          valid = false;
          break;
        }
      }
    }
    return valid;
  }
}
