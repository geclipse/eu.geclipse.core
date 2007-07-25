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

package eu.geclipse.core.filesystem.internal.filesystem;

/**
 * Centrally defined g-Eclipse file system properties.
 */
public interface IFileSystemProperties {
  
  /**
   * The g-Eclipse file system scheme.
   */
  public static final String SCHEME = "gecl"; //$NON-NLS-1$
  
  /**
   * The ID of the query part for g-Eclipse file systems.
   */
  public static final String QUERY_ID = "geclslave"; //$NON-NLS-1$
  
  /**
   * The assign part of the query part for g-Eclipse file systems.
   */
  public static final String QUERY_ASSIGN = "="; //$NON-NLS-1$
  
  /**
   * The separator for multiple queries.
   */
  public static final String QUERY_SEPARATOR = "&";  //$NON-NLS-1$

}
