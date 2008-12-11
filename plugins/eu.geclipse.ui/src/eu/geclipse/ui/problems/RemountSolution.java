/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
 *    Szymon Mueller - initial API and implementation
 *****************************************************************************/
package eu.geclipse.ui.problems;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.model.IMountable;
import eu.geclipse.core.model.IMountable.MountPointID;
import eu.geclipse.core.reporting.ISolution;
import eu.geclipse.ui.internal.actions.MountAction;


/**
 * Solution for "Mount with this name already exists" problem. It opens new
 * connection wizard for the same storage element. User then can easily change
 * mount name to create a connection.
 *
 */
public class RemountSolution implements ISolution {

  private MountPointID mountID = null;
  private IMountable mountable = null;
  
  /**
   * Constructor. Parameters should not be null.
   * @param mountable The {@link IMountable}, for which the mount failed.
   * @param mountID The {@link MountPointID} of the mounted element.
   */
  public RemountSolution( final IMountable mountable, final MountPointID mountID ) {
    this.mountable = mountable;
    this.mountID = mountID;
  }
  
  public String getDescription() {
    return Messages.getString("RemountSolution.rename_connection"); //$NON-NLS-1$
  }

  public String getID() {
    return "rename_mount_connection"; //$NON-NLS-1$
  }

  public boolean isActive() {
    return true;
  }

  public void solve() throws InvocationTargetException {
    IWorkbench workbench = PlatformUI.getWorkbench();
    IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
    Shell shell = null;
    if( window != null
        && this.mountID != null
        && this.mountable != null )
    {
      shell = window.getShell();
      IMountable[] toMount = new IMountable[]{ this.mountable };
      MountAction action = new MountAction( shell,
                                            toMount,
                                            this.mountID,
                                            true );
      action.run();
    }
    
  }

}
