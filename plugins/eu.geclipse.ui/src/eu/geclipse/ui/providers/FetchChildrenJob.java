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
 *     Mathias Stuempert - initial API and implementation
 *****************************************************************************/
package eu.geclipse.ui.providers;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Shell;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.util.MasterMonitor;
import eu.geclipse.ui.dialogs.ProblemDialog;


public class FetchChildrenJob extends Job {
  
  private IGridContainer container;
  
  private IProgressMonitor externalMonitor;
  
  private Shell shell;
  
  public FetchChildrenJob( final IGridContainer container, final Shell shell ) {
    super( "Fetching Children of " + container.getName() );
    this.container = container;
    this.shell = shell;
  }
  
  public void setExternalMonitor( final IProgressMonitor monitor ) {
    this.externalMonitor = monitor;
  }

  @Override
  protected IStatus run( final IProgressMonitor monitor ) {

    IStatus result = Status.OK_STATUS;
    MasterMonitor mMonitor = new MasterMonitor( monitor, this.externalMonitor );
    
    try {
      this.container.setDirty();
      this.container.getChildren( mMonitor );
      result = Status.OK_STATUS;
    } catch ( ProblemException pExc ) {
      ProblemDialog.openProblem( this.shell,
                                 "Fetch Error",
                                 "Error while fetching children of " + this.container.getName(),
                                 pExc );
    } finally {
      mMonitor.done();
    }
    
    return result;
    
  }

}
