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
 *    Yifan Zhou - initial API and implementation
 *****************************************************************************/

package eu.geclipse.ui.wizards.deployment;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import eu.geclipse.core.IApplicationDeployment;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.ui.internal.Activator;

/**
 * @author Yifan Zhou
 */
public class DeploymentWizard extends Wizard {
  
  public static final String EXT_CLASS = "class"; //$NON-NLS-1$
  
  public static final String EXT_NAME = "name"; //$NON-NLS-1$
  
  public static final String EXT_ID = "id"; //$NON-NLS-1$
  
  public static final String EXT_CATEGORY = "category"; //$NON-NLS-1$

  /**
   * The chooser page ( first page ) in deployment wizard.
   */
  private DeploymentChooser chooserPage;
  /**
   * The source page ( two page ) in deployment wizard.
   */
  private DeploymentSource sourcePage;
  /**
   * The target page ( three page ) in deployment wizard.
   */
  private DeploymentTarget targetPage;
  /**
   * The description page ( four page ) in deployment wizard.
   */
  private DeploymentDescription descriptionPage;
  /**
   * Get the structured selection.
   */
  private IStructuredSelection selection;
  /**
   * The current grid project.
   */
  private IGridElement trueGridProject;
  /**
   * All referenced projects.
   */
  private IGridElement[] referencedProjects;
  
  public DeploymentWizard( final IStructuredSelection structuredSelection ) {
    super();
    this.setForcePreviousAndNextButtons( true );
    this.setWindowTitle( Messages.getString( "Deployment.deployment_wizard_window_title" ) ); //$NON-NLS-1$
    URL imgUrl = Activator.getDefault().getBundle().getEntry( "icons/wizban/newtoken_wiz.gif" ); //$NON-NLS-1$
    this.setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
    this.selection = structuredSelection;
    this.initProjects();
  }

  @Override
  public void addPages() {
    this.chooserPage = new DeploymentChooser( Messages.getString( "Deployment.deployment_wizard_chooser" ) ); //$NON-NLS-1$
    this.addPage( this.chooserPage );
    this.sourcePage = new DeploymentSource( Messages.getString( "Deployment.deployment_wizard_source" ) ); //$NON-NLS-1$
    this.addPage( this.sourcePage );
    this.targetPage = new DeploymentTarget( Messages.getString( "Deployment.deployment_wizard_target" ) ); //$NON-NLS-1$
    this.addPage( this.targetPage );
    this.descriptionPage = new DeploymentDescription( Messages.getString( "Deployment.deployment_wizard_description" ) ); //$NON-NLS-1$
    this.addPage( this.descriptionPage );
  }

  @Override
  public boolean performFinish() {
    Object[] sourceObjects = this.sourcePage.getSourceTree().getCheckedElements();
    List< IGridElement > sourceList = new ArrayList< IGridElement >();
    for ( Object  object : sourceObjects ) {
      IGridElement element = ( IGridElement ) object;
      if ( !this.sourcePage.getSourceTree().getChecked( element.getParent() ) ) {
        sourceList.add( element );
      }
    }
    IGridElement[] source = sourceList.toArray( new IGridElement[ sourceList.size() ] );
    List< Object > targetList = new ArrayList< Object >();
    Object[] ceObjects = this.targetPage.getCETree().getCheckedElements();
    Object[] seObjects = this.targetPage.getSETree().getCheckedElements();
    for ( Object ceObject : ceObjects ) {
      targetList.add( ceObject );
    }
    for ( Object seObject : seObjects ) {
      targetList.add( seObject );
    }
    IGridElement[] target = targetList.toArray( new IGridElement[ targetList.size() ] );
    String tag = this.descriptionPage.getTag();
    IApplicationDeployment appDeployment = this.chooserPage.getExecuteExt();
    DeploymentJob job 
      = new DeploymentJob( Messages.getString( "Deployment.deployment_wizard_job" ),  //$NON-NLS-1$
                           appDeployment,
                           source, 
                           target,
                           tag );
    job.setPriority( Job.SHORT );
    job.schedule();
    return true;
  }

  @Override
  public boolean canFinish() {
    boolean finished = false;
    IWizardPage currentPage = this.getContainer().getCurrentPage();
    if ( currentPage == this.descriptionPage ) {
      finished = true;
    }
    return finished;
  }

  protected void initProjects() {
    List< IGridElement > refProjects = new ArrayList< IGridElement >();
    IGridElement[] allProjects = null;
    IProject[] rProjects = null;
    this.trueGridProject = ( IGridElement ) this.selection.getFirstElement();
    IProject gProject = ( IProject ) this.trueGridProject.getResource();
    try {
      rProjects = gProject.getReferencedProjects();
      allProjects = GridModel.getRoot().getChildren( null );
      for ( IGridElement project : allProjects ) {
        for ( IProject rProject : rProjects ) {
          if ( rProject.getName().equals( project.getName() ) ) {
            refProjects.add( project );
          }
        }
      }
    } catch( CoreException e ) {
      Activator.logException( e );
    }
    if ( refProjects.size() > 0 ) {
      this.referencedProjects = refProjects.toArray( new IGridElement[ refProjects.size() ] );
    } else {
      this.referencedProjects = new IGridElement[ 0 ];
    }
  }
  
//  private IFileStore createFileStoreByTag( final IGridElement element,
//                                           final String tag )
//    throws CoreException
//  {
//    IFileStore fileStore;
//    if ( element instanceof IGridConnectionElement ) {
//      fileStore = ( ( IGridConnectionElement )element ).getConnectionFileStore();
//    } else {
//      fileStore = element.getFileStore();
//    }
//    IFileStore newFolder = fileStore.getChild( tag );
//    IFileInfo newFolderInfo = newFolder.fetchInfo();
//    boolean isExist = newFolderInfo.exists();
//    NullProgressMonitor monitor = new NullProgressMonitor();
//    SubProgressMonitor subMonitor = new SubProgressMonitor( monitor, 9 );
//    if ( !isExist ) {
//      newFolder.mkdir( EFS.NONE, new SubProgressMonitor( subMonitor, 1 ) );
//    }
//    return newFolder;
//  }

  public IGridElement[] getReferencedProjects() {
    return this.referencedProjects;
  }

  public IGridElement getGridProject() {
    return this.trueGridProject;
  }
  
}
