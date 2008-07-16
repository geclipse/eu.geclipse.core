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

package eu.geclipse.core.model;

import java.net.URI;

/**
 * An <code>IGridApplication</code> represents VO- and Computing specific
 * applications that are accessible to the user.
 */
public interface IGridApplication
    extends IGridResource {
  
  /**
   * The computings on which this application is installed.
   * 
   * @return The {@link IGridComputing}s.
   */
  public IGridComputing[] getComputing();

  /** get the tag of the installed application
   * must return a string which represents the tag
   * this tag is given by the deployer in the process of install and is published
   * on the info system
   * @return String
   */
  public String getTag();
  
  /** get the tag of the installed application
   * must return a URI which represents the script
   * this script is given by the user in the uninstall wizard
   * on the info system
   * @return URI
   */
  public URI getScript();
  
  /**set the script to the deployed application
   * this method is called by the uninstall wizard, so that the script can be 
   * delivered to the uninstall method
   * @param script
   */
  public void setScript( URI script );

  public IGridApplicationParameters getApplicationParameters();
  
}
