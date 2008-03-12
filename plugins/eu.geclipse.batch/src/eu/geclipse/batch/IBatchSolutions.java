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
 * Identifiers for solutions that are declared in this plug-in. 
 * The solutions are declared in the plugin.xml file.
 * 
 * @author hgjermund
 */
public interface IBatchSolutions {
  /*
   * Please keep the list sorted by extensionID!
   */

  /**
   * Id for the solution to check username and password
   */
  public static final String CHECK_USERNAME_AND_PASSWORD 
    = "eu.geclipse.batch.solution.check_username_password"; //$NON-NLS-1$
  
  /**
   * Id for the solution to check ssh service configuration
   */
  public static final String CHECK_SSH_SERVER_CONFIG
    = "eu.geclipse.batch.solution.check_ssh_server_config"; //$NON-NLS-1$
}
