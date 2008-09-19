/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.internal.Activator;


public class TransformAction extends Action {
  
  protected IGridElementCreator creator;
  
  protected IGridJobDescription[] input;
  
  public TransformAction( final String title,
                          final IGridElementCreator creator,
                          final IGridJobDescription[] input ) {
    super( title );
    this.creator = creator;
    this.input = input;
  }
  
  @Override
  public void run() {
    WorkspaceJob job = new WorkspaceJob( "transformer" ) {
      @Override
      public IStatus runInWorkspace( final IProgressMonitor monitor )
          throws CoreException {
        return transform( TransformAction.this.creator, TransformAction.this.input, monitor );
      }
    };
    job.setUser( true );
    job.schedule();
  }
  
  protected IStatus transform( final IGridElementCreator creator,
                               final IGridJobDescription[] input,
                               final IProgressMonitor monitor ) {

    IStatus result = Status.OK_STATUS;
    List< IStatus > errors = new ArrayList< IStatus >();
    
    IProgressMonitor lMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
      
    lMonitor.beginTask( "Transforming job descriptions", input.length );
    
    try {
      for ( IGridJobDescription description : input ) {
        lMonitor.subTask( description.getName() );
        IStatus status = transform( creator, description );
        if ( ! status.isOK() ) {
          errors.add( status );
        }
        lMonitor.worked( 1 );
      }
    } finally {
      lMonitor.done();
    }
    
    if ( ( errors != null ) && ( errors.size() > 0 ) ) {
      if ( errors.size() == 1 ) {
        result = errors.get( 0 );
      } else {
        result = new MultiStatus(
            Activator.PLUGIN_ID,
            0,
            errors.toArray( new IStatus[ errors.size() ] ),
            "Transformation problems",
            null
        );
      }
    }
    
    return result;
    
  }
  
  private IStatus transform( final IGridElementCreator creator,
                             final IGridJobDescription input ) {
    
    IStatus result = Status.OK_STATUS;
    
    creator.setSource( input );
    IGridContainer parent = input.getParent();

    try {
      creator.create( parent );
    } catch ( ProblemException pExc ) {
      result = new Status(
                          IStatus.ERROR,
                          Activator.PLUGIN_ID,
                          String.format( "Transformation failed for %s", input.getName() ),
                          pExc
      );
    }
    
    return result;
    
  }

}
