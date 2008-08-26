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
package eu.geclipse.workflow.ui.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

import eu.geclipse.workflow.model.IWorkflow;
import eu.geclipse.workflow.model.IWorkflowPackage;
import eu.geclipse.workflow.ui.edit.parts.InputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.LinkEditPart;
import eu.geclipse.workflow.ui.edit.parts.OutputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobDescriptionEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobNameEditPart;
import eu.geclipse.workflow.ui.internal.WorkflowDiagramEditorPlugin;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented
 * by a domain model object.
 * 
 * @generated
 */
public class WorkflowVisualIDRegistry {

  /**
   * @generated
   */
  private static final String DEBUG_KEY = WorkflowDiagramEditorPlugin.getInstance().getBundle().getSymbolicName()
          + "/debug/visualID"; //$NON-NLS-1$

  /**
   * @generated
   */
  public static int getVisualID( View view ) {
    if( view instanceof Diagram ) {
      if( WorkflowEditPart.MODEL_ID.equals( view.getType() ) ) {
        return WorkflowEditPart.VISUAL_ID;
      } else {
        return -1;
      }
    }
    return eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry.getVisualID( view.getType() );
  }

  /**
   * @generated
   */
  public static String getModelID( View view ) {
    View diagram = view.getDiagram();
    while( view != diagram ) {
      EAnnotation annotation = view.getEAnnotation( "Shortcut" ); //$NON-NLS-1$
      if( annotation != null ) {
        return ( String )annotation.getDetails().get( "modelID" ); //$NON-NLS-1$
      }
      view = ( View )view.eContainer();
    }
    return diagram != null
                          ? diagram.getType()
                          : null;
  }

  /**
   * @generated
   */
  public static int getVisualID( String type ) {
    try {
      return Integer.parseInt( type );
    } catch( NumberFormatException e ) {
      if( Boolean.TRUE.toString()
        .equalsIgnoreCase( Platform.getDebugOption( DEBUG_KEY ) ) )
      {
        WorkflowDiagramEditorPlugin.getInstance()
          .logError( "Unable to parse view type as a visualID number: " + type ); //$NON-NLS-1$
      }
    }
    return -1;
  }

  /**
   * @generated
   */
  public static String getType( int visualID ) {
    return String.valueOf( visualID );
  }

  /**
   * @generated
   */
  public static int getDiagramVisualID( EObject domainElement ) {
    if( domainElement == null ) {
      return -1;
    }
    if( IWorkflowPackage.eINSTANCE.getIWorkflow()
      .isSuperTypeOf( domainElement.eClass() )
        && isDiagram( ( IWorkflow )domainElement ) )
    {
      return WorkflowEditPart.VISUAL_ID;
    }
    return -1;
  }

  /**
   * @generated
   */
  public static int getNodeVisualID( View containerView, EObject domainElement )
  {
    if( domainElement == null
        || !WorkflowEditPart.MODEL_ID.equals( eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry.getModelID( containerView ) ) )
    {
      return -1;
    }
    switch( eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry.getVisualID( containerView ) ) {
      case WorkflowJobEditPart.VISUAL_ID:
        if( IWorkflowPackage.eINSTANCE.getIOutputPort().isSuperTypeOf( domainElement.eClass() ) ) {
          return OutputPortEditPart.VISUAL_ID;
        }
        if( IWorkflowPackage.eINSTANCE.getIInputPort().isSuperTypeOf( domainElement.eClass() ) ) {
          return InputPortEditPart.VISUAL_ID;
        }
      break;
      case WorkflowEditPart.VISUAL_ID:
        if( IWorkflowPackage.eINSTANCE.getIWorkflowJob().isSuperTypeOf( domainElement.eClass() ) ) {
          return WorkflowJobEditPart.VISUAL_ID;
        }
      break;
    }
    return -1;
  }

  /**
   * @generated
   */
  public static boolean canCreateNode( View containerView, int nodeVisualID ) {
    String containerModelID = eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry.getModelID( containerView );
    if( !WorkflowEditPart.MODEL_ID.equals( containerModelID ) ) {
      return false;
    }
    int containerVisualID;
    if( WorkflowEditPart.MODEL_ID.equals( containerModelID ) ) {
      containerVisualID = eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry.getVisualID( containerView );
    } else {
      if( containerView instanceof Diagram ) {
        containerVisualID = WorkflowEditPart.VISUAL_ID;
      } else {
        return false;
      }
    }
    switch( containerVisualID ) {
      case WorkflowJobEditPart.VISUAL_ID:
        if( WorkflowJobNameEditPart.VISUAL_ID == nodeVisualID ) {
          return true;
        }
        if( WorkflowJobDescriptionEditPart.VISUAL_ID == nodeVisualID ) {
          return true;
        }
        if( OutputPortEditPart.VISUAL_ID == nodeVisualID ) {
          return true;
        }
        if( InputPortEditPart.VISUAL_ID == nodeVisualID ) {
          return true;
        }
      break;
      case WorkflowEditPart.VISUAL_ID:
        if( WorkflowJobEditPart.VISUAL_ID == nodeVisualID ) {
          return true;
        }
      break;
    }
    return false;
  }

  /**
   * @generated
   */
  public static int getLinkWithClassVisualID( EObject domainElement ) {
    if( domainElement == null ) {
      return -1;
    }
    if( IWorkflowPackage.eINSTANCE.getILink()
      .isSuperTypeOf( domainElement.eClass() ) )
    {
      return LinkEditPart.VISUAL_ID;
    }
    return -1;
  }

  /**
   * User can change implementation of this method to handle some specific
   * situations not covered by default logic.
   * 
   * @generated
   */
  private static boolean isDiagram( IWorkflow element ) {
    return true;
  }
}
