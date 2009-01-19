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
 *     Mariusz Wojtysiak - initial API and implementation
 *     
 *****************************************************************************/
package eu.geclipse.jsdl.parametric;


/**
 * Exception thrown when generation of parametric jobs was canceled (e.g. by the user)
 */
public class ParametricGenerationCanceled extends ParametricJsdlException {

  /**
   * 
   */
  private static final long serialVersionUID = -9191917323935543073L;

  /**
   * 
   */
  public ParametricGenerationCanceled() {
    super( "Operation canceled" ); //$NON-NLS-1$
  }
}
