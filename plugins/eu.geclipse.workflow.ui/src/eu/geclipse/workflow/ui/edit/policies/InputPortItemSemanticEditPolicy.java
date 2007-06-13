/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import eu.geclipse.workflow.ui.edit.commands.LinkCreateCommand;
import eu.geclipse.workflow.ui.edit.commands.LinkReorientCommand;
import eu.geclipse.workflow.ui.edit.parts.LinkEditPart;
import eu.geclipse.workflow.ui.providers.WorkflowElementTypes;

/**
 * @generated
 */
public class InputPortItemSemanticEditPolicy
  extends WorkflowBaseItemSemanticEditPolicy
{

  /**
   * @generated
   */
  @Override
  protected Command getDestroyElementCommand( DestroyElementRequest req ) {
    CompoundCommand cc = getDestroyEdgesCommand();
    addDestroyShortcutsCommand( cc );
    cc.add( getGEFWrapper( new DestroyElementCommand( req ) ) );
    return cc.unwrap();
  }

  /**
   * @generated
   */
  @Override
  protected Command getCreateRelationshipCommand( CreateRelationshipRequest req )
  {
    Command command = req.getTarget() == null
                                             ? getStartCreateRelationshipCommand( req )
                                             : getCompleteCreateRelationshipCommand( req );
    return command != null
                          ? command
                          : super.getCreateRelationshipCommand( req );
  }

  /**
   * @generated
   */
  protected Command getStartCreateRelationshipCommand( CreateRelationshipRequest req )
  {
    if( WorkflowElementTypes.ILink_3001 == req.getElementType() ) {
      return null;
    }
    return null;
  }

  /**
   * @generated
   */
  protected Command getCompleteCreateRelationshipCommand( CreateRelationshipRequest req )
  {
    if( WorkflowElementTypes.ILink_3001 == req.getElementType() ) {
      return getGEFWrapper( new LinkCreateCommand( req,
                                                   req.getSource(),
                                                   req.getTarget() ) );
    }
    return null;
  }

  /**
   * Returns command to reorient EClass based link. New link target or source
   * should be the domain model element associated with this node.
   * 
   * @generated
   */
  @Override
  protected Command getReorientRelationshipCommand( ReorientRelationshipRequest req )
  {
    switch( getVisualID( req ) ) {
      case LinkEditPart.VISUAL_ID:
        return getGEFWrapper( new LinkReorientCommand( req ) );
    }
    return super.getReorientRelationshipCommand( req );
  }
}
