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
package eu.geclipse.workflow.ui.edit.helpers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.GetEditContextCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelper;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.notation.Diagram;

/**
 * @generated
 */
public class WorkflowBaseEditHelper extends AbstractEditHelper {

  /**
   * @generated
   */
  public static final String EDIT_POLICY_COMMAND = "edit policy command"; //$NON-NLS-1$

  /**
   * @generated
   */
  @Override
  protected ICommand getInsteadCommand( IEditCommandRequest req ) {
    ICommand epCommand = ( ICommand )req.getParameter( EDIT_POLICY_COMMAND );
    req.setParameter( EDIT_POLICY_COMMAND, null );
    ICommand ehCommand = super.getInsteadCommand( req );
    if( epCommand == null ) {
      return ehCommand;
    }
    if( ehCommand == null ) {
      return epCommand;
    }
    CompositeCommand command = new CompositeCommand( null );
    command.add( epCommand );
    command.add( ehCommand );
    return command;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelper#getContainerCommand(org.eclipse.gmf.runtime.emf.type.core.requests.GetContainerRequest)
   */
  @Override
  protected ICommand getEditContextCommand(GetEditContextRequest req) {

      GetEditContextCommand result = null;
      
      IEditCommandRequest editRequest = req.getEditCommandRequest();
      
      if (editRequest instanceof CreateElementRequest) {
          result = new GetEditContextCommand(req);
          EObject container = ((CreateElementRequest) editRequest).getContainer();

          if (container instanceof Diagram) {
              EObject element = ((Diagram) container).getElement();
  
              if (element == null) {
                  // Element is null if the diagram was created using the wizard
                  EObject annotation = ((Diagram) container).eContainer();
  
                  if (annotation != null) {
                      element = annotation.eContainer();
                  }
              }
              container = element;
          }
          result.setEditContext(container);
      }
      return result;
  }

  /**
   * @generated
   */
  @Override
  protected ICommand getCreateCommand( CreateElementRequest req )
  {
    return null;
  }
  
  /**
   * @generated
   */
  @Override
  protected ICommand getCreateRelationshipCommand( CreateRelationshipRequest req )
  {
    return null;
  }

  /**
   * @generated
   */
  @Override
  protected ICommand getDestroyElementCommand( DestroyElementRequest req ) {
    return null;
  }

  /**
   * @generated
   */
  @Override
  protected ICommand getDestroyReferenceCommand( DestroyReferenceRequest req ) {
    return null;
  }
}