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
 *    Ariel Garcia      - added more strings
 *****************************************************************************/

package eu.geclipse.core;

public interface ICoreProblems {
  
  /*
   * Please keep the list sorted by extensionID!
   */
  
  // IO
  public static final String UNSPECIFIED_IO_PROBLEM
    = "eu.geclipse.problem.io.unspecified"; //$NON-NLS-1$
  
  // Net
  public static final String BIND_FAILED
    = "eu.geclipse.problem.net.bindFailed"; //$NON-NLS-1$
  
  public static final String CONNECTION_FAILED
    = "eu.geclipse.problem.net.connectionFailed"; //$NON-NLS-1$
  
  public static final String CONNECTION_TIMEOUT
    = "eu.geclipse.problem.net.connectionTimeout"; //$NON-NLS-1$
  
  public static final String UNKNOWN_HOST
    = "eu.geclipse.problem.net.unknownHost"; //$NON-NLS-1$
  
  // System
  public static final String SYSTEM_TIME_CHECK_FAILED
    = "eu.geclipse.problem.sys.systemTimeCheckFailed"; //$NON-NLS-1$

}
