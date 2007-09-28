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
 *     PSNC: 
 *      - Katarzyna Bylec (katis@man.poznan.pl)
 *           
 *****************************************************************************/
package eu.geclipse.jsdl;

import eu.geclipse.jsdl.model.DataStagingType;
import eu.geclipse.jsdl.model.JsdlPackage;
import eu.geclipse.jsdl.model.SourceTargetType;

/**
 * This class is for obtaining instances of model's objects. It hides subsequent
 * factories calls.
 * 
 * @author Kasia
 */
public class JSDLModelFacade {

  /**
   * Creates new instance of {@link DataStagingType}
   * 
   * @return new instance of {@link DataStagingType}
   */
  public static DataStagingType getDataStagingType() {
    return JsdlPackage.eINSTANCE.getJsdlFactory().createDataStagingType();
  }

  /**
   * Creates new instance of {@link SourceTargetType}. This object is needed to
   * set details of {@link DataStagingType} objects.
   * 
   * @return new instance of {@link SourceTargetType}
   */
  public static SourceTargetType getSourceTargetType() {
    return JsdlPackage.eINSTANCE.getJsdlFactory().createSourceTargetType();
  }
}
