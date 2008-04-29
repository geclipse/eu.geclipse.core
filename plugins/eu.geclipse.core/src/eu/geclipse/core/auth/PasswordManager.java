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

package eu.geclipse.core.auth;

import java.util.Hashtable;

/**
 * The <code>PasswordManager</code> povides some static methods to manage passwords.
 * Therefore each password is stored with a unique password ID and can afterwards be
 * retrieved again with this ID. In the case of a private key this id could be the
 * path to the key file in the file system. If the key file is then opened for the
 * first time the user is asked for the password. Then the password is stored
 * with the file path as <code>pwuid</code>. If the file has to be opened a second
 * time the user has not again to specify the password since the PasswordManager has
 * already stored this password and knows that it belongs to the key file. Please note
 * that passwords are only hold in memory. They are not stored on disk and are
 * therefore not available across different sessions.
 * 
 * @author stuempert-m
 */
public abstract class PasswordManager {

  /**
   * The internal list of managed passwords.
   */
  static private Hashtable<String, String> registeredPasswords
    = new Hashtable<String, String>();
  
  /**
   * Look up the password with the specified ID and return it if it could be found.
   * 
   * @param pwuid The unique ID of the password.
   * @return The password if it could be found, null otherwise.
   */
  static public String getPassword( final String pwuid ) {
    return registeredPasswords.get( pwuid );
  }
  
  /**
   * Register a password with the specified ID in this password manager. If a password
   * with the same ID was registered before it is erases before this password is
   * registered.
   * 
   * @param pwuid The unique ID of the new password.
   * @param pw The password that should be registered.
   */
  static public void registerPassword( final String pwuid, final String pw ) {
    erasePassword( pwuid );
    registeredPasswords.put( pwuid, pw );
  }
  
  /**
   * Erase the password with the specified ID. Nothing happens if the password could
   * not be found.
   * 
   * @param pwuid The unique ID of the password that should be erased.
   */
  static public void erasePassword( final String pwuid ) {
    registeredPasswords.remove( pwuid );
  }
  
}
