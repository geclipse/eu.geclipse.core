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
package eu.geclipse.ui.internal.actions;

import java.util.Iterator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.SelectionListenerAction;

import eu.geclipse.core.JobOutputsDownloader;
import eu.geclipse.core.model.IGridJob;
import eu.geclipse.ui.internal.Activator;

/**
 * Action, which allow to download output files for submitted job
 */
public class DownloadJobOutputsAction extends SelectionListenerAction {

  protected DownloadJobOutputsAction() {
    super( Messages.getString( "DownloadJobOutputsAction.actionName" ) ); //$NON-NLS-1$
    setImageDescriptor( Activator.getDefault()
      .getImageRegistry()
      .getDescriptor( Activator.IMG_DOWNLOAD_JOB_OUTPUT ) );
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    IStructuredSelection selection = getStructuredSelection();
    if( isAnyJobSelected( selection ) ) {
      for( Iterator<?> iterator = selection.iterator(); iterator.hasNext(); ) {
        Object object = iterator.next();
        if( object instanceof IGridJob ) {
          IGridJob job = ( IGridJob )object;
          JobOutputsDownloader downloader = new JobOutputsDownloader( job );
          downloader.setUser( true );
          downloader.schedule();
        }
      }
    }
    super.run();
  }

  private boolean isAnyJobSelected( final IStructuredSelection selection ) {
    return selection != null && !selection.isEmpty();
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    return isAnyJobSelected( selection );
  }
}
