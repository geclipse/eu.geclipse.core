package eu.geclipse.core;

public interface IProblemProvider {
  
  public IProblem getProblem( final int problemID,
                              final Throwable exc );
  
}
