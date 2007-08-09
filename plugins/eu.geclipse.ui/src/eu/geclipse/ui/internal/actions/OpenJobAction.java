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

package eu.geclipse.ui.internal.actions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import eu.geclipse.core.model.IGridJob;
import eu.geclipse.ui.views.jobdetailsNEW.JobDetailsView;

/**
 * Dedicated action for opening {@link IGridJob}s.
 */
public class OpenJobAction
    extends BaseSelectionListenerAction {
  
  /**
   * The workbench page this action belongs to.
   */
  private IWorkbenchPage workbenchPage;

  /**
   * Construct a new open job action for the specified workbench page.
   * 
   * @param workbenchPage The {@link IWorkbenchPage} for which to 
   * create this action.
   */
  protected OpenJobAction( final IWorkbenchPage workbenchPage ) {
    super( Messages.getString("OpenJobAction.open_job_action_test") ); //$NON-NLS-1$
    this.workbenchPage = workbenchPage;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    Object element
      = getStructuredSelection().getFirstElement();
    if ( ( element != null ) && ( element instanceof IGridJob ) ) {
      try {
        this.workbenchPage.showView( JobDetailsView.ID,
                                     null,
                                     IWorkbenchPage.VIEW_ACTIVATE );
      } catch( PartInitException e ) {
        // Just ignore this exception and do not open the job
      }
    }
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    return
      ( selection.size() == 1 )
      && ( selection.getFirstElement() instanceof IGridJob );
  }
  
}
