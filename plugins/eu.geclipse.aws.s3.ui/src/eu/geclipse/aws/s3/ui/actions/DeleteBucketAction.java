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

package eu.geclipse.aws.s3.ui.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;

import eu.geclipse.aws.s3.S3BucketStorage;
import eu.geclipse.aws.s3.internal.S3ServiceRegistry;
import eu.geclipse.core.reporting.ProblemException;

/**
 * This action deletes a selected bucket.
 * 
 * @author Moritz Post
 */
public class DeleteBucketAction extends AbstractAWSProjectAction {

  /** The list of selected {@link S3BucketStorage}. */
  private List<S3BucketStorage> bucketList;

  /**
   * Default constructor.
   */
  public DeleteBucketAction() {
    this.bucketList = new ArrayList<S3BucketStorage>();
  }

  @Override
  public void run( final IAction action ) {
    if( action.isEnabled() ) {

      Job job = new Job( Messages.getString( Messages.getString("DeleteBucketAction.jobDeleteBucket_title") ) ) { //$NON-NLS-1$

        @Override
        protected IStatus run( final IProgressMonitor monitor ) {
          try {
            monitor.beginTask( Messages.getString("DeleteBucketAction.taskTitleDeleteBucket_text"), IProgressMonitor.UNKNOWN ); //$NON-NLS-1$

            S3ServiceRegistry s3ServiceRegistry = S3ServiceRegistry.getRegistry();

            S3Service service;
            service = s3ServiceRegistry.getService( getAwsVo().getProperties()
              .getAwsAccessId() );

            if( service != null ) {
              for( S3BucketStorage bucket : DeleteBucketAction.this.bucketList )
              {
                service.deleteBucket( bucket.getS3Bucket() );
              }
            }

          } catch( ProblemException e ) {
            new InvocationTargetException( e,
                                           "Could not obtain the s3 registry" ); //$NON-NLS-1$
          } catch( S3ServiceException e ) {
            new InvocationTargetException( e,
                                           "Could not delete s3 bucket via the S3Service" ); //$NON-NLS-1$
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
  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    extractAWSVoFromCategory( selection );

    boolean enable = false;
    this.bucketList.clear();

    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection structuredSelection = ( IStructuredSelection )selection;
      for( Object element : structuredSelection.toList() ) {
        if( element instanceof S3BucketStorage ) {
          S3BucketStorage bucket = ( S3BucketStorage )element;
          this.bucketList.add( bucket );
        }
      }
    }
    if( this.bucketList.size() > 0 ) {
      enable = true;
    }
    action.setEnabled( enable );
  }

}
