/*****************************************************************************
 * Copyright (c) 2006, 2007, 2008 g-Eclipse Consortium 
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
 *    Jie Tao -- initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.wizards.deployment;


import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.model.IGridApplication;
import eu.geclipse.core.model.IGridApplicationManager;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;


/**remove the installed software
 * @author tao-j
 *
 */
public class UninstallJob  extends Job {
  
  private IGridApplication[] uninstallapllications;
  private IVirtualOrganization vo;
  /** this job is responsible to execute the uninstal activities
   * @param name
   * @param applications
   * @param vo
   */
  public UninstallJob( final String name, final IGridApplication[] applications, final IVirtualOrganization vo ) {
    super( name );
    this.uninstallapllications = applications;
    this.vo = vo;
  }

  @Override
  protected IStatus run( final IProgressMonitor monitor ) {
    IStatus status = Status.OK_STATUS;
    SubMonitor betterMonitor = SubMonitor.convert( monitor,
                                                   this.uninstallapllications.length );
    testCancelled( betterMonitor );
    betterMonitor.setTaskName( "Starting to remove the software" ); //$NON-NLS-1$
    IGridApplicationManager appmanager = this.vo.getApplicationManager();
    if (appmanager == null)
    {
      final Throwable e = new Throwable();
      Display.getDefault().asyncExec( new Runnable() {
        public void run() {
          
          ProblemDialog.openProblem( null,
                                     "Deployment error", //$NON-NLS-1$
                                     "The middleware does not support application deployment", //$NON-NLS-1$
                                     e );
        }
      } );
    }
    else {
    for (IGridApplication one : this.uninstallapllications)
      try {
        appmanager.uninstall( one, betterMonitor.newChild( 1 ) );
      } catch( ProblemException e ) {
        ProblemDialog.openProblem( PlatformUI.getWorkbench()
                                   .getActiveWorkbenchWindow()
                                   .getShell(),
                                   "Application uninstall error", //$NON-NLS-1$
                                   "Error when removing the software", //$NON-NLS-1$
                                   e);
      }
    testCancelled( betterMonitor );
    betterMonitor.done();
    }
    return status;
  }
  
  private void testCancelled( final IProgressMonitor monitor ) {
    if( monitor.isCanceled() ) {
      throw new OperationCanceledException();
    }
  }
  
}
