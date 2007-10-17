/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     PSNC: 
 *      Szymon Mueller
 *****************************************************************************/
package eu.geclipse.core;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElementCreator;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.core.model.IGridJobStatusListener;
import eu.geclipse.core.model.IGridJobStatusService;
import eu.geclipse.core.model.IGridJobSubmissionService;
import eu.geclipse.core.model.IGridTest;

/**
 * Class is responsible for updating statuses of the test jobs. One StructuralTestUpdater is created
 * for each structural test, and it contains all simple tests of this structural tests.
 */
public class StructuralTestUpdater extends Job {
  
  /**
   * Name of the structural test
   */
  private String name;
  
  private IGridTest structuralTest;
  
  private Hashtable< IGridTest, IGridJobID > fragmentTests = new Hashtable< IGridTest, IGridJobID >();
  
  private Hashtable< IGridJobStatusListener, IGridTest > listeners=new Hashtable< IGridJobStatusListener, IGridTest >();

  public StructuralTestUpdater( final String name, final IGridTest structuralTest ) {
    super( name );
    this.structuralTest = structuralTest;
  }
  
  public StructuralTestUpdater( final String name ) {
    super( name );
  }
  
  public boolean addSingleTest( final IGridTest test ) {
    boolean result = false;
    if ( !( this.fragmentTests.contains( test ) ) ) {
      //try to submit job, get jobid
      IGridJobSubmissionService service = test.getSubmissionService();
      IGridJobID jobID = null;
      try {
        jobID = service.submitJob( test.getJSDLDescription(), null );
      } catch( GridException e ) {
        e.printStackTrace();
      }
      if ( jobID != null ) {
        this.fragmentTests.put( test, jobID );
        result = true;
      } else {
        //throw Error
      }
    }
    return result;
  }

  /**
   * Connect with job service and get newest statuses of the jobs
   * If any of the fetched statuses are done/purged
   * notice all registered listeners and remove test from test storage list 
   */
  public void fetchTestsStatuses() {
    Enumeration<IGridTest> enumeration = this.fragmentTests.keys();
    while ( enumeration.hasMoreElements() ) {
      IGridTest test = enumeration.nextElement();
      IGridJobID jobID = this.fragmentTests.get( test );
      List<IGridElementCreator> elementCreators = GridModel.getElementCreators( IGridJobStatusService.class );
      for( IGridElementCreator creator : elementCreators ) {
        if( creator.canCreate( jobID ) ) {
          try {
            IGridJobStatusService service = ( IGridJobStatusService )creator.create( null );
            IGridJobStatus status = service.getJobStatus( jobID );
            if ( status.getType() == IGridJobStatus.DONE ) {
              informListeners( test );
              this.fragmentTests.remove( test );
            }
            //TODO set actions for different statuses of jobs
          } catch( GridModelException e ) {
            // empty implementation
          } catch( GridException e ) {
            // empty implementation
          }
        }
      }
    }
  }
 
  public void registerListener( final IGridJobStatusListener listener, final IGridTest test ) {
    this.listeners.put( listener, test );
  }
  
  @Override
  protected IStatus run( final IProgressMonitor monitor ) {
    //cyclic chekcking of fragment tests for finished test-jobs
    return null;
  }

  
  private void informListeners( final IGridTest test ) {
    Enumeration<IGridJobStatusListener> enumeration = this.listeners.keys();
    while ( enumeration.hasMoreElements() ) {
      IGridJobStatusListener listener = enumeration.nextElement();
      IGridTest tempTest = this.listeners.get( listener );
      if ( tempTest == test ) {
        //TODO listener.statusChanged - need IGridJob to execute this method
      }
    }
  }
  
}