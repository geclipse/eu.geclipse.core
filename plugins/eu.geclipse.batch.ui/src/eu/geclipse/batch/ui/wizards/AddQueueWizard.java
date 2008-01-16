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
 *      - Neophytos Theodorou (TODO)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import eu.geclipse.batch.ui.internal.Messages;
import eu.geclipse.batch.BatchException;
import eu.geclipse.batch.IBatchService;
import eu.geclipse.batch.IQueueInfo;
import eu.geclipse.ui.dialogs.NewProblemDialog;

/**
 * Wizard to create a new queue
 */
public class AddQueueWizard extends Wizard implements INewWizard {
  private IBatchService batchWrapper;
  private AddQueueWizardRequiredPage requiredPage;
  private AddQueueWizardOptionalPage optionalPage;
  private IWizardPage currentPage;
  /**
   * Argument constructor for this class. 
   * @param batchWrapper Provides access to the batch service where the queue will be created
   */
  public AddQueueWizard( final IBatchService batchWrapper ) {
    setNeedsProgressMonitor( true );
    this.batchWrapper = batchWrapper;
  }
  
  @Override
  public boolean performFinish() {
    if ( this.getContainer().getCurrentPage() == this.requiredPage ){
      this.createQueue();
    } else if ( this.getContainer().getCurrentPage() == this.optionalPage ) {
      this.createAdvQueue();
    }   

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
    this.requiredPage = new AddQueueWizardRequiredPage();
    this.optionalPage = new AddQueueWizardOptionalPage(); 
  }

  
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages() {
    this.addPage( this.requiredPage );
    this.addPage( this.optionalPage );
  }

  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#getWindowTitle()
   */
  @Override
  public String getWindowTitle() {
    return Messages.getString( "AddQueueWizard.Title" ); //$NON-NLS-1$
  }  
  
  
  /**
   * Creates a queue if user have the rights, if not an error dialog is displayed 
   */
  public void createQueue() {
    IQueueInfo.QueueType type;
    
    if ( this.requiredPage.typeCombo.getText().equals( Messages.getString( "AddQueueRequiredPage.Type_Execution" ) ) ) //$NON-NLS-1$
      type = IQueueInfo.QueueType.execution;
    else
      type = IQueueInfo.QueueType.route;

    try {
      this.batchWrapper.createQueue( this.requiredPage.nameText.getText(),
                                     type,
                                     this.requiredPage.enabledButton.getSelection(),
                                     this.requiredPage.getTimeCPU(), 
                                     this.requiredPage.getTimeWall(),
                                     this.requiredPage.getVONames() );
    } catch( BatchException excp ) {
      // Display error dialog
      NewProblemDialog.openProblem( this.getShell(),
                                    Messages.getString( "AddQueueWizard.error_manipulate_title" ),  //$NON-NLS-1$
                                    Messages.getString( "AddQueueWizard.error_manipulate_message" ), //$NON-NLS-1$
                                    excp );      
    }
  }

  /**
   * Creates a queue if user have the rights, if not an error dialog is displayed 
   */
  public void createAdvQueue() {
    IQueueInfo.QueueType type;
    
    if ( this.requiredPage.typeCombo.getText().equals( Messages.getString( "AddQueueRequiredPage.Type_Execution" ) ) ) //$NON-NLS-1$
      type = IQueueInfo.QueueType.execution;
    else
      type = IQueueInfo.QueueType.route;

    try {
      this.batchWrapper.createQueue( this.requiredPage.nameText.getText(),
                                     this.optionalPage.getPriority(),
                                     type,
                                     this.requiredPage.enabledButton.getSelection(),
                                     this.optionalPage.getMaxRun(),
                                     this.requiredPage.getTimeCPU(), 
                                     this.requiredPage.getTimeWall(),
                                     this.optionalPage.getMaxQueue(),
                                     this.optionalPage.getAssignedResources(),
                                     this.requiredPage.getVONames() );
    } catch( BatchException excp ) {
      // Display error dialog
      NewProblemDialog.openProblem( this.getShell(),
                                    Messages.getString( "AddQueueWizard.error_manipulate_title" ),  //$NON-NLS-1$
                                    Messages.getString( "AddQueueWizard.error_manipulate_message" ), //$NON-NLS-1$
                                    excp );      
    }
  }
  
}
