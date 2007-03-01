package eu.geclipse.core.model.impl;

import java.net.URI;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridProject;
import eu.geclipse.core.model.IGridRoot;

public abstract class AbstractGridElement
    implements IGridElement {
  
  private IResource resource;
  
  protected AbstractGridElement( final IResource resource ) {
    this.resource = resource;
  }
  
  public void dispose() {
    // empty implementation
  }
  
  public IFileStore getFileStore() {
    IResource res = getResource();
    URI uri = res.getLocationURI();
    IFileStore store = EFS.getLocalFileSystem().getStore( uri );
    return store;
  }

  public String getName() {
    return getResource().getName();
  }

  public IGridContainer getParent() {
    IGridContainer parent = null;
    if ( this.resource != null ) {
      IContainer parentResource = this.resource.getParent();
      if ( parentResource != null ) {
        IGridElement element = getRoot().findElement( parentResource );
        if ( element instanceof IGridContainer ) {
          parent = ( IGridContainer ) element;
        }
      }
    }
    return parent;
  }

  public IPath getPath() {
    return this.resource.getFullPath();
  }

  public IGridProject getProject() {
    IGridProject project = null;
    IGridContainer parent = getParent();
    while ( ( parent != null ) && !( parent instanceof IGridProject ) ) {
      parent = parent.getParent();
    }
    if ( parent instanceof IGridProject ) {
      project = ( IGridProject ) parent; 
    }
    return project;
  }

  public IResource getResource() {
    return this.resource;
  }

  @SuppressWarnings("unchecked")
  public Object getAdapter( final Class adapter ) {
    Object result = null;
    if ( adapter.isAssignableFrom( IResource.class ) ) {
      result = getResource();
    }
    return result;
  }
  
  protected IGridRoot getRoot() {
    return GridModel.getRoot();
  }
  
}
