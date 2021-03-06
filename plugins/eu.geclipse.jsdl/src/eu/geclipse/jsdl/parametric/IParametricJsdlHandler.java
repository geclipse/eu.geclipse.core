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


import java.util.List;

/**
 * Handler passed to {@link IParametricJsdlGenerator}. Methods from that
 * interface are called during generation JSDLs for Parametric JSDL.<br>
 * It's useful to control generation process (e.g. serialization of generated
 * JSDL or tracking substitution of parameters).
 */
public interface IParametricJsdlHandler {

  /**
   * Called, when sweep jsdl is successfully validated and generation process is
   * starting
   * 
   * @param generatedJsdls number of JSDLs, which will be generated (number of
   *          iterations)
   * @param paramNames list of XPath queries selecting nodes, which values will be replaced during generation
   * @throws ParametricJsdlException may be thrown in case on error in order to interrupt generation process
   */
  void generationStarted( int generatedJsdls, List<String> paramNames ) throws ParametricJsdlException;

  /**
   * Called after last JSDL was generated
   * @throws ParametricJsdlException may be thrown in case on error in order to interrupt generation process
   * 
   */
  void generationFinished() throws ParametricJsdlException;

  /**
   * Called, when all parameters were substituted and new generated jsdl is
   * ready. Now you can store it on disk, show for user, submit etc
   * 
   * @param generatedJsdl new instance of jsdl
   * @param monitor progress monitor
   * @throws ParametricJsdlException may be thrown in case on error in order to interrupt generation process
   */
  void newJsdlGenerated( IGeneratedJsdl generatedJsdl ) throws ParametricJsdlException;
  
  /**
   * @return <code>true</code> if generation process is cancelled and should be interrupted 
   */
  boolean isCanceled();
}
