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

import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.osgi.framework.Bundle;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.internal.model.notify.GridModelEvent;
import eu.geclipse.core.internal.model.notify.GridNotificationService;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridModelEvent;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.ResourceGridContainer;
import eu.geclipse.core.project.GridProjectNature;
import eu.geclipse.core.reporting.ProblemException;


/**
 * This is the grid model class that represents any project in the
 * workspace. This has not to be necessarily a grid project. Use the
 * {@link #hasGridNature()} method to determine if this is really a
 * grid project.
 */
public class GridProject
    extends ResourceGridContainer
    implements IGridProject {
  
  /**
   * ID of the preference node for grid projects.
   */
  public static final String PROJECT_NODE = "eu.geclipse.core"; //$NON-NLS-1$
  
  /**
   * ID of the preference folder node for grid projects.
   */
  public static final String PROJECT_FOLDER_NODE = "eu.geclipse.core.folders"; //$NON-NLS-1$
  
  private static final String VO_ATTRIBUTE = "vo"; //$NON-NLS-1$
  
  private static final QualifiedName PROJECT_FOLDER_ID_QN
    = new QualifiedName( Activator.PLUGIN_ID, "grid.project.folder.id" ); //$NON-NLS-1$
  
  private ProjectVo vo;
  
  /**
   * Default constructor that constructs a grid project out of an
   * ordinary <code>IProject</code> object.
   *  
   * @param project The <code>IProject</code> that corresponds to this
   * grid project.
   */
  protected GridProject( final IProject project ) {
    super( project );
    loadProjectProperties( project );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#canContain(eu.geclipse.core.model.IGridElement)
   */
  @Override
  public boolean canContain( final IGridElement element ) {
    return true;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#getChildren(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public IGridElement[] getChildren( final IProgressMonitor monitor )
      throws ProblemException {
    IGridElement[] children = new IGridElement[0];
    if ( isOpen() ) {
      children = super.getChildren( monitor );
      if ( ! hasVo( children ) ) {
        loadProjectProperties( ( IProject ) getResource() );
        children = super.getChildren( monitor );
      }
    }
    return children;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#getChildCount()
   */
  @Override
  public int getChildCount() {
    int result = 0;
    if ( isOpen() ) {
      result = super.getChildCount();
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridElement#getProject()
   */
  @Override
  public IGridProject getProject() {
    return this;
  }
  
  public IGridContainer getProjectFolder( final Class< ? extends IGridElement > elementType ) {
    
    IGridContainer result = this;
    
    ProjectScope projectScope = new ProjectScope( ( IProject ) getResource() );
    Preferences folderNode = projectScope.getNode( PROJECT_FOLDER_NODE );
    
    ExtensionManager extm = new ExtensionManager();
    List< IConfigurationElement > configurationElements
      = extm.getConfigurationElements( Extensions.PROJECT_FOLDER_POINT, Extensions.PROJECT_FOLDER_ELEMENT );
    
    for( IConfigurationElement element : configurationElements ) {
      
      String id = element.getAttribute( Extensions.PROJECT_FOLDER_ID_ATTRIBUTE );
      String label = folderNode.get( id, null );
      
      if ( label != null ) {
        String className = element.getAttribute( Extensions.PROJECT_FOLDER_ELEMENTCLASS_ATTRIBUTE );
        String contributor = element.getContributor().getName();
        Class<?> elementClass = null;
        Bundle bundle = Platform.getBundle( contributor );    
        try {
           elementClass = bundle.loadClass( className );
        } catch( ClassNotFoundException exception ) {
          try {
            elementClass = Class.forName( className );
          } catch ( ClassNotFoundException cnfExc ) {
            Activator.logException( cnfExc );
          }
        }
        
        if( elementClass != null ) {
          if ( elementClass.isAssignableFrom( elementType ) ) {
            result = getProjectFolder( label );
//            break;
          }
        }        
      }      
    }
    
    return result;
    
  }
  
  private LocalFolder getProjectFolder( final String name ) {
    
    LocalFolder result = null;
    
    IGridElement child = findChild( name );
    
    if ( child == null ) {
      IProject project = ( IProject ) getResource();
      IFolder folder = project.getFolder( name );
      if ( ! folder.exists() ) {
        try {
          folder.create( IResource.FORCE, true, null );
          result = getProjectFolder( name );
        } catch (CoreException e) {
          // Do nothing, just ignore
        }
      }
    } else if ( child instanceof LocalFolder ) {
      result = ( LocalFolder ) child;
    }
    
    return result;
    
  }
  
  public IGridContainer getProjectFolder( final IGridElement element ) {
    return getProjectFolder( element.getClass() );
  }
  
  public String getProjectFolderID( final IGridContainer folder ) {
    
    String result = null;
    
    if ( folder.getParent() == this ) {
      
      String name = folder.getName();
      
      ProjectScope projectScope = new ProjectScope( ( IProject ) getResource() );
      Preferences folderNode = projectScope.getNode( PROJECT_FOLDER_NODE );
      
      try {
      
        String[] keys = folderNode.keys();
        
        for ( String key : keys ) {
          String label = folderNode.get( key, null );
          if ( name.equals( label ) ) {
            result = key;
            break;
          }
        }
        
      } catch ( BackingStoreException bsExc ) {
        Activator.logException( bsExc );
      }
      
    }
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridProject#getVO()
   */
  public IVirtualOrganization getVO() {
    return this.vo;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridProject#hasGridNature()
   */
  public boolean hasGridNature() {
    boolean result = false;
    IProject project = ( IProject ) getResource();
    try {
      result = project.hasNature( GridProjectNature.getID() );
    } catch( CoreException e ) {
      // Don't do anything here, just return false
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridProject#isOpen()
   */
  public boolean isOpen() {
    return ( ( IProject ) getResource() ).isOpen();
  }
  
  @Override
  protected IGridElement addElement( final IGridElement element ) throws ProblemException {
    
    IGridElement result = super.addElement( element );
    
    if ( ( result != null ) && ( result instanceof IGridContainer ) && ! result.isVirtual() ) {
      try {
        GridNotificationService.getInstance().lock( this );
        boolean updated = updateProjectProperties( ( IGridContainer ) element );
        if ( ! updated ) {
          updateProjectFolderProperties( ( IGridContainer ) element );
        }
        GridNotificationService.getInstance().unlock( this );
      } catch ( CoreException cExc ) {
        Activator.logException( cExc );
      } catch ( BackingStoreException bsExc ) {
        Activator.logException( bsExc );
      }
    }
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#fetchChildren(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected IStatus fetchChildren( final IProgressMonitor monitor ) {
    IStatus result = Status.CANCEL_STATUS;
    if ( isOpen() ) {
      result = super.fetchChildren( monitor );
    }
    return result;
  }
  
  private boolean hasVo( final IGridElement[] children ) {
    boolean result = false;
    for ( IGridElement child : children ) {
      if ( child instanceof ProjectVo ) {
        result = true;
        break;
      }
    }
    return result;
  }
  
  private void loadProjectProperties( final IProject project ) {

    IScopeContext projectScope = new ProjectScope( project );
    Preferences projectNode = projectScope.getNode( PROJECT_NODE );
    
    try {
      
      projectNode.sync();
      String voName = projectNode.get( VO_ATTRIBUTE, null );
      this.vo = null;
      VoManager voManager = VoManager.getManager();
      if ( voName != null ) {
        IVirtualOrganization globalVo = ( IVirtualOrganization ) voManager.findChild( voName );
        if ( globalVo != null ) {
          this.vo = new ProjectVo( this, globalVo );
          addElement( this.vo );
        }
      }
      
    } catch ( BackingStoreException bsExc ) {
      Activator.logException( bsExc );
    } catch ( ProblemException pExc ) {
      Activator.logException( pExc );
    }
    
  }
  
  private boolean updateProjectFolderProperties( final IGridContainer folder )
      throws CoreException, BackingStoreException {
    
    boolean result = false;
    
    String name = folder.getName();
    
    IScopeContext projectScope = new ProjectScope( ( IProject ) getResource() );
    Preferences folderNode = projectScope.getNode( PROJECT_FOLDER_NODE );
    
    for ( String id : folderNode.keys() ) {
      String label = folderNode.get( id, null );
      if ( name.equals( label ) ) {
        IResource resource = folder.getResource();
        resource.setSessionProperty( PROJECT_FOLDER_ID_QN, id );
        result = true;
      }
    }
    
    if ( result ) {
      GridModelEvent event = new GridModelEvent( IGridModelEvent.PROJECT_FOLDER_CHANGED,
                                                 this,
                                                 new IGridElement[] { folder } );
      GridNotificationService.getInstance().queueEvent( event );
    }
    
    return result;
    
  }
  
  private boolean updateProjectProperties( final IGridContainer folder )
      throws CoreException {
    
    boolean result = false;
    
    IResource resource = folder.getResource();
    String id = ( String ) resource.getSessionProperty( PROJECT_FOLDER_ID_QN );
    String name = folder.getName();
    
    if ( id != null ) {
      
      IScopeContext projectScope = new ProjectScope( ( IProject ) getResource() );
      final Preferences folderNode = projectScope.getNode( PROJECT_FOLDER_NODE );
      
      String label = folderNode.get( id, null );
      if ( ! name.equals( label ) ) {
        folderNode.put( id, name );
        Job syncJob = new Job( "syncProjectPreferences@" + getName() ) { //$NON-NLS-1$
          @Override
          protected IStatus run( final IProgressMonitor monitor ) {
            IStatus status = Status.OK_STATUS;
            try {
              folderNode.flush();
            } catch ( BackingStoreException bsExc ) {
              Activator.logException( bsExc );
              status = Status.CANCEL_STATUS;
            }
            return status;
          }
        };
        syncJob.setSystem( true );
        syncJob.schedule();
        result = true;
      }
      
    }
    
    if ( result ) {
      GridModelEvent event = new GridModelEvent( IGridModelEvent.PROJECT_FOLDER_CHANGED,
                                                 this,
                                                 new IGridElement[] { folder } );
      GridNotificationService.getInstance().queueEvent( event );
    }
    
    return result;
    
  }
  
}
