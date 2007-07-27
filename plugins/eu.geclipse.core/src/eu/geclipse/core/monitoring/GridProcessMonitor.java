/**
 * 
 */
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
  protected int updateinterval = 0;
  protected HashSet<Integer> proclist = null;
  private Job updateJob = null;
  
  public GridProcessMonitor(URI target){
     this.targeturi = target;
     this.proclist = new HashSet<Integer>();
     
     updateJob=new Job(this.targeturi.getHost()+" updater"){ //$NON-NLS-1$
       @Override
       protected IStatus run( IProgressMonitor monitor )
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
  
  synchronized boolean update(IProgressMonitor monitor) {
     
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
          this.targeturi = new URI( this.targeturi.getScheme(),this.targeturi.getUserInfo(),this.targeturi.getHost(), this.targeturi.getPort(),"/proc/", this.targeturi.getQuery(), this.targeturi.getFragment()); //$NON-NLS-1$
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
  
  //Kinder der Gridconnection sind GridConnectionElements und haben Methode .isFolder()
  
  
  void setUpdateInterval(final int seconds){
    if (seconds >= 0){
      this.updateinterval = seconds;
      this.updateJob.schedule(this.updateinterval);
    }
  }
  
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

/*  public void run( IProgressMonitor monitor ) throws InvocationTargetException   {
 
    try {
      while (this.updateinterval > 0){
        this.update(monitor);

        wait(this.updateinterval*1000);
      }
    } catch( InterruptedException e ) {
      this.updateinterval = 0;
    } catch( GridException e ) {
      throw new InvocationTargetException(e);
    }
  }*/
  
  public HashSet<Integer> getProcessList(){
    if (this.proclist.isEmpty()){
        this.update(null );
    }
    return this.proclist;
  }
  
  public int getUpdateInterval(){
    return this.updateinterval;
  }
  
  public URI getTarget(){
    return this.targeturi;
  }
  
  @Override
  public boolean equals( Object o){
    
    boolean isequal = false;
    if (o instanceof GridProcessMonitor){
      GridProcessMonitor mon = (GridProcessMonitor)o;
      isequal = this.targeturi == mon.getTarget();
    }
    return isequal; 
    
  }
}