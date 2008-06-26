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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.internal;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ISolution;
import eu.geclipse.ui.internal.dialogs.ProblemReportDialog;

public class MailToSolution implements ISolution {
  
  private IProblem problem;
  
  public MailToSolution( final IProblem problem ) {
    this.problem = problem;
  }

  public String getDescription() {
    return "Create problem report";
  }
  
  public String getID() {
    return null;
  }

  public boolean isActive() {
    return this.problem != null;
  }

  public void solve() {
    ProblemReportDialog dialog = new ProblemReportDialog( getShell(), this.problem );
    dialog.open();
  }
  
  private Shell getShell() {
    
    Shell result = null;
    
    IWorkbench workbench = PlatformUI.getWorkbench();
    IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
    
    if ( window != null ) { 
      result = window.getShell();
    }

    return result;
    
  }

}
