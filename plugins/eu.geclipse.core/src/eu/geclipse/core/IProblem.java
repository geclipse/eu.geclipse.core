package eu.geclipse.core;

import java.util.List;
import org.eclipse.core.runtime.IStatus;

public interface IProblem {
  
  public void addSolution( final int solutionID );
  
  public void addSolution( final ISolution solution );
  
  public Throwable getException();
  
  public int getID();
  
  public List< ISolution > getSolutions( final SolutionRegistry registry );

  public IStatus getStatus();
  
  public String getText();
  
}
