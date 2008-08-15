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

package eu.geclipse.traceview;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Interface for trace data.
 */
public interface ITrace extends IPropertySource {

  /**
   * Returns the number of processes the trace contains.
   * 
   * @return number of processes.
   */
  int getNumberOfProcesses();
  
  /**
   * Returns the trace data for the specified process number.
   * 
   * @param processId Process number to get event data for.
   * @return A object implementing IProcessTrace which provides event data of
   *         this process.
   * @throws IndexOutOfBoundsException Thrown if the process id is out of
   *             bounds.
   */
  IProcess getProcess( int processId ) throws IndexOutOfBoundsException;

  /**
   * Returns the name of the trace
   * 
   * @return trace name
   */
  String getName();

  /**
   * Returns the path to the trace.
   * 
   * @return path
   */
  IPath getPath();
  
  /**
   * Sets userdata. This can be used for example for marker plugins which
   * require additional data for marking. The id should be the plugin id
   * of the plugin setting the user data.
   * @param id the id of the user data (should be the plugin id of the plugin
   *        using the plugin data)
   * @param data the user data object
   */
  void setUserData( String id, Object data );

  /**
   * Returns the user data associated to an id.
   * @param id the id of the user data
   * @return the user data object
   */
  Object getUserData( String id );
}
