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

import java.util.ArrayList;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;

import eu.geclipse.aws.ec2.EC2Registry;
import eu.geclipse.aws.ec2.IEC2;
import eu.geclipse.aws.ec2.op.EC2OpRegisterImage;
import eu.geclipse.aws.ec2.op.OperationExecuter;
import eu.geclipse.aws.ec2.ui.Messages;
import eu.geclipse.aws.ec2.ui.internal.Activator;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.reporting.ProblemException;

/**
 * This {@link IObjectActionDelegate} starts a {@link Job} to register an image,
 * contained within an S3 bucket, as an EC2 AMI image.
 * 
 * @author Moritz Post
 */
public class RegisterImageAction extends AbstractAWSProjectAction {

  /** The list of selected {@link IGridConnectionElement}s. */
  private ArrayList<IGridConnectionElement> connectionElementList;

  /**
   * Instantiate the action.
   */
  public RegisterImageAction() {
    this.connectionElementList = new ArrayList<IGridConnectionElement>();
  }

  @Override
  public void run( final IAction action ) {
    Job job = new Job( Messages.getString( "RegisterImageAction.job_title" ) ) { //$NON-NLS-1$

      @Override
      protected IStatus run( final IProgressMonitor monitor ) {

        try {
          if( RegisterImageAction.this.connectionElementList.size() == 0 ) {
            return Status.CANCEL_STATUS;
          }

          monitor.beginTask( Messages.getString( "RegisterImageAction.monitor_title" ), 2 ); //$NON-NLS-1$
          extractAWSVoFromGridElement( RegisterImageAction.this.connectionElementList.get( 0 ) );

          IEC2 ec2;
          try {
            ec2 = EC2Registry.getRegistry().getEC2( getAwsVo() );
          } catch( ProblemException problemEx ) {
            Activator.log( "Can not obtain IEC2 instance", problemEx ); //$NON-NLS-1$
            return Status.CANCEL_STATUS;
          }
          monitor.worked( 1 );

          IPath path = RegisterImageAction.this.connectionElementList.get( 0 )
            .getPath();

          String[] segments = path.segments();

          StringBuilder strBldr = new StringBuilder();
          strBldr.append( segments[ segments.length - 2 ] );
          strBldr.append( '/' );
          strBldr.append( segments[ segments.length - 1 ] );

          EC2OpRegisterImage opRegisterImage = new EC2OpRegisterImage( ec2,
                                                                       strBldr.toString() );

          new OperationExecuter().execOp( opRegisterImage );

          // process any errors
          if( opRegisterImage.getException() != null ) {
            Throwable cause = opRegisterImage.getException();
            processException( cause,
                              Messages.getString( "RegisterImageAction.problem_description" ) ); //$NON-NLS-1$
            return Status.CANCEL_STATUS;
          }
          return Status.OK_STATUS;
        } finally {
          monitor.done();
        }
      }
    };

    job.setPriority( Job.SHORT );
    job.setUser( true );
    job.schedule();
  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    boolean enable = false;
    this.connectionElementList.clear();

    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection structuredSelection = ( IStructuredSelection )selection;
      for( Object element : structuredSelection.toList() ) {
        if( element instanceof IGridConnectionElement ) {
          IGridConnectionElement connElem = ( IGridConnectionElement )element;
          this.connectionElementList.add( connElem );
        }
      }
    }
    if( this.connectionElementList.size() == 1 ) {
      enable = true;
    }
    action.setEnabled( enable );

  }
}
