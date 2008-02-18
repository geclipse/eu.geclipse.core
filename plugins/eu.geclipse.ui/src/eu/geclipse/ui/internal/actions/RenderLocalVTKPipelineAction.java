/******************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse consortium
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
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.SelectionListenerAction;

import eu.geclipse.core.ICoreProblems;
import eu.geclipse.core.model.IGridVisualisation;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;
import eu.geclipse.ui.views.VisualisationView;


/**
 * @author sgirtel
 *
 */
public class RenderLocalVTKPipelineAction extends SelectionListenerAction {

  private IWorkbenchSite site;

  private ArrayList< IGridVisualisation > vis;

  protected RenderLocalVTKPipelineAction( final String text ) {
    super( text );
    // TODO Auto-generated constructor stub
  }

  /**
   * @param site
   */
  public RenderLocalVTKPipelineAction( final IWorkbenchPartSite site ) {
    super ( Messages.getString( "RenderLocalVTKPipelineAction.title" ) ); //$NON-NLS-1$
    this.site = site;
  }


  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    Object element
      = getStructuredSelection().getFirstElement();
    if ( ( element != null ) && ((IGridVisualisation) element).isValid() ) {
      try {
        IViewPart view = this.site.getPage().showView( "eu.geclipse.ui.views.visualisationview" ); //$NON-NLS-1$
        view.setFocus();
        ( ( VisualisationView )view ).setPipeline( (IGridVisualisation) element, "local" ); //$NON-NLS-1$
        ( ( VisualisationView )view ).render();
      } catch( PartInitException e ) {
        ProblemDialog.openProblem( null,
               Messages.getString( "RenderLocalVTKPipelineAction.errorDialogTitle" ), //$NON-NLS-1$
               Messages.getString( "RenderLocalVTKPipelineAction.errorOpeningView"), //$NON-NLS-1$
               e );
      }
    }
    else {
      // TODO: check, we have here a very general IO error, is this right??
      final ProblemException fileException = new ProblemException( ICoreProblems.IO_OPERATION_FAILED,
                Messages.getString( "RenderLocalVTKPipelineAction.elementNotVisualizable" ), //$NON-NLS-1$
                Activator.PLUGIN_ID );
      
      ProblemDialog.openProblem( null,
          Messages.getString( "RenderLocalVTKPipelineAction.errorDialogTitle" ), //$NON-NLS-1$
          Messages.getString( "RenderLocalVTKPipelineAction.errorInfo" ), //$NON-NLS-1$
          fileException );
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection
   * (org.eclipse.jface.viewers.IStructuredSelection)
   */
  @Override
  protected boolean updateSelection( final IStructuredSelection selection ) {
    this.vis = new ArrayList< IGridVisualisation >();
    boolean enabled = super.updateSelection( selection );
    Iterator< ? > iter = selection.iterator();
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
