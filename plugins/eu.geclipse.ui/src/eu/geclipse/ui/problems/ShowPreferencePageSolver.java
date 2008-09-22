/******************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse consortium 
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

import eu.geclipse.core.reporting.IConfigurableSolver;


/**
 * This Solver opens the preferences window at the requested
 * properties page.
 */
public class ShowPreferencePageSolver
    implements IConfigurableSolver {
  
  private static final String PREFERENCE_PAGE_ID_ATTRIBUTE = "pageID"; //$NON-NLS-1$
  
  private String preferencePageID;
  
  /*
   * (non-Javadoc)
   * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement, java.lang.String, java.lang.Object)
   */
  public void setInitializationData( final IConfigurationElement config,
                                     final String propertyName,
                                     final Object data ) {
    this.preferencePageID = config.getAttribute( PREFERENCE_PAGE_ID_ATTRIBUTE );
  }
  
  /*
   * (non-Javadoc)
   * @see eu.geclipse.core.reporting.ISolver#solve()
   */
  public void solve() {
    
    PreferenceDialog dialog
      = PreferencesUtil.createPreferenceDialogOn( getShell(),
                                                  this.preferencePageID,
                                                  null,
                                                  null );
    
    Shell dialogShell = dialog.getShell();
    Shell currentShell = dialogShell.getDisplay().getActiveShell();
    Shell parentShell = currentShell;
    
    // Create the chain of shells to be closed for making the preference
    // dialog the top level component
    List< Shell > toClose = new ArrayList< Shell >();
    while ( ( parentShell != null ) && ( parentShell != dialogShell ) ) {
      toClose.add( parentShell );
      Composite parent = parentShell.getParent();
      if ( parent instanceof Shell ) {
        parentShell = ( Shell ) parent;
      } else {
        parentShell = null;
      }
    }
    
    // If the preference dialog is part of the chain close
    // all shells "above" the dialog
    if ( parentShell == dialogShell ) {
      for ( Shell shell : toClose ) {
        shell.close();
      }
    }
    
    dialogShell.forceActive();
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
