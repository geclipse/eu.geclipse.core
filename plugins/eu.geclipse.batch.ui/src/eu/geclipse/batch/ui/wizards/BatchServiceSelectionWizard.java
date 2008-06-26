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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.wizards;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import eu.geclipse.batch.ui.BatchServiceManager;
import eu.geclipse.batch.ui.internal.Messages;
import eu.geclipse.core.model.IGridBatchQueueDescription;



/**
 * @author nloulloud
 *
 */
public class BatchServiceSelectionWizard extends Wizard implements INewWizard {

  protected List<IGridBatchQueueDescription> queueDescriptions;
  private BatchServiceSelectionWizardPage wizardPage;
  
  /**
   * 
   * The default Class Constructor
   * 
   * Access as parameter the list of {@link IGridBatchQueueDescription}.
   * @param queueDescrList
   */
  public BatchServiceSelectionWizard(final List<IGridBatchQueueDescription> queueDescrList) {
    this.queueDescriptions = queueDescrList;
    setNeedsProgressMonitor( true );    
    setWindowTitle( Messages.getString("BatchServiceSelectionDialog.title") ); //$NON-NLS-1$
    
  }
  
  @Override
  public void addPages() {
    this.wizardPage = 
      new BatchServiceSelectionWizardPage( Messages.getString( "BatchServiceSelectionDialog.page" ) ); //$NON-NLS-1$
    this.wizardPage.setInitialData( this.queueDescriptions );
    addPage( this.wizardPage );    

  }
  
  @Override
  public boolean canFinish() {
    boolean result = false;
    
    int serviceCount = BatchServiceManager.getManager().getServiceCount();
    
    /* 
     * If there are running Batch Service instances then perform Finish and 
     * apply the Queue Configurations else prompt an error message.
     */
    if (serviceCount > 0 ) {
      if ( this.wizardPage.isPageComplete() ) {
        result = super.canFinish();
      }
    }
    else {      
      this.wizardPage.setErrorMessage( 
                         Messages.getString( "BatchServiceSelectionDialog.NoRunningServices.Error" ) ); //$NON-NLS-1$
    }
    return result;
  }  
  
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    
    return this.wizardPage.finish();
  }

  public void init( final IWorkbench workbench, final IStructuredSelection selection ) {
    // Auto-generated method stub
    
  }
}
