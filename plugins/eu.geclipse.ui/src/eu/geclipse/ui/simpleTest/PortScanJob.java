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
import java.util.TreeMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;

/**
 * A Job class that is used to port scan a selected host.
 * 
 * @author cs05ce1
 *
 */
public class PortScanJob extends Job {

  protected TreeMap<Integer, Boolean> portMap;
  protected Display display;
  protected Display display2;
  protected TreeItem treeItem;
  
  InetAddress ia;

  StyledText results;
  

  
  int portsScanned,portsOpen,portsClosed;
  int serverPort;
  
  /**
   * The Default constructor
   * 
   * @param address The address of the host to scan.
   * @param map The TreeMap which contains the ports to scan.
   * @param results The text field where the output is printed.
   */
  public PortScanJob( final InetAddress address, final TreeMap<Integer, Boolean> map, final StyledText results, final TreeItem treeItem ) {
    super( "Port Scan Job" ); //$NON-NLS-1$
    
    this.ia = address;
    this.portMap=map;
    this.results=results;
    this.treeItem = treeItem;
    

    this.display = this.treeItem.getDisplay();
    
    this.display2 = this.results.getDisplay();
  }

  @Override
  protected IStatus run( final IProgressMonitor monitor ) {
    long timeBefore;
    long timeAfter;
    final long resultTime;
    int p = 0;
    
    final Integer[] ports = new Integer[ this.portMap.keySet().size() ];
    
    this.portMap.keySet().toArray(ports);
    
    IStatus result = Status.CANCEL_STATUS;
    
    monitor.beginTask( "Port Scan", ports.length );
    
   // String hostname = ia.getHostName();
    
    Socket s=null;
    
    timeBefore = System.currentTimeMillis();

    
    
    for ( p = 0; p < ports.length && !monitor.isCanceled(); p++ ) {
      try {
        this.serverPort = ports[ p ].intValue();
          
        s = new Socket();

        //set the socket to timeout
        s.connect( new InetSocketAddress( this.ia, this.serverPort ), 100 );
        s.close();

        this.portMap.put( ports[ p ], true );
        this.portsOpen++;
            
        if ( !this.display.isDisposed() ) {   
          this.display.syncExec( new Runnable() {
            public void run() {
              if ( !PortScanJob.this.treeItem.isDisposed() ){
                PortScanJob.this.treeItem.setText(new String[]{ PortScanJob.this.ia.getHostName(),"OK" } );
                TreeItem portItem = new TreeItem( PortScanJob.this.treeItem, SWT.NONE );
                portItem.setText( new String[] { "" + PortScanJob.this.serverPort, "Open" } );    
              }
            }
          } );
        }
      }
      catch (IOException ex) {
        this.portsClosed++;

        try {
          if( s != null )
            s.close();
          } catch (IOException e) {
          // Nothing needed
        }
      }
          
      monitor.worked( 1 );
    }
    timeAfter = System.currentTimeMillis();
    resultTime = timeAfter - timeBefore;
     
    this.portsScanned = p;
     
    if ( !this.display2.isDisposed() ) {   
      this.display2.syncExec( new Runnable() {
        public void run() {
          if ( !PortScanJob.this.results.isDisposed() ){
            PortScanJob.this.results.append( "Statistics for " + ia + "\n" 
                    + "Ports scanned:\t\t"+ portsScanned + "\n" 
                    + "Ports openned:\t"+ portsOpen + "\n" 
                    + "Ports closed:\t\t"+ portsClosed + "\n" 
                    + "Total Processing time: " + resultTime + " ms\n\n" );
          }
        }
      });
    }
    this.portMap.clear();
    this.portsScanned=0;
    this.portsOpen=0;
    this.portsClosed=0;
     
    monitor.done();
     
    result = Status.OK_STATUS;
     
    return result;
  }

}