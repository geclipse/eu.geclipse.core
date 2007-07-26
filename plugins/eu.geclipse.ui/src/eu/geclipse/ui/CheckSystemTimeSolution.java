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

package eu.geclipse.ui;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import eu.geclipse.core.GridException;
import eu.geclipse.core.ISolution;
import eu.geclipse.core.util.TimeChecker;
import eu.geclipse.ui.Messages;
import eu.geclipse.ui.dialogs.NewProblemDialog;


/**
 * {@link UISolution} for checking the system time against
 * a set of reference time servers.
 * 
 * @author ariel
 */
public class CheckSystemTimeSolution extends UISolution {
  
  /**
   * Creates a new <code>CheckSystemTimeSolution</code>.
   * 
   * @param slave A non-UI solution that is made active by the UI.
   * @param shell The {@link Shell} used for the solution.
   */
  public CheckSystemTimeSolution( final ISolution slave,
                                   final Shell shell ) {
    super( slave, shell );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.UISolution#isActive()
   */
  @Override
  public boolean isActive() {
    return true;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.UISolution#solve()
   */
  @Override
  public void solve() {
    final TimeChecker tr = new TimeChecker();
    boolean timeOK = false;
    boolean timeCheckValid = true;

    IRunnableWithProgress runnable = new IRunnableWithProgress() {
      public void run( final IProgressMonitor monitor )
          throws InvocationTargetException, InterruptedException
      {
        try {
          tr.checkSysTime( monitor );
        } catch( GridException ge ) {
          throw new InvocationTargetException( ge );
        }
      }
    };
    
    // Run the dialog
    try {
      new ProgressMonitorDialog( getShell() ).run( true, true, runnable );
    } catch ( InterruptedException ie ) {
      // The user interrupted the operation
      timeCheckValid = false;
    } catch( InvocationTargetException ite ) {
      // The system time check failed, inform the user
      Throwable cause = ite.getCause();
      if ( cause instanceof GridException ) {
        String title = Messages.getString( "CheckSystemTimeSolution.failed_dialog_title" ); //$NON-NLS-1$
        String msg = Messages.getString( "CheckSystemTimeSolution.failed_check_system_time" ); //$NON-NLS-1$
        NewProblemDialog.openProblem( getShell(), title, msg, cause );
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
    Shell parent = getShell();
    
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

}
