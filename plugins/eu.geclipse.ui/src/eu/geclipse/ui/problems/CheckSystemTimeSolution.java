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
 *                 - updated to new problem reporting
 *****************************************************************************/

package eu.geclipse.ui.problems;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.reporting.ISolver;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.util.TimeChecker;
import eu.geclipse.ui.dialogs.ProblemDialog;


/**
 * {@link ISolution} for checking the system time against
 * a set of reference time servers.
 * 
 * @author agarcia
 */
public class CheckSystemTimeSolution implements ISolver {
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.ISolution#getDescription()
   */
  public String getDescription() {
    return Messages.getString("CheckSystemTimeSolution.description"); //$NON-NLS-1$
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.ISolver#solve()
   */
  public void solve() {
    final TimeChecker tr = new TimeChecker();
    boolean timeOK = false;
    boolean timeCheckValid = true;
    
    // Get the shell first
    IWorkbench workbench = PlatformUI.getWorkbench();
    IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
    Shell shell = null;
    if ( window != null ) { 
      shell = window.getShell();
    }
    
    IRunnableWithProgress runnable = new IRunnableWithProgress() {
      public void run( final IProgressMonitor monitor )
          throws InvocationTargetException, InterruptedException
      {
        try {
          tr.checkSysTime( monitor );
        } catch( ProblemException pe ) {
          throw new InvocationTargetException( pe );
        }
      }
    };
    
    // Run the dialog
    try {
      new ProgressMonitorDialog( shell ).run( true, true, runnable );
    } catch ( InterruptedException ie ) {
      // The user interrupted the operation
      timeCheckValid = false;
    } catch( InvocationTargetException ite ) {
      // The system time check failed, inform the user
      Throwable cause = ite.getCause();
      if ( cause instanceof ProblemException ) {
        String title = Messages.getString( "CheckSystemTimeSolution.failed_dialog_title" ); //$NON-NLS-1$
        String msg = Messages.getString( "CheckSystemTimeSolution.failed_check_system_time" ); //$NON-NLS-1$
        ProblemDialog.openProblem( shell, title, msg, cause );
        timeCheckValid = false;
      }
    }
    
    // Don't display any more dialogs, we cannot help further...
    if ( ! timeCheckValid ) {
      return;
    }
    
    // The servers have been queried
    timeOK = tr.getTimeCheckStatus();
    
    String title = Messages.getString( "CheckSystemTimeSolution.dialog_title" ); //$NON-NLS-1$
    Shell parent = shell;
    
    // Construct the message string
    String message;
    if ( timeOK ) {
      message = Messages.getString( "CheckSystemTimeSolution.system_clock_ok" ); //$NON-NLS-1$
      message = String.format( message, Integer.valueOf( tr.getTolerance() ) );
    } else {
      message = Messages.getString( "CheckSystemTimeSolution.system_clock_not_ok" ); //$NON-NLS-1$
      message = String.format( message, Long.valueOf( tr.getOffset() ) );
    }
    message += ":\n\t" + tr.getSystemDate() + "\n\n"; //$NON-NLS-1$ //$NON-NLS-2$
    message += Messages.getString( "CheckSystemTimeSolution.queried_servers_report" ); //$NON-NLS-1$
    message += ":\n\t" + tr.getReferenceDate(); //$NON-NLS-1$

    // Show the appropriate dialog
    if ( timeOK ) {
      MessageDialog.openInformation( parent, title, message );
    } else {
      MessageDialog.openError( parent, title, message );
    }
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.ISolution#getID()
   */
  public String getID() {
    return null;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.ISolution#isActive()
   */
  public boolean isActive() {
    return true;
  }

}
