/*******************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Initial development of
 * the original code was made for the g-Eclipse project founded by European
 * Union project number: FP6-IST-034327 http://www.geclipse.eu/ Contributors:
 * Yifan Zhou - initial API and implementation
 ******************************************************************************/
package eu.geclipse.ui.wizards.deployment;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnectionElement;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.ui.internal.Activator;

/**
 * @author Yifan Zhou
 */
public class DeploymentWizard extends Wizard implements INewWizard {

  /**
   * Get the structured selection.
   */
  private IStructuredSelection selection;
  /**
   * The source page ( first page ) in deployment wizard.
   */
  private DeploymentSource sourcePage;
  /**
   * The target page ( second page ) in deployment wizard.
   */
  private DeploymentTarget targetPage;
  /**
   * The description page ( third page ) in deployment wizard.
   */
  private DeploymentDescription descriptionPage;
  /**
   * The current grid project.
   */
  private IGridElement gridProject;
  /**
   * All referenced projects.
   */
  private IGridElement[] referencedProjects;

  /**
   * Create a wizard.
   */
  public DeploymentWizard() {
    super();
    this.setForcePreviousAndNextButtons( true );
    this.setWindowTitle( Messages.getString( "DeploymentWizard.window_title" ) ); //$NON-NLS-1$
    URL imgUrl = Activator.getDefault()
      .getBundle()
      .getEntry( "icons/wizban/deployment_wiz.gif" ); //$NON-NLS-1$
    this.setDefaultPageImageDescriptor( ImageDescriptor.createFromURL( imgUrl ) );
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.IWorkbenchWizard#init(IWorkbench workbench,
   *      IStructuredSelection selection)
   */
  public void init( final IWorkbench workbench,
                    final IStructuredSelection structuredSelection )
  {
    this.selection = structuredSelection;
    this.referencedProjects = this.fillProjects();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.wizard.Wizard#performFinish()
   */
  @Override
  public boolean performFinish()
  {
    boolean finishing = false;
    IWizardPage currentPage = this.getContainer().getCurrentPage();
    try {
      if( currentPage == this.descriptionPage ) {
          for( IGridElement referencedProject : this.referencedProjects ) {
            if( this.descriptionPage.getMapOfTags()
            .containsKey( referencedProject.getName() ) )
          {
            Object[] targetObjects = this.targetPage.getTargetTree()
              .getCheckedElements();
            for( Object targetObject : targetObjects ) {
              if( targetObject instanceof IGridConnectionElement ) {
                if( ( ( IGridConnectionElement )targetObject ).getConnectionFileStore()
                  .fetchInfo()
                  .isDirectory() )
                {
                  Object[] sourceObjects = this.sourcePage.getSourceTree()
                    .getCheckedElements();
                  List<IGridElement> sourceList = new ArrayList<IGridElement>();
                  for( Object sourceObject : sourceObjects ) {
                    if( !this.sourcePage.getSourceTree()
                      .getChecked( ( ( IGridElement )sourceObject ).getParent() ) )
                    {
                      if( referencedProject.getName()
                        .equals( ( ( IGridElement )sourceObject ).getProject()
                          .getName() ) )
                      {
                        sourceList.add( ( IGridElement )sourceObject );
                      }
                    }
                  }
                  IGridElement[] sources = sourceList.toArray( new IGridElement[ sourceList.size() ] );
                  IFileStore target = createFileStoreByTag( ( IGridElement )targetObject,
                                                            this.descriptionPage.getMapOfTags()
                                                              .get( referencedProject.getName() ) );
                  DeploymentTransfer transfer = new DeploymentTransfer( sources,
                                                                        target,
                                                                        false );
                  if( transfer != null ) {
                    transfer.setUser( true );
                    transfer.schedule();
                  }
                }
              }
            }
          }
        }
      }
      // YIFAN-TODO implement the applicaiton deployment into the computing
      // element
      finishing = true;
    } catch( CoreException e ) {
      Activator.logException( e );
    }
    return finishing;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.wizard.Wizard#canFinish()
   */
  @Override
  public boolean canFinish()
  {
    boolean finished = false;
    IWizardPage currentPage = this.getContainer().getCurrentPage();
    if( currentPage == this.descriptionPage ) {
      finished = true;
    }
    return finished;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.wizard.Wizard#addPages()
   */
  @Override
  public void addPages()
  {
    this.sourcePage = new DeploymentSource( Messages.getString( "DeploymentWizard.deployment_source" ) ); //$NON-NLS-1$
    this.sourcePage.init( this.referencedProjects );
    this.addPage( this.sourcePage );
    this.targetPage = new DeploymentTarget( Messages.getString( "DeploymentWizard.deployment_target" ) ); //$NON-NLS-1$
    this.targetPage.init( this.gridProject );
    this.addPage( this.targetPage );
    this.descriptionPage = new DeploymentDescription( Messages.getString( "DeploymentWizard.deployment_description" ) ); //$NON-NLS-1$
    this.addPage( this.descriptionPage );
  }

  /**
   * Get the selected grid project and its all referenced projects.
   * 
   * @return all referenced projects.
   */
  private IGridElement[] fillProjects() {
    List<IGridElement> elements = new ArrayList<IGridElement>();
    IProject[] refProjects = null;
    IGridElement[] allProjectsInGridView = null;
    this.gridProject = ( IGridElement )this.selection.getFirstElement();
    IProject gProject = ( IProject )this.gridProject.getResource();
    try {
      refProjects = gProject.getReferencedProjects();
      allProjectsInGridView = GridModel.getRoot().getChildren( null );
      for( IGridElement projectInGridView : allProjectsInGridView ) {
        for( IProject referencedProject : refProjects ) {
          if( referencedProject.getName().equals( projectInGridView.getName() ) )
          {
            elements.add( projectInGridView );
          }
        }
      }
    } catch( CoreException e ) {
      Activator.logException( e );
    }
    return ( elements.size() > 0 )
                                  ? elements.toArray( new IGridElement[ elements.size() ] )
                                  : new IGridElement[ 0 ];
  }

  /**
   * Get all referenced projects.
   * 
   * @return All referenced projects.
   */
  public IGridElement[] getReferencedProjects() {
    return this.referencedProjects;
  }

  /**
   * Get the current grid project.
   * 
   * @return The current grid project.
   */
  public IGridProject getGridProject() {
    return ( IGridProject )this.gridProject;
  }

  /**
   * Create a specified file store by tag
   * 
   * @param element the file system.
   * @param tag the name of directory.
   */
  private IFileStore createFileStoreByTag( final IGridElement element,
                                           final String tag )
    throws CoreException
  {
    IFileStore fileStore;
    if( element instanceof IGridConnectionElement ) {
      fileStore = ( ( IGridConnectionElement )element ).getConnectionFileStore();
    } else {
      fileStore = element.getFileStore();
    }
    IFileStore newFolder = fileStore.getChild( tag );
    IFileInfo newFolderInfo = newFolder.fetchInfo();
    boolean isExist = newFolderInfo.exists();
    NullProgressMonitor monitor = new NullProgressMonitor();
    SubProgressMonitor subMonitor = new SubProgressMonitor( monitor, 9 );
    if( !isExist ) {
      newFolder.mkdir( EFS.NONE, new SubProgressMonitor( subMonitor, 1 ) );
    }
    return newFolder;
  }
}
