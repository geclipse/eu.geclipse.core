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

import eu.geclipse.workflow.model.IInputPort;
import eu.geclipse.workflow.model.ILink;
import eu.geclipse.workflow.model.IOutputPort;
import eu.geclipse.workflow.model.IWorkflow;
import eu.geclipse.workflow.model.IWorkflowFactory;
import eu.geclipse.workflow.model.IWorkflowPackage;
import eu.geclipse.workflow.ui.edit.policies.WorkflowBaseItemSemanticEditPolicy;
import eu.geclipse.workflow.ui.part.Messages;

/**
 * The command to create a Link in the model.
 */
public class LinkCreateCommand extends CreateElementCommand {

  /**
   * The source of the link
   */
  private final EObject source;
  /**
   * The target of the link
   */
  private final EObject target;
  /**
   * The workflow in which the link is being created
   */
  private IWorkflow container;

  /**
   * Constructor
   * @param request 
   * @param source 
   * @param target 
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
        this.container = ( IWorkflow )element;
        super.setElementToEdit( this.container );
        break;
      }
    }
  }

  /**
   * @generated
   */
  @Override
  public boolean canExecute() {
    if( this.source == null && this.target == null ) {
      return false;
    }
    if( this.source != null && !( this.source instanceof IOutputPort ) ) {
      return false;
    }
    if( this.target != null && !( this.target instanceof IInputPort ) ) {
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
   * @generated NOT
   */
  @Override
  protected CommandResult doExecuteWithResult( IProgressMonitor monitor,
                                               IAdaptable info )
    throws ExecutionException
  {
    if( !canExecute() ) {
      throw new ExecutionException( Messages.getString("LinkCreateCommand_InvalidArgumentsInCreateLink" ) ); //$NON-NLS-1$
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
    return ( IOutputPort )this.source;
  }

  /**
   * @generated
   */
  protected IInputPort getTarget() {
    return ( IInputPort )this.target;
  }

  /**
   * @return container
   * @generated
   */
  public IWorkflow getContainer() {
    return this.container;
  }
}
