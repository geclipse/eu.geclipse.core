package eu.geclipse.core.internal.model;

import eu.geclipse.core.model.IGridResourceCategory;
import eu.geclipse.core.model.IGridResourceContainer;

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
  
  @Override
  public boolean equals( final Object o ) {
    
    boolean result = false;
    
    if ( o instanceof IGridResourceCategory ) {
      
      IGridResourceCategory category = ( IGridResourceCategory ) o;
      
      if ( getName().equals( category.getName() ) ) {
        
        IGridResourceCategory par = getParent();
        IGridResourceCategory oPar = category.getParent();
      
        if ( par != null ) {
          result = par.equals( oPar );
        } else if ( oPar == null ) {
          result = true;
        } 
          
      }
      
    }
    
    return result;
    
  }

}
