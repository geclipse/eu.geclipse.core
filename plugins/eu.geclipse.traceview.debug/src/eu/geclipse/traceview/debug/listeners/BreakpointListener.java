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

package eu.geclipse.traceview.debug.listeners;

import java.util.StringTokenizer;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.debug.core.CDIDebugModel;
import org.eclipse.cdt.debug.core.ICDTLaunchConfigurationConstants;
import org.eclipse.cdt.debug.core.model.ICBreakpointFilterExtension;
import org.eclipse.cdt.debug.core.model.ICDebugTarget;
import org.eclipse.cdt.debug.core.model.ICLineBreakpoint;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.ISourceLocation;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.ITraceView;
import eu.geclipse.traceview.debug.Activator;
import eu.geclipse.traceview.debug.EventBreakpoint;

/**
 * Removes wrong targets from Breakpoint
 */
public class BreakpointListener implements IBreakpointListener {

  /**
   * Redraws the TraceViewer
   */
  private void redraw( final IBreakpoint breakpoint ) {
    if( breakpoint instanceof ICLineBreakpoint ) {
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

  protected boolean otherBreakpoints( final EventBreakpoint eventBreakpoint )
    throws CoreException
  {
    for( IBreakpoint breakpoint : DebugPlugin.getDefault()
      .getBreakpointManager()
      .getBreakpoints() )
    {
      if( breakpoint instanceof EventBreakpoint ) {
        EventBreakpoint otherEventBreakpoint = ( EventBreakpoint )breakpoint;
        if( eventBreakpoint.getProjectName()
          .equals( otherEventBreakpoint.getProjectName() )
            && eventBreakpoint.getTracePath()
              .equals( otherEventBreakpoint.getTracePath() )
            && eventBreakpoint.getNumberOfProcesses() == otherEventBreakpoint.getNumberOfProcesses()
            && eventBreakpoint.getProcess() == otherEventBreakpoint.getProcess() )
        {
          return true;
        }
      }
    }
    return false;
  }

  private void enableDebugProcess( final EventBreakpoint eventBreakpoint )
    throws CoreException
  {
    // add to launchconfiguration
    for( ILaunchConfiguration launchConfiguration : DebugPlugin.getDefault()
      .getLaunchManager()
      .getLaunchConfigurations() )
    {
      int process = eventBreakpoint.getProcess();
      int launchNumberOfProcesses = launchConfiguration.getAttribute( "numberOfProcesses",
                                                                      -1 );
      String launchProjectname = launchConfiguration.getAttribute( ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME,
                                                                   ( String )null );
      boolean traceViewer = launchConfiguration.getAttribute( "traceViewer",
                                                              false );
      if( launchProjectname != null
          && launchProjectname.equals( eventBreakpoint.getProjectName() )
          && launchNumberOfProcesses == eventBreakpoint.getNumberOfProcesses()
          && traceViewer )
      {
        String debugprocs = launchConfiguration.getAttribute( "processes", "" );
        if( "all".equals( debugprocs ) ) {
          debugprocs = Integer.toString( process );
        } else {
          StringTokenizer st = new StringTokenizer( debugprocs, ":" );
          StringBuffer bf = new StringBuffer();
          boolean a = true;
          while( st.hasMoreElements() ) {
            int proc = Integer.parseInt( st.nextToken() );
            if( proc > process && a ) {
              bf.append( process );
              bf.append( ':' );
              a = false;
            }
            if( proc == process ) {
              a = false;
            }
            bf.append( proc );
            bf.append( ':' );
          }
          if( a ) {
            bf.append( process );
          } else {
            bf.deleteCharAt( bf.length() - 1 );
          }
          debugprocs = bf.toString();
        }
        ILaunchConfigurationWorkingCopy launchConfigurationWorkingCopy = launchConfiguration.getWorkingCopy();
        launchConfigurationWorkingCopy.setAttribute( "processes", debugprocs );
        launchConfigurationWorkingCopy.doSave();
      }
    }
  }

  private void disableDebugProcess( final EventBreakpoint eventBreakpoint )
    throws CoreException
  {
    // remove launchconfiguration
    for( ILaunchConfiguration launchConfiguration : DebugPlugin.getDefault()
      .getLaunchManager()
      .getLaunchConfigurations() )
    {
      int process = eventBreakpoint.getProcess();
      int launchNumberOfProcesses = launchConfiguration.getAttribute( "numberOfProcesses",
                                                                -1 );
      String launchProjectname = launchConfiguration.getAttribute( ICDTLaunchConfigurationConstants.ATTR_PROJECT_NAME,
                                                                   ( String )null );
      boolean traceViewer = launchConfiguration.getAttribute( "traceViewer",
                                                              false );
      if( launchProjectname != null
          && launchProjectname.equals( eventBreakpoint.getProjectName() )
          && launchNumberOfProcesses == eventBreakpoint.getNumberOfProcesses()
          && traceViewer )
      {
        String debugprocs = launchConfiguration.getAttribute( "processes", "" );
        if( "all".equals( debugprocs ) ) {
          // TODO
        } else {
          StringTokenizer st = new StringTokenizer( debugprocs, ":" ); //$NON-NLS-1$
          StringBuffer bf = new StringBuffer();
          while( st.hasMoreElements() ) {
            int proc = Integer.parseInt( st.nextToken() );
            if( proc != process ) {
              bf.append( proc );
              bf.append( ':' );
            }
          }
          if( bf.length() > 0 ) {
            bf.deleteCharAt( bf.length() - 1 );
          } else {
            bf.append( "all" );
          }
          debugprocs = bf.toString();
        }
        ILaunchConfigurationWorkingCopy launchConfigurationWorkingCopy = launchConfiguration.getWorkingCopy();
        launchConfigurationWorkingCopy.setAttribute( "processes", debugprocs );
        launchConfigurationWorkingCopy.doSave();
      }
    }
  }

  public void breakpointAdded( final IBreakpoint breakpoint ) {
    if( breakpoint instanceof EventBreakpoint ) {
      EventBreakpoint eventBreakpoint = ( EventBreakpoint )breakpoint;
      try {
        enableDebugProcess( eventBreakpoint );
      } catch( CoreException coreException ) {
        // TODO
      }
    }
    redraw( breakpoint );
  }

  public void breakpointChanged( final IBreakpoint breakpoint,
                                 final IMarkerDelta delta )
  {
    // Filter DebugTargets in case of an EventBreakpoint
    if( breakpoint instanceof EventBreakpoint ) {
      EventBreakpoint eventBreakPoint = ( EventBreakpoint )breakpoint;
      try {
        ICBreakpointFilterExtension extension = ( ICBreakpointFilterExtension )eventBreakPoint.getExtension( CDIDebugModel.getPluginIdentifier(),
                                                                                                             ICBreakpointFilterExtension.class );
        // TODO only filter if all targets are available
        for( ICDebugTarget target : extension.getTargetFilters() ) {
          // Wait until the target is suspended
          try {
            while( !target.isSuspended() ) {
              wait( 100 );
            }
          } catch( InterruptedException interruptedException ) {
            Activator.logException( interruptedException );
          }
          String processIdString = target.getProcess().getAttribute( "mpi_id" );
          if( processIdString != null ) {
            int processID = Integer.parseInt( processIdString );
            if( eventBreakPoint.getProcess() != processID ) {
              extension.removeTargetFilter( target );
            }
          }
        }
      } catch( CoreException exception ) {
        Activator.logException( exception );
      }
    }
    redraw( breakpoint );
  }

  public void breakpointRemoved( final IBreakpoint breakpoint,
                                 final IMarkerDelta delta )
  {
    if( breakpoint instanceof EventBreakpoint ) {
      EventBreakpoint eventBreakpoint = ( EventBreakpoint )breakpoint;
      try {
        if( !otherBreakpoints( eventBreakpoint ) ) {
          disableDebugProcess( eventBreakpoint );
        }
      } catch( CoreException coreException ) {
        Activator.logException( coreException );
      }
    }
    redraw( breakpoint );
  }
}
