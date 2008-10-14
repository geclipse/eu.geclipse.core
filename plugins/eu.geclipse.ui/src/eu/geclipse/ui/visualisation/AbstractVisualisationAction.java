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
package eu.geclipse.ui.visualisation;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.SelectionListenerAction;

import eu.geclipse.core.model.IGridVisualisation;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.actions.Messages;
import eu.geclipse.ui.views.VisualisationView;


/**
 * @author sgirtel
 *
 */
public abstract class AbstractVisualisationAction extends SelectionListenerAction {


  /**
   * Extension point which has to be implemented to provide additional actions for
   * visualisable elements (i.e. elements that extend the IGridVisualisation interface).
   */
  public static final String ACTION_EXTENSION_POINT = "eu.geclipse.ui.visualisationaction"; //$NON-NLS-1$

  /**
   * Name of the attribute which specifies the class name of the AbstractVisualisationAction
   * implementation.
   */
  public static final String EXT_ACTION_CLASS = "class"; //$NON-NLS-1$

  /**
   * The name of the element which contains the visualisation action specification.
   */
  public static final String EXT_ACTION_ELEMENT = "action"; //$NON-NLS-1$

  /**
   * Name of the attribute which specifies the text for the action.
   */
  public static final String EXT_ACTION_TEXT = "text"; //$NON-NLS-1$

  /**
   * Name of the attribute which specifies the option text for the action's tool-tip.
   */
  public static final String EXT_ACTION_TOOLTIP = "tooltip"; //$NON-NLS-1$

  /**
   * The file extension of the resource that this action will become active for.
   */
  public static final String EXT_ACTION_FILE_EXTENSION = "fileExtension"; //$NON-NLS-1$

  private String fileExt = null;

  private ArrayList< IGridVisualisation > vis;

  private IWorkbenchSite workbenchSite;

  protected AbstractVisualisationAction() {
    super( Messages.getString( "AbstractVisualisationAction.title" ) ); //$NON-NLS-1$
  }

  /**
   * @param actionText
   * @param actionTooltip
   * @param fileExtension
   * @param site
   */
  public void init( final String actionText,
                    final String actionTooltip,
                    final String fileExtension,
                    final IWorkbenchSite site ) {
    super.setText( actionText );
    super.setToolTipText( actionTooltip );
    this.fileExt = fileExtension;
    this.workbenchSite = site;
  }

  protected boolean isVisualizable( final Object element ) {
    return element instanceof eu.geclipse.core.model.IGridVisualisation;
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.action.Action#run()
   */
  @Override
  public void run() {
    Object element = getStructuredSelection().getFirstElement();
    if( element != null ) {
      try {
        ( ( IGridVisualisation )element ).validate();
        IViewPart view = this.workbenchSite.getPage().showView( "eu.geclipse.ui.views.visualisationview" ); //$NON-NLS-1$
        ( ( VisualisationView )view ).setVisResource( ( IGridVisualisation )element );
        ( ( VisualisationView )view ).render( ( ( IGridVisualisation )element).getResourceFileNameExtension() );
        view.setFocus();
      } catch( PartInitException pie ) {
        ProblemDialog.openProblem( null,
                                   Messages.getString( "AbstractVisualisationAction.errorDialogTitle" ), //$NON-NLS-1$
                                   Messages.getString( "AbstractVisualisationAction.errorOpeningView" ), //$NON-NLS-1$
                                   pie );
      } catch( ProblemException pe ) {
        ProblemDialog.openProblem( null,
                                   Messages.getString( "AbstractVisualisationAction.errorDialogTitle" ), //$NON-NLS-1$
                                   Messages.getString( "AbstractVisualisationAction.elementNotVisualizable" ), //$NON-NLS-1$
                                   pe );
      }
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

  /**
   * @return
   */
  public String getFileExt() {
    return this.fileExt;
  }
}
