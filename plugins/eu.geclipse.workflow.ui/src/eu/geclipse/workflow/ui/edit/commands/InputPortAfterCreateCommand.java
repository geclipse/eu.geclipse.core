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

import eu.geclipse.workflow.model.IInputPort;
import eu.geclipse.workflow.ui.part.Messages;

/**
 * 
 * @author david
 *
 */
public class InputPortAfterCreateCommand extends Command {
  
  private IAdaptable adapter;
  String uri;
  private TransactionalEditingDomain domain;
  IInputPort newPort;

  /**
   * @param adapter Adapter to retrieve model part from visual part 
   * @param uri URI to add to port
   * @param domain Transactional editing domain
   */
  public InputPortAfterCreateCommand(IAdaptable adapter, String uri, TransactionalEditingDomain domain) {
    this.adapter = adapter;
    this.uri = uri;
    this.domain = domain;
  }
  
  @Override
  public void execute() {
    EObject newVisualElement = (EObject)adapter.getAdapter(EObject.class);
    Object o = newVisualElement.eCrossReferences().get( 0 );
    if (o instanceof IInputPort) {
      this.newPort = ( IInputPort )o;
      AbstractTransactionalCommand command = new AbstractTransactionalCommand( this.domain,
                                                                               Messages.getString( "InputPortAfterCreateCommand.settingPortUri" ), //$NON-NLS-1$
                                                                               null )
      {
        @Override
        protected CommandResult doExecuteWithResult( IProgressMonitor monitor,
                                                     IAdaptable info )
        {
          InputPortAfterCreateCommand.this.newPort.setName( InputPortAfterCreateCommand.this.uri );
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
