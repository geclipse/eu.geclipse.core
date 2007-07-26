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
package eu.geclipse.jsdl.ui.internal.preference;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

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
  public ApplicationSpecificObject( int id,
                                    String appName,
                                    String path,
                                    IPath xmlPath,
                                    IPath jsdlPath )
  {
    this.id = id;
    this.appName = appName;
    this.appPath = path;
    this.xmlPath = xmlPath;
    this.jsdlPath = jsdlPath;
  }

  /**
   * Method to acc
   * @return
   */
  public IPath getJsdlPath() {
    return this.jsdlPath;
  }

  public void setJSDLPath( IPath jsdlPath ) {
    this.jsdlPath = jsdlPath;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppName( String appName ) {
    this.appName = appName;
  }

  public String getAppPath() {
    return appPath;
  }

  public void setAppPath( String appPath ) {
    this.appPath = appPath;
  }

  public IPath getXmlPath() {
    return xmlPath;
  }

  public void setXmlPath( IPath xmlPath ) {
    this.xmlPath = xmlPath;
  }

  public int getId() {
    return this.id;
  }

}
