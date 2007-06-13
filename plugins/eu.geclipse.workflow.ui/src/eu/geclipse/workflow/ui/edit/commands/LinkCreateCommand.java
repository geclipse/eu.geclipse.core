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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import eu.geclipse.workflow.IInputPort;
import eu.geclipse.workflow.ILink;
import eu.geclipse.workflow.IOutputPort;
import eu.geclipse.workflow.IWorkflow;
import eu.geclipse.workflow.IWorkflowFactory;
import eu.geclipse.workflow.IWorkflowPackage;
import eu.geclipse.workflow.ui.edit.policies.WorkflowBaseItemSemanticEditPolicy;

/**
 * @generated
 */
public class LinkCreateCommand extends CreateElementCommand {

  /**
   * @generated
   */
  private final EObject source;
  /**
   * @generated
   */
  private final EObject target;
  /**
   * @generated
   */
  private IWorkflow container;

  /**
   * @generated
   */
  public LinkCreateCommand( CreateRelationshipRequest request,
                            EObject source,
                            EObject target )
  {
    super( request );
    this.source = source;
    this.target = target;
    if( request.getContainmentFeature() == null ) {
      setContainmentFeature( IWorkflowPackage.eINSTANCE.getIWorkflow_Links() );
    }
    // Find container element for the new link.
    // Climb up by containment hierarchy starting from the source
    // and return the first element that is instance of the container class.
    for( EObject element = source; element != null; element = element.eContainer() )
    {
      if( element instanceof IWorkflow ) {
        container = ( IWorkflow )element;
        super.setElementToEdit( container );
        break;
      }
    }
  }

  /**
   * @generated
   */
  @Override
  public boolean canExecute() {
    if( source == null && target == null ) {
      return false;
    }
    if( source != null && !( source instanceof IOutputPort ) ) {
      return false;
    }
    if( target != null && !( target instanceof IInputPort ) ) {
      return false;
    }
    if( getSource() == null ) {
      return true; // link creation is in progress; source is not defined yet
    }
    // target may be null here but it's possible to check constraint
    if( getContainer() == null ) {
      return false;
    }
    return WorkflowBaseItemSemanticEditPolicy.LinkConstraints.canCreateILink_3001( getContainer(),
                                                                                   getSource(),
                                                                                   getTarget() );
  }

  /**
   * @generated
   */
  @Override
  protected EObject doDefaultElementCreation() {
    // eu.geclipse.workflow.ILink newElement = (eu.geclipse.workflow.ILink) super.doDefaultElementCreation();
    ILink newElement = IWorkflowFactory.eINSTANCE.createILink();
    getContainer().getLinks().add( newElement );
    newElement.setSource( getSource() );
    newElement.setTarget( getTarget() );
    return newElement;
  }

  /**
   * @generated
   */
  @Override
  protected EClass getEClassToEdit() {
    return IWorkflowPackage.eINSTANCE.getIWorkflow();
  }

  /**
   * @generated
   */
  @Override
  protected CommandResult doExecuteWithResult( IProgressMonitor monitor,
                                               IAdaptable info )
    throws ExecutionException
  {
    if( !canExecute() ) {
      throw new ExecutionException( "Invalid arguments in create link command" ); //$NON-NLS-1$
    }
    return super.doExecuteWithResult( monitor, info );
  }

  /**
   * @generated
   */
  @Override
  protected ConfigureRequest createConfigureRequest() {
    ConfigureRequest request = super.createConfigureRequest();
    request.setParameter( CreateRelationshipRequest.SOURCE, getSource() );
    request.setParameter( CreateRelationshipRequest.TARGET, getTarget() );
    return request;
  }

  /**
   * @generated
   */
  @Override
  protected void setElementToEdit( EObject element ) {
    throw new UnsupportedOperationException();
  }

  /**
   * @generated
   */
  protected IOutputPort getSource() {
    return ( IOutputPort )source;
  }

  /**
   * @generated
   */
  protected IInputPort getTarget() {
    return ( IInputPort )target;
  }

  /**
   * @generated
   */
  public IWorkflow getContainer() {
    return container;
  }
}
