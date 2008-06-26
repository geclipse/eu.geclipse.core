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
import java.net.UnknownHostException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;

/**
 * A Job class that is used to lookup the IP for a set of hosts
 * 
 * @author kgjermun
 */
public class DNSLookUpJob extends Job {

  protected Display display;
  
  private Table table;
 
  private String[][] itemStrings;
 
  /**
   * The default constructor. 
   * 
   * @param table The table where the result is displayed
   * @param itemString The row in the table for this job
   */
  public DNSLookUpJob( final Table table, final String[][] itemString ) {
    super( Messages.getString( "DNSLookUpJob.lookUpMsg" ) ); //$NON-NLS-1$

    this.table = table;
    this.itemStrings = itemString;

    this.display = this.table.getDisplay();
  }
  
  @Override
  protected IStatus run( final IProgressMonitor monitor ) {
    InetAddress adr = null;

    IStatus result = Status.CANCEL_STATUS;
    
    monitor.beginTask( Messages.getString( "DNSLookUpJob.lookUp" ) //$NON-NLS-1$
                       + this.itemStrings.length
                       + Messages.getString( "DNSLookUpJob.hosts" ), //$NON-NLS-1$ 
                       this.itemStrings.length );
    
    for ( int i = 0; i < this.itemStrings.length && !monitor.isCanceled(); ++i ) {
      try {
        adr = InetAddress.getByName( this.itemStrings[ i ][ 0 ] );
          
        if ( null != adr ) 
          this.itemStrings[ i ][ 1 ] = adr.getHostAddress();
        else
          this.itemStrings[ i ][ 1 ] = Messages.getString( "DNSLookUpJob.n_a" ); //$NON-NLS-1$
      } catch( UnknownHostException e ) {
        this.itemStrings[ i ][ 1 ] = Messages.getString( "DNSLookUpJob.n_a" ); //$NON-NLS-1$
      }
      
      if ( !this.display.isDisposed() ) {   
        this.display.syncExec( new Runnable() {
          public void run() {
            // Write the summary
            if ( !DNSLookUpJob.this.table.isDisposed() ) { 
              DNSLookUpJob.this.table.clearAll();
            }
          }
        } );
      }

      monitor.worked( 1 );
    }
    
    monitor.done();
    
    result = Status.OK_STATUS;
    
    return result;
  }
}
