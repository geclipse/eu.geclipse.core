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
 *    Mathias Stuempert
 *****************************************************************************/

package eu.geclipse.ui.problems;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import eu.geclipse.core.reporting.IConfigurableSolver;


/**
 * This Solver opens a wizard at the requested wizard page.
 */
public class ShowWizardPageSolver
    implements IConfigurableSolver {
  
  private static final String PAGE_ID_ATTRIBUTE = "pageID"; //$NON-NLS-1$
  
  private String pageID;
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
   */
  public void setInitializationData( final IConfigurationElement config,
                                     final String propertyName,
                                     final Object data )
      throws CoreException {
    this.pageID = config.getAttribute( PAGE_ID_ATTRIBUTE );
  }
  
  /*
   * (non-Javadoc)
   * @see eu.geclipse.core.reporting.ISolver#solve()
   */
  public void solve() throws InvocationTargetException {
    // TODO mathias
  }

}
