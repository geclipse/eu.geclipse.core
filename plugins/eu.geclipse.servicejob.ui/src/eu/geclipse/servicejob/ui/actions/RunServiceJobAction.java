/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium All rights reserved. This program and
 * the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Initial development of the original
 * code was made for project g-Eclipse founded by European Union project number:
 * FP6-IST-034327 http://www.geclipse.eu/ Contributor(s): PSNC: - Katarzyna
 * Bylec (katis@man.poznan.pl)
 *****************************************************************************/
package eu.geclipse.servicejob.ui.actions;

import java.util.ArrayList;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import eu.geclipse.core.model.IServiceJob;
import eu.geclipse.servicejob.ui.wizard.JobSubmissionServiceWizard;

/**
 * Action for running Operator's Job.
 * 
 * @author Katarzyna Bylec
 */
public class RunServiceJobAction implements IObjectActionDelegate {

  private ArrayList<IServiceJob> serviceJobList = new ArrayList<IServiceJob>();

  public void setActivePart( final IAction action,
                             final IWorkbenchPart targetPart )
  {
    // do nothing
  }

  public void run( final IAction action ) {
    for( IServiceJob serviceJob : this.serviceJobList ) {
      if( serviceJob.needsSubmissionWizard() ) {
        JobSubmissionServiceWizard serviceWizard = new JobSubmissionServiceWizard( serviceJob.getParent()
          .getProject()
          .getVO() );
        WizardDialog dialog = new WizardDialog( PlatformUI.getWorkbench()
          .getActiveWorkbenchWindow()
          .getShell(), serviceWizard );
        if( dialog.open() == WizardDialog.OK ) {
          serviceJob.setSubmissionService( serviceWizard.getSelectedService() );
          serviceJob.run();
        }
      } else {
        serviceJob.run();
      }
    }
  }

  public void selectionChanged( final IAction action, final ISelection selection )
  {
    this.serviceJobList = new ArrayList<IServiceJob>();
    if( selection instanceof IStructuredSelection ) {
      IStructuredSelection sselection = ( IStructuredSelection )selection;
      for( Object obj : sselection.toArray() ) {
        if( obj instanceof IServiceJob ) {
          this.serviceJobList.add( ( IServiceJob )obj );
        }
      }
    }
  }
}
