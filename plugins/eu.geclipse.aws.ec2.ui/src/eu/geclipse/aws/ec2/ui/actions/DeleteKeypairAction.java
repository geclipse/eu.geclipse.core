/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Moritz Post - initial API and implementation
 *****************************************************************************/

package eu.geclipse.aws.ec2.ui.actions;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;

import eu.geclipse.aws.ec2.EC2Keypair;
import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.op.EC2OpDeleteKeypair;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.core.reporting.ProblemException;

/**
 * Deletes the currently selected security group from EC2.
 * 
 * @author Moritz Post
 */
public class DeleteKeypairAction extends AbstractAWSProjectAction {

  /** The list of keypairs to delete. */
  private List<EC2Keypair> keypairList;

  /**
   * Creates a new Action and initializes the internal data structures.
   */
  public DeleteKeypairAction() {
    this.keypairList = new ArrayList<EC2Keypair>();
  }

  @Override
  public void run( final IAction action ) {
    if( action.isEnabled() ) {

      StringBuffer strBuf = new StringBuffer();

      String dash = "- "; //$NON-NLS-1$
      String newLine = "\n"; //$NON-NLS-1$

      for( EC2Keypair keypair : this.keypairList ) {
        strBuf.append( dash + keypair.getName() + newLine );
      }

      Shell shell = getWorkbenchPart().getSite().getShell();
      boolean confirmation = MessageDialog.openConfirm( shell,
                                                        Messages.getString( "DeleteKeypairAction.dialog_delete_keypair_title" ), //$NON-NLS-1$
                                                        Messages.getString( "DeleteKeypairAction.dialog_delete_keypair_description" ) //$NON-NLS-1$
                                                            + newLine
                                                            + newLine
                                                            + strBuf.toString() );

      if( confirmation ) {
        deleteKeypairs();
      }
    }
  }

  /**
   * Deletes the user selected keypair in the {@link #keypairList}.
   */
  private void deleteKeypairs() {
    Job job = new Job( Messages.getString( "DeleteKeypairAction.job_title" ) ) { //$NON-NLS-1$

      @Override
      protected IStatus run( final IProgressMonitor monitor ) {
        try {

          monitor.beginTask( Messages.getString( "DeleteKeypairAction.monitor_title" ), 2 ); //$NON-NLS-1$

          // extract aws access id from first security group (at least one is
          // present)
          EC2Keypair ec2Keypair = DeleteKeypairAction.this.keypairList.get( 0 );
          extractAWSVoFromGridElement( ec2Keypair );

          // create operation
          IEC2 ec2 = EC2Registry.getRegistry().getEC2( getAwsVo() );

          monitor.worked( 1 );
          EC2OpDeleteKeypair opDeleteKeypair = new EC2OpDeleteKeypair( ec2,
                                                                       ec2Keypair.getKeyPairInfo()
                                                                         .getKeyName() );
          // execute operations
          new OperationExecuter().execOp( opDeleteKeypair );

          if( opDeleteKeypair.getException() != null ) {
            processException( opDeleteKeypair.getException(),
                              MessageFormat.format( Messages.getString( "DeleteKeypairAction.problem_could_not_delete_keypair_description" ), //$NON-NLS-1$
                                                    ec2Keypair.getName() ) );
          }
        } catch( ProblemException problemEx ) {
          processException( problemEx,
                            Messages.getString( "DeleteKeypairAction.problem_no_ec2_description" ) ); //$NON-NLS-1$
        } finally {
          monitor.done();
        }
        return Status.OK_STATUS;
      }
    };
    job.setPriority( Job.SHORT );
    job.setUser( true );
    job.schedule();
  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    boolean enable = false;
    this.keypairList.clear();

    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection structuredSelection = ( IStructuredSelection )selection;
      for( Object element : structuredSelection.toList() ) {
        if( element instanceof EC2Keypair ) {
          EC2Keypair ec2Keypair = ( EC2Keypair )element;
          this.keypairList.add( ec2Keypair );
        }
      }
    }
    if( this.keypairList.size() == 1 ) {
      enable = true;
    }
    action.setEnabled( enable );
  }
}
