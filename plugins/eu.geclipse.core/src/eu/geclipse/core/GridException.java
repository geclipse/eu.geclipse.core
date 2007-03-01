package eu.geclipse.core;

import org.eclipse.core.runtime.CoreException;

public class GridException extends CoreException {
  
  private static final long serialVersionUID = -6816743743846963886L;
  
  private IProblem problem;
  
  public GridException( final int problemID ) {
    this( problemID, null );
  }
  
  public GridException( final int problemID,
                        final Throwable exc ) {
    this( ProblemRegistry.getRegistry().getProblem( problemID, exc ) );
  }
  
  public GridException( final IProblem problem ) {
    super( problem.getStatus() );
    this.problem = problem;
  }
  
  public IProblem getProblem() {
    return this.problem;
  }
  
}
