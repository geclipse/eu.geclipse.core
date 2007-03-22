package eu.geclipse.core;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * Abstract implementation of the {@link IProblem} interface. 
 */
public abstract class AbstractProblem implements IProblem {
  
  private Throwable exception;
  
  private int id;
  
  private List< ISolution > solutions = new ArrayList< ISolution >();
  
  private List< Integer > solutionIDs = new ArrayList< Integer >();
  
  private IStatus status;
  
  private String text;
  
  private String reason;
  
  protected AbstractProblem( final int id,
                             final String text ) {
    this( id, text, null );
  }
  
  protected AbstractProblem( final int id,
                             final String text,
                             final Throwable exception ) {
    this.id = id;
    this.text = text;
    this.exception = exception;
  }
  
  public void addSolution( final int solutionID ) {
    Integer value = new Integer( solutionID );
    if ( !this.solutionIDs.contains( value ) ) {
      this.solutionIDs.add( value );
    }
  }
  
  public void addSolution( final ISolution solution ) {
    ISolution oldSolution = findSolution( solution.getID() );
    if ( oldSolution != null ) {
      this.solutions.remove( oldSolution );
    }
    this.solutions.add( solution );
  }
  
  public Throwable getException() {
    return this.exception;
  }

  public int getID() {
    return this.id;
  }

  public List< ISolution > getSolutions( final SolutionRegistry registry ) {
    List< ISolution > resultList = new ArrayList< ISolution >(); 
    for ( int i = 0 ; i < this.solutionIDs.size() ; i++ ) {
      int solutionID = this.solutionIDs.get( i ).intValue();
      ISolution solution = findSolution( solutionID );
      if ( solution == null ) {
        solution = registry.findSolution( solutionID );
      }
      if ( solution != null ) {
        resultList.add( solution );
      }
    }
    return resultList;
  }
  
  public void setReason(final String reason){
    this.reason=reason;
  }
  
  public IStatus getStatus() {
    if ( this.status == null ) {
      this.status = new Status(
        IStatus.ERROR,
        getPluginID(),
        getID(),
        getText(),
        getException()
      );
    }
    return this.status;
  }

  public String getText() {
    String message=text;
    if(reason==null){
      message+="\""+reason;
    }
    return message;
  }
  
  protected abstract String getPluginID();
  
  private ISolution findSolution( final int solutionID ) {
    ISolution solution = null;
    for ( int i = 0 ; i < this.solutions.size() ; i++ ) {
      if ( this.solutions.get( i ).getID() == solutionID ) {
        solution = this.solutions.get( i );
        break;
      }
    }
    return solution;
  }
  
  
}
