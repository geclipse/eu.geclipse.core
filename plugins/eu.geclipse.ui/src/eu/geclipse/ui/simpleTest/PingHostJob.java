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
 *     Harald Gjermundrod - initial API and implementation
 *****************************************************************************/
package eu.geclipse.ui.simpleTest;

import java.net.InetAddress;
import java.text.DecimalFormat;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import eu.geclipse.core.simpleTest.PingTest;


/**
 * A Job class that is used to ping a host a number of times
 * 
 * @author kgjermun
 */
public class PingHostJob extends Job {
  
  private static DecimalFormat df = new DecimalFormat( "0.000" ); //$NON-NLS-1$

  protected String lineDelimiter;
  
  protected PingTest pingTest;
  
  protected Display display;

  protected String summary;

  protected double pingDelay;

  private InetAddress hostAdr;
  
  private int numPing;
  
  private Text outPut; 
  
  private String hostName;
  
  private long timeOut;

  /**
   * The default constructor. 
   * 
   * @param hostAdr A non-null address of the host to be pinged. 
   * @param nPing The number of times that this host should be pinged.
   * @param timeOut the delay between each ping attempt. 
   * @param outPut The text field where the output is printed
   * @param pingTest A reference to the ping test class to be used. 
   */
  public PingHostJob( final InetAddress hostAdr, final int nPing, final long timeOut, 
                      final Text outPut, final PingTest pingTest ) {
    super( Messages.getString( "PingTestDialog.pingMsg" ) + hostAdr.getHostName() ); //$NON-NLS-1$
    
    this.hostName = hostAdr.getHostName();
    this.hostAdr = hostAdr;
    this.numPing = nPing;
    this.outPut = outPut;
    this.pingTest = pingTest;
    this.timeOut = timeOut;
    this.display = this.outPut.getDisplay();
    this.lineDelimiter = this.outPut.getLineDelimiter();
  }

  @Override
  protected IStatus run( final IProgressMonitor monitor ) {
    double min = Long.MAX_VALUE; 
    double max = Long.MIN_VALUE;
    double avg = 0;
    int nOk = 0;
    int nFailed = 0;
    int i;
    
    IStatus result = Status.CANCEL_STATUS;
    monitor.beginTask( Messages.getString( "PingTestDialog.pingMsg" ) + this.hostName //$NON-NLS-1$ 
                       + " " + this.numPing //$NON-NLS-1$
                       + Messages.getString( "PingTestDialog.spacePlusTimes" ), this.numPing ); //$NON-NLS-1$
    
    for ( i = 0; i < this.numPing && !monitor.isCanceled(); ++i ) {
      this.pingDelay = this.pingTest.ping( this.hostAdr );

        if ( -1 == this.pingDelay ) {
          ++nFailed;
          
        if ( !this.display.isDisposed() ) {   
          this.display.syncExec( new Runnable() {
            public void run() {
              if ( !PingHostJob.this.outPut.isDisposed() )
                PingHostJob.this.outPut.append( hostName + ": "   //$NON-NLS-1$
                                  + Messages.getString( "PingTestDialog.notReachable" )  //$NON-NLS-1$
                                  + lineDelimiter );
            }
          } );
          }
        }
        else {
          ++nOk;
          if ( this.pingDelay < min )
            min = this.pingDelay;
          if ( this.pingDelay > max )
            max = this.pingDelay;
          avg += this.pingDelay;

          if ( !this.display.isDisposed() ) {   
            this.display.syncExec( new Runnable() {
              public void run() {
                if ( !PingHostJob.this.outPut.isDisposed() )
                  PingHostJob.this.outPut.append( hostName + ": "  //$NON-NLS-1$
                              + Messages.getString( "PingTestDialog.time" )  //$NON-NLS-1$
                              + df.format( pingDelay ) + " ms"  //$NON-NLS-1$
                              + PingHostJob.this.lineDelimiter );
              }
            } );
          }      
        }
        
//        try {
//          this.getThread().wait( this.timeOut * 1000 );
//        } catch( InterruptedException e ) {
//          // We just ignore this. 
//        }
      
      monitor.worked( 1 );
    }
    
    this.summary = this.lineDelimiter + "Summary for: " + this.hostName + this.lineDelimiter  //$NON-NLS-1$
                   + i + Messages.getString( "PingTestDialog.transmitted" )  //$NON-NLS-1$
                   + nOk + Messages.getString( "PingTestDialog.receivedPlusSpace" )  //$NON-NLS-1$
                   + df.format( ( ( ( double )nFailed ) / i ) * 100 ) 
                   + Messages.getString( "PingTestDialog.packetLoss" )  //$NON-NLS-1$
                   + this.lineDelimiter;
    
    if ( nOk > 0 )
      this.summary = this.summary.concat( Messages.getString( "PingTestDialog.summaryPlusSpace" )  //$NON-NLS-1$
                    + df.format( min ) + "/"  //$NON-NLS-1$
                    + df.format( avg/nOk ) + "/"  //$NON-NLS-1$
                    + df.format( max ) + " ms"  //$NON-NLS-1$
                    + this.lineDelimiter + this.lineDelimiter );
    else
      this.summary = this.summary.concat( Messages.getString( "PingTestDialog.summaryFailed" )  //$NON-NLS-1$
                    + this.lineDelimiter + this.lineDelimiter );
    
    if ( !this.display.isDisposed() ) {   
      this.display.syncExec( new Runnable() {
        public void run() {
          // Write the summary
          if ( !PingHostJob.this.outPut.isDisposed() ) 
            PingHostJob.this.outPut.append( PingHostJob.this.summary ); 
        }
      } );
    }

    monitor.done();
    
    result = Status.OK_STATUS;
    
    return result;
  }

}