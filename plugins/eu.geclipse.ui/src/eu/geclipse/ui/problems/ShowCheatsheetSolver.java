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
 *    Ariel Garcia - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.problems;

import java.lang.reflect.Method;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.reporting.IConfigurableSolver;
import eu.geclipse.ui.internal.Activator;


/**
 * This Solver takes care of opening the Cheatsheet viewer at the
 * requested cheatsheet ID.
 */
public class ShowCheatsheetSolver implements IConfigurableSolver {
  
  private static final String CHEATSHEET_VIEW_ID
    = "org.eclipse.ui.cheatsheets.views.CheatSheetView"; //$NON-NLS-1$
  
  private static final String CHEATSHEET_ID_ATTRIBUTE
    = "cheatsheetID"; //$NON-NLS-1$
  
  private String cheatsheetID;
  
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
   */
  public void setInitializationData( final IConfigurationElement config,
                                     final String propertyName,
                                     final Object data)
      throws CoreException {
    this.cheatsheetID = config.getAttribute( CHEATSHEET_ID_ATTRIBUTE );
  }
  
  /*
   * (non-Javadoc)
   * @see eu.geclipse.core.reporting.ISolver#solve()
   */
  public void solve() {
    
    IWorkbench workbench = PlatformUI.getWorkbench();
    IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
    IWorkbenchPage page = window.getActivePage();
    
    IViewPart view = page.findView( CHEATSHEET_VIEW_ID );
    if ( view == null ) {
      try {
        view = page.showView( CHEATSHEET_VIEW_ID );
      } catch ( PartInitException piExc ) {
        // The view could not be initialized... why?
      }
    }
    
    /*
     * The CheatSheetView class is eclipse-ui private, so we cannot
     * simply cast here, we use reflection to run the method...
     */
    try {
      Class< ? >[] params = new Class< ? >[ 1 ];
      params[ 0 ] = String.class;
      Method method = view.getClass().getDeclaredMethod( "setInput", params ); //$NON-NLS-1$
      method.invoke( view, this.cheatsheetID );
    } catch ( Exception exc ) {
      Activator.logException( exc );
    }
    
    page.activate( view );
    page.bringToTop( view );
  }

}
