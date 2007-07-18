/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.ui;

import org.eclipse.core.runtime.Assert;

import eu.geclipse.core.ISolution;
import eu.geclipse.core.Solution;
import eu.geclipse.core.SolutionRegistry;
import eu.geclipse.ui.widgets.DateTimeText;

/**
 * Solutions for problems connected with {@link DateTimeText}
 */
public class DateTimeSolutionRegistry extends SolutionRegistry {

  /**
   * 
   */
  public static int USE_CALENDAR_BUTTON = SolutionRegistry.uniqueID();
  /**
   * 
   */
  public static int APPLY_VALID_DATEFORMAT = SolutionRegistry.uniqueID();
  /**
   * 
   */
  public static int DELETE_ENTERED_DATE = SolutionRegistry.uniqueID();
  private static DateTimeSolutionRegistry singleton;

  public static DateTimeSolutionRegistry getRegistry() {
    if( singleton == null ) {
      singleton = new DateTimeSolutionRegistry();
    }
    return singleton;
  }

  /**
   * @param solutionId
   * @param widget widget for which solution will be found
   * @return found solution
   */
  public ISolution findSolution( final int solutionId, final DateTimeText widget )
  {
    ISolution solution = null;
    if( solutionId == USE_CALENDAR_BUTTON ) {
      solution = createStartCalendarSolutution( widget );
    } else if( solutionId == APPLY_VALID_DATEFORMAT ) {
      solution = new Solution( APPLY_VALID_DATEFORMAT,
                               Messages.getString( "DateTimeSolutionRegistry.EnterDateInFormat" ) + widget.getValidDateFormat() ) { //$NON-NLS-1$
        // empty implementation, because constructor of Solution is protected
      };
    } else if( solutionId == DELETE_ENTERED_DATE ) {
      solution = new Solution( DELETE_ENTERED_DATE,
                               Messages.getString( "DateTimeSolutionRegistry.DeleteDate" ) ) { //$NON-NLS-1$
        // empty implementation, because constructor of Solution is protected
      };
    }
    Assert.isNotNull( solution,
                      "Unknown id in DateTimeSolutionRegistry: " + Integer.valueOf( solutionId ).toString() ); //$NON-NLS-1$
    return solution;
  }

  private ISolution createStartCalendarSolutution( final DateTimeText widget ) {
    return new UISolution( USE_CALENDAR_BUTTON,
                           Messages.getString( "DateTimeSolutionRegistry.UseCalendarButton" ), widget.getShell() ) { //$NON-NLS-1$

      @Override
      public void solve() {
        widget.openCalendarDialog();
      }
    };
  }
}
