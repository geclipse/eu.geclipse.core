/*******************************************************************************
 * Copyright (c) 2009 g-Eclipse Consortium 
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
 *     - David Johnson - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.edit.commands;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;

import eu.geclipse.workflow.ui.edit.parts.InputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.LinkEditPart;
import eu.geclipse.workflow.ui.edit.parts.OutputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.edit.policies.InputPortItemSemanticEditPolicy;
import eu.geclipse.workflow.ui.edit.policies.OutputPortItemSemanticEditPolicy;
import eu.geclipse.workflow.ui.part.Messages;


/**
 * @author David
 *
 */
public class ClearLinksCommand extends AbstractTransactionalCommand {
  
  private GraphicalEditPart editPart;
  /**
   * @param editPart Workflow job edit part in which to remove all links from
   */
  public ClearLinksCommand( WorkflowJobEditPart editPart )
  {
    super( TransactionUtil.getEditingDomain( editPart.resolveSemanticElement() ),
           Messages.getString("ClearLinksCommand_constructorMessage"), //$NON-NLS-1$
           null );
    this.editPart = editPart;
    editPart.resolveSemanticElement();
  }

  @Override
  protected CommandResult doExecuteWithResult( IProgressMonitor arg0,
                                               IAdaptable arg1 )
    throws ExecutionException
  {
    CommandResult result = CommandResult.newOKCommandResult();
    CompoundCommand cmd = new CompoundCommand();
    List childParts = this.editPart.getChildren();
    for (Iterator iterChildParts = childParts.iterator(); iterChildParts.hasNext(); ) {
      Object o = iterChildParts.next();
      if (o instanceof InputPortEditPart) {
        InputPortEditPart portPart = (InputPortEditPart)o;
        List targets = portPart.getTargetConnections();
        for (Iterator iterTargets = targets.iterator(); iterTargets.hasNext(); ) {
          Object o1 = iterTargets.next();
          if (o1 instanceof LinkEditPart) {
            InputPortItemSemanticEditPolicy editPolicy = (InputPortItemSemanticEditPolicy)portPart.getEditPolicy( EditPolicyRoles.SEMANTIC_ROLE );
            LinkEditPart link = (LinkEditPart)o1;
            Command destroyCmd =  editPolicy.getDestroyElementCommand( new DestroyElementRequest(link.resolveSemanticElement(),  false) );
            cmd.add( destroyCmd );
          }
        }
      } else
      if (o instanceof OutputPortEditPart) {
        OutputPortEditPart portPart = (OutputPortEditPart)o;
        List sources = portPart.getSourceConnections();
        for (Iterator iterSources = sources.iterator(); iterSources.hasNext(); ) {
          Object o2 = iterSources.next();
          if (o2 instanceof LinkEditPart) {
            OutputPortItemSemanticEditPolicy editPolicy = (OutputPortItemSemanticEditPolicy)portPart.getEditPolicy( EditPolicyRoles.SEMANTIC_ROLE );
            LinkEditPart link = (LinkEditPart)o2; 
            Command destroyCmd =  editPolicy.getDestroyElementCommand( new DestroyElementRequest(link.resolveSemanticElement(),  false) );
            cmd.add( destroyCmd );
          }
        }
      }
    }
    cmd.execute();
    return result;
  }    
}
