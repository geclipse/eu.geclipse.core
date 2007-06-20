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
 *    Mathias Stuempert - initial API and implementation
 *    Pawel Wolniewicz 
 *****************************************************************************/
package eu.geclipse.core.internal.model;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;
import eu.geclipse.core.JobStatusUpdater;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobID;
import eu.geclipse.core.model.IGridJobManager;
import eu.geclipse.core.model.IGridJobStatusListener;

/**
 * Core implementation of an {@link IGridJobManager}.
 */
public class JobManager extends AbstractGridElementManager
  implements IGridJobManager
{

  /**
   * The name of this manager.
   */
  private static final String NAME = ".jobs"; //$NON-NLS-1$
  /**
   * The singleton.
   */
  private static JobManager singleton;
  /**
   * Hashtable for holding information about updaters assigned to jobs.
   */
  private Hashtable<IGridJobID, JobStatusUpdater> updaters = new Hashtable<IGridJobID, JobStatusUpdater>();

  /**
   * Private constructor to ensure to have only one instance of this class. This
   * can be obtained by {@link #getManager()}.
   */
  private JobManager() {
    // empty imlementation
  }

  @Override
  public boolean addElement( final IGridElement element )
    throws GridModelException
  {
    boolean flag;
    flag = super.addElement( element );
    if( element instanceof IGridJob ) {
      JobStatusUpdater updater = new JobStatusUpdater( (( IGridJob )element).getID() );
      updaters.put( (( IGridJob )element).getID(), updater );
      updater.setSystem( true );
      updater.schedule( 120000 );
    }
    return flag;
  }

  /**
   * Get the singleton instance of the <code>JobManager</code>.
   * 
   * @return The singleton.
   */
  public static JobManager getManager() {
    if( singleton == null ) {
      singleton = new JobManager();
    }
    return singleton;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.internal.model.AbstractGridElementManager#getName()
   */
  public String getName() {
    return NAME;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridElementManager#canManage(eu.geclipse.core.model.IGridElement)
   */
  public boolean canManage( final IGridElement element ) {
    return element instanceof IGridJob;
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridJobManager#addJobStatusListener(java.util.List,
   *      int, eu.geclipse.core.model.IGridJobStatusListener)
   */
  public void addJobStatusListener( final IGridJob[] jobs,
                                    final int status,
                                    final IGridJobStatusListener listener )
  {
    JobStatusUpdater updater;
    for( IGridJob job : jobs ) {
      updater = ( JobStatusUpdater )updaters.get( job.getID() );
      updater.addJobStatusListener( status, listener );
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridJobManager#addJobStatusListener(java.util.List,
   *      int, eu.geclipse.core.model.IGridJobStatusListener)
   */
  public void addJobStatusListener( final IGridJobID[] ids,
                                    final int status,
                                    final IGridJobStatusListener listener )
  {
    JobStatusUpdater updater;
    for( IGridJobID id : ids ) {
      updater = ( JobStatusUpdater )updaters.get( id );
      updater.addJobStatusListener( status, listener );
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see eu.geclipse.core.model.IGridJobManager#removeJobStatusListener(eu.geclipse.core.model.IGridJobStatusListener)
   */
  public void removeJobStatusListener( final IGridJobStatusListener listener ) {
    JobStatusUpdater updater;
    for( Enumeration<JobStatusUpdater> e = updaters.elements(); e.hasMoreElements(); )
    {
      updater = e.nextElement();
      updater.removeJobStatusListener( listener );
    }
  }

  /**
   * Removes updater from JobManager. It is called by updater, when it finished
   * it works (job status cannot change any more)
   * 
   * @param updater
   */
  public void removeUpdater( final JobStatusUpdater updater ) {
    Collection<JobStatusUpdater> values = updaters.values();
    while( values.remove( updater ) ) {
      // empty block};
    }
  }

  // void waitForJob(final String id) throws InterruptedException,
  // IllegalAccessException{
  // JobStatusUpdater jsu = updaters.get( id );
  //
  // if(jsu==null){
  // throw new IllegalAccessException("No updater for job id: "+id);
  // }
  // jsu.join();
  // }
  //
  // void waitForJob(final IGridJobID id) throws InterruptedException,
  // IllegalAccessException{
  // waitForJob(id.getJobID());
  // }

  
  /**
   * Wait until updater for the given job finishes, which means that the job status 
   * is final and cannot be changed any more.
   * @param job
   * @throws InterruptedException
   * @throws NoSuchElementException
   */
  void waitForJob( final IGridJob job )
    throws InterruptedException, NoSuchElementException
  {
    JobStatusUpdater updater = updaters.get( job );
    if( updater == null ) {
      throw new NoSuchElementException();
    }
    updater.join();
  }
}
