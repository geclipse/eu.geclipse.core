/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium
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

package eu.geclipse.traceview.debug.actions;

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
import org.eclipse.core.runtime.Path;
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

import eu.geclipse.traceview.IEvent;
import eu.geclipse.traceview.ISourceLocation;
import eu.geclipse.traceview.ITrace;
import eu.geclipse.traceview.debug.Activator;
import eu.geclipse.traceview.debug.EventBreakpoint;

public abstract class AbstractEventBreakpointAction extends Action
  implements IActionDelegate
{

  protected StructuredSelection selection;

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
    boolean workspace = ResourcesPlugin.getWorkspace()
      .getRoot()
      .getLocation()
      .isPrefixOf( tracePath );
    if( workspace ) {
      IPath path = tracePath.removeFirstSegments( ResourcesPlugin.getWorkspace()
        .getRoot()
        .getLocation()
        .segmentCount() );
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

  protected IResource getResource( final ICProject project,
                                   final String sourceHandle )
    throws CModelException
  {
    String source = new Path( sourceHandle ).lastSegment();
    IResource resource = null;
    ISourceRoot[] roots = project.getSourceRoots();
    for( ISourceRoot root : roots ) {
      ITranslationUnit[] units = root.getTranslationUnits();
      for( ITranslationUnit unit : units ) {
        if( source.equals( unit.getResource().getName() ) ) {
          resource = unit.getResource();
          break;
        }
      }
    }
    return resource;
  }

  public void selectionChanged( final IAction action, final ISelection selection ) {
    if( selection instanceof StructuredSelection ) {
      this.selection = ( StructuredSelection )selection;
    } else {
      this.selection = StructuredSelection.EMPTY;
    }
  }

  protected boolean existing( final IEvent event, final ICProject project ) {
    boolean existing = false;
    if( event instanceof ISourceLocation ) {
      ISourceLocation sourceLocation = ( ISourceLocation )event;
      String sourceHandle = project.getProject()
        .getFile( sourceLocation.getSourceFilename() )
        .getLocation()
        .toOSString();
      IBreakpoint[] breakpoints = DebugPlugin.getDefault()
        .getBreakpointManager()
        .getBreakpoints();
      for( int i = 0; i < breakpoints.length && !existing; i++ ) {
        if( breakpoints[ i ] instanceof EventBreakpoint ) {
          EventBreakpoint eventBreakPoint = ( EventBreakpoint )breakpoints[ i ];
          try {
            if( eventBreakPoint.getLineNumber() == sourceLocation.getSourceLineNumber()
                && eventBreakPoint.getSourceHandle().equals( sourceHandle )
                && eventBreakPoint.getProcess() == event.getProcessId() )
            {
              if( eventBreakPoint.containtsEvent( event ) ) {
                eventBreakPoint.removeEvent( event );
              } else {
                eventBreakPoint.addEvent( event );
              }
              existing = true;
            }
          } catch( CoreException coreException ) {
            Activator.logException( coreException );
          }
        }
      }
    }
    return existing;
  }

  protected EventBreakpoint createEventBreakpoint( final IEvent event )
    throws CoreException
  {
    EventBreakpoint eventBreakpoint = null;
    if( event instanceof ISourceLocation ) {
      boolean enabled = true;
      boolean register = false;
      IResource resource = null;
      ISourceLocation sourceLocation = ( ISourceLocation )event;
      String condition = ""; //$NON-NLS-1$
      String sourceHandle = ""; //$NON-NLS-1$
      int lineNumber = sourceLocation.getSourceLineNumber();
      int ignoreCount = sourceLocation.getOccurrenceCount();
      // get project
      ICProject project = getProject( event.getProcess().getTrace() );
      if( project != null ) {
        sourceHandle = project.getProject()
          .getFile( sourceLocation.getSourceFilename() )
          .getLocation()
          .toOSString();
        resource = getResource( project, sourceLocation.getSourceFilename() );
        if( resource != null ) {
          if( !existing( event, project ) ) {
            HashMap<String, Object> attributes = new HashMap<String, Object>( 10 );
            attributes.put( IBreakpoint.ID,
                            CDebugCorePlugin.getUniqueIdentifier() );
            attributes.put( IMarker.LINE_NUMBER, Integer.valueOf( lineNumber ) );
            attributes.put( IBreakpoint.ENABLED, Boolean.valueOf( enabled ) );
            attributes.put( ICBreakpoint.IGNORE_COUNT,
                            Integer.valueOf( ignoreCount ) );
            attributes.put( ICBreakpoint.CONDITION, condition );
            attributes.put( ICBreakpoint.SOURCE_HANDLE, sourceHandle );
            attributes.put( EventBreakpoint.PROCESS,
                            Integer.valueOf( event.getProcessId() ) );
            eventBreakpoint = new EventBreakpoint( resource,
                                                                   attributes,
                                                                   register );
            eventBreakpoint.addEvent( event );
          }
        } else {
          ErrorDialog.openError( Display.getDefault().getActiveShell(),
                                 "Error",
                                 "Cannot find source file.",
                                 new Status( IStatus.ERROR,
                                             Activator.PLUGIN_ID,
                                             "The project does not contain the source file." ) );
        }
      } else {
        ErrorDialog.openError( Display.getDefault().getActiveShell(),
                               "Error",
                               "Cannot find source file.",
                               new Status( IStatus.ERROR,
                                           Activator.PLUGIN_ID,
                                           "The trace must be located in a project of the workspace." ) );
      }
    }
    return eventBreakpoint;
  }
}
