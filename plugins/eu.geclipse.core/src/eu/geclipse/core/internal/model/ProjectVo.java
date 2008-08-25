/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
import org.eclipse.core.runtime.IProgressMonitor;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.IGridApplicationManager;
import eu.geclipse.core.model.IGridComputing;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridInfoService;
import eu.geclipse.core.model.IGridJobService;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridResource;
import eu.geclipse.core.model.IGridResourceCategory;
import eu.geclipse.core.model.IGridService;
import eu.geclipse.core.model.IGridStorage;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.IWrappedElement;
import eu.geclipse.core.model.impl.AbstractGridContainer;
import eu.geclipse.core.model.impl.GridResourceCategoryFactory;
import eu.geclipse.core.reporting.ProblemException;


/**
 * Wrapper of a VO in order to map the VO from the manager to a
 * project.
 */
public class ProjectVo
    extends AbstractGridContainer
    implements IVirtualOrganization, IWrappedElement {
  
  /**
   * Definition of standard categories that are used whenever a VO does not specify
   * dedicated categories.
   */
  public static final IGridResourceCategory[] standardCategories
    = new IGridResourceCategory[] {
      GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_APPLICATIONS ),
      GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_COMPUTING ),
      GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_DATA_SERVICES ),
      GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_INFO_SERVICES ),
      GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_JOB_SERVICES ),
      GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_OTHER_SERVICES ),
      GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_STORAGE )
  };
  
  private static final String NA_STRING = "N/A"; //$NON-NLS-1$
  
  private IGridProject project;
  
  private String voName;
  
  protected ProjectVo( final IGridProject project,
                       final IVirtualOrganization vo ) {
    
    super();
    
    this.project = project;
    this.voName = vo.getName();
    
    IGridResourceCategory[] supportedCategories = vo.getSupportedCategories();
    
    for ( IGridResourceCategory category : supportedCategories ) {
      try {
        getResourceContainer( category );
      } catch ( ProblemException pExc ) {
        Activator.logException( pExc );
      }
    }
    
  }
  
  @Override
  public boolean canContain( final IGridElement element ) {
    return element instanceof ResourceCategoryContainer; // QueryContainer;
  }

  public String getTypeName() {
    IVirtualOrganization vo = getSlave();
    return vo != null ? vo.getTypeName() : NA_STRING;
  }
  
  public IGridApplicationManager getApplicationManager() {
    IVirtualOrganization vo = getSlave();
    return vo != null ? vo.getApplicationManager() : null;
  }
  
  public IGridResource[] getAvailableResources( final IGridResourceCategory category,
                                                final boolean exclusive,
                                                final IProgressMonitor monitor )
      throws ProblemException {
    IGridResource[] result = null;
    IVirtualOrganization vo = getSlave();
    if ( vo != null ) {
      IGridInfoService infoService = vo.getInfoService();
      if ( infoService != null ) {
        result = infoService.fetchResources( this, vo, category, false, null, monitor );
      }
    }
    return result;
  }

  public IGridComputing[] getComputing( final IProgressMonitor monitor )
      throws ProblemException {
    
    IGridComputing[] result = null;
    IVirtualOrganization vo = getSlave();
    
    if ( vo != null ) {
      
      IGridInfoService infoService = vo.getInfoService();
      IGridResource[] resources = infoService.fetchResources(
          this,
          vo,
          GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_COMPUTING ),
          false,
          IGridComputing.class,
          monitor );
      
      if ( resources != null ) {
        result = new IGridComputing[ resources.length ];
        for ( int i = 0 ; i < resources.length ; i++ ) {
          result[ i ] = ( IGridComputing ) resources[ i ];
        }
      }
      
    }
    
    return result;
    
  }

  public IFileStore getFileStore() {
    return getProject().getFileStore().getChild( getName() );
  }
  
  public IGridInfoService getInfoService()
      throws ProblemException {
    IVirtualOrganization vo = getSlave();
    return vo != null ? vo.getInfoService() : null;
  }

  public String getName() {
    IVirtualOrganization vo = getSlave();
    return vo != null ? vo.getName() : "Vo-Wrapper"; //$NON-NLS-1$
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
  
  public IGridService[] getServices( final IProgressMonitor monitor )
      throws ProblemException {
    
    IGridService[] result = null;
    IVirtualOrganization vo = getSlave();
    
    if ( vo != null ) {
      
      IGridInfoService infoService = vo.getInfoService();
      IGridResource[] resources = infoService.fetchResources(
          this,
          vo,
          GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_SERVICES ),
          false,
          IGridService.class,
          monitor );
      
      if ( resources != null ) {
        result = new IGridService[ resources.length ];
        for ( int i = 0 ; i < resources.length ; i++ ) {
          result[ i ] = ( IGridService ) resources[ i ];
        }
      }
      
    }
    
    return result;
    
  }
  
  public IGridStorage[] getStorage( final IProgressMonitor monitor )
      throws ProblemException {
    
    IGridStorage[] result = null;
    IVirtualOrganization vo = getSlave();
    
    if ( vo != null ) {
      
      IGridInfoService infoService = vo.getInfoService();
      IGridResource[] resources = infoService.fetchResources(
          this,
          vo,
          GridResourceCategoryFactory.getCategory( GridResourceCategoryFactory.ID_STORAGE ),
          false,
          IGridStorage.class,
          monitor );
      
      if ( resources != null ) {
        result = new IGridStorage[ resources.length ];
        for ( int i = 0 ; i < resources.length ; i++ ) {
          result[ i ] = ( IGridStorage ) resources[ i ];
        }
      }
      
    }
    
    return result;
    
  }
  
  public IGridResourceCategory[] getSupportedCategories() {
    IVirtualOrganization vo = getSlave();
    return vo != null ? vo.getSupportedCategories() : standardCategories;
  }
  
  public IGridJobService[] getJobSubmissionServices( final IProgressMonitor monitor )
      throws ProblemException {
    IVirtualOrganization vo = getSlave();
    return vo != null ? vo.getJobSubmissionServices( monitor ) : new IGridJobService[ 0 ];
  }
  
  public IGridElement getWrappedElement() {
    return getSlave();
  }
  
  public boolean isLazy() {
    return false;
  }

  public boolean isLocal() {
    return true;
  }

  @SuppressWarnings( "unchecked" )
  @Override
  public Object getAdapter( final Class adapter ) {
    IVirtualOrganization vo = getSlave();
    return vo != null ? vo.getAdapter( adapter ) : null;
  }

  public void load() throws ProblemException {
    // TODO mathias
  }
  
  public void refreshResources( final IGridResourceCategory category,
                                final IProgressMonitor monitor )
      throws ProblemException {
    
    ResourceCategoryContainer container = getResourceContainer( category );
    
    if ( container != null ) {
      container.refresh( monitor );
    }
    
  }

  public void save() throws ProblemException {
    // Empty implementation
  }
  
  @Override
  public void setDirty() {
    try {
      for ( IGridElement child : getChildren( null ) ) {
        if ( child instanceof IGridContainer ) {
          ( ( IGridContainer ) child ).setDirty();
        }
      }
    } catch ( ProblemException pExc ) {
      // Should never happen, if it does we will at least log it
      Activator.logException( pExc );
    }
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IVirtualOrganization#getWizardId()
   */
  public String getWizardId() {
    IVirtualOrganization vo = getSlave();
    return vo != null ? vo.getWizardId() : null;
  }
  
  protected IVirtualOrganization getSlave() {
    IVirtualOrganization result
      = ( IVirtualOrganization ) VoManager.getManager().findChild( this.voName );
    return result;
  }
  
  private ResourceCategoryContainer getResourceContainer( final IGridResourceCategory category )
      throws ProblemException {
    
    ResourceCategoryContainer result = null;
    
    String name = category.getName();
    IGridResourceCategory parentCategory = category.getParent();
    
    IGridContainer parentContainer
      = parentCategory == null
      ? this
      : getResourceContainer( parentCategory );
    
    IGridElement child = parentContainer.findChild( name );
    
    if ( ( child == null ) || ! ( child instanceof ResourceCategoryContainer ) ) {
      result = new ResourceCategoryContainer( parentContainer, category );
      if ( parentContainer == this ) {
        addElement( result );
      } else {
        ( ( ResourceCategoryContainer ) parentContainer ).addChild( result );
      }
    } else {
      result = ( ResourceCategoryContainer ) child;
    }
    
    return result;
    
  }

  public String getId() {
    IVirtualOrganization vo = getSlave();
    return vo != null ? vo.getId() : NA_STRING;
  }
  
}
