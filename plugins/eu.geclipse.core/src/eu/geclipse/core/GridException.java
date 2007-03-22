package eu.geclipse.core;

import org.eclipse.core.runtime.CoreException;

public class GridException extends CoreException {
  
  private static final long serialVersionUID = -6816743743846963886L;
  
  private IProblem problem;
  
  public GridException( final int problemID ) {
    this( problemID, (Throwable)null );
  }
  
  public GridException( final int problemID, final String description ) {
    this( problemID, (Throwable)null, description );
  }
  
  public GridException( final int problemID,
                        final Throwable exc ) {
    this( ProblemRegistry.getRegistry().getProblem( problemID, exc ) );
  }
  
  public GridException( final int problemID,
                        final Throwable exc, final String description ) {
    this( ProblemRegistry.getRegistry().getProblem( problemID, exc ) );
    problem.setReason( description );
  }

  public GridException( final IProblem problem ) {
    super( problem.getStatus() );
    this.problem = problem;
  }
  
  public GridException( final IProblem problem, String description ) {
    super( problem.getStatus() );
    this.problem = problem;
    problem.setReason( description );
  }

  public IProblem getProblem() {
    return this.problem;
  }
  
}
