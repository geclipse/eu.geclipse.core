/*****************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse Consortium 
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
 *    Ariel Garcia - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.util;

import java.lang.Math;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import org.eclipse.core.runtime.IProgressMonitor;
import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.ICoreSolutions;
import eu.geclipse.core.Messages;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ISolution;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.reporting.ReportingPlugin;


/**
 * A time checker using the TIME protocol (RFC 868),
 * also known as rdate protocol.
 * 
 * @author agarcia
 */
public class TimeChecker {

  // Public rdate servers
  static final String[] SERVERS = {
    "time.fu-berlin.de", //$NON-NLS-1$
    "time.mtu.edu",      //$NON-NLS-1$
    "ntp0.csx.cam.ac.uk" //$NON-NLS-1$
  };
  
  // Port
  private static final int RDATE_PORT = 37;
  
  // Seconds from midnight Jan. 1st 1900 - 1970
  private static final long EPOCH_DIFFERENCE = 2208988800L;
  
  // Connection timeout in milliseconds
  private static final int TIMEOUT = 5000;
  
  // Maximum allowed system-time offset in seconds
  private static final int MAX_SYSTIME_OFFSET = 90;
  
  // Maximum expected time difference between servers in seconds
  private static final int MAX_SERVER_SPREAD = 10;
  
  // The system time at the moment of querying the rdate servers
  private long sysTime;
  
  // The reference time, mean of the values returned by the servers
  private long referenceTime;


  /**
   * The constructor. Each instance stores the system and reference
   * time at the time of calling <code>{@link #checkSysTime}</code>.
   */
  public TimeChecker() {
    this.sysTime = 0;
    this.referenceTime = 0;
  }
  
  /**
   * Gets the system clock status. The method <code>{@link #checkSysTime}</code>
   * must be called first to initialize the TimeChecker instance.
   * 
   * @return <code>true</code> if the system time is OK, false otherwise
   */
  public boolean getTimeCheckStatus() {
    boolean result = false;
    if( ( this.sysTime != 0 )
        && ( Math.abs( this.sysTime - this.referenceTime ) <= MAX_SYSTIME_OFFSET ) )
    {
      result = true;
    }
    return result;
  }
  
  /**
   * Gets the time from some rdate time servers and compares it
   * to the system time. In the worst case, this method can take
   * up to TIMEOUT * SERVERS.length seconds (currently 15s) to
   * return. DNS timeouts would add to this delay, but they should
   * be a pathological case only.
   * The method <code>{@link #getTimeCheckStatus}</code> must be
   * called afterwards to get the result of the system time check.
   * 
   * @param  monitor a progress monitor used to monitor the
   *         time-check operation
   * @throws GridException if no servers could be contacted or they yielded
   *         inconsistent results
   * @throws InterruptedException if the operation was canceled by the user
   */
  public void checkSysTime( final IProgressMonitor monitor )
    throws ProblemException, InterruptedException
  {
    long[] time = new long[ SERVERS.length ];
    long mean = 0;
    long disp = 0;
    ProblemException problemExc = null;

    // Time from 1970 in seconds...
    this.sysTime = System.currentTimeMillis() / 1000;

    // Network queries might take some time, use a progress monitor
    monitor.beginTask( Messages.getString( "TimeChecker.quering_time_servers" ), //$NON-NLS-1$
                                           SERVERS.length );
    
    // Loop over all servers
    for ( int i = 0; i <  SERVERS.length; ++i ) {
      monitor.subTask( Messages.getString( "TimeChecker.contacting" ) //$NON-NLS-1$
                       + " " + SERVERS[ i ] + "..." ); //$NON-NLS-1$ //$NON-NLS-2$
      try {
        time[ i ] = queryTime( SERVERS[ i ] );
      } catch ( ProblemException pe ) {
        // We don't care about individual servers failing
        time[ i ] = 0;
        // Keep the last exception, we propagate it to the user if this check as a whole fails
        problemExc = pe;
      }
      monitor.worked( 1 );
      if ( monitor.isCanceled() ) {
        throw new InterruptedException();
      }
    }
    Arrays.sort( time );
    
    // The potentially time consuming operations are finished now
    monitor.done();
    
    // Loop over all results and compare them
    short i_min = 0;
    for ( int i = 0; i <  SERVERS.length; ++i ) {
      // Search the first non-null result, time[] is sorted
      if ( time[ i ] == 0 ) {
        i_min ++;
      } else {
        mean += time[ i ];
      }
    }
    // No servers could be contacted
    if ( i_min == SERVERS.length ) {
      IProblem problem;
      problem = ReportingPlugin.getReportingService()
                  .getProblem( ICoreProblems.SYSTEM_TIME_CHECK_FAILED,
                               null,
                               problemExc,
                               Activator.PLUGIN_ID );
      problem.addReason( Messages.getString( "TimeChecker.no_servers_reachable" ) ); //$NON-NLS-1$
      ISolution solution;
      solution = ReportingPlugin.getReportingService()
                   .getSolution( ICoreSolutions.CHECK_INTERNET_CONNECTION, null );
      problem.addSolution( solution );
      solution = ReportingPlugin.getReportingService()
                   .getSolution( ICoreSolutions.CHECK_FIREWALL, null );
      problem.addSolution( solution );
      throw new ProblemException( problem );
    }
    mean = mean / ( SERVERS.length - i_min );
    this.referenceTime = mean;
    
    // The servers don't agree among themselves
    disp = time[ SERVERS.length -1 ] - time[ i_min ];
    if ( disp >= MAX_SERVER_SPREAD ) {
      IProblem problem = ReportingPlugin.getReportingService()
                           .getProblem( ICoreProblems.SYSTEM_TIME_CHECK_FAILED,
                                        null,
                                        problemExc,
                                        Activator.PLUGIN_ID );
      problem.addReason( Messages.getString( "TimeChecker.inconsistent_servers" ) ); //$NON-NLS-1$
      ISolution solution = ReportingPlugin.getReportingService()
                             .getSolution( ICoreSolutions.CONTACT_SERVER_ADMIN, null );
      problem.addSolution( solution );
      throw new ProblemException( problem );
    }
  }
  
  /**
   * This method contacts an rdate server and returns the time
   * it reported. It <b>blocks</b> until it gets the answer back or
   * a timeout (TIMEOUT seconds) happens.
   * 
   * @param  serverHostname the rdate server to contact
   * @return the time in seconds since 1970
   * @throws GridException if the server could not be contacted
   */
  protected static long queryTime( final String serverHostname )
    throws ProblemException
  {
    long time = 0;
    InetAddress addr;

    // Resolve the server's IP address
    try {
      addr = InetAddress.getByName( serverHostname );
    } catch ( UnknownHostException uhe ) {
      throw new ProblemException( ICoreProblems.UNKNOWN_HOST, uhe, Activator.PLUGIN_ID );
    }
    
    /*
     * The rdate "protocol" is trivial: establish a connection
     * to the server on UDP/37 and send an empty datagram, the
     * server answers back with the time in seconds since 1900.
     */
    DatagramSocket s;
    try {
      s = new DatagramSocket();
      s.setSoTimeout( TIMEOUT );
    } catch ( SocketException se ) {
      throw new ProblemException( ICoreProblems.BIND_FAILED, se, Activator.PLUGIN_ID );
    }
    
    // Empty datagram to be sent to the rdate server
    DatagramPacket p;
    p = new DatagramPacket( new byte[0], 0, addr, RDATE_PORT );
    
    // Contact the server
    try {      
      s.send( p );
    } catch ( Exception exc ) {
      throw new ProblemException( ICoreProblems.CONNECTION_FAILED, exc, Activator.PLUGIN_ID );
    }
    
    // Datagram to collect the server's answer, 4 bytes
    byte[] b = new byte[4];
    p = new DatagramPacket( b, b.length);
    
    //  We wait for the server's response
    try {
      s.receive( p );
    } catch ( Exception exc ) {
      throw new ProblemException( ICoreProblems.CONNECTION_TIMEOUT, exc, Activator.PLUGIN_ID );
    }
    
    // Read value from the buffer
    time = readLongFromNetwork( b );
    
    // The system time is measured starting from 1970...
    return time - EPOCH_DIFFERENCE;
  }
  
  /**
   * Reads an array of bytes (unsigned!) into a long. Network order is
   * big endian (MSB first), which is also Java's default for a byte array.
   * 
   * @param  array the array to read
   * @return the long value contained in the array
   */
  private static long readLongFromNetwork( final byte[] array ) {
    long unsigned = 0;
    long val = 0;

    for ( int i = 0; i < array.length; ++i ) {
      // The first shift is a no-op
      val <<= 8;
      // This casts the byte to long, avoiding sign issues
      unsigned = 0xFFL & array[ i ];
      val |= unsigned;
    }
    
    return val;
  }
  
  /**
   * Gets the system date/time. The method <code>{@link #checkSysTime}</code>
   * must be called first to initialize the TimeChecker instance.
   * 
   * @return a string representation of the system date/time
   */
  public String getSystemDate() {
    // Date needs the time in ms
    Date date = new Date( this.sysTime * 1000 );
    DateFormat df = DateFormat.getDateTimeInstance( DateFormat.MEDIUM,
                                                    DateFormat.FULL);
    
    return df.format( date );
  }

  /**
   * Gets the reference date/time. The method <code>{@link #checkSysTime}</code>
   * must be called first to initialize the TimeChecker instance.
   * 
   * @return a string representation of the reference date/time
   */
  public String getReferenceDate() {
    // Date needs the time in ms
    Date date = new Date( this.referenceTime * 1000 );
    DateFormat df = DateFormat.getDateTimeInstance( DateFormat.MEDIUM,
                                                    DateFormat.FULL);
    
    return df.format( date );
  }
  
  /**
   * Gets the system clock offset. The method <code>{@link #checkSysTime}</code>
   * must be called first to initialize the TimeChecker instance.
   * 
   * @return the system clock offset in seconds
   */
  public long getOffset() {
    return ( Math.abs( this.sysTime - this.referenceTime ) );
  }
  
  /**
   * Gets the tolerance for the system clock offset.
   * 
   * @return the maximum allowed system clock offset
   */
  public int getTolerance() {
    return MAX_SYSTIME_OFFSET;
  }

}
