/******************************************************************************
 * Copyright (c) 2006 g-Eclipse consortium
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
 *     Sylva Girtelschmid - JKU
 *****************************************************************************/
package eu.geclipse.core.model;



/**
 * An interfaces for integrating visualisation into the Grid model.
 */
public interface IGridVisualization extends IGridContainer {

  /**
   * @return user's description for the specific VTK pipeline 
   */
  String getDescription();

  /**
   * Locally runs the rendering of the VTK pipeline based on 
   * what is described in the vtkpipeline model.
   */
  void renderLocal();

  /**
   * Remotely runs the rendering of the VTK pipeline based on 
   * what is described in the vtkpipeline model.
   */
  void renderRemote();

  /**
   * @return true if the vtkpipeline specification is complete and correct.
   */
  boolean isValid();
  
}
