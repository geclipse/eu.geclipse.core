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
package eu.geclipse.workflow.ui.edit.policies;

import java.util.Iterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import eu.geclipse.workflow.IWorkflowPackage;
import eu.geclipse.workflow.ui.edit.commands.InputPortCreateCommand;
import eu.geclipse.workflow.ui.edit.commands.OutputPortCreateCommand;
import eu.geclipse.workflow.ui.edit.commands.WorkflowJobEditCommand;
import eu.geclipse.workflow.ui.edit.parts.InputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.OutputPortEditPart;
import eu.geclipse.workflow.ui.part.WorkflowVisualIDRegistry;
import eu.geclipse.workflow.ui.providers.WorkflowElementTypes;

/**
 * @generated
 */
public class WorkflowJobItemSemanticEditPolicy extends WorkflowBaseItemSemanticEditPolicy
{

  /**
   * @generated
   */
  @Override
  protected Command getCreateCommand( CreateElementRequest req ) {
    if( WorkflowElementTypes.IOutputPort_2001 == req.getElementType() ) {
      if( req.getContainmentFeature() == null ) {
        req.setContainmentFeature( IWorkflowPackage.eINSTANCE.getIWorkflowNode_Outputs() );
      }
      return getGEFWrapper( new OutputPortCreateCommand( req ) );
    }
    if( WorkflowElementTypes.IInputPort_2002 == req.getElementType() ) {
      if( req.getContainmentFeature() == null ) {
        req.setContainmentFeature( IWorkflowPackage.eINSTANCE.getIWorkflowNode_Inputs() );
      }
      return getGEFWrapper( new InputPortCreateCommand( req ) );
    }
    return super.getCreateCommand( req );
  }

  @Override
  protected Command getEditCommand ( EditCommandRequestWrapper req ) {
    View view = ( View )getHost().getModel();
    if ( WorkflowElementTypes.IWorkflowJob_1001 == req.getType() )
      return getGEFWrapper( new WorkflowJobEditCommand( "update jobDescription", view, req.getEditCommandRequest() ) ); //$NON-NLS-1$
      
    return null;
    
  }
  
  /**
   * @generated
   */
  @Override
  protected Command getDestroyElementCommand( DestroyElementRequest req ) {
    CompoundCommand cc = getDestroyEdgesCommand();
    addDestroyChildNodesCommand( cc );
    addDestroyShortcutsCommand( cc );
    View view = ( View )getHost().getModel();
    if( view.getEAnnotation( "Shortcut" ) != null ) { //$NON-NLS-1$
      req.setElementToDestroy( view );
    }
    cc.add( getGEFWrapper( new DestroyElementCommand( req ) ) );
    return cc.unwrap();
  }

  /**
   * @generated
   */
  protected void addDestroyChildNodesCommand( CompoundCommand cmd ) {
    View view = ( View )getHost().getModel();
    EAnnotation annotation = view.getEAnnotation( "Shortcut" ); //$NON-NLS-1$
    if( annotation != null ) {
      return;
    }
    for( Iterator it = view.getChildren().iterator(); it.hasNext(); ) {
      Node node = ( Node )it.next();
      switch( WorkflowVisualIDRegistry.getVisualID( node ) ) {
        case OutputPortEditPart.VISUAL_ID:
          cmd.add( getDestroyElementCommand( node ) );
        break;
        case InputPortEditPart.VISUAL_ID:
          cmd.add( getDestroyElementCommand( node ) );
        break;
      }
    }
  }
}
