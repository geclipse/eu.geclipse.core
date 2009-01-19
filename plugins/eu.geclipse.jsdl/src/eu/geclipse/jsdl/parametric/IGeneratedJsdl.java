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

import org.w3c.dom.Document;


/**
 * Jsdl generated from parametric JSDL
 */
public interface IGeneratedJsdl {
  /**
   * @return XML Document containing generated JSDL (without sweep extension)
   */
  Document getDocument();
  
  /**
   * Returns value from any node in the generated Jsdl. It returns also values from nodes, which aren't parametrized 
   * @param paramName XPath query selecting XML node, from which value will be returned
   * @return value from Node
   * @throws ParametricJsdlException exception thrown when value cannot be read
   */
  String getParamValue( String paramName ) throws ParametricJsdlException;
  
  /**
   * @return String representing current iteration during generation process
   */
  String getIterationName();
}
