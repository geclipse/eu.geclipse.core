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

package eu.geclipse.ui.providers;

import org.eclipse.jface.viewers.ITableLabelProvider;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.core.model.IGridJobStatus;
import eu.geclipse.ui.views.GridJobView;

/**
 * This is a specialized {@link GridModelLabelProvider} to be used
 * by the {@link GridJobView}. It additionally implements the
 * {@link ITableLabelProvider} to give the user more information
 * about his jobs.
 */
public class JobViewLabelProvider
    extends ElementManagerLabelProvider {

  @Override
  protected String getColumnText( final IGridElement element,
                                  final int columnIndex ) {
    String text = ""; //$NON-NLS-1$
    if ( element instanceof IGridJob ) {
      IGridJob job = ( IGridJob ) element;
      switch ( columnIndex ) {
        case 2:
          text = job.getID().getJobID();
          break;
        case 3:
          IGridJobStatus status = job.getJobStatus();
          if ( status != null ) {
            text = status.getName();
          }
          break;
      }
    }
    return text;
  }
  
}
