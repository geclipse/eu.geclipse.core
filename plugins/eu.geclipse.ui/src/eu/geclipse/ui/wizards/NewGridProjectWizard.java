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
 *    Mathias Stuempert - initial API and implementation
 *    Harald Kornmayer 
 *****************************************************************************/

package eu.geclipse.ui.wizards;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.dialogs.WizardNewProjectReferencePage;
import eu.geclipse.ui.internal.Activator;

/**
 * Wizard to create Grid Project in the Eclipse workspace. 
 * 
 * @author korn
 */

public class NewGridProjectWizard extends Wizard implements INewWizard {
  
  private WizardNewProjectCreationPage creationPage;
  
  private VoSelectionWizardPage voPage;
  
  private GridProjectStructureWizardPage structurePage;
  
  private WizardNewProjectReferencePage referencePage;
  
  private IProject project = null;

  /**
   *  when the wizard is finished, the Grid project is created. 
   */
  @Override
  public boolean performFinish() {
    IProject proj = createNewProject();
    return proj != null;
  }

  /**
   * Initialize the Grid project in the Grid project in the workspace
   */
  public void init( final IWorkbench workbench, final IStructuredSelection selection ) {
    setWindowTitle( Messages.getString("NewGridProjectWizard.windowTitle") ); //$NON-NLS-1$
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/wizban/newgridprj_wiz.gif" ); //$NON-NLS-1$
    setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }
  
  /**
   * Adds the different pages for the wizard i.e. for VO selection, Project Structure,
   * reference projects.  
   */
  @Override
  public void addPages() {
    
    this.creationPage = new WizardNewProjectCreationPage( "newGridProjectBasicPage" ); //$NON-NLS-1$
    this.creationPage.setTitle( Messages.getString( "NewGridProjectWizard.basic_page_title" ) ); //$NON-NLS-1$
    this.creationPage.setDescription( Messages.getString( "NewGridProjectWizard.basic_page_description" ) ); //$NON-NLS-1$
    addPage( this.creationPage );
    
    this.voPage = new VoSelectionWizardPage( false );
    addPage( this.voPage );
    
    this.structurePage = new GridProjectStructureWizardPage();
    addPage( this.structurePage );
    
    if ( ResourcesPlugin.getWorkspace().getRoot().getProjects().length > 0 ) {
      this.referencePage = new WizardNewProjectReferencePage( "newGridProjectReferencePage" ); //$NON-NLS-1$
      this.referencePage.setTitle( Messages.getString( "NewGridProjectWizard.reference_page_title" ) ); //$NON-NLS-1$
      this.referencePage.setDescription( Messages.getString( "NewGridProjectWizard.reference_page_description" ) ); //$NON-NLS-1$
      addPage( this.referencePage );
    }
    
  }
  
  @Override
  public boolean canFinish() {
    boolean result = false;
    IWizardPage currentPage = getContainer().getCurrentPage();
    if ( currentPage != this.creationPage ) {
      result = super.canFinish();
    }
    return result;
  }
  
  /**
   * Creates the Grid Project in the workspace and 
   * @return
   */
  private IProject createNewProject() {
    
    if ( ( this.project == null ) && ( this.creationPage != null ) ) {
    
      GridProjectProperties properties = new GridProjectProperties();
      
      properties.setProjectName( this.creationPage.getProjectName() );
      properties.setProjectLocation( this.creationPage.useDefaults() ? null : this.creationPage.getLocationPath() );
      properties.setProjectVo( this.voPage.getSelectedVos()[0] );
      
      properties.addProjectFolders( this.structurePage.getProjectFolders() );
      
      if ( this.referencePage != null ) {
        properties.setReferencesProjects( this.referencePage.getReferencedProjects() );
      }
      
      GridProjectCreationOperation creationOp = new GridProjectCreationOperation( properties );
      WorkspaceModifyDelegatingOperation modifyOp = new WorkspaceModifyDelegatingOperation( creationOp );
      
      // Create the new project
      try {
        getContainer().run( true, true, modifyOp );
        this.project = creationOp.getProject();
      } catch ( InterruptedException intExc ) {
        eu.geclipse.ui.internal.Activator.logException( intExc );
      } catch ( InvocationTargetException itExc ) {
        eu.geclipse.ui.internal.Activator.logException( itExc );
      } 
    }
    return this.project; 
  }
}
