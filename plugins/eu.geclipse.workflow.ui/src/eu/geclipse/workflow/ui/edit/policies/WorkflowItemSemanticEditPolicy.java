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

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;

import eu.geclipse.workflow.model.IWorkflowPackage;
import eu.geclipse.workflow.ui.edit.commands.WorkflowJobCreateCommand;
import eu.geclipse.workflow.ui.providers.WorkflowElementTypes;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

/**
 * @generated
 */
public class WorkflowItemSemanticEditPolicy extends WorkflowBaseItemSemanticEditPolicy
{
 
  
  @Override
  public Command getCommand( Request request ) {
        
    return super.getCommand( request );
  }

  /**
   * @generated
   */
  @Override
  protected Command getCreateCommand( CreateElementRequest req ) {
    if( WorkflowElementTypes.IWorkflowJob_1001 == req.getElementType() ) {
      if( req.getContainmentFeature() == null ) {
        req.setContainmentFeature( IWorkflowPackage.eINSTANCE.getIWorkflow_Nodes() );
      }
      return getGEFWrapper( new WorkflowJobCreateCommand( req ) );
    }
    return super.getCreateCommand( req );
  }

  /**
   * @generated
   */
  @Override
  protected Command getDuplicateCommand( DuplicateElementsRequest req ) {
    TransactionalEditingDomain editingDomain = ( ( IGraphicalEditPart )getHost() ).getEditingDomain();
    return getGEFWrapper( new DuplicateAnythingCommand( editingDomain, req ) );
  }
  /**
   * @generated
   */
  private static class DuplicateAnythingCommand
    extends DuplicateEObjectsCommand
  {

    /**
     * @generated
     */
    public DuplicateAnythingCommand( TransactionalEditingDomain editingDomain,
                                     DuplicateElementsRequest req )
    {
      super( editingDomain,
             req.getLabel(),
             req.getElementsToBeDuplicated(),
             req.getAllDuplicatedElementsMap() );
    }
  }
}
