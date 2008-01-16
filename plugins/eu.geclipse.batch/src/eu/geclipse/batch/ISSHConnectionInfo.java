/******************************************************************************
 * Copyright (c) 2007 g-Eclipse consortium
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

import com.jcraft.jsch.UserInfo;

/**
 * Interface containing information needed to establish an ssh connection.
 */
public interface ISSHConnectionInfo extends UserInfo {

  /**
   * @return the username of the account on the remote server.
   */
  public String getUsername();
  
  /**
   * @return the hostname of the remote server.
   */
  public String getHostname();
  
  /**
   * @return the port of the remote server.
   */
  public int getPort();

  /**
   * Returns if the user pushed cancel when queried for password for the ssh session.
   * @return Returns <code>true</code> if the user pushed cancel when asked for the pw,
   * <code>true</code> otherwise.
   */
  public boolean getCanceledPWValue();
}
