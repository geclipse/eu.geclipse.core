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

import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import eu.geclipse.core.ExtensionManager;
import eu.geclipse.core.Extensions;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.GridModelException;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IVirtualOrganization;
import eu.geclipse.core.model.impl.ResourceGridContainer;
import eu.geclipse.core.project.GridProjectNature;

/**
 * This is the grid model class that represents any project in the
 * workspace. This has not to be necessarily a grid project. Use the
 * {@link #isGridProject()} method to determine if this is really a
 * grid project.
 */
public class GridProject
    extends ResourceGridContainer
    implements IGridProject {
  
  private static final String PROJECT_NODE = "eu.geclipse.core"; //$NON-NLS-1$
  
  private static final String PROJECT_FOLDER_NODE = "eu.geclipse.core.folders"; //$NON-NLS-1$
  
  private static final String VO_ATTRIBUTE = "vo"; //$NON-NLS-1$
  
  private IVirtualOrganization vo;
  
  private Hashtable< Class< ? >, IGridContainer > projectFolders
    = new Hashtable< Class< ? >, IGridContainer >();
  
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
      throws GridModelException {
    IGridElement[] children = new IGridElement[0];
    if ( isOpen() ) {
      children = super.getChildren( monitor );
      if ( !hasVo( children ) ) {
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
    IGridContainer result = null;
    for ( Class< ? > cls : this.projectFolders.keySet() ) {
      if ( cls.isAssignableFrom( elementType ) ) {
        result = this.projectFolders.get( cls );
        break;
      }
    }
    return result;
  }
  
  public IGridContainer getProjectFolder( final IGridElement element ) {
    return getProjectFolder( element.getClass() );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridProject#getVO()
   */
  public IVirtualOrganization getVO() {
    return this.vo;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.model.IGridProject#isGridProject()
   */
  public boolean isGridProject() {
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
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.model.impl.AbstractGridContainer#fetchChildren(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  protected boolean fetchChildren( final IProgressMonitor monitor ) {
    boolean result = false;
    if ( isOpen() ) {
      result = super.fetchChildren( monitor );
    }
    return result;
  }
  
  private boolean hasVo( final IGridElement[] children ) {
    boolean result = false;
    for ( IGridElement child : children ) {
      if ( child instanceof VoWrapper ) {
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
        this.vo = ( IVirtualOrganization ) voManager.findChild( voName );
        if ( this.vo != null ) {
          addElement( new VoWrapper( this, this.vo ) );
        }
      }
      
      loadProjectFolders( projectScope );
      
    } catch( BackingStoreException bsExc ) {
      Activator.logException( bsExc );
    } catch ( GridModelException gmExc ) {
      Activator.logException( gmExc );
    }
    
  }
  
  private void loadProjectFolders( final IScopeContext projectScope )
      throws BackingStoreException {
    
    this.projectFolders.clear();
    
    Preferences folderNode = projectScope.getNode( PROJECT_FOLDER_NODE );
    
    ExtensionManager extm = new ExtensionManager();
    List< IConfigurationElement > configurationElements
      = extm.getConfigurationElements( Extensions.PROJECT_FOLDER_POINT, Extensions.PROJECT_FOLDER_ELEMENT );
    
    for ( IConfigurationElement element : configurationElements ) {
      String id = element.getAttribute( Extensions.PROJECT_FOLDER_ID_ATTRIBUTE );
      String label = folderNode.get( id, null );
      if ( label != null ) {
        IGridElement child = findChild( label );
        if ( ( child != null ) && ( child instanceof IGridContainer ) ) {
          String className = element.getAttribute( Extensions.PROJECT_FOLDER_ELEMENTCLASS_ATTRIBUTE );
          try {
            Class< ? > cls = Class.forName( className );
            this.projectFolders.put( cls, ( IGridContainer ) child );
          } catch ( ClassNotFoundException cnfExc ) {
            Activator.logException( cnfExc );
          }
        }
      }
    }
    
  }

}
