/*****************************************************************************
 * Copyright (c) 2006, 2007, 2008 g-Eclipse Consortium 
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
 *    Jie Tao -- extensions
 *****************************************************************************/

package eu.geclipse.ui.wizards.deployment;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.ui.internal.Activator;


/**
 * @author Yifan Zhou
 */
public class DeploymentWizard extends Wizard {
  
  /**
   * The source page ( two page ) in deployment wizard.
   */
  public DeploymentSource sourcePage;
  /**
   * The target page ( three page ) in deployment wizard.
   */
  public DeploymentTarget targetPage;
  
  /**
   * The script page ( three page ) in deployment wizard.
   */
  public DeploymentScript scriptPage;
  
  /**
   * The description page ( four page ) in deployment wizard.
   */
  public DeploymentDescription descriptionPage;
  /**
   * Get the structured selection.
   */
  private IStructuredSelection selection;
 
  /**
   * The current grid project.
   */
  private IGridProject gridproject;
  /**
   * All referenced projects.
   */
  private IGridElement[] referencedProjects;
  
  /**the wizard for acquiring source files and target CEs
   * @param structuredSelection
   */
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
    //this.chooserPage = new DeploymentChooser( Messages.getString( "Deployment.deployment_wizard_chooser" ) ); //$NON-NLS-1$
    //this.addPage( this.chooserPage );
    this.targetPage = new DeploymentTarget( Messages.getString( "Deployment.deployment_wizard_target" ) ); //$NON-NLS-1$
    this.addPage( this.targetPage );
    this.sourcePage = new DeploymentSource( Messages.getString( "Deployment.deployment_wizard_source" ) ); //$NON-NLS-1$
    this.addPage( this.sourcePage );
    this.scriptPage = new DeploymentScript("Install script"); //$NON-NLS-1$
    this.addPage( this.scriptPage );
    this.descriptionPage = new DeploymentDescription( Messages.getString( "Deployment.deployment_wizard_description" ) ); //$NON-NLS-1$
    this.addPage( this.descriptionPage );
  }

  @Override
  public boolean performFinish() {
   
    URI [] source = this.sourcePage.getSourceURIs();
    URI script = this.scriptPage.getScript();
    List< Object > targetList = new ArrayList< Object >();
    Object[] ceObjects = this.targetPage.getCETree().getCheckedElements();
    IVirtualOrganization vo = this.gridproject.getVO();
    
    for ( Object ceObject : ceObjects ) {
      targetList.add( ceObject );
    }
 
   IGridComputing[] target = targetList.toArray( new IGridComputing[ targetList.size() ] );
    String tag = this.descriptionPage.getTag();
    DeploymentJob job 
      = new DeploymentJob( Messages.getString( "Deployment.deployment_wizard_job" ),  //$NON-NLS-1$
                           vo,
                           source, 
                           target,
                           script,
                           tag );
    //job.setPriority( Job.SHORT );
    job.setUser( true );
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

  /**
   * set the reference to a grid project
   */
  public void initProjects() {
    List< IGridElement > refProjects = new ArrayList< IGridElement >();
    IGridElement[] allProjects = null;
    IProject[] rProjects = null;
    IGridElement selected = ( IGridElement ) this.selection.getFirstElement();
    this.gridproject = selected.getProject();
    IResource resource = this.gridproject.getResource();
    IProject gProject = resource.getProject();
  
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

  /**get the referenced project. A project to be deployed 
   * must be combined with a grid project
   * @return IGridElement[]
   */
  public IGridElement[] getReferencedProjects() {
    return this.referencedProjects;
  }

  /** return the grid project
   * @return IGridProject
   */
  public IGridProject getGridProject() {
    return this.gridproject;
  }
  
  /**get the user selection of PEs or vo
   * @return IStructuredSelection
   */
  public IStructuredSelection getSelection() {
    return this.selection;
  }
  
}
