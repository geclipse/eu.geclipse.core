/******************************************************************************
 * Copyright (c) 2006 - 2008 g-Eclipse consortium
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
 *     Pawel Wolniewicz - PSNC
 *****************************************************************************/
package eu.geclipse.ui.wizards.jobsubmission;

import java.net.URL;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.Wizard;

import eu.geclipse.core.model.IGridJobCreator;
import eu.geclipse.core.model.IGridJobDescription;
import eu.geclipse.core.model.ILocalFolder;
import eu.geclipse.ui.internal.Activator;

public class JobCreatorSelectionWizard extends Wizard {

  protected List<IGridJobDescription> jobDescriptions;
  private final List<IGridJobCreator> jobCreators;
  private JobCreatorSelectionWizardPage selectionPage;
  private FolderSelectionWizardPage folderSelection;

  public JobCreatorSelectionWizard( final List<IGridJobDescription> jobDescriptions,
                                    final List<IGridJobCreator> jobCreators )
  {
    this.jobDescriptions = jobDescriptions;
    this.jobCreators = jobCreators;
    setNeedsProgressMonitor( true );
    setForcePreviousAndNextButtons( true );
    setWindowTitle( Messages.getString( "JobCreatorSelectionWizard.title" ) ); //$NON-NLS-1$
    URL imgUrl = Activator.getDefault()
      .getBundle()
      .getEntry( "icons/wizban/jobsubmit_wiz.gif" ); //$NON-NLS-1$
    setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }

  List<IGridJobCreator> getJobCreators() {
    return this.jobCreators;
  }
  
  public List<String> getJobNames(){
    return this.folderSelection.getJobNames();
  }
  
  public IResource getDestinationFolder(){
    return this.folderSelection.getDestinationFolder();
  }

  @Override
  public void addPages() {
    this.folderSelection = new FolderSelectionWizardPage( "name",
                                                          this.jobDescriptions.get( 0 )
                                                            .getProject(),
                                                          this.jobDescriptions );
    addPage( this.folderSelection );
    this.selectionPage = new JobCreatorSelectionWizardPage( this );
    this.selectionPage.setInitData( this.jobDescriptions );
    addPage( this.selectionPage );
  }

  @Override
  public boolean performFinish() {
    return false;
  }

  public void changeInitData() {
    WrapperInitObject initObject = new WrapperInitObject(this.jobDescriptions, this.folderSelection.getJobNames(), this.folderSelection.getDestinationFolder());
    this.selectionPage.setInitData( initObject );
  }
}
