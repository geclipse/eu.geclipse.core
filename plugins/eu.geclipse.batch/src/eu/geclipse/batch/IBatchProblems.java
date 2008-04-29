/******************************************************************************
 * Copyright (c) 2008 g-Eclipse consortium
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial development of the original code was made for
 * project g-Eclipse founded by European Union
 * project number: FP6-IST-034327  http://www.geclipse.eu/
 *
 * Contributor(s):
 *     UCY (http://www.cs.ucy.ac.cy)
 *      - Harald Gjermundrod (harald@cs.ucy.ac.cy)
 *
 *****************************************************************************/
package eu.geclipse.batch;

/**
 * Identifiers for problems that are declared in this plug-in. 
 * The problems are declared in the plugin.xml file.
 * 
 * @author hgjermund
 */
public interface IBatchProblems {

  /**
   * Id for the problem of connection io error
   */
  public static final String CONNECTION_IO_ERROR 
    = "eu.geclipse.batch.problem.io_error"; //$NON-NLS-1$
  
  /**
   * Id for the problem of command which failed
   */
  public static final String COMMAND_FAILED 
    = "eu.geclipse.batch.problem.command_failed"; //$NON-NLS-1$
  
  /**
   * Id for the problem of a authorization failure
   */
  public static final String NO_AUTHORIZATION 
    = "eu.geclipse.batch.problem.no_authorization"; //$NON-NLS-1$
  
  /**
   * Id for the problem of login failure
   */
  public static final String LOGIN_FAILED 
    = "eu.geclipse.batch.problem.login_failed"; //$NON-NLS-1$
  
  /**
   * Id for the problem of getting a ssh serivce connection
   */
  public static final String GET_SSH_SERVICE_FAILED 
    = "eu.geclipse.batch.problem.get_ssh_service_failed"; //$NON-NLS-1$
}
