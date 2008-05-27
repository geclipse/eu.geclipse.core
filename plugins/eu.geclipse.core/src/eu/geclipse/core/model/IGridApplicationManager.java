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
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core.model;

import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.model.impl.AbstractApplicationManager;
import eu.geclipse.core.reporting.ProblemException;

/**
 * An application manager is responsible for installing and
 * uninstalling applications for a specific VO. Middleware-specific
 * implementations should rather extend {@link AbstractApplicationManager}
 * than implementing this interface-
 */
public interface IGridApplicationManager
    extends IGridElement {

  /**
   * Get all known applications for the specified {@link IGridComputing}.
   * If computing is <code>null</code> all applications are returned that
   * are defined on any computing for the associated VO.
   * 
   * @param computing The {@link IGridComputing} that should be queried
   * for available applications. If may be <code>null</code>. In this case
   * all computings of the associated VO are queried for available
   * applications.
   * @param monitor A {@link IProgressMonitor} to monitor the progress of
   * this operations. 
   * @return All available applications for the specified {@link IGridComputing}
   * or the VO itself.
   * @throws ProblemException If an error occurs while fetching the
   * applications. 
   */
  public IGridApplication[] getApplications( final IGridComputing computing,
                                             final IProgressMonitor monitor )
      throws ProblemException;

  /**
   * Get the VO this manager is associated with.
   * 
   * @return This manager's VO.
   */
  public IVirtualOrganization getVo();
  
  /**
   * Install the application represented by the specified
   * {@link IGridInstallParameters}.
   * 
   * @param parameters The {@link IGridInstallParameters} describing the
   * application to be installed.
   * @param monitor A {@link IProgressMonitor} to monitor the progress of
   * this operations.
   * @throws ProblemException If an error occurs while installing the
   * applications.
   */
  public void install( final IGridInstallParameters parameters,
                       final IProgressMonitor monitor )
      throws ProblemException;
  
  /**
   * Uninstall the specified application.
   * 
   * @param application The application to be uninstalled.
   * @param monitor A {@link IProgressMonitor} to monitor the progress of
   * this operations.
   * @throws ProblemException If an error occurs while uninstalling the
   * application.
   */
  public void uninstall( final IGridApplication application,
                        final IProgressMonitor monitor )
      throws ProblemException;
  
  
  /**validate the installed software
   * @param application The application to validate
   * @param monitor A {@link IProgressMonitor} to monitor the progress of
   * this operations.
   * @throws ProblemException ProblemException If an error occurs while validating the
   * application.
   */
  public void validate( final IGridApplication application,
                         final IProgressMonitor monitor )
       throws ProblemException;
}
