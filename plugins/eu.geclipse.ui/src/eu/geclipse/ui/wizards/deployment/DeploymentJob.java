/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *    Jie Tao - extension
 *****************************************************************************/

package eu.geclipse.ui.wizards.deployment;

import java.net.URI;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;

import eu.geclipse.core.model.IGridApplicationManager;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.GenericGridInstallParameters;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.internal.Activator;


/**
 * @author Yifan Zhou
 */
public class DeploymentJob extends Job {
  
  /**
   * The deployment source.
   */
  private URI[] source;
 
  /**
   * The deployment target.
   */
  private IGridComputing[] target;
  
  private IVirtualOrganization vo;
  
  private IGridProject gridproject;
  
  /**
   * The deployment tag.
   */
  private String tag;
  
  private GenericGridInstallParameters installparameters;
  /**This interface defines the job for deployment
   * @param name the name of the deployment
   * @param vo the project vo
   * @param deploySource the source
   * @param deployTarget the target CEs
   * @param deployTag the tag of the software
   * @param project the container for refresching
   */
  public DeploymentJob( final String name, 
                        final IVirtualOrganization vo,
                        final URI[] deploySource,
                        final IGridComputing[] deployTarget,
                        final String deployTag, 
                        final IGridProject project) {
    super( name );
    this.source = deploySource;
    this.target = deployTarget;
    this.tag = deployTag;
    this.vo = vo;
    this.gridproject = project;
  }

  @Override
  protected IStatus run( final IProgressMonitor monitor ) {
    IStatus status = Status.OK_STATUS;
          
    this.installparameters = new GenericGridInstallParameters();
    this.installparameters.setSources( this.source );
    this.installparameters.setTargets( this.target );
    this.installparameters.setTag( this.tag );
    IGridApplicationManager installmanager = this.vo.getApplicationManager();
    if (installmanager == null)
    {
      final Throwable e = new Throwable();
      Display.getDefault().asyncExec( new Runnable() {
        public void run() {
          
          ProblemDialog.openProblem( null,
                                     "Deployment error", //$NON-NLS-1$
                                     "The middleware does not support application deployment", //$NON-NLS-1$
                                     e );
        }
      } );
    }
    else {
      SubMonitor betterMonitor = SubMonitor.convert( monitor,
                                                     this.target.length );
      testCancelled( betterMonitor );
      betterMonitor.setTaskName( "Starting deployment" ); //$NON-NLS-1$
      try {
        installmanager.install( this.installparameters, betterMonitor.newChild( 1 ));
      } catch( ProblemException e ) {
        final Throwable e1 = e;
        Display.getDefault().asyncExec( new Runnable() {
          public void run() {
            ProblemDialog.openProblem( null,
                                       "Application install error", //$NON-NLS-1$
                                       "Error when installing the software", //$NON-NLS-1$
                                       e1 );
          }
        } );
      }
      testCancelled( betterMonitor );
      betterMonitor.done();
      try {
        this.gridproject.refresh( new NullProgressMonitor() );
      } catch ( ProblemException e ) {
        Activator.logException( e );
      }
    }
    return status;
  }
  
  private void testCancelled( final IProgressMonitor monitor ) {
    if( monitor.isCanceled() ) {
      throw new OperationCanceledException();
    }
  }
  
}
