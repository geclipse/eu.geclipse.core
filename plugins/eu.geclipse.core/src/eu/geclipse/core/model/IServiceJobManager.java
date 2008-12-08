/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium 
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
 *     PSNC:
 *      - Szymon Mueller    
 *****************************************************************************/
package eu.geclipse.core.model;

import java.util.List;

/**
 * Interface class for manager of the service jobs.
 *
 */
public interface IServiceJobManager extends IGridElementManager {

  /**
   * Returns all service jobs registered in the manager.
   * 
   * @return List of {@link IServiceJob}s which are managed.
   */
  public List<IServiceJob> getServiceJobs();

  /**
   * Adds specified service job to list of managed service job's of this
   * manager.
   * 
   * @param serviceJob Service job to be added to this manager.
   */
  public void addServiceJob( IServiceJob serviceJob );

  /**
   * Returns {@link IServiceJob} with the specified name in the specified
   * project.
   * 
   * @param name Unique service job name in the <code>project</code>.
   * @param project {@link IGridProject} which should be searched for specified
   *          service job.
   * @return Service job instance or null if service job with specified
   *         <code>name</code> does not exist in the <code>project</code>.
   */
  public IServiceJob getServiceJob( final String name, final IGridProject project );

  /**
   * Adds new status listener to this manager. Listener should be notified each
   * time a service job's status changes in the manager.
   * 
   * @param listener {@link IServiceJobStatusListener} to be registered.
   */
  public void addServiceJobStatusListener( IServiceJobStatusListener listener );
}
