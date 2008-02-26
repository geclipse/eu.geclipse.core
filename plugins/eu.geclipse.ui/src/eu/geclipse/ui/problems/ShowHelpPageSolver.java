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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;

import eu.geclipse.core.reporting.IConfigurableSolver;


/**
 * This Solver opens the help browser at the requested help resource.
 */
public class ShowHelpPageSolver implements IConfigurableSolver {
  
  private static final String HELP_PAGE_ID_ATTRIBUTE = "pagePath"; //$NON-NLS-1$
  
  private String helpPageHRef;
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
   */
  public void setInitializationData( final IConfigurationElement config,
                                     final String propertyName,
                                     final Object data)
      throws CoreException {
    this.helpPageHRef = config.getAttribute( HELP_PAGE_ID_ATTRIBUTE );
  }
  
  /*
   * (non-Javadoc)
   * @see eu.geclipse.core.reporting.ISolver#solve()
   */
  public void solve() {
    
    try {
      IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
      helpSystem.displayHelpResource( this.helpPageHRef );
    } catch ( Exception exc ) {
      // Ignore, no workbench available or no UI thread
    }
  }

}
