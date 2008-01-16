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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/
package eu.geclipse.ui.internal;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.reporting.ISolution;

public class LogExceptionSolution
    implements ISolution {
  
  /**
   * The ID of the PDE log view.
   */
  public static final String LOG_VIEW_ID
    = "org.eclipse.pde.runtime.LogView"; //$NON-NLS-1$
  
  private Throwable exception;
  
  public LogExceptionSolution( final Throwable exc ) {
    this.exception = exc;
  }

  public String getDescription() {
    return "Log exception";
  }
  
  public String getID() {
    return null;
  }

  public boolean isActive() {
    return true;
  }

  public void solve() {
    
    Activator.logException( this.exception );
    
    IWorkbench workbench = PlatformUI.getWorkbench();
    IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
    IWorkbenchPage page = window.getActivePage();
  
    try {
      page.showView( LOG_VIEW_ID );
    } catch( PartInitException piExc ) {
      Activator.logException( piExc );
    }
    
  }

}
