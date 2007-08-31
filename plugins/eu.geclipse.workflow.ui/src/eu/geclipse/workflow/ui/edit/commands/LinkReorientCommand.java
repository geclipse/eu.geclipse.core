/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Ashish Thandavan - initial API and implementation
 ******************************************************************************/
package eu.geclipse.workflow.ui.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import eu.geclipse.workflow.IInputPort;
import eu.geclipse.workflow.ILink;
import eu.geclipse.workflow.IOutputPort;
import eu.geclipse.workflow.IWorkflow;
import eu.geclipse.workflow.ui.edit.policies.WorkflowBaseItemSemanticEditPolicy;
import eu.geclipse.workflow.ui.part.Messages;

/**
 * @generated
 */
public class LinkReorientCommand extends EditElementCommand {

  /**
   * @generated
   */
  private final int reorientDirection;
  /**
   * @generated
   */
  private final EObject oldEnd;
  /**
   * @generated
   */
  private final EObject newEnd;

  /**
   * @generated
   */
  public LinkReorientCommand( ReorientRelationshipRequest request ) {
    super( request.getLabel(), request.getRelationship(), request );
    reorientDirection = request.getDirection();
    oldEnd = request.getOldRelationshipEnd();
    newEnd = request.getNewRelationshipEnd();
  }

  /**
   * @generated
   */
  @Override
  public boolean canExecute() {
    if( !( getElementToEdit() instanceof ILink ) ) {
      return false;
    }
    if( reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE ) {
      return canReorientSource();
    }
    if( reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET ) {
      return canReorientTarget();
    }
    return false;
  }

  /**
   * @generated
   */
  protected boolean canReorientSource() {
    if( !( oldEnd instanceof IOutputPort && newEnd instanceof IOutputPort ) ) {
      return false;
    }
    IInputPort target = getLink().getTarget();
    if( !( getLink().eContainer() instanceof IWorkflow ) ) {
      return false;
    }
    IWorkflow container = ( IWorkflow )getLink().eContainer();
    return WorkflowBaseItemSemanticEditPolicy.LinkConstraints.canExistILink_3001( container,
                                                                                  getNewSource(),
                                                                                  target );
  }

  /**
   * @generated
   */
  protected boolean canReorientTarget() {
    if( !( oldEnd instanceof IInputPort && newEnd instanceof IInputPort ) ) {
      return false;
    }
    IOutputPort source = getLink().getSource();
    if( !( getLink().eContainer() instanceof IWorkflow ) ) {
      return false;
    }
    IWorkflow container = ( IWorkflow )getLink().eContainer();
    return WorkflowBaseItemSemanticEditPolicy.LinkConstraints.canExistILink_3001( container,
                                                                                  source,
                                                                                  getNewTarget() );
  }

  /**
   * @generated NOT
   */
  @Override
  protected CommandResult doExecuteWithResult( IProgressMonitor monitor,
                                               IAdaptable info )
    throws ExecutionException
  {
    if( !canExecute() ) {
      throw new ExecutionException( Messages.getString("LinkCreateCommand_InvalidArgumentsInReorientLink") ); //$NON-NLS-1$
    }
    if( reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE ) {
      return reorientSource();
    }
    if( reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET ) {
      return reorientTarget();
    }
    throw new IllegalStateException();
  }

  /**
   * @generated
   */
  protected CommandResult reorientSource() throws ExecutionException {
    getLink().setSource( getNewSource() );
    return CommandResult.newOKCommandResult( getLink() );
  }

  /**
   * @generated
   */
  protected CommandResult reorientTarget() throws ExecutionException {
    getLink().setTarget( getNewTarget() );
    return CommandResult.newOKCommandResult( getLink() );
  }

  /**
   * @generated
   */
  protected ILink getLink() {
    return ( ILink )getElementToEdit();
  }

  /**
   * @generated
   */
  protected IOutputPort getOldSource() {
    return ( IOutputPort )oldEnd;
  }

  /**
   * @generated
   */
  protected IOutputPort getNewSource() {
    return ( IOutputPort )newEnd;
  }

  /**
   * @generated
   */
  protected IInputPort getOldTarget() {
    return ( IInputPort )oldEnd;
  }

  /**
   * @generated
   */
  protected IInputPort getNewTarget() {
    return ( IInputPort )newEnd;
  }
}
