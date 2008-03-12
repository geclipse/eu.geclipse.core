/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.workflow.resources;

import eu.geclipse.core.model.IGridWorkflowJob;
import eu.geclipse.workflow.IWorkflowJob;


/**
 * Wrapper to emf implementation
 */
public class GridWorkflowJob implements IGridWorkflowJob {
  private IWorkflowJob jobImpl;

  GridWorkflowJob( final IWorkflowJob jobImpl ) {
    super();
    this.jobImpl = jobImpl;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridWorkflowJob#getDescription()
   */
  public String getDescription() { 
    return this.jobImpl.getJobDescription();
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridWorkflowJob#getName()
   */
  public String getName() {
    return this.jobImpl.getName();
  }
}
