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
package eu.geclipse.ui.views.jobdetailsNEW;

import java.util.List;

import eu.geclipse.core.model.IGridJob;

/**
 *
 */
public interface IJobDetailsFactory {

  /**
   * Returns details, which potentially can be obtained from passed gridJob.
   * Potentially means, it's possible to get this value from passed gridJob, but
   * returned value can be null.
   * 
   * @param gridJob job, for which details should be returned
   * @param sectionManager manages sections, on which details are grouped
   * @return All details which can be shown for passed gridJob
   */
  List<IJobDetail> getDetails( final IGridJob gridJob,
                               final JobDetailSectionsManager sectionManager );
}
