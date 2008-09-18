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
package eu.geclipse.workflow.ui.part;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewAndElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import eu.geclipse.workflow.model.IInputPort;
import eu.geclipse.workflow.model.ILink;
import eu.geclipse.workflow.model.IOutputPort;
import eu.geclipse.workflow.model.IWorkflowJob;
import eu.geclipse.workflow.ui.edit.parts.InputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.OutputPortEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;
import eu.geclipse.workflow.ui.providers.WorkflowElementTypes;


/**
 * @author david
 *
 */
public class CalculateJobDependenciesAction  implements IObjectActionDelegate {
  
  /**
   * The WorkflowEditPart that has been selected.
   */
  protected WorkflowEditPart mySelectedElement;

  public void setActivePart( IAction action, IWorkbenchPart targetPart ) {
    targetPart.getSite().getShell();
    
  }

  
  // iterates through all jobs in diagram with ports and attempts to match
  // inputs to outputs and creates the links between them
  @SuppressWarnings("unchecked")
  public void run( IAction action ) {
    WorkflowEditPart diagramPart = this.mySelectedElement;
    List<WorkflowJobEditPart> jobParts = diagramPart.getChildren();
    
    // iterate through all job parts
    for (Iterator<WorkflowJobEditPart> i1 = jobParts.iterator(); i1.hasNext();) {
      WorkflowJobEditPart jobPart1 = i1.next();
      IWorkflowJob job1 = (IWorkflowJob)jobPart1.resolveSemanticElement();    
      
      // match with all job parts
      for (Iterator<WorkflowJobEditPart> i2 = jobParts.iterator(); i2.hasNext();) {
        WorkflowJobEditPart jobPart2 = i2.next();
        IWorkflowJob job2 = (IWorkflowJob)jobPart2.resolveSemanticElement();
        
        // try match ports of job1 and job2
        List<IInputPort> inputs = job1.getInputs();
        List<IOutputPort> outputs = job2.getOutputs();
        for (Iterator<IInputPort> iterInputs = inputs.iterator(); iterInputs.hasNext(); ) {
          IInputPort inputPort = iterInputs.next();  
          
          // clear existing links
          // TODO FIX: Only creates one link. WhY????????
//          EList<ILink> inputPortLinks = inputPort.getLinks();
//          for (Iterator<ILink> iterInputLinks = inputPortLinks.iterator(); iterInputLinks.hasNext();) {
//            DestroyElementRequest linkDestroyReq = new DestroyElementRequest(job1, true);
//            DestroyElementCommand linkDestroyCmd = linkDestroyReq.getBasicDestroyCommand();
//            try {
//              linkDestroyCmd.execute( new NullProgressMonitor(), null ); // exception is thrown, but not reported - NullPointerException here
//            } catch( ExecutionException e ) {
//              // TODO Auto-generated catch block
//              e.printStackTrace();
//            }
//          }
          
          for (Iterator<IOutputPort> iterOutputs = outputs.iterator(); iterOutputs.hasNext(); ) {
            IOutputPort outputPort = iterOutputs.next();
            if (inputPort.getName().equals( outputPort.getName() )) {
              Object j1 = jobPart1.findEditPart( jobPart1, inputPort );
              Object j2 = jobPart2.findEditPart( jobPart2, outputPort );
              if ((j2 instanceof OutputPortEditPart) && (j1 instanceof InputPortEditPart)) {
                IElementType type = WorkflowElementTypes.ILink_3001;
                Command linkCreateCmd = CreateConnectionViewAndElementRequest.getCreateCommand( new CreateConnectionViewAndElementRequest(
                                                                   type, ((IHintedType)type).getSemanticHint(), this.mySelectedElement.getDiagramPreferencesHint()),
                                                                   (OutputPortEditPart)j2, (InputPortEditPart)j1);
                linkCreateCmd.execute(); 
              }
            }
        }
      }
      }
    }
  }

  public void selectionChanged( IAction action, ISelection selection ) {
    this.mySelectedElement = null;
    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection structuredSelection = ( IStructuredSelection )selection;
      if( structuredSelection.size() == 1
          && structuredSelection.getFirstElement() instanceof WorkflowEditPart )
      {
        this.mySelectedElement = ( WorkflowEditPart )structuredSelection.getFirstElement();
      }
    }
    action.setEnabled( isEnabled() );
  }
  
  private boolean isEnabled() {
    return this.mySelectedElement != null;
  }  
  
}
