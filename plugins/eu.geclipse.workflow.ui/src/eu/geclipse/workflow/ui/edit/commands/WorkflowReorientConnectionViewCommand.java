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

import java.util.List;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class WorkflowReorientConnectionViewCommand extends AbstractTransactionalCommand
{

  /**
   * @generated
   */
  private IAdaptable edgeAdaptor;

  /**
   * @param editingDomain 
   * @param label 
   * @generated
   */
  public WorkflowReorientConnectionViewCommand( TransactionalEditingDomain editingDomain, String label ) {
    super( editingDomain, label, null );
  }

  /**
   * @generated
   */
  @Override
  public List getAffectedFiles() {
    View view = ( View )this.edgeAdaptor.getAdapter( View.class );
    if( view != null ) {
      return getWorkspaceFiles( view );
    }
    return super.getAffectedFiles();
  }

  /**
   * @return edgeAdaptor
   * @generated
   */
  public IAdaptable getEdgeAdaptor() {
    return this.edgeAdaptor;
  }

  /**
   * @param edgeAdaptor 
   * @generated
   */
  public void setEdgeAdaptor( IAdaptable edgeAdaptor ) {
    this.edgeAdaptor = edgeAdaptor;
  }

  /**
   * @generated
   */
  @Override
  protected CommandResult doExecuteWithResult( IProgressMonitor progressMonitor, IAdaptable info )
  {
    assert null != this.edgeAdaptor : "Null child in WorkflowReorientConnectionViewCommand"; //$NON-NLS-1$
    Edge edge = ( Edge )getEdgeAdaptor().getAdapter( Edge.class );
    assert null != edge : "Null edge in WorkflowReorientConnectionViewCommand"; //$NON-NLS-1$
    View tempView = edge.getSource();
    edge.setSource( edge.getTarget() );
    edge.setTarget( tempView );
    return CommandResult.newOKCommandResult();
  }
}
