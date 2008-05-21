package eu.geclipse.core.internal.model;

import eu.geclipse.core.model.IGridResourceCategory;

public class ResourceCategory implements IGridResourceCategory {
  
  private String name;
  
  private IGridResourceCategory parent;
  
  public ResourceCategory( final String name, final IGridResourceCategory parent ) {
    this.name = name;
    this.parent = parent;
  }

  public String getName() {
    return this.name;
  }

  public IGridResourceCategory getParent() {
    return this.parent;
  }

}
