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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.core.model;

import org.eclipse.core.runtime.IPath;

public interface IGridApplicationParameters {

  /**
   * Method to access path to basic JSDL
   * 
   * @return JSDL path
   */
  public IPath getJsdlPath();

  /**
   * Method to access application name
   * 
   * @return name of application for which specific data is kept
   */
  public String getApplicationName();

  /**
   * Method to access path to executable file of application
   * 
   * @return path (local, usually absolute) in form of String to application's
   *         executable file (e.g. /bin/echo)
   */
  public String getApplicationPath();

  /**
   * Path to user's local XML file in which specific wizard pages for
   * application are defined
   * 
   * @return path to XML file on user's machine. This file keep information of
   *         how should wizard specific pages look like for application
   */
  public IPath getXmlPath();
  
  public int getId();
  
  public IVirtualOrganization getVO();

  public void setId( int currentIdPointer );
  
}
