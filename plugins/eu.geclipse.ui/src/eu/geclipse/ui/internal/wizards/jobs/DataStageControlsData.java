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
package eu.geclipse.ui.internal.wizards.jobs;

import org.eclipse.swt.widgets.Control;

/**
 * Class to keep information about controls that hold parameters and values of
 * files that need to be staged in or out to/form executable host
 */
public class DataStageControlsData {

  private Control nameControl;
  private Control URIControl;
  private String argName;
  private boolean isMultipleList;

  /**
   * Creates new instance of the class
   * 
   * @param argName name of the parameter for JSDL
   * @param nameControl control that holds name of file
   * @param URIControl control that holds URI of file
   */
  public DataStageControlsData( final String argName,
                                final Control nameControl,
                                final Control URIControl )
  {
    this.argName = argName;
    this.nameControl = nameControl;
    this.URIControl = URIControl;
    if( this.URIControl == null ) {
      this.isMultipleList = true;
    } else {
      this.isMultipleList = false;
    }
  }

  /**
   * Method to access {@link DataStageControlsData#argName}
   * 
   * @return name of the argument
   */
  public String getArgName() {
    return this.argName;
  }

  /**
   * Method to access {@link DataStageControlsData#argName}
   * 
   * @return control that holds name of the file
   */
  public Control getNameControl() {
    return this.nameControl;
  }

  /**
   * Method to access {@link DataStageControlsData#URIControl}
   * 
   * @return control that holds URI of the file
   */
  public Control getURIControl() {
    return this.URIControl;
  }

  /**
   * Method to find out if controls whose information are kept in this object
   * are lists
   * 
   * @return true if one of the controls can hold more than one value
   */
  public boolean isMultipleList() {
    return this.isMultipleList;
  }
}
