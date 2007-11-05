package eu.geclipse.core.internal.model;

import java.util.ArrayList;

import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IWrappedElement;

public class ClassTypeQueryFilter implements IQueryFilter {
  
  private Class< ? > classType;
  
  private boolean allow;
  
  public ClassTypeQueryFilter( final Class< ? > classType,
                               final boolean allow ) {
    this.classType = classType;
    this.allow = allow;
  }

  public IGridElement[] filter( final IGridElement[] input ) {
    
    java.util.List< IGridElement > result = new ArrayList< IGridElement >();
    
    for ( IGridElement element : input ) {
      if ( element instanceof IWrappedElement ) {
        element = ( ( IWrappedElement ) element ).getWrappedElement();
      }
      if ( this.allow
          && this.classType.isAssignableFrom( element.getClass() ) ) {
        result.add( element );
      } else if ( ! this.allow
          && ! this.classType.isAssignableFrom( element.getClass() ) ) {
        result.add( element );
      }
    }
    
    return result.toArray( new IGridElement[ result.size() ] );
    
  }

}
