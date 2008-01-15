/******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium
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
 *     Sylva Girtelschmid - JKU
 *****************************************************************************/
package eu.geclipse.ui.internal.actions;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.SelectionListenerAction;

import eu.geclipse.core.CoreProblems;
import eu.geclipse.core.GridException;
import eu.geclipse.core.model.IGridVisualisation;
import eu.geclipse.ui.dialogs.NewProblemDialog;


/**
 * @author sgirtel
 *
 */
public class RenderRemoteVTKPipelineAction extends SelectionListenerAction {

  private IWorkbenchSite site;
  private ArrayList< IGridVisualisation > vis;

  protected RenderRemoteVTKPipelineAction( final String text ) {
    super( text );
    // TODO Auto-generated constructor stub
  }

  /**
   * @param site
   */
  public RenderRemoteVTKPipelineAction( final IWorkbenchPartSite site ) {
    super( Messages.getString( "RenderRemoteVTKPipelineAction.title" ) ); //$NON-NLS-1$
    this.site = site;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    //TODO - open GVid view enabled and start job submission wizard
    Object element
      = getStructuredSelection().getFirstElement();
    if ( ( element != null ) && checkForPipelineCompletion( element ) ) {
      try {
        this.site.getPage().showView( "eu.geclipse.gvid.views.GVidView" ); //$NON-NLS-1$
        //TODO make a job that executes the rendering remotely and creates a client
        //that intercepts the steaming data locally
      } catch( PartInitException e ) {
        NewProblemDialog.openProblem( null,
                                      Messages.getString( "RenderLocalVTKPipelineAction.errorDialogTitle" ), //$NON-NLS-1$
                                      Messages.getString( "RenderLocalVTKPipelineAction.errorOpeningView"), //$NON-NLS-1$
                                      e );
      }
    }
    else {
      final GridException fileException = new GridException( CoreProblems.FILE_ACCESS_PROBLEM,
                                                             Messages.getString( "RenderLocalVTKPipelineAction.elementNotVisualizable" ) ); //$NON-NLS-1$

          NewProblemDialog.openProblem( null,
                                        Messages.getString( "RenderLocalVTKPipelineAction.errorDialogTitle" ), //$NON-NLS-1$
                                        Messages.getString( "RenderLocalVTKPipelineAction.errorInfo" ), //$NON-NLS-1$
                                        fileException );
    }
  }

  private boolean checkForPipelineCompletion( @SuppressWarnings("unused")
  final Object element ) {
    //TODO - validate that the .vtkpipeline file specifies at least the data location,
    //        appropriate reader for the data type and instruction about how to render
    //        the data (i.e. most likely one or more filters' specifications)
    return true;
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    this.vis = new ArrayList<IGridVisualisation>();
    boolean enabled = super.updateSelection( selection );
    Iterator<?> iter = selection.iterator();
    while( iter.hasNext() && enabled ) {
      Object element = iter.next();
      boolean isVisualizableFile = isVisualizable( element );
      enabled &= isVisualizableFile;
      if( isVisualizableFile ) {
        this.vis.add( ( IGridVisualisation )element );
      }
    }
    return enabled && !this.vis.isEmpty();
  }

  protected boolean isVisualizable( final Object element ) {
    return element instanceof eu.geclipse.core.model.IGridVisualisation;
  }
}
