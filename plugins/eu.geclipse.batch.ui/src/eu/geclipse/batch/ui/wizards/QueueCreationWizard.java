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
 *     UCY (http://www.ucy.cs.ac.cy)
 *      - Nicholas Loulloudes (loulloudes.n@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

import eu.geclipse.batch.ui.internal.Activator;
import eu.geclipse.batch.ui.internal.Messages;



/**
 * @author nickl
 *
 */
public class QueueCreationWizard extends Wizard implements INewWizard {

  private QueueLocationWizard queueFileLocationPage;
  private AddQueueWizardRequiredPage requiredPage;
//  private AddQueueWizardOptionalPage optionalPage;  
  private IFile file;
  
  
  
  /**
   * The default constructor.
   */
  public QueueCreationWizard() {
    setNeedsProgressMonitor( true );
  }
  
  
  
  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.jface.wizard.IWizard#addPages()
   */
  @Override
  public void addPages() {
    // Add pages to this wizard
    addPage( this.queueFileLocationPage );
    addPage( this.requiredPage );
//    this.addPage( this.optionalPage );
    
  }
  
  
  
  /* (non-Javadoc)
   * @see org.eclipse.jface.wizard.Wizard#getWindowTitle()
   */
  @Override
  public String getWindowTitle() {
    return Messages.getString( "AddQueueWizard.Title" ); //$NON-NLS-1$
  }
  
  
  
  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.jface.wizard.IWizard#canFinish()
   */
  @Override
  public boolean canFinish() {
    boolean result = false;
    IWizardPage currentPage = getContainer().getCurrentPage();
    if ( currentPage != this.queueFileLocationPage ) {
      result = super.canFinish();
    }
    return result;
  }
 
   
  private void openFile() {
    try {
      IDE.openEditor( Activator.getDefault()
        .getWorkbench()
        .getActiveWorkbenchWindow()
        .getActivePage(), this.file, true );
    } catch( PartInitException partInitException ) {
      Activator.logException( partInitException );
    }
  }
  
  
  /* (non-Javadoc)
   * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
   */
  public void init( final IWorkbench workbench, final IStructuredSelection selection ) {
    this.queueFileLocationPage = new QueueLocationWizard(selection);
    this.requiredPage = new AddQueueWizardRequiredPage();

  }
  
  
  @Override
  public boolean performFinish() {
   
    boolean ret = false;
       
      this.file = this.queueFileLocationPage.createFile();
      try {
        if ( this.requiredPage.finish( this.file ) ) { 
          this.openFile();
          ret = true;
        }
      } catch( Exception e ) {
        Activator.logException( e );
      }
    
    return ret;
  }  
  
  

  
  

}
