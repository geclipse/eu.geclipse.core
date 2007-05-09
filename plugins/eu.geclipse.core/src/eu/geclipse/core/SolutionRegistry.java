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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import eu.geclipse.core.internal.Activator;

/**
 * This class is the central repository for core related solutions. Whenever
 * no other registry is specified this standard registry is used to query
 * solutions. 
 */
public class SolutionRegistry {
  
  /**
   * Unique ID of the check authentication tokens solution.
   */
  public static final int CHECK_AUTH_TOKENS = uniqueID();
  
  /**
   * Unique ID of the check CA certificates solution.
   */
  public static final int CHECK_CA_CERTIFICATES = uniqueID();
  
  /**
   * Unique ID of the check internet connection solution.
   */
  public static final int CHECK_INTERNET_CONNECTION = uniqueID();
  
  /**
   * Unique ID of the check proxy settings solution.
   */
  public static final int CHECK_PROXY_SETTINGS = uniqueID();
  
  /**
   * Unique ID of the check timeout settings solution.
   */
  public static final int CHECK_TIMEOUT_SETTINGS = uniqueID();
  
  /**
   * Unique ID of the check server URL solution.
   */
  public static final int CHECK_SERVER_URL = uniqueID();
  
  /**
   * Unique ID of the check VO settings solution.
   */
  public static final int CHECK_VO_SETTINGS = uniqueID();
  
  /**
   * Unique ID of the server may be down solution.
   */
  public static final int SERVER_DOWN = uniqueID();
  
  /**
   * The singleton instance. 
   */
  private static SolutionRegistry singleton;
  
  /**
   * Field to track the last generated unique solution ID.
   */
  private static int lastUniqueID = 0;
  
  /**
   * Internal constructor.
   */
  protected SolutionRegistry() {
    // empty implementation
  }
  
  /**
   * Access method to the singleton instance.
   * 
   * @return The singleton instance that will be created if
   * it is yet <code>null</code>. 
   */
  public static SolutionRegistry getRegistry() {
    if ( singleton == null ) {
      singleton = new SolutionRegistry();
    }
    return singleton;
  }
  
  /**
   * Get the solution with the specified ID. If no such solution could be
   * found <code>null</code> is returned.
   * 
   * @param solutionID The unique ID of the solution.
   * @return The solution with the specified ID or <code>null</code>.
   */
  public ISolution getSolution( final int solutionID ) {
    ISolution solution = findSolution( solutionID );
    if ( solution == null ) {
      IStatus status = new Status(
        IStatus.WARNING,
        Activator.PLUGIN_ID,
        IStatus.OK,
        String.format( Messages.getString("SolutionRegistry.no_solution_found"), //$NON-NLS-1$
                       new Integer( solutionID ) ),
        null
      );
      Activator.logStatus( status );
    }
    return solution;
  }
  
  /**
   * Try to find the solution with the specified ID. If no such
   * solution could be found <code>null</code> is returned.
   * 
   * @param solutionID The solution's unique ID.
   * @return The solution with the specified unique ID or <code>null</code>
   * if no solution with that ID could be found.
   */
  protected ISolution findSolution( final int solutionID ) {
    
    ISolution solution = null;
    
    if ( solutionID == CHECK_AUTH_TOKENS ) {
      solution = new Solution( CHECK_AUTH_TOKENS, Messages.getString("SolutionRegistry.check_auth_tokens") ); //$NON-NLS-1$
    }
    
    else if ( solutionID == CHECK_CA_CERTIFICATES ) {
      solution = new Solution( CHECK_CA_CERTIFICATES, Messages.getString("SolutionRegistry.check_ca_certificates") ); //$NON-NLS-1$
    }
    
    else if ( solutionID == CHECK_INTERNET_CONNECTION ) {
      solution = new Solution( CHECK_INTERNET_CONNECTION, Messages.getString("SolutionRegistry.check_internet_connection") ); //$NON-NLS-1$
    }
    
    else if ( solutionID == CHECK_PROXY_SETTINGS ) {
      solution = new Solution( CHECK_PROXY_SETTINGS, Messages.getString("SolutionRegistry.check_proxy_settings") ); //$NON-NLS-1$
    }

    else if ( solutionID == CHECK_SERVER_URL ) {
      solution = new Solution( CHECK_SERVER_URL, Messages.getString("SolutionRegistry.check_server_url")); //$NON-NLS-1$
    }
    
    else if ( solutionID == CHECK_TIMEOUT_SETTINGS ) {
      solution = new Solution( CHECK_TIMEOUT_SETTINGS, Messages.getString("SolutionRegistry.check_timeout_settings") ); //$NON-NLS-1$
    }
    
    else if ( solutionID == CHECK_VO_SETTINGS ) {
      solution = new Solution( CHECK_VO_SETTINGS, "Check your VO settings" );
    }
    
    else if ( solutionID == SERVER_DOWN ) {
      solution = new Solution( SERVER_DOWN, Messages.getString("SolutionRegistry.server_down")); //$NON-NLS-1$
    }
    
    return solution;
    
  }
  
  /**
   * Get a unique ID for a solution. The returned ID is unique during runtime but
   * may change between different sessions.
   * 
   * @return A runtime unique ID.
   */
  protected static int uniqueID() {
    return ++lastUniqueID;
  }
  
}
