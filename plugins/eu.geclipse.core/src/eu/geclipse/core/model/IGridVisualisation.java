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

import eu.geclipse.core.reporting.ProblemException;



/**
 * An interfaces for integrating visualisation into the Grid model.
 */
public interface IGridVisualisation extends IGridContainer {

  /**
   * @return user's description for the specific VTK pipeline
   */
  String getDescription();

  /**
   * Runs the rendering of the VTK pipeline either locally or
   * remotely based on what is described in the vtkpipeline model.
   */
  void render();

  /**
   * @throws ProblemException
   */
  void validate() throws ProblemException;


}
