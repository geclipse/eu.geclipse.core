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

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridElementCreator;


/**
 * @author Martin Polak
 *
 */
public class GridProcessMonitor {
  protected URI targeturi = null;
  protected IGridConnection currentcon = null;
  protected int updateinterval = 5;
  protected HashSet<Integer> proclist = null;
  private Job updateJob = null;
  
  /**
   * This class implements functionality to monitor a resource specified by the target URI.
   * The connection is created by g-Eclipse via the URI specified for the GridProcessMonitor.
   * It creates a Job for automatically updating its contents in the background.
   * @param target URI of the (remote) host of which the /proc file system will be parsed.
   */
  public GridProcessMonitor(final URI target){
     this.targeturi = target;
     this.proclist = new HashSet<Integer>();
     
     this.updateJob=new Job(this.targeturi.getHost()+" updater"){ //$NON-NLS-1$
       @Override
       protected IStatus run( final IProgressMonitor monitor )
       {
         Status status=new Status(Status.ERROR,"eu.geclipse.core.monitoring","Fetching running process data from "+GridProcessMonitor.this.targeturi.getHost()+" failed"); //$NON-NLS-1$ //$NON-NLS-2$
         if(update(monitor)){
           status=new Status(Status.OK,"eu.geclipse.core.monitoring","Process data fetched successfully."); //$NON-NLS-1$ //$NON-NLS-2$
         }
         return status;
       }
       
     };
  } 
  
  protected void scheduleUpdate(){
    if(this.updateJob.getState()==Job.NONE){
      this.updateJob.schedule();
    }
  }
  
  protected void stopUpdate(){
    this.updateJob.cancel();
  }
  
  synchronized boolean update(final IProgressMonitor monitor) {
     
    IGridElement[] procs = null;
    boolean success = true;
    monitor.beginTask( "Fetching data from "+this.targeturi.getHost(), 3 ); //$NON-NLS-1$
    
    if (this.currentcon == null){
      success = this.initialize();
    }
    monitor.worked( 1 );
    
    if (this.currentcon != null){
      this.currentcon.setDirty();
      try {
        procs = this.currentcon.getChildren( monitor );
      } catch( GridModelException e ) {
        Activator.logException( e );
       // Throwable cause = e.getCause();
       // throw new GridException(eu.geclipse.core.CoreProblems.CONNECTION_FAILED,cause, "updating a temporary Connection failed"); //$NON-NLS-1$
        //perhaps better to throw nothing and just return status
        success = false;
      }  
      monitor.worked( 1 );
    }

    if (procs != null){
      for (IGridElement gproc : procs ){
        if (gproc instanceof IGridConnectionElement && ((IGridConnectionElement)gproc).isFolder() ){
          try {
            this.proclist.add(Integer.valueOf( ((IGridConnectionElement)gproc).getName()));
          }catch (NumberFormatException e){
            // ignore
          }
        }
      }
      monitor.worked( 1 );
    }
    monitor.done();
    return success;
  }
  
  
  boolean initialize() {
    boolean success = true;
    
    if ( this.targeturi != null ) {
      String path = this.targeturi.getPath();
      if ((path == null) || (path.length() == 0)) {
        try {
          this.targeturi = new URI( this.targeturi.getScheme(),this.targeturi.getUserInfo(),this.targeturi.getHost(), this.targeturi.getPort(),"/proc/", "", ""); //$NON-NLS-1$
        } catch( URISyntaxException e ) {
          // Dont do anything, will barf lateron
        }
      }
      List< IGridElementCreator > standardCreators = GridModel.getStandardCreators();

      for ( final IGridElementCreator creator : standardCreators ) {
        if ( creator.canCreate( this.targeturi ) ) {
          try {
            try {
              this.currentcon = ( IGridConnection ) creator.create( null );
            } catch( Exception gmExc ) {
              throw new InvocationTargetException( gmExc );
            }
          } catch( InvocationTargetException itExc ) {
              Activator.logException( itExc );
              itExc.printStackTrace();
            //Throwable cause = itExc.getCause();
            //throw new GridException(eu.geclipse.core.CoreProblems.CONNECTION_FAILED,cause, "Temporary Connection failed"); //$NON-NLS-1$
            success = false;
          }
          break;
        }
      }
    }
    return success;
  }
  
 
  
  
  /**
   * Sets the interval of seconds between two updates of the remote list of processes
   * @param seconds between two updates
   */
  public void setUpdateInterval(final int seconds){
    if (seconds >= 0){
      this.updateinterval = seconds;
      this.updateJob.schedule(this.updateinterval);
    } else {
      this.updateinterval = 0;
    }
  }
  
  /**
   * Fetches the status of one process identified via its pid represented by a GridProcess object
   * @param pid the remote machines process-id to fetch information about
   * @return an Object of type GridProcess containing information about the remote process with PID pid
   */
  public GridProcess getProcessInfo(int pid){
    
    GridProcess newproc = null;
    if (pid > 0 && this.proclist != null && this.proclist.contains( new Integer(pid) )){

      if (this.currentcon != null ){
        IGridElement procdirelem = this.currentcon.findChild( new Integer(pid).toString() );
        if (procdirelem != null && procdirelem instanceof IGridConnectionElement){
          IGridConnectionElement procdir = ( ( IGridConnectionElement ) procdirelem );
          newproc = new GridProcess (procdir);
        }
      }
    }
   
    return newproc;
  }
  
  /**
   * Get a list of remotely running processes.
   * @return a HashSet with PIDs of the remotely running processes
   */
  public HashSet<Integer> getProcessList(){
    if (this.proclist.isEmpty()){
        this.update(null );
    }
    return this.proclist;
  }
  
  /**
   * @return the currently run time between two updates. A zero or negative value means that automatic updates
   * are disabled.
   */
  public int getUpdateInterval(){
    return this.updateinterval;
  }
  
  /**
   * @return the URI of the currently monitored machine
   */
  public URI getTarget(){
    return this.targeturi;
  }
  
  @Override
  public boolean equals( final Object o){
    
    boolean isequal = false;
    if (o instanceof GridProcessMonitor){
      GridProcessMonitor mon = (GridProcessMonitor)o;
      isequal = this.targeturi == mon.getTarget();
    }
    return isequal; 
    
  }
}