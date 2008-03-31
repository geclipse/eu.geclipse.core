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
 *    Pawel Wolniewicz
 *****************************************************************************/
package eu.geclipse.core.model;

/**
 * Specialised interface for the creation of jobs for the grid. Offers also the
 * possibility for submitting the job.
 */
public interface IGridJobCreator extends IGridElementCreator {

  /**
   * Test if this creator can create a job from the specified job description.
   * 
   * @param description The description from which to create the job.
   * @return True if a job can be created from the specified description.
   */
  public boolean canCreate( final IGridJobDescription description );

  /**
   * Creates GridJob and add this to GridProjectView. This method is called
   * after successfull job submission.
   * 
   * @param parent - The porent node in GridProjectView
   * @param id - Identifier of job
   * @param jobName - name under which job should be stored in the workspace
   * @throws GridModelException
   */
  public void create( final IGridContainer parent, IGridJobID id, final String jobName )
    throws GridModelException;
}
