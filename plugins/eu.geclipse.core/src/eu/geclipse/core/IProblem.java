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
  
  /**
   * This method allow to set specific text for already created problem.
   * Typical usage is to get predefined problem from ProblemRegistry and 
   * than set text explaining specific reason of the problem 
   * @param reason
   */
  public void setReason(String reason);
  
}
