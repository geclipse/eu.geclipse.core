/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s): 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *****************************************************************************/
package eu.geclipse.servicejob.ui.wizard;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.model.IGridJobService;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;
import eu.geclipse.ui.wizards.jobsubmission.JobServiceSelectionWizardPage;

public class JobSubmissionServiceWizard extends Wizard {

  private JobServiceSelectionWizardPage selectionPage;
  private IVirtualOrganization vo;
  private IGridJobService selectedService;
//  private ArrayList<IGridJobService> jobServices;

  public JobSubmissionServiceWizard( final IVirtualOrganization vo ) {
    this.vo = vo;
  }

  @Override
  public void addPages() {
    this.selectionPage = new JobServiceSelectionWizardPage( "Select job submission service",
                                                            null );
    this.selectionPage.setTitle( "Submitting Operator's Job" );
//    this.jobServices = new ArrayList<IGridJobService>();
    Job job = new Job( "Retrieving list of job services" ) {

      @Override
      protected IStatus run( final IProgressMonitor monitor ) {
        IGridJobService[] allServices = null;
        IWorkbench workbench = PlatformUI.getWorkbench();
        Display display = workbench.getDisplay();
        display.syncExec( new Runnable() {

          public void run() {
            JobSubmissionServiceWizard.this.selectionPage.setServices( getJobServices() );
          }
        } );
        return Status.OK_STATUS;
      }
    };
    job.setUser( true );
    job.schedule();
    addPage( this.selectionPage );
  }

  @Override
  public boolean performFinish() {
    this.selectedService = this.selectionPage.getSubmissionService();
    return true;
  }

  private List<IGridJobService> getJobServices() {
    List<IGridJobService> result = new ArrayList<IGridJobService>();
    try {
      IGridJobService[] jobServices = this.vo.getJobSubmissionServices( new NullProgressMonitor() );
      for( IGridJobService service : jobServices ) {
        result.add( service );
      }
    } catch( ProblemException e ) {
      // TODO Auto-generated catch block
      ProblemDialog.openProblem( getShell(),
                                 "Failed to submit job", 
                                 "Fetching of the submission services failed",
                                 e );
    }
    return result;
  }

  public IGridJobService getSelectedService() {
    return this.selectedService;
  }

  @Override
  public boolean canFinish() {
    return super.canFinish()
           && this.selectionPage.getSubmissionService() != null;
  }
}
