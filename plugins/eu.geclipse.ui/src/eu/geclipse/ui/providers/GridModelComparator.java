package eu.geclipse.ui.providers;

import java.util.Comparator;
import eu.geclipse.core.model.IGridElement;
import eu.geclipse.core.model.IGridMount;

public class GridModelComparator implements Comparator< Object > {
  
  public int compare( final Object o1,
                      final Object o2 ) {
    int result = 0;
    if ( ( o1 instanceof IGridElement ) && ( o2 instanceof IGridElement ) ) {
      result = compare( ( IGridElement ) o1, ( IGridElement ) o2 );
    } else {
      result = o1.toString().compareTo( o2.toString() );
    }
    return result;
  }

  public int compare( final IGridElement e1,
                      final IGridElement e2 ) {
    int result = 0;
    boolean c1 = isFolder( e1 );
    boolean c2 = isFolder( e2 );
    if ( c1 == c2 ) {
      result = e1.getName().compareTo( e2.getName() );
    } else {
      result = c1 ? -1 : 1;         
    }
    return result;
  }
  
  private boolean isFolder( final IGridElement element ) {
    boolean result = false;
    if ( element instanceof IGridMount ) {
      result = ( ( IGridMount ) element ).isFolder();
    }/* else if ( element instanceof IGridContainer ) {
      result = true;
    }*/
    return result;
  }
  
}
