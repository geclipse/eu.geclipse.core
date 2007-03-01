package eu.geclipse.core.model;

import org.eclipse.core.runtime.CoreException;

public class GridModelException extends CoreException {

  private static final long serialVersionUID = 271834096070966136L;
  
  public GridModelException( final CoreException exc ) {
    super( exc.getStatus() );
  }
  
  public GridModelException( final IGridModelStatus status ) {
    super( status );
  }
  
}
