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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.StringTokenizer;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;

import eu.geclipse.core.GridException;
import eu.geclipse.core.Messages;


/**
 * Contains the /proc/stat values of a process identified via a EFS
 * IFilesore and serves its data as a localized java.util.Hashtable
 * @author mpolak
 *
 */
public class GridProcess {
  protected int pid = 0;
  protected IFileStore procinfo = null;
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
  
  /**
   * Constructs a GridProcess object and associates a /proc/stat IFilesStore resource with it
   * @param proc a valid org.eclipse.core.filesystem.IFileStore representing a processes /proc/stat file
   */
  public GridProcess(final IFileStore proc){
      
    if (proc != null && proc.fetchInfo().isDirectory() ){       // roughly check the validity of the FileStore 
        String dirname = proc.getName();
        this.pid = Integer.parseInt( dirname );
        if (this.pid > 0){
          this.procinfo = proc;
        }
        
    }
    this.values = new Hashtable<String, Integer>();
  }
  
  /**
   * calling this method causes the process data associated with this object to be re-read
   * @throws GridException
   */
  @SuppressWarnings("null")
  public void update() throws GridException{
   
    if (this.procinfo != null && this.values != null){
      this.values.clear();
      IFileStore stat = this.procinfo.getChild( "stat"); //$NON-NLS-1$
      
      if (stat != null ){
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
          throw new GridException(eu.geclipse.core.CoreProblems.CONNECTION_FAILED,cause, "Error reading/parsing stat of remote process "+this.pid); //$NON-NLS-1$
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
  
  /**
   * return the processes /proc/stat values as a localized Hashtable
   * @return a localized Hashtable containing /proc/<pid>/stat
   */
  public Hashtable<String, Integer> getStat(){
    
    return this.values;
  }
}

