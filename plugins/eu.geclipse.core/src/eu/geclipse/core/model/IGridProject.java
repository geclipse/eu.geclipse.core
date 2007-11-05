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

package eu.geclipse.core.model;

import eu.geclipse.core.Messages;

/**
 * Grid element for projects in the grid model. These projects
 * have not necessarily to be Grid projects but can also be any
 * other type of project located in the workspace. If this is
 * really a grid project, i.e. if {@link #isGridProject()} returns
 * true, it contains a number of standard folders.
 */
public interface IGridProject extends IGridContainer {
  
  /**
   * The name of the mount directory contained in a grid project.
   */
  public static final String DIR_MOUNTS = Messages.getString( "IGridProject.dir_mounts" ); //$NON-NLS-1$

  /**
   * The name of the job description directory contained in a grid project.
   */
  public static final String DIR_JOBDESCRIPTIONS = Messages.getString( "IGridProject.dir_jobdescriptions" ); //$NON-NLS-1$
  
  /**
   * The name of the jobs directory contained in a grid project.
   */
  public static final String DIR_JOBS = Messages.getString( "IGridProject.dir_jobs" ); //$NON-NLS-1$
  
  /**
   * The name of the workflows directory contained in a grid project.
   */
  public static final String DIR_WORKFLOWS = Messages.getString( "IGridProject.dir_workflows" ); //$NON-NLS-1$
  
  public IGridContainer getProjectFolder( final Class< ? extends IGridElement > elementType );
  
  public IGridContainer getProjectFolder( final IGridElement element );
  
  /**
   * Get the virtual organisation that is associated with this project.
   * 
   * @return The associated {@link IVirtualOrganization}.
   */
  public IVirtualOrganization getVO();
  
  /**
   * Returns true of this is a Grid project and false if it is any
   * other type of project.
   * 
   * @return True if this is really a Grid project.
   */
  public boolean isGridProject();
  
  /**
   * Determine if this project is currently open.
   * 
   * @return True if this project is open, false otherwise.
   */
  public boolean isOpen();
  
}
