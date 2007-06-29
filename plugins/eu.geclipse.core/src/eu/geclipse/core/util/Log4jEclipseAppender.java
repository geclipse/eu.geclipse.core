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
 *    Thomas Koeckerbauer GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.util;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * Wrapper to log to the eclipse logging system from Log4j.
 */
public class Log4jEclipseAppender extends AppenderSkeleton {
  private ILog log;
    
  /**
   * Creates a wrapper which logs all Log4j log messages to
   * the Eclipse logger.
   * @param log logger to log to.
   */
  public Log4jEclipseAppender( final ILog log ) {
    this.log = log;
  }
  
  /* (non-Javadoc)
   * @see org.apache.log4j.AppenderSkeleton#append(org.apache.log4j.spi.LoggingEvent)
   */
  @Override
  protected void append( final LoggingEvent event ) {
    int level = IStatus.INFO;
    if ( event.getLevel() == Level.DEBUG ) level = IStatus.INFO;
    else if ( event.getLevel() == Level.WARN ) level = IStatus.WARNING;
    else if ( event.getLevel() == Level.ERROR ) level = IStatus.ERROR;
    else if ( event.getLevel() == Level.FATAL ) level = IStatus.ERROR;
    else if ( event.getLevel() == Level.INFO ) level = IStatus.INFO;
    String message = event.getMessage().toString();
    String loggerName = event.getLoggerName();

    IStatus status = new Status( level,
                                 loggerName,
                                 IStatus.OK,
                                 message,
                                 null );
    this.log.log( status );
  }

  /* (non-Javadoc)
   * @see org.apache.log4j.Appender#close()
   */
  @Override
  public void close() {
    // not required
  }

  /* (non-Javadoc)
   * @see org.apache.log4j.Appender#requiresLayout()
   */
  @Override
  public boolean requiresLayout() {
    return false;
  }
}
