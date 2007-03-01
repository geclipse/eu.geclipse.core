package eu.geclipse.core.internal.model;

import org.eclipse.core.runtime.IPath;
import eu.geclipse.core.model.IGridContainer;
import eu.geclipse.core.model.impl.AbstractGridContainer;

public class GridResourceContainer
    extends AbstractGridContainer {
  
  private IGridContainer elementParent;
  
  private String elementName;
  
  protected GridResourceContainer( final IGridContainer parent,
                                   final String name ) {
    super( null );
    this.elementParent = parent;
    this.elementName = name;
  }
  
  @Override
  public String getName() {
    return this.elementName;
  }

  @Override
  public IGridContainer getParent() {
    return this.elementParent;
  }
  
  @Override
  public IPath getPath() {
    return this.elementParent.getPath().append( getName() );
  }
  
  public boolean isLazy() {
    return false;
  }

  public boolean isLocal() {
    return true;
  }

  public boolean isVirtual() {
    return true;
  }

}
