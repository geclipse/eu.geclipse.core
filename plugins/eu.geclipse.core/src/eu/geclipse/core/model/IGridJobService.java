/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *    Pawel Wolniewicz
 *****************************************************************************/

package eu.geclipse.core.model;

import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.reporting.ProblemException;

/**
 * TODO pawel
 */
public interface IGridJobService extends IGridService{

  /**
   * TODO pawel
   * 
   * @param description TODO pawel
   * @param monitor progress monitor
   * @return TODO pawel
   * @throws ProblemException TODO pawel
   * @throws GridModelException 
   */
  public IGridJobID submitJob( final IGridJobDescription description,
                               final IProgressMonitor monitor )
    throws ProblemException, GridModelException;
  
  /**
   * Download job status from server
   * 
   * @param id of job, which status will be checked on server
   * @param progressMonitor may be <code>null</code>
   * @return current job status
   * @throws ProblemException thrown when status cannot be downloaded/checked
   */
  public IGridJobStatus getJobStatus( final IGridJobID id, final IProgressMonitor progressMonitor ) throws ProblemException;
  
  /**
   * Deletes job from server and release resources on server
   * @param job job identifier, which has to be deleted
   * @param monitor 
   * @throws ProblemException
   */
  public void deleteJob( final IGridJob job, IProgressMonitor monitor ) throws ProblemException;

  /**
   * @param desc
   * @return true if passed job description may be submitted to grid using <code>this</code> service
   */
  public boolean canSubmit(final IGridJobDescription desc);

//  /**
//   * TODO pawel
//   * 
//   * @param description TODO pawel
//   * @param destination TODO pawel
//   * @return TODO pawel
//   * @throws GridModelException TODO pawel
//   */
//  public IGridJobID submitJob( final IGridJobDescription description, final String destination )
//    throws GridException;
  
}
