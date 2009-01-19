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
 * Exception thrown in case of error occured during generating JSDL from parametric JSDL
 */
public class ParametricJsdlException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -3771003389992793660L;

  /**
   * @param message
   */
  public ParametricJsdlException( final String message ) {
    super( message );
  }

  /**
   * @param message
   * @param cause other exception, which causes that generation cannot be done
   */
  public ParametricJsdlException( final String message,
                                  final Exception cause )
  {
    super( message, cause );
  }
}
