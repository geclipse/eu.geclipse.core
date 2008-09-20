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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;

import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.workflow.model.IWorkflowJob;
import eu.geclipse.workflow.ui.edit.commands.CopyJobDescToWorkflowCommand;
import eu.geclipse.workflow.ui.edit.commands.UpdateJobPortsCommand;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.part.Messages;

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
        IWorkflowJob selectedJob = (IWorkflowJob)this.selectedElement.resolveSemanticElement();
        
        CopyJobDescToWorkflowCommand copyCmd = new CopyJobDescToWorkflowCommand( this.selectedElement.resolveSemanticElement(), jsdl);
        UpdateJobPortsCommand updatePortsCmd = new UpdateJobPortsCommand(this.selectedElement, jsdl);
        
        if (!(selectedJob.getName()==null && selectedJob.getJobDescription()==null)) {
          MessageDialog confirmDialog = new MessageDialog( null,
               Messages.getString("WorkflowJobDragDropEditPolicy_confirmationTitle"), //$NON-NLS-1$
               null,
               Messages.getString( "WorkflowJobDragDropEditPolicy_userPrompt" ), //$NON-NLS-1$
               true ? MessageDialog.QUESTION : MessageDialog.WARNING ,
               new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL },
               0 );
          int result = confirmDialog.open();
          if (result==0) {
            cmd.add( new ICommandProxy(copyCmd) );        
            cmd.add (new ICommandProxy(updatePortsCmd) ); 
          }
        } else {
          cmd.add( new ICommandProxy(copyCmd) );        
          cmd.add (new ICommandProxy(updatePortsCmd) ); 
        }      
      }

      return cmd;
    }
    return super.getDropObjectsCommand( dropRequest );
  }   
}