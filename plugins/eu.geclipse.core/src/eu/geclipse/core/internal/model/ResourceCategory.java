package eu.geclipse.core.internal.model;

import eu.geclipse.core.model.IGridResourceCategory;

public class ResourceCategory implements IGridResourceCategory {
  
  private String name;
  
  private IGridResourceCategory parent;
  
  private boolean isActive;
  
  public ResourceCategory( final String name,
                           final IGridResourceCategory parent,
                           final boolean isActive ) {
    this.name = name;
    this.parent = parent;
    this.isActive = isActive;
  }

  public String getName() {
    return this.name;
  }

  public IGridResourceCategory getParent() {
    return this.parent;
  }
  
  public boolean isActive() {
    return this.isActive;
  }

}
