package eu.geclipse.core.model.impl;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.model.IGridContainer;

public class EmptyLazyContainerMarker extends AbstractGridElement {
  
  private IGridContainer parent;
  
  public EmptyLazyContainerMarker( final IGridContainer parent ) {
    this.parent = parent;
  }

  public IFileStore getFileStore() {
    return null;
  }

  public String getName() {
    return "Folder is empty";
  }

  public IGridContainer getParent() {
    return this.parent;
  }

  public IPath getPath() {
    return getParent().getPath().append( getName() );
  }

  public IResource getResource() {
    return null;
  }

  public boolean isLocal() {
    return true;
  }

}
