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

package eu.geclipse.ui;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import eu.geclipse.core.ISolution;
import eu.geclipse.ui.internal.Activator;

@Deprecated
public class ShowViewSolution extends UISolution {
  
  private String viewID;

  public ShowViewSolution( final ISolution slave,
                           final String viewID ) {
    super( slave, null );
    this.viewID = viewID;
  }
  
  public ShowViewSolution( final int id,
                           final String text,
                           final String viewID ) {
    super( id, text, null );
    this.viewID = viewID;
  }
  
  public String getViewID() {
    return this.viewID;
  }
  
  @Override
  public void solve() {
    IWorkbench workbench
      = PlatformUI.getWorkbench();
    IWorkbenchWindow window
      = workbench.getActiveWorkbenchWindow();
    IWorkbenchPage page
      = window.getActivePage();
    try {
      page.showView( getViewID() );
    } catch( PartInitException piExc ) {
      Activator.logException( piExc );
    }
  }
  
}
