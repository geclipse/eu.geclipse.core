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

package eu.geclipse.traceview.debug;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.cdt.debug.internal.core.breakpoints.AbstractLineBreakpoint;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;

import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.ISourceLocation;
import eu.geclipse.traceview.ITrace;

/**
 * A Breakpoint the suspends when an Event is reached.
 */
@SuppressWarnings("restriction")
public class EventBreakpoint extends AbstractLineBreakpoint {

  /** Process */
  public final static String PROCESS = "Process"; //$NON-NLS-1$
  final static String IGNORE_COUNTS = "IgnoreCounts"; //$NON-NLS-1$
  final static String TRACE_PATH = "TracePath"; //$NON-NLS-1$
  final static String NUM_PROCS = "NumProcs"; //$NON-NLS-1$
  final static String PROJ_NAME = "ProjName"; //$NON-NLS-1$
  private boolean alive;
  private int position;

  /**
   * Constructor for EventBreakpoint.
   */
  public EventBreakpoint() {
    this.position = 0;
    this.alive = true;
  }

  /**
   * Constructor for EventBreakpoint.
   * 
   * @param resource
   * @param attributes
   * @param register
   * @param event 
   * @throws CoreException
   */
  @SuppressWarnings({
    "unchecked"
  })
  public EventBreakpoint( final IResource resource, final Map attributes, final boolean register, final IEvent event ) throws CoreException {
    super( resource, getMarkerType(), attributes, register );
    this.position = 0;
    this.alive = true;
    String tracePath = event.getProcess().getTrace().getPath().toString();
    String projectName = getProject( event.getProcess().getTrace() ).getProject().getName();
    int numberOfProcesses = event.getProcess().getTrace().getNumberOfProcesses();
    int process = event.getProcessId();
    ensureMarker().setAttribute( TRACE_PATH, tracePath );
    ensureMarker().setAttribute( PROJ_NAME, projectName );
    ensureMarker().setAttribute( NUM_PROCS, numberOfProcesses );
    ensureMarker().setAttribute( PROCESS, process );
  }

  /**
   * Returns the Marker for this Breakpoint
   * 
   * @return the Marker for this Breakpoint
   */
  public static String getMarkerType() {
    return "eu.geclipse.traceview.debug.BreakpointMarker"; //$NON-NLS-1$
  }

  @Override
  public int getIgnoreCount() throws CoreException {
    if( this.alive == true ) {
      setIgnoreCount( getIgnoreCounts().first().intValue() );
      this.alive = false;
    }
    return super.getIgnoreCount();
  }

  /**
   * Returns the Project which contains the Trace
   * 
   * @param trace
   * @return project
   */
  protected ICProject getProject( final ITrace trace ) {
    ICProject project = null;
    IPath tracePath = trace.getPath();
    // check if the trace is located in the workspace
    boolean workspace = ResourcesPlugin.getWorkspace().getRoot().getLocation().isPrefixOf( tracePath );
    if( workspace ) {
      IPath path = tracePath.removeFirstSegments( ResourcesPlugin.getWorkspace().getRoot().getLocation().segmentCount() );
      // get the name of the project the trace is located in
      String projectName = path.uptoSegment( 1 ).toPortableString();
      String device = path.getDevice();
      if( device != null ) {
        projectName = projectName.substring( device.length() );
      }
      // get the project
      if( projectName != null && projectName.length() > 0 ) {
        project = CoreModel.getDefault().getCModel().getCProject( projectName );
      }
    }
    return project;
  }

  /**
   * Returns the number of processes
   * 
   * @return number of processes
   * @throws CoreException
   */
  public int getNumberOfProcesses() throws CoreException {
    return this.ensureMarker().getAttribute( NUM_PROCS, -1 );
  }

  /**
   * Returns the process this breakpoint is assigned to.
   * 
   * @return assigned process
   * @throws CoreException
   */
  public int getProcess() throws CoreException {
    return this.ensureMarker().getAttribute( PROCESS, -1 );
  }

  /**
   * Returns the Path of the Trace
   * 
   * @return trace path
   * @throws CoreException
   */
  public String getTracePath() throws CoreException {
    return this.ensureMarker().getAttribute( TRACE_PATH, "" ); //$NON-NLS-1$
  }

  /**
   * Returns the name of the project
   * 
   * @return project name
   * @throws CoreException
   */
  public String getProjectName() throws CoreException {
    return this.ensureMarker().getAttribute( PROJ_NAME, "" ); //$NON-NLS-1$
  }

  /**
   * Revives a EventBreakpoint at the end
   */
  public void revive() {
    if( !this.alive ) {
      this.alive = true;
      try {
        this.setEnabled( true );
      } catch( CoreException coreException ) {
        Activator.logException( coreException );
      }
    }
  }

  private SortedSet<Integer> getIgnoreCounts() throws CoreException {
    String countString = this.ensureMarker().getAttribute( IGNORE_COUNTS, "" ); //$NON-NLS-1$
    StringTokenizer tokenizer = new StringTokenizer( countString );
    SortedSet<Integer> counts = new TreeSet<Integer>();
    while( tokenizer.hasMoreTokens() ) {
      counts.add( Integer.valueOf( Integer.parseInt( tokenizer.nextToken() ) ) );
    }
    return counts;
  }

  private void setIgnoreCounts( final SortedSet<Integer> ignoreCounts ) throws DebugException, CoreException {
    if( !ignoreCounts.isEmpty() ) {
      setIgnoreCount( ignoreCounts.first().intValue() );
    }
    Iterator<Integer> it = ignoreCounts.iterator();
    StringBuffer buffer = new StringBuffer();
    while( it.hasNext() ) {
      buffer.append( it.next().toString() + " " ); //$NON-NLS-1$
      // System.out.println( buffer.toString() );
    }
    ensureMarker().setAttribute( IGNORE_COUNTS, buffer.toString() );
  }

  /**
   * Checks if the EventBreakpoint is active for the IEvent
   * 
   * @param event
   * @return true if Breakpoint contains Event
   * @throws CoreException
   */
  public boolean containtsEvent( final IEvent event ) throws CoreException {
    SortedSet<Integer> ignoreCounts = this.getIgnoreCounts();
    boolean result = false;
    Iterator<Integer> it = ignoreCounts.iterator();
    if( event.getProcessId() == this.getProcess() ) {
      while( !result && it.hasNext() ) {
        ISourceLocation sourceLocation = ( ISourceLocation )event;
        if( it.next().intValue() == sourceLocation.getOccurrenceCount() && this.getLineNumber() == sourceLocation.getSourceLineNumber() ) {
          result = true;
        }
      }
    }
    return result;
  }

  /**
   * Adds an Event to the Breakpoint
   * 
   * @param event
   * @throws CoreException
   */
  public void addEvent( final IEvent event ) throws CoreException {
    if( event instanceof ISourceLocation ) {
      ISourceLocation sourceLocation = ( ISourceLocation )event;
      SortedSet<Integer> ignoreCounts = this.getIgnoreCounts();
      if( ignoreCounts.add( Integer.valueOf( sourceLocation.getOccurrenceCount() ) ) ) {
        setIgnoreCounts( ignoreCounts );
      }
    }
  }

  /**
   * Removes an Event from the Breakpoint
   * 
   * @param event
   * @throws CoreException
   */
  public void removeEvent( final IEvent event ) throws CoreException {
    if( event instanceof ISourceLocation ) {
      ISourceLocation sourceLocation = ( ISourceLocation )event;
      SortedSet<Integer> ignoreCounts = getIgnoreCounts();
      if( ignoreCounts.remove( Integer.valueOf( sourceLocation.getOccurrenceCount() ) ) ) {
        setIgnoreCounts( ignoreCounts );
        if( ignoreCounts.isEmpty() ) {
          DebugPlugin.getDefault().getBreakpointManager().removeBreakpoint( this, true );
        }
      }
    }
  }

  /**
   * Switches to the next Event
   * 
   * @throws CoreException
   */
  public void next() throws CoreException {
    Integer[] ignoreCounts = getIgnoreCounts().toArray( new Integer[ 0 ] );
    if( ignoreCounts.length > this.position + 1 ) {
      setIgnoreCount( ignoreCounts[ this.position + 1 ].intValue() - ignoreCounts[ this.position++ ].intValue() - 1 );
    } else {
      setEnabled( false );
    }
  }

  @Override
  protected String getMarkerMessage() throws CoreException {
    String result = "breakpoint: process " + getProcess();
    return result;
  }
}
