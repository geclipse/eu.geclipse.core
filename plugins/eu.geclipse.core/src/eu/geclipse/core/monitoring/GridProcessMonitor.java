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
 *    Martin Polak GUP, JKU - initial implementation
 *****************************************************************************/

package eu.geclipse.core.monitoring;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.geclipse.core.GridException;
import eu.geclipse.core.internal.Activator;


/**
 * @author Martin Polak
 *
 */
public class GridProcessMonitor {
  protected URI targeturi = null;
  protected IFileStore procfs = null;
  protected IFileSystem currentfs = null;
  protected int updateinterval = 5;
  protected HashSet<Integer> proclist = null;
  private Job updateJob = null;
  
  /**
   * This class implements functionality to monitor a resource specified by the target URI.
   * It creates a Job for automatically updating its contents in the background.
   * @param target URI of the (remote) host of which the /proc file system will be parsed.
   */
  public GridProcessMonitor( final URI target ) {
     this.targeturi = target;
     this.proclist = new HashSet<Integer>();
     
     this.updateJob = new Job( this.targeturi.getHost() + " updater" ) { //$NON-NLS-1$
       @Override
       protected IStatus run( final IProgressMonitor monitor )
       {
         Status status = new Status( IStatus.ERROR,
                                     "eu.geclipse.core.monitoring", //$NON-NLS-1$
                                     "Fetching running process data from " //$NON-NLS-1$
                                       + GridProcessMonitor.this.targeturi.getHost()
                                       + " failed" ); //$NON-NLS-1$
         try {
           if ( update( monitor ) ) {
             status = new Status( IStatus.OK,
                                  "eu.geclipse.core.monitoring", //$NON-NLS-1$
                                  "Process data fetched successfully." ); //$NON-NLS-1$
           }
         } catch( GridException e ) {
           Activator.logException( e ); 
         }
         return status;
       }
     };
  } 
  
  protected void scheduleUpdate() {
    if ( this.updateJob.getState() == Job.NONE ) {
      this.updateJob.schedule();
    }
  }
  
  protected void stopUpdate() {
    this.updateJob.cancel();
  }
  
  synchronized boolean update( final IProgressMonitor monitor ) throws GridException {
    
    this.procfs = null;
    String[] procfslist = null;
    
    boolean success = true;
    monitor.beginTask( "Fetching data from " + this.targeturi.getHost(), 3 ); //$NON-NLS-1$
    
    if ( this.currentfs == null ) {
      success = this.initialize();
    }
    monitor.worked( 1 );
    
    if ( this.currentfs != null ) {
      
      try {
        this.procfs = this.currentfs.getStore( this.targeturi );
        procfslist = this.procfs.childNames( EFS.NONE, monitor );
      } catch ( CoreException e ) {
        Activator.logException( e );
        //Throwable cause = e.getCause();
        //throw new GridException(eu.geclipse.core.CoreProblems.CONNECTION_FAILED,
        //                        cause, "updating a temporary Connection failed"); //$NON-NLS-1$
        //perhaps better to throw nothing and just return status
        success = false;
      }
      monitor.worked( 1 );
    }

    if ( procfslist != null ) {
      this.proclist.clear();
      for ( String gproc : procfslist ) {
          try {
            this.proclist.add( Integer.valueOf( gproc ) );
          } catch ( NumberFormatException e ) {
            // ignore
          }
      }
      monitor.worked( 1 );
    }
    monitor.done();
    return success;
  }
  
  
  boolean initialize() throws GridException {
    boolean success = false;
    
    if ( this.targeturi != null ) {
      String path = this.targeturi.getPath();
      if ( (path == null) || (path.length() == 0) ) {
        try {
          this.targeturi = new URI( this.targeturi.getScheme(),
                                    this.targeturi.getUserInfo(),
                                    this.targeturi.getHost(),
                                    this.targeturi.getPort(),
                                    "/proc/", "", "" ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        } catch ( URISyntaxException e ) {
          // Don't do anything, will be handled later when the connection fails
        }
      }
      try {
        this.currentfs = EFS.getFileSystem( this.targeturi.getScheme() );
        success = true;
      } catch ( CoreException e ) {
        Activator.logException( e );
        Throwable cause = e.getCause();
        throw new GridException( eu.geclipse.core.CoreProblems.CONNECTION_FAILED,
                                 cause, "updating a temporary Connection failed" ); //$NON-NLS-1$
      }
    }
    return success;
  }
  
  /**
   * Sets the interval of seconds between two updates of the remote list of
   * processes
   * 
   * @param seconds between two updates
   */
  public void setUpdateInterval( final int seconds ) {
    if ( seconds >= 0 ) {
      this.updateinterval = seconds;
      this.updateJob.schedule( this.updateinterval );
    } else {
      this.updateinterval = 0;
    }
  }
  
  /**
   * Fetches the status of one process identified via its pid represented by a
   * GridProcess object
   * 
   * @param pid the remote machines process-id to fetch information about
   * @return an Object of type GridProcess containing information about the
   *         remote process with PID pid
   */
  public GridProcess getProcessInfo( final int pid ) {
    GridProcess newproc = null;
    if ( pid > 0
         && this.proclist != null
         && this.proclist.contains( Integer.valueOf( pid ) ) )
    {
      if ( this.currentfs != null && this.procfs != null ) {
        IFileStore procdirelem = this.procfs.getChild( new Integer( pid ).toString() );
    
        if ( procdirelem != null ) {
          newproc = new GridProcess( procdirelem );     // perhaps this should be synchronized
          // this.proclist.remove( new Integer(pid) );
          // this.proclist.add( newproc );               // reserved for future use
        }
      }
    }
    return newproc;
  }
  
  /**
   * Get a list of remotely running processes.
   * 
   * @return a HashSet with PIDs of the remotely running processes
   */
  public HashSet<Integer> getProcessList() {
    if ( this.proclist.isEmpty() ) {
        this.updateJob.schedule();
        try {
          this.updateJob.join();
        } catch( InterruptedException e ) {
          // if we are interrupted by something perhaps the joblist is unfetchable
        }  
    }
    return this.proclist;
  }
  
  /**
   * @return the currently run time between two updates. A zero or negative value means that automatic updates
   * are disabled.
   */
  public int getUpdateInterval() {
    return this.updateinterval;
  }
  
  /**
   * @return the URI of the currently monitored machine
   */
  public URI getTarget() {
    return this.targeturi;
  }
  
  @Override
  public boolean equals( final Object o ) {
    
    boolean isequal = false;
    if ( o instanceof GridProcessMonitor ) {
      GridProcessMonitor mon = (GridProcessMonitor)o;
      isequal = this.targeturi.equals( mon.getTarget() );
    }
    return isequal;
  }
}
