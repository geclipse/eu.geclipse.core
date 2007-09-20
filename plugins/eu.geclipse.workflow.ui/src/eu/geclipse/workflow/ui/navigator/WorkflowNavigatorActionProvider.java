/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Initial development of the original code was made for the g-Eclipse project 
 * funded by European Union project number: FP6-IST-034327 
 * http://www.geclipse.eu/
 *  
 * Contributors:
 *     RUR (http://acet.rdg.ac.uk/)
 *     - Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.navigator;

import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPart;
import eu.geclipse.workflow.ui.part.WorkflowDiagramEditor;
import eu.geclipse.workflow.ui.part.WorkflowDiagramEditorPlugin;
import eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry;
import java.util.Iterator;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.part.FileEditorInput;

/**
 * @generated
 */
public class WorkflowNavigatorActionProvider extends CommonActionProvider {

  /**
   * @generated
   */
  private boolean myContribute;
  /**
   * @generated
   */
  private OpenDiagramAction myOpenDiagramAction;

  /**
   * @generated
   */
  @Override
  public void init( ICommonActionExtensionSite aSite ) {
    super.init( aSite );
    if( aSite.getViewSite() instanceof ICommonViewerWorkbenchSite ) {
      myContribute = true;
      makeActions( ( ICommonViewerWorkbenchSite )aSite.getViewSite() );
    } else {
      myContribute = false;
    }
  }

  /**
   * @generated
   */
  private void makeActions( ICommonViewerWorkbenchSite viewerSite ) {
    myOpenDiagramAction = new OpenDiagramAction( viewerSite );
  }

  /**
   * @generated
   */
  @Override
  public void fillActionBars( IActionBars actionBars ) {
    if( !myContribute ) {
      return;
    }
    IStructuredSelection selection = ( IStructuredSelection )getContext().getSelection();
    myOpenDiagramAction.selectionChanged( selection );
    if( myOpenDiagramAction.isEnabled() ) {
      actionBars.setGlobalActionHandler( ICommonActionConstants.OPEN,
                                         myOpenDiagramAction );
    }
  }

  /**
   * @generated
   */
  @Override
  public void fillContextMenu( IMenuManager menu ) {
  }
  /**
   * @generated
   */
  private class OpenDiagramAction extends Action {

    /**
     * @generated
     */
    private Diagram myDiagram;
    /**
     * @generated
     */
    private ICommonViewerWorkbenchSite myViewerSite;

    /**
     * @generated
     */
    public OpenDiagramAction( ICommonViewerWorkbenchSite viewerSite ) {
      super( "Open Diagram" );
      myViewerSite = viewerSite;
    }

    /**
     * @generated
     */
    public final void selectionChanged( IStructuredSelection selection ) {
      myDiagram = null;
      if( selection.size() == 1 ) {
        Object selectedElement = selection.getFirstElement();
        if( selectedElement instanceof WorkflowNavigatorItem ) {
          selectedElement = ( ( WorkflowNavigatorItem )selectedElement ).getView();
        } else if( selectedElement instanceof IAdaptable ) {
          selectedElement = ( ( IAdaptable )selectedElement ).getAdapter( View.class );
        }
        if( selectedElement instanceof Diagram ) {
          Diagram diagram = ( Diagram )selectedElement;
          if( WorkflowEditPart.MODEL_ID.equals( WorkflowVisualIDRegistry.getModelID( diagram ) ) )
          {
            myDiagram = diagram;
          }
        }
      }
      setEnabled( myDiagram != null );
    }

    /**
     * @generated
     */
    @Override
    public void run() {
      if( myDiagram == null || myDiagram.eResource() == null ) {
        return;
      }
      IEditorInput editorInput = getEditorInput();
      IWorkbenchPage page = myViewerSite.getPage();
      try {
        page.openEditor( editorInput, WorkflowDiagramEditor.ID );
      } catch( PartInitException e ) {
        WorkflowDiagramEditorPlugin.getInstance()
          .logError( "Exception while openning diagram", e );
      }
    }

    /**
     * @generated
     */
    private IEditorInput getEditorInput() {
      Resource diagramResource = myDiagram.eResource();
      for( Iterator it = diagramResource.getContents().iterator(); it.hasNext(); )
      {
        EObject nextEObject = ( EObject )it.next();
        if( nextEObject == myDiagram ) {
          return new FileEditorInput( WorkspaceSynchronizer.getFile( diagramResource ) );
        }
        if( nextEObject instanceof Diagram ) {
          break;
        }
      }
      return new URIEditorInput( diagramResource.getURI()
        .appendFragment( diagramResource.getURIFragment( myDiagram ) ) );
    }
  }
}
