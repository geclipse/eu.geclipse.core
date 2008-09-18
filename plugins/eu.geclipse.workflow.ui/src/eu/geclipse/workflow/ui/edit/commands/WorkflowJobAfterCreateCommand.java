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
package eu.geclipse.workflow.ui.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import eu.geclipse.jsdl.JSDLJobDescription;
import eu.geclipse.workflow.model.IWorkflowJob;
import eu.geclipse.workflow.ui.edit.parts.WorkflowEditPart;
import eu.geclipse.workflow.ui.edit.parts.WorkflowJobEditPart;

/**
 * 
 * @author david
 */
public class WorkflowJobAfterCreateCommand extends Command {
  
  private IAdaptable adapter;
  JSDLJobDescription jsdl;
  IWorkflowJob newElement = null;
  TransactionalEditingDomain domain;
  protected String[] dirs;
  protected IFileStore wfRootFileStore;
  private WorkflowEditPart diagram;
  private WorkflowJobEditPart newWorkflowJobEditPart;
  
  
  /**
   * @param adapter Adapter to get model element from graphical part
   * @param jsdl JSDL to relate to new workflow job part
   * @param diagram The related workflow diagram edit part
   */
  public WorkflowJobAfterCreateCommand(IAdaptable adapter, JSDLJobDescription jsdl, WorkflowEditPart diagram) {
    this.adapter = adapter;
    this.jsdl = jsdl;
    this.domain = diagram.getEditingDomain();
    this.diagram = diagram;
  }

  @Override
  public void execute() {
    EObject newVisualElement = ( EObject ) this.adapter.getAdapter( EObject.class );
    Object o = newVisualElement.eCrossReferences().get( 0 );
    if ( o instanceof IWorkflowJob ) {
       this.newElement = ( IWorkflowJob )o;
       AbstractTransactionalCommand copyCommand = new CopyJobDescToWorkflowCommand(this.newElement, this.jsdl);
       this.newWorkflowJobEditPart = ( WorkflowJobEditPart )this.diagram.findEditPart( this.diagram, this.newElement );
       AbstractTransactionalCommand updatePortsCommand = new UpdateJobPortsCommand(this.newWorkflowJobEditPart, this.jsdl);
       try {
         OperationHistoryFactory.getOperationHistory()
           .execute( copyCommand, new NullProgressMonitor(), null );
         OperationHistoryFactory.getOperationHistory()
         .execute( updatePortsCommand, new NullProgressMonitor(), null );
       } catch ( ExecutionException eE ) {
         // ignore for now... very naughty!
       }
             
            
    }
  } 
  
}
