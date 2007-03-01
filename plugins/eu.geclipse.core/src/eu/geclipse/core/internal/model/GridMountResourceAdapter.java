package eu.geclipse.core.internal.model;

import java.net.URI;
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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.osgi.service.prefs.BackingStoreException;
import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridProject;

public class GridMountResourceAdapter
    implements IResource {
  
  private GridMount gridMount;
  
  GridMountResourceAdapter( final GridMount gridMount ) {
    this.gridMount = gridMount;
  }
  
  public void accept( final IResourceVisitor visitor )
      throws CoreException {
    accept( visitor,IResource.DEPTH_INFINITE, IResource.NONE );
  }

  public void accept( final IResourceProxyVisitor visitor,
                      final int memberFlags )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public void accept( final IResourceVisitor visitor,
                      final int depth,
                      final boolean includePhantoms )
      throws CoreException {
    accept( visitor,
            depth,
            includePhantoms ? IContainer.INCLUDE_PHANTOMS : IResource.NONE );
  }

  public void accept( final IResourceVisitor visitor,
                      final int depth,
                      final int memberFlags )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public void clearHistory( final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public void copy( final IPath destination,
                    final boolean force,
                    final IProgressMonitor monitor )
      throws CoreException {
    copy( destination, ( force ? FORCE : IResource.NONE ), monitor );
  }

  public void copy( final IPath destination,
                    final int updateFlags,
                    final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public void copy( final IProjectDescription description,
                    final boolean force,
                    final IProgressMonitor monitor )
      throws CoreException {
    copy( description, ( force ? FORCE : IResource.NONE ), monitor );
  }

  public void copy( final IProjectDescription description,
                    final int updateFlags,
                    final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public IMarker createMarker( final String type )
  throws CoreException {
    // TODO mathias
    notYetImplemented();
    return null;
  }

  public IResourceProxy createProxy() {
    // TODO mathias
    return null;
  }

  public void delete( final boolean force,
                      final IProgressMonitor monitor )
      throws CoreException {
    delete( force ? FORCE : IResource.NONE, monitor );
  }

  public void delete( final int updateFlags,
                      final IProgressMonitor monitor )
      throws CoreException {
    IFileStore fileStore = getFileStore();
    if ( fileStore != null ) {
      fileStore.delete( EFS.NONE, monitor );
    }
  }

  public void deleteMarkers( final String type,
                             final boolean includeSubtypes,
                             final int depth )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public boolean exists() {
    boolean result = false;
    IFileInfo fileInfo = getFileInfo();
    if ( fileInfo != null ) {
      result = fileInfo.exists();
    }
    return result;
  }

  public IMarker findMarker( final long id )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
    return null;
  }

  public IMarker[] findMarkers( final String type,
                                final boolean includeSubtypes,
                                final int depth )
  throws CoreException {
    // TODO mathias
    notYetImplemented();
    return null;
  }

  public String getFileExtension() {
    return new Path( getName() ).getFileExtension();
  }
  
  /**
   * Get the {@link IFileInfo} that corresponds to this adapter.
   * 
   * @return The {@link IFileInfo} of the related {@link GridMount}
   * or <code>null</code>.
   */
  public IFileInfo getFileInfo() {
    return this.gridMount.getFileInfo();
  }
  
  /**
   * Get the {@link IFileStore} that corresponds to this adapter.
   * 
   * @return The {@link IFileStore} of the related {@link GridMount}
   * or <code>null</code>.
   */
  public IFileStore getFileStore() {
    return this.gridMount.getFileStore();
  }
  
  public IPath getFullPath() {
    return getGridMount().getPath();
  }
  
  /**
   * Get the associated {@link GridMount} object.
   * 
   * @return The {@link GridMount}.
   */
  public GridMount getGridMount() {
    return this.gridMount;
  }

  public long getLocalTimeStamp() {
    // TODO mathias
    return 0;
  }

  public IPath getLocation() {
    return null;
  }

  public URI getLocationURI() {
    return null;
  }

  public IMarker getMarker( final long id ) {
    // TODO mathias
    return null;
  }

  public long getModificationStamp() {
    // TODO mathias
    return 0;
  }
  
  public String getName() {
    return getGridMount().getName();
  }

  public IContainer getParent() {
    IContainer parent = null;
    IGridContainer mountParent = getGridMount().getParent();
    if ( mountParent != null ) {
      parent = ( IContainer ) mountParent.getResource();
    }
    return parent;
  }

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

  public IProject getProject() {
    IProject result = null;
    IGridProject gridProject = getGridMount().getProject();
    if ( gridProject != null ) {
      result = ( IProject ) gridProject.getResource();
    }
    return result;
  }

  public IPath getProjectRelativePath() {
    // TODO mathias
    return null;
  }

  public IPath getRawLocation() {
    // TODO mathias
    return null;
  }

  public URI getRawLocationURI() {
    // TODO mathias
    return null;
  }

  public ResourceAttributes getResourceAttributes() {
    // TODO mathias
    return null;
  }

  public Object getSessionProperty( final QualifiedName key )
  throws CoreException {
    // TODO mathias
    notYetImplemented();
    return null;
  }

  public int getType() {
    return IResource.FILE;
  }

  public IWorkspace getWorkspace() {
    // TODO mathias
    return null;
  }

  public boolean isAccessible() {
    return exists();
  }

  public boolean isDerived() {
    // TODO mathias
    return false;
  }

  public boolean isLinked() {
    // TODO mathias
    return false;
  }

  public boolean isLinked( final int options ) {
    // TODO mathias
    return false;
  }

  public boolean isLocal( final int depth ) {
    return false;
  }

  public boolean isPhantom() {
    return false;
  }
  
  public boolean isReadOnly() {
    boolean result = true;
    IFileInfo fileInfo = getFileInfo();
    if ( fileInfo != null ) {
      result = fileInfo.getAttribute( EFS.ATTRIBUTE_READ_ONLY );
    }
    return result;
  }

  public boolean isSynchronized( final int depth ) {
    // TODO mathias
    return false;
  }

  public boolean isTeamPrivateMember() {
    // TODO mathias
    return false;
  }

  public void move( final IPath destination,
                    final boolean force,
                    final IProgressMonitor monitor )
      throws CoreException {
    move( destination, force ? FORCE : IResource.NONE, monitor );
  }

  public void move( final IPath destination,
                    final int updateFlags,
                    final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public void move( final IProjectDescription description,
                    final int updateFlags,
                    final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

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

  public void refreshLocal( final int depth,
                            final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public void revertModificationStamp( final long value )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public void setDerived( final boolean isDerived )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public void setLocal( final boolean flag,
                        final int depth,
                        final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public long setLocalTimeStamp( final long value )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
    return 0;
  }

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
                                     "Unable to set persistent property",
                                     bsExc );
        throw new CoreException( status );
      }
    }
  }

  public void setReadOnly( final boolean readOnly ) {
    IFileInfo fileInfo = getFileInfo();
    if ( fileInfo != null ) {
      fileInfo.setAttribute( EFS.ATTRIBUTE_READ_ONLY, readOnly );
    }
  }

  public void setResourceAttributes( final ResourceAttributes attributes )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public void setSessionProperty( final QualifiedName key,
                                  final Object value )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public void setTeamPrivateMember( final boolean isTeamPrivate )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  public void touch( final IProgressMonitor monitor )
      throws CoreException {
    // TODO mathias
    notYetImplemented();
  }

  @SuppressWarnings("unchecked")
  public Object getAdapter( final Class adapter ) {
    return null;
  }

  public boolean contains( final ISchedulingRule rule ) {
    // TODO mathias
    return false;
  }

  public boolean isConflicting( final ISchedulingRule rule ) {
    // TODO mathias
    return false;
  }
  
  protected void notYetImplemented() throws CoreException {
    IStatus status = new Status( IStatus.ERROR,
                                 Activator.PLUGIN_ID,
                                 IStatus.CANCEL,
                                 "Mathias says: Method not yet implemented", //$NON-NLS-1$
                                 null );
    CoreException exc = new CoreException( status );
    exc.printStackTrace();
    throw exc;
  }

}
