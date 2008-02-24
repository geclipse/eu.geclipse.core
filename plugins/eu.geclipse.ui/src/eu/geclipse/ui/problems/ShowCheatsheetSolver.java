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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.cheatsheets.CheatSheetViewerFactory;
import org.eclipse.ui.cheatsheets.ICheatSheetViewer;

import eu.geclipse.core.reporting.IConfigurableSolver;


/**
 * This Solver takes care of opening the Cheatsheet viewer at the
 * requested cheatsheet ID.
 */
public class ShowCheatsheetSolver implements IConfigurableSolver {
  
  public static final String CHEATSHEET_ID_ATTRIBUTE = "cheatsheetID"; //$NON-NLS-1$
  
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
    ICheatSheetViewer viewer = CheatSheetViewerFactory.createCheatSheetView();
    viewer.setInput( this.cheatsheetID );
    viewer.reset( null );
    viewer.createPartControl( getContainer() );
    viewer.setFocus();
  }
  
  private Composite getContainer() {
    Composite container = null;
    
    // TODO ariel
    
    return container;
  }

}
