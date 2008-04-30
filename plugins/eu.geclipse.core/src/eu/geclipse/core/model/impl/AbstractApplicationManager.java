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

package eu.geclipse.core.model.impl;

import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.model.IGridApplication;
import eu.geclipse.core.model.IGridApplicationManager;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Abstract implementation of the {@link IGridApplicationManager} interfaces
 * that should be used by middleware-specific implementations rather than
 * the interface itself. Note that each VO should only have one application
 * manager.
 */
public abstract class AbstractApplicationManager
    implements IGridApplicationManager {
  
  /**
   * The manager's associated VO.
   */
  private IVirtualOrganization vo;
  
  /**
   * Create a new application manager for the specified VO.
   * 
   * @param vo The VO this application manager is instantiated for.
   */
  public AbstractApplicationManager( final IVirtualOrganization vo ) {
    this.vo = vo;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridApplicationManager#getApplications(eu.geclipse.core.model.IGridComputing, org.eclipse.core.runtime.IProgressMonitor)
   */
  public IGridApplication[] getApplications( final IGridComputing computing,
                                             final IProgressMonitor monitor )
      throws ProblemException {
    IGridApplication[] result = null;
    IGridInfoService infoService = this.vo.getInfoService();
    if ( infoService != null ) {
      result = infoService.fetchApplications( this.vo, this.vo, computing, null );
    }
    return result;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridApplicationManager#getVo()
   */
  public IVirtualOrganization getVo() {
    return this.vo;
  }
  
}
