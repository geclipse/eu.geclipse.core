/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
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
 *    Yifan Zhou - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.wizards.deployment;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;

import eu.geclipse.core.IApplicationDeployment;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.wizards.jobsubmission.JobSubmissionWizardBase;
import eu.geclipse.ui.wizards.jobsubmission.Messages;

import java.net.URI;

/**
 * @author Yifan Zhou
 */
public class DeploymentJob extends Job {
  
  /**
   * The execute method for an extension.
   */
  private IApplicationDeployment appDeployment;
  
  /**
   * The deployment source.
   */
  private URI[] source;
  
  /**
   * The deployment target.
   */
  private IGridElement[] target;
  
  /**
   * The deployment tag.
   */
  private String tag;
  
  /**This interface defines the job for deployment
   * @param name the name of the deployment
   * @param deployment the object
   * @param deploySource the source
   * @param deployTarget the target CEs
   * @param deployTag the tag of the software
   */
  public DeploymentJob( final String name, 
                        final IApplicationDeployment deployment,
                        final URI[] deploySource,
                        final IGridElement[] deployTarget,
                        final String deployTag ) {
    super( name );
    this.appDeployment = deployment; 
    this.source = deploySource;
    this.target = deployTarget;
    this.tag = deployTag;
  }

  @Override
  protected IStatus run( final IProgressMonitor monitor ) {
    IStatus status = Status.OK_STATUS;
    SubMonitor betterMonitor = SubMonitor.convert( monitor,
                                                   this.target.length );
    testCancelled( betterMonitor );
    betterMonitor.setTaskName( "Starting deployment" ); //$NON-NLS-1$
    this.appDeployment.deploy( this.source, this.target, this.tag, betterMonitor.newChild( 1 ) );
    testCancelled( betterMonitor );
    betterMonitor.done();
    return status;
  }
  
  private void testCancelled( final IProgressMonitor monitor ) {
    if( monitor.isCanceled() ) {
      throw new OperationCanceledException();
    }
  }
  
}
