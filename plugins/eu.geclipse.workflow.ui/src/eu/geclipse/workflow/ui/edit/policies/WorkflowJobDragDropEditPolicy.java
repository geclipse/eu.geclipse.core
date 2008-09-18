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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest.ViewAndElementDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Node;

import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.workflow.ui.edit.commands.CopyJobDescToWorkflowCommand;
import eu.geclipse.workflow.ui.edit.commands.InputPortAfterCreateCommand;
import eu.geclipse.workflow.ui.edit.commands.OutputPortAfterCreateCommand;
import eu.geclipse.workflow.ui.edit.commands.UpdateJobPortsCommand;
import eu.geclipse.workflow.ui.edit.parts.InputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.OutputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.providers.WorkflowElementTypes;

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
    CompoundCommand cmd = new CompoundCommand();
    JSDLJobDescription jsdl = null;

    for( Object o : objects ) {
      
      if( o instanceof JSDLJobDescription ) {
        jsdl = ( JSDLJobDescription )o;
        this.selectedElement = ( WorkflowJobEditPart )getHost();
        CopyJobDescToWorkflowCommand copyCmd = new CopyJobDescToWorkflowCommand( this.selectedElement.resolveSemanticElement(), jsdl);
        cmd.add( new ICommandProxy(copyCmd) );        
        UpdateJobPortsCommand updatePortsCmd = new UpdateJobPortsCommand(this.selectedElement, jsdl);
        cmd.add (new ICommandProxy(updatePortsCmd) );        
      }

      return cmd;
    }
    return super.getDropObjectsCommand( dropRequest );
  }
  
}