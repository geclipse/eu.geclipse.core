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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/

package eu.geclipse.ui.views.jobdetails;

import java.util.List;

import org.eclipse.swt.widgets.Composite;

import eu.geclipse.core.model.IGridJob;

/**
 * Section containing grouped details of submitted job
 */
public interface IJobDetailsSection {

  /**
   * Refresh all items in section using gridJob. Also creates section widgets if
   * haven't created yet.
   * 
   * @param gridJob refreshed job
   * @param parent parent, on which widgets should be created
   */
  void refresh( final IGridJob gridJob,
                final Composite parent );

  /**
   * @return position, on which section will be visibled in
   *         {@link JobDetailsView}
   */
  int getOrder();

  /**
   * Puts detail to section
   * 
   * @param detail
   */
  void addDetail( IJobDetail detail );

  /**
   * Removes detail from section
   * 
   * @param detail
   */
  void removeDetail( IJobDetail detail );

  /**
   * Disposes widgets created in section
   */
  void dispose();
  
  /**
   * @return details currently shown in section
   */
  List<IJobDetail> getDetails();
  
  /**
   * @return the job currently shown in view
   */
  IGridJob getInputJob();
  
  /**
   * @return current configuration of Job Details View
   */
  IViewConfiguration getViewConfiguration();
}
