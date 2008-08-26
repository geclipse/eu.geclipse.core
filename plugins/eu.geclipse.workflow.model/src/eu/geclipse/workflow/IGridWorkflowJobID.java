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
package eu.geclipse.workflow;

import java.util.List;

import eu.geclipse.core.model.IGridJobID;


/**
 * Job ID for jobs, which are workflow
 */
public interface IGridWorkflowJobID extends IGridJobID {
  /**
   * @return list of jobs id, which are part of workflow.
   * May return null, if job has no childs
   */
  List<IGridWorkflowJobID> getChildrenJobs();
  
  /**
   * @return name of job returned from middleware
   */
  String getName();
}
