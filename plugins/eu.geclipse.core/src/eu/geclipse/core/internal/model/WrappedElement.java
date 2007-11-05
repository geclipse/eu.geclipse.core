package eu.geclipse.core.internal.model;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IWrappedElement;
import eu.geclipse.core.model.impl.AbstractGridElement;

public class WrappedElement
    extends AbstractGridElement
    implements IWrappedElement {
  
  private IGridContainer parent;
  
  private IGridElement wrapped;
  
  public WrappedElement( final IGridContainer parent,
                         final IGridElement wrapped ) {
    this.parent = parent;
    this.wrapped = wrapped;
  }

  public IGridElement getWrappedElement() {
    return this.wrapped;
  }

  public IFileStore getFileStore() {
    return getParent().getFileStore().getChild( getName() );
  }

  public String getName() {
    return this.wrapped.getName();
  }

  public IGridContainer getParent() {
    return this.parent;
  }

  public IPath getPath() {
    return getParent().getPath().append( getName() );
  }

  public IResource getResource() {
    return getWrappedElement().getResource();
  }

  public boolean isLocal() {
    return getWrappedElement().isLocal();
  }

}
