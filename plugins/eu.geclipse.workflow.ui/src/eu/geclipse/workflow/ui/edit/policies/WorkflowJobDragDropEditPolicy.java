/*******************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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
package eu.geclipse.workflow.ui.edit.policies;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;

import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.workflow.ui.edit.commands.JSDLDropCommand;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;

/**
 * @author david
 */
public class WorkflowJobDragDropEditPolicy extends DragDropEditPolicy {

  private WorkflowJobEditPart selectedElement = null;

  /*
   * (non-Javadoc)
   * @see
   * org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy
   * #getDropObjectsCommand
   * (org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest)
   */
  @SuppressWarnings("unchecked")
  @Override
  public Command getDropObjectsCommand( DropObjectsRequest dropRequest ) {
    List objects = dropRequest.getObjects();
    for( Object o : objects ) {
      ICommandProxy cmd = null;
      if( o instanceof JSDLJobDescription ) {
        JSDLJobDescription jsdl = ( JSDLJobDescription )o;
        this.selectedElement = ( WorkflowJobEditPart )getHost();
        cmd = new ICommandProxy(new JSDLDropCommand( this.selectedElement.resolveSemanticElement(), ((EObject)this.selectedElement.getModel()).eResource(), jsdl));
        }
        return cmd;
      }
    return super.getDropObjectsCommand( dropRequest );
  }
}