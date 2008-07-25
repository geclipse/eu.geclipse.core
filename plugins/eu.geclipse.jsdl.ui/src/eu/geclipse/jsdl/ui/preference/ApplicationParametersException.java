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
package eu.geclipse.jsdl.ui.preference;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

import eu.geclipse.core.model.IGridApplicationParameters;

/**
 * Exception for invalid operations with {@link IGridApplicationParameters}
 * objects or {@link ApplicationParametersRegistry}.
 */
public class ApplicationParametersException extends CoreException {

  /**
   * Creates instance of exception.
   * 
   * @param status status which will be used as a base for this exception's
   *            instance
   */
  public ApplicationParametersException( final IStatus status ) {
    super( status );
  }
}
