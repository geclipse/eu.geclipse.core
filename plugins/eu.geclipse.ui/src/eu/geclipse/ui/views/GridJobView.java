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

package eu.geclipse.ui.views;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElementManager;
import eu.geclipse.core.model.IJobManager;
import eu.geclipse.ui.providers.JobViewLabelProvider;

/**
 * Job view that shows all jobs that are currently managed by
 * the default implementation of the {@link IJobManager}
 * interface
 */
public class GridJobView extends ElementManagerViewPart {

  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridElementManagerViewPart#getManager()
   */
  @Override
  protected IGridElementManager getManager() {
    return GridModel.getJobManager();
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.ui.views.GridModelViewPart#createLabelProvider()
   */
  @Override
  protected IBaseLabelProvider createLabelProvider() {
    return new JobViewLabelProvider();
  }
  
  protected boolean createTreeColumns( final Tree tree ) {
    
    super.createTreeColumns( tree );
    
    TreeColumn idColumn = new TreeColumn( tree, SWT.NONE );
    idColumn.setText( "ID" );
    idColumn.setAlignment( SWT.CENTER );
    idColumn.setWidth( 200 );
    
    TreeColumn statusColumn = new TreeColumn( tree, SWT.NONE );
    statusColumn.setText( "Status" );
    statusColumn.setAlignment( SWT.CENTER );
    statusColumn.setWidth( 100 );
    
    return true;
    
  }

}
