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
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import eu.geclipse.core.IApplicationDeployment;
import eu.geclipse.core.model.IGridElement;

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
  private IGridElement[] source;
  
  /**
   * The deployment target.
   */
  private IGridElement[] target;
  
  /**
   * The deployment tag.
   */
  private String tag;
  
  public DeploymentJob( final String name, 
                        final IApplicationDeployment deployment,
                        final IGridElement[] deploySource,
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
    this.appDeployment.deploy( this.source, this.target, this.tag, monitor );
    if ( monitor.isCanceled() ) status =  Status.CANCEL_STATUS;
    return status;
  }
  
  
  
}
