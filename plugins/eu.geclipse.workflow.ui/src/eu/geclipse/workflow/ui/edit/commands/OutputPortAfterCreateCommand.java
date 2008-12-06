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
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import eu.geclipse.workflow.model.IOutputPort;
import eu.geclipse.workflow.ui.part.Messages;

/**
 * 
 * @author david
 *
 */
public class OutputPortAfterCreateCommand extends Command {
  
  private IAdaptable adapter;
  String filename;
  String uri;
  private TransactionalEditingDomain domain;
  IOutputPort newPort;

  /**
   * @param adapter Adapter to get model element from visual part
   * @param uri URI to add to port
   * @param domain Transactional editing domain
   */
  public OutputPortAfterCreateCommand(IAdaptable adapter, String filename, String uri, TransactionalEditingDomain domain) {
    this.adapter = adapter;
    this.filename = filename;
    this.uri = uri;
    this.domain = domain;
  }
  
  @Override
  public void execute() {
    EObject newVisualElement = (EObject)this.adapter.getAdapter(EObject.class);
    Object o = newVisualElement.eCrossReferences().get( 0 );
    if (o instanceof IOutputPort) {
      this.newPort = ( IOutputPort )o;
      AbstractTransactionalCommand command = new AbstractTransactionalCommand( this.domain,
                                                                               Messages.getString( "OutputPortAfterCreateCommand.settingPortUri" ), //$NON-NLS-1$
                                                                               null )
      {
        @Override
        protected CommandResult doExecuteWithResult( IProgressMonitor monitor,
                                                     IAdaptable info )
        {
          OutputPortAfterCreateCommand.this.newPort.setName( OutputPortAfterCreateCommand.this.uri );
          OutputPortAfterCreateCommand.this.newPort.setFileName( OutputPortAfterCreateCommand.this.filename );
          return CommandResult.newOKCommandResult();
        }
      };
      try {
        OperationHistoryFactory.getOperationHistory()
          .execute( command, new NullProgressMonitor(), null );
      } catch( ExecutionException eE ) { 
        // TODO implement problem reporting        
      }
    }
  }
  
}
