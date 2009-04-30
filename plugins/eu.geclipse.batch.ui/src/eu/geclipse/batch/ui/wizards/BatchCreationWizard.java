/******************************************************************************
 * Copyright (c) 2007-2008 g-Eclipse consortium
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
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Create new .batch-file. Those files can be used with the BatchEditor
 * (see plugin.xml).
 */
public class BatchCreationWizard extends Wizard implements INewWizard {

  private BatchLocationWizardPage fileCreationPage;
  private BatchDefinitionWizardPage definitionPage;
  private IFile file;
  private IStructuredSelection selection;
  
  /**
   * The default constructor.
   */
  public BatchCreationWizard() {
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
    addPage( this.fileCreationPage );

    addPage( this.definitionPage );
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.jface.wizard.IWizard#getWindowTitle()
   */
  @Override
  public String getWindowTitle() {
    return Messages.getString( "BatchCreationWizard.WindowTitle" ); //$NON-NLS-1$
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
    if ( currentPage != this.fileCreationPage ) {
      result = super.canFinish();
    }
    return result;
  }
   
  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
   *      org.eclipse.jface.viewers.IStructuredSelection)
   */
  public void init( final IWorkbench workbench,
                    final IStructuredSelection sel )  {
    this.selection = sel;
    // Create pages for this wizard
    this.fileCreationPage = new BatchLocationWizardPage( sel );
    this.definitionPage = new BatchDefinitionWizardPage();
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

  /**
   * Returns the grid project
   * 
   * @return IGridProject if exists, <code>null</code> otherwise 
   */
  public IGridProject getGridProject() {
    IGridProject result = null;

    if( null != this.selection ) {
      Object selected = this.selection.getFirstElement();
      if ( selected != null && selected instanceof IGridElement ) {
        result = ((IGridElement)selected).getProject();
      }
    }
    
    return result;
  }
  
  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.jface.wizard.IWizard#performFinish()
   */
  @Override
  public boolean performFinish() {
    boolean ret = false;
    
    if ( this.definitionPage.isInputValid() ) {
      this.file = this.fileCreationPage.createFile();
      try {
        if ( this.definitionPage.finish( this.file ) ) { 
          this.openFile();
          ret = true;
        }
      } catch ( ProblemException e ) {
        // TODO Harald look into
      }
    }
    return ret;
  }  
}
