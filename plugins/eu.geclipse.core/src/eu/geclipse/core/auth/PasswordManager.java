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
 *    Moritz Post       - switch to use new Equinox secure storage
 *    Ariel Garcia      - updated to allow both secure storage or internal
 *****************************************************************************/

package eu.geclipse.core.auth;

import java.util.Hashtable;

import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;

import eu.geclipse.core.internal.Activator;


/**
 * The <code>PasswordManager</code> provides some static methods to manage
 * passwords. Therefore each password is stored with a unique password ID and
 * can afterwards be retrieved again with this ID. In the case of a private key
 * this id could be the path to the key file in the file system. If the key file
 * is then opened for the first time the user is asked for the password. Then
 * the password is stored with the file path as <code>pwuid</code>. If the file
 * has to be opened a second time the user does not have to specify the password
 * again since the PasswordManager has already stored it and knows that it
 * belongs to the key file. The PasswordManager currently relies on Equinox's
 * secure storage (SS) mechanism to persist passwords across sessions. Note
 * however that this SS does have an access password of its own. Therefore,
 * depending on the SS settings and also the platform, the user might need
 * to provide the SS password to access its stored passwords. If the user avoids
 * providing this SS password then an internal in-memory implementation is used
 * to keep already entered passwords for the lifetime of the gEclipse session.
 * 
 * @author stuempert-m
 */
public abstract class PasswordManager {

  /** A constant to denote the password field. */
  private static final String PASSWORD = "password"; //$NON-NLS-1$

  /** The error code for the case "user denied providing password" */
  private static final int SECURE_STORAGE_NO_PASSWD_PROVIDED = 4;

  /** A flag denoting that the user allowed access to secure storage */
  private static boolean useSecureStorage = true;

  /**
   * The internal list of managed passwords.
   */
  private static Hashtable< String, String > registeredPasswords
           = new Hashtable< String, String >();


  /**
   * Selects the type of storage to use, either Equinox's secure
   * storage or the internal implementation. Needed in certain cases
   * if running in head-less mode. Do not switch the mode if the user
   * is interacting with the system!
   * 
   * @param useSS if Equinox's secure storage should be used or not
   */
  public static void setUseSecureStorage( final boolean useSS ) {
    PasswordManager.useSecureStorage = useSS;
  }

  /**
   * Look up the password with the specified ID and return it if it could be
   * found.
   * 
   * @param pwuid The unique ID of the password.
   * @return The password if it could be found, null otherwise.
   */
  public static String getPassword( final String pwuid ) {
    String result = null;

    if ( useSecureStorage ) {
      ISecurePreferences securePreferences = SecurePreferencesFactory.getDefault();
      ISecurePreferences node = securePreferences.node( pwuid );

      try {
        result = node.get( PasswordManager.PASSWORD, null );
      } catch ( StorageException storageEx ) {
        handleStorageException( storageEx );
      }
    }

    // Be careful, handleStorageException() above could have switched back to the internal store
    if ( ! useSecureStorage ) {
      result = registeredPasswords.get( pwuid );
    }

    return result;
  }

  /**
   * Register a password with the specified ID in this password manager. If a
   * password with the same ID was registered before it is erases before this
   * password is registered.
   * 
   * @param pwuid The unique ID of the new password.
   * @param pw The password that should be registered.
   */
  public static void registerPassword( final String pwuid, final String pw ) {

    if ( useSecureStorage ) {
      ISecurePreferences securePreferences = SecurePreferencesFactory.getDefault();
      ISecurePreferences node = securePreferences.node( pwuid );

      try {
        node.put( PasswordManager.PASSWORD, pw, true );
      } catch ( StorageException storageEx ) {
        handleStorageException( storageEx );
      }
    }

    // Be careful, handleStorageException() above could have switched back to the internal store
    if ( ! useSecureStorage ) {
      registeredPasswords.put( pwuid, pw );
    }

  }

  /**
   * Erase the password with the specified ID. Nothing happens if the password
   * could not be found.
   * 
   * @param pwuid The unique ID of the password that should be erased.
   */
  public static void erasePassword( final String pwuid ) {

    if ( useSecureStorage ) {
      ISecurePreferences securePreferences = SecurePreferencesFactory.getDefault();
      ISecurePreferences node = securePreferences.node( pwuid );
      node.remove( PasswordManager.PASSWORD );
    } else {
      registeredPasswords.remove( pwuid );
    }

  }

  /**
   * Helper method to handle the exceptions thrown by the secure store
   * 
   * @param storageExc the storage exception to handle
   */
  private static void handleStorageException( final StorageException storageExc ) {

    // Switch to use the internal store unconditionally, for robustness
    useSecureStorage = false;

    // The user declined accessing the secure storage
    if ( storageExc.getErrorCode() == SECURE_STORAGE_NO_PASSWD_PROVIDED ) {
      // Do nothing
    } else {
      // Some other error, just log it
      Activator.logException( storageExc );
    }

  }

}
