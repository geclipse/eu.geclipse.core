package eu.geclipse.core.model.impl;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IResource;

import eu.geclipse.core.model.GridModel;
import eu.geclipse.core.model.IGridConnection;
import eu.geclipse.core.model.IGridElement;

public class GridModelPropertyTester extends PropertyTester {

  public boolean test( final Object receiver,
                       final String property,
                       final Object[] args,
                       final Object expectedValue ) {
    
    boolean result = false;
    
    IResource resource = ( IResource ) receiver;
    if ( "isGridConnection".equals( property ) ) {
      IGridElement element = GridModel.getRoot().findElement( resource );
      if ( ( element != null ) && ( element instanceof IGridConnection ) ) {
        result = true;
      }
    }
    
    return result;
    
  }

}
