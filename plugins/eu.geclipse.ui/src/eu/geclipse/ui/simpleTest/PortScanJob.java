/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Christodoulos Efstathiades (cs05ce1@cs.ucy.ac.cy)
 *
 *****************************************************************************/

package eu.geclipse.ui.simpleTest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.TreeMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;

/**
 * A Job class that is used to port scan a selected host.
 * 
 * @author cs05ce1
 *
 */
public class PortScanJob extends Job {

  protected TreeMap<Integer, Boolean> portMap;
  protected Display display;
  
  InetAddress ia;

  StyledText results;
  

  
  int portsScanned,portsOpen,portsClosed;
  
  /**
   * The Default constructor
   * 
   * @param address The address of the host to scan.
   * @param map The TreeMap which contains the ports to scan.
   * @param results The text field where the output is printed.
   */
  public PortScanJob( final InetAddress address, final TreeMap<Integer, Boolean> map, final StyledText results ) {
    super( "Port Scan Job" );
    
    this.ia = address;
    this.portMap=map;
    this.results=results;
    this.display = this.results.getDisplay();
    
    
  }

  @Override
  protected IStatus run( final IProgressMonitor monitor ) {
    
    IStatus result = Status.CANCEL_STATUS;
    long timeBefore;
    long timeAfter;
    final long resultTime;
    
    final Integer[] ports = new Integer[portMap.keySet().size()];
    
    portMap.keySet().toArray(ports);
    
    monitor.beginTask("Port Scan",ports.length);
    
   // String hostname = ia.getHostName();
    
    Socket s=null;
    
    timeBefore = System.currentTimeMillis();

     for (int p = 0; p < ports.length; p++) {
        
       
       
       if(monitor.isCanceled()){
         result =  Status.CANCEL_STATUS;
       }

         try {

           int serverPort = ports[p].intValue();
          
            s = new Socket();

               //set the socket to timeout
            s.connect(new InetSocketAddress(ia, serverPort), 
                         10);
            s.close();

            portMap.put(ports[p],true);
            portsOpen++;

          }
          catch (IOException ex) {
              portsClosed++;
                try {
                    if(s!=null)
                        s.close();
                } catch (IOException e) {
                    
                    //e.printStackTrace();
                }
          }
      
          monitor.worked( 1 );
     }
     timeAfter = System.currentTimeMillis();

     resultTime = timeAfter - timeBefore;
     
    portsScanned = ports.length;
     
    if ( !this.display.isDisposed() ) {   
     this.display.syncExec( new Runnable() {
        public void run() {
          if ( !PortScanJob.this.results.isDisposed() ){
            PortScanJob.this.results.setText("");
            PortScanJob.this.results.append("Results for " + ia + "\n");
            PortScanJob.this.results.append("========================\n");
            PortScanJob.this.results.append("Openned Ports:\n");
          }
        }
      });
    }

     final Iterator<Integer> keys = portMap.keySet().iterator();
     
     
     if ( !this.display.isDisposed() ) {   
       this.display.syncExec( new Runnable() {
         public void run() {
           if ( !PortScanJob.this.results.isDisposed() ){
             while(keys.hasNext()){
               final Object currentKey = keys.next();
               
               if(portMap.get(currentKey)==true){
                 PortScanJob.this.results.append(currentKey + "\n");
               }
             }
           }
         }
       } );
     }      
     

     if ( !this.display.isDisposed() ) {   
       this.display.syncExec( new Runnable() {
         public void run() {
           if ( !PortScanJob.this.results.isDisposed() ){
             PortScanJob.this.results.append("\nStatistics:\n\n");
             PortScanJob.this.results.append("Ports scanned:\t\t"+ portsScanned + "\n");
             PortScanJob.this.results.append("Ports openned:\t"+ portsOpen + "\n");
             PortScanJob.this.results.append("Ports closed:\t\t"+ portsClosed + "\n\n");
             PortScanJob.this.results.append("Total Processing time: " + resultTime + " milliseconds\n");
           }
          }
       } );
     }

     portMap.clear();
     portsScanned=0;
     portsOpen=0;
     portsClosed=0;
     
     monitor.done();
     
     result = Status.OK_STATUS;
     
     return result;
  }
  

}
