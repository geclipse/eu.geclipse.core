/*****************************************************************************
 * Copyright (c) 2007 g-Eclipse Consortium 
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
 *    Ariel Garcia - initial implementation
 *****************************************************************************/

package eu.geclipse.core.util;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import junit.framework.AssertionFailedError;
import java.util.Date;
import java.text.ParseException;
import java.text.DateFormat;
import org.eclipse.core.runtime.NullProgressMonitor;
import eu.geclipse.core.GridException;


/**
 * Test class for the {@link eu.geclipse.core.util.TimeChecker} class.
 * 
 * @author ariel
 */
public class TimeChecker_Test {

  /*
   * Minimum and maximum time values to expect from the server
   * queries, this covers approx. years 2006-2012.
   */
  private static final long MIN_SENSIBLE_TIME = 1150000000;
  private static final long MAX_SENSIBLE_TIME = 1350000000;
  
  // Tolerance should not be bigger than 2 minutes
  private static final long MAX_SENSIBLE_TOLERANCE = 120;
  
  // Share the queries between some of the tests
  private TimeChecker tr;
  private boolean timeOK;
  
  @Before
  public void setUp() {
    this.tr = new TimeChecker();
    
    // We expect the machine running the tests to be on time
    try {
      this.tr.checkSysTime( new NullProgressMonitor() );
      this.timeOK = this.tr.getTimeCheckStatus();
    } catch ( GridException ge ) {
      this.timeOK = false;
    } catch ( InterruptedException ie ) {
      // The operation can only be interrupted from the UI
    }
  }
  
  /**
   * Test method for {@link TimeChecker#queryTime}.
   * This also checks the individual servers. We assume that
   * the machine running the tests has network connectivity.
   */
  @Test
  public void testQueryTime() {
    long[] time = new long[ TimeChecker.SERVERS.length ];

    // Loop over all servers
    for ( int i = 0; i < TimeChecker.SERVERS.length; ++i ) {
      try {
        time[ i ] = TimeChecker.queryTime( TimeChecker.SERVERS[ i ] );
      } catch ( GridException ge ) {
        String msg = "Server " + TimeChecker.SERVERS[ i ] + " could not be contacted!"; //$NON-NLS-1$ //$NON-NLS-2$
        throw new AssertionFailedError( msg );
      }
      
      if ( ( time[ i ] < MIN_SENSIBLE_TIME )
        || ( time[ i ] > MAX_SENSIBLE_TIME ) )
      {
        String msg = "Result from server " //$NON-NLS-1$
                     + TimeChecker.SERVERS[ i ]
                     + " is unrealistic!"; //$NON-NLS-1$
        throw new AssertionFailedError( msg );
      }
    }
  }
  
  /**
   * Test method for {@link TimeChecker#checkSysTime}.
   */
  @Test
  public void testCheckSysTime() {
    // The time servers are queried in setUp(), before running this test 
    assertTrue( this.timeOK );
  }
  
  /**
   * Test method for {@link TimeChecker#getSystemDate}.
   */
  @Test
  public void testGetSystemDate() {
    String dateString = this.tr.getSystemDate();

    DateFormat df = DateFormat.getDateTimeInstance( DateFormat.MEDIUM,
                                                    DateFormat.FULL);
    Date date;
    try {
      date = df.parse( dateString );
    } catch ( ParseException pe ) {
      throw new AssertionFailedError( "Could not parse the returned date string." ); //$NON-NLS-1$
    }
    long time = date.getTime() / 1000;
    assertTrue ( time >= MIN_SENSIBLE_TIME );
    assertTrue ( time <= MAX_SENSIBLE_TIME );
  }
  
  /**
   * Test method for {@link TimeChecker#getReferenceDate}.
   */
  @Test
  public void testGetReferenceDate() {
    String dateString = this.tr.getReferenceDate();

    DateFormat df = DateFormat.getDateTimeInstance( DateFormat.MEDIUM,
                                                    DateFormat.FULL);
    Date date;
    try {
      date = df.parse( dateString );
    } catch ( ParseException pe ) {
      throw new AssertionFailedError( "Could not parse the returned date string." ); //$NON-NLS-1$
    }
    long time = date.getTime() / 1000;
    assertTrue ( time >= MIN_SENSIBLE_TIME );
    assertTrue ( time <= MAX_SENSIBLE_TIME );
  }
  
  /**
   * Test method for {@link TimeChecker#getOffset}.
   */
  @Test
  public void testGetOffset() {
    long offset = this.tr.getOffset();
    // We only check if it is non-negative
    assertTrue ( offset >= 0 );
  }
  
  /**
   * Test method for {@link TimeChecker#getTolerance}.
   */
  @Test
  public void testGetTolerance() {
    long tol = this.tr.getTolerance();
    assertTrue ( tol > 0 );
    assertTrue ( tol <= MAX_SENSIBLE_TOLERANCE );
  }

}
