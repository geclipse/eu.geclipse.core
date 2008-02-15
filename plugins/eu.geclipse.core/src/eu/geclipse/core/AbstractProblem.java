/*****************************************************************************
 * Copyright (c) 2006, 2007 g-Eclipse Consortium 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for the
 * g-Eclipse project founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributors:
 *    Mathias Stuempert - initial API and implementation
 *****************************************************************************/

package eu.geclipse.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

/**
 * Abstract implementation of the {@link IProblem} interface. 
 */
@Deprecated
public abstract class AbstractProblem implements IProblem {
  
  /**
   * The exception that caused the problem or <code>null</code>.
   */
  private Throwable exception;
  
  /**
   * The ID of that problem.
   */
  private int id;
  
  /**
   * List of solutions.
   */
  private List< ISolution > solutions = new ArrayList< ISolution >();
  
  /**
   * List of solution IDs.
   */
  private List< Integer > solutionIDs = new ArrayList< Integer >();
  
  /**
   * The problem text.
   */
  private String text;
  
  /**
   * Additional descriptive text for this problem.
   */
  private List< String > reasons;
  
  /**
   * Construct a new problem with the specified ID and text.
   * 
   * @param id The ID of this problem.
   * @param text A descriptive text that describes this problem.
   */
  protected AbstractProblem( final int id,
                             final String text ) {
    this( id, text, null );
  }
  
  /**
   * Construct a new problem with the specified ID and text.
   * 
   * @param id The ID of this problem.
   * @param text A descriptive text that describes this problem.
   * @param exception An exception that caused this problem.
   */
  protected AbstractProblem( final int id,
                             final String text,
                             final Throwable exception ) {
    this.id = id;
    this.text = text;
    this.exception = exception;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.IProblem#setReason(java.lang.String)
   */
  public void addReason( final String reason ){
    if ( this.reasons == null ) {
      this.reasons = new ArrayList< String >();
    }
    this.reasons.add( reason );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.IProblem#addSolution(int)
   */
  public void addSolution( final int solutionID ) {
    Integer value = Integer.valueOf( solutionID );
    if ( !this.solutionIDs.contains( value ) ) {
      this.solutionIDs.add( value );
    }
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.IProblem#addSolution(eu.geclipse.core.ISolution)
   */
  public void addSolution( final ISolution solution ) {
    ISolution oldSolution = findSolution( solution.getID() );
    if ( oldSolution != null ) {
      this.solutions.remove( oldSolution );
      this.solutionIDs.remove( oldSolution.getID() );
    }
    this.solutions.add( solution );
    this.solutionIDs.add( Integer.valueOf( solution.getID() ) );
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.IProblem#getException()
   */
  public Throwable getException() {
    return this.exception;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.IProblem#getID()
   */
  public int getID() {
    return this.id;
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.IProblem#getReasons()
   */
  public List< String > getReasons() {
    
    List< String > resultList = new ArrayList< String >();
    
    if ( this.reasons != null ) {
      resultList.addAll( this.reasons );
    }
    
    Throwable exc = getException();
    if ( ( exc != null ) && ( exc instanceof GridException ) ) {
      List< String > secReasons = ( ( GridException ) exc ).getProblem().getReasons();
      if ( secReasons != null ) {
        resultList.addAll( secReasons );
      }
    }
    
    return resultList;
    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.IProblem#getSolutions(eu.geclipse.core.SolutionRegistry)
   */
  public List< ISolution > getSolutions( final SolutionRegistry registry ) {
    List< ISolution > resultList = new ArrayList< ISolution >(); 
    List<Integer> sIDs = getSolutionIDs();
    for ( int i = 0 ; i < sIDs.size() ; i++ ) {
      int solutionID = sIDs.get( i ).intValue();
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
  
  /**
   * Get the IDs of all solutions that are currently defined for this
   * problem.
   * 
   * @return The ID's of all associated solutions.
   */
  public List< Integer > getSolutionIDs() {
    
    List< Integer > ids = new ArrayList< Integer >();
    for ( Integer sid : this.solutionIDs ) {
      if ( ! ids.contains( sid ) ) {
        ids.add( sid );
      }
    }
    
    Throwable exc = getException();
    if ( ( exc != null ) && ( exc instanceof GridException ) ) {
      IProblem problem = ( ( GridException ) exc ).getProblem();
      List< Integer > sids = ( ( AbstractProblem ) problem ).getSolutionIDs();
      for ( Integer sid : sids ) {
        if ( ! ids.contains( sid ) ) {
          ids.add( sid );
        }
      }
    }
    
    return ids;
    
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.IProblem#getStatus()
   */
  public IStatus getStatus() {
    
    IStatus result = null;
    
    if ( hasReasons() ) {
      
      List< String > reasonList = getReasons();
      
      result = new MultiStatus(
        getPluginID(),
        getID(),
        getText(),
        getException()
      );
      
      for ( String reason : reasonList ) {
        IStatus status = new Status(
            IStatus.ERROR,
            getPluginID(),
            getID(),
            reason,
            null
        );
        ( ( MultiStatus ) result ).add( status );
      }
      
    } else {
      result = new Status(
        IStatus.ERROR,
        getPluginID(),
        getID(),
        getText(),
        getException()
      );
    }
    
    return result;
    
    /*
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
    */
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.IProblem#getText()
   */
  public String getText() {
    return this.text;
  }
  
  /**
   * Determine if this problem currently contains reasons.
   * 
   * @return True if there are reasons for that problem.
   */
  public boolean hasReasons() {
    List< String > reasonList = getReasons();
    return ( reasonList != null ) && ! reasonList.isEmpty();
  }
  
  /**
   * Get the plugin ID of this problem, i.e. the ID of the plugin where this
   * problem is defined.
   * 
   * @return The plugin ID of this problem.
   */
  protected abstract String getPluginID();
  
  /**
   * Get the solution with the specified ID from the list of solutions of this
   * problem.
   * 
   * @param solutionID The ID of the solution.
   * @return The solution with the specified ID or <code>null</code>.
   */
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
