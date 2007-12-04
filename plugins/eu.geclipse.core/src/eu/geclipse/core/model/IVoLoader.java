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

import java.net.URI;

import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.GridException;

/**
 * Definition of a VO loader. A VO loader is a
 * mechanism to import VOs from a remote or local repository.
 */
public interface IVoLoader {
  
  /**
   * Get the VO with the specified name from the specified
   * <code>URI</code>. The <code>URI</code> may be a remote or local
   * repository or file.
   * 
   * @param uri The location from which to import the VO.
   * @param name The name of the VO.
   * @param monitor A {@link IProgressMonitor} that is used to
   * indicate the progress of the import operation.
   * @return The imported VO or <code>null</code> if no
   * VO could be imported from the specified location.
   * @throws GridException If an error occurs while the
   * VO is imported.
   */
  public IVirtualOrganization getVo( final URI uri,
                                     final String name,
                                     final IProgressMonitor monitor )
    throws GridException;
  
  /**
   * Get a list of VO names that are found at the specified
   * <code>URI</code>. The returned names can be used in combination with
   * the <code>URI</code> to finally retrieve a VO with
   * {@link #getVo(URI, String, IProgressMonitor)}.
   * 
   * @param uri The location to be queried for available VOs.
   * @param monitor A progress monitor for monitoring the progress of
   * this operation.
   * @return A list of VO names that could be found at the
   * specified location. This may be <code>null</code> if no VOs
   * could be found.
   * @throws GridException If an error occurs while querying the specified
   * location.
   * @see #getVo(URI, String, IProgressMonitor)
   */
  public String[] getVoList( final URI uri,
                             final IProgressMonitor monitor )
    throws GridException; 
  
  /**
   * Get a list of predefined import location for this VO
   * loader.
   * 
   * @return The list of predefined VO repositories.
   */
  public URI[] getPredefinedURIs();

}
