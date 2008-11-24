/*****************************************************************************
 * Copyright (c) 2008 g-Eclipse Consortium 
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

package eu.geclipse.core.config;

import java.util.Set;

import org.eclipse.core.filesystem.IFileStore;

import eu.geclipse.core.reporting.ProblemException;

/**
 * This interface is used in the configurator framework in order to hold and
 * provide configuration parameters. These parameters are keyed values of
 * Strings or arrays of Strings.
 */
public interface IConfiguration {
  
  /**
   * Get the keys of all currently registered parameters.
   * 
   * @return A {@link Set} of keys for all currently registered parameters.
   */
  public Set< String > getKeys();
  
  /**
   * Get the parameter corresponding to the specified key. If no parameter with
   * the specified key is found or if the parameter is a multi-string parameter
   * <code>null</code> will be returned.
   * 
   * @param key The key of the parameter to be returned.
   * @return The single-string parameter or <code>null</code> if either the
   * paremeter does not exist or if the specified key corresponds to a
   * multi-string parameter.
   */
  public String getParameter( final String key );
  
  /**
   * Get the parameter corresponding to the specified key. If no parameter with
   * the specified key is found <code>null</code> will be returned. If the
   * parameter corresponds to a single-string this string is wrapped into a
   * one-element array before returning.
   *  
   * @param key The key of the parameter to be returned.
   * @return The multi-string parameter or <code>null</code> if no such
   * parameter exists.
   */
  public String[] getParameters( final String key );
  
  /**
   * Load the configuration from the specified file store.
   * 
   * @param store The {@link IFileStore} from which to load the parameters.
   * @throws ProblemException If the file store does not exists or an error
   * occurs while reading the data from the file store.
   */
  public void loadFromXML( final IFileStore store ) throws ProblemException;
  
  /**
   * Save this configuration to the specified file store.
   * 
   * @param store The {@link IFileStore} to which to save the configuration.
   * @throws ProblemException If an error occurs while saving the configuration.
   */
  public void storeToXML( final IFileStore store ) throws ProblemException;
  
}
