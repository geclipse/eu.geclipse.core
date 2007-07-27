package eu.geclipse.core.monitoring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.StringTokenizer;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import eu.geclipse.core.GridException;
import eu.geclipse.core.Messages;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridElement;


public class GridProcess {
  protected int pid = 0;
  protected IGridConnectionElement procinfo = null;
  protected Hashtable<String,Integer> values = null;
  
  private final String[] captions={
    Messages.getString("ProcessStatView.pid"), //$NON-NLS-1$
    Messages.getString("ProcessStatView.comm"), //$NON-NLS-1$
    Messages.getString("ProcessStatView.state"),  //$NON-NLS-1$ 
    Messages.getString("ProcessStatView.ppid"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.pgrp"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.session"), //$NON-NLS-1$
    Messages.getString("ProcessStatView.tty_nr"), //$NON-NLS-1$
    Messages.getString("ProcessStatView.tpgid"), //$NON-NLS-1$
    Messages.getString("ProcessStatView.flags"), //$NON-NLS-1$
    Messages.getString("ProcessStatView.minflt"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.cminflt"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.majflt"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.cmajflt"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.utime"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.stime"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.cutime"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.cstime"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.priority"), //$NON-NLS-1$
    Messages.getString("ProcessStatView.nice"),  //$NON-NLS-1$
    "",   //$NON-NLS-1$
    Messages.getString("ProcessStatView.itrealvalue"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.starttime"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.vsize"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.rss"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.rlim"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.startcode"), //$NON-NLS-1$
    Messages.getString("ProcessStatView.endcode"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.startstack"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.kstkesp"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.ksteip"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.signal"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.blocked"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.sigignore"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.sigcatch"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.wchan"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.nswap"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.cnswap"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.exit_signal"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.processor"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.rt_priority"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.policy"),  //$NON-NLS-1$
    Messages.getString("ProcessStatView.delayacct_blkio_ticks")}; //$NON-NLS-1$
  
  public GridProcess(final IGridConnectionElement proc){
      
    if (proc != null && proc.isFolder() ){
        String dirname = proc.getName();
        this.pid = Integer.parseInt( dirname );
        if (this.pid > 0){
          this.procinfo = proc;
        }
        
    }
    this.values = new Hashtable<String, Integer>();
  }
  
  @SuppressWarnings("null")
  public void update() throws GridException{
   
    if (this.procinfo != null && this.values != null){
      this.values.clear();
      IGridElement statchild = this.procinfo.findChild("stat"); //$NON-NLS-1$
      
      if (statchild != null && statchild instanceof IGridConnectionElement ){
        IGridConnectionElement statelem = (IGridConnectionElement)statchild;
        IFileStore stat = statelem.getFileStore();
        BufferedReader inr = null;
        StringTokenizer filecontents = null;
        try {
          inr = new BufferedReader (new InputStreamReader(stat.openInputStream( EFS.NONE, null )));
          filecontents = new StringTokenizer(inr.readLine());
          int elemcount = 0;
          while (filecontents.hasMoreTokens()){
            this.values.put( this.captions[elemcount++],Integer.valueOf(filecontents.nextToken()));
          }
        } catch( Exception e1 ) {
          Throwable cause = e1.getCause();
          throw new GridException(eu.geclipse.core.CoreProblems.CONNECTION_FAILED,cause, "Error reading/parsing stat of remote process"+this.pid); //$NON-NLS-1$
        } 
        if (inr != null){
          try {
            inr.close();
          } catch( IOException e ) {
            // do nothing here
          }
        }
      }
    }        
  }
  
  public Hashtable<String, Integer> getStat(){
    
    return this.values;
  }
}

