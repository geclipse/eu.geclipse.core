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

import java.io.InputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
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
   * Create a temporary file that is hidden withing the grid project.
   * This is a first implementation that will change in the future so
   * do not rely on this method.
   * 
   * @param name The name of the file.
   * @param contents The content if the file.
   * @return The newly created file itself.
   * @throws CoreException
   */
  public IFile createTempFile( final String name, final InputStream contents ) throws CoreException;
  
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
