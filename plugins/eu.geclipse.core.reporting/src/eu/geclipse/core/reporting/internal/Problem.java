/*****************************************************************************
 * Copyright (c) 2007, 2008 g-Eclipse Consortium 
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

package eu.geclipse.core.reporting.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.ISolution;

/**
 * Plug-in internal implementation of the {@link IProblem}-interface.
 */
public class Problem implements IProblem {
  
  /**
   * The description of this problem.
   */
  private String description;
  
  /**
   * The exception of this problem.
   */
  private Throwable exception;
  
  /**
   * The problem's id.
   */
  private String id;
  
  /**
   * The mailto-field of this problem.
   */
  private String mailto;
  
  /**
   * The ID of the plug-in where this problem occurred.
   */
  private String pluginID;
  
  /**
   * The list containing all known reasons.
   */
  private List< String > reasons;
  
  /**
   * The list containing all known solutions.
   */
  private List< ISolution> solutions;
  
  /**
   * Create a new problem with the specified parameters.
   * 
   * @param id The problem's ID.
   * @param description The problem's description.
   * @param exception The associated exception if any.
   * @param mailto The associated mail-to address if any.
   * @param pluginID The ID of the plug-in where this problem
   * happened.
   */
  public Problem( final String id,
                  final String description,
                  final Throwable exception,
                  final String mailto,
                  final String pluginID ) {
    this.id = id;
    this.description = description;
    this.exception = exception;
    this.mailto = mailto;
    this.pluginID = pluginID;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.IProblem#addReason(java.lang.String)
   */
  public void addReason( final String reason ) {
    if ( this.reasons == null ) {
      this.reasons = new ArrayList< String >();
    }
    this.reasons.add( reason );
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.IProblem#addSolution(eu.geclipse.core.reporting.ISolution)
   */
  public void addSolution( final ISolution solution ) {
    
    if ( this.solutions == null ) {
      this.solutions = new ArrayList< ISolution >();
    }
    
    String solutionID = solution.getID();
    if ( solutionID != null ) {
      for ( ISolution s : this.solutions ) {
        if ( solutionID.equals( s.getID() ) ) {
          this.solutions.remove( s );
          break;
        }
      }
    }
    
    this.solutions.add( solution );
    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.IProblem#addSolution(java.lang.String, java.lang.String)
   */
  public void addSolution( final String solutionID, final String desc ) {
    SolutionFactory factory = SolutionFactory.getFactory();
    Solution solution = factory.getSolution( solutionID, desc );
    if ( solution != null ) {
      addSolution( solution );
    }
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.IProblem#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.IProblem#getException()
   */
  public Throwable getException() {
    return this.exception;
  }
  
  /**
   * Get the problem's ID.
   * 
   * @return The ID of the problem or <code>null</code> if this
   * problem was created programmatically.
   */
  public String getID() {
    return this.id;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.IProblem#getMailTo()
   */
  public String getMailTo() {
    return this.mailto;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.IProblem#getPluginID()
   */
  public String getPluginID() {
    return this.pluginID;
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.IProblem#getReasons()
   */
  public String[] getReasons() {

    String[] result;
    
    if ( this.reasons != null ) {
      result = this.reasons.toArray( new String[ this.reasons.size() ] );
    } else {
      result = new String[ 0 ];
    }
    
    return result;
    
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.IProblem#getSolutions()
   */
  public ISolution[] getSolutions() {

    ISolution[] result;
    
    if ( this.solutions != null ) {
      result = this.solutions.toArray( new ISolution[ this.solutions.size() ] );
    } else {
      result = new ISolution[ 0 ];
    }
    
    return result;
    
  }
  
  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.IProblem#hasReasons()
   */
  public boolean hasReasons() {
    return ( this.reasons != null ) && ! this.reasons.isEmpty(); 
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.IProblem#hasSolutions()
   */
  public boolean hasSolutions() {
    return ( this.solutions != null ) && ! this.solutions.isEmpty(); 
  }

  /* (non-Javadoc)
   * @see eu.geclipse.core.reporting.IProblem#getStatus()
   */
  public IStatus getStatus() {

    IStatus result = null;
    
    String pid = getPluginID();
    if ( pid == null ) {
      pid = IProblemReporting.PLUGIN_ID;
    }
    
    int code = 0;
    if ( getID() != null ) {
      code = getID().hashCode();
    }
    
    if ( hasReasons() ) {
      
      String[] reasonList = getReasons();
      
      result = new MultiStatus(
        pid,
        code,
        getDescription(),
        getException()
      );
      
      for ( String reason : reasonList ) {
        IStatus status = new Status(
            IStatus.ERROR,
            pid,
            code,
            reason,
            null
        );
        ( ( MultiStatus ) result ).add( status );
      }
      
    } else {
      result = new Status(
        IStatus.ERROR,
        pid,
        code,
        getDescription(),
        getException()
      );
    }
    
    return result;
    
  }

}
