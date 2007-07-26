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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import eu.geclipse.core.model.IGridJob;

/**
 * Interface for section in job details view
 */
interface ISection {

  /**
   * Creates all widgets for visualisation of section
   * 
   * @param parentComposite
   * @param formToolkit
   */
  void createWidgets( final Composite parentComposite,
                      final FormToolkit formToolkit );

  /**
   * Refresh values showed in this section
   * 
   * @param gridJob - object containg job data
   */
  void refresh( final IGridJob gridJob );
}
