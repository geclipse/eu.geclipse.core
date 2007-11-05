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
 *****************************************************************************/

package eu.geclipse.core.internal.model;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridJobStatusService;
import eu.geclipse.core.model.IGridJobSubmissionService;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.IWrappedElement;
import eu.geclipse.core.model.impl.AbstractGridContainer;

/**
 * Wrapper of a VO in order to map the VO from the manager to a
 * project.
 */
public class VoWrapper
    extends AbstractGridContainer
    implements IVirtualOrganization, IWrappedElement {
  
  private IGridProject project;
  
  private IVirtualOrganization vo;
  
  protected VoWrapper( final IGridProject project,
                       final IVirtualOrganization vo ) {
    
    super();
    
    this.project = project;
    this.vo = vo;
    
    try {
    
      QueryContainer computingContainer
        = new QueryContainer(
            this,
            "Computing",
            new IQueryInputProvider() {
              public IGridElement[] getInput() throws GridModelException {
                return getComputing();
              }
            } );
      addElement( computingContainer );
    
      QueryContainer storageContainer
      = new QueryContainer(
          this,
          "Storage",
          new IQueryInputProvider() {
            public IGridElement[] getInput() throws GridModelException {
              return getStorage();
            }
          } );
      addElement( storageContainer );
      
      QueryContainer serviceContainer
      = new QueryContainer(
          this,
          "Services",
          new IQueryInputProvider() {
            public IGridElement[] getInput() throws GridModelException {
              return getServices();
            }
          } );
      serviceContainer.setQueryAsChildren( false );
      addElement( serviceContainer );
      
      QueryContainer infoServiceContainer
        = new QueryContainer( serviceContainer, "Info Services" );
      infoServiceContainer.addFilter(
        new ClassTypeQueryFilter( IGridInfoService.class, true )
      );
      
      QueryContainer jobStatusServiceContainer
        = new QueryContainer( serviceContainer, "Job Status Services" );
      jobStatusServiceContainer.addFilter(
        new ClassTypeQueryFilter( IGridJobStatusService.class, true )
      );
      
      QueryContainer jobSubmissionServiceContainer
        = new QueryContainer( serviceContainer, "Job Submission Services" );
      jobSubmissionServiceContainer.addFilter(
        new ClassTypeQueryFilter( IGridJobSubmissionService.class, true )
      );
      
      QueryContainer otherServiceContainer
      = new QueryContainer( serviceContainer, "Other Services" );
      otherServiceContainer.addFilter(
        new ClassTypeQueryFilter( IGridInfoService.class, false )
      );
      otherServiceContainer.addFilter(
        new ClassTypeQueryFilter( IGridJobStatusService.class, false )
      );
      otherServiceContainer.addFilter(
        new ClassTypeQueryFilter( IGridJobSubmissionService.class, false )
      );
      
    } catch ( GridModelException gmExc ) {
      Activator.logException( gmExc );
    }
    
  }
  
  @Override
  public boolean canContain( final IGridElement element ) {
    return element instanceof QueryContainer;
  }

  public String getTypeName() {
    return this.vo.getTypeName();
  }

  @Override
  public void dispose() {
    this.vo.dispose();
  }
  
  public IGridComputing[] getComputing()
      throws GridModelException {
    IGridComputing[] computing = this.vo.getComputing();
    if ( computing != null ) {
      for ( int i = 0 ; i < computing.length ; i++ ) {
        computing[ i ] = new ComputingWrapper( this, computing[ i ] );
      }
    }
    return computing;
  }

  public IFileStore getFileStore() {
    return getProject().getFileStore().getChild( getName() );
  }
  
  public IGridInfoService getInfoService()
      throws GridModelException {
    return this.vo.getInfoService();
  }

  public String getName() {
    return this.vo.getName();
  }

  public IGridContainer getParent() {
    return this.project;
  }

  public IPath getPath() {
    return this.project.getPath().append( getName() );
  }

  @Override
  public IGridProject getProject() {
    return this.project;
  }
  
  public IResource getResource() {
    return null;
  }
  
  public IGridService[] getServices()
      throws GridModelException {
    IGridService[] services = this.vo.getServices();
    if ( services != null ) {
      for ( int i = 0 ; i < services.length ; i++ ) {
        services[ i ] = new ServiceWrapper( this, services[ i ] );
      }
    }
    return services;
  }
  
  public IGridStorage[] getStorage()
      throws GridModelException {
    IGridStorage[] storage = this.vo.getStorage();
    if ( storage != null ) {
      for ( int i = 0 ; i < storage.length ; i++ ) {
        storage[ i ] = new StorageWrapper( this, storage[ i ] );
      }
    }
    return storage;
  }
  
  public IGridJobSubmissionService[] getJobSubmissionServices()
      throws GridModelException {
    return this.vo.getJobSubmissionServices();
  }
  
  public IGridElement getWrappedElement() {
    return this.vo;
  }
  
  public boolean isLazy() {
    return false;
  }

  public boolean isLocal() {
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Object getAdapter( final Class adapter ) {
    return this.vo.getAdapter( adapter );
  }

  public void load() throws GridModelException {
    // TODO mathias
  }

  public void save() throws GridModelException {
    // TODO mathias
  }
  
  @Override
  public void setDirty() {
    try {
      for ( IGridElement child : getChildren( null ) ) {
        if ( child instanceof IGridContainer ) {
          ( ( IGridContainer ) child ).setDirty();
        }
      }
    } catch ( GridModelException gmExc ) {
      // Should never happen, if it does we will at least log it
      Activator.logException( gmExc );
    }
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IVirtualOrganization#getWizardId()
   */
  public String getWizardId() {
    return this.vo.getWizardId();
  }
}
