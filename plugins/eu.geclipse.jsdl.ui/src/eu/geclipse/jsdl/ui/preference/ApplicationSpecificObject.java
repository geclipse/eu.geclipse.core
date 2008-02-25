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
 *     PSNC - Katarzyna Bylec
 *           
 *****************************************************************************/
package eu.geclipse.jsdl.ui.preference;

import org.eclipse.core.runtime.IPath;

/**
 * Class representing data necesary to create extra pages for New Job Wizard.
 * See also {@link ApplicationSpecificRegistry}.
 */
public class ApplicationSpecificObject {

  private String appName;
  private String appPath;
  private IPath xmlPath;
  private IPath jsdlPath;
  private int id;

  /**
   * Creates new ApplicationSpecificObject. This method does not serialize data -
   * an object is created but will be destroyed when Eclipse is stopped.
   * 
   * @param id
   * @param appName
   * @param path
   * @param xmlPath
   * @param jsdlPath
   */
  public ApplicationSpecificObject( final int id,
                                    final String appName,
                                    final String path,
                                    final IPath xmlPath,
                                    final IPath jsdlPath )
  {
    this.id = id;
    this.appName = appName;
    this.appPath = path;
    this.xmlPath = xmlPath;
    this.jsdlPath = jsdlPath;
  }

  /**
   * Method to access path to basic JSDL
   * 
   * @return JSDL path
   */
  public IPath getJsdlPath() {
    return this.jsdlPath;
  }

  /**
   * Method to set basic JSDL path
   * 
   * @param newJsdlPath path to set
   */
  public void setJSDLPath( final IPath newJsdlPath ) {
    this.jsdlPath = newJsdlPath;
  }

  /**
   * Method to access application name
   * 
   * @return name of application for which specific data is kept
   */
  public String getAppName() {
    return this.appName;
  }

  /**
   * Method to set application name, for which the specific data is kept
   * 
   * @param appName application name to set
   */
  public void setAppName( final String appName ) {
    this.appName = appName;
  }

  /**
   * Method to access path to executable file of application
   * 
   * @return path (local, usually absolute) in form of String to application's
   *         executable file (e.g. /bin/echo)
   */
  public String getAppPath() {
    return this.appPath;
  }

  /**
   * Method to set path to application's executable file
   * 
   * @param appPath path to application's executable file in form of String
   *            (e.g. /bin/echo)
   */
  public void setAppPath( final String appPath ) {
    this.appPath = appPath;
  }

  /**
   * Path to user's local XML file in which specific wizard pages for
   * application are defined
   * 
   * @return path to XML file on user's machine. This file keep information of
   *         how should wizard specific pages look like for application
   */
  public IPath getXmlPath() {
    return this.xmlPath;
  }

  /**
   * Method to set path to XML file in which information of how should specific
   * wizard pages look like is kept
   * 
   * @param xmlPath path to set
   */
  public void setXmlPath( final IPath xmlPath ) {
    this.xmlPath = xmlPath;
  }

  /**
   * Get unique id that distinguishes this object among other
   * {@link ApplicationSpecificObject}s
   * 
   * @return unique {@link ApplicationSpecificObject}'s id
   */
  public int getId() {
    return this.id;
  }
}
