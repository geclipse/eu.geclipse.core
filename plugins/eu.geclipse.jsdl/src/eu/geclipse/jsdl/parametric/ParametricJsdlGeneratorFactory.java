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

import eu.geclipse.jsdl.parametric.internal.ParametricJsdlGenerator;


/**
 * Factory producing generator of non parametric JSDL based on parametric JSDL (containing sweep extension)
 */
public class ParametricJsdlGeneratorFactory {
  
  /**
   * @param parametricJsdl JSDL with sweep-extensions, for which generator should be obtained 
   * @return object generating non parametric JSDL
   */
  static public IParametricJsdlGenerator getGenerator( final String parametricJsdl ) {
    return new ParametricJsdlGenerator( parametricJsdl );
  }
  
}
