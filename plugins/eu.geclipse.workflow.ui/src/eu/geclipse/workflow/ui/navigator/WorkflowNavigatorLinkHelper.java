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

import java.util.Iterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.navigator.ILinkHelper;
import org.eclipse.ui.part.FileEditorInput;
import eu.geclipse.workflow.ui.part.WorkflowDiagramEditorPlugin;

/**
 * @generated
 */
public class WorkflowNavigatorLinkHelper implements ILinkHelper {

  /**
   * @generated
   */
  private static IEditorInput getEditorInput( Diagram diagram ) {
    Resource diagramResource = diagram.eResource();
    for( Iterator it = diagramResource.getContents().iterator(); it.hasNext(); )
    {
      EObject nextEObject = ( EObject )it.next();
      if( nextEObject == diagram ) {
        return new FileEditorInput( WorkspaceSynchronizer.getFile( diagramResource ) );
      }
      if( nextEObject instanceof Diagram ) {
        break;
      }
    }
    return new URIEditorInput( EcoreUtil.getURI( diagram ) );
  }

  /**
   * @generated
   */
  public IStructuredSelection findSelection( IEditorInput anInput ) {
    IDiagramDocument document = WorkflowDiagramEditorPlugin.getInstance()
      .getDocumentProvider()
      .getDiagramDocument( anInput );
    if( document == null ) {
      return StructuredSelection.EMPTY;
    }
    Diagram diagram = document.getDiagram();
    IFile file = WorkspaceSynchronizer.getFile( diagram.eResource() );
    if( file != null ) {
      WorkflowNavigatorItem item = new WorkflowNavigatorItem( diagram,
                                                              file,
                                                              false );
      return new StructuredSelection( item );
    }
    return StructuredSelection.EMPTY;
  }

  /**
   * @generated
   */
  public void activateEditor( IWorkbenchPage aPage,
                              IStructuredSelection aSelection )
  {
    if( aSelection == null || aSelection.isEmpty() ) {
      return;
    }
    if( false == aSelection.getFirstElement() instanceof WorkflowAbstractNavigatorItem )
    {
      return;
    }
    WorkflowAbstractNavigatorItem abstractNavigatorItem = ( WorkflowAbstractNavigatorItem )aSelection.getFirstElement();
    View navigatorView = null;
    if( abstractNavigatorItem instanceof WorkflowNavigatorItem ) {
      navigatorView = ( ( WorkflowNavigatorItem )abstractNavigatorItem ).getView();
    } else if( abstractNavigatorItem instanceof WorkflowNavigatorGroup ) {
      WorkflowNavigatorGroup navigatorGroup = ( WorkflowNavigatorGroup )abstractNavigatorItem;
      if( navigatorGroup.getParent() instanceof WorkflowNavigatorItem ) {
        navigatorView = ( ( WorkflowNavigatorItem )navigatorGroup.getParent() ).getView();
      }
    }
    if( navigatorView == null ) {
      return;
    }
    IEditorInput editorInput = getEditorInput( navigatorView.getDiagram() );
    IEditorPart editor = aPage.findEditor( editorInput );
    if( editor == null ) {
      return;
    }
    aPage.bringToTop( editor );
    if( editor instanceof DiagramEditor ) {
      DiagramEditor diagramEditor = ( DiagramEditor )editor;
      ResourceSet diagramEditorResourceSet = diagramEditor.getEditingDomain()
        .getResourceSet();
      EObject selectedView = diagramEditorResourceSet.getEObject( EcoreUtil.getURI( navigatorView ),
                                                                  true );
      if( selectedView == null ) {
        return;
      }
      GraphicalViewer graphicalViewer = ( GraphicalViewer )diagramEditor.getAdapter( GraphicalViewer.class );
      EditPart selectedEditPart = ( EditPart )graphicalViewer.getEditPartRegistry()
        .get( selectedView );
      if( selectedEditPart != null ) {
        graphicalViewer.select( selectedEditPart );
      }
    }
  }
}
