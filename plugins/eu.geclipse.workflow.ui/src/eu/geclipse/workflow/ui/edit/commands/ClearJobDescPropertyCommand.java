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
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

import eu.geclipse.workflow.model.IWorkflowJob;
import eu.geclipse.workflow.ui.part.Messages;


/**
 * @author david
 *
 */
public class ClearJobDescPropertyCommand extends AbstractTransactionalCommand {   

  private IWorkflowJob job;

  /**
   * @param job IWorkflow job to clear property of
   */
  public ClearJobDescPropertyCommand( IWorkflowJob job )
  {
    super( TransactionUtil.getEditingDomain( job ),
           Messages.getString("ClearJobDescPropertyCommand_constructorMessage"), //$NON-NLS-1$
           null);
    this.job = job;
  }

  @Override
  protected CommandResult doExecuteWithResult( IProgressMonitor arg0,
                                               IAdaptable arg1 )
    throws ExecutionException
  {
    this.job.setJobDescription( "" ); //$NON-NLS-1$
    return CommandResult.newOKCommandResult();
  }
}
