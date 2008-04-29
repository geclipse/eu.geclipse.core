/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
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
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.wizards;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import eu.geclipse.batch.BatchJobManager;
import eu.geclipse.batch.IBatchJobInfo;
import eu.geclipse.batch.ui.internal.Messages;

import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.ui.dialogs.ProblemDialog;

/**
 * A wizard to move a job to another batch service and/or queue.
 * @author harald
 */
public class MoveJobWizard extends Wizard implements INewWizard {
  private BatchJobManager jobManager;
  private List< IBatchJobInfo > jobs;
  private MoveJobWizardPage page;
  private IWizardPage currentPage;

  /**
   * Argument constructor for this class. 
   * @param manager Provides access to the manager of batch jobs
   * @param jobs The jobs to be moved
   */
  public MoveJobWizard( final BatchJobManager manager, final List< IBatchJobInfo > jobs ) {
    setNeedsProgressMonitor( true );
    this.jobManager = manager;
    this.jobs = jobs;
  }
  
  @Override
  public boolean performFinish() {
    this.moveJob();

    return true;
  }

  @Override
  public boolean canFinish() {
    boolean result = false;

    this.currentPage = getContainer().getCurrentPage();
    if ( this.currentPage.isPageComplete() ) {
      result = super.canFinish();
    }
    return result;
  }  
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
   */
  public void init( final IWorkbench workbench, final IStructuredSelection selection ) {
    this.page = new MoveJobWizardPage();
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    this.addPage( this.page );
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#getWindowTitle()
   */
  @Override
  public String getWindowTitle() {
    return Messages.getString( "MoveJobWizard.Title" ); //$NON-NLS-1$
  }  
  
  /**
   * Moves a job if the user have the rights, if not an error dialog is displayed 
   */
  public void moveJob() {
    String ceName = this.page.getComputingElementName();
    String qName = this.page.getQueueName();
    
    if ( 0 == ceName.length() )
      ceName = null;
    
    if ( 0 == qName.length() )
      qName = null;
    
    for ( IBatchJobInfo batchJob : this.jobs ) {  
      try {
        this.jobManager.moveJob( batchJob, qName, ceName );
      } catch( ProblemException excp ) {
        // Display error dialog
        ProblemDialog.openProblem( this.getShell(),
                                   Messages.getString( "MoveJobWizard.error_manipulate_title" ),  //$NON-NLS-1$
                                   Messages.getString( "MoveJobWizard.error_manipulate_message" ), //$NON-NLS-1$
                                   excp );      
      }
    }
  }
}

