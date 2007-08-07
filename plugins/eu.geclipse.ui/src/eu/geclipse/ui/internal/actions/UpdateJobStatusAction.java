/*****************************************************************************
 * Copyright (c) 2007 g-Eclipse Consortium 
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
 *    Szymon Mueller - initial API and implementation
 *****************************************************************************/
package eu.geclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.SelectionListenerAction;

import eu.geclipse.core.jobs.GridJob;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridJob;

/**
 * Action for toggling refresh for selected job
 */
public class UpdateJobStatusAction extends SelectionListenerAction {

  private ArrayList< IGridJob > selectedJobs = new ArrayList< IGridJob >();

  protected UpdateJobStatusAction() {
    super( Messages.getString( "UpdateJobStatusAction.title" ) ); //$NON-NLS-1$
  }

  @Override
  public void run() {
    if( this.selectedJobs.size() > 0 ) {
      GridModel.getJobManager().updateJobsStatus( this.selectedJobs );
    }
  }

  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    this.selectedJobs.clear();
    for( Iterator<?> iterator = selection.iterator(); iterator.hasNext(); ) {
      Object element = iterator.next();
      if( element instanceof GridJob ) {
        this.selectedJobs.add( ( GridJob )element );
      }
    }
    
    
    
    if( this.selectedJobs.size() > 0 ) {
      if( !this.isEnabled() ) {
        this.setEnabled( true );
      }
    } else {
      this.setEnabled( false );
    }
    return super.updateSelection( selection ) && ( this.selectedJobs.size() > 0 );
  }
}
