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

import eu.geclipse.workflow.model.IWorkflowPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class OutputPortCreateCommand extends CreateElementCommand {

  /**
   * @param req 
   * @generated
   */
  public OutputPortCreateCommand( CreateElementRequest req ) {
    super( req );
  }

  /**
   * @generated
   */
  @Override
  protected EClass getEClassToEdit() {
    return IWorkflowPackage.eINSTANCE.getIWorkflowNode();
  }

  /**
   * @generated
   */
  @Override
  protected EObject getElementToEdit() {
    EObject container = ( ( CreateElementRequest )getRequest() ).getContainer();
    if( container instanceof View ) {
      container = ( ( View )container ).getElement();
    }
    return container;
  }
}
