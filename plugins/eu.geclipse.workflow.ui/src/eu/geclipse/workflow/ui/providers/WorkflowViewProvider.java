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
package eu.geclipse.workflow.ui.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.providers.AbstractViewProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.View;

import eu.geclipse.workflow.ui.edit.parts.InputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.LinkEditPart;
import eu.geclipse.workflow.ui.edit.parts.OutputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobNameEditPart;
import eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry;
import eu.geclipse.workflow.ui.view.factories.InputPortViewFactory;
import eu.geclipse.workflow.ui.view.factories.LinkViewFactory;
import eu.geclipse.workflow.ui.view.factories.OutputPortViewFactory;
import eu.geclipse.workflow.ui.view.factories.WorkflowJobNameViewFactory;
import eu.geclipse.workflow.ui.view.factories.WorkflowJobViewFactory;
import eu.geclipse.workflow.ui.view.factories.WorkflowViewFactory;

/**
 * @generated
 */
public class WorkflowViewProvider extends AbstractViewProvider {

  /**
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  protected Class getDiagramViewClass( IAdaptable semanticAdapter, String diagramKind )
  {
    EObject semanticElement = getSemanticElement( semanticAdapter );
    if( WorkflowEditPart.MODEL_ID.equals( diagramKind )
        && WorkflowVisualIDRegistry.getDiagramVisualID( semanticElement ) != -1 )
    {
      return WorkflowViewFactory.class;
    }
    return null;
  }

  /**
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  protected Class getNodeViewClass( IAdaptable semanticAdapter, View containerView, String semanticHint )
  {
    if( containerView == null ) {
      return null;
    }
    IElementType elementType = getSemanticElementType( semanticAdapter );
    EObject domainElement = getSemanticElement( semanticAdapter );
    int visualID;
    if( semanticHint == null ) {
      if( elementType != null || domainElement == null ) {
        return null;
      }
      visualID = WorkflowVisualIDRegistry.getNodeVisualID( containerView,
                                                           domainElement );
    } else {
      visualID = WorkflowVisualIDRegistry.getVisualID( semanticHint );
      if( elementType != null ) {
        if( !WorkflowElementTypes.isKnownElementType( elementType )
            || false == elementType instanceof IHintedType )
        {
          return null;
        }
        String elementTypeHint = ( ( IHintedType )elementType ).getSemanticHint();
        if( !semanticHint.equals( elementTypeHint ) ) {
          return null;
        }
        if( domainElement != null
            && visualID != WorkflowVisualIDRegistry.getNodeVisualID( containerView,
                                                                     domainElement ) )
        {
          return null;
        }
      } else {
        switch( visualID ) {
          case WorkflowEditPart.VISUAL_ID:
          case WorkflowJobEditPart.VISUAL_ID:
          case OutputPortEditPart.VISUAL_ID:
          case InputPortEditPart.VISUAL_ID:
          case LinkEditPart.VISUAL_ID:
            return null;
        }
      }
    }
    if( !WorkflowVisualIDRegistry.canCreateNode( containerView, visualID ) ) {
      return null;
    }
    switch( visualID ) {
      case WorkflowJobEditPart.VISUAL_ID:
        return WorkflowJobViewFactory.class;
      case WorkflowJobNameEditPart.VISUAL_ID:
        return WorkflowJobNameViewFactory.class;
      case OutputPortEditPart.VISUAL_ID:
        return OutputPortViewFactory.class;
      case InputPortEditPart.VISUAL_ID:
        return InputPortViewFactory.class;
    }
    return null;
  }

  /**
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  protected Class getEdgeViewClass( IAdaptable semanticAdapter,
                                    View containerView,
                                    String semanticHint )
  {
    IElementType elementType = getSemanticElementType( semanticAdapter );
    if( elementType == null ) {
      return null;
    }
    if( !WorkflowElementTypes.isKnownElementType( elementType )
        || false == elementType instanceof IHintedType )
    {
      return null;
    }
    String elementTypeHint = ( ( IHintedType )elementType ).getSemanticHint();
    if( elementTypeHint == null ) {
      return null;
    }
    if( semanticHint != null && !semanticHint.equals( elementTypeHint ) ) {
      return null;
    }
    int visualID = WorkflowVisualIDRegistry.getVisualID( elementTypeHint );
    EObject domainElement = getSemanticElement( semanticAdapter );
    if( domainElement != null
        && visualID != WorkflowVisualIDRegistry.getLinkWithClassVisualID( domainElement ) )
    {
      return null;
    }
    switch( visualID ) {
      case LinkEditPart.VISUAL_ID:
        return LinkViewFactory.class;
    }
    return null;
  }

  /**
   * @generated
   */
  private IElementType getSemanticElementType( IAdaptable semanticAdapter ) {
    if( semanticAdapter == null ) {
      return null;
    }
    return ( IElementType )semanticAdapter.getAdapter( IElementType.class );
  }
}
