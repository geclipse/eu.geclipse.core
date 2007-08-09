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

import org.eclipse.swt.widgets.Composite;

import eu.geclipse.core.model.IGridJob;

/**
 * Single element containing detail information about submitted job
 */
public interface IJobDetail {

  /**
   * @return section, in which detail should be shown. Section should be
   *         obtained using {@link JobDetailSectionsManager#getSection(Integer)}
   */
  IJobDetailsSection getSection();  
  
  /**
   * @return detail identifier unique within {@link IJobDetailsSection}
   * <p>Just return detail name if you know, that detail has unique
   *         name in {@link IJobDetailsSection}, in which it's placed. If you
   *         cannot assure name is unique in section, then return something
   *         unique within section (see GliteJobDetailsFactory.createStatusHistoryItem). 
   *         <P>
   *         Two {@link IJobDetail} objects with the same identifier in the same
   *         {@link IJobDetailsSection} share created widgets (to avoid
   *         flickering).
   */
  String getId();

  /**
   * @param gridJob job, for which details are refreshed
   * @param parent composite, on which widgets showing details should be created
   * @param viewConfiguration configuration of current view
   * @return true if refreshed value is defined (not empty). If all details in
   *         section are empty, then section is hidden
   */
  boolean refresh( final IGridJob gridJob,
                   final Composite parent,
                   final IViewConfiguration viewConfiguration );

  /**
   * Use widgets created in oldDetail. Used when view is refreshed to avoid
   * blinking.
   * 
   * @param oldDetail detail used previously to show detail. Old detail has same
   *            name and section as this detail
   */
  void reuseWidgets( IJobDetail oldDetail );

  /**
   * Dispose created widgets (also reused widgets)
   */
  void dispose();
}
