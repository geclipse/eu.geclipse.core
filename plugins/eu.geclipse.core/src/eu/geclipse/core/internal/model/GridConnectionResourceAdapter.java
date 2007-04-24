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

import java.net.URI;
import java.util.Hashtable;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileInfo;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.osgi.service.prefs.BackingStoreException;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridProject;

/**
 * {@link IResource} implementation in order to wrap
 * {@link GridConnectionElement}s.
 */
abstract public class GridConnectionResourceAdapter
    implements IResource {
  
  /**
   * The associated grid connection.
   */
  private GridConnectionElement connection;
  
  /**
   * Hashtable that holds the session properties of this resource.
   */
  private Hashtable< QualifiedName, Object > sessionProperties
    = new Hashtable< QualifiedName, Object >();
  
  /**
   * Created a new resource adapter.
   * 
   * @param connection The adapters associated connection.
   */
  GridConnectionResourceAdapter( final GridConnectionElement connection ) {
    this.connection = connection;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources.IResourceVisitor)
   */
  public void accept( final IResourceVisitor visitor )
      throws CoreException {
    accept( visitor,IResource.DEPTH_INFINITE, IResource.NONE );
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources.IResourceProxyVisitor, int)
   */
  public void accept( final IResourceProxyVisitor visitor,
                      final int memberFlags )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources.IResourceVisitor, int, boolean)
   */
  public void accept( final IResourceVisitor visitor,
                      final int depth,
                      final boolean includePhantoms )
      throws CoreException {
    accept( visitor,
            depth,
            includePhantoms ? IContainer.INCLUDE_PHANTOMS : IResource.NONE );
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#accept(org.eclipse.core.resources.IResourceVisitor, int, int)
   */
  public void accept( final IResourceVisitor visitor,
                      final int depth,
                      final int memberFlags )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#clearHistory(org.eclipse.core.runtime.IProgressMonitor)
   */
  public void clearHistory( final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#copy(org.eclipse.core.runtime.IPath, boolean, org.eclipse.core.runtime.IProgressMonitor)
   */
  public void copy( final IPath destination,
                    final boolean force,
                    final IProgressMonitor monitor )
      throws CoreException {
    copy( destination, ( force ? FORCE : IResource.NONE ), monitor );
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#copy(org.eclipse.core.runtime.IPath, int, org.eclipse.core.runtime.IProgressMonitor)
   */
  public void copy( final IPath destination,
                    final int updateFlags,
                    final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#copy(org.eclipse.core.resources.IProjectDescription, boolean, org.eclipse.core.runtime.IProgressMonitor)
   */
  public void copy( final IProjectDescription description,
                    final boolean force,
                    final IProgressMonitor monitor )
      throws CoreException {
    copy( description, ( force ? FORCE : IResource.NONE ), monitor );
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#copy(org.eclipse.core.resources.IProjectDescription, int, org.eclipse.core.runtime.IProgressMonitor)
   */
  public void copy( final IProjectDescription description,
                    final int updateFlags,
                    final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#createMarker(java.lang.String)
   */
  public IMarker createMarker( final String type )
  throws CoreException {
    // TODO mathias
    notYetImplemented();
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#createProxy()
   */
  public IResourceProxy createProxy() {
    // TODO mathias
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#delete(boolean, org.eclipse.core.runtime.IProgressMonitor)
   */
  public void delete( final boolean force,
                      final IProgressMonitor monitor )
      throws CoreException {
    delete( force ? FORCE : IResource.NONE, monitor );
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#delete(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  public void delete( final int updateFlags,
                      final IProgressMonitor monitor )
      throws CoreException {
    
    IProgressMonitor localMonitor
      = monitor == null
      ? new NullProgressMonitor()
      : monitor;
    
    localMonitor.beginTask( "Deleting", 1 ); //$NON-NLS-1$
      
    try {
      localMonitor.setTaskName( getName() );
      IGridContainer parent = this.connection.getParent();
      parent.delete( this.connection );
      localMonitor.worked( 1 );
    } finally {
      localMonitor.done();
    }
    
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#deleteMarkers(java.lang.String, boolean, int)
   */
  public void deleteMarkers( final String type,
                             final boolean includeSubtypes,
                             final int depth )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#exists()
   */
  public boolean exists() {
    boolean result = false;
    IFileInfo fileInfo = getFileInfo();
    if ( fileInfo != null ) {
      result = fileInfo.exists();
    }
    return result;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#findMarker(long)
   */
  public IMarker findMarker( final long id )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#findMarkers(java.lang.String, boolean, int)
   */
  public IMarker[] findMarkers( final String type,
                                final boolean includeSubtypes,
                                final int depth )
  throws CoreException {
    // TODO mathias
    notYetImplemented();
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#getFileExtension()
   */
  public String getFileExtension() {
    return new Path( getName() ).getFileExtension();
  }
  
  /**
   * Get the {@link IFileInfo} that corresponds to this adapter.
   * 
   * @return The {@link IFileInfo} of the related {@link GridConnectionElement}
   * or <code>null</code>.
   */
  public IFileInfo getFileInfo() {
    return this.connection.getConnectionFileInfo();
  }
  
  /**
   * Get the {@link IFileStore} that corresponds to this adapter.
   * 
   * @return The {@link IFileStore} of the related {@link GridConnectionElement}
   * or <code>null</code>.
   */
  public IFileStore getFileStore() {
    IFileStore result = null;
    try {
      result = this.connection.getConnectionFileStore();
    } catch ( CoreException cExc ) {
      Activator.logException( cExc );
    }
    return result;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#getFullPath()
   */
  public IPath getFullPath() {
    return getGridConnection().getPath();
  }
  
  /**
   * Get the associated {@link GridConnectionElement} object.
   * 
   * @return The {@link GridConnectionElement}.
   */
  public GridConnectionElement getGridConnection() {
    return this.connection;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#getLocalTimeStamp()
   */
  public long getLocalTimeStamp() {
    // TODO mathias
    return 0;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#getLocation()
   */
  public IPath getLocation() {
    return getParent().getLocation().append( getName() );
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#getLocationURI()
   */
  public URI getLocationURI() {
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#getMarker(long)
   */
  public IMarker getMarker( final long id ) {
    // TODO mathias
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#getModificationStamp()
   */
  public long getModificationStamp() {
    // TODO mathias
    return 0;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#getName()
   */
  public String getName() {
    return getGridConnection().getName();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#getParent()
   */
  public IContainer getParent() {
    IContainer parent = null;
    IGridContainer mountParent = getGridConnection().getParent();
    if ( mountParent != null ) {
      parent = ( IContainer ) mountParent.getResource();
    }
    return parent;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#getPersistentProperty(org.eclipse.core.runtime.QualifiedName)
   */
  public String getPersistentProperty( final QualifiedName key )
      throws CoreException {
    String result = null;
    IProject project = getProject();
    if ( project != null ) {
      IScopeContext scope = new ProjectScope( project );
      IEclipsePreferences node = scope.getNode( getFileStore().toString() );
      result = node.get( key.toString(), null );
    }
    return result;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#getProject()
   */
  public IProject getProject() {
    IProject result = null;
    IGridProject gridProject = getGridConnection().getProject();
    if ( gridProject != null ) {
      result = ( IProject ) gridProject.getResource();
    }
    return result;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#getProjectRelativePath()
   */
  public IPath getProjectRelativePath() {
    // TODO mathias
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#getRawLocation()
   */
  public IPath getRawLocation() {
    // TODO mathias
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#getRawLocationURI()
   */
  public URI getRawLocationURI() {
    // TODO mathias
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#getResourceAttributes()
   */
  public ResourceAttributes getResourceAttributes() {
    // TODO mathias
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#getSessionProperty(org.eclipse.core.runtime.QualifiedName)
   */
  public Object getSessionProperty( final QualifiedName key )
      throws CoreException {
    return this.sessionProperties.get( key );
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#getWorkspace()
   */
  public IWorkspace getWorkspace() {
    return ResourcesPlugin.getWorkspace();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#isAccessible()
   */
  public boolean isAccessible() {
    return exists();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#isDerived()
   */
  public boolean isDerived() {
    // TODO mathias
    return false;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#isLinked()
   */
  public boolean isLinked() {
    // TODO mathias
    return false;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#isLinked(int)
   */
  public boolean isLinked( final int options ) {
    // TODO mathias
    return false;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#isLocal(int)
   */
  public boolean isLocal( final int depth ) {
    return false;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#isPhantom()
   */
  public boolean isPhantom() {
    return false;
  }
  
  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#isReadOnly()
   */
  public boolean isReadOnly() {
    boolean result = true;
    IFileInfo fileInfo = getFileInfo();
    if ( fileInfo != null ) {
      result = fileInfo.getAttribute( EFS.ATTRIBUTE_READ_ONLY );
    }
    return result;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#isSynchronized(int)
   */
  public boolean isSynchronized( final int depth ) {
    // TODO mathias
    return false;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#isTeamPrivateMember()
   */
  public boolean isTeamPrivateMember() {
    // TODO mathias
    return false;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#move(org.eclipse.core.runtime.IPath, boolean, org.eclipse.core.runtime.IProgressMonitor)
   */
  public void move( final IPath destination,
                    final boolean force,
                    final IProgressMonitor monitor )
      throws CoreException {
    move( destination, force ? FORCE : IResource.NONE, monitor );
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#move(org.eclipse.core.runtime.IPath, int, org.eclipse.core.runtime.IProgressMonitor)
   */
  public void move( final IPath destination,
                    final int updateFlags,
                    final IProgressMonitor monitor )
      throws CoreException {
    
    IProgressMonitor localMonitor = monitor;
    if ( localMonitor == null ) {
      localMonitor = new NullProgressMonitor();
    }
    
    localMonitor.beginTask( Messages.getString("GridConnectionResourceAdapter.moving_task") + getName(), 2 ); //$NON-NLS-1$
    
    IProgressMonitor copyMonitor = new SubProgressMonitor( localMonitor, 1 );
    copy( destination, updateFlags, copyMonitor );

    IProgressMonitor deleteMonitor = new SubProgressMonitor( localMonitor, 1 );
    delete( updateFlags, deleteMonitor );
    
    localMonitor.done();
    
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#move(org.eclipse.core.resources.IProjectDescription, int, org.eclipse.core.runtime.IProgressMonitor)
   */
  public void move( final IProjectDescription description,
                    final int updateFlags,
                    final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#move(org.eclipse.core.resources.IProjectDescription, boolean, boolean, org.eclipse.core.runtime.IProgressMonitor)
   */
  public void move( final IProjectDescription description,
                    final boolean force,
                    final boolean keepHistory,
                    final IProgressMonitor monitor ) 
      throws CoreException {
    move( description,
          ( keepHistory ? KEEP_HISTORY : IResource.NONE )
          | ( force ? FORCE : IResource.NONE ),
          monitor );
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#refreshLocal(int, org.eclipse.core.runtime.IProgressMonitor)
   */
  public void refreshLocal( final int depth,
                            final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#revertModificationStamp(long)
   */
  public void revertModificationStamp( final long value )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#setDerived(boolean)
   */
  public void setDerived( final boolean isDerived )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#setLocal(boolean, int, org.eclipse.core.runtime.IProgressMonitor)
   */
  public void setLocal( final boolean flag,
                        final int depth,
                        final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#setLocalTimeStamp(long)
   */
  public long setLocalTimeStamp( final long value )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
    return 0;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#setPersistentProperty(org.eclipse.core.runtime.QualifiedName, java.lang.String)
   */
  public void setPersistentProperty( final QualifiedName key,
                                     final String value )
      throws CoreException {
    IProject project = getProject();
    if ( project != null ) {
      IScopeContext scope = new ProjectScope( project );
      IEclipsePreferences node = scope.getNode( getFileStore().toString() );
      node.put( key.toString(), value );
      try {
        node.flush();
      } catch( BackingStoreException bsExc ) {
        IStatus status = new Status( IStatus.ERROR,
                                     Activator.PLUGIN_ID,
                                     IStatus.OK,
                                     Messages.getString("GridConnectionResourceAdapter.pers_prop_set_failed"), //$NON-NLS-1$
                                     bsExc );
        throw new CoreException( status );
      }
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#setReadOnly(boolean)
   */
  public void setReadOnly( final boolean readOnly ) {
    IFileInfo fileInfo = getFileInfo();
    if ( fileInfo != null ) {
      fileInfo.setAttribute( EFS.ATTRIBUTE_READ_ONLY, readOnly );
    }
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#setResourceAttributes(org.eclipse.core.resources.ResourceAttributes)
   */
  public void setResourceAttributes( final ResourceAttributes attributes )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#setSessionProperty(org.eclipse.core.runtime.QualifiedName, java.lang.Object)
   */
  public void setSessionProperty( final QualifiedName key,
                                  final Object value )
      throws CoreException {
    this.sessionProperties.put( key, value );
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#setTeamPrivateMember(boolean)
   */
  public void setTeamPrivateMember( final boolean isTeamPrivate )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.resources.IResource#touch(org.eclipse.core.runtime.IProgressMonitor)
   */
  public void touch( final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  public Object getAdapter( final Class adapter ) {
    return null;
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.jobs.ISchedulingRule#contains(org.eclipse.core.runtime.jobs.ISchedulingRule)
   */
  public boolean contains( final ISchedulingRule rule ) {
    
    boolean result = false;
    
    if ( rule == this ) {
      result = true;
    } else if ( rule instanceof IResource ){
      IPath thisPath = getFullPath();
      IPath otherPath = ( ( IResource ) rule ).getFullPath();
      result = thisPath.isPrefixOf( otherPath );
    }
    
    return result;
    
  }

  /* (non-Javadoc)
   * @see org.eclipse.core.runtime.jobs.ISchedulingRule#isConflicting(org.eclipse.core.runtime.jobs.ISchedulingRule)
   */
  public boolean isConflicting( final ISchedulingRule rule ) {
    
    boolean result = false;
    
    if ( rule instanceof IResource ) {
      IPath thisPath = getFullPath();
      IPath otherPath = ( ( IResource ) rule ).getFullPath();
      result
        = thisPath.isPrefixOf( otherPath )
        || otherPath.isPrefixOf( thisPath );
    }
    
    return result;
    
  }
  
  protected static void checkExists( final IResource resource )
      throws CoreException {
    if ( !resource.exists() ) {
      IStatus status = new Status( IStatus.ERROR,
                                   Activator.PLUGIN_ID,
                                   IStatus.CANCEL,
                                   Messages.getString("GridConnectionResourceAdapter.resource") + resource.getName() + Messages.getString("GridConnectionResourceAdapter.not_existent"), //$NON-NLS-1$ //$NON-NLS-2$
                                   null );
      throw new CoreException( status );
    }
  }
  
  /**
   * Internal method denoting that a method is not yet implemented.
   * 
   * @throws CoreException Always thrown.
   */
  protected void notYetImplemented() throws CoreException {
    IStatus status = new Status( IStatus.ERROR,
                                 Activator.PLUGIN_ID,
                                 IStatus.CANCEL,
                                 "Mathias says: Method not yet implemented", //$NON-NLS-1$
                                 null );
    CoreException exc = new CoreException( status );
    Activator.logException( exc );
    throw exc;
  }

  /**
   * Method from Eclipse 3.3, documentation follows.
   * 
   * @param type Type.
   * @param includeSubtypes Include subtypes.
   * @param depth Depth.
   * @return Max problem severity.
   * @throws CoreException
   */
  public int findMaxProblemSeverity( @SuppressWarnings("unused") final String type,
                                     @SuppressWarnings("unused") final boolean includeSubtypes,
                                     @SuppressWarnings("unused") final int depth )
      throws CoreException {
    // TODO ariel - This method was added to the Interface in v3.3
    notYetImplemented();
    return -1;
  }
}
