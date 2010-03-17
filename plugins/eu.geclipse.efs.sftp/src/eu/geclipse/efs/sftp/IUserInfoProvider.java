/*****************************************************************************
 * Copyright (c) 2006, 2008 g-Eclipse Consortium 
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
 *    Christof Klausecker GUP, JKU - initial API and implementation
 *****************************************************************************/

package eu.geclipse.efs.sftp;

import com.jcraft.jsch.UserInfo;

/**
 * Provides UserInfo
 */
public interface IUserInfoProvider {

  /**
   * Returns the UserInfo
   * 
   * @param username
   * @param hostname
   * @param password
   * @param passphrase
   * @param portNumber
   * @return UserInfo
   */
  public UserInfo getUserInfo( final String username,
                               final String hostname,
                               final String password,
                               final String passphrase,
                               final int portNumber );
}
