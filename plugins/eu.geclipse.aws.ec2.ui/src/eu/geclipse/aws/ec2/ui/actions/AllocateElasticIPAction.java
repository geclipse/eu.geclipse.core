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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;

import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.op.EC2OpAllocateAddress;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.core.reporting.ProblemException;

/**
 * This {@link IObjectActionDelegate} invokes the {@link Job} to allocate a new
 * elastic IP from the EC2 inftrastructure.
 * 
 * @author Moritz Post
 */
public class AllocateElasticIPAction extends AbstractAWSProjectAction {

  @Override
  public void run( final IAction action ) {
    Job job = new Job( Messages.getString( "AllocateElasticIPAction.job_title" ) ) { //$NON-NLS-1$

      @Override
      protected IStatus run( final IProgressMonitor monitor ) {

        monitor.beginTask( Messages.getString( "AllocateElasticIPAction.monitor_title" ), 2 ); //$NON-NLS-1$

        IEC2 ec2;
        try {
          ec2 = EC2Registry.getRegistry().getEC2( getAwsVo() );
        } catch( ProblemException problemEx ) {
          Activator.log( "Can not obtain IEC2 instance", problemEx ); //$NON-NLS-1$
          return Status.CANCEL_STATUS;
        }

        EC2OpAllocateAddress opAllocateElasticIP = new EC2OpAllocateAddress( ec2 );

        new OperationExecuter().execOp( opAllocateElasticIP );

        // process any errors
        if( opAllocateElasticIP.getException() != null ) {
          Throwable cause = opAllocateElasticIP.getException();
          processException( cause,
                            Messages.getString( "AllocateElasticIPAction.problem_description" ) ); //$NON-NLS-1$
          return Status.CANCEL_STATUS;
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
    extractAWSVoFromCategory( selection );
  }
}
