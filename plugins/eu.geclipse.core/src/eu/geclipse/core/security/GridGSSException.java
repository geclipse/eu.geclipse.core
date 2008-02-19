/*****************************************************************************
 * Copyright (c) 2006-2008 g-Eclipse Consortium 
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
 *    Ariel Garcia      - updated to new problem reporting
 *****************************************************************************/

package eu.geclipse.core.security;

import org.ietf.jgss.GSSException;

import eu.geclipse.core.internal.Activator;
import eu.geclipse.core.reporting.IProblem;
import eu.geclipse.core.reporting.IReportingService;
import eu.geclipse.core.reporting.ProblemException;
import eu.geclipse.core.reporting.ReportingPlugin;

/**
 * Problem exception that is dedicated to {@link GSSException}s and manages
 * the mapping of the GSS error codes to {@link IProblem}s.
 */
public class GridGSSException extends ProblemException {
  
  /**
   * The serial version UID.
   */
  private static final long serialVersionUID = 2329402671364280256L;

  /**
   * Construct a new <code>GridGSSException</code> from the specified
   * {@link GSSException} and map the GSS error code to a g-Eclipse
   * problem.
   *  
   * @param exc The {@link GSSException} to be wrapped up.
   * @param pluginID The id of the plug-in where the exception happened.
   */
  public GridGSSException( final GSSException exc, final String pluginID ) {
    super( createProblem( exc, null ) );
  }
  
  /**
   * Create a problem from the specified {@link GSSException}.
   * 
   * @param exc The exception that caused the problem.
   * @param pluginID The ID of the plug-in where the problem occurred.
   * If this is <code>null</code> the plug-in ID of the core is used
   * instead.
   * @return A problem created from the specified exception.
   */
  private static IProblem createProblem( final GSSException exc, final String pluginID ) {
    
    String pid = pluginID != null ? pluginID : Activator.PLUGIN_ID;
    
    IReportingService reportingService = ReportingPlugin.getReportingService();
    
    IProblem result = reportingService.createProblem( exc.getMajorString(), exc, null, pid );
    
    String reason = exc.getMinorString();
    if ( reason != null ) {
      result.addReason( exc.getMinorString() );
    }
    
    return result;
    
  }
  
}
